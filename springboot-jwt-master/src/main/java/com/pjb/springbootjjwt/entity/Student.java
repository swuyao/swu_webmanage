package com.pjb.springbootjjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yc
 * @date 2018-07-08 20:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private static final long serialVersionUID = 1L;

    /**
     * 是校园卡号，也是账号（登录用）
     */
    private String studentnumber;

    /**
     * stuname
     */
    private String stuname;

    /**
     * 身份证号
     */
    private String idcards;

    /**
     * 入学时间
     */
    private String date;

    /**
     * 状态
     */
    private int state;

    /**
     * 电话
     */
    private String tel;

    /**
     * 公司
     */
    private String department;

    /**
     * 地址
     */
    private String address;

    /**
     * 收件人姓名
     */
    private String receiveName;

    /**
     * 收件人电话
     */
    private String receiveTel;

    /**
     * 指导教师编号
     */
    private int zdteacher;

    /**
     * 论文
     */
    private String thesis;

    /**
     * 开题报告
     */
    private String thesisProposal;


}
