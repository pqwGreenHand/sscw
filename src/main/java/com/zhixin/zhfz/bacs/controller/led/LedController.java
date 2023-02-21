package com.zhixin.zhfz.bacs.controller.led;

import javax.servlet.http.HttpServletRequest;

import com.zhixin.zhfz.bacs.entity.LED2010Entity;
import com.zhixin.zhfz.common.common.LedOldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.bacs.entity.LedEntity;
import com.zhixin.zhfz.bacs.services.led.ILedService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;

import java.util.List;

@Controller
@RequestMapping("/zhfz/bacs/led")
public class LedController {
    private static final Logger logger = LoggerFactory.getLogger(LedController.class);

    @Autowired
    private ILedService ledService;

    @RequestMapping(value = "/LedByRoomId")
    @ResponseBody
    public LedEntity LedByRoomId(int roomId, HttpServletRequest request) {
        LedEntity entity = new LedEntity();
        try {
            logger.info("roomId===" + roomId);
            entity = ledService.LedByRoomId(roomId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 保存led
     *
     * @param entity
     * @param request
     * @return
     */
    @RequestMapping("/saveLed")
    @ResponseBody
    public MessageEntity saveLed(@RequestBody LedEntity from, HttpServletRequest request) {
        logger.info("++++++++saveLed++++++" + from);
        try {
            if (from.getId() > 0) {
                ledService.update(from);
            } else {
                from.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                from.setOpUserId(ControllerTool.getUserID(request) + "");
                ledService.insert(from);
            }
            if (from.getLednr() != null && !"".equals(from.getLednr())) {
                try {
                    LED2010Entity led = new LED2010Entity();
                    led.setIp(from.getIp());
                    led.setPort(from.getPort());
                    led.setColor("red");
                    led.setContent(from.getLednr());
                    if (from.getLednr().indexOf("未成年讯询问") > -1) {
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
                    logger.info("++++++++led success 设置成功++++++" + from);

//					LedUtil.ledPlay(from.getLednr(), from.getIp(), from.getPort(), 0);
                } catch (Exception e) {
                    logger.info("++++++++led error 设置有问题++++++" + from);
                    logger.info("异常信息----" + e);
                }
            } else {
                LedEntity entity = ledService.LedByRoomId(from.getRoomId());
                try {

                    LED2010Entity led = new LED2010Entity();
                    led.setIp(from.getIp());
                    led.setPort(from.getPort());
                    led.setColor("red");
                    led.setContent(entity.getRoomName());
                    if (from.getLednr().indexOf("未成年讯询问") > -1) {
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
                    logger.info("++++++++led success 设置成功++++++" + from);

//					LedUtil.ledPlay(from.getLednr(), from.getIp(), from.getPort(), 0);
                } catch (Exception e) {
                    logger.info("++++++++led error 设置有问题++++++" + from);
                    logger.info("异常信息----" + e);
                }
            }

        } catch (Exception e) {
            logger.info("Error on editing saveLed!", e);

            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }

    /**
     * 一键设置led
     *
     * @param request
     * @return
     */
    @RequestMapping("/saveLedAll")
    @ResponseBody
    public MessageEntity saveLedAll(HttpServletRequest request) {
        try {
            List<LedEntity> list = ledService.LedByRoom();
            for (int i = 0; i < list.size(); i++) {

                try {

                    LedEntity led = new LedEntity();
                    led.setIp(list.get(i).getIp());
                    led.setPort(list.get(i).getPort());
                    led.setColor("red");
                    led.setContent(list.get(i).getRoomName());
                    if (list.get(i).getRoomName().indexOf("未成年讯询问") > -1) {
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
                    logger.info("++++++++led success 设置成功++++++");

                } catch (Exception e) {
                    logger.info("++++++++led error 设置有问题++++++");
                    logger.info("异常信息----" + e);
                }

//					LedUtil.ledPlay(list.get(i).getRoomName(), list.get(i).getIp(), list.get(i).getPort(), 0);
            }

        } catch (Exception e) {
            logger.info("Error on editing saveLed!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }
}
