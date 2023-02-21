package com.zhixin.zhfz.bacs.dao.downloadtask;


import com.zhixin.zhfz.bacs.entity.DownloadTaskEntity;
import com.zhixin.zhfz.common.entity.AbstractTreeEntity;
import com.zhixin.zhfz.common.entity.CaseEntity;
import com.zhixin.zhfz.common.entity.SerialCaseTreeEntity;

import java.util.List;
import java.util.Map;


public interface IDownloadTaskMapper {
    

    void insert(DownloadTaskEntity entity) throws Exception;


    int queryRecordTaskCount(Map<String,Object> map);

    int queryTaskCount(Map<String, Object> map) throws Exception;

    List<AbstractTreeEntity> getPersonTreeByCaseId(Integer caseId) throws Exception;

    List<SerialCaseTreeEntity> getSerialCaseTree(Map<String, Object> map) throws Exception;

    List<DownloadTaskEntity> list(Map<String,Object> map) throws Exception;

    int count(Map<String,Object> map)  throws Exception;
    //重新下载
    void reDownLoad(int downLoadId) throws Exception;

}
