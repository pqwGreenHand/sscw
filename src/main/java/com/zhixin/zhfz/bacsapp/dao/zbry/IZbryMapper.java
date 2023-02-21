package com.zhixin.zhfz.bacsapp.dao.zbry;

import com.zhixin.zhfz.bacsapp.entity.ZbryMenuEntity;
import com.zhixin.zhfz.bacsapp.entity.ZbryPersonEntity;

import java.util.List;
import java.util.Map;

public interface IZbryMapper {

    List<ZbryPersonEntity> personList(Map<String,Object> params);

    Integer personCount(Map<String,Object> params);

    ZbryMenuEntity menu(Map<String,Object> params);

}
