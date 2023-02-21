package com.zhixin.zhfz.common.services.signfile;

import com.zhixin.zhfz.common.entity.SignFileEntity;

import java.util.List;
import java.util.Map;

public interface SignFileService {

    void insertSerialFile(SignFileEntity serialFileEntity)throws Exception;

    void deleteById(int serialFileId)throws Exception;

    SignFileEntity getLastFileBySerialAndType(SignFileEntity record)throws Exception;

    List<SignFileEntity> getSignatureList(Map<String,Object> param) throws Exception;
}
