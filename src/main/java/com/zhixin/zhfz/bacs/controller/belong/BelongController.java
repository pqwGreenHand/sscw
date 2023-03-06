package com.zhixin.zhfz.bacs.controller.belong;

import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.BelongAppForm;
import com.zhixin.zhfz.bacs.form.BelongForm;
import com.zhixin.zhfz.bacs.services.bazx.IBazxService;
import com.zhixin.zhfz.bacs.services.belong.IBelongService;
import com.zhixin.zhfz.bacs.services.belong.IBelongVideoService;
import com.zhixin.zhfz.bacs.services.belongtemp.IBelongDetailTempService;
import com.zhixin.zhfz.bacs.services.belongtemp.IBelongTempService;
import com.zhixin.zhfz.bacs.services.cabinetConfig.ICabinetConfigDetailService;
import com.zhixin.zhfz.bacs.services.combobox.IComboboxService;
import com.zhixin.zhfz.bacs.services.openCabinet.IOpenCabinetService;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.serial.ISerialStatusService;
import com.zhixin.zhfz.bacs.services.zfba.IZfbaService;
import com.zhixin.zhfz.common.common.CabinetUtil;
import com.zhixin.zhfz.common.common.CabinetUtilZhiXin;
import com.zhixin.zhfz.common.common.FileUtil;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.common.utils.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/belong")
public class BelongController {

    private static Logger logger = LoggerFactory.getLogger(BelongController.class);
    private static String kh_base_url = "http://14.112.65.153:8090";

    @Autowired
    private IBelongService belongService;

    @Autowired
    private IBelongVideoService belongVideoService;

    @Autowired
    private ICabinetConfigDetailService cabinetConfigDetailService;

    @Autowired
    private IComboboxService comboboxService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ISerialService serialService;

    @Autowired
    private ISerialStatusService serialStatusService;

    @Autowired
    private IFileConfigService fileConfigService;

    @Autowired
    private IOpenCabinetService openCabinetService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IOrganizationService organizationService;
    @Autowired
    private IBelongDetailTempService belongDetailTempService;
    @Autowired
    private IBelongTempService belongTempService;

    @Autowired
    private ICaseService caseService;

    @Resource
    private IBazxService bazxService;

    @Resource
    private IZfbaService zfbaService;

