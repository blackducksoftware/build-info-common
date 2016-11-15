/*
 * Copyright (C) 2016 Black Duck Software Inc.
 * http://www.blackducksoftware.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Black Duck Software ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Black Duck Software.
 */
package com.blackducksoftware.integration.build;

public class GavWithType {

    private final Gav gav;

    private final GavTypeEnum type;

    public GavWithType(Gav gav, GavTypeEnum type) {
        this.gav = gav;
        this.type = type;
    }

    public Gav getGav() {
        return gav;
    }

    public GavTypeEnum getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gav == null) ? 0 : gav.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GavWithType other = (GavWithType) obj;
        if (gav == null) {
            if (other.gav != null) return false;
        } else if (!gav.equals(other.gav)) return false;
        if (type != other.type) return false;
        return true;
    }

}
