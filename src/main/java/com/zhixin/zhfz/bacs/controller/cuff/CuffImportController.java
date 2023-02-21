package com.zhixin.zhfz.bacs.controller.cuff;

import com.zhixin.zhfz.bacs.services.cuff.ICuffService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

@Controller("cuffImportController")
@RequestMapping("/zhfz/bacs/cuff")
public class CuffImportController {

	private static final Logger logger =LoggerFactory.getLogger(CuffImportController.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private ICuffService cuffService;


	
	//上传用户照片
	@RequestMapping(value = "/cuffImportByXls")
	@ResponseBody
	public MessageEntity userImportByXls(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(file==null)
		{
			return new MessageEntity().addCode(0).addIsError(true).addTitle("Message").addContent("上传文件为空!");
		}
		logger.info("size:"+file.getBytes().length);
		try{
			Integer areaId = ControllerTool.getCurrentAreaID(request);
			cuffService.userImportByXls(file,areaId, request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new MessageEntity().addCode(0).addIsError(true).addTitle("Message").addContent(e.getMessage());
		}
		return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("导入标签信息成功!");
	}
	
}
