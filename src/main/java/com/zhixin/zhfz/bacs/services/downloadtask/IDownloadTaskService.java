package com.zhixin.zhfz.bacs.services.downloadtask;

import com.zhixin.zhfz.bacs.entity.DownloadTaskEntity;
import com.zhixin.zhfz.common.entity.AbstractTreeEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description:
 * @author: jzw
 * @create: 2019-03-05 16:55
 **/

public interface IDownloadTaskService {

    void insert(DownloadTaskEntity entity) throws Exception;

    int queryRecordTaskCount(Map<String,Object> map);

    public int queryTaskCount(Map<String, Object> map) throws Exception;

    List<AbstractTreeEntity> getCaseAndPersonTree(Map<String,Object> map);

    List<DownloadTaskEntity> list(Map<String,Object> map) throws Exception;

    int count(Map<String,Object> map)  throws Exception;

    void reDownLoad(int downLoadId) throws Exception;
}
