package com.zhixin.zhfz.sacw.dao.visualization;

import com.zhixin.zhfz.sacw.entity.ConsoleEntity;

import java.util.List;
import java.util.Map;

public interface IVisualizationMapper {

    /**
     * 查询按案件性质统计数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleEntity> listTotalByCaseNature(Map<String, Object> map) throws Exception;

    /**
     * 按办案单位统计数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleEntity> listTotalByOrganization(Map<String, Object> map) throws Exception;


    /**
     * 按出库方式统计数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleEntity> listTotalByOutputType(Map<String, Object> map) throws Exception;

    /**
     * 按物品的状态统计数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleEntity> listTotalByInvolvedStatus(Map<String, Object> map) throws Exception;

    /**
     * 按物品种类统计数量
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleEntity> listTotalByInvolvedType(Map<String, Object> map) throws Exception;

    /**
     * 查看有涉案财物仓库的办案场所
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<ConsoleEntity> listWarehouse(Map<String, Object> map) throws Exception;

}
