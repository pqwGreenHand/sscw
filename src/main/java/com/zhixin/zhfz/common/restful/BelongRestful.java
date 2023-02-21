package com.zhixin.zhfz.common.restful;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.spi.resource.Singleton;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.belong.IBelongService;
import com.zhixin.zhfz.bacs.services.belongtemp.IBelongDetailTempService;
import com.zhixin.zhfz.bacs.services.belongtemp.IBelongTempService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.entity.FileServiceConfigEntity;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.ws.rs.POST;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;


@Path("/belongRestful")
@Component
@Scope("request")
@Singleton
public class BelongRestful {

    @InjectParam
    private IBelongDetailTempService belongDetailTempService;
    @InjectParam
    private IBelongTempService belongTempService;
    @InjectParam
    private IBelongService belongService;
    @InjectParam
    private ISerialService serialService;
    @InjectParam
    private IFileConfigService fileConfigService;

    private static final Logger logger = LoggerFactory.getLogger(BelongRestful.class);

    /* *
     * 接收数据，进行存物操作，插入到临时表中
     *
     * */
    @POST
    @Scope("request")
    @Path("/saveBelong")
    @Produces(MediaType.APPLICATION_JSON)
    public MessageEntity saveBelong(@RequestBody String belongJson) {
        logger.info("BelongRestful传入数据：" + belongJson);
        try {
            JSONObject formObject = JSONObject.fromObject(belongJson);
            BelongTempEntity belongTempEntity = new BelongTempEntity();
            belongTempEntity.setXm(formObject.get("xm").toString());
            belongTempEntity.setSfzh(formObject.get("sfzh").toString());
            belongTempEntity.setJsdwId(formObject.get("jsdwId").toString());
            belongTempEntity.setYjdwId(formObject.get("yjdwId").toString());
            belongTempService.saveBelong(belongTempEntity);
            JSONArray belongJsonArr = JSONArray.fromObject(formObject.get("wpList").toString());
            for (int i = 0; i < belongJsonArr.size(); i++) {
                JSONObject jsonObject = JSONObject.fromObject(belongJsonArr.get(i));
                BelongDetailTempEntity belongDetailTempEntity = new BelongDetailTempEntity();
                belongDetailTempEntity.setTempId(belongTempEntity.getId());
                belongDetailTempEntity.setWpUuid(jsonObject.get("wpUuid").toString());
                belongDetailTempEntity.setName(jsonObject.get("name").toString());
                belongDetailTempEntity.setUnit(jsonObject.get("unit").toString());
                belongDetailTempEntity.setDescription(jsonObject.get("description").toString());
                belongDetailTempEntity.setDetailCount(Integer.valueOf(jsonObject.get("detailCount").toString()));
                belongDetailTempService.saveBelongTemp(belongDetailTempEntity);
            }
        } catch (Exception e) {
            logger.info("进行存物操作，插入到临时表中错误！！" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
    }

    /* *
     * 返回图片信息
     *
     * */
    @POST
    @Scope("request")
    @Path("/belongImageList")
    @Produces(MediaType.APPLICATION_JSON)
    public MessageEntity belongImageList(@RequestBody String belongJson) {
        logger.info("belongImageList传入数据：" + belongJson);
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject formObject = JSONObject.fromObject(belongJson);
            String wpUuid = formObject.get("wpUuid").toString();
            Map<String, Object> map = new HashMap<>();
            map.put("wpUuid", wpUuid);
            BelongEntity entity = belongService.getBelongdetByWpUuid(map);
            SerialEntity serialEntity = serialService.queryById(entity.getSerialId());
            String http ="";
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("sysId",serialEntity.getAreaId());
            fileMap.put("pageStart",0);
            fileMap.put("rows",1);
            List<FileServiceConfigEntity> list1 = fileConfigService.list(fileMap);
            if(list1.size()>0){
                http=list1.get(0).getServiceUrl()+"/DownloadFile/";
            }else{
                logger.info("无地址配置查询物品图片信息错误！！");
                return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("查询错误!").addCallbackData("无地址配置查询物品图片信息错误");
            }
            http += serialEntity.getUuid() + "/WP/";
            List<BelongingsPhotoEntity> list = belongService.selectPhotoByBelongingsId(entity.getId());
            for (BelongingsPhotoEntity photoEntity : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("url", http + photoEntity.getUrl());
                jsonObject.put("wpUuid", wpUuid);
                jsonArray.add(jsonObject);
            }

        } catch (Exception e) {
            logger.info("查询物品图片信息错误！！" + e.getMessage());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("查询错误!").addCallbackData(jsonArray);
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("查询正确  !").addCallbackData(jsonArray);
    }
}
