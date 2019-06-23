package com.pjb.springbootjjwt.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.entity.Download;
import com.pjb.springbootjjwt.entity.DownloadAndSc;
import com.pjb.springbootjjwt.entity.Teacher;
import com.pjb.springbootjjwt.mapper.DownloadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:52
 */
@Service("DownloadService")
public class DownloadService {
    @Autowired
    DownloadMapper downloadMapper;


    public Map<String, Object> findAllDownload(int pageNum, int pageSize) throws Exception{

        Page page=PageHelper.startPage(pageNum, pageSize,true);
        List<Download> downloadList=downloadMapper.findAllDownload();
        Map<String,Object> map=new HashMap<>();
        map.put("count", page.getTotal());
        map.put("nowPage", pageNum);
        map.put("data", downloadList);
        map.put("code" ,0);
        return map;

    }

    public void addDownload(Download download){

        downloadMapper.addDownload(download);
    }
    public void updateDownload(Download download){

        downloadMapper.updateDownload(download);
    }
    public List<Download> findOneDownload(Download download){return downloadMapper.findOneDownload(download);};
    public List<DownloadAndSc> showHomeworkList(int courseid){return downloadMapper.showHomeworkList(courseid);};



}
