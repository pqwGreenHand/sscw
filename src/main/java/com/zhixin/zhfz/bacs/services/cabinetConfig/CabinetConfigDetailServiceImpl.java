package com.zhixin.zhfz.bacs.services.cabinetConfig;

import com.zhixin.zhfz.bacs.dao.cabinetConfig.ICabinetConfigDetailMapper;
import com.zhixin.zhfz.bacs.entity.CabinetConfigDetailEntity;
import com.zhixin.zhfz.bacs.entity.CabinetConfigEntity;
import com.zhixin.zhfz.common.dao.authortity.IAuthorityMapper;
import com.zhixin.zhfz.common.entity.AbstractTreeEntity;
import com.zhixin.zhfz.common.entity.AuthorityEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CabinetConfigDetailServiceImpl implements ICabinetConfigDetailService {

    @Autowired
    private ICabinetConfigDetailMapper cabinetConfigDetailMapper;

    @Override
    public List<CabinetConfigDetailEntity> list(Map<String, Object> param) {
        return cabinetConfigDetailMapper.list(param);
    }

    @Override
    public int count(Map<String, Object> param) {
        return cabinetConfigDetailMapper.count(param);
    }

    @Override
    public void Insert(CabinetConfigDetailEntity detailEntity) {
        cabinetConfigDetailMapper.insert(detailEntity);
    }

    @Override
    public void delete(int id) {
        cabinetConfigDetailMapper.delete(id);
    }

    @Override
    public void update(CabinetConfigDetailEntity entity) {
        cabinetConfigDetailMapper.update(entity);
    }

    /**
     * 根据行列排序
     *
     * @param areaId
     * @return
     */
    @Override
    public List<CabinetConfigDetailEntity> selectRowColSort(Integer areaId) {
        return cabinetConfigDetailMapper.selectRowColSort(areaId);
    }

    /**
     * 根据行列排序
     *
     * @param areaId
     * @return
     */
    @Override
    public List<CabinetConfigDetailEntity> selectRowColSortByPolice(Integer areaId) {
        return cabinetConfigDetailMapper.selectRowColSortByPolice(areaId);
    }

    /**
     * 涉案物品柜根据行列排序
     *
     * @param areaId
     * @return
     */
    @Override
    public List<CabinetConfigDetailEntity> selectRowColSortBySA(Integer areaId) {
        return cabinetConfigDetailMapper.selectRowColSortBySA(areaId);
    }

    /**
     * 根据外参ID获取内参LIST
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public CabinetConfigEntity listInParamDetailByOutParamId(Long id) throws Exception {
        return cabinetConfigDetailMapper.listInParamDetailByOutParamId(id);
    }

    /**
     * 根据储物柜id查询编号
     *
     * @param lockid
     * @return
     */
    @Override
    public String queryBoxNumberById(int lockid) {
        return cabinetConfigDetailMapper.queryBoxNumberById(lockid);
    }

    /**
     * 涉案物品柜手机排序
     * @param areaId
     * @return
     */
    @Override
    public List<CabinetConfigDetailEntity> selectRowColSortBySAAPP(Integer areaId) {
        return cabinetConfigDetailMapper.selectRowColSortBySAAPP(areaId);
    }
}
