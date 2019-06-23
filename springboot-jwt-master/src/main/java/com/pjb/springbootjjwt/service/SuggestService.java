package com.pjb.springbootjjwt.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.entity.suggest;
import com.pjb.springbootjjwt.mapper.SuggestMapper;
import com.pjb.springbootjjwt.mapper.SuggestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:52
 */
@Service("SuggestService")
public class SuggestService {
    @Autowired
   SuggestMapper suggestMapper;

    public boolean sentSuggest(suggest suggest){return suggestMapper.sentSuggest(suggest);}
    public List<suggest> looklook(){return suggestMapper.looklook();}



}
