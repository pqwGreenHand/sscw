package com.zhixin.zhfz.bacs.services.infocollection;

import com.zhixin.zhfz.bacs.entity.InfoCollectionEntity;

import java.util.List;
import java.util.Map;

public interface IInfoCollectionService {


    int selectserialNo(Long serialNo);

    InfoCollectionEntity selectByInterrogateSerialId(Long serialId);

    void updateByPrimaryKeySelective(InfoCollectionEntity infoCollectionEntity);

    List<InfoCollectionEntity> getAllInfocollection(Map<String, Object> map);

    int count(Map<String, Object> map);

    InfoCollectionEntity getInfoCollectionBySerialId(InfoCollectionEntity temInfoCollection);
}
