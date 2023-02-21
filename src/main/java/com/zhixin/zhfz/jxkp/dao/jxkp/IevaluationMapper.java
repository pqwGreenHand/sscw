/*
 * FileName: IpFilterEntity.java
 * auto create by wangguhua 2016
 * Author:   
 * Date:     2016-7-6 11:28:55
 * Description: IpFilterMapper实体类   
 */
 
package com.zhixin.zhfz.jxkp.dao.jxkp;
import com.zhixin.zhfz.bacs.entity.LockerBoxEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.jxkp.entity.*;
import com.zhixin.zhfz.jxkp.form.BaseInformationForm;
import com.zhixin.zhfz.jxkp.form.BaseUpdEvaForm;
import com.zhixin.zhfz.jxkp.form.EvaPoliceJoinForm;
import com.zhixin.zhfz.jxkp.form.ExamineForm;
import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.common.entity.InformEntity;
import com.zhixin.zhfz.common.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 实体类IpFilterMapper table: ip_filter
 * 
 * @author wangguhua
 */
public interface IevaluationMapper {
    List<EvaluationEntity> selectAllEvaluation(Map map);
    Integer  selectAllEvaluationCount(Map map);
    List<EvaluationEntity> selectAllEvaliationByParam(Map map);
    Integer selectAllEvaliationByParamCount(Map map);
    List<BatchEntity> listBatchByTypeId(Integer typeId);
    List<AreaEntity> selectAllInterrogateArea();
    List<UserEntity> selectAllUser();
    List<UserEntity> selectUserCommon(Map map);
    List<UserEntity> selectUserCommonCount(Map map);
    List<UserEntity> selectUserCommonParam(Map map);
    Integer selectUserCommonParamCount(Map map);
    List<QuotaEntity> selectAllQuota(Map map);
    List<ItemEntity> selectAllItem();
    List<QuotaEntity> selectQuotaByItem(Map map);
    void addSomeExamin(ExamineForm examineForm);
    void addOneEvaluation(BaseInformationForm baseInformationForm);
    Integer selectMaxId();
    String selectRealNameById(String userId);
    String selectPoliceNoById(String userId);
    void updateOneEva(BaseUpdEvaForm form);
    void addPoliceJoin(EvaPoliceJoinForm form);
    List<EvaJoinEntity> selectEvaJoinByEvaId(String evaId);
    void deleteSomeEvaJoinByEvaId(String evaId);
    List<ExamineEntity> selectAllExamineByEvaId(String evaId);
    String selectOneQuotaById(String quotaId);
    void deleteSomeExamByevaId(String evaId);
    String selectCpsx(String evaId);
    void deleteOneEvaById(String evaId);
    EvaluationEntity  selectOneEvaById(String evaId);
    EvaluationEntity selectAllEvaliationByEvaId(Map map);
    List<EvaluationEntity> selectAllEvaliationByParamWord(Map map);
    List<ExamineEntity> selectAllExamineByEvaIdPrint(String evaId);
    Integer selectCountOfCaseNo(String caseNo);
    List<AreaEntity> selectOrgIdsOfOrg(Map map);
    String selectUserByNo(String loginName);
    void addXx(Map map);
    int getMaxXxId();
    void addXxReceiver(Map map);
    List<Map<String,Object>> selectXxPolice(Map map);
    List<EvaluationEntity>  selectEvaByAjbh(String ajbh);
    Integer selectEvaByAjbhCount(String ajbh);
    List<String> selectSonsOrgId(String parentId);
    EvaluationEntity selectEvaByEvaId(String evaId);
    void deleteSomeExamByevaIdTabChange(String evaId);
    void addSomeExaminTabChange(ExamineForm examineForm);
    void updateOneEvaNoUpdScore(BaseUpdEvaForm form);
    void addOneAskStatus(AskStatusEntity askStatusEntity);
    List<AskStatusEntity> selectAskStatus(Map map);
    List<ExamineEntity> selectAllExamineFromTabExamineChangeByEvaId(Map map);
    void deleteOneAskStatus(String evaId);
     String selectMaxIdTabAskStatus();
    void updateEvaScoreAndPoint(Map map);
    String  selectAskStatusCount(Map map);
    AskStatusEntity selectOneAskStatusById(String id);
    void updateOneStatusChange(Map map);
    Integer selectQuotaByItemCount(Map map);
    Integer selectRateCount(Map map);
    List<ExamineRateEntity> selectRate(Map map);
    void delOneAskStatus(String id);
    void insertOneInform(InformEntity entity);
    String selectOrgCodeByOrgId(String orgId);
    List<gdjzSynConfigEntity> listConfig(Map<String,Object> map);
    List<GdajEntity> listAjxx(Map<String, Object> map) throws Exception;
    int countAjxx(Map<String, Object> map) throws Exception;
    public List<OrganizationEntity> listAllOrgAndParent();
    List<UserEntity> selectUsers(Map map);
    List<RateSignEntity> selectRateSign();
    List<RateSignEntity> getfazhiyuanTop10();
    List<RateSignEntity> getdutypoliceTop10();
    List<RateSignEntity> getShenherenTop10();
    List<RateSignEntity> selectRateChange();
    List<RateSignEntity> selectRateFzday();
    List<RateSignEntity> selectRateFzByday(Map<String, Object> map);
    List<RateSignEntity> selectCaseNumOfOrg();
    void updScheduleById(String id);
    List<BJDWTJEntity> getbjdwtjVisual();
    List<Integer> getbjdwtjVisualCount(Map map);
}