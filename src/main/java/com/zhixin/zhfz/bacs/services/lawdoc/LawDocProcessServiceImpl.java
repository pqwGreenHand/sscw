package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.controller.lawdoc.LawDocController;
import com.zhixin.zhfz.bacs.dao.belong.IBelongMapper;
import com.zhixin.zhfz.bacs.dao.checkbody.ICheckBodyMapper;
import com.zhixin.zhfz.bacs.dao.document.IDocumentMapper;
import com.zhixin.zhfz.bacs.dao.exhibit.IExhibitMapper;
import com.zhixin.zhfz.bacs.dao.exhibit.IExhibitdetMapper;
import com.zhixin.zhfz.bacs.dao.lawdoc.IDocCasespaceInstructionMapper;
import com.zhixin.zhfz.bacs.dao.lawdoc.IDocDietrestRegistrationMapper;
import com.zhixin.zhfz.bacs.dao.lawdoc.IDocInjurypictureInstructionMapper;
import com.zhixin.zhfz.bacs.dao.lawdoc.IDocVideoInstructionMapper;
import com.zhixin.zhfz.bacs.dao.person.IPersonMapper;
import com.zhixin.zhfz.bacs.dao.policebelong.IPoliceBelongMapper;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.BelongDocForm;
import com.zhixin.zhfz.bacs.form.CheckbodyLawDocForm;
import com.zhixin.zhfz.bacs.form.LawDocForm;
import com.zhixin.zhfz.bacs.services.checkbody.ICheckBodyService;
import com.zhixin.zhfz.bacs.services.infocollection.IInfoCollectionService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.security.ISecurityService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.serial.TempOutService;
import com.zhixin.zhfz.bacs.services.serialVideoMapping.ISerialVideoMappingService;
import com.zhixin.zhfz.bacs.services.waitingmanage.IWaitingManageService;
import com.zhixin.zhfz.common.common.IdCardUtil;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.utils.BaseConfig;
import com.zhixin.zhfz.common.utils.ImageUtil;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.common.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LawDocProcessServiceImpl implements ILawDocProcessService {

    private static Logger logger = Logger.getLogger(LawDocProcessServiceImpl.class);

    @Resource
    private IPersonMapper iPersonMapper;
   @Resource
    private ICheckBodyMapper iCheckBodyMapper;

    @Resource
    private IBelongMapper iBelongMapper;

    @Resource
    private IExhibitMapper iExhibitMapper;

    @Resource
    private IPoliceBelongMapper iPoliceBelongMapper;

    @Resource
    private IExhibitdetMapper iExhibitdetMapper;

    @Autowired
    private IDocVideoInstructionMapper iDocVideoInstructionMapper;

    @Autowired
    private IDocCasespaceInstructionMapper iDocCasespaceInstructionMapper;

    @Autowired
    private IDocSendCaseService docSendCaseService;
/*    @Autowired
    private ICheckBodyService checkBodyService;*/

    @Autowired
    private IInfoCollectionService infoCollectionService;

    @Autowired
    private IWaitingManageService waitingManageService;

    /*    @Autowired
    private IWaitingManageService waitingManageService;*/

    @Autowired
    private ICheckBodyService checkBodyService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private ISerialService serialService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private ICaseService caseService;

    @Autowired
    private IRecordService recordService;

    @Autowired
    private ISerialLawDocService iSerialLawDocService;

    @Autowired
    private IDocPhotoEvidenceService docPhotoEvidenceService;

    @Autowired
    private IDocEnforcementVideoService docEnforcementVideoService;

    @Autowired
    private IDocDietrestRegistrationMapper iDocDietrestRegistrationMapper;

    @Autowired
    private IInterrogateSerialLawDocService iInterrogateSerialLawDocService;

    @Autowired
    private IDocInjurypictureInstructionMapper iDocInjurypictureInstructionMapper;

    @Autowired
    private ISerialVideoMappingService serialVideoMappingService;

    @Autowired
    private IFileConfigService fileConfigService;

    @Autowired
    private IDocumentMapper document;
    
    @Autowired
	private TempOutService tempOutService;

    @Override
    public LawDocProcessEntity getProcessData(LawDocForm form, HttpServletRequest request, HttpSession session) throws ParseException {
        LawDocProcessEntity result = new LawDocProcessEntity();
        // 示例代码
        if (form.getNumber() == 1) {// 示例
            result.setUserId(form.getUserId());
            result.setSerialId(form.getSerialId());
            getTestData(result);
        }

        if (form.getNumber() == 2) {// 涉案人员进出办案区登记台账
            logger.debug("出区 " + form);
            result.setSerialNo(form.getSerialNo());
            result.setXmlFileName("涉案人员进出办案区登记台账.xml");
            result.setDownFileName("涉案人员进出办案区登记台账.doc");
            result.setFileType(1);
            // --------------------------------------------
            String filename1 = Utils.getUniqueId();
            String filename2 = ".doc";
            String fileName = filename1 + filename2;
            result.setTempFileName(fileName);
            // ---------------------------------
            getTestData2(result);
        }

        if (form.getNumber() == 3) {// 饮食休息登记表
            result.setSerialNo(form.getSerialNo());
            result.setSerialId(form.getSerialId());
            getTestData3(result, request);
        }

        if (form.getNumber() == 4) {// 三室录像底联说明
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getTestData4(result, request);
        }

        if (form.getNumber() == 5) {// 伤情照片制作说明
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getTestData5(result, request);
        }

        if (form.getNumber() == 6) {// 作案地点说明
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getTestData6(result, request);
        }

        if (form.getNumber() == 7) {// 示例
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getTestData7(result);
        }

        if (form.getNumber() == 8) {// 示例
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getTestData8(result);
        }

        if (form.getNumber() == 9) {// 示例
            result.setUserId(form.getUserId());
            result.setBelongingsId(form.getBelongingsId());
            result.setCaseId(form.getCaseId());
            getTestData9(result);
        }

        if (form.getNumber() == 10) {// 示例
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getTestData10(result);
        }
        if (form.getNumber() == 11) {// 违法犯罪嫌疑人人身安全检查情况登记表
            logger.debug("安检form: " + form);
            result.setSerialNo(form.getSerialNo());
            result.setSerialUUID(form.getSerialUUID());
            result.setCount(form.getCount());
            result.setXmlFileName("违法犯罪嫌疑人人身安全检查情况登记表.xml");
            result.setDownFileName("违法犯罪嫌疑人人身安全检查情况登记表.doc");
            result.setFileType(1);
            // --------------------------------------------
            String filename1 = Utils.getUniqueId();
            String filename2 = ".doc";
            String fileName = filename1 + filename2;
            result.setTempFileName(fileName);
            // ---------------------------------
            getTestData11(result, session);
        }
        if (form.getNumber() == 12) {// 示例
            // 违法犯罪嫌疑人体貌特征表
            result.setSerialNo(form.getSerialNo());
            result.setSerialId(form.getSerialId());
            getTestData12(result, request);
        }

        if (form.getNumber() == 13) {// 吸毒人员登记表
            result.setSerialNo(form.getSerialNo());
            result.setSerialId(form.getSerialId());
            getTestData13(result, request);
        }

        if (form.getNumber() == 14) {// 违法犯罪嫌疑人进入办案场所凭证
            logger.debug("入区form: " + form);
            result.setSerialNo(form.getSerialNo());
            result.setXmlFileName("违法犯罪嫌疑人进入办案场所凭证.xml");
            result.setDownFileName("违法犯罪嫌疑人进入办案场所凭证.doc");
            result.setFileType(1);
            // --------------------------------------------
            String filename1 = Utils.getUniqueId();
            String filename2 = ".doc";
            String fileName = filename1 + filename2;
            result.setTempFileName(fileName);
            // ---------------------------------
            getTestData14(result);
        }

        if (form.getNumber() == 15) {// 示例
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getOtherCheckBodyData(result);
        }

        if (form.getNumber() == 16) {// 示例
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getTestData16(result);

        }
        if (form.getNumber() == 17) {
            result.setXmlFileName("涉案物品入库台账.xml");
            result.setDownFileName("涉案物品入库台账.doc");

        }
        if (form.getNumber() == 18) {
            result.setXmlFileName("涉案物品出库台账.xml");
            result.setDownFileName("涉案物品出库台账.doc");
        }
        if (form.getNumber() == 19) {
            result.setXmlFileName("涉案物品移交台账.xml");
            result.setDownFileName("涉案物品移交台账.doc");
        }
        if (form.getNumber() == 21) {
            result.setXmlFileName("（新版）办案区、执法视音频信息登记表.xml");
            result.setDownFileName("（新版）办案区、执法视音频信息登记表.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("casename", doc.getCasename());
            result.setData(maps);
        }
        if (form.getNumber() == 22) {
            result.setXmlFileName("被传唤人家属通知书.xml");
            result.setDownFileName("被传唤人家属通知书.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("areaname", doc.getAreaname());
            result.setData(maps);
        }
        if (form.getNumber() == 24) {
            result.setXmlFileName("逮捕证文书.xml");
            result.setDownFileName("逮捕证文书.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("address", doc.getAddress());
            SimpleDateFormat sft = new SimpleDateFormat("yyyy年MM月dd日");
            String birth = sft.format(doc.getBirth().getTime());
            maps.put("birth", birth);
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            result.setData(maps);
        }

        if (form.getNumber() == 25) {
            result.setXmlFileName("电话查询记录表.xml");
            result.setDownFileName("电话查询记录表.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("address", doc.getAddress());
            SimpleDateFormat sft = new SimpleDateFormat("yyyy年MM月dd日");
            String birth = sft.format(doc.getBirth().getTime());
            maps.put("birth", birth);
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            result.setData(maps);
        }

        if (form.getNumber() == 26) {
            result.setXmlFileName("毒品案件提取笔录和称量笔录.xml");
            result.setDownFileName("毒品案件提取笔录和称量笔录.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("casename", doc.getCasename());
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            SimpleDateFormat sft = new SimpleDateFormat("yyyy年MM月dd日");
            String birth = sft.format(doc.getBirth().getTime());
            maps.put("birth", birth);
            maps.put("cardno", doc.getCardno());
            maps.put("cardtype", getcardtype(doc.getCardtype()));
            result.setData(maps);
        }
        if (form.getNumber() == 27) {
            result.setXmlFileName("发还清单.xml");
            result.setDownFileName("发还清单.doc");
        }
        if (form.getNumber() == 28) {
            result.setXmlFileName("寄押决定书.xml");
            result.setDownFileName("寄押决定书.doc");
            Map<String, Object> maps = (Map<String, Object>) session.getAttribute("downloadparam");
            result.setData(maps);
        }
        if (form.getNumber() == 29) {
            result.setXmlFileName("寄押审批表.xml");
            result.setDownFileName("寄押审批表.doc");
            Map<String, Object> maps = (Map<String, Object>) session.getAttribute("downloadparam");
            result.setData(maps);
        }
        if (form.getNumber() == 30) {
            result.setXmlFileName("检查笔录.xml");
            result.setDownFileName("检查笔录.doc");
        }
        if (form.getNumber() == 31) {
            result.setXmlFileName("检察院逮捕证审批表.xml");
            result.setDownFileName("检察院逮捕证审批表.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("address", doc.getAddress());
            maps.put("age", doc.getAge());
            maps.put("areaname", doc.getAreaname());
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            result.setData(maps);
        }
        if (form.getNumber() == 32) {
            result.setXmlFileName("接受证据材料清单.xml");
            result.setDownFileName("接受证据材料清单.doc");
        }
        if (form.getNumber() == 33) {
            result.setXmlFileName("扣押.xml");
            result.setDownFileName("扣押.doc");
        }

        if (form.getNumber() == 34) {
            result.setXmlFileName("尿检报告书.xml");
            result.setDownFileName("尿检报告书.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            SimpleDateFormat sft = new SimpleDateFormat("yyyy年MM月dd日");
            String birth = sft.format(doc.getBirth().getTime());
            maps.put("birth", birth);
            maps.put("cardno", doc.getCardno());
            maps.put("cardtype", getcardtype(doc.getCardtype()));
            result.setData(maps);
        }
        if (form.getNumber() == 35) {
            result.setXmlFileName("收缴、追缴物品清单.xml");
            result.setDownFileName("收缴、追缴物品清单.doc");
        }
        if (form.getNumber() == 36) {
            result.setXmlFileName("搜查笔录.xml");
            result.setDownFileName("搜查笔录.doc");
        }
        if (form.getNumber() == 37) {
            result.setXmlFileName("提解审批表.xml");
            result.setDownFileName("提解审批表.doc");
            Map<String, Object> maps = (Map<String, Object>) session.getAttribute("downloadparam");
            result.setData(maps);
        }
        if (form.getNumber() == 38) {
            result.setXmlFileName("提解通知书.xml");
            result.setDownFileName("提解通知书.doc");
            Map<String, Object> maps = (Map<String, Object>) session.getAttribute("downloadparam");
            result.setData(maps);
        }
        if (form.getNumber() == 39) {
            result.setXmlFileName("违法犯罪嫌疑人信息查询情况登记表（2018版）.xml");
            result.setDownFileName("违法犯罪嫌疑人信息查询情况登记表（2018版）.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("address", doc.getAddress());
            maps.put("cardno", doc.getCardno());
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            result.setData(maps);
        }
        if (form.getNumber() == 40) {
            result.setXmlFileName("违法嫌疑人信息采集登记表.xml");
            result.setDownFileName("违法嫌疑人信息采集登记表.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("cardno", doc.getCardno());
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            result.setData(maps);
        }
        if (form.getNumber() == 41) {
            result.setXmlFileName("延长临时寄押期限通知书.xml");
            result.setDownFileName("延长临时寄押期限通知书.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("age", doc.getAge());
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            result.setData(maps);
        }
        if (form.getNumber() == 42) {
            result.setXmlFileName("延长临时寄押审批表.xml");
            result.setDownFileName("延长临时寄押审批表.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("address", doc.getAddress());
            maps.put("age", doc.getAge());
            maps.put("areaname", doc.getAreaname());
            if (doc.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (doc.getSex() == 2) {
                maps.put("sex", "女");
            }
            result.setData(maps);
        }
        if (form.getNumber() == 43) {
            result.setXmlFileName("证据清单.xml");
            result.setDownFileName("证据清单.doc");

        }
        if (form.getNumber() == 44) {
            result.setXmlFileName("制作说明（所内）.xml");
            result.setDownFileName("制作说明（所内）.doc");
        }
        if (form.getNumber() == 45) {
            result.setXmlFileName("抓获经过11.xml");
            result.setDownFileName("抓获经过11.doc");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serid", form.getSerialId());
            DocumenEntity doc = document.selectdocument(map);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("sername", doc.getSername());
            maps.put("cardno", doc.getCardno());
            SimpleDateFormat sft = new SimpleDateFormat("yyyy年MM月dd日");
            String birth = sft.format(doc.getBirth().getTime());
            maps.put("birth", birth);
            result.setData(maps);
        }
        if (form.getNumber() == 46) {
            result.setXmlFileName("体检表.xml");
            result.setDownFileName("体检表.xls");
        }
        if (form.getNumber() == 47) {
            result.setXmlFileName("体检登记表（一式两份）.xml");
            result.setDownFileName("体检登记表（一式两份）.doc");
        }
        if (form.getNumber() == 48) {
            result.setXmlFileName("涉案人员扣留物品代为保管物品登记表.xml");
            result.setDownFileName("涉案人员扣留物品代为保管物品登记表.doc");
            result.setFileType(1);
            // --------------------------------------------
            String filename1 = Utils.getUniqueId();
            String filename2 = ".doc";
            String fileName = filename1 + filename2;
            result.setTempFileName(fileName);

            logger.info("涉案人员扣留物品代为保管物品登记表 " + form);
            BelongDocForm belongDocForm = new BelongDocForm();
            belongDocForm.setDataId(form.getAreaId());
            belongDocForm.setStartTime(form.getStartTime());
            belongDocForm.setEndTime(form.getEndTime());
            belongDocForm.setSerialId(form.getSerialId().toString());
            belongDocForm.setSerialNo(form.getSerialNo());
            getProcessData5(belongDocForm, result);
        }

        if (form.getNumber() == 50) {
            result.setXmlFileName("南沙分局案件审核表.xml");
            result.setDownFileName("案件与事件审核表.doc");

        }
       /* if (form.getNumber() == 49) {//涉案物品移交
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            getTestData49(result);
        }*/
        /*if (form.getNumber() == 50) {
            result.setXmlFileName("南沙分局案件审核表.xml");
            result.setDownFileName("案件与事件审核表.doc");
            Map<String, Object> maps = (Map<String, Object>) session.getAttribute("downloadparam");
            result.setData(maps);
        }*/
        /*if (form.getNumber() == 51) {
            Integer id = Integer.parseInt(session.getAttribute("appRecordId").toString());
            Map<String,Object> params = recordService.selectRecordTypeById(id);
            String ask_type = params.get("ask_type").toString();
            System.out.println(ask_type);
            Integer askType = Integer.parseInt(params.get("ask_type").toString());
            if(askType==0||askType==1){
                result.setXmlFileName("手机笔录无问答模板.xml");
                getTestData51NoAsk(params,result);
                result.setDownFileName("手机笔录无问答模板.doc");
            }else if(Integer.parseInt(params.get("ask_type").toString())==2){
                result.setXmlFileName("手机笔录有问答模板.xml");
                result.setDownFileName("手机笔录有问答模板.doc");
                getTestData51Ask(id,result);
            }
        }*/
        if (form.getNumber() == 52) {
            /*result.setXmlFileName("涉案物品入库保管单.xml");
            result.setDownFileName("涉案物品入库保管单.doc");*/
            result.setXmlFileName("涉案物品临时保管台账.xml");
            result.setDownFileName("涉案物品临时保管台账.doc");
            result.setFileType(1);
            String filename1 = Utils.getUniqueId();
            String filename2 = ".doc";
            String fileName = filename1 + filename2;
            result.setTempFileName(fileName);
            result.setSerialId(form.getSerialId());
            result.setSerialNo(form.getSerialNo());
            logger.info("涉案物品临时保管台账" + form);
            getTestData52(result);
        }
        if (form.getNumber() == 53) {
            //result.setBelongingsId(form.getBelongingsId());
            result.setPoliceId(form.getPoliceId());
            getTestData53(result);

        }

        if (form.getNumber() == 57) {
            result.setXmlFileName("民警随身物品代为保管物品登记表.xml");
            result.setDownFileName("民警随身物品代为保管物品登记表.doc");
            result.setFileType(1);
            String filename1 = Utils.getUniqueId();
            String filename2 = ".doc";
            String fileName = filename1 + filename2;
            result.setTempFileName(fileName);
            logger.info("民警随身涉案物品代为保管物品登记表 " + form);
            BelongDocForm belongDocForm = new BelongDocForm();
            belongDocForm.setDataId(form.getAreaId());
            belongDocForm.setStartTime(form.getStartTime());
            belongDocForm.setEndTime(form.getEndTime());
            belongDocForm.setSerialId(form.getSerialId().toString());
            belongDocForm.setSerialNo(form.getSerialNo());
            getProcessData57(belongDocForm, result);
        }
        
      //五合一台账
        if (form.getNumber() == 58) {
            logger.debug("出区 " + form);
            result.setSerialNo(form.getSerialNo());
           /* result.setXmlFileName("涉案人员五合一台账.xml");
            result.setDownFileName("涉案人员五合一台账.doc");
            result.setFileType(1);
            // --------------------------------------------
            String filename1 = Utils.getUniqueId();
            String filename2 = ".doc";
            String fileName = filename1 + filename2;
            result.setTempFileName(fileName);
            // ---------------------------------   
*/            LawDocProcessEntity formatResult = new LawDocProcessEntity();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            getTestData14(result);
            formatResult.setData(result.getData());
            FiveDoc.getFiveDoc(formatResult,"RQ");
            resultMap.putAll(formatResult.getData());
           // result.setData(formatResult.getData());
            
            getTestData11(result, session);
            formatResult = new LawDocProcessEntity();
            formatResult.setData(result.getData());
            FiveDoc.getFiveDoc(formatResult,"AJ");
            resultMap.putAll(formatResult.getData());
            //result.setData(formatResult.getData());
            
            getTestData9(result); 
            formatResult = new LawDocProcessEntity();
            formatResult.setData(result.getData());
            FiveDoc.getFiveDoc(formatResult,"SS");
            resultMap.putAll(formatResult.getData());
            //result.setData(formatResult.getData());
            
            BelongDocForm belongDocForm = new BelongDocForm();
            belongDocForm.setDataId(form.getAreaId());
            belongDocForm.setStartTime(form.getStartTime());
            belongDocForm.setEndTime(form.getEndTime());
            belongDocForm.setSerialId(form.getSerialId().toString());
            belongDocForm.setSerialNo(form.getSerialNo());
            getProcessData5(belongDocForm, result); 
            formatResult = new LawDocProcessEntity();
            formatResult.setData(result.getData());
            FiveDoc.getFiveDoc(formatResult,"SA");
            resultMap.putAll(formatResult.getData());
            //result.setData(formatResult.getData());
            
            getTestData2(result);
            formatResult = new LawDocProcessEntity();
            formatResult.setData(result.getData());
            FiveDoc.getFiveDoc(formatResult,"CQ");
            resultMap.putAll(formatResult.getData());
            //result.setData(formatResult.getData());
            
            result.setXmlFileName("涉案人员五合一台账.xml");
            result.setDownFileName("涉案人员五合一台账.doc");
            result.setFileType(1);
            // --------------------------------------------
            String filename1 = Utils.getUniqueId();
            String filename2 = ".doc";
            String fileName = filename1 + filename2;
            result.setTempFileName(fileName);
            result.setData(resultMap);
            
        }
        
        
        
        
        return result;
    };

    // 送案表
    private void getTestData(LawDocProcessEntity result) {
        result.setXmlFileName("送案表.xml");
        result.setDownFileName("送案表.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        PersonEntity person = personService.getPersonById(result.getUserId());
        DocSendcaseEntity doc = docSendCaseService.getDocSendcaseById(result.getSerialId());
        if (doc == null) {
            doc = new DocSendcaseEntity();
        }
        if (person == null) {
            person = new PersonEntity();
        }
        Map mapq = new HashMap();
        List<Map> list = new ArrayList<Map>();
        mapq.put("name", person.getName());
        if (person.getSex() == 0) {
            mapq.put("sex", "未知的性别");
        }
        if (person.getSex() == 1) {
            mapq.put("sex", "男");
        }
        if (person.getSex() == 2) {
            mapq.put("sex", "女");
        }
        if (person.getSex() == 9) {
            mapq.put("sex", "未说明的性别");
        }
        if (person.getBirth() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            String year1 = sdf.format(person.getBirth()).substring(0, 4);
            int year2 = new Date().getYear() + 1900;
            mapq.put("age", year2 - Integer.parseInt(year1));
        }
        mapq.put("nation", person.getNation());
        mapq.put("job", person.getJob());
        mapq.put("register", person.getCensusRegister());
        mapq.put("politic", person.getPolitic());
        mapq.put("healthy", doc.getPhysicalCondition());
        mapq.put("procedure", doc.getLawProcedure());
        mapq.put("comment", doc.getLawProcedureComment());
        mapq.put("fact", doc.getBasicFacts());
        mapq.put("evidence", doc.getEvidenceCondition());
        mapq.put("advice", doc.getDealSuggestion());
        mapq.put("leaderAd", doc.getLeaderInstructions());
        mapq.put("tel", doc.getTelephone() == null ? "" : doc.getTelephone());
        mapq.put("taker1", doc.getUndertaker1() == null ? "" : doc.getUndertaker1()); // &#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;&#x0020;
        mapq.put("taker2", doc.getUndertaker2() == null ? "" : doc.getUndertaker2());
        mapq.put("legalmember", doc.getLegalMember() == null ? "" : doc.getLegalMember());
        result.setData(mapq);
    }

    // 涉案人员进出办案区登记台账
    private void getTestData2(LawDocProcessEntity result) throws ParseException {
    	logger.info("====出区台账数据====="+result.toString());
        SerialEntity entity = new SerialEntity();
        SerialEntity serial = new SerialEntity();
        entity.setSerialNo(result.getSerialNo());
        SecurityEntity temSecurity = new SecurityEntity();
        SecurityEntity security = new SecurityEntity();
        CheckBodyEntity temCheckBody = new CheckBodyEntity();
        CheckBodyEntity checkBody = new CheckBodyEntity();
        InfoCollectionEntity temInfoCollection = new InfoCollectionEntity();
        InfoCollectionEntity infoCollection = new InfoCollectionEntity();
        WaitingRecordEntity temWaitingRecord = new WaitingRecordEntity();
        List<WaitingRecordEntity> waitingRecords = new ArrayList<WaitingRecordEntity>();
        List<RecordEntity> records = new ArrayList<RecordEntity>();
        List<TempOutEntity> listout = new   ArrayList<TempOutEntity>();
        try {
            serial = serialService.outGetSerialByNo(entity);
            temSecurity.setSerialId(serial.getId());
            security = securityService.getOneSecurity(temSecurity);
            temCheckBody.setInterrogateSerialId(serial.getId());
            checkBody = checkBodyService.getCheckBodyBySerialId(temCheckBody);
            temInfoCollection.setSerialId(serial.getId());
            infoCollection = infoCollectionService.getInfoCollectionBySerialId(temInfoCollection);
            temWaitingRecord.setSerialId(serial.getId());
            waitingRecords = waitingManageService.getWaitingRecordBySerialId(temWaitingRecord);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("serialId", serial.getId());
            records = recordService.selectBySerialId(map);
            //查询 临时出区的信息
			listout =tempOutService.queryBySerialId(Integer.valueOf(serial.getId()+""));
        } catch (Exception e) {
            logger.debug("出区Exception: " + e);
        }

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        Map mapq = new HashMap();
        if (serial.getInterrogateAreaName() != null && !"".equals(serial.getInterrogateAreaName())) {
            mapq.put("department", serial.getInterrogateAreaName());
        } else {
            mapq.put("department", "");
        }
        mapq.put("approver", "");

        if (serial.getName() != null && !"".equals(serial.getName())) {
            mapq.put("name", serial.getName());
        } else {
            mapq.put("name", "");
        }
        mapq.put("sex", serial.getSexName());
        if (serial.getBirth() != null) {
            String year1 = sdf1.format(serial.getBirth()).substring(0, 4);
            int year2 = new Date().getYear() + 1900;
            mapq.put("age", year2 - Integer.parseInt(year1));
        }else {
            if(serial.getCertificateTypeId() != null
                    && serial.getCertificateTypeId() == 111
                    && serial.getCertificateNo() != null
                    && !serial.getCertificateNo().equals("")){
                Map<String,String> map= IdCardUtil.getBirAgeSex(serial.getCertificateNo());
                mapq.put("age",(map.get("birthday").toString()));
            }
        }

        if (serial.getCertificateTypeId() != null && serial.getCertificateTypeId() == 1) {
            if (serial.getCertificateNo() != null) {
                if (serial.getCertificateNo().startsWith("110")) {
                    mapq.put("isThisCity", "是");
                } else {
                    mapq.put("isThisCity", "不是");
                }
            } else {
                mapq.put("isThisCity", "不是");
            }
        } else {
            mapq.put("isThisCity", "不是");
        }
        String caseType = "";
        if (serial.getCaseType() != null) {
            if (serial.getCaseType() == 0) {
                caseType = "刑事";
            } else {
                caseType = "行政";
            }
        }
        mapq.put("caseType", caseType);

        if (serial.getEntranceProcedure() != null && !"".equals(serial.getEntranceProcedure())) {
            mapq.put("inReason", serial.getEntranceProcedure());
        } else {
            mapq.put("inReason", "");
        }

        if (serial.getInTime() != null && !"".equals(serial.getInTime())) {
            mapq.put("inTime", sdf1.format(serial.getInTime()));
        } else {
            mapq.put("inTime", "");
        }

        List<Map> list = new ArrayList<Map>();
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH时mm分");
        // 安检
        // System.err.println("security= "+security);
        if (security != null) {
            Map smap = new HashMap();
            if (security.getCheckedStartTime() != null && !"".equals(security.getCheckedStartTime())) {
                smap.put("startTime", sdf2.format(security.getCheckedStartTime()));
            } else {
                smap.put("startTime", "");
            }

            if (security.getCheckedEndTime() != null && !"".equals(security.getCheckedEndTime())) {
                smap.put("endTime", sdf2.format(security.getCheckedEndTime()));
            } else {
                smap.put("endTime", "");
            }
            smap.put("roomName", "人身安全检查室");
            list.add(smap);
        }
        // 999体检
        // System.err.println("checkBody.getChecktimeStart=
        // "+checkBody.getChecktimeStart());
        if (checkBody != null) {
            Map ckmap = new HashMap();
            if (checkBody.getChecktimeStart() != null && !"".equals(checkBody.getChecktimeStart())) {
                ckmap.put("startTime", sdf2.format(checkBody.getChecktimeStart()));
            } else {
                ckmap.put("startTime", "");
            }

            if (checkBody.getChecktimeEnd() != null && !"".equals(checkBody.getChecktimeEnd())) {
                ckmap.put("endTime", sdf2.format(checkBody.getChecktimeEnd()));
            } else {
                ckmap.put("endTime", "");
            }

            ckmap.put("roomName", "999身体检查室");
            list.add(ckmap);
        }
        // 信息采集
        if (infoCollection != null) {
            Map icmap = new HashMap();
            if (infoCollection.getCreatedTime() != null && !"".equals(infoCollection.getCreatedTime())) {
                icmap.put("startTime", sdf2.format(infoCollection.getCreatedTime()));
            } else {
                icmap.put("startTime", "");
            }

            if (infoCollection.getUpdatedTime() != null && !"".equals(infoCollection.getUpdatedTime())) {
                icmap.put("endTime", sdf2.format(infoCollection.getUpdatedTime()));
            } else {
                icmap.put("endTime", "");
            }
            icmap.put("roomName", "信息采集室");
            list.add(icmap);
        }
        // 候问
        if (waitingRecords != null && waitingRecords.size() > 0) {
            for (int i = 0; i < waitingRecords.size(); i++) {
                temWaitingRecord = waitingRecords.get(i);
                Map wrmap = new HashMap();
                if (temWaitingRecord.getInTime() != null && !"".equals(temWaitingRecord.getInTime())) {
                    wrmap.put("startTime", sdf2.format(temWaitingRecord.getInTime()));
                } else {
                    wrmap.put("startTime", "");
                }

                if (temWaitingRecord.getOutTime() != null && !"".equals(temWaitingRecord.getOutTime())) {
                    wrmap.put("endTime", sdf2.format(temWaitingRecord.getOutTime()));
                } else {
                    wrmap.put("endTime", "");
                }
                wrmap.put("roomName", temWaitingRecord.getRoomName());
                list.add(wrmap);
            }
        }

        // 审讯
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (records != null && records.size() > 0) {
            for (int i = 0; i < records.size(); i++) {
                RecordEntity temRecord = new RecordEntity();
                temRecord = records.get(i);
                logger.info("审讯====="+temRecord.toString());
                Map rmap = new HashMap();
                if (temRecord.getStartTime() != null ) {
                	logger.info("StartTime====="+temRecord.getStartTime());
                    try {
                        rmap.put("startTime", sdf2.format(temRecord.getStartTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                	logger.info("StartTime1====="+temRecord.getStartTime());
                    rmap.put("startTime", "");
                }

                if (temRecord.getEndTime() != null ) {
                	logger.info("endTime====="+temRecord.getEndTime());
                    try {
                        rmap.put("endTime", sdf2.format(temRecord.getEndTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                 	logger.info("endTime11====="+temRecord.getEndTime());
                    rmap.put("endTime", "");
                }
                rmap.put("roomName", temRecord.getRoomName());
                list.add(rmap);
            }
        }
        
        //临时
        if(listout.size()>0){
			for (int i = 0; i < listout.size(); i++) {
				Map wrmap = new HashMap();
				if(listout.get(i).getOutTime() != null  &&  !"".equals(listout.get(i).getOutTime())){
					wrmap.put("startTime", sdf2.format(listout.get(i).getOutTime()));
				}else{
					wrmap.put("startTime", "");
				}
				if(listout.get(i).getBackTime() != null  &&  !"".equals(listout.get(i).getBackTime())){
					wrmap.put("endTime", sdf2.format(listout.get(i).getBackTime()));
				}else{
					wrmap.put("endTime", "");
				}
				wrmap.put("roomName", "临时出区");
				list.add(wrmap);
			}
		}
        
        
        
        
        int size = list.size();
        if (size < 7) {
            for (int i = 0; i < (7 - size); i++) {
                Map map = new HashMap();
                map.put("startTime", "");
                map.put("endTime", "");
                map.put("roomName", "");
                list.add(map);
            }
        }

        if (serial.getOutTime() != null && !"".equals(serial.getOutTime())) {
            mapq.put("outTime", sdf1.format(serial.getOutTime()));
        } else {
            mapq.put("outTime", "");
        }
        if (serial.getOutReason() != null && !"".equals(serial.getOutReason())) {
            mapq.put("outReason", serial.getOutReason());
        } else {
            mapq.put("outReason", "");
        }
        mapq.put("list", list);

        result.setData(mapq);

    }

    // 违法人员饮食和休息登记表
    private void getTestData3(LawDocProcessEntity result, HttpServletRequest request) {
        result.setXmlFileName("违法人员饮食和休息登记表.xml");
        result.setDownFileName("违法人员饮食和休息登记表.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        SerialEntity serialEntity = new SerialEntity();
        serialEntity.setSerialNo(result.getSerialNo());
        SerialEntity interrogateSeria = this.serialService.GetSerialDetailInfoById(result.getSerialId());
        Map mapq = new HashMap();
        if (interrogateSeria != null) {
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy年MM月dd日");
            String sdfNowTime = sdfNow.format(new Date());
            mapq.put("policeTime", sdfNowTime);
            mapq.put("personTime", sdfNowTime);
            if (interrogateSeria.getPersonName() != null) {
                mapq.put("name", interrogateSeria.getPersonName());
            } else {
                mapq.put("name", "");
            }

            Integer sex = interrogateSeria.getSex();
            if (sex != null) {
                if (sex == 0) {
                    mapq.put("sex", "  ☐男 ☐女  ☑未知的性别 ☐未说明的性别");
                }
                if (sex == 1) {
                    mapq.put("sex", "  ☑男 ☐女  ☐未知的性别 ☐未说明的性别 ");
                }
                if (sex == 2) {
                    mapq.put("sex", "  ☐男 ☑女  ☐未知的性别 ☐未说明的性别 ");
                }
                if (sex == 9) {
                    mapq.put("sex", "  ☐男 ☐女  ☐未知的性别 ☑未说明的性别 ");
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            if (interrogateSeria.getBirth() != null) {
                String year1 = sdf.format(interrogateSeria.getBirth()).substring(0, 4);
                int year2 = new Date().getYear() + 1900;
                mapq.put("age", year2 - Integer.parseInt(year1));
            }
            if (interrogateSeria.getCaseType() != null) {
                if (interrogateSeria.getCaseType() == 0) {
                    mapq.put("involvedType", "  ☑刑事  ☐行政 ");
                }
                if (interrogateSeria.getCaseType() == 1) {
                    mapq.put("involvedType", "  ☐刑事   ☑行政 ");
                }
            }
        }

        SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
        if (sessioninfo != null) {
            String areaName = "";
            if (sessioninfo.getCurrentArea() != null) {
                areaName = sessioninfo.getCurrentArea().getName();
            }
            if (areaName != null && !"".equals(areaName)) {
                mapq.put("workSpace", areaName);
            }
        } else {
            mapq.put("workSpace", "");
        }
        // -------------------------------------------

        List<DocDietrestRegistrationEntity> listValue = new ArrayList<DocDietrestRegistrationEntity>();
        List<WaitingRecordEntity> waitRecordList = iSerialLawDocService.queryWaitRecord(result.getSerialId());
        if (waitRecordList != null && waitRecordList.size() > 0) {
            for (int i = 0; i < waitRecordList.size(); i++) {
                WaitingRecordEntity obj = waitRecordList.get(i);
                DocDietrestRegistrationEntity waitobj = new DocDietrestRegistrationEntity();
                waitobj.setRegistry("休息");
                Date waitTime = obj.getCreatedTime();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (waitTime != null) {
                    String dateTime = sdf1.format(waitTime);
                    waitobj.setDate(dateTime);
                }
                if (obj.getInTime() != null) {
                    String inTime = sdf2.format(obj.getInTime());
                    if (inTime != null && !"".equals(inTime)) {
                        String[] inTimes = inTime.split(" ");
                        waitobj.setBeginTime(inTimes[1]);
                    }

                }
                if (obj.getOutTime() != null) {
                    String outTime = sdf2.format(obj.getOutTime());
                    if (outTime != null && !"".equals(outTime)) {
                        String[] outTimes = outTime.split(" ");
                        waitobj.setEndTime(outTimes[1]);
                    }
                }
                if (obj.getPersonName() != null && !"".equals(obj.getPersonName())) {
                    waitobj.setCriminalSuspect(obj.getPersonName());
                }
                listValue.add(waitobj);
            }
        }

        // -----------------------------------------
        List<Map> list = new ArrayList<Map>();
        List<DocDietrestRegistrationEntity> list1 = new ArrayList<DocDietrestRegistrationEntity>();
        list1 = this.iDocDietrestRegistrationMapper.queryDietRestInfoBySeriaNo(result.getSerialId());
        System.out.println("##############list1##################" + list1);
        listValue.addAll(list1);
        for (int i = 0; i < listValue.size(); i++) {
            Map mapValue = new HashMap();
            DocDietrestRegistrationEntity obj = listValue.get(i);
            mapValue.put("item", obj.getRegistry());
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (obj.getDate() != null) {
                mapValue.put("date", obj.getDate());
            } else {
                mapValue.put("date", "");
            }
            if (obj.getBeginTime() != null) {
                mapValue.put("startDate", obj.getBeginTime());
            } else {
                mapValue.put("startDate", "");
            }
            if (obj.getEndTime() != null) {
                mapValue.put("endDate", obj.getEndTime());
            } else {
                mapValue.put("endDate", "");
            }

            list.add(mapValue);
        }
        int size = list.size();
        if (size < 10) {
            for (int i = 0; i < (10 - size); i++) {
                Map map = new HashMap();
                map.put("item", "");
                map.put("date", "");
                map.put("startDate", "");
                map.put("endDate", "");
                list.add(map);
            }
        }
        mapq.put("list", list);
        result.setData(mapq);
    }

    // 三室录像底联说明
    private void getTestData4(LawDocProcessEntity result, HttpServletRequest request) {
        result.setXmlFileName("三室录像底联说明.xml");
        result.setDownFileName("三室录像底联说明.doc");
        result.setFileType(1);
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map mapq = new HashMap();
        List<Map> list = new ArrayList<Map>();
        // 填充数据
        SerialEntity serialEntity = new SerialEntity();
        serialEntity.setSerialNo(result.getSerialNo());
        SerialEntity interrogateSeria = serialService.GetSerialInfoByNo(serialEntity);
        DocVideoInstructionEntity obj = iDocVideoInstructionMapper.getVideoInstructionBySerialId(result.getSerialId());
        mapq.put("caseName", interrogateSeria.getCaseName());
        if (obj != null) {
            mapq.put("caseContent", obj.getInformationContent());
            System.out.println("------------------fomate----------------" + obj.getFomate());
            mapq.put("fomate", obj.getFomate());
            if (obj.getExtractTime() != null && !"".equals(obj.getExtractTime())) {
                String time = obj.getExtractTime();
                String[] times = time.split("-");
                mapq.put("year", times[0]);
                mapq.put("month", times[1]);
                mapq.put("day", times[2]);
            } else {
                mapq.put("year", "");
                mapq.put("month", "");
                mapq.put("day", "");
            }
            mapq.put("space", obj.getExtractSites());
            mapq.put("method", obj.getExtractMethod());
            mapq.put("resouce", obj.getVedioResouce());
            if ("0".equals(obj.getIsOriginal())) {
                mapq.put("scope", "是");
            }
            if ("1".equals(obj.getIsOriginal())) {
                mapq.put("scope", "否");
            }
            mapq.put("reason", obj.getReason());
            mapq.put("saveSpace", obj.getSaveSpace());
            SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
            if (sessioninfo != null) {
                String areaName = "";
                if (sessioninfo.getCurrentArea() != null) {
                    areaName = sessioninfo.getCurrentArea().getName();
                }
                if (areaName != null && !"".equals(areaName)) {
                    mapq.put("handSpace", areaName);
                }
            } else {
                mapq.put("handSpace", "");
            }
            mapq.put("witnesses", obj.getWitnesses());
            mapq.put("maker", obj.getMaker());
            mapq.put("policemen", obj.getPolicemen());

            System.err.println((String) (PropertyUtil.getContextProperty("lawdocImageSavePath")));
            System.err.println(result.getSerialNo());
            System.err.println(Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator);

            // 照片
            String imagePath = (String) (PropertyUtil.getContextProperty("lawdocImageSavePath")
                    + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator) + "docvedio-"
                    + result.getSerialNo() + "-yt.jpg";
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("serialId", result.getSerialId());
            try {
                List<SerialVideoMappingEntity> serialVideoMappingEntities = serialVideoMappingService.list(map1);
                if (serialVideoMappingEntities.size() > 0) {

                    imagePath = serialVideoMappingEntities.get(0).getWebPath() + File.separator + "lawdocImageUpload" + File.separator
                            + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator + "docvedio-"
                            + result.getSerialNo() + "-yt.jpg";

                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
            File file = new File(imagePath);
            if (!file.exists()) {
                System.err.println("未找到图片");
            } else {
                mapq.put("image", new ImageUtil().getImageStr(imagePath));
            }
        } else {
            mapq.put("caseContent", "");
            mapq.put("fomate", "");

            mapq.put("year", "");
            mapq.put("month", "");
            mapq.put("day", "");
            mapq.put("space", "");
            mapq.put("method", "");
            mapq.put("resouce", "");
            mapq.put("scope", "");
            mapq.put("reason", "");
            mapq.put("saveSpace", "");
            SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
            if (sessioninfo != null) {
                String areaName = "";
                if (sessioninfo.getCurrentArea() != null) {
                    areaName = sessioninfo.getCurrentArea().getName();
                }
                if (areaName != null && !"".equals(areaName)) {
                    mapq.put("handSpace", areaName);
                }
            } else {
                mapq.put("handSpace", "");
            }
            mapq.put("witnesses", "");
            mapq.put("maker", "");
            mapq.put("policemen", "");
        }
        result.setData(mapq);
    }

    // 伤情照片底联说明
    private void getTestData5(LawDocProcessEntity result, HttpServletRequest request) {

        result.setXmlFileName("伤情照片底联说明.xml");
        result.setDownFileName("伤情照片底联说明.doc");
        result.setFileType(1);
        // result.setTempFileName("伤情照片底联说明.doc");
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map mapq = new HashMap();
        List<Map> list = new ArrayList<Map>();
        // 填充数据
        SerialEntity interrogateSerialEntity = new SerialEntity();
        interrogateSerialEntity.setSerialNo(result.getSerialNo());
        SerialEntity interrogateSeria = serialService.GetSerialInfoByNo(interrogateSerialEntity);
        DocInjurypictureInstructionEntity obj = iDocInjurypictureInstructionMapper.queryDocInjueryPicture(result.getSerialId());
        mapq.put("caseName", interrogateSeria.getCaseName());

        if (interrogateSeria != null) {
            mapq.put("caseName", interrogateSeria.getCaseName());
        }
        if (obj != null) {
            System.err.println("##########" + obj);
            String maketime = obj.getMakeTime();
            if (maketime != null && !"".equals(maketime)) {
                String[] maketimes = maketime.split("-");
                if (maketimes.length > 0 && maketimes != null) {
                    mapq.put("year", maketimes[0]);
                    mapq.put("month", maketimes[1]);
                    mapq.put("day", maketimes[2]);
                }
            }
            mapq.put("produceSpace", obj.getMakeSites());
            mapq.put("saveSpace", obj.getSaveSpace());
            mapq.put("content", obj.getContent());
            mapq.put("state", obj.getIsOriginal());
            mapq.put("recordPerson", obj.getRecordPerson());
            mapq.put("witnesses", obj.getWitnesses());
            mapq.put("theLord", obj.getTheLord());
            mapq.put("maker", obj.getMaker());
            // 照片
            String imagePath = (String) (PropertyUtil.getContextProperty("lawdocImageSavePath")
                    + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator) + "docinjury-"
                    + result.getSerialNo() + "-yt.jpg";
            File file = new File(imagePath);
            if (!file.exists()) {
                System.err.println("未找到图片");
            } else {
                mapq.put("image", new ImageUtil().getImageStr(imagePath));
            }
        } else {
            mapq.put("year", " ");
            mapq.put("month", " ");
            mapq.put("day", " ");
            mapq.put("produceSpace", " ");
            mapq.put("saveSpace", " ");
            mapq.put("state", " ");
            mapq.put("recordPerson", " ");
            mapq.put("witnesses", " ");
            mapq.put("theLord", " ");
            mapq.put("maker", " ");
            mapq.put("content", "");

        }
        SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
        if (sessioninfo != null) {
            String areaName = "";
            if (sessioninfo.getCurrentArea() != null) {
                areaName = sessioninfo.getCurrentArea().getName();
            }
            if (areaName != null && !"".equals(areaName)) {
                mapq.put("workspce", areaName);
            }
        } else {
            mapq.put("workspce", "");
        }
        result.setData(mapq);
    }

    // 作案地点照片底联说明
    private void getTestData6(LawDocProcessEntity result, HttpServletRequest request) {
        result.setXmlFileName("作案地点照片底联说明.xml");
        result.setDownFileName("作案地点照片底联说明.doc");
        result.setFileType(1);
        // result.setTempFileName("伤情照片底联说明.doc");
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map mapq = new HashMap();
        List<Map> list = new ArrayList<Map>();
        // 填充数据
        SerialEntity interrogateSerialEntity = new SerialEntity();
        interrogateSerialEntity.setSerialNo(result.getSerialNo());
        SerialEntity interrogateSeria = serialService.GetSerialInfoByNo(interrogateSerialEntity);
        DocCasespaceInstructionEntity obj = this.iDocCasespaceInstructionMapper.queryDocCasespaceById(result.getSerialId());
        mapq.put("caseName", interrogateSeria.getCaseName());

        if (interrogateSeria != null) {
            mapq.put("caseName", interrogateSeria.getCaseName());
        }
        if (obj != null) {
            System.err.println("##########" + obj);
            String maketime = obj.getMakeTime();
            if (maketime != null && !"".equals(maketime)) {
                /*
                 * String[] maketimes=maketime.split("-");
				 * if(maketimes.length>0&&maketimes!=null){
				 * mapq.put("year",maketimes[0] ); mapq.put("month",
				 * maketimes[1] ); mapq.put("day", maketimes[2] ); }
				 */
                mapq.put("makeTime", obj.getMakeTime());
            }
            mapq.put("makeSpace", obj.getMakeSites());
            mapq.put("saveSpace", obj.getSaveSpace());
            mapq.put("content", obj.getContent());
            mapq.put("isOriginal", obj.getIsOriginal());
            mapq.put("recordPerson", obj.getRecordPerson());
            mapq.put("witnesses", obj.getWitnesses());
            mapq.put("theLord", obj.getTheLord());
            mapq.put("maker", obj.getMaker());
            // 照片
            String imagePath = (String) (PropertyUtil.getContextProperty("lawdocImageSavePath")
                    + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator) + "doccasespace-"
                    + result.getSerialNo() + "-yt.jpg";
            File file = new File(imagePath);
            if (!file.exists()) {
                System.err.println("未找到图片");
            } else {
                String str = new ImageUtil().getImageStr(imagePath);
                // System.err.println(result.getSerialNo());
                // System.err.println(str);
                mapq.put("image", str);
            }
            SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
            if (sessioninfo != null) {
                String areaName = "";
                if (sessioninfo.getCurrentArea() != null) {
                    areaName = sessioninfo.getCurrentArea().getName();
                }
                if (areaName != null && !"".equals(areaName)) {
                    mapq.put("workspce", areaName);
                }
            } else {
                mapq.put("workspce", "");
            }

        } else {
            mapq.put("year", " ");
            mapq.put("month", " ");
            mapq.put("day", " ");
            mapq.put("makeSpace", " ");
            mapq.put("saveSpace", " ");
            mapq.put("isOriginal", " ");
            mapq.put("recordPerson", " ");
            mapq.put("witnesses", " ");
            mapq.put("theLord", " ");
            mapq.put("maker", " ");
            mapq.put("content", "");
            mapq.put("image", "");
            // //照片
            // String imagePath =
            // (String)PropertyUtil.getContextProperty("lawdocImageSavePath") +
            // "doccasespace-" + result.getSerialNo() + "-yt.jpg";
            // mapq.put("image", new ImageUtil().getImageStr(imagePath,
            // "jpg"));
        }
        SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
        if (sessioninfo != null) {
            String areaName = "";
            if (sessioninfo.getCurrentArea() != null) {
                areaName = sessioninfo.getCurrentArea().getName();
            }
            if (areaName != null && !"".equals(areaName)) {
                mapq.put("workspce ", areaName);
            }
        } else {
            mapq.put("workspce", "");
        }
        result.setData(mapq);
    }

    // 物证照片底联说明
    private void getTestData7(LawDocProcessEntity result) {
        result.setXmlFileName("24物证照片底联说明.xml");
        result.setDownFileName("24物证照片底联说明.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map mapq = new HashMap();
        SerialEntity interrogateSerialEntity = new SerialEntity();
        interrogateSerialEntity.setSerialNo(result.getSerialNo());
        SerialEntity interrogateSeria = serialService.GetSerialInfoByNo(interrogateSerialEntity);
        DocPhotoEvidenceEntity obj = docPhotoEvidenceService.getDocPhotoEvidence(result.getSerialId());
        if (interrogateSeria != null) {
            mapq.put("caseName", interrogateSeria.getCaseName());
        }
        if (obj != null) {
            mapq.put("mtime", obj.getMakeTime());
            mapq.put("mplace", obj.getMakeAddress());
            mapq.put("evidinfo", obj.getEvidenceExplain());
            mapq.put("evidplace", obj.getStorageAddress());
            if ("0".equals(obj.getChecks())) {
                mapq.put("check", "是");
            } else {
                mapq.put("check", "否");
            }
            mapq.put("cameraman", obj.getShootingPerson());
            mapq.put("witness", obj.getWitnessPerson());
            mapq.put("party", obj.getParty());
            mapq.put("producer", obj.getProducer());
            mapq.put("unit", obj.getUnit());
            // 照片
            String imagePath = (String) (PropertyUtil.getContextProperty("lawdocImageSavePath")
                    + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator) + "docwzzp-"
                    + result.getSerialNo() + "-yt.jpg";
            File file = new File(imagePath);
            if (!file.exists()) {
                System.err.println("未找到图片");
            } else {
                String str = new ImageUtil().getImageStr(imagePath);
                mapq.put("image", str);
            }
        } else {
            mapq.put("mtime", "");
            mapq.put("mplace", "");
            mapq.put("evidinfo", "");
            mapq.put("evidplace", "");
            mapq.put("check", "");
            mapq.put("cameraman", "");
            mapq.put("witness", "");
            mapq.put("party", "");
            mapq.put("producer", "");
            mapq.put("unit", "");
            // //照片
            // String imagePath =
            // (String)PropertyUtil.getContextProperty("lawdocImageSavePath") +
            // "docwzzp-" + result.getSerialNo() + "-yt.jpg";
            // String str = new ImageUtil().getImageStr(imagePath, "jpg");
            // //System.err.println("----aaaaaa-----"+str);
            // mapq.put("image", str);
        }
        result.setData(mapq);
    }

    // 现场、搜查执法记录仪录像底联说明
    private void getTestData8(LawDocProcessEntity result) {
        result.setXmlFileName("24现场、搜查执法记录仪录像底联说明.xml");
        result.setDownFileName("24现场、搜查执法记录仪录像底联说明.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map mapq = new HashMap();
        SerialEntity interrogateSerialEntity = new SerialEntity();
        interrogateSerialEntity.setSerialNo(result.getSerialNo());
        SerialEntity interrogateSeria = serialService.GetSerialInfoByNo(interrogateSerialEntity);
        DocEnforcementVideoEntity obj = docEnforcementVideoService.getDocEnforcementVideo(result.getSerialId());
        if (interrogateSeria != null) {
            mapq.put("caseName", interrogateSeria.getCaseName());
        }
        if (obj != null) {
            mapq.put("evidinfo", obj.getDataContent());
            mapq.put("format", obj.getFormat());
            mapq.put("evidtime", obj.getEvidtime());
            mapq.put("evidplace", obj.getExtractionAddress());
            mapq.put("evidmethod", obj.getExtractionMethod());
            mapq.put("videosource", obj.getVideosource());
            if ("0".equals(obj.getOriginal())) {
                mapq.put("original", "是");
            } else {
                mapq.put("original", "否");
            }
            mapq.put("reason", obj.getReason());
            mapq.put("originalplace", obj.getOriginalplace());
            mapq.put("witness", obj.getWitnessPerson());
            mapq.put("producer", obj.getProducer());
            mapq.put("pname", obj.getPoliceName());
            mapq.put("unit", obj.getUnit());
            // 照片
            String imagePath = (String) (PropertyUtil.getContextProperty("lawdocImageSavePath")
                    + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator) + "docxclx-"
                    + result.getSerialNo() + "-yt.jpg";
            File file = new File(imagePath);
            if (!file.exists()) {
                System.err.println("未找到图片");
            } else {
                String str = new ImageUtil().getImageStr(imagePath);
                mapq.put("image", str);
            }
        } else {
            mapq.put("evidinfo", "");
            mapq.put("format", "");
            mapq.put("evidtime", "");
            mapq.put("evidplace", "");
            mapq.put("evidmethod", "");
            mapq.put("videosource", "");
            mapq.put("original", "");
            mapq.put("reason", "");
            mapq.put("originalplace", "");
            mapq.put("witness", "");
            mapq.put("producer", "");
            mapq.put("pname", "");
            mapq.put("unit", "");
            // //照片
            // String imagePath =
            // (String)PropertyUtil.getContextProperty("lawdocImageSavePath") +
            // "docxclx-" + result.getSerialNo() + "-yt.jpg";
            // String str = new ImageUtil().getImageStr(imagePath, "jpg");
            // // System.err.println(str);
            // mapq.put("image", str);
        }
        result.setData(mapq);
    }

    // 随身物品登记
    private void getTestData9(LawDocProcessEntity result) {
        BelongEntity entity = new BelongEntity();
        List<BelongEntity> security = new ArrayList<BelongEntity>();

        entity.setPersonId(result.getUserId());
        entity.setId(Integer.valueOf(result.getBelongingsId()+""));
        try {
            security = iBelongMapper.getDocInfoNew(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // LawDocProcessEntity result1 = new LawDocProcessEntity();
        result.setXmlFileName("随身物品登记新.xml");
        result.setDownFileName("随身物品登记.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map maps = new HashMap();
        Map map = new HashMap();
        List<Map> list = new ArrayList<Map>();
        System.out.println("size:" + security.size());
        if (security.size() > 0 && security != null) {
            BelongEntity obj = security.get(0);
            maps.put("policeStation", obj.getCasename());
            maps.put("branch", "");
            maps.put("name", obj.getPersonname());
            maps.put("identityCard", obj.getCertificateNo());
            maps.put("caseReason", obj.getInvolvedReason());
            maps.put("source", "随身携带");
            maps.put("caseReason", obj.getInvolvedReason());
            if (security.size() >= 6) {
                int s = 0;
                if (security.size() == 0) {
                    s = 0;
                } else {
                    s = security.size();
                }
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("objectName", objs.getBename());
                    System.err.println("" + objs.getBename());
                    mapq.put("objectCount", objs.getDetailCount());
                    mapq.put("characteristics", objs.getDescription());
                    mapq.put("objectspace", objs.getBoxname());
                    System.err.println("" + objs.getGetWay());
                    String handling = "";
                    if (objs.getGetWay() != null) {
                        if ((objs.getGetWay()).equals("0")) {
                            handling = "";
                        }
                        if ((objs.getGetWay()).equals("1")) {
                            handling = "本人领取";
                        }
                        if ((objs.getGetWay()).equals("2")) {
                            handling = "委托他人代为领取";
                        }
                        if ((objs.getGetWay()).equals("3")) {
                            handling = "本人收到扣押物品清单";
                        }
                        if ((objs.getGetWay()).equals("4")) {
                            handling = "转涉案财物";
                        }
                        if ((objs.getGetWay()).equals("5")) {
                            handling = "移交";
                        }
                    }

                    mapq.put("handling", handling);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getGetTime() != null) {
                        mapq.put("date", sdf.format(objs.getGetTime()));
                    }
                    mapq.put("receiver", objs.getGetPerson());
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("objectName", objs.getBename());
                    System.err.println("" + objs.getBename());
                    mapq.put("objectCount", objs.getDetailCount());
                    mapq.put("characteristics", objs.getDescription());
                    mapq.put("objectspace", objs.getBoxname());
                    System.err.println("" + objs.getGetWay());
                    String handling = "";
                    if (objs.getGetWay() != null) {
                        if ((objs.getGetWay()).equals("0")) {
                            handling = "";
                        }
                        if ((objs.getGetWay()).equals("1")) {
                            handling = "本人领取";
                        }
                        if ((objs.getGetWay()).equals("2")) {
                            handling = "委托他人代为领取";
                        }
                        if ((objs.getGetWay()).equals("3")) {
                            handling = "本人收到扣押物品清单";
                        }
                        if ((objs.getGetWay()).equals("4")) {
                            handling = "转涉案财物";
                        }
                        if ((objs.getGetWay()).equals("5")) {
                            handling = "移交";
                        }
                    }

                    mapq.put("handling", handling);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getGetTime() != null) {
                        mapq.put("date", sdf.format(objs.getGetTime()));
                    }
                    mapq.put("receiver", objs.getGetPerson());
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);

                }
                // blank
                for (int i = 0; i < 6 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("no", security.size() + i + 1);
                    mapq.put("objectName", "");
                    mapq.put("objectCount", "");
                    mapq.put("characteristics", "");
                    mapq.put("objectspace", "");
                    mapq.put("handling", "");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    mapq.put("date", "");
                    mapq.put("receiver", "");
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);
                }
            }

        } else {
            security = iBelongMapper.getDocInfoNoLockersNew(entity);
            logger.info("size2======================" + security.size());
            BelongEntity obj = security.get(0);
            maps.put("policeStation", obj.getCasename());
            maps.put("branch", "");
            maps.put("name", obj.getPersonname());
            maps.put("identityCard", obj.getCertificateNo());
            maps.put("caseReason", obj.getInvolvedReason());
            maps.put("source", "随身携带");
            maps.put("caseReason", obj.getInvolvedReason());

            for (int i = 0; i < security.size(); i++) {
                BelongEntity objs = security.get(i);
                Map mapq = new HashMap();
                mapq.put("no", i + 1);
                mapq.put("objectName", "无");

                mapq.put("objectCount", "无");
                mapq.put("characteristics", "无");
                mapq.put("objectspace", "无");
                mapq.put("handling", "无");
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd
                // HH:mm");
                // if (objs.getGetTime() != null) {
                // mapq.put("date", sdf.format(objs.getGetTime()));
                // }
                mapq.put("date", "无");
                mapq.put("receiver", "无");
                mapq.put("policeman", "");
                mapq.put("legalMember", "");
                list.add(mapq);
            }

            // blank
            for (int i = 0; i < 6 - security.size(); i++) {
                Map mapq = new HashMap();
                mapq.put("no", security.size() + i + 1);
                mapq.put("objectName", "");
                mapq.put("objectCount", "");
                mapq.put("characteristics", "");
                mapq.put("objectspace", "");
                mapq.put("handling", "");

                mapq.put("date", "");
                mapq.put("receiver", "");
                mapq.put("policeman", "");
                mapq.put("legalMember", "");
                list.add(mapq);
            }

        }
        System.err.println("" + list);
        maps.put("list", list);
        result.setData(maps);
    }
    // 民警物品登记
    private void getTestData53(LawDocProcessEntity result) {
        PoliceBelongEntity entity = new PoliceBelongEntity();
        List<PoliceBelongEntity> security = new ArrayList<PoliceBelongEntity>();

       // entity.setSerialId(result.getSerialId());
        entity.setPoliceId(result.getPoliceId());
        //entity.setBelongingsId(result.getBelongingsId());
        try {
            security = iPoliceBelongMapper.getDocInfo(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // LawDocProcessEntity result1 = new LawDocProcessEntity();
        result.setXmlFileName("民警随身物品登记.xml");
        result.setDownFileName("民警随身物品登记.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map maps = new HashMap();
        Map map = new HashMap();
        List<Map> list = new ArrayList<Map>();
        System.out.println("size:" + security.size());
        if (security.size() > 0 && security != null) {
            PoliceBelongEntity obj = security.get(0);
            maps.put("policeStation", obj.getCasename());
            maps.put("branch", "");
            maps.put("name", obj.getPersonname());
            maps.put("identityCard", obj.getCertificateNo());
            maps.put("caseReason", obj.getInvolvedReason());
            maps.put("source", "随身携带");
            maps.put("caseReason", obj.getInvolvedReason());
            if (security.size() >= 8) {
                int s = 0;
                if (security.size() == 0) {
                    s = 0;
                } else {
                    s = security.size();
                }
                for (int i = 0; i < security.size(); i++) {
                    PoliceBelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("objectName", objs.getBename());
                    System.err.println("" + objs.getBename());
                    mapq.put("objectCount", objs.getDetailCount());
                    mapq.put("characteristics", objs.getDescription());
                    mapq.put("objectspace", objs.getBoxname());
                    System.err.println("" + objs.getGetWay());
                    String handling = "";
                    if (objs.getGetWay() != null) {
                        if ((objs.getGetWay()).equals("0")) {
                            handling = "";
                        }
                        if ((objs.getGetWay()).equals("1")) {
                            handling = "本人领取";
                        }
                        if ((objs.getGetWay()).equals("2")) {
                            handling = "委托他人代为领取";
                        }
                        if ((objs.getGetWay()).equals("3")) {
                            handling = "本人收到扣押物品清单";
                        }
                        if ((objs.getGetWay()).equals("4")) {
                            handling = "转涉案财物";
                        }
                        if ((objs.getGetWay()).equals("5")) {
                            handling = "移交";
                        }
                    }

                    mapq.put("handling", handling);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getGetTime() != null) {
                        mapq.put("date", sdf.format(objs.getGetTime()));
                    }
                    mapq.put("receiver", objs.getGetPerson());
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    PoliceBelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("objectName", objs.getBename());
                    System.err.println("" + objs.getBename());
                    mapq.put("objectCount", objs.getDetailCount());
                    mapq.put("characteristics", objs.getDescription());
                    mapq.put("objectspace", objs.getBoxname());
                    System.err.println("" + objs.getGetWay());
                    String handling = "";
                    if (objs.getGetWay() != null) {
                        if ((objs.getGetWay()).equals("0")) {
                            handling = "";
                        }
                        if ((objs.getGetWay()).equals("1")) {
                            handling = "本人领取";
                        }
                        if ((objs.getGetWay()).equals("2")) {
                            handling = "委托他人代为领取";
                        }
                        if ((objs.getGetWay()).equals("3")) {
                            handling = "本人收到扣押物品清单";
                        }
                        if ((objs.getGetWay()).equals("4")) {
                            handling = "转涉案财物";
                        }
                        if ((objs.getGetWay()).equals("5")) {
                            handling = "移交";
                        }
                    }

                    mapq.put("handling", handling);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getGetTime() != null) {
                        mapq.put("date", sdf.format(objs.getGetTime()));
                    }
                    mapq.put("receiver", objs.getGetPerson());
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);

                }
                // blank
                for (int i = 0; i < 8 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("no", security.size() + i + 1);
                    mapq.put("objectName", "");
                    mapq.put("objectCount", "");
                    mapq.put("characteristics", "");
                    mapq.put("objectspace", "");
                    mapq.put("handling", "");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    mapq.put("date", "");
                    mapq.put("receiver", "");
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);
                }
            }

        } else {
            security = iPoliceBelongMapper.getDocInfoNoLockers(entity);
            logger.info("size2======================" + security.size());
            PoliceBelongEntity obj = security.get(0);
            maps.put("policeStation", obj.getCasename());
            maps.put("branch", "");
            maps.put("name", obj.getPersonname());
            maps.put("identityCard", obj.getCertificateNo());
            maps.put("caseReason", obj.getInvolvedReason());
            maps.put("source", "随身携带");
            maps.put("caseReason", obj.getInvolvedReason());

            for (int i = 0; i < security.size(); i++) {
                PoliceBelongEntity objs = security.get(i);
                Map mapq = new HashMap();
                mapq.put("no", i + 1);
                mapq.put("objectName", "无");

                mapq.put("objectCount", "无");
                mapq.put("characteristics", "无");
                mapq.put("objectspace", "无");
                mapq.put("handling", "无");
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd
                // HH:mm");
                // if (objs.getGetTime() != null) {
                // mapq.put("date", sdf.format(objs.getGetTime()));
                // }
                mapq.put("date", "无");
                mapq.put("receiver", "无");
                mapq.put("policeman", "");
                mapq.put("legalMember", "");
                list.add(mapq);
            }

            // blank
            for (int i = 0; i < 8 - security.size(); i++) {
                Map mapq = new HashMap();
                mapq.put("no", security.size() + i + 1);
                mapq.put("objectName", "");
                mapq.put("objectCount", "");
                mapq.put("characteristics", "");
                mapq.put("objectspace", "");
                mapq.put("handling", "");

                mapq.put("date", "");
                mapq.put("receiver", "");
                mapq.put("policeman", "");
                mapq.put("legalMember", "");
                list.add(mapq);
            }

        }
        System.err.println("" + list);
        maps.put("list", list);
        result.setData(maps);
    }
    // 涉案物品登记
    private void getTestData52(LawDocProcessEntity result) {
        ExhibitEntity entity = new ExhibitEntity();
        List<ExhibitEntity> security = new ArrayList<ExhibitEntity>();
        entity.setSerialId(result.getSerialId());
        try {
            security = iExhibitMapper.getDocInfo(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setXmlFileName("涉案物品临时保管台账.xml");
        result.setDownFileName("涉案物品临时保管台账.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map maps = new HashMap();
        Map map = new HashMap();
        List<Map> list = new ArrayList<Map>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (security != null && security.size() > 0) {
            for(int i =0;i<security.size();i++){
                ExhibitEntity obj = security.get(i);
                Map mapq = new HashMap();
                mapq.put("no", i + 1);
                mapq.put("name", obj.getName());
                mapq.put("description",obj.getDescription());
                mapq.put("count", obj.getDetailCount());
                mapq.put("pName", obj.getPname());
                mapq.put("unit",obj.getUnit());
                list.add(mapq);
            }
        } else {
                Map mapq = new HashMap();
                mapq.put("no", "");
                mapq.put("name", "");
                mapq.put("description", "");
                mapq.put("count", "");
                mapq.put("pName", "");
                mapq.put("unit", "");
                list.add(mapq);
        }
//        maps.put("list", list);
//        maps.put("caseNo", security.get(0).getCaseNo());
//        maps.put("caseName", security.get(0).getCaseName());
//        maps.put("plName", security.get(0).getPlName());
//        maps.put("organization1", security.get(0).getOrganization1());
//        maps.put("organization2", security.get(0).getOrganization2());
//        maps.put("sendUserId", security.get(0).getSendUserId());
//        maps.put("getPerson", security.get(0).getGetPerson());
//        maps.put("areaName", security.get(0).getAreaName());
        for (int i = 0; i < 8 - security.size(); i++) {
        	maps.put("list", list);
           maps.put("caseNo", security.get(0).getCaseNo());
           maps.put("caseName", security.get(0).getCaseName());
           maps.put("plName", security.get(0).getPlName());
           maps.put("organization1", security.get(0).getOrganization1());
           maps.put("organization2", security.get(0).getOrganization2());
           maps.put("sendUserId", security.get(0).getSendUserId());
           maps.put("getPerson", security.get(0).getGetPerson());
           maps.put("areaName", security.get(0).getAreaName());
        }
        result.setData(maps);
    }
    // 物品管理台账
    private void getTestData10(LawDocProcessEntity result) {
        BelongEntity entity = new BelongEntity();
        List<BelongEntity> security = new ArrayList<BelongEntity>();
        entity.setSerialId(result.getSerialId());
        SerialEntity interrogateSerialEntity = new SerialEntity();
        interrogateSerialEntity.setId(result.getSerialId());
        interrogateSerialEntity.setSerialNo(result.getSerialNo());
        SerialEntity interrogateSerial = serialService.outGetSerialByNo(interrogateSerialEntity);
        try {
            security = iBelongMapper.getexDocInfo(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setXmlFileName("涉案物品临时保管台账.xml");
        result.setDownFileName("涉案物品临时保管台账.doc");
        result.setFileType(2);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map maps = new HashMap();
        Map map = new HashMap();
        List<Map> list = new ArrayList<Map>();
        System.out.println("size:" + security.size());
        if (security.size() > 0 && security != null) {
            BelongEntity obj = security.get(0);
            maps.put("interrogateAreaName", obj.getAreaName());
            if (security.size() >= 8) {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("caseName", obj.getCasename());
                    mapq.put("mobile", objs.getBename());
                    mapq.put("count", objs.getDetailCount());
                    mapq.put("phoneStyle", objs.getDetailCount());
                    mapq.put("casePro", "扣押");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getCreatedTime() != null) {
                        mapq.put("inTime", sdf.format(objs.getCreatedTime()));
                    }
                    mapq.put("sender", "");
                    mapq.put("reciever", "");
                    mapq.put("borrowTime", "");
                    mapq.put("borrower", "");
                    mapq.put("resendTime", "");
                    mapq.put("finalResult", "");
                    mapq.put("agent", "");
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("caseName", obj.getCasename());
                    mapq.put("mobile", objs.getBename());
                    mapq.put("count", objs.getDetailCount());
                    mapq.put("phoneStyle", objs.getDescription());
                    mapq.put("casePro", "扣押");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getCreatedTime() != null) {
                        mapq.put("inTime", sdf.format(objs.getCreatedTime()));
                    }
                    mapq.put("sender", "");
                    mapq.put("reciever", "");
                    mapq.put("borrowTime", "");
                    mapq.put("borrower", "");
                    mapq.put("resendTime", "");
                    mapq.put("finalResult", "");
                    mapq.put("agent", "");
                    list.add(mapq);
                }
                for (int i = 0; i < 8 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("caseName", "");
                    mapq.put("mobile", "");
                    mapq.put("count", "");
                    mapq.put("phoneStyle", "");
                    mapq.put("casePro", "");
                    mapq.put("inTime", "");
                    mapq.put("sender", "");
                    mapq.put("reciever", "");
                    mapq.put("borrowTime", "");
                    mapq.put("borrower", "");
                    mapq.put("resendTime", "");
                    mapq.put("finalResult", "");
                    mapq.put("agent", "");
                    list.add(mapq);
                }
            }

        }else{
        	for (int i = 0; i < 8; i++) {
				Map mapq = new HashMap();
				 if(i==0){
						mapq.put("caseName", interrogateSerial.getInvolvedReason());
						mapq.put("mobile", "无");
						mapq.put("count", "无");
						mapq.put("phoneStyle", "无");
						mapq.put("casePro", "无");
						mapq.put("inTime", "无");
						mapq.put("sender", "无");
						mapq.put("reciever", "无");
						mapq.put("borrowTime", "无");
						mapq.put("borrower", "无");
						mapq.put("resendTime", "无");
						mapq.put("finalResult", "无");
						mapq.put("agent", "无");
					}else{
						mapq.put("caseName", "");
						mapq.put("mobile", "");
						mapq.put("count", "");
						mapq.put("phoneStyle", "");
						mapq.put("casePro", "");
						mapq.put("inTime", "");
						mapq.put("sender", "");
						mapq.put("reciever", "");
						mapq.put("borrowTime", "");
						mapq.put("borrower", "");
						mapq.put("resendTime", "");
						mapq.put("finalResult", "");
						mapq.put("agent", "");
					}
				list.add(mapq);
			}
        }
        maps.put("list", list);
        result.setData(maps);
    }

    // 违法犯罪嫌疑人人身安全检查情况登记表
    private void getTestData11(LawDocProcessEntity result, HttpSession session){
        SerialEntity serial = new SerialEntity();
        serial.setSerialNo(result.getSerialNo());
        SecurityEntity security = new SecurityEntity();
        try {
            serial = serialService.inGetSerialByNo(serial);
            security.setSerialId(serial.getId());
            logger.info("@@@@@@@@@@@@@"+security);
            security = securityService.getOneSecurity(security);
            logger.info("@@@@@@@@@@@@@+++++"+security);
        } catch (Exception e) {
            logger.debug("入区1Exception: " + e);
        }

        // 在生成html
        String templatePath = BaseConfig.getInstance().getTemplateDir();
        LawDocForm docData = new LawDocForm();
        Map<String, Object> map = new HashMap<String, Object>();
        if (security != null) {
            map.put("areaName", serial.getInterrogateAreaName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            if (security.getPersonName() != null && !"".equals(security.getPersonName())) {
                map.put("personName", security.getPersonName());
            } else {
                map.put("personName", "");
            }

            if (security.getCheckedStartTime() != null && !"".equals(security.getCheckedStartTime())) {
                map.put("checkedStartTime", sdf.format(security.getCheckedStartTime()));
            } else {
                map.put("checkedStartTime", "");
            }

            if (security.getCheckedEndTime() != null && !"".equals(security.getCheckedEndTime())) {
                map.put("checkedEndTime", sdf.format(security.getCheckedEndTime()));
            } else {
                map.put("checkedEndTime", "");
            }

            if (security.getPersonName() != null && !"".equals(security.getPersonName())) {
                map.put("personName", security.getPersonName());
            } else {
                map.put("personName", "");
            }
            String sex = "";
            if (security.getSex() != null) {
                if (security.getSex() == 0) {
                    sex = "未知的性别";
                }
                if (security.getSex() == 1) {
                    sex = "男";
                }
                if (security.getSex() == 2) {
                    sex = "女";
                }
                if (security.getSex() == 9) {
                    sex = "未说明的性别";
                }
            } else {
                sex = "";
            }
            map.put("sex", sex);
            if (security.getBirth() != null) {
                String year1 = sdf.format(security.getBirth()).substring(0, 4);
                int year2 = new Date().getYear() + 1900;
                map.put("birth", year2 - Integer.parseInt(year1));
            }
            String caseType = "";
            if (security.getAjlx() != null && !"".equals(security.getAjlx())) {
                if (security.getAjlx() == 0) {
                    caseType = "刑事";
                } else {
                    caseType = "行政";
                }
            } else {
                caseType = "";
            }
            map.put("caseType", caseType);

            if (security.getAgainReason() != null && !"".equals(security.getAgainReason())) {
                map.put("involvedReason", security.getAgainReason());
            } else {
                map.put("involvedReason", "");
            }
            if (security.getMedicalHistory() != null && !"".equals(security.getMedicalHistory())) {
                map.put("medicalHistory", security.getMedicalHistory());
            } else {
                map.put("medicalHistory", "");
            }

            String type = "";
            if (security.getCheckType() != null) {
                if (security.getCheckType() == 0) {
                    type = "  ☑一般检查; ☐全面检查";
                } else {
                    type = "  ☐一般检查;  ☑全面检查";
                }
            } else {
                type = "  ☐一般检查;  ☐全面检查";
            }
            map.put("type", type);
            String physical = "";
            if (security.getPhysical() != null) {
                if (security.getPhysical() == 0) {
                    physical = "  ☑无; ☐有";
                } else {
                    physical = "  ☐无;  ☑有";
                }
            } else {
                physical = "  ☐无;  ☐有";
            }
            map.put("physical", physical);

            if (security.getPhysicalDescription() == null || security.getPhysicalDescription() == "") {
                security.setPhysicalDescription("无");
            }

            if (security.getPhysicalDescription() != null && !"".equals(security.getPhysicalDescription())) {
                map.put("physicalDescription", security.getPhysicalDescription());
            } else {
                map.put("physicalDescription", "");
            }
            if (security.getDangerous() != null && !"".equals(security.getDangerous())) {
                map.put("dangerous", security.getDangerous());
            } else {
                map.put("dangerous", "无");
            }

            String injury = "";
            if (security.getInjury() != null) {
                if (security.getInjury() == 0) {
                    injury = "  ☑无; ☐有";
                } else {
                    injury = "  ☐无;  ☑有";
                }
            } else {
                injury = "  ☐无;  ☐有";
            }
            map.put("injury", injury);
            if (security.getInjuryDescription() == null || security.getInjuryDescription() == "") {
                security.setInjuryDescription("无");
            }
            map.put("injuryDescription", security.getInjuryDescription());
            if (security.getOtherDescription() == null || security.getOtherDescription() == "") {
                security.setOtherDescription("无");
            }
            if (security.getOtherDescription() != null && !"".equals(security.getOtherDescription())) {
                map.put("otherDescription", security.getOtherDescription());
            } else {
                map.put("otherDescription", "");
            }
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("uuid",result.getSerialUUID());
            //param.put("type",result.getSerialUUID());
            param.put("count",result.getCount());
            List<SecurityPhotoEntity> list =  securityService.getImages(param);
            List<Map<String,String>> photo = new ArrayList<Map<String,String>>();
            if(list.size()>0){
                for(int i = 0;i<list.size();i++){
                    int j = i+1;
                    //String imagePath1 = list.get(i).getUrl();
                   // map.put("personPhoto"+j,new ImageUtil().getImageStr(imagePath1));
                   /* if((i+1)%2==0){
                        Map<String,String> params = new HashMap<String,String>();
                        String imagePath1 = list.get(i).getUrl();
                        String imagePath2 = list.get(i-1).getUrl();
                        params.put("personPhoto1",new ImageUtil().getImageStr(imagePath1));
                        params.put("personPhoto2",new ImageUtil().getImageStr(imagePath2));
                        photo.add(params);

                        map.put("personPhoto1",new ImageUtil().getImageStr(imagePath1));
                        map.put("personPhoto2",new ImageUtil().getImageStr(imagePath2));
                    }else{
                        Map<String,String> params = new HashMap<String,String>();
                        String imagePath1 = list.get(i).getUrl();
                        params.put("personPhoto1",new ImageUtil().getImageStr(imagePath1));
                        photo.add(params);
                    }*/
                }
                map.put("list",photo);
            }else{
                Map<String,String> params = new HashMap<String,String>();
                params.put("personPhoto1","无照片");
                photo.add(params);
            }
        }
        result.setData(map);
    }
    // 通用体貌特征表
    private void getTestData12(LawDocProcessEntity result, HttpServletRequest request) {
        result.setXmlFileName("通用体貌特征表1.xml");
        result.setDownFileName("通用体貌特征表1.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        SerialEntity interrogateSerialEntity = new SerialEntity();
        interrogateSerialEntity.setSerialNo(result.getSerialNo());
        SerialEntity obj = null;
        SerialEntity objDtail = null;
        Map mapq = new HashMap();
        try {
            obj = serialService.GetSerialInfoByNo(interrogateSerialEntity);
            objDtail = serialService.GetSerialDetailInfoById(obj.getId());
            String imagePath = (String) (PropertyUtil.getContextProperty("faceImageFileSavePath")
                    + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator) + "rq-" + result.getSerialNo()
                    + "-yt.jpg";
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("serialId", result.getSerialId());
            try {
                List<SerialVideoMappingEntity> serialVideoMappingEntities = serialVideoMappingService.list(map1);
                if (serialVideoMappingEntities.size() > 0) {

                    imagePath = serialVideoMappingEntities.get(0).getWebPath() + File.separator + "faceImageUpload" + File.separator
                            + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator + "rq-"
                            + result.getSerialNo() + "-yt.jpg";

                }
                mapq.put("image", new ImageUtil().getImageStr(imagePath));
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // 填表单位
        SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
        if (sessioninfo != null) {
            String areaName = "";
            if (sessioninfo.getCurrentArea() != null) {
                areaName = sessioninfo.getCurrentArea().getName();
            }

            if (areaName != null && !"".equals(areaName)) {
                mapq.put("policeStationName", areaName);
            }
        } else {
            mapq.put("policeStationName", "");
        }
        if (objDtail != null) {
            // 姓 名
            if (objDtail.getPersonName() != null && !"".equals(objDtail.getPersonName())) {
                mapq.put("name", objDtail.getPersonName());
            } else {
                mapq.put("name", "");
            }
            // 性 别
            if (0 == (objDtail.getSex())) {
                mapq.put("sex", "未知的性别");
            }
            if (1 == (objDtail.getSex())) {
                mapq.put("sex", "男");
            }
            if (2 == (objDtail.getSex())) {
                mapq.put("sex", "女");
            }
            if (9 == (objDtail.getSex())) {
                mapq.put("sex", "未说明的性别");
            }

            // 户口所在地
            mapq.put("address", objDtail.getAddress());
            // 出生日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (objDtail.getBirth() != null) {
                String birthTime = sdf.format(objDtail.getBirth());
                mapq.put("birthDate", birthTime);
            }
            // 身份证号码
            mapq.put("IDName", objDtail.getCertificateNo());
            // 民 族
            mapq.put("nation", objDtail.getNation());
            // 身 高
            mapq.put("height", objDtail.getHeight());
            // 体 重
            mapq.put("weight", objDtail.getWeight());
            // 体 态
            mapq.put("posture", objDtail.getBodysize());
            // 脸 型
            mapq.put("feature", objDtail.getFaceShape());
            // 发 型
            mapq.put("hairStyle", objDtail.getHairType());
            // 鞋 号
            mapq.put("shoeSize", objDtail.getFootsize());
            // 肤 色
            mapq.put("complexion", objDtail.getSkinColor());
            // 眼
            mapq.put("eye", objDtail.getEye());
            // 眉
            mapq.put("eyebrow", objDtail.getEyeBrow());
            // 鼻
            mapq.put("nose", objDtail.getNose());
            // 牙
            mapq.put("tooth", objDtail.getTooth());
            // 耳
            mapq.put("ear", objDtail.getEar());
            // 生理标记(痣、记、疤、纹身)
            mapq.put("physiologyFeature", objDtail.getPhysiologicalMarkers());
            // 其他明显特征
            if (objDtail.getOtherFeature() != null && !"".equals(objDtail.getOtherFeature())) {
                mapq.put("otherFeature", objDtail.getOtherFeature());
            } else {
                mapq.put("otherFeature", "");
            }

            // 备注
            mapq.put("remark", objDtail.getRemark());
            // 填表人
            mapq.put("preparer", objDtail.getPreparer());
            // 年
            if (objDtail.getFormTime() != null && !"".equals(objDtail.getFormTime())
                    && !"null".equals(objDtail.getFormTime().toLowerCase())) {
                System.err.println("---------------" + objDtail.getFormTime());
                String[] formTimes = objDtail.getFormTime().split("-");
                mapq.put("year", formTimes[0]);
                // 月
                mapq.put("month", formTimes[1]);
                // 日
                mapq.put("day", formTimes[2]);
            }
            // 照片
            String imagePath = (String) (PropertyUtil.getContextProperty("faceImageFileSavePath")
                    + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator) + "rq-" + result.getSerialNo()
                    + "-yt.jpg";
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("serialId", result.getSerialId());
            try {
                List<SerialVideoMappingEntity> serialVideoMappingEntities = serialVideoMappingService.list(map1);
                if (serialVideoMappingEntities.size() > 0) {

                    imagePath = serialVideoMappingEntities.get(0).getWebPath() + File.separator + "faceImageUpload" + File.separator
                            + Utils.getDateFromSerialNO(result.getSerialNo()) + File.separator + "rq-"
                            + result.getSerialNo() + "-yt.jpg";

                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
            if (imagePath != null && !"".equals(imagePath)) {
                File file = new File(imagePath);
                if (!file.exists()) {
                    System.err.println("本地无此图片");
                } else {
                    mapq.put("image", new ImageUtil().getImageStr(imagePath));
                }

            }
        }

        result.setData(mapq);
    }

    // 吸毒人员登记表
    private void getTestData13(LawDocProcessEntity result, HttpServletRequest request) {
        result.setXmlFileName("吸毒人员登记表(开发版).xml");
        result.setDownFileName("吸毒人员登记表.xls");
        result.setFileType(2);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".xls";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map mapq = new HashMap();
        // --------------------------------------------------------------------------------------------

        SerialEntity interrogateSerialEntity = new SerialEntity();
        interrogateSerialEntity.setSerialNo(result.getSerialNo());
        SerialEntity obj = null;
        SerialEntity objDtail = null;
        try {
            obj = serialService.GetSerialInfoByNo(interrogateSerialEntity);
            objDtail = serialService.GetSerialDetailInfoById(obj.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        // ----------------------------------------------------------------------------------------------
        if (objDtail != null) {

            // 姓名
            mapq.put("name", objDtail.getPersonName());
            // 别名
            mapq.put("asName", objDtail.getNickName());
            // 性别
            if ("0".equals(objDtail.getSex())) {
                mapq.put("sex", "未知的性别");
            }
            if ("1".equals(objDtail.getSex())) {
                mapq.put("sex", "男");
            }
            if ("2".equals(objDtail.getSex())) {
                mapq.put("sex", "女");
            }
            if ("9".equals(objDtail.getSex())) {
                mapq.put("sex", "未说明的性别");
            }
            // 民族
            mapq.put("nation", objDtail.getNation());
            // 生日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (objDtail.getBirth() != null) {
                String birthday = sdf.format(objDtail.getBirth());
                mapq.put("birthDate", birthday);
            }
            mapq.put("birthDate", "");
            String nowTime = sdf.format(new Date());
            mapq.put("nowTime", nowTime);
            // 身高
            mapq.put("height", objDtail.getHeight());
            // 身份证
            mapq.put("IDNumber", objDtail.getCertificateNo());
            // 现状
            mapq.put("currState", "");
            // 服务场所
            mapq.put("serviceAddr", objDtail.getJob());
            // 户籍
            mapq.put("currPoliceStationName", "");
            // 户籍地址
            mapq.put("address", objDtail.getAddress());
            // 现居住地
            mapq.put("currAddress", objDtail.getCensusRegister());
            // 居住地派出所
            mapq.put("policeStationName", "");
            // 手机
            mapq.put("phone", objDtail.getMobile());
            // 固定电话
            mapq.put("tel", objDtail.getTel());
            // qq
            mapq.put("qq", objDtail.getQq());
            // 病情
            mapq.put("condition", "");
            // 滥用毒品
            mapq.put("type", "");
            // 查获日期
            mapq.put("catchTime", nowTime);
            // 查获地区
            // 填表单位
            SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
            if (sessioninfo != null) {
                String areaName = "";
                if (sessioninfo.getCurrentArea() != null) {
                    areaName = sessioninfo.getCurrentArea().getName();
                }

                if (areaName != null && !"".equals(areaName)) {
                    mapq.put("catchArea", areaName);
                    // 单位
                    mapq.put("formUnit", areaName);
                }
            } else {
                mapq.put("catchArea", "");
                // 单位
                mapq.put("areaName", "");
            }

            // 查获地派出所
            mapq.put("catchTime", "");
            // 处置情况
            mapq.put("handle", "");
            // 处置日期
            mapq.put("handTime", nowTime);
            // 查获地点
            mapq.put("catchSpace", nowTime);

            // 填表人
            mapq.put("former", nowTime);

            // 联系电话
            mapq.put("formTel", objDtail.getTel());
            mapq.put("caseContent", "");
        } else {

            // 姓名
            mapq.put("name", "");
            // 别名
            mapq.put("asName", "");
            // 性别
            mapq.put("sex", "");
            // 民族
            mapq.put("nation", "");
            // 生日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            mapq.put("birthDate", "");
            String nowTime = sdf.format(new Date());
            mapq.put("nowTime", nowTime);
            // 身高
            mapq.put("height", "");
            // 身份证
            mapq.put("IDNumber", "");
            // 现状
            mapq.put("currState", "");
            // 服务场所
            mapq.put("serviceAddr", "");
            // 户籍
            mapq.put("currPoliceStationName", "");
            // 户籍地址
            mapq.put("address", "");
            // 现居住地
            mapq.put("currAddress", "");
            // 居住地派出所
            mapq.put("policeStationName", "");
            // 手机
            mapq.put("phone", "");
            // 固定电话
            mapq.put("tel", "");
            // qq
            mapq.put("qq", "");
            // 病情
            mapq.put("condition", "");
            // 滥用毒品
            mapq.put("type", "");
            // 查获日期
            mapq.put("catchTime", nowTime);
            // 查获地区
            // 填表单位
            SessionInfo sessioninfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
            if (sessioninfo != null) {
                String areaName = "";
                if (sessioninfo.getCurrentArea() != null) {
                    areaName = sessioninfo.getCurrentArea().getName();
                }

                if (areaName != null && !"".equals(areaName)) {
                    mapq.put("catchArea", areaName);
                    // 单位
                    mapq.put("formUnit", areaName);
                }
            } else {
                mapq.put("catchArea", "");
                // 单位
                mapq.put("areaName", "");
            }

            // 查获地派出所
            mapq.put("catchTime", "");
            // 处置情况
            mapq.put("handle", "");
            // 处置日期
            mapq.put("handTime", nowTime);
            // 查获地点
            mapq.put("catchSpace", "");

            // 填表人
            mapq.put("former", "");

            // 联系电话
            mapq.put("formTel", "");

            mapq.put("caseContent", "");

        }

        result.setData(mapq);
    }

    // 违法犯罪嫌疑人进入办案场所凭证
    private void getTestData14(LawDocProcessEntity result) {
        SerialEntity entity = new SerialEntity();
        SerialEntity serial = new SerialEntity();
        entity.setSerialNo(result.getSerialNo());
        try {
            serial = serialService.inGetSerialByNo(entity);
        } catch (Exception e) {
            logger.debug("入区1Exception: " + e);
        }

        // 在生成html
        String templatePath = BaseConfig.getInstance().getTemplateDir();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("areaName1", serial.getInterrogateAreaName());
        map.put("serailNO", serial.getSerialNo());
        map.put("sendUser",serial.getSendUserNo());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        map.put("date", sdf.format(serial.getInTime()));
        // System.out.println("sdf.format(serial.getInTime())=" +
        // sdf.format(serial.getInTime()));
        map.put("personName", serial.getName());
        map.put("certificateType", serial.getCertificateTypeName());
        map.put("certificateNO", serial.getCertificateNo());
        map.put("areaName2", serial.getInterrogateAreaName());
        map.put("areaName3", serial.getInterrogateAreaName());
        map.put("mainusername",serial.getSendUserRealName() +" "+ serial.getMobile());
        map.put("workspce",serial.getOrganization());
        if(serial.getWrittenTime() != null){
            map.put("calltime",sdf.format(serial.getWrittenTime()));
        }else {
            map.put("calltime","                             ");
        }
        // 取入区照片和条码
        if (!"".equals(serial.getInPhotoId()) || serial.getInPhotoId() != null) {
            // System.err.println("serial.getInPhotoId()="+serial.getInPhotoId());
            String imageBase64 = "";
            String barcodeBase64 = "";
            try {
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("serialId", serial.getId());
                String barCodeName = "rq-XYR" +  serial.getSerialNo() + FileUploadForm.BARCODEXYRRQ;
                try {
                    imageBase64 = fileConfigService.getFileBase64(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serial.getUuid(), serial.getAreaId(), serial.getInPhotoId()));
                } catch( Exception e){
                    System.err.println("无此照片");
                    imageBase64 = "/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCACSANoDASIAAhEBAxEB/8QAHAABAAEFAQEAAAAAAAAAAAAAAAECAwQFBgcI/8QAOhAAAQMCAwUFBQcFAAMAAAAAAQACAwQRBQYhEjFBUZEHE2FxgRUWIlOhFDJCVHKxwSMzUmLwJGOS/8QAGwEAAgMBAQEAAAAAAAAAAAAAAAQBAgMFBgf/xAAtEQACAQMDBAEBCAMAAAAAAAAAAQIDBBEFITESFEFRYRMGIiMycYGRoRXR8P/aAAwDAQACEQMRAD8A9hdG5vj5KhZXVUuYHcPVMZOc4+jHTqrhiI3aqjdvUlcYI6p1UqLqSMjqnVTdLoAjqnVTdLoAjqnVTdLoAjqnVTdEAR1Tqpul0AR1Tqpul0AR1Tqpul0AQsaQWeVlKxONQVMeSs+C0l0RXMyoO4FTsg6hUICQgMlWyQVVdQ12uqnRQSZ7XbQuCqvVY4NjcK614d5rLAypZK1BaDv1U3S6gsWnRcirZaW77rI9UU5K9KMb1T1V4xg7jZUOY5o8FOSri0U+qeqJdSVCtTzw0sLpqiaOGJu+SRwa0epXK5zz3S5WjFNCxtTicjdpsJPwxg7nP/gbz5LxTGMdxPH6nv8AEquSd1/haTZjP0t3BUlPBtTouW74PbqvtIyrRvLTiffuH5eF0g62sseLtUytI6zp6uIf5PpnW+l14Siz+ozft4H0xheP4RjTb4biVPUneWMf8Y82nX6LYr5YY98UjZI3uZI03a9psQfAhej5S7Uamkkjo8wPdUUx0FXa8kf6v8h47x4q8Z55M50Gt4nsHqp9VRHKyWJksT2vje0Oa9puHA7iCq7+K0Fh6p6pfxS/igCPVUTC8fkrl/FQ4bQIQiHwYiIi0MQiIgAqdof8VLjstJWOpwVlLBtURFkMl1knAn1VfqsdVNeRodyq0XUi9fxS/igNxcFFBcX8UU3S6AKS0OXPZvx6PK+X5q87L5ie7p43fjkO70G8+AXREheI9r+LOq8yU+GNd/SooQ4jh3j9T9AFDbSJhBSlg4Cqqp62qlqqmV0s8ri+SR29xPFV0NDVYnWxUVDTyVFTKbMjjFy7/uax1692H0dOHY1ikjbywtZE021a0gudbzsOixHTgMeyVmDLUEdRilAY4HnZErHiRodyJG4+a0tNTT1tVFS0sL5p5XBsccYu5xPABe3U2b5c1Yj7v4kzD5qDGsOkngFOSX0xAcQyS5sXWbfhYrjOzKop8FhzBmSeATzYbStbCy9rve7Z38LkAX8SgDQ43kXMeXqFtbiWGujpiQDIyRsgYTu2rblzq+lKGuxStxWbK2aYaGR1dQGojfStcGlt9l8ZDidWkix4r5vni7iolhvfu5HMvzsbfwgD0bsuzc+mq2ZfrZL08xP2Rzj/AG379jydw5HzXsHqvleOR8UrJYnFsjHBzHDgQbg9V9MYFiQxnAqHERYGoha9wHB2531BWtOWdhOvDD6kZ9/FL+KXS61FxfxS6XS6AMZ4s8+apV2YfEDzVpaIxfIREJsLoILUrtbK2pJubqFYxbyzaoiLEdCIiAJa4tKuteHeasoowWTwX9rRQSSqA6+9VWPgjBbOQvm/O85qM8Y1I7hVuYPJtmj9l9Iedl8y5nnZU5qxaoiv3ctVI9lxYkE6LKo1shihF7yxsapdz2Y5xp8rY3NFiDi3D61oZI+1+7cPuuI5akHzXN4DgNRmGufS080MWwzbe+QnQXtoBqV3OH9nGEs1q6qorHNNnBru7aD5DX6rmXWpW9s+mb39I6FK2qVVmK2Nni0uR8lR1+L4BVMqsXrInx0sUcwkZBt73C33R5m/ALjOzzMlFl7HJI8UjbLhdbGIqjbZthpBux5HEA7/ADvwXfwZOy5C0bOD0p8ZAXk9SrkmVsvubb2NQ+kIC5j+0dDOFF/0MLTp+0bvNef8uYThjq+iq6OtxR0Lo6UQkPeNrmR91t7E87L50JLiS43cdSeZXrlTkHL1RcR0b6dx408jm/Q3C5XGOz59HSVFZQYlHNDA0vfHONkgDfZw0v52TlvrNrWfTlp/JlUsqsFnk4xe7dlkrpci07Sb91UTMF+W1f8AleENdtNDt1xde29k1ZFLlN9I0OEsE73uuPvB50I8NCF2ISSkvk59aEpU20uDvk0UJZMCBOiKLJZAFuYXZfkVYWU4XaR4LFV4mc+QrchsLequcFjuO04lWRlJ7EIiKTI2qIixHgiIgAiIgAqmu11VKIJNbmfERhmXauYODZHs7qLXUudpp6XPovD67Do6xgsdiVos138FbHtLzM/FMyfYqaU/ZMOcWNLTo6X8TvTcPI81qMNxT7XJHTyNP2h7gxmyL7ZO4Ac1yL6NXrVSn4PX6FUtfoyt7hby9/8AbGoLKzCqtkrHSQTMN2SsNuhXW4bnuOYsZjTJIpBoK6k+Fw/U3iOvkrUjLF0cjNQbOa4bvMFa6fBqGa57ssP/AK3W+iQqVaNwsV47+0Pz0SrSfVazyvTO7kxPGY6ZtVhtRTYtRu1EjI7uHmGn/uSxocw5irZBDBhse2dLmJwA8yTZcTTYXLQSGShxKqpnnjG61+iyJ3YzVRmOozBXyRne3atfoVz3ZUc7NP8AVFla3aWHT3/VHUYvmSlwhhZimJOrqy2tFRkMYD/s4cPM+i4HGs04nj5ED7Q0oPwUkAIb4X4u9VkMwKkYbvdLJ5mw+izYaaCnFoYms8QNeqcoxtrfeK6pe+P4Kf4i7rv8WSjH4NRR4TJIRJV/CwaiMbz5r0Ts9rW0mZWU7iGx1UTogNw2h8Tf2I9Vykzu4pTUva/uQ7Y2w022juF919CtJNiU7qmOaF7ojE4PjLTq1w1B803QdatVVR7JFr6NlZWk7eG8pL9/3PqAs5Km1t4WoyhmBmZst02IHZE/9uoaPwyDf6HQjzW8Ou8LvJ5PBOOHgtJ6KssvuVBaRwUlWiFjPGy8hZNvBWZhZwPMKY8lJrYx5HWbbmrKqkdtO03KlaisnlhERBU2qIixHgiIgAiIgAtdj2JeyMv4hiI+9T073t/VazfqQtiuW7R9o5AxXY37Md/LvG3UPgtFZkkfP5c5zi57i55N3E8TxK6HIUbZc/YE1277W13QE/wufYx8srY42OfI9wa1rRckncAtpleubhmbcJrH6MhrIy+/AbVj+6WOkeodqDm+8UEbGta5tMC4gbyXHfz3Lg2zP71sT2C7nBtx4my7TtKcffGUcBBGB5WK5BrGyTw33943X1C4Fdp13n2fQdPTjYwcX4Oxm7Mswxn+mKSUeE2z+4Vgdm2ZnG32anHiZwvbARp5KbrpdhRPMrX7xbbfweQ0vZTi8pH2qtpIG/6B0h/gLp8M7MMFoi19YZq544SHZZ/8j+Su3ul1pC0ox4QvW1e8qrDnhfGx5z2u0VNT9nRjhgjiZFVQljGNADdSNAPAr5/XvXbdXNgylSUd/jqatpt/qwEn6kLwcRvMRl2Hd2HBhfbQOIuBfnYFMpY4OY3l5Z6N2O4o6DHa3C3OPdVUPesF9z2b+rSei9nXz32bPdHn3DnAE6S7VuWwV72ao8GAeZW9NNoSryUZGSnosM1Eh4geQVBe873HqtOkwdVGa7ZtrYeqxalzQzRwJVpWZTd1uAVlEynPYoREVxYIiIA2qLnveGo+RF9VHvDU/Ji+qt2tQv3tH2dEi5z3gqflR/VR7fqPlM6lHa1A72l7OkSy5v29UfLZ1Kj27P8AJZ1KntahHfUjpbrCxjD48WwWtw6RwDamF0d+RI0PobFaf25N8mPqU9uTfJj6lHaVCVfUlucrkDs/qsLxMYtjbI2TQE/ZoA8Os7dtm30Hjdcf2g5cOBZjmfCwihrC6WEjc0n7zPQnoQvWvbk3yY+pWDjELMy4XJQ1lLH3btWSAkOjdwc3x/dK3NONvT66rSXyxu3vXWq9ME236RnZY9j9oWUqOoxGBstdTNFPO5p2ZGvA33HBwsfVZI7L8DZOyVs1bZrg7YMgINjffa68mwyrxvsxzE2odH31FLZkoabR1DPA/hcN4v8AsV71gGY8MzLh7azDKkSN/HGdHxnk5vA/Rc6MaFZKosP5O9C6uaC6IyaXo2o0FlKhLpgVJuipuF5Z2g9pkVPFJguXZu/r5bxy1EXxCK+hay33n+W7zQ3jkDiu1bMTcwZu+zUru8paAGnj2dduQn4yPWzfRdhg2RoG5Edg9e3ZqKr+vK8amKX8Nv0jTrzXPZPyhNhk8eK4jTxvqW/FDTyH+2f8nc3chw8125xyoa4gwRgjgSVeynSuW1Skm14yI6hWlb4U00veDlsiZKxHAsxVVZiMbA2GIxQPY8ESFx1cOQsOPNejLRe3Zvkx9Snt2b5MfUroxtJxWEjlzv6c3lv+jeotF7dm+TH1Ke3ZvkR9SrdtU9Fe8pezeONmkrHWhqMyTtcGCCInedSrPvJUfl4epVlaVfRjO+o5xk6RFzfvJUfl4epT3kqPy8PUqezq+infUfZ0iLm/eWo/Lw9Sp946r8rF1cjtKnoO9ovh/wBFKdUROiY6p1REAOqdURADqnVEQBbqJRBA+UgkNsSPC+q20T2Pia+JwcwjQjctaQCDfdxutNL3lLIXYbUuhHFp1afILz2vaLV1KMXSlhx8eDuaNq1GwclVWz8+TrJoYaqB0M8TJYnizmPaCD5hctPkaCmrBW4FiNVhNUNxicS0fW9vC5CgYzjTBYtppPG1lRJi2OSiw7iPxC8xbfZ7WbeX4ey/XY9FV1/S6izKWf2ZtPeXPWCQ/wDkVWD4jG3c6Zro3npZW4O0vNVe/uqfD8Ehf/nJM8joufloaureXVNXtE+BKo9j2ILaggjcdlert9Ju/otVZrr8YWy/2cKtr1sqq+nH7vn2zf1+HZrzG0x43mQRUrvvU1BHsNI5E6X9brOwbLWFYEL0VMO+tYzSfE8+vD0sudgnxijAbDUsezk5ZbcbxoCxhpz43XnL3Q9aqvpbTj8PB2bfXdKiurOH8o6xarEqmMVkFO0gyuuXAfhbbS/qtQK/Fak7M9VHA0/LasqlpIqYFzSXSO+9I43JTeifZu6tbiNxXljHhCOra/bXNCVGis58mSiIvcHkQoc4NaXHcNVKxqx9mBg3nf5ISyysnhZMRzi95cd5KhETAmFLWuedloJPIK9DSvlsT8LOfNbCOJkTbMFuZ4lZymkawpOXJjwUbWWdJ8TuXALKRLhYtt8jMYqKwiGG7AfBSrcJ+EjkriHySuAiIoJCIiACguDQS4gAcVTJK2Ju04+XitdNO6Z2ujRuCvGOTOdRRK6ipdLdrTZn7rHRFuklwKNuTywiIpICIiACIiACuwzuiNjq3lyVpFDWSU2t0bNj2vbtNNwqlrI5HRuu0+nNZ0UzZRpo7iFlKOBiFRS5Lu5a2V/eSF3Dgsuqk2IiOLtFYgpXy6u+FnPiVMNlllamZPpRaYx0jtlgJKzYaNrLOks53LgFkRxtibstAAVSrKo3wXhSS3Y9URQ97Y2FzjYBZmzeA9zWNLnGwHFYhrxf+2eqx553TO5NG4K0tow9is6zb+6bODeVeRFk+RiHAREUFgiIgDXVhJntf8IWOiJmHAjU/MwiIrFQiIgAiIgAiIgAiIgAqmG0jSOaIofAR5MyQA1cIIvoVl8URLy8DkOWSoO9EVTQLBxAnbYOFkRXp/mM6v5TERETAmf/2W==";
                }
                if(imageBase64 != null && !imageBase64.equals("")){
                    map.put("image", imageBase64);
                } else{
                    result.setXmlFileName("违法犯罪嫌疑人进入办案场所凭证.xml");
                    System.err.println("无此照片");
                }
                try {
                    barcodeBase64 = fileConfigService.getFileBase64(FileUploadForm.of("ba", FileUploadForm.FILETYPRRQ, serial.getUuid(), serial.getAreaId(),barCodeName));
                } catch( Exception e){
                    System.err.println("无此照片");
                }
                if(barcodeBase64 != null && !barcodeBase64.equals("")){
                    map.put("barcode", barcodeBase64);
                } else{
                    System.err.println("无此照片");
                }
                System.err.println(imageBase64);
                System.err.println(barcodeBase64);
            } catch (Exception e) {
                logger.info("找不到照片");
            }
        }
        result.setData(map);
    }

    private void getOtherCheckBodyData(LawDocProcessEntity result) {
        SerialEntity interrogateSerialEntity = new SerialEntity();
        interrogateSerialEntity.setSerialNo(result.getSerialNo());
        SerialEntity interrogateSeria = serialService.GetSerialInfoByNo(interrogateSerialEntity);
        // -----------------------s
        CheckBodyEntity checkbodydata = new CheckBodyEntity();
        CheckBodyEntity checkbody = new CheckBodyEntity();
        checkbodydata.setInterrogateSerialId(result.getSerialId());
        checkbodydata.setSerialNo(result.getSerialNo());
        List<CheckBodyEntity> checkbodyList = iCheckBodyMapper.getCheckBodyBySerialId(checkbodydata);
        if (checkbodyList != null && checkbodyList.size() > 0) {
            checkbody = checkbodyList.get(0);
        }
        // 被检查人id
        long personId = interrogateSeria.getPersonId();
        PersonEntity obj = iPersonMapper.queryPersonInfoById(personId);
        // ToDo 根据id 查出数据填充map
        // LawDocProcessEntity result = new LawDocProcessEntity();
        result.setXmlFileName("入所健康检查表.xml");
        result.setDownFileName("入所健康检查表.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map mapq = new HashMap();
        List<Map> list = new ArrayList<Map>();
        if (obj != null && checkbody != null) {
            // 主表
            String checktime = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (checkbody.getCheckTime() != null) {
                checktime = sdf.format(checkbody.getCheckTime());
                String[] checktimes = checktime.split("-");
                mapq.put("year", checktimes[0]);
                mapq.put("month", checktimes[1]);
                mapq.put("day", checktimes[2]);
            }
            mapq.put("name", obj.getName());
            if (obj.getSex() != null) {
                if (0 == obj.getSex()) {
                    mapq.put("sex", "未知的性别");
                }
                if (1 == obj.getSex()) {
                    mapq.put("sex", "男");
                }
                if (2 == obj.getSex()) {
                    mapq.put("sex", "女");
                }
                if (9 == obj.getSex()) {
                    mapq.put("sex", "未说明的性别");
                }
            } else {
                mapq.put("sex", "");
            }

            if (obj.getBirth() != null) {
                String birthTime = sdf.format(obj.getBirth());
                mapq.put("birth", birthTime);
            } else {
                mapq.put("birth", "");
            }
            mapq.put("weight", checkbody.getWeight());
            mapq.put("height", checkbody.getHeight());
            mapq.put("footsize", checkbody.getFootsize());
            mapq.put("china", obj.getNation());
            if ("0".equals(obj.getEducation())) {
                mapq.put("kownleage", "文盲");
            }
            if ("1".equals(obj.getEducation())) {
                mapq.put("kownleage", "小学");
            }
            if ("2".equals(obj.getEducation())) {
                mapq.put("kownleage", "初中");
            }
            mapq.put("marital", checkbody.getMaritalstatus());
            mapq.put("health", checkbody.getHealth());
            mapq.put("identityCard", obj.getCertificateNo());
            if (obj.getJob() != null && obj.getJobTitle() != null) {
                mapq.put("workSpace", obj.getJob() + obj.getJobTitle());
            } else if (obj.getJob() != null && obj.getJobTitle() == null) {
                mapq.put("workSpace", obj.getJob() + "");
            } else if (obj.getJob() == null && obj.getJobTitle() != null) {
                mapq.put("workSpace", "" + obj.getJobTitle());
            }
            mapq.put("workSpace", "");

            mapq.put("bodyTag", checkbody.getBodyTag());
            mapq.put("historyHealthy", checkbody.getHistoryhealthy());
            mapq.put("drugHistory", checkbody.getDrughistory());
            mapq.put("contagion", checkbody.getContagion());
            mapq.put("selfReported", checkbody.getSelfReported());
            mapq.put("checkStatus", checkbody.getCheckstatus());
            mapq.put("compete", checkbody.getLanguagecompetence());
            mapq.put("mouthSound", checkbody.getMouthsound());
            mapq.put("Activity", checkbody.getPhysicalactivity());
            mapq.put("doctor", checkbody.getDoctorsignature());
            mapq.put("leader", checkbody.getLeadersignature());
            mapq.put("checked", checkbody.getCheckedSignature());
            mapq.put("sender", checkbody.getSendersignature());
            mapq.put("sendUnit", checkbody.getSendunit());
            mapq.put("remark", checkbody.getRemark());
            // 附表
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (checkbody.getChecktimeStart() != null) {
                String checktimeS = sdf1.format(checkbody.getChecktimeStart());
                String timestart[] = checktimeS.split(" ");
                String[] start = timestart[0].split("-");
                mapq.put("ys", start[0]);
                mapq.put("ms", start[1]);
                mapq.put("ds", start[2]);
                String timestarts[] = timestart[1].split(":");
                mapq.put("hs", timestarts[0]);
                mapq.put("mis", timestarts[1]);
            } else {
                mapq.put("ys", "");
                mapq.put("ms", "");
                mapq.put("ds", "");
                mapq.put("hs", "");
                mapq.put("mis", "");
            }
            if (checkbody.getChecktimeEnd() != null) {
                String checktimeE = sdf1.format(checkbody.getChecktimeEnd());
                String timeend[] = checktimeE.split(" ");
                String timeends[] = timeend[1].split(":");
                String end[] = timeend[0].split("-");
                mapq.put("ye", end[0]);
                mapq.put("me", end[1]);
                mapq.put("de", end[2]);
                mapq.put("he", timeends[0]);
                mapq.put("mie", timeends[1]);
            } else {
                mapq.put("ye", "");
                mapq.put("me", "");
                mapq.put("de", "");
                mapq.put("he", "");
                mapq.put("mie", "");
            }
            mapq.put("checkSpace", checkbody.getCheckspace());
            mapq.put("n1", checkbody.getBodytemperature());
            mapq.put("n2", checkbody.getPulse());
            mapq.put("n3", checkbody.getBloodpressure());
            mapq.put("n4", checkbody.getBreathing());
            if ("0".equals(checkbody.getBodysize())) {
                mapq.put("n5", "（肥胖√ 中等 偏瘦）");
            }
            if ("1".equals(checkbody.getBodysize())) {
                mapq.put("n5", "（肥胖 中等√ 偏瘦）");
            }
            if ("2".equals(checkbody.getBodysize())) {
                mapq.put("n5", "（肥胖 中等 偏瘦√）");
            } else {
                mapq.put("n5", "（肥胖 中等 偏瘦）");
            }
            mapq.put("n6", checkbody.getFaceshape());
            mapq.put("n7", checkbody.getBloodtype());
            if ("0".equals(checkbody.getNutritionalstatus())) {
                mapq.put("n8", "（良好√ 中等 不良）");
            }
            if ("1".equals(checkbody.getNutritionalstatus())) {
                mapq.put("n8", "（良好 中等√ 不良）");
            }
            if ("2".equals(checkbody.getNutritionalstatus())) {
                mapq.put("n8", "（良好 中等 不良√）");
            } else {
                mapq.put("n8", "（良好 中等 不良）");
            }
            if ("0".equals(checkbody.getConsciousness())) {
                mapq.put("n9", "（清晰√/障碍）");
            }
            if ("1".equals(checkbody.getConsciousness())) {
                mapq.put("n9", "（清晰/障碍√）");
            } else {
                mapq.put("n9", "（清晰/障碍）");
            }
            if ("0".equals(checkbody.getWords())) {
                mapq.put("n10", "（正常√/障碍）");
            }
            if ("1".equals(checkbody.getWords())) {
                mapq.put("n10", "（正常/障碍√）");
            } else {
                mapq.put("n10", "（正常/障碍）");
            }
            if ("0".equals(checkbody.getGait())) {
                mapq.put("n11", "（正常√/不正常） ");
            }
            if ("1".equals(checkbody.getGait())) {
                mapq.put("n11", "（正常/不正常√） ");
            } else {
                mapq.put("n11", "（正常/不正常） ");
            }
            if ("0".equals(checkbody.getDoublepupil())) {
                mapq.put("n12", "（等大√/不等大）");
            }
            if ("1".equals(checkbody.getDoublepupil())) {
                mapq.put("n12", "（等大/不等大√）");
            } else {
                mapq.put("n12", "（等大/不等大）");
            }
            if ("0".equals(checkbody.getSkinsclerayellowdye())) {
                mapq.put("n13", "（无√/有）");
            }
            if ("1".equals(checkbody.getSkinsclerayellowdye())) {
                mapq.put("n13", "（无/有√）");
            } else {
                mapq.put("n13", "（无/有）");
            }
            if ("0".equals(checkbody.getDoublelung())) {
                mapq.put("n14", "呼吸音（清晰√/异常）");
            }
            if ("1".equals(checkbody.getDoublelung())) {
                mapq.put("n14", "呼吸音（清晰/异常√）");
            } else {
                mapq.put("n14", "呼吸音（清晰/异常）");
            }
            mapq.put("n15", checkbody.getHeartrate());
            if ("0".equals(checkbody.getHeartratestatus())) {
                mapq.put("n16", "（齐√/不齐）");
            }
            if ("1".equals(checkbody.getHeartratestatus())) {
                mapq.put("n16", "（齐/不齐√）");
            } else {
                mapq.put("n16", "（齐/不齐）");
            }
            if ("0".equals(checkbody.getNoise())) {
                mapq.put("n17", "（未闻及√/可闻及）");
            }
            if ("1".equals(checkbody.getNoise())) {
                mapq.put("n17", "（未闻及/可闻及√）");
            } else {
                mapq.put("n17", "（未闻及/可闻及）");
            }
            if ("0".equals(checkbody.getAbdomen())) {
                mapq.put("n18", "压痛(无√/有) ");
            }
            if ("1".equals(checkbody.getAbdomen())) {
                mapq.put("n18", "压痛(无/有√) ");
            } else {
                mapq.put("n18", "压痛(无/有) ");
            }

            if ("0".equals(checkbody.getReboundtenderness())) {
                mapq.put("n19", "(无√/有) ");
            }
            if ("1".equals(checkbody.getAbdomen())) {
                mapq.put("n19", "(无/有√) ");
            } else {
                mapq.put("n19", "(无/有) ");
            }
            if ("0".equals(checkbody.getMuscletension())) {
                mapq.put("n20", "(无√/有)");
            }
            if ("1".equals(checkbody.getMuscletension())) {
                mapq.put("n20", "(无/有√)");
            } else {
                mapq.put("n20", "(无/有)");
            }
            if ("0".equals(checkbody.getLiverandspleen())) {
                mapq.put("n21", "（未扪及√/可扪及）");
            }
            if ("1".equals(checkbody.getLiverandspleen())) {
                mapq.put("n21", "（未扪及/可扪及√）");
            } else {
                mapq.put("n21", "（未扪及/可扪及）");
            }
            if ("0".equals(checkbody.getSpinallimbs())) {
                mapq.put("n22", "（无异常√/异常）");
            }
            if ("1".equals(checkbody.getSpinallimbs())) {
                mapq.put("n22", "（无异常/异常√）");
            } else {
                mapq.put("n22", "（无异常/异常）");
            }
            mapq.put("surfaceInspection", checkbody.getSurfaceinspection());
            mapq.put("abnormalSigns", checkbody.getAbnormalsigns());
            mapq.put("routineBlood", checkbody.getRoutineblood());
            mapq.put("bUltrasonic", checkbody.getBultrasonic());
            mapq.put("electrocardiogram", checkbody.getElectrocardiogram());
            mapq.put("rabat", checkbody.getRabat());
            mapq.put("doctorAdvice", checkbody.getDoctoradvice());
            mapq.put("urinePregnancy", checkbody.getUrinepregnancyresult());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            // String womenLastPeriodTime=sdf2.format(
            // checkbody.getWomenlastperiodtime());
            mapq.put("womenTime", checkbody.getWomenlastperiodtime());
            if (checkbody.getCheckbodyPicturePath() != null && checkbody.getCheckbodyPicturePath() != "") {
                File file = new File(checkbody.getCheckbodyPicturePath());
                if (!file.exists()) {
                    System.err.println("本地未找到照片");
                } else {
                    String imageStr = new ImageUtil().getImageStr(checkbody.getCheckbodyPicturePath());
                    mapq.put("image", imageStr);
                }
            }
            result.setData(mapq);
        }
        // ------------------------e
    }

    // 入所通知
    private LawDocProcessEntity getTestData16(LawDocProcessEntity result) {
        // TODO Auto-generated method stub
        result.setXmlFileName("入所通知.xml");
        result.setDownFileName("入所通知.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        String serialNo = result.getSerialNo();
        // String policeStation="";//派出所名称，欲从session中获取
        SerialEntity entity = new SerialEntity();
        entity.setSerialNo(serialNo);
        Map<String, Object> maps = new HashMap<String, Object>();
        // 查询部分
        System.err.println("-----id--------" + result.getSerialId());
        SerialEntity interrogateSerialEntity = serialService
                .GetSerialDetailInfoById(result.getSerialId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String year1 = "";
        if (interrogateSerialEntity != null) {
            maps.put("address", interrogateSerialEntity.getAddress());
            if (interrogateSerialEntity.getBirth() != null) {
                String birthTime = sdf.format(interrogateSerialEntity.getBirth());
                if (birthTime != null && !"".equals(birthTime)) {
                    year1 = birthTime.substring(0, 4);
                    @SuppressWarnings("deprecation")
                    int year2 = new Date().getYear() + 1900;
                    maps.put("age", year2 - Integer.parseInt(year1));
                }
            }

            if (interrogateSerialEntity.getSex() == 0) {
                maps.put("sex", "未知的性别");
            }
            if (interrogateSerialEntity.getSex() == 1) {
                maps.put("sex", "男");
            }
            if (interrogateSerialEntity.getSex() == 2) {
                maps.put("sex", "女");
            }
            if (interrogateSerialEntity.getSex() == 9) {
                maps.put("sex", "未说明的性别");
            }
            System.err.println("----" + interrogateSerialEntity);
            System.err.println("----" + interrogateSerialEntity.getType());
            if (interrogateSerialEntity.getCaseType() != null) {

                if (0 == interrogateSerialEntity.getCaseType()) {
                    maps.put("natureCaseType", "刑事");
                }
                if (1 == interrogateSerialEntity.getCaseType()) {
                    maps.put("natureCaseType", "行政");
                }
            }

        }
        maps.put("personName", interrogateSerialEntity.getPersonName());
        maps.put("serialNumber", result.getSerialNo());// 编号(待定中)
        // 通知部分
        DocInNotificationEntity obj = new DocInNotificationEntity();
        if (result.getSerialId() != null) {
            List<DocInNotificationEntity> list = new ArrayList<DocInNotificationEntity>();
            list = iInterrogateSerialLawDocService.queryInNotificationById(result.getSerialId());
            if (list.size() > 0 && list != null) {
                obj = list.get(0);
                String birthTime = obj.getBirth();
                System.err.println("-------------------" + birthTime);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date birth = null;
                try {
                    birth = sdf1.parse(birthTime);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                int age = LawDocController.getAge(birth);
                System.err.println("===年龄====" + age);
                if (age > 0) {
                    String agestr = Integer.toString(age);
                    obj.setAge(agestr);
                }
            }

            if (obj != null) {
                if (obj.getInTime() != null) {
                    maps.put("date", sdf.format(obj.getInTime()));
                } else {
                    maps.put("date", sdf.format(new Date()));
                }

                maps.put("sendUnit", obj.getSendUnit());
                maps.put("handlingUnit", obj.getHandlingUnit());
                maps.put("connectionSituation", obj.getConnectionSituation());
                maps.put("approver", obj.getApprover());
                maps.put("agent", obj.getAgent());
                maps.put("policeStation", obj.getPoliceStation());
            }
        }
        result.setData(maps);
        return result;
    }

    /**
     * 返回证件类型
     *
     * @param cardtype
     * @return
     */
    private String getcardtype(int cardtype) {
        String cardname = "";
        if (cardtype == 0) {
            cardname = "警官证";
        } else if (cardtype == 1) {
            cardname = "居民身份证";
        } else if (cardtype == 2) {
            cardname = "普通护照";
        } else if (cardtype == 3) {
            cardname = "港澳通行证";
        }
        return cardname;
    }

    /**
     * 随身物品登记
     *
     * @param form
     * @return
     */
    public LawDocProcessEntity getProcessData2(BelongDocForm form) {
        BelongEntity entity = new BelongEntity();
        List<BelongEntity> security = new ArrayList<BelongEntity>();
        entity.setSerialId(form.getDataId());
        try {
            security = iBelongMapper.getDocInfo(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }
        LawDocProcessEntity result = new LawDocProcessEntity();
        result.setXmlFileName("随身物品登记新.xml");
        result.setDownFileName("随身物品登记.doc");
        result.setFileType(1);
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        result.setSerialNo(form.getSerialNo());
        Map maps = new HashMap();
        Map map = new HashMap();
        List<Map> list = new ArrayList<Map>();
        System.out.println("size:" + security.size());
        if (security != null && security.size() > 0) {
            BelongEntity obj = security.get(0);
            maps.put("policeStation", obj.getCasename());
            maps.put("branch", "");
            maps.put("name", obj.getPersonname());
            maps.put("identityCard", obj.getCertificateNo());
            maps.put("caseReason", obj.getInvolvedReason());
            maps.put("source", "随身携带");
            maps.put("caseReason", obj.getInvolvedReason());
            if (security.size() >= 8) {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("objectName", objs.getBename());
                    System.err.println("" + objs.getBename());
                    mapq.put("objectCount", objs.getDetailCount());
                    mapq.put("characteristics", objs.getDescription());
                    mapq.put("objectspace", objs.getBoxname());
                    System.err.println("" + objs.getGetWay());
                    String handling = "";
                    if (objs.getGetWay() != null) {
                        if ((objs.getGetWay()).equals("0")) {
                            handling = "";
                        }
                        if ((objs.getGetWay()).equals("1")) {
                            handling = "本人领取";
                        }
                        if ((objs.getGetWay()).equals("2")) {
                            handling = "委托他人代为领取";
                        }
                        if ((objs.getGetWay()).equals("3")) {
                            handling = "本人收到扣押物品清单";
                        }
                        if ((objs.getGetWay()).equals("4")) {
                            handling = "转涉案财物";
                        }
                        if ((objs.getGetWay()).equals("5")) {
                            handling = "移交";
                        }
                    }
                    mapq.put("handling", handling);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getGetTime() != null) {
                        mapq.put("date", sdf.format(objs.getGetTime()));
                    }
                    mapq.put("receiver", objs.getGetPerson());
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("objectName", objs.getBename());
                    mapq.put("objectCount", objs.getDetailCount());
                    mapq.put("characteristics", objs.getDescription());
                    mapq.put("objectspace", objs.getBoxname());
                    String handling = "";
                    if (objs.getGetWay() != null) {
                        if ((objs.getGetWay()).equals("0")) {
                            handling = "";
                        }
                        if ((objs.getGetWay()).equals("1")) {
                            handling = "本人领取";
                        }
                        if ((objs.getGetWay()).equals("2")) {
                            handling = "委托他人代为领取";
                        }
                        if ((objs.getGetWay()).equals("3")) {
                            handling = "本人收到扣押物品清单";
                        }
                        if ((objs.getGetWay()).equals("4")) {
                            handling = "转涉案财物";
                        }
                        if ((objs.getGetWay()).equals("5")) {
                            handling = "移交";
                        }
                    }
                    mapq.put("handling", handling);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getGetTime() != null) {
                        mapq.put("date", sdf.format(objs.getGetTime()));
                    }
                    mapq.put("receiver", objs.getGetPerson());
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);

                }
                for (int i = 0; i < 8 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("no", security.size() + i + 1);
                    mapq.put("objectName", "");
                    mapq.put("objectCount", "");
                    mapq.put("characteristics", "");
                    mapq.put("objectspace", "");
                    mapq.put("handling", "");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    mapq.put("date", "");
                    mapq.put("receiver", "");
                    mapq.put("policeman", "");
                    mapq.put("legalMember", "");
                    list.add(mapq);
                }
            }
        } else {
            security = iBelongMapper.getDocInfoNoLockers(entity);
            BelongEntity obj = security.get(0);
            maps.put("policeStation", obj.getCasename());
            maps.put("branch", "");
            maps.put("name", obj.getPersonname());
            maps.put("identityCard", obj.getCertificateNo());
            maps.put("caseReason", obj.getInvolvedReason());
            maps.put("source", "随身携带");
            maps.put("caseReason", obj.getInvolvedReason());
            for (int i = 0; i < security.size(); i++) {
                BelongEntity objs = security.get(i);
                Map mapq = new HashMap();
                mapq.put("no", i + 1);
                mapq.put("objectName", "无");
                mapq.put("objectCount", "无");
                mapq.put("characteristics", "无");
                mapq.put("objectspace", "无");
                mapq.put("handling", "无");
                mapq.put("date", "无");
                mapq.put("receiver", "无");
                mapq.put("policeman", "");
                mapq.put("legalMember", "");
                list.add(mapq);
            }
            for (int i = 0; i < 8 - security.size(); i++) {
                Map mapq = new HashMap();
                mapq.put("no", security.size() + i + 1);
                mapq.put("objectName", "");
                mapq.put("objectCount", "");
                mapq.put("characteristics", "");
                mapq.put("objectspace", "");
                mapq.put("handling", "");

                mapq.put("date", "");
                mapq.put("receiver", "");
                mapq.put("policeman", "");
                mapq.put("legalMember", "");
                list.add(mapq);
            }
        }
        maps.put("list", list);
        result.setData(maps);
        return result;
    }

    /**
     * 涉案物品登记
     *
     * @param form
     * @return
     */
    public LawDocProcessEntity getProcessData3(BelongDocForm form) {
        BelongEntity entity = new BelongEntity();
        List<BelongEntity> security = new ArrayList<BelongEntity>();
        entity.setSerialId(form.getDataId());
        try {
            security = iBelongMapper.getexDocInfo(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LawDocProcessEntity result = new LawDocProcessEntity();
        result.setXmlFileName("涉案物品管理台账.xml");
        result.setDownFileName("涉案物品管理台账.xls");
        result.setFileType(2);
        String filename1 = Utils.getUniqueId();
        String filename2 = ".xls";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        result.setSerialNo(form.getSerialNo());
        Map maps = new HashMap();
        List<Map> list = new ArrayList<Map>();
        System.out.println("size:" + security.size());
        if (security.size() > 0 && security != null) {
            BelongEntity obj = security.get(0);
            maps.put("interrogateAreaName", obj.getAreaName());
            if (security.size() >= 8) {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("caseName", obj.getCasename());
                    mapq.put("mobile", objs.getBename());
                    mapq.put("count", objs.getDetailCount());
                    mapq.put("phoneStyle", objs.getDetailCount());
                    mapq.put("casePro", "扣押");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getCreatedTime() != null) {
                        mapq.put("inTime", sdf.format(objs.getCreatedTime()));
                    }
                    mapq.put("sender", "");
                    mapq.put("reciever", "");
                    mapq.put("borrowTime", "");
                    mapq.put("borrower", "");
                    mapq.put("resendTime", "");
                    mapq.put("finalResult", "");
                    mapq.put("agent", "");
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("caseName", obj.getCasename());
                    mapq.put("mobile", objs.getBename());
                    mapq.put("count", objs.getDetailCount());
                    mapq.put("phoneStyle", objs.getDetailCount());
                    mapq.put("casePro", "扣押");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if (objs.getCreatedTime() != null) {
                        mapq.put("inTime", sdf.format(objs.getCreatedTime()));
                    }
                    mapq.put("sender", "");
                    mapq.put("reciever", "");
                    mapq.put("borrowTime", "");
                    mapq.put("borrower", "");
                    mapq.put("resendTime", "");
                    mapq.put("finalResult", "");
                    mapq.put("agent", "");
                    list.add(mapq);
                }
                for (int i = 0; i < 8 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("caseName", "");
                    mapq.put("mobile", "");
                    mapq.put("count", "");
                    mapq.put("phoneStyle", "");
                    mapq.put("casePro", "");
                    mapq.put("inTime", "");
                    mapq.put("sender", "");
                    mapq.put("reciever", "");
                    mapq.put("borrowTime", "");
                    mapq.put("borrower", "");
                    mapq.put("resendTime", "");
                    mapq.put("finalResult", "");
                    mapq.put("agent", "");
                    list.add(mapq);
                }
            }
        }
        maps.put("list", list);
        result.setData(maps);
        return result;
    }

    /**
     * 涉案人员随身物品代为保管物品登记表
     *
     * @param form
     * @param result
     * @return
     */
    @Override
    public LawDocProcessEntity getProcessData4(BelongDocForm form, LawDocProcessEntity result) {
        List<ExhibitEntity> security = new ArrayList<ExhibitEntity>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("areaId", form.getDataId());
        paramMap.put("startTime", form.getStartTime());
        paramMap.put("endTime", form.getEndTime());
        try {
            security = iExhibitMapper.getExhibitForExport(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setXmlFileName("涉案人员涉案物品代为保管物品登记表.xml");
        result.setDownFileName("涉案人员涉案物品代为保管物品登记表.doc");
        result.setFileType(1);
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        result.setSerialNo(form.getSerialNo());
        Map maps = new HashMap();
        List<Map> list = new ArrayList<Map>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (security != null && security.size() > 0) {
            ExhibitEntity obj = security.get(0);
            maps.put("policeStation", obj.getPoliceName());
            if (security.size() >= 8) {
                for (int i = 0; i < security.size(); i++) {
                    ExhibitEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("burcode", objs.getBurcode());
                    mapq.put("name", objs.getName());
                    mapq.put("description", objs.getDescription());
                    mapq.put("detailCount", objs.getDetailCount());
                    mapq.put("personName", objs.getPersonName());
                    mapq.put("involvedReason", objs.getInvolvedReason());
                    mapq.put("policeName", objs.getPoliceName());
                    mapq.put("areaName", objs.getAreaName());
                    if (objs.getGetTime() != null) {
                        mapq.put("registerTime", sdf.format(objs.getRegisterTime()));
                    } else {
                        mapq.put("registerTime", "");
                    }
                    mapq.put("getPerson", objs.getGetPerson());
                    if (objs.getGetTime() != null) {
                        mapq.put("getTime", sdf.format(objs.getGetTime()));
                    } else {
                        mapq.put("getTime", "");
                    }
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    ExhibitEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("burcode", objs.getBurcode());
                    mapq.put("name", objs.getName());
                    mapq.put("description", objs.getDescription());
                    mapq.put("detailCount", objs.getDetailCount());
                    mapq.put("personName", objs.getPersonName());
                    mapq.put("involvedReason", objs.getInvolvedReason());
                    mapq.put("policeName", objs.getPoliceName());
                    mapq.put("areaName", objs.getAreaName());
                    if (objs.getGetTime() != null) {
                        mapq.put("registerTime", sdf.format(objs.getRegisterTime()));
                    } else {
                        mapq.put("registerTime", "");
                    }
                    mapq.put("getPerson", objs.getGetPerson());
                    if (objs.getGetTime() != null) {
                        mapq.put("getTime", sdf.format(objs.getGetTime()));
                    } else {
                        mapq.put("getTime", "");
                    }
                    list.add(mapq);

                }
                for (int i = 0; i < 8 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("no", security.size() + i + 1);
                    mapq.put("burcode", "");
                    mapq.put("name", "");
                    mapq.put("description", "");
                    mapq.put("detailCount", "");
                    mapq.put("personName", "");
                    mapq.put("involvedReason", "");
                    mapq.put("policeName", "");
                    mapq.put("registerTime", "");
                    mapq.put("getPerson", "");
                    mapq.put("getTime", "");
                    mapq.put("areaName", "");
                    list.add(mapq);
                }
            }
        } else {
            for (int i = 0; i < 8 - security.size(); i++) {
                Map mapq = new HashMap();
                mapq.put("no", security.size() + i + 1);
                mapq.put("burcode", "");
                mapq.put("name", "");
                mapq.put("description", "");
                mapq.put("detailCount", "");
                mapq.put("personName", "");
                mapq.put("involvedReason", "");
                mapq.put("policeName", "");
                mapq.put("registerTime", "");
                mapq.put("getPerson", "");
                mapq.put("getTime", "");
                mapq.put("areaName", "");
                list.add(mapq);
            }

        }
        maps.put("list", list);
        maps.put("areaName", list.get(0).get("areaName"));
        result.setData(maps);
        return result;
    }
    @Override
    public LawDocProcessEntity getProcessData5(BelongDocForm form, LawDocProcessEntity result) {
        List<BelongEntity> security = new ArrayList<BelongEntity>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("areaId", form.getDataId());
        paramMap.put("startTime", form.getStartTime());
        paramMap.put("endTime", form.getEndTime());
        try {
            security = iBelongMapper.getBelongForExport(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setXmlFileName("涉案人员扣留物品代为保管物品登记表.xml");
        result.setDownFileName("涉案人员扣留物品代为保管物品登记表.doc");
        result.setFileType(1);
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        result.setSerialNo(form.getSerialNo());
        Map maps = new HashMap();
        Map map = new HashMap();
        List<Map> list = new ArrayList<Map>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (security != null && security.size() > 0) {
            if (security.size() >= 8) {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("burcode", objs.getBurcode());
                    mapq.put("name", objs.getName());
                    mapq.put("description", objs.getDescription());
                    mapq.put("detailCount", objs.getDetailCount());
                    mapq.put("personName", objs.getPersonname());
                    mapq.put("involvedReason", objs.getInvolvedReason());
                    mapq.put("policeName", objs.getPoliceName());
                    if (objs.getGetTime() != null) {
                        mapq.put("registerTime", sdf.format(objs.getRegisterTime()));
                    } else {
                        mapq.put("registerTime", "");
                    }
                    mapq.put("getPerson", objs.getGetPerson());
                    if (objs.getGetTime() != null) {
                        mapq.put("getTime", sdf.format(objs.getGetTime()));
                    } else {
                        mapq.put("getTime", "");
                    }
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("burcode", objs.getBurcode());
                    mapq.put("name", objs.getName());
                    mapq.put("description", objs.getDescription());
                    mapq.put("detailCount", objs.getDetailCount());
                    mapq.put("personName", objs.getPersonname());
                    mapq.put("involvedReason", objs.getInvolvedReason());
                    mapq.put("policeName", objs.getPoliceName());
                    if (objs.getGetTime() != null) {
                        mapq.put("registerTime", sdf.format(objs.getRegisterTime()));
                    } else {
                        mapq.put("registerTime", "");
                    }
                    mapq.put("getPerson", objs.getGetPerson());
                    if (objs.getGetTime() != null) {
                        mapq.put("getTime", sdf.format(objs.getGetTime()));
                    } else {
                        mapq.put("getTime", "");
                    }
                    list.add(mapq);
                }
                // blank
                for (int i = 0; i < 8 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("no", security.size() + i + 1);
                    mapq.put("burcode", "");
                    mapq.put("name", "");
                    mapq.put("description", "");
                    mapq.put("detailCount", "");
                    mapq.put("personName", "");
                    mapq.put("involvedReason", "");
                    mapq.put("policeName", "");
                    mapq.put("registerTime", "");
                    mapq.put("getPerson", "");
                    mapq.put("getTime", "");
                    list.add(mapq);
                }
            }
        } else {
            for (int i = 0; i < 8 - security.size(); i++) {
                Map mapq = new HashMap();
                mapq.put("no", security.size() + i + 1);
                mapq.put("burcode", "");
                mapq.put("name", "");
                mapq.put("description", "");
                mapq.put("detailCount", "");
                mapq.put("personName", "");
                mapq.put("involvedReason", "");
                mapq.put("policeName", "");
                mapq.put("registerTime", "");
                mapq.put("getPerson", "");
                mapq.put("getTime", "");
                list.add(mapq);
            }
        }
        System.err.println("" + list);
        maps.put("list", list);
        result.setData(maps);
        return result;
    }
    /**
     * 民警随身物品代为保管物品登记表
     *
     * @param form
     * @param result
     * @return
     */
    @Override
    public LawDocProcessEntity getProcessData57(BelongDocForm form, LawDocProcessEntity result) {
        List<PoliceBelongEntity> security = new ArrayList<PoliceBelongEntity>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("areaId", form.getDataId());
        paramMap.put("startTime", form.getStartTime());
        paramMap.put("endTime", form.getEndTime());
        try {
            security = iPoliceBelongMapper.getPoliceBelongForExport(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setXmlFileName("民警随身物品代为保管物品登记表.xml");
        result.setDownFileName("民警随身物品代为保管物品登记表.doc");
        result.setFileType(1);
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        result.setSerialNo(form.getSerialNo());
        Map maps = new HashMap();
        List<Map> list = new ArrayList<Map>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (security != null && security.size() > 0) {
            PoliceBelongEntity obj = security.get(0);
            maps.put("policeStation", obj.getPoliceName());
            if (security.size() >= 8) {
                for (int i = 0; i < security.size(); i++) {
                    PoliceBelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("burcode", objs.getBurcode());
                    mapq.put("name", objs.getName());
                    mapq.put("description", objs.getDescription());
                    mapq.put("detailCount", objs.getDetailCount());
                    mapq.put("personname", objs.getPersonname());
                    mapq.put("involvedReason", objs.getInvolvedReason());
                    mapq.put("policeName", objs.getPoliceName());
                    mapq.put("areaName", objs.getAreaName());
                    if (objs.getGetTime() != null) {
                        mapq.put("registerTime", sdf.format(objs.getRegisterTime()));
                    } else {
                        mapq.put("registerTime", "");
                    }
                    mapq.put("getPerson", objs.getGetPerson());
                    if (objs.getGetTime() != null) {
                        mapq.put("getTime", sdf.format(objs.getGetTime()));
                    } else {
                        mapq.put("getTime", "");
                    }
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    PoliceBelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("burcode", objs.getBurcode());
                    mapq.put("name", objs.getName());
                    mapq.put("description", objs.getDescription());
                    mapq.put("detailCount", objs.getDetailCount());
                    mapq.put("personname", objs.getPersonname());
                    mapq.put("involvedReason", objs.getInvolvedReason());
                    mapq.put("policeName", objs.getPoliceName());
                    mapq.put("areaName", objs.getAreaName());
                    if (objs.getGetTime() != null) {
                        mapq.put("registerTime", sdf.format(objs.getRegisterTime()));
                    } else {
                        mapq.put("registerTime", "");
                    }
                    mapq.put("getPerson", objs.getGetPerson());
                    if (objs.getGetTime() != null) {
                        mapq.put("getTime", sdf.format(objs.getGetTime()));
                    } else {
                        mapq.put("getTime", "");
                    }
                    list.add(mapq);
                }
                for (int i = 0; i < 8 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("no", security.size() + i + 1);
                    mapq.put("burcode", "");
                    mapq.put("name", "");
                    mapq.put("description", "");
                    mapq.put("detailCount", "");
                    mapq.put("personname", "");
                    mapq.put("involvedReason", "");
                    mapq.put("policeName", "");
                    mapq.put("registerTime", "");
                    mapq.put("getPerson", "");
                    mapq.put("getTime", "");
                    mapq.put("areaName", "");
                    list.add(mapq);
                }
            }
        } else {
            for (int i = 0; i < 8 - security.size(); i++) {
                Map mapq = new HashMap();
                mapq.put("no", security.size() + i + 1);
                mapq.put("burcode", "");
                mapq.put("name", "");
                mapq.put("description", "");
                mapq.put("detailCount", "");
                mapq.put("personname", "");
                mapq.put("involvedReason", "");
                mapq.put("policeName", "");
                mapq.put("registerTime", "");
                mapq.put("getPerson", "");
                mapq.put("getTime", "");
                mapq.put("areaName", "");
                list.add(mapq);
            }
        }
        maps.put("list", list);
        maps.put("areaName", list.get(0).get("areaName"));
        result.setData(maps);
        return result;
    }
    @Override
    public LawDocProcessEntity getProcessData1(CheckbodyLawDocForm form) {
        int id = form.getCheckbodyId();
        System.err.println("################################id########################" + id);
        CheckBodyEntity checkbody = iCheckBodyMapper.queryDataByid(id);
        // 被检查人id
        long personId = form.getUserId();
        PersonEntity obj = iPersonMapper.queryPersonInfoById(personId);
        // ToDo 根据id 查出数据填充map
        LawDocProcessEntity result = new LawDocProcessEntity();
        result.setXmlFileName("入所健康检查表.xml");
        result.setDownFileName("入所健康检查表.doc");
        result.setFileType(1);
        // --------------------------------------------
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        // ---------------------------------
        Map mapq = new HashMap();
        List<Map> list = new ArrayList<Map>();
        if (obj != null && checkbody != null) {
            // 主表
            String checktime = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (checkbody.getCheckTime() != null) {
                checktime = sdf.format(checkbody.getCheckTime());
                String[] checktimes = checktime.split("-");
                mapq.put("year", checktimes[0]);
                mapq.put("month", checktimes[1]);
                mapq.put("day", checktimes[2]);
            }
            mapq.put("name", obj.getName());
            if (obj.getSex() != null) {
                if ("0".equals(obj.getSex())) {
                    mapq.put("sex", "未知的性别");
                }
                if ("1".equals(obj.getSex())) {
                    mapq.put("sex", "男");
                }
                if ("2".equals(obj.getSex())) {
                    mapq.put("sex", "女");
                }
                if ("9".equals(obj.getSex())) {
                    mapq.put("sex", "未说明的性别");
                }
            } else {
                mapq.put("sex", "");
            }

            if (obj.getBirth() != null) {
                String birthTime = sdf.format(obj.getBirth());
                mapq.put("birth", birthTime);
            } else {
                mapq.put("birth", "");
            }
            mapq.put("weight", checkbody.getWeight());
            mapq.put("height", checkbody.getHeight());
            mapq.put("footsize", checkbody.getFootsize());
            mapq.put("china", obj.getNation());
            if ("0".equals(obj.getEducation())) {
                mapq.put("kownleage", "文盲");
            }
            if ("1".equals(obj.getEducation())) {
                mapq.put("kownleage", "小学");
            }
            if ("2".equals(obj.getEducation())) {
                mapq.put("kownleage", "初中");
            }
            mapq.put("marital", checkbody.getMaritalstatus());
            mapq.put("health", checkbody.getHealth());
            mapq.put("identityCard", obj.getCertificateNo());
            if (obj.getJob() != null && obj.getJobTitle() != null) {
                mapq.put("workSpace", obj.getJob() + obj.getJobTitle());
            } else if (obj.getJob() != null && obj.getJobTitle() == null) {
                mapq.put("workSpace", obj.getJob() + "");
            } else if (obj.getJob() == null && obj.getJobTitle() != null) {
                mapq.put("workSpace", "" + obj.getJobTitle());
            }
            mapq.put("workSpace", "");

            mapq.put("bodyTag", checkbody.getBodyTag());
            mapq.put("historyHealthy", checkbody.getHistoryhealthy());
            mapq.put("drugHistory", checkbody.getDrughistory());
            mapq.put("contagion", checkbody.getContagion());
            mapq.put("selfReported", checkbody.getSelfReported());
            mapq.put("checkStatus", checkbody.getCheckstatus());
            mapq.put("compete", checkbody.getLanguagecompetence());
            mapq.put("mouthSound", checkbody.getMouthsound());
            mapq.put("Activity", checkbody.getPhysicalactivity());
            mapq.put("doctor", checkbody.getDoctorsignature());
            mapq.put("leader", checkbody.getLeadersignature());
            mapq.put("checked", checkbody.getCheckedSignature());
            mapq.put("sender", checkbody.getSendersignature());
            mapq.put("sendUnit", checkbody.getSendunit());
            mapq.put("remark", checkbody.getRemark());
            // 附表
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (checkbody.getChecktimeStart() != null) {
                String checktimeS = sdf1.format(checkbody.getChecktimeStart());
                String timestart[] = checktimeS.split(" ");
                String[] start = timestart[0].split("-");
                mapq.put("ys", start[0]);
                mapq.put("ms", start[1]);
                mapq.put("ds", start[2]);
                String timestarts[] = timestart[1].split(":");
                mapq.put("hs", timestarts[0]);
                mapq.put("mis", timestarts[1]);
            } else {
                mapq.put("ys", "");
                mapq.put("ms", "");
                mapq.put("ds", "");
                mapq.put("hs", "");
                mapq.put("mis", "");
            }
            if (checkbody.getChecktimeEnd() != null) {
                String checktimeE = sdf1.format(checkbody.getChecktimeEnd());
                String timeend[] = checktimeE.split(" ");
                String timeends[] = timeend[1].split(":");
                String end[] = timeend[0].split("-");
                mapq.put("ye", end[0]);
                mapq.put("me", end[1]);
                mapq.put("de", end[2]);
                mapq.put("he", timeends[0]);
                mapq.put("mie", timeends[1]);
            } else {
                mapq.put("ye", "");
                mapq.put("me", "");
                mapq.put("de", "");
                mapq.put("he", "");
                mapq.put("mie", "");
            }
            mapq.put("checkSpace", checkbody.getCheckspace());
            mapq.put("n1", checkbody.getBodytemperature());
            mapq.put("n2", checkbody.getPulse());
            mapq.put("n3", checkbody.getBloodpressure());
            mapq.put("n4", checkbody.getBreathing());
            if ("0".equals(checkbody.getBodysize())) {
                mapq.put("n5", "（肥胖√ 中等 偏瘦）");
            }
            if ("1".equals(checkbody.getBodysize())) {
                mapq.put("n5", "（肥胖 中等√ 偏瘦）");
            }
            if ("2".equals(checkbody.getBodysize())) {
                mapq.put("n5", "（肥胖 中等 偏瘦√）");
            }
            if ("".equals(checkbody.getBodysize()) || checkbody.getBodysize() == null) {
                mapq.put("n5", "（肥胖 中等 偏瘦）");
            }
            mapq.put("n6", checkbody.getFaceshape());
            mapq.put("n7", checkbody.getBloodtype());
            if ("0".equals(checkbody.getNutritionalstatus())) {
                mapq.put("n8", "（良好√ 中等 不良）");
            }
            if ("1".equals(checkbody.getNutritionalstatus())) {
                mapq.put("n8", "（良好 中等√ 不良）");
            }
            if ("2".equals(checkbody.getNutritionalstatus())) {
                mapq.put("n8", "（良好 中等 不良√）");
            }
            if ("".equals(checkbody.getNutritionalstatus()) || checkbody.getNutritionalstatus() == null) {
                mapq.put("n8", "（良好 中等 不良）");
            }
            if ("0".equals(checkbody.getConsciousness())) {
                mapq.put("n9", "（清晰√/障碍）");
            }
            if ("1".equals(checkbody.getConsciousness())) {
                mapq.put("n9", "（清晰/障碍√）");
            }
            if ("".equals(checkbody.getConsciousness()) || checkbody.getConsciousness() == null) {
                mapq.put("n9", "（清晰/障碍）");
            }
            if ("0".equals(checkbody.getWords())) {
                mapq.put("n10", "（正常√/障碍）");
            }
            if ("1".equals(checkbody.getWords())) {
                mapq.put("n10", "（正常/障碍√）");
            }
            if ("".equals(checkbody.getWords()) || checkbody.getWords() == null) {
                mapq.put("n10", "（正常/障碍）");
            }
            if ("0".equals(checkbody.getGait())) {
                mapq.put("n11", "（正常√/不正常） ");
            }
            if ("1".equals(checkbody.getGait())) {
                mapq.put("n11", "（正常/不正常√） ");
            }
            if ("".equals(checkbody.getGait()) || checkbody.getGait() == null) {
                mapq.put("n11", "（正常/不正常） ");
            }
            if ("0".equals(checkbody.getDoublepupil())) {
                mapq.put("n12", "（等大√/不等大）");
            }
            if ("1".equals(checkbody.getDoublepupil())) {
                mapq.put("n12", "（等大/不等大√）");
            }
            if ("".equals(checkbody.getDoublepupil()) || checkbody.getDoublepupil() == null) {
                mapq.put("n12", "（等大/不等大）");
            }
            if ("0".equals(checkbody.getSkinsclerayellowdye())) {
                mapq.put("n13", "（无√/有）");
            }
            if ("1".equals(checkbody.getSkinsclerayellowdye())) {
                mapq.put("n13", "（无/有√）");
            }
            if ("".equals(checkbody.getSkinsclerayellowdye()) || checkbody.getSkinsclerayellowdye() == null) {
                mapq.put("n13", "（无/有）");
            }
            if ("0".equals(checkbody.getDoublelung())) {
                mapq.put("n14", "呼吸音（清晰√/异常）");
            }
            if ("1".equals(checkbody.getDoublelung())) {
                mapq.put("n14", "呼吸音（清晰/异常√）");
            }
            if ("".equals(checkbody.getDoublelung()) || checkbody.getDoublelung() == null) {
                mapq.put("n14", "呼吸音（清晰/异常）");
            }
            mapq.put("n15", checkbody.getHeartrate());
            if ("0".equals(checkbody.getHeartratestatus())) {
                mapq.put("n16", "（齐√/不齐）");
            }
            if ("1".equals(checkbody.getHeartratestatus())) {
                mapq.put("n16", "（齐/不齐√）");
            }
            if ("".equals(checkbody.getHeartratestatus()) || checkbody.getHeartratestatus() == null) {
                mapq.put("n16", "（齐/不齐）");
            }
            if ("0".equals(checkbody.getNoise())) {
                mapq.put("n17", "（未闻及√/可闻及）");
            }
            if ("1".equals(checkbody.getNoise())) {
                mapq.put("n17", "（未闻及/可闻及√）");
            }
            if ("".equals(checkbody.getNoise()) || checkbody.getNoise() == null) {
                mapq.put("n17", "（未闻及/可闻及）");
            }
            if ("0".equals(checkbody.getAbdomen())) {
                mapq.put("n18", "压痛(无√/有) ");
            }
            if ("1".equals(checkbody.getAbdomen())) {
                mapq.put("n18", "压痛(无/有√) ");
            }
            if ("".equals(checkbody.getAbdomen()) || checkbody.getAbdomen() == null) {
                mapq.put("n18", "压痛(无/有) ");
            }

            if ("0".equals(checkbody.getReboundtenderness())) {
                mapq.put("n19", "(无√/有) ");
            }
            if ("1".equals(checkbody.getAbdomen())) {
                mapq.put("n19", "(无/有√) ");
            }
            if ("".equals(checkbody.getReboundtenderness()) || checkbody.getReboundtenderness() == null) {
                mapq.put("n19", "(无/有) ");
            }
            if ("0".equals(checkbody.getMuscletension())) {
                mapq.put("n20", "(无√/有)");
            }
            if ("1".equals(checkbody.getMuscletension())) {
                mapq.put("n20", "(无/有√)");
            }
            if ("".equals(checkbody.getMuscletension()) || checkbody.getMuscletension() == null) {
                mapq.put("n20", "(无/有)");
            }
            if ("0".equals(checkbody.getLiverandspleen())) {
                mapq.put("n21", "（未扪及√/可扪及）");
            }
            if ("1".equals(checkbody.getLiverandspleen())) {
                mapq.put("n21", "（未扪及/可扪及√）");
            }
            if ("".equals(checkbody.getLiverandspleen()) || checkbody.getLiverandspleen() == null) {
                mapq.put("n21", "（未扪及/可扪及）");
            }
            if ("0".equals(checkbody.getSpinallimbs())) {
                mapq.put("n22", "（无异常√/异常）");
            }
            if ("1".equals(checkbody.getSpinallimbs())) {
                mapq.put("n22", "（无异常/异常√）");
            }
            if ("".equals(checkbody.getSpinallimbs()) || checkbody.getSpinallimbs() == null) {
                mapq.put("n22", "（无异常/异常）");
            }
            mapq.put("surfaceInspection", checkbody.getSurfaceinspection());
            mapq.put("abnormalSigns", checkbody.getAbnormalsigns());
            mapq.put("routineBlood", checkbody.getRoutineblood());
            mapq.put("bUltrasonic", checkbody.getBultrasonic());
            mapq.put("electrocardiogram", checkbody.getElectrocardiogram());
            mapq.put("rabat", checkbody.getRabat());
            mapq.put("doctorAdvice", checkbody.getDoctoradvice());
            mapq.put("urinePregnancy", checkbody.getUrinepregnancyresult());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            mapq.put("womenTime", checkbody.getWomenlastperiodtime());
            if (checkbody.getCheckbodyPicturePath() != null && checkbody.getCheckbodyPicturePath() != "") {
                String imageStr = "";
                ImageUtil imageUtil = new ImageUtil();
                try {
                    File file = new File(checkbody.getCheckbodyPicturePath());
                    if (!file.exists()) {
                        System.err.println("本地无此图片");
                    } else {
                        imageStr = imageUtil.getImageStr(checkbody.getCheckbodyPicturePath());
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                mapq.put("image", imageStr);
            }

            result.setData(mapq);
        }
        return result;
    }

    /**
     * 涉案人员随身物品代为保管物品登记表
     *
     * @param form
     * @param result
     * @return
     */
    @Override
    public LawDocProcessEntity getProcessData55(BelongDocForm form, LawDocProcessEntity result) {
        List<BelongEntity> security = new ArrayList<BelongEntity>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("areaId", form.getDataId());
        paramMap.put("startTime", form.getStartTime());
        paramMap.put("endTime", form.getEndTime());
        try {
            security = iBelongMapper.getBelongForExport(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setXmlFileName("涉案人员随身物品代为保管物品登记表.xml");
        result.setDownFileName("涉案人员随身物品代为保管物品登记表.doc");
        result.setFileType(1);
        String filename1 = Utils.getUniqueId();
        String filename2 = ".doc";
        String fileName = filename1 + filename2;
        result.setTempFileName(fileName);
        result.setSerialNo(form.getSerialNo());
        Map maps = new HashMap();
        List<Map> list = new ArrayList<Map>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (security != null && security.size() > 0) {
            BelongEntity obj = security.get(0);
            maps.put("policeStation", obj.getPoliceName());
            if (security.size() >= 8) {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("burcode", objs.getBurcode());
                    mapq.put("name", objs.getName());
                    mapq.put("description", objs.getDescription());
                    mapq.put("detailCount", objs.getDetailCount());
                    mapq.put("personName", objs.getPersonname());
                    mapq.put("involvedReason", objs.getInvolvedReason());
                    mapq.put("policeName", objs.getPoliceName());
                    mapq.put("areaName", objs.getAreaName());
                    if (objs.getGetTime() != null) {
                        mapq.put("registerTime", sdf.format(objs.getRegisterTime()));
                    } else {
                        mapq.put("registerTime", "");
                    }
                    mapq.put("getPerson", objs.getGetPerson());
                    if (objs.getGetTime() != null) {
                        mapq.put("getTime", sdf.format(objs.getGetTime()));
                    } else {
                        mapq.put("getTime", "");
                    }
                    list.add(mapq);
                }
            } else {
                for (int i = 0; i < security.size(); i++) {
                    BelongEntity objs = security.get(i);
                    Map mapq = new HashMap();
                    mapq.put("no", i + 1);
                    mapq.put("burcode", objs.getBurcode());
                    mapq.put("name", objs.getName());
                    mapq.put("description", objs.getDescription());
                    mapq.put("detailCount", objs.getDetailCount());
                    mapq.put("personName", objs.getPersonname());
                    mapq.put("involvedReason", objs.getInvolvedReason());
                    mapq.put("policeName", objs.getPoliceName());
                    mapq.put("areaName", objs.getAreaName());
                    if (objs.getGetTime() != null) {
                        mapq.put("registerTime", sdf.format(objs.getRegisterTime()));
                    } else {
                        mapq.put("registerTime", "");
                    }
                    mapq.put("getPerson", objs.getGetPerson());
                    if (objs.getGetTime() != null) {
                        mapq.put("getTime", sdf.format(objs.getGetTime()));
                    } else {
                        mapq.put("getTime", "");
                    }
                    list.add(mapq);

                }
                for (int i = 0; i < 8 - security.size(); i++) {
                    Map mapq = new HashMap();
                    mapq.put("no", security.size() + i + 1);
                    mapq.put("burcode", "");
                    mapq.put("name", "");
                    mapq.put("description", "");
                    mapq.put("detailCount", "");
                    mapq.put("personName", "");
                    mapq.put("involvedReason", "");
                    mapq.put("policeName", "");
                    mapq.put("registerTime", "");
                    mapq.put("getPerson", "");
                    mapq.put("getTime", "");
                    mapq.put("areaName", "");
                    list.add(mapq);
                }
            }
        } else {
            for (int i = 0; i < 8 - security.size(); i++) {
                Map mapq = new HashMap();
                mapq.put("no", security.size() + i + 1);
                mapq.put("burcode", "");
                mapq.put("name", "");
                mapq.put("description", "");
                mapq.put("detailCount", "");
                mapq.put("personName", "");
                mapq.put("involvedReason", "");
                mapq.put("policeName", "");
                mapq.put("registerTime", "");
                mapq.put("getPerson", "");
                mapq.put("getTime", "");
                mapq.put("areaName", "");
                list.add(mapq);
            }
        }
        maps.put("list", list);
        maps.put("areaName", list.get(0).get("areaName"));
        result.setData(maps);
        return result;
    }
}
