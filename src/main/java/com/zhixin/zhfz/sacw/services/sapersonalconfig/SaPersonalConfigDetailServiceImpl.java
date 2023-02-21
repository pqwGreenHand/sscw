package com.zhixin.zhfz.sacw.services.sapersonalconfig;

import com.zhixin.zhfz.sacw.dao.sapersonalconfig.ISaPersonalConfigDetailMapper;
import com.zhixin.zhfz.sacw.entity.SaPersonalConfigDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaPersonalConfigDetailServiceImpl implements ISaPersonalConfigDetailService {

    @Autowired
    private ISaPersonalConfigDetailMapper dao;

    @Override
    public List<SaPersonalConfigDetailEntity> list(Map<String, Object> params) throws Exception {
        return dao.list(params);
    }

    @Override
    public void insert(SaPersonalConfigDetailEntity entity) throws Exception {
        dao.insert(entity);
    }

    @Override
    public void update(SaPersonalConfigDetailEntity entity) throws Exception {
        dao.update(entity);
    }

    @Override
    public void delete(int id) throws Exception {
        dao.delete(id);
    }

    @Override
    public int count(Map<String, Object> params) throws Exception {
        return dao.count(params);
    }

    @Override
    public List<SaPersonalConfigDetailEntity> listDetailsByNameAndInterrogateAreaId(String name, int id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("warehouseId", id);
        return dao.listDetailsByNameAndInterrogateAreaId(map);
    }

    @Override
    public List<SaPersonalConfigDetailEntity> checkurl(String areaid) {

        return dao.checkurl(areaid);
    }

    @Override
    public String queryBoxNumberById(int lockid) {
        return dao.queryBoxNumberById(lockid);
    }

    @Override
    public SaPersonalConfigDetailEntity listDetailByCodeAndParam(String codeKey, String paramKey, Long areaId) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("codeKey", codeKey);
        params.put("paramKey", paramKey);
        params.put("areaId", areaId);
        return dao.listDetailByCodeAndParam(params);
    }

    @Override
    public List<SaPersonalConfigDetailEntity> listInParamDetailByOutParamId(Long id) throws Exception {
        return dao.listInParamDetailByOutParamId(id);
    }

    /**
     * 查询个性化配置
     *
     * @param warehouseId
     * @param name
     * @return
     */
    @Override
    public Map<String, String> listConfigDetailByAreaAndName(Integer warehouseId, String name) {
        Map<String, String> result = new HashMap<String, String>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("warehouseId", warehouseId);
        param.put("name", name);
        List<SaPersonalConfigDetailEntity> pcdes = dao.listConfigDetailByAreaAndName(param);
        if (pcdes != null) {
            for (SaPersonalConfigDetailEntity pcds : pcdes) {
                result.put(pcds.getParamKey(), pcds.getParamValue());
            }
        }
        return result;
    }
}
