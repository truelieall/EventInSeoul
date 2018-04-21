package com.eventpage.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * The primary key class for the Seoulevent database table.
 * 
 */
@Getter @Setter
public class SeouleventPK implements Serializable {

    private static final long serialVersionUID = -4342119054986887946L;

    private String createdate;

    private int cultcode;

    public SeouleventPK() {
    }
    
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SeouleventPK)) {
            return false;
        }
        SeouleventPK castOther = (SeouleventPK) other;
        return this.createdate.equals(castOther.createdate) && (this.cultcode == castOther.cultcode);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.createdate.hashCode();
        hash = hash * prime + this.cultcode;

        return hash;
    }
}