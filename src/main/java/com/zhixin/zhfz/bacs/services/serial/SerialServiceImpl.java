package com.zhixin.zhfz.bacs.services.serial;

import com.zhixin.zhfz.bacs.dao.serial.ISerialMapper;
import com.zhixin.zhfz.bacs.daoOracle.IOracleMapper;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.entity.SerialStatusEntity;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SerialServiceImpl implements ISerialService {

    private static Logger logger = LoggerFactory.getLogger(SerialServiceImpl.class);

    @Autowired
    private ISerialMapper serialMapper;

    @Autowired
    private ISerialStatusService SerialStatusService;
    @Autowired
    private IFileConfigService fileConfigService;

    @Autowired
    private IOracleMapper oracleMapper;

    @Override
    public List<SerialEntity> list(Map<String, Object> params) throws Exception {
        return serialMapper.list(params);
    }

    @Override
    public int count(Map<String, Object> params) throws Exception {
        return serialMapper.count(params);
    }

    @Override
    public List<SerialEntity> timeoutRecodelist(Map<String, Object> params) throws Exception {
        return serialMapper.timeoutRecodelist(params);
    }

    @Override
    public int countTimeoutRecode(Map<String, Object> params) throws Exception {
        return serialMapper.countTimeoutRecode(params);
    }

    @Override
    public void updateStatusById(Long id, Integer status) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", status);
        serialMapper.updateStatusById(map);
    }

    @Override
    public List<SerialEntity> getIdentifyPersonList(Map<String, Object> params) {
        return serialMapper.getIdentifyPersonList(params);
    }

    @Override
    public List<SerialEntity> getPersonnelList(Map<String, Object> map) {
        return serialMapper.getPersonnelList(map);
    }

    public int getPersonnelListCount(Map<String, Object> params) throws Exception {
        return serialMapper.getPersonnelListCount(params);
    }

    @Override
    public SerialEntity getSerialByNo(SerialEntity entity1) {
        return serialMapper.getSerialByNo(entity1);
    }

    @Override
    public void changestatus(Long serialId, int stasusId) {
        SerialEntity entity = new SerialEntity();
        entity.setId(serialId);
        int oldStatusId = serialMapper.getSerialById2(entity).getStatus();
        String statusName = "";
        SerialStatusEntity status = new SerialStatusEntity();
        //判断当前状态和要修改的状态大小
        if (oldStatusId != 10) {
            status.setInterrogateSerialId(serialId);
            //0已入区 1已安检 2物品已暂存 3候问开始 4候问结束 5信息已采集 6审讯开始 7审讯结束 8物品已领取 9临时出区返回 10临时出区 11已出区
            if (stasusId == 0) {
                statusName = "已入区";
            } else if (stasusId == 1) {
                statusName = "已安检";
            } else if (stasusId == 2) {
                statusName = "物品已暂存";
            } else if (stasusId == 3) {
                statusName = "候问开始";
            } else if (stasusId == 4) {
                statusName = "候问结束";
            } else if (stasusId == 5) {
                statusName = "信息已采集";
            } else if (stasusId == 6) {
                statusName = "审讯开始";
            } else if (stasusId == 7) {
                statusName = "审讯结束";
            } else if (stasusId == 8) {
                statusName = "物品已领取";
            } else if (stasusId == 9) {
                statusName = "临时出区返回";
            } else if (stasusId == 10) {
                statusName = "临时出区";
            } else if (stasusId == 11) {
                statusName = "已出区";
            }
            if (stasusId < 12) {//oldStatusId<=stasusId&&
                entity.setStatus(stasusId);
                serialMapper.changestatus(entity);
                System.out.println("判断当前状态和要修改的状态大小entity" + entity);
            }
        } else {
            if (stasusId < 12) {
                entity.setStatus(stasusId);
                serialMapper.changestatus(entity);
                System.out.println("判断当前状态和要修改的状态大小entity" + entity);
                status.setInterrogateSerialId(serialId);
                statusName = "临时出区返回";
            }
        }
        status.setStatusName(statusName);
        SerialStatusService.insert(status);
    }

    @Override
    public SerialEntity getSerialByCuffNo(Map<String, Object> params) {
        List<SerialEntity> list = serialMapper.getSerialByCuffNo(params);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public SerialEntity queryById(Long sid) {
        return serialMapper.queryBySid(sid);
    }

    @Override
    public Integer insert(SerialEntity entity) throws Exception {
        return serialMapper.insert(entity);
    }

    @Override
    public SerialEntity findserialbyNo(String serialNo) {
        return serialMapper.findserialbyNo(serialNo);
    }

    @Override
    public SerialEntity getSerialById(SerialEntity entity) {
        return serialMapper.getSerialById(entity);
    }

    @Override
    public SerialEntity getSerialById2(SerialEntity entity) {
        return serialMapper.getSerialById2(entity);
    }

    @Override
    public SerialEntity getCaseIdByOrderId(int orderId) throws Exception {
        List<SerialEntity> list = serialMapper.getCaseIdByOrderId(orderId);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void updateSerialById(SerialEntity entity) throws Exception {
        serialMapper.updateSerialById(entity);
    }

    @Override
    public List<SerialEntity> otherPersonList(Map<String, Object> map) {
        return serialMapper.otherPersonList(map);
    }

    @Override
    public int otherPersonCount(Map<String, Object> params) throws Exception {
        return serialMapper.otherPersonCount(params);
    }

    @Override
    public List<SerialEntity> otherPersonList2(Map<String, Object> map) {
        return serialMapper.otherPersonList2(map);
    }

    @Override
    public int otherPersonCount2(Map<String, Object> params) throws Exception {
        return serialMapper.otherPersonCount2(params);
    }

    @Override
    public List<SerialEntity> listAllLawDoc(Map<String, Object> map) {
        return serialMapper.listAllLawDoc(map);
    }

    @Override
    public int countAllLawDoc(Map<String, Object> map) {
        return serialMapper.countAllLawDoc(map);
    }

    @Override
    public SerialEntity outGetSerialByNo(SerialEntity entity) {
        return serialMapper.outGetSerialByNo(entity);
    }

    @Override
    public SerialEntity GetSerialDetailInfoById(Long serialId) {
        return serialMapper.GetSerialDetailInfoById(serialId);
    }

    @Override
    public SerialEntity GetSerialInfoByNo(SerialEntity serialEntity) {
        return serialMapper.GetSerialInfoByNo(serialEntity);
    }

    @Override
    public SerialEntity inGetSerialByNo(SerialEntity serial) {
        return serialMapper.inGetSerialByNo(serial);
    }

    @Override
    public void exit(SerialEntity entity) throws Exception {
        serialMapper.exit(entity);
    }

    @Override
    public SerialEntity getSerialByNo1(SerialEntity entity1) {
        return serialMapper.getSerialByNo1(entity1);
    }

    @Override
    public void confirm(SerialEntity entity) throws Exception {
        serialMapper.confirm(entity);
    }

    /**
     * 上传照片通用方法，因为上传相同参数的照片会替代之前的照片，所以修改与新建通用
     *
     * @param file
     * @param photoName
     * @param serialId
     * @throws Exception
     */
    @Override
    public void uploadPicture(MultipartFile file, String photoName, Long serialId) throws Exception {
        SerialEntity serial = new SerialEntity();
        serial.setId(serialId);
        SerialEntity serialEntity = serialMapper.getSerialById2(serial);
        logger.info("serialEntity======" + serialEntity);
        // 生成条码
        try {
            JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(),
                EAN13TextPainter.getInstance());


        logger.info("localJBarcode======" + localJBarcode);
        // 条形码数值
        String str = "552885233512";
        // BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
        // 条形码值
        str = serialEntity.getSerialNo();
        localJBarcode.setEncoder(Code39Encoder.getInstance());
        localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
        localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
        localJBarcode.setShowCheckDigit(false);
        BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean flag = ImageIO.write(localBufferedImage, "jpg", out);
        byte[] b = out.toByteArray();
        String barCodeName = "rq-XYR" + str + FileUploadForm.BARCODEXYRRQ;

        logger.info("barCodeName======" + barCodeName);
        MultipartFile multipartFile = new MockMultipartFile(barCodeName,barCodeName,"", b);
        logger.info("multipartFile======" + multipartFile);
        //上传入区图片
        fileConfigService.upload(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serialEntity.getUuid(), serialEntity.getAreaId(), photoName, file));
        //上传入区条码图片
        fileConfigService.upload(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serialEntity.getUuid(), serialEntity.getAreaId(), barCodeName, multipartFile));
        }catch (Exception e){
            e.printStackTrace();
            logger.info("图片上传异常:"+e.toString());
        }
    }

    @Override
    public List<SerialEntity> suspectlist(Map<String, Object> params) throws Exception {
        return serialMapper.suspectlist(params);
    }

    @Override
    public int suspectcount(Map<String, Object> params) throws Exception {
        return serialMapper.suspectcount(params);
    }

    @Override
    public List<SerialEntity> otherlist(Map<String, Object> params) throws Exception {
        return serialMapper.otherlist(params);
    }

    @Override
    public int othercount(Map<String, Object> params) throws Exception {
        return serialMapper.othercount(params);
    }

    /**
     * 羁押超时告警查询
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<SerialEntity> listOver23Hours(Map<String, Object> params) throws Exception {
        return serialMapper.listOver23Hours(params);
    }

    @Override
    public List<SerialEntity> selectByOrderIdAndPersonId(Map<String, Object> params) {
        return serialMapper.selectByOrderIdAndPersonId(params);
    }

    @Override
    public void updateAppSerialById(SerialEntity entity) {
         serialMapper.updateAppSerialById(entity);
    }

    @Override
    public void updateSerialById2(SerialEntity entity) throws Exception {
        serialMapper.updateSerialById2(entity);
    }

    @Override
    public SerialEntity queryNatureByAb(Map<String, Object> params) {
        return serialMapper.queryNatureByAb(params);
    }

    @Override
    public SerialEntity queryOrgIdByPoliceNo(Map<String, Object> params) {
        return serialMapper.queryOrgIdByPoliceNo(params);
    }

    @Override
    public List<SerialEntity> querySerialBySendUserId(Map<String, Object> map) {
        return serialMapper.querySerialBySendUserId(map);
    }

    @Override
    public int queryStatusById(Map<String, Object> map) {
        return serialMapper.queryStatusById(map);
    }

    @Override
    public List<SerialEntity> selectByCaseAndPerson(Map<String, Object> params) throws Exception {
        return serialMapper.selectByCaseAndPerson(params);
    }

	@Override
	public List<SerialEntity> listWarnTime(Map<String, Object> params) {
		return serialMapper.listWarnTime(params);
	}

	@Override
	public void batchConfirmByIds(Map<String, Object> params) {
		serialMapper.batchConfirmByIds(params);
	}

	@Override
	public void updateSftjclByid(Map<String, Object> params) {
		serialMapper.updateSftjclByid(params);
	}

    @Override
    public List<Map<String, Object>> queryJzDataAjxx(Map<String, Object> map) {
        return oracleMapper.queryJzDataAjxx(map);
    }

    @Override
    public int queryJzDataAjxxCount(Map<String, Object> map) {
       // return dataAjxxMapper.queryJzDataAjxxCount(map);
        return oracleMapper.queryJzDataAjxxCount(map);
    }

    @Override
    public List<Map<String, Object>> queryJzDataPerson(Map<String, Object> params) {
        return oracleMapper.queryJzDataPerson(params);
    }

    @Override
    public List<Map<String, Object>> selectJzInfoByzjhm(Map<String, Object> params) {
        return oracleMapper.queryJzDataPerson(params);
    }

    @Override
    public void update(SerialEntity serialEntity) {
        serialMapper.update(serialEntity);
    }

    @Override
    public List<Map<String, Object>> jzFillByRybhAndAJbh(Map<String, Object> map) {
        return oracleMapper.jzFillByRybhAndAJbh(map);
    }
}
