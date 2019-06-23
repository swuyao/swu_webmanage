package com.pjb.springbootjjwt.mapper;

import com.pjb.springbootjjwt.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface NoticeMapper {
    boolean addNotice(Notice notice);
    List<Notice> findAllTitle();
    Notice findByTitle(String title);
    Notice findNewestNotice();
    List<Notice> showNoticeByRole(String role);
    boolean deleteNotice(String title);

}
