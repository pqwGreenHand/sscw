package com.zhixin.zhfz.bacs.dao.lawdoc;

import com.zhixin.zhfz.bacs.entity.DocVideoInstructionEntity;

public interface IDocVideoInstructionMapper {


    DocVideoInstructionEntity getVideoInstructionBySerialId(Long serialId);
}
