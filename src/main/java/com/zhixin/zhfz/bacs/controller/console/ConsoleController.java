package com.zhixin.zhfz.bacs.controller.console;


import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.belong.IBelongService;
import com.zhixin.zhfz.bacs.services.belongtemp.IBelongDetailTempService;
import com.zhixin.zhfz.bacs.services.console.IConsoleService;
import com.zhixin.zhfz.common.entity.CaseEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.apache.commons.codec.language.bm.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/console")
public class ConsoleController {

	private static final Logger logger = LoggerFactory.getLogger(ConsoleController.class);


	@Autowired
	private IConsoleService consoleService;

	@Autowired
	private ICaseService caseService;

	@Autowired
	private IBelongService belongService;



	//查询所有办案中心
	@RequestMapping(value="/queryCenters")
	@ResponseBody
	public List<AreaEntity> queryCenterAsked(@RequestParam Map<String, Object> params, HttpServletRequest request){
		List<AreaEntity> list = new ArrayList<AreaEntity>();
		if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			list = ControllerTool.getSessionInfo(request).getSuperAndSubArea();
		}else {
			list = ControllerTool.getSessionInfo(request).getCurrentAndSubArea();
		}
			return list;
	}
	//办各个办案中心入区嫌疑人状态
	@RequestMapping(value = "/queryPersonType")
	@ResponseBody
	public Map<String,Integer> queryPersonType(HttpServletRequest request) throws Exception {
		//获得数据集合
		int areaId=0;
		String str="";
		if(request.getParameter("areaId").equals("null")){
			str = ControllerTool.getAreasInSql(" s.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
		}else{
			if(request.getParameter("areaId")==null){
				areaId = ControllerTool.getCurrentAreaID(request);
			} else{
				areaId=Integer.parseInt(request.getParameter("areaId")) ;
			}
		}
		List<SerialEntity> list = consoleService.queryDetainByAreaId(areaId,str);
		Map<Integer, Map<String, Integer>> allMap = new HashMap<Integer, Map<String, Integer>>();
		//遍历获取每个人员信息
		int houwenkaishi = 0;
		int shenxunkaishi = 0;
		int linshichuqu = 0;
		int qita=0;
		for (SerialEntity SerialEntity : list) {
			// 进行判断
			if (SerialEntity.getStatus() == 3) {
				houwenkaishi++;
			} else if (SerialEntity.getStatus() == 6) {
				shenxunkaishi++;
			} else if (SerialEntity.getStatus() == 10) {
				linshichuqu++;
			} else {
				qita++;
			}
		}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("安检/信采/存物/其他", qita);
			map.put("看押中", houwenkaishi);
			map.put("审讯中", shenxunkaishi);
			map.put("临时出区", linshichuqu);
			return map;
	}
	@RequestMapping("/queryCountByAreaId")
	@ResponseBody
	public String queryCountByAreaId(HttpServletRequest request) throws Exception {
		int areaId=0;
		String str="";
		if(request.getParameter("areaId").equals("null")){
			str = ControllerTool.getAreasInSql(" r.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
		}else{
			if(request.getParameter("areaId")==null){
				areaId = ControllerTool.getCurrentAreaID(request);
			} else{
				areaId=Integer.parseInt(request.getParameter("areaId")) ;
			}
		}
		Map<String,Integer> map = consoleService.queryCountByAreaId(areaId,str);
		Number  a = (Number)map.get("num");
		Number  b = (Number)map.get("volume");
		String p;
		if(a.intValue()!=0&&b.intValue()!=0){
			float count = (a.floatValue()/b.floatValue())*100;
			logger.info("__________"+count);
			DecimalFormat format=new DecimalFormat("###.##");
			p=format.format(count);
			logger.info("__________"+p);
		}else{
			p = "0";
		}
		return p;
	}

	@RequestMapping("/queryRenByAreaId")
	@ResponseBody
	public List<Map<String,Integer>> queryRenByAreaId(HttpServletRequest request) throws Exception {
		int areaId=0;
		String str="";
		if(request.getParameter("areaId").equals("null")){
			str = ControllerTool.getAreasInSql(" ints.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
		}else{
			if(request.getParameter("areaId")==null){
				areaId = ControllerTool.getCurrentAreaID(request);
			} else{
				areaId=Integer.parseInt(request.getParameter("areaId")) ;
			}
		}
		List<Map<String,Integer>> map = consoleService.queryRenByAreaId(areaId,str);
		return map;
	}

	@RequestMapping("/queryChuquByDay")
	@ResponseBody
	public Map<String,Integer> queryChuquByDay(HttpServletRequest request) throws Exception {
		int areaId=0;
		String str="";
		if(request.getParameter("areaId").equals("null")){
			str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
		}else{
			if(request.getParameter("areaId")==null){
				areaId = ControllerTool.getCurrentAreaID(request);
			} else{
				areaId=Integer.parseInt(request.getParameter("areaId")) ;
			}
		}
		Map<String,Integer> map = consoleService.queryChuquByDay(areaId,str);
		return map;
	}

	@RequestMapping("/queryLinshiByDay")
	@ResponseBody
	public Map<String,Integer> queryLinshiByDay(HttpServletRequest request) throws Exception {
		int areaId=0;
		String str="";
		if(request.getParameter("areaId").equals("null")){
			str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
		}else{
			if(request.getParameter("areaId")==null){
				areaId = ControllerTool.getCurrentAreaID(request);
			} else{
				areaId=Integer.parseInt(request.getParameter("areaId")) ;
			}
		}
		Map<String,Integer> map = consoleService.queryLinshiByDay(areaId,str);
		return map;
	}

	//已预约
	@RequestMapping(value = "/countOrderRequest")
	@ResponseBody
	public Map<String, Object> countOrderRequest(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("interrogateAreaId", (request.getParameter("areaId")!=null)?request.getParameter("areaId"):ControllerTool.getCurrentAreaID(request));
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		int count = consoleService.countOrderRequest(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", count);
		return result;
	}

	@RequestMapping(value = "/countotherentrance")
	@ResponseBody
	public Map<String, Object> countOtherentrance(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Map<String, Object> param = ControllerTool.mapFilter(params);
		//param.put("interrogateAreaId", (request.getParameter("areaId")!=null)?request.getParameter("areaId"):ControllerTool.getCurrentAreaID(request));
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		int count = consoleService.countOtherentrance(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", count);

		return result;
	}


	//审讯中
	@RequestMapping(value = "/countInterrogateRecord")
	@ResponseBody
	public Map<String, Object> countInterrogateRecord(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("interrogateAreaId", (request.getParameter("areaId")!=null)?request.getParameter("areaId"):ControllerTool.getCurrentAreaID(request));
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		int count = consoleService.countInterrogateRecord(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", count);
		return result;
	}

	//看押中
	@RequestMapping(value = "/countWaitingRecord")
	@ResponseBody
	public Map<String, Object> countWaitingRecord(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("interrogateAreaId", (request.getParameter("areaId")!=null)?request.getParameter("areaId"):ControllerTool.getCurrentAreaID(request));
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		int count = consoleService.countWaitingRecord(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", count);
		return result;
	}

	//0已入区 1已安检 2物品已暂存 3候问开始 4候问结束 5信息已采集 6审讯开始 7审讯结束 8物品已领取 9临时出区返回 10临时出区 11已出区
	@RequestMapping(value = "/countInterrogateSerial")
	@ResponseBody
	public Map<String, Object> countInterrogateSerial(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Map<String, Object> param = ControllerTool.mapFilter(params);
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (params.get("areaId") != null) ? params.get("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		Map<String, Object> result = new HashMap<String, Object>();
		int count = consoleService.countInterrogateSerial(param);
		int count1 = consoleService.countOtherStatus(param);
		result.put("count", count);
		result.put("count1", count1);
		return result;
	}

	//办各个办案中心案件状态
	@RequestMapping(value = "/queryCaseType")
	@ResponseBody
	public Map<String,Integer> queryCaseType(HttpServletRequest request) throws Exception {
		//获得数据集合
		int areaId=0;
		String str="";
		if(request.getParameter("areaId").equals("null")){
			str = ControllerTool.getAreasInSql(" ar.id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
		}else{
			if(request.getParameter("areaId")==null){
				areaId = ControllerTool.getCurrentAreaID(request);
			} else{
				areaId=Integer.parseInt(request.getParameter("areaId")) ;
			}
		}
		List<CaseEntity> list = caseService.listCase(areaId,str);
		Map<Integer, Map<String, Integer>> allMap = new HashMap<Integer, Map<String, Integer>>();
		//遍历获取每个人员信息
		int xsCount = 0;
		int xzCount = 0;
		if (list.size() > 0 ){
			for (CaseEntity caseEntity : list) {
				// 进行判断
				if ( "2".equals(caseEntity.getAjlx()+"")) {
					xsCount++;
				} else if ("1".equals(caseEntity.getAjlx()+"")) {
					xzCount++;
				}
			}
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("刑事案件", xsCount);
		map.put("行政案件", xzCount);
		//map.put("警情案件", jqCount);
		return map;
	}

	//办各个办案中心物品流转状态
	@RequestMapping(value = "/queryCscwType")
	@ResponseBody
	public Map<String,Object> queryCscwType(HttpServletRequest request) throws Exception {
		//获得数据集合
		int areaId=0;
		String str="";
		if(request.getParameter("areaId").equals("null")){
			str = ControllerTool.getAreasInSql(" bb.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
		}else{
			if(request.getParameter("areaId")==null){
				areaId = ControllerTool.getCurrentAreaID(request);
			} else{
				areaId=Integer.parseInt(request.getParameter("areaId")) ;
			}
		}
		List<BelongEntity> list = belongService.getListByBelongDetail(areaId,str);
		System.out.println(list);
		Map<Integer, Map<String, Integer>> allMap = new HashMap<Integer, Map<String, Integer>>();
		//遍历获取物品信息信息
		int wpCount = 0;
		String wpName="";
		if (list.size() > 0){
			for (BelongEntity belongEntity : list) {
				/*	wpName =belongEntity.getName();
				wpCount =belongEntity.getDetailCount();*/
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put(wpName, wpCount);
		map.put("belongList",list);
		System.out.println(map  + "2222222");
		return map;
	}

	//已存物
	@RequestMapping(value = "/countSscwTj")
	@ResponseBody
	public List<Map<String, Object>>  countSscwTj(HttpServletRequest request) {
		List<Map<String, Object>> result  = consoleService.countSscwTj();
		return result;
	}

	//已存物
	@RequestMapping(value = "/countTj")
	@ResponseBody
	public Map<String, Object>  countTj(HttpServletRequest request) {
		Map<String, Object> result  = consoleService.countTj();
		return result;
	}

	//已存物
	@RequestMapping(value = "/countSscw")
	@ResponseBody
	public Map<String, Object> countSscw(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("interrogateAreaId", (request.getParameter("areaId")!=null)?request.getParameter("areaId"):ControllerTool.getCurrentAreaID(request));
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" bb.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		int count = consoleService.countSscw(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", count);
		return result;
	}


	//已移交
	@RequestMapping(value = "/countSsyj")
	@ResponseBody
	public Map<String, Object> countSsyj(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("interrogateAreaId", (request.getParameter("areaId")!=null)?request.getParameter("areaId"):ControllerTool.getCurrentAreaID(request));
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" bb.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		int count = consoleService.countSsyj(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", count);
		return result;
	}

	//已接收
	@RequestMapping(value = "/countSsjj")
	@ResponseBody
	public Map<String, Object> countSsjj(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("interrogateAreaId", (request.getParameter("areaId")!=null)?request.getParameter("areaId"):ControllerTool.getCurrentAreaID(request));
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" bb.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		int count = consoleService.countSsjj(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", count);
		return result;
	}

	//已接收
	@RequestMapping(value = "/countSsjs")
	@ResponseBody
	public Map<String, Object> countSsjs(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("interrogateAreaId", (request.getParameter("areaId")!=null)?request.getParameter("areaId"):ControllerTool.getCurrentAreaID(request));
		String str = "";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" bb.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		int count = consoleService.countSsjs(param);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", count);
		return result;
	}

	/**
	 * 柜门使用率
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBelongCountByAreaId")
	@ResponseBody
	public String queryBelongCountByAreaId(HttpServletRequest request){
		Map<String, Object> param = new HashMap<String, Object>();
		int areaId=0;
		String str="";
		if (request.getParameter("areaId") == null) {
			param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
		} else {
			if (request.getParameter("areaId").equals("null")) {
				str = ControllerTool.getAreasInSql(" bcc.area_id", ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
			} else {
				param.put("areaId", (request.getParameter("areaId") != null) ? request.getParameter("areaId") : ControllerTool.getCurrentAreaID(request));
			}
		}
		param.put("dataAuth", str);
		Integer countBelongDetail = consoleService.queryUsedCountBelongDetail(param);
		Integer allBelongCount = consoleService.queryAllCountBelongDetail(param);
		/*Number  a = (Number)map.get("num");
		Number  b = (Number)map.get("volume");*/
		String p;
		if(countBelongDetail.intValue()!= 0 && allBelongCount.intValue() != 0){
			float count = (countBelongDetail.floatValue()/allBelongCount.floatValue())*100;
			logger.info("__________"+count);
			DecimalFormat format=new DecimalFormat("###.##");
			p=format.format(count);
			logger.info("柜门使用率__________"+p);
		}else{
			p = "0";
		}
		return p;
	}


}
