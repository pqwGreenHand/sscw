package com.zhixin.zhfz.jzgl.controller.console;

import java.util.ArrayList;
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

import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.CzrzEntity;
import com.zhixin.zhfz.jzgl.entity.JzlxRateEntity;
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;
import com.zhixin.zhfz.jzgl.services.console.ITjfxService;
import com.zhixin.zhfz.jzgl.services.jzxx.ICzrzService;
import com.zhixin.zhfz.jzgl.services.jzxx.IJzxxService;
/**
 * 统计分析
 *  柜门使用率
 *  排行榜
 * @author xdp
 * 
 *
 */
@Controller
@RequestMapping(value = "/zhfz/jzgl/console")
public class TjfxController {
	private static Logger logger = Logger.getLogger(TjfxController.class);
	
	  @Autowired
	  private ITjfxService tjfxService;
	  
	  @Autowired
	  private ICzrzService czrzService;
	  
	  @Autowired
	  private IJzxxService jzxxService;

	/**
	 * 柜门使用率
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/listGmxxSyl")
    @ResponseBody
    public   Map<String, Map<String, Object>> listGmxxSyl (HttpServletRequest request){
    	logger.info("====柜门使用率===");
        Map<String, Object> mapParam = new HashMap<String, Object>();
        Map<String, Map<String, Object>> allMap = new TreeMap<String, Map<String, Object>>();
        if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
        	String sql = "(jzg.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+""
		            +" or jzg.org_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr()+")";
	        mapParam.put("dataAuth", sql);
		}
        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = "(jzg.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+""
			            +" or jzg.org_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")";
		    mapParam.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			mapParam.put("dataAuth", " (jzg.op_user_id= " + ControllerTool.getUser(request).getId() + " ) ");
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			String sql = "(jzg.org_id= " + ControllerTool.getUser(request).getOrganizationId() + " or jzg.op_pid="
					+ ControllerTool.getSessionInfo(request).getCurrentOrgPid() + ") ";
			mapParam.put("dataAuth", sql);
		}
       List<JzlxRateEntity>  listGmxx =  tjfxService.selectAllGmxx(mapParam);
       List<JzlxRateEntity> userGmxx = tjfxService.selectUsedGmxx(mapParam);
		for (JzlxRateEntity jzlxRateEntity : listGmxx) {
			if (jzlxRateEntity.getJzgId()!=null) {
				for (JzlxRateEntity entity : userGmxx) {
					if (jzlxRateEntity.getJzgId() == entity.getJzgId()) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.clear();
						map.put("org", jzlxRateEntity.getOrganization());
						map.put("count", jzlxRateEntity.getCount());
						map.put("userCount", entity.getUserCount());
						map.put("mc", entity.getMc());
                        map.put("isExist", (jzlxRateEntity.getZxsj()!=null && (System.currentTimeMillis()-jzlxRateEntity.getZxsj().getTime())<(30*24*60*60*1000l))?1:0);
						allMap.put(jzlxRateEntity.getOrganization(), map);
					}
				}
			}
	    }
		logger.info("====柜门使用率allMap==="+allMap);
        return allMap;
    }
	
    /**
	 * 办案单位存取总次数-查询卷宗存取数量
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listJzCqCount")
	@ResponseBody
	public Map<String, Map<Long, Object>> listJzCqCount(@RequestParam Long id, @RequestParam Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Map<Long, Object>> mapAll = new HashMap<String, Map<Long, Object>>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		if (id != 0) {
			map.put("jzgId", id);
		} else {
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
				map.put("dataAuth", " cz.op_pid= " + ControllerTool.getSessionInfo(request).getCurrentOrgPid());
			}
		}
		List<CzrzEntity> crQcCount = this.czrzService.listJzCrQcCount(map);
		List<String> list = new ArrayList<String>();
		int count = 0;
		for (int i = 0; i < crQcCount.size(); i++) {
			count = 0;
			CzrzEntity cr = crQcCount.get(i);
			if (mapAll.containsKey(cr.getOrganization())) {
				mapAll.get(cr.getOrganization()).put(cr.getCzlx(), cr.getCount());
				count += cr.getCount();
			} else {
				Map<Long, Object> temp = new HashMap<>();
				temp.put(cr.getCzlx(), cr.getCount());
				mapAll.put(cr.getOrganization(), temp);
				count += cr.getCount();
			}
		}
		System.err.println(mapAll);
		return mapAll;
	}
	
	/**
	 * 民警存取总次数-查询卷宗存取数量
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listMjCount")
	@ResponseBody
	public Map<String, Map<Long, Object>> listAllMj(@RequestParam Long id, @RequestParam Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Map<Long, Object>> mapAll = new HashMap<String, Map<Long, Object>>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		logger.info("------------" + id);
		if (id != 0) {
			map.put("jzgId", id);
		} else {
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
				map.put("dataAuth", " cz.op_pid= " + ControllerTool.getSessionInfo(request).getCurrentOrgPid());
			}
		}
		List<CzrzEntity> mjQcCount = this.czrzService.listMjCount(map);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < mjQcCount.size(); i++) {
			CzrzEntity cr = mjQcCount.get(i);
			System.out.printf("cr.getRealName()" + cr.getRealName());
			if (mapAll.containsKey(cr.getRealName())) {
				mapAll.get(cr.getRealName()).put(cr.getCzlx(), cr.getCount());
			} else {
				Map<Long, Object> temp = new HashMap<>();
				temp.put(cr.getCzlx(), cr.getCount());
				mapAll.put(cr.getRealName(), temp);
			}
		}
		return mapAll;
	}
	
	/**
	 * 办案单位告警总数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dwGj")
	@ResponseBody
	public Map<String, Map<String, Object>> dwGj(HttpServletRequest request) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		  Map<String, Map<String, Object>> allMap = new TreeMap<String, Map<String, Object>>();
		  if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				//上级及下级部门
			  String sql =" org.p_id like "+ ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid();
				mapParam.put("dataAuth", sql);
			}
		  if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 公安领导-本部门及下级部门
			    String sql =" org.p_id like "+ ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid();
				mapParam.put("dataAuth", sql);
			}
			if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 办案人员-本人
				String sql = " org.id="+ControllerTool.getUser(request).getOrganizationId();
				mapParam.put("dataAuth", sql);
			}
			if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 办案人员-本部门
				String sql = " org.id="+ControllerTool.getUser(request).getOrganizationId();
				mapParam.put("dataAuth", sql);
			}
		// 查询告警信息
		List<Map<String, String>> list = tjfxService.queryAllDwGj(mapParam);
		int count =0;
		for (Map<String, String> map : list) {
			count=0;
			if (allMap.containsKey(map.get("organization"))) {
				allMap.get(String.valueOf(map.get("organization"))).put(String.valueOf(map.get("xxlx")), Integer.parseInt(String.valueOf(map.get("count"))));
				count+=Integer.parseInt(String.valueOf(map.get("count")));
			} else {
				Map<String, Object> temp = new HashMap<>();
				temp.put(String.valueOf(map.get("xxlx")), Integer.parseInt(String.valueOf(map.get("count"))));
				allMap.put(String.valueOf(map.get("organization")), temp);
				count+=Integer.parseInt(String.valueOf(map.get("count")));
			}
		}
		System.err.println(allMap);
		return allMap;
	}
	
	/**
	 * 民警告警总数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mjGj")
	@ResponseBody
	public Map<String, Map<String, Object>> mjGj(HttpServletRequest request) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		  Map<String, Map<String, Object>> allMap = new TreeMap<String, Map<String, Object>>();
		  if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				//上级及下级部门
			  String sql= " USER.organization_id "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgInStr();
				mapParam.put("dataAuth", sql);
			}
		  
		  if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 公安领导-本部门及下级部门
				// 公安领导-本部门及下级部门
			 
				String sql= " USER.organization_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr();
				mapParam.put("dataAuth", sql);
			}
			if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 办案人员-本人
				String sql = " USER.id="+ControllerTool.getUserID(request);
				mapParam.put("dataAuth", sql);
			}
			if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
				// 办案人员-本部门
				String sql = " USER.organization_id="+ControllerTool.getUser(request).getOrganizationId();
				mapParam.put("dataAuth", sql);
			}
		// 查询告警信息
		List<Map<String, String>> list = tjfxService.queryAllMjGj(mapParam);
		int count =0;
		for (Map<String, String> map : list) {
			count=0;
			if (allMap.containsKey(map.get("realName"))) {
				allMap.get(String.valueOf(map.get("realName"))).put(String.valueOf(map.get("xxlx")), Integer.parseInt(String.valueOf(map.get("count"))));
				count+=Integer.parseInt(String.valueOf(map.get("count")));
			} else {
				Map<String, Object> temp = new HashMap<>();
				temp.put(String.valueOf(map.get("xxlx")), Integer.parseInt(String.valueOf(map.get("count"))));
				allMap.put(String.valueOf(map.get("realName")), temp);
				count+=Integer.parseInt(String.valueOf(map.get("count")));
			}
		}
		System.err.println(allMap);
		return allMap;
	}
	
	
	/**
	 * 单位卷宗新增次数
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listJzXzCsCount")
	@ResponseBody
	public Map<String,  Object> listJzXzCsCount(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Map<Long, Object>> mapAll = new HashMap<String, Map<Long, Object>>();
		Map<String, Object> map = ControllerTool.mapFilter(param);
		
		if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			String sql = "org.p_id like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid();
			map.put("dataAuth", sql);
		}
		
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			String sql = "org.p_id like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid();
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			String sql = " org.id="+ControllerTool.getUser(request).getOrganizationId();
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			String sql = " org.id="+ControllerTool.getUser(request).getOrganizationId();
			map.put("dataAuth", sql);
		}
		List<JzxxEntity> jzXzCount = this.jzxxService.listJzXzCount(map);
		System.err.println(jzXzCount);
		for (int i = 0; i < jzXzCount.size(); i++) {
				result.put(jzXzCount.get(i).getOrganization(), jzXzCount.get(i).getCount());
		}
		System.err.println(request);
		return result;
	 
	}
	 
}
