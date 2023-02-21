package com.zhixin.zhfz.bacs.dao.checkbody;

import com.zhixin.zhfz.bacs.entity.CheckBodyEntity;

import java.util.List;

public interface ICheckBodyMapper {

    List<CheckBodyEntity> getCheckBodyBySerialId(CheckBodyEntity checkBodyEntity);

    CheckBodyEntity queryDataByid(int id);
}
