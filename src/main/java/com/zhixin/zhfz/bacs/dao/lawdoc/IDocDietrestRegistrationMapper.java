package com.zhixin.zhfz.bacs.dao.lawdoc;

import com.zhixin.zhfz.bacs.entity.DocDietrestRegistrationEntity;

import java.util.List;

public interface IDocDietrestRegistrationMapper {

    List<DocDietrestRegistrationEntity> queryDietRestInfoBySeriaNo(Long serialId);
}
