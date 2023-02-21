package com.zhixin.zhfz.jzgl.controller.console;

import java.text.DateFormat;
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
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;
import com.zhixin.zhfz.jzgl.entity.XxEntity;
import com.zhixin.zhfz.jzgl.services.jzxx.ICzrzService;
import com.zhixin.zhfz.jzgl.services.jzxx.IJzxxService;
import com.zhixin.zhfz.jzgl.services.xx.IXxService;

/**
 * 数据统计
 * @author xdp
 *
 */
@Controller
@RequestMapping(value = "/zhfz/jzgl/console")
public class SjtjController {

	private static Logger logger = Logger.getLogger(SjtjController.class);

	  @Autowired
	  private ICzrzService czrzService;
	  @Autowired
	  private IJzxxService jzxxService;
	  
	  @Autowired
	  private IXxService xxService;

	/**
	 * 本周查询卷宗存取数量
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listJzXzCount")
	@ResponseBody
	public Map<String,  Object> orgXzList(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Map<Long, Object>> mapAll = new HashMap<String, Map<Long, Object>>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			String sql = " cz.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid();
			map.put("dataAuth", sql);
		}
		
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			String sql = " cz.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid();
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			String sql = " cz.user_id="+ControllerTool.getUserID(request);
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			String sql = " cz.op_pid="+ControllerTool.getSessionInfo(request).getCurrentOrgPid();
			map.put("dataAuth", sql);
		}
		
		List<CzrzEntity> crQcCount = this.czrzService.listJzXzCount(map);
		List<String> list = new ArrayList<String>();
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 int week1CrCount=0;int week1QcCount=0;
		 int week2CrCount=0;int week2QcCount=0;
		 int week3CrCount=0;int week3QcCount=0;
		 int week4CrCount=0;int week4QcCount=0;
		 int week5CrCount=0;int week5QcCount=0;
		 int week6CrCount=0;int week6QcCount=0;
		 int week7CrCount=0;int week7QcCount=0;
		for (int i = 0; i < crQcCount.size(); i++) {
			CzrzEntity cr = crQcCount.get(i);
			System.err.println("--------"+cr.getCzsj());
			int week =dayForWeek(sdf.format(cr.getCzsj()));
			if (week==1) {
				if(cr.getCzlx()==1){
					week1CrCount++;
				}else{
					week1QcCount++;
				}
			}
			if (week==2) {
				if(cr.getCzlx()==1){
					week2CrCount++;
				}else{
					week2QcCount++;
				}
			}
			if (week==3) {
				if(cr.getCzlx()==1){
					week3CrCount++;
				}else{
					week3QcCount++;
				}
			}
			if (week==4) {
				if(cr.getCzlx()==1){
					week4CrCount++;
				}else{
					week4QcCount++;
				}
			}
			if (week==5) {
				if(cr.getCzlx()==1){
					week5CrCount++;
				}else{
					week5QcCount++;
				}
			}
			if (week==6) {
				if(cr.getCzlx()==1){
					week6CrCount++;
				}else{
					week6QcCount++;
				}
			}
			if (week==7) {
				if(cr.getCzlx()==1){
					week7CrCount++;
				}else{
					week7QcCount++;
				}
			}
		}
		int countCr[] = {week1CrCount, week2CrCount,week3CrCount, week4CrCount, week5CrCount, week6CrCount,week7CrCount};
		int countQc[] = {week1QcCount, week2QcCount,week3QcCount, week4QcCount, week5QcCount, week6QcCount,week7QcCount};
		result.put("cr", countCr);
		result.put("qc", countQc);
		return result;
	}
	
	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String pTime) throws Exception {
		System.err.println(pTime);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		System.err.println(dayForWeek);
		return dayForWeek;
	}
	
	/**
	 * 查询卷宗存取数量
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listdwJzCq")
	@ResponseBody
	public Map<String, Object>  listdwJzCq(@RequestParam Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Map<Long, Object>> mapAll = new HashMap<String, Map<Long, Object>>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			String sql =  " org.p_id like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid();
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			String sql =  " org.p_id like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid();
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", "cz.user_id= " + ControllerTool.getUser(request).getId());
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			map.put("dataAuth", " org.id= " + ControllerTool.getUser(request).getOrganizationId());
		}
		//存入1 2取出
		List<CzrzEntity> crCount = this.czrzService.listdwJzCr(map);
//		List<CzrzMysqlEntity> qcCount = this.czrzMysqlService.listdwJzQc(map);
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
		String [] arrCr = null;
		String [] arrQc = null;
		List<String []> list = new ArrayList<String []>();
		List<String []> list1 = new ArrayList<String []>();
		for (int i = 0; i < crCount.size(); i++) {
			String   sj1="";
			arrCr = new String[2];
			arrQc = new String[2];
			CzrzEntity cr = crCount.get(i);
			String sj=sdf.format(cr.getCzsj()).substring(8,12);
			if ("0".equals(sj.substring(0, 1))) {
				sj1=sj.substring(1, 2)+"."+sj.substring(2, 4);
				System.err.println(sj.substring(1, 2)+"."+sj.substring(2, 4));
			}else{
				sj1=sj.substring(0, 2)+"."+sj.substring(2, 4);
				System.err.println(sj.substring(0, 2)+"."+sj.substring(2, 4));
			}
			if(cr.getCzlx()==1){
				arrCr[0]=String.valueOf(cr.getId());
				arrCr[1]=sj1;
				list.add(arrCr);
			}else{
				arrQc[0]=String.valueOf(cr.getId());
				arrQc[1]=sj1;
				list1.add(arrQc);
			}
		}
		result.put("cr", list);
		result.put("qc", list1);
		System.err.println(result);
		return result;
	}
	
	/**
	 *查询卷宗存取数量
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listJzXsXzDay30")
	@ResponseBody
	public Map<String, Map<String, Integer>> listxsxzDay30(@RequestParam Long id,@RequestParam Map<String,Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Map<Long, Object>> mapAll = new HashMap<String, Map<Long, Object>>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		if (id !=0) {
			map.put("jzgId",id);
		}
		else {
			if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				//上级及下级部门
				String sql= " org.p_id like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid();
				map.put("dataAuth", sql);
			}
			if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 公安领导-本部门及下级部门
				// 公安领导-本部门及下级部门
				String sql= " org.p_id like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid();
				map.put("dataAuth", sql);
			}
			if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 办案人员-本人
				map.put("dataAuth", "jz.user_id= " + ControllerTool.getUser(request).getId());
			}
			if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 本部门
				map.put("dataAuth", " org.id= " + ControllerTool.getUser(request).getOrganizationId());
			}
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
		//查询近30天czrz所有的存取信息
		List<JzxxEntity> crQcCount =jzxxService.queryXsxzBy30Count(map);
		for (JzxxEntity jzxxMysqlEntity : crQcCount) {
			// 将时间 进行格式化
			String date = (simpleDateFormat.format(jzxxMysqlEntity.getCjsj())).toString();
			if (allMap.containsKey(date)) {
				Map<String, Integer> map1 = allMap.get(date);
				int xs=0;
				int xz=0;
				if (jzxxMysqlEntity.getJzlx()==1) {
					xz++;
				}else{
					xs++;
				}
				map1.clear();
				map1.put("xs",xs);
				map1.put("xz",xz);
				allMap.put(date, map1);
			}else{
				Map<String, Integer> map2 = new HashMap<String, Integer>();
				int xs=0;
				int xz=0;
				if (jzxxMysqlEntity.getJzlx()==1) {
					xz++;
				}else{
					xs++;
				}
				map2.clear();
				map2.put("xs",xs);
				map2.put("xz",xz);
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
						map3.put("xs",0);
						map3.put("xz",0);
						allMap.put(string, map3);
					}
				}
				System.err.println(allMap);
				return allMap;
	}
	/**
	 * 根据开始时间和结束时间返回时间段内的
	 * 时间集合
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
	

	/**
	 * 卷宗各类告警统计
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listYjTj")
	@ResponseBody
	public Map<String, Object> listYjTj(@RequestParam Map<String,Object> param,HttpServletRequest request,
									  HttpServletResponse response){
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			String sql= " o.p_id like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid();
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", "  yj.yh_id= " + ControllerTool.getUserID(request));
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			map.put("dataAuth", " o.id= " + ControllerTool.getUser(request).getOrganizationId());
		}
 
		List<XxEntity> datas=this.xxService.listYjTj(map);
		System.err.println("++++datas++++"+datas);
		int labcGj=0; int yqbcGj=0;int yabcGj=0;int yqbhGj=0;
		int jzshWt=0; int jzysWt=0; int jzqsWt=0; int jzhzWt=0; int ghyqWt=0;int labbWt=0; int qtWt=0;
//		5卷宗损坏 6 卷宗遗失 7 卷宗缺失 8 卷宗混杂 9归还逾期10立案不办 99 其他
//		1立案不存 2 逾期不存 3 有案不查 4 逾期不还
		for (XxEntity xxMysqlEntity : datas) {
//			0预警  1问题
			if(xxMysqlEntity.getXxly()!=null) {
				if(xxMysqlEntity.getXxly()==0){
					if (xxMysqlEntity.getXxlx() ==1) {
						labcGj++;
					}
					if (xxMysqlEntity.getXxlx() ==2) {
						yqbcGj++;
					}
					if (xxMysqlEntity.getXxlx() ==3) {
						yabcGj++;
					}
					if (xxMysqlEntity.getXxlx() ==4) {
						yqbcGj++;
					}
					
				}else{
					if (xxMysqlEntity.getXxlx() ==5) {
						jzshWt++;
					}
					if (xxMysqlEntity.getXxlx() ==6) {
						jzysWt++;
					}
					if (xxMysqlEntity.getXxlx() ==7) {
						jzqsWt++;
					}
					if (xxMysqlEntity.getXxlx() ==8) {
						jzhzWt++;
					}
					if (xxMysqlEntity.getXxlx() ==9) {
						ghyqWt++;
					}
					if (xxMysqlEntity.getXxlx() ==10) {
						labbWt++;
					}
					if (xxMysqlEntity.getXxlx() ==99) {
						qtWt++;
					}
					
				}
			}
			
		}
		Map<String, Object> result = new HashMap<String, Object>();
//		int labcGj=0; int yqbcGj=0;int yabcGj=0;int yqbhGj=0;
//		int jzshWt=0; int jzysWt=0; int jzqsWt=0; int jzhzWt=0; int ghyqWt=0;int labbWt=0; int qtWt=0;
		result.put("labcGj", labcGj);
		result.put("yqbcGj", yqbcGj);
		result.put("yabcGj", yabcGj);
		result.put("yqbhGj", yqbhGj);
		result.put("jzshWt", jzshWt);
		result.put("jzysWt", jzysWt);
		result.put("jzqsWt", jzqsWt);
		result.put("jzhzWt", jzhzWt);
		result.put("ghyqWt", ghyqWt);
		result.put("labbWt", labbWt);
		result.put("qtWt", qtWt);
		System.err.println("++++result++++"+result);
		return result;
	}
}
