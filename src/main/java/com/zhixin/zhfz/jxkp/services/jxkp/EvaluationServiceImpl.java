package com.zhixin.zhfz.jxkp.services.jxkp;

import com.zhixin.zhfz.bacs.entity.LockerBoxEntity;
import com.zhixin.zhfz.common.entity.InformEntity;
import com.zhixin.zhfz.bacs.entity.LawDocProcessEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.jxkp.dao.jxkp.IevaluationMapper;
import com.zhixin.zhfz.jxkp.entity.*;
import com.zhixin.zhfz.jxkp.form.BaseInformationForm;
import com.zhixin.zhfz.jxkp.form.BaseUpdEvaForm;
import com.zhixin.zhfz.jxkp.form.EvaPoliceJoinForm;
import com.zhixin.zhfz.jxkp.form.ExamineForm;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.sacw.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhixin.zhfz.bacs.entity.AreaEntity;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zhixin.zhfz.common.entity.UserEntity;
/**
 * Created by wangly on 2018/4/17.
 */
@Service
public class EvaluationServiceImpl implements IEvaluationService {
    @Autowired
    private IevaluationMapper evaluationMapper;

    @Override
    public List<EvaluationEntity> selectAllEvaluation(Map map) {
        return evaluationMapper.selectAllEvaluation(map);
    }

    @Override
    public   Integer  selectAllEvaluationCount(Map map) {
        return evaluationMapper.selectAllEvaluationCount(map);
    }

    @Override
    public List<EvaluationEntity> selectAllEvaliationByParam(Map map) {
        return evaluationMapper.selectAllEvaliationByParam(map);
    }

    @Override
    public  Integer  selectAllEvaliationByParamCount(Map map) {
        return evaluationMapper.selectAllEvaliationByParamCount(map);
    }

    @Override
    public List<BatchEntity> listBatchByTypeId(Integer typeId) {
        return evaluationMapper.listBatchByTypeId(typeId);
    }

    @Override
    public List<AreaEntity> selectAllInterrogateArea() {
        return evaluationMapper.selectAllInterrogateArea();
    }

    @Override
    public List<UserEntity> selectAllUser() {
        return evaluationMapper.selectAllUser();
    }

    @Override
    public List<UserEntity> selectUserCommon(Map map) {
        return evaluationMapper.selectUserCommon(map);
    }

    @Override
    public List<UserEntity> selectUserCommonCount(Map map) {
        return evaluationMapper.selectUserCommonCount(map);
    }

    @Override
    public List<UserEntity> selectUserCommonParam(Map map) {
        return evaluationMapper.selectUserCommonParam(map);
    }

    @Override
    public Integer selectUserCommonParamCount(Map map) {
        return evaluationMapper.selectUserCommonParamCount(map);
    }

    @Override
    public List<QuotaEntity> selectAllQuota(Map map) {
        return evaluationMapper.selectAllQuota(map);
    }

    @Override
    public List<ItemEntity> selectAllItem() {
        return evaluationMapper.selectAllItem();
    }

    @Override
    public List<QuotaEntity> selectQuotaByItem(Map map) {
        return evaluationMapper.selectQuotaByItem(map);
    }

    @Override
    public void addSomeExamin(ExamineForm examineForm) {
        evaluationMapper.addSomeExamin(examineForm);
    }

    @Override
    public void addOneEvaluation(BaseInformationForm baseInformationForm) {
        evaluationMapper.addOneEvaluation(baseInformationForm);
    }
    @Override
    public Integer selectMaxId() {
        return evaluationMapper.selectMaxId();
    }

    @Override
    public String selectRealNameById(String userId) {
        return evaluationMapper.selectRealNameById(userId);
    }
    @Override
    public String selectPoliceNoById(String userId) {
        return evaluationMapper.selectPoliceNoById(userId);
    }

    @Override
    public void updateOneEva(BaseUpdEvaForm form) {
        evaluationMapper.updateOneEva(form);
    }

    @Override
    public void addPoliceJoin(EvaPoliceJoinForm form) {
        evaluationMapper.addPoliceJoin(form);
    }
    @Override
    public List<EvaJoinEntity> selectEvaJoinByEvaId(String evaId) {
        return evaluationMapper.selectEvaJoinByEvaId(evaId);
    }

