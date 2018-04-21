package com.eventpage.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the seouleventmylist database table.
 * 
 */
@Entity
@Table(name = "seouleventmylist")
@DynamicInsert
@DynamicUpdate
@Getter @Setter
public class Seouleventmylist implements Serializable {

    private static final long serialVersionUID = 8141715174390743203L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int list_no;

    private String user_id;

    private int cultcode;

    @Column(name = "FRST_REGISTER_ID")
    private String frstRegisterId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FRST_RGST_DTTM", columnDefinition = "DATETIME")
    private Date frstRgstDttm;

    @Column(name = "LAST_UPDUSR_ID")
    private String lastUpdusrId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDT_DTTM", columnDefinition = "DATETIME")
    private Date lastUpdtDttm;

    public Seouleventmylist() {
    }

}