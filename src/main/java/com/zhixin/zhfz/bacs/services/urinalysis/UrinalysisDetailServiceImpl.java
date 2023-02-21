package com.zhixin.zhfz.bacs.services.urinalysis;

import com.zhixin.zhfz.bacs.dao.urinalysis.UrinalysisDetailMapper;
import com.zhixin.zhfz.bacs.entity.UrinalysisDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrinalysisDetailServiceImpl implements UrinalysisDetailService{
    @Autowired
    private UrinalysisDetailMapper urinalysisDetailMapper;

    @Override
    public void insert(UrinalysisDetailEntity urinalysisDetailEntity)  throws Exception {
        this.urinalysisDetailMapper.insert(urinalysisDetailEntity);
    }

    @Override
    public void insertList(List<UrinalysisDetailEntity> urinalysisDetailList)  throws Exception {
        if(urinalysisDetailList != null && urinalysisDetailList.size() >0){
            this.urinalysisDetailMapper.deleteByUrinalysisId(urinalysisDetailList.get(0).getUrinalysisId());
            this.urinalysisDetailMapper.insertList(urinalysisDetailList);
        }
    }
    @Override
    public void deleteByUrinalysisId(long urinalysisId) throws Exception {
        this.urinalysisDetailMapper.deleteByUrinalysisId(urinalysisId);
    }
}
