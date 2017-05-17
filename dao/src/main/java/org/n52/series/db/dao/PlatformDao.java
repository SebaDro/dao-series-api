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

package org.n52.series.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.n52.series.db.DataAccessException;
import org.n52.series.db.beans.I18nPlatformEntity;
import org.n52.series.db.beans.PlatformEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PlatformDao extends AbstractDao<PlatformEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformDao.class);

    private static final String SERIES_PROPERTY = "platform";

    public PlatformDao(DbQueryFactory queryFactory, Session session) {
        super(queryFactory, session);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PlatformEntity> find(DbQuery query) {
        LOGGER.debug("find instance: {}", query);
        Criteria criteria = i18n(I18nPlatformEntity.class, getDefaultCriteria(), query);
        criteria.add(Restrictions.ilike(PlatformEntity.PROPERTY_NAME, "%" + query.getSearchTerm() + "%"));
        return query.addFilters(criteria, getDatasetProperty())
                    .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PlatformEntity> getAllInstances(DbQuery query) throws DataAccessException {
        final String seriesProperty = getDatasetProperty();
        Criteria criteria = i18n(I18nPlatformEntity.class, getDefaultCriteria(seriesProperty), query);
        return (List<PlatformEntity>) query.addFilters(criteria, seriesProperty)
                                           .list();
    }

    @Override
    protected String getDatasetProperty() {
        return SERIES_PROPERTY;
    }

    @Override
    protected Class<PlatformEntity> getEntityClass() {
        return PlatformEntity.class;
    }

}
