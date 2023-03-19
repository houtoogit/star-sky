package com.star.sky.member.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MemberBaseInfo {

    private long id;
    private int base_uuid;
    private String base_country;
    private String base_phone;
    private String base_name;
    private String base_head_img;
    private String base_password;
    @DateTimeFormat(pattern = "YYYY-mm-dd HH:mm:ss")
    private Date base_register_time;
    private int base_state;
    private String remark;

}
