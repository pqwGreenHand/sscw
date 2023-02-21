package com.zhixin.zhfz.zhag.services.xsajgj;

import com.zhixin.zhfz.zhag.dao.xsajgj.IXsajgjMapper;
import com.zhixin.zhfz.zhag.dao.xsajgj.IXzajgjMapper;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
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

public class XsajgjServiceImpl implements IXsajgjService{

    @Autowired
    private IXsajgjMapper xsajgjMapper;
    @Autowired
    private IXzajgjMapper xzajgjMapper;

    @Override
    public List<GjxxEntity> listXsangj(Map<String, Object> param) {

        return this.xsajgjMapper.list(param);
    }

    @Override
    public int count(Map<String, Object> map) {
        return this.xsajgjMapper.count(map);
    }

    @Override
    public List<GjxxEntity> listXzangj(Map<String, Object> map) {
        return xzajgjMapper.list(map);
    }

    @Override
    public int xzanCount(Map<String, Object> map) {
        return xzajgjMapper.count(map);
    }
}
