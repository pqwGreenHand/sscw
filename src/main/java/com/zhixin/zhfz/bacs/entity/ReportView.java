package com.zhixin.zhfz.bacs.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ReportView extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 从model中获取数据对象
		List<ExportEntity> list = (List<ExportEntity>) model.get("list");
		System.err.println("-----------------总数--------------------" + list.size());
		// 创建样式
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
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 设置长文本自动换行
		headerStyle.setWrapText(true);
		headerStyle.setFont(font);

		// 创建工作表对象并命名
		HSSFSheet sheet = workbook.createSheet("数据表");
		//获取excel表格的列名称
		//cell1,cell3,cell5,cell7,cell9
		String tempString=	(String) request.getAttribute("tempString");
		System.err.println("======================tempString=========reportView======"+tempString);
		List<String> tempStringList= new ArrayList<String>();
		if(tempString!=null &&!"".equals(tempString)){
			String []tempStrings=tempString.split(",");
			if(tempStrings!=null && tempStrings.length>0){
				 tempStringList=Arrays.asList(tempStrings);
			}
		}
		
		System.err.println("----------tempStringList.size()----------"+tempStringList.size());
		int colNumber=0;
		// 设置行列的默认宽度和高度
		
		sheet.setColumnWidth(0, 32 * 50);// 对A列设置宽度为80像素
		// 创建表头
		HSSFRow headerRow = sheet.createRow(0);
		// headerRow.setHeightInPoints(25f);//设置行高
		//序号
		HSSFCell xuhaoHeader = headerRow.createCell(0);
		xuhaoHeader.setCellValue("序号");
		xuhaoHeader.setCellStyle(headerStyle);
		sheet.setColumnWidth(tempStringList.size(), 32 * 200);// 对A列设置宽度为80像素
		for(int i = 0 ;i<tempStringList.size(); i++){
			colNumber=0;
			colNumber=i+1;
			sheet.setColumnWidth(i, 32 * 200);// 对A列设置宽度为80像素
			String colName=tempStringList.get(i);
			//biaotouneirong
			//人员类型
			if("cell0".equals(colName)){
				HSSFCell personTypeHeader = headerRow.createCell(colNumber);
				personTypeHeader.setCellValue("人员类型");
				personTypeHeader.setCellStyle(headerStyle);	
			}
			
			//姓名
			if("cell1".equals(colName)){
				HSSFCell personNameHeader = headerRow.createCell(colNumber);
				personNameHeader.setCellValue("姓名");
				personNameHeader.setCellStyle(headerStyle);	
			}
			
			//性别
			if("cell2".equals(colName)){
				HSSFCell personSex = headerRow.createCell(colNumber);
				personSex.setCellValue("性别");
				personSex.setCellStyle(headerStyle);
			}
			
			//是否成年
			if("cell11".equals(colName)){
				HSSFCell personAge = headerRow.createCell(colNumber);
				personAge.setCellValue("是否成年");
				personAge.setCellStyle(headerStyle);
			}
			
			//证件号码
			if("cell3".equals(colName)){
				HSSFCell personCertificateNo = headerRow.createCell(colNumber);
				personCertificateNo.setCellValue("证件号码");
				personCertificateNo.setCellStyle(headerStyle);
			}
			
			//案件性质
			if("cell4".equals(colName)){
				HSSFCell caseType = headerRow.createCell(colNumber);
				caseType.setCellValue("案件性质");
				caseType.setCellStyle(headerStyle);
			}
			
			//罪名
			if("cell5".equals(colName)){
				HSSFCell caseProperties = headerRow.createCell(colNumber);
				caseProperties.setCellValue("主案别");
				caseProperties.setCellStyle(headerStyle);
			}
			
			//入区时间
			if("cell6".equals(colName)){
				HSSFCell inTime = headerRow.createCell(colNumber);
				inTime.setCellValue("入区时间");
				inTime.setCellStyle(headerStyle);
			}
			
			//出区时间
			if("cell7".equals(colName)){
				HSSFCell outTime = headerRow.createCell(colNumber);
				outTime.setCellValue("出区时间");
				outTime.setCellStyle(headerStyle);
			}
		
			//出区去向
			if("cell8".equals(colName)){
				HSSFCell outPlace = headerRow.createCell(colNumber);
				outPlace.setCellValue("出区去向");
				outPlace.setCellStyle(headerStyle);
			}
			
			//办案民警
			if("cell9".equals(colName)){
				HSSFCell policeman = headerRow.createCell(colNumber);
				policeman.setCellValue("办案民警");
				policeman.setCellStyle(headerStyle);
			}
		
			//办案单位
			if("cell10".equals(colName)){
				HSSFCell workSpace = headerRow.createCell(colNumber);
				workSpace.setCellValue("办案单位");
				workSpace.setCellStyle(headerStyle);
			}
			//裁决时间
			if("cell13".equals(colName)){
				HSSFCell confirmTime = headerRow.createCell(colNumber);
				confirmTime.setCellValue("裁决时间");
				confirmTime.setCellStyle(headerStyle);
			}
			if("cell14".equals(colName)){
				HSSFCell confirmResult = headerRow.createCell(colNumber);
				confirmResult.setCellValue("裁决结果");
				confirmResult.setCellStyle(headerStyle);
			}
			if("cell12".equals(colName)){
				HSSFCell entranceProcedure = headerRow.createCell(colNumber);
				entranceProcedure.setCellValue("法律手续");
				entranceProcedure.setCellStyle(headerStyle);
			}
			if("cell15".equals(colName)){
				HSSFCell writtenTime = headerRow.createCell(colNumber);
				writtenTime.setCellValue("手续开具时间");
				writtenTime.setCellStyle(headerStyle);
			}
			if("cell16".equals(colName)){
				HSSFCell sfxxcj = headerRow.createCell(colNumber);
				sfxxcj.setCellValue("是否信息采集");
				sfxxcj.setCellStyle(headerStyle);
			}
			if("cell17".equals(colName)){
				HSSFCell sfsyjd = headerRow.createCell(colNumber);
				sfsyjd.setCellValue("是否送押解队");
				sfsyjd.setCellStyle(headerStyle);
				System.out.println(sfsyjd);
			}
			if("cell18".equals(colName)){
				HSSFCell recordTime = headerRow.createCell(colNumber);
				recordTime.setCellValue("入区到第一次审讯时间");
				recordTime.setCellStyle(headerStyle);
				System.out.println(recordTime);
			}
			
			
			
			
		}
		/*
		sheet.setColumnWidth(0, 32 * 50);// 对A列设置宽度为80像素
		sheet.setColumnWidth(1, 32 * 80);
		sheet.setColumnWidth(2, 32 * 80);
		sheet.setColumnWidth(3, 32 * 50);
		sheet.setColumnWidth(4, 32 * 200);
		sheet.setColumnWidth(5, 32 * 80);
		sheet.setColumnWidth(6, 32 * 200);
		sheet.setColumnWidth(7, 32 * 200);
		sheet.setColumnWidth(8, 32 * 200);
		sheet.setColumnWidth(9, 32 * 200);
		sheet.setColumnWidth(10, 32 * 100);
		sheet.setColumnWidth(11, 32 * 150);
		*/

		
		
		

		// 设置表体样式
		HSSFCellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int rownum = 0;
		// 遍历集合对象创建行和单元格
		for (int i = 0; i < list.size(); i++) {
			// 取出SysUser对象
			ExportEntity exportEntity = list.get(i);
			// 创建行
			Row row = sheet.createRow(i + 1);
			rownum = 0;
			rownum = i;
			// System.err.println("------------------rownum--------------------------------"+rownum);
			// 开始创建单元格并赋值 ,序号
			Cell xuhaoCell = row.createCell(0);
			xuhaoCell.setCellValue(i + 1);
			xuhaoCell.setCellStyle(bodyStyle);
			int temColNum=0;
			for(int j = 0 ;j<tempStringList.size(); j++){
				String  temColName=tempStringList.get(j);
				temColNum=j+1;
				if("cell0".equals(temColName)){
					// 以下是为每一列赋值,人员类型0 嫌疑人 1事主 2证人 3其它
					Cell PersonTypeCell = row.createCell(temColNum);
					if (exportEntity.getPersonType() != null) {
						if ("0".equals(exportEntity.getPersonType())) {
							PersonTypeCell.setCellValue("嫌疑人");
						}
						if ("1".equals(exportEntity.getPersonType())) {
							PersonTypeCell.setCellValue("事主");
						}
						if ("2".equals(exportEntity.getPersonType())) {
							PersonTypeCell.setCellValue("证人");
						}
						if ("3".equals(exportEntity.getPersonType())) {
							PersonTypeCell.setCellValue("其它");
						}
					}
					PersonTypeCell.setCellStyle(bodyStyle);
				}
			
				
				if("cell1".equals(temColName)){
					// 姓名
					Cell personNameCell = row.createCell(temColNum);
					if (exportEntity.getPersonName() != null) {
						personNameCell.setCellValue(exportEntity.getPersonName());
					}
					personNameCell.setCellStyle(bodyStyle);
				}
				

				// 性别
				if("cell2".equals(temColName)){
					Cell personSexCell = row.createCell(temColNum);
					if (exportEntity.getPersonSex() != null) {
						if ("1".equals(exportEntity.getPersonSex())) {
							personSexCell.setCellValue("男");
						}
						if ("2".equals(exportEntity.getPersonSex())) {
							personSexCell.setCellValue("女");
						}
					}
					personSexCell.setCellStyle(bodyStyle);
				}
				//是否成年
				if("cell11".equals(temColName)){
					Cell personAgeCell = row.createCell(temColNum);
					if (exportEntity.getPersonAge() != null) {
						if ("0".equals(exportEntity.getPersonAge())) {
							personAgeCell.setCellValue("未成年");
						}
						if ("1".equals(exportEntity.getPersonAge())) {
							personAgeCell.setCellValue("成年");
						}
					}
					personAgeCell.setCellStyle(bodyStyle);
				}
				if("cell3".equals(temColName)){
					// 证件号码
					Cell PersonCertificateNo = row.createCell(temColNum);
					if (exportEntity.getPersonCertificateNo() != null) {
						PersonCertificateNo.setCellValue(exportEntity.getPersonCertificateNo());
					}
					PersonCertificateNo.setCellStyle(bodyStyle);
				}
				
				if("cell4".equals(temColName)){
					// 案件类型 0刑事 1行政
					Cell caseTypeCell = row.createCell(temColNum);
					if (exportEntity.getCaseType() != null) {
						if ("2".equals(exportEntity.getCaseType())) {
							caseTypeCell.setCellValue("刑事");
						}
						if ("1".equals(exportEntity.getCaseType())) {
							caseTypeCell.setCellValue("行政");
						}
					}
					caseTypeCell.setCellStyle(bodyStyle);
				}
				
				if("cell5".equals(temColName)){
					// 案件性质
					Cell casePropertiesCell = row.createCell(temColNum);
					if (exportEntity.getCaseProperties() != null) {
						casePropertiesCell.setCellValue(exportEntity.getCaseProperties());
					}
					casePropertiesCell.setCellStyle(bodyStyle);
				}
				
				if("cell6".equals(temColName)){
					// 入区时间
					Cell inTimeCell = row.createCell(temColNum);
					if (exportEntity.getInTime() != null) {
						String inTimeStr = sdf.format(exportEntity.getInTime());
						if (inTimeStr != null && !"".equals(inTimeStr)) {
							inTimeCell.setCellValue(inTimeStr);
						}
					}
					inTimeCell.setCellStyle(bodyStyle);
				}
				
				if("cell7".equals(temColName)){
					// 出区时间
					Cell outTimeCell = row.createCell(temColNum);
					if (exportEntity.getOutTime() != null) {
						String outTimeStr = sdf.format(exportEntity.getOutTime());
						if (outTimeStr != null && !"".equals(outTimeStr)) {
							outTimeCell.setCellValue(outTimeStr);
						}
					}
					outTimeCell.setCellStyle(bodyStyle);

				}
				
				if("cell8".equals(temColName)){
					// 出区去向
					Cell outPlaceCell = row.createCell(temColNum);
					if (exportEntity.getOutPlace() != null) {
						outPlaceCell.setCellValue(exportEntity.getOutPlace());
					}
					outPlaceCell.setCellStyle(bodyStyle);
				}
				
				if("cell9".equals(temColName)){
					// 办案民警
					Cell policemanCell = row.createCell(temColNum);
					if (exportEntity.getPoliceman() != null) {
						policemanCell.setCellValue(exportEntity.getPoliceman());
					}
					policemanCell.setCellStyle(bodyStyle);
				}
				
				if("cell10".equals(temColName)){
					// 办案单位
					Cell workSpaceCell = row.createCell(temColNum);
					if (exportEntity.getWorkSpace() != null) {
						workSpaceCell.setCellValue(exportEntity.getWorkSpace());
					}
					workSpaceCell.setCellStyle(bodyStyle);	
				}
				if("cell13".equals(temColName)){
					// 裁决时间
					Cell confirmTimeCell = row.createCell(temColNum);
					if (exportEntity.getConfirmTime() != null) {
						String confirmTimeStr = sdf.format(exportEntity.getConfirmTime());
						if (confirmTimeStr != null && !"".equals(confirmTimeStr)) {
							confirmTimeCell.setCellValue(confirmTimeStr);
						}
					}
					confirmTimeCell.setCellStyle(bodyStyle);

				}
				if("cell14".equals(temColName)){
					// 裁决结果
					Cell workSpaceCell = row.createCell(temColNum);
					if (exportEntity.getConfirmResult() != null) {
						workSpaceCell.setCellValue(exportEntity.getConfirmResult());
					}
					workSpaceCell.setCellStyle(bodyStyle);	
				}
				if("cell12".equals(temColName)){
					// 法律手续
					Cell workSpaceCell = row.createCell(temColNum);
					if (exportEntity.getEntranceProcedure() != null) {
						workSpaceCell.setCellValue(exportEntity.getEntranceProcedure());
					}
					workSpaceCell.setCellStyle(bodyStyle);	
				}
				if("cell15".equals(temColName)){
					// 手续开具时间
					Cell confirmTimeCell = row.createCell(temColNum);
					if (exportEntity.getWrittenTime() != null) {
						String writtenTime = sdf.format(exportEntity.getWrittenTime());
						if (writtenTime != null && !"".equals(writtenTime)) {
							confirmTimeCell.setCellValue(writtenTime);
						}
					}
					confirmTimeCell.setCellStyle(bodyStyle);

				}
				
				if("cell16".equals(temColName)){
					// 是否信息采集（0未采集，1已采集，2所内采集,3办案中心内采集）
					Cell caseTypeCell = row.createCell(temColNum);
					if (exportEntity.getSfxxcj() != null) {
						if (exportEntity.getSfxxcj()==0) {
							caseTypeCell.setCellValue("未采集");
						}
						if (exportEntity.getSfxxcj()==1) {
							caseTypeCell.setCellValue("已采集");
						}
						if (exportEntity.getSfxxcj()==2) {
							caseTypeCell.setCellValue("所内采集");
						}
						if (exportEntity.getSfxxcj()==3) {
							caseTypeCell.setCellValue("办案中心内采集");
						}
					}
					caseTypeCell.setCellStyle(bodyStyle);
				}
				if("cell17".equals(temColName)){
					// 是否送押解队(0 未送 ,1 送)
					Cell caseTypeCell = row.createCell(temColNum);
					if (exportEntity.getSfsyjd() != null) {
						if (exportEntity.getSfsyjd()==0) {
							caseTypeCell.setCellValue("未送");
						}
						if (exportEntity.getSfsyjd()==1) {
							caseTypeCell.setCellValue("送");
						}
					}
					caseTypeCell.setCellStyle(bodyStyle);
				}
				
				if("cell17".equals(temColName)){
					// 是否送押解队(0 未送 ,1 送)
					Cell caseTypeCell = row.createCell(temColNum);
					if (exportEntity.getSfsyjd() != null) {
						if (exportEntity.getSfsyjd()==0) {
							caseTypeCell.setCellValue("未送");
						}
						if (exportEntity.getSfsyjd()==1) {
							caseTypeCell.setCellValue("送");
						}
					}
					caseTypeCell.setCellStyle(bodyStyle);
				}
				if("cell18".equals(temColName)){
					// 入区到第一次审讯时间
					Cell recordTimeCell = row.createCell(temColNum);
					if (exportEntity.getRecordTime() != null) {
						recordTimeCell.setCellValue(exportEntity.getRecordTime());
					}
					recordTimeCell.setCellStyle(bodyStyle);
				}
				
			}
		

		}
		// 循环结束
		// 添加合计-------
		// 开始创建单元格并赋值 ,序号
		if (rownum == list.size() - 1) {
			System.err.println("==========统计开始=================");
			Row row = sheet.createRow(rownum + 2);
			Cell xuhaoCell = row.createCell(0);
			xuhaoCell.setCellValue("合计");
			xuhaoCell.setCellStyle(bodyStyle);

			// 以下是为每一列赋值,人员类型0 嫌疑人 1事主 2证人 3其它
			Cell PersonTypeCell = row.createCell(1);
			PersonTypeCell.setCellValue(list.size());
			PersonTypeCell.setCellStyle(bodyStyle);
			/*
			// 姓名
			Cell personNameCell = row.createCell(2);
			personNameCell.setCellValue("");
			personNameCell.setCellStyle(bodyStyle);
			// 性别
			Cell personSexCell = row.createCell(3);
			personSexCell.setCellValue("");
			personSexCell.setCellStyle(bodyStyle);
			// 证件号码
			Cell PersonCertificateNo = row.createCell(4);
			PersonCertificateNo.setCellValue("");
			PersonCertificateNo.setCellStyle(bodyStyle);
			// 案件类型 0刑事 1行政
			Cell caseTypeCell = row.createCell(5);
			caseTypeCell.setCellValue("");
			caseTypeCell.setCellStyle(bodyStyle);
			// 案件性质
			Cell casePropertiesCell = row.createCell(6);
			casePropertiesCell.setCellValue("");
			casePropertiesCell.setCellStyle(bodyStyle);

			// 入区时间
			Cell inTimeCell = row.createCell(7);
			inTimeCell.setCellValue("");
			inTimeCell.setCellStyle(bodyStyle);
			// 出区时间
			Cell outTimeCell = row.createCell(8);
			outTimeCell.setCellValue("");
			outTimeCell.setCellStyle(bodyStyle);
			// 出区去向
			Cell outPlaceCell = row.createCell(9);
			outPlaceCell.setCellValue("");
			outPlaceCell.setCellStyle(bodyStyle);
			// 办案民警
			Cell policemanCell = row.createCell(10);
			policemanCell.setCellValue("");
			policemanCell.setCellStyle(bodyStyle);

			// 办案单位
			Cell workSpaceCell = row.createCell(11);
			workSpaceCell.setCellValue("");
			workSpaceCell.setCellStyle(bodyStyle);*/
		}
	}
}
