package com.zhixin.zhfz.jzgl.controller.ajxx;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhixin.zhfz.bacs.entity.OrderPersonEntity;
import com.zhixin.zhfz.bacs.entity.OrderRequestEntity;
import com.zhixin.zhfz.bacs.entity.PersonEntity;
import com.zhixin.zhfz.bacs.form.OrdeAddPersonForm;
import com.zhixin.zhfz.bacs.services.order.IOrderPersonService;
import com.zhixin.zhfz.bacs.services.order.IOrderRequestService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jzgl.entity.AjxxEntity;
import com.zhixin.zhfz.jzgl.entity.CzrzEntity;
import com.zhixin.zhfz.jzgl.entity.JzxxEntity;
import com.zhixin.zhfz.jzgl.entity.XtrzEntity;
import com.zhixin.zhfz.jzgl.form.JzxxForm;
import com.zhixin.zhfz.jzgl.services.ajxx.IAjxxService;
import com.zhixin.zhfz.jzgl.services.jzxx.ICzrzService;
import com.zhixin.zhfz.jzgl.services.jzxx.IJzxxDetailService;
import com.zhixin.zhfz.jzgl.services.jzxx.IJzxxService;
import com.zhixin.zhfz.jzgl.services.jzxx.IXtrzService;

/**
 * @author xdp
 */
@Controller
@RequestMapping("/zhfz/jzgl/ajxx")
public class AjxxController {

	private static Logger logger = Logger.getLogger(AjxxController.class);
	SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private IAjxxService ajxxService;
	
	@Autowired
	private IJzxxService jzxxService;

	@Autowired
	private IJzxxDetailService jzxxDetailService;

	@Autowired
	private ICzrzService czrzService;
	
	@Autowired
	private IXtrzService xtrzService;

	@Autowired
	private IOrderRequestService orderRequestService;

	@Autowired
	private IPersonService personService;

	@Autowired
	private IOrderPersonService orderPersonService;
	
