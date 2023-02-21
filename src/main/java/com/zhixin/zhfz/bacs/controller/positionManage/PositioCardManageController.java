package com.zhixin.zhfz.bacs.controller.positionManage;


import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.bacs.entity.PositionAlarmEntity;
import com.zhixin.zhfz.bacs.entity.PositionDataEntity;
import com.zhixin.zhfz.bacs.entity.PositionEntranceEntity;
import com.zhixin.zhfz.bacs.services.positionManage.IPositioAlarmService;
import com.zhixin.zhfz.bacs.services.positionManage.IPositioManageService;
import com.zhixin.zhfz.bacs.services.positionManage.IPositionEntranceService;
import com.zhixin.zhfz.common.utils.ControllerTool;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/positionManage")
public class PositioCardManageController {

	private static Logger logger = LoggerFactory.getLogger(PositioCardManageController.class);
    @Autowired
    private IPositioManageService positionDataService;
    @Autowired
    private IPositioAlarmService positionAlarmService;
	
    @Autowired
    private IPositionEntranceService positionEntranceService;
	  // 查询在办案区的人员信息
    @RequestMapping(value = "/listPolices")
    @ResponseBody
    public Map<String, Object> listPersons(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<Map<String, Object>> datas = new ArrayList<>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
//        if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            datas = this.positionDataService.selectPositionDataPoliceInfo(map);
            total = this.positionDataService.selectPositionDataPoliceInfoCount(map);
//        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }
    
    // 查询人员的定位轨迹
    @RequestMapping(value = "/listPositionData")
    @ResponseBody
    public Map<String, Object> listPositionData(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<PositionDataEntity> datas = new ArrayList<PositionDataEntity>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        System.err.println(params);
//        if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            datas = this.positionDataService.list(map);
            total = this.positionDataService.count(map);
//        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }

 // 查询手环的告警信息
    @RequestMapping(value = "/listAlarm")
    @ResponseBody
    public Map<String, Object> listAlarm(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<PositionAlarmEntity> datas = new ArrayList<PositionAlarmEntity>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
//        if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            datas = this.positionAlarmService.list(map);
            total = this.positionAlarmService.count(map);
//        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }

    
//    // 查询人员的进出记录
    @RequestMapping(value = "/listPositionEntrance")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<PositionEntranceEntity> datas = new ArrayList<PositionEntranceEntity>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
//        if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            datas = this.positionEntranceService.list(map);
            total = this.positionEntranceService.count(map);
//        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }

}
