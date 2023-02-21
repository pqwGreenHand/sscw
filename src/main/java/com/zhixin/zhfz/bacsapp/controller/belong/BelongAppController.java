package com.zhixin.zhfz.bacsapp.controller.belong;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacsapp.services.belong.IBelongAppService;
import com.zhixin.zhfz.bacsapp.vo.PageResponse;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacsapp/belong")
public class BelongAppController {

    private static Logger logger = LoggerFactory.getLogger(BelongAppController.class);

    @Autowired
    private IBelongAppService belongService;

    /**
     * 随身储物柜页面显示排序
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/selectBelongByAreaId")
    @ResponseBody
    public PageResponse selectBelongByAreaId(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                             HttpServletResponse response) {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if(sessionInfo != null){
            map.put("areaId",sessionInfo.getCurrentArea().getId());
        }
        List<BelongEntity> result =  belongService.selectBelongByAreaId(map);
        return PageResponse.of(result,result.size());
    }
    /**
     * 涉案储物柜页面显示排序
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/selectExhibitByAreaId")
    @ResponseBody
    public PageResponse selectExhibitByAreaId(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                             HttpServletResponse response) {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if(sessionInfo != null){
            map.put("areaId",sessionInfo.getCurrentArea().getId());
        }
        List<BelongEntity> result =  belongService.selectExhibitByAreaId(map);
        return PageResponse.of(result,result.size());
    }
}