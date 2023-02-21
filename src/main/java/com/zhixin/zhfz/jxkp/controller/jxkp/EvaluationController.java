package com.zhixin.zhfz.jxkp.controller.jxkp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.jxkp.entity.*;
import com.zhixin.zhfz.jxkp.form.*;
import com.zhixin.zhfz.jxkp.services.jxkp.IEvaluationService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.services.combobox.IComboboxService;
import com.zhixin.zhfz.bacs.services.law.ILawService;
import com.zhixin.zhfz.common.services.notice.IMyNoticeService;
import com.zhixin.zhfz.sacw.common.FreemarkerUtil;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.zhixin.zhfz.jzgl.controller.jzlxRate.JzlxRateController.getDatesBetweenTwoDate;
import static java.lang.Integer.parseInt;

/**
 * Created by wangly on 2018/4/17.
 */
@Controller
@RequestMapping("/zhfz/jxkp")
public class EvaluationController {
    private static Logger logger = Logger.getLogger(EvaluationController.class);
    @Autowired
    private IEvaluationService evaluationService;
    @Autowired
    private IMyNoticeService myNoticeService;
    @Autowired
    private ILawService service;
    @Autowired
    private IComboboxService comboboxService;

    @RequestMapping(value = "/listEvaluation")
    @ResponseBody
    public Map<String, Object> listEvaluation(HttpServletResponse response, Integer pageSize, Integer pageNumber, HttpServletRequest request) throws IOException {
        Integer pageStart = (pageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageStart", pageStart);
        map.put("pageEnd", pageSize);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForEva(request))) {
            //本人的数据 （0）
            map.put("dataAuth", " ( e.creater=" + ControllerTool.getUserID(request) + " or e.police_id="
                    + ControllerTool.getUserID(request) + " ) ");
        }else if(RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForEva(request))){
             //本部门及下级部门(6)
            map.put("dataAuth"," ( area.p_id like  " + sessionInfo.getCurrentAndSubOrgPid() + " ) ");
        }else if(RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForEva(request))){
            //本部门（5）
            map.put("dataAuth"," ( e.oraganizationId = " + sessionInfo.getUser().getOrganizationId()+ " ) ");
        }else if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForEva(request))){
            //上级部门及下级部门（7）
            map.put("dataAuth"," ( area.p_id like " + sessionInfo.getSuperAndSubOrgPid() +  " ) ");
        }
            //否则就查全部数据
        List<EvaluationEntity> list = evaluationService.selectAllEvaluation(map);
       /* for (int i = 0; i < list.size(); i++) {
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getHandleTime());
            String dateStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getExamineTime());
            list.get(i).setHandleTime2(dateStr);
            list.get(i).setExamineTime2(dateStr2);
        }*/
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", evaluationService.selectAllEvaluationCount(map));
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/listEvaluationParam")
    @ResponseBody
    public Map<String, Object> listEvaluation(HttpServletResponse response, @RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException {
        Integer pageNumber = parseInt(params.get("pageNumber").toString());
        Integer pageSize = parseInt(params.get("pageSize").toString());
        Integer pageStart = (pageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        params.put("pageStart", pageStart);
        params.put("pageEnd", pageSize);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForEva(request))) {
            //本人的数据 （0）
            params.put("dataAuth", " ( e.creater=" + ControllerTool.getUserID(request) + " or e.police_id="
                    + ControllerTool.getUserID(request) + " ) ");
        }else if(RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForEva(request))){
            //本部门及下级部门(6)
            map.put("dataAuth"," ( area.p_id like  " + sessionInfo.getCurrentAndSubOrgPid() + " ) ");
        }else if(RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForEva(request))){
            //本部门（5）
            params.put("dataAuth"," ( e.oraganizationId = " + sessionInfo.getUser().getOrganizationId()+ " )");
        }else if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForEva(request))){
            //上级部门及下级部门（7）
            params.put("dataAuth"," ( area.p_id like " + sessionInfo.getSuperAndSubOrgPid() +  " ) ");
        }
       /* List<String> sonsOrgSelect = evaluationService.selectSonsOrgId(params.get("jbdw").toString());*/
        /*if(sonsOrgSelect.size()>0){
            String sonsOrgIdsSelect= StringUtils.join(sonsOrgSelect, ",");
            String orgIds3 = "(" + sonsOrgIdsSelect + ","+params.get("jbdw").toString()+")";
            params.put("dataAreaIdsOfSelectOrg", "e.oraganizationId in " + orgIds3);
        }else{*/
        params.put("dataAreaIdsOfSelectOrg", "e.oraganizationId in ( " + params.get("jbdw").toString() + " ) ");
        /*}*/
        List<EvaluationEntity> list = evaluationService.selectAllEvaliationByParam(params);
       /* for (int i = 0; i < list.size(); i++) {
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getHandleTime());
            String dateStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getExamineTime());
            list.get(i).setHandleTime2(dateStr);
            list.get(i).setExamineTime2(dateStr2);
        }*/
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", evaluationService.selectAllEvaliationByParamCount(params));
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/listBatchByTypeId")
    @ResponseBody
    public void listBatchByTypeId(@RequestParam Map<String, Object> map, HttpServletResponse response) throws IOException {
        List<BatchEntity> list = evaluationService.listBatchByTypeId(parseInt(map.get("typeId").toString()));
        String jsonString = JSONObject.toJSONString(list);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    @RequestMapping(value = "/selectAllInterrogateArea")
    @ResponseBody
    public void selectAllInterrogateArea(HttpServletResponse response,HttpServletRequest request) throws IOException {
        Map params = new HashMap();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 办案人员-本人
            /*params.put("dataAuth", " ( u.id=" + ControllerTool.getUserID(request)
                    + " or u.op_user_id=" + ControllerTool.getUserID(request)
                    + " or o.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");*/

            params.put("dataAuth", " ( o.id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or o.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 公安领导-本部门及下级部门
            params.put("dataAuth", " ( o.id like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or o.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or u.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } /*else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 法制人员-上级部门及下级部门
            params.put("dataAuth", " ( o.id like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or o.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or u.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        }*/ else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForXtConf(request))) {
            // 本部门
            params.put("dataAuth", " ( o.p_id = " + sessionInfo.getCurrentOrgPid()
                    + " or o.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or u.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        list = this.comboboxService.listAllOrganizationName(params);
        String jsonString = JSONObject.toJSONString(list);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    @RequestMapping(value = "/selectAllUser")
    @ResponseBody
    public void selectAllUser(HttpServletResponse response) throws IOException {
        List<UserEntity> list = evaluationService.selectAllUser();
        String jsonString = JSONObject.toJSONString(list);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    @RequestMapping(value = "/selectOneUser")
    @ResponseBody
    public void selectOneUser(HttpServletResponse response, HttpServletRequest request) throws IOException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(ControllerTool.getUserID(request));
        userEntity.setRealName(evaluationService.selectRealNameById(ControllerTool.getUserID(request).toString()));
        String jsonString = JSONObject.toJSONString(userEntity);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    @RequestMapping(value = "/selectUserCommon")
    @ResponseBody
    public Map<String, Object> selectUserCommon(HttpServletRequest request,HttpServletResponse response, Integer pageSize, Integer pageNumber, String orgId) throws IOException {
        Integer pageStart = (pageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageStart", pageStart);
        map.put("pageEnd", pageSize);
        map.put("orgId", orgId);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        map.put("dataAuth", " ( org.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
        List<UserEntity> list = evaluationService.selectUserCommon(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", evaluationService.selectUserCommonCount(map));
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/selectUserCommonParam")
    @ResponseBody
    public Map<String, Object> selectUserCommonParam(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> params) throws IOException {
        params.put("orgId", "org.id in (" + params.get("mjdw").toString() + ")");
        Integer pageNumber = parseInt(params.get("pageNumber").toString());
        Integer pageSize = parseInt(params.get("pageSize").toString());
        Integer pageStart = (pageNumber - 1) * pageSize;
        params.put("pageStart", pageStart);
        params.put("pageEnd", pageSize);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        params.put("dataAuth", " ( org.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
        List<UserEntity> listUser = evaluationService.selectUserCommonParam(params);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", evaluationService.selectUserCommonParamCount(params));
        result.put("rows", listUser);
        return result;
    }

    @RequestMapping(value = "/selectAllQuota")
    @ResponseBody
    public Map<String, Object> selectAllQuota(HttpServletResponse response,  @RequestParam Map<String, Object> params) throws IOException {
     /*   for(String s:params.keySet()){
            System.err.println(s+"===="+params.get(s));
        }*/
        Integer pageNumber = parseInt(params.get("pageNumber").toString());
        Integer pageSize = parseInt(params.get("pageSize").toString());
        Integer pageStart = (pageNumber - 1) * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageStart", pageStart);
        map.put("pageEnd", pageSize);
        map.put("scoringStand",params.get("scoringStand").toString());
        if(params.get("caseType").toString()!=null && params.get("caseType").toString().trim().length()>0){
            map.put("caseType", params.get("caseType").toString());
        }
        map.put("item",  params.get("item").toString());
        Map<String, Object> result = new HashMap<>();
        result.put("rows",evaluationService.selectQuotaByItem(map));
        result.put("total",evaluationService.selectQuotaByItemCount(map));
        return result;
    }

    @RequestMapping(value = "selectEvaJoinByEvaId")
    @ResponseBody
    public void selectEvaJoinByEvaId(HttpServletResponse response, String evaId) throws IOException {
        List<EvaJoinEntity> listEvaJoin = evaluationService.selectEvaJoinByEvaId(evaId);
        List<UserJoinEntity> listReturn = new ArrayList<>();
        for (int i = 0; i < listEvaJoin.size(); i++) {
            UserJoinEntity userJoinEntity = new UserJoinEntity();
            userJoinEntity.setUserId(listEvaJoin.get(i).getUserId().toString());
            userJoinEntity.setRealNameAndPoliceNo("已选：" + evaluationService.selectRealNameById(listEvaJoin.get(i).getUserId().toString()) + "(" + evaluationService.selectPoliceNoById(listEvaJoin.get(i).getUserId().toString()) + ")");
            listReturn.add(userJoinEntity);
        }
        String jsonString = JSONObject.toJSONString(listReturn);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    @RequestMapping(value = "addSomeExamine")
    @ResponseBody
    public void addSomeExamine(HttpServletResponse response, Integer count, ExamineForm examineForm) throws IOException {
        if (examineForm.getSubContext() != null) {
            List<String> listContext2 = new ArrayList<>();
            String[] arrContext = examineForm.getSubContext().split("#");
            List<String> listContext = Arrays.asList(arrContext);
            String s = "";
//            System.err.println("listContext=============="+listContext);
            for (int i = 0; i < count / 4; i++) {
                if (i == 0) {
                    s = listContext.get(0);
                } else {
                    s = listContext.get(i).substring(1, listContext.get(i).length());
                }
                listContext2.add(s);
            }

            String[] arrSubPointValue = examineForm.getSubPointValue().split(",");
            List<String> listarrSubPointValue = Arrays.asList(arrSubPointValue);

            String[] arrSubPoliceNo = examineForm.getSubPoliceNo().split(",");
            List<String> listarrSubPoliceNo = Arrays.asList(arrSubPoliceNo);

            String[] arrSubQuotaId = examineForm.getSubQuotaId().split(",");
            List<String> listarrSubQuotaId = Arrays.asList(arrSubQuotaId);
            for (int i = 0; i < count / 4; i++) {
                ExamineForm examineForm1 = new ExamineForm();
                examineForm1.setSubEvaId(evaluationService.selectMaxId());
                examineForm1.setSubContext(listContext2.get(i));
                examineForm1.setSubPointValue(listarrSubPointValue.get(i));
                examineForm1.setSubPoliceNo(listarrSubPoliceNo.get(i));
                examineForm1.setSubQuotaId(listarrSubQuotaId.get(i));
                examineForm1.setSubStatus("0");
                evaluationService.addSomeExamin(examineForm1);
            }
        }
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("AddExamineSuccess");
    }

    @RequestMapping(value = "addBaseEvaInfo")
    @ResponseBody
    public void addBaseExamineInfo(HttpServletRequest request, HttpServletResponse response, BaseInformationForm baseInformationForm) throws IOException {
        Integer lawerId = ControllerTool.getUserID(request);
        baseInformationForm.setSubCreaterId(ControllerTool.getUserID(request).toString());
        baseInformationForm.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrgPid());
        evaluationService.addOneEvaluation(baseInformationForm);
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("AddEvaSuccess");
    }

    @RequestMapping(value = "addPoliceJoin")
    @ResponseBody
    public void addPoliceJoin(HttpServletResponse response, EvaPoliceJoinForm evaPoliceJoinForm,String zbzcyId,HttpServletRequest request,String ajbh,String ajmc) throws IOException {
        if (evaPoliceJoinForm.getPoliceJoinId() != null) {
            String[] arrPoliceJoin = evaPoliceJoinForm.getPoliceJoinId().split(",");
            List<String> listarrPoliceJoin = Arrays.asList(arrPoliceJoin);
            for (int i = 0; i < listarrPoliceJoin.size(); i++) {
                EvaPoliceJoinForm evaPoliceJoinForm1 = new EvaPoliceJoinForm();
                evaPoliceJoinForm1.setPoliceJoinId(listarrPoliceJoin.get(i));
                evaPoliceJoinForm1.setEvaId(evaluationService.selectMaxId().toString());
                if (Integer.parseInt(evaPoliceJoinForm1.getPoliceJoinId()) != 0) {
                    evaluationService.addPoliceJoin(evaPoliceJoinForm1);
                }
            }
            InformEntity entity  =  new InformEntity();
            Date date = new Date();
            SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entity.setSenderId(ControllerTool.getSessionInfo(request).getUser().getId());
            entity.setReceiverId(Integer.parseInt(zbzcyId));
            entity.setTitle("考评签领通知！");
            entity.setType(2);
            entity.setContent("您好，您办理的编号为："+ajbh+"的案件已经在"+dateformat1.format(date)+"由法制员："+ControllerTool.getSessionInfo(request).getUser().getRealName()+"完成考核，请您及时进行签领！");
            entity.setAjbh(ajbh);
            entity.setAjmc(ajmc);
            evaluationService.insertOneInform(entity);
        }
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("AddJoinSuccess");
    }

    @RequestMapping(value = "updateBaseEva")
    @ResponseBody
    public void updateBaseEva(HttpServletResponse response, BaseUpdEvaForm baseUpdEvaForm) throws IOException {
        if (baseUpdEvaForm.getSubCpsx() == 100) {
            baseUpdEvaForm.setSubCpsx(parseInt(evaluationService.selectCpsx(baseUpdEvaForm.getCaseIdU())));
        }
        evaluationService.updateOneEva(baseUpdEvaForm);
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("UpdEvaSuccess");
    }

    @RequestMapping(value = "updSomeEvaJoin")
    @ResponseBody
    public void updSomeEvaJoin(HttpServletResponse response, String evaId, EvaPoliceJoinForm evaPoliceJoinForm) throws IOException {
        evaluationService.deleteSomeEvaJoinByEvaId(evaId);
        if (evaPoliceJoinForm.getPoliceJoinId() != null) {
            String[] arrPoliceJoin = evaPoliceJoinForm.getPoliceJoinId().split(",");
            List<String> listarrPoliceJoin = Arrays.asList(arrPoliceJoin);
            for (int i = 0; i < listarrPoliceJoin.size(); i++) {
                EvaPoliceJoinForm evaPoliceJoinForm1 = new EvaPoliceJoinForm();
                evaPoliceJoinForm1.setPoliceJoinId(listarrPoliceJoin.get(i));
                evaPoliceJoinForm1.setEvaId(evaId);
                if (Integer.parseInt(evaPoliceJoinForm1.getPoliceJoinId()) != 0) {
                    evaluationService.addPoliceJoin(evaPoliceJoinForm1);
                }
            }
        }
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("UpdJoinSuccess");
    }

    @RequestMapping(value = "updSomeExamine")
    @ResponseBody
    public void updSomeExamine(HttpServletResponse response, Integer count, String evaId, ExamineForm examineForm) throws IOException {
       /* System.err.println(examineForm);*/
        if (examineForm.getSubContext() != null) {
            List<String> listContext2 = new ArrayList<>();
            String[] arrContext = examineForm.getSubContext().split("#");
            List<String> listContext = Arrays.asList(arrContext);
            String s = "";
            for (int i = 0; i < count / 5; i++) {
                if (i == 0) {
                    s = listContext.get(0);
                } else {
                    s = listContext.get(i).substring(1, listContext.get(i).length());
                }
                listContext2.add(s);
            }

            String[] arrSubPointValue = examineForm.getSubPointValue().split(",");
            List<String> listarrSubPointValue = Arrays.asList(arrSubPointValue);

            String[] arrSubPoliceNo = examineForm.getSubPoliceNo().split(",");
            List<String> listarrSubPoliceNo = Arrays.asList(arrSubPoliceNo);

            String[] arrSubQuotaId = examineForm.getSubQuotaId().split(",");
            List<String> listarrSubQuotaId = Arrays.asList(arrSubQuotaId);

            String[] arrSubStatus = examineForm.getSubStatus().split(",");
            List<String> listarrSubStatus = Arrays.asList(arrSubStatus);
            evaluationService.deleteSomeExamByevaId(evaId);
            for (int i = 0; i < count / 5; i++) {
                ExamineForm examineForm1 = new ExamineForm();
                examineForm1.setSubEvaId(parseInt(evaId));
                examineForm1.setSubContext(listContext2.get(i));
                examineForm1.setSubPointValue(listarrSubPointValue.get(i));
                examineForm1.setSubPoliceNo(listarrSubPoliceNo.get(i));
                examineForm1.setSubQuotaId(listarrSubQuotaId.get(i));
                examineForm1.setSubStatus(listarrSubStatus.get(i));
                evaluationService.addSomeExamin(examineForm1);
            }
        }
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("UpdExamineSuccess");
    }

    @RequestMapping(value = "selectAllExamineOfEva")
    @ResponseBody
    public void selectAllExamineOfEva(HttpServletResponse response, String evaId) throws IOException {
        List<ExamineEntity> listExamin = evaluationService.selectAllExamineByEvaId(evaId);
        for (int i = 0; i < listExamin.size(); i++) {
            String name = "";
            String policeNo = "";
            String scoringStand = "";
            if (listExamin.get(i).getDutypoliceId() != null) {
                name = evaluationService.selectRealNameById(listExamin.get(i).getDutypoliceId().toString());
                policeNo = evaluationService.selectPoliceNoById(listExamin.get(i).getDutypoliceId().toString());
            }
            if (listExamin.get(i).getQuotaId() != null) {
                scoringStand = evaluationService.selectOneQuotaById(listExamin.get(i).getQuotaId().toString());
            }
            String newNameAndNo = name + "(" + policeNo + ")";
            listExamin.get(i).setDutyPoliceNameAndNo(newNameAndNo);
            listExamin.get(i).setScoringStand(scoringStand);
        }
        String jsonString = JSONObject.toJSONString(listExamin);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    @RequestMapping(value = "deleteOneEva")
    @ResponseBody
    public void deleteOneEva(HttpServletResponse response, String evaId) throws IOException {
        evaluationService.deleteOneEvaById(evaId);
        evaluationService.deleteSomeEvaJoinByEvaId(evaId);
        evaluationService.deleteSomeExamByevaId(evaId);
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("DeleteEvaSuccess");
    }

    @RequestMapping(value = "download")
    @ResponseBody
    public void exportReport(HttpServletRequest request, HttpServletResponse response, String caseNo) throws Exception {
        String[] arr = caseNo.split(",");
        List<String> list = Arrays.asList(arr);
        List<String> listCaseNo = new ArrayList<String>(list);
        if (listCaseNo.size() > 0) {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download;charset=UTF-8");

            URL url = Thread.currentThread().getContextClassLoader().getResource("");
            String templatePath = url.getPath() + "template";
            String tempPath = url.getPath() + "tempPath" + new Date().getTime();
            File tf = new File(tempPath);
            if (listCaseNo.size() > 1) {
                response.addHeader("Content-Disposition", "attachment;filename=" + new String("案件审核表".getBytes(), "ISO8859-1") + ".zip");// 下载文件的名称
                if (!tf.exists()) {
                    tf.mkdir();
                }
            } else {
                LawDocProcessEntity resultOne = new LawDocProcessEntity();
                resultOne = evaluationService.getDocData(listCaseNo.get(0), request);
                String s = "案件审核表-"+resultOne.getData().get("caseName");
           /*     response.addHeader("Content-Disposition", "attachment;filename=" + new String("案件审核表33333".getBytes(), "ISO8859-1") + ".doc");*/
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(s.getBytes(), "ISO8859-1") +".doc");
              /*  response.addHeader("Content-Disposition", "attachment;filename="+s+ ".doc");*/
            }

            for (int i = 0; i < listCaseNo.size(); i++) {

                LawDocProcessEntity result = new LawDocProcessEntity();
                result = evaluationService.getDocData(listCaseNo.get(i), request);
                String downFileName = result.getDownFileName();
                String userAgent = request.getHeader("User-Agent").toLowerCase();
                if (userAgent.contains("msie") || userAgent.contains("trident") || userAgent.indexOf("firefox") > 0) {
                    downFileName = new String(downFileName.getBytes("UTF-8"), "ISO8859-1");
                } else {
                    downFileName = URLEncoder.encode(downFileName, "UTF-8");
                }
                String xmlFileName = result.getXmlFileName();
                if (listCaseNo.size() > 1) {
                    FreemarkerUtil.process(templatePath, xmlFileName, tempPath + File.separator + "案件审核表-" + result.getData().get("caseName") + "(" + i + ")" + ".doc", result.getData());
                } else {
                    FreemarkerUtil.process(templatePath, xmlFileName, result.getData(), response.getWriter());
                }
            }
            if (listCaseNo.size() > 1) {
                File[] flist = tf.listFiles();
                String tmpFileName = "work.zip";
                byte[] buffer = new byte[1024];
                String strZipPath = tempPath + File.separator + tmpFileName;
                try {
                    ZipOutputStream outFile = new ZipOutputStream(new FileOutputStream(
                            strZipPath));
                    for (int i = 0; i < flist.length; i++) {
                        FileInputStream fis = new FileInputStream(flist[i].getAbsoluteFile());
                        outFile.putNextEntry(new ZipEntry(flist[i].getName()));
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            outFile.write(buffer, 0, len);
                        }
                        outFile.closeEntry();
                        fis.close();
                        flist[i].delete();
                    }
                    outFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                InputStream inStream = null;
                File zipFile = new File(strZipPath);
                try {
                    if (zipFile.exists()) {

                        inStream = new FileInputStream(strZipPath);
                        response.setContentLength(Integer.valueOf(((Long) zipFile.length()).toString()));
                        byte[] b = new byte[1024];
                        int len;
                        while ((len = inStream.read(b)) > 0) {
                            try {
                                response.getOutputStream().write(b, 0, len);
                            } catch (Exception we) {
                                break;
                            }
                        }

                    }
                } catch (Exception e) {
                } finally {
                    try {
                        if (inStream != null) {
                            inStream.close();
                        }
                        response.getOutputStream().flush();
                        response.getOutputStream().close();
                        zipFile.delete();
                        tf.delete();
                    } catch (Exception e) {

                    }
                }
            }

        }


    }

    @RequestMapping(value = "print")
    @ResponseBody
    public JSON print(String evaId, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("evaId", evaId);
        List<EvaluationEntity> listEvaluation = evaluationService.selectAllEvaliationByParamWord(params);
        EvaluationEntity evaEntity = listEvaluation.get(listEvaluation.size() - 1);
        Map<String, Object> map = new HashMap();
        if (listEvaluation.size() != 0) {
            ExamineEntity examineEntity0 = new ExamineEntity();
            examineEntity0.setAjmc(listEvaluation.get(0).getCaseName());
            examineEntity0.setXyr(listEvaluation.get(0).getSuspect());
            if (listEvaluation.get(0).getCaseType() == 0) {
                examineEntity0.setAjxz("警情");
            }
            if (listEvaluation.get(0).getCaseType() == 1) {
                examineEntity0.setAjxz("行政");
            }
            if (listEvaluation.get(0).getCaseType() == 2) {
                examineEntity0.setAjxz("刑事");
            }
            if (listEvaluation.get(0).getCaseType() == 3) {
                examineEntity0.setAjxz("执法监督");
            }

            map.put("baseInfo", examineEntity0);
            map.put("baseInfo2", evaEntity);
            for (int i = 0; i < listEvaluation.size(); i++) {
                List<ExamineEntity> listExamineEntity = new ArrayList();
                listExamineEntity = evaluationService.selectAllExamineByEvaIdPrint(listEvaluation.get(i).getId().toString());
                if (listExamineEntity.size() != 0) {
                    listExamineEntity.get(0).setJbdw(listEvaluation.get(i).getOraganizationName());
                    listExamineEntity.get(0).setJbr(evaluationService.selectRealNameById(listEvaluation.get(i).getCreaterId()));
                    listExamineEntity.get(0).setShld(listEvaluation.get(i).getPolice());
                    listExamineEntity.get(0).setDfz(listEvaluation.get(i).getScorevalue().toString());
                    listExamineEntity.get(0).setCpyj(listEvaluation.get(i).getBatchItem());
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listEvaluation.get(i).getHandleTime());
                    String dateSt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listEvaluation.get(i).getExamineTime());
                    listExamineEntity.get(0).setCprq(dateStr);
                    listExamineEntity.get(0).setShrq(dateSt2);
                    listExamineEntity.get(0).setJyaq(listEvaluation.get(i).getCaseDesc());
                    listExamineEntity.get(0).setXyzj(listEvaluation.get(i).getExistEvidence());
                    listExamineEntity.get(0).setFlyj(listEvaluation.get(i).getLawAccording());
                    listExamineEntity.get(0).setExamineLeader(evaluationService.selectRealNameById(listEvaluation.get(i).getLeaderId()));
                    listExamineEntity.get(0).setLawerOpinion(listEvaluation.get(i).getLawerOpinion());
                    map.put(i + "", listExamineEntity);
                }
                if (listExamineEntity.size() == 0) {
                    ExamineEntity examineEntity = new ExamineEntity();
                    examineEntity.setJbdw(listEvaluation.get(i).getOraganizationName());
                    examineEntity.setJbr(evaluationService.selectRealNameById(listEvaluation.get(i).getCreaterId()));
                    examineEntity.setContext("无");
                    examineEntity.setQuotaId(0);
                    examineEntity.setDutyPoliceNameAndNo("无");
                    examineEntity.setPointsValue(0);
                    examineEntity.setShld(listEvaluation.get(i).getPolice());
                    examineEntity.setDfz("100");
                    examineEntity.setCpyj(listEvaluation.get(i).getBatchItem());
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listEvaluation.get(i).getHandleTime());
                    String dateSt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listEvaluation.get(i).getExamineTime());
                    examineEntity.setCprq(dateStr);
                    examineEntity.setShrq(dateSt2);
                    examineEntity.setJyaq(listEvaluation.get(i).getCaseDesc());
                    examineEntity.setXyzj(listEvaluation.get(i).getExistEvidence());
                    examineEntity.setFlyj(listEvaluation.get(i).getLawAccording());
                    examineEntity.setExamineLeader(evaluationService.selectRealNameById(listEvaluation.get(i).getLeaderId()));
                    examineEntity.setLawerOpinion(listEvaluation.get(i).getLawerOpinion());
                    listExamineEntity.add(examineEntity);
                    map.put(0 + "", listExamineEntity);

                }
            }
        }
        return (JSON) JSON.toJSON(map);
    }

    @RequestMapping(value = "printsuper")
    @ResponseBody
    public JSON printsuper(String caseNo, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("caseNo", caseNo);
        List<EvaluationEntity> listEvaluation = evaluationService.selectAllEvaliationByParamWord(params);
        EvaluationEntity evaEntity = listEvaluation.get(listEvaluation.size() - 1);
        Map<String, Object> map = new HashMap();
        if (listEvaluation.size() != 0) {
            ExamineEntity examineEntity0 = new ExamineEntity();
            examineEntity0.setAjmc(listEvaluation.get(0).getCaseName());
            examineEntity0.setXyr(listEvaluation.get(0).getSuspect());
            if (listEvaluation.get(0).getCaseType() == 0) {
                examineEntity0.setAjxz("警情");
            }
            if (listEvaluation.get(0).getCaseType() == 1) {
                examineEntity0.setAjxz("行政");
            }
            if (listEvaluation.get(0).getCaseType() == 2) {
                examineEntity0.setAjxz("刑事");
            }
            if (listEvaluation.get(0).getCaseType() == 3) {
                examineEntity0.setAjxz("执法监督");
            }

            map.put("baseInfo", examineEntity0);
            map.put("baseInfo2", evaEntity);
            for (int i = 0; i < listEvaluation.size(); i++) {
                List<ExamineEntity> listExamineEntity = new ArrayList();
                listExamineEntity = evaluationService.selectAllExamineByEvaIdPrint(listEvaluation.get(i).getId().toString());
                if (listExamineEntity.size() != 0) {
                    listExamineEntity.get(0).setJbdw(listEvaluation.get(i).getOraganizationName());
                    listExamineEntity.get(0).setJbr(evaluationService.selectRealNameById(listEvaluation.get(i).getCreaterId()));
                    listExamineEntity.get(0).setShld(listEvaluation.get(i).getPolice());
                    listExamineEntity.get(0).setDfz(listEvaluation.get(i).getScorevalue().toString());
                    listExamineEntity.get(0).setCpyj(listEvaluation.get(i).getBatchItem());
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listEvaluation.get(i).getHandleTime());
                    String dateSt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listEvaluation.get(i).getExamineTime());
                    listExamineEntity.get(0).setCprq(dateStr);
                    listExamineEntity.get(0).setShrq(dateSt2);
                    listExamineEntity.get(0).setJyaq(listEvaluation.get(i).getCaseDesc());
                    listExamineEntity.get(0).setXyzj(listEvaluation.get(i).getExistEvidence());
                    listExamineEntity.get(0).setFlyj(listEvaluation.get(i).getLawAccording());
                    listExamineEntity.get(0).setExamineLeader(evaluationService.selectRealNameById(listEvaluation.get(i).getLeaderId()));
                    listExamineEntity.get(0).setLawerOpinion(listEvaluation.get(i).getLawerOpinion());
                    map.put(i + "", listExamineEntity);
                }
                if (listExamineEntity.size() == 0) {
                    ExamineEntity examineEntity = new ExamineEntity();
                    examineEntity.setJbdw(listEvaluation.get(i).getOraganizationName());
                    examineEntity.setJbr(evaluationService.selectRealNameById(listEvaluation.get(i).getCreaterId()));
                    examineEntity.setContext("无");
                    examineEntity.setQuotaId(0);
                    examineEntity.setDutyPoliceNameAndNo("无");
                    examineEntity.setPointsValue(0);
                    examineEntity.setShld(listEvaluation.get(i).getPolice());
                    examineEntity.setDfz("100");
                    examineEntity.setCpyj(listEvaluation.get(i).getBatchItem());
                    String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listEvaluation.get(i).getHandleTime());
                    String dateSt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listEvaluation.get(i).getExamineTime());
                    examineEntity.setCprq(dateStr);
                    examineEntity.setShrq(dateSt2);
                    examineEntity.setJyaq(listEvaluation.get(i).getCaseDesc());
                    examineEntity.setXyzj(listEvaluation.get(i).getExistEvidence());
                    examineEntity.setFlyj(listEvaluation.get(i).getLawAccording());
                    examineEntity.setExamineLeader(evaluationService.selectRealNameById(listEvaluation.get(i).getLeaderId()));
                    examineEntity.setLawerOpinion(listEvaluation.get(i).getLawerOpinion());
                    listExamineEntity.add(examineEntity);
                    map.put(i + "", listExamineEntity);

                }
            }
        }
        return (JSON) JSON.toJSON(map);
    }

    @RequestMapping(value = "/listLaw")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Integer pageStart = (Integer.parseInt(String.valueOf(params.get("pageNumber"))) - 1) * (Integer.parseInt(String.valueOf(params.get("pageSize"))));
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("pageStart", pageStart);
        map2.put("rows", (Integer.parseInt(String.valueOf(params.get("pageSize")))));
        map2.put("type", params.get("type"));
        map2.put("content", params.get("content"));
        // map.put("usePage", true);
        List<LawEntity> list = service.list(map2);
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).getName();
            String content = list.get(i).getContent();
            String[] arr = content.split("条");
            List<String> listArr = Arrays.asList(arr);
            String nameOfContent = name + listArr.get(0) + "条; ";
            list.get(i).setLawNameOfBar(nameOfContent);
            String r1 = list.get(i).getContent().replaceAll("br", " ");
            String r2 = r1.replaceAll(" ", "");
            list.get(i).setContent(r2);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.service.count(map2));
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/lawType")
    @ResponseBody
    public List<ComboboxEntity> lawType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        List<ComboboxEntity> comboboxEntities = comboboxService.listLawType(params);
        for (int i = 0; i < comboboxEntities.size(); i++) {
            comboboxEntities.get(i).setId((i + 1) + "");
        }
        return comboboxEntities;
    }

    @RequestMapping(value = "/lawName")
    @ResponseBody
    public List<ComboboxEntity> lawName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        List<ComboboxEntity> comboboxEntities = comboboxService.listLawName(params);
        for (int i = 0; i < comboboxEntities.size(); i++) {
            comboboxEntities.get(i).setId((i + 1) + "");
        }
        return comboboxEntities;
    }

    @RequestMapping(value = "/countofCaseNo")
    @ResponseBody
    public Integer countofCaseNo(HttpServletRequest request, HttpServletResponse response, String caseNo) throws Exception {
        return evaluationService.selectCountOfCaseNo(caseNo);
    }

    @RequestMapping(value = "/makeKeyValue")
    @ResponseBody
    public void makeKeyValue(HttpSession session, String zbId, EvaPoliceJoinForm evaPoliceJoinForm, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Map<String, String> map = new HashMap();
        List<KeyEntity> list = new ArrayList<>();
        if (evaPoliceJoinForm.getPoliceJoinId() != null) {
            String[] arrPoliceJoin = evaPoliceJoinForm.getPoliceJoinId().split(",");
            List<String> listarrPoliceJoin = Arrays.asList(arrPoliceJoin);
            for (int i = 0; i < listarrPoliceJoin.size(); i++) {
                map.put(listarrPoliceJoin.get(i), evaluationService.selectRealNameById(listarrPoliceJoin.get(i).toString()) + "(" + evaluationService.selectPoliceNoById(listarrPoliceJoin.get(i).toString()) + ")");
            }
        }
        map.put(zbId, evaluationService.selectRealNameById(zbId.toString()) + "(" + evaluationService.selectPoliceNoById(zbId.toString()) + ")");
        for (String s : map.keySet()) {
            KeyEntity keyEntity = new KeyEntity();
            keyEntity.setId(s);
            keyEntity.setName(map.get(s));
            list.add(keyEntity);
        }
        session.setAttribute(ControllerTool.getUserID(request).toString(), list);
    }

    @RequestMapping(value = "/getKeyValue")
    @ResponseBody
    public void getKeyValue(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<KeyEntity> list = (List) session.getAttribute(ControllerTool.getUserID(request).toString());
        String jsonString = JSONObject.toJSONString(list);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    @RequestMapping(value = "/getMaxId")
    @ResponseBody
    public String getMaxId(HttpSession session, HttpServletResponse response) throws IOException {
        String idMax = evaluationService.selectMaxId().toString();
      /*  String jsonString = JSONObject.toJSONString(idMax);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);*/
        return idMax;
    }

    @RequestMapping(value = "/getUserId")
    @ResponseBody
    public String getUserId(HttpSession session, HttpServletResponse response, String loginName) throws IOException {
        String userId = evaluationService.selectUserByNo(loginName);
      /*  String jsonString = JSONObject.toJSONString(idMax);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);*/
        return userId;
    }

   /* @Autowired
    private XxMysqlService xxMysqlService = null;*/

  /*  @RequestMapping(value = "/addXx")
    @ResponseBody
    public String addXx(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        try {
            Map<String, Object> map = new HashedMap();
            XxMysqlEntity entity = new XxMysqlEntity();
            entity.setXxbt(params.get("xxbt").toString());
            entity.setXxnr(params.get("xxnr").toString());
            entity.setXxlx(Long.valueOf(12));
            entity.setCjyhId(Long.valueOf(ControllerTool.getSessionInfo(request).getUser().getId()));
            entity.setXxdj(0);
            entity.setXxly(1);
            xxMysqlService.insertYj(entity);
            String[] yhid = (params.get("mjid") + "").split(",");
            for (int i = 0; i < yhid.length; i++) {
                map.clear();
                map.put("xxId", entity.getId());
                map.put("yhId", yhid[i]);
                xxMysqlService.insertYjToUser(map);
            }
        } catch (Exception e) {
            return "error" + e.getMessage();
        }


//    	params.put("cjyh_id", ControllerTool.getSessionInfo(request).getUser().getId());
//    	params.put("zt", 0);
//    	params.put("xxlx", 12);
//    	params.put("xxdj",0);
//    	try {
//    		evaluationService.addXx(params);
//    		int MaxXxId = evaluationService.getMaxXxId();
//    		params.put("xxid", MaxXxId);
//    		String[] yhid = (params.get("mjid")+"").split(",");
//
//    		for(int i = 0;i<yhid.length;i++) {
//    			params.put("yhid", yhid[i]);
//    			evaluationService.addXxReceiver(params);
//    		}
//    	} catch (Exception e) {
//			// TODO: handle exception
//			return "error";
//		}
        return "success";
    }*/

    @RequestMapping(value = "/selectXxPolice")
    @ResponseBody
    public List<Map<String, Object>> selectXxPolice(@RequestParam Map<String, Object> params) {
        return evaluationService.selectXxPolice(params);
    }

    @RequestMapping(value = "/queryEvaExist")
    @ResponseBody
    public void queryEvaExist(String ajbh, HttpServletResponse response) throws IOException {
        List<EvaluationEntity> list = evaluationService.selectEvaByAjbh(ajbh);
        if (list.size() == 0) {
            response.setContentType("Text/plain;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("0");
        } else {
            response.setContentType("Text/plain;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("1");
        }
    }

    @RequestMapping(value = "/evaListByAjbh")
    @ResponseBody
    public Map<String, Object> queryOneEvaExist(String ajbh, HttpServletResponse response) throws IOException {
        List<EvaluationEntity> list = evaluationService.selectEvaByAjbh(ajbh);
        for(int i =0;i<list.size();i++){
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getHandleTime());
            String dateStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getExamineTime());
            list.get(i).setLeaderId(evaluationService.selectRealNameById(list.get(i).getLeaderId()));
            list.get(i).setCreaterId(evaluationService.selectRealNameById(list.get(i).getCreaterId()));
            list.get(i).setHandleTime2(dateStr);
            list.get(i).setExamineTime2(dateStr2);
            list.get(i).setPoliceIdZb(evaluationService.selectRealNameById(list.get(i).getPoliceIdZb()));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", evaluationService.selectEvaByAjbhCount(ajbh));
        map.put("rows", list);
        return map;
    }
    @RequestMapping(value = "/queryScoreIsChange")
    @ResponseBody
    public void queryOneEvaExist(String pointValue,String evaId,String score,Integer count, ExamineForm examineForm, HttpServletResponse response,HttpServletRequest request) throws IOException {


        EvaluationEntity evaEntity   = evaluationService.selectEvaByEvaId(evaId);
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        if(evaEntity.getScorevalue().toString().trim().equals(score)){
            out.print("ScoreIsSame");
        }else if(!evaEntity.getScorevalue().toString().trim().equals(score)){
            //修改前后分值有变化
            AskStatusEntity askStatusEntity = new AskStatusEntity();
            askStatusEntity.setEvaId(evaId);
            askStatusEntity.setAskUserId(ControllerTool.getUserID(request));
            askStatusEntity.setOldDfz(Integer.valueOf(evaEntity.getScorevalue().toString().trim()));
            askStatusEntity.setOldKfz(Integer.valueOf(evaEntity.getPointsvalue().toString().trim()));
            askStatusEntity.setNewDfz(Integer.valueOf(score));
            askStatusEntity.setNewKfz(Integer.valueOf(pointValue));
            askStatusEntity.setStatus("0");
            askStatusEntity.setAjbh(evaEntity.getCaseNo());
            askStatusEntity.setAjmc(evaEntity.getCaseName());
            evaluationService.deleteSomeExamByevaIdTabChange(evaId);
            evaluationService.addOneAskStatus(askStatusEntity);
            if (examineForm.getSubContext() != null) {
                List<String> listContext2 = new ArrayList<>();
                String[] arrContext = examineForm.getSubContext().split("#");
                List<String> listContext = Arrays.asList(arrContext);
                String s = "";
                for (int i = 0; i < count / 5; i++) {
                    if (i == 0) {
                        s = listContext.get(0);
                    } else {
                        s = listContext.get(i).substring(1, listContext.get(i).length());
                    }
                    listContext2.add(s);
                }
                String[] arrSubPointValue = examineForm.getSubPointValue().split(",");
                List<String> listarrSubPointValue = Arrays.asList(arrSubPointValue);
                String[] arrSubPoliceNo = examineForm.getSubPoliceNo().split(",");
                List<String> listarrSubPoliceNo = Arrays.asList(arrSubPoliceNo);
                String[] arrSubQuotaId = examineForm.getSubQuotaId().split(",");
                List<String> listarrSubQuotaId = Arrays.asList(arrSubQuotaId);
                String[] arrSubStatus = examineForm.getSubStatus().split(",");
                List<String> listarrSubStatus = Arrays.asList(arrSubStatus);
                for (int i = 0; i < count / 5; i++) {
                    ExamineForm examineForm1 = new ExamineForm();
                    examineForm1.setSubEvaId(Integer.parseInt(evaId));
                    examineForm1.setSubContext(listContext2.get(i));
                    examineForm1.setSubPointValue(listarrSubPointValue.get(i));
                    examineForm1.setSubPoliceNo(listarrSubPoliceNo.get(i));
                    examineForm1.setSubQuotaId(listarrSubQuotaId.get(i));
                    examineForm1.setStatusId(evaluationService.selectMaxIdTabAskStatus());
                    examineForm1.setSubStatus(listarrSubStatus.get(i));
                    evaluationService.addSomeExaminTabChange(examineForm1);
                }
            }
            out.print("InsertEvaExamineChangeSuccess");
        }
    }
    @RequestMapping(value = "updateBaseEvaNoScore")
    @ResponseBody
    public void updateBaseEvaNoScore(HttpServletResponse response, BaseUpdEvaForm baseUpdEvaForm) throws IOException {
        if (baseUpdEvaForm.getSubCpsx() == 100) {
            baseUpdEvaForm.setSubCpsx(parseInt(evaluationService.selectCpsx(baseUpdEvaForm.getCaseIdU())));
        }
        evaluationService.updateOneEvaNoUpdScore(baseUpdEvaForm);
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("UpdEvaSuccess");
    }
    @RequestMapping(value = "selectAllMessage")
    @ResponseBody
    public  Map<String, Object> selectAllMessage(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, Object> params) throws IOException {
        Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
        Integer pageNumber  = Integer.valueOf(params.get("pageNumber").toString());
        Integer pageStart = (pageNumber - 1) * pageSize;
        params.put("pageStart", pageStart);
        params.put("pageEnd", pageSize);
        List<AskStatusEntity> list =  evaluationService.selectAskStatus(params);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", Integer.valueOf(evaluationService.selectAskStatusCount(params)));
        result.put("rows", list);
        return result;
    }
    @RequestMapping(value = "sureChange")
    @ResponseBody
    public  void sureChange(HttpServletResponse response, HttpServletRequest request,String evaId,String id) throws IOException {
        AskStatusEntity askStatusEntity = evaluationService.selectOneAskStatusById(id);
        if(askStatusEntity != null){
            Map map1 =new HashMap();
            map1.put("evaId",evaId);
            map1.put("id",id);
            List<ExamineEntity> listExamin2 = evaluationService.selectAllExamineFromTabExamineChangeByEvaId(map1);
            evaluationService.deleteSomeExamByevaId(evaId);
            for (int i = 0; i < listExamin2.size(); i++) {
                ExamineForm examineForm1 = new ExamineForm();
                examineForm1.setSubEvaId(parseInt(evaId));
                examineForm1.setSubContext(listExamin2.get(i).getContext());
                examineForm1.setSubPointValue(listExamin2.get(i).getPointsValue().toString());
                examineForm1.setSubPoliceNo(listExamin2.get(i).getDutypoliceId().toString());
                examineForm1.setSubQuotaId(listExamin2.get(i).getQuotaId().toString());
                examineForm1.setSubStatus(listExamin2.get(i).getStatus().toString());
                evaluationService.addSomeExamin(examineForm1);
            }
            Map map = new HashMap();
            Map map2 = new HashMap();
            map.put("evaId",evaId);
            map.put("subScorevalue",askStatusEntity.getNewDfz());
            map.put("pointsvalue",askStatusEntity.getNewKfz());
            map2.put("status",1);
            map2.put("id",id);
            map2.put("replyleaderId",ControllerTool.getUserID(request));
            evaluationService.updateOneStatusChange(map2);
            evaluationService.updateEvaScoreAndPoint(map);
            //确认以后开始执行删除
            /*evaluationService.delOneAskStatus(id);*/
        }
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("sureSuccess");
    }
    @RequestMapping(value = "refuseChange")
    @ResponseBody
    public  void refuseChange(HttpServletResponse response, HttpServletRequest request,String id) throws IOException {
        Map map2 = new HashMap();
        map2.put("status",0);
        map2.put("replyleaderId",ControllerTool.getUserID(request));
        map2.put("id",id);
        evaluationService.updateOneStatusChange(map2);
        //拒绝以后开始执行删除
        /*evaluationService.delOneAskStatus(id);*/
        response.setContentType("Text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("refuseSuccess");
    }
    @RequestMapping(value = "itemType")
    @ResponseBody
    public JSON itemType(HttpServletResponse response, HttpServletRequest request,String id) throws IOException {
        return (JSON) JSON.toJSON(evaluationService.selectAllItem());
    }
    @RequestMapping(value = "listRate")
    @ResponseBody
    public Map<String,Object> listRate(HttpServletResponse response, HttpServletRequest request,@RequestParam Map<String, Object> params) throws IOException {
        Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
        Integer pageNumber  = Integer.valueOf(params.get("pageNumber").toString());
        Integer pageStart = (pageNumber - 1) * pageSize;
        params.put("pageStart", pageStart);
        params.put("pageEnd", pageSize);
        List<ExamineRateEntity> list =  evaluationService.selectRate(params);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", Integer.valueOf(evaluationService.selectRateCount(params)));
        result.put("rows", list);
        return result;
    }
    @RequestMapping("/updEvaInformById")
    @ResponseBody
    public MessageEntity updatInformById(HttpServletRequest request, String sendId, String receiverId, String ajbh, String ajmc){
        String idStr=request.getParameter("id");
        try {
            System.err.println(ajbh);
            System.err.println(ajmc);
            evaluationService.updScheduleById(idStr);
            if(ajbh.trim().length()>0 && ajmc.trim().length()>0 && !ajbh.equals("null") && !ajmc.equals("null")){
                InformEntity entity  =  new InformEntity();
                Date date = new Date();
                SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                entity.setSenderId(Integer.parseInt(receiverId));
                entity.setReceiverId(Integer.parseInt(sendId));
                entity.setTitle("办案人员签领通知！");
                entity.setType(2);
                entity.setContent("您好，办案人员："+ControllerTool.getUser(request).getRealName()+"已在"+dateformat1.format(date)+"完成对案件："+ajmc+"("+ajbh+")"+"的签领！");
                evaluationService.insertOneInform(entity);
            }
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("阅读失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("阅读成功!");
    }
    @RequestMapping(value = "/getJZAJXX")
    @ResponseBody
    public Map<String, Object> getJZAJXX(@RequestParam Map<String, Object> param,HttpServletRequest request,HttpServletResponse response) throws Exception {
        param = ControllerTool.mapFilter(param);
        String caseName = "";
        String caseNo = "";
        String personName = "";
        String startTime = "", endTime = "";
        if (param.containsKey("caseName")) {
            caseName = param.get("caseName").toString();
        }
        if (param.containsKey("caseNo")) {
            caseNo = param.get("caseNo").toString();
        }
        if (param.containsKey("startTime")) {
            startTime = param.get("startTime").toString();
        }
        if (param.containsKey("endTime")) {
            endTime = param.get("endTime").toString();
        }
        if (param.containsKey("personName")) {
            personName = param.get("personName").toString();
        }
        if (startTime != null && startTime != "") {
            if (startTime.length() < 12) {
                startTime = startTime + " 00:00:00";
                param.put("startTime", startTime);
            }
        }
        if (endTime != null && endTime != "") {
            if (endTime.length() < 12) {
                endTime = endTime + " 23:59:59";
                param.put("endTime", endTime);
            }
        }

        if (caseNo != "") {
            if (param.containsKey("startTime")) {
                param.remove("startTime");
            }
            if (param.containsKey("endTime")) {
                param.remove("endTime");
            }
        }
        //如果是本人登陆
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForEva(request))) {
            Integer orgId = ControllerTool.getSessionInfo(request).getUser().getOrganizationId();
            String  orgCode = evaluationService.selectOrgCodeByOrgId(orgId.toString());
            if(orgCode != null && orgCode.trim().length()>0){
                param.put("orgCode", orgCode);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", 0);
        List<GdajEntity> listGdajEntity1 = new ArrayList<>();
        result.put("rows", listGdajEntity1);
        if (caseName != null && caseName.equals("-1")) {
            return result;
        }
        try {
            Map<String, Object> map = new HashedMap();
            if (startTime != null && startTime != "") {

                map.put("type", "0");
                List<gdjzSynConfigEntity> list = evaluationService.listConfig(map);
                if (list.size() > 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d = sdf.parse(startTime);
                    logger.info(d);
                    logger.info(list.get(0).getMidSynTime().getTime());
                   /*此处已经将其做成服务的形式，所以已经不需要再通过调取服务去查询了
                   if (list.get(0).getMidSynTime().getTime() > d.getTime()) {
                        logger.info("通过警综查询案件" + startTime);
                        return getAjxxFromJz(caseName, caseNo, personName, startTime, endTime);
                    }*/

                }
//            map.clear();
//            map.put("caseName", caseName);
//            map.put("caseNo", caseNo);
//            map.put("startTime", startTime);
//            map.put("endTime", endTime);
            }
            List<GdajEntity> data = evaluationService.listAjxx(param);
            int count = evaluationService.countAjxx(param);
            result.clear();
            result.put("total", count);
            result.put("rows", data);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return result;
    }
    @RequestMapping(value = "/listAllOrgAndParent")
    @ResponseBody
    public Map<String, Object> listAllOrgAndParent() {

        List<OrganizationEntity> list = evaluationService.listAllOrgAndParent();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        return result;
    }
    @RequestMapping(value = "/queryUsersByOrgId")
    @ResponseBody
    public void queryUsersByOrgId(String orgId,HttpServletResponse response){
        Map map = new HashMap();
        if(orgId!="" && !orgId.equals("0")) {
            map.put("orgId",orgId);
        }else {
            map.put("orgId",orgId);
        }
        List<UserEntity> list = evaluationService.selectUsers(map);
        String jsonString = JSONObject.toJSONString(list);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(jsonString);
    }
    @RequestMapping(value = "/rateSign")
    @ResponseBody
    public  Map<String,Object>  RateSign(){
      /*  //签领的统计
        List<RateSignEntity> listSign =  evaluationService.selectRateSign();*/
        //整改的统计
        List<RateSignEntity> listChange =  evaluationService.selectRateChange();
        //法制员近一个月的统计
        List<RateSignEntity> listFzday =  evaluationService.selectRateFzday();
        //近一个月各单位上报案件统计
        List<RateSignEntity> listSbaj =  evaluationService.selectCaseNumOfOrg();
        //近一个月各单位办案得分统计排名
        List<BJDWTJEntity> list = evaluationService.getbjdwtjVisual();
        //法制员工作量统计TOP10
        List<RateSignEntity> listFazhiyuan = evaluationService.getfazhiyuanTop10();
        //审核领导考评统计TOP10
        List<RateSignEntity> listShenheren = evaluationService.getShenherenTop10();
        //责任民警考评总分值统计TOP10
        List<RateSignEntity> listDutypolice = evaluationService.getdutypoliceTop10();
        int px = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        for(BJDWTJEntity i:list) {
            i.setPx(px);
            px++;
            map.put("jbdw", i.getJbdwid());
            i.setAjsl(evaluationService.getbjdwtjVisualCount(map).size());
            int s= evaluationService.getbjdwtjVisualCount(map).size();
            float avg =  ((100*s)+i.getKf_sum())/s;
			/*float avg = i.getAvg();*/
            BigDecimal b = new BigDecimal(avg);
            double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            i.setAvg(Float.parseFloat(newavg+""));
        }

        List<RateSignEntity> listOrgAvg = new ArrayList<>();
        for(int i =0;i<list.size();i++){
            RateSignEntity entity  = new RateSignEntity();
            entity.setName(list.get(i).getJbdw());
            entity.setValue(String.valueOf(list.get(i).getAvg()));
            listOrgAvg.add(entity);
        }
        Map result = new HashMap();
  /*      map.put("listSign",listSign);*/
        result.put("listChange",listChange);
        result.put("listFzday",listFzday);
        result.put("listSbaj",listSbaj);
        result.put("listOrgAvg",listOrgAvg);
        result.put("listFazhiyuan",listFazhiyuan);
        result.put("listDutypolice",listDutypolice);
        result.put("listShenheren",listShenheren);
        return result;
    }

    /**
     * 查询多少天内案件绩效考评折线图
     *
     * @param day
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectRateFzByday")
    @ResponseBody
    public Map<String, Map<String, Integer>> selectRateFzByday(Integer day, HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {
        List<RateSignEntity> list = evaluationService.selectRateFzByday(day);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (RateSignEntity entity : list) {
            // 将时间 进行格式化
            String date = entity.getName();
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int kaoping = map.get("kaoping");

                map.put("kaoping", kaoping++);
                allMap.put(date, map);
            } else {

                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("kaoping", Integer.valueOf(entity.getValue()));
                allMap.put(date, map);
            }
        }
        //获取日期
        //获取当前日期
        Date today = new Date();
        String endDate = simpleDateFormat.format(today);//当前日期
        //获取三十天前日期
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(today);
        theCa.add(theCa.DATE, -day);//最后一个数字30可改，30天的意思
        Date start = theCa.getTime();
        String startDate = simpleDateFormat.format(start);//三十天之前日期
        Date dBegin = simpleDateFormat.parse(startDate);
        Date dEnd = simpleDateFormat.parse(endDate);
        List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);
        //若没有数据的日期自动补充
        for (int i = 0; i < listDate.size(); i++) {
            String string = simpleDateFormat.format(listDate.get(i)).toString();
            if (!allMap.containsKey(string)) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("kaoping", 0);
                allMap.put(string, map);
            }
        }
        return allMap;
    }
}
