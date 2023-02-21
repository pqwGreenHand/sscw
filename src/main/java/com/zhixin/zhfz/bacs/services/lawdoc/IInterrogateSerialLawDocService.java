package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.entity.DocInNotificationEntity;

import java.util.List;

public interface IInterrogateSerialLawDocService {

    List<DocInNotificationEntity> queryInNotificationById(Long serialId);
}
