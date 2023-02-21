package com.zhixin.zhfz.glpt.controller.ryPatrol;

import com.zhixin.zhfz.bacs.daoOracle.IOracleDataMapper;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.services.person.IPersonService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.form.PersonTrailForm;
import com.zhixin.zhfz.glpt.form.PersonTrialResp;
import com.zhixin.zhfz.glpt.services.ryPatrol.IRyPatrolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @prgram: zhfz
 * @Description: 人员巡查控制中心
 * @Auther: xcf
 * @Date: 2019-04-18 09:33
 */
@Controller
@RequestMapping(value = "/zhfz/glpt/ryPatrol")
public class RyPatrolController {


    private static final Logger logger = LoggerFactory.getLogger(RyPatrolController.class);

    @Autowired
    private IPersonService personService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IRyPatrolService ryPatrolService;

    @Autowired
    private ISerialService serialService;

    @Autowired
    private IOracleDataMapper oracleDataMapper;

    /**
     *
     * @param params
     * @param request
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/ryList")
    @ResponseBody
    public Map<String, Object> listByAlarm(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        List<SerialEntity> interrogateSerials = new ArrayList<SerialEntity>();
        int count = 0;
        Map<String, Object> map = ControllerTool.mapFilter(params);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " ( ints.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or ints.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or ints.send_user_id=" + ControllerTool.getUserID(request)
                    + " or ic.cjr=" + ControllerTool.getUserID(request)
                    + " or ic.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',ic.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ints.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", " ints.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            map.put("dataAuth", " ints.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            map.put("dataAuth","  ( ints.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ic.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or ic.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            map.put("dataAuth","  ( ints.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ic.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or ic.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")" );
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            map.put("dataAuth","  ( ints.op_pid =" + sessionInfo.getCurrentOrgPid()
                    + " or ic.zbmj_pid =" + sessionInfo.getCurrentOrgPid()
                    + " or ic.op_pid =" + sessionInfo.getCurrentOrgPid()
                    + ")" );
        }
//        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 办案场所-本办案场所
//            map.put("dataAuth", " p.area_id=" + ControllerTool.getCurrentArea(request).getId());
//        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 管理员 -本办案场所及下级办案场所
//            map.put("dataAuth", "p.area_id " + ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
//        }
//        persons = personService.list(map);
////        total = this.personService.count(map);

        interrogateSerials = this.ryPatrolService.list(map);
        count = this.ryPatrolService.count(map);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", count);
        result.put("rows", interrogateSerials);
        return result;
    }



    @RequestMapping(value = "/selectPersonTrail")
    @ResponseBody
    public Map<String, Object> selectPersonTrail(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {

        Map<String, Object> map = ControllerTool.mapFilter(params);
//        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 办案场所-本办案场所
//            map.put("dataAuth", " p.area_id=" + ControllerTool.getCurrentArea(request).getId());
//        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request)) || RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
//            // 管理员 -本办案场所及下级办案场所
//            map.put("dataAuth", "p.area_id " + ControllerTool.getSessionInfo(request).getCurrentAndSubArea());
//        }

//        map.put("areaId",ControllerTool.getCurrentArea(request).getId());

        List<PersonTrialResp> listAll = new ArrayList<>();

        //预约

        PersonTrailForm yyInfo = this.ryPatrolService.selectOrderTrail(map);

        if (yyInfo!=null && (yyInfo.getStatus()==3 || yyInfo.getStatus()==4 && yyInfo.getCreatedTime()!=null)){

            PersonTrialResp  resp= new PersonTrialResp();

            resp.setFs("预约已取消");

            resp.setFsTime(yyInfo.getUpdatedTime());

            listAll.add(resp);

        }else {

            if (yyInfo!=null && yyInfo.getCreatedTime()!=null){

                PersonTrialResp  resp= new PersonTrialResp();

                resp.setFs("预约");

                resp.setFsTime(yyInfo.getCreatedTime());

                listAll.add(resp);
            }

            //入区登记查询

            PersonTrialResp  respRQ= new PersonTrialResp();

            if (null!=yyInfo){

                respRQ.setFsTime(yyInfo.getInTime());

                respRQ.setFs("入区");

                listAll.add(respRQ);

            }

            //安检登记查询

            List<PersonTrailForm> ajList = this.ryPatrolService.selectCheckTrail(map);

            if (null!=ajList && ajList.size()!=0){

                for (PersonTrailForm info:
                     ajList) {

                    if (1==info.getCount()){

                        PersonTrialResp  respAJ= new PersonTrialResp();

                        respAJ.setFs("安检");

                        respAJ.setFsTime(info.getCreatedTime());

                        listAll.add(respAJ);
                    }else {

                        PersonTrialResp  respAJ= new PersonTrialResp();

                        respAJ.setFs("复检");

                        respAJ.setFsTime(info.getCreatedTime());

                        listAll.add(respAJ);

                    }

                }

            }


            //存物取物登记查询

            List<PersonTrailForm> cwList = this.ryPatrolService.selectBelongTrail(map);


            if (cwList!=null && cwList.size()!=0){

                for (PersonTrailForm info:
                        cwList) {

                    if (info.getIsGet()==0){


                        PersonTrialResp  respCW= new PersonTrialResp();

                        respCW.setFs("存物");

                        respCW.setFsTime(info.getRegisterTime());

                        listAll.add(respCW);

                    }else {

                        PersonTrialResp  respCW= new PersonTrialResp();

                        PersonTrialResp  respCW1= new PersonTrialResp();

                        respCW1.setFs("存物");

                        respCW1.setFsTime(info.getRegisterTime());

                        listAll.add(respCW1);

                        respCW.setFs("取物");

                        respCW.setFsTime(info.getUpdatedTime());

                        listAll.add(respCW);

                    }

                }


            }


            //信息采集登记查询

            PersonTrailForm xxcjInfo = this.ryPatrolService.selectCollectionTrail(map);

            if (null!=xxcjInfo){

                PersonTrialResp  respXXCJ= new PersonTrialResp();

                respXXCJ.setFs("信息采集");

                respXXCJ.setFsTime(xxcjInfo.getRegisterTime());

            }


            //候问看押登记查询

            List<PersonTrailForm> hwList = this.ryPatrolService.selectWaitRecordTrail(map);


            if (hwList!=null && hwList.size()!=0){

                for (PersonTrailForm info:
                        hwList) {

                    if (info.getStatus()==0 && info.getOutTime()==null){

                        PersonTrialResp  respHW= new PersonTrialResp();

                        respHW.setFs("候问");

                        respHW.setFsTime(info.getInTime());

                        listAll.add(respHW);

                    }else {

                        if (null!=info.getGetResult()) {

                            PersonTrialResp respHW = new PersonTrialResp();

                            respHW.setFs("候问");

                            respHW.setFsTime(info.getInTime());

                            listAll.add(respHW);

                            PersonTrialResp respHW1 = new PersonTrialResp();

                            StringBuffer sb= new StringBuffer();

                            sb.append("候问->").append(info.getGetResult());

                            respHW1.setFs(String.valueOf(sb));

                            respHW1.setFsTime(info.getOutTime());

                            listAll.add(respHW1);
                        }

                    }

                }

            }

            // 警综案件
            List<PersonTrailForm> jzList = this.ryPatrolService.selectJzAjxx(map);
            if (null!=jzList && jzList.size()!=0){
                for (PersonTrailForm info: jzList) {
                    if(info != null && info.getSlsj() != null){
                        PersonTrialResp respJz = new PersonTrialResp();
                        respJz.setFsTime(info.getSlsj());
                        respJz.setFs("受理");
                        listAll.add(respJz);
                    }
                }
            }

            // 警综人员
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String,Object> jzMap = new HashMap<String,Object>();
            jzMap.put("rybh",jzList.get(0).getRybh());
            List<PersonTrailForm> jzPersonList = this.ryPatrolService.selectJzPerson(jzMap);
            if(jzPersonList != null && jzPersonList.size() > 0){
                for(PersonTrailForm info : jzPersonList){
                    if(info != null && info.getPersonTime() != null && !"".equals(info.getChuliResult())){
                        PersonTrialResp respJzPerson = new PersonTrialResp();
                        respJzPerson.setFsTime(info.getPersonTime());
                        respJzPerson.setFs(info.getChuliResult());
                        listAll.add(respJzPerson);
                    }
                }
            }

            //尿检登记查询
            List<PersonTrailForm> njList = this.ryPatrolService.selectUrinalysisTrail(map);

            if (null!=njList && njList.size()!=0){

                for (PersonTrailForm info:
                        njList) {

                    PersonTrialResp respNJ = new PersonTrialResp();

                    respNJ.setFsTime(info.getRegisterTime());

                    respNJ.setFs("尿检");

                    listAll.add(respNJ);

                }


            }

            //审讯登记查询

            List<PersonTrailForm> sxList=this.ryPatrolService.selectRecordTrail(map);

            if (null!=sxList && sxList.size()!=0){

                for (PersonTrailForm info:
                        sxList) {

                    if (null!=info.getOutTime()){

                        PersonTrialResp  respSX= new PersonTrialResp();

                        respSX.setFs("审讯开始");

                        respSX.setFsTime(info.getRegisterTime());

                        listAll.add(respSX);
                    }

                    if (null!=info.getOutTime()){

                        PersonTrialResp  respSX= new PersonTrialResp();

                        respSX.setFs("审讯结束");

                        respSX.setFsTime(info.getOutTime());

                        listAll.add(respSX);

                    }


                }
            }
        }

        Collections.sort(listAll, new Comparator<PersonTrialResp>() {
                    @Override
                    public int compare(PersonTrialResp o1, PersonTrialResp o2) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            // format.format(o1.getTime()) 表示 date转string类型 如果是string类型就不要转换了
                            Date dt1 = format.parse(format.format(o1.getFsTime()));
                            Date dt2 = format.parse(format.format(o2.getFsTime()));
                            // 这是由大向小排序   如果要由小向大转换比较符号就可以
                            if (dt1.getTime() > dt2.getTime()) {
                                return 1;
                            } else if (dt1.getTime() < dt2.getTime()) {
                                return -1;
                            } else {
                                return 0;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
        });


//        List<PersonTrialResp> slist = listAll.stream().sorted(Comparator.comparing(PersonTrialResp::getFsTime)).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("listAll", listAll);
        return result;
    }




}
