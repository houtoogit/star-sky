package com.star.sky.member.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
public class MemberBaseInfo implements Serializable {

    private static final long serialVersionUID = -2889099802329294028L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private int base_uuid;
    private String base_country;
    @Pattern(regexp = "1[3-9]\\d{9}", message = "PHONE_NUMBER_ERROR")
    private String base_phone;
    private String base_name;
    private String base_head_img;
    @Length(min = 8, max = 16, message = "PASSWORD_ERROR")
    private String base_password;
    @DateTimeFormat(pattern = "YYYY-mm-dd HH:mm:ss")
    private Date base_register_time;
    private int base_state;
    private String remark;

}
