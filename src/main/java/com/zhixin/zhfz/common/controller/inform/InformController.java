package com.zhixin.zhfz.common.controller.inform;


import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.services.Information.IInformationService;
import com.zhixin.zhfz.bacsapp.services.common.ICommonService;
import com.zhixin.zhfz.common.dao.common.ICommonCommonMapper;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.form.InformForm;
import com.zhixin.zhfz.common.services.notice.IMyNoticeService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.json.XMLTokener.entity;

/**
 * @prgram: zhfz
 * @Description: 通知控制中心updateInform
 * @Auther: xcf
 * @Date: 2019-04-15 17:23
 */


@Controller
@RequestMapping("/zhfz/common/inform")
public class InformController {


    @Autowired
    private IMyNoticeService iMyNoticeService;
    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private IInformationService iInformationService;
    @Autowired
    private IUserService userService;
    @Resource
    private ICommonCommonMapper commonCommonMapper;

    @RequestMapping(value = "/listInform")
    @ResponseBody
    public Map<String, Object> listInformList(@RequestParam Map<String, Object> param, HttpServletRequest request) {


        UserEntity user = ControllerTool.getUser(request);

        Map<String, Object> map = ControllerTool.mapFilter(param);
        map.put("receiverId", user.getId());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.iMyNoticeService.count(map));
        result.put("rows", this.iMyNoticeService.listInform(map));
        return result;

    }

    @RequestMapping(value = "updateInform")
    @ResponseBody
    public MessageEntity updateInform(@RequestBody InformForm informForm, HttpServletRequest request) {

        try {

            this.iMyNoticeService.updatInformById(informForm.getId());

            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改通知:" + entity, ControllerTool.getRoleName(request), true, informForm.getSystemName());
        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "修改通知:" + entity, ControllerTool.getRoleName(request), false, informForm.getSystemName());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改通知失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("更新通知成功!");
    }

    /**
     * 公告信息发送全局民警
     *
     * @param informForm
     * @param request
     * @return
     */
    @RequestMapping(value = "ggInformInsert")
    @ResponseBody
    public MessageEntity ggInformInsert(@RequestBody final InformForm informForm, HttpServletRequest request) {

        try {
            final List<UserEntity> userInfo = userService.getAllUsers();
            Integer sendId = ControllerTool.getUser(request).getId();
            String pid = ControllerTool.getSessionInfo(request).getCurrentOrg().getPid();
            final InformationEntity inform = new InformationEntity();
            inform.setSenderId(Long.parseLong(sendId + ""));
            inform.setTitle(informForm.getTitle());
            inform.setContent(informForm.getContent());
            inform.setSendTime(new Date());
            inform.setSystemName("BA");
            inform.setType(0);
            inform.setIsRead(0);
            inform.setOpPid(pid);
            inform.setOpUserId(sendId);

            new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < userInfo.size(); i++) {
                    inform.setId(null);
                    inform.setReceiverId(Long.parseLong(userInfo.get(i).getId() + ""));
                    try {
                        iInformationService.insertInform(inform);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Map<String, Object> map = new HashMap<>();
                if("停止预约".equals(informForm.getTitle())){
                    //更新表，页面提示不在进行预约操作
                    map.put("status",1);
                    commonCommonMapper.updateStatus(map);
                }else if("开始预约".equals(informForm.getTitle())){
                    //更新表，页面提示可进行预约操作
                    map.put("status",2);
                    commonCommonMapper.updateStatus(map);
                }
            }
        }).start();

        } catch (Exception e) {
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "发布通知:" + entity, ControllerTool.getRoleName(request), false, informForm.getSystemName());
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("发布通知失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("发布通知成功!");
    }


}
