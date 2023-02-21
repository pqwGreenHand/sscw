package com.zhixin.zhfz.zhag.controller.xzajtj;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import com.zhixin.zhfz.zhag.services.xzajtj.XzajtjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zhixin.zhfz.jzgl.controller.jzlxRate.JzlxRateController.getDatesBetweenTwoDate;

@Controller
@RequestMapping("/zhfz/zhag/xzajtj")
public class XzajtjController {

    @Autowired
    private XzajtjService xzajtjService;

    /**
     * 案件来源
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXZAJSourceByDay")
    @ResponseBody
    public MessageEntity quyXZAJSourceByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzajtjService.quyXZAJSourceByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXZAJSourceByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXZAJSourceByDay查询成功!").addCallbackData(list);
    }

    /**
     * 案件类型
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXZAJTypeByDay")
    @ResponseBody
    public MessageEntity quyXZAJTypeByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzajtjService.quyXZAJTypeByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXZAJTypeByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXZAJTypeByDay查询成功!").addCallbackData(list);
    }

    /**
     * 案件状态
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXZAJStateByDay")
    @ResponseBody
    public MessageEntity quyXZAJStateByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzajtjService.quyXZAJStateByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXZAJStateByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXZAJStateByDay查询成功!").addCallbackData(list);
    }

    /**
     * 案由
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXZAJCauseByDay")
    @ResponseBody
    public MessageEntity quyXZAJCauseByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzajtjService.quyXZAJCauseByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXZAJCauseByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXZAJCauseByDay查询成功!").addCallbackData(list);
    }

    /**
     * 刑事案件数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXZAJAllNumberByDay")
    @ResponseBody
    public MessageEntity quyXZAJAllNumberByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzajtjService.quyXZAJAllNumberByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXZAJAllNumberByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXZAJAllNumberByDay查询成功!").addCallbackData(list);
    }

    /**
     * 告警类型
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXZGJTypeByDay")
    @ResponseBody
    public MessageEntity quyXZGJTypeByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<GjxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xzajtjService.quyXZGJTypeByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXZGJTypeByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXZGJTypeByDay查询成功!").addCallbackData(list);
    }
    /**
     * 查询多少天内受理立案折线图
     *
     * @param day
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectSLLAByDay")
    @ResponseBody
    public Map<String, Map<String, Integer>> selectSLLAByDay(Integer day, HttpServletRequest request,
                                                             HttpServletResponse response) throws Exception {
        List<CriminalAndAdministrationCaseEntity> list = xzajtjService.selectSLLAByDay(day);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (CriminalAndAdministrationCaseEntity entity : list) {
            // 将时间 进行格式化
            String date = (simpleDateFormat.format(entity.getCreatedTime())).toString();
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int shouli = map.get("shouli");
                // 进行判断
                if (entity.getAjlx() != null && entity.getAjlx().toString() != "") {
                        shouli++;
                }
                map.clear();
                map.put("shouli", shouli);
                allMap.put(date, map);
            } else {
                int shouli = 0;
                // 进行判断
                if (entity.getAjlx() != null && entity.getAjlx().toString() != "") {
                        shouli++;
                    }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("shouli", shouli);
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
                map.put("shouli", 0);
                allMap.put(string, map);
            }
        }
        return allMap;
    }
    /**
     * 查询多少天告警折线图
     *
     * @param day
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectXSGJByDay")
    @ResponseBody
    public Map<String, Map<String, Integer>> selectXSGJByDay(Integer day, HttpServletRequest request,
                                                             HttpServletResponse response) throws Exception {
        List<GjxxEntity> list = xzajtjService.selectXSGJByDay(day);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (GjxxEntity entity : list) {
            // 将时间 进行格式化
            String date = (simpleDateFormat.format(entity.getCjsj())).toString();
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int gaojing = map.get("gaojing");
                // 进行判断
                if (Integer.parseInt(entity.getGjly()) == 1) {
                    gaojing++;
                }
                map.clear();
                map.put("gaojing", gaojing);
                allMap.put(date, map);
            } else {
                int gaojing = 0;
                // 进行判断
                if (Integer.parseInt(entity.getGjly()) == 1) {
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
