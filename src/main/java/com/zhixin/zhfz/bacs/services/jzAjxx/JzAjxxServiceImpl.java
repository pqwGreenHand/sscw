package com.zhixin.zhfz.bacs.services.jzAjxx;

import com.zhixin.zhfz.bacs.dao.jzAjxx.IJzAjxxMapper;
import com.zhixin.zhfz.bacs.entity.JzAjxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JzAjxxServiceImpl implements IJzAjxxService {

    @Autowired
    private IJzAjxxMapper jzAjxxMapper;

    @Override
    public List<JzAjxxEntity> list(Map<String, Object> map) {
        return jzAjxxMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return jzAjxxMapper.count(map);
    }

    @Override
    public List<Map<String, Object>> listRyByAjbh(Map<String, Object> map) {
        return jzAjxxMapper.listRyByAjbh(map);
    }

    @Override
    public int countListRyByAjbh(Map<String, Object> map) {
        return jzAjxxMapper.countListRyByAjbh(map);
    }

	@Override
	public List<Map<String, Object>> queryRybhByZjhm(Map<String, Object> map) {
		return jzAjxxMapper.queryRybhByZjhm(map);
	}
}
