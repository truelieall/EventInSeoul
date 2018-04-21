package com.eventpage.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the commcode database table.
 * 
 */
@Entity
@Table(name = "commcode")
@IdClass(CommcodePK.class)
@DynamicInsert
@DynamicUpdate
@NamedQuery(name = "Commcode.findAll", query = "SELECT c FROM Commcode c order by code_category, code_id, code")
@Getter @Setter
public class Commcode implements Serializable {

    private static final long serialVersionUID = -6342050734788890534L;

    @Id
    private String code_category;

    @Id
    private String code_id;

    @Id
    private String code;

    private String codename;

    @Column(name = "FRST_REGISTER_ID")
    private String frstRegisterId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FRST_RGST_DTTM", columnDefinition = "DATETIME")
    private Date frstRgstDttm;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDT_DTTM", columnDefinition = "DATETIME")
    private Date lastUpdtDttm;

    @Column(name = "LAST_UPDUSR_ID")
    private String lastUpdusrId;

    public Commcode() {
    }

}