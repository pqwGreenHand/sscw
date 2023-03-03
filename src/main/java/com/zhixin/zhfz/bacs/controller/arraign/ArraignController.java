package com.zhixin.zhfz.bacs.controller.arraign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhixin.zhfz.bacs.entity.LedEntity;
import com.zhixin.zhfz.bacs.services.led.ILedService;
import com.zhixin.zhfz.common.common.LedOldUtil;
import com.zhixin.zhfz.common.common.led.LedUtil;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.bacs.entity.ArraignEntity;
import com.zhixin.zhfz.bacs.entity.RoomAssignEntity;
import com.zhixin.zhfz.bacs.form.ArraignForm;
import com.zhixin.zhfz.bacs.services.arraign.IArraignService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;


@Controller
@RequestMapping("/zhfz/bacs/arraign")
public class ArraignController {
    private static Logger logger = LoggerFactory.getLogger(ArraignController.class);

    @Autowired
    private IArraignService service;
    @Autowired
    private ILedService ledService;

    @Autowired
    private IOperLogService operLogService;
    @RequestMapping("/all")
    @ResponseBody
    public List<ArraignEntity> all(@RequestParam Map<String,Object> params, HttpServletRequest request){

        Map<String, Object> map = ControllerTool.mapFilter(params);
        return service.all(map);
    }

    @RequestMapping("/listAllRecordRoom")
    @ResponseBody
    public List<ArraignEntity> listAllRecordRoom(@RequestParam Map<String,Object> params, HttpServletRequest request){
        Map<String, Object> map = ControllerTool.mapFilter(params);
        return service.listAllRecordRoom(map);
    }


    @RequestMapping("/arraign")
    @ResponseBody
    public MessageEntity arraign(ArraignForm form, HttpServletRequest request){
        logger.info("======arraign========");
        RoomAssignEntity entity;
        try {
            entity = service.arraign(form);
            entity.setId(service.getMaxId());
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "候问室提讯", ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "候问室提讯", ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("通知").addContent("提讯失败!");
        }
        return MessageEntity.success("提讯成功!").addCallbackData(entity);
    }

    @RequestMapping("/clear")
    @ResponseBody
    public MessageEntity clear(Long roomId,Integer serialId, HttpServletRequest request){
        logger.info("======clear========");
        try {
            this.operLogService.insertLog(OperLogEntity.UPLOAD_TYPE, "清理提讯", ControllerTool.getLoginName(request), true,"办案场所");
            return service.clear(roomId,serialId,request);
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.UPLOAD_TYPE, "清理提讯", ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("通知").addContent("清理失败!");
        }
    }

    /**
     * 功能室查询
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryRoomInfo")
    @ResponseBody
    public Map<String, Object> queryRoomInfo(@RequestParam Map<String, Object> map) {

        List<ArraignEntity> list = service.queryRoomInfo(map);
        logger.info("queryRoomInfo size++++++++++++++++++++" + list.size());

        if (list != null && list.size() > 0) {
            Map<String, Object> param = new HashMap<>();
            List<ArraignEntity> listPerson = service.queryRoomByPerson(param);

            for (ArraignEntity recordEntity : list) {
                if (listPerson != null && listPerson.size() > 0) {
                    for (ArraignEntity recordPerson : listPerson) {
                        if (recordPerson.getId() == recordEntity.getId()) {
                            recordEntity.setCertificateNo(recordPerson.getCertificateNo());
                            recordEntity.setPersonName(recordPerson.getPersonName());
                        }
                    }

                }
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        List<List<ArraignEntity>> resultList = new ArrayList<List<ArraignEntity>>();
        if (list != null) {
            resultList = handleList(list);
        }
        result.put("list", resultList);
        return result;
    }


    /**
     * 前台展示
     *
     * @param list
     * @return
     */
    private List<List<ArraignEntity>> handleList(List<ArraignEntity> list) {

        List<ArraignEntity> tempList = new ArrayList<ArraignEntity>();
        List<List<ArraignEntity>> countlist = new ArrayList<List<ArraignEntity>>();
        int prevgroupid = -1;
        for (int i = 0; i < list.size(); i++) {
            ArraignEntity e = list.get(i);
            if (prevgroupid == e.getAreaId()) {
                tempList.add(e);
            } else {
                tempList = new ArrayList<ArraignEntity>();
                tempList.add(e);
                countlist.add(tempList);
                logger.info("++++++++前台展示" + i + "+++++++++++++" + tempList.toString());

            }
        }
        return countlist;
    }


    /**
     * 功能室置空
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateRoomInfo")
    @ResponseBody
    public MessageEntity updateRoomInfo(@RequestBody Map<String,Object> param,HttpServletRequest request){
        Integer status = 0;
        try {
            Map<String, Object> map = ControllerTool.mapFilter(param);
            //置空房间前查询是否是在审讯中,如果是在审讯中则不予以置空
            List<ArraignEntity> list = service.listAllRecordRoom1(map);
            for (ArraignEntity entity : list) {
                status = entity.getRoomStatus();
                if (status == 2) {
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("正在审讯中,请终止审讯!");
                } else {
                    // 修改led
                    LedEntity led = ledService.LedByRoomId(Integer.parseInt(map.get("roomId").toString()));
                    led.setIp(led.getIp());
                    led.setPort(led.getPort());
                    led.setColor("red");
                    led.setContent(led.getRoomName());
                    if (led.getContent().indexOf("未成年讯询问") > -1) {
                        led.setFontSize(12);
                        led.setLeft(4);
                        led.setTop(6);
                    } else {
                        led.setFontSize(17);
                        led.setLeft(8);
                        led.setTop(2);
                    }
                    led.setWidth(200);
                    led.setHeight(24);
                    LedOldUtil.controlLED2010(led);

//                    LedUtil.ledPlay(led.getRoomName(), led.getIp(), led.getPort(), 0);
                    service.updateRoomInfo(map);
                }
            }
        }catch (Exception e){
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("功能室更新失败!");
        }
        return new MessageEntity().addCode(0).addIsError(true).addTitle("通知").addContent("功能室更新成功!");
    }
}
