package com.zhixin.zhfz.bacs.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;


public class ReportViewManagement extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int total = 0;
		int j =0;
		int i =0;
		// 从model中获取数据对象    看押
		List<AskManagementExportEntity> waitinglist = (List<AskManagementExportEntity>) model.get("waitinglist");

		// 创建工作表对象并命名
		HSSFSheet sheet = workbook.createSheet("数据统计");

		// 设置行列的默认宽度和高度
		sheet.setColumnWidth(0, 20 * 80);//  序号
		sheet.setColumnWidth(1, 40 * 80);  //姓名
		sheet.setColumnWidth(2, 20 * 80);   //性别
		sheet.setColumnWidth(3, 30 * 80);   //看押室
		sheet.setColumnWidth(4, 40 * 80);   //案件类型
		sheet.setColumnWidth(5, 64 * 80);   //案件性质
		sheet.setColumnWidth(6, 40 * 80);   //送押民警
		sheet.setColumnWidth(7, 90 * 80);   //送押单位
		sheet.setColumnWidth(8, 20 * 80);   //同案
		

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
		personNameHeader.setCellValue("姓名");
		personNameHeader.setCellStyle(headerStyle);

		HSSFCell sexHeader = headerRow.createCell(2);
		sexHeader.setCellValue("性别");
		sexHeader.setCellStyle(headerStyle);

		HSSFCell waitingRoomHeader = headerRow.createCell(3);
		waitingRoomHeader.setCellValue("功能室");
		waitingRoomHeader.setCellStyle(headerStyle);

		HSSFCell caseTypeHeader = headerRow.createCell(4);
		caseTypeHeader.setCellValue("案件类型");
		caseTypeHeader.setCellStyle(headerStyle);
		
		HSSFCell casePropertiesHeader = headerRow.createCell(5);
		casePropertiesHeader.setCellValue("案件性质");
		casePropertiesHeader.setCellStyle(headerStyle);
		
		HSSFCell policemanHeader = headerRow.createCell(6);
		policemanHeader.setCellValue("送押民警");
		policemanHeader.setCellStyle(headerStyle);
		
		
		HSSFCell organizationHeader = headerRow.createCell(7);
		organizationHeader.setCellValue("送押单位");
		organizationHeader.setCellStyle(headerStyle);
		
		
		HSSFCell tonganHeader = headerRow.createCell(8);
		tonganHeader.setCellValue("同案");
		tonganHeader.setCellStyle(headerStyle);
		
		
		if(waitinglist!=null&&waitinglist.size()>0){
			total = total + waitinglist.size();
			
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
			

			// 遍历集合对象创建行和单元格
			String lawcaseId = waitinglist.get(0).getCaseId();
			
			List<Integer> regionList = new ArrayList<Integer>();
			boolean flag = true;
			while(true){
				j = j+1;
				//regionList.add(j);
				AskManagementExportEntity obj = waitinglist.get(i);
				
				if(i!=0 && !lawcaseId.equals(obj.getCaseId())){
					lawcaseId = obj.getCaseId();
					Row row = sheet.createRow(j);
				    regionList.add(j-1);	

					// 设置表体样式
					HSSFCellStyle hcs = workbook.createCellStyle();
//					hcs.setAlignment(CellStyle.ALIGN_CENTER);
//					hcs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//					hcs.setBorderTop(CellStyle.BORDER_THIN);
//					hcs.setBorderBottom(CellStyle.BORDER_THIN);
//					hcs.setBorderLeft(CellStyle.BORDER_THIN);
//					hcs.setBorderRight(CellStyle.BORDER_THIN);
//					HSSFFont ffont = workbook.createFont();
//					bodyFont.setFontHeightInPoints((short)11);
//					bodyStyle.setFont(bodyFont);
					hcs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//					hcs.setFillBackgroundColor(HSSFColor.PALE_BLUE.index);
					hcs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
					
					for(int ci=0;ci<9;ci++){
						Cell cell = row.createCell(ci);
						cell.setCellStyle(hcs);
					}
					
					flag=true;
					j = j+1;
				}
				
				// 创建行
				if(flag){
					regionList.add(j);
					flag=false;
				}
				Row row = sheet.createRow(j);
				row.setHeight((short)(25 * 20));
				// 开始创建单元格并赋值 ,序号
				Cell xuhaoCell = row.createCell(0);
				xuhaoCell.setCellValue(i+1);
				xuhaoCell.setCellStyle(bodyStyle);
				
				//姓名
				Cell personNameCell = row.createCell(1);
				if (obj.getPersonName()!=null && !"".equals(obj.getPersonName())) {
					personNameCell.setCellValue(obj.getPersonName());
				}
				personNameCell.setCellStyle(bodyStyle);
				
				//性别
				Cell sexCell = row.createCell(2);
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
				
				
				//看押室
				Cell waitingRoomCell = row.createCell(3);
				if (obj.getRoomName() != null && !"".equals(obj.getRoomName())) {
					waitingRoomCell.setCellValue(obj.getRoomName());
				}
				waitingRoomCell.setCellStyle(bodyStyle);
				
				
				// 案件类型 0刑事 1行政
				Cell caseTypeCell = row.createCell(4);
				if (obj.getAjlx()!=null) {
					if(obj.getAjlx() == 2){
						caseTypeCell.setCellValue("刑事");
					}
	                if(obj.getAjlx() == 1){
	                	caseTypeCell.setCellValue("行政");
					}
					/*if(obj.getAjlx() == 0){
						caseTypeCell.setCellValue("警情");
					}*/
				}
				caseTypeCell.setCellStyle(bodyStyle);

				// 案件性质
				Cell casePropertiesCell = row.createCell(5);
				if (obj.getCaseNature()!=null && !"".equals(obj.getCaseNature())) {
					casePropertiesCell.setCellValue(obj.getCaseNature());
				}
				casePropertiesCell.setCellStyle(bodyStyle);
				
				//送押民警
				Cell policemanCell = row.createCell(6);
				if (obj.getPoliceName()!=null && !"".equals(obj.getPoliceName())) {
					policemanCell.setCellValue(obj.getPoliceName());
				}
				policemanCell.setCellStyle(bodyStyle);
				
				
				//送押单位
				Cell organizationCell = row.createCell(7);
				if (obj.getOrgName()!=null && !"".equals(obj.getOrgName())) {
					organizationCell.setCellValue(obj.getOrgName());
				}
				organizationCell.setCellStyle(bodyStyle);
				
				
				//同案
				Cell tonganCell = row.createCell(8);
//			    tonganCell.setCellValue("是");
				tonganCell.setCellStyle(bodyStyle);

				i = i+1;
				if(i>=waitinglist.size()){
					regionList.add(j);
					break;
				}
			}
			
		
			

			// 最后一行统计
			// 创建行
			j = j+1;
			Row row = sheet.createRow(j);
			row.setHeight((short)(25 * 20));
			Cell countCell = row.createCell(7);
			countCell.setCellValue("看押人数统计");
			countCell.setCellStyle(bodyStyle);
			
			Cell countNumCell = row.createCell(8);
			countNumCell.setCellValue(waitinglist.size() + " 人");
			countNumCell.setCellStyle(bodyStyle);
			
			/*Cell caseTypexzCell4 = row.createCell(3);
			caseTypexzCell4.setCellValue(totalxz);
			caseTypexzCell4.setCellStyle(bodyStyle);
			
			Cell caseTypexzCell5 = row.createCell(4);
			caseTypexzCell5.setCellValue(total);
			caseTypexzCell5.setCellStyle(bodyStyle);*/
			
			
			//合并
					for(int k=0;k<regionList.size();k+=2){
						sheet.addMergedRegion(new CellRangeAddress(regionList.get(k),regionList.get(k+1),8,8));
						Row regionRow = sheet.getRow(regionList.get(k));
						Cell regionCell = regionRow.getCell(8);
						regionCell.setCellStyle(bodyStyle);
						regionCell.setCellValue(regionList.get(k+1)-regionList.get(k)+1+"人");
					}
			
		}
	


		
		
		
		
		
			
				
		
		// 从model中获取数据对象      审讯
		List<AskManagementExportEntity> recordList = (List<AskManagementExportEntity>) model.get("interrogaterecord");

		// 创建工作表对象并命名
