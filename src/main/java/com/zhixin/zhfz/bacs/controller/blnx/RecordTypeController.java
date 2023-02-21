package com.zhixin.zhfz.bacs.controller.blnx;

import com.zhixin.zhfz.bacs.entity.RecordTypeAnswerEntity;
import com.zhixin.zhfz.bacs.entity.RecordTypeQuestionEntity;
import com.zhixin.zhfz.bacs.services.blnx.IRecordTypeAnswerService;
import com.zhixin.zhfz.bacs.services.blnx.IRecordTypeQuestionService;
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
 * @description: 笔录类型问答
 * @author: jzw
 * @create: 2019-02-22 14:09
 **/
@Controller
@RequestMapping("/zhfz/bacs/blnx")
public class RecordTypeController {

    private static final Logger logger = LoggerFactory.getLogger(RecordTypeController.class);

    @Autowired
    private IRecordTypeQuestionService questionService;

    @Autowired
    private IRecordTypeAnswerService answerService;

    @Autowired
    private IOperLogService operLogService;

    /**
     * @Author jzw
     * @Description 笔录类型问题list
     * @Date 15:14 2019/2/22
     * @Param [param]
     * @return java.util.Map<java.lang.String , java.lang.Object>
     **/
    @RequestMapping("/question/list")
    @ResponseBody
    public Map<String,Object> questionList(@RequestParam Map<String,Object> param, HttpServletRequest request){
        logger.info("---------------------------blnx/question/list---------------------------");
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<RecordTypeQuestionEntity> list=new ArrayList<RecordTypeQuestionEntity>();
        int total=0;
        list = this.questionService.list(map);
        total=this.questionService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }


    /**
     * @Author jzw
     * @Description 问题增加
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/question/add")
    @ResponseBody
    public MessageEntity questionAdd(@RequestBody RecordTypeQuestionEntity entity, HttpServletRequest request){
        logger.info("---------------------------blnx/question/add---------------------------");
        entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            questionService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加笔录类型问题：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on adding RecordTypeQuestion!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加笔录类型问题：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    /**
     * @Author jzw
     * @Description 答案修改
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/question/edit")
    @ResponseBody
    public MessageEntity questionEdit(@RequestBody RecordTypeQuestionEntity entity,HttpServletRequest request){
        logger.info("---------------------------blnx/question/edit---------------------------");
        try {
            questionService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改笔录类型问题：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on adding RecordTypeQuestion!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改笔录类型问题：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }

    /**
     * @Author jzw
     * @Description 问题删除
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/question/remove")
    @ResponseBody
    public MessageEntity questionRemove(Long id,HttpServletRequest request){
        logger.info("---------------------------blnx/RecordTypeQuestionEntityId/remove---------------------------");
        try {
            questionService.delete(id);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除笔录类型问题：" + id , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on removing RecordTypeQuestion!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除笔录类型问题：" + id , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }


    /**
     * @Author jzw
     * @Description 笔录类型答案list
     * @Date 15:14 2019/2/22
     * @Param [param]
     * @return java.util.Map<java.lang.String , java.lang.Object>
     **/
    @RequestMapping("/answer/list")
    @ResponseBody
    public Map<String,Object> answerList(@RequestParam Map<String,Object> param){
        logger.info("---------------------------blnx/answer/list---------------------------");
        Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.answerService.count(map));
        result.put("rows", answerService.list(map));
        return result;
    }


    /**
     * @Author jzw
     * @Description 答案增加
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/answer/add")
    @ResponseBody
    public MessageEntity answerAdd(@RequestBody RecordTypeAnswerEntity entity,HttpServletRequest request){
        logger.info("---------------------------blnx/answer/add---------------------------");
        try {
            answerService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加笔录类型答案：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on adding RecordTypeAnswer!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "添加笔录类型答案：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("添加成功!");
    }

    /**
     * @Author jzw
     * @Description 答案修改
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/answer/edit")
    @ResponseBody
    public MessageEntity answerEdit(@RequestBody RecordTypeAnswerEntity entity,HttpServletRequest request){
        logger.info("---------------------------blnx/answer/edit---------------------------");
        try {
            answerService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改笔录类型答案：" + entity , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on editing RecordTypeAnswer!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改笔录类型答案：" + entity , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("更新失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("更新成功!");
    }

    /**
     * @Author jzw
     * @Description 问题删除
     * @Date 10:42 2019/2/23
     * @Param [form]
     * @return com.zhixin.zhfz.common.entity.MessageEntity
     **/
    @RequestMapping("/answer/remove")
    @ResponseBody
    public MessageEntity answerRemove(Long id,HttpServletRequest request){
        logger.info("---------------------------blnx/answer/remove---------------------------");
        try {
            answerService.delete(id);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除笔录类型答案：" + id , ControllerTool.getLoginName(request), true,"办案场所");
        } catch (Exception e) {
            logger.error("Error on removing RecordTypeAnswer!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除笔录类型答案：" + id , ControllerTool.getLoginName(request), false,"办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("删除成功!");
    }

}
