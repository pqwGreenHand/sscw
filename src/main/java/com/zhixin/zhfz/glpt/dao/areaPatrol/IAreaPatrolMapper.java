package com.zhixin.zhfz.glpt.dao.areaPatrol;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.glpt.entity.AlarmEntity;
import com.zhixin.zhfz.glpt.entity.KpEvaEntity;
import com.zhixin.zhfz.glpt.entity.KpPoliceEntity;

import java.util.List;
import java.util.Map;

public interface IAreaPatrolMapper {

    /**
     * 违规报警查询
     *
     * @param map
     * @return
     */
    List<AlarmEntity> listByAlarm(Map<String, Object> map);

    /**
     * 增加违规告警
     *
     * @param entity
     */
    void addAlarm(AlarmEntity entity);

    /**
     * 修改处理内容
     *
     * @param entity
     */
    void uodateById(AlarmEntity entity);

    /**
     * 管理平台查询告警信息总数
     *
     * @param map
     * @return
     */
    Integer countByAlarm(Map<String, Object> map);
 //办案场所巡查相关
    List<AreaEntity> listAllArea(Map<String, Object> map);

    int listAllAreaCount(Map<String, Object> map);
    /**
     * 各单位考评总数
     *
     * @param map
     * @return
     */
    List<KpEvaEntity> selectOrgEva(Map map);
    int selectOrgEvaCount(Map map);
    List<KpPoliceEntity> selectPoliceInfo(Map map);
    int selectPoliceInfoCount(Map map);
    List<OrganizationEntity> selectAllOrgPages(Map map);
    int selectAllOrgPagesCount(Map map);

    //获取办案中心在区人员
    List<SerialEntity> listPerson(Map<String, Object> params) throws Exception;

    int listPersonCount(Map<String, Object> params) throws Exception;
}
