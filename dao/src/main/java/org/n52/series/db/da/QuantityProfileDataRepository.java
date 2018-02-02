/*
 * Copyright (C) 2015-2017 52°North Initiative for Geospatial Open Source
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

package org.n52.series.db.da;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.n52.io.response.dataset.profile.ProfileDataItem;
import org.n52.io.response.dataset.profile.ProfileValue;
import org.n52.io.response.dataset.quantity.QuantityValue;
import org.n52.series.db.beans.DataEntity;
import org.n52.series.db.beans.ProfileDataEntity;
import org.n52.series.db.beans.ProfileDatasetEntity;
import org.n52.series.db.beans.QuantityDataEntity;
import org.n52.series.db.dao.DbQuery;

public class QuantityProfileDataRepository extends ProfileDataRepository<BigDecimal> {

    private final QuantityDataRepository quantityRepository;

    public QuantityProfileDataRepository() {
        this.quantityRepository = new QuantityDataRepository();
    }

    @Override
    protected ProfileValue<BigDecimal> createValue(ProfileDataEntity observation,
                                               ProfileDatasetEntity datasetEntity,
                                               DbQuery query) {
        ProfileValue<BigDecimal> profile = prepareValue(observation, query);
        List<ProfileDataItem<BigDecimal>> dataItems = new ArrayList<>();
        for (DataEntity< ? > dataEntity : observation.getValue()) {
            QuantityDataEntity entity = (QuantityDataEntity) dataEntity;
            QuantityValue valueItem = quantityRepository.createValue(entity.getValue(), entity, query);
            addParameters(entity, valueItem, query);
            if (observation.hasVerticalFrom() || observation.hasVerticalTo()) {
                dataItems.add(assembleDataItem(entity, profile, observation));
            } else {
                for (Map<String, Object> parameterObject : valueItem.getParameters()) {
                    String verticalName = datasetEntity.getVerticalParameterName();
                    if (isVertical(parameterObject, verticalName)) {
                        dataItems.add(assembleDataItem(entity, profile, parameterObject));
                    }
                }
            }
        }
        profile.setValue(dataItems);
        return addMetadatasIfNeeded(observation, profile, datasetEntity, query);
    }

}
