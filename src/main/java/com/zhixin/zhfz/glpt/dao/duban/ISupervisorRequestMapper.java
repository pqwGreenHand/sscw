package com.zhixin.zhfz.glpt.dao.duban;

import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.glpt.entity.SupervisorRequestEntity;
import com.zhixin.zhfz.glpt.form.SupervisorForm;

import java.util.List;
import java.util.Map;

public interface ISupervisorRequestMapper {


    List<SupervisorRequestEntity> list(Map<String,Object> params);

    Integer count(Map<String,Object> params);

    void insert(SupervisorRequestEntity entity) throws Exception;

    void status(SupervisorForm form) throws Exception;

    List<ComboboxEntity> caseCombobox(Map<String,Object> params);

    SupervisorRequestEntity get(SupervisorForm form);

}
