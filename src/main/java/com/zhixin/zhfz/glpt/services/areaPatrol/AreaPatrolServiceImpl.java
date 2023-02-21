package com.zhixin.zhfz.glpt.services.areaPatrol;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.glpt.dao.areaPatrol.IAreaPatrolMapper;
import com.zhixin.zhfz.glpt.entity.AlarmEntity;
import com.zhixin.zhfz.glpt.entity.KpEvaEntity;
import com.zhixin.zhfz.glpt.entity.KpPoliceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaPatrolServiceImpl implements IAreaPatrolService {

    @Autowired
    private IAreaPatrolMapper iAlarmMapper;

    /**
     * 违规报警查询
     *
     * @param map
     * @return
     */
    @Override
    public List<AlarmEntity> listByAlarm(Map<String, Object> map) {
        return iAlarmMapper.listByAlarm(map);
    }

    /**
     * 增加违规告警
     *
     * @param entity
     */
    @Override
    public void addAlarm(AlarmEntity entity) {
        iAlarmMapper.addAlarm(entity);
    }

    /**
     * 管理平台查询告警信息总数
     *
     * @param map
     * @return
     */
    @Override
    public Integer countByAlarm(Map<String, Object> map) {
        return iAlarmMapper.countByAlarm(map);
    }
 @Override
    public List<AreaEntity> listAllArea(Map<String, Object> map) {
        return iAlarmMapper.listAllArea(map);
    }

    @Override
    public int listAllAreaCount(Map<String, Object> map) {
        return iAlarmMapper.listAllAreaCount(map);
    }

    /**
     * 修改处理内容
     *
     * @param entity
     */
    @Override
    public void uodateById(AlarmEntity entity) {
        iAlarmMapper.uodateById(entity);
    }

    @Override
    public List<KpEvaEntity> selectOrgEva(Map map) {
        return iAlarmMapper.selectOrgEva(map);
    }

    @Override
    public int selectOrgEvaCount(Map map) {
        return iAlarmMapper.selectOrgEvaCount(map);
    }

    @Override
    public List<KpPoliceEntity> selectPoliceInfo(Map map) {
        return iAlarmMapper.selectPoliceInfo(map);
    }

    @Override
    public int selectPoliceInfoCount(Map map) {
        return iAlarmMapper.selectPoliceInfoCount(map);
    }

    @Override
    public List<OrganizationEntity> selectAllOrgPages(Map map) {
        return iAlarmMapper.selectAllOrgPages(map);
    }

    @Override
    public int selectAllOrgPagesCount(Map map) {
        return iAlarmMapper.selectAllOrgPagesCount(map);
    }

    @Override
    public List<SerialEntity> listPerson(Map<String, Object> params) throws Exception {
        return iAlarmMapper.listPerson(params);
    }

    @Override
    public int listPersonCount(Map<String, Object> params) throws Exception {
        return iAlarmMapper.listPersonCount(params);
    }
}
