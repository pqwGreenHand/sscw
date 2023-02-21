package com.zhixin.zhfz.glpt.dao.duban;

import com.zhixin.zhfz.glpt.entity.SupervisorAnswerEntity;
import com.zhixin.zhfz.glpt.form.SupervisorForm;

import java.util.List;
import java.util.Map;

public interface ISupervisorAnswerMapper {

    List<SupervisorAnswerEntity> list(Map<String,Object> params);

    Integer count(Map<String,Object> params);

    void insert(SupervisorAnswerEntity entity) throws Exception;

    void status(SupervisorForm form) throws Exception;

    SupervisorAnswerEntity get(SupervisorForm form);

}
