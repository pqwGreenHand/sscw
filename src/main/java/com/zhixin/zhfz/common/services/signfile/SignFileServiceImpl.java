package com.zhixin.zhfz.common.services.signfile;

import com.zhixin.zhfz.common.dao.signfile.SignFileMapper;
import com.zhixin.zhfz.common.entity.SignFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SignFileServiceImpl implements SignFileService {
    @Autowired
    private SignFileMapper serialFileMapper;
    @Override
    public void insertSerialFile(SignFileEntity serialFileEntity) throws Exception{
        serialFileMapper.insertSelective(serialFileEntity);
    }

    @Override
    public void deleteById(int serialFileId) throws Exception {
        serialFileMapper.deleteByPrimaryKey(serialFileId);
    }

    @Override
    public SignFileEntity getLastFileBySerialAndType(SignFileEntity record)  throws Exception {
        return serialFileMapper.getLastFileBySerialAndType(record);
    }

    @Override
    public List<SignFileEntity> getSignatureList(Map<String, Object> param) throws Exception  {
        return serialFileMapper.getSignatureList(param);
    }
}
