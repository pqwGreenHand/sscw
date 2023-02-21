package com.zhixin.zhfz.zhag.controller.xzcftj;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.CscfEntity;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import com.zhixin.zhfz.zhag.services.xzcftj.XzcftjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zhixin.zhfz.jzgl.controller.jzlxRate.JzlxRateController.getDatesBetweenTwoDate;

@Controller
@RequestMapping("/zhfz/zhag/xzcftj")
public class XzcftjController {

    @Autowired
    private XzcftjService xzcftjService;

    /**
     * 行政处罚名称
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXZCfmcByDay")
    @ResponseBody
    public MessageEntity quyXZCfmcByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CscfEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzcftjService.quyXZCfmcByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXZCfmcByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXZCfmcByDay查询成功!").addCallbackData(list);
    }

    /**
     * 行政处罚总数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXZCfzsByDay")
    @ResponseBody
    public MessageEntity quyXZCfzsByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CscfEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzcftjService.quyXZCfzsByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXZCfzsByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXZCfzsByDay查询成功!").addCallbackData(list);
    }

    /**
     * 警情类别
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyjqlbByDay")
    @ResponseBody
    public MessageEntity quyjqlbByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<JqxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzcftjService.quyjqlbByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyjqlbByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyjqlbByDay查询成功!").addCallbackData(list);
    }


    /**
     * 创建时间
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping("/quyXZcfCreateDateByDay")
    @ResponseBody
    public Map<String,List<String>>  quyXZcfCreateDateByDay(HttpServletRequest request) throws Exception {
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        int dayNumber = day-1;

        // 封装数据map
        Map<String,List<String>>  resultMap = new HashMap<>();
        // 格式化日期  年-月-日
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 查询范围内数据
        List<CscfEntity> cscfEntitylist = xzcftjService.quyXZcfNumberByDay(day);
        //当前日期
        Date nowDate = new Date();
        String endDate = simpleDateFormat.format(nowDate);//当前日期
        //根据当前日期算出 减去参数 day-1 的日期  作为开始日期
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(nowDate);
        canlendar.add(canlendar.DATE, -dayNumber);//减去天数
        Date startDate = canlendar.getTime();// 拿到开始日期
        int count = 0;
        String useDate = simpleDateFormat.format(startDate);//  用来对比的日期
        if(cscfEntitylist.size() > 0 && !cscfEntitylist.isEmpty()){
            List<String> listDate = new ArrayList<String>();
            List<String> listCount = new ArrayList<String>();
            // 从第一天开始
            for (int j =0;j<day;j++){
                // 遍历数据集合
                for (int i = 0; i< cscfEntitylist.size(); i++){
                    if (simpleDateFormat.format(cscfEntitylist.get(i).getCjsj()).contains(useDate)){
                        count++;
                    }
                }
                listDate.add(useDate);
                listCount.add(String.valueOf(count));
                // 重置count 并且 日期加一天
                count=0;
                Date usingDate = simpleDateFormat.parse(useDate);
                canlendar.setTime(usingDate);
                canlendar.add(canlendar.DATE, 1);//加一天
                Date lastDate = canlendar.getTime();
                useDate = simpleDateFormat.format(lastDate);// 拿到下一个日期
                if(lastDate.getTime() > nowDate.getTime()){
                    break;
                }
            }
            resultMap.put("date",listDate);
            resultMap.put("count",listCount);
        }
        //获得数据集合
        return resultMap;
    }


    /**
     * 行政处罚预警和告警统计
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping("/quyXZcfYJandGJByDay")
    @ResponseBody
    public Map<String,List<String>>  quyXZcfYjGJByDay(HttpServletRequest request) throws Exception {
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());
        int dayNumber = day-1;
        // 封装数据map
        Map<String,List<String>>  resultMap = new HashMap<>();
        // 格式化日期  年-月-日
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //当前日期
        Date nowDate = new Date();
        String endDate = simpleDateFormat.format(nowDate);//当前日期
        //根据当前日期算出 减去参数 day-1 的日期  作为开始日期
        Calendar canlendar = Calendar.getInstance();
        canlendar.setTime(nowDate);
        canlendar.add(canlendar.DATE, -dayNumber);//减去天数
        Date startDate = canlendar.getTime();// 拿到开始日期
//        int count = 0;
        String useDate = simpleDateFormat.format(startDate);//  用来对比的日期
        if(day > 0){
            List<String> listDate = new ArrayList<String>();
            List<String> listGJCount = new ArrayList<String>();
            List<String> listYJCount = new ArrayList<String>();
            // 从第一天开始
            for (int j =0;j<day;j++){
                //  拿时间去查询 行政处罚的告警数量和预警数量
                int gjCount = xzcftjService.quyXZcfGJByDate(useDate);
                int yjCount = xzcftjService.quyXZcfYJByDate(useDate);

                listDate.add(useDate);
                listGJCount.add(String.valueOf(gjCount));
                listYJCount.add(String.valueOf(yjCount));
                // 日期加一天
//                count=0;
                Date usingDate = simpleDateFormat.parse(useDate);
                canlendar.setTime(usingDate);
                canlendar.add(canlendar.DATE, 1);//加一天
                Date lastDate = canlendar.getTime();
                useDate = simpleDateFormat.format(lastDate);// 拿到下一个日期
                if(lastDate.getTime() > nowDate.getTime()){
                    break;
                }
            }
            resultMap.put("datetime",listDate);
            resultMap.put("gjcount",listGJCount);
            resultMap.put("yjcount",listYJCount);
        }
        //获得数据集合
        return resultMap;
    }

    /**
     * 行政处罚执行时间
     *
     * @param day
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectZXSJByDay")
    @ResponseBody
    public Map<String, Map<String, Integer>> selectZXSJByDay(Integer day, HttpServletRequest request,
                                                             HttpServletResponse response) throws Exception {
        List<CscfEntity> list = xzcftjService.selectZXSJByDay(day);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (CscfEntity entity : list) {
            // 将时间 进行格式化
            String date = (simpleDateFormat.format(entity.getCjsj())).toString();
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int gaojing = map.get("gaojing");
                // 进行判断
                if (entity.getZxsj()!= null) {
                    gaojing++;
                }
                map.clear();
                map.put("gaojing", gaojing);
                allMap.put(date, map);
            } else {
                int gaojing = 0;
                // 进行判断
                if (entity.getZxsj()!= null) {
                    gaojing++;
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("gaojing", gaojing);
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
        theCa.add(theCa.DATE, -day);//最后一个数字30可改，30天的意思
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
                map.put("gaojing", 0);
                allMap.put(string, map);
            }
        }
        return allMap;
    }

    /**
     * 行政处罚案别
     *
     * @param day
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectCFABByDay")
    @ResponseBody
    public Map<String, Map<String, Integer>> selectCFABByDay(Integer day, HttpServletRequest request,
                                                             HttpServletResponse response) throws Exception {
        List<CscfEntity> list = xzcftjService.selectCFABByDay(day);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (CscfEntity entity : list) {
            // 将时间 进行格式化
            String date = (simpleDateFormat.format(entity.getCjsj())).toString();
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int gaojing = map.get("gaojing");
                // 进行判断
                if (entity.getAb()!= null) {
                    gaojing++;
                }
                map.clear();
                map.put("gaojing", gaojing);
                allMap.put(date, map);
            } else {
                int gaojing = 0;
                // 进行判断
                if (entity.getAb()!= null) {
                    gaojing++;
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("gaojing", gaojing);
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
        theCa.add(theCa.DATE, -day);//最后一个数字30可改，30天的意思
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
                map.put("gaojing", 0);
                allMap.put(string, map);
            }
        }
        return allMap;
    }
}
