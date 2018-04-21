package com.eventpage.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * The primary key class for the Commcode database table.
 * 
 */
@Getter @Setter
public class CommcodePK implements Serializable {

    private static final long serialVersionUID = -999647885927437800L;

    private String code_category;

    private String code_id;

    private String code;

    public CommcodePK() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((code_category == null) ? 0 : code_category.hashCode());
        result = prime * result + ((code_id == null) ? 0 : code_id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommcodePK other = (CommcodePK) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (code_category == null) {
            if (other.code_category != null)
                return false;
        } else if (!code_category.equals(other.code_category))
            return false;
        if (code_id == null) {
            if (other.code_id != null)
                return false;
        } else if (!code_id.equals(other.code_id))
            return false;
        return true;
    }

}