package com.zhixin.zhfz.bacs.controller.combobox;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.bacs.entity.RoomTypeEntity;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.bacs.services.combobox.IComboboxService;
import com.zhixin.zhfz.bacs.services.order.IOrderRequestService;
import com.zhixin.zhfz.bacs.services.room.IRoomTypeService;
import com.zhixin.zhfz.common.entity.CombogridEntity;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.combogrid.ICombogridService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/combobox")
public class BacsComboboxController {

	private static final Logger logger = LoggerFactory.getLogger(BacsComboboxController.class);

	@Autowired
	private IComboboxService comboboxService;

	@Autowired
	private ICombogridService combogridService;

	@Autowired
	private IRoomTypeService roomTypeService;

	@Autowired
	private IAreaService areaService;
	@Autowired
	private IOrderRequestService orderRequestService;


	// 查询所有部门信息---w.xb
	@RequestMapping(value = "/listAllOrganizationNameCombobox")
	@ResponseBody
	public List<ComboboxEntity> listAllOrganizationNameCombobox(@RequestParam Map<String, Object> params,
																HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------listAllOrganizationNameCombobox-----------------");
		List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
		boolean flag = true;

		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 办案人员-本人
			params.put("dataAuth", " o.op_user_id = " + ControllerTool.getUserID(request) );
		}  else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 办案场所-本办案场所
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本办案场所及下级办案场所
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 上级办案场所及其下级办案场所
		}else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门
			params.put("dataAuth", "o.op_pid = " + sessionInfo.getCurrentOrgPid()+" or o.p_id ="+sessionInfo.getCurrentOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 法制人员-上级部门及其下级部门
			params.put("dataAuth", "o.op_pid like  " + sessionInfo.getSuperAndSubOrgPid()+" or o.p_id like"+sessionInfo.getSuperAndSubOrgPid());
		} else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuthForBacsConf(request))){
			//全部
			flag = true;
		}else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 公安领导-本部门及下级部门
			params.put("dataAuth", "o.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()+" or o.p_id like"+sessionInfo.getCurrentAndSubOrgPid());
		}

		if (flag) {
			list = this.comboboxService.listAllOrganizationName(params);
		}
		return list;
	}
	// 查询所有部门信息---不要加权限
	@RequestMapping(value = "/listAllOrganizationNameComboboxWithNo")
	@ResponseBody
	public List<ComboboxEntity> listAllOrganizationNameComboboxWithNo(@RequestParam Map<String, Object> params,
																HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------listAllOrganizationNameCombobox-----------------");
		List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
		List<ComboboxEntity> orgList = new ArrayList<ComboboxEntity>();
		list = this.comboboxService.listAllOrganizationName(params);
		for(ComboboxEntity c:list){
			if(c.getValue() != null && c.getValue().indexOf("系统管理" )< 0){
				orgList.add(c);
			}
		}
		return list;
	}
	// 查询所有部门信息---w.xb
	@RequestMapping(value = "/listAllOrganization")
	@ResponseBody
	public List<ComboboxEntity> listAllOrganization(@RequestParam Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------listAllOrganizationNameCombobox-----------------");
		List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
			list = this.comboboxService.listAllOrganization(params);
		return list;
	}

	@RequestMapping(value = "/listAllOrganizationCode")
	@ResponseBody
	public List<ComboboxEntity> listAllOrganizationCode(@RequestParam Map<String, Object> params,
													HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------listAllOrganizationNameCombobox-----------------");
		List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
		list = this.comboboxService.listAllOrganizationCode(params);
		return list;
	}

	// 查询所有部门信息---w.xb
	@RequestMapping(value = "/listAllOrganization1")
	@ResponseBody
	public List<ComboboxEntity> listAllOrganizationName(@RequestParam Map<String, Object> params,
													HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------listAllOrganizationNameCombobox-----------------");
		logger.info("-----------------RoleEntity.DATA_AUTH_DOWN_CASCADEORG-----------------"+RoleEntity.DATA_AUTH_DOWN_CASCADEORG);
		logger.info("-----------------RoleEntity.RoleEntity.DATA_AUTH_SELF-----------------"+RoleEntity.DATA_AUTH_SELF);
		logger.info("-----------------listAllOrganizationNameCombobox-----------------"+ControllerTool.getRoleDataAuthForJZ(request));
		logger.info("-----------------listAllOrganizationNameCombobox-----------------"+ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());

		Map<String, Object> map = ControllerTool.mapFilter(params);
//		map.put("organizationId",organizationId);
		if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 公安领导-本部门及下级部门
			// 公安领导-本部门及下级部门
			String sql1 ="";
			List<OrganizationEntity> currentAndSubOrg=ControllerTool.getSessionInfo(request).getCurrentAndSubOrg();
			for (OrganizationEntity organizationEntity : currentAndSubOrg) {
				sql1+=Integer.toString(organizationEntity.getId())+",";
			}
			logger.info("---==----"+sql1);
			String sql= sql1.substring(0,sql1.length()-1);
			map.put("dataAuth", "id " + "in("+sql+")");
		}
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 办案人员-本人
			map.put("dataAuth", " (id= " + ControllerTool.getUser(request).getOrganizationId() + " ) ");
			logger.info("-----------------listAllOrganizationNameCombobox-----------------"+"2222222222");
		}
		if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForJZ(request))) {
			// 本部门
			map.put("dataAuth", " (id= " + ControllerTool.getUser(request).getOrganizationId() + " ) ");
		}
		List<ComboboxEntity> list = this.comboboxService.listAllOrganization1(map);
		return list;
	}

	// 查询所有RoomGroup
	@RequestMapping(value = "/listAllRoomNameCombobox")
	@ResponseBody
	public List<ComboboxEntity> listAllRoomNameCombobox(@RequestParam Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------interrogateSerialCombobox-----------------");
		return this.comboboxService.listAllRoomName(params);
	}

	// 查询本办案中心的所有房间
	@RequestMapping(value = "/listRoomBySerial")
	@ResponseBody
	public List<ComboboxEntity> listRoomBySerial(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
		logger.info("-----------------interrogateSerialCombobox-----------------");
		return this.comboboxService.listRoomBySerial(params);
	}

	// 新增编辑room时选择room类型的下拉菜单
	@RequestMapping(value = "/roomtype")
	@ResponseBody
	public List<ComboboxEntity> roomtype() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		List<RoomTypeEntity> list = this.roomTypeService.listAllroomType(params);
		logger.info("++++++++getroomtype++++++getroomtype=" + list.size());
		List<ComboboxEntity> ll = new ArrayList<ComboboxEntity>();
		for (RoomTypeEntity s : list) {
			String sid = String.valueOf(s.getId());
			ComboboxEntity b = new ComboboxEntity(sid, s.getName());
			ll.add(b);
		}
		return ll;
	}



	/**
	 * 办案场所 下拉框
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/comboArea")
	@ResponseBody
	public List<AreaEntity> comboArea(HttpServletRequest request) {
		List<AreaEntity> list = new ArrayList<AreaEntity>();
		Map<String, Object> map = new HashMap<>();
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员-本人
			map.put("dataAuth", " ( ia.op_user_id=" + ControllerTool.getUserID(request) + " or o.op_user_id="
					+ ControllerTool.getUserID(request) + " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", " ia.id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", " ia.id  " + sessionInfo.getCurrentAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", " ia.id  " + sessionInfo.getSuperAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth"," ( ia.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or o.p_id like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth"," ( ia.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or o.p_id like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
			// 本部门
			map.put("dataAuth"," ( ia.op_pid = " + sessionInfo.getCurrentOrgPid()
					+ " or o.p_id = " +  sessionInfo.getCurrentOrgPid()
					+ " ) ");
		}

		list = this.areaService.listAllArea(map);
		return list;
	}

	/**
	 * 查询办案场所
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllInterrogateAreaName")
	@ResponseBody
	public List<ComboboxEntity> getAllInterrogateAreaName(@RequestParam Map<String, Object> params,
														  HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------getAllInterrogateAreaName-----------------");
		List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
		boolean flag = true;
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 办案人员-本人
			params.put("dataAuth", " o.op_user_id = " + ControllerTool.getUserID(request) );
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 本部门
			params.put("dataAuth", "ia.op_pid = " + sessionInfo.getCurrentOrgPid()+" or o.p_id ="+sessionInfo.getCurrentOrgPid());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 法制人员-上级部门及其下级部门
			params.put("dataAuth", "ia.op_pid like  " + sessionInfo.getSuperAndSubOrgPid()+" or o.p_id like"+sessionInfo.getSuperAndSubOrgPid());
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuthForBacsConf(request))) {
			// 公安领导-本部门及下级部门
			params.put("dataAuth", "ia.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()+" or o.p_id like"+sessionInfo.getCurrentAndSubOrgPid());
		} else {
			flag = true;
			// return null
		}
		if (flag) {
			list = this.comboboxService.getAllInterrogateAreaName(params);
		}
		return list;
	}

	@RequestMapping("/listCrimeDefine")
	@ResponseBody
	public List<ComboboxEntity> listCrimeDefine(@RequestParam Map<String, Object> params){
		logger.info("-----------------listCrimeDefine-----------------");
		Map<String, Object> map = ControllerTool.mapFilter(params);
		return comboboxService.listCrimeDefine(map);
	}

	@RequestMapping("/listKnowledgeCrime")
	@ResponseBody
	public List<ComboboxEntity> listKnowledgeCrime(@RequestParam Map<String, Object> params){
		logger.info("-----------------listKnowledgeCrime-----------------");
		Map<String, Object> map = ControllerTool.mapFilter(params);
		return comboboxService.listKnowledgeCrime(map);
	}


	/**
	 * 案件下，人员下拉框
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listPerson")
	@ResponseBody
	public List<ComboboxEntity> listPerson(@RequestParam("caseId") String caseId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("caseId",caseId);
		List<ComboboxEntity> comboboxEntities = comboboxService.listPersonByCaseId(params);
//		for (int i = 0; i < comboboxEntities.size(); i++) {
//			comboboxEntities.get(i).setId((i + 1) + "");
//		}
		return comboboxEntities;
	}

	/**
	 * 法律法规 下拉框
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listLawType")
	@ResponseBody
	public List<ComboboxEntity> listLawType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		List<ComboboxEntity> comboboxEntities = comboboxService.listLawType(params);
		for (int i = 0; i < comboboxEntities.size(); i++) {
			comboboxEntities.get(i).setId((i + 1) + "");
		}
		return comboboxEntities;
	}

	/**
	 * 法律名称 下拉框
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listLawName")
	@ResponseBody
	public List<ComboboxEntity> listLawName(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> params = new HashMap<String,Object>();
		List<ComboboxEntity> comboboxEntities = comboboxService.listLawName(params);
		for (int i = 0; i < comboboxEntities.size(); i++){
			comboboxEntities.get(i).setId((i+1) + "");
		}
		return comboboxEntities;
	}

	/**
	 * 醒酒 下拉框
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listSerial")
	@ResponseBody
	public List<ComboboxEntity> listSerial(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员-本人
			map.put("dataAuth", " ( s.in_register_user_id=" + ControllerTool.getUserID(request) + " or s.out_register_user_id="
					+ ControllerTool.getUserID(request)
					+" or s.send_user_id = " + ControllerTool.getUserID(request)
					+" or p.op_user_id = " + ControllerTool.getUserID(request)
					+" or f.police_id = " + ControllerTool.getUserID(request)
					+" or c.cjr = " + ControllerTool.getUserID(request)
					+" or c.zbmj_id = " + ControllerTool.getUserID(request)
					+ " or locate('," + ControllerTool.getUserID(request) + ",', c.xbmj_ids)"
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", "( s.area_id =" + ControllerTool.getCurrentAreaID(request)
				+" or p.area_id = " + ControllerTool.getCurrentAreaID(request)
			+" ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", " ( s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
			+" or p.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
			+" ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", "( s.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
				+" or p.area_id " + sessionInfo.getSuperAndSubAreaInStr()
			+" ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth"," ( s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or p.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " or c.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth"," ( s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or p.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " or c.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
			// 本部门
			map.put("dataAuth"," ( s.op_pid = " + sessionInfo.getCurrentOrgPid()
					+ " or p.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " or c.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " ) ");
		}
		//map.put("areaId",1);
		List<ComboboxEntity> list = comboboxService.listSerial(map);
		return list;
	}
	@RequestMapping(value = "listSerialPolice")
	@ResponseBody
	public List<ComboboxEntity> listSerialPolice(HttpServletRequest request,Long caseId) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员-本人
			map.put("dataAuth", " ( p.op_user_id=" + ControllerTool.getUserID(request) + " or p.police_id ="
					+ ControllerTool.getUserID(request)
					+" or u.op_user_id = " + ControllerTool.getUserID(request)
					+" or u.id = " + ControllerTool.getUserID(request)
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", "p.area_id =" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", "  p.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", "p.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth"," ( p.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or u.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth"," ( p.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or u.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
			// 本部门
			map.put("dataAuth"," ( p.op_pid = " + sessionInfo.getCurrentOrgPid()
					+ " or u.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " ) ");
		}
		int areaId = ControllerTool.getCurrentAreaID(request);
		//map.put("areaId",areaId);
		map.put("caseId",caseId);
		List<ComboboxEntity> list = comboboxService.listSerialPolice(map);
		return list;
	}

	/**
	 * 查询证件类型
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/certificateTypes")
	@ResponseBody
	public List<ComboboxEntity> getCertificateTypeCombobox(@RequestParam Map<String, Object> params,
														   HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("++++++++getCertificateTypeCombobox+++++++++++");
		return this.comboboxService.listCertificateType(params);
	}

	/**
	 * 查询所有的无名氏
	 */
	@RequestMapping(value = "/listWMSCount")
	@ResponseBody
	public int listWMSCount(@RequestParam Map<String, Object> map, HttpServletRequest request) throws Exception {
		int  countwms =	orderRequestService.countWuMs();
		return countwms;
	}

	/**
	 * 查询笔录答问模板
	 */
	@RequestMapping(value = "/listRecordTemplate")
	@ResponseBody
	public List<ComboboxEntity> listRecordTemplate(@RequestParam Map<String, Object> map, HttpServletRequest request) throws Exception {
		return comboboxService.listRecordTemplate(map);
	}



	public static List<ComboboxEntity> AllCrimeTypeByNatureList;
	public static List<ComboboxEntity> XSCrimeTypeByNatureList;// 刑事
	public static List<ComboboxEntity> XZCrimeTypeByNatureList;// 行政
	// 展示性质下的罪名
	@RequestMapping(value = "/listcrimetypebynature")
	@ResponseBody
	public List<ComboboxEntity> listCrimeTypeByNature(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("-----------------listCrimeType-----------------");
		String nature = request.getParameter("nature");
		Map<String, Object> params = new HashMap<String, Object>();
		if (nature != null && nature != "") {
			if(nature.equals("0")){
				nature="罪名";
			}else if(nature.equals("1")){
				nature="行政案由";
			} else if(nature.equals("刑事案件")){
				nature="罪名";
			}else if(nature.equals("行政案件")){
				nature="行政案由";
			}
			params.put("nature", nature);
		}
		if (nature != null && nature != "") {
			if (nature.equals("罪名")) {
				if (XSCrimeTypeByNatureList != null && XSCrimeTypeByNatureList.size() > 0) {

					return XSCrimeTypeByNatureList;
				}
			} else {

				if (XZCrimeTypeByNatureList != null && XZCrimeTypeByNatureList.size() > 0) {
					return XZCrimeTypeByNatureList;
				}
			}
		} else {

			if (AllCrimeTypeByNatureList != null && AllCrimeTypeByNatureList.size() > 0) {
				return AllCrimeTypeByNatureList;
			}
		}

		if (nature != null && nature != "") {
			if (nature.equals("罪名")) {
				XSCrimeTypeByNatureList = this.comboboxService.listCrimeTypeByNature(params);
				return XSCrimeTypeByNatureList;

			} else {

				XZCrimeTypeByNatureList = this.comboboxService.listCrimeTypeByNature(params);
				return XZCrimeTypeByNatureList;

			}
		} else {
			AllCrimeTypeByNatureList = this.comboboxService.listCrimeTypeByNature(params);
			return AllCrimeTypeByNatureList;

		}
	}
	
	// 入区获取所有手环
	@RequestMapping(value = "/getallcuff")
	@ResponseBody
	public List<ComboboxEntity> getAllCuffNoCombobox(@RequestParam Map<String, Object> params,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("-----------------getAllCuffNoCombobox-----------------");
		List<ComboboxEntity> list = new ArrayList<ComboboxEntity>();
		boolean flag = true;
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员
			params.put("dataAuth", " area_id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所
			params.put("dataAuth", " area_id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导与管理员
			params.put("dataAuth", ControllerTool.getAreasInSql(" area_id",
					ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员
			params.put("dataAuth", ControllerTool.getAreasInSql(" area_id",
					ControllerTool.getSessionInfo(request).getSuperAndSubArea()));
		} else if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
			flag = true;
		} else {
			flag = false;
			// return null
		}
//		if (flag) {
			list = this.comboboxService.getAllCuff(params);
//		}

		return list;
	}


	/**
	 * 获取在押人员(候审室) 入区编号、姓名、身份证号码等
	 * @return
	 */
	@RequestMapping("/getAllDetainSerialNo")
	@ResponseBody
	public List<CombogridEntity> getAllDetainSerialNo(@RequestParam Map<String, Object> params , HttpServletRequest request){
		logger.info("-----------------getAllDetainSerialNo-----------------");
		Map<String, Object> map = ControllerTool.mapFilter(params);
		return this.combogridService.getAllDetainSerialNo(map);
	}
	
	@RequestMapping(value = "/listAlarmType")
	@ResponseBody
	public List<ComboboxEntity> listAlarmType(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception{
		List<ComboboxEntity> list = comboboxService.listAlarmType(params);
		return list;
	}

	@RequestMapping(value = "/getOfficer")
	@ResponseBody
	public List<ComboboxEntity> getOfficerCombobox(@RequestParam Map<String, Object> params,
														   HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("++++++++getOfficerCombobox+++++++++++");
		return this.comboboxService.listOfficer(params);
	}

	@RequestMapping(value = "/getOrderContentForEntrance")
	@ResponseBody
	public List<CombogridEntity> getOrderContentForEntrance(@RequestParam Map<String, Object> params,
															HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> map = ControllerTool.mapFilter(params);
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员-本人
			map.put("dataAuth", " ( o.op_user_id=" + ControllerTool.getUserID(request) + " or p.op_user_id="
					+ ControllerTool.getUserID(request)
					+" or bc.cjr = " + ControllerTool.getUserID(request)
					+" or bc.zbmj_id = " + ControllerTool.getUserID(request)
					+" or t.order_user_id = " + ControllerTool.getUserID(request)
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", " ( p.area_id=" + ControllerTool.getCurrentAreaID(request)
					+" or t.area_id = " + ControllerTool.getCurrentAreaID(request)
			+" ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", "(  p.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
				+" or t.area_id " +  sessionInfo.getCurrentAndSubAreaInStr()
			+" ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", " ( p.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
			+" or t.area_id " + sessionInfo.getSuperAndSubAreaInStr()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth"," ( o.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or t.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " or p.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " or bc.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth"," ( o.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or t.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " or p.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " or bc.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
			// 本部门
			map.put("dataAuth"," ( o.op_pid = " + sessionInfo.getCurrentOrgPid()
					+ " or t.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " or p.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " or bc.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " ) ");
		}
		List<CombogridEntity> list = new ArrayList<CombogridEntity>();
		//params.put("areaId", ControllerTool.getCurrentAreaID(request));
		list = combogridService.getOrderContentForEntrance(map);
		System.err.println("list.size()=" + list.size());
		return list;
	}

	// 获取预约人信息
	@RequestMapping(value = "/getAllOrderInfo")
	@ResponseBody
	public List<CombogridEntity> getAllOrderInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,
												 HttpServletResponse response) throws Exception {
		logger.info("-----------------getAllOrderInfo-----------------");
		List<CombogridEntity> list = new ArrayList<CombogridEntity>();
		List<CombogridEntity> listValue = new ArrayList<CombogridEntity>();
		boolean flag = true;
		Map<String, Object> map = ControllerTool.mapFilter(params);
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员-本人
			map.put("dataAuth", " ( orq.order_user_id=" + ControllerTool.getUserID(request)
					+ " or u.id=" + ControllerTool.getUserID(request)
					+ " or u.op_user_id=" + ControllerTool.getUserID(request)
					+ " or cs.cjr=" + ControllerTool.getUserID(request)
					+ " or cs.zbmj_id=" + ControllerTool.getUserID(request)
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", " orq.area_id=" + ControllerTool.getCurrentAreaID(request));
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", " orq.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", " orq.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth"," ( orq.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or u.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " or cs.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth"," ( orq.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or u.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " or cs.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
			// 本部门
			map.put("dataAuth"," ( orq.op_pid = " + sessionInfo.getCurrentOrgPid()
					+ " or u.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " or cs.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " ) ");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 48);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (flag) {
			CombogridEntity tem = new CombogridEntity();
			tem.setId(-1);
			tem.setOrderNo("未预约");
			tem.setName("无");
			tem.setCertificateNo("无");
			listValue.add(tem);
			list = this.combogridService.getAllOrderInfo(params);
			Date beforeTime = df.parse(df.format(calendar.getTime()));
			for (int i = 0; i < list.size(); i++) {
				CombogridEntity obj = list.get(i);
				if (obj.getTimenow().after(beforeTime)) {
					listValue.add(obj);
				}
			}
		}
		return listValue;
	}

	/**
	 * 嫌疑人下拉列表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSerialList")
	@ResponseBody
	public List<ComboboxEntity> getSerialList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
			// 办案人员-本人
			map.put("dataAuth", " ( s.in_register_user_id=" + ControllerTool.getUserID(request) + " or s.out_register_user_id="
					+ ControllerTool.getUserID(request)
					+" or s.send_user_id = " + ControllerTool.getUserID(request)
					+" or p.op_user_id = " + ControllerTool.getUserID(request)
					+" or c.cjr = " + ControllerTool.getUserID(request)
					+" or c.zbmj_id = " + ControllerTool.getUserID(request)
					+ " or locate('," + ControllerTool.getUserID(request) + ",', c.xbmj_ids)"
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 办案场所-本办案场所
			map.put("dataAuth", "( s.area_id =" + ControllerTool.getCurrentAreaID(request)
					+" or p.area_id = " + ControllerTool.getCurrentAreaID(request)
					+" ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 管理员 -本办案场所及下级办案场所
			map.put("dataAuth", " ( s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
					+" or p.area_id " + sessionInfo.getCurrentAndSubAreaInStr()
					+" ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
			// 上级办案场所及其下级办案场所
			map.put("dataAuth", "( s.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
					+" or p.area_id " + sessionInfo.getSuperAndSubAreaInStr()
					+" ) ");
		} else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 公安领导-本部门及下级部门
			map.put("dataAuth"," ( s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
					+ " or p.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " or c.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
			// 法制人员-上级部门及下级部门
			map.put("dataAuth"," ( s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
					+ " or p.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " or c.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
					+ " ) ");
		} else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
			// 本部门
			map.put("dataAuth"," ( s.op_pid = " + sessionInfo.getCurrentOrgPid()
					+ " or p.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " or c.op_pid = " +  sessionInfo.getCurrentOrgPid()
					+ " ) ");
		}
		List<ComboboxEntity> list = comboboxService.getSerialList(map);
		return list;
	}


}
