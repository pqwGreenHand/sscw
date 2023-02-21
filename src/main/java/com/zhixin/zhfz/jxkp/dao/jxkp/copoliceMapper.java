package com.zhixin.zhfz.jxkp.dao.jxkp;

import com.zhixin.zhfz.jxkp.entity.*;

import java.util.List;
import java.util.Map;

public interface copoliceMapper {
	List<CopoliceEntity> getCopoliceList(Map map);
	List<CopoliceOrg> getCopoliceSelect();
	List<Integer> getajsl(Map map);
	List<Mainpolice> getEvaluationList(Map map);
	List<Mainpolice> getdutypolice(Map map);
	List<Integer> getxbajsl(Map map);
	List<Mainpolice> getleadership(Map map);
	List<Integer> getleadershipajsl(Map map);
	List<BJTJEntity> getbjtj(Map map);
	List<Integer> getbjajsl(Map map);
	List<BJDWTJEntity> getbjdwtj(Map map);
	List<Integer> getbjdwajsl(Map map);
	List<Integer> getzerenajsl(Map map);
	List<Integer> getzerenkpsl(Map map);
	List<ZhibiaoEntity> getzhibiao(Map map);
	int getzhibiao_zkf(Map map);
	List<Integer> getzhibiao_ajsl(Map map);
	OrgCountEntity getzhibiao_kfzd(Map map);
	List<Mainpolice> getfazhiyuan(Map map);
	List<Integer> getfzyajsl(Map map);
	List<QuotaEntity_tqy> queryquota(Map map);
	int maxpage(Map map);
	int getmaxid();
	void addquota(Map map);
	void updatequota(Map map);
	void delequota(Map map);
	QuotaEntity_tqy update_getquota(Map map);
	EvaluationEntity_tqy selectCaseNo(Map map);
	List<EvaluationEntity_tqy> addevaluationCaseNo(Map map);
	Integer count_addevaluationCaseNo(Map map);
	List<Integer> getCopoliceListCount(Map map);
	List<Integer> getEvaluationListCount(Map map);
	Integer getdutypoliceCount(Map map);
	List<Integer> getleadershipCount(Map map);
	List<Integer> getbjdwtjCount(Map map);
	List<Integer> getzhibiaoCount(Map map);
	List<Integer> getfazhiyuanCount(Map map);
	PoliceIdLoginnameRealname getzhubanbyid(Map map);
	PoliceIdLoginnameRealname getlingdaobyid(Map map);
	List<PoliceIdLoginnameRealname> getxiebanbyid(Map map);
	List<ItemEntity> selectAllItem();
	List<OrgIdNameEntity> selectorganization(Map map);
	int selectorganization_count(Map map);
}
