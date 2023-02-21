package com.zhixin.zhfz.sacw.controller.location;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.LocationEntity;
import com.zhixin.zhfz.sacw.form.LocationForm;
import com.zhixin.zhfz.sacw.services.location.ILocationService;
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
import java.util.*;

@Controller
@RequestMapping("/zhfz/sacw/location")
public class LocationController {

    private static Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private ILocationService locationService;

    @Autowired
    private IOperLogService operLogService;

    /**
     * 查询某个仓库包含的区域
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listlocationbywarehouse")
    @ResponseBody
    public Map<String, Object> listLocationByWarehouse(@RequestParam Map<String, Object> params,
                                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<LocationEntity> listLocation = new ArrayList<LocationEntity>();
        int count = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        //if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
        //map.put("usePage", true);
        listLocation = locationService.listLocationByWarehouse(map);
        count = locationService.count(map);

        //}
        logger.info("++++++++listLocationByWarehouse++++++++" + listLocation);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", listLocation);
        return result;
    }

    /**
     * 插入区域信息
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/addlocation")
    @ResponseBody
    public MessageEntity addLocation(@RequestBody LocationForm form, HttpServletRequest request) {
        System.out.println("++++++++addLocation++++++UserForm=" + form);
        if(isTheOnlyCheck(form) == 1){
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加区域已存在!");
        }else{
            LocationEntity entity = new LocationEntity();
            entity.setId(form.getId());
            entity.setName(form.getName());
            entity.setWareHouseId(form.getWareHouseId());
            entity.setInvolvedTypeId(form.getInvolvedTypeId());
            entity.setDescription(form.getDescription());
            entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setCreatedTime(new Date());
            try {
                locationService.insertLocation(entity);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加区域信息" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
            } catch (Exception e) {
                logger.error("Error on adding user!", e);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加区域信息" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
                return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加区域失败!");
            }
        }

        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加区域成功!");
    }

    /**
     * 根据id删除区域信息
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/removelocation")
    @ResponseBody
    public MessageEntity removeLocation(@RequestBody IDForm form, HttpServletRequest request) {
        System.out.println("++++++++removelocation++++++id=  " + form.getId());
        try {
            locationService.deleteLocation(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除区域信息" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("Error on deleting user!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除区域信息" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除区域失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除区域成功!");
    }

    /**
     * 修改区域信息
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/editLocation")
    @ResponseBody
    public MessageEntity editLocation(@RequestBody LocationForm form, HttpServletRequest request) throws Exception {
        // System.out.println("++++++++edit++++++UserForm=" + form);
        LocationEntity entity = new LocationEntity();
        entity.setId(form.getId());
        entity.setName(form.getName());
        entity.setInvolvedTypeId(form.getInvolvedTypeId());
        entity.setDescription(form.getDescription());
        entity.setUpdatedTime(new Date());
        try {
            locationService.updateLocation(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改区域信息" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            logger.error("Error on editing user!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改区域信息" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改区域失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改区域成功!");
    }

    /**
     * 唯一校验
     * @param form
     * @return
     * @throws Exception
     */
    private int isTheOnlyCheck(LocationForm form){
        LocationEntity entity = new LocationEntity();
        entity.setWareHouseId(form.getWareHouseId());
        entity.setName(form.getName());
        return locationService.qureyEntityByPram(entity);
    }

}
