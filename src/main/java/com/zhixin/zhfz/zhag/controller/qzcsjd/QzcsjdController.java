package com.zhixin.zhfz.zhag.controller.qzcsjd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.bacs.entity.PersonEntity;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.CscfEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import com.zhixin.zhfz.zhag.entity.YjxxEntity;
import com.zhixin.zhfz.zhag.services.cscf.ICscfService;
import com.zhixin.zhfz.zhag.services.gjxx.IGjxxService;
import com.zhixin.zhfz.zhag.services.xsajxx.CriminalCaseService;
import com.zhixin.zhfz.zhag.services.yjxx.IYjxxService;

/**
 * 强制措施监督
 * @author xdp
 *
 */

@Controller
@RequestMapping("/zhfz/zhag/qzcsjd")
public class QzcsjdController {
	private static Logger logger = Logger.getLogger(QzcsjdController.class);
	
	@Autowired
	private IYjxxService yjxxService;
	
	@Autowired
	private IGjxxService gjxxService;
	
	@Autowired
	private ICscfService qzcsService;
	
	@Autowired
	private CriminalCaseService criminalCaseService;
	
    @Autowired
	private IPersonService personService; 
	 
	/**
	 * 预警信息
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listYjxx")
	@ResponseBody
	public Map<String, Object> listYjxx(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		logger.info("==param=="+map);
		map.put("usePage", true);
		List<YjxxEntity> datas = this.yjxxService.selectYjxx(map);
		int count = this.yjxxService.countYjxx(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		return result;
	}

	/**
	 * 告警信息
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listGjxx")
	@ResponseBody
	public Map<String, Object> listGjxx(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		logger.info("==param=="+map);
		map.put("usePage", true);
		List<GjxxEntity> datas = this.gjxxService.selectGjxx(map);
		int count = this.gjxxService.countGjxx(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		return result;
	}
	
	/**
	 * 强制措施信息
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listQzcs")
	@ResponseBody
	public Map<String, Object> listQzcs(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		logger.info("==param=="+map);
		map.put("usePage", true);
		List<CscfEntity> datas = this.qzcsService.selectCscf(map);
		int count = this.qzcsService.countCscf(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		return result;
	}
	
	/**
	 * 新增强制措施
	 * @param entity
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addQzcs")
	@ResponseBody
	public MessageEntity addQzcs(@RequestBody CscfEntity entity, HttpServletRequest request,
			HttpServletResponse response){
		 logger.info(entity.toString());
		try {
			entity.setLx(0);
			this.qzcsService.addCscf(entity);
		} catch (Exception e) {
		 
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
	}
	
	/**
	 * 新增强制措施
	 * @param entity
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editQzcs")
	@ResponseBody
	public MessageEntity editQzcs(@RequestBody CscfEntity entity, HttpServletRequest request,
			HttpServletResponse response){
		 logger.info(entity.toString());
		try {
			this.qzcsService.editCscf(entity);
		} catch (Exception e) {
		    logger.info(e.getMessage());
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("修改失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("修改成功!");
	}
	
	/**
     * 刑事案件信息
     * @param param
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/xsajList")
	@ResponseBody
	public Map<String, Object> xsajList(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		logger.info("==param=="+map);
		map.put("usePage", true);
		List<CriminalAndAdministrationCaseEntity> datas = this.criminalCaseService.xsajList(map);
		int count = this.criminalCaseService.xsajCount(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		return result;
	}
    
    /**
     * 根据案件查询人员信息
     * @param param
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/personsByCase")
	@ResponseBody
	public List<PersonEntity> personsByCase(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = ControllerTool.mapFilter(param);
		logger.info("==param=="+map);
		map.put("usePage", true);
		List<PersonEntity> lists = this.personService.personListByCase(map);
		return lists;
	}
    
    /**
     * 添加嫌疑人
     * @param entity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "addPerson")
	@ResponseBody
	public MessageEntity addPerson(@RequestBody PersonEntity entity, HttpServletRequest request,
			HttpServletResponse response){
		 logger.info(entity.toString());
		try {
			SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
			entity.setOpPid(sessionInfo.getCurrentOrgPid());
			entity.setOpUserId(sessionInfo.getUser().getId());
			entity.setAreaId(sessionInfo.getCurrentArea().getId());
			entity.setIsArrive(1);
			this.personService.insert(entity);
		} catch (Exception e) {
		 
			return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("添加失败!");
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
	}
   
}
