package com.zhixin.zhfz.jxkp.services.jxkp;

import com.zhixin.zhfz.jxkp.dao.jxkp.copoliceMapper;
import com.zhixin.zhfz.jxkp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class copoliceServiceImpl implements IcopoliceService{
	@Autowired
	private copoliceMapper cm;

	@Override
	public List<CopoliceEntity> getCopoliceList(Map map){
		return cm.getCopoliceList(map);
	}

	@Override
	public List<CopoliceOrg> getCopoliceSelect(){
		return cm.getCopoliceSelect();
	}

	@Override
	public List<Integer> getajsl(Map map) {
		return cm.getajsl(map);
	}

	@Override
	public List<Mainpolice> getEvaluationList(Map map){
		return cm.getEvaluationList(map);
	}

	@Override
	public List<Mainpolice> getdutypolice(Map map){
		return cm.getdutypolice(map);
	}

	@Override
	public List<Integer> getxbajsl(Map map){
		return cm.getxbajsl(map);
	}

	@Override
	public List<Mainpolice> getleadership(Map map){
		return cm.getleadership(map);
	}

	@Override
	public List<Integer> getleadershipajsl(Map map){
		return cm.getleadershipajsl(map);
	}

	@Override
	public List<BJTJEntity> getbjtj(Map map){
		return cm.getbjtj(map);
	}

	@Override
	public List<Integer> getbjajsl(Map map){
		return cm.getbjajsl(map);
	}

	@Override
	public List<BJDWTJEntity> getbjdwtj(Map map){
		return cm.getbjdwtj(map);
	}

	@Override
	public List<Integer> getbjdwajsl(Map map){
		return cm.getbjdwajsl(map);
	}

	@Override
	public List<Integer> getzerenajsl(Map map){
		return cm.getzerenajsl(map);
	}
	@Override
	public List<Integer> getzerenkpsl(Map map){
		return cm.getzerenkpsl(map);
	}
	@Override
	public List<ZhibiaoEntity> getzhibiao(Map map){
		return cm.getzhibiao(map);
	}

	@Override
	public int getzhibiao_zkf(Map map) {
		return cm.getzhibiao_zkf(map);
	}

	@Override
	public List<Integer> getzhibiao_ajsl(Map map){
		return cm.getzhibiao_ajsl(map);
	}

	@Override
	public OrgCountEntity getzhibiao_kfzd(Map map) {
		return cm.getzhibiao_kfzd(map);
	}

	@Override
	public List<Mainpolice> getfazhiyuan(Map map){
		return cm.getfazhiyuan(map);
	}

	@Override
	public List<Integer> getfzyajsl(Map map){
		return cm.getfzyajsl(map);
	}

	@Override
	public List<QuotaEntity_tqy> queryquota(Map map){
		return cm.queryquota(map);
	}

	@Override
	public int maxpage(Map map) {
		return cm.maxpage(map);
	}

	@Override
	public int getmaxid() {
		return cm.getmaxid();
	}

	@Override
	public void addquota(Map map) {
		cm.addquota(map);
	}
	@Override
	public void updatequota(Map map) {
		cm.updatequota(map);
	}
	@Override
	public void delequota(Map map) {
		cm.delequota(map);
	}
	@Override
	public QuotaEntity_tqy update_getquota(Map map) {
		return cm.update_getquota(map);
	}

	@Override
	public EvaluationEntity_tqy selectCaseNo(Map map){
		return cm.selectCaseNo(map);
	}

	@Override
	public List<EvaluationEntity_tqy> addevaluationCaseNo(Map map){
		return cm.addevaluationCaseNo(map);
	}

	@Override
	public Integer count_addevaluationCaseNo(Map map) {
		return cm.count_addevaluationCaseNo(map);
	}

	@Override
	public List<Integer> getCopoliceListCount(Map map){
		return cm.getCopoliceListCount(map);
	}

	@Override
	public List<Integer> getEvaluationListCount(Map map){
		return cm.getEvaluationListCount(map);
	}

	@Override
	public Integer getdutypoliceCount(Map map){
		return cm.getdutypoliceCount(map);
	}

	@Override
	public List<Integer> getleadershipCount(Map map){
		return cm.getleadershipCount(map);
	}

	@Override
	public List<Integer> getbjdwtjCount(Map map){
		return cm.getbjdwtjCount(map);
	}

	@Override
	public List<Integer> getzhibiaoCount(Map map){
		return cm.getzhibiaoCount(map);
	}

	@Override
	public List<Integer> getfazhiyuanCount(Map map){
		return cm.getfazhiyuanCount(map);
	}
	@Override
	public PoliceIdLoginnameRealname getzhubanbyid(Map map) {
		return cm.getzhubanbyid(map);
	}

	@Override
	public PoliceIdLoginnameRealname getlingdaobyid(Map map) {
		return cm.getlingdaobyid(map);
	}
	@Override
	public List<PoliceIdLoginnameRealname> getxiebanbyid(Map map) {
		return cm.getxiebanbyid(map);
	}
	@Override
	public List<ItemEntity> selectAllItem(){
		return cm.selectAllItem();
	}
	@Override
	public List<OrgIdNameEntity> selectorganization(Map map){
		return cm.selectorganization(map);
	}

	@Override
	public int selectorganization_count(Map map){
		return cm.selectorganization_count(map);
	}
}
