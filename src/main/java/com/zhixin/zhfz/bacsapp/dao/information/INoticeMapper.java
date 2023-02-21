package com.zhixin.zhfz.bacsapp.dao.information;

import com.zhixin.zhfz.bacsapp.entity.InformationEntity;

import java.util.List;
import java.util.Map;

public interface INoticeMapper {

    List<InformationEntity> list(Map<String,Object> params);

    int count(Map<String,Object> params);

    void deal(Map<String,Object> params) throws Exception;

    void insertInform(InformationEntity entity) throws Exception;
    
    
    int isInform(InformationEntity entity) throws Exception;

}
