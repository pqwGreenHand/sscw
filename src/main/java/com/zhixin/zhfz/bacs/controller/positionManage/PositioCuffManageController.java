package com.zhixin.zhfz.bacs.controller.positionManage;


import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhixin.zhfz.bacs.entity.PositionDataEntity;
import com.zhixin.zhfz.bacs.services.positionManage.IPositioManageService;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/positionManage")
public class PositioCuffManageController {

	private static Logger logger = LoggerFactory.getLogger(PositioCuffManageController.class);
    @Autowired
    private IPositioManageService positionDataService;
	
    // 查询在办案区的人员信息
    @RequestMapping(value = "/listSearial")
    @ResponseBody
    public Map<String, Object> listPersons(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<Map<String, Object>> datas = new ArrayList<>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
//        if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            datas = this.positionDataService.selectPositionDataSearialInfo(map);
            total = this.positionDataService.selectPositionDataSearialInfoCount(map);
//        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }

}
