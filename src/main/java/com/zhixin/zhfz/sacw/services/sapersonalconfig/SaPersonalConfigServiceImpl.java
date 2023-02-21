package com.zhixin.zhfz.sacw.services.sapersonalconfig;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhixin.zhfz.common.common.ConfigJsonUtil;
import com.zhixin.zhfz.sacw.dao.sapersonalconfig.ISaPersonalConfigDetailMapper;
import com.zhixin.zhfz.sacw.dao.sapersonalconfig.ISaPersonalConfigMapper;
import com.zhixin.zhfz.sacw.entity.SaPersonalConfigEntity;
import com.zhixin.zhfz.sacw.entity.SaPersonalConfigFusionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SaPersonalConfigServiceImpl implements ISaPersonalConfigService {

    private static Logger logger = LoggerFactory.getLogger(SaPersonalConfigServiceImpl.class);

    private static JSONArray configJsonArray = null;

    private final static String configJsonFileName = "common/personal_config.txt";

    @Autowired
    private ISaPersonalConfigMapper dao;
    @Autowired
    private ISaPersonalConfigDetailMapper detaildao;


    @Override
    public List<SaPersonalConfigEntity> list(Map<String, Object> params) throws Exception {
        return dao.list(params);
    }

    @Override
    public int count(Map<String, Object> params) throws Exception {
        return dao.count(params);
    }

    @Override
    public void insert(SaPersonalConfigEntity entity) throws Exception {
        dao.insert(entity);
    }

    @Override
    public void update(SaPersonalConfigEntity entity) throws Exception {
        dao.update(entity);
    }

    @Override
    public void delete(int id) throws Exception {
        dao.delete(id);
        detaildao.deleteByPersonalConfigId(id);
    }

    @Override
    public void initArea(int warehouseId) {
        initConfigJsonArray();
        if (configJsonArray != null) {
            for (int i = 0; i < configJsonArray.size(); i++) {
                JSONObject json = configJsonArray.getJSONObject(i);
                if (!json.isEmpty()) {
                    SaPersonalConfigEntity entity = new SaPersonalConfigEntity();

                    try {
                        entity.setType(json.getString("name"));
                        entity.setWarehouseId(warehouseId);
                        entity.setDesc(json.getString("desc"));
                        entity.setIsEnable(json.getInteger("isEnable"));
                        dao.insert(entity);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    @Override
    public List<SaPersonalConfigFusionEntity> getDetailByType(Map<String, Object> map) {
        return dao.getDetailByType(map);
    }

    private void initConfigJsonArray() {
        if (configJsonArray == null) {
            configJsonArray = ConfigJsonUtil.readJsonArray(configJsonFileName);
        }
    }

}
