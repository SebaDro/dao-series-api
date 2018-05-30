/*
 * Copyright (C) 2015-2018 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public License
 * version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */

package org.n52.series.db.da.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.n52.io.DatasetFactoryException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataRepositoryFactory implements IDataRepositoryFactory {

    private final ApplicationContext applicationContext;

    private final Map<String, DataRepository< ? , ? >> cache;

    public DefaultDataRepositoryFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.cache = new HashMap<>();
    }

    private Stream<DataRepository< ? , ? >> getAllDataAssemblers() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(DataAssembler.class);
        Collection<Object> dataAssembleTypes = beansWithAnnotation.values();
        return dataAssembleTypes.stream()
                                .filter(DataRepository.class::isInstance)
                                .map(DataRepository.class::cast);
    }

    @Override
    public boolean isKnown(String valueType) {
        return hasCacheEntry(valueType) || getAllDataAssemblers().map(this::getDataType)
                                                                 .filter(it -> it.equals(valueType))
                                                                 .findFirst()
                                                                 .isPresent();
    }

    private Optional<DataRepository< ? , ? >> findDataAssembler(String type) {
        return getAllDataAssemblers().filter(it -> getDataType(it).equals(type)).findFirst();
    }

    @Override
    public Set<String> getKnownTypes() {
        return getAllDataAssemblers().map(this::getDataType)
                                     .collect(Collectors.toSet());
    }

    private String getDataType(DataRepository< ? , ? > it) {
        return it.getClass().getAnnotation(DataAssembler.class).value();
    }

    @Override
    public DataRepository< ? , ? > create(String type) throws DatasetFactoryException {
        Optional<DataRepository< ? , ? >> assemblers = findDataAssembler(type);
        return addToCache(type, assemblers.orElseThrow(() -> new DatasetFactoryException("Unknown type: " + type)));
    }

    private DataRepository< ? , ? > addToCache(String valueType, DataRepository< ? , ? > assembler) {
        cache.put(valueType, assembler);
        return assembler;
    }

    @Override
    public boolean hasCacheEntry(String valueType) {
        return cache.containsKey(valueType);
    }

}
