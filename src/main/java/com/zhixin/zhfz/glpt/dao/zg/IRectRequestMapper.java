package com.zhixin.zhfz.glpt.dao.zg;

import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.glpt.entity.RectRequestEntity;
import com.zhixin.zhfz.glpt.form.RectForm;

import java.util.List;
import java.util.Map;

public interface IRectRequestMapper {


    List<RectRequestEntity> list(Map<String, Object> params);

    Integer count(Map<String, Object> params);

    void insert(RectRequestEntity entity) throws Exception;

    void status(RectForm form) throws Exception;

}
