package com.zhixin.zhfz.zhag.controller.jqxx;

import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import com.zhixin.zhfz.zhag.services.jqxx.IJqxxService;
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
@RequestMapping("/zhfz/zhag/jqxx")
public class jqxxController {

    @Autowired
    private IJqxxService jqxxService;

    private static final Logger logger = LoggerFactory.getLogger(jqxxController.class);

    /**
     * 警情信息列表
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/jqxxList")
    @ResponseBody
    public Map<String, Object> jqxxList(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<JqxxEntity> list = null;
        int total = 0;
        list = jqxxService.list(map);
        total = jqxxService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }
}
