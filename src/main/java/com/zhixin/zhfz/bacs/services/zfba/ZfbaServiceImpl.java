package com.zhixin.zhfz.bacs.services.zfba;

import com.zhixin.zhfz.bacs.zfbaDao.IZfbaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ZfbaServiceImpl implements IZfbaService {

    @Resource
    private IZfbaMapper zfbaMapper;

    @Override
    public List<Map<String, Object>> queryAjxx(Map<String, Object> param) {
        return zfbaMapper.queryAjxx(param);
    }

    @Override
    public Integer queryAjxxCount(Map<String, Object> param) {
        return zfbaMapper.queryAjxxCount(param);
    }

    @Override
    public List<Map<String, Object>> queryPerson(Map<String, Object> param) {
        return zfbaMapper.queryPerson(param);
    }

    @Override
    public List<Map<String, Object>> queryBelong(Map<String, Object> param) {
        return zfbaMapper.queryBelong(param);
    }

    @Override
    public Map<String, Object> queryAjxxById(String id) {
        return zfbaMapper.queryAjxxById(id);
    }

    @Override
    public Map<String, Object> queryYyxxById(String id) {
        return zfbaMapper.queryYyxxById(id);
    }
}
