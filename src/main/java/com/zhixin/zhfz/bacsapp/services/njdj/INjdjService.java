package com.zhixin.zhfz.bacsapp.services.njdj;

import com.zhixin.zhfz.bacsapp.entity.NjdjAppEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface INjdjService {

    List<NjdjAppEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<NjdjAppEntity> personList(Map<String, Object> map);

    List<NjdjAppEntity> personListUpdate(HashMap<String, Object> map);
}
