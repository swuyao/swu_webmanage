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
public class Notice {
    private Integer id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告日期
     */
    private Date noticedate;

    /**
     * 公告内容
     */
    private String noticecontent;

    private String noticemaker;
    private String type;

}
