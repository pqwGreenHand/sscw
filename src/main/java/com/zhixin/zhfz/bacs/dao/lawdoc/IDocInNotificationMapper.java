package com.zhixin.zhfz.bacs.dao.lawdoc;

import com.zhixin.zhfz.bacs.entity.DocInNotificationEntity;

import java.util.List;

public interface IDocInNotificationMapper {

    List<DocInNotificationEntity> queryInNotificationById(Long serialId);
}
