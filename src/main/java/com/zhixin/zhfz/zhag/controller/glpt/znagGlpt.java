package com.zhixin.zhfz.zhag.controller.glpt;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.jxkp.entity.RateSignEntity;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.services.xsajtj.IXsajtjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/zhfz/zhag/glpt")
public class znagGlpt {

    @Autowired
    private IXsajtjService xsajtjService;
    /**
     * 刑事行政案件数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXSXZAJAllNumberByDay")
    @ResponseBody
    public Map<String,Object> quyXSAJAllNumberByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        List<RateSignEntity> list = xsajtjService.quyXSXZAJAllNumberByDay(day);
        //告警来源数据统计
        List<RateSignEntity> listGjly = xsajtjService.listGjly(day);
        map.put("list",list);
        map.put("listGjly",listGjly);

        return map ;

    }

    /**
     * 案由
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyAJCauseByDay")
    @ResponseBody
    public MessageEntity quyAJCauseByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xsajtjService.quyAJCauseByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyAJCauseByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyAJCauseByDay查询成功!").addCallbackData(list);
    }
}
