package com.zhixin.zhfz.bacs.entity;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ReportViewOrder extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int total = 0;
		// 从model中获取数据对象    看押
		List<OrderRequestEntity> orderlist = (List<OrderRequestEntity>) model.get("orderlist");

		// 创建工作表对象并命名
		HSSFSheet sheet = workbook.createSheet("数据统计");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置行列的默认宽度和高度
		sheet.setColumnWidth(0, 20 * 80);//  序号
		sheet.setColumnWidth(1, 40 * 80);  //预约编号
		sheet.setColumnWidth(2, 40 * 80);   //预约民警
		sheet.setColumnWidth(3, 60 * 80);   //预约时间
		sheet.setColumnWidth(4, 60 * 80);   //预约电话
		sheet.setColumnWidth(5, 40 * 80);   //办案单位
		sheet.setColumnWidth(6, 64 * 80);   //办案场所
		sheet.setColumnWidth(7, 64 * 80);   //案件类型
		sheet.setColumnWidth(8, 40 * 80);   //案件性质
		sheet.setColumnWidth(9, 40 * 80);   //嫌疑人
		sheet.setColumnWidth(10, 80 * 80);   //证件号码
		sheet.setColumnWidth(11, 20 * 80);   //性别

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
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 设置长文本自动换行
		headerStyle.setWrapText(true);
		headerStyle.setFont(font);
		HSSFCell xuhaoHeader = headerRow.createCell(0);
		xuhaoHeader.setCellValue("序号");
		xuhaoHeader.setCellStyle(headerStyle);

		HSSFCell personNameHeader = headerRow.createCell(1);
		personNameHeader.setCellValue("预约编号");
		personNameHeader.setCellStyle(headerStyle);

		HSSFCell sexHeader = headerRow.createCell(2);
		sexHeader.setCellValue("预约民警");
		sexHeader.setCellStyle(headerStyle);

		HSSFCell waitingRoomHeader = headerRow.createCell(3);
		waitingRoomHeader.setCellValue("预约时间");
		waitingRoomHeader.setCellStyle(headerStyle);

		HSSFCell phoneRoomHeader = headerRow.createCell(4);
		phoneRoomHeader.setCellValue("预约电话");
		phoneRoomHeader.setCellStyle(headerStyle);

		HSSFCell caseTypeHeader = headerRow.createCell(5);
		caseTypeHeader.setCellValue("办案单位");
		caseTypeHeader.setCellStyle(headerStyle);
		
		HSSFCell casePropertiesHeader = headerRow.createCell(6);
		casePropertiesHeader.setCellValue("办案场所");
		casePropertiesHeader.setCellStyle(headerStyle);

		HSSFCell ajlxHeader = headerRow.createCell(7);
		ajlxHeader.setCellValue("案件类型");
		ajlxHeader.setCellStyle(headerStyle);

		HSSFCell policemanHeader = headerRow.createCell(8);
		policemanHeader.setCellValue("案件性质");
		policemanHeader.setCellStyle(headerStyle);
		
		
		HSSFCell organizationHeader = headerRow.createCell(9);
		organizationHeader.setCellValue("嫌疑人");
		organizationHeader.setCellStyle(headerStyle);
		
		
		HSSFCell tonganHeader = headerRow.createCell(10);
		tonganHeader.setCellValue("证件号码");
		tonganHeader.setCellStyle(headerStyle);

		HSSFCell xbHeader = headerRow.createCell(11);
		xbHeader.setCellValue("性别");
		xbHeader.setCellStyle(headerStyle);
		
		
		if(orderlist!=null&&orderlist.size()>0){
			total = total + orderlist.size();

			// 设置表体样式
			HSSFCellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
			bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
			bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
			bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
			bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
			bodyStyle.setWrapText(true);
			HSSFFont bodyFont = workbook.createFont();
			bodyFont.setFontHeightInPoints((short)11);
			bodyStyle.setFont(bodyFont);


			for (int i = 0; i < orderlist.size(); i++) {
				OrderRequestEntity obj = orderlist.get(i);
				// 开始创建单元格并赋值 ,序号
				// 创建行
					Row row = sheet.createRow(i + 1);
					Cell xuhaoCell = row.createCell(0);
					xuhaoCell.setCellValue(i+1);
					xuhaoCell.setCellStyle(bodyStyle);

					//预约编号
					Cell personNameCell = row.createCell(1);
					if (obj.getOrderNo()!=null && !"".equals(obj.getOrderNo())) {
						personNameCell.setCellValue(obj.getOrderNo());
					}
					personNameCell.setCellStyle(bodyStyle);

					//预约民警
					Cell waitingRoomCell = row.createCell(2);
					if (obj.getPersonName() != null && !"".equals(obj.getPersonName())) {
						waitingRoomCell.setCellValue(obj.getPersonName());
					}
					waitingRoomCell.setCellStyle(bodyStyle);

					// 预约时间
					Cell casePropertiesCell = row.createCell(3);
					if (obj.getCreatedTime()!=null && !"".equals(obj.getCreatedTime())) {
						casePropertiesCell.setCellValue(sdf.format(obj.getCreatedTime()));
					}
					casePropertiesCell.setCellStyle(bodyStyle);

					// 预约电话
					Cell timePropertiesCell = row.createCell(4);
					if (obj.getUserMobile()!=null && !"".equals(obj.getUserMobile())) {
						timePropertiesCell.setCellValue(obj.getUserMobile());
					}
					timePropertiesCell.setCellStyle(bodyStyle);

					//办案单位
					Cell orgNmameCell = row.createCell(5);
					if (obj.getOrganizationName()!=null && !"".equals(obj.getOrganizationName())) {
						orgNmameCell.setCellValue(obj.getOrganizationName());
					}
					orgNmameCell.setCellStyle(bodyStyle);

					//办案场所
					Cell areaNameCell = row.createCell(6);
					if (obj.getOrganizationName()!=null && !"".equals(obj.getOrganizationName())) {
						areaNameCell.setCellValue(obj.getOrganizationName());
					}
					areaNameCell.setCellStyle(bodyStyle);

					// 案件类型 0刑事 1行政
					Cell caseTypeCell = row.createCell(7);
					if (obj.getAjlx()!=null) {
						if(obj.getAjlx() == 2){
							caseTypeCell.setCellValue("刑事");
						}
						if(obj.getAjlx() == 1){
							caseTypeCell.setCellValue("行政");
						}
					}
					caseTypeCell.setCellStyle(bodyStyle);

					//案件性质
					Cell caseNatureCell = row.createCell(8);
					if (obj.getCaseNature()!=null && !"".equals(obj.getCaseNature())) {
						caseNatureCell.setCellValue(obj.getCaseNature());
					}
					caseNatureCell.setCellStyle(bodyStyle);
					//嫌疑人
					Cell nameCell = row.createCell(9);
					if (obj.getName()!=null && !"".equals(obj.getName())) {
						nameCell.setCellValue(obj.getName());
					}
					nameCell.setCellStyle(bodyStyle);

					//证件号码
					Cell cnoCell = row.createCell(10);
					if (obj.getCertificateNo()!=null && !"".equals(obj.getCertificateNo())) {
						cnoCell.setCellValue(obj.getCertificateNo());
					}
					cnoCell.setCellStyle(bodyStyle);

					//性别
					Cell sexCell = row.createCell(11);
					if (obj.getSex()!=null) {

						if(obj.getSex() == 0){
							sexCell.setCellValue("未知的性别");
						}
						if(obj.getSex() == 1){
							sexCell.setCellValue("男");
						}
						if(obj.getSex() == 2){
							sexCell.setCellValue("女");
						}
						if(obj.getSex() == 9){
							sexCell.setCellValue("未说明的性别");
						}
					}
					sexCell.setCellStyle(bodyStyle);
			}


			// 最后一行统计
			// 创建行
			Row row = sheet.createRow(orderlist.size()+1);
			row.setHeight((short)(25 * 20));
			Cell countCell = row.createCell(10);
			countCell.setCellValue("预约人数统计");
			countCell.setCellStyle(bodyStyle);
			
			Cell countNumCell = row.createCell(11);
			countNumCell.setCellValue(orderlist.size() + " 人");
			countNumCell.setCellStyle(bodyStyle);
			
			/*Cell caseTypexzCell4 = row.createCell(3);
			caseTypexzCell4.setCellValue(totalxz);
			caseTypexzCell4.setCellStyle(bodyStyle);
			
			Cell caseTypexzCell5 = row.createCell(4);
			caseTypexzCell5.setCellValue(total);
			caseTypexzCell5.setCellStyle(bodyStyle);*/
			
			
			//合并
//					for(int k=0;k<regionList.size();k+=2){
//						sheet.addMergedRegion(new CellRangeAddress(regionList.get(k),regionList.get(k+1),8,8));
//						Row regionRow = sheet.getRow(regionList.get(k));
//						Cell regionCell = regionRow.getCell(8);
//						regionCell.setCellStyle(bodyStyle);
//						regionCell.setCellValue(regionList.get(k+1)-regionList.get(k)+1+"人");
//					}
			
		}
	
	}
}
