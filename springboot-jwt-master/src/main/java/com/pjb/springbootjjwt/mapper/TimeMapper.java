package com.pjb.springbootjjwt.mapper;

import com.pjb.springbootjjwt.entity.Time;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface TimeMapper {
    Date showPostTime();
    Date showTheisTime();
    Date showAddressTime();

    boolean addPostTime(@Param("posttime") Date posttime);
    boolean addTheisTime(@Param("theistime") Date theistime);
    boolean addAddressTime(@Param("addresstime") Date addresstime);




}
