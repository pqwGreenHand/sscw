package com.zhixin.zhfz.bacs.services.jzPerson;

import com.zhixin.zhfz.bacs.dao.jzPerson.IRyCljdMapper;
import com.zhixin.zhfz.bacs.entity.RycljdMysqlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JzRyxxServiceImpl implements IJzRyxxService {

    @Autowired
    private IRyCljdMapper ryCljdMapper;


    @Override
    public List<RycljdMysqlEntity> queryCljdPzsjMaxXs() {
        return ryCljdMapper.queryCljdPzsjMaxXs();
    }

    @Override
    public void insertRYCLJD(RycljdMysqlEntity entity) {
        ryCljdMapper.insertRYCLJD(entity);
    }

    @Override
    public List<Map<String, Object>> queryCljdMap() {
        return ryCljdMapper.queryCljdMap();
    }

    @Override
    public List<Map<String, Object>> queryAjztMap() {
        return ryCljdMapper.queryAjztMap();
    }

    @Override
    public List<Map<String, Object>> queryOrgMap() {
        return ryCljdMapper.queryOrgMap();
    }

    @Override
    public List<Map<String, Object>> queryUserMap() {
        return ryCljdMapper.queryUserMap();
    }

}
