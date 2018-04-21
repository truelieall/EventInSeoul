package com.eventpage.entity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the seoulevent database table.
 * 
 */
@Entity
@Table(name = "seoulevent")
@IdClass(SeouleventPK.class)
@DynamicInsert
@DynamicUpdate
@Getter @Setter
public class Seoulevent implements Serializable {

    private static final long serialVersionUID = 3277848355255847122L;

    @Id
    private String createdate;

    @Id
    private int cultcode;

    private String agelimit;

    private String codename;

    @Lob
    private String contents;

    private String end_date;

    private String etc_desc;

    @Column(name = "FRST_REGISTER_ID")
    private String frstRegisterId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FRST_RGST_DTTM", columnDefinition = "DATETIME")
    private Date frstRgstDttm;

    private String gcode;

    private String homepage;

    private String inquiry;

    private String is_free;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDT_DTTM", columnDefinition = "DATETIME")
    private Date lastUpdtDttm;

    @Column(name = "LAST_UPDUSR_ID")
    private String lastUpdusrId;

    private String main_img;

    private String org_link;

    private String place;

    private String player;
    
    @Lob
    private String program;

    private String sponsor;

    private String strtdate;

    private int subjcode;

    private String support;

    private String ticket;

    private String time;

    private String title;

    private String use_fee;

    private String use_trgt;

    public Seoulevent() {
    }
    
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Seoulevent)) {
            return false;
        }
        Seoulevent castOther = (Seoulevent) other;
        
        if(this.agelimit.equals(castOther.agelimit)
        && this.codename.equals(castOther.codename)
        && this.contents.equals(castOther.contents)
        && this.end_date.equals(castOther.end_date)
        && this.etc_desc.equals(castOther.etc_desc)
        && this.gcode.equals   (castOther.gcode)
        && this.homepage.equals(castOther.homepage)
        && this.inquiry.equals (castOther.inquiry)
        && this.is_free.equals (castOther.is_free)
        && this.main_img.equals(castOther.main_img)
        && this.org_link.equals(castOther.org_link)
        && this.place.equals   (castOther.place) 
        && this.player.equals  (castOther.player)
        && this.program.equals (castOther.program)
        && this.sponsor.equals (castOther.sponsor)
        && this.strtdate.equals(castOther.strtdate)
        && this.support.equals (castOther.support)
        && this.ticket.equals  (castOther.ticket) 
        && this.time.equals    (castOther.time)
        && this.title.equals   (castOther.title)
        && this.use_fee.equals (castOther.use_fee)
        && this.use_trgt.equals(castOther.use_trgt)
        && this.subjcode == castOther.subjcode) return true;
    
        return false;
    }
}