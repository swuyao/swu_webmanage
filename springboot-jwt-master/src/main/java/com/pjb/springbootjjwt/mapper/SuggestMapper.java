package com.pjb.springbootjjwt.mapper;


import com.pjb.springbootjjwt.entity.suggest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface SuggestMapper {

    boolean sentSuggest( suggest suggest);
    List<suggest> looklook();

}
