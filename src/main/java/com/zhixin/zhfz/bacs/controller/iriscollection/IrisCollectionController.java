package com.zhixin.zhfz.bacs.controller.iriscollection;

import com.zhixin.zhfz.bacs.entity.IrisEntity;
import com.zhixin.zhfz.bacs.services.iriscollection.IIrisCollectionService;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/zhfz/bacs/iriscollection")
public class IrisCollectionController {

    @Autowired
    private IIrisCollectionService irisCollectionService;

    /**
     * 分页查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam Map<String,Object> param, HttpServletRequest request){
        Map<String, Object> map = ControllerTool.mapFilter(param);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( bi.op_user_id=" + ControllerTool.getUserID(request) + " or bi.user_id="
                    + ControllerTool.getUserID(request)
                    +" or ca.cjr = " + ControllerTool.getUserID(request)
                    +" or s.in_register_user_id = " + ControllerTool.getUserID(request)
                    +" or s.out_register_user_id = " + ControllerTool.getUserID(request)
                    +" or s.send_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " bi.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " bi.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " bi.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( bi.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ca.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " or s.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( bi.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ca.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " or s.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( bi.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ca.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " or s.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<IrisEntity> list =  irisCollectionService.list(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total",this.irisCollectionService.count(map));
        result.put("rows",list);
        return result;
    }


    // 图片下载
    @RequestMapping(value = "/leftDownload")
    @ResponseBody
    public void leftDownload(@RequestParam Map<String,Object> param, HttpServletRequest request, HttpServletResponse response){
        try {
            File  file = new File("D:/test.jpg");
            response.reset();
            response.setContentType("application/x-download");
            response.setCharacterEncoding("UTF-8");
           // String fileName = entity.getFileName();
            String fileName = "test.jpg";
            if(request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }else{
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            response.addHeader("Content-Disposition", "attachment;filename=" +fileName);
            OutputStream output = response.getOutputStream();
            FileUtils.copyFile(file, output);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
