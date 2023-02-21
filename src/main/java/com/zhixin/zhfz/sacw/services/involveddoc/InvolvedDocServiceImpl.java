package com.zhixin.zhfz.sacw.services.involveddoc;


import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.sacw.common.Utils;
import com.zhixin.zhfz.sacw.dao.involveddoc.IInvolvedDocMapper;
import com.zhixin.zhfz.sacw.entity.InputBookEntity;
import com.zhixin.zhfz.sacw.entity.InvolvedDocEntity;
import com.zhixin.zhfz.sacw.form.InvolvedDocForm;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvolvedDocServiceImpl implements IInvolvedDocService {

    private static Logger logger = Logger.getLogger(InvolvedDocServiceImpl.class);

    @Resource
    private IInvolvedDocMapper lawDocMapper;

    @Override
    public InvolvedDocEntity getProcessData(InvolvedDocForm form, HttpServletRequest request) {
        InvolvedDocEntity result = new InvolvedDocEntity();
        if (form.getNumber() == 17) {//涉案物品入库台账
            result.setInvolvedId(form.getInvolvedId());
            result.setRecordId(form.getRecordId());
            getInputData(result, request);
        }
        if (form.getNumber() == 18) {//涉案物品出库台账
            result.setInvolvedId(form.getInvolvedId());
            result.setRecordId(form.getRecordId());
            getOutputData(result, request);
        }

        if (form.getNumber() == 19) {//涉案物品移交台账
            result.setInvolvedId(form.getInvolvedId());
            result.setRecordId(form.getRecordId());
            sendOutputData(result, request);
        }

        return result;
    }

    /**
     * 获得
     *
     * @param result
     */
    private void getInputData(InvolvedDocEntity result, HttpServletRequest request) {
        result.setXmlFileName("涉案物品入库台账.xml");
        result.setDownFileName("涉案物品入库台账.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map paramMap = new HashMap();//保存台账中需要的参数值
        Map<String, Object> queryParam = new HashMap<>();//查询的参数

        queryParam.put("involvedId", result.getInvolvedId());
        queryParam.put("recordId", result.getRecordId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<InputBookEntity> inputBookEntities = lawDocMapper.listInputBook(queryParam);
            if (inputBookEntities != null && inputBookEntities.size() > 0) {//只有一笔记录
                paramMap.put("caseName", inputBookEntities.get(0).getCaseName());
                paramMap.put("caseNo", inputBookEntities.get(0).getCaseNo());
                paramMap.put("name", inputBookEntities.get(0).getName());
                paramMap.put("description", inputBookEntities.get(0).getDescription());
                paramMap.put("createdTime", inputBookEntities.get(0).getReceivedTime() == null ? "" : simpleDateFormat.format(inputBookEntities.get(0).getReceivedTime()));
                paramMap.put("policeName", inputBookEntities.get(0).getPoliceName() == null ? "" : inputBookEntities.get(0).getPoliceName());
                paramMap.put("organization", inputBookEntities.get(0).getOrganization() == null ? "" : inputBookEntities.get(0).getOrganization());
                paramMap.put("involvedOwner", inputBookEntities.get(0).getInvolvedOwner() == null ? "" : inputBookEntities.get(0).getInvolvedOwner());
                paramMap.put("detailCount", inputBookEntities.get(0).getDetailCount() == null ? "" : inputBookEntities.get(0).getDetailCount());
                paramMap.put("unit", inputBookEntities.get(0).getUnit() == null ? "" : inputBookEntities.get(0).getUnit());
                paramMap.put("address", ControllerTool.getSessionInfo(request).getCurrentArea().getName());

            }
        } catch (Exception e) {
            logger.info("查询入库台账参数异常：" + e);
        }
        result.setData(paramMap);
    }

    private void getOutputData(InvolvedDocEntity result, HttpServletRequest request) {
        result.setXmlFileName("涉案物品出库台账.xml");
        result.setDownFileName("涉案物品出库台账.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map paramMap = new HashMap();//保存台账中需要的参数值
        Map<String, Object> queryParam = new HashMap<>();//查询的参数

        queryParam.put("involvedId", result.getInvolvedId());
        queryParam.put("recordId", result.getRecordId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<InputBookEntity> inputBookEntities = lawDocMapper.listOutputBook(queryParam);
            if (inputBookEntities != null && inputBookEntities.size() > 0) {//只有一笔记录
                paramMap.put("caseName", inputBookEntities.get(0).getCaseName());
                paramMap.put("caseNo", inputBookEntities.get(0).getCaseNo());
                paramMap.put("name", inputBookEntities.get(0).getName());
                paramMap.put("description", inputBookEntities.get(0).getDescription());
                paramMap.put("createdTime", inputBookEntities.get(0).getReceivedTime() == null ? "" : simpleDateFormat.format(inputBookEntities.get(0).getReceivedTime()));
                paramMap.put("policeName", inputBookEntities.get(0).getPoliceName() == null ? "" : inputBookEntities.get(0).getPoliceName());
                paramMap.put("organization", inputBookEntities.get(0).getOrganization() == null ? "" : inputBookEntities.get(0).getOrganization());
                paramMap.put("sendOrganization", inputBookEntities.get(0).getSendOrganization() == null ? "" : inputBookEntities.get(0).getSendOrganization());
                paramMap.put("involvedOwner", inputBookEntities.get(0).getInvolvedOwner() == null ? "" : inputBookEntities.get(0).getInvolvedOwner());
                paramMap.put("outputCount", inputBookEntities.get(0).getOutputCount() == null ? "" : inputBookEntities.get(0).getOutputCount());
                paramMap.put("unit", inputBookEntities.get(0).getUnit() == null ? "" : inputBookEntities.get(0).getUnit());
                paramMap.put("address", ControllerTool.getSessionInfo(request).getCurrentArea().getName());

            }
        } catch (Exception e) {
            logger.info("查询出库台账参数异常：" + e);
        }
        result.setData(paramMap);
    }

    /*
    移交检察院、移交法院、退回检察院、退回公安局的台账
     */
    private void sendOutputData(InvolvedDocEntity result, HttpServletRequest request) {
        result.setXmlFileName("涉案物品移交台账.xml");
        result.setDownFileName("涉案物品移交台账.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map paramMap = new HashMap();//保存台账中需要的参数值
        Map<String, Object> queryParam = new HashMap<>();//查询的参数

        queryParam.put("involvedId", result.getInvolvedId());
        queryParam.put("recordId", result.getRecordId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<InputBookEntity> inputBookEntities = lawDocMapper.listOutputBook(queryParam);
            if (inputBookEntities != null && inputBookEntities.size() > 0) {//只有一笔记录
                paramMap.put("caseName", inputBookEntities.get(0).getCaseName());
                paramMap.put("caseNo", inputBookEntities.get(0).getCaseNo());
                paramMap.put("name", inputBookEntities.get(0).getName());
                paramMap.put("description", inputBookEntities.get(0).getDescription());
                paramMap.put("createdTime", inputBookEntities.get(0).getReceivedTime() == null ? "" : simpleDateFormat.format(inputBookEntities.get(0).getReceivedTime()));
                //paramMap.put("handlerPerson", inputBookEntities.get(0).getHandlerPerson()==null?"":inputBookEntities.get(0).getHandlerPerson());
//				String handlerPerson="";
//				if(inputBookEntities.get(0).getPoliceName()!=null){
//					handlerPerson+=inputBookEntities.get(0).getPoliceName();
//				}
//				if(inputBookEntities.get(0).getHandlerPerson()!=null){
//					handlerPerson+=" "+inputBookEntities.get(0).getHandlerPerson();
//				}
                paramMap.put("handlerPerson", inputBookEntities.get(0).getPoliceName());


                paramMap.put("organization", inputBookEntities.get(0).getOrganization() == null ? "" : inputBookEntities.get(0).getOrganization());
                paramMap.put("sendOrganization", inputBookEntities.get(0).getSendOrganization() == null ? "" : inputBookEntities.get(0).getSendOrganization());
                paramMap.put("involvedOwner", inputBookEntities.get(0).getInvolvedOwner() == null ? "" : inputBookEntities.get(0).getInvolvedOwner());
                paramMap.put("outputCount", inputBookEntities.get(0).getOutputCount() == null ? "" : inputBookEntities.get(0).getOutputCount());
                paramMap.put("unit", inputBookEntities.get(0).getUnit() == null ? "" : inputBookEntities.get(0).getUnit());
                paramMap.put("address", inputBookEntities.get(0).getAreaName() == null ? "" : inputBookEntities.get(0).getAreaName());
                paramMap.put("reason", inputBookEntities.get(0).getReason() == null ? "" : inputBookEntities.get(0).getReason());
                paramMap.put("outputTypeName", inputBookEntities.get(0).getOutputTypeName() == null ? "" : inputBookEntities.get(0).getOutputTypeName());

            }
        } catch (Exception e) {
            logger.info("查询出库台账参数异常：" + e);
        }
        result.setData(paramMap);
    }


}
