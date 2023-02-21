package com.zhixin.zhfz.glpt.dao.policePatrol;

import com.zhixin.zhfz.bacs.dao.exhibit.IExhibitdetMapper;
import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.jxkp.entity.EvaluationEntity;
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;

import java.util.List;
import java.util.Map;

public interface PolicePatrolMapper {

    //嫌疑人相关
    List<SerialEntity> listPerson(Map<String, Object> param) throws Exception;
    int listPersonCount(Map<String, Object> param)  throws Exception;
    //涉案物品相关
    List<ExhibitEntity> listExhibit(Map<String, Object> param) throws Exception;
    int listExhibitCount(Map<String, Object> param)  throws Exception;
    //卷宗相关
    public List<JzxxEntity> getJzxxAndAjxx(Map<String, Object> map) throws Exception;
    public Integer countJzxxAndAjxx(Map<String, Object> map) throws Exception;
    //考评相关
    List<EvaluationEntity> selectAllEvaluation(Map map);
    Integer  selectAllEvaluationCount(Map map);
    String selectPoliceNoById(String userId);
    String selectRealNameById(String userId);
}
