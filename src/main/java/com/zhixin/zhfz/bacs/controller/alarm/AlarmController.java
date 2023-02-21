package com.zhixin.zhfz.bacs.controller.alarm;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.alarm.IAlarmService;
import com.zhixin.zhfz.bacs.services.archives.IElecArchivesService;
import com.zhixin.zhfz.common.common.HikUtil8700;
import com.zhixin.zhfz.common.controller.organization.OrganizationController;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/alarm")
public class AlarmController {

    @Autowired
    private IAlarmService iAlarmService;

    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);


    /**
     * 告警类型查询数量信息
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/listAlarmCountByType")
    @ResponseBody
    public Map<String,Object>  listAlarmCountByType(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<AlarmTypeCoutEntity> alarmTypeCoutEntities = iAlarmService.listAlarmCountByType(map);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        if(alarmTypeCoutEntities != null && alarmTypeCoutEntities.size() > 0){
            for (AlarmTypeCoutEntity alarmTypeCoutEntity:alarmTypeCoutEntities ) {
                returnMap.put(alarmTypeCoutEntity.getAlarmTypeId().toString(),alarmTypeCoutEntity.getAlarmCount());
            }
        }
        return returnMap;
    }

    /**
     * 告警信息列表
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/listAlarm")
    @ResponseBody
    public Map<String, Object> listAlarm(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<AlarmEntity> list = null;
        int total = 0;
        list = iAlarmService.list(map);
        total = iAlarmService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }

    @RequestMapping(value = "/playBackVideo")
    @ResponseBody
    public MessageEntity playBackVideo(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                       HttpServletResponse response) {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        String cameraNo = request.getParameter("cameraNo");
        return HikUtil8700.getPlayBackXml(cameraNo);
    }

}
