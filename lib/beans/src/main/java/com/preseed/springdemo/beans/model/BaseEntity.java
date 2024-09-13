package com.preseed.springdemo.beans.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity implements Serializable {

    private String createBy;

    private String updateBy;

    private Timestamp createTime;

    private Timestamp updateTime;

}