    @Override
    public void deleteSomeEvaJoinByEvaId(String evaId) {
        evaluationMapper.deleteSomeEvaJoinByEvaId(evaId);
    }

    @Override
    public List<ExamineEntity> selectAllExamineByEvaId(String evaId) {
        return evaluationMapper.selectAllExamineByEvaId(evaId);
    }

    @Override
    public String selectOneQuotaById(String quotaId) {
        return evaluationMapper.selectOneQuotaById(quotaId);
    }

    @Override
    public void deleteSomeExamByevaId(String evaId) {
        evaluationMapper.deleteSomeExamByevaId(evaId);
    }

    @Override
    public String selectCpsx(String evaId) {
        return evaluationMapper.selectCpsx(evaId);
    }

    @Override
    public void deleteOneEvaById(String evaId) {
        evaluationMapper.deleteOneEvaById(evaId);
    }

    @Override
    public void getDocMapData(LawDocProcessEntity result, String evaId, SessionInfo sessionInfo) {
        /*EvaluationEntity evaluationEntity   =  evaluationMapper.selectOneEvaById(caseNo);*/
        System.err.println("caseNo"+evaId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("evaId", evaId);
        EvaluationEntity evaEntity =  evaluationMapper.selectAllEvaliationByEvaId(params);
        System.err.println("evaEntity"+evaEntity);
        List<Map> list2 = new ArrayList<Map>();
        List<Map>  list = new ArrayList<>();
        Map docMap = new HashMap();
        List<ExamineEntity>  listExamin = evaluationMapper.selectAllExamineByEvaId(evaEntity.getId().toString());
            Map mapq2 = new HashMap();
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(evaEntity.getHandleTime());
            String dateStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(evaEntity.getExamineTime());
            evaEntity.setLeaderId2(evaEntity.getLeaderId());
            evaEntity.setCreaterId2(evaEntity.getCreaterId());
            evaEntity.setPoliceNo(evaluationMapper.selectPoliceNoById(evaEntity.getLeaderId()));
            evaEntity.setPoliceNo2(evaluationMapper.selectPoliceNoById(evaEntity.getCreaterId()));
            evaEntity.setLeaderId(evaluationMapper.selectRealNameById(evaEntity.getLeaderId()));
            evaEntity.setCreaterId(evaluationMapper.selectRealNameById(evaEntity.getCreaterId()));
            evaEntity.setPoliceZbJh(evaluationMapper.selectPoliceNoById(evaEntity.getPoliceIdZb()));
            evaEntity.setHandleTime2(dateStr);
            evaEntity.setExamineTime2(dateStr2);

        docMap.put("oraganizationName", evaEntity.getOraganizationName());
        docMap.put("createrId", evaEntity.getCreaterId());
        docMap.put("policeId", evaEntity.getPolice());
        docMap.put("leaderId", evaEntity.getLeaderId());
        docMap.put("batchItem", evaEntity.getBatchItem());
        docMap.put("handleTime2", evaEntity.getHandleTime2());
        docMap.put("examineTime2", evaEntity.getExamineTime2());
           docMap.put("caseDesc", evaEntity.getCaseDesc());
           docMap.put("remarks", evaEntity.getRemarks());
           docMap.put("implementation", evaEntity.getImplementation());
           docMap.put("accordwith", evaEntity.getAccordwith());
           docMap.put("remarks", evaEntity.getRemarks());
           docMap.put("scoreValue", evaEntity.getScorevalue());
        docMap.put("evidenceNow", evaEntity.getExistEvidence());
        docMap.put("lawAccording", evaEntity.getLawAccording());
        docMap.put("lawerOpinion", evaEntity.getLawerOpinion());
            list2.add(mapq2);



        for (int i = 0; i < listExamin.size(); i++) {
            Map mapq = new HashMap();
            mapq.put("quotaId", listExamin.get(i).getQuotaId());
            mapq.put("context", listExamin.get(i).getContext());
            mapq.put("dutyPoliceNameAndNo",evaluationMapper.selectRealNameById(listExamin.get(i).getDutypoliceId().toString()));
            mapq.put("pointsValue", listExamin.get(i).getPointsValue());
            list.add(mapq);
        }

        docMap.put("caseName", evaEntity.getCaseName());
        docMap.put("caseNo", evaEntity.getCaseNo());
        docMap.put("suspect", evaEntity.getSuspect());

        if(evaEntity.getCaseType() == 0){
            docMap.put("caseType", "警情");
        }
        if(evaEntity.getCaseType() == 1){
            docMap.put("caseType", "行政");
        }
        if(evaEntity.getCaseType() == 2){
            docMap.put("caseType", "刑事");
        }
        if(evaEntity.getCaseType() == 3){
            docMap.put("caseType", "执法监督");
        }
        /*docMap.put("list", list);*/
        docMap.put("list", list);
        result.setData(docMap);
    }

    @Override
    public LawDocProcessEntity getDocData(String caseNo, HttpServletRequest request) {
        LawDocProcessEntity lawDocProcessEntity = new LawDocProcessEntity();
        //
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        //模板这是测试模板，非真实
        lawDocProcessEntity.setXmlFileName("案件考评审核表.xml");
        lawDocProcessEntity.setDownFileName("XX分局案件审核表.doc");
        lawDocProcessEntity.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        lawDocProcessEntity.setTempFileName(fileName);
        // ---------------------------------
        getDocMapData(lawDocProcessEntity, caseNo, sessionInfo);
        System.err.println("********************"+lawDocProcessEntity);
        return lawDocProcessEntity;
    }

    @Override
    public List<EvaluationEntity> selectAllEvaliationByParamWord(Map map) {
        return evaluationMapper.selectAllEvaliationByParamWord(map);
    }

    @Override
    public List<ExamineEntity> selectAllExamineByEvaIdPrint(String evaId) {
        return evaluationMapper.selectAllExamineByEvaIdPrint(evaId);
    }

    @Override
    public EvaluationEntity selectAllEvaliationByEvaId(Map map) {
        return evaluationMapper.selectAllEvaliationByEvaId(map);
    }

    @Override
    public Integer selectCountOfCaseNo(String caseNo) {
        return evaluationMapper.selectCountOfCaseNo(caseNo);
    }

    @Override
    public List<AreaEntity> selectOrgIdsOfOrg(Map map) {
        return evaluationMapper.selectOrgIdsOfOrg(map);
    }

    @Override
    public String selectUserByNo(String loginName) {
        return evaluationMapper.selectUserByNo(loginName);
    }
    

    
    @Override
    public int getMaxXxId() {
    	return evaluationMapper.getMaxXxId();
    }
    
    @Override
    public void addXxReceiver(Map map) {
    	evaluationMapper.addXxReceiver(map);
    }
    
    @Override
    public List<Map<String,Object>> selectXxPolice(Map map){
    	return evaluationMapper.selectXxPolice(map);
    }

    @Override
    public List<EvaluationEntity> selectEvaByAjbh(String ajbh) {
        return evaluationMapper.selectEvaByAjbh(ajbh);
    }

    @Override
    public Integer selectEvaByAjbhCount(String ajbh) {
        return evaluationMapper.selectEvaByAjbhCount(ajbh);
    }

    @Override
    public List<String> selectSonsOrgId(String parentId) {
        return evaluationMapper.selectSonsOrgId(parentId);
    }

    @Override
    public EvaluationEntity selectEvaByEvaId(String evaId) {
        return evaluationMapper.selectEvaByEvaId(evaId);
    }

    @Override
    public void deleteSomeExamByevaIdTabChange(String evaId) {
        evaluationMapper.deleteSomeExamByevaIdTabChange(evaId);
    }

    @Override
    public void addSomeExaminTabChange(ExamineForm examineForm) {
        evaluationMapper.addSomeExaminTabChange(examineForm);
    }

    @Override
    public void updateOneEvaNoUpdScore(BaseUpdEvaForm form) {
        evaluationMapper.updateOneEvaNoUpdScore(form);
    }

    @Override
    public void addOneAskStatus(AskStatusEntity askStatusEntity) {
        evaluationMapper.addOneAskStatus(askStatusEntity);
    }

    @Override
    public  List<AskStatusEntity> selectAskStatus(Map map) {
        return evaluationMapper.selectAskStatus(map);
    }

    @Override
    public List<ExamineEntity> selectAllExamineFromTabExamineChangeByEvaId(Map map ) {
        return evaluationMapper.selectAllExamineFromTabExamineChangeByEvaId(map);
    }

    @Override
    public void deleteOneAskStatus(String evaId) {
        evaluationMapper.deleteOneAskStatus(evaId);
    }

    @Override
    public String selectMaxIdTabAskStatus() {
        return evaluationMapper.selectMaxIdTabAskStatus();
    }

    @Override
    public void updateEvaScoreAndPoint(Map map) {
        evaluationMapper.updateEvaScoreAndPoint(map);
    }

    @Override
    public String selectAskStatusCount(Map map) {
        return evaluationMapper.selectAskStatusCount(map);
    }

    @Override
    public AskStatusEntity selectOneAskStatusById(String id) {
        return evaluationMapper.selectOneAskStatusById(id);
    }

    @Override
    public void updateOneStatusChange(Map map) {
        evaluationMapper.updateOneStatusChange(map);
    }

    @Override
    public Integer selectQuotaByItemCount(Map map) {
        return evaluationMapper.selectQuotaByItemCount(map);
    }

    @Override
    public List<ExamineRateEntity> selectRate(Map map) {
        return evaluationMapper.selectRate(map);
    }

    @Override
    public Integer selectRateCount(Map map) {
        return evaluationMapper.selectRateCount(map);
    }

    @Override
    public void delOneAskStatus(String id) {
        evaluationMapper.delOneAskStatus(id);
    }

    @Override
    public void insertOneInform(InformEntity entity) {
        evaluationMapper.insertOneInform(entity);
    }

    @Override
    public void addXx(Map map) {
        evaluationMapper.addXx(map);
    }

    @Override
    public String selectOrgCodeByOrgId(String orgId) {
        return evaluationMapper.selectOrgCodeByOrgId(orgId);
    }

    @Override
    public List<gdjzSynConfigEntity> listConfig(Map<String, Object> map) {
        return evaluationMapper.listConfig(map);
    }

    @Override
    public List<GdajEntity> listAjxx(Map<String, Object> map) throws Exception {
        return evaluationMapper.listAjxx(map);
    }

    @Override
    public int countAjxx(Map<String, Object> map) throws Exception {
        return evaluationMapper.countAjxx(map);
    }

    @Override
    public List<OrganizationEntity> listAllOrgAndParent() {
        return evaluationMapper.listAllOrgAndParent();
    }

    @Override
    public List<UserEntity> selectUsers(Map map) {
        return evaluationMapper.selectUsers(map);
    }

    @Override
    public List<RateSignEntity> selectRateSign() {
        return evaluationMapper.selectRateSign();
    }
    @Override
    public List<RateSignEntity> getfazhiyuanTop10() {
        return evaluationMapper.getfazhiyuanTop10();
    }
    @Override
    public List<RateSignEntity> getdutypoliceTop10() {
        return evaluationMapper.getdutypoliceTop10();
    }
    @Override
    public List<RateSignEntity> getShenherenTop10() {
        return evaluationMapper.getShenherenTop10();
    }
    @Override
    public List<RateSignEntity> selectRateChange() {
        return evaluationMapper.selectRateChange();
    }

    @Override
    public List<RateSignEntity> selectRateFzday() {
        return evaluationMapper.selectRateFzday();
    }
    @Override
    public List<RateSignEntity> selectRateFzByday(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return evaluationMapper.selectRateFzByday(map);
    }

    @Override
    public List<RateSignEntity> selectCaseNumOfOrg() {
        return evaluationMapper.selectCaseNumOfOrg();
    }

    @Override
    public void updScheduleById(String id) {
        evaluationMapper.updScheduleById(id);
    }

    @Override
    public List<BJDWTJEntity> getbjdwtjVisual() {
        return evaluationMapper.getbjdwtjVisual();
    }

    @Override
    public List<Integer> getbjdwtjVisualCount(Map map ) {
        return evaluationMapper.getbjdwtjVisualCount(map);
    }
}
