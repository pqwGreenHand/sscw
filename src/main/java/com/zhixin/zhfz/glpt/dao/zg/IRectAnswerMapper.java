package com.zhixin.zhfz.glpt.dao.zg;

import com.zhixin.zhfz.glpt.entity.RectAnswerEntity;
import com.zhixin.zhfz.glpt.form.RectForm;

import java.util.List;
import java.util.Map;

public interface IRectAnswerMapper {

    List<RectAnswerEntity> list(Map<String, Object> params);

    Integer count(Map<String, Object> params);

    void insert(RectAnswerEntity entity) throws Exception;

    void status(RectForm form) throws Exception;

    RectAnswerEntity get(RectForm form);

}
