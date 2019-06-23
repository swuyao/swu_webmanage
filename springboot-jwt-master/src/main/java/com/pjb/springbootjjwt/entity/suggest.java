package com.pjb.springbootjjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yc
 * @date 2018-07-08 20:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class suggest {
    private Integer id;

    /**
     * 课程名称
     */
    private String name;

    private String role;

    private String varchar;
    private Date suggedate;
    private String suggest;

}
