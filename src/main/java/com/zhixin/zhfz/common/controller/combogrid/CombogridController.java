package com.zhixin.zhfz.common.controller.combogrid;

import com.zhixin.zhfz.common.controller.combobox.ComboboxController;
import com.zhixin.zhfz.common.entity.CombogridEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.combogrid.ICombogridService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/common/combogrid")
public class CombogridController {

    private static final Logger logger = LoggerFactory.getLogger(ComboboxController.class);

    @Autowired
    private ICombogridService combogridService;

    // 获取所有嫌疑人入区编号、姓名、身份证号码
    @RequestMapping(value = "/getPersonBelong")
    @ResponseBody
    public List<CombogridEntity> getPersonBelong(@RequestParam Map<String, Object> params,
                                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------getSuspectSerialNo-----------------");
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            params.put("dataAuth", " ( ints.send_user_id=" + ControllerTool.getUserID(request)
                    + " or ints.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or ints.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or odr.order_user_id=" + ControllerTool.getUserID(request)
                    + " or odr.noter_id=" + ControllerTool.getUserID(request)
                    + " or cs.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or cs.cjr=" + ControllerTool.getUserID(request)
                    + " or cs.xbmj_ids like '%," + ControllerTool.getUserID(request) +",%'"
                    + " or bpe.police_id=" + ControllerTool.getUserID(request)
                    + " or bpe.op_user_id=" + ControllerTool.getUserID(request)
                    + " or bu.id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " b.area_id = " + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            params.put("dataAuth", " b.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " ia.id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth"," ( ints.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or odr.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or cs.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or bpe.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门
            params.put("dataAuth"," ( ints.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or odr.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or cs.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or bpe.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            params.put("dataAuth"," ( ints.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or odr.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or cs.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or bpe.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
//        List<CombogridEntity> list = this.combogridService.getSuspectSerialNo(params);
        List<CombogridEntity> list = this.combogridService.getPersonBelong(params);
        return list;
    }


    // 获取所有嫌疑人入区编号、姓名、身份证号码
    @RequestMapping(value = "/getSuspectSerialNo")
    @ResponseBody
    public List<CombogridEntity> getSuspectSerialNo(@RequestParam Map<String, Object> params,
                                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------getSuspectSerialNo-----------------");
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        boolean flag = true;
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            params.put("dataAuth", " ( ints.send_user_id=" + ControllerTool.getUserID(request)
                    + " or ints.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or ints.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or odr.order_user_id=" + ControllerTool.getUserID(request)
                    + " or odr.noter_id=" + ControllerTool.getUserID(request)
                    + " or cs.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or cs.cjr=" + ControllerTool.getUserID(request)
                    + " or cs.xbmj_ids like '%," + ControllerTool.getUserID(request) +",%'"
                    + " or bpe.police_id=" + ControllerTool.getUserID(request)
                    + " or bpe.op_user_id=" + ControllerTool.getUserID(request)
                    + " or bu.id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " ia.id = " + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            params.put("dataAuth", " ia.id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " ia.id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth"," ( ints.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or odr.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or cs.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or bpe.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门
            params.put("dataAuth"," ( ints.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or odr.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or cs.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or bpe.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            params.put("dataAuth"," ( ints.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or odr.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or cs.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or bpe.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        String type = null;
        try {
            type = params.get("type").toString();
        } catch (Exception e) {
        }
        if (type != null && !"".equals(type)) {
            if ("belongin".equalsIgnoreCase(type)) {// 存物
                params.put("type", " ints.case_id is not null and (ints.status > 0 and ints.status < 11 ) ");// 已安检且未出区and ints.status <>3
            } else if ("belongout".equalsIgnoreCase(type)) {// 取物
                params.put("type", " ints.case_id is not null and ints.status > 0 ");// 已安检 and ints.status <>3
            } else if ("exhibitin".equalsIgnoreCase(type)) {// 存物
                params.put("type", " ints.case_id is not null and (ints.status > 0 and ints.status < 11 ) ");// 已安检且未出区and ints.status <>3
            } else if ("exhibitout".equalsIgnoreCase(type)) {// 取物
                params.put("type", " ints.case_id is not null and ints.status > 0 ");// 已安检and ints.status <>3
            } else if ("infocollection".equalsIgnoreCase(type)) {// 信息采集
                params.put("type", " ints.case_id is not null and (ints.status > 0 and ints.status < 11) ");// 已安检且未出区
            } else if ("combineinfo".equalsIgnoreCase(type)) {// 情报合成作战请求
                params.put("type", " ints.case_id is not null and (ints.status > 0 and ints.status < 11) ");// 已安检且未出区
            } else if ("security".equalsIgnoreCase(type)) {// 安检不用有案件编号
                params.put("type",
                        " (ints.status = 0 or (ints.status =9 and (ints.id not in (SELECT serial_id from ba_security )))) ");// 已入区未安检，如果入区后直接临时出区，那么返回时也可以进行安检
            } else if ("record".equalsIgnoreCase(type)) {// 已安检未出区
                params.put("type", " ( ints.case_id is not null and (ints.status > 0 and ints.status < 11 "
                        + "and ints.id not in (select r.serial_id from ba_record r where r.`status`=1 or r.`status`=2) "
                        + "and ints.id not in (select serial_id from ba_waiting_record where `status` = 0))) ");//
            } else {// 出区
                params.put("type", " ints.status < 11 ");// 未出区
            }
        } else {// 出区，安检
            params.put("type", " ints.status < 11 ");// 未出区
        }
        if (flag) {
            if ("addrecord".equalsIgnoreCase(type)) // 开始审讯获取嫌疑人
            {
                if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
                    params.put("dataAuth", " ped.police_id=" + ControllerTool.getUserID(request));
                }
                list = this.combogridService.getSuspectSerialNoForRecord(params);
            } else {
                list = this.combogridService.getSuspectSerialNo(params);
            }
            if (list.size() <= 0) {
                if ("record".equalsIgnoreCase(type) || "addrecord".equalsIgnoreCase(type)) {
                    // 查询其他人入区
                    List<CombogridEntity> otherSerialNo = this.combogridService.getRecordOtherSerialNo(params);
                    if (otherSerialNo != null && otherSerialNo.size() > 0) {
                        if (list != null) {
                            list.addAll(otherSerialNo);
                        } else {
                            list = otherSerialNo;
                        }
                    }
                }
            }
        }
        return list;
    }


    /**
     * 获取所有在区民警
     *
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPoliceSerialNo")
    @ResponseBody
    public List<CombogridEntity> getPoliceSerialNo(@RequestParam Map<String, Object> params,
                                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-----------------getPoliceSerialNo-----------------");
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            params.put("dataAuth", " ( pd.police_id=" + ControllerTool.getUserID(request)
                    + " or pd.op_user_id=" + ControllerTool.getUserID(request)
                    + " or u.id=" + ControllerTool.getUserID(request)
                    + " or u.op_user_id=" + ControllerTool.getUserID(request)
                    + " or c.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or c.cjr=" + ControllerTool.getUserID(request)
                    + " or c.xbmj_ids like '%," + ControllerTool.getUserID(request) +",%'"
                    + " or f.user_id=" + ControllerTool.getUserID(request)
                    + " or f.op_user_id=" + ControllerTool.getUserID(request)
                    + " or bs.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or bs.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or bs.send_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " pd.area_id = " + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            params.put("dataAuth", " pd.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " pd.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth"," ( pd.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or u.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or f.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or bs.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门
            params.put("dataAuth"," ( pd.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or u.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or c.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or f.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or bs.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            params.put("dataAuth"," ( pd.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or u.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or c.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or f.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or bs.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        list = combogridService.getPoliceSerialNo(params);
        return list;
    }

    @RequestMapping(value = "/getOrderContentForEntrance")
    @ResponseBody
    public List<CombogridEntity> getOrderContentForEntrance(@RequestParam Map<String, Object> params,
                                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            params.put("dataAuth", " ( o.op_user_id=" + ControllerTool.getUserID(request)
                    + " or t.order_user_id=" + ControllerTool.getUserID(request)
                    + " or t.noter_id=" + ControllerTool.getUserID(request)
                    + " or p.op_user_id=" + ControllerTool.getUserID(request)
                    + " or p.op_user_id=" + ControllerTool.getUserID(request)
                    + " or bc.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or bc.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or bc.cjr=" + ControllerTool.getUserID(request)
                    + " or bc.xbmj_ids like '," + ControllerTool.getUserID(request)+",'"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            params.put("dataAuth", " t.area_id = " + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            params.put("dataAuth", " t.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            params.put("dataAuth", " t.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth"," ( o.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or t.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or p.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or bc.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or bc.zbmj_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or bc.zbdw_id like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 上级部门及下级部门
            params.put("dataAuth"," ( o.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or t.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or p.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or bc.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or bc.zbmj_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or bc.zbdw_id like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            params.put("dataAuth"," ( o.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or t.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or p.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or bc.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or bc.zbmj_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or bc.zbdw_id = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        list = combogridService.getOrderContentForEntrance(params);
        System.err.println("list.size()=" + list.size());
        return list;
    }

    // 获取预约人信息
    @RequestMapping(value = "/getAllOrderInfo")
    @ResponseBody
    public List<CombogridEntity> getAllOrderInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        logger.info("-----------------getAllOrderInfo-----------------");
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        List<CombogridEntity> listValue = new ArrayList<CombogridEntity>();
        boolean flag = true;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 48);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (flag) {
            CombogridEntity tem = new CombogridEntity();
            tem.setId(-1);
            tem.setOrderNo("未预约");
            tem.setName("无");
            tem.setCertificateNo("无");
            listValue.add(tem);
            list = this.combogridService.getAllOrderInfo(params);
            Date beforeTime = df.parse(df.format(calendar.getTime()));
            for (int i = 0; i < list.size(); i++) {
                CombogridEntity obj = list.get(i);
                if (obj.getTimenow().after(beforeTime)) {
                    listValue.add(obj);
                }
            }
        }
        return listValue;
    }

    // 获取所有预信息
    @RequestMapping(value = "/getAllOrder")
    @ResponseBody
    public List<CombogridEntity> getAllOrder(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        logger.info("-----------------getAllOrder-----------------");
        List<CombogridEntity> list = new ArrayList<CombogridEntity>();
        List<CombogridEntity> listValue = new ArrayList<CombogridEntity>();
        boolean flag = true;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 48);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (flag) {
            CombogridEntity tem = new CombogridEntity();
            tem.setId(-1);
            tem.setOrderNo("未预约");
            tem.setName("无");
            tem.setCertificateNo("无");
            listValue.add(tem);
            list = this.combogridService.getAllOrderInfo(params);
            Date beforeTime = df.parse(df.format(calendar.getTime()));
            for (int i = 0; i < list.size(); i++) {
                CombogridEntity obj = list.get(i);
                if (obj.getTimenow().after(beforeTime)) {
                    listValue.add(obj);
                }
            }
        }
        return listValue;
    }
}
