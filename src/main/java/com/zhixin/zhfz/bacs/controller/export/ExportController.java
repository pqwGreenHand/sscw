package com.zhixin.zhfz.bacs.controller.export;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.SerialForm;
import com.zhixin.zhfz.bacs.services.export.BaFileMappingService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.common.Base64Util;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhixin.zhfz.bacs.services.export.IExportService;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;

/*
 * 数据导出
 * */
@Controller
@RequestMapping("/zhfz/bacs/export")
public class ExportController {

    private static Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private IExportService exportService;
    @Autowired
    private ISerialService serialService;
    @Autowired
    private BaFileMappingService baFileMappingService;

    /**
     * 生成报表数据返回spring配置文件中进行数据整合形成下载文件
     * 看押   审讯    入口   其他
     */
    @RequestMapping(value = "/exportExcelForOrder")
    public ModelAndView exportExcelForOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("预约记录", "UTF-8") + ".xls");
        Map<String, Object> map = new HashMap<String, Object>();
        String areaId = request.getParameter("areaId");
        if (areaId != null && !"".equals(areaId)) {
            map.put("areaId", areaId);
        }
        String userName = request.getParameter("userName");
        if (userName != null && !"".equals(userName)) {
            map.put("userName", userName);
        }
        String orderNo = request.getParameter("orderNo");
        if (orderNo != null && !"".equals(orderNo)) {
            map.put("orderNo", orderNo);
        }
        String personName = request.getParameter("personName");
        if (personName != null && !"".equals(personName)) {
            map.put("personName", personName);
        }

        List<OrderRequestEntity> orderlist = this.exportService.listOrderExport(map);
        String strs = "下载成功";
        request.setAttribute("strs", strs);
        Map<String, List> mode = new HashMap<String, List>();
        mode.put("orderlist", orderlist);
        return new ModelAndView("reportViewOrder", mode);
    }

    /**
     * 生成报表数据返回spring配置文件中进行数据整合形成下载文件
     * 看押   审讯    入口   其他
     */
    @RequestMapping(value = "/exportExcelForWaiting")
    public ModelAndView waitingRecordExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("交班记录", "UTF-8") + ".xls");
        Map<String, Object> map = new HashMap<String, Object>();
        String areaId = request.getParameter("areaId");
        if (areaId != null && !"".equals(areaId)) {
            map.put("areaId", areaId);
        }
        List<AskManagementExportEntity> waitinglist = this.exportService.listWaitingRecord(map);
        List<AskManagementExportEntity> interrogaterecordlist = this.exportService.listInterrogateRecord(map);
        List<AskManagementExportEntity> rqList = this.exportService.listInterrogate0(map);
        List<AskManagementExportEntity> elseList = this.exportService.listInterrogateElse(map);
        List<AskManagementExportEntity> tempOutList = this.exportService.listTempOut(map);
        //if ((waitinglist != null && waitinglist.size() > 0) || (interrogaterecordlist != null && interrogaterecordlist.size() > 0)) {
            String strs = "下载成功";
            request.setAttribute("strs", strs);
            Map<String, List> mode = new HashMap<String, List>();
            mode.put("waitinglist", waitinglist);
            mode.put("interrogaterecord", interrogaterecordlist);
            mode.put("rqList", rqList);
            mode.put("elseList", elseList);
            mode.put("tempOutList", tempOutList);
            return new ModelAndView("reportViewAskManage", mode);
        /*} else {
            return null;
        }*/
    }

    /**
     * 导出侯问室历史记录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/waitingmanageExcel")
    public ModelAndView waitingmanageExcel(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String areaId = request.getParameter("areaId");
        if (areaId != null && !"".equals(areaId)) {
            map.put("areaId", areaId);
        }
        String personName = request.getParameter("personName");
        if (personName != null && !"".equals(personName)) {
            map.put("personName", personName);
        }
        String personcertNo = request.getParameter("personcertNo");
            if (personcertNo != null && !"".equals(personcertNo)) {
            map.put("personcertNo", personcertNo);
        }
        String roomId = request.getParameter("roomId");
        if (roomId != null && !"".equals(roomId)) {
            map.put("roomId", roomId);
        }
        String beginTime = request.getParameter("beginTime");
        if (beginTime != null && !"".equals(beginTime)) {
            map.put("beginTime", beginTime);
        }
        String endTime = request.getParameter("endTime");
        if (endTime != null && !"".equals(endTime)) {
            map.put("endTime", endTime);
        }
        List<WaitingRecordEntity> waitingHitorylist = exportService.listRecord(map);
        String strs = "下载成功";
        request.setAttribute("strs", strs);
        Map<String, List> mode = new HashMap<String, List>();
        mode.put("waitingHitorylist", waitingHitorylist);
        return new ModelAndView("waitingHitoryManagement", mode);
    }


    /*@RequestMapping(value="/waitingmanageExcel")
    @ResponseBody
    public String viodownload(@RequestParam Map<String, Object> param, HttpSession session) throws Exception {
        Map<String, Object> map = ControllerTool.mapFilter(param);
        List<WaitingRecordEntity> list = exportService.listRecord(map);
        String wjjurl = "C://统计导出Excel";
        String creaturl = "C://统计导出Excel/侯问室历史记录";
        String creaturlold = creaturl;
        File wjj = new File(wjjurl);
        if(!wjj.exists()) {
            wjj.mkdirs();
        }
        boolean fb = false;
        try {
            WritableFont bold = new WritableFont(WritableFont.ARIAL,14,WritableFont.BOLD);//设置字体种类和黑体显示,字体为Arial,字号大小为14,采用黑体显示
            WritableCellFormat titleFormate = new WritableCellFormat(bold);//生成一个单元格样式控制对象
            titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平方向居中
            titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
            titleFormate.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//边框
            titleFormate.setWrap(true);

            WritableFont bold2 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
            WritableCellFormat titleFormate2 = new WritableCellFormat(bold2);//生成一个单元格样式控制对象
            titleFormate2.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平方向居中
            titleFormate2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
            titleFormate2.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//边框
            titleFormate2.setWrap(true);

            WritableFont bold3 = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD);
            WritableCellFormat titleFormate3 = new WritableCellFormat(bold3);//生成一个单元格样式控制对象
            titleFormate3.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平方向居中
            titleFormate3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直方向居中
            titleFormate3.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//边框
            titleFormate3.setWrap(true);

            int num = 2;
            while(!fb) {
                File file = new File(creaturl+".xls");
                fb = file.createNewFile();
                if(fb==false) {
                    creaturl = creaturlold + "(" +(num++)+")";
                }
            }
            WritableWorkbook book = Workbook
                    .createWorkbook(new File(creaturl+".xls"));

            WritableSheet sheet1 = book.createSheet("侯问室历史记录", 0);

            sheet1.mergeCells(0,0, 11, 0);//合并单元格

            Label label = new Label(0, 0, "侯问室历史记录",titleFormate);
            sheet1.addCell(label);
            sheet1.setRowView(0, 700);//设置第一行的高度

            Label label31 = new Label(0, 1, "嫌疑人",titleFormate2);
            sheet1.addCell(label31);
            sheet1.setColumnView(0, 20);
            Label label3 = new Label(1, 1, "案件类型",titleFormate2);
            sheet1.addCell(label3);
            sheet1.setColumnView(1, 20);
            Label label5 = new Label(2, 1, "案件性质",titleFormate2);
            sheet1.addCell(label5);
            sheet1.setColumnView(2, 40);
            Label label6 = new Label(3, 1, "性别",titleFormate2);
            sheet1.addCell(label6);
            sheet1.setColumnView(3, 20);
            Label label7 = new Label(4, 1, "证件号码",titleFormate2);
            sheet1.addCell(label7);
            sheet1.setColumnView(4, 20);
            Label label8 = new Label(5, 1, "侯问室",titleFormate2);
            sheet1.addCell(label8);
            sheet1.setColumnView(5, 20);
            Label label9 = new Label(6, 1, "状态",titleFormate2);
            sheet1.addCell(label9);
            sheet1.setColumnView(6, 20);
            Label label10 = new Label(7, 1, "押送民警",titleFormate2);
            sheet1.addCell(label10);
            sheet1.setColumnView(7, 20);
            Label label11 = new Label(8, 1, "提讯民警",titleFormate2);
            sheet1.addCell(label11);
            sheet1.setColumnView(8, 20);
            Label label12 = new Label(9, 1, "进入时间",titleFormate2);
            sheet1.addCell(label12);
            sheet1.setColumnView(9, 20);
            Label label13 = new Label(10, 1, "离开时间",titleFormate2);
            sheet1.addCell(label13);
            sheet1.setColumnView(10, 20);
            Label label14 = new Label(11, 1, "去向",titleFormate2);
            sheet1.addCell(label14);
            sheet1.setColumnView(11, 20);

            int num2 = -1;
            for(WaitingRecordEntity i:list) {
                num2++;
                Label s22 = new Label(0, 2+num2, i.getPersonName(),titleFormate3);
                sheet1.addCell(s22);
                String CaseType = "";
                if (i.getCaseType().equals("0")){
                    CaseType="刑事";
                }
                if (i.getCaseType().equals("1")){
                    CaseType="行政";
                }
                if (i.getCaseType().equals("3")){
                    CaseType="警情";
                }
                Label s = new Label(1, 2+num2,CaseType,titleFormate3);
                sheet1.addCell(s);
                Label s2 = new Label(2, 2+num2, i.getCaseNature(),titleFormate3);
                sheet1.addCell(s2);
                String sex = "";
                if(i.getSex()==0){
                    sex="未知性别";
                }else if (i.getSex()==1){
                    sex="男";
                }else if (i.getSex()==2){
                    sex="女";
                }else if (i.getSex()==9){
                    sex="未说明的性别";
                }
                Label s3 = new Label(3, 2+num2, sex,titleFormate3);
                sheet1.addCell(s3);
                Label s4 = new Label(4, 2+num2, i.getPersoncertNo().toString(),titleFormate3);
                sheet1.addCell(s4);


                String roomname1 = i.getGetResult();
                String RoomName = "";
                String Result = "";
                if (roomname1!=null&&roomname1!="") {
                    boolean ii = roomname1.contains("&*&");
                    if (ii) {
                        String str[] = roomname1.split("&*&");
                        RoomName =  str[0];
                        Result = str[2];
                    }else{
                        RoomName = i.getRoomName().toString();
                        Result = i.getGetResult().toString();
                    }
                }
                Label s5 = new Label(5, 2+num2,RoomName ,titleFormate3);
                sheet1.addCell(s5);
                String status = "";
                if (i.getStatus().toString()==""||i.getStatus().toString()==null){
                    status = " ";
                }else{
                    if(i.getStatus()==0){
                        status = "已看押";
                    }else if (i.getStatus()==1){
                        status = "已提讯";
                    }else if (i.getStatus()==2){
                        status = "预约";
                    }

                }
                Label s6 = new Label(6, 2+num2, status,titleFormate3);
                sheet1.addCell(s6);
                Label s7 = new Label(7, 2+num2, i.getSendUserName1().toString(),titleFormate3);
                sheet1.addCell(s7);
                String username = "";
                if (i.getGetUserName1()==""||i.getGetUserName1()==null){
                    username = " ";
                }else{
                    username = i.getGetUserName1().toString();
                }
                Label s8 = new Label(8, 2+num2, username,titleFormate3);
                sheet1.addCell(s8);

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date1=sdf.format(i.getInTime());
                Label s9 = new Label(9, 2+num2, date1 ,titleFormate3);
                sheet1.addCell(s9);
                String date2;
                if (i.getOutTime()!=null){
                    date2=sdf.format(i.getOutTime());
                }else{
                    date2="";
                }

                Label s10 = new Label(10, 2+num2, date2,titleFormate3);
                sheet1.addCell(s10);
                Label s11 = new Label(11, 2+num2,Result,titleFormate3);
                sheet1.addCell(s11);
            }

            book.write();
            book.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "0";
        }

        if(fb) {
            session.setAttribute("filepath", creaturl+".xls");
            session.setAttribute("filename", "侯问室历史记录");
            return "1";
        }else {
            return "0";
        }
    }

    @RequestMapping("/downLoadExcel")
    public void downLoadExcel(HttpServletResponse res, HttpSession session){
        String path = (String)session.getAttribute("filepath");
        String filename = (String)session.getAttribute("filename");
        File file = new File(path);
        try {
            res.setContentType("application/vnd.ms-excel;charset=utf-8");
            res.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(filename+".xls", "UTF-8"));
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = res.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
            in.close();
            file.delete();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
    
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        logger.info("数据导出查询");
        List<Map<String, Object>> datas = new ArrayList<>();

        int total = 0;
        boolean flag = true;

        Map<String, Object> map = ControllerTool.mapFilter(param);

        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ins.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", ControllerTool.getAreasInSql("ins.area_id",
                    ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        }

        datas = this.exportService.listByExport(map);

        total = this.exportService.listByExportCount(map);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }

    @RequestMapping(value = "/expOutTimeExecl")
    public void expExecl( HttpServletRequest request,
                         HttpServletResponse response) {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();//创建HSSFWorkbook对象
            HSSFSheet sheet = wb.createSheet("分析结果");//建立sheet对象
            HSSFRow headRow = sheet.createRow(0);
            //创建单元格并设置单元格内容
            headRow.createCell(0).setCellValue("姓名");
            headRow.createCell(1).setCellValue("办案民警");
            headRow.createCell(2).setCellValue("办案单位");
            headRow.createCell(3).setCellValue("入区时间");
            headRow.createCell(4).setCellValue("首次审讯时间");
            Map<String, Object> map = new HashMap<String, Object>();
            String name = request.getParameter("name");
            if (name != null && !"".equals(name)) {
                map.put("name", name);
            }
            String stratTime = request.getParameter("stratTime");
            if (stratTime != null && !"".equals(stratTime)) {
                map.put("stratTime", stratTime);
            }
            String endTime = request.getParameter("endTime");
            if (endTime != null && !"".equals(endTime)) {
                map.put("endTime", endTime);
            }
            String hour = request.getParameter("hour");
            if (hour != null && !"".equals(hour)) {
                map.put("hour", hour);
            }
            int tempI = 0;
            List<SerialEntity> data = null;
            try {
                data = this.serialService.timeoutRecodelist(map);
            } catch (Exception e) {
                e.printStackTrace();
            };
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (data != null && data.size() > 0) {
                for (SerialEntity tempMap : data) {
                    HSSFRow row = sheet.createRow(++tempI);
                    row.createCell(0).setCellValue(tempMap.getName());
                    row.createCell(1).setCellValue(tempMap.getInUserRealName1());
                    row.createCell(2).setCellValue(tempMap.getOrganization());
                    row.createCell(3).setCellValue(format.format(tempMap.getInTime()));
                    String  sxDate= tempMap.getSxDate()!=null?format.format(tempMap.getSxDate()):"未在办案中心审讯";
                    row.createCell(4).setCellValue(sxDate);
                }
            }
                //输出Excel文件
                OutputStream output = null;
                try {
                    output = response.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.reset();
                String fileName = encodeFileName("审讯超时导出.xls", request);
                response.setContentType("application/ms-excel;charset=utf-8");
                response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                wb.write(output);
                output.close();
            } catch(IOException e){
                logger.info("应进未进导出错误：" + e);
            }
        }
    /**
     * @param   fileNames
     * @param   request
     * @Description: 导出文件转换文件名称编码
     */
    public static String encodeFileName(String fileNames, HttpServletRequest request) {
        String codedFilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident") || null != agent && -1 != agent.indexOf("Edge")) {// ie浏览器及Edge浏览器
                String name = java.net.URLEncoder.encode(fileNames, "UTF-8");
                codedFilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                // 火狐,Chrome等浏览器
                codedFilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedFilename;
    }

    /*@RequestMapping(value = "/exportZip")
    @ResponseBody
    public Map<String,Object> wsexport(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("台账打包下载开始++++++++++++++++++++++++++++++++");
        List<ExportEntity> datas = new ArrayList<>();
        String userAgent = request.getHeader("User-Agent");
        String fileName = UUID.randomUUID().toString()+".zip";
        String msg = "下载成功";
        Map<String, Object> map = ControllerTool.mapFilter(param);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ins.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", ControllerTool.getAreasInSql("ins.area_id",
                    ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        }

        datas = this.exportService.listquery(map);
        logger.info("台账打包下载人员数据："+datas);
        StringBuffer str=new StringBuffer();
        str.append(" ( ");
        for (int i=0;i<datas.size();i++){
            ExportEntity exportEntity = datas.get(i);
            str.append(" '"+exportEntity.getSerialId()+"' ");
            if(i!=datas.size()-1){
                str.append(" , ");
            }
        }
        str.append(" ) ");
        String subsysId = str.toString();
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("subsysId",subsysId);
        List<BaFileMappingEntity> baFileMappingEntityList = baFileMappingService.queryList(queryMap);
        logger.info("台账打包下载文件数据："+datas);
        ArrayList files = new ArrayList();
        for (int j=0;j<baFileMappingEntityList.size();j++){
            BaFileMappingEntity baFileMappingEntity = baFileMappingEntityList.get(j);
            files.add(new File(baFileMappingEntity.getFilePath()));
        }
        *//*files.add(new File("D:/var/us.txt"));*//*
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String dd=dateFormat.format(date);
        String filePath = PropertyUtil.getContextProperty("downZipTempFileSavePath")+dd+"-"+UUID.randomUUID().toString()+".zip";
        Map<String, Object> mode = new HashMap<String, Object>();
        try {
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);// 压缩方式
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);// 压缩级别
            *//*parameters.setEncryptFiles( false );*//*
            *//*parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);*//*
            *//*parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);*//*

            ZipFile zipFile = new ZipFile(filePath);
            zipFile.addFiles(files,parameters);

        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString()+"--------------------");
        }
        File file = new File(filePath);

        //如果文件不存在，提示404
        try {
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
            }
            if (file.exists()) {
                response.setContentType("binary/octet-stream");
                response.setHeader("Content-Disposition",
                        "attachment; " + "filename=" + fileName);
                OutputStream os = response.getOutputStream();
                os.write(FileUtils.readFileToByteArray(file));
                os.flush();
                if (os != null) {
                    os.close();
                }
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            logger.info(e1.toString()+"+++++++++++++++++");
        }
        return mode;
    }*/


    @RequestMapping(value = "/exportZip")
    @ResponseBody
    public void wsexport( HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("台账打包下载开始++++++++++++++++++++++++++++++++");
        List<Map<String, Object>> datas = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put("name",request.getParameter("name"));
        param.put("sex",request.getParameter("sex"));
        param.put("ajlx",request.getParameter("ajlx"));
        param.put("abmc",request.getParameter("abmc"));
        Map<String, Object> map = ControllerTool.mapFilter(param);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ins.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", ControllerTool.getAreasInSql("ins.area_id",
                    ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        }

        map.put("pageStart", 0);
        map.put("rows", 999);
        List<Map<String, Object>> personList = this.exportService.listByExport(map);
        if (personList!=null && personList.size()>0){
            for (int i = 0; i < personList.size(); i++) {
                datas.addAll(this.exportService.listBelongDetailByPersonId(Long.valueOf(personList.get(i).get("id")+"")));
            }
        }
        logger.info("台账下载数据："+datas);
        try {
            HSSFWorkbook wb = new HSSFWorkbook();//创建HSSFWorkbook对象
            HSSFSheet sheet = wb.createSheet("物品数据");//建立sheet对象
            HSSFRow headRow = sheet.createRow(0);
            //创建单元格并设置单元格内容
            headRow.createCell(0).setCellValue("嫌疑人姓名");
            headRow.createCell(1).setCellValue("证件号码");
            headRow.createCell(2).setCellValue("物品名称");
            headRow.createCell(3).setCellValue("物品数量");
            headRow.createCell(4).setCellValue("物品单位");
            headRow.createCell(5).setCellValue("是否已领取");
            headRow.createCell(6).setCellValue("领取方式");
            headRow.createCell(7).setCellValue("领取人姓名");
            headRow.createCell(8).setCellValue("领取时间");
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (datas != null && datas.size() > 0) {
                int tempI = 0;
                for (Map<String, Object> tempMap : datas) {
                    HSSFRow row = sheet.createRow(++tempI);
                    row.createCell(0).setCellValue(tempMap.get("name")+"");
                    row.createCell(1).setCellValue(tempMap.get("certificate_no")+"");
                    row.createCell(2).setCellValue(tempMap.get("wpName")+"");
                    row.createCell(3).setCellValue(tempMap.get("detail_count")+"");
                    row.createCell(4).setCellValue(tempMap.get("unit")+"");
                    Integer isGet = Integer.valueOf(tempMap.get("is_get")+"");
                    row.createCell(5).setCellValue(isGet==1?"已领取":"未领取");

                    // 0 未领取，1本人领取，2委托他人代为保管，3本人收到扣押物品清单4转涉案财物5移交
                    Integer getWay = Integer.valueOf(tempMap.get("get_way")+"");
                    String getWayStr = "";
                    switch (getWay){
                        case 0 : getWayStr = "未领取";break;
                        case 1 : getWayStr = "本人领取";break;
                        case 2 : getWayStr = "委托他人代为保管";break;
                        case 3 : getWayStr = "本人收到扣押物品清单";break;
                        case 4 : getWayStr = "转涉案财物";break;
                        case 5 : getWayStr = "移交";break;
                        default: getWayStr = "其他";
                    }
                    row.createCell(6).setCellValue(getWayStr);
                    row.createCell(7).setCellValue(tempMap.get("get_person")+"");
                    row.createCell(8).setCellValue(tempMap.get("get_time")+"");
                }
            }
            for (int i = 0; i < 9; i++) {
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(i,sheet.getColumnWidth(i)*17/10);
            }
            //输出Excel文件
            OutputStream output = null;
            try {
                output = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.reset();
            String fileName = encodeFileName("物品数据表.xls", request);
            response.setContentType("application/ms-excel;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            wb.write(output);
            output.close();
        } catch(IOException e){
            logger.info("应进未进导出错误：" + e);
        }
    }

/*
    @RequestMapping(value = "/exportZip")
    @ResponseBody
    public void wsexport(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("台账打包下载开始++++++++++++++++++++++++++++++++");
        List<ExportEntity> datas = new ArrayList<>();
        String userAgent = request.getHeader("User-Agent");
        String msg = "下载成功";
        Map<String, Object> map = ControllerTool.mapFilter(param);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ins.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", ControllerTool.getAreasInSql("ins.area_id",
                    ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        }

        datas = this.exportService.listquery(map);
        logger.info("台账打包下载人员数据："+datas);
        String subsysId = "";
        ArrayList filezip = new ArrayList();
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String dd=dateFormat.format(date);
        String filePaths="";
        String zipname=dd+"-"+UUID.randomUUID().toString()+".zip";
        filePaths = PropertyUtil.getContextProperty("downZipTempFileSavePath")+zipname;
        OutputStream os = response.getOutputStream();
        for (int i=0;i<datas.size();i++){
            String name=PropertyUtil.getContextProperty("downZipTempFileSavePath")+dd+"-"+UUID.randomUUID().toString();
            File file = new File(name);
            file.mkdir();
            ExportEntity exportEntity = datas.get(i);
            subsysId = "('"+exportEntity.getSerialId()+"')";
            Map<String,Object> queryMap = new HashMap<>();
            queryMap.put("subsysId",subsysId);
            List<BaFileMappingEntity> baFileMappingEntityList = baFileMappingService.queryList(queryMap);
            for (int j=0;j<baFileMappingEntityList.size();j++){

                BaFileMappingEntity baFileMappingEntity = baFileMappingEntityList.get(j);
                */
/*InputStream fis = new BufferedInputStream(new FileInputStream(baFileMappingEntity.getFilePath()));*//*
*/
/*
                File file3 = new File(baFileMappingEntity.getUrl());
                response.setContentType("application/x-download");
                response.setCharacterEncoding("UTF-8");
                String fileName = baFileMappingEntity.getUrl().substring(baFileMappingEntity.getUrl().lastIndexOf("/")+1);
                File file4 = new File(name+"/"+fileName);
                response.addHeader("Content-Disposition", "attachment;filename=" +fileName);
                OutputStream output = response.getOutputStream();
                FileUtils.copyFile(file3, file4);*//*

                String fileName = baFileMappingEntity.getUrl().substring(baFileMappingEntity.getUrl().lastIndexOf("/")+1);
                FileOutputStream fileOut = null;
                HttpURLConnection conn = null;
                InputStream inputStream = null;
                URL httpUrl=new URL(baFileMappingEntity.getUrl());
                conn=(HttpURLConnection) httpUrl.openConnection();
                //以Post方式提交表单，默认get方式
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                // post方式不能使用缓存
                conn.setUseCaches(false);
                //连接指定的资源
                conn.connect();
                //获取网络输入流
                inputStream=conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                fileOut = new FileOutputStream(name+"/"+fileName);
                BufferedOutputStream bos = new BufferedOutputStream(fileOut);

                byte[] buf = new byte[4096];
                int length = bis.read(buf);
                //保存文件
                while(length != -1)
                {
                    bos.write(buf, 0, length);
                    length = bis.read(buf);
                }
                bos.close();
                bis.close();
                conn.disconnect();
            }
            */
/*filePath = PropertyUtil.getContextProperty("downZipTempFileSavePath")+dd+"-"+UUID.randomUUID().toString()+".zip";*//*

            try {
                ZipParameters parameter = new ZipParameters();
                parameter.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);// 压缩方式
                parameter.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);// 压缩级别

                ZipParameters parameters = new ZipParameters();
                parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);// 压缩方式
                parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);// 压缩级别
                ZipFile zipFile1 = new ZipFile(filePaths);
                zipFile1.addFolder(file,parameters);
            }catch (Exception e){
                e.printStackTrace();
                logger.info(e.toString()+"--------------------");
            }
            finally {
                os.close();
            }
            filezip.add(file);
            FileUtils.deleteDirectory(file);
        }
        logger.info("台账打包下载文件数据："+datas);

        */
/*files.add(new File("D:/var/us.txt"));*//*

        File file = new File(filePaths);
        //如果文件不存在，提示404
        try {
            if (file.exists()) {
                response.setContentType("binary/octet-stream");
                response.setHeader("Content-disposition", "attachment; filename=" + new String(zipname.getBytes("utf-8"), "ISO8859-1"));
                OutputStream os2 = response.getOutputStream();
                os2.write(FileUtils.readFileToByteArray(file));
                os2.flush();
                if (os2 != null) {
                    os2.close();
                }
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        Map<String, Object> mode = new HashMap<String, Object>();

        */
/*File file = new File(filePaths);

        //如果文件不存在，提示404
        try {
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
            }
            if (file.exists()) {
                response.setContentType("binary/octet-stream");
                response.setHeader("Content-Disposition",
                        "attachment; " + "filename=" + fileName);
                OutputStream os = response.getOutputStream();
                os.write(FileUtils.readFileToByteArray(file));
                os.flush();
                if (os != null) {
                    os.close();
                }
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            logger.info(e1.toString()+"+++++++++++++++++");
        }*//*

        */
/*return mode;*//*

    }
*/


    @RequestMapping(value = "/exportExcel")
    public ModelAndView exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("进入查询！！！！！！！！！！！！！！！！jyouttime" + request.getParameter("jyouttime"));
//        String filename = new String("数据报表".getBytes(), "iso8859-1");
//        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("数据统计", "UTF-8") + ".xls");
        Map<String, Object> map = new HashMap<String, Object>();
        String personName = request.getParameter("personName1");
        if (personName != null && !"".equals(personName)) {
            map.put("personName", personName);
        }
        String personType = request.getParameter("personType1");

        if (personType != null && !"".equals(personType)) {
            System.err.println("---personType---" + personType);
            map.put("personType", personType);
        }
        String personSex = request.getParameter("personSex1");
        System.err.println(",-----personSex-----" + personSex);
        if (personSex != null && !"".equals(personSex)) {
            map.put("personSex", personSex);
        }
        String caseType = request.getParameter("caseType1");
        if (caseType != null && !"".equals(caseType)) {
            map.put("caseType", caseType);
        }
        String caseProperties = request.getParameter("caseProperties1");
        if (caseProperties != null && !"".equals(caseProperties)) {
            map.put("caseProperties", caseProperties);
        }
        String workSpace = request.getParameter("workSpace1");
        if (workSpace != null && !"".equals(workSpace)) {
            map.put("workSpace", workSpace);
        }
        String betweenTime = request.getParameter("betweenTime1");
        if (betweenTime != null && !"".equals(betweenTime)) {
            map.put("betweenTime", betweenTime);
        }
        String betweenTimeMax = request.getParameter("betweenTimeMax1");
        if (betweenTimeMax != null && !"".equals(betweenTimeMax)) {
            map.put("betweenTimeMax", betweenTimeMax);
        }

        String outBetweenTime = request.getParameter("outBetweenTime1");
        if (outBetweenTime != null && !"".equals(outBetweenTime)) {
            map.put("outBetweenTime", outBetweenTime);
        }
        String outBetweenTimeMax = request.getParameter("outBetweenTimeMax1");
        if (outBetweenTimeMax != null && !"".equals(outBetweenTimeMax)) {
            map.put("outBetweenTimeMax", outBetweenTimeMax);
        }
        //交班记录
        String workbetweenTime = request.getParameter("workbetweenTime1");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (workbetweenTime != null && !"".equals(workbetweenTime)) {
            map.put("workbetweenTimea", workbetweenTime);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 24);
            Date beforeTime = df.parse(df.format(calendar.getTime()));
            String beforeTimeStr = df.format(beforeTime);
            if (beforeTimeStr != null && !"".equals(beforeTimeStr)) {
                map.put("workbetweenTimeb", beforeTimeStr);
            }
            System.err.println("=====workbetweenTimeb=====" + beforeTimeStr + "======workbetweenTimea======" + workbetweenTime);
        }

        String subjectType = request.getParameter("subjectType1");
        if (subjectType != null && !"".equals(subjectType)) {
            map.put("subjectType", subjectType);
        } 
        String subjectTime = request.getParameter("subjectTime1");
        if (subjectTime != null && !"".equals(subjectTime)) {
            map.put("subjectTime", subjectTime);
        }
        

        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ins.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", ControllerTool.getAreasInSql("ins.area_id",
                    ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        }
        String tempString = request.getParameter("tempString");
        List<ExportEntity> list = new ArrayList<ExportEntity>();
        List<ExportEntity> list1 = new ArrayList<ExportEntity>();
        List<ExportEntity> list2 = new ArrayList<ExportEntity>();
        if (request.getParameter("jyouttime") != null && request.getParameter("jyouttime") != null) {
            list1 = this.exportService.listother1(map);

            list2 = this.exportService.listother2(map);

            list.addAll(list1);
            list.addAll(list2);
        } else {
            list = this.exportService.listother(map);
        }
        System.err.println("---------------------------" + list.size());
     //   List<AskManagementExportEntity> waitinglist = this.exportService.listWaitingRecord(map);
        Map<String, List> mode = new HashMap<String, List>();
        if (list.size() > 0 && list != null) {
        	mode.put("list", list);
        	String strs = "下载成功";
        	request.setAttribute("strs", strs);
        	request.setAttribute("tempString", tempString);
//        	mode.put("waitinglist", waitinglist);
        	return new ModelAndView("reportView", mode);
//            System.err.println("---------------------------" + list.size());
//            Map<String, List> mode = new HashMap<String, List>();
//            mode.put("list", list);
//            mode.put("waitinglist", waitinglist);
//            
//            if (request.getParameter("jyouttime") != null && request.getParameter("jyouttime") != "") {
//            	System.err.println("22222222");
//                return new ModelAndView("reportOuttimeView", mode);
//            } else {
//            	System.err.println("111111");
//            	return new ModelAndView("reportViewAskManage", mode);
////                return new ModelAndView("reportViewAskManage", mode);
//            }
        } else {

            return null;
        }
    }
    //台账list
    @RequestMapping(value = "/accountList")
    @ResponseBody
    public Map<String, Object> accountList(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        logger.info("------------台账数据导出--------");
        List<ExportEntity> datas = new ArrayList<>();
        int total = 0;
        Map<String, Object> map = ControllerTool.mapFilter(param);
        if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " ins.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", ControllerTool.getAreasInSql("ins.area_id",
                    ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
            //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
            map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
        }

        datas = this.exportService.accountList(map);

        total = this.exportService.accountCount(map);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("rows", datas);
        return result;
    }
    



    /**
     * type =(nian,yue,ri)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/accountExcel")
	public ModelAndView accountExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    System.err.println("进入查询！！！！！！！！！！！！！！！！type==" + request.getParameter("type"));
	    String type = request.getParameter("type");
	    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("台账", "UTF-8") + ".xls");
	    Map<String, Object> map = new HashMap<String, Object>();
	    String startTime = request.getParameter("startTime");
	    if (startTime != null && !"".equals(startTime)) {
	        map.put("startTime", startTime);
	    }
	    String endTime = request.getParameter("endTime");
	    if (endTime != null && !"".equals(endTime)) {
	        map.put("endTime", endTime);
	    }
	
	    if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
	        // 办案场所-本办案场所
	        map.put("dataAuth", " ins.area_id=" + ControllerTool.getCurrentAreaID(request));
	    } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
	        // 管理员 -本办案场所及下级办案场所
	        map.put("dataAuth", ControllerTool.getAreasInSql("ins.area_id",
	                ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
	    } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
	        // 公安领导-本部门及下级部门
	        String sql = ControllerTool
	                .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
	        //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
	        map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
	    } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
	        // 法制人员-上级部门及下级部门
	        String sql = ControllerTool
	                .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
	        //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
	        map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
	    } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
	        // 本部门
	        String sql = ControllerTool
	                .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
	        //map.put("dataAuth", "ins.in_user_id1 " + sql + " or ins.in_register_user_id " + sql);
	        map.put("dataAuth", " (ints.in_register_user_id " + sql + " or ints.out_register_user_id " + sql + " ) ");
	    }
	    List<ExportEntity> list =  exportService.accountExport(map);
	    System.err.println("---------------------------" + list.size());
	    Map<String, Object> mode = new HashMap<String, Object>();
	    if (list.size() > 0 && list != null) {
	    	mode.put("list", list);
	    	mode.put("type", type);
	    	mode.put("time", getMonthBetween(startTime,endTime));
	    	String strs = "下载成功";
	    	request.setAttribute("strs", strs);
	    	//日报表
	    	ReportViewAccount reportview = new ReportViewAccount();
	    	return new ModelAndView(reportview, mode);
	    }else {
	    	return null;
	    }
	
	}
	
	/**
	 * 
	 * @param minDate 最小时间  2015-01
	 * @param maxDate 最大时间 2015-10
	 * @return 日期集合 格式为 年-月
	 * @throws Exception
	 */
	private  List<String> getMonthBetween(String minDate, String maxDate) throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
		if(minDate==null || minDate.equals("")) {
			minDate = sdf.format(new Date());
		}
		if(maxDate==null || maxDate.equals("")) {
			maxDate = sdf.format(new Date());
		}
 
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
 
		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
 
		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
 
		Calendar curr = min;
		while (curr.before(max)) {
		 result.add(sdf.format(curr.getTime()));
		 curr.add(Calendar.MONTH, 1);
		}
 
		return result;
	}
	
}
