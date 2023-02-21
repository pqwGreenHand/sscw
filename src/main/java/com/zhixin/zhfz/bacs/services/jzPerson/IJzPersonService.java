package com.zhixin.zhfz.bacs.services.jzPerson;


import com.zhixin.zhfz.bacs.entity.JzPersonEntity;

import java.util.List;
import java.util.Map;

public interface IJzPersonService {

    public List<JzPersonEntity> list(Map<String, Object> map);

    public int count(Map<String, Object> map);
}
