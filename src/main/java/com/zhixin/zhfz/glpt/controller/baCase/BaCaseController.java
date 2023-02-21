package com.zhixin.zhfz.glpt.controller.baCase;

import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.bacs.services.area.IAreaService;
import com.zhixin.zhfz.common.entity.CommonConfigDetailEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.services.commonConfig.ICommonConfigDetailService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.entity.BaCaseEntity;
import com.zhixin.zhfz.glpt.entity.BaSerialEntity;
import com.zhixin.zhfz.glpt.entity.ChartEntity;
import com.zhixin.zhfz.glpt.services.IBaCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zhixin.zhfz.jzgl.controller.jzlxRate.JzlxRateController.getDatesBetweenTwoDate;

@Controller
@RequestMapping("/zhfz/glpt/baCase")
public class BaCaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaCaseController.class);

    @Autowired
    private IBaCaseService baCaseService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private ICommonConfigDetailService service;

    //办案中心30天内刑事行政总数量统计
    @RequestMapping(value = "/query30DayByCategory")
    @ResponseBody
    public Map<String, Map<String, Integer>> query30DayByCategory(@RequestParam Map<String, Object> params, HttpServletRequest request) throws ParseException {
        Map<String, Object> param = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            param.put("dataAuth", " ( zbmj_id=" + ControllerTool.getUserID(request)
                    + " or cjr=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            param.put("dataAuth"," zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            param.put("dataAuth"," zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            param.put("dataAuth"," zbmj_pid = " + sessionInfo.getCurrentOrgPid());
        }
        //String str = ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
//        param.put("dataAuth", str);
        Date d = new Date();
        //获得数据集合
        List<BaCaseEntity> list = baCaseService.query30DayByCategory(param);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (BaCaseEntity InterrogateCaseEntity : list) {
            // 将时间 进行格式化
            String date = (simpleDateFormat.format(InterrogateCaseEntity.getCreatedTime())).toString();
            //int date = Integer.parseInt(simpleDateFormat.format(interrogateCaseEntity.getCreatedTime()));
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int xingshi = map.get("xingshi");
                int xingzheng = map.get("xingzheng");
                int jingqing = map.get("jingqing");
                // 进行判断
                if (InterrogateCaseEntity.getAjlx().equals(0)) {
                    xingshi++;
                }
                if (InterrogateCaseEntity.getAjlx().equals(1)) {
                    xingzheng++;
                }
                if (InterrogateCaseEntity.getAjlx().equals(2)) {
                    jingqing++;
                }
                map.clear();
                map.put("xingshi", xingshi);
                map.put("xingzheng", xingzheng);
                map.put("jingqing", jingqing);
                allMap.put(date, map);
            } else {
                int xingshi = 0;
                int xingzheng = 0;
                int jingqing =0;
                // 进行判断
                if (InterrogateCaseEntity.getAjlx().equals(0)) {
                    xingshi++;
                }
                if (InterrogateCaseEntity.getAjlx().equals(1)) {
                    xingzheng++;
                }
                if (InterrogateCaseEntity.getAjlx().equals(1)) {
                    jingqing++;
                }

                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("xingshi", xingshi);
                map.put("xingzheng", xingzheng);
                map.put("jingqing", jingqing);
                allMap.put(date, map);
            }

        }
        //获取日期
        //获取当前日期
        Date today = new Date();
        String endDate = simpleDateFormat.format(today);//当前日期
        //获取三十天前日期
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(today);
        theCa.add(theCa.DATE, -29);//最后一个数字30可改，30天的意思
        Date start = theCa.getTime();
        String startDate = simpleDateFormat.format(start);//三十天之前日期
        Date dBegin = simpleDateFormat.parse(startDate);
        Date dEnd = simpleDateFormat.parse(endDate);
        List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);
        //若没有数据的日期自动补充
        for (int i = 0; i < listDate.size(); i++) {
            String string = simpleDateFormat.format(listDate.get(i)).toString();
            if (!allMap.containsKey(string)) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("xingshi", 0);
                map.put("xingzheng", 0);
                map.put("jingqing",0);
                allMap.put(string, map);
            }
        }
        return allMap;
    }

    /**
     * 各个办案中心裁决统计
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping("/queryAdjudicationByAll")
    @ResponseBody
    public Map<String, Map<String, Integer>> queryAdjudicationByAll(@RequestParam Map<String, Object> params, HttpServletRequest request) throws ParseException {
        Map<String, Object> param = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            param.put("dataAuth", " ( s.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.send_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            param.put("dataAuth", " s.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            param.put("dataAuth", " s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            param.put("dataAuth", " s.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            param.put("dataAuth"," s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            param.put("dataAuth"," s.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            param.put("dataAuth"," s.op_pid = " + sessionInfo.getCurrentOrgPid());
        }
//        String str = ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
////        param.put("dataAuth", str);
        //获得数据集合
        List<BaSerialEntity> list = baCaseService.queryAdjudicationByAll(param);
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (BaSerialEntity InterrogateSerialEntity : list) {
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(InterrogateSerialEntity.getAreaName())) {
                Map<String, Integer> map = allMap.get(InterrogateSerialEntity.getAreaName());
                int daibu = map.get("daibu");
                int jianju = map.get("jianju");
                int paichu = map.get("paichu");
                int xingju = map.get("xingju");
                int yijiao = map.get("yijiao");
                int zaitao = map.get("zaitao");
                int zhibao = map.get("zhibao");
                int zhiju = map.get("zhiju");
                int jinggao = map.get("jinggao");
                int fakuan = map.get("fakuan");
                int jiaoyu = map.get("jiaoyu");
                int qita = map.get("qita");
                int wu = map.get("wu");
                // 进行判断
                if (InterrogateSerialEntity.getConfirmResult().equals("逮捕")) {
                    daibu++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("监居")) {
                    jianju++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("排除")) {
                    paichu++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("刑拘")) {
                    xingju++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("移交")) {
                    yijiao++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("在逃羁押")) {
                    zaitao++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("直保")) {
                    zhibao++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("治拘")) {
                    zhiju++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("警告")) {
                    jinggao++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("罚款")) {
                    fakuan++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("教育")) {
                    jiaoyu++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("其他")) {
                    qita++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("无")) {
                    wu++;
                }
                map.clear();
                map.put("daibu", daibu);
                map.put("jianju", jianju);
                map.put("paichu", paichu);
                map.put("xingju", xingju);
                map.put("yijiao", yijiao);
                map.put("zaitao", zaitao);
                map.put("zhibao", zhibao);
                map.put("zhiju", zhiju);
                map.put("jinggao", jinggao);
                map.put("fakuan", fakuan);
                map.put("jiaoyu", jiaoyu);
                map.put("qita", qita);
                map.put("wu", wu);
                allMap.put(InterrogateSerialEntity.getAreaName(), map);
            } else {
                int daibu = 0;
                int jianju = 0;
                int paichu = 0;
                int xingju = 0;
                int yijiao = 0;
                int zaitao = 0;
                int zhibao = 0;
                int zhiju = 0;
                int jinggao = 0;
                int fakuan = 0;
                int jiaoyu = 0;
                int qita = 0;
                int wu = 0;
                // 进行判断
                if (InterrogateSerialEntity.getConfirmResult().equals("逮捕")) {
                    daibu++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("监居")) {
                    jianju++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("排除")) {
                    paichu++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("刑拘")) {
                    xingju++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("移交")) {
                    yijiao++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("在逃羁押")) {
                    zaitao++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("直保")) {
                    zhibao++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("治拘")) {
                    zhiju++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("警告")) {
                    jinggao++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("罚款")) {
                    fakuan++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("教育")) {
                    jiaoyu++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("其他")) {
                    qita++;
                }
                if (InterrogateSerialEntity.getConfirmResult().equals("无")) {
                    wu++;
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("daibu", daibu);
                map.put("jianju", jianju);
                map.put("paichu", paichu);
                map.put("xingju", xingju);
                map.put("yijiao", yijiao);
                map.put("zaitao", zaitao);
                map.put("zhibao", zhibao);
                map.put("zhiju", zhiju);
                map.put("jinggao", jinggao);
                map.put("fakuan", fakuan);
                map.put("jiaoyu", jiaoyu);
                map.put("qita", qita);
                map.put("wu", wu);
                allMap.put(InterrogateSerialEntity.getAreaName(), map);
            }
        }
        return allMap;
    }

    //各办案中心七天内男女总入区数量
    @RequestMapping(value = "/queryIntoByWeek")
    @ResponseBody
    public Map<String, Map<String, Integer>> queryIntoByWeek(@RequestParam Map<String, Object> params, HttpServletRequest request) throws ParseException {
        Map<String, Object> param = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            param.put("dataAuth", " ( s.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.send_user_id=" + ControllerTool.getUserID(request)
                    + " or bc.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or bc.cjr=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',bc.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            param.put("dataAuth", " s.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            param.put("dataAuth", " s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            param.put("dataAuth", " s.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            param.put("dataAuth"," (s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + " or bc.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            param.put("dataAuth"," (s.op_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + " or bc.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid()
                    + ")");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            param.put("dataAuth"," (s.op_pid =" + sessionInfo.getCurrentOrgPid()
                    + " or bc.zbmj_pid =" + sessionInfo.getCurrentOrgPid()
                    + ")");
        }
//        String str = ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrgs());
//        param.put("dataAuth", str);
        Date d = new Date();
        //获得数据集合
        List<BaSerialEntity> list = baCaseService.queryIntoByWeek(param);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (BaSerialEntity InterrogateSerialEntity : list) {
            // 将时间 进行格式化
            String date = (simpleDateFormat.format(InterrogateSerialEntity.getInTime())).toString();
            //int date = Integer.parseInt(simpleDateFormat.format(interrogateCaseEntity.getCreatedTime()));
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int nan = map.get("nan");
                int nv = map.get("nv");
                // 进行判断
                if (InterrogateSerialEntity.getSex().equals(1)) {
                    nan++;
                }
                if (InterrogateSerialEntity.getSex().equals(2)) {
                    nv++;
                }
                map.clear();
                map.put("nan", nan);
                map.put("nv", nv);
                allMap.put(date, map);
            } else {
                int nan = 0;
                int nv = 0;
                // 进行判断
                if (InterrogateSerialEntity.getSex().equals(1)) {
                    nan++;
                }
                if (InterrogateSerialEntity.getSex().equals(2)) {
                    nv++;
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("nan", nan);
                map.put("nv", nv);
                allMap.put(date, map);
            }

        }
        //获取日期
        //获取当前日期
        Date today = new Date();
        String endDate = simpleDateFormat.format(today);//当前日期
        //获取三十天前日期
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(today);
        theCa.add(theCa.DATE, -6);//最后一个数字30可改，30天的意思
        Date start = theCa.getTime();
        String startDate = simpleDateFormat.format(start);//三十天之前日期
        Date dBegin = simpleDateFormat.parse(startDate);
        Date dEnd = simpleDateFormat.parse(endDate);
        List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);
        //若没有数据的日期自动补充
        for (int i = 0; i < listDate.size(); i++) {
            String string = simpleDateFormat.format(listDate.get(i)).toString();
            if (!allMap.containsKey(string)) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("nan", 0);
                map.put("nv", 0);
                allMap.put(string, map);
            }
        }
        return allMap;
    }

    /**
     * 各个办案中心总的收押人数Top10
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping("/queryDetainByAll")
    @ResponseBody
    public List<BaSerialEntity> queryDetainByAll(HttpServletRequest request) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        //查询其下所有办案场所
        List<AreaEntity> listArea = ControllerTool.queryAreaListByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < listArea.size(); i++) {
            str.append("select count(1) AS count ,a.`name` AS areaName from ba_serial ints LEFT JOIN ba_area a on  ints.area_id=a.id where ints.`status` <> 11 and ints.type = 0 and a.id=" + listArea.get(i).getId() + "");
            if (i != (listArea.size() - 1)) {
                str.append(" union all ");
            } else {
                str.append(" ORDER BY count desc");
            }
        }
        map.put("dataAuth", str);
        List<BaSerialEntity> list = baCaseService.queryDetainByAll(map);
        List resultList = new ArrayList();
        if (listArea.size() > 10) {
            String[] arr = new String[10];
            int[] arr1 = new int[10];
            for (int i = 0; i < 10; i++) {
                String areaName = list.get(i).getAreaName();
                int count = list.get(i).getCount();
                arr[i] = areaName;
                arr1[i] = count;
                resultList.add(arr);
                resultList.add(arr1);
            }
        } else {
            String[] arr = new String[listArea.size()];
            int[] arr1 = new int[listArea.size()];
            for (int i = 0; i < list.size(); i++) {
                String areaName = list.get(i).getAreaName();
                int count = list.get(i).getCount();
                arr[i] = areaName;
                arr1[i] = count;
                resultList.add(arr);
                resultList.add(arr1);
            }
        }
        //获得数据集合
        return resultList;
    }

    //办案中心24小时内每天入区出区总人数
    @RequestMapping(value = "/query24HourOut")
    @ResponseBody
    public Map<String, Map<String, Integer>> query24HourOut(@RequestParam Map<String, Object> params, HttpServletRequest request) throws ParseException {
        Map<String, Object> param = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            param.put("dataAuth", " ( s.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.send_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            param.put("dataAuth", " s.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            param.put("dataAuth", " s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            param.put("dataAuth", " s.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            param.put("dataAuth"," s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            param.put("dataAuth"," s.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            param.put("dataAuth"," s.op_pid = " + sessionInfo.getCurrentOrgPid());
        }
        //String str = ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
        //param.put("dataAuth", str);
        Date d = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        calendar.add(calendar.DATE, -1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        d = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(d);
        //获得数据集合
        List<BaSerialEntity> list = baCaseService.query24HourOut(param);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (BaSerialEntity InterrogateSerialEntity : list) {
            // 将时间 进行格式化
            //String date = (simpleDateFormat.format(InterrogateSerialEntity.getWrittenTime())).toString();
            //int date = Integer.parseInt(simpleDateFormat.format(interrogateCaseEntity.getCreatedTime()));
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(InterrogateSerialEntity.getAreaName())) {
                Map<String, Integer> map = allMap.get(InterrogateSerialEntity.getAreaName());
                int ruqu = map.get("ruqu");
                int chuqu = map.get("chuqu");
                // 进行判断
                if (InterrogateSerialEntity.getInTime() != null && !InterrogateSerialEntity.getInTime().equals("")) {
                    if (InterrogateSerialEntity.getInTime().after(d)) {
                        ruqu++;
                    }
                }
                if (InterrogateSerialEntity.getOutTime() != null && !InterrogateSerialEntity.getOutTime().equals("")) {
                    if (InterrogateSerialEntity.getOutTime().after(d)) {
                        chuqu++;
                    }
                }
                map.clear();
                map.put("ruqu", ruqu);
                map.put("chuqu", chuqu);
                allMap.put(InterrogateSerialEntity.getAreaName(), map);
            } else {
                int ruqu = 0;
                int chuqu = 0;
                // 进行判断
                if (InterrogateSerialEntity.getInTime() != null && !InterrogateSerialEntity.getInTime().equals("")) {
                    if (InterrogateSerialEntity.getInTime().after(d)) {
                        ruqu++;
                    }
                } else if (InterrogateSerialEntity.getOutTime() != null && !InterrogateSerialEntity.getOutTime().equals("")) {
                    if (InterrogateSerialEntity.getOutTime().after(d)) {
                        chuqu++;
                    }
                } else {
                    Map<String, Integer> map = new HashMap<String, Integer>();
                    map.put("ruqu", ruqu);
                    map.put("chuqu", chuqu);
                    allMap.put(InterrogateSerialEntity.getAreaName(), map);
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("ruqu", ruqu);
                map.put("chuqu", chuqu);
                allMap.put(InterrogateSerialEntity.getAreaName(), map);
            }
        }
        return allMap;
    }

    //各办案中心类别统计
    @RequestMapping(value = "/queryCategoryByAll")
    @ResponseBody
    public Map<String, Map<String, Integer>> queryCategoryByAll(@RequestParam Map<String, Object> params, HttpServletRequest request) throws ParseException {
        Map<String, Object> param = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            param.put("dataAuth", " ( c.zbmj_id=" + ControllerTool.getUserID(request)
                    + " or c.cjr=" + ControllerTool.getUserID(request)
                    + " or locate('," + ControllerTool.getUserID(request) + ",',c.xbmj_ids)"
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            param.put("dataAuth"," c.zbmj_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            param.put("dataAuth"," c.zbmj_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            param.put("dataAuth"," c.zbmj_pid = " + sessionInfo.getCurrentOrgPid());
        }else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            param.put("dataAuth", " a.id=" + ControllerTool.getCurrentAreaID(request));
        }else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            param.put("dataAuth", " a.id " + sessionInfo.getSuperAndSubAreaInStr());
        }else if(RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))){

        }
        //String str = ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
        //param.put("dataAuth", str);
        //获得数据集合
        List<BaCaseEntity> list = baCaseService.queryCategoryByAll(param);
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        if(list != null && list.size() > 0) {
            for (BaCaseEntity baCaseEntity : list) {
                // 判断allMap里面是不是有这个key
                if(baCaseEntity != null) {
                    if (allMap.containsKey(baCaseEntity.getAreaName())) {
                        Map<String, Integer> map = allMap.get(baCaseEntity.getAreaName());
                        int xingzheng = map.get("xingzheng");
                        int xingshi = map.get("xingshi");
                        int jingqing = map.get("jingqing");
                        // 进行判断
                        if ("1".equals(baCaseEntity.getAjlx())) {
                            xingzheng++;
                        }
                        if ("0".equals(baCaseEntity.getAjlx())) {
                            xingshi++;
                        }
                        if ("2".equals(baCaseEntity.getAjlx())) {
                            jingqing++;
                        }
                        map.clear();
                        map.put("xingzheng", xingzheng);
                        map.put("xingshi", xingshi);
                        map.put("jingqing", jingqing);
                        allMap.put(baCaseEntity.getAreaName(), map);
                    } else {
                        int xingzheng = 0;
                        int xingshi = 0;
                        int jingqing = 0;
                        // 进行判断    刑事 行政
                        if (baCaseEntity.getAjlx() != null && !baCaseEntity.getAjlx().equals("")) {
                            if (baCaseEntity.getAjlx().equals(1)) {
                                xingzheng++;
                            }
                            if (baCaseEntity.getAjlx().equals(0)) {
                                xingshi++;
                            }
                            if (baCaseEntity.getAjlx().equals(2)) {
                                jingqing++;
                            }
                        }
                        Map<String, Integer> map = new HashMap<String, Integer>();
                        map.put("xingzheng", xingzheng);
                        map.put("xingshi", xingshi);
                        map.put("jingqing", jingqing);
                        allMap.put(baCaseEntity.getAreaName(), map);
                    }
                }
            }
        }
        return allMap;
    }
    /**
     * 各办案中心总的收押比率
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping("/queryDetainByCount")
    @ResponseBody
    public String queryDetainByCount(@RequestParam Map<String, Object> params, HttpServletRequest request) throws ParseException {
        Map<String, Object> param = new HashMap<String, Object>();
//        String str = ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
//        param.put("dataAuth", str);
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            param.put("dataAuth", " r.op_user_id=" + ControllerTool.getUserID(request));
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            param.put("dataAuth", " r.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            param.put("dataAuth", " r.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            param.put("dataAuth", " r.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            param.put("dataAuth"," r.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            param.put("dataAuth"," r.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            param.put("dataAuth"," r.op_pid = " + sessionInfo.getCurrentOrgPid());
        }
        Map<String, Integer> map = baCaseService.queryDetainByCount(param);
        Number a = (Number) map.get("num");
        Number b = (Number) map.get("volume");
        String p;
        if (a.intValue() != 0 && b.intValue() != 0) {
            float count = (a.floatValue() / b.floatValue()) * 100;
            DecimalFormat format = new DecimalFormat(".00");
            p = format.format(count);
        } else {
            p = "0";
        }
        return p;
    }

    //办各个办案中心入区嫌疑人状态
    @RequestMapping(value = "/queryPersonType")
    @ResponseBody
    public Map<String, Map<String, Integer>> queryPersonType(@RequestParam Map<String, Object> params, HttpServletRequest request) throws ParseException {
        Map<String, Object> param = new HashMap<String, Object>();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            param.put("dataAuth", " ( s.in_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.out_register_user_id=" + ControllerTool.getUserID(request)
                    + " or s.send_user_id=" + ControllerTool.getUserID(request)
                    + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            param.put("dataAuth", " s.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            param.put("dataAuth", " s.area_id  " + sessionInfo.getCurrentAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 上级办案场所及其下级办案场所
            param.put("dataAuth", " s.area_id  " + sessionInfo.getSuperAndSubAreaInStr());
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            param.put("dataAuth"," s.op_pid like " + sessionInfo.getCurrentAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            param.put("dataAuth"," s.op_pid like " + sessionInfo.getSuperAndSubOrgPid());
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            param.put("dataAuth"," s.op_pid = " + sessionInfo.getCurrentOrgPid());
        }
        //String str = ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
        //param.put("dataAuth", str);
        //获得数据集合
        List<BaSerialEntity> list = baCaseService.queryPersonType(param);
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (BaSerialEntity InterrogateSerialEntity : list) {
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(InterrogateSerialEntity.getAreaName())) {
                Map<String, Integer> map = allMap.get(InterrogateSerialEntity.getAreaName());
                int ruqu = map.get("ruqu");
                int anjian = map.get("anjian");
                int zancun = map.get("zancun");
                int houwenkaishi = map.get("houwenkaishi");
                int houwenjieshu = map.get("houwenjieshu");
                int xinxicaiji = map.get("xinxicaiji");
                int shenxunkaishi = map.get("shenxunkaishi");
                int shenxunjieshu = map.get("shenxunjieshu");
                int wupinlingqu = map.get("wupinlingqu");
                int linshichuqu = map.get("linshichuqu");
                int linshichuqufanhui = map.get("linshichuqufanhui");
                // 进行判断
                if (InterrogateSerialEntity.getStatus() != null && !InterrogateSerialEntity.getStatus().equals("")) {
                    if (InterrogateSerialEntity.getStatus() == 0) {
                        ruqu++;
                    } else if (InterrogateSerialEntity.getStatus() == 1) {
                        anjian++;
                    } else if (InterrogateSerialEntity.getStatus() == 2) {
                        zancun++;
                    } else if (InterrogateSerialEntity.getStatus() == 3) {
                        houwenkaishi++;
                    } else if (InterrogateSerialEntity.getStatus() == 4) {
                        houwenjieshu++;
                    } else if (InterrogateSerialEntity.getStatus() == 5) {
                        xinxicaiji++;
                    } else if (InterrogateSerialEntity.getStatus() == 6) {
                        shenxunkaishi++;
                    } else if (InterrogateSerialEntity.getStatus() == 7) {
                        shenxunjieshu++;
                    } else if (InterrogateSerialEntity.getStatus() == 8) {
                        wupinlingqu++;
                    } else if (InterrogateSerialEntity.getStatus() == 9) {
                        linshichuqufanhui++;
                    } else if (InterrogateSerialEntity.getStatus() == 10) {
                        linshichuqu++;
                    }
                    map.clear();
                    map.put("ruqu", ruqu);
                    map.put("anjian", anjian);
                    map.put("zancun", zancun);
                    map.put("houwenkaishi", houwenkaishi);
                    map.put("houwenjieshu", houwenjieshu);
                    map.put("xinxicaiji", xinxicaiji);
                    map.put("shenxunkaishi", shenxunkaishi);
                    map.put("shenxunjieshu", shenxunjieshu);
                    map.put("wupinlingqu", wupinlingqu);
                    map.put("linshichuqufanhui", linshichuqufanhui);
                    map.put("linshichuqu", linshichuqu);
                    allMap.put(InterrogateSerialEntity.getAreaName(), map);
                }
            } else {
                int ruqu = 0;
                int anjian = 0;
                int zancun = 0;
                int houwenkaishi = 0;
                int houwenjieshu = 0;
                int xinxicaiji = 0;
                int shenxunkaishi = 0;
                int shenxunjieshu = 0;
                int wupinlingqu = 0;
                int linshichuqu = 0;
                int linshichuqufanhui = 0;
                // 进行判断
                if (InterrogateSerialEntity.getStatus() != null && !InterrogateSerialEntity.getStatus().equals("")) {
                    if (InterrogateSerialEntity.getStatus() == 0) {
                        ruqu++;
                    } else if (InterrogateSerialEntity.getStatus() == 1) {
                        anjian++;
                    } else if (InterrogateSerialEntity.getStatus() == 2) {
                        zancun++;
                    } else if (InterrogateSerialEntity.getStatus() == 3) {
                        houwenkaishi++;
                    } else if (InterrogateSerialEntity.getStatus() == 4) {
                        houwenjieshu++;
                    } else if (InterrogateSerialEntity.getStatus() == 5) {
                        xinxicaiji++;
                    } else if (InterrogateSerialEntity.getStatus() == 6) {
                        shenxunkaishi++;
                    } else if (InterrogateSerialEntity.getStatus() == 7) {
                        shenxunjieshu++;
                    } else if (InterrogateSerialEntity.getStatus() == 8) {
                        wupinlingqu++;
                    } else if (InterrogateSerialEntity.getStatus() == 9) {
                        linshichuqufanhui++;
                    } else if (InterrogateSerialEntity.getStatus() == 10) {
                        linshichuqu++;
                    }
                    Map<String, Integer> map = new HashMap<String, Integer>();
                    map.clear();
                    map.put("ruqu", ruqu);
                    map.put("anjian", anjian);
                    map.put("zancun", zancun);
                    map.put("houwenkaishi", houwenkaishi);
                    map.put("houwenjieshu", houwenjieshu);
                    map.put("xinxicaiji", xinxicaiji);
                    map.put("shenxunkaishi", shenxunkaishi);
                    map.put("shenxunjieshu", shenxunjieshu);
                    map.put("wupinlingqu", wupinlingqu);
                    map.put("linshichuqufanhui", linshichuqufanhui);
                    map.put("linshichuqu", linshichuqu);
                    allMap.put(InterrogateSerialEntity.getAreaName(), map);
                } else {
                    Map<String, Integer> map = new HashMap<String, Integer>();
                    map.clear();
                    map.put("ruqu", ruqu);
                    map.put("anjian", anjian);
                    map.put("zancun", zancun);
                    map.put("houwenkaishi", houwenkaishi);
                    map.put("houwenjieshu", houwenjieshu);
                    map.put("xinxicaiji", xinxicaiji);
                    map.put("shenxunkaishi", shenxunkaishi);
                    map.put("shenxunjieshu", shenxunjieshu);
                    map.put("wupinlingqu", wupinlingqu);
                    map.put("linshichuqufanhui", linshichuqufanhui);
                    map.put("linshichuqu", linshichuqu);
                    allMap.put(InterrogateSerialEntity.getAreaName(), map);
                }
            }
        }
        return allMap;
    }

    //查询当前在区人数，历史总人数，历史总案件
    @RequestMapping(value = "/inInterrogateAndTotal")
    @ResponseBody
    public List<Integer> inInterrogateAndTotal(HttpServletRequest request, HttpServletResponse response, @RequestParam String interrogateName) {
        Map<String, Object> map = new HashMap<>();
        if (!"".equals(interrogateName) && interrogateName != null) {
            map.put("name", interrogateName);
            List<AreaEntity> areaEntityList = areaService.listAllArea(map);
            if (areaEntityList != null && areaEntityList.size() != 0) {
                map.put("areaId", areaEntityList.get(0).getId());
            }
        } else {
            if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
                String dataAuth = ControllerTool.queryAreaIdByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
                map.put("dataAuth", dataAuth);
            }
        }
        List<Integer> resultMap = baCaseService.queryInAndTotal(map);
        return resultMap;
    }

    //侯问室容积率
    @RequestMapping(value = "/useRate")
    @ResponseBody
    public String useRate(@RequestParam Map<String, Object> map) {
        String useRate = "0";
        DecimalFormat df = new DecimalFormat("0.00");
        try {

            List<Integer> useRateCount = baCaseService.queryUseRateCount(map);
            int valueTotal = useRateCount.get(1).intValue();//总数

            if (valueTotal != 0) {
                int value0 = useRateCount.get(0).intValue();
                useRate = df.format((float) value0 / valueTotal);
                useRate = df.format(Float.valueOf(useRate) * 100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return useRate;

    }

    //每个办案中心在区人数
    @RequestMapping(value = "/inInterrogateNum")
    @ResponseBody
    public List<ChartEntity> inInterrogateNum(@RequestParam Map<String, Object> map, HttpServletRequest request,
                                              HttpServletResponse response) {
        boolean flag = false;
//        if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {

            List<AreaEntity> areaEntityList = ControllerTool.queryAreaListByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            StringBuffer sbSql = new StringBuffer();
            if (areaEntityList != null && areaEntityList.size() > 0) {
                flag = true;
                int areaEntityListSize = areaEntityList.size();
                int areaId = 0;
                for (int i = 0; i < areaEntityListSize; i++) {
                    areaId = areaEntityList.get(i).getId();
                    sbSql.append(
                            "SELECT count(1) v,(SELECT name from ba_area where id=" + areaId + ") k  " +
                                    " FROM ba_serial WHERE `status` <> 11 " +
                                    "  and area_id=" + areaId);
                    if (i != areaEntityListSize - 1) {
                        sbSql.append(" union all ");
                    }


                }
            }

            map.put("dataAuth", sbSql);
//        }
        if (flag) {
            List<ChartEntity> resultMap = baCaseService.queryInInterrogateNum(map);

            return resultMap;

        }
        return null;
    }

    @RequestMapping(value = "/caseTop10")
    @ResponseBody
    public List<ChartEntity> caseTop10(@RequestParam Map<String, Object> map) {
        List<ChartEntity> resultMap = baCaseService.queryCaseTop10(map);
        return resultMap;

    }

    //每个月入区人数
    @RequestMapping(value = "/entranceCountEveryMonth")
    @ResponseBody
    public List<Integer> entranceCountEveryMonth(@RequestParam Map<String, Object> map) {
        String interrogateName = map.get("interrogateName").toString();
        if (!"".equals(interrogateName) && interrogateName != null) {
            map.clear();
            map.put("name", interrogateName);
            List<AreaEntity> areaEntityList = areaService.listAllArea(map);
            if (areaEntityList != null && areaEntityList.size() != 0) {
                map.put("areaId", areaEntityList.get(0).getId());
            }
        }
        List<Integer> resultMap = baCaseService.queryEntranceCountEveryMonth(map);
        return resultMap;
    }

    //每个办案中心在区人数
    @RequestMapping(value = "/everyInterrogateIn")
    @ResponseBody
    public List<ChartEntity> everyInterrogateIn(HttpServletRequest request, HttpServletResponse response) {

        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        List<AreaEntity> areaEntityList = ControllerTool.queryAreaListByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
        StringBuffer sbSql = new StringBuffer();
        if (areaEntityList != null && areaEntityList.size() > 0) {
            flag = true;
            int areaEntityListSize = areaEntityList.size();
            int areaId = 0;
            for (int i = 0; i < areaEntityListSize; i++) {
                areaId = areaEntityList.get(i).getId();
                sbSql.append(
                        "SELECT count(1) v,a.name k ,a.geo_coord from ba_serial s,ba_area a  " +
                                "  where  s.status <> 11 " +
                                "  and s.area_id=" + areaId +
                                "  and a.id= " + areaId);
                if (i != areaEntityListSize - 1) {
                    sbSql.append(" union all ");
                }


            }
        }

        map.put("dataAuth", sbSql);

        List<ChartEntity> resultMap = baCaseService.queryEveryInterrogateIn(map);
        return resultMap;

    }

    //办案中心30天内刑事行政总数量统计
    @RequestMapping(value = "/queryDetailByType")
    @ResponseBody
    public Map<String, Map<String, String>> queryDetailByType(@RequestParam Map<String, Object> params, HttpServletRequest request) throws Exception {
        Map<String, Map<String, String >> allMap = new TreeMap<String, Map<String, String >>();
        Map<String, String> map = new HashMap<String, String >();
        CommonConfigDetailEntity entity = service.queryDetailByType(null);
        if(entity != null) {
            map.put("hanzi",entity.getParamKey());
            map.put("pinyin", entity.getParamValue());
            allMap.put(entity.getDesc(), map);
        }
        return  allMap;
    }
}
