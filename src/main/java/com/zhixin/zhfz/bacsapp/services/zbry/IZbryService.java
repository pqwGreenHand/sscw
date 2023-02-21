package com.zhixin.zhfz.bacsapp.services.zbry;

import com.zhixin.zhfz.bacsapp.entity.ZbryMenuEntity;
import com.zhixin.zhfz.bacsapp.vo.PageResponse;

import java.util.Map;

public interface IZbryService {

    PageResponse personPage(Map<String,Object> map);

    ZbryMenuEntity menu(Map<String,Object> params);
}