	@RequestMapping(value = "/listAjxx")
	@ResponseBody
	public Map<String, Object> listAjxx(@RequestParam Map<String,Object> param,HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===param===="+param);
		Map<String, Object> map = ControllerTool.mapFilter(param);
		map.put("usePage", true);
		map.put("ajmc", param.get("ajmc"));
		map.put("ajlb", param.get("ajlb"));
		map.put("zbmj", param.get("zbmj"));
		map.put("badw", param.get("badw"));
		map.put("rkzt", param.get("rkzt"));
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


	@RequestMapping(value = "/addJzxx")
	@ResponseBody
	public JzxxEntity addJzxx(@RequestBody JzxxForm form,String resource, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("///////////////////////////////"+form);
		try {
			form.setJzms(form.getJzms().replaceAll(" ", ""));
			System.err.println("///////////////////////////////"+form);
			JzxxEntity entity = new JzxxEntity();
			PropertyUtils.copyProperties(entity, form);
			entity.setJzzt(0);
		    Long jzbh= 0L;
		    List<String> list1 = jzxxService.getGzbhByAjId(entity.getAjxxId());
		    if(list1.size()==0){
			   jzbh=1L;
		    }else{
		    	List<String> jzbhList = jzxxService.getGzbhByAjId(entity.getAjxxId());
				List<Long> list=new ArrayList<Long>();
				for(String str:jzbhList){
					list.add(Long.parseLong(str));
				}
				Long max = Collections.max(list);
			jzbh=max+1L;
		    }
		    UUID uuid = UUID.randomUUID();
		    entity.setUuid(uuid.toString());
		    
		    String jzbhStr=String.valueOf(jzbh);
			DecimalFormat df=new DecimalFormat("00000");
			String jzbhStr2=df.format(Integer.parseInt(jzbhStr));
		    entity.setJzbh(jzbhStr2);
			
		    entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
			this.jzxxService.insertJzxx(entity);
			 
			//修改案件信息
			AjxxEntity ajxxEntity = new AjxxEntity();
			ajxxEntity.setId(entity.getAjxxId());
			ajxxEntity.setAjmc(entity.getAjmc());
			ajxxEntity.setAjbh(entity.getAjbh());
			ajxxEntity.setRkzt("1"); 
			
			if(resource.equals("1")) {//手动填写
				ajxxEntity.setResource("1");
			}else {
				ajxxEntity.setResource("0");
			}
			this.ajxxService.updateRkAjxx(ajxxEntity);
			
//			CzrzMysqlEntity czrzEntity = new CzrzMysqlEntity(entity.getId(), null, (long)4,request,response,entity.getAjxxId());
			CzrzEntity czrzEntity = new CzrzEntity();
			czrzEntity.setAjAjmc(entity.getAjmc());
			czrzEntity.setAjxxId(entity.getAjxxId());
			czrzEntity.setJzxxId(entity.getId());
			czrzEntity.setUserId(Long.valueOf(ControllerTool.getUserID(request)));
			czrzEntity.setGmxxId(entity.getGmxxId());
			czrzEntity.setCzlx((long)4);
			czrzEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
			this.czrzService.insertCzrz(czrzEntity);
			xtrzService.insertXtrz(new XtrzEntity((long)1, entity,request,response));
			//删除该用户所选类别下的卷宗
			jzxxDetailService.removeJzxxDetailByYhId(entity.getId(), form.getJzlx());
			//获得所选卷宗字符串
			 String str = form.getJzzlList();
			 if(str!=null && !"".equals(str.trim())){
				// 在每个空格字符处进行分解。
				 String[] s = str.split(",");
				 //遍历分解后的数组
				 for(int i=0;i<s.length;i++){
					//卷宗选择集合
					Long jz_id = Long.parseLong(s[i]);
					//逐个添加
					jzxxDetailService.addJzxxDetailByYhId(entity.getId(), jz_id,form.getJzlx());
				 }
			 }
			return entity;
		} catch (Exception e) {
			logger.fatal("Error on adding JzxxMysqlEntity!", e);
			return null;
		}
		
	}


	// 添加预约信息
	@RequestMapping(value = "/addPerson")
	@ResponseBody
	public MessageEntity addPerson(@RequestBody OrdeAddPersonForm form, HttpServletRequest request) throws Exception {
		System.err.println(form);
		Integer value = 0;
		OrderRequestEntity entity = new OrderRequestEntity();
		entity.setId(form.getOrderRequestId());
		// 添加预约内容
		try {
			//为op_pid赋值
			SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
			if (sessionInfo.getCurrentOrgPid() != null && sessionInfo.getCurrentOrgPid() != "") {
				entity.setOpPid(sessionInfo.getCurrentOrg().getPid());
			}
			// person
			PersonEntity personEntity = new PersonEntity();
			personEntity.setName(form.getPerson_name());
			personEntity.setCertificateTypeId(form.getPerson_certificate_type());
			personEntity.setCertificateNo(form.getPerson_certificate_no());
			personEntity.setSex(form.getPerson_sex());
			personEntity.setCountry(form.getPerson_country());
			personEntity.setNation(form.getPerson_nation());

			//personEntity.setAreaId(form.getAreaId());
			personEntity.setAreaId(ControllerTool.getCurrentAreaID(request));
			personEntity.setUuid(java.util.UUID.randomUUID().toString());
			personEntity.setIsArrive(0);
			//为op_pid赋值
			String opPid2;
			if (sessionInfo.getCurrentOrg().getOp_pid() != null && sessionInfo.getCurrentOrg().getOp_pid() != "") {
				opPid2 = sessionInfo.getCurrentOrg().getOp_pid();
			} else {
				opPid2 = "0";
			}
			personEntity.setOpPid(opPid2);
			personEntity.setOpUserId(ControllerTool.getUserID(request));
			// orderPerson
			OrderPersonEntity orderPersonEntity = new OrderPersonEntity();

			orderPersonEntity.setJkb(form.getJkb());
			orderPersonEntity.setSfcrgfxdq(form.getSfcrgfxdq()+"");
			orderPersonEntity.setJzyms(form.getJzyms());
			orderPersonEntity.setIsJuvenile(form.getIsJuvenile());
			orderPersonEntity.setIsGravida(form.getIsGravida());
			orderPersonEntity.setGravidaMonth(form.getGravidaMonth());
			orderPersonEntity.setIsGravidaProve(form.getIsGravidaProve());
			orderPersonEntity.setIsDisease(form.getIsDisease());
			orderPersonEntity.setIsDiseaseProve(form.getIsDiseaseProve());
			orderPersonEntity.setPersonType(form.getPerson_personInfo());
			orderPersonEntity.setOther(form.getOther());
			orderPersonEntity.setOrderRequestId(form.getOrderRequestId());

			orderPersonEntity.setZjdz(form.getZjdz());
			orderPersonEntity.setZhdz(form.getZhdz());
			orderPersonEntity.setSfsjyqldaj(form.getSfsjyqldaj());
			orderPersonEntity.setXyrtw(form.getXyrtw());
			orderPersonEntity.setZjfjrq(form.getZjfjrq());
			orderPersonEntity.setJ3gysfyjjcgqk(form.getJ3gysfyjjcgqk()+"");
			orderPersonEntity.setMgsf(form.getMgsf());
			orderPersonEntity.setSfymjjcs(form.getSfymjjcs());

			if (!"".equals(form.getAjbh()) && null != form.getAjbh()) {
				orderPersonEntity.setAjbh(form.getAjbh());
			}

			if (!"".equals(form.getRybh()) && null != form.getRybh()) {
				orderPersonEntity.setRybh(form.getRybh());
			}

			if (!"".equals(form.getAjmc()) && null != form.getAjmc()) {
				orderPersonEntity.setAjmc(form.getAjmc());
			}

			//通过身份证号判断本次预约中嫌疑人是否已经添加
			Map<String, Object> mapInfo = new HashMap();
			mapInfo.put("orderRequestId", form.getOrderRequestId());
			mapInfo.put("certificateNo", form.getPerson_certificate_no());
			int count = orderRequestService.checkCertificateNoInOrder(mapInfo);
			if (count == 0) {
				//插入Person表
				this.personService.insert(personEntity);
				int personId = Integer.parseInt(personEntity.getId().toString());
				//插入Order_person表
				orderPersonEntity.setPersonId(personId);
				this.orderPersonService.insertOrderPerson(orderPersonEntity);
			}
			if (count > 0) {
				return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("本次预约已经存在该嫌疑人").addCallbackData(orderPersonEntity.getOrderRequestId());

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加失败!").addCallbackData("");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!").addCallbackData("");
	}

}