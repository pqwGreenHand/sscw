package com.zhixin.zhfz.zhag.controller.punish;

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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/zhag/punish")
public class PunishController {

    private static Logger logger = Logger.getLogger(PunishController.class);

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
     * 行政处罚信息列表
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listPunish")
    @ResponseBody
    public Map<String, Object> listPunish(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        map.put("usePage", true);
        List<CscfEntity> datas = this.qzcsService.selectCscf(map);
        int count = this.qzcsService.countCscf(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", datas);
        return result;
    }
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
        map.put("usePage", true);
        List<GjxxEntity> datas = this.gjxxService.selectGjxx(map);
        int count = this.gjxxService.countGjxx(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", datas);
        return result;
    }

    /**
     * 行政案件信息
     * @param param
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/xzajList")
    @ResponseBody
    public Map<String, Object> xzajList(@RequestParam Map<String, Object> param, HttpServletRequest request,
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


    /**
     * 新增行政处罚
     * @param entity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "addXzcf")
    @ResponseBody
    public MessageEntity addXzcf(@RequestBody CscfEntity entity, HttpServletRequest request,
                                 HttpServletResponse response){
        logger.info(entity.toString());
        try {
            entity.setLx(1);//0强制措施  1：行政处罚
            this.qzcsService.addCscf(entity);
        } catch (Exception e) {

            return new MessageEntity().addCode(1).addIsError(true).addTitle("警告").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }
}
