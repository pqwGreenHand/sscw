package com.zhixin.zhfz.bacs.controller.policeEntrance;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.cuff.ICuffLogService;
import com.zhixin.zhfz.bacs.services.cuff.ICuffService;
import com.zhixin.zhfz.bacs.services.downloadtask.IDownloadTaskService;
import com.zhixin.zhfz.bacs.services.personalconfig.IPersonalConfigDetailService;
import com.zhixin.zhfz.bacs.services.policeEntrance.IPoliceEntranceService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.track.ITrackService;
import com.zhixin.zhfz.common.common.HttpClientUtil;
import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.services.Icase.CasePoliceService;
import com.zhixin.zhfz.common.services.Icase.ICaseService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
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
import java.util.*;

/*
 * 民警入区
 * */
@Controller
@RequestMapping("/zhfz/bacs/policeEntrance")
public class PoliceEntranceController {
    private static Logger logger = LoggerFactory.getLogger(PoliceEntranceController.class);

    @Autowired
    private IPoliceEntranceService policeEntranceService;


    @Autowired
    private ICaseService caseService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ICuffService cuffService;

    @Autowired
    private ICuffLogService cuffLogService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITrackService trackService;

    @Autowired
    private IDownloadTaskService taskService;

    @Autowired
    private CasePoliceService casePoliceService;

    @Autowired
    private IPersonalConfigDetailService personalConfigDetailService;

    @Autowired
    private ISerialService serialService;

