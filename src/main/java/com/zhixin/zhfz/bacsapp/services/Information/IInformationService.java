package com.zhixin.zhfz.bacsapp.services.Information;

import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.entity.ZbryMenuEntity;
import com.zhixin.zhfz.bacsapp.vo.PageResponse;

import java.util.Map;

public interface IInformationService {

    PageResponse noticePage(Map<String , Object> params);

    int noticeCount(Map<String , Object> params);

    PageResponse todoPage(Map<String , Object> params);

    int todoCount(Map<String , Object> params);

    void deal(Map<String,Object> params) throws Exception;

    void insertInform(InformationEntity entity) throws Exception;
    
    
    int isInform(InformationEntity entity) throws Exception;

}
