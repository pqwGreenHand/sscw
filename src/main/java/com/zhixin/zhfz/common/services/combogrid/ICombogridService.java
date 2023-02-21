package com.zhixin.zhfz.common.services.combogrid;

import com.zhixin.zhfz.common.entity.CombogridEntity;

import java.util.List;
import java.util.Map;

public interface ICombogridService {

    /**
     * 在区嫌疑人
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<CombogridEntity> getSuspectSerialNo(Map<String, Object> map) throws Exception;

    /**
     * 开始审讯时获取所有嫌疑人入区编号、姓名、身份证号码
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<CombogridEntity> getSuspectSerialNoForRecord(Map<String, Object> map) throws Exception;

    /**
     * 查询其他人获取所有嫌疑人入区编号、姓名、身份证号码
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<CombogridEntity> getRecordOtherSerialNo(Map<String, Object> map) throws Exception;

    /**
     * 在看押中的嫌疑人
     *
     * @param params
     * @return
     */
    List<CombogridEntity> getAllDetainSerialNo(Map<String, Object> params);

    //嫌疑人入区获取嫌疑人下拉框
    List<CombogridEntity> getOrderContentForEntrance(Map<String, Object> map);

    //嫌疑人入区获取预约下拉框
    List<CombogridEntity> getAllOrderInfo(Map<String, Object> params) throws Exception;

    /**
     * 查询在区民警
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<CombogridEntity> getPoliceSerialNo(Map<String, Object> map) throws Exception;

    List<CombogridEntity> getPersonBelong(Map<String, Object> params);
}
