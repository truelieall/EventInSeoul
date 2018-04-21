package com.eventpage.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the Seouleventuser database table.
 * 
 */
@Entity
@Table(name = "seouleventuser")
@DynamicInsert
@DynamicUpdate
@Getter @Setter
public class Seouleventuser implements Serializable {

    private static final long serialVersionUID = 700807490604425159L;

    @Id
    private String user_id;

    private String user_type;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Seouleventmylist> mylist;

    public Seouleventuser() {
    }

}