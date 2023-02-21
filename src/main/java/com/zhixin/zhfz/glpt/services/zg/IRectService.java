package com.zhixin.zhfz.glpt.services.zg;

import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.glpt.entity.RectAnswerEntity;
import com.zhixin.zhfz.glpt.entity.RectRequestEntity;
import com.zhixin.zhfz.glpt.form.RectForm;
import com.zhixin.zhfz.glpt.vo.PageResponse;

import java.util.List;
import java.util.Map;

public interface IRectService {

    PageResponse<RectRequestEntity> listRequest(Map<String, Object> params);

    void status(RectForm form) throws Exception;

    void insertRequest(RectRequestEntity entity) throws Exception;

    PageResponse<RectAnswerEntity> listAnswer(Map<String, Object> params);

    void insertAnswer(RectAnswerEntity entity) throws Exception;

}
