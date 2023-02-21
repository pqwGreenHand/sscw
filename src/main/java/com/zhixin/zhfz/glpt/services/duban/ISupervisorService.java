package com.zhixin.zhfz.glpt.services.duban;

import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.glpt.entity.SupervisorAnswerEntity;
import com.zhixin.zhfz.glpt.entity.SupervisorRequestEntity;
import com.zhixin.zhfz.glpt.form.SupervisorForm;
import com.zhixin.zhfz.glpt.vo.PageResponse;

import java.util.List;
import java.util.Map;

public interface ISupervisorService {

    PageResponse<SupervisorRequestEntity> listRequest(Map<String,Object> params);

    void status(SupervisorForm form) throws Exception;

    void insertRequest(SupervisorRequestEntity entity) throws Exception;

    PageResponse<SupervisorAnswerEntity> listAnswer(Map<String,Object> params);

    void insertAnswer(SupervisorAnswerEntity entity) throws Exception;

    List<ComboboxEntity> caseCombobox(Map<String, Object> params);
}
