package com.zhixin.zhfz.glpt.services.policePatrol;

import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.glpt.dao.policePatrol.PolicePatrolMapper;
import com.zhixin.zhfz.jxkp.entity.EvaluationEntity;
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class PolicePatrolServiceImpl implements PolicePatrolService {
    @Autowired
    private PolicePatrolMapper policePatrolMapper;
    @Override
    public List<SerialEntity> listPerson(Map<String, Object> param)  throws Exception{
        return this.policePatrolMapper.listPerson(param);
    }
    @Override
    public int listPersonCount(Map<String, Object> param)  throws Exception{
        return this.policePatrolMapper.listPersonCount(param);
    }

    @Override
    public List<ExhibitEntity> listExhibit(Map<String, Object> param) throws Exception {
        return this.policePatrolMapper.listExhibit(param);
    }

    @Override
    public int listExhibitCount(Map<String, Object> param) throws Exception {
        return this.policePatrolMapper.listExhibitCount(param);
    }

    @Override
    public List<JzxxEntity> getJzxxAndAjxx(Map<String, Object> map) throws Exception {
        return this.policePatrolMapper.getJzxxAndAjxx(map);
    }

    @Override
    public Integer countJzxxAndAjxx(Map<String, Object> map) throws Exception {
        return this.policePatrolMapper.countJzxxAndAjxx(map);
    }
    @Override
    public List<EvaluationEntity> selectAllEvaluation(Map map) {
        return this.policePatrolMapper.selectAllEvaluation(map);
    }
    @Override
    public String selectPoliceNoById(String userId) {
        return this.policePatrolMapper.selectPoliceNoById(userId);
    }
    @Override
    public String selectRealNameById(String userId) {
        return this.policePatrolMapper.selectRealNameById(userId);
    }
    @Override
    public   Integer  selectAllEvaluationCount(Map map) {
        return this.policePatrolMapper.selectAllEvaluationCount(map);
    }
}
