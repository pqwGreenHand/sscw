package com.zhixin.zhfz.bacs.services.urinalysis;

import com.zhixin.zhfz.bacs.dao.urinalysis.UrinalysisMapper;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.entity.UrinalysisDocProcessEntity;
import com.zhixin.zhfz.bacs.entity.UrinalysisEntity;
import com.zhixin.zhfz.bacs.entity.UrinalysisPhotoEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.sacw.common.ImageUtil;
import com.zhixin.zhfz.sacw.common.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UrinalysisServiceImpl  implements UrinalysisService{

    private static Logger logger = Logger.getLogger(UrinalysisServiceImpl.class);

    @Autowired
    private UrinalysisMapper urinalysisMapper;
    @Autowired
    private ISerialService serialService;
    @Autowired
    private UrinalysisPhotoService urinalysisPhotoService;

    @Override
    public List<UrinalysisEntity> listAllUrinalysis() throws Exception {
        return urinalysisMapper.listAllUrinalysis();
    }

    @Override
    public Integer countAllUrinalysis() throws Exception {
        return urinalysisMapper.countAllUrinalysis();
    }

    @Override
    public List<UrinalysisEntity> listUrinalysis(Map<String, Object> map) throws Exception {
        return urinalysisMapper.listUrinalysis(map);
    }

    @Override
    public Integer countUrinalysis(Map<String, Object> map) throws Exception {
        return urinalysisMapper.countUrinalysis(map);
    }

    @Override
    public Integer count(Map<String,Object> map) throws Exception {
        return urinalysisMapper.count(map);
    }

    @Override
    public void insertUrinalysis(UrinalysisEntity entity) throws Exception {
        urinalysisMapper.insertSelective(entity);
    }

    @Override
    public void updateUrinalysis(UrinalysisEntity entity) throws Exception {
        urinalysisMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void deleteUrinalysisById(Long id) throws Exception {
        urinalysisMapper.deleteUrinalysisById(id);
    }

    @Override
    public UrinalysisDocProcessEntity getProcessData(Long urinalysisId, HttpServletRequest request) {
        UrinalysisDocProcessEntity result = new UrinalysisDocProcessEntity();
        // 示例代码
        SessionInfo sessionInfo= ControllerTool.getSessionInfo(request);
        result.setXmlFileName("尿检登记台账.xml");
        result.setDownFileName("尿检登记台账.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        getResultData(result,urinalysisId,sessionInfo);
        return result;
    }

    private void getResultData(UrinalysisDocProcessEntity result,Long urinalysisId,SessionInfo sessionInfo){
        SerialEntity entity = new SerialEntity();
        UrinalysisEntity urinalysisEntity=null;
        try {
            urinalysisEntity=urinalysisMapper.getUrinalysisById(urinalysisId);
        } catch (Exception e) {
            logger.info("query exception ="+e.getMessage());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        Map mapq = new HashMap();
        if(urinalysisEntity!=null){

            mapq.put("serialNo", urinalysisEntity.getSerialNo()+"-"+urinalysisEntity.getCount());
            mapq.put("name", urinalysisEntity.getPersonName());
            if(urinalysisEntity.getSex()==0){
                mapq.put("sex", "未知的性别");
            }
            if(urinalysisEntity.getSex()==1){
                mapq.put("sex", "男");
            }
            if(urinalysisEntity.getSex()==2){
                mapq.put("sex", "女");
            }
            if(urinalysisEntity.getSex()==9){
                mapq.put("sex", "未说明的性别");
            }

            if(urinalysisEntity.getBirth()==null||urinalysisEntity.getBirth()==""){
                mapq.put("birth", "            ");
            }else{
                mapq.put("birth", urinalysisEntity.getBirth());
            }
            mapq.put("certificateNo", urinalysisEntity.getCertificateType()+urinalysisEntity.getCertificateNo());
            mapq.put("createdTime", simpleDateFormat.format(urinalysisEntity.getUrinalysisTime()));
            mapq.put("testMethod", urinalysisEntity.getTestMethod());
            mapq.put("result", urinalysisEntity.getResult());
            SerialEntity serialEntity=serialService.queryById(urinalysisEntity.getSerialId());
            mapq.put("areaName",serialEntity.getInterrogateAreaName());
            List<UrinalysisPhotoEntity> urinalysisPhotoList = urinalysisPhotoService.getPhotoByUrinalysisId(urinalysisId);
            String imgUrl = "C:/Users/liu123/Desktop/素材库/黑灰.jpg";
            if(urinalysisPhotoList != null && urinalysisPhotoList.size() >0){
                for (UrinalysisPhotoEntity urinalysisPhotoEntity:urinalysisPhotoList) {
                    imgUrl += urinalysisPhotoEntity.getUrl();
                }
            }
            // 照片
//            String imagePath = PropertyUtil.getContextProperty("urinalysisFileSavePath").toString()
//                    + serialEntity.getSerialNo() + File.separator + imgUrl ;
            String imagePath = imgUrl;
            File file = new File(imagePath);
            if (!file.exists()) {
                System.err.println("未找到图片");
            } else {
                String str = new ImageUtil().getImageStr(imagePath);
                mapq.put("img", str);
            }
        }
        result.setData(mapq);

    }

}
