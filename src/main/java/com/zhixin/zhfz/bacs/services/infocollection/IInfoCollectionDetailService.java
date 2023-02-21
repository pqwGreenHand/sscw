package com.zhixin.zhfz.bacs.services.infocollection;

import com.zhixin.zhfz.bacs.entity.InfoCollectionDetailEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IInfoCollectionDetailService {


    List<InfoCollectionDetailEntity> selectByPrimaryKey(Map<String, Object> map);

    Long insertAllDeatils(Long id, Integer registerUserId , HttpServletRequest request);


    InfoCollectionDetailEntity selectByInfoCollectionId(Map<String, Object> map2);

    void updateDeatils(List<InfoCollectionDetailEntity> list, int collectUserId);


}
