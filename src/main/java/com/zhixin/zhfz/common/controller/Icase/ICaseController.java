package com.zhixin.zhfz.common.controller.Icase;

import com.zhixin.zhfz.bacs.entity.JzAjxxEntity;
import com.zhixin.zhfz.bacs.entity.OrderRequestEntity;
import com.zhixin.zhfz.bacs.services.jzAjxx.IJzAjxxService;
import com.zhixin.zhfz.bacs.services.order.IOrderPersonService;
import com.zhixin.zhfz.bacs.services.order.IOrderRequestService;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.CaseForm;
import com.zhixin.zhfz.common.services.Icase.CasePoliceService;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/common/case")
public class ICaseController {
    private static final Logger logger = LoggerFactory.getLogger(ICaseController.class);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private IUserService userService;
    @Autowired
    private ICaseService caseService;

    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private IOrderRequestService orderRequestService;
    @Autowired
    private IOrderPersonService orderPersonServicee;
    @Autowired
    private CasePoliceService casePoliceService;
    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IJzAjxxService jzAjxxService;

    // 查询历史案件
    @RequestMapping(value = "/listCase")
    @ResponseBody
    public Map<String, Object> listCase(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        System.out.println("+++++++++++++listCase+++++ Start+++++++++++++++");
        Map<String, Object> map = ControllerTool.mapFilter(params);
//        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (c.cjr=" + ControllerTool.getUserID(request) + " or org.op_user_id=" + ControllerTool.getUserID(request)
                    + " or ct.op_user_id=" + ControllerTool.getUserID(request)
                    + " or xu.op_user_id =" + ControllerTool.getUserID(request)
                    + " or xbmln.op_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } /*else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " t.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "t.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        }
        else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "t.area_id " + ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        }*/ else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "( c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or org.p_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ct.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or xu.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or xbmln.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", "( c.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or org.p_id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ct.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or xu.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or xbmln.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", "( c.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or org.p_id = " + sessionInfo.getCurrentOrgPid()
                    + " or ct.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or xu.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or xbmln.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")");
        }
        List<CaseEntity> datas = new ArrayList<CaseEntity>();
        int count = 0;
        try {
            //当zbmjId为-1时代表初始化表格
            if (map.get("zbmjId") == null || map.get("zbmjId").equals("") || !map.get("zbmjId").equals("-1")) {
                datas = caseService.list(map);
                count = caseService.count(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", datas);
        System.out.println("+++++++++++++result+++++ Start+++++++++++++++" + result);
        return result;
    }


    // 添加案件
    @RequestMapping(value = "/addCase")
    @ResponseBody
    public MessageEntity addCase(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        System.out.println("+++++++++++++addCase+++++ Start+++++++++++++++");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        CaseEntity caseEntity = new CaseEntity();
        MessageEntity messageEntity = new MessageEntity();
        try {
            JSONObject orderJson = JSONObject.fromObject(params.get("form"));
            logger.info(orderJson.toString());
            CaseForm caseForm = (CaseForm) JSONObject.toBean(orderJson, CaseForm.class);
            String CaseNo = "AJ" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            caseEntity.setAfsj(df.parse(caseForm.getAfsj()));
            caseEntity.setAreaId(ControllerTool.getCurrentArea(request).getId());
            String casedata = sdf2.format(caseEntity.getAfsj());
            if(caseForm.getAjbh()==null||"null".equals(caseForm.getAjbh())||"".equals(caseForm.getAjbh())){
                caseEntity.setAjbh(CaseNo);
            }
            caseEntity.setAjmc(casedata + " " + caseForm.getAfdd() + " " + caseForm.getAbmc());
            caseEntity.setAjly(caseForm.getAjly());
            caseEntity.setZbmjId(caseForm.getZbmjId());
            caseEntity.setId(caseForm.getId());
            caseEntity.setAjlx(caseForm.getAjlx());
            caseEntity.setAb(caseForm.getAb());
            caseEntity.setZyaq(caseForm.getZyaq());
            caseEntity.setUuid(java.util.UUID.randomUUID().toString());
            caseEntity.setAfdd(caseForm.getAfdd());
            caseEntity.setZbdwId(caseForm.getZbdwId());
            caseEntity.setCreatedTime(new Date());
            caseEntity.setUpdatedTime(caseForm.getCreatedTime());
            caseEntity.setCjr(ControllerTool.getUser(request).getId());
            caseEntity.setZbmjxm(caseForm.getZbmjxm());
            caseEntity.setZbdwmc(caseForm.getZbdwmc());
            caseEntity.setAbmc(caseForm.getAbmc());
            caseService.insertCase(caseEntity);
        } catch (ParseException e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加案件" + caseEntity, ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_XTPZ);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Add failure!");
        }
        this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加案件" + caseEntity, ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_XTPZ);
        messageEntity.addCode(1).addIsError(false).addTitle("消息").addContent("添加成功!");
        return messageEntity;
    }

    // 删除Case
    @RequestMapping(value = "/removeCase")
    @ResponseBody
    public MessageEntity removeCase(@RequestParam("caseId") int caseId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //删除案件
            caseService.deleteCase(caseId);
        } catch (Exception e) {
            logger.error("Error on deleting operlog!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("消息").addContent("删除成功!");
    }

    // 修改Case
    @RequestMapping(value = "/editCase")
    @ResponseBody
    public MessageEntity editCase(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        CaseEntity caseEntity = new CaseEntity();
        MessageEntity messageEntity = new MessageEntity();
        try {
            JSONObject orderJson = JSONObject.fromObject(params.get("form"));
            CaseForm caseForm = (CaseForm) JSONObject.toBean(orderJson, CaseForm.class);
            caseEntity.setId(caseForm.getId());
            caseEntity.setAfsj(df.parse(caseForm.getAfsj()));
            caseEntity.setAjbh(caseForm.getAjbh());
            caseEntity.setAjlx(caseForm.getAjlx());
            caseEntity.setAb(caseForm.getAb());
            caseEntity.setZyaq(caseForm.getZyaq());
            caseEntity.setAfdd(caseForm.getAfdd());
            caseEntity.setZbmjId(caseForm.getZbmjId());
            caseEntity.setZbmjxm(caseForm.getZbmjxm());
            caseEntity.setZbdwmc(caseForm.getZbdwmc());
            caseEntity.setAbmc(caseForm.getAbmc());
            caseEntity.setZbdwId(caseForm.getZbdwId());
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            String casedata = sdf2.format(caseEntity.getAfsj());
            caseEntity.setAjmc(casedata + " " + caseForm.getAfdd() + " " + caseForm.getAbmc());
            caseEntity.setUpdatedTime(new Date());
            caseEntity.setCjr(ControllerTool.getUser(request).getId());
            caseService.updateCase(caseEntity);

        } catch (ParseException e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改案件" + caseEntity, ControllerTool.getUser(request).getLoginName(), false, OperLogEntity.SYSTEM_XTPZ);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("Add failure!");
        }
        this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改案件" + caseEntity, ControllerTool.getUser(request).getLoginName(), true, OperLogEntity.SYSTEM_XTPZ);
        messageEntity.addCode(1).addIsError(false).addTitle("消息").addContent("修改成功!");
        return messageEntity;
    }

    /**
     * 民警信息列表
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/listPolice")
    @ResponseBody
    public Map<String, Object> listPolice(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Collection<UserEntity> users = new ArrayList<UserEntity>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
//        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
//            // 办案人员-本人
//            map.put("dataAuth", " ( u.op_user_id=" + ControllerTool.getUserID(request)
//                    + " or u.id=" + ControllerTool.getUserID(request)
//                    + " or r.op_user_id=" + ControllerTool.getUserID(request)
//                    + " or org.op_user_id=" + ControllerTool.getUserID(request)
//                    + " or xc.op_user_id=" + ControllerTool.getUserID(request)
//                    + " or ia.op_user_id=" + ControllerTool.getUserID(request)
//                    + " ) ");
//        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 办案场所-本办案场所
//            map.put("dataAuth", " ia.id=" + ControllerTool.getCurrentAreaID(request));
//        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 管理员 -本办案场所及下级办案场所
//            map.put("dataAuth", " ia.id  " + sessionInfo.getCurrentAndSubAreaInStr());
//        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 上级办案场所及其下级办案场所
//            map.put("dataAuth", " ia.id  " + sessionInfo.getSuperAndSubAreaInStr());
//        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
//            // 公安领导-本部门及下级部门
//            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
//                    + " or r.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
//                    + " or org.p_id like " +  sessionInfo.getCurrentAndSubOrgPid()
//                    + " or org.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
//                    + " or xc.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
//                    + " or ia.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
//
//                    + " ) ");
//        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
//            // 法制人员-上级部门及下级部门
//            map.put("dataAuth"," ( u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
//                    + " or r.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
//                    + " or org.p_id like " +  sessionInfo.getSuperAndSubOrgPid()
//                    + " or org.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
//                    + " or xc.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
//                    + " or ia.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
//                    + " ) ");
//        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
//            // 本部门
//            map.put("dataAuth"," ( u.op_pid = " + sessionInfo.getCurrentOrgPid()
//                    + " or r.op_pid = " +  sessionInfo.getCurrentOrgPid()
//                    + " or org.p_id = " +  sessionInfo.getCurrentOrgPid()
//                    + " or org.op_pid = " +  sessionInfo.getCurrentOrgPid()
//                    + " or xc.op_pid = " +  sessionInfo.getCurrentOrgPid()
//                    + " or ia.op_pid = " +  sessionInfo.getCurrentOrgPid()
//                    + " ) ");
//        }
        //map.put("dataAuth","u.type is not null");
        users = this.userService.getUserInfonoJurisdiction(map);
        int count = this.userService.noJurisdictionCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", users);
        return result;
    }


    /**
     * 协办民警信息列表
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/listXbPolice")
    @ResponseBody
    public List<UserEntity> listXbPolice(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        List<UserEntity> users = new ArrayList<UserEntity>();
        String xbmjS = (String) param.get("xbmjIds");
        if (xbmjS != null && xbmjS != "") {
            xbmjS = xbmjS.substring(1, xbmjS.length() - 1);
            param.put("dataAuth", "u.type is not null and u.id in (" + xbmjS + ")");
        } else {
            param.put("dataAuth", "u.type is not null  and u.id in (-1)");
        }
        users = this.userService.getXbUserInfo(param);
        return users;
    }

    // 查询警综案件信息
    @RequestMapping(value = "/listJzAjxx")
    @ResponseBody
    public Map<String, Object> listJzAjxx(@RequestParam Map<String, Object> params, HttpServletRequest request)
            throws Exception {
        System.out.println("+++++++++++++listJzAjxx警综案件信息+++++ Start+++++++++++++++");
        Map<String, Object> map = ControllerTool.mapFilter(params);
        List<JzAjxxEntity> datas = new ArrayList<JzAjxxEntity>();
        int count = 0;
        datas = jzAjxxService.list(map);
        count = jzAjxxService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", datas);
        return result;
    }


    // 查询警综案件关联人员信息
    @RequestMapping(value = "/listRy")
    @ResponseBody
    public Map<String, Object> listRy(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        System.out.println("+++++++++++++listRy警综人员信息+++++ Start+++++++++++++++");

        Map<String, Object> map = ControllerTool.mapFilter(params);
        List<Map<String, Object>> list = new ArrayList<>();
        list = jzAjxxService.listRyByAjbh(map);
        int count = 0;
        count = jzAjxxService.countListRyByAjbh(map);

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 通过证件号码得到人员编号
     *
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryRybhByZjhm")
    @ResponseBody
    public List<Map<String, Object>> queryRybhByZjhm(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        System.out.println("+++++++++++++queryRybhByZjhm警综人员信息+++++ Start+++++++++++++++");
        Map<String, Object> map = ControllerTool.mapFilter(params);
        List<Map<String, Object>> list = new ArrayList<>();
        list = jzAjxxService.queryRybhByZjhm(map);
        return list;
    }

}
