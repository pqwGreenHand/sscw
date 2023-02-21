package com.zhixin.zhfz.bacs.controller.statistics;


/*
 * 数据统计
 * */

import com.zhixin.zhfz.bacs.controller.serial.SerialController;
import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.bacs.services.console.IConsoleService;
import com.zhixin.zhfz.bacs.services.statistics.IStatisticsService;
import com.zhixin.zhfz.common.entity.CaseEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/statistics")
public class StatisticsController {

    private static Logger logger = LoggerFactory.getLogger(StatisticsController.class);
    @Autowired
    private IStatisticsService statisticsService;

    @Autowired
    private IConsoleService consoleService;


    // 查询羁押超时人数

    @RequestMapping(value = "/countkyouttime")
    @ResponseBody
    public Map<String,Object> countkyouttime(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        Map<String, Object> param = ControllerTool.mapFilter(params);
        String str = "";
        if (request.getParameter("areaId") == null) {
            param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
        } else {
            if (request.getParameter("areaId").equals("null")) {
                str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
            } else {
                param.put("areaId", (params.get("areaId") != null) ? params.get("areaId") : ControllerTool.getCurrentAreaID(request));
            }
        }
        param.put("dataAuth", str);
        Map<String, Object> result = new HashMap<String, Object>();
        int count = consoleService.countkyouttime(param);
        result.put("count", count);
        return result;
    }
    @RequestMapping(value = "/listJyOutTime")
    @ResponseBody
    public Map<String, Object> listJyOutTime(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        Map<String, Object> param = ControllerTool.mapFilter(params);
        String str = "";
        if (request.getParameter("areaId") == null) {
            param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
        } else {
            if (request.getParameter("areaId").equals("null")) {
                str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
            } else {
                param.put("areaId", (params.get("areaId") != null) ? params.get("areaId") : ControllerTool.getCurrentAreaID(request));
            }
        }
        param.put("dataAuth", str);
        List<SerialEntity> list  = this.consoleService.listJyOutTime(param);
        int  count = this.consoleService.countJyOutTime(param);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", list);
        return result;
    }


    // 预约人数
    @RequestMapping(value = "countOrderRequest" )
    @ResponseBody
    public Map<String, Object> countOrderRequest(@RequestParam(required=false) String areaid,HttpServletRequest request){

        logger.info("预约人数查询");
        Map<String,Object> param = new HashMap<>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if(areaid !=null && areaid != "" && areaid != "null"){
            param.put("areaId", Integer.parseInt(areaid));
        }else{
            if(sessionInfo.getCurrentArea().getId() == -100){
                param.put("areaId",1);
            }else{
                param.put("areaId", sessionInfo.getCurrentArea().getId());
            }
        }
        int total = consoleService.countOrderRequest(param);
        logger.info("预约人数查询"  +  total);
        Map<String,Object> resule = new HashMap<>();
        resule.put("total", total);
        return resule;
    }


    // 出入区 安检人数
    @RequestMapping(value = "countSerial")
    @ResponseBody
    public Map<String,Object> countSerial(@RequestParam Map<String, Object> params,HttpServletRequest request,@RequestParam(required=false) String areaid){
        System.err.println("============areaid=============="+areaid);
        logger.info("出入区 安检人数查询");
        Map<String, Object> param = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if(areaid!=null && areaid!="" && areaid !="null"){
            param.put("areaId", Integer.parseInt(areaid));
        }else{
            if(sessionInfo.getCurrentArea().getId() == -100){
                param.put("areaId",1);
            }else{
                param.put("areaId", sessionInfo.getCurrentArea().getId());
            }
        }
        int total_rq = consoleService.countInterrogateSerial(param);
        int  total_cq =consoleService.queryChuquByDay1(param);

        int total_els = consoleService.countOtherStatus(param);
        int total_lscq = consoleService.queryLinshiByDay1(param);
        logger.info("出入区 安检人数" + total_rq + "--"+ total_cq + "--"+ total_els + "--"+ total_lscq);
        Map<String,Object> result = new HashMap<>();
        result.put("total_rq", total_rq);
        result.put("total_cq", total_cq);
        result.put("total_els", total_els);
        result.put("total_lscq", total_lscq);
        return result;
    }


    // 看押中人数
    @RequestMapping(value = "/countWaitingRecord")
    @ResponseBody
    public Map<String, Object> countWaitingRecord(HttpServletRequest request,@RequestParam(required=false) String areaid){

        logger.info("看押中人数查询");
        Map<String, Object> param = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if(areaid!=null && areaid!="" && areaid!="null"){
            param.put("areaId", Integer.parseInt(areaid));
        }else{
            if(sessionInfo.getCurrentArea().getId() == -100){
                param.put("areaId",1);
            }else{
                param.put("areaId", sessionInfo.getCurrentArea().getId());
            }
        }
        int total = consoleService.countWaitingRecord(param);
        logger.info("看押中人数查询"+total);
        Map<String ,Object> result = new HashMap<String ,Object>();
        result.put("total", total);
        return result;
    }


    // 审讯中人数
    @RequestMapping(value = "/countRecord")
    @ResponseBody
    public Map<String, Object> countRecord(HttpServletRequest request,@RequestParam(required=false) String areaid){
        logger.info("审讯中人数查询");
        Map<String, Object> param = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if(areaid!=null && areaid!="" && areaid!="null"){
            param.put("areaId", Integer.parseInt(areaid));
        }else{
            if(sessionInfo.getCurrentArea().getId() == -100){
                param.put("areaId",1);
            }else{
                param.put("areaId", sessionInfo.getCurrentArea().getId());
            }
        }
        int total = consoleService.countInterrogateRecord(param);
        logger.info("审讯中人数查询"+total);
        Map<String ,Object> result = new HashMap<String ,Object>();
        result.put("total", total);
        return result;
    }


    // 其他人员人数
    @RequestMapping(value = "/countotherentrance")
    @ResponseBody
    public Map<String, Object> countOtherentrance(@RequestParam Map<String, Object> params,HttpServletRequest request,@RequestParam(required=false) String areaid){
        logger.info("其他人员人数查询");
        Map<String, Object> param = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if(areaid!=null && areaid!="" && areaid!="null"){
            param.put("areaId", Integer.parseInt(areaid));
        }else{
            if(sessionInfo.getCurrentArea().getId() == -100){
                param.put("areaId",1);
            }else{
                param.put("areaId", sessionInfo.getCurrentArea().getId());
            }
        }
        int total = consoleService.countOtherentrance(param);
        logger.info("其他人员人数查询"+total);
            
        Map<String ,Object> result = new HashMap<String ,Object>();
        result.put("total", total);
        return result;
    }
}
