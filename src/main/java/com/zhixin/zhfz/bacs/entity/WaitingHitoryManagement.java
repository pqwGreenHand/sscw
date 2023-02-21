package com.zhixin.zhfz.bacs.entity;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class WaitingHitoryManagement extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {

		int total = 0;
		int j = 0;
		int i = 0;
		//设置时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 从model中获取数据对象
		List<WaitingRecordEntity> waitingHistorylist = (List<WaitingRecordEntity>) model.get("waitingHitorylist");

		// 创建工作表对象并命名
		HSSFSheet sheet = workbook.createSheet("侯问室历史记录");

		// 设置行列的默认宽度和高度
		sheet.setColumnWidth(0, 20 * 80);//  序号
		sheet.setColumnWidth(1, 40 * 80);  //嫌疑人姓名
		sheet.setColumnWidth(2, 20 * 80);   //性别
		sheet.setColumnWidth(3, 80 * 80);   //证件号码
		sheet.setColumnWidth(4, 30 * 80);   //侯问室
		sheet.setColumnWidth(5, 40 * 80);   //案件类型
		sheet.setColumnWidth(6, 64 * 80);   //案件性质
		sheet.setColumnWidth(7, 40 * 80);   //状态
		sheet.setColumnWidth(8, 40 * 80);   //送押民警
		sheet.setColumnWidth(9, 40 * 80);   //提讯民警
		sheet.setColumnWidth(10, 64 * 80);   //进入时间
		sheet.setColumnWidth(11, 64 * 80);   //离开时间
		sheet.setColumnWidth(12, 64 * 80);   //去向


		// 创建表头
		HSSFRow headerRow = sheet.createRow(0);
		// 创建样式  表头
		HSSFFont font = workbook.createFont();
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		// 设置垂直居中
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置边框
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		// 字体加粗
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 设置长文本自动换行
		headerStyle.setWrapText(true);
		headerStyle.setFont(font);
		HSSFCell xuhaoHeader = headerRow.createCell(0);
		xuhaoHeader.setCellValue("序号");
		xuhaoHeader.setCellStyle(headerStyle);

		HSSFCell personNameHeader = headerRow.createCell(1);
		personNameHeader.setCellValue("嫌疑人姓名");
		personNameHeader.setCellStyle(headerStyle);

		HSSFCell sexHeader = headerRow.createCell(2);
		sexHeader.setCellValue("性别");
		sexHeader.setCellStyle(headerStyle);

		HSSFCell certificateNoHeader = headerRow.createCell(3);
		certificateNoHeader.setCellValue("证件号码");
		certificateNoHeader.setCellStyle(headerStyle);

		HSSFCell waitingRoomHeader = headerRow.createCell(4);
		waitingRoomHeader.setCellValue("侯问室");
		waitingRoomHeader.setCellStyle(headerStyle);

		HSSFCell caseTypeHeader = headerRow.createCell(5);
		caseTypeHeader.setCellValue("案件类型");
		caseTypeHeader.setCellStyle(headerStyle);

		HSSFCell casePropertiesHeader = headerRow.createCell(6);
		casePropertiesHeader.setCellValue("案件性质");
		casePropertiesHeader.setCellStyle(headerStyle);

		HSSFCell statusHeader = headerRow.createCell(7);
		statusHeader.setCellValue("状态");
		statusHeader.setCellStyle(headerStyle);

		HSSFCell policemanHeader = headerRow.createCell(8);
		policemanHeader.setCellValue("送押民警");
		policemanHeader.setCellStyle(headerStyle);

		HSSFCell getUserName1Header = headerRow.createCell(9);
		getUserName1Header.setCellValue("提讯民警");
		getUserName1Header.setCellStyle(headerStyle);

		HSSFCell inTimeHeader = headerRow.createCell(10);
		inTimeHeader.setCellValue("进入时间");
		inTimeHeader.setCellStyle(headerStyle);

		HSSFCell outTimeHeader = headerRow.createCell(11);
		outTimeHeader.setCellValue("离开时间");
		outTimeHeader.setCellStyle(headerStyle);

		HSSFCell getResult = headerRow.createCell(12);
		getResult.setCellValue("去向");
		getResult.setCellStyle(headerStyle);
		// 设置表体样式
		HSSFCellStyle bodyStyle = workbook.createCellStyle();
		
		// 设置表体样式
		HSSFCellStyle hcs = workbook.createCellStyle();
		
		if (waitingHistorylist != null && waitingHistorylist.size() > 0) {
			total = total + waitingHistorylist.size();

			// 设置表体样式
			//HSSFCellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
			bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
			bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
			bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
			bodyStyle.setWrapText(true);
			HSSFFont bodyFont = workbook.createFont();
			bodyFont.setFontHeightInPoints((short) 11);
			bodyStyle.setFont(bodyFont);


			// 遍历集合对象创建行和单元格
			Integer recordId = waitingHistorylist.get(0).getRecordId();

			List<Integer> regionList = new ArrayList<Integer>();
			boolean flag = true;
			while (true) {
				j = j + 1;
				WaitingRecordEntity obj = waitingHistorylist.get(i);
				if (i != 0 && !(recordId == obj.getRecordId())) {
					recordId = obj.getRecordId();
					Row row = sheet.createRow(j);
					regionList.add(j - 1);
					// 设置表体样式
					//HSSFCellStyle hcs = workbook.createCellStyle();
					hcs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					hcs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
					for (int ci = 0; ci < 13; ci++) {
						Cell cell = row.createCell(ci);
						cell.setCellStyle(hcs);
					}
					flag = true;
					j = j + 1;
				}

				// 创建行
				if (flag) {
					regionList.add(j);
					flag = false;
				}
				Row row = sheet.createRow(j);
				row.setHeight((short) (25 * 20));
				// 开始创建单元格并赋值 ,序号
				Cell xuhaoCell = row.createCell(0);
				xuhaoCell.setCellValue(i + 1);
				xuhaoCell.setCellStyle(bodyStyle);

				//姓名
				Cell personNameCell = row.createCell(1);
				if (obj.getPersonName() != null && !"".equals(obj.getPersonName())) {
					personNameCell.setCellValue(obj.getPersonName());
				}
				personNameCell.setCellStyle(bodyStyle);

				//性别
				Cell sexCell = row.createCell(2);
				if (obj.getSex() != null) {

					if (obj.getSex() == 0) {
						sexCell.setCellValue("未知的性别");
					}
					if (obj.getSex() == 1) {
						sexCell.setCellValue("男");
					}
					if (obj.getSex() == 2) {
						sexCell.setCellValue("女");
					}
					if (obj.getSex() == 9) {
						sexCell.setCellValue("未说明的性别");
					}
				}
				sexCell.setCellStyle(bodyStyle);

				//证件号码
				Cell certificateNoCell = row.createCell(3);
				if (obj.getPersoncertNo() != null && !"".equals(obj.getPersoncertNo())) {
					certificateNoCell.setCellValue(obj.getPersoncertNo());
				}
				certificateNoCell.setCellStyle(bodyStyle);


				//侯问室
				Cell waitingRoomCell = row.createCell(4);
				if (obj.getRoomName() != null && !"".equals(obj.getRoomName())) {
					waitingRoomCell.setCellValue(obj.getRoomName());
				}
				waitingRoomCell.setCellStyle(bodyStyle);


				// 案件类型 0刑事 1行政
				Cell caseTypeCell = row.createCell(5);
				if (obj.getCaseType() != null) {
					if (obj.getCaseType().equals("2")) {
						caseTypeCell.setCellValue("刑事");
					}else if (obj.getCaseType().equals("1")) {
						caseTypeCell.setCellValue("行政");
					}else{
						caseTypeCell.setCellValue("其他");
					}
				}
				caseTypeCell.setCellStyle(bodyStyle);

				// 案件性质
				Cell casePropertiesCell = row.createCell(6);
				if (obj.getCaseNature() != null && !"".equals(obj.getCaseNature())) {
					casePropertiesCell.setCellValue(obj.getCaseNature());
				}
				casePropertiesCell.setCellStyle(bodyStyle);

				// 状态
				Cell statusCell = row.createCell(7);
				if (obj.getStatus() != null) {
					if (obj.getStatus() == 0){
						statusCell.setCellValue("已看押");
					}
					if (obj.getStatus() == 1){
						statusCell.setCellValue("已提讯");
					}
				}
				statusCell.setCellStyle(bodyStyle);

				//送押民警
				Cell policemanCell = row.createCell(8);
				if (obj.getSendUserName1() != null && !"".equals(obj.getSendUserName1())) {
					policemanCell.setCellValue(obj.getSendUserName1());
				}
				policemanCell.setCellStyle(bodyStyle);

				//提讯民警
				Cell getUser1Cell = row.createCell(9);
				String getUserName = "";
				if (obj.getGetUserName1() != null && !"".equals(obj.getGetUserName1())) {
					getUserName += obj.getGetUserName1();
				}
				if (!"".equals(getUserName) && getUserName.length()>0) {
					getUserName += ",";
				}
				if (obj.getGetUserName2() != null && !"".equals(obj.getGetUserName2())) {
					getUserName += obj.getGetUserName2();
				}
				getUser1Cell.setCellValue(getUserName);
				getUser1Cell.setCellStyle(bodyStyle);

				//进入时间
				Cell inTimeCell = row.createCell(10);
				if (obj.getInTime() != null) {
					inTimeCell.setCellValue(sdf.format(obj.getInTime()));
				}
				inTimeCell.setCellStyle(bodyStyle);

				//离开时间
				Cell outTimeCell = row.createCell(11);
				if (obj.getOutTime() != null) {
					outTimeCell.setCellValue(sdf.format(obj.getOutTime()));
				}
				outTimeCell.setCellStyle(bodyStyle);

				//去向
				Cell getResultCell = row.createCell(12);
				if (obj.getGetResult() != null && !"".equals(obj.getGetResult())) {
					getResultCell.setCellValue(obj.getGetResult());
				}
				getResultCell.setCellStyle(bodyStyle);

				i = i+1;
				if(i >= waitingHistorylist.size()){
					regionList.add(j);
					break;
				}
			}
		}
		exportExcel2Brower(workbook,response,"候问历史记录");
	}

	//发送响应流方法
	private void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void exportExcel2Brower(HSSFWorkbook workbook, HttpServletResponse response,String filename) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowDate = sdf.format(new Date());
		String fileName = "attachment; filename="+filename+".xls";
		OutputStream out = null;
		try {
			response.setHeader("Content-disposition", new String(fileName.getBytes("gbk"),
					"ISO-8859-1"));
			response.setContentType("application/msexcel;charset=GBK");
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
