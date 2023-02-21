package com.zhixin.zhfz.bacs.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ReportViewAccount extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 从model中获取数据对象
		List<ExportEntity> datalist = (List<ExportEntity>) model.get("list");
		List<String> timeList =  (List<String>) model.get("time");
		String type = model.get("type").toString();
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
		 
		
	   List<List<ExportEntity>> tartList = formatList(timeList, datalist);  
       for(int k=0;k<tartList.size();k++) {
    	 String sheetName = "";
    	 if(type.equals("ri")) {
    		 sheetName = "Sheet";
    	 }else if(type.equals("yue")){
    		 sheetName = timeList.get(k);
    	 }
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook.createSheet(sheetName);
		
		// 设置行列的默认宽度和高度
		
		sheet.setColumnWidth(0, 32 * 50);// 对A列设置宽度为80像素
		// 创建表头
		HSSFRow headerRow = sheet.createRow(0);
		// headerRow.setHeightInPoints(25f);//设置行高
		//序号
		HSSFCell xuhaoHeader = headerRow.createCell(0);
		xuhaoHeader.setCellValue("序号");
		xuhaoHeader.setCellStyle(headerStyle);
		sheet.setColumnWidth(16, 32 * 200);// 对A列设置宽度为80像素
		
		HSSFCell aHeader = headerRow.createCell(1);
		aHeader.setCellValue("姓名");
		aHeader.setCellStyle(headerStyle);	
		
		HSSFCell bHeader = headerRow.createCell(2);
		bHeader.setCellValue("性别");
		bHeader.setCellStyle(headerStyle);	
		
		HSSFCell cHeader = headerRow.createCell(3);
		cHeader.setCellValue("身份证号/护照号");
		cHeader.setCellStyle(headerStyle);
		
		HSSFCell dpeHeader = headerRow.createCell(4);
		dpeHeader.setCellValue("核查时间");
		dpeHeader.setCellStyle(headerStyle);	
		 
		HSSFCell epeHeader = headerRow.createCell(5);
		epeHeader.setCellValue("主案别");
		epeHeader.setCellStyle(headerStyle);
		
		
		HSSFCell fpeHeader = headerRow.createCell(6);
		fpeHeader.setCellValue("案件性质");
		fpeHeader.setCellStyle(headerStyle);
		
		
		HSSFCell gpeHeader = headerRow.createCell(7);
		gpeHeader.setCellValue("查询系统");
		gpeHeader.setCellStyle(headerStyle);
		
		HSSFCell hpeHeader = headerRow.createCell(8);
		hpeHeader.setCellValue("最终案件定性");
		hpeHeader.setCellStyle(headerStyle);

		HSSFCell ipeHeader = headerRow.createCell(9);
		ipeHeader.setCellValue("核查结果");
		ipeHeader.setCellStyle(headerStyle);
		
		HSSFCell jpeHeader = headerRow.createCell(10);
		jpeHeader.setCellValue("送案单位");
		jpeHeader.setCellStyle(headerStyle);
		
		HSSFCell kpeHeader = headerRow.createCell(11);
		kpeHeader.setCellValue("出区时间");
		kpeHeader.setCellStyle(headerStyle);
		
		HSSFCell lpeHeader = headerRow.createCell(12);
		lpeHeader.setCellValue("出区方式");
		lpeHeader.setCellStyle(headerStyle);
		
		HSSFCell mpeHeader = headerRow.createCell(13);
		mpeHeader.setCellValue("备注");
		mpeHeader.setCellStyle(headerStyle);
		
		HSSFCell npeHeader = headerRow.createCell(14);
		npeHeader.setCellValue("总人数");
		npeHeader.setCellStyle(headerStyle);
		
		HSSFCell opeHeader = headerRow.createCell(15);
		opeHeader.setCellValue("男/女");
		opeHeader.setCellStyle(headerStyle);
		 
		// 设置表体样式
		HSSFCellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyStyle.setBorderTop(CellStyle.BORDER_THIN);
		bodyStyle.setBorderBottom(CellStyle.BORDER_THIN);
		bodyStyle.setBorderLeft(CellStyle.BORDER_THIN);
		bodyStyle.setBorderRight(CellStyle.BORDER_THIN);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 遍历集合对象创建行和单元格
		List<ExportEntity> list = tartList.get(k);
		int listLen = list.size();
		System.err.println("-----------------总数--------------------" + listLen);
		int man = 0;
		int women = 0;
		for (int i = 0; i < listLen; i++) {
			 
			// 取出SysUser对象
			ExportEntity exportEntity = list.get(i); 
			// 创建行
			Row row = sheet.createRow(i + 1);
			// 开始创建单元格并赋值 ,序号
			Cell xuhaoCell = row.createCell(0);
			xuhaoCell.setCellValue(i + 1);
			xuhaoCell.setCellStyle(bodyStyle);
			//姓名
			Cell aCell = row.createCell(1);
			aCell.setCellStyle(bodyStyle);
			if(exportEntity.getPersonName()!=null) {
				aCell.setCellValue(exportEntity.getPersonName());
			}
			//性别
			Cell bCell = row.createCell(2);
			bCell.setCellStyle(bodyStyle);
			if (exportEntity.getPersonSex() != null) {
				if ("1".equals(exportEntity.getPersonSex())) {
					bCell.setCellValue("男");
					man++;
				}
				if ("2".equals(exportEntity.getPersonSex())) {
					bCell.setCellValue("女");
					women++;
				}
			}
			//身份证号/护照号
			Cell cCell = row.createCell(3);
			cCell.setCellStyle(bodyStyle);
			if (exportEntity.getPersonCertificateNo() != null) {
				cCell.setCellValue(exportEntity.getPersonCertificateNo());
			} 
			//核查时间
			Cell dCell = row.createCell(4);
			dCell.setCellStyle(bodyStyle);
			dCell.setCellValue(""); 
			//主案别
			Cell eCell = row.createCell(5);
			eCell.setCellStyle(bodyStyle);
			if (exportEntity.getCaseType() != null) {
				if ("2".equals(exportEntity.getCaseType())) {
					eCell.setCellValue("刑事");
				}
				if ("1".equals(exportEntity.getCaseType())) {
					eCell.setCellValue("行政");
				}
			}
			//案件性质
			Cell fCell = row.createCell(6);
			fCell.setCellStyle(bodyStyle);
			if (exportEntity.getCaseProperties() != null) {
				fCell.setCellValue(exportEntity.getCaseProperties());
			}
			//查询系统
			Cell gCell = row.createCell(7);
			gCell.setCellStyle(bodyStyle);
			gCell.setCellValue("智能检索、核录"); 
			//最终案件定性
			Cell hCell = row.createCell(8);
			hCell.setCellStyle(bodyStyle);
			if (exportEntity.getConfirmResult() != null) {
				hCell.setCellValue(exportEntity.getConfirmResult());
			}
			//核查结果
			Cell iCell = row.createCell(9);
			iCell.setCellStyle(bodyStyle);
			iCell.setCellValue("无"); 
			//送案单位
			Cell jCell = row.createCell(10);
			jCell.setCellStyle(bodyStyle);
			if (exportEntity.getWorkSpace() != null) {
				jCell.setCellValue(exportEntity.getWorkSpace());
			} 
			//出区时间
			Cell kCell = row.createCell(11);
			kCell.setCellStyle(bodyStyle);
			if (exportEntity.getOutTime() != null) {
				String outTimeStr = sdf.format(exportEntity.getOutTime());
				if (outTimeStr != null && !"".equals(outTimeStr)) {
					kCell.setCellValue(outTimeStr);
				}
			}
			//出区方式
			Cell lCell = row.createCell(12);
			lCell.setCellStyle(bodyStyle);
			if (exportEntity.getSfsyjd() != null) {
				if (exportEntity.getSfsyjd()==1) {
					lCell.setCellValue("送押解队");
				}
			}
			//备注
			Cell mCell = row.createCell(13);
			mCell.setCellStyle(bodyStyle);
			mCell.setCellValue(""); 
			//总人数
			Cell nCell = row.createCell(14);
			nCell.setCellStyle(bodyStyle);
			nCell.setCellValue(""); 
			//男/女
			Cell oCell = row.createCell(15);
			oCell.setCellStyle(bodyStyle);
			oCell.setCellValue("");
	      } 
		//合并单元格
		//送案单位   (x,y,10,10)
		//总人数(x,y,14,14)
		//男女(x,y,15,15)
		
		if(listLen>0) {
			CellRangeAddress region = new CellRangeAddress(1, listLen, 14, 14);
			Cell  cell1 = sheet.getRow(1).getCell(14);
			System.out.println("=="+listLen+"==="+man+"男"+women+"女");
			cell1.setCellValue(listLen);
			cell1.setCellStyle(headerStyle);
			sheet.addMergedRegion(region);
		 
			CellRangeAddress region1 = new CellRangeAddress(1, listLen, 15, 15);
	        Cell cell2 = sheet.getRow(1).getCell(15);
	        cell2.setCellValue(man+"男"+women+"女");
	        cell2.setCellStyle(headerStyle);
			sheet.addMergedRegion(region1);
		}
		
        
       }
	}
	
	
	private List<List<ExportEntity>> formatList(List<String> timeList,List<ExportEntity> datalist){
		List<List<ExportEntity>> targetList = new ArrayList<>();
		for(int i=0;i<timeList.size();i++) {
			List<ExportEntity> list = new ArrayList<>();
			for(int j=0;j<datalist.size();j++) {
				ExportEntity entity = datalist.get(j);
				if(entity.getYue().equals(timeList.get(i))) {
					list.add(entity);
				}
			}
			targetList.add(list);
		}
		return targetList;
	}
}
