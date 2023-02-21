/**
 * 分别输入打印机端口，打印机名称，办案中心名称，嫌疑人姓名，条码信息，案件信息
 * @param printerPort    打印机端口
 * @param printerName    打印机名称
 * @param centerName     办案中心名称
 * @param suspectsName   嫌疑人姓名
 * @param codeInfo       条码信息
 * @param caseInfo       案件信息
 */
function barCodePrint(printerPort,printerName,centerName,suspectsName,codeInfo,caseInfo){
//	alert("xxxxxx");
	print_epl.Open_Port("USB001)");
//	print_epl.Open_Port(printerPort);

	print_epl.Begin_Job("2", "12", "False", "B");
//	print_epl.Print_Winfont(200, 15, "办案中心名称", "宋体", 20, 12, "True", "False", "False", "False", "False");
	print_epl.Print_Winfont(200, 15, centerName, "宋体", 20, 12, "True", "False", "False", "False", "False");

//	print_epl.Print_Winfont(200, 45, "嫌疑人姓名", "宋体", 20, 12, "True", "False", "False", "False", "False");
	print_epl.Print_Winfont(200, 45, suspectsName, "宋体", 20, 12, "True", "False", "False", "False", "False");

//	print_epl.Print_BarCode(200, 80, "2015082014010001", "1", "60", "3", "6", "B", "0");
	print_epl.Print_BarCode(200, 80, codeInfo, "1", "60", "3", "6", "B", "0");

//	print_epl.Print_Winfont(200, 170, "案件名称嫌疑人因盗窃，被人民群众逮住并报警，新警察号", "宋体", 20, 8, "True", "False", "False", "False", "False"); 
//	print_epl.Print_Winfont(200, 200, "于2015年07月14日进入海淀西北旺办案中心", "宋体", 20, 8, "True", "False", "False", "False", "False"); 
	if(caseInfo.length>50){
		caseInfo=caseInfo.substr(0,50)
	}
	if(caseInfo.length>0 && caseInfo.length<=25){
		print_epl.Print_Winfont(200, 170, caseInfo, "宋体", 20, 8, "True", "False", "False", "False", "False");
	}else if(caseInfo.length>25 && caseInfo.length<=50){
		caseInfo1 = caseInfo.substr(0,25);
		caseInfo2 = caseInfo.substr(25);
		print_epl.Print_Winfont(200, 170, caseInfo1, "宋体", 20, 8, "True", "False", "False", "False", "False"); 
		print_epl.Print_Winfont(200, 200, caseInfo2, "宋体", 20, 8, "True", "False", "False", "False", "False"); 
	}
	
	print_epl.End_Job();
	print_epl.Close_Port();
	print_epl.Printing_USBPORT("ZDesigner GT800 (EPL) (副本 1)");  // // prot 打印机名称 从 控制面板  ”设备和打印机“ 拷贝即可 
//	print_epl.Printing_USBPORT(printerName);  // // prot 打印机名称 从 控制面板  ”设备和打印机“ 拷贝即可 
}

