package com.star.sky.member.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MemberBaseInfo implements Serializable {

    private static final long serialVersionUID = -2889099802329294028L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private int uuid;
    private String country;
    @Pattern(regexp = "1[3-9]\\d{9}", message = "PHONE_NUMBER_ERROR")
    private String phone;
    private String username;
    private String headImg;
    @TableField(select = false)
    @Length(min = 8, max = 16, message = "PASSWORD_ERROR")
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime registerTime;
    private int state;
    private String remark;

}
