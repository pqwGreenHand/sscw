package com.zhixin.zhfz.sacw.dao.handfy;


import com.zhixin.zhfz.sacw.entity.InvolvedEntity;

import java.util.List;
import java.util.Map;

public interface IHandFyMapper {

    List<InvolvedEntity> list(Map<String, Object> map) throws Exception;

    int count(Map<String, Object> map) throws Exception;


}
