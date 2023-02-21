package com.zhixin.zhfz.sacw.services.outrecordphoto;


import com.zhixin.zhfz.sacw.dao.outrecordphoto.IOutRecordPhotoMapper;
import com.zhixin.zhfz.sacw.entity.OutRecordPhotoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IOutRecordPhotoServiceImpl implements IOutRecordPhotoService {

    @Autowired
    private IOutRecordPhotoMapper outRecordPhotoMapper;

    @Override
    public List<OutRecordPhotoEntity> listOutRecordPhoto(Map<String, Object> map) throws Exception {

        return outRecordPhotoMapper.listOutRecordPhoto(map);
    }

    @Override
    public void insertOutRecordPhoto(OutRecordPhotoEntity outRecordPhotoEntity) throws Exception {
        outRecordPhotoMapper.insertOutRecordPhoto(outRecordPhotoEntity);
    }

    @Override
    public void deleteOutRecord(int id) throws Exception {
        outRecordPhotoMapper.deleteOutRecordPhoto(id);
    }

    @Override
    public void updateOutRecordPhoto(OutRecordPhotoEntity outRecordPhotoEntity) throws Exception {
        outRecordPhotoMapper.updateOutRecordPhoto(outRecordPhotoEntity);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {

        return outRecordPhotoMapper.count(map);
    }

    @Override
    public List<OutRecordPhotoEntity> listOutRecordPhotoById(Map<String, Object> map) throws Exception {

        return outRecordPhotoMapper.listOutRecordPhotoById(map);
    }

}
