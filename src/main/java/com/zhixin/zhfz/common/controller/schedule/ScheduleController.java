package com.zhixin.zhfz.common.controller.schedule;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.form.ScheduleForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.schedule.IScheduleSevice;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.json.XMLTokener.entity;

/**
 * @prgram: zhfz
 * @Description: 待办控制层
 * @Auther: xcf
 * @Date: 2019-04-16 20:41
 */
@Controller
@RequestMapping("/zhfz/common/schedule")
public class ScheduleController {


    @Autowired
    private IScheduleSevice scheduleSevice;
    @Autowired
    private IOperLogService operLogService;

    @RequestMapping(value = "/listSchedule")
    @ResponseBody
    public Map<String,Object > listSchedule(@RequestParam Map<String,Object> param, HttpServletRequest request){


        UserEntity user = ControllerTool.getUser(request);

        Map<String, Object> map = ControllerTool.mapFilter(param);
        map.put("receiverId",user.getId());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.scheduleSevice.count(map));
        result.put("rows", this.scheduleSevice.listSchedule(map));
        return result;

    }

    @RequestMapping(value = "updateSchedule")
    @ResponseBody
    public MessageEntity updateSchedule(@RequestBody ScheduleForm scheduleForm, HttpServletRequest request){

        try {

            this.scheduleSevice.updateScheduleById(scheduleForm);

            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改待办通知:" + entity, ControllerTool.getRoleName(request), true,scheduleForm.getSystemName());
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改待办通知:" + entity, ControllerTool.getRoleName(request), false,scheduleForm.getSystemName());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改待办通知失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改待办通知成功!");
    }

}
