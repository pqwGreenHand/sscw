package com.zhixin.zhfz.zhag.controller.xsajtj;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import com.zhixin.zhfz.zhag.services.xsajtj.IXsajtjService;
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
@RequestMapping("/zhfz/zhag/xsajtj")
public class XsajtjController {

    @Autowired
    private IXsajtjService xsajtjService;
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
        List<CriminalAndAdministrationCaseEntity> list = xsajtjService.selectSLLAByDay(day);
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
                int lian = map.get("lian");
                // 进行判断
                if (entity.getAjlx() != null && entity.getAjlx().toString() != "") {
                    if(entity.getLasj() != null && entity.getLasj().toString() != ""){
                        shouli++;
                    }else{
                        lian++;
                    }
                }
                map.clear();
                map.put("shouli", shouli);
                map.put("lian", lian);
                allMap.put(date, map);
            } else {
                int shouli = 0;
                int lian = 0;
                // 进行判断
                if (entity.getAjlx() != null && entity.getAjlx().toString() != "") {
                    if(entity.getLasj() != null && entity.getLasj().toString() != ""){
                        shouli++;
                    }else{
                        lian++;
                    }
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("shouli", shouli);
                map.put("lian", lian);
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
                map.put("lian", 0);
                allMap.put(string, map);
            }
        }
        return allMap;
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
    @RequestMapping(value = "/selectXSGJByDay")
    @ResponseBody
    public Map<String, Map<String, Integer>> selectXSGJByDay(Integer day, HttpServletRequest request,
                                                             HttpServletResponse response) throws Exception {
        List<GjxxEntity> list = xsajtjService.selectXSGJByDay(day);
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
                if (Integer.parseInt(entity.getGjly()) == 2) {
                    gaojing++;
                }
                map.clear();
                map.put("gaojing", gaojing);
                allMap.put(date, map);
            } else {
                int gaojing = 0;
                // 进行判断
                if (Integer.parseInt(entity.getGjly()) == 2) {
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
     * 案件来源
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyAJSourceByDay")
    @ResponseBody
    public MessageEntity quyAJSourceByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xsajtjService.quyAJSourceByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyAJSourceByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyAJSourceByDay查询成功!").addCallbackData(list);
    }

    /**
     * 案件类型
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyAJTypeByDay")
    @ResponseBody
    public MessageEntity quyAJTypeByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xsajtjService.quyAJTypeByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyAJTypeByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyAJTypeByDay查询成功!").addCallbackData(list);
    }

    /**
     * 案件状态
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyAJStateByDay")
    @ResponseBody
    public MessageEntity quyAJStateByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xsajtjService.quyAJStateByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyAJStateByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyAJStateByDay查询成功!").addCallbackData(list);
    }

    /**
     * 案由
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyAJCauseByDay")
    @ResponseBody
    public MessageEntity quyAJCauseByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xsajtjService.quyAJCauseByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyAJCauseByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyAJCauseByDay查询成功!").addCallbackData(list);
    }

    /**
     * 刑事案件数量
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyXSAJAllNumberByDay")
    @ResponseBody
    public MessageEntity quyXSAJAllNumberByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<CriminalAndAdministrationCaseEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xsajtjService.quyXSAJAllNumberByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyXSAJAllNumberByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyXSAJAllNumberByDay查询成功!").addCallbackData(list);
    }

    /**
     * 告警类型
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyGJTypeByDay")
    @ResponseBody
    public MessageEntity quyGJTypeByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<GjxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = xsajtjService.quyGJTypeByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyGJTypeByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyGJTypeByDay查询成功!").addCallbackData(list);
    }
}
