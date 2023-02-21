package com.zhixin.zhfz.bacsapp.controller.track;


import com.sun.xml.bind.v2.model.core.ID;
import com.zhixin.zhfz.bacsapp.entity.TrackInfoEntity;
import com.zhixin.zhfz.bacsapp.form.TrackInfoForm;
import com.zhixin.zhfz.bacsapp.services.common.ICommonService;
import com.zhixin.zhfz.bacsapp.services.track.ITrackService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.apache.commons.lang.math.RandomUtils;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacsapp/track")
public class TrackController {
    private static Logger logger = LoggerFactory.getLogger(TrackController.class);
    @Autowired
    private ITrackService service;
    @Autowired
    private ICommonService commonService;
    @RequestMapping(value = "/listTrack")
    @ResponseBody
    public List<TrackInfoEntity> listTrack(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        logger.debug("=======================TrackController.listTrack=======================");
        Integer areaId=ControllerTool.getCurrentAreaID(request);
        Map<String, Object> map = ControllerTool.mapFilter(param);
         map.put("areaId", areaId);
        List<TrackInfoEntity> list = service.listTrack(map);
        return list;
    }
    @RequestMapping(value = "/insertTrack")
    @ResponseBody
    public MessageEntity insertTrack(@RequestBody   TrackInfoForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        TrackInfoEntity entity = new TrackInfoEntity();
        entity.setSerialId(form.getSerialId());
        entity.setRoomGroup(form.getRoomGroup());
        entity.setCardType(1);
        entity.setStartTime(form.getStartTime());
        entity.setEndTime(form.getEndTime());
        try {
            service.insertTrack(entity);

        } catch (Exception e) {
            logger.error("Error on add insertTrack!", e);

            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加成功!");
    }

    @RequestMapping(value = "/removeTrack")
    @ResponseBody
    public MessageEntity removeEnterprise(@RequestParam  Map<String, Object> param, HttpServletRequest request) {

        String id = request.getParameter("id");
        try {
            service.deleteTrack(Integer.parseInt(id));
        } catch (Exception e) {
            logger.error("Error on deleting Enterprise!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功!");
    }

    // 查询所有入区嫌疑人
    @RequestMapping(value = "/listAllSerial")
    @ResponseBody
    public Map<String,Object> listAllSerial(@RequestParam Map<String, Object> params,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------listAllSerial-----------------");
        Integer areaId=ControllerTool.getCurrentAreaID(request);
        Map<String, Object> map = ControllerTool.mapFilter(params);
        map.put("areaId",areaId);
        Map<String, Object> map1 = getAuthMethod(request);
        Map<String, Object> data = new HashMap<String, Object>();
        if ("true".equals(map1.get("flag"))){
            map.put("dataAuth",map1.get("dataAuth"));
        }
        data.put("total", this.commonService.countAllSerial(map));
        data.put("rows", this.commonService.listAllSerial(map));

        return data;
    }



    public  Map<String,Object> getAuthMethod(HttpServletRequest request){

        Map<String,Object> map = new HashMap<String,Object>();

        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (a.in_register_user_id=" + ControllerTool.getUserID(request) + " or a.out_register_user_id="
                    + ControllerTool.getUserID(request) + " ) ");
            map.put("flag","true");
        }else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " a.area_id=" + ControllerTool.getCurrentAreaID(request));
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "a.area_id" + sessionInfo.getCurrentAndSubAreaInStr());
            map.put("flag","true");
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " a.area_id" + ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr());
            map.put("flag","true");
        }  else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","a.op_pid like " + ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","a.op_pid like " + ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid());
            map.put("flag","true");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","a.op_pid =" + sessionInfo.getCurrentOrgPid());
            map.put("flag","true");
        } else {
            map.put("flag","false");
        }

        return map;
    }


}
