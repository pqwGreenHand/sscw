package com.zhixin.zhfz.bacsapp.controller.information;

import com.zhixin.zhfz.bacsapp.services.Information.IInformationService;
import com.zhixin.zhfz.bacsapp.vo.PageResponse;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacsapp/information")
public class InformationController {

    @Autowired
    private IInformationService informationService;

    @ResponseBody
    @RequestMapping("/noticeList")
    public PageResponse noticePage(@RequestParam Map<String,Object> params, HttpServletRequest request){
        params.put("receiverId", ControllerTool.getUserID(request));
        return informationService.noticePage(ControllerTool.mapFilter(params));
    }

    @ResponseBody
    @RequestMapping("/todoList")
    public PageResponse todoPage(@RequestParam Map<String,Object> params, HttpServletRequest request){
        params.put("receiverId", ControllerTool.getUserID(request));
        return informationService.todoPage(ControllerTool.mapFilter(params));
    }

    @ResponseBody
    @RequestMapping("/total")
    public Map<String,Integer> total(@RequestParam Map<String,Object> params, HttpServletRequest request){
        Map result = new HashMap<String,Integer>();
        params.put("receiverId", ControllerTool.getUserID(request));
        result.put("notice",this.informationService.noticeCount(params));
        result.put("todo",this.informationService.todoCount(params));
        return result;
    }

    @ResponseBody
    @RequestMapping("/deal")
    public MessageEntity deal(@RequestParam Map<String,Object> params){
        try {
            this.informationService.deal(params);
        } catch (Exception e) {
            return new MessageEntity().addCode(0).addIsError(true);
        }
        return new MessageEntity().addCode(1).addIsError(false);
    }

}
