package com.zhixin.zhfz.bacs.services.openCabinet;

import com.zhixin.zhfz.bacs.dao.belong.IOpenCabinetMapper;
import com.zhixin.zhfz.bacs.entity.OpenCabinetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OpenCabinetServiceImpl implements IOpenCabinetService {
    @Autowired
    private IOpenCabinetMapper openCabinetMapper;

    /**
     * 插入开柜记录
     *
     * @param record
     */
    @Override
    public void insertSelective(OpenCabinetEntity record) {
        openCabinetMapper.insertSelective(record);
    }

    /**
     * 查询开柜记录
     *
     * @param params
     * @return
     */
    @Override
    public List<OpenCabinetEntity> list(Map<String, Object> params) {
        return openCabinetMapper.list(params);
    }

    /**
     * 查询开柜总数
     *
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return openCabinetMapper.count(params);
    }
    /**
     * 查询开柜记录
     *
     * @param params
     * @return
     */
    @Override
    public List<OpenCabinetEntity> listByPolice(Map<String, Object> params) {
        return openCabinetMapper.list(params);
    }

    /**
     * 查询开柜总数
     *
     * @param params
     * @return
     */
    @Override
    public int countByPolice(Map<String, Object> params) {
        return openCabinetMapper.count(params);
    }
    /**
     * 查询开柜记录
     *
     * @param params
     * @return
     */
    @Override
    public List<OpenCabinetEntity> listBySA(Map<String, Object> params) {
        return openCabinetMapper.list(params);
    }

    /**
     * 查询开柜总数
     *
     * @param params
     * @return
     */
    @Override
    public int countBySA(Map<String, Object> params) {
        return openCabinetMapper.count(params);
    }
}
