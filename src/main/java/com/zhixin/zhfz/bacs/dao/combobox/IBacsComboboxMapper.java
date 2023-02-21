package com.zhixin.zhfz.bacs.dao.combobox;

import com.zhixin.zhfz.bacs.entity.ComboboxEntity;

import java.util.List;
import java.util.Map;

public interface IBacsComboboxMapper {

    List<ComboboxEntity> listCertificateType(Map<String, Object> map) throws Exception;

    List<ComboboxEntity> getAllCuff(Map<String, Object> map) throws Exception;

    /**
     * 根据类型获取对象类型下面的办案区的名字
     *
     * @param map
     * @return
     * @throws Exception
     */

    List<ComboboxEntity> getAllInterrogateAreaName(Map<String, Object> map) throws Exception;


    List<ComboboxEntity> listAllOrganizationName(Map<String, Object> params);

    List<ComboboxEntity> listAllRoomName(Map<String, Object> map);


    List<ComboboxEntity> listRoomBySerial(Map<String, Object> params);


    List<ComboboxEntity> listAllOrganization(Map<String, Object> map);


    List<ComboboxEntity> listAllOrganization1(Map<String, Object> map);

    List<ComboboxEntity> listLawType(Map<String, Object> params);

    List<ComboboxEntity> listLawName(Map<String, Object> params);


    /**
     * @return java.util.List<com.zhixin.zhfz.bacs.entity.ComboboxEntity>
     * @Author jzw
     * @Description 行政案由下拉
     * @Date 9:26 2019/2/25
     * @Param [map]
     **/
    List<ComboboxEntity> listCrimeDefine(Map<String, Object> map);

    List<ComboboxEntity> listKnowledgeCrime(Map<String, Object> map);

    /**
     * @return java.util.List<com.zhixin.zhfz.bacs.entity.ComboboxEntity>
     * @Author jzw
     * @Description 醒酒下拉
     * @Date 16:43 2019/2/26
     * @Param [map]
     **/
    List<ComboboxEntity> listSerial(Map<String, Object> map);

    List<ComboboxEntity> listSerialPolice(Map<String, Object> map);

    /**
     * @return java.util.List<com.zhixin.zhfz.bacs.entity.ComboboxEntity>
     * @Author jzw
     * @Description 笔录问答模板
     * @Date 18:48 2019/3/4
     * @Param [map]
     **/
    List<ComboboxEntity> listRecordTemplate(Map<String, Object> map);

    List<ComboboxEntity> listCrimeTypeByNature(Map<String, Object> params);

    /**
     * 查询储物柜下拉框
     *
     * @param params
     * @return
     */
    List<ComboboxEntity> listBelongLockerBox(Map<String, Object> params);

    /**
     * 查询储物柜下拉框
     *
     * @param params
     * @return
     */
    List<ComboboxEntity> listExhibitLockerBox(Map<String, Object> params);
    /**
     * 查询储物柜下拉框
     *
     * @param params
     * @return
     */
    List<ComboboxEntity> listPoliceBelongLockerBox(Map<String, Object> params);

    /**
	 * 查询告警类型
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<ComboboxEntity> listAlarmType(Map<String, Object> map) throws Exception;

    /**
     * 查询特殊身份
     * @param map
     * @return
     * @throws Exception
     */
    List<ComboboxEntity> listOfficer(Map<String, Object> map) throws Exception;

    List<ComboboxEntity> getSerialList(Map<String, Object> map);

    List<ComboboxEntity> listAllOrganizationCode(Map<String, Object> params);

    List<ComboboxEntity> listPersonByCaseId(Map<String, Object> params);
}
