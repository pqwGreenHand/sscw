package com.zhixin.zhfz.zhag.controller.jqtj;

import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import com.zhixin.zhfz.zhag.services.jqtj.IJqtjService;
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
@RequestMapping("/zhfz/zhag/jqtj")
public class JqtjController {


    @Autowired
    private IJqtjService jqtjService;

    /**
     * 查询多少天内接警处警折线图
     *
     * @param day
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectJCJByDay")
    @ResponseBody
    public Map<String, Map<String, Integer>> selectJCJByDay(Integer day, HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {
        List<JqxxEntity> list = jqtjService.selectJCJByDay(day);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (JqxxEntity entity : list) {
            // 将时间 进行格式化
            String date = (simpleDateFormat.format(entity.getCjsj())).toString();
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int chujing = map.get("chujing");
                int jiejing = map.get("jiejing");
                // 进行判断
                if (entity.getJjsj() != null && entity.getJjsj().toString() != "") {
                    jiejing++;
                }
                if (entity.getJcjsj() != null && entity.getJcjsj().toString() != "") {
                    chujing++;
                }
                map.clear();
                map.put("chujing", chujing);
                map.put("jiejing", jiejing);
                allMap.put(date, map);
            } else {
                int chujing = 0;
                int jiejing = 0;
                // 进行判断
                if (entity.getJjsj() != null && entity.getJjsj().toString() != "") {
                    jiejing++;
                }
                if (entity.getJcjsj() != null && entity.getJcjsj().toString() != "") {
                    chujing++;
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("chujing", chujing);
                map.put("jiejing", jiejing);
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
                map.put("chujing", 0);
                map.put("jiejing", 0);
                allMap.put(string, map);
            }
        }
        return allMap;
    }

    /**
     * 处理结果
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyHandleResultByDay")
    @ResponseBody
    public MessageEntity listTotalByOutputType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<JqxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = jqtjService.quyHandleResultByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyHandleResultByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyHandleResultByDay查询成功!").addCallbackData(list);
    }

    /**
     * 警情类别
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyJQTypeByDay")
    @ResponseBody
    public MessageEntity quyJQTypeByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<JqxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = jqtjService.quyJQTypeByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyJQTypeByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyJQTypeByDay查询成功!").addCallbackData(list);
    }

    /**
     * 接警方式
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyJJMethedByDay")
    @ResponseBody
    public MessageEntity quyJJMethedByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<JqxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = jqtjService.quyJJMethedByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyJJMethedByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyJJMethedByDay查询成功!").addCallbackData(list);
    }

    /**
     * 受理单位
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quySLCompanyByDay")
    @ResponseBody
    public MessageEntity quySLCompanyByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<JqxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = jqtjService.quySLCompanyByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quySLCompanyByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quySLCompanyByDay查询成功!").addCallbackData(list);
    }

    /**
     * 总警情数
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyAllJQNumberByDay")
    @ResponseBody
    public MessageEntity quyAllJQNumberByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<JqxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = jqtjService.quyAllJQNumberByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyAllNumberByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyAllNumberByDay查询成功!").addCallbackData(list);
    }

    /**
     * 处警单位
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/quyCJCompanyByDay")
    @ResponseBody
    public MessageEntity quyCJCompanyByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<JqxxEntity> list = null;
        //   查询范围
        int day = Integer.parseInt(request.getParameter("day").toString());

        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);

        try {
            list = jqtjService.quyCJCompanyByDay(day);
        } catch (Exception e) {
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("quyCJCompanyByDay查询异常!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("quyCJCompanyByDay查询成功!").addCallbackData(list);
    }
    /**
     * 查询多少天内接警处警折线图
     *
     * @param day
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectCLByDay")
    @ResponseBody
    public Map<String, Map<String, Integer>> selectCLByDay(Integer day, HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {
        List<JqxxEntity> list = jqtjService.selectCLByDay(day);
        //按天格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Map<String, Integer>> allMap = new TreeMap<String, Map<String, Integer>>();
        //遍历获取每个人员信息
        for (JqxxEntity entity : list) {
            // 将时间 进行格式化
            String date = (simpleDateFormat.format(entity.getCjsj())).toString();
            // 判断allMap里面是不是有这个key
            if (allMap.containsKey(date)) {
                Map<String, Integer> map = allMap.get(date);
                int shouli = map.get("shouli");
                int weishouli = map.get("weishouli");
                // 进行判断
                if (entity.getSlsj() != null && entity.getSlsj().toString() != "") {
                    shouli++;
                }else{
                    weishouli++;
                }
                map.clear();
                map.put("shouli", shouli);
                map.put("weishouli", weishouli);
                allMap.put(date, map);
            } else {
                int shouli = 0;
                int weishouli = 0;
                // 进行判断
                if (entity.getSlsj() != null && entity.getSlsj().toString() != "") {
                    shouli++;
                }else{
                    weishouli++;
                }
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("shouli", shouli);
                map.put("weishouli", weishouli);
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
                map.put("weishouli", 0);
                allMap.put(string, map);
            }
        }
        return allMap;
    }

}