//		HSSFSheet sheet1 = workbook.createSheet("审讯记录");
		
		// 设置行列的默认宽度和高度
//		sheet1.setColumnWidth(0, 20 * 80);//  序号
//		sheet1.setColumnWidth(1, 40 * 80);  //姓名
//		sheet1.setColumnWidth(2, 20 * 80);   //性别
//		sheet1.setColumnWidth(3, 36 * 80);   //审讯室
//		sheet1.setColumnWidth(4, 40 * 80);   //案件类型
//		sheet1.setColumnWidth(5, 64 * 80);   //案件性质
//		sheet1.setColumnWidth(6, 40 * 80);   //审讯民警
//				

				// 创建表头
//				HSSFRow headerRow1 = sheet1.createRow(0);
//				// 创建样式  表头
//				HSSFFont font1 = workbook.createFont();
//				HSSFCellStyle headerStyle1 = workbook.createCellStyle();
//				// 设置垂直居中
//				headerStyle1.setAlignment(CellStyle.ALIGN_CENTER);
//				headerStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//				// 设置边框
//				headerStyle1.setBorderTop(CellStyle.BORDER_THIN);
//				headerStyle1.setBorderBottom(CellStyle.BORDER_THIN);
//				headerStyle1.setBorderLeft(CellStyle.BORDER_THIN);
//				headerStyle1.setBorderRight(CellStyle.BORDER_THIN);
//				// 字体加粗
//				font1.setFontHeightInPoints((short)12);
//				font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
//				// 设置长文本自动换行
//				headerStyle1.setWrapText(true);
//				headerStyle1.setFont(font1);
//				HSSFCell xuhaoHeader1 = headerRow1.createCell(0);
//				xuhaoHeader1.setCellValue("序号");
//				xuhaoHeader1.setCellStyle(headerStyle1);
//
//				HSSFCell personNameHeader1 = headerRow1.createCell(1);
//				personNameHeader1.setCellValue("姓名");
//				personNameHeader1.setCellStyle(headerStyle1);
//
//				HSSFCell sexHeader1 = headerRow1.createCell(2);
//				sexHeader1.setCellValue("性别");
//				sexHeader1.setCellStyle(headerStyle1);
//
//				HSSFCell waitingRoomHeader1 = headerRow1.createCell(3);
//				waitingRoomHeader1.setCellValue("审讯室");
//				waitingRoomHeader1.setCellStyle(headerStyle1);
//
//				HSSFCell caseTypeHeader1 = headerRow1.createCell(4);
//				caseTypeHeader1.setCellValue("案件类型");
//				caseTypeHeader1.setCellStyle(headerStyle1);
//				
//				HSSFCell casePropertiesHeader1 = headerRow1.createCell(5);
//				casePropertiesHeader1.setCellValue("案件性质");
//				casePropertiesHeader1.setCellStyle(headerStyle1);
//				
//				HSSFCell policemanHeader1 = headerRow1.createCell(6);
//				policemanHeader1.setCellValue("审讯民警");
//				policemanHeader1.setCellStyle(headerStyle1);
				
				
			    if(recordList!=null && recordList.size()>0){
			    	total = total + recordList.size();
			    	j = j+1;
			    	sheet.createRow(j);

		    		// 设置表体样式
					HSSFCellStyle bodyStyle1 = workbook.createCellStyle();
					bodyStyle1.setAlignment(CellStyle.ALIGN_CENTER);
					bodyStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					bodyStyle1.setBorderTop(CellStyle.BORDER_THIN);
					bodyStyle1.setBorderBottom(CellStyle.BORDER_THIN);
					bodyStyle1.setBorderLeft(CellStyle.BORDER_THIN);
					bodyStyle1.setBorderRight(CellStyle.BORDER_THIN);
					bodyStyle1.setWrapText(true);
					HSSFFont bodyFont1 = workbook.createFont();
					bodyFont1.setFontHeightInPoints((short)11);
					bodyStyle1.setFont(bodyFont1);
					
			    	for(int ii=0;ii<recordList.size();ii++){
			    		AskManagementExportEntity obj = recordList.get(ii);
			    		
			    		j = j+1;
			    		i = i+1;
			    		
			    		Row row = sheet.createRow(j);
						row.setHeight((short)(25 * 20));
						// 开始创建单元格并赋值 ,序号
						Cell xuhaoCell = row.createCell(0);
						xuhaoCell.setCellValue(i);
						xuhaoCell.setCellStyle(bodyStyle1);
						
						//姓名
						Cell personNameCell = row.createCell(1);
						if (obj.getPersonName()!=null && !"".equals(obj.getPersonName())) {
							personNameCell.setCellValue(obj.getPersonName());
						}
						personNameCell.setCellStyle(bodyStyle1);
						
						//性别
						Cell sexCell = row.createCell(2);
						if (obj.getSex()!=null && !"".equals(obj.getSex())) {
							
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
						sexCell.setCellStyle(bodyStyle1);
						
						
						//审讯室
						Cell interrogateRoomCell = row.createCell(3);
						if (obj.getRoomName() != null && !"".equals(obj.getRoomName())) {
							interrogateRoomCell.setCellValue(obj.getRoomName());
						}
						interrogateRoomCell.setCellStyle(bodyStyle1);
						
						
						// 案件类型 0刑事 1行政
						Cell caseTypeCell = row.createCell(4);
						if (obj.getAjlx()!=null) {
							if(obj.getAjlx() == 0){
								caseTypeCell.setCellValue("刑事");
							}
			                if(obj.getAjlx() == 1){
			                	caseTypeCell.setCellValue("行政");
							}
							if(obj.getAjlx() == 2){
								caseTypeCell.setCellValue("警情");
							}
						}
						caseTypeCell.setCellStyle(bodyStyle1);

						// 案件性质
						Cell casePropertiesCell = row.createCell(5);
						if (obj.getCaseNature()!=null && !"".equals(obj.getCaseNature())) {
							casePropertiesCell.setCellValue(obj.getCaseNature());
						}
						casePropertiesCell.setCellStyle(bodyStyle1);
						
						//审讯民警
						Cell policemanCell = row.createCell(6);
						if (obj.getPoliceName()!=null && !"".equals(obj.getPoliceName())) {
							policemanCell.setCellValue(obj.getPoliceName());
						}
						policemanCell.setCellStyle(bodyStyle1);
						
						
						
						
						//送押单位
						Cell organizationCell = row.createCell(7);
						if (obj.getOrgName()!=null && !"".equals(obj.getOrgName())) {
							organizationCell.setCellValue(obj.getOrgName());
						}
						organizationCell.setCellStyle(bodyStyle1);
						
						
						
						//
						Cell tonganCell = row.createCell(8);
						tonganCell.setCellValue("");
						tonganCell.setCellStyle(bodyStyle1);
			    		
			    	}
			    	
			    	
			    	// 最后一行统计
					// 创建行
			    	j=j+1;
					Row row = sheet.createRow(j);
					row.setHeight((short)(25 * 20));
					Cell countCell = row.createCell(7);
					countCell.setCellValue("审讯人数统计");
					countCell.setCellStyle(bodyStyle1);
					
					Cell countNumCell = row.createCell(8);
					countNumCell.setCellValue(recordList.size() + " 人");
					countNumCell.setCellStyle(bodyStyle1);
			    }
				
				
			
			
		
		
		
				
				
				
				// 从model中获取数据对象       入区
				List<AskManagementExportEntity> rqList = (List<AskManagementExportEntity>) model.get("rqList");

				// 创建工作表对象并命名
//				HSSFSheet sheet2 = workbook.createSheet("入区记录");
				
//				// 设置行列的默认宽度和高度
//				sheet2.setColumnWidth(0, 20 * 80);//  序号
//				sheet2.setColumnWidth(1, 40 * 80);  //姓名
//				sheet2.setColumnWidth(2, 20 * 80);   //性别
//				sheet2.setColumnWidth(3, 36 * 80);   //送案时间
//				sheet2.setColumnWidth(4, 40 * 80);   //送案民警
//						

						// 创建表头
//						HSSFRow headerRow2 = sheet2.createRow(0);
//						// 创建样式  表头
//						HSSFFont font2 = workbook.createFont();
//						HSSFCellStyle headerStyle2 = workbook.createCellStyle();
//						// 设置垂直居中
//						headerStyle2.setAlignment(CellStyle.ALIGN_CENTER);
//						headerStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//						// 设置边框
//						headerStyle2.setBorderTop(CellStyle.BORDER_THIN);
//						headerStyle2.setBorderBottom(CellStyle.BORDER_THIN);
//						headerStyle2.setBorderLeft(CellStyle.BORDER_THIN);
//						headerStyle2.setBorderRight(CellStyle.BORDER_THIN);
//						// 字体加粗
//						font2.setFontHeightInPoints((short)12);
//						font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
//						// 设置长文本自动换行
//						headerStyle2.setWrapText(true);
//						headerStyle2.setFont(font2);
//						HSSFCell xuhaoHeader2 = headerRow2.createCell(0);
//						xuhaoHeader2.setCellValue("序号");
//						xuhaoHeader2.setCellStyle(headerStyle2);
//
//						HSSFCell personNameHeader2 = headerRow2.createCell(1);
//						personNameHeader2.setCellValue("姓名");
//						personNameHeader2.setCellStyle(headerStyle2);
//
//						HSSFCell sexHeader2 = headerRow2.createCell(2);
//						sexHeader2.setCellValue("性别");
//						sexHeader2.setCellStyle(headerStyle2);
//
//						HSSFCell timeHeader2 = headerRow2.createCell(3);
//						timeHeader2.setCellValue("送案时间");
//						timeHeader2.setCellStyle(headerStyle2);
//						
//						HSSFCell policemanHeader2 = headerRow2.createCell(4);
//						policemanHeader2.setCellValue("送案民警");
//						policemanHeader2.setCellStyle(headerStyle2);
						
						
					    if(rqList!=null && rqList.size()>0){
					    	
					    	total = total + rqList.size();
					    	j = j+1;
					    	sheet.createRow(j);

				    		// 设置表体样式
							HSSFCellStyle bodyStyle2 = workbook.createCellStyle();
							bodyStyle2.setAlignment(CellStyle.ALIGN_CENTER);
							bodyStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
							bodyStyle2.setBorderTop(CellStyle.BORDER_THIN);
							bodyStyle2.setBorderBottom(CellStyle.BORDER_THIN);
							bodyStyle2.setBorderLeft(CellStyle.BORDER_THIN);
							bodyStyle2.setBorderRight(CellStyle.BORDER_THIN);
							bodyStyle2.setWrapText(true);
							HSSFFont bodyFont2 = workbook.createFont();
							bodyFont2.setFontHeightInPoints((short)11);
							bodyStyle2.setFont(bodyFont2);;
							
					    	for(int ii=0;ii<rqList.size();ii++){
					    		AskManagementExportEntity obj = rqList.get(ii);
					    		
					    		j = j+1;
					    		i = i+1;
 					    		
					    		Row row = sheet.createRow(j);
								row.setHeight((short)(25 * 20));
								// 开始创建单元格并赋值 ,序号
								Cell xuhaoCell = row.createCell(0);
								xuhaoCell.setCellValue(i);
								xuhaoCell.setCellStyle(bodyStyle2);
								
								//姓名
								Cell personNameCell = row.createCell(1);
								if (obj.getPersonName()!=null && !"".equals(obj.getPersonName())) {
									personNameCell.setCellValue(obj.getPersonName());
								}
								personNameCell.setCellStyle(bodyStyle2);
								
								//性别
								Cell sexCell = row.createCell(2);
								if (obj.getSex()!=null && !"".equals(obj.getSex())) {
									
									if("0".equals(obj.getSex())){
										sexCell.setCellValue("未知的性别");	
									}
									if("1".equals(obj.getSex())){
										sexCell.setCellValue("男");	
									}
									if("2".equals(obj.getSex())){
										sexCell.setCellValue("女");	
									}
									if("9".equals(obj.getSex())){
										sexCell.setCellValue("未说明的性别");	
									}
								}
								sexCell.setCellStyle(bodyStyle2);
								
								
								//
								Cell interrogateRoomCell = row.createCell(3);
								interrogateRoomCell.setCellValue("新入区");
								interrogateRoomCell.setCellStyle(bodyStyle2);
								
								
								// 
								Cell caseTypeCell = row.createCell(4);
					            caseTypeCell.setCellValue("");
								caseTypeCell.setCellStyle(bodyStyle2);

								// 
								Cell casePropertiesCell = row.createCell(5);
							    casePropertiesCell.setCellValue("");
								casePropertiesCell.setCellStyle(bodyStyle2);
								
								//送案民警
								Cell policemanCell = row.createCell(6);
								if (obj.getPoliceName()!=null && !"".equals(obj.getPoliceName())) {
									policemanCell.setCellValue(obj.getPoliceName());
								}
								policemanCell.setCellStyle(bodyStyle2);
								
								
								
								
								//送押单位
								Cell organizationCell = row.createCell(7);
								if (obj.getOrgName()!=null && !"".equals(obj.getOrgName())) {
									organizationCell.setCellValue(obj.getOrgName());
								}
								organizationCell.setCellStyle(bodyStyle2);
								
								
								
								//
								Cell tonganCell = row.createCell(8);
								tonganCell.setCellValue("");
								tonganCell.setCellStyle(bodyStyle2);
								
								
//								//送案时间
//								Cell interrogateCell = row.createCell(3);
//								if (obj.getInterrogateRoom() != null && !"".equals(obj.getInterrogateRoom() )) {
//									interrogateCell.setCellValue(obj.getInterrogateRoom() );
//								}
//								interrogateCell.setCellStyle(bodyStyle2);
//								
//								
//				
//								
//								//送案民警
//								Cell policemanCell = row.createCell(4);
//								if (obj.getPoliceman()!=null && !"".equals(obj.getPoliceman())) {
//									policemanCell.setCellValue(obj.getPoliceman());
//								}
//								policemanCell.setCellStyle(bodyStyle2);
					    		
					    	}
					    	
					    	
					    	// 最后一行统计
							// 创建行
					    	j = j+1;
							Row row = sheet.createRow(j);
							row.setHeight((short)(25 * 20));
							Cell countCell = row.createCell(7);
							countCell.setCellValue("新入区人数统计计");
							countCell.setCellStyle(bodyStyle2);
							
							Cell countNumCell = row.createCell(8);
							countNumCell.setCellValue(rqList.size() + " 人");
							countNumCell.setCellStyle(bodyStyle2);
					    }
							
		
		
		
		
		
					 // 从model中获取数据对象     临时出区
						List<AskManagementExportEntity> tempOutList = (List<AskManagementExportEntity>) model.get("tempOutList");
						
						
						if(tempOutList!=null && tempOutList.size()>0){
							
							total = total + tempOutList.size();
					    	
					    	j = j+1;
					    	sheet.createRow(j);

				    		// 设置表体样式
							HSSFCellStyle bodyStyle1 = workbook.createCellStyle();
							bodyStyle1.setAlignment(CellStyle.ALIGN_CENTER);
							bodyStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
							bodyStyle1.setBorderTop(CellStyle.BORDER_THIN);
							bodyStyle1.setBorderBottom(CellStyle.BORDER_THIN);
							bodyStyle1.setBorderLeft(CellStyle.BORDER_THIN);
							bodyStyle1.setBorderRight(CellStyle.BORDER_THIN);
							bodyStyle1.setWrapText(true);
							HSSFFont bodyFont1 = workbook.createFont();
							bodyFont1.setFontHeightInPoints((short)11);
							bodyStyle1.setFont(bodyFont1);
							
							
					    	for(int ii=0;ii<tempOutList.size();ii++){
					    		AskManagementExportEntity obj = tempOutList.get(ii);
					    		
					    		j = j+1;
					    		i = i+1;
					    		
					    		Row row = sheet.createRow(j);
								row.setHeight((short)(25 * 20));
								// 开始创建单元格并赋值 ,序号
								Cell xuhaoCell = row.createCell(0);
								xuhaoCell.setCellValue(i);
								xuhaoCell.setCellStyle(bodyStyle1);
								
								//姓名
								Cell personNameCell = row.createCell(1);
								if (obj.getPersonName()!=null && !"".equals(obj.getPersonName())) {
									personNameCell.setCellValue(obj.getPersonName());
								}
								personNameCell.setCellStyle(bodyStyle1);
								
								//性别
								Cell sexCell = row.createCell(2);
								if (obj.getSex()!=null && !"".equals(obj.getSex())) {
									
									if("0".equals(obj.getSex())){
										sexCell.setCellValue("未知的性别");	
									}
									if("1".equals(obj.getSex())){
										sexCell.setCellValue("男");	
									}
									if("2".equals(obj.getSex())){
										sexCell.setCellValue("女");	
									}
									if("9".equals(obj.getSex())){
										sexCell.setCellValue("未说明的性别");	
									}
								}
								sexCell.setCellStyle(bodyStyle1);
								
								
								//
								Cell interrogateRoomCell = row.createCell(3);
							    interrogateRoomCell.setCellValue("临时出区");
								interrogateRoomCell.setCellStyle(bodyStyle1);
								
								
								// 案件类型 0刑事 1行政
								Cell caseTypeCell = row.createCell(4);
								if (obj.getAjlx()!=null) {
									if(obj.getAjlx() == 0){
										caseTypeCell.setCellValue("刑事");
									}
									if(obj.getAjlx() == 1){
										caseTypeCell.setCellValue("行政");
									}
									if(obj.getAjlx() == 2){
										caseTypeCell.setCellValue("警情");
									}
								}
								caseTypeCell.setCellStyle(bodyStyle1);

								// 案件性质
								Cell casePropertiesCell = row.createCell(5);
								if (obj.getCaseNature()!=null && !"".equals(obj.getCaseNature())) {
									casePropertiesCell.setCellValue(obj.getCaseNature());
								}
								casePropertiesCell.setCellStyle(bodyStyle1);
								
								//民警
								Cell policemanCell = row.createCell(6);
								if (obj.getPoliceName()!=null && !"".equals(obj.getPoliceName())) {
									policemanCell.setCellValue(obj.getPoliceName());
								}
								policemanCell.setCellStyle(bodyStyle1);
								
								
								//送押单位
								Cell organizationCell = row.createCell(7);
								if (obj.getOrgName()!=null && !"".equals(obj.getOrgName())) {
									organizationCell.setCellValue(obj.getOrgName());
								}
								organizationCell.setCellStyle(bodyStyle1);
								
								
								
								//
								Cell tonganCell = row.createCell(8);
								tonganCell.setCellValue("");
								tonganCell.setCellStyle(bodyStyle1);
					    		
					    	}
					    	
					    	
					    	// 最后一行统计
							// 创建行
					    	j=j+1;
							Row row = sheet.createRow(j);
							row.setHeight((short)(25 * 20));
							Cell countCell = row.createCell(7);
							countCell.setCellValue("临时出区人数统计");
							countCell.setCellStyle(bodyStyle1);
							
							Cell countNumCell = row.createCell(8);
							countNumCell.setCellValue(tempOutList.size() + " 人");
							countNumCell.setCellStyle(bodyStyle1);
					    }
						
						
						
						
						// 从model中获取数据对象       （安检、信采、存物）
						List<AskManagementExportEntity> elseList = (List<AskManagementExportEntity>) model.get("elseList");

						// 创建工作表对象并命名
//						HSSFSheet sheet3 = workbook.createSheet("其他(安检、信采、存物)");
						
						// 设置行列的默认宽度和高度
//						sheet3.setColumnWidth(0, 20 * 80);//  序号
//						sheet3.setColumnWidth(1, 40 * 80);  //姓名
//						sheet3.setColumnWidth(2, 20 * 80);   //性别
//						sheet3.setColumnWidth(3, 40 * 80);   //案件类型
//						sheet3.setColumnWidth(4, 64 * 80);   //案件性质
//						sheet3.setColumnWidth(5, 40 * 80);   //送押民警
//						
//						
//						
//								// 创建表头
//								HSSFRow headerRow3 = sheet3.createRow(0);
//								// 创建样式  表头
//								HSSFFont font3 = workbook.createFont();
//								HSSFCellStyle headerStyle3 = workbook.createCellStyle();
//								// 设置垂直居中
//								headerStyle3.setAlignment(CellStyle.ALIGN_CENTER);
//								headerStyle3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//								// 设置边框
//								headerStyle3.setBorderTop(CellStyle.BORDER_THIN);
//								headerStyle3.setBorderBottom(CellStyle.BORDER_THIN);
//								headerStyle3.setBorderLeft(CellStyle.BORDER_THIN);
//								headerStyle3.setBorderRight(CellStyle.BORDER_THIN);
//								// 字体加粗
//								font3.setFontHeightInPoints((short)12);
//								font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
//								// 设置长文本自动换行
//								headerStyle3.setWrapText(true);
//								headerStyle3.setFont(font3);
//								HSSFCell xuhaoHeader3 = headerRow3.createCell(0);
//								xuhaoHeader3.setCellValue("序号");
//								xuhaoHeader3.setCellStyle(headerStyle3);
//
//								HSSFCell personNameHeader3 = headerRow3.createCell(1);
//								personNameHeader3.setCellValue("姓名");
//								personNameHeader3.setCellStyle(headerStyle3);
//
//								HSSFCell sexHeader3 = headerRow3.createCell(2);
//								sexHeader3.setCellValue("性别");
//								sexHeader3.setCellStyle(headerStyle3);
//
//								HSSFCell ceseType3 = headerRow3.createCell(3);
//								ceseType3.setCellValue("案件类型");
//								ceseType3.setCellStyle(headerStyle3);
//								
//								HSSFCell caseProp3 = headerRow3.createCell(4);
//								caseProp3.setCellValue("案件性质");
//								caseProp3.setCellStyle(headerStyle3);
//								
//								HSSFCell policemanHeader3 = headerRow2.createCell(5);
//								policemanHeader3.setCellValue("送案民警");
//								policemanHeader3.setCellStyle(headerStyle3);
//								
								
							    if(elseList!=null && elseList.size()>0){
							    	
							    	total = total + elseList.size();
							    	j = j+1;
							    	sheet.createRow(j);

						    		// 设置表体样式
									HSSFCellStyle bodyStyle3 = workbook.createCellStyle();
									bodyStyle3.setAlignment(CellStyle.ALIGN_CENTER);
									bodyStyle3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
									bodyStyle3.setBorderTop(CellStyle.BORDER_THIN);
									bodyStyle3.setBorderBottom(CellStyle.BORDER_THIN);
									bodyStyle3.setBorderLeft(CellStyle.BORDER_THIN);
									bodyStyle3.setBorderRight(CellStyle.BORDER_THIN);
									bodyStyle3.setWrapText(true);
									HSSFFont bodyFont3 = workbook.createFont();
									bodyFont3.setFontHeightInPoints((short)11);
									bodyStyle3.setFont(bodyFont3);
									
									
							    	for(int ii=0;ii<elseList.size();ii++){
							    		AskManagementExportEntity obj = elseList.get(ii);
							    		
							    		j = j+1;
							    		i = i+1;
							    		
							    		Row row = sheet.createRow(j);
										row.setHeight((short)(25 * 20));
										// 开始创建单元格并赋值 ,序号
										Cell xuhaoCell = row.createCell(0);
										xuhaoCell.setCellValue(i);
										xuhaoCell.setCellStyle(bodyStyle3);
										
										//姓名
										Cell personNameCell = row.createCell(1);
										if (obj.getPersonName()!=null && !"".equals(obj.getPersonName())) {
											personNameCell.setCellValue(obj.getPersonName());
										}
										personNameCell.setCellStyle(bodyStyle3);
										
										//性别
										Cell sexCell = row.createCell(2);
										if (obj.getSex()!=null && !"".equals(obj.getSex())) {
											
											if("0".equals(obj.getSex())){
												sexCell.setCellValue("未知的性别");	
											}
											if("1".equals(obj.getSex())){
												sexCell.setCellValue("男");	
											}
											if("2".equals(obj.getSex())){
												sexCell.setCellValue("女");	
											}
											if("9".equals(obj.getSex())){
												sexCell.setCellValue("未说明的性别");	
											}
										}
										sexCell.setCellStyle(bodyStyle3);
										
										
										
										
										//
										Cell interrogateRoomCell = row.createCell(3);
									    interrogateRoomCell.setCellValue("人身检查室");
										interrogateRoomCell.setCellStyle(bodyStyle3);
										
										//案件类型
										Cell caseType3 = row.createCell(4);
										if (obj.getAjlx()!=null) {
											if(obj.getAjlx() == 0){
												caseType3.setCellValue("刑事");
											}
											if(obj.getAjlx() == 1){
												caseType3.setCellValue("行政");
											}
											if(obj.getAjlx() == 2){
												caseType3.setCellValue("警情");
											}
										}
										caseType3.setCellStyle(bodyStyle3);
										
										
										//案件性质
										Cell casePro3 = row.createCell(5);
										if (obj.getCaseNature() != null && !"".equals(obj.getCaseNature())) {
											casePro3.setCellValue(obj.getCaseNature());
										}
										casePro3.setCellStyle(bodyStyle3);
										
										
										//送案民警
										Cell policemanCell = row.createCell(6);
										if (obj.getPoliceName()!=null && !"".equals(obj.getPoliceName())) {
											policemanCell.setCellValue(obj.getPoliceName());
										}
										policemanCell.setCellStyle(bodyStyle3);
										
										
										
										
										//送押单位
										Cell organizationCell = row.createCell(7);
										if (obj.getOrgName()!=null && !"".equals(obj.getOrgName())) {
											organizationCell.setCellValue(obj.getOrgName());
										}
										organizationCell.setCellStyle(bodyStyle3);
										
										
										
										//
										Cell tonganCell = row.createCell(8);
										tonganCell.setCellValue("");
										tonganCell.setCellStyle(bodyStyle3);
							    		
							    	}
							    	
							    	
							    	// 最后一行统计
									// 创建行
							    	j=j+1;
									Row row = sheet.createRow(j);
									row.setHeight((short)(25 * 20));
									Cell countCell = row.createCell(7);
									countCell.setCellValue("安检、信采、存物人数统计");
									countCell.setCellStyle(bodyStyle3);
									
									Cell countNumCell = row.createCell(8);
									countNumCell.setCellValue(elseList.size() + " 人");
									countNumCell.setCellStyle(bodyStyle3);
							    }
									
				
				
							    
							 // 设置表体样式
								HSSFCellStyle totalStyle = workbook.createCellStyle();
								totalStyle.setAlignment(CellStyle.ALIGN_CENTER);
								totalStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
								totalStyle.setBorderTop(CellStyle.BORDER_THIN);
								totalStyle.setBorderBottom(CellStyle.BORDER_THIN);
								totalStyle.setBorderLeft(CellStyle.BORDER_THIN);
								totalStyle.setBorderRight(CellStyle.BORDER_THIN);
								HSSFFont bodyFont = workbook.createFont();
								bodyFont.setFontHeightInPoints((short)11);
								totalStyle.setFont(bodyFont);
								
							    j=j+1+1;
								Row row = sheet.createRow(j);
								row.setHeight((short)(25 * 20));
								Cell countCell = row.createCell(7);
								countCell.setCellValue("总计");
								countCell.setCellStyle(totalStyle);
								
								Cell countNumCell = row.createCell(8);
								countNumCell.setCellValue(total + " 人");
								countNumCell.setCellStyle(totalStyle);
		
		
		
							    
		
		
		
		
		
		
		
//		// 从model中获取数据对象
//		List<AskManagementExportEntity> list = (List<AskManagementExportEntity>) model.get("list");
//
//		// 创建样式
//		HSSFFont font = workbook.createFont();
//		HSSFCellStyle headerStyle = workbook.createCellStyle();
//		// 设置垂直居中
//		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
//		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		// 设置边框
//		headerStyle.setBorderTop(CellStyle.BORDER_THIN);
//		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
//		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
//		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
//		// 字体加粗
//		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		// 设置长文本自动换行
//		headerStyle.setWrapText(true);
//		headerStyle.setFont(font);
//
//		// 创建工作表对象并命名
//		HSSFSheet sheet = workbook.createSheet("办案中心数据统计表");
//
//		// 设置行列的默认宽度和高度
//		sheet.setColumnWidth(0, 32 * 80);// 对A列设置宽度为80像素
//		sheet.setColumnWidth(1, 32 * 80);
//		sheet.setColumnWidth(2, 32 * 80);
//		sheet.setColumnWidth(3, 32 * 80);
//		sheet.setColumnWidth(4, 32 * 80);
//
//		// 创建表头
//		HSSFRow headerRow = sheet.createRow(0);
//		HSSFCell xuhaoHeader = headerRow.createCell(0);
//		xuhaoHeader.setCellValue("序号");
//		xuhaoHeader.setCellStyle(headerStyle);
//
//		HSSFCell waitingRoomHeader = headerRow.createCell(1);
//		waitingRoomHeader.setCellValue("候问室");
//		waitingRoomHeader.setCellStyle(headerStyle);
//
//		HSSFCell personNameHeader = headerRow.createCell(2);
//		personNameHeader.setCellValue("嫌疑人");
//		personNameHeader.setCellStyle(headerStyle);
//
//		HSSFCell caseTypeHeader = headerRow.createCell(3);
//		caseTypeHeader.setCellValue("案件类型");
//		caseTypeHeader.setCellStyle(headerStyle);
//
//		HSSFCell casePropertiesHeader = headerRow.createCell(4);
//		casePropertiesHeader.setCellValue("案件性质");
//		casePropertiesHeader.setCellStyle(headerStyle);
//		
//		HSSFCell sexHeader = headerRow.createCell(5);
//		sexHeader.setCellValue("性别");
//		sexHeader.setCellStyle(headerStyle);
//		
//		
//		
//		HSSFCell personCardNoHeader = headerRow.createCell(6);
//		personCardNoHeader.setCellValue("证件号码");
//		personCardNoHeader.setCellStyle(headerStyle);
//		
//		HSSFCell inTimeHeader = headerRow.createCell(7);
//		inTimeHeader.setCellValue("进入时间");
//		inTimeHeader.setCellStyle(headerStyle);
//		
//		HSSFCell policemanHeader = headerRow.createCell(8);
//		policemanHeader.setCellValue("送押民警");
//		policemanHeader.setCellStyle(headerStyle);
//
//		// 设置表体样式
//		HSSFCellStyle bodyStyle = workbook.createCellStyle();
//		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
//		bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
//		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
//		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
//		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
//
//		int total=0,totalxs=0,totalxz = 0;
//
//		// 遍历集合对象创建行和单元格
//		for (int i = 0; i < list.size(); i++) {
//			/*total += (list.get(i).getDtxsCount())+(list.get(i).getDtxzCount());
//			totalxs+=list.get(i).getDtxsCount();
//			totalxz+=list.get(i).getDtxzCount();*/
//			// 取出SysUser对象
//			AskManagementExportEntity obj = list.get(i);
//			// 创建行
//			Row row = sheet.createRow(i + 1);
//			// 开始创建单元格并赋值 ,序号
//			Cell xuhaoCell = row.createCell(0);
//			xuhaoCell.setCellValue(i + 1);
//			xuhaoCell.setCellStyle(bodyStyle);
//			//候问室
//			Cell waitingRoomCell = row.createCell(1);
//			if (obj.getWaitingRoom() != null && !"".equals(obj.getWaitingRoom() )) {
//				waitingRoomCell.setCellValue(obj.getWaitingRoom() );
//			}
//			waitingRoomCell.setCellStyle(bodyStyle);
//			// 嫌疑人
//			Cell personNameCell = row.createCell(2);
//			if (obj.getPersonName()!=null && !"".equals(obj.getPersonName())) {
//				personNameCell.setCellValue(obj.getPersonName());
//			}
//			personNameCell.setCellStyle(bodyStyle);
//
//			// 案件类型 0刑事 1行政
//			Cell caseTypeCell = row.createCell(3);
//			if (obj.getCaseType()!=null && !"".equals(obj.getCaseType())) {
//				if("0".equals(obj.getCaseType())){
//					caseTypeCell.setCellValue("刑事");
//				}
//                if("1".equals(obj.getCaseType())){
//                	caseTypeCell.setCellValue("行政");
//				}
//			}
//			caseTypeCell.setCellStyle(bodyStyle);
//
//			// 案件性质
//			Cell casePropertiesCell = row.createCell(4);
//			if (obj.getCaseProperties()!=null && !"".equals(obj.getCaseProperties())) {
//				casePropertiesCell.setCellValue(obj.getCaseProperties());
//			}
//			casePropertiesCell.setCellStyle(bodyStyle);
//			//性别
//			Cell sexCell = row.createCell(5);
//			if (obj.getSex()!=null && !"".equals(obj.getSex())) {
//				
//				if("0".equals(obj.getSex())){
//					sexCell.setCellValue("男");	
//				}
//				if("1".equals(obj.getSex())){
//					sexCell.setCellValue("女");	
//				}
//			}
//			sexCell.setCellStyle(bodyStyle);
//			//证件号码
//			Cell personCardNoCell = row.createCell(6);
//			if (obj.getPersonCardNo()!=null && !"".equals(obj.getPersonCardNo())) {
//				personCardNoCell.setCellValue(obj.getPersonCardNo());
//			}
//			personCardNoCell.setCellStyle(bodyStyle);
//			//进入时间
//			Cell inTimeCell = row.createCell(7);
//			if (obj.getInTime()!=null && !"".equals(obj.getInTime())) {
//				inTimeCell.setCellValue(obj.getInTime());
//			}
//			inTimeCell.setCellStyle(bodyStyle);
//			//送压民警
//			Cell policemanCell = row.createCell(8);
//			if (obj.getPoliceman()!=null && !"".equals(obj.getPoliceman())) {
//				policemanCell.setCellValue(obj.getPoliceman());
//			}
//			policemanCell.setCellStyle(bodyStyle);
//		} // end for
//
//		// 最后一行统计
//		// 创建行
//		Row row = sheet.createRow(list.size()+1);
//		Cell caseTypexzCell = row.createCell(0);
//		caseTypexzCell.setCellValue("合计");
//		caseTypexzCell.setCellStyle(bodyStyle);
//		
//		Cell caseTypexzCell2 = row.createCell(1);
//		caseTypexzCell2.setCellValue("");
//		caseTypexzCell2.setCellStyle(bodyStyle);
//		
//		Cell caseTypexzCell3 = row.createCell(2);
//		caseTypexzCell3.setCellValue(list.size());
//		caseTypexzCell3.setCellStyle(bodyStyle);
//		
//		/*Cell caseTypexzCell4 = row.createCell(3);
//		caseTypexzCell4.setCellValue(totalxz);
//		caseTypexzCell4.setCellStyle(bodyStyle);
//		
//		Cell caseTypexzCell5 = row.createCell(4);
//		caseTypexzCell5.setCellValue(total);
//		caseTypexzCell5.setCellStyle(bodyStyle);*/
	}
}
