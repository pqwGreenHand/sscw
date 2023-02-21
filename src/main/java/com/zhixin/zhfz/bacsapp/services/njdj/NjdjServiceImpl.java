package com.zhixin.zhfz.bacsapp.services.njdj;

import com.zhixin.zhfz.bacsapp.dao.entrance.IEntranceMapper;
import com.zhixin.zhfz.bacsapp.dao.njdj.INjdjMapper;
import com.zhixin.zhfz.bacsapp.entity.EntranceEntity;
import com.zhixin.zhfz.bacsapp.entity.NjdjAppEntity;
import com.zhixin.zhfz.bacsapp.services.entrance.IEntranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NjdjServiceImpl implements INjdjService {

    @Autowired
    private INjdjMapper njdjMapper;


    @Override
    public List<NjdjAppEntity> list(Map<String, Object> map) {
        return njdjMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return njdjMapper.count(map);
    }

    @Override
    public List<NjdjAppEntity> personList(Map<String, Object> map) {
        return njdjMapper.personList(map);
    }

    @Override
    public List<NjdjAppEntity> personListUpdate(HashMap<String, Object> map) {
        return njdjMapper.personListUpdate(map);
    }


}
