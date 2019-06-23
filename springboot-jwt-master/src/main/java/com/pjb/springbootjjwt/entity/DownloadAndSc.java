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

/**
 * <p>
 *
 * </p>
 *
 * @author yc
 * @since 2018-12-10
 */
public class DownloadAndSc {

    private Integer id;

    /**
     * 文件上传者
     */
    private String filemaker;

    /**
     * 文件名和地址
     */
    private String filename;
    private String files;//文件地址
    private String pdffilename;
    private String pdffiles;//pdf文件地址

    private Integer courseid;

    /**
     * 文件日期
     */
    private Date filedate;
    private String filemakername;
    private Date posttime;
    private String remark;
    private Integer score;





    private String state;
    ;


    /**
     * 成绩表
     */




}
