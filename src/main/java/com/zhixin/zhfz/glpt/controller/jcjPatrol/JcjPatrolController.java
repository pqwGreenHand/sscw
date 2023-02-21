package com.zhixin.zhfz.glpt.controller.jcjPatrol;

import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.services.jcjPatrol.JcjPatrolService;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
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
@RequestMapping("/zhfz/glpt/jcjPatrol")
public class JcjPatrolController {

    @Autowired
    private JcjPatrolService jcjPatrolService;

    /**
     * 警情信息查询
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyJqxxList")
    @ResponseBody
    public Map<String, Object> quyJqxxList(@RequestParam Map<String, Object> pageMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<JqxxEntity> list = null;
        int total = 0;
        list = jcjPatrolService.list(map);
        total = jcjPatrolService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }

    /**
     * 按id查询警情xx
     *
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyJqxxListById")
    @ResponseBody
    public Map<String, Object> quyJqxxListById(@RequestParam Map<String, Object> pageMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<JqxxEntity> list = null;
        int total = 0;
        list = jcjPatrolService.quyJqxxListById(map);
        total = jcjPatrolService.count_ById(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;
    }
}
