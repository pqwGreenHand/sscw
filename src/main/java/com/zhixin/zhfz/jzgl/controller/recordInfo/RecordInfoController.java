package com.zhixin.zhfz.jzgl.controller.recordInfo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.common.DayUtil;
import com.zhixin.zhfz.jzgl.entity.AjxxEntity;
import com.zhixin.zhfz.jzgl.entity.CrgLogEntity;
import com.zhixin.zhfz.jzgl.entity.CzrzEntity;
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;
import com.zhixin.zhfz.jzgl.entity.XtrzEntity;
import com.zhixin.zhfz.jzgl.form.JzxxForm;
import com.zhixin.zhfz.jzgl.services.ajxx.IAjxxService;
import com.zhixin.zhfz.jzgl.services.jzxx.ICrglogService;
import com.zhixin.zhfz.jzgl.services.jzxx.ICzrzService;
import com.zhixin.zhfz.jzgl.services.jzxx.IJzxxDetailService;
import com.zhixin.zhfz.jzgl.services.jzxx.IJzxxPoliceService;
import com.zhixin.zhfz.jzgl.services.jzxx.IJzxxService;
import com.zhixin.zhfz.jzgl.services.jzxx.IXtrzService;

/**
 * 
 * @author cuichengwei
 */
@Controller
@RequestMapping("/zhfz/jzgl/recordInfo")
public class RecordInfoController {

