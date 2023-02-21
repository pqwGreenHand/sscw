package com.zhixin.zhfz.zhag.controller.xsajyj;


import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import com.zhixin.zhfz.zhag.services.xsajgj.IXsajgjService;
import com.zhixin.zhfz.zhag.services.xsajyj.IXsajyjService;
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
 * @Description: 行政案件预警
 * @Auther: xcf
 * @Date: 2019-05-21 20:55
 */
@Controller
@RequestMapping("/zhfz/zhag/xzajyj")
public class XzajyjController {

    private static Logger logger = Logger.getLogger(XzajyjController.class);

    @Autowired private IXsajyjService xsajyjService;
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> listXzanyj(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                          HttpServletResponse response) {

        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<GjxxEntity> list = null;
        int total = 0;
        list=this.xsajyjService.listXzanyj(map);
        total=this.xsajyjService.xzanCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;

    }

    @RequestMapping(value = "/hisList")
    @ResponseBody
    public Map<String, Object> hisList(@RequestParam Map<String, Object> param, HttpServletRequest request,
                                          HttpServletResponse response) {

        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<GjxxEntity> list = null;
        int total = 0;
        list=this.xsajyjService.hisList(map);
        total=this.xsajyjService.hisCount(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        result.put("total", total);
        return result;

    }


}
