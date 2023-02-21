package com.zhixin.zhfz.bacsapp.controller.authIndex;

import com.zhixin.zhfz.bacsapp.services.authIndex.IAuthIndexService;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 权限首页控制中心
 * @author: xcf
 * @create: 2019-04-01 10:47
 **/
@Controller
@RequestMapping("/zhfz/bacsapp/authIndex")
public class AuthIndexController {

    @Autowired
    private IAuthIndexService authIndexService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object>  authIndex(HttpServletRequest request) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        UserEntity user = ControllerTool.getUser(request);
        result.put("list",this.authIndexService.getAuthList());
        return result;
    }
}
