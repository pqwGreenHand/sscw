package com.zhixin.zhfz.bacs.services.downloadtask;

import com.zhixin.zhfz.bacs.dao.downloadtask.IDownloadTaskMapper;
import com.zhixin.zhfz.bacs.entity.DownloadTaskEntity;
import com.zhixin.zhfz.common.dao.Icase.ICaseMapper;
import com.zhixin.zhfz.common.entity.AbstractTreeEntity;
import com.zhixin.zhfz.common.entity.CaseEntity;
import com.zhixin.zhfz.common.entity.SerialCaseTreeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description:
 * @author: jzw
 * @create: 2019-03-05 16:57
 **/
@Service
public class DownloadTaskServiceImpl implements IDownloadTaskService {

    @Autowired
    private IDownloadTaskMapper mapper;
    @Autowired
    private ICaseMapper caseMapper;

    @Override
    public void insert(DownloadTaskEntity entity) throws Exception {
        mapper.insert(entity);
    }
    //获取下载任务列表
    @Override
    public List<DownloadTaskEntity> list(Map<String,Object> map) throws Exception {
        return mapper.list(map);
    }
    //获取下载任务数量
    @Override
    public int count(Map<String,Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public void reDownLoad(int downLoadId) throws Exception {
        mapper.reDownLoad(downLoadId);
    }

    @Override
    public int queryRecordTaskCount(Map<String,Object> map) {
        return mapper.queryRecordTaskCount(map);
    }

    @Override
    public int queryTaskCount(Map<String, Object> map) throws Exception {
        return mapper.queryTaskCount(map);
    }

    @Override
    public List<AbstractTreeEntity> getCaseAndPersonTree(Map<String, Object> map) {
        try{
            List<SerialCaseTreeEntity> caseList = mapper.getSerialCaseTree(map);
            System.err.println("-----caseList:"+caseList.size());
            List<AbstractTreeEntity> caseAndPersonTree = toCaseAndPersonTree(caseList);
            System.err.println("-----caseAndPersonTree:"+caseAndPersonTree.size());
            return caseAndPersonTree;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private  List<AbstractTreeEntity> toCaseAndPersonTree(List<SerialCaseTreeEntity> serialCaseList) throws Exception{
        List<AbstractTreeEntity> caseAndPersonTree = new ArrayList<AbstractTreeEntity>();
        if(serialCaseList == null || serialCaseList.size() == 0){
            AbstractTreeEntity defaultEntity = new AbstractTreeEntity();
            defaultEntity.setId(0L);
            defaultEntity.setText("暂无数据");
            defaultEntity.setType(0);
            defaultEntity.setState("close");
            List<AbstractTreeEntity> cliderList = new ArrayList<AbstractTreeEntity>();
            AbstractTreeEntity defaultClidernEntity = new AbstractTreeEntity();
            defaultEntity.setId(0L);
            defaultEntity.setText("暂无数据");
            defaultEntity.setType(1);
            cliderList.add(defaultClidernEntity);
            defaultEntity.setChildren(cliderList);
            caseAndPersonTree.add(defaultEntity);
        } else{
            Map<Integer,Integer> caseIdMap = new HashMap<Integer,Integer>();
            for(SerialCaseTreeEntity serialCaseTreeEntity : serialCaseList){
                if(caseIdMap.get(serialCaseTreeEntity.getCaseId()) == null
                        || caseIdMap.get(serialCaseTreeEntity.getCaseId()) == 0) {
                    caseIdMap.put(serialCaseTreeEntity.getCaseId(), serialCaseTreeEntity.getCaseId());
                    AbstractTreeEntity fatharEntity = new AbstractTreeEntity();
                    fatharEntity.setId(Long.parseLong(serialCaseTreeEntity.getCaseId() + ""));
                    fatharEntity.setText(serialCaseTreeEntity.getCaseName());
                    fatharEntity.setType(0);
                    fatharEntity.setState("close");
                    List<AbstractTreeEntity> clidernList = new ArrayList<AbstractTreeEntity>();
                    for (SerialCaseTreeEntity clidernTreEntity : serialCaseList) {
                        if(clidernTreEntity.getCaseId() == serialCaseTreeEntity.getCaseId()){
                            AbstractTreeEntity clidernEntity = new AbstractTreeEntity();
                            clidernEntity.setId(clidernTreEntity.getSerialId());
                            clidernEntity.setText(clidernTreEntity.getPersonName());
                            clidernEntity.setType(1);
                            clidernList.add(clidernEntity);
                        }
                    }
                    if(clidernList.size() == 0){
                        AbstractTreeEntity defaultClidernEntity = new AbstractTreeEntity();
                        defaultClidernEntity.setId(0L);
                        defaultClidernEntity.setText("暂无数据");
                        defaultClidernEntity.setType(1);
                        clidernList.add(defaultClidernEntity);
                    }
                    fatharEntity.setChildren(clidernList);
                    caseAndPersonTree.add(fatharEntity);
                }else{
                    continue;
                }
            }

        }
        return caseAndPersonTree;
    }
}
