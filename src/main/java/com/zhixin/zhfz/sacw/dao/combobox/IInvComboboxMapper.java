package com.zhixin.zhfz.sacw.dao.combobox;


import com.zhixin.zhfz.bacs.entity.ComboboxEntity;

import java.util.List;
import java.util.Map;

public interface IInvComboboxMapper {

    List<ComboboxEntity> listCertificateType(Map<String, Object> map) throws Exception;

    /**
     * 根据类型获取对象类型下面的办案区的名字
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ComboboxEntity> getAllInterrogateAreaName(Map<String, Object> map) throws Exception;

    List<ComboboxEntity> getAllPerson(Map<String, Object> map);

    List<ComboboxEntity> getAllUser(Map<String, Object> map);

    List<ComboboxEntity> listAllRoomGroupName(Map<String, Object> map);

    List<ComboboxEntity> listAllOrganizationName(Map<String, Object> params);

    List<ComboboxEntity> listAllRoomName(Map<String, Object> map);

    List<ComboboxEntity> listRoomBySerial(Map<String, Object> params);

    List<ComboboxEntity> getAllInvLocation(Map<String, Object> params);

    List<ComboboxEntity> getAllShelf(Map<String, Object> params);

    /**
     * 查询保管部门
     *
     * @return
     * @throws Exception
     */
    List<ComboboxEntity> listOrgByType(Map<String, Object> map) throws Exception;

    /**
     * 查询仓库
     *
     * @return
     * @throws Exception
     */
    List<ComboboxEntity> listInRecordByWareHouseId(Map<String, Object> map) throws Exception;

    /**
     * 查询仓库的区域
     *
     * @return
     * @throws Exception
     */
    List<ComboboxEntity> listLocation(Map<String, Object> map) throws Exception;

    /**
     * 查询区域对应的货架
     *
     * @return
     * @throws Exception
     */
    List<ComboboxEntity> listShelf(Map<String, Object> map) throws Exception;


    /**
     * 查询字典类型
     *
     * @return
     * @throws Exception
     */
    List<ComboboxEntity> listCodeCombobox(Map<String, Object> map) throws Exception;

    List<ComboboxEntity> getWareHouse(Map<String, Object> map) throws Exception;

    List<ComboboxEntity> listCrimeTypeByNature(Map<String, Object> params);

    List<ComboboxEntity> getInvolvedByCase(Map<String, Object> params);

    List<ComboboxEntity> getInvolvedByCaseId(Map<String, Object> params);


    List<ComboboxEntity> listInputType();

}
