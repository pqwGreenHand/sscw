package com.zhixin.zhfz.bacs.dao.infocollection;

import com.zhixin.zhfz.bacs.entity.InfoCollectionDetailEntity;

import java.util.List;
import java.util.Map;

public interface IInfoCollectionDetailMapper {

    List<InfoCollectionDetailEntity> selectByPrimaryKey(Map<String, Object> param);

    void insertAllDeatils(List<InfoCollectionDetailEntity> listInfo);

    void updateDeatils(InfoCollectionDetailEntity infoCollectionDetailEntity);

    List<InfoCollectionDetailEntity> selectByInfoCollectionId(Map<String, Object> map);
}
