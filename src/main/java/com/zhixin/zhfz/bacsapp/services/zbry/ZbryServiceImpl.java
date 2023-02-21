package com.zhixin.zhfz.bacsapp.services.zbry;

import com.zhixin.zhfz.bacsapp.dao.zbry.IZbryMapper;
import com.zhixin.zhfz.bacsapp.entity.ZbryMenuEntity;
import com.zhixin.zhfz.bacsapp.entity.ZbryPersonEntity;
import com.zhixin.zhfz.bacsapp.vo.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ZbryServiceImpl implements IZbryService{

    @Autowired
    private IZbryMapper mapper;

    @Override
    public PageResponse personPage(Map<String, Object> map) {
        List<ZbryPersonEntity> list = mapper.personList(map);
        int count = mapper.personCount(map);
        return PageResponse.of(list,count);
    }

    @Override
    public ZbryMenuEntity menu(Map<String, Object> params) {
        return mapper.menu(params);
    }
}
