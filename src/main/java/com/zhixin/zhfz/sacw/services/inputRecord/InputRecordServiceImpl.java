package com.zhixin.zhfz.sacw.services.inputRecord;


import com.zhixin.zhfz.sacw.dao.inputRecord.IInputRecordMapper;
import com.zhixin.zhfz.sacw.dao.inrecordphoto.IInRecordPhotoMapper;
import com.zhixin.zhfz.sacw.entity.InRecordPhotoEntity;
import com.zhixin.zhfz.sacw.entity.InputRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InputRecordServiceImpl implements IInputRecordService {

    @Autowired
    IInputRecordMapper mapper;

    @Autowired
    IInRecordPhotoMapper photomapper;

    @Override
    public List<InputRecordEntity> listInputRecord(Map<String, Object> map) throws Exception {
        return mapper.listInputRecord(map);
    }

    @Override
    public Integer countInputRecord(Map<String, Object> map) throws Exception {
        return mapper.countInputRecord(map);
    }

    @Override
    public InputRecordEntity getInputRecordById(Integer id) throws Exception {
        return mapper.getInputRecordById(id);
    }

    @Override
    public List<InRecordPhotoEntity> listInRecordPhotoById(Map<String, Object> map) throws Exception {

        return photomapper.listInRecordPhotoById(map);
    }

    @Override
    public List<InputRecordEntity> listLastRecord(Map<String, Object> map) throws Exception {
        return mapper.listLastRecord(map);
    }

}
