package com.zhixin.zhfz.bacs.controller.njxzwd;

import com.zhixin.zhfz.bacs.entity.CaseNatureAnswerEntity;
import com.zhixin.zhfz.bacs.entity.CaseNatureQuestionEntity;
import com.zhixin.zhfz.bacs.services.njxzwd.ICaseNatureAnswerService;
import com.zhixin.zhfz.bacs.services.njxzwd.ICaseNatureQuestionService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 案件性质问答
 * @author: jzw
 * @create: 2019-02-22 14:09
 **/
@Controller
@RequestMapping("/zhfz/bacs/njxzwd")
public class CaseNatureController {

    private static final Logger logger = LoggerFactory.getLogger(CaseNatureController.class);

    @Autowired
    private ICaseNatureQuestionService questionService;

    @Autowired
    private ICaseNatureAnswerService answerService;

    @Autowired
    private IOperLogService operLogService;

    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author jzw
     * @Description 笔录类型问题list
     * @Date 15:14 2019/2/22
     * @Param [param]
     **/
    @RequestMapping("/question/list")
    @ResponseBody
    public Map<String, Object> questionList(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        logger.info("------------------------------njxzwd/question/list---------------------------------");
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<CaseNatureQuestionEntity> list = new ArrayList<CaseNatureQuestionEntity>();
        int total = 0;
        list = this.questionService.list(map);
        total = this.questionService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 问题增加
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/question/add")
    @ResponseBody
    public MessageEntity questionAdd(@RequestBody CaseNatureQuestionEntity entity, HttpServletRequest request) {
        logger.info("------------------------------njxzwd/question/add---------------------------------");
        entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            questionService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加案件性质问题:" + entity, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on adding CaseNatureQuestion!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加案件性质问题:" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 答案修改
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/question/edit")
    @ResponseBody
    public MessageEntity questionEdit(@RequestBody CaseNatureQuestionEntity entity, HttpServletRequest request) {
        logger.info("------------------------------njxzwd/question/edit---------------------------------");
        try {
            questionService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改案件性质问题：" + entity, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on adding CaseNatureQuestion!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改案件性质问题：" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 问题删除
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/question/remove")
    @ResponseBody
    public MessageEntity questionRemove(Long id, HttpServletRequest request) {
        logger.info("------------------------------njxzwd/question/questionRemove---------------------------------");
        try {
            questionService.delete(id);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除案件性质问题：" + id, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on removing CaseNatureQuestionEntityId!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除案件性质问题：" + id, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }


    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author jzw
     * @Description 笔录类型答案list
     * @Date 15:14 2019/2/22
     * @Param [param]
     **/
    @RequestMapping("/answer/list")
    @ResponseBody
    public Map<String, Object> answerList(@RequestParam Map<String, Object> param) {
        logger.info("------------------------------njxzwd/answer/answerList---------------------------------");
        Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.answerService.count(map));
        result.put("rows", answerService.list(map));
        return result;
    }


    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 答案增加
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/answer/add")
    @ResponseBody
    public MessageEntity answerAdd(@RequestBody CaseNatureAnswerEntity entity, HttpServletRequest request) {
        logger.info("------------------------------njxzwd/answer/answerAdd---------------------------------");
        try {
            answerService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加案件性质答案:" + entity, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on adding RecordTypeAnswer!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加案件性质答案:" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 答案修改
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/answer/edit")
    @ResponseBody
    public MessageEntity answerEdit(@RequestBody CaseNatureAnswerEntity entity, HttpServletRequest request) {
        logger.info("------------------------------njxzwd/answer/edit---------------------------------");
        try {
            answerService.update(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改案件性质答案:" + entity, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on editing RecordTypeAnswer!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改案件性质答案:" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }

    /**
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     * @Author jzw
     * @Description 问题删除
     * @Date 10:42 2019/2/23
     * @Param [form]
     **/
    @RequestMapping("/answer/remove")
    @ResponseBody
    public MessageEntity answerRemove(Long id, HttpServletRequest request) {
        logger.info("------------------------------njxzwd/answer/answerRemove---------------------------------");
        try {
            answerService.delete(id);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "添加案件性质答案:" + id, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on removing RecordTypeAnswer!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "添加案件性质答案:" + id, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }

}
