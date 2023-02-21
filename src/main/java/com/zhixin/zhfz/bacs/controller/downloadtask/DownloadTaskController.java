package com.zhixin.zhfz.bacs.controller.downloadtask;

import com.zhixin.zhfz.bacs.entity.DownloadTaskEntity;
import com.zhixin.zhfz.bacs.services.downloadtask.IDownloadTaskService;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 视频下载任务
 * @author: jzw
 * @create: 2019-03-05 15:19
 **/
@Controller
@RequestMapping("/zhfz/bacs/downLoadTask")
public class DownloadTaskController {
    private static final Logger logger = LoggerFactory.getLogger(DownloadTaskController.class);
    @Autowired
    private IDownloadTaskService downloadTaskService;


    // 查询
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        logger.info("+++++list+++++++"+params);
        List<DownloadTaskEntity> datas=new ArrayList<DownloadTaskEntity>();
        int total=0;
        String serialId = (String)params.get("serialId");
        if(serialId != null && !serialId.equals("-1")){
            Map<String, Object> map = ControllerTool.mapFilter(params);
            logger.info(map.toString());
            datas = this.downloadTaskService.list(map);
            total=this.downloadTaskService.count(map);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }
//重新下载downLoadTask
    @RequestMapping(value = "/reDownLoad")
    @ResponseBody
    public MessageEntity reDownLoad(Integer downLoadTaskId, HttpServletRequest request) throws Exception {
        logger.info("+++++reDownLoad+++++++"+downLoadTaskId);
       try{
            this.downloadTaskService.reDownLoad(downLoadTaskId);
           return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("重新下载成功！");
       }catch(Exception e){
           e.printStackTrace();
           return new MessageEntity().addCode(1).addIsError(true).addTitle("提示").addContent("重新下载失败!"+e);
       }
    }
    /**
     * 查询案件并生成树
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/getCaseAndPersonTree")
    @ResponseBody
    public List<AbstractTreeEntity> getCaseAndPersonTree(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.info("+++++getCaseAndPersonTree+++++++"+params);
        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        logger.info("+++++map:+++++++"+map);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (c.cjr=" + ControllerTool.getUserID(request) + " or org.op_user_id=" + ControllerTool.getUserID(request)
                    + " or ct.op_user_id=" + ControllerTool.getUserID(request)
                    + " or xu.op_user_id =" + ControllerTool.getUserID(request)
                    + " or xbmln.op_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","( c.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or org.p_id like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or ct.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or xu.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +" or xbmln.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    +")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","( c.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or org.p_id like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or ct.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or xu.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +" or xbmln.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    +")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","( c.op_pid = " + sessionInfo.getCurrentOrgPid()
                    +" or org.p_id = " + sessionInfo.getCurrentOrgPid()
                    +" or ct.op_pid = " + sessionInfo.getCurrentOrgPid()
                    +" or xu.op_pid = " + sessionInfo.getCurrentOrgPid()
                    +" or xbmln.op_pid = "+ sessionInfo.getCurrentOrgPid()
                    +")");
        }
        List<AbstractTreeEntity> datas = new ArrayList<AbstractTreeEntity>();
        try {
            datas = downloadTaskService.getCaseAndPersonTree(map);
            logger.info("+++++datas:+++++++"+datas);
            return datas;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
