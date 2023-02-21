package com.zhixin.zhfz.bacs.dao.infocollection;

import com.zhixin.zhfz.bacs.entity.InfoCollectionEntity;

import java.util.List;
import java.util.Map;

public interface IInfocollectionMapper {
    
    List<InfoCollectionEntity> getAllInfocollection(Map<String, Object> map);

    int count(Map<String, Object> map);

    int getSerialByNo(Long serialNo);

    InfoCollectionEntity selectByPrimaryKey(Long id);

    InfoCollectionEntity selectByInterrogateSerialId(Map<String, Object> map);

    void insert(InfoCollectionEntity collection);

    void updateByPrimaryKeySelective(InfoCollectionEntity infoCollectionEntity);

    List<InfoCollectionEntity> getInfoCollectionBySerialId(InfoCollectionEntity temInfoCollection);
}
