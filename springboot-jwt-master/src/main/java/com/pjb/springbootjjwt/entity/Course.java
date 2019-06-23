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
public class Course {
    private Integer courseid;

    /**
     * 课程名称
     */
    private String coursename;

    private String skteacher;

    private String number;
    private Date posttime;
    private String teaname;

}
