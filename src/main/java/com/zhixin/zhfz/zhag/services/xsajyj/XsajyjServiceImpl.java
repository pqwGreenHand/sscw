package com.zhixin.zhfz.zhag.services.xsajyj;

import com.zhixin.zhfz.zhag.dao.xsajgj.IXsajgjMapper;
import com.zhixin.zhfz.zhag.dao.xsajgj.IXzajgjMapper;
import com.zhixin.zhfz.zhag.dao.xsajyj.IXsajyjMapper;
import com.zhixin.zhfz.zhag.dao.xsajyj.IXzajyjMapper;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import com.zhixin.zhfz.zhag.services.xsajgj.IXsajgjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @prgram: zhfz
 * @Description: 刑事案件告警服务类
 * @Auther: xcf
 * @Date: 2019-05-22 09:35
 */

@Service

public class XsajyjServiceImpl implements IXsajyjService{

    @Autowired
    private IXsajyjMapper xsajyjMapper;
    @Autowired
    private IXzajyjMapper xzajyjMapper;

    @Override
    public List<GjxxEntity> listXsanyj(Map<String, Object> param) {

        return this.xsajyjMapper.list(param);
    }

    @Override
    public int count(Map<String, Object> map) {
        return this.xsajyjMapper.count(map);
    }

    @Override
    public List<GjxxEntity> listXzanyj(Map<String, Object> map) {
        return xzajyjMapper.list(map);
    }

    @Override
    public int xzanCount(Map<String, Object> map) {
        return xzajyjMapper.count(map);
    }

    @Override
    public List<GjxxEntity> hisList(Map<String, Object> map) {
        return xzajyjMapper.hisList(map);
    }

    @Override
    public int hisCount(Map<String, Object> map) {
        return xzajyjMapper.hisCount(map);
    }

    @Override
    public List<GjxxEntity> hisListXsanyj(Map<String, Object> map) {
        return xsajyjMapper.hisListXsanyj(map);
    }

    @Override
    public int hisCountXsaj(Map<String, Object> map) {
        return xsajyjMapper.hisCountXsaj(map);
    }
}
