package com.zhixin.zhfz.sacw.services.combobox;


import com.zhixin.zhfz.bacs.entity.ComboboxEntity;

import java.util.List;
import java.util.Map;

public interface IInvComboboxService {

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listCertificateType(Map<String, Object> map) throws Exception;

    /**
     * 根据类型获取对象类型下面的办案区的名字
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> getAllInterrogateAreaName(Map<String, Object> map) throws Exception;

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> getAllUser(Map<String, Object> params);

    List<ComboboxEntity> listAllOrganizationName(Map<String, Object> params);

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listAllRoomGroupName(Map<String, Object> params);

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listAllRoomName(Map<String, Object> params);

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listRoomBySerial(Map<String, Object> params);

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> getAllInvLocation(Map<String, Object> params);

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> getAllShelf(Map<String, Object> params);

    /**
     * 查询保管部门
     *
     * @return
     * @throws Exception
     */
    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listOrgByType(Map<String, Object> map) throws Exception;

    /**
     * 查询仓库
     *
     * @return
     * @throws Exception
     */
    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listInRecordByWareHouseId(Map<String, Object> map) throws Exception;

    /**
     * 查询仓库的区域
     *
     * @return
     * @throws Exception
     */
    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listLocation(Map<String, Object> map) throws Exception;


    /**
     * 查询区域对应的货架
     *
     * @return
     * @throws Exception
     */
    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listShelf(Map<String, Object> map) throws Exception;

    /**
     * 查询字典类型
     *
     * @return
     * @throws Exception
     */
    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listCodeCombobox(Map<String, Object> map) throws Exception;

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> getWareHouse(Map<String, Object> map) throws Exception;

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listCrimeTypeByNature(Map<String, Object> params);

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> getInvolvedByCase(Map<String, Object> params);

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> getInvolvedByCaseId(Map<String, Object> params);

    List<com.zhixin.zhfz.bacs.entity.ComboboxEntity> listInputType();

    /*List<ComboboxEntity> listWarehouseByCase();*/
}
