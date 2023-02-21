package com.zhixin.zhfz.zhag.controller.jqyj;

import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.YjxxEntity;
import com.zhixin.zhfz.zhag.services.yjxx.IYjxxService;
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
@RequestMapping("/zhfz/zhag/jqyj")
public class JqyjController {

    @Autowired
    private IYjxxService yjxxService;

    private static final Logger logger = LoggerFactory.getLogger(JqyjController.class);

    /**
     * 警情预警列表
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/jqyjList")
    @ResponseBody
    public Map<String, Object> jqyjList(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<YjxxEntity> list = null;
        int total = 0;
        list = yjxxService.jqYjList(map);
        total = yjxxService.countJqYj(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }
}
