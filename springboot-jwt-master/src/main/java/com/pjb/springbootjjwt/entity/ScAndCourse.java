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
public class ScAndCourse {
    private Integer id;
    private String studentnumber;

    /**
     * 成绩表
     */
    private Integer courseid;

    private Integer score;
    private String state;
    private Date year;
    private String homework;
    private String remark;
    private String teaname;
    private Date posttime;



    /**
     * 课程名称
     */
    private String coursename;

    private String skteacher;

    private String number;






}
