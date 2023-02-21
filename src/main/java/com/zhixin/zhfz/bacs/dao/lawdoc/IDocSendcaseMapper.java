package com.zhixin.zhfz.bacs.dao.lawdoc;

import com.zhixin.zhfz.bacs.entity.DocSendcaseEntity;

public interface IDocSendcaseMapper {

    DocSendcaseEntity selectBySerialId(Long serialId);
}
