package com.zhixin.zhfz.bacs.services.checkbody;

import com.zhixin.zhfz.bacs.dao.checkbody.ICheckBodyMapper;
import com.zhixin.zhfz.bacs.entity.CheckBodyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckBodyServiceImpl implements ICheckBodyService {

    @Autowired
    private ICheckBodyMapper checkBodyMapper;

    @Override
    public CheckBodyEntity getCheckBodyBySerialId(CheckBodyEntity checkBodyEntity) {
        List<CheckBodyEntity> list = checkBodyMapper.getCheckBodyBySerialId(checkBodyEntity);
        if (list != null && list.size() > 0) {
            return checkBodyMapper.getCheckBodyBySerialId(checkBodyEntity).get(0);
        }
        return null;
    }
}
