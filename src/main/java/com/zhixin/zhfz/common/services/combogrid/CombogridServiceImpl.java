package com.zhixin.zhfz.common.services.combogrid;

import com.zhixin.zhfz.common.dao.combogrid.ICombogridMapper;
import com.zhixin.zhfz.common.entity.CombogridEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CombogridServiceImpl implements ICombogridService {

    @Autowired
    private ICombogridMapper combogridMapper;

    /**
     * 在区嫌疑人
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<CombogridEntity> getSuspectSerialNo(Map<String, Object> map) throws Exception {
        return combogridMapper.getSuspectSerialNo(map);
    }

    /**
     * 开始审讯时获取所有嫌疑人入区编号、姓名、身份证号码
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<CombogridEntity> getSuspectSerialNoForRecord(Map<String, Object> map) throws Exception {
        return combogridMapper.getSuspectSerialNoForRecord(map);
    }

    /**
     * 查询其他人获取所有嫌疑人入区编号、姓名、身份证号码
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<CombogridEntity> getRecordOtherSerialNo(Map<String, Object> map) throws Exception {
        return combogridMapper.getRecordOtherSerialNo(map);
    }

    /**
     * 看押中的嫌疑人
     *
     * @param params
     * @return
     */
    @Override
    public List<CombogridEntity> getAllDetainSerialNo(Map<String, Object> params) {
        return combogridMapper.getAllDetainSerialNo(params);
    }

    @Override
    public List<CombogridEntity> getOrderContentForEntrance(Map<String, Object> map) {
        return combogridMapper.getOrderContentForEntrance(map);
    }

    @Override
    public List<CombogridEntity> getAllOrderInfo(Map<String, Object> params) throws Exception {
        return combogridMapper.getAllOrderInfo(params);
    }

    /**
     * 查询在区民警
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<CombogridEntity> getPoliceSerialNo(Map<String, Object> map) throws Exception {
        return combogridMapper.getPoliceSerialNo(map);
    }

    @Override
    public List<CombogridEntity> getPersonBelong(Map<String, Object> params) {
        return combogridMapper.getPersonBelong(params);
    }
}