    @RequestMapping(value = "/listCaseZfba")
    @ResponseBody
    public Map<String, Object> listCaseZfba(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
       map.put("start", (Integer.parseInt(map.get("page").toString()) - 1) * Integer.parseInt(map.get("rows").toString()) + 1);
        map.put("end", Integer.parseInt(map.get("page").toString()) * Integer.parseInt(map.get("rows").toString()));
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("orgCode",sessionInfo.getCurrentOrg().getOrgCode());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = belongService.listCaseZfba(map);
        int total = belongService.listCaseZfbaCount(map);
        result.put("total", total);
        result.put("rows", list);
        return result;
    }
    @RequestMapping(value = "/listPersonZfba")
    @ResponseBody
    public Map<String, Object> listPersonZfba(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list= new ArrayList<>();
        try{
            list = belongService.listPersonZfba(map);
        }catch (Exception e){}

        result.put("rows", list);
        return result;
    }
    /**
     * 轨迹时间轴
     *
     * @param map
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSjz")
    @ResponseBody
    public Map<String, Object> getSjz(@RequestParam Map<String, Object> map, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        BelongEntity belongdetById = belongService.getBelongdetById(Integer.valueOf(id));
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map> list = new ArrayList<Map>();
        if (belongdetById == null) {
            result.put("result", list);
            return result;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (belongdetById.getCreatedTime() != null) {
            Map smap = new HashMap();
            smap.put("startTime", format.format(belongdetById.getCreatedTime()));
            smap.put("url", "");
            smap.put("name", "存物");
            list.add(smap);
        }
        if (belongdetById.getGetTime() != null) {
            Map smap = new HashMap();
            smap.put("startTime", format.format(belongdetById.getGetTime()));
            smap.put("url", "");
            String value = belongdetById.getGetWay();
            String name = "取物";
            if ("1".equals(value) ) {
                name = "本人领取";
            } else if ("0".equals(value) ) {
                name = "未领取";
            } else if ("2".equals(value) ) {
                name = "委托他人代为领取";
            } else if ("3".equals(value) ) {
                name = "本人收到扣押物品清单";
            } else if ("4".equals(value) ) {
                name = "转涉案财物";
            } else if ("5".equals(value) ) {
                name = "移交";
            }
            smap.put("name", "取物__"+name);
            list.add(smap);
        }
        ListSorts(list);
        result.put("result", list);
        return result;
    }

    private static void ListSorts(List<Map> list) {
        final SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 按照进入时间
        Collections.sort(list, new Comparator<Map>() {
            @Override
            public int compare(Map u1, Map u2) {
                String D1 = u1.get("startTime").toString();
                String D2 = u2.get("startTime").toString();
                Date u1s = new Date();
                Date u2s = new Date();
                try {
                    u1s = sdfs.parse(D1);
                    u2s = sdfs.parse(D2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if ((u1s).compareTo(u2s) > 0) {
                    return 1;
                }
                if ((u1s).compareTo(u2s) == 0) {
                    return 0;
                }
                return -1;
            }
        });
    }

    /**
     * 查询物品信息
     *
     * @param wpUuid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryBelongDtailByUUID")
    @ResponseBody
    public List<BelongEntity> queryBelongDtailByUUID(@RequestParam String wpUuid) throws Exception {
        List<BelongEntity> list = new ArrayList<>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("wpUuid", wpUuid);
            BelongEntity entity = belongService.getBelongdetByWpUuid(map);
            if (entity != null) {
                list.add(entity);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return list;
    }

    /**
     * 随身物品保存随身物品 （新增(存柜时)）
     *
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/saveYjBelong")
    @ResponseBody
    public MessageEntity saveYjBelong(@RequestBody BelongTempEntity form, HttpServletRequest request,
                                      HttpServletResponse response) {
        BelongEntity entity = new BelongEntity();
        Boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        map.put("id", form.getId());
        map.put("tempId", form.getId());
        List<BelongTempEntity> tempEntity = belongTempService.getEntityById(map);
        List<BelongDetailTempEntity> detailEntity = belongDetailTempService.getListByTempId(map);

        try {
            PersonEntity personEntity = personService.getPersonById(tempEntity.get(0).getPersonId());
            /*if (ControllerTool.getCurrentAreaID(request) != personEntity.getAreaId()) {
                personEntity.setId(null);
                personEntity.setAreaId(ControllerTool.getCurrentAreaID(request));
                personService.insert(personEntity);
            }*/
            for (BelongDetailTempEntity belongEntity : detailEntity) {
                entity.setSerialId(personEntity.getId());
                entity.setRegisterUserId(ControllerTool.getUserID(request));
                entity.setRegisterTime(new Date());
                entity.setCaseId(tempEntity.get(0).getCaseId());
                entity.setAreaId(ControllerTool.getCurrentAreaID(request));
                entity.setLockerId(form.getLockerId() + "");
                entity.setIsGet(0);
                entity.setGetWay("0");
                entity.setBelongingsId(form.getId());
                entity.setCreatedTime(new Date());
                entity.setUpdatedTime(new Date());
                entity.setName(belongEntity.getName());
                entity.setDetailCount(belongEntity.getDetailCount());
                entity.setDescription(belongEntity.getDescription());
                entity.setUnit(belongEntity.getUnit());
                entity.setSaveMethod(belongEntity.getSaveMethod());
                entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                if (belongEntity.getWpUuid() == null) {
                    entity.setWpUuid(UUID.randomUUID().toString());
                    flag = true;//先关闭掉调用宽和接口
                } else {
                    entity.setWpUuid(belongEntity.getWpUuid());
                }
                int result = this.belongService.insertBelong(entity, request);
                if (result == 0) {// 判断柜子是否被占用，返回提示
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("失败").addContent("该柜已被占用,请选择其他柜!");
                }
                //更新移交状态
                BelongTempEntity entityTemp = new BelongTempEntity();
                entityTemp.setId(form.getId());
                entityTemp.setStatus(1);
                entityTemp.setYjyj("已接收");
                entityTemp.setYjjg("Y");
                belongTempService.updateTempYjyj(entityTemp);


//先关闭掉调用宽和接口，后期对接的时候再打开
//                if (flag) {
//                    //调用宽和新增物品接口
//                    try {
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("xm", personEntity.getName());
//                        jsonObject.put("sfzh", personEntity.getCertificateNo());
//                        jsonObject.put("ay", serialEntity.getCaseNature());
//                        jsonObject.put("rywbbh", form.getSerialId());
//                        jsonObject.put("userZH", ControllerTool.getLoginName(request));
//                        JSONArray jsonArray = new JSONArray();
//                        JSONObject jsonObject1 = new JSONObject();
//                        jsonObject1.put("mc", belongEntity.getName());
//                        jsonObject1.put("sl", belongEntity.getDetailCount());
//                        jsonObject1.put("tz", belongEntity.getDescription());
//                        jsonObject1.put("cfdd", form.getCabinetNoStr());
//                        jsonObject1.put("wbwpbh", entity.getWpUuid());
//                        jsonObject1.put("bamjjh", ControllerTool.getLoginName(request));
//                        jsonObject1.put("czmjjh", ControllerTool.getLoginName(request));
//                        jsonObject1.put("glmjjh", ControllerTool.getLoginName(request));
//                        jsonArray.add(jsonObject1);
//                        jsonObject.put("sscwSscwList", jsonArray);
//                        String url = kh_base_url + "/sscw/sarjk/addJK";
//                        String resultObj = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url, "", jsonObject.toString()));
//                        logger.info("调用随身物品接口(" + url + ")，参数为：" + jsonObject.toString() + "，结果：" + resultObj);
//                    } catch (Exception e) {
//                        logger.info("调用随身物品接口" + e);
//                    }
//                } else {
//                    try {
//                        BelongTempEntity entityTemp = new BelongTempEntity();
//                        entityTemp.setId(Integer.valueOf(belongEntity.getTempId()));
//                        entityTemp.setYjyj("已同意");
//                        entityTemp.setYjjg("Y");
//                        belongTempService.updateTempYjyj(entityTemp);
//
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("id", belongEntity.getTempId());
//                        List<BelongTempEntity> tempEntity = belongTempService.getEntityById(map);
//                        for (BelongTempEntity belongTempEntity : tempEntity) {
//                            //调用宽和新增物品接口
//                            try {
//                                JSONObject jsonObject = new JSONObject();
//                                jsonObject.put("yjdwid", belongTempEntity.getJsdwId());
//                                jsonObject.put("jsdwid", belongTempEntity.getJsdwId());
//                                jsonObject.put("yjjg", belongTempEntity.getYjjg());
//                                jsonObject.put("btyyj", belongTempEntity.getYjyj());
//                                jsonObject.put("wbwpbh", belongTempEntity.getWpUuid());
//                                jsonObject.put("userZH", ControllerTool.getLoginName(request));
//                                String url = kh_base_url + "/sscwjk/sftyYiJiao";
//                                String resultObj = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url, "", jsonObject.toString()));
//                                logger.info("随身物品移交是否同意数据接收接口(" + url + ")，参数为：" + jsonObject.toString() + "，结果：" + resultObj);
//                            } catch (Exception e) {
//                                logger.info("随身物品移交是否同意数据接收接口" + e);
//                            }
//
//                        }
//                    } catch (Exception e) {
//                        logger.info("随身物品移交是否同意数据接收接口" + e);
//                    }
//                }
            }
            return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent(personEntity.getId() + "");
        } catch (Exception e) {
            logger.info("" + e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }

    }

    /**
     * 查询物品临时主表
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateBelongTempStatusById")
    @ResponseBody
    public MessageEntity updateBelongTempStatusById(@RequestBody BelongTempEntity form) throws Exception {
        List<BelongDetailTempEntity> list = new ArrayList<>();
        try {
            BelongTempEntity entity = new BelongTempEntity();
            entity.setId(form.getId());
            entity.setStatus(2);
            entity.setId(form.getId());
            entity.setYjyj(form.getYjyj());
            entity.setYjjg("N");
            belongTempService.updateTempYjyj(entity);
            logger.info("### 更新临时主表成功 ### ");
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改成功!");
    }

    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listBelongTemp")
    @ResponseBody
    public Map<String, Object> listBelongTemp(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-------------------------law/list------------------------------");
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<BelongTempEntity> list = new ArrayList<BelongTempEntity>();
        int total = 0;
        String orgCode = organizationService.getOrganizationById(ControllerTool.getSessionInfo(request).getCurrentOrg().getId()).getOrgCode();
        map.put("orgCode", orgCode);
        list = this.belongTempService.list(map);
        total = this.belongTempService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    /**
     * 查所有及分页及条件查询
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listBelongTempDetail")
    @ResponseBody
    public Map<String, Object> listBelongTempDetail(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("-------------------------law/list------------------------------");
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<BelongDetailTempEntity> list = new ArrayList<BelongDetailTempEntity>();
        int total = 0;
        list = this.belongDetailTempService.list(map);
        total = this.belongDetailTempService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/queryCase")
    @ResponseBody
    public Map<String, Object> queryCase(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        logger.info("开始警综信息数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        map.put("orgCode",sessionInfo.getCurrentOrg().getOrgCode());
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( c.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ( c.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<CaseEntity> list = caseService.list(map);
        int total = caseService.count(map);
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/queryPerson")
    @ResponseBody
    public Map<String, Object> queryPerson(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        logger.info("开始警综信息数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("orgCode",sessionInfo.getCurrentOrg().getOrgCode());
            map.put("dataAuth", " ( m.id = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ( m.id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<PersonEntity> list = personService.list(map);
        int total = personService.count(map);
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/queryBazxCase")
    @ResponseBody
    public Map<String, Object> queryBazxCase(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info("开始办案中心案件数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = bazxService.queryAjxx(map);
        int total = bazxService.queryAjxxCount(map);
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/queryBazxPerson")
    @ResponseBody
    public Map<String, Object> queryBazxPerson(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        logger.info("开始办案中心人员数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = bazxService.queryPerson(map);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/queryBazxBelong")
    @ResponseBody
    public Map<String, Object> queryBazxBelong(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        logger.info("开始办案中心物品数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = bazxService.queryBelong(map);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/queryZfbaCase")
    @ResponseBody
    public Map<String, Object> queryZfbaCase(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info("开始执法办案平台案件数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = zfbaService.queryAjxx(map);
        int total = zfbaService.queryAjxxCount(map);
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/queryZfbaPerson")
    @ResponseBody
    public Map<String, Object> queryZfbaPerson(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        logger.info("开始执法办案平台人员数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = zfbaService.queryPerson(map);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/queryZfbaBelong")
    @ResponseBody
    public Map<String, Object> queryZfbaBelong(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        logger.info("开始执法办案平台物品数据查询------------------");
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = zfbaService.queryBelong(map);
        result.put("rows", list);
        return result;
    }

    /**
     * 查询物品临时主表
     *
     * @param sfzh
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryBelongTempBySfzh")
    @ResponseBody
    public List<BelongTempEntity> queryDeDjstailById(@RequestParam String sfzh, HttpServletRequest request) throws Exception {
        List<BelongTempEntity> list = new ArrayList<>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("sfzh", sfzh);
            map.put("jsdwId", organizationService.getOrganizationById(ControllerTool.getSessionInfo(request).getCurrentOrg().getId()).getOrgCode());
            list = belongTempService.getListBySfzh(map);
            logger.info("###查询物品临时主表成功 ### ");
        } catch (Exception e) {
            logger.error("", e);
        }
        return list;
    }

    /**
     * 查询物品临时主表
     *
     * @param tempId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryBelongDtailById")
    @ResponseBody
    public List<BelongDetailTempEntity> queryBelongDtailById(@RequestParam String tempId) throws Exception {
        List<BelongDetailTempEntity> list = new ArrayList<>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("tempId", tempId);
            list = belongDetailTempService.getListByTempId(map);
            logger.info("### 查询物品临时主表成功 ### ");
        } catch (Exception e) {
            logger.error("", e);
        }
        return list;
    }

    /**
     * 修改移交意见
     *
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateTempYjyj")
    @ResponseBody
    public MessageEntity updateTempYjyj(@RequestBody BelongTempEntity form, HttpServletRequest request,
                                        HttpServletResponse response) {
        BelongTempEntity entity = new BelongTempEntity();
        entity.setId(form.getId());
        entity.setYjyj(form.getYjyj());
        entity.setYjjg("N");
        try {
            belongTempService.updateTempYjyj(entity);
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("id", form.getId());
                List<BelongTempEntity> tempEntity = belongTempService.getEntityById(map);
                for (BelongTempEntity belongTempEntity : tempEntity) {
                    //调用宽和新增物品接口
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("yjdwid", belongTempEntity.getJsdwId());
                    jsonObject.put("jsdwid", belongTempEntity.getJsdwId());
                    jsonObject.put("yjjg", belongTempEntity.getYjjg());
                    jsonObject.put("btyyj", belongTempEntity.getYjyj());
                    jsonObject.put("wbwpbh", belongTempEntity.getWpUuid());
                    jsonObject.put("userZH", ControllerTool.getLoginName(request));
                    String url = kh_base_url + "/sscwjk/sftyYiJiao";
                    String resultObj = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url, "", jsonObject.toString()));
                    logger.info("随身物品移交是否同意数据接收接口(" + url + ")，参数为：" + jsonObject.toString() + "，结果：" + resultObj);
                }
            } catch (Exception e) {
                logger.info("随身物品移交是否同意数据接收接口" + e);
            }

            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改移交意见" + entity, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改移交意见" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改成功!");
    }

    /**
     * 随身储物柜页面显示排序
     *
     * @param areaid
     * @return
     */
    @RequestMapping(value = "/listboxinfo")
    @ResponseBody
    public TreeMap<String, List<CabinetConfigDetailEntity>> listboxinfo(@RequestParam String areaid) {
        List<CabinetConfigDetailEntity> list = cabinetConfigDetailService.selectRowColSort(Integer.parseInt(areaid));
        TreeMap<String, List<CabinetConfigDetailEntity>> result = new TreeMap<String, List<CabinetConfigDetailEntity>>();
        for (int i = 0; i < list.size(); i++) {
            if (result.containsKey(list.get(i).getGroup())) {
                result.get(list.get(i).getGroup()).add(list.get(i));
            } else {
                List<CabinetConfigDetailEntity> param = new ArrayList<CabinetConfigDetailEntity>();
                param.add(list.get(i));
                result.put(list.get(i).getGroup(), param);
            }
        }
        return result;
    }

    /**
     * 展示一个入区编号的所有柜子及规则所有物品
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelongdet2")
    @ResponseBody
    public Map<String, Object> listAllBelongdet2(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        if (pageMap.get("enpId") == null) {
            return null;
        }
        List<BelongEntity> list = new ArrayList<BelongEntity>();
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        map.put("flag", "Belong");
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( d.op_user_id=" + ControllerTool.getUserID(request)
                    + " or s.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.send_user_id=" + ControllerTool.getUserID(request)
                    + " or pn.id =" + ControllerTool.getUserID(request)
                    + " or xu.id=" + ControllerTool.getUserID(request)
                    + " or xu2.id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( b.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ( b.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( b.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "  ( d.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or pn.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or xu.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or xu2.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", "  ( d.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or pn.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or xu.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or xu2.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", "  ( d.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or s.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or pn.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or xu.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or xu2.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")");
        }
        list = this.belongService.listAllBelongdet2(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        return result;
    }

    /**
     * 拼接存物柜下拉框信息
     *
     * @param lockerId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listBelongLockerBox")
    @ResponseBody
    public List<ComboboxEntity> listBelongLockerBox(Long lockerId, Integer areaId, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        logger.info("lockerId:" + lockerId);
        List<ComboboxEntity> list = null;
        if (sessionInfo != null) {
            list = comboboxService.listBelongLockerBox(areaId, lockerId);
        } else {
            list = new ArrayList<ComboboxEntity>();
        }
        return list;
    }

    /**
     * 随身物品保存随身物品 （新增(存柜时)）
     *
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/belongsave")
    @ResponseBody
    public MessageEntity belongsave(@RequestBody BelongForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        BelongEntity entity = new BelongEntity();
        try {
            entity.setSerialId(form.getSerialId());
            entity.setRegisterUserId(ControllerTool.getUserID(request));
            entity.setRegisterTime(new Date());
            entity.setCaseId(form.getCaseId());
            entity.setAreaId(ControllerTool.getCurrentAreaID(request));
            entity.setLockerId(form.getLockerId());
            entity.setIsGet(0);
            entity.setGetWay("0");
            entity.setCreatedTime(new Date());
            entity.setUpdatedTime(new Date());
            entity.setName(form.getName());
            entity.setDetailCount(form.getDetailCount());
            entity.setDescription(form.getDescription());
            entity.setUnit(form.getUnit());
            entity.setSaveMethod(form.getSaveMethod());
            entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            entity.setWpUuid("WP" + format.format(new Date()));
            int result = this.belongService.insertBelong(entity, request);
            if (result == 0) {// 判断柜子是否被占用，返回提示
                return new MessageEntity().addCode(1).addIsError(true).addTitle("失败").addContent("该柜已被占用,请选择其他柜!");
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增随身物品(存柜)", ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.info("" + e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增随身物品(存柜)", ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!").addCallbackData(form.getSerialId());
    }


    /**
     * 随身物品保存随身物品 （新增(存柜时)）
     *
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/belongsaveold")
    @ResponseBody
    public MessageEntity belongsaveold(@RequestBody Map<String, Object> arr, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        JSONArray jsonArr = JSONArray.fromObject(arr);
        JSONObject jsonObj = jsonArr.getJSONObject(0);
        JSONArray belongJsonArr = JSONArray.fromObject(jsonObj.get("list"));
        List<BelongEntity> list = new ArrayList<BelongEntity>();
        JSONArray formJsonArr = JSONArray.fromObject(jsonObj.get("data"));
        BelongForm form = (BelongForm) JSONObject.toBean(formJsonArr.getJSONObject(0), BelongForm.class);
        if ("bazx".equals(form.getXzType())) {
            System.err.println(form);
            //新建案件信息
            InterrogateCaseEntity interrogateCaseEntity = bazxService.queryAjxxById(form.getBazxCaseId());
            CaseEntity caseEntity = new CaseEntity();
            caseEntity.setAreaId(form.getAreaId());
            caseEntity.setAjmc(interrogateCaseEntity.getCaseName());
            caseEntity.setAjbh(interrogateCaseEntity.getCaseNo());
            caseEntity.setAfsj(interrogateCaseEntity.getInvolvedDate());
            caseEntity.setAfdd(interrogateCaseEntity.getInvolvedAddress());
            caseEntity.setAjlx(interrogateCaseEntity.getType());
            caseEntity.setZbdwId(ControllerTool.getCurrentOrgID(request));
            caseEntity.setCreatedTime(new Date());
            caseEntity.setZbmjPid(ControllerTool.getUserID(request) + "");
            caseEntity.setUuid(UUID.randomUUID().toString());
            caseEntity.setAreaId(ControllerTool.getCurrentArea(request).getId());
            Integer caseId = caseService.insertCase(caseEntity);
            form.setCaseId(caseId);
            //新建人员信息
            SerialEntity serialEntity = bazxService.querySerialById(form.getBazxSerialId());
            PersonEntity personEntity = bazxService.getPersonById(serialEntity.getPersonId());
            personEntity.setId(null);
            personEntity.setAreaId(form.getAreaId());
            personEntity.setCaseId(caseId);
            personService.insert(personEntity);
//            serialEntity.setId(null);
//            serialEntity.setPersonId(personEntity.getId());
//            serialEntity.setAreaId(form.getAreaId());
//            serialEntity.setSerialNo("XYR" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
//            Integer serialId = serialService.insert(serialEntity);
            form.setSerialId(personEntity.getId());
        }
        if ("zfba".equals(form.getXzType())) {
            Map<String, Object> ajxx = zfbaService.queryAjxxById(form.getZfbaAjId());
            //新建案件信息
            CaseEntity caseEntity = new CaseEntity();
            caseEntity.setAreaId(form.getAreaId());
            caseEntity.setAjmc(ajxx.get("ajmc") + "");
            caseEntity.setAjbh(ajxx.get("ajbh") + "");
            caseEntity.setAfdd(ajxx.get("faddxz") + "");
            caseEntity.setAjlx(Integer.valueOf(ajxx.get("ajlx") + ""));
            caseEntity.setZbdwId(ControllerTool.getCurrentOrgID(request));
            caseEntity.setCreatedTime(new Date());
            caseEntity.setZbmjPid(ControllerTool.getUserID(request) + "");
            caseEntity.setUuid(UUID.randomUUID().toString());
            caseEntity.setAreaId(ControllerTool.getCurrentArea(request).getId());
            Integer caseId = caseService.insertCase(caseEntity);
            form.setCaseId(caseId);
            //新建人员信息
            Map<String, Object> yyxx = zfbaService.queryYyxxById(form.getZfbaYyId());
            PersonEntity personEntity = new PersonEntity();
            personEntity.setName(yyxx.get("ryxm") + "");
            personEntity.setSex(Integer.valueOf(yyxx.get("xb") + ""));
            personEntity.setCertificateTypeId(111);
            personEntity.setCertificateNo(yyxx.get("zjhm") + "");
            personEntity.setAreaId(form.getAreaId());
            personEntity.setCreatedTime(new Date());
            personEntity.setOpUserId(ControllerTool.getUserID(request));
            personEntity.setIsArrive(1);
            personEntity.setCaseId(caseId);
            personService.insert(personEntity);
            form.setSerialId(personEntity.getId());
        }
        BelongEntity entity = new BelongEntity();
        Boolean flag = false;
        Integer count = 0;
        try {
            for (int i = 0; i < belongJsonArr.size(); i++) {
                list.add((BelongEntity) JSONObject.toBean(belongJsonArr.getJSONObject(i), BelongEntity.class));
            }
            PersonEntity personEntity = personService.getPersonById(form.getSerialId());
            for (BelongEntity belongEntity : list) {
                count++;
                entity.setId(entity.getId());
                entity.setSerialId(form.getSerialId());
                entity.setRegisterUserId(ControllerTool.getUserID(request));
                entity.setRegisterTime(new Date());
                entity.setCaseId(form.getCaseId());
//                entity.setAreaId(form.getAreaId());
                entity.setAreaId(ControllerTool.getCurrentAreaID(request));
                entity.setLockerId(form.getLockerId());
                entity.setIsGet(0);
                entity.setGetWay("0");
                entity.setBelongingsId(form.getId());
                entity.setGetPerson(form.getGetPerson());
                entity.setGetTime(form.getGetTime());
                entity.setCreatedTime(new Date());
                entity.setUpdatedTime(new Date());
                entity.setDid(form.getDid());
                entity.setName(belongEntity.getName());
                entity.setDetailCount(belongEntity.getDetailCount());
                entity.setDescription(belongEntity.getDescription());
                entity.setUnit(belongEntity.getUnit());
                entity.setSaveMethod(form.getSaveMethod());
                entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                if (belongEntity.getWpUuid() == null) {
//                    entity.setWpUuid(UUID.randomUUID().toString());
                    entity.setWpUuid("WP" + format.format(new Date()) + count);
                    flag = true;//先关闭掉调用宽和接口
                } else {
//                    entity.setWpUuid(belongEntity.getWpUuid());
                    entity.setWpUuid("WP" + format.format(new Date()) + count);
                }
                int result = this.belongService.insertBelong(entity, request);
                if (result == 0) {// 判断柜子是否被占用，返回提示
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("失败").addContent("该柜已被占用,请选择其他柜!");
                }
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增随身物品(存柜)" + list, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.info("" + e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增随身物品(存柜)" + list, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!").addCallbackData(form.getSerialId());
    }

    /**
     * 查询嫌疑人现在的状态
     *
     * @param serialId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCurrentStatus")
    @ResponseBody
    public MessageEntity getCurrentStatus(@RequestParam long serialId, HttpServletRequest request) throws Exception {
        String result = "";
        SerialStatusTypeEntity obj = serialStatusService.checkStatus(serialId);
        boolean sec = obj.isSecurity();
        if (sec) {
            result = "已安检";
        } else {
            result = "未安检";
        }
        System.err.println("---------------" + result);
        return new MessageEntity().addIsError(false).addContent(result);
    }

    /**
     * 修改物品详情
     *
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/editBelongdet")
    @ResponseBody
    public MessageEntity editBelongdet(@RequestBody BelongForm form, HttpServletRequest request,
                                       HttpServletResponse response) {
        BelongEntity entity = new BelongEntity();
        entity.setId(form.getId());
        entity.setBelongingsId(form.getBelongingsId());
        entity.setName(form.getName());
        entity.setDetailCount(form.getDetailCount());
        entity.setDescription(form.getDescription());
        try {
            belongService.updateBelongdet(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改随身物品详情" + entity, ControllerTool.getLoginName(request), true, "办案场所");
            //调用宽和修改物品接口
//            BelongEntity belongEntity = belongService.getBelongdetById(form.getId());
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("userZH", ControllerTool.getLoginName(request));
//            jsonObject.put("mc", form.getName());
//            jsonObject.put("sl", form.getDetailCount());
//            jsonObject.put("tz", form.getDescription());
//            jsonObject.put("wbwpbh", belongEntity.getWpUuid());
//            jsonObject.put("bamjjh", ControllerTool.getLoginName(request));
//            jsonObject.put("czmjjh", ControllerTool.getLoginName(request));
//            jsonObject.put("glmjjh", ControllerTool.getLoginName(request));
//            String url = kh_base_url + "/sscw/sarjk/editWpJK";
//            String resultObj = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url, "", jsonObject.toString()));
//            logger.info("调用随身物品接口(" + url + ")，参数为：" + jsonObject.toString() + "，结果：" + resultObj);
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "修改随身物品详情" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("修改成功!");
    }

    /**
     * 随身物品存入时删除一条物品记录（存柜时）
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeBelongdet")
    @ResponseBody
    public MessageEntity removeBelongdet(HttpServletRequest request, Integer belongingsId, Integer detailId) {
        try {
//            BelongEntity belongEntity = belongService.getBelongdetById(detailId);
            // 删除一条物品记录，如果所有的物品记录都删除了则删除存柜记录
            belongService.deleteBelongdetAndBelong(belongingsId, detailId);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除随身物品详情" + detailId, ControllerTool.getLoginName(request), true, "办案场所");
            //对接宽和删除接口
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("userZH", ControllerTool.getLoginName(request));
//                jsonObject.put("wbwpbh", belongEntity.getWpUuid());
//                String url = kh_base_url + "/sscw/sarjk/removeJK";
//                String resultObj = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url, "", jsonObject.toString()));
//                logger.info("调用随身物品接口(" + url + ")，参数为：" + jsonObject.toString() + "，结果：" + resultObj);
//            } catch (Exception e) {
//                logger.info("调用随身物品接口" + e);
//            }
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "删除随身物品详情" + detailId, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功!");
    }


    @RequestMapping({"/outgetpicturenew"})
    @ResponseBody
    public MessageEntity outgetpicturenew(@RequestParam Map<String, Object> params1, HttpServletRequest request, String serialNo, String serialid, String dataImage, HttpServletResponse response)
            throws Exception {
        logger.info("========================图片上传 serialID=" + serialid);
        String serialID = serialid;
        try {
            logger.info("========================图片上传serialNo=" + serialNo);
            String path = PropertyUtil.getContextProperty("belongImageFileSavePath").toString() + serialNo + "/";

            logger.info("========================图片上传 path=" + path);
            FileUtil.createDir(path);
            String filename = "belong-" + serialNo + "-" + new Random().nextInt(10000) + "-yt.jpg";
            String spath = path + filename;
            String base64 = dataImage.substring(23);
            try {
                BASE64Decoder d = new BASE64Decoder();
                byte[] bs = d.decodeBuffer(base64);
                FileOutputStream os = new FileOutputStream(spath);
                os.write(bs);
                os.close();
                belongService.creatbelongphoto(serialID, spath, ControllerTool.getSessionInfo(request).getCurrentOrg().getPid(), ControllerTool.getSessionInfo(request).getUser().getId().toString());
                return new MessageEntity().addIsError(false).addContent("上传成功");
            } catch (IOException e) {
                logger.error("### 图片上传错误  ###", e);
                return new MessageEntity().addIsError(false).addContent("上传失败");
            }
        } catch (Exception e) {
            logger.error("### 图片上传错误  ###", e);
            return new MessageEntity().addIsError(false).addContent("上传失败");
        }
    }


    /**
     * 存物图片上传
     *
     * @param params1
     * @param request
     * @param serialid
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/outgetpicture")
    @ResponseBody
    public void outgetpicture(@RequestParam Map<String, Object> params1, HttpServletRequest request, String
            serialid, String serialNo,
                              HttpServletResponse response) throws Exception {
        try {
            // 图片开始
            String serialID = serialid;
//                SerialEntity entity = new SerialEntity();
//                entity.setId(Long.valueOf(serialid));
//                SerialEntity serial = serialService.getSerialByNo1(entity);
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            FileUploadForm form = new FileUploadForm();
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        if (!"".equals(myFileName.trim())) {
                            logger.info("========================图片上传错误serialNo=" + serialNo);
                            String path = PropertyUtil.getContextProperty("belongImageFileSavePath").toString()
                                    + serialNo + "/";
                            FileUtil.createDir(path);
                            String filename = "belong-" + serialNo + "-" + new Random().nextInt(10000) + "-yt.jpg";
                            String spath = path + filename;
                            File localFile = new File(path, filename);
                            file.transferTo(localFile);
                            logger.info("========================图片上传错误 create file=");
                            belongService.creatbelongphoto(serialID, spath, ControllerTool.getSessionInfo(request).getCurrentOrg().getPid(), ControllerTool.getSessionInfo(request).getUser().getId().toString());
                        }
                    }
                }
            } else {
                logger.info("========================图片上传错误 isNotMultipart=");
            }
            // 图片结束
        } catch (Exception e) {
            logger.error("### 图片上传错误  ###", e);
        }
    }

    /**
     * 根据柜门id查询柜门存储信息
     *
     * @param pageMap
     * @param request
     * @param response
     * @param lockerId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelongdetByLockerId")
    @ResponseBody
    public Map<String, Object> listAllBelongdetByLockerId
    (@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
     HttpServletResponse response, String lockerId) throws Exception {
        List<BelongEntity> list = new ArrayList<BelongEntity>();
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        map.put("lockerId", lockerId);
        map.put("flag", "Belong");
        list = belongService.listAllBelongdetByLockerId(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", list);
        return result;
    }

    /**
     * 通过lockId 查询 serial_id
     */
    @RequestMapping(value = "/getSerialIdByLockId")
    @ResponseBody
    public long getSerialIdByLockId(String lockId) {
        return belongService.getSerialIdByLockId(lockId);
    }

    /**
     * 随身物品开柜单保存(单个领取)----out----
     *
     * @param form
     * @param request
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/editBoxopenouts")
    @ResponseBody
    public MessageEntity editBoxopenouts(@RequestBody BelongForm form, HttpServletRequest request, int id,
                                         HttpServletResponse response) {
        int ss = id;
        BelongEntity entity = new BelongEntity();
        entity.setId(ss);
        entity.setBelongingsId(form.getBelongingsId());
        entity.setLockerId(form.getLockerId());
        entity.setIsGet(1);
        entity.setGetWay(form.getGetWay());
        entity.setGetPerson(form.getGetPerson());
        entity.setGetTime(new Date());
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
        try {
            belongService.updateBoxopenouts(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写单个领取开柜单" + entity, ControllerTool.getLoginName(request), true, "办案场所");

            //对接宽和领取接口
            BelongEntity belongEntity = belongService.getBelongdetById(ss);

            if ("5".equals(form.getGetWay())) {//移交
                if (form.getJsdwId() != null && belongEntity.getWpUuid() != null) {
//                对接并且更新接收单位
                    Map<String, Object> map = new HashMap<>();
                    map.put("wpUuid", belongEntity.getWpUuid());
                    List<BelongDetailTempEntity> listByTempId = belongDetailTempService.getListByTempId(map);
                    if (listByTempId.size() > 0) {
                        BelongTempEntity temEntity = new BelongTempEntity();
                        temEntity.setId(listByTempId.get(0).getTempId());
                        temEntity.setJsdwId(form.getJsdwId());
                        temEntity.setYjdwId(organizationService.getOrganizationById(ControllerTool.getSessionInfo(request).getCurrentOrg().getId()).getOrgCode());
                        belongTempService.updateTempYjyj(temEntity);
                    } else {
                        Long sid = belongService.selectSerialid(form.getBelongingsId());
                        PersonEntity personById = personService.getPersonById(sid);
                        BelongTempEntity temEntity = new BelongTempEntity();
//                            查询主表数据
                        List<BelongDetailTempEntity> entityById = belongDetailTempService.getByWpUuid(map);
                        Integer tempId = 0;
                        if (entityById.size() == 0) {
                            temEntity.setPersonId(sid);
                            temEntity.setBelongId(form.getBelongingsId());
                            temEntity.setCaseId(personById.getCaseId());
                            temEntity.setJsdwId(form.getJsdwId());
                            temEntity.setXm(personById.getName());
                            temEntity.setSfzh(personById.getCertificateNo());
                            temEntity.setYjdwId(organizationService.getOrganizationById(ControllerTool.getSessionInfo(request).getCurrentOrg().getId()).getOrgCode());
//                                temEntity.setYjjg("Y");
                            belongTempService.saveBelong(temEntity);
                            tempId = temEntity.getId();
                        } else {
                            tempId = entityById.get(0).getTempId();
                        }
                        BelongDetailTempEntity belongDetailTempEntity = new BelongDetailTempEntity();
                        belongDetailTempEntity.setTempId(tempId);
                        belongDetailTempEntity.setWpUuid(belongEntity.getWpUuid());
                        belongDetailTempEntity.setName(belongEntity.getName());
                        belongDetailTempEntity.setUnit(belongEntity.getUnit());
                        belongDetailTempEntity.setDescription(belongEntity.getDescription());
                        belongDetailTempEntity.setSaveMethod(belongEntity.getSaveMethod());
                        belongDetailTempEntity.setDetailCount(belongEntity.getDetailCount());
                        belongDetailTempService.saveBelongTemp(belongDetailTempEntity);
                        //移交后图片 插入到对应的表中
                        Map<String, Object> fileMap = new HashMap<>();
                        fileMap.put("areaId", 1);
                        Map<String, Object> serverUrl = belongService.selectImage(fileMap);
                        BelongingsPhotoTempEntity photoTempEntity = new BelongingsPhotoTempEntity();
                        if (!serverUrl.isEmpty()) {
                            List<BelongingsPhotoEntity> list = belongService.selectPhotoByBelongingsId(form.getBelongingsId());
                            for (BelongingsPhotoEntity photoEntity : list) {
                                photoTempEntity.setTempId(tempId);
                                photoTempEntity.setUrl(serverUrl.get("web_url") + "?path=" + photoEntity.getUrl());
                                belongTempService.insertBelongphotoTemp(photoTempEntity);
                            }
                        }
                    }

//                        try {
//                            JSONObject json = new JSONObject();
//                            json.put("userZH", ControllerTool.getLoginName(request));
//                            json.put("wbwpbh", belongEntity.getWpUuid());
//                            json.put("yjdwid", organizationService.getOrganizationById(ControllerTool.getSessionInfo(request).getCurrentOrg().getId()).getOrgCode());
//                            json.put("jsdwid", form.getJsdwId());
//                            String url1 = kh_base_url + "/sscwjk/yiJiao";
//                            String result = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url1, "", json.toString()));
//                            logger.info("随身物品移交数据接收接口(" + url1 + ")，参数为：" + json.toString() + "，结果：" + result);
//                        } catch (Exception e) {
//                            logger.info("随身物品移交数据接收接口" + e);
//                        }
                }
            } else if ("4".equals(form.getGetWay())) {//转涉案财物
//                    try {
//                        JSONObject json = new JSONObject();
//                        json.put("userZH", ControllerTool.getLoginName(request));
//                        json.put("wbwpbh", belongEntity.getWpUuid());
//                        String url1 = kh_base_url + "/sscwjk/zhuanSACW";
//                        String result = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url1, "", json.toString()));
//                        logger.info("随身物品转涉案财物数据接收接口(" + url1 + ")，参数为：" + json.toString() + "，结果：" + result);
//                    } catch (Exception e) {
//                        logger.info("随身物品转涉案财物数据接收接口" + e);
//                    }
            } else {
//                    try {
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("userZH", ControllerTool.getLoginName(request));
//                        jsonObject.put("wbwpbh", belongEntity.getWpUuid());
//                        jsonObject.put("lhrxm", form.getGetPerson());
//                        String url = kh_base_url + "/sscwjk/lingHui";
//                        String resultObj = HttpClientUtil.getAllInfoFromHTTPResponse(HttpClientUtil.post(url, "", jsonObject.toString()));
//                        logger.info("调用随身物品接口(" + url + ")，参数为：" + jsonObject.toString() + "，结果：" + resultObj);
//                    } catch (Exception e) {
//                        logger.info("调用随身物品接口" + e);
//                    }
            }
        } catch (Exception e) {
            logger.info("错误日志：" + e.getMessage());
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写单个领取开柜单" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("领取失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("领取成功!");
    }

    /**
     * 打开储物柜
     *
     * @param lockid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/belongsavebox")
    @ResponseBody
    public MessageEntity belongsavebox(@RequestParam long lockid, HttpServletRequest request) throws Exception {
        SessionInfo session = ControllerTool.getSessionInfo(request);
        try {
            openBoxOne(lockid, session, request);
            logger.info("### open belong cabinet 开柜成功 ### ");
        } catch (Exception e) {
            logger.error("", e);
            return new MessageEntity().addIsError(true).addContent(e.getMessage());
        }
        return new MessageEntity().addIsError(false).addContent("开柜成功");
    }

    /**
     * 查询开柜配置并连接打开柜门
     *
     * @param lockerId
     * @param session
     * @throws Exception
     */
    private void openBoxOne(Long lockerId, SessionInfo session, HttpServletRequest request) throws Exception {
        System.err.println("-----id--------" + ControllerTool.getCurrentAreaID(request));
        CabinetConfigEntity cabinetConfig = cabinetConfigDetailService.listInParamDetailByOutParamId(lockerId);
        String lockNo = cabinetConfigDetailService.queryBoxNumberById(lockerId.intValue());
        String boxip = cabinetConfig.getIp();
        int boxport = Integer.valueOf(cabinetConfig.getPort());
        String boxgroup = cabinetConfig.getGroup();
        String openips = cabinetConfig.getOpenIp();
        logger.info("### open belong cabinet ### " + lockNo + "," + boxip + "," + boxgroup + "," + boxport + "[client:"
                + session.getClientIP() + "]" + "，ips:[" + openips + "].");
        if (openips.indexOf(session.getClientIP()) < 0) {
            logger.info("user ip:" + session.getClientIP());
            logger.info("openips:" + openips);
            throw new Exception("您的IP不可以开柜！");
        }
        //记录开柜操作
        try {
            OpenCabinetEntity openCabinetEntity = new OpenCabinetEntity();
            openCabinetEntity.setId(openCabinetEntity.getId());
            openCabinetEntity.setCreatedTime(new Date());
            openCabinetEntity.setLockerId(lockerId.toString());
            openCabinetEntity.setAreaId(session.getCurrentArea().getId());
            openCabinetEntity.setOpener(session.getUser().getRealName());
            openCabinetEntity.setOpPid(session.getCurrentOrg().getPid());
            openCabinetEntity.setOpUserId(session.getUser().getId());
            belongService.insertOpenboxData(openCabinetEntity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
//            if (ControllerTool.getCurrentAreaID(request) == 1) {
        logger.info("### open belong cabinet11111 ### " + lockNo + "," + boxip + "," + boxgroup + "," + boxport + "[client:"
                + session.getClientIP() + "]" + "，ips:[" + openips + "].");
        CabinetUtilZhiXin util = new CabinetUtilZhiXin();
        try {
            util.openBoxOne(boxgroup, lockNo, boxip, boxport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception("连接柜子失败！");
        }
//            } else {
            /*CabinetUtilZhiXin util = new CabinetUtilZhiXin();
            try {
                util.openBoxOne(boxgroup, lockNo, boxip, boxport);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new Exception("连接柜子失败！");
            }*/
//            }
    }

    /**
     * 随身物品开柜单保存----out---- 全部提取
     *
     * @param form
     * @param request
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/editBoxopenout")
    @ResponseBody
    public MessageEntity editBoxopenout(@RequestBody BelongForm form, HttpServletRequest request, Long id,
                                        HttpServletResponse response) {
        logger.info("++++++++edit++++++BelongForm=" + form);
        BelongEntity entity = new BelongEntity();
        Long ss = id;
        entity.setSerialId(ss);
        entity.setId(form.getId());
        entity.setIsGet(1);
        entity.setGetWay(form.getGetWay());
        entity.setGetPerson(form.getGetPerson());
        entity.setGetTime(new Date());
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
        entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
        entity.setJsdwId(form.getJsdwId());
        try {
            belongService.updateBoxopenout(entity, request);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写全部领取开柜单" + entity, ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.error("Error on edit updateArea!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "填写全部领取开柜单" + entity, ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("提取失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("提取成功!");
    }

    /**
     * 取物开柜
     *
     * @param lockid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/boxoutopen")
    @ResponseBody
    public MessageEntity boxoutopen(@RequestParam long lockid, HttpServletRequest request) throws Exception {
        SessionInfo session = ControllerTool.getSessionInfo(request);
        try {
            openBoxOne(lockid, session, request);
            logger.info("### open belong cabinet 开柜成功 ### ");
        } catch (Exception e) {
            logger.error("开柜异常：", e);
            return new MessageEntity().addIsError(true).addContent(e.getMessage());
        }
        return new MessageEntity().addIsError(false).addContent("开柜成功");
    }

    /**
     * 查询嫌疑人存物历史
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelongdet")
    @ResponseBody
    public Map<String, Object> listAllBelongdet(@RequestParam Map<String, Object> pageMap, HttpServletRequest
            request,
                                                HttpServletResponse response) throws Exception {
        if (pageMap.get("enpId") == null) {
            return null;
        }
        Map<String, Object> map2 = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map2.put("dataAuth", " ( d.get_person=" + ControllerTool.getUserID(request)
                    + " or d.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map2.put("dataAuth", "  ( d.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map2.put("dataAuth", "  ( d.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map2.put("dataAuth", "  ( d.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")");
        }
        List<BelongEntity> list = this.belongService.listAllBelongdet(map2);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.belongService.count1(map2));
        result.put("rows", list);
        return result;
    }

    /**
     * 选择空闲的随身储物柜
     *
     * @param params
     * @param request
     * @param response
     * @throws Exception
     * @returnlistAllBelong
     */
    @RequestMapping(value = "/listunUsedbox")
    @ResponseBody
    public List<ComboboxEntity> listunUsedbox(@RequestParam Map<String, Object> params, HttpServletRequest
            request,
                                              HttpServletResponse response) throws Exception {
        String areaid = request.getParameter("areaid");
        params.put("areaid", areaid);
        params.put("type", 1);
        return this.belongService.listunUsedbox(params);
    }

    /**
     * 随身物品管理 list
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelong")
    @ResponseBody
    public Map<String, Object> listAllBelong(@RequestParam Map<String, Object> pageMap, HttpServletRequest
            request,
                                             HttpServletResponse response) throws Exception {
        List<BelongEntity> list = new ArrayList<BelongEntity>();
        int count = 0;
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        map.put("flag", "Belong");
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( b.register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.send_user_id=" + ControllerTool.getUserID(request)
                    + " or dl.op_user_id=" + ControllerTool.getUserID(request)
                    + " or ba.cjr=" + ControllerTool.getUserID(request)
                    + " or ba.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',ba.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ( b.area_id = " + ControllerTool.getCurrentAreaID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ( b.area_id  " + sessionInfo.getCurrentAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ( b.area_id  " + sessionInfo.getSuperAndSubAreaInStr()
                    + " or s.area_id " + sessionInfo.getSuperAndSubAreaInStr()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", "  ( b.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or dl.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", "  ( b.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or dl.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ba.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", "  ( b.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or s.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or dl.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ba.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + ")");
        }
        list = this.belongService.listAllBelong(map);
        count = this.belongService.count(map);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", list);
        return result;
    }

    /**
     * 查询随身物品开柜记录
     *
     * @param pageMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listAllBelongcod")
    @ResponseBody
    public Map<String, Object> listAllBelongcod(@RequestParam Map<String, Object> pageMap, HttpServletRequest
            request,
                                                HttpServletResponse response) throws Exception {
        Map<String, Object> map3 = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map3.put("dataAuth", " ( bcl.opener=" + ControllerTool.getUserID(request)
                    + " or pcl.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map3.put("dataAuth", " bcl.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map3.put("dataAuth", " bcl.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map3.put("dataAuth", " bcl.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map3.put("dataAuth", " ( bcl.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map3.put("dataAuth", " ( bcl.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map3.put("dataAuth", " ( bcl.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<BelongEntity> list = this.belongService.queryBelongingsCabinetLlog(map3);
        List<BelongEntity> resultList = new ArrayList<BelongEntity>();
        for (BelongEntity belongEntity : list) {
            BelongEntity resultEntity = new BelongEntity();
            resultEntity.setLogId(belongEntity.getLogId());
            resultEntity.setLogBelongId(belongEntity.getLogBelongId());
            resultEntity.setLogBelongLockerId(belongEntity.getLogBelongLockerId());
            resultEntity.setLogBelongOpener(ControllerTool.getSessionInfo(request).getUser().getRealName());
            resultEntity.setLogBelongReason(belongEntity.getLogBelongReason());
            resultEntity.setLogCreatedTime(belongEntity.getLogCreatedTime());
            resultList.add(resultEntity);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", this.belongService.countBelongingsCabinetLog(map3));
        result.put("rows", resultList);
        return result;
    }

    /**
     * 获取图片
     */
    @RequestMapping(value = "/getImages")
    @ResponseBody
    public List<String> getImages(HttpServletRequest request, Integer belongingsId) {
        //根据belongingsId,获取照片集合
        List<BelongingsPhotoEntity> photos = belongService.selectPhotoByBelongingsId(belongingsId);
        List<String> result = new ArrayList<String>();
        if (photos != null && photos.size() > 0) {
            for (BelongingsPhotoEntity photo : photos) {
                if (photo.getWebUrl() != null) {
                    result.add(photo.getWebUrl() + "?path=" + photo.getUrl());
                } else {
                    result.add("http://127.0.0.1:8081/sscw/zhfz/bacs/iriscollection/imageshow.do?path=" + photo.getUrl());
                }
            }
        }
        return result;
    }

    /**
     * 获取图片
     */
    @RequestMapping(value = "/getImagesTemp")
    @ResponseBody
    public List<BelongingsPhotoTempEntity> getImagesTemp(HttpServletRequest request, Integer tempId) {
        //根据belongingsId,获取照片集合
        List<BelongingsPhotoTempEntity> photos = belongService.selectPhotoByTempId(tempId);
        return photos;
    }

    /**
     * 查询开柜记录
     */
    @RequestMapping(value = "/openBoxLog")
    @ResponseBody
    public Map<String, Object> openBoxLog(HttpServletRequest request, @RequestParam Map<String, Object> pageMap) {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( pcl.opener=" + ControllerTool.getUserID(request)
                    + " or pcl.op_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " pcl.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " pcl.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " pcl.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( pcl.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( pcl.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( pcl.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        result.put("rows", openCabinetService.list(map));
        result.put("total", openCabinetService.count(map));
        return result;
    }

    /**
     * app存物提交
     *
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/belongsaveApp")
    @ResponseBody
    public MessageEntity belongsaveApp(BelongAppForm form, HttpServletRequest request,
                                       HttpServletResponse response) {
        BelongEntity entity = new BelongEntity();
        try {
            serialService.updateStatusById((long) form.getSerialId(), 2);
            for (int i = 0; i < form.getList().size(); i++) {
                entity.setSerialId((long) form.getSerialId());
                entity.setRegisterUserId(ControllerTool.getUserID(request));
                entity.setRegisterTime(new Date());
                entity.setCaseId(form.getCaseId());
                entity.setAreaId(form.getAreaId());
                entity.setLockerId(form.getLockerId().toString());
                entity.setIsGet(0);
                entity.setGetWay("0");
                entity.setCreatedTime(new Date());
                entity.setUpdatedTime(new Date());
                entity.setName(form.getList().get(i).getName());
                entity.setDetailCount(form.getList().get(i).getDetailCount());
                entity.setDescription(form.getList().get(i).getDescription());
                entity.setUnit(form.getList().get(i).getUnit());
                entity.setSaveMethod(form.getList().get(i).getSaveMethod());
                entity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                entity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                int result = this.belongService.insertBelong(entity, request);
                if (result == 0) {// 判断柜子是否被占用，返回提示
                    return new MessageEntity().addCode(1).addIsError(true).addTitle("失败").addContent("该柜已被占用,请选择其他柜!");
                }
            }
            String serialID = form.getSerialId().toString();
            SerialEntity seentity = new SerialEntity();
            entity.setId(form.getSerialId());
            seentity.setId(form.getSerialId().longValue());
            SerialEntity serial = serialService.getSerialByNo1(seentity);
            if (form.getFiles() != null && form.getFiles().length > 0) {
                //循环获取file数组中得文件
                for (int i = 0; i < form.getFiles().length; i++) {
                    FileUploadForm form1 = new FileUploadForm();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    form1.setSysType("ba");
                    form1.setFileType("WP");
                    form1.setUuid(serial.getUuid());
                    form1.setSysId(form.getAreaId().toString());
                    form1.setFile(form.getFiles()[i]);
                    String filename = "belong-" + serial.getSerialNo() + "-" + new Random().nextInt(10000) + "-yt.jpg";
                    form1.setFileName(filename);
                    fileConfigService.upload(form1);
                    belongService.creatbelongphoto(serialID, filename, ControllerTool.getSessionInfo(request).getCurrentOrg().getPid(), ControllerTool.getSessionInfo(request).getUser().getId().toString());
                }
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增随身物品(存柜)" + form.getList(), ControllerTool.getLoginName(request), true, "办案场所");
        } catch (Exception e) {
            logger.info("" + e);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "新增随身物品(存柜)" + form.getList(), ControllerTool.getLoginName(request), false, "办案场所");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("添加成功!");
    }

    @RequestMapping(value = "/selectBelonginfo")
    @ResponseBody
    public BelongEntity addBelongcod22(@RequestBody BelongForm form, HttpServletRequest request) {
        logger.info("++++++++add++++++addBelong=" + form);
        Long ss = form.getSerialId();
        Integer belongingsId = form.getBelongingsId();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("serialId", ss);
        param.put("belongingsId", belongingsId);
        return belongService.selectBelonginfo(param);
    }

    @RequestMapping(value = "/addBelongcodNew")
    @ResponseBody
    public List<BelongEntity> addBelongcodNew(@RequestBody BelongForm form, HttpServletRequest request) {
        List<BelongEntity> obj = new ArrayList<>();
        Long ss = form.getSerialId();
        Integer belongingsId = form.getBelongingsId();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("serialId", ss);
        param.put("belongingsId", belongingsId);
        obj = belongService.addBelongcodNew(param);
        return obj;
    }

    public static void main(String[] args) {
        String a = "1635916807";
        String b = "1635916807";
        System.err.println(Integer.parseInt(a));
        System.err.println(Integer.parseInt(b));
    }


    @RequestMapping(value = "/onlineupload", method = RequestMethod.POST)
    @ResponseBody
    public String onlineupload(@RequestParam("onlinefilename") MultipartFile file, @RequestParam("belongId") Integer belongId, @RequestParam("type") Integer type, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String myFileName = file.getOriginalFilename();
        String status = "success";
        try {
            String path = PropertyUtil.getContextProperty("sswpVideoFileSavePath").toString() + "/";
            FileUtil.createDir(path);
            String filename = "sswp-" + System.currentTimeMillis() + "-" + new Random().nextInt(10000) + "-" + (type == 1 ? "cr" : "lq") + ".mp4";
            String spath = path + filename;
            logger.info("========================图片上传错误 spath=" + spath);
            File localFile = new File(path, filename);
            file.transferTo(localFile);

            //存入数据库
            String sswpVideoNginxPath = PropertyUtil.getContextProperty("sswpVideoNginxPath").toString();
            BelongVideoEntity belongVideoEntity = new BelongVideoEntity();
            belongVideoEntity.setBelongId(belongId);
            belongVideoEntity.setCreatedUser(ControllerTool.getUserID(request));
            belongVideoEntity.setType(type);
            belongVideoEntity.setUrl(sswpVideoNginxPath + filename);
            belongVideoService.insert(belongVideoEntity);
            status = "success";
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("随身财物视频上传异常" + e);
            status = "error";
        }
        if (file == null) {
            status = "error";
        }
        return status;
    }

    @RequestMapping(value = "/listBelongVideo")
    @ResponseBody
    public Map<String, Object> listBelongVideo(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", this.belongVideoService.list(map));
        result.put("total", this.belongVideoService.count(map));
        return result;
    }

    @RequestMapping(value = "/removeBelongVideo")
    @ResponseBody
    public MessageEntity removeBelongVideo(@RequestBody IDForm form, HttpServletRequest request) {
        try {
            belongVideoService.delete(form.getLongID());
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("通知").addContent("删除成功!");
    }
}