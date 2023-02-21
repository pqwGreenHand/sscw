package com.zhixin.zhfz.zhag.controller.jqgj;

import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import com.zhixin.zhfz.zhag.services.gjxx.IGjxxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping("/zhfz/zhag/jqgj")
public class JqgjController {

    @Autowired
    private IGjxxService gjxxService;

    private static final Logger logger = LoggerFactory.getLogger(JqgjController.class);

    /**
     * 警情告警列表
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/jqgjList")
    @ResponseBody
    public Map<String, Object> jqyjList(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<GjxxEntity> list = null;
        int total = 0;
        list = gjxxService.jqGjList(map);
        total = gjxxService.countJqGj(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }
}
