package com.eventpage.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the batchjoblog database table.
 * 
 */
@Entity
@Table(name = "batchjoblog")
@DynamicInsert
@DynamicUpdate
@Getter @Setter
public class Batchjoblog implements Serializable {    
    
    private static final long serialVersionUID = -2092543720779314708L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int batch_job_seq;
        
    private String batch_id;

    private String batch_date;

    private String batch_job_name;

    private String result_code;

    private String result_desc;

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

    public Batchjoblog() {
    }

}