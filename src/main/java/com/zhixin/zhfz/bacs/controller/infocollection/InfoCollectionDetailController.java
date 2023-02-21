package com.zhixin.zhfz.bacs.controller.infocollection;


import com.zhixin.zhfz.bacs.entity.InfoCollectionDetailEntity;
import com.zhixin.zhfz.bacs.entity.InfoCollectionEntity;

import com.zhixin.zhfz.bacs.services.infocollection.IInfoCollectionDetailService;
import com.zhixin.zhfz.bacs.services.infocollection.IInfoCollectionService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/infocollection")
public class InfoCollectionDetailController {

    private static Logger logger = LoggerFactory.getLogger(InfoCollectionDetailController.class);

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IInfoCollectionDetailService iInfoCollectionDetailService;

    @Autowired
    private ISerialService serialService;

    @Autowired
    private IInfoCollectionService infoCollectionService;



     // 根据左边的id去右边的数据
    @RequestMapping(value = "/selectDetaialByPrimaryKey")
    @ResponseBody
    public Map<String,Object> selectDetaialByPrimaryKey(@RequestParam Map<String,Object> param){

        Map<String, Object> result=new HashMap<String,Object>();
        String ids =(String) param.get("id");
        if(ids == null){
            return null;
        }
        List<InfoCollectionDetailEntity> list = iInfoCollectionDetailService.selectByPrimaryKey(param);
        result.put("rows", list);
        return result;
    }
    /**
     *查所有信息采集基本项
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/listAllInfoCollectionDetail")
    @ResponseBody
    public Map<String,Object > listAllInfoCollectionDetail(@RequestParam Map<String,Object> map,HttpServletRequest request){
        try {
            Map<String,Object> result = new HashMap<String, Object>();
            if(map.get("infoCollectionId") == null){
                return  null;
            }
            map.put("id",map.get("infoCollectionId"));
            List<InfoCollectionDetailEntity> list = iInfoCollectionDetailService.selectByPrimaryKey(map);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集基本项" , ControllerTool.getRoleName(request), true,"办案中心");
            result.put("rows",list);
            return result;
        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集基本项", ControllerTool.getRoleName(request), false,"办案中心");
            return null;
        }

    }

    @RequestMapping(value = "/listAllInfoCollectionDetailById")
    @ResponseBody
    public Map<String,Object> listAllInfoCollectionDetailById(@RequestParam Map<String,Object > map, HttpServletRequest request, HttpServletResponse response){

        Map<String,Object> result = new HashMap<String, Object>();
        try {
            List<InfoCollectionDetailEntity> list =  iInfoCollectionDetailService.selectByPrimaryKey(map);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集基本项" , ControllerTool.getRoleName(request), true,"办案场所");
            System.err.println(list);
            result.put("rows",list);
            return result;
        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集基本项", ControllerTool.getRoleName(request), false,"办案场所");
            return null;
        }
    }

    /**
     * 继续采集
     * @return
     */
    @RequestMapping(value = "/updateDeatils")
    @ResponseBody
    public MessageEntity updateDeatils(@RequestParam Map<String,Object> map,HttpServletRequest request){
        try {
            //获取用户ID
            int collectUserId=ControllerTool.getUserID(request);
            String ids=java.net.URLDecoder.decode(request.getParameter("ids"),"utf-8") ;
            String infoCollectionIdStr=request.getParameter("infoCollectionId");
            //int collectionId = Integer.parseInt(infoCollectionIdStr);;

            //infoCollectionService.getSeriaId(collectionId)
          //  String serialNo=request.getParameter("serialNo");
          //  String s = request.getParameter("serialId");

            String[] idss=ids.split(",");
            List<InfoCollectionDetailEntity> list=new ArrayList<InfoCollectionDetailEntity>();
            Long infoCollectionId=null;
          /*  if(serialNo!=null){
                InterrogateSerialEntity entity=new InterrogateSerialEntity();
                entity.setSerialNo(serialNo);
                InterrogateSerialEntity entitySerial=interrogateSerialService.getSerialByNo(entity);
                InfoCollectionEntity collectionEntity=infoCollectionService.selectByInterrogateSerialId(entitySerial.getId());
                infoCollectionId=collectionEntity.getId();
            }*/
            if(infoCollectionIdStr!=null){
                infoCollectionId=Long.parseLong(infoCollectionIdStr);
            }
            Map<String, Object> map2=new HashMap<String,Object>();
            map2.put("infoCollectionId", infoCollectionId);
            for(int i=0;i<idss.length;i++){
                map2.put("collKey", idss[i]);
                InfoCollectionDetailEntity entity=iInfoCollectionDetailService.selectByInfoCollectionId(map2);
                if(entity!=null&&entity.getCollValue()==null){
                    list.add(entity);
                }
            }
            iInfoCollectionDetailService.updateDeatils(list,collectUserId);
            //修改updateTime
            InfoCollectionEntity infoCollectionEntity=new InfoCollectionEntity();
            infoCollectionEntity.setId(infoCollectionId);
            infoCollectionService.updateByPrimaryKeySelective(infoCollectionEntity);
            //判断是否已全部修改完毕，修改register-time
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集基本项修改成功" , ControllerTool.getRoleName(request), true,"办案中心");

        } catch (Exception e) {
            logger.error("Error on adding user!", e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "信息采集基本项修改失败" , ControllerTool.getRoleName(request), false,"办案中心");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("采集失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("采集成功!");

    }
}
