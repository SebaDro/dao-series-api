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

package org.n52.series.db.beans;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.n52.series.db.DataModelUtil;
import org.n52.series.db.beans.parameter.Parameter;

public abstract class DataEntity<T> {

    public static final String SERIES_PKID = "seriesPkid";

    public static final String PROPERTY_TIMESTART = "timestart";

    public static final String PROPERTY_TIMEEND = "timeend";

    public static final String PROPERTY_RESULTTIME = "resultTime";

    private Long pkid;

    // optional
    private Date timestart;

    // required
    private Date timeend;

    private T value;

    private Long seriesPkid;

    private GeometryEntity geometryEntity;

    private Boolean deleted;

    private Date validTimeStart;

    private Date validTimeEnd;

    private Date resultTime;

    private String valueType;

    private boolean parent;

    private boolean child;

    private Set<Parameter> parameters = new HashSet<>(0);

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    /**
     * @return timestamp
     * @deprecated use {@link #getTimeend()}
     */
    @Deprecated
    public Date getTimestamp() {
        return DataModelUtil.createUnmutableTimestamp(timeend);
    }

    /**
     * @param timestamp
     *        the timestamp
     * @deprecated use {@link #setTimeend(java.util.Date)}
     */
    @Deprecated
    public void setTimestamp(Date timestamp) {
        this.timeend = DataModelUtil.createUnmutableTimestamp(timestamp);
    }

    /**
     * @return the timestart
     * @since 2.0.0
     */
    public Date getTimestart() {
        return DataModelUtil.createUnmutableTimestamp(timestart);
    }

    /**
     * @param timestart
     *        the timestart
     * @since 2.0.0
     */
    public void setTimestart(Date timestart) {
        this.timestart = DataModelUtil.createUnmutableTimestamp(timestart);
    }

    /**
     * @return the timeend
     * @since 2.0.0
     */
    public Date getTimeend() {
        return DataModelUtil.createUnmutableTimestamp(timeend);
    }

    /**
     * @param timeend
     *        the timeend
     * @since 2.0.0
     */
    public void setTimeend(Date timeend) {
        this.timeend = DataModelUtil.createUnmutableTimestamp(timeend);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract boolean isNoDataValue(Collection<String> noDataValues);

    public Long getSeriesPkid() {
        return seriesPkid;
    }

    public void setSeriesPkid(Long seriesPkid) {
        this.seriesPkid = seriesPkid;
    }

    public GeometryEntity getGeometryEntity() {
        return geometryEntity;
    }

    public void setGeometryEntity(GeometryEntity geometryEntity) {
        this.geometryEntity = geometryEntity;
    }

    public boolean isSetGeometry() {
        return geometryEntity != null && !geometryEntity.isEmpty();
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getValidTimeStart() {
        return DataModelUtil.createUnmutableTimestamp(validTimeStart);
    }

    public void setValidTimeStart(Date validTimeStart) {
        this.validTimeStart = DataModelUtil.createUnmutableTimestamp(validTimeStart);
    }

    public Date getValidTimeEnd() {
        return DataModelUtil.createUnmutableTimestamp(validTimeEnd);
    }

    public void setValidTimeEnd(Date validTimeEnd) {
        this.validTimeEnd = DataModelUtil.createUnmutableTimestamp(validTimeEnd);
    }

    public boolean isSetValidTime() {
        return isSetValidStartTime() && isSetValidEndTime();
    }

    public boolean isSetValidStartTime() {
        return validTimeStart != null;
    }

    public boolean isSetValidEndTime() {
        return validTimeEnd != null;
    }

    public Date getResultTime() {
        return DataModelUtil.createUnmutableTimestamp(resultTime);
    }

    public void setResultTime(Date resultTime) {
        this.resultTime = DataModelUtil.createUnmutableTimestamp(resultTime);
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public Set<Parameter> getParameters() {
        return parameters;
    }

    @SuppressWarnings(value = "unchecked")
    public void setParameters(Object parameters) {
        if (parameters instanceof Set<?>) {
            this.parameters = (Set<Parameter>) parameters;
        } else {
            getParameters().add((Parameter) parameters);
        }
    }

    public boolean hasParameters() {
        return getParameters() != null && !getParameters().isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName())
          .append(" [");
        sb.append(" id: ")
          .append(pkid);
        return sb.append(" ]")
                 .toString();
    }
}
