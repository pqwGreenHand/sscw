package com.zhixin.zhfz.bacs.services.combobox;

import com.zhixin.zhfz.bacs.entity.ComboboxEntity;

import java.util.List;
import java.util.Map;

public interface IComboboxService {

	List<ComboboxEntity> listCertificateType(Map<String, Object> map) throws Exception;
	
	List<ComboboxEntity> getAllCuff(Map<String, Object> map) throws Exception;
	/**
	 * 根据类型获取对象类型下面的办案区的名字
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<ComboboxEntity> getAllInterrogateAreaName(Map<String, Object> map) throws Exception;

	List<ComboboxEntity> listAllOrganizationName(Map<String, Object> params);

	List<ComboboxEntity> listAllRoomName(Map<String, Object> params);
	
	List<ComboboxEntity> listRoomBySerial(Map<String, Object> params);

	List<ComboboxEntity> listAllOrganization(Map<String, Object> params);

	List<ComboboxEntity> listAllOrganization1(Map<String, Object> params);


	List<ComboboxEntity> listLawType(Map<String, Object> params);

	List<ComboboxEntity> listLawName(Map<String, Object> params);


	List<ComboboxEntity> listCrimeDefine(Map<String, Object> params);

	List<ComboboxEntity> listKnowledgeCrime(Map<String, Object> params);

	/**
	 * @Author jzw
	 * @Description 醒酒下拉
	 * @Date 16:44 2019/2/26
	 * @Param [params]
	 * @return java.util.List<com.zhixin.zhfz.bacs.entity.ComboboxEntity>
	 **/
	List<ComboboxEntity> listSerial(Map<String, Object> params);
	List<ComboboxEntity> listSerialPolice(Map<String, Object> params);

	/**
	 * @Author jzw
	 * @Description 笔录问答模板
	 * @Date 18:46 2019/3/4
	 * @Param [params]
	 * @return java.util.List<com.zhixin.zhfz.bacs.entity.ComboboxEntity>
	 **/
	List<ComboboxEntity> listRecordTemplate(Map<String, Object> params);

	List<ComboboxEntity> listCrimeTypeByNature(Map<String, Object> params);

	/**
	 * 查询储物柜下拉框
	 * @param
	 * @return
	 */
	List<ComboboxEntity> listBelongLockerBox(Integer areaId,Long lockerId);
	/**
	 * 查询储物柜下拉框
	 * @param
	 * @return
	 */
	List<ComboboxEntity> listExhibitLockerBox(Integer areaId,Long lockerId);
	/**
	 * 查询储物柜下拉框
	 * @param
	 * @return
	 */
	List<ComboboxEntity> listPoliceBelongLockerBox(Integer areaId,Long lockerId);

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
