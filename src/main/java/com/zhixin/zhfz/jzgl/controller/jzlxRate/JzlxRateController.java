package com.zhixin.zhfz.jzgl.controller.jzlxRate;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhixin.zhfz.common.entity.SessionInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.CzrzEntity;
import com.zhixin.zhfz.jzgl.entity.GmxxEntity;
import com.zhixin.zhfz.jzgl.entity.JzlxRateEntity;
import com.zhixin.zhfz.jzgl.entity.ZxgjEntity;
import com.zhixin.zhfz.jzgl.services.jzlxRate.IJzlxRateService;
import com.zhixin.zhfz.jzgl.services.jzxx.ICzrzService;

@Controller
@RequestMapping(value = "/zhfz/jzgl/jzlxRate")
public class JzlxRateController {
	
	private static Logger logger = Logger.getLogger(JzlxRateController.class);
	
    @Autowired
    private IJzlxRateService jzlxRateService;
    
    @Autowired
    private ICzrzService czrzService;
    
    @RequestMapping(value = "/listJzlxCount")
    @ResponseBody
    public List<JzlxRateEntity> listJzlxCount(@RequestParam Long idJzg,HttpServletRequest request){
        List<JzlxRateEntity> list = new ArrayList<>();
        // 本部门及下级部门
        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("dataAuthGmid",idJzg);
            String sql = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            mapParam.put("dataAuth", "jzxx.user_id" + sql);
            //行政卷宗及其数量
            JzlxRateEntity xzEntity = jzlxRateService.selectCountOfJzlx(mapParam);
            //刑事卷宗及其数量
            JzlxRateEntity xsEntity =   jzlxRateService.selectCountOfJzlxXs(mapParam);
            if(xzEntity.getJzlxCount() !=0 ){
                list.add(xzEntity);
            }
            if(xsEntity.getJzlxCount() !=0 ){
                list.add(xsEntity);
                xsEntity.setJzlx(2);
            }
        }
        // 本部门
        if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("dataAuthGmid",idJzg);
            List<OrganizationEntity> listOrg = new ArrayList<>();
            OrganizationEntity org = new OrganizationEntity();
            org.setId( ControllerTool.getUser(request).getOrganizationId());
            listOrg.add(org);
            String  userIds =  ControllerTool.queryUsersInSqlByOrganizations(listOrg);
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth", "jzxx.user_id" + userIds);
            //行政卷宗及其数量
            JzlxRateEntity xzEntity = jzlxRateService.selectCountOfJzlx(mapParam);
            //刑事卷宗及其数量
            JzlxRateEntity xsEntity =   jzlxRateService.selectCountOfJzlxXs(mapParam);
            if(xzEntity.getJzlxCount() !=0 ){
                list.add(xzEntity);
            }
            if(xsEntity.getJzlxCount() !=0 ){
                list.add(xsEntity);
                xsEntity.setJzlx(2);
            }

        }
        // 本人
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =  ControllerTool.getUser(request).getId();
            String sql = "jzxx.user_id = " + id;
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth",sql);
            //行政卷宗及其数量
            JzlxRateEntity xzEntity = jzlxRateService.selectCountOfJzlx(mapParam);
            //刑事卷宗及其数量
            JzlxRateEntity xsEntity =   jzlxRateService.selectCountOfJzlxXs(mapParam);
            if(xzEntity.getJzlxCount() !=0 ){
                list.add(xzEntity);
            }
            if(xsEntity.getJzlxCount() !=0 ){
                list.add(xsEntity);
                xsEntity.setJzlx(2);
            }
        }
        // 上级部门及其下级部门
        if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =  ControllerTool.getUser(request).getId();
            String sql = "jzxx.user_id = " + id;
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth",sql);
            //行政卷宗及其数量
            JzlxRateEntity xzEntity = jzlxRateService.selectCountOfJzlx(mapParam);
            //刑事卷宗及其数量
            JzlxRateEntity xsEntity =   jzlxRateService.selectCountOfJzlxXs(mapParam);
            if(xzEntity.getJzlxCount() !=0 ){
                list.add(xzEntity);
            }
            if(xsEntity.getJzlxCount() !=0 ){
                list.add(xsEntity);
                xsEntity.setJzlx(2);
            }
        }
        //  全部
        if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =  ControllerTool.getUser(request).getId();
            String sql = "jzxx.user_id = " + id;
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth","");
            //行政卷宗及其数量
            JzlxRateEntity xzEntity = jzlxRateService.selectCountOfJzlx(mapParam);
            //刑事卷宗及其数量
            JzlxRateEntity xsEntity =   jzlxRateService.selectCountOfJzlxXs(mapParam);
            if(xzEntity.getJzlxCount() !=0 ){
                list.add(xzEntity);
            }
            if(xsEntity.getJzlxCount() !=0 ){
                list.add(xsEntity);
                xsEntity.setJzlx(2);
            }
        }
        return list;
    }
    
    
    @RequestMapping(value = "/listZxgj")
    @ResponseBody
    public List<ZxgjEntity> listZxgj(@RequestParam Long idJzg,Long gd, HttpServletRequest request){
        System.out.println("gd*****************************"+gd);
        System.out.println("idJzg*****************************"+idJzg);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        List<ZxgjEntity>  list = new ArrayList<>() ;
        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            //String sql = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth", "jzxx.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
            mapParam.put("gd",gd);
            list = jzlxRateService.selectListZxgj(mapParam);
        }
        if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            List<OrganizationEntity> listOrg = new ArrayList<>();
            OrganizationEntity org = new OrganizationEntity();
            org.setId( ControllerTool.getUser(request).getOrganizationId());
            listOrg.add(org);
            String  userIds =  ControllerTool.queryUsersInSqlByOrganizations(listOrg);
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth", "jzxx.op_pid = " + sessionInfo.getCurrentOrgPid());
            mapParam.put("gd",gd);
            list = jzlxRateService.selectListZxgj(mapParam);
        }
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
             Integer id =  ControllerTool.getUser(request).getId();
             String sql = "jzxx.user_id = " + id;
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth", sql);
            mapParam.put("gd",gd);
            list = jzlxRateService.selectListZxgj(mapParam);
        }
        // 全部
        if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Integer id =  ControllerTool.getUser(request).getId();
            String sql = "jzxx.user_id = " + id;
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth", "");
            mapParam.put("gd",gd);
            list = jzlxRateService.selectListZxgj(mapParam);
        }
        // 上级部门及其下级部门
        if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Integer id =  ControllerTool.getUser(request).getId();
            // String sql = "jzxx.user_id = " + id;
            Map<String, Object> mapParam = new HashMap<>();
            mapParam.put("dataAuthGmid",idJzg);
            mapParam.put("dataAuth", " jzxx.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
            mapParam.put("gd",gd);
            list = jzlxRateService.selectListZxgj(mapParam);
        }
        return list;
    }
    
    
    @RequestMapping(value = "/listAlarmCount")
    @ResponseBody
    public String listAlarmCount(@RequestParam Long idJzg,HttpServletRequest request){
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            // 公安领导-本部门及下级部门
            String result = "";
        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            String sql = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            mapParam.put("dataAuth", "jzxx.user_id" + sql);
            mapParam.put("dataAuthGmid",idJzg);
            Integer countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParam);//告警卷宗的总数量
            Integer countJz = jzlxRateService.selectCountOfJz(mapParam);//卷宗的总数量
            if(countJz==0){
                Integer q =1;
                result = numberFormat.format((float)countAlarmJz/(float)q*100);
            }else{
                result = numberFormat.format((float)countAlarmJz/(float)countJz*100);
            }
        }
        //本部门
        if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            List<OrganizationEntity> listOrg = new ArrayList<>();
            OrganizationEntity org = new OrganizationEntity();
            org.setId( ControllerTool.getUser(request).getOrganizationId());
            listOrg.add(org);
            String  userIds =  ControllerTool.queryUsersInSqlByOrganizations(listOrg);
            mapParam.put("dataAuth", "jzxx.user_id" + userIds);
            mapParam.put("dataAuthGmid",idJzg);
            Integer countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParam);//告警卷宗的总数量
            Integer countJz = jzlxRateService.selectCountOfJz(mapParam);//卷宗的总数量
            if(countJz==0){
                Integer q =1;
                result = numberFormat.format((float)countAlarmJz/(float)q*100);
            }else{
                result = numberFormat.format((float)countAlarmJz/(float)countJz*100);
            }

        }
           //本人
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =  ControllerTool.getUserID(request);
            String sql = "jzxx.user_id = "+id;
            mapParam.put("dataAuth",sql);
            mapParam.put("dataAuthGmid",idJzg);
            Integer countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParam);//告警卷宗的总数量
            Integer countJz = jzlxRateService.selectCountOfJz(mapParam);//卷宗的总数量
            if(countJz==0){
                Integer q =1;
                result = numberFormat.format((float)countAlarmJz/(float)q*100);
            }else{
                result = numberFormat.format((float)countAlarmJz/(float)countJz*100);
            }
        }
        //上级部门及其下级部门
        if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =  ControllerTool.getUserID(request);
            String sql = "jzxx.user_id = "+id;
            mapParam.put("dataAuth",sql);
            mapParam.put("dataAuthGmid",idJzg);
            Integer countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParam);//告警卷宗的总数量
            Integer countJz = jzlxRateService.selectCountOfJz(mapParam);//卷宗的总数量
            if(countJz==0){
                Integer q =1;
                result = numberFormat.format((float)countAlarmJz/(float)q*100);
            }else{
                result = numberFormat.format((float)countAlarmJz/(float)countJz*100);
            }
        }
        // 全部
        if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =  ControllerTool.getUserID(request);
            String sql = "jzxx.user_id = "+id;
            mapParam.put("dataAuth","");
            mapParam.put("dataAuthGmid",idJzg);
            Integer countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParam);//告警卷宗的总数量
            Integer countJz = jzlxRateService.selectCountOfJz(mapParam);//卷宗的总数量
            if(countJz==0){
                Integer q =1;
                result = numberFormat.format((float)countAlarmJz/(float)q*100);
            }else{
                result = numberFormat.format((float)countAlarmJz/(float)countJz*100);
            }
        }
        return  result;
    }
    
    @RequestMapping(value = "/listGmUsedRate")
    @ResponseBody
    public String listGmUsedRate(@RequestParam Long idJzg,HttpServletRequest request){

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = "";
        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            List<OrganizationEntity> currentAndSubOrg=ControllerTool.getSessionInfo(request).getCurrentAndSubOrg();
            String sqlOrg ="";
            for (OrganizationEntity organizationEntity : currentAndSubOrg) {
                sqlOrg+=Integer.toString(organizationEntity.getId())+",";
            }
            String sql= sqlOrg.substring(0,sqlOrg.length()-1);
            mapParam.put("dataAuth", "jzg.org_id" + " in("+sql+")");
            mapParam.put("dataAuthGmid",idJzg);
            Integer countGm =  jzlxRateService.selectAllGmCount(mapParam);
            Integer countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            if(countGm==0){
                Integer q =1;
                result = numberFormat.format((float)countUsedGm/(float)q*100);  //获取柜门使用率
            }else{
                result = numberFormat.format((float)countUsedGm/(float)countGm*100);  //获取柜门使用率
            }
        }
        if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =   ControllerTool.getUser(request).getOrganizationId();
            String sql = "jzg.org_id = "+id;
            mapParam.put("dataAuth", sql);
            mapParam.put("dataAuthGmid",idJzg);
            Integer countGm =  jzlxRateService.selectAllGmCount(mapParam);
            Integer countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            if(countGm==0){
                Integer q =1;
                result = numberFormat.format((float)countUsedGm/(float)q*100);  //获取柜门使用率
            }else{
                result = numberFormat.format((float)countUsedGm/(float)countGm*100);  //获取柜门使用率
            }
        }
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =   ControllerTool.getUser(request).getOrganizationId();
            String sql = "jzg.org_id = "+id;
            mapParam.put("dataAuth", sql);
            mapParam.put("dataAuthGmid",idJzg);
            Integer countGm =  jzlxRateService.selectAllGmCount(mapParam);
            Integer countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            if(countGm==0){
                Integer q =1;
                result = numberFormat.format((float)countUsedGm/(float)q*100);  //获取柜门使用率
            }else{
                result = numberFormat.format((float)countUsedGm/(float)countGm*100);  //获取柜门使用率
            }
        }
        //上级部门及其下级部门
        if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =   ControllerTool.getUser(request).getOrganizationId();
            String sql = "jzg.org_id = "+id;
            mapParam.put("dataAuth", sql);
            mapParam.put("dataAuthGmid",idJzg);
            Integer countGm =  jzlxRateService.selectAllGmCount(mapParam);
            Integer countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            if(countGm==0){
                Integer q =1;
                result = numberFormat.format((float)countUsedGm/(float)q*100);  //获取柜门使用率
            }else{
                result = numberFormat.format((float)countUsedGm/(float)countGm*100);  //获取柜门使用率
            }
        }
        // 全部
        if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Integer id =   ControllerTool.getUser(request).getOrganizationId();
            String sql = "jzg.org_id = "+id;
            mapParam.put("dataAuth", sql);
            mapParam.put("dataAuthGmid",idJzg);
            Integer countGm =  jzlxRateService.selectAllGmCount(mapParam);
            Integer countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            if(countGm==0){
                Integer q =1;
                result = numberFormat.format((float)countUsedGm/(float)q*100);  //获取柜门使用率
            }else{
                result = numberFormat.format((float)countUsedGm/(float)countGm*100);  //获取柜门使用率
            }
        }
        return result;
    }
    
    
    @RequestMapping(value = "/listGmxx")
    @ResponseBody
    public GmxxEntity listGmxx (@RequestParam Long idJzg, HttpServletRequest request){
        GmxxEntity gmxxEntity = new GmxxEntity();
        Integer countGm =0;
        Integer  countUsedGm = 0;
        Integer countJz = 0;
        Integer countAlarmJz = 0;
        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Map<String, Object> mapParamJz = new HashMap<>();
            List<OrganizationEntity> currentAndSubOrg=ControllerTool.getSessionInfo(request).getCurrentAndSubOrg();
            String sqlOrg ="";
            for (OrganizationEntity organizationEntity : currentAndSubOrg) {
                sqlOrg+=Integer.toString(organizationEntity.getId())+",";
            }
            String sqlJz = ControllerTool.queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            String sql= sqlOrg.substring(0,sqlOrg.length()-1);
            mapParam.put("dataAuth", "jzg.org_id" + " in("+sql+")");
            mapParam.put("dataAuthGmid",idJzg);
            mapParamJz.put("dataAuthGmid",idJzg);
            mapParamJz.put("dataAuth", "jzxx.user_id" + sqlJz);
            countGm =  jzlxRateService.selectAllGmCount(mapParam);
            countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            countJz = jzlxRateService.selectCountOfJz(mapParamJz);//卷宗的总数量
            countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParamJz);//告警卷宗的总数量
        }
        if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Map<String, Object> mapParamJz = new HashMap<>();
            List<OrganizationEntity> listOrg = new ArrayList<>();
            OrganizationEntity org = new OrganizationEntity();
            org.setId( ControllerTool.getUser(request).getOrganizationId());
            listOrg.add(org);
            String  userIds =  ControllerTool.queryUsersInSqlByOrganizations(listOrg);
            Integer organizationIdaId =   ControllerTool.getUser(request).getOrganizationId();
            mapParamJz.put("dataAuth", "jzxx.user_id" + userIds);
            mapParam.put("dataAuth", "jzg.org_id =" + organizationIdaId);
            mapParam.put("dataAuthGmid",idJzg);
            mapParamJz.put("dataAuthGmid",idJzg);
            countGm =  jzlxRateService.selectAllGmCount(mapParam);
            countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            countJz = jzlxRateService.selectCountOfJz(mapParamJz);//卷宗的总数量
            countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParamJz);//告警卷宗的总数量
        }
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Map<String, Object> mapParamJz = new HashMap<>();
            Integer id =  ControllerTool.getUserID(request);
            Integer organizationIdaId =   ControllerTool.getUser(request).getOrganizationId();
            mapParam.put("dataAuth", "jzg.org_id =" + organizationIdaId);
            mapParamJz.put("dataAuth", "jzxx.user_id =" + id);
            mapParam.put("dataAuthGmid",idJzg);
            mapParamJz.put("dataAuthGmid",idJzg);
            countGm =  jzlxRateService.selectAllGmCount(mapParam);
            countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            countJz = jzlxRateService.selectCountOfJz(mapParamJz);//卷宗的总数量
            countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParamJz);//告警卷宗的总数量
        }
        // 上下级部门start
        if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Map<String, Object> mapParamJz = new HashMap<>();
            Integer id = ControllerTool.getUserID(request);
            Integer organizationIdaId = ControllerTool.getUser(request).getOrganizationId();
            mapParam.put("dataAuth", "jzg.org_id =" + organizationIdaId);
            mapParamJz.put("dataAuth", "jzxx.user_id =" + id);
            mapParam.put("dataAuthGmid", idJzg);
            mapParamJz.put("dataAuthGmid", idJzg);
            countGm = jzlxRateService.selectAllGmCount(mapParam);
            countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            countJz = jzlxRateService.selectCountOfJz(mapParamJz);//卷宗的总数量
            countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParamJz);//告警卷宗的总数量
        }
        //上下级部门end
        // 全部start
        if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForJZ(request))) {
            Map<String, Object> mapParam = new HashMap<>();
            Map<String, Object> mapParamJz = new HashMap<>();
            Integer id = ControllerTool.getUserID(request);
            Integer organizationIdaId = ControllerTool.getUser(request).getOrganizationId();
            mapParam.put("dataAuth", "");
            mapParamJz.put("dataAuth", "");
            mapParam.put("dataAuthGmid", idJzg);
            mapParamJz.put("dataAuthGmid", idJzg);
            countGm = jzlxRateService.selectAllGmCount(mapParam);
            countUsedGm = jzlxRateService.selectUsedGmCount(mapParam);
            countJz = jzlxRateService.selectCountOfJz(mapParamJz);//卷宗的总数量
            countAlarmJz = jzlxRateService.selectCountOfAlarmJz(mapParamJz);//告警卷宗的总数量
        }
        //全部end
        Integer countFreeGm = countGm-countUsedGm;//空闲的的柜门数
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        if(countGm==0){
            String r = "";
            Integer q =1;
            r = numberFormat.format((float)countUsedGm/(float)q*100)+"%";  //获取柜门使用率
            gmxxEntity.setGmsSyl(r);
        }else{
            String  result = numberFormat.format((float)countUsedGm/(float)countGm*100)+"%";  //获取柜门使用率
            gmxxEntity.setGmsSyl(result);
        }
        //Integer countJz = jzlxRateService.selectCountOfJz();//卷宗的总数量
        //Integer countAlarmJz = jzlxRateService.selectCountOfAlarmJz();//告警卷宗的总数量
        if(countJz==0){
            Integer s =1;
            String re = "";
            re = numberFormat.format((float)countAlarmJz/(float)s*100)+"%";  //卷宗告警率
            gmxxEntity.setJzGjl(re);
        }else{
            String  result2 = numberFormat.format((float)countAlarmJz/(float)countJz*100)+"%";  //卷宗告警率
            gmxxEntity.setJzGjl(result2);
        }
        gmxxEntity.setGmsCount(countGm);
        gmxxEntity.setGmsKx(countFreeGm);
        gmxxEntity.setJzsYc(countJz);
        gmxxEntity.setJzsGjs(countAlarmJz);
        return gmxxEntity;
    }
    
    /**
	 * 查询卷宗存取数量
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listJzCqDay30")
	@ResponseBody
	public Map<String, Map<String, Integer>> listDay30(@RequestParam Long id, @RequestParam Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Map<Long, Object>> mapAll = new HashMap<String, Map<Long, Object>>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		if (id != 0) {
			map.put("jzgId", id);
		} else {
            SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
            if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
                // 办案人员-本人
                map.put("dataAuth", " ( cz.user_id=" + ControllerTool.getUserID(request)
                        + " or aj.zbmj_id= "+ ControllerTool.getUserID(request)
                        + " or locate('," + ControllerTool.getUserID(request) + ",', aj.xbmj_ids)"
                        + " or aj.cjr="+ ControllerTool.getUserID(request)
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
                // 公安领导-本部门及下级部门
                map.put("dataAuth"," ( cz.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                        + " or aj.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                        + " or aj.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
                // 法制人员-上级部门及下级部门
                map.put("dataAuth"," ( cz.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                        + " or aj.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                        + " or aj.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                        + " ) ");
            } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
                // 本部门
                map.put("dataAuth"," ( cz.op_pid = " + sessionInfo.getCurrentOrgPid()
                        + " or aj.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                        + " or aj.op_pid = " + sessionInfo.getCurrentOrgPid()
                        + " ) ");
            }
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
		// 查询近30天czrz所有的存取信息
		List<CzrzEntity> crQcCount = czrzService.queryCqBy30Count(map);
		for (CzrzEntity czrzEntity : crQcCount) {
			// 将时间 进行格式化
			String date = (simpleDateFormat.format(czrzEntity.getCzsj())).toString();
			if (allMap.containsKey(date)) {
				Map<String, Integer> map1 = allMap.get(date);
				int cr = map1.get("cr");
				int qc = map1.get("qc");
				if (czrzEntity.getCzlx() == 1) {
					cr++;
				}
				if (czrzEntity.getCzlx() == 2) {
					qc++;
				}
				map1.clear();
				map1.put("cr", cr);
				map1.put("qc", qc);
				allMap.put(date, map1);
			} else {
				Map<String, Integer> map2 = new HashMap<String, Integer>();
				int cr = 0;
				int qc = 0;
				if (czrzEntity.getCzlx() == 1) {
					cr++;
				}
				if (czrzEntity.getCzlx() == 2) {
					qc++;
				}
				map2.clear();
				map2.put("cr", cr);
				map2.put("qc", qc);
				allMap.put(date, map2);
			}
		}
		// 获取当前日期
		Date today = new Date();
		String endDate = simpleDateFormat.format(today);// 当前日期
		// 获取三十天前日期
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(today);
		theCa.add(theCa.DATE, -30);// 最后一个数字30可改，30天的意思
		Date start = theCa.getTime();
		String startDate = simpleDateFormat.format(start);// 三十天之前日期
		Date dBegin = simpleDateFormat.parse(startDate);
		Date dEnd = simpleDateFormat.parse(endDate);
		List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);
		// 若没有数据的日期自动补充
		for (int i = 0; i < listDate.size(); i++) {
			String string = simpleDateFormat.format(listDate.get(i)).toString();
			if (!allMap.containsKey(string)) {
				Map<String, Integer> map3 = new HashMap<String, Integer>();
				map3.put("cr", 0);
				map3.put("qc", 0);
				allMap.put(string, map3);
			}
		}
		return allMap;
	}
	
	/**
	 * 根据开始时间和结束时间返回时间段内的 时间集合
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return List
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

    
}
