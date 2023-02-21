package com.zhixin.zhfz.sacw.quartz.cwalarmquartz;

import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import com.zhixin.zhfz.sacw.entity.CwAlarmEntity;
import com.zhixin.zhfz.sacw.services.cwalarm.ICwAlarmService;
import com.zhixin.zhfz.sacw.services.sapersonalconfig.ISaPersonalConfigDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CwalarmQuartz {

    private static final Logger logger = LoggerFactory.getLogger(PowerCacheUtil.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ICwAlarmService service;

    @Autowired
    private ISaPersonalConfigDetailService iSaPersonalConfigDetailService;

    /**
     * 定时查询并插入超期未入库和借出超时的告警数据
     */
    public void timeTaskInsertAlarm() throws Exception {

        Map<String, String> map = iSaPersonalConfigDetailService.listConfigDetailByAreaAndName(null, java.net.URLDecoder.decode("超期未入库告警天数", "utf-8"));
        String day1 = map.get("day");
        Map<String, String> map1 = iSaPersonalConfigDetailService.listConfigDetailByAreaAndName(null, java.net.URLDecoder.decode("借出超时告警", "utf-8"));
        String day2 = map.get("day");


        //查询超期未入库
        List<CwAlarmEntity> timeOutList = service.quyTimeOutListByParam(Integer.parseInt(day1));

        //插入超期未入库告警数据
        if (!timeOutList.isEmpty() && timeOutList.size() != 0) {
            for (int i = 0; i < timeOutList.size(); i++) {
                CwAlarmEntity caEntity = new CwAlarmEntity();
                try {
                    caEntity.setInvolved_id(timeOutList.get(i).getInvolved_id());
                    caEntity.setWarehouse_id(timeOutList.get(i).getWarehouse_id());
                    caEntity.setLabel_id(timeOutList.get(i).getLabel_id());
                    caEntity.setDevice_id(timeOutList.get(i).getDevice_id());
                    caEntity.setLocation_id(timeOutList.get(i).getLocation_id());
                    caEntity.setAlarm_type(1);
                    caEntity.setAlarm_name("超期未入库");
                    service.addAlarm(caEntity);
                    this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加告警信息:" + caEntity, "system", true, "管理平台");
                } catch (Exception e) {
                    System.out.println(e);
                    this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加告警信息:" + caEntity, "system", false, "管理平台");
                }
            }
        }


        //查询借出超时
        List<CwAlarmEntity> lendList = service.quyLendListByParam(Integer.parseInt(day2));

        //插入借出超时告警数据
        if (!timeOutList.isEmpty() && timeOutList.size() != 0) {
            for (int i = 0; i < lendList.size(); i++) {
                CwAlarmEntity caEntity = new CwAlarmEntity();
                try {
                    caEntity.setInvolved_id(lendList.get(i).getInvolved_id());
                    caEntity.setWarehouse_id(lendList.get(i).getWarehouse_id());
                    caEntity.setLabel_id(lendList.get(i).getLabel_id());
                    caEntity.setDevice_id(lendList.get(i).getDevice_id());
                    caEntity.setLocation_id(lendList.get(i).getLocation_id());
                    caEntity.setAlarm_type(2);
                    caEntity.setAlarm_name("借出超时");
                    service.addAlarm(caEntity);
                    this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加告警信息:" + caEntity, "system", true, "管理平台");
                } catch (Exception e) {
                    System.out.println(e);
                    this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加告警信息:" + caEntity, "system", false, "管理平台");
                }
            }
        }
    }

}
