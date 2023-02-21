package com.zhixin.zhfz.zhag.controller.xsajgj;


import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.entity.SupervisorRequestEntity;
import com.zhixin.zhfz.glpt.services.duban.ISupervisorService;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import com.zhixin.zhfz.zhag.services.xsajgj.IXsajgjService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @prgram: zhfz
 * @Description: 刑事案件告警
 * @Auther: xcf
 * @Date: 2019-05-21 20:55
 */
@Controller
@RequestMapping("/zhfz/zhag/xsajgj")
public class XsajgjController {

    private static Logger logger = Logger.getLogger(XsajgjController.class);

    @Autowired private IXsajgjService xsajgjService;
    @Autowired
    private ISupervisorService supervisorService;

    @Autowired
    private IOperLogService operLogService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> listXsangj(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                          HttpServletResponse response) {

        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<GjxxEntity> list = null;
        int total = 0;
        list=this.xsajgjService.listXsangj(map);
        total=this.xsajgjService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;

    }

    @RequestMapping("addDuBan")
    @ResponseBody
    public MessageEntity addDouBan(SupervisorRequestEntity entity, HttpServletRequest request){

        try {
            entity.setCount(0);
            entity.setAgSign(1);
            entity.setAgFun(3);
            entity.setAuditOrgId(entity.getReceiveOrgId());
            entity.setOpUserId(Long.valueOf(ControllerTool.getUserID(request)));
            entity.setSendUserId(entity.getOpUserId());
            entity.setSendOrgId(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            supervisorService.insertRequest(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "下发督办" + entity.toString(), ControllerTool.getUser(request).getLoginName(), true,"管理平台");
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "下发督办" + entity.toString(), ControllerTool.getUser(request).getLoginName(), false,"管理平台");
            return MessageEntity.error("添加失败!");
        }

        return MessageEntity.success("添加成功!");
    }


}
