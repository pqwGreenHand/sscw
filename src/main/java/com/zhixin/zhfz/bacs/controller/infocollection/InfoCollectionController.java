package com.zhixin.zhfz.bacs.controller.infocollection;

import com.zhixin.zhfz.bacs.controller.law.LawController;
import com.zhixin.zhfz.bacs.entity.InfoCollectionEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.services.infocollection.IInfoCollectionDetailService;
import com.zhixin.zhfz.bacs.services.infocollection.IInfoCollectionService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/infocollection")
public class InfoCollectionController {

    private static Logger logger = LoggerFactory.getLogger(LawController.class);

    @Autowired
    private IInfoCollectionService infoCollectionService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ISerialService interrogateSerialService;

    @Autowired
    private IInfoCollectionDetailService infoCollectionDetailService;


    //获得已存在的嫌疑人相关事项及分页
    @RequestMapping(value = "/getAllInfocollection")
    @ResponseBody
    public Map<String,Object> getAllInfocollection(@RequestParam Map<String,Object> param,HttpServletRequest request){
            Map<String,Object> map = ControllerTool.mapFilter(param);
            List<InfoCollectionEntity> list = new ArrayList<InfoCollectionEntity>();
            int total = 0;
            boolean flag = true;

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( ict.op_user_id=" + ControllerTool.getUserID(request)
                    + " or ict.register_user_id=" + ControllerTool.getUserID(request)
                    + " or ic.cjr=" + ControllerTool.getUserID(request)
                    +" or ic.zbmj_id = " + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', ic.xbmj_ids)"
                    +" or igs.in_register_user_id = " + ControllerTool.getUserID(request)
                    +" or igs.out_register_user_id = " + ControllerTool.getUserID(request)
                    +" or igs.send_user_id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", "  ict.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", "  ict.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", "  ict.area_id " + sessionInfo.getSuperAndSubAreaInStr()
            );
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth"," ( ict.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ic.op_pid like " +  sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth"," ( ict.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ic.op_pid like " +  sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth"," ( ict.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ic.op_pid = " +  sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))){
            //全部
        } else {
            flag = false;
        }
        if (flag) {
            list = this.infoCollectionService.getAllInfocollection(map);
            total = this.infoCollectionService.count(map);
        }

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("total", total);
            result.put("rows", list);

            return result;
    }



    @RequestMapping(value = "/exitCollectionById")
    @ResponseBody
    public MessageEntity exitCollectionById(@RequestBody IDForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 获取登录用户ID
            int registerUserId = ControllerTool.getUserID(request);
           SerialEntity entity = new SerialEntity();
            long seriaId = form.getLongID();
            entity.setId(seriaId);
            // 通过id去查询 serial_id 是否存在
            InfoCollectionEntity collectionEntity =  infoCollectionService.selectByInterrogateSerialId(seriaId);
            // 如果不存在就新增
            if(collectionEntity == null ){
                Long infoId = infoCollectionDetailService.insertAllDeatils(seriaId,registerUserId, request);


                SerialEntity entity1 = new SerialEntity();
                entity1.setId(seriaId);
                SerialEntity entitySerial1 = interrogateSerialService.getSerialByNo1(entity1);
                InfoCollectionEntity infoCollectionEntity = new InfoCollectionEntity();
                infoCollectionEntity.setId(infoId);
                infoCollectionService.updateByPrimaryKeySelective(infoCollectionEntity);
                //修改状态
                interrogateSerialService.changestatus(entitySerial1.getId(), 5);
                response.getWriter().print(infoId);
            }else{
                response.getWriter().print(collectionEntity.getId());
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集", ControllerTool.getRoleName(request), true,"办案中心");

        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集",  ControllerTool.getRoleName(request), false,"办案中心");
        }

        return null;
    }


    // 保存新增信息采集
    @RequestMapping(value = "/exitCollectionBySerialNo")
    @ResponseBody
    public MessageEntity exitCollectionBySerialNo(@RequestParam Map<String,Object> pmap,HttpServletRequest request, HttpServletResponse response){
         // 获取用户ID
        Integer registerUserId = ControllerTool.getUserID(request);
        SerialEntity entity = new SerialEntity();
        String serialNo = request.getParameter("serialId");
        // Long serialId = Long.valueOf(request.getParameter("serialId")).longValue();
       // Long seriaId = interrogateSerialService.getSerialId(serialNo);
        Long seriaId = Long.parseLong(serialNo);
        entity.setId(seriaId);
        // 查询serialNo是否存在
        int count =  infoCollectionService.selectserialNo(seriaId);
        if (count == 0){
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("该专属编号不存在");
        }
        try {
            // 查询专属编号ID
            SerialEntity entitySerial = interrogateSerialService.getSerialByNo1(entity);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", entitySerial.getId());
            //通过专属编号ID查询信息采集ID判断其是否存在
            InfoCollectionEntity collectionEntity = infoCollectionService.selectByInterrogateSerialId(entitySerial.getId());
            // 如果不存在则新增
            if (collectionEntity == null){
               long infoId = infoCollectionDetailService.insertAllDeatils(entitySerial.getId(), registerUserId, request);
                SerialEntity entity1 = new SerialEntity();
                entity1.setSerialNo(serialNo);
                SerialEntity entitySerial1 = interrogateSerialService.getSerialByNo(entity1);
                InfoCollectionEntity infoCollectionEntity = new InfoCollectionEntity();
               infoCollectionEntity.setId(infoId);
                infoCollectionService.updateByPrimaryKeySelective(infoCollectionEntity);
                //修改状态
                interrogateSerialService.changestatus(entitySerial1.getId(), 5);
                 response.getWriter().print(infoId);
            } else {
                response.getWriter().print(collectionEntity.getId());
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集", ControllerTool.getRoleName(request), true,"办案中心");
        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            e.printStackTrace();
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集", ControllerTool.getRoleName(request), false,"办案中心");
        }
        return null;
    }



}
