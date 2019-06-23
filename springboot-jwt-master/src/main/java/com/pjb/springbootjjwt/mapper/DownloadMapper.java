package com.pjb.springbootjjwt.mapper;

import com.pjb.springbootjjwt.entity.Download;
import com.pjb.springbootjjwt.entity.DownloadAndSc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface DownloadMapper {
    void addDownload(Download download);
    List<Download> findOneDownload(Download download);
    void updateDownload(Download download);
    List<DownloadAndSc> showHomeworkList(int courseid);
     List<Download> findAllDownload();

}