    // 首页快捷添加民警
    @RequestMapping(value = "/mainAddPolice")
    @ResponseBody
    public MessageEntity mainAddPolice(Long policeId, Integer cuffId, Integer caseId, Integer areaId, Integer policeType, String cuffNo, HttpServletRequest request) {
        try {
            PoliceEntranceEntity policeEntrance = new PoliceEntranceEntity();
            policeEntrance.setCaseId(caseId);
            policeEntrance.setCuffId(cuffId);
            policeEntrance.setPoliceId(policeId);
            policeEntrance.setAreaId(areaId);
            policeEntrance.setPoliceType(policeType);
            policeEntrance.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
            policeEntrance.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
            // 调用致昕DLS定位
            this.cuffService.otherPoliceBindCuff(policeEntrance);
            // 民警入区
            this.policeEntranceService.addPoliceEntrance(policeEntrance);


            //插入即时通讯数据库数据
            String flag = PropertyUtil.getContextProperty("insert.oraytalk.database").toString();
            int databaseSouce = Integer.parseInt(PropertyUtil.getContextProperty("database.source.id").toString());
            int addValue = Integer.parseInt(PropertyUtil.getContextProperty("base.database.id.add").toString());
            if ("1".equals(flag)) {
                logger.error("insert.oraytalk.database start");
                try {
                    PoliceEntranceEntity policeEntranceEntity = new PoliceEntranceEntity();
                    policeEntranceEntity.setPoliceType(policeEntrance.getPoliceType());
                    policeEntranceEntity.setCaseId(policeEntrance.getCaseId() + addValue);
                    policeEntranceEntity.setAreaId(policeEntrance.getAreaId() + addValue);
                    policeEntranceEntity.setPoliceId(policeEntrance.getPoliceId() + addValue);
                    policeEntranceEntity.setCuffId(policeEntrance.getCuffId() + addValue);
                    policeEntranceEntity.setDatabaseSource(databaseSouce);
                    policeEntranceEntity.setTableId(policeEntrance.getId().longValue());
                    policeEntranceEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                    policeEntranceEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
//                    policeEntranceOrayTalkService.addPoliceEntrance(policeEntranceEntity);
                } catch (Exception ex) {
                    logger.error("insert.oraytalk.database=", ex);
                }
                logger.error("insert.oraytalk.database end");
            }

            // 更新民警卡片状态为已绑定
//			CuffEntity cuffEntity = new CuffEntity();
//			cuffEntity.setId(policeEntrance.getCuffId());
//			cuffEntity.setStatus(1);
//			cuffEntity.setUserId(policeEntrance.getPoliceId());
            //this.cuffService.update(cuffEntity);

            // 添加卡片绑定日志
            this.cuffLogService.insertLog(policeEntrance.getCuffId(), CuffLogEntity.BINGDING_TYPE, "民警卡片" + policeEntrance.getCuffId() + "绑定成功！");
            // 根据案件id查询案件的xbmj_ids
            CaseEntity xbmj_entity = this.caseService.getCaseInfoById(Integer.valueOf(caseId));
            // 如果当前民警为协办民警，则更新案件表的xbmj_ids字段，同时将该协办民警添加到协办民警表
            if ("1".equals(policeEntrance.getPoliceType())) {
                CaseEntity ce = new CaseEntity();
                if (xbmj_entity != null) {
                    if (xbmj_entity.getXbmjIds() != "") {
                        ce.setXbmjIds(xbmj_entity.getXbmjIds() + policeEntrance.getPoliceId() + ",");
                    } else {
                        ce.setXbmjIds(xbmj_entity.getXbmjIds() + "," + policeEntrance.getPoliceId() + ",");
                    }
                    // 更新案件表的xbmj_ids字段
                    this.caseService.updateCase(ce);
                }
                // 判断协办民警表是否存在该协办民警；如果不存在，则添加协办民警
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("caseId", policeEntrance.getCaseId());
                params.put("policeId", policeEntrance.getPoliceId());
                CasePoliceEntity casePoliceEntity = this.casePoliceService.selectCasePoliceByPolice(params);
                if (casePoliceEntity == null) {
                    CasePoliceEntity policeEntity = new CasePoliceEntity();
                    policeEntity.setCaseId(policeEntrance.getCaseId());
                    policeEntity.setXbmjId(policeEntrance.getPoliceId().intValue());
                    policeEntity.setCreatedTime(new Date());
                    policeEntity.setUpdatedTime(new Date());
                    this.casePoliceService.inserPolice(policeEntity);
                }
            }

            try {
                //调用天地伟业卡片启用
                tiandyUpdateCuffStatus(cuffNo, 1, request, areaId);
            } catch (Exception e) {
                logger.info("调用天地伟业激活系统错误：", e);
                this.operLogService.insertLog("民警入区登记", "调用天地伟业激活系统错误：" + e,
                        ControllerTool.getSessionInfo(request).getUser().getRealName(), false, OperLogEntity.SYSTEM_BACS);
            }


        } catch (Exception e) {
            logger.error("民警添加失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("民警添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("民警添加成功!");
    }

    // 单独添加民警卡片
    @RequestMapping(value = "/addPolice")
    @ResponseBody
    public MessageEntity addPolice(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        try {
            PoliceEntranceEntity policeEntrance = new PoliceEntranceEntity();
            policeEntrance.setPoliceId(Long.valueOf(param.get("policeId")+""));
            policeEntrance.setCuffNo(param.get("cuffNo")+"");
            policeEntrance.setCuffId(Integer.valueOf(param.get("cuffId")+""));
            policeEntrance.setPoliceNo(param.get("policeNo")+"");
            policeEntrance.setOpPid(param.get("opPid")+"");
            policeEntrance.setOpUserId(Integer.valueOf(param.get("opUserId")+""));
            policeEntrance.setOrderRequestId(Integer.valueOf(param.get("orderRequestId")+""));
            policeEntrance.setPoliceType(Integer.valueOf(param.get("policeType")+""));
            policeEntrance.setAreaId(Integer.valueOf(param.get("areaId")+""));
            // 添加民警信息
            PoliceEntranceEntity pe = this.policeEntranceService.findPoliceEntranceByPoliceId(policeEntrance.getPoliceId().intValue());
            if (pe == null) {
                // 调用致昕DLS定位
                this.cuffService.otherPoliceBindCuff(policeEntrance);
                // 民警入区
                this.policeEntranceService.addPoliceEntrance(policeEntrance);
                // 添加卡片绑定日志
                this.cuffLogService.insertLog(policeEntrance.getCuffId(), CuffLogEntity.BINGDING_TYPE, "民警卡片" + policeEntrance.getCuffNo() + "绑定成功！");

                try {
                    //调用天地伟业卡片启用
                    tiandyUpdateCuffStatus(policeEntrance.getCuffNo(), 1, request, 1);
                } catch (Exception e) {
                    logger.info("调用天地伟业激活系统错误：", e);
                    this.operLogService.insertLog("民警入区登记", "调用天地伟业激活系统错误：" + e,
                            ControllerTool.getSessionInfo(request).getUser().getRealName(), false, OperLogEntity.SYSTEM_BACS);
                }

            }
        } catch (Exception e) {
            logger.error("民警入区信息添加失败!", e);
            return new MessageEntity().addCode(0).addIsError(true).addTitle("错误").addContent("入区失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("入区成功!");
    }

    // 添加案件信息和民警入区信息
    @RequestMapping(value = "/addPoliceEntrance")
    @ResponseBody
    public MessageEntity addPoliceEntrance(@RequestBody Map<String, Object> arr, HttpServletRequest request) {
        try {
            //AreaEntity areaEntity = ControllerTool.getSessionInfo(request).getCurrentArea();
            List<PoliceEntranceEntity> list = new ArrayList<PoliceEntranceEntity>();
            System.out.println("----------------eeeeeeeeeeeeeeee" + arr);
            JSONArray jsonArr = JSONArray.fromObject(arr);
            JSONObject jsonObj = jsonArr.getJSONObject(0);
            // 民警信息
            JSONArray policeJsonArr = JSONArray.fromObject(jsonObj.get("list"));
            // 案件信息
            JSONArray caseJsonArr = JSONArray.fromObject(jsonObj.get("case"));
            CaseEntity caseEntity = (CaseEntity) JSONObject.toBean(caseJsonArr.getJSONObject(0), CaseEntity.class);
            // 民警信息放入list
            for (int i = 0; i < policeJsonArr.size(); i++) {
                list.add((PoliceEntranceEntity) JSONObject.toBean(policeJsonArr.getJSONObject(i), PoliceEntranceEntity.class));
            }
            // 更新案件信息
            //this.caseService.updateCase(caseEntity);
            // 根据案件id查询案件的xbmj_ids
            //CaseEntity xbmj_entity = this.caseService.getCaseInfoById(caseEntity.getId());
            // 添加民警信息
            for (PoliceEntranceEntity policeEntrance : list) {
                PoliceEntranceEntity pe = this.policeEntranceService.findPoliceEntranceByPoliceId(policeEntrance.getPoliceId().intValue());
				/*if(pe != null){
					logger.error("警号为"+pe.getPoliceNo()+",该民警还未出区!");
					return new MessageEntity().addCode(0).addIsError(true).addTitle("错误").addContent("警号为"+pe.getPoliceNo()+",该民警还未出区!");
				}*/
                if (pe == null) {
                    policeEntrance.setAreaId(caseEntity.getAreaId());
                    policeEntrance.setOrderRequestId(caseEntity.getOrderRequestId());
                    policeEntrance.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                    policeEntrance.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
                    // 调用致昕DLS定位
                    this.cuffService.otherPoliceBindCuff(policeEntrance);
                    // 民警入区
                    this.policeEntranceService.addPoliceEntrance(policeEntrance);


                    //插入即时通讯数据库数据
                    String flag = PropertyUtil.getContextProperty("insert.oraytalk.database").toString();
                    int databaseSouce = Integer.parseInt(PropertyUtil.getContextProperty("database.source.id").toString());
                    Integer addValue = Integer.parseInt(PropertyUtil.getContextProperty("base.database.id.add").toString());
                    if ("1".equals(flag)) {
                        logger.error("insert.oraytalk.database start");
                        try {
                            PoliceEntranceEntity policeEntranceEntity = new PoliceEntranceEntity();
                            policeEntranceEntity.setPoliceType(policeEntrance.getPoliceType());
                            policeEntranceEntity.setCaseId(policeEntrance.getCaseId().intValue() + addValue);
                            policeEntranceEntity.setAreaId(policeEntrance.getAreaId().intValue() + addValue);
                            policeEntranceEntity.setPoliceId(policeEntrance.getPoliceId() + addValue);
                            policeEntranceEntity.setCuffId(policeEntrance.getCuffId() + addValue);
                            policeEntranceEntity.setDatabaseSource(databaseSouce);
                            policeEntranceEntity.setTableId(policeEntrance.getId().longValue());
                            policeEntranceEntity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
                            policeEntranceEntity.setOpUserId(ControllerTool.getSessionInfo(request).getUser().getId());
//                            policeEntranceOrayTalkService.addPoliceEntrance(policeEntranceEntity);
                        } catch (Exception ex) {
                            logger.error("insert.oraytalk.database=", ex);
                        }
                        logger.error("insert.oraytalk.database end");
                    }

                    // 更新民警卡片状态为已绑定
//					CuffEntity cuffEntity = new CuffEntity();
//					cuffEntity.setId(policeEntrance.getCuffId());
//					cuffEntity.setStatus(1);
//					cuffEntity.setUserId(policeEntrance.getPoliceId());

                    // 添加卡片绑定日志
                    this.cuffLogService.insertLog(policeEntrance.getCuffId(), CuffLogEntity.BINGDING_TYPE, "民警卡片" + policeEntrance.getCuffNo() + "绑定成功！");
                    // 如果当前民警为协办民警，则更新案件表的xbmj_ids字段，同时将该协办民警添加到协办民警表
                    if ("1".equals(policeEntrance.getPoliceType())) {
						/*CaseEntity ce= new CaseEntity();
						if(xbmj_entity != null){
							if(xbmj_entity.getXbmjIds() != ""){
								ce.setXbmjIds(xbmj_entity.getXbmjIds() + policeEntrance.getPoliceId() + ",");
							}else {
								ce.setXbmjIds(xbmj_entity.getXbmjIds() + "," + policeEntrance.getPoliceId() + ",");
							}
							// 更新案件表的xbmj_ids字段
							this.caseService.updateCase(ce);
						}*/
                        // 判断协办民警表是否存在该协办民警；如果不存在，则添加协办民警
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("caseId", policeEntrance.getCaseId());
                        params.put("policeId", policeEntrance.getPoliceId());
                        CasePoliceEntity casePoliceEntity = this.casePoliceService.selectCasePoliceByPolice(params);
                        if (casePoliceEntity == null) {
                            CasePoliceEntity policeEntity = new CasePoliceEntity();
                            policeEntity.setCaseId(policeEntrance.getCaseId());
                            policeEntity.setXbmjId(policeEntrance.getPoliceId().intValue());
                            policeEntity.setCreatedTime(new Date());
                            policeEntity.setUpdatedTime(new Date());
                            this.casePoliceService.inserPolice(policeEntity);
                        }
                    }
                }

                try {
                    //调用天地伟业卡片启用
                    tiandyUpdateCuffStatus(policeEntrance.getCuffNo(), 1, request, caseEntity.getAreaId());
                } catch (Exception e) {
                    logger.info("调用天地伟业激活系统错误：", e);
                    this.operLogService.insertLog("民警入区登记", "调用天地伟业激活系统错误：" + e,
                            ControllerTool.getSessionInfo(request).getUser().getRealName(), false, OperLogEntity.SYSTEM_BACS);
                }

            }
        } catch (Exception e) {
            logger.error("民警入区信息添加失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("入区失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("入区成功!");
    }

    // 更新民警卡片状态
    @RequestMapping(value = "/updateCuffStatus")
    @ResponseBody
    public MessageEntity updateCuffStatus(Integer cuffId, HttpServletRequest request) {
        try {
            // 更新民警卡片状态为已绑定
            CuffEntity cuffEntity = new CuffEntity();
            cuffEntity.setId(cuffId);
            cuffEntity.setStatus(1);
            this.cuffService.update(cuffEntity);
        } catch (Exception e) {
            logger.error("民警卡片状态更新失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("民警卡片状态更新失败!");
        }
        return new MessageEntity().addCode(0).addIsError(false).addTitle("提示").addContent("民警卡片状态更新成功!");
    }

    // 民警出区
    @RequestMapping(value = "/updateOutTime")
    @ResponseBody
    public MessageEntity updateOutTime(Integer cuffId, Long policeId, String cuffNo, Integer areaId, HttpServletRequest request) throws Exception {
        // 获取areaId
        PoliceEntranceEntity pee = this.policeEntranceService.findPoliceEntranceByPoliceId(policeId.intValue());

        // 查询卡片状态
        CuffEntity cuffEntity = cuffService.getCuffById(cuffId);
        if (cuffEntity != null && cuffEntity.getStatus() == 0) {
            return new MessageEntity().addCode(0).addIsError(true).addTitle("提示").addContent("民警卡片未绑定!");
        }

        // 判断入区民警物品是否领取
        Map<String, Object> map = new HashMap<>();
        map.put("policeId", policeId);
        PoliceBelongEntity policeBelongEntity = this.policeEntranceService.selectSidnfo(map);
        if (policeBelongEntity != null) {
            return new MessageEntity().addCode(0).addIsError(true).addTitle("提示").addContent("民警物品未领取，不能出区!");
        }
        try {
            // 更新民警出区时间
            PoliceEntranceEntity policeEntranceEntity = new PoliceEntranceEntity();
            policeEntranceEntity.setCuffId(cuffId);
            policeEntranceEntity.setPoliceId(policeId);
            this.policeEntranceService.updateOutTime(policeEntranceEntity);


            //更新第三方数据库数据
            String flag = PropertyUtil.getContextProperty("insert.oraytalk.database").toString();
            long tableId = Long.parseLong(PropertyUtil.getContextProperty("base.database.id.add").toString());
            if ("1".equals(flag)) {
                logger.error("update.oraytalk.database start");
                try {
                    PoliceEntranceEntity policeEntranceEntity1 = new PoliceEntranceEntity();
                    policeEntranceEntity1.setCuffId(Integer.parseInt(tableId + "") + cuffId);
                    policeEntranceEntity1.setPoliceId(tableId + policeId);
//                    policeEntranceOrayTalkService.updateOutTime(policeEntranceEntity1);
                } catch (Exception ex) {
                    logger.error("update.oraytalk.database=", ex);
                }
                logger.error("update.oraytalk.database end");
            }

            // 解绑卡片,更新民警卡片状态为空闲
            this.cuffService.unbindCuffById(cuffId);

            // 添加卡片解绑日志
            this.cuffLogService.insertLog(policeEntranceEntity.getCuffId(), CuffLogEntity.UNBINGDING_TYPE, "民警卡片ID=" + cuffEntity.getCuffNo() + "解绑成功！");
            // 根据民警找到serial
            Map<String, Object> pcMap = new HashMap<>();
            map.put("policeId", policeId);
            map.put("cuffId", cuffId);
            List<SerialEntity> serials = this.policeEntranceService.getSerialByPolice(pcMap);
            if (serials != null && serials.size() > 0) {
                // 生成定位轨迹
                insertDownloadTack(serials.get(0).getId(), cuffNo);
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "民警出区" + policeEntranceEntity,
                    ControllerTool.getSessionInfo(request).getUser().getRealName(), true, "出入区管理");

            try {
                //调用天地伟业卡片禁用
                tiandyUpdateCuffStatus(cuffNo, 0, request, pee.getAreaId());
            } catch (Exception e) {
                logger.info("调用天地伟业禁用系统错误：", e);
                this.operLogService.insertLog("民警出区登记", "调用天地伟业禁用系统错误：" + e,
                        ControllerTool.getSessionInfo(request).getUser().getRealName(), false, OperLogEntity.SYSTEM_BACS);
            }

        } catch (Exception e) {
            logger.error("出区失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("出区失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("出区成功!");
    }


    @RequestMapping(value = "/querySerialBySendUserId")
    @ResponseBody
    public List<SerialEntity> querySerialBySendUserId(Long policeId, HttpServletRequest request) throws Exception {
        // 获取areaId
        PoliceEntranceEntity pee = this.policeEntranceService.findPoliceEntranceByPoliceId(policeId.intValue());
        Map<String, Object> map = new HashMap<>();
        map.put("policeId", policeId);
        map.put("areaId", pee.getAreaId());
        List<SerialEntity> list = serialService.querySerialBySendUserId(map);
        return list;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( t.op_user_id=" + ControllerTool.getUserID(request)
                    + " or t.cjr=" + ControllerTool.getUserID(request)
                    + " or t.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', t.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " t.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " t.area_id " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " t.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( t.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or t.opPid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or t.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( t.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or t.opPid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or t.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( t.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or t.opPid = " + sessionInfo.getCurrentOrgPid()
                    + " or t.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<PoliceEntranceEntity> list = new ArrayList<PoliceEntranceEntity>();
        int total = 0;
        list = this.policeEntranceService.list(map);
        total = this.policeEntranceService.count(map);
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/list2")
    @ResponseBody
    public Map<String, Object> list2(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( pe.op_user_id=" + ControllerTool.getUserID(request)
                    + " or ca.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or ca.cjr=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",', ca.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " pe.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " pe.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " pe.area_id " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( pe.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ca.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ca.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( pe.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ca.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ca.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( pe.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ca.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or ca.zbmj_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        List<PoliceEntranceEntity> list = new ArrayList<PoliceEntranceEntity>();
        int total = 0;
        list = this.policeEntranceService.list2(map);
        total = this.policeEntranceService.count2(map);
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    // 根据userId获取民警警号
    @RequestMapping(value = "/findPoliceNoById")
    @ResponseBody
    public MessageEntity findPoliceNoById(Integer userId, HttpServletRequest request) {
        UserEntity entity = new UserEntity();
        try {
            entity = userService.getUserByID(userId);
        } catch (Exception e) {
            logger.error("获取民警警号失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("获取民警警号失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("获取民警警号成功!").addCallbackData(entity);
    }

    @RequestMapping(value = "/policeList")
    @ResponseBody
    public Map<String, Object> policeList(@RequestParam Map<String, Object> pageMap, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = ControllerTool.mapFilter(pageMap);
        List<PoliceEntranceEntity> list = new ArrayList<PoliceEntranceEntity>();
        int total = 0;
        list = this.policeEntranceService.policeList(map);
        total = this.policeEntranceService.policeListCount(map);
        result.put("total", total);
        result.put("rows", list);
        return result;
    }

    // 便捷民警出区
    @RequestMapping(value = "/updateOutTime2")
    @ResponseBody
    public MessageEntity updateOutTime2(Integer cuffId, Long policeEntranceId, Long policeId, String cuffNo, Integer areaId, HttpServletRequest request) {
        try {
            // 查询卡片状态
            CuffEntity cuffEntity = cuffService.getCuffById(cuffId);
            if (cuffEntity != null && cuffEntity.getStatus() == 0) {
                return new MessageEntity().addCode(0).addIsError(true).addTitle("提示").addContent("民警卡片未绑定!");
            }
            // 判断入区民警物品是否领取
            Map<String, Object> map = new HashMap<>();
            map.put("policeId", policeId);
            PoliceBelongEntity policeBelongEntity = this.policeEntranceService.selectSidnfo(map);
            if (policeBelongEntity != null) {
                return new MessageEntity().addCode(0).addIsError(true).addTitle("提示").addContent("民警物品未领取，不能出区!");
            }
            // 更新民警出区时间
            PoliceEntranceEntity policeEntranceEntity = new PoliceEntranceEntity();
            policeEntranceEntity.setId(policeEntranceId);
            this.policeEntranceService.updateOutTime(policeEntranceEntity);


            //更新第三方数据库数据
            String flag = PropertyUtil.getContextProperty("insert.oraytalk.database").toString();
            long tableId = Long.parseLong(PropertyUtil.getContextProperty("base.database.id.add").toString());
            if ("1".equals(flag)) {
                logger.error("update.oraytalk.database start");
                try {
                    PoliceEntranceEntity policeEntranceEntity1 = new PoliceEntranceEntity();
                    policeEntranceEntity1.setCuffId(Integer.parseInt(tableId + "") + cuffId);
                    policeEntranceEntity1.setPoliceId(tableId + policeId);
//                    policeEntranceOrayTalkService.updateOutTime(policeEntranceEntity1);
                } catch (Exception ex) {
                    logger.error("update.oraytalk.database=", ex);
                }
                logger.error("update.oraytalk.database end");
            }

            // 解绑卡片,更新民警卡片状态为空闲
            this.cuffService.unbindCuffById(cuffId);
            // 添加卡片解绑日志
            this.cuffLogService.insertLog(cuffId, CuffLogEntity.UNBINGDING_TYPE, "民警卡片" + cuffId + "解绑成功！");
            // 根据民警找到serial
            Map<String, Object> pcMap = new HashMap<>();
            map.put("policeId", policeId);
            map.put("cuffId", cuffId);
            List<SerialEntity> serials = this.policeEntranceService.getSerialByPolice(pcMap);
            if (serials != null && serials.size() > 0) {
                // 生成定位轨迹
                insertDownloadTack(serials.get(0).getId(), cuffNo);
            }
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "民警出区" + policeEntranceEntity,
                    ControllerTool.getSessionInfo(request).getUser().getRealName(), true, "出入区管理");

            try {
                //调用天地伟业卡片禁用
                tiandyUpdateCuffStatus(cuffNo, 0, request, areaId);
            } catch (Exception e) {
                logger.info("调用天地伟业禁用系统错误：", e);
                this.operLogService.insertLog("民警出区登记", "调用天地伟业禁用系统错误：" + e,
                        ControllerTool.getSessionInfo(request).getUser().getRealName(), false, OperLogEntity.SYSTEM_BACS);
            }


        } catch (Exception e) {
            logger.error("出区失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("出区失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("提示").addContent("出区成功!");
    }

    // 获取在区民警
    @RequestMapping(value = "/getPoliceEnteance")
    @ResponseBody
    public List<PoliceEntranceEntity> getPoliceEnteance(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        List<PoliceEntranceEntity> list = new ArrayList<PoliceEntranceEntity>();


        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        map.put("areaId", ControllerTool.getSessionInfo(request).getCurrentArea().getId());
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( pe.op_user_id=" + ControllerTool.getUserID(request) + " or xu.op_user_id="
                    + ControllerTool.getUserID(request)
                    + " or xu.id = " + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " pe.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " pe.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " pe.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth", " ( pe.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or xu.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth", " ( pe.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or xu.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth", " ( pe.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " or xu.op_pid = " + sessionInfo.getCurrentOrgPid()
                    + " ) ");
        }
        try {
            list = this.policeEntranceService.getPoliceEnteance(map);
        } catch (Exception e) {
            logger.error("获取在区民警失败!", e);
            return new ArrayList<PoliceEntranceEntity>();
        }
        return list;
    }

    // 根据policeId获取民警入区信息
    @RequestMapping(value = "/findPoliceEntranceByPoliceId")
    @ResponseBody
    public MessageEntity findPoliceEntranceByPoliceId(Integer policeId, HttpServletRequest request) {
        PoliceEntranceEntity entity = new PoliceEntranceEntity();
        try {
            entity = this.policeEntranceService.findPoliceEntranceByPoliceId(policeId);
        } catch (Exception e) {
            logger.error("获取民警入区信息失败!", e);
            return new MessageEntity().addCode(1).addIsError(true).addTitle("错误").addContent("获取民警入区信息失败!");
        }
        return new MessageEntity().addCode(0).addIsError(false).addTitle("提示").addContent("获取民警入区信息成功!").addCallbackData(entity);
    }

    private void insertDownloadTack(long serialId, String cuffNo) throws Exception {
        // 查询该手环的所有轨迹
        Map<String, Object> param = new HashMap<>();
        param.put("serialId", serialId);
        param.put("cuffNo", cuffNo);
        // 定位数据转为手环轨迹信息
        trackService.bathCreateTrackInfo(param);
        // 获取手环轨迹信息
        List<TrackInfoEntity> tracks = trackService.listTrack(param);
        //int minutes = Integer.parseInt(PropertyUtil.getContextProperty("record.minutes").toString());
        for (TrackInfoEntity track : tracks) {
            // 获取拆分时间段的间隔时间
            int taskNo = 1;
            DownloadTaskEntity taskEntity = new DownloadTaskEntity();
            taskEntity.setCameraNo(track.getCameraNo());
            taskEntity.setTaskNo(taskNo);
            taskEntity.setTaskType(2);// 轨迹视频
            taskEntity.setSerialId(serialId);
            taskEntity.setCuffNo(cuffNo);
            taskEntity.setStatus(0);
            taskEntity.setStartTime(track.getStartTime());
            taskEntity.setEndTime(track.getEndTime());
            taskEntity.setHashValue(RandomUtils.nextInt(10));
            param.clear();
            param.put("serialId", serialId);
            param.put("cuffNo", cuffNo);
            param.put("starttime", track.getStartTime());
            param.put("endtime", track.getEndTime());
            param.put("camerano", track.getCameraNo());
            int taskCount = taskService.queryTaskCount(param);
            if (taskCount <= 0) {
                taskService.insert(taskEntity);
            }
        }
    }

    /**
     * 启动禁用定位设备
     *
     * @param cuffNo  卡片编号
     * @param status  0 禁用   1启用
     * @param request
     */
    private String tiandyUpdateCuffStatus(String cuffNo, int status, HttpServletRequest request, Integer areaId) throws Exception {
        String uri = getTiandyUrlInfo(areaId);
        if (uri == null || "".equals(uri.trim())) {
            return "未正确配置手环系统的IP和端口！";
        }
        String url = uri + "/Easy7/rest/ring/updateRingStatus";
        logger.info("绑定手环接口URL=" + url);
        this.operLogService.insertLog("民警出入区登记", "绑定手环接口URL=" + url,
                ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sSn", cuffNo);
        jsonObject.put("status", status);
        String json = jsonObject.toString();
        logger.info("绑定手环接口json=" + json);
        this.operLogService.insertLog("民警出入区登记", "绑定手环接口json=" + json,
                ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
        Map<String, String> params = new HashMap<String, String>();
        params.put("param", json);
        // 处理接口反馈结果，解析json
        JSONObject result = HttpClientUtil.get(url, params);
        String ret = result.get("ret").toString();
        logger.info("绑定手环接口结果=" + ret);
        this.operLogService.insertLog("民警出入区登记", "绑定手环接口结果=" + ret,
                ControllerTool.getSessionInfo(request).getUser().getRealName(), true, OperLogEntity.SYSTEM_BACS);
        return ret;
    }

    private String getTiandyUrlInfo(Integer areaId) {
        String result = null;
        Map<String, String> map = personalConfigDetailService.listConfigDetailByAreaAndName(areaId, "天地伟业审讯平台");
        if (map != null && map.size() > 0) {
            result = "http://" + map.get("ip") + ":" + map.get("port");
        }
        return result;
    }

}
