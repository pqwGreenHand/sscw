package com.zhixin.zhfz.sacw.services.visualization;

import com.zhixin.zhfz.sacw.dao.visualization.IVisualizationMapper;
import com.zhixin.zhfz.sacw.entity.ConsoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VisualizationServiceImpl implements IVisualizationService {

    @Autowired
    IVisualizationMapper mapper;


    @Override
    public List<ConsoleEntity> listTotalByCaseNature(Map<String, Object> map) throws Exception {
        return mapper.listTotalByCaseNature(map);
    }

    @Override
    public List<ConsoleEntity> listTotalByOrganization(Map<String, Object> map) throws Exception {
        return mapper.listTotalByOrganization(map);
    }

    @Override
    public List<ConsoleEntity> listTotalByOutputType(Map<String, Object> map) throws Exception {
        return mapper.listTotalByOutputType(map);
    }

    @Override
    public List<ConsoleEntity> listTotalByInvolvedStatus(Map<String, Object> map) throws Exception {
        return mapper.listTotalByInvolvedStatus(map);
    }

    @Override
    public List<ConsoleEntity> listTotalByInvolvedType(Map<String, Object> map) throws Exception {
        return mapper.listTotalByInvolvedType(map);
    }

    @Override
    public List<ConsoleEntity> listWarehouse(Map<String, Object> map) throws Exception {
        return mapper.listWarehouse(map);
    }
}
