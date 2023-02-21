package com.zhixin.zhfz.bacs.services.infocollection;


import com.zhixin.zhfz.bacs.dao.infocollection.IInfoCollectionDetailMapper;
import com.zhixin.zhfz.bacs.dao.infocollection.IInfocollectionMapper;
import com.zhixin.zhfz.bacs.dao.serial.ISerialMapper;
import com.zhixin.zhfz.bacs.entity.InfoCollectionDetailEntity;
import com.zhixin.zhfz.bacs.entity.InfoCollectionEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.common.dao.commonConfig.ICommonConfigDetailMapper;
import com.zhixin.zhfz.common.dao.commonConfig.ICommonConfigMapper;
import com.zhixin.zhfz.common.entity.CommonConfigDetailEntity;
import com.zhixin.zhfz.common.entity.CommonConfigEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoCollectionDetailServiceImpl implements IInfoCollectionDetailService{

    @Autowired
    private IInfoCollectionDetailMapper dao;

    @Autowired
    private ISerialMapper serialMapper;

    @Autowired
    private IInfocollectionMapper infocollectionMapper;

    @Autowired
    private ICommonConfigMapper commonConfigMapper;

    @Autowired
    private ICommonConfigDetailMapper commonConfigDetailMapper;


    @Override
    public List<InfoCollectionDetailEntity> selectByPrimaryKey(Map<String, Object> map) {
        return dao.selectByPrimaryKey(map);
    }

    /**
     * 当输入的专属编号不存在对应的信息采集时
     */
    @Override
    public Long insertAllDeatils(Long seriaId, Integer registerUserId, HttpServletRequest request) {

        try {
            //通过专属编号查询serialEntity，获取其中的办案场所ID，编号ID，嫌疑人ID，案件ID
            SerialEntity entity = new SerialEntity();
            entity.setId(seriaId);
            SerialEntity serialEntity = serialMapper.getSerialByNo1(entity);
            // 判断各不能为空的字段值是否存在
            // 新增信息采集
            InfoCollectionEntity collection = new InfoCollectionEntity();
            collection.setAreaId(serialEntity.getAreaId());
            collection.setSerialId(serialEntity.getId());
            collection.setPersonId(serialEntity.getPersonId());
            collection.setLawCaseId(serialEntity.getCaseId());
            System.err.println("案件ID" + serialEntity.getCaseId());
            collection.setRegisterUserId(registerUserId);
            //信息采集添加新的字段
            collection.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            collection.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            infocollectionMapper.insert(collection);


            Long ints = collection.getId();
            //通过专属编号ID查询新增的信息采集ID
            Map<String, Object> mapInter = new HashMap<String, Object>();
            mapInter.put("serialId", seriaId);


            Long infoCollectionId = infocollectionMapper.selectByInterrogateSerialId(mapInter).getId();

            // 新增信息采集基本项
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("type", "信息采集基本项1");
            //通过通用配置生成信息采集基本项
            List<CommonConfigEntity> list1 = commonConfigMapper.list(map);
            if (list1.size() == 0) {
                throw new Exception("信息采集项基本项已删除,请重置");
            } else if (list1.size() > 1) {
                throw new Exception("信息采集基本项出现多条，请重置");
            } else {
                //新增信息采集基本项
                List<InfoCollectionDetailEntity> listInfo = new ArrayList<InfoCollectionDetailEntity>();
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("commonConfigId", list1.get(0).getId());
                List<CommonConfigDetailEntity> listDeatil = commonConfigDetailMapper.list(map1);

                // 将查询出的基本采集项展示到右边
                for (int i = 0; i < listDeatil.size(); i++) {
                    InfoCollectionDetailEntity entityInfo = new InfoCollectionDetailEntity();
                    entityInfo.setCollectUserId(registerUserId);
                    entityInfo.setCollKey(listDeatil.get(i).getParamKey());
                    entityInfo.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                    entityInfo.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                    listInfo.add(entityInfo);
                }

                for (int i = 0; i < listInfo.size(); i++) {
                    listInfo.get(i).setInfoCollectionId(infoCollectionId);
                }
                dao.insertAllDeatils(listInfo);
            }
           return ints;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return (long) 0;
    }

    @Override
    public InfoCollectionDetailEntity selectByInfoCollectionId(Map<String, Object> map2) {
        List<InfoCollectionDetailEntity>list=dao.selectByInfoCollectionId(map2);
        if(list!=null&&list.size()>0){
            System.out.println("=========="+list.get(0));
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 继续采集
     */
    @Override
    public void updateDeatils(List<InfoCollectionDetailEntity> list, int collectUserId) {
        // TODO Auto-generated method stub
        for(int i=0;i<list.size();i++){
            list.get(i).setCollValue("是");
            list.get(i).setCollectUserId(collectUserId);
            dao.updateDeatils(list.get(i));
        }
    }



}
