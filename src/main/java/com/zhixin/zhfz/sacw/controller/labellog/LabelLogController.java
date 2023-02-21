package com.zhixin.zhfz.sacw.controller.labellog;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.entity.LabelLogEntity;
import com.zhixin.zhfz.sacw.services.labellog.ILabelLogService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 标签日志
 */
@Controller
@RequestMapping("/zhfz/sacw/labellog")
public class LabelLogController {

    private static final Logger logger = LoggerFactory.getLogger(LabelLogController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ILabelLogService service;

    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        // map.put("usePage", true);
        List<LabelLogEntity> list = service.list(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.service.count(map));
        result.put("rows", list);
        return result;
    }

    /**
     * 根据id删除
     *
     * @param form
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public MessageEntity delete(@RequestBody IDForm form, HttpServletRequest request) {
        try {
            service.delete(form.getIntID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除标签日志信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), true, "涉案财物");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除标签日志信息:" + form.getId(), ControllerTool.getUser(request).getLoginName(), false, "涉案财物");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除标签日志失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除标签日志成功!");
    }

}
