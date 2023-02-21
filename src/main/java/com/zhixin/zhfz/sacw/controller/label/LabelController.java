package com.zhixin.zhfz.sacw.controller.label;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.LabelEntity;
import com.zhixin.zhfz.sacw.form.LabelForm;
import com.zhixin.zhfz.sacw.services.label.ILabelService;
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

/**
 * 涉案标签
 */
@Controller
@RequestMapping("/zhfz/sacw/label")
public class LabelController {

    private static final Logger logger = LoggerFactory.getLogger(LabelController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ILabelService service;

    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案人员-本人
            map.put("dataAuth", " l.op_user_id = " + ControllerTool.getUserID(request) );
        }else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " l.warehouse_id=" + ControllerTool.getCurrentWarehouseID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本办案场所及下级办案场所
            map.put("dataAuth", " l.warehouse_id " + sessionInfo.getCurrentAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " l.warehouse_id " + sessionInfo.getSuperAndSubWarehouseInStr());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 本部门
            map.put("dataAuth", "l.op_pid like " + sessionInfo.getCurrentOrgPid());
        } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 全部
            map.put("dataAuth", "");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {
            // 法制人员-上级部门及其下级部门
            map.put("dataAuth", "l.op_pid like  " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForSacwConf(request))) {

            // 公安领导-本部门及下级部门
            map.put("dataAuth", "l.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else {
            flag = false;
            // return null
        }
        List<LabelEntity> list = service.list(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.service.count(map));
        result.put("rows", list);
        return result;
    }

    /**
     * 插入涉案标签
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public MessageEntity add(@RequestBody LabelForm form, HttpServletRequest request) {
        LabelEntity entity = new LabelEntity();
        entity.setLabelNo(form.getLabelNo());
        entity.setIcNo(form.getIcNo());
        entity.setStatus(form.getStatus());
        entity.setType(form.getType());
        entity.setWarehouseId(form.getWarehouseId());
        entity.setWarehouseName(form.getWarehouseName());
        entity.setOp_pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_user_id(ControllerTool.getSessionInfo(request).getUser().getId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("labelNo", form.getLabelNo());
        map.put("IcNo", form.getIcNo());
        LabelEntity	entity1 = null;
        LabelEntity	entity2 = null;
        try {
            entity1 = service.getLabelByNo(map);
            entity2 = service.getLabelByIcNo(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(entity1!=null||entity2!=null){

            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("数据重复!");

        }
        try {
            service.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加标签信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加标签信息:" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加标签失败!数据有问题");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加标签成功!");
    }

    /**
     * 根据id删除
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public MessageEntity delete(@RequestBody IDForm form, HttpServletRequest request) {
        try {
            service.delete(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除标签信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除标签信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除标签失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除标签成功!");
    }

    /**
     * 根据id更新
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public MessageEntity update(@RequestBody LabelForm form, HttpServletRequest request) {
        LabelEntity entity = new LabelEntity();
        try {
            entity.setId(form.getId());
            entity.setLabelNo(form.getLabelNo());
            entity.setIcNo(form.getIcNo());
            entity.setType(form.getType());
            entity.setWarehouseName(form.getWarehouseName());
            entity.setStatus(form.getStatus());
            entity.setWarehouseId(form.getWarehouseId());
            service.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "更新标签信息:" + entity, ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "更新标签信息:" + entity, ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("更新标签失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新标签成功!");
    }

    @RequestMapping(value = "/getLabelByNo")
    @ResponseBody
    public MessageEntity getLabelByNo(@RequestBody LabelForm form, HttpServletRequest request) throws Exception {
        logger.info("++++++++++++++++++++++++++getLabelByNo++++++++++++++++++++++++++++++form= " + form);
        LabelEntity entity = new LabelEntity();
        try {
            String cuffNo = form.getLabelNo();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("labelNo", cuffNo);
            boolean flag = true;
           /* if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 办案人员
                map.put("dataAuth", "l.warehouse_id=" + ControllerTool.getCurrentAreaID(request));
            } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request)) || RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 办案场所
                map.put("dataAuth", "  l.warehouse_id=" + ControllerTool.getCurrentAreaID(request));
            } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request)) || RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 公安领导与管理员
                map.put("dataAuth", "l.warehouse_id " + ControllerTool.getSessionInfo(request).getCurrentAndSubAreaInStr());
            } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
                // 法制人员-上级部门及下级部门
                String area = ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr();
                map.put("dataAuth", "l.warehouse_id " + area);
			*//*}else if (RoleEntity.DATA_AUTH_UP_Police == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门，能看到上级部门民警及下属单位所有民警的数据
            String sql = ControllerTool.getSessionInfo(request).getaLLPoliceByParentOrg();
            map.put("dataAuth", "r.order_user_id1 " + sql + " or n.order_user_id2 " + sql);*//*
            } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
                flag = true;
            } else {
                flag = false;
            }*/
            if (flag) {
                entity = service.getLabelByNo(map);
            }
            logger.info("+++++++++++++++++++++++++getLabelByNo++++++++++++++++entity= " + entity);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请求失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("请求成功!")
                .addCallbackData(entity);
    }

    @RequestMapping(value = "/getLabelByIcNo")
    @ResponseBody
    public MessageEntity getLabelByIcNo(@RequestBody LabelForm form, HttpServletRequest request) throws Exception {
        logger.info("++++++++++++++++++++++++++getLabelByNo++++++++++++++++++++++++++++++form= " + form);
        LabelEntity entity = new LabelEntity();
        try {
            String cuffNo = form.getIcNo();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("IcNo", cuffNo);
            boolean flag = true;
           /* if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 办案人员
                map.put("dataAuth", "l.warehouse_id=" + ControllerTool.getCurrentAreaID(request));
            } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForINV(request)) || RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 办案场所
                map.put("dataAuth", "  l.warehouse_id=" + ControllerTool.getCurrentAreaID(request));
            } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request)) || RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForINV(request))) {
                // 公安领导与管理员
                map.put("dataAuth", "l.warehouse_id " + ControllerTool.getSessionInfo(request).getCurrentAndSubAreaInStr());
            } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
                // 法制人员-上级部门及下级部门
                String area = ControllerTool.getSessionInfo(request).getSuperAndSubAreaInStr();
                map.put("dataAuth", "l.warehouse_id " + area);
			*//*}else if (RoleEntity.DATA_AUTH_UP_Police == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门，能看到上级部门民警及下属单位所有民警的数据
            String sql = ControllerTool.getSessionInfo(request).getaLLPoliceByParentOrg();
            map.put("dataAuth", "r.order_user_id1 " + sql + " or n.order_user_id2 " + sql);*//*
            } else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForINV(request))) {
                flag = true;
            } else {
                flag = false;
            }*/
            if (flag) {
                entity = service.getLabelByIcNo(map);
            }
            logger.info("+++++++++++++++++++++++++getLabelByNo++++++++++++++++entity= " + entity);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("请求失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("请求成功!")
                .addCallbackData(entity);
    }


    @RequestMapping(value = "/refrshCuff")
    @ResponseBody
    public MessageEntity refrshCuff() {
        Map<String, String> cuffMap = new HashMap<>();
        try {
            List<LabelEntity> cuffs = service.listAll();
            if (cuffs != null) {
                for (LabelEntity cuff : cuffs) {
                    cuffMap.put(cuff.getIcNo(), cuff.getLabelNo());
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("刷新标签对应关系失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addCallbackData(cuffMap).addTitle("提示").addContent("刷新标签对应关系成功!");
    }

}
