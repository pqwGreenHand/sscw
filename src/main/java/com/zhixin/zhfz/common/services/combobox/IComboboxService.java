package com.zhixin.zhfz.common.services.combobox;

import com.zhixin.zhfz.common.entity.ComboboxEntity;

import java.util.List;
import java.util.Map;


public interface IComboboxService {
    /**
     * 查询证件类型
     *
     * @param params
     * @return
     */
    List<ComboboxEntity> listCertificateType(Map<String, Object> params);

    /**
     * 字典下拉列表
     *
     * @param params
     * @return
     */
    List<ComboboxEntity> listCode(Map<String, Object> params);

    /**
     * 查询所有部门信息
     *
     * @param params
     * @return
     */
    List<ComboboxEntity> listAllOrganizationName(Map<String, Object> params);

    List<ComboboxEntity> listOutOrganizationName(Map<String, Object> params);

    List<ComboboxEntity> getRoles(Map<String, Object> params);

    List<ComboboxEntity> listLawType(Map<String, Object> params);

    List<ComboboxEntity> listLawName(Map<String, Object> params);

    List<ComboboxEntity> listArea(Map<String, Object> params);

    List<ComboboxEntity> listWare(Map<String, Object> params);

}
