package com.zhixin.zhfz.common.services.combobox;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.common.dao.combobox.IComboboxMapper;
import com.zhixin.zhfz.common.entity.ComboboxEntity;

@Service
public class ComboboxServiceImpl implements IComboboxService {
    @Autowired
    private IComboboxMapper comboboxMapper;

    /**
     * 查询证件类型
     *
     * @param params
     * @return
     */
    @Override
    public List<ComboboxEntity> listCertificateType(Map<String, Object> params) {
        return comboboxMapper.listCertificateType(params);
    }

    /**
     * 字典下拉列表
     *
     * @param params
     * @return
     */
    @Override
    public List<ComboboxEntity> listCode(Map<String, Object> params) {
        return comboboxMapper.listCode(params);
    }

    /**
     * 查询所有部门信息
     *
     * @param params
     * @return
     */
    @Override
    public List<ComboboxEntity> listAllOrganizationName(Map<String, Object> params) {
        return comboboxMapper.listAllOrganizationName(params);
    }

    @Override
    public List<ComboboxEntity> listOutOrganizationName(Map<String, Object> params) {
        return comboboxMapper.listOutOrganizationName(params);
    }

    @Override
    public List<ComboboxEntity> getRoles(Map<String, Object> params) {
        return comboboxMapper.getRoles(params);
    }

    @Override
    public List<ComboboxEntity> listLawType(Map<String, Object> params) {
        return comboboxMapper.listLawType(params);
    }

    @Override
    public List<ComboboxEntity> listLawName(Map<String, Object> params) {
        return comboboxMapper.listLawName(params);
    }

    @Override
    public List<ComboboxEntity> listArea(Map<String, Object> params) {
        return comboboxMapper.listArea(params);
    }
    @Override
    public List<ComboboxEntity> listWare(Map<String, Object> params) {
        return comboboxMapper.listWare(params);
    }
}
