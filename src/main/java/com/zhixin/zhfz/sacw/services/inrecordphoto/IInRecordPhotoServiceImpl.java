package com.zhixin.zhfz.sacw.services.inrecordphoto;


import com.zhixin.zhfz.sacw.dao.inrecordphoto.IInRecordPhotoMapper;
import com.zhixin.zhfz.sacw.entity.InRecordPhotoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IInRecordPhotoServiceImpl implements IInRecordPhotoService {

    @Autowired
    private IInRecordPhotoMapper inRecordPhotoMapper;

    @Override
    public List<InRecordPhotoEntity> listInRecordPhoto(Map<String, Object> map) throws Exception {
        return inRecordPhotoMapper.listInRecordPhoto(map);
    }

    @Override
    public void insertinRecordPhoto(InRecordPhotoEntity inRecordPhotoEntity) throws Exception {
        inRecordPhotoMapper.insertInRecordPhoto(inRecordPhotoEntity);
    }

    @Override
    public void deleteInRecord(int id) throws Exception {
        inRecordPhotoMapper.deleteInRecordPhoto(id);
    }

    @Override
    public void updateInRecordPhoto(InRecordPhotoEntity inRecordPhotoEntity) throws Exception {
        inRecordPhotoMapper.updateInRecordPhoto(inRecordPhotoEntity);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return inRecordPhotoMapper.count(map);
    }


}
