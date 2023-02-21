package com.zhixin.zhfz.sacw.services.nvr;

import com.zhixin.zhfz.sacw.dao.sanvr.ISaNvrMapper;
import com.zhixin.zhfz.sacw.entity.SaNvrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SaNvrServiceImpl implements ISaNvrService {

    @Autowired
    ISaNvrMapper mapper;

    @Override
    public List<SaNvrEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public void delete(int id) throws Exception {
        mapper.delete(id);
    }

    @Override
    public void update(SaNvrEntity entity) throws Exception {
        mapper.update(entity);
    }

    @Override
    public void insert(SaNvrEntity entity) throws Exception {
        mapper.insert(entity);
    }

    @Override
    public void insertList(List<SaNvrEntity> nvrList) throws Exception {
        mapper.insertList(nvrList);
    }

    @Override
    public void deleteCameraByNvrId(int id) throws Exception {
        mapper.deleteCameraByNvrId(id);
    }

    @Override
    public List<SaNvrEntity> comboNvr(Map<String, Object> map) throws Exception {
        return mapper.comboNvr(map);
    }


}