	private static Logger logger = Logger.getLogger(RecordInfoController.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private IAjxxService ajxxService;

	@Autowired
	private IJzxxService jzxxService;

	@Autowired
	private IJzxxDetailService jzxxDetailService;

	@Autowired
	private IJzxxPoliceService jzxxPoliceService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICzrzService czrzService;
	
	@Autowired
	private IXtrzService xtrzService;
	
	@Autowired
	private ICrglogService crgLogService;

	@RequestMapping(value = "/listAjxx")
	@ResponseBody
	public Map<String, Object> listAjxx(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		map.put("ajmc", param.get("ajmc"));
		map.put("ajlb", param.get("ajlb"));
		map.put("zbmj", param.get("zbmj"));
		map.put("badw", param.get("badw"));
		if(RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			//上级及下级部门
			String sql= "( aj.zbmj_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()
					+" or aj.op_pid like "+ControllerTool.getSessionInfo(request).getSuperAndSubOrgPid()+" )";
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			String sql= "( aj.zbmj_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()
					+" or aj.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()+" )";
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			String sql = " ( aj.zbmj_id="+ControllerTool.getUserID(request)
			+" or aj.cjr="+ControllerTool.getUserID(request)
			+" or locate(',"+ControllerTool.getUserID(request)+",', aj.xbmj_ids))";
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			String sql = " (aj.zbdw_id="+ControllerTool.getCurrentOrgID(request)
			+" or aj.op_pid="+ControllerTool.getSessionInfo(request).getCurrentOrgPid()+" )";
			map.put("dataAuth", sql);
		}
		List<AjxxEntity> datas = this.ajxxService.listAjxx(map);
		int count = this.ajxxService.countAjxx(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		return result;
	}

	@RequestMapping(value = "/jzxxList")
	@ResponseBody
	public Map<String, Object> jzxxList(@RequestParam Map<String, Object> param) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		System.err.println("====------map-----------==========" + map);
		map.put("usePage", true);
		List<JzxxEntity> jzxxList = this.jzxxService.getJzxxByAjxxId(map);
		int count = this.jzxxService.countJzxxByAjxxId(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", jzxxList);
		return result;
	}

	// 根据卷宗id查询其所拥有的卷宗
	@RequestMapping(value = "/queryJzxxDetailById")
	@ResponseBody
	public List<Map<String, Object>> queryJzxxDetailById(Long jzxx_id, Integer jzlb) {
		List<Map<String, Object>> list = jzxxDetailService.queryJzxxDetailByYhId(jzxx_id, jzlb);
		return list;
	}

	// 根据卷宗和id查询其所拥有的卷宗数量
	@RequestMapping(value = "/queryJzxxDetailCountByHyId")
	@ResponseBody
	public Integer queryJzxxDetailCountByHyId(Long jzxx_id, Integer jzlb) {
		System.out.println(jzxx_id);
		System.out.println(jzlb);
		if (jzxx_id == null || jzlb == null) {
			return 0;
		}
		List<Map<String, Object>> list = jzxxDetailService.queryJzxxDetailByYhId(jzxx_id, jzlb);
		Integer count = list.size();
		return count;
	}
	
	/**
	 * 新增卷宗信息
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addJzxx")
	@ResponseBody
	public MessageEntity addJzxx(@RequestBody JzxxForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			form.setJzms(form.getJzms().replaceAll(" ", ""));
			logger.info("///////////////////////////////" + form);
			JzxxEntity entity = new JzxxEntity();
			PropertyUtils.copyProperties(entity, form);
			entity.setJzzt(0);
			Long jzbh = 0L;
			List<String> list1 = jzxxService.getGzbhByAjId(entity.getAjxxId());
			if (list1.size() == 0) {
				jzbh = 1L;
			} else {
				List<String> jzbhList = jzxxService.getGzbhByAjId(entity.getAjxxId());
				List<Long> list = new ArrayList<Long>();
				for (String str : jzbhList) {
					list.add(Long.parseLong(str));
				}
				Long max = Collections.max(list);
				jzbh = max + 1L;
			}
			UUID uuid = UUID.randomUUID();
			entity.setUuid(uuid.toString());

			String jzbhStr = String.valueOf(jzbh);
			DecimalFormat df = new DecimalFormat("00000");
			String jzbhStr2 = df.format(Integer.parseInt(jzbhStr));
			entity.setJzbh(jzbhStr2);

			entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			entity.setOpUserId(ControllerTool.getUserID(request));
			this.jzxxService.insertJzxx(entity);
			CzrzEntity czrzEntity = new CzrzEntity();
			czrzEntity.setAjAjmc(entity.getAjmc());
			czrzEntity.setAjxxId(entity.getAjxxId());
			czrzEntity.setJzxxId(entity.getId());
			czrzEntity.setUserId(Long.valueOf(ControllerTool.getUserID(request)));
			czrzEntity.setGmxxId(entity.getGmxxId());
			czrzEntity.setCzlx((long) 4);
			
			czrzEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			this.czrzService.insertCzrz(czrzEntity);
			xtrzService.insertXtrz(new XtrzEntity((long) 1, entity, request, response));
			// 删除该用户所选类别下的卷宗
			jzxxDetailService.removeJzxxDetailByYhId(entity.getId(), form.getJzlx());
			// 获得所选卷宗字符串
			String str = form.getJzzlList();
			if (str != null && !"".equals(str.trim())) {
				// 在每个空格字符处进行分解。
				String[] s = str.split(",");
				// 遍历分解后的数组
				for (int i = 0; i < s.length; i++) {
					// 卷宗选择集合
					Long jz_id = Long.parseLong(s[i]);
					// 逐个添加
					jzxxDetailService.addJzxxDetailByYhId(entity.getId(), jz_id, form.getJzlx());
				}
			}
			
			for(String policeNo : entity.getPoliceNo().split(",")) {
				if(!StringUtils.isEmpty(policeNo)){
					UserEntity userEntity = userService.queryUserByCertNo(policeNo);
					jzxxPoliceService.addJzxxDetailByJzxxId(entity.getId(), userEntity.getId());
				}				
			}
		} catch (Exception e) {
			logger.fatal("Error on adding JzxxEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("新增失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("新增成功!");
	}

	@RequestMapping(value = "/editJzxx")
	@ResponseBody
	public MessageEntity editJzxx(@RequestBody JzxxForm form, HttpServletRequest request,
			HttpServletResponse response) {
		JzxxEntity entity = new JzxxEntity();
		form.setJzms(form.getJzms().replaceAll(" ", ""));
		try {
			PropertyUtils.copyProperties(entity, form);
			List<JzxxEntity> jlist = new ArrayList<>();
			jlist.add(this.jzxxService.getJzxxById(entity.getId()));
			this.jzxxService.updateJzxx(entity);
			jlist.add(this.jzxxService.getJzxxById(entity.getId()));
			this.xtrzService.insertXtrz(new XtrzEntity((long) 2,jlist, request, response));
			xtrzService.insertXtrz(new XtrzEntity((long) 1, entity,request, response));
			// 删除该用户所选类别下的卷宗
			jzxxDetailService.removeJzxxDetailByJzxxId(entity.getId());
			// 获得所选卷宗字符串
			String str = form.getJzzlList();
			if (str != null && !"".equals(str.trim())) {
				// 在每个空格字符处进行分解。
				String[] s = str.split(",");
				// 遍历分解后的数组
				for (int i = 0; i < s.length; i++) {
					// 卷宗选择集合
					Long jz_id = Long.parseLong(s[i]);
					// 逐个添加
					jzxxDetailService.addJzxxDetailByYhId(entity.getId(), jz_id, form.getJzlx());
				}
			}
			// 删除卷宗下的协办民警
			jzxxPoliceService.deleteJzxxPoliceByjzxxId(entity.getId());
			// 添加卷宗下的协办民警
			for (String policeNo : entity.getPoliceNo().split(",")) {
				if (!"".equals(policeNo)) {
					UserEntity userEntity = userService.queryUserByCertNo(policeNo);
					jzxxPoliceService.addJzxxDetailByJzxxId(entity.getId(), userEntity.getId());
				}
			}
		} catch (Exception e) {
			logger.fatal("Error on edit JzxxMysqlEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改成功!");
	}

	/**
	 * 根据jzxx查询
	 * 
	 * @param jzxxId
	 * @return
	 */
	@RequestMapping(value = "/getPoliceNoByJzxxId")
	@ResponseBody
	public String getPoliceNoByJzxxId(@RequestParam String jzxxId) {
		List<Map<String, Object>> list = jzxxPoliceService.queryJzxxPoliceByJzxxId(Long.valueOf(jzxxId));
		String policeNo = "";
		for (Map<String, Object> map : list) {
			policeNo += map.get("policeNo") + ",";
		}
		return !policeNo.equals("") ? policeNo.substring(0, policeNo.length() - 1) : policeNo;
	}

	/**
	 * 检查警号是否存在
	 * 
	 * @param policeNos
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkPoliceNo")
	@ResponseBody
	public MessageEntity checkPoliceNo(@RequestParam String policeNos, HttpServletRequest request,
			HttpServletResponse response) {
		String msg = "";
		String errorNo = "";
		try {
			for (String policeNo : policeNos.split(",")) {
				UserEntity entity = userService.queryUserByCertNo(policeNo);
				if (entity == null) {
					errorNo += policeNo + ",";
				}
			}
			if (!errorNo.equals("")) {
				msg = "不存在警号:" + errorNo.substring(0, errorNo.length() - 1);
			}
		} catch (Exception e) {
			logger.fatal("Error  checkPoliceNo!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("系统内部错误");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent(msg);

	}
	
	
	@RequestMapping(value = "/getJzxxForQR")
	@ResponseBody
	public JzxxEntity getJzxxForQR(@RequestParam Long id) {
		try {
			JzxxEntity entity = this.jzxxService.getJzxxForQR(id);
			String ajmcJzbh = entity.getAjmc();
			int a = ajmcJzbh.getBytes("GB2312").length;
			int b = a / 16;
			String ajmcJzbhFlag = "";
			if (a % 16 != 0) {
				b = b + 1;
			}
			int c = 0;
			List<String> strList = new ArrayList<>();
			for (int i = 0; i < b; i++) {
				String str = RecordInfoController.bSubstring(ajmcJzbh, 16);
				strList.add(str);

				ajmcJzbh = ajmcJzbh.substring(str.length());

			}
			for (int i = 0; i < strList.size(); i++) {
				ajmcJzbhFlag += strList.get(i) + "*";
			}
			entity.setAjmcJzbhFlag(ajmcJzbhFlag);
			return entity;
		} catch (Exception e) {
			logger.fatal("Error on adding JzxxEntity!", e);
			return null;
		}

	}
	
	@RequestMapping(value = "/getJzxxAndAjxx")
	@ResponseBody
	public Map<String, Object> getJzxxAndAjxx(@RequestParam Map<String,Object> param,HttpServletRequest request) throws Exception{
		Map<String, Object> map = ControllerTool.mapFilter(param);
		System.err.println("====------map-----------=========="+map);
		map.put("usePage", true);
		
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
		    String sql = "(jz.op_pid like "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgPid()
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  jz.user_id=xu.id AND xu.organization_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")"
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  jz.zrr=xu.id AND xu.organization_id "+ControllerTool.getSessionInfo(request).getCurrentAndSubOrgInStr()+")"
					+" )";
		    map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			String sql = "( jz.user_id="+ControllerTool.getUserID(request)
			+" or jz.zrr="+ControllerTool.getUserID(request)
			+" or jz.op_user_id="+ControllerTool.getUserID(request)
			+" or EXISTS(SELECT 1 FROM  jz_jzxx_police jjp WHERE  jz.id=jjp.jzxx_id))";
			map.put("dataAuth", sql);
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本部门
			String sql = "(jz.op_pid="+ControllerTool.getSessionInfo(request).getCurrentOrgPid()
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  jz.user_id=xu.id AND xu.organization_id="+ControllerTool.getCurrentOrgID(request)+")"
					+" or EXISTS (SELECT 1 FROM  xt_user xu WHERE  jz.zrr=xu.id AND xu.organization_id="+ControllerTool.getCurrentOrgID(request)+")"
					+" )";
			map.put("dataAuth", sql);
		}
		
		
		
		List<JzxxEntity> jzxxList = this.jzxxService.getJzxxAndAjxx(map);
		int count = this.jzxxService.countJzxxAndAjxx(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", jzxxList);
		return result;
	}
	
	@RequestMapping(value = "/getJzxxByuuid")
	@ResponseBody
	public MessageEntity getJzztByuuid(@RequestBody JzxxForm form) throws Exception{ 	
		Map<String,Object> map = new HashMap<String,Object>();
		String jzzt = null;
		try {	
			map.put("uuid", form.getUuid().trim());
			List<JzxxEntity> list= this.jzxxService.getJzxxByuuid(map);
			jzzt = list.get(0).getZ();
			map.put("result", list);
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("查询卷宗失败").addCallbackData(map);
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent(jzzt).addCallbackData(map);
	}
	
	@RequestMapping(value = "/addCglog")
	@ResponseBody
	public MessageEntity addCglog(@RequestBody CrgLogEntity form,HttpServletRequest request) throws Exception{
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uuid", form.getUuid().trim());
			map.put("jzzt", form.getJzzt());
			this.jzxxService.updateJzztByuuid(map);
			List<JzxxEntity> list = this.jzxxService.getJzxxByuuid(map);
			if(list != null && list.size() > 0){
				for(JzxxEntity entity : list){
					form.setAjxxId(entity.getAjxxId());
					form.setJzxxId(entity.getId());
				}
			}						
			form.setYqday(0);
			form.setCzlx(form.getJzzt());
			form.setOpUserId(ControllerTool.getUserID(request));
			form.setOpPid(ControllerTool.getCurrentOrgID(request));
			this.crgLogService.insertCglog(form);
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("出柜失败");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("出柜成功");
	}
	
	@RequestMapping(value = "/listCglog")
	@ResponseBody
	public Map<String, Object> listCglog(@RequestParam Map<String,Object> param) throws Exception{
		Map<String, Object> map = ControllerTool.mapFilter(param);
		System.err.println("====------map-----------=========="+map);
		map.put("usePage", true);
		List<CrgLogEntity> list = this.crgLogService.listCglog(map);
		int count = this.crgLogService.countCglog(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", list);
		return result;
	}
	
	@RequestMapping(value = "/addGHlog")
	@ResponseBody
	public MessageEntity addGHlog(@RequestBody CrgLogEntity form,HttpServletRequest request) throws Exception{
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uuid", form.getUuid().trim());
			map.put("jzzt", "1");
			this.jzxxService.updateJzztByuuid(map);
			List<JzxxEntity> list = this.jzxxService.getJzxxByuuid(map);
			if(list != null && list.size() > 0){
				for(JzxxEntity entity : list){
					map.put("jzxxId", entity.getId());
					form.setAjxxId(entity.getAjxxId());
					form.setJzxxId(entity.getId());
				}	
			}
			List<CrgLogEntity> cologList = this.crgLogService.getCglogByJzxxId(map);
			if(cologList != null && cologList.size() > 0){
				form.setCglx(cologList.get(0).getCglx());
			}			
			form.setYqday(0);
			form.setCgsj(0);
			form.setReason("归还");
			form.setCzlx(2L);
			form.setOpUserId(ControllerTool.getUserID(request));
			form.setOpPid(ControllerTool.getCurrentOrgID(request));
			this.crgLogService.insertCglog(form);
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("归还失败");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("归还成功");
	}
	
	@RequestMapping(value = "/addYQCglog")
	@ResponseBody
	public MessageEntity addYQCglog(@RequestBody CrgLogEntity form,HttpServletRequest request) throws Exception{
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uuid", form.getUuid().trim());
			List<JzxxEntity> list = this.jzxxService.getJzxxByuuid(map);
			if(list != null && list.size() > 0){
				for(JzxxEntity entity : list){
					map.put("jzxxId", entity.getId());
					form.setAjxxId(entity.getAjxxId());
					form.setJzxxId(entity.getId());
				}	
			}
			List<CrgLogEntity> cologList = this.crgLogService.getCglogByJzxxId(map);
			if(cologList != null && cologList.size() > 0){
				form.setCglx(cologList.get(0).getCglx());			
				//form.setReason(cologList.get(0).getReason());
			}
			form.setCgsj(form.getYqday());
			form.setCzlx(1L);
			form.setOpUserId(ControllerTool.getUserID(request));
			form.setOpPid(ControllerTool.getCurrentOrgID(request));
			this.crgLogService.insertCglog(form);
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("延期失败");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("延期成功");
	}
	
	@RequestMapping(value = "/getOverTimeCglogByJzxxId")
	@ResponseBody
	public MessageEntity getOverTimeCglogByJzxxId(Long jzxxId) throws Exception{ 	
		Map<String,Object> map = new HashMap<String,Object>();
		int flag = 0;
		try {	
			map.put("jzxxId", jzxxId);
			CrgLogEntity cglogEntity = this.crgLogService.getOverTimeCglogByJzxxId(map);
			if(cglogEntity != null){
				int day = DayUtil.dayUtil(cglogEntity.getCjsj(), new Date());
				if (day > cglogEntity.getCgsj()) {
					flag = 1;
				}
			}
		} catch (Exception e) {
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("发生异常错误");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent(String.valueOf(flag));
	}
	
	@RequestMapping(value = "/printLog")
	@ResponseBody
	public MessageEntity printLog(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		try {

			CzrzEntity entity = new CzrzEntity(new Long(map.get("jzxxId").toString()), null, (long) 0,
					request, response, new Long(map.get("ajxxId").toString()));
			entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			this.czrzService.insertCzrz(entity);
		} catch (Exception e) {
			logger.fatal("Error on edit GmxxEntity!", e);
			return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("系统内部错误");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("存取成功");

	}
	
	public static String bSubstring(String s, int length) throws Exception {

		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始
		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1)

		{
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0)
				i = i - 1;
			// 该UCS2字符是字母或数字，则保留该字符
			else
				i = i + 1;
		}

		return new String(bytes, 0, i, "Unicode");
	}


}