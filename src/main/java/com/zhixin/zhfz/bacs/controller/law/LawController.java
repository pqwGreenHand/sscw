package com.zhixin.zhfz.bacs.controller.law;


import com.zhixin.zhfz.bacs.entity.LawEntity;
import com.zhixin.zhfz.bacs.form.LawForm;
import com.zhixin.zhfz.bacs.services.law.ILawService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.json.XMLTokener.entity;

@Controller
@RequestMapping("/zhfz/bacs/law")
public class LawController {

    private static Logger logger = LoggerFactory.getLogger(LawController.class);

    @Autowired
    private ILawService lawService;

    @Autowired
    private IOperLogService operLogService;


    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-------------------------law/list------------------------------");
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<LawEntity> list = new ArrayList<LawEntity>();
        int total = 0;
        list = this.lawService.list(map);
        total = this.lawService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    /**
     * 插入法律法规
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/addLawEntity")
    @ResponseBody
    public MessageEntity addLawEntity(@RequestBody LawForm lawForm, HttpServletRequest request) {
        logger.info("-------------------------law/addLawEntity------------------------------");
        LawEntity lawEntity = new LawEntity();
        lawEntity.setType(lawForm.getType());
        lawEntity.setName(lawForm.getName());
        lawEntity.setChapter(lawForm.getChapter());
        lawEntity.setContent(lawForm.getContent());
        lawEntity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        lawEntity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            lawService.insertLaw(lawEntity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加法律法规:" + entity, ControllerTool.getRoleName(request), true, "办案场所");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加法律法规:" + entity, ControllerTool.getRoleName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加法律法规失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加法律法规成功!");
    }


    /**
     * 根据id修改法律法规
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "updateLawEntity")
    @ResponseBody
    public MessageEntity updateLaw(@RequestBody LawForm lawForm, HttpServletRequest request) {
        logger.info("-------------------------law/updateLawEntity------------------------------");
        LawEntity lawEntity = new LawEntity();
        try {
            lawEntity.setId(lawForm.getId());
            lawEntity.setType(lawForm.getType());
            lawEntity.setName(lawForm.getName());
            lawEntity.setChapter(lawForm.getChapter());
            lawEntity.setContent(lawForm.getContent());
            lawService.updateLaw(lawEntity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改法律法规:" + entity, ControllerTool.getRoleName(request), true, "办案场所");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改法律法规:" + entity, ControllerTool.getRoleName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改法律法规失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新法律法规成功!");
    }

    /**
     * 根据id删除法律法规
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "removeLawEntity")
    @ResponseBody
    public MessageEntity removeLaw(@RequestBody IDForm from, HttpServletRequest request) {
        logger.info("-------------------------law/removeLawEntity------------------------------");
        try {
            lawService.removeLaw(from.getId());
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "删除法律法规:" + entity, ControllerTool.getRoleName(request), true, "办案场所");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "删除法律法规:" + entity, ControllerTool.getRoleName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除法律法规失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除法律法规成功!");
    }
}
