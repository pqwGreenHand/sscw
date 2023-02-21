package com.zhixin.zhfz.jxkp.controller.jxkp;


import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.jxkp.entity.*;
import com.zhixin.zhfz.jxkp.services.jxkp.IcopoliceService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("/zhfz/copolice")
public class copoliceController {
	@Autowired
	private IcopoliceService cs;

	@RequestMapping(value = "/getCopoliceList")
	@ResponseBody
	public Map<String,Object> getCopoliceList(HttpServletRequest request,HttpServletResponse response,String mjbm,String kssj,String jssj,String casetype,Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		if(mjbm!="") {
			map.put("mjbm", mjbm);
		}else {
			map.put("mjbm", null);
		}
		if(kssj!="") {
			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		map.put("casetype", casetype);
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
		List<CopoliceEntity> ce =  cs.getCopoliceList(map);
		int px = 1;
		for(CopoliceEntity i:ce) {
			i.setPx(px);
			px++;
			map.put("mjid", i.getMjid());

			i.setAjsl(cs.getxbajsl(map).size());

		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.getCopoliceListCount(map).size());
		result.put("rows", ce);
		return result;
	}


	@RequestMapping(value = "/getCopoliceSelect")
	@ResponseBody
	public List<CopoliceOrg> getCopoliceSelect(HttpServletResponse response){
		return cs.getCopoliceSelect();
	}


	@RequestMapping(value = "/getEvaluationList")
	@ResponseBody
	public Map<String,Object> getEvaluationList(HttpServletRequest request,HttpServletResponse response,String mjbm,String kssj,String jssj,String casetype,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("casetype", casetype);
		if(mjbm!="") {
			map.put("mjbm", mjbm);
		}else {
			map.put("mjbm", null);
		}
		if(kssj!="") {

			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
		List<Mainpolice> ce =  cs.getEvaluationList(map);
		int px = 1;
		for(Mainpolice i:ce) {
			i.setPx(px);
			px++;
			map.put("mjid", i.getMjid());

			i.setAjsl(cs.getajsl(map).size());

			int s= cs.getajsl(map).size();
			float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
			BigDecimal b = new BigDecimal(avg);
			double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			i.setAvg(Float.parseFloat(newavg+""));
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.getEvaluationListCount(map).size());
		result.put("rows", ce);
		return result;
	}


	@RequestMapping(value = "/getdutypolice")
	@ResponseBody
	public  Map<String,Object> getdutypolice(HttpServletRequest request,HttpServletResponse response,String mjbm,String kssj,String jssj,String casetype,Integer pageSize, Integer pageNumber,String mjid){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("casetype", casetype);
		if(mjbm!="") {
			map.put("mjbm", mjbm);
		}else {
			map.put("mjbm", null);
		}
		if(kssj!="") {

			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		if(mjid!="" && !mjid.equals("0")) {
			map.put("mjid2", mjid);
		}else {
			map.put("mjid2", null);
		}
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
		List<Mainpolice> ce =  cs.getdutypolice(map);
		int px = 1;
		for(Mainpolice i:ce) {
			i.setPx(px);
			px++;
			map.put("mjid", i.getMjid());
			i.setAjsl(cs.getzerenajsl(map).size());
			i.setKpsl(cs.getzerenkpsl(map).size());

			int s= cs.getzerenajsl(map).size();
			float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
			BigDecimal b = new BigDecimal(avg);
			double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			i.setAvg(Float.parseFloat(newavg+""));
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.getdutypoliceCount(map));
		result.put("rows", ce);
		return result;
	}

	@RequestMapping(value = "/getleadership")
	@ResponseBody
	public  Map<String,Object> getleadership(HttpServletRequest request,HttpServletResponse response,String mjbm,String kssj,String jssj,String casetype,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("casetype", casetype);
		if(mjbm!="") {
			map.put("mjbm", mjbm);
		}else {
			map.put("mjbm", null);
		}
		if(kssj!="") {

			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
		List<Mainpolice> ce =  cs.getleadership(map);
		int px = 1;
		for(Mainpolice i:ce) {
			i.setPx(px);
			px++;
			map.put("mjid", i.getMjid());

			i.setAjsl(cs.getleadershipajsl(map).size());

			int s= cs.getleadershipajsl(map).size();
			float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
			BigDecimal b = new BigDecimal(avg);
			double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			i.setAvg(Float.parseFloat(newavg+""));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.getleadershipCount(map).size());
		result.put("rows", ce);
		return result;
	}

	@RequestMapping(value = "/getajtj")
	@ResponseBody
	public List<BJTJEntity> getbjtj(HttpServletResponse response,String kssj,String jssj,String dw,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(kssj!="") {
			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		if(dw!="") {
			map.put("dw", dw);
		}else {
			map.put("dw", null);
		}
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		map.put("dataAuth", " ( org.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
		List<BJTJEntity> list = cs.getbjtj(map);
		int ajzs = 0;
		int kpzs = 0;
		float sum = 0;
		map.put("ajlx", null);
		ajzs = cs.getbjajsl(map).size();
		for(BJTJEntity i:list) {
			kpzs += i.getKpzs();
			sum += Math.round(i.getAvg());
			map.put("ajlx", i.getAjlxid());
			i.setAjzs(cs.getbjajsl(map).size());

			int s= cs.getbjajsl(map).size();
			float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
			BigDecimal b = new BigDecimal(avg);
			double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			i.setAvg(Float.parseFloat(newavg+""));


			if(i.getAjlxid()==0) {
				i.setAjlx("警情");
			}
			if(i.getAjlxid()==1) {
				i.setAjlx("行政");
			}
			if(i.getAjlxid()==2) {
				i.setAjlx("刑事");
			}
			if(i.getAjlxid()==3) {
				i.setAjlx("执法监督");
			}
		}
//		list.add(new BJTJEntity("合计", 111, ajzs, kpzs, (Math.round(sum/list.size())),));
		return list;
	}

	@RequestMapping(value = "/getbjdwtj")
	@ResponseBody
	public Map<String,Object> getbjdwtj(HttpServletResponse response,String kssj,String jssj,String casetype,Integer pageSize, Integer pageNumber,String dw,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("casetype", casetype);
		if(kssj!="") {
			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		if(dw!="") {
			map.put("dw", dw);
		}else {
			map.put("dw", null);
		}
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
		List<BJDWTJEntity> list = cs.getbjdwtj(map);
		int px = 1;
		for(BJDWTJEntity i:list) {
			i.setPx(px);
			px++;
			map.put("jbdw", i.getJbdwid());
			map.put("casetype", casetype);
			i.setAjsl(cs.getbjdwajsl(map).size());

			int s= cs.getbjdwajsl(map).size();
			float avg =  ((100*s)+i.getKf_sum())/s;
			/*float avg = i.getAvg();*/
			BigDecimal b = new BigDecimal(avg);
			double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			i.setAvg(Float.parseFloat(newavg+""));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.getbjdwtjCount(map).size());
		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/getzhibiao")
	@ResponseBody
	public Map<String, Object> getzhibiao(HttpServletRequest request,HttpServletResponse response,String kssj,String jssj,String casetype,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("casetype", casetype);

		if(kssj!="") {
			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
		List<ZhibiaoEntity> list = cs.getzhibiao(map);
		int px = 1;
		for(ZhibiaoEntity i:list) {
//			i.setKfzb(String.format("%.2f", ((double)i.getKfzz()/cs.getzhibiao_zkf(map)*100))+"%");
			i.setPx(px);
			px++;
			map.put("id", i.getId());
			i.setAjsl(cs.getzhibiao_ajsl(map).size());
			i.setKfzddw(cs.getzhibiao_kfzd(map).getConcatCountOrgName());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.getzhibiaoCount(map).size());
		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/getdwzhibiao")
	@ResponseBody
	public Map<String, Object> getdwzhibiao(HttpServletResponse response,String kssj,String jssj,Integer bmid,String casetype,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("casetype", casetype);
		map.put("bmid",bmid);
		if(kssj!="") {
			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		int px = 1;
		List<ZhibiaoEntity> list = cs.getzhibiao(map);
		for(ZhibiaoEntity i:list) {
			/*i.setKfzb(String.format("%.2f", ((double)i.getKfzz()/cs.getzhibiao_zkf(map)*100))+"%");*/
			i.setPx(px);
			px++;
			map.put("id", i.getId());
			i.setAjsl(cs.getzhibiao_ajsl(map).size());
			i.setKfzddw("");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.getzhibiaoCount(map).size());
		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/getfazhiyuan")
	@ResponseBody
	public Map<String, Object> getfazhiyuan(HttpServletRequest request,HttpServletResponse response,String kssj,String jssj,String casetype,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("casetype", casetype);
		if(kssj!="") {
			map.put("kssj", kssj);
		}else {
			map.put("kssj", null);
		}
		if(jssj!="") {
			map.put("jssj", jssj);
		}else {
			map.put("jssj", null);
		}
		SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
		map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
		List<Mainpolice> list = cs.getfazhiyuan(map);
		int px = 1;
		for(Mainpolice i:list) {
			i.setPx(px);
			px++;
			map.put("mjid", i.getMjid());
			i.setAjsl(cs.getfzyajsl(map).size());

			int s= cs.getfzyajsl(map).size();
			float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
			BigDecimal b = new BigDecimal(avg);
			double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			i.setAvg(Float.parseFloat(newavg+""));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.getfazhiyuanCount(map).size());
		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/createxcel")
	@ResponseBody
	public String createxcel(HttpServletRequest request,HttpServletResponse response,HttpSession session,String mjbm,String wjjurl,String type,String creaturl,String excelname,String exceltime,String bmid,String kssj,String jssj,String mjid) {
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

			if(type.equals("bjtj")) {
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

				WritableSheet sheet1 = book.createSheet("案件统计", 0);

				sheet1.mergeCells(0,0, 5, 0);//合并单元格



				Label label = new Label(0, 0, excelname,titleFormate);
				sheet1.addCell(label);
				sheet1.setRowView(0, 700);//设置第一行的高度



				sheet1.mergeCells(0,1, 5, 1);
				Label label2 = new Label(0, 1, exceltime,titleFormate2);
				sheet1.addCell(label2);


				sheet1.mergeCells(0,2, 3, 2);//合并单元格
				Label label3 = new Label(0, 2, "案件统计",titleFormate2);
				sheet1.addCell(label3);
				Label label4 = new Label(0, 3, "-",titleFormate2);
				sheet1.addCell(label4);
				Label label5 = new Label(1, 3, "案件总数",titleFormate2);
				sheet1.addCell(label5);
				Label label6 = new Label(2, 3, "考评总数",titleFormate2);
				sheet1.addCell(label6);
				Label label7 = new Label(3, 3, "平均得分",titleFormate2);
				sheet1.addCell(label7);
				Map<String, Object> map1 = new HashMap<String, Object>();
				if(kssj!="") {
					map1.put("kssj", kssj);
				}else {
					map1.put("kssj", null);
				}
				if(jssj!="") {
					map1.put("jssj", jssj);
				}else {
					map1.put("jssj", null);
				}
				if(mjbm!="") {
					map1.put("dw", mjbm);
				}else {
					map1.put("dw", null);
				}
				SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
				map1.put("dataAuth", " ( org.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				List<BJTJEntity> list = cs.getbjtj(map1);
				int ajzs = 0;
				int kpzs = 0;
				float sum = 0;
				map1.put("ajlx", null);
				ajzs = cs.getbjajsl(map1).size();
				for(BJTJEntity i:list) {
					kpzs += i.getKpzs();
					sum += i.getAvg();
					map1.put("ajlx", i.getAjlxid());
					i.setAjzs(cs.getbjajsl(map1).size());


					int s= cs.getbjajsl(map1).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
					if(i.getAjlxid()==0) {
						i.setAjlx("警情");
					}
					if(i.getAjlxid()==1) {
						i.setAjlx("行政");
					}
					if(i.getAjlxid()==2) {
						i.setAjlx("刑事");
					}
					if(i.getAjlxid()==3) {
						i.setAjlx("执法监督");
					}
				}
//				list.add(new BJTJEntity("合计", 111, ajzs, kpzs, (Math.round(sum/list.size()))));
				int num1 = -1;
				for(BJTJEntity i:list) {
					num1++;
					Label q = new Label(0, 4+num1, i.getAjlx(),titleFormate3);
					sheet1.addCell(q);
					Number w = new Number(1, 4+num1, i.getAjzs(),titleFormate3);
					sheet1.addCell(w);
					Number e = new Number(2, 4+num1, i.getKpzs(),titleFormate3);
					sheet1.addCell(e);
					Number r = new Number(3, 4+num1, i.getAvg(),titleFormate3);
					sheet1.addCell(r);
				}
				WritableSheet sheet11 = book.createSheet("警情", 1);
				Label labell = new Label(0, 0, excelname,titleFormate);
				Label label2l = new Label(0, 1, exceltime,titleFormate2);
				sheet11.mergeCells(0,0, 5, 0);//合并单元格

				sheet11.addCell(labell);
				sheet11.setRowView(0, 700);//设置第一行的高度
				sheet11.setColumnView(1, 60);
				sheet11.mergeCells(0,1, 5, 1);

				sheet11.addCell(label2l);

				sheet11.mergeCells(0,2, 5, 2);//合并单元格
				Label a = new Label(0, 2, "各单位执法数据排行(警情)",titleFormate2);
				sheet11.addCell(a);

				Label a1 = new Label(0, 3, "排名",titleFormate2);
				sheet11.addCell(a1);
				Label a2 = new Label(1, 3, "经办单位",titleFormate2);
				sheet11.addCell(a2);
				Label a3 = new Label(2, 3, "警情数量",titleFormate2);
				sheet11.addCell(a3);
				Label a4 = new Label(3, 3, "考评数量",titleFormate2);
				sheet11.addCell(a4);
				Label a5 = new Label(4, 3, "总扣分",titleFormate2);
				sheet11.addCell(a5);
				Label a6 = new Label(5, 3, "平均得分",titleFormate2);
				sheet11.addCell(a6);
				Map<String, Object> map2 = new HashMap<String, Object>();
				if(kssj!="") {
					map2.put("kssj", kssj);
				}else {
					map2.put("kssj", null);
				}
				if(jssj!="") {
					map2.put("jssj", jssj);
				}else {
					map2.put("jssj", null);
				}
				if(mjbm!="") {
					map2.put("dw", mjbm);
				}else {
					map2.put("dw", null);
				}

				map2.put("pageStart", 0);
				map2.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				map2.put("casetype", "jq");
				map2.put("pageEnd", cs.getbjdwtjCount(map2).size());
				List<BJDWTJEntity> list2 = cs.getbjdwtj(map2);
				int px = 1;
				for(BJDWTJEntity i:list2) {
					i.setPx(px);
					px++;
					map2.put("jbdw", i.getJbdwid());
					i.setAjsl(cs.getbjdwajsl(map2).size());
					int s= cs.getbjdwajsl(map2).size();
					float avg =  ((100*s)+i.getKf_sum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num2 = -1;
				for(BJDWTJEntity i:list2) {
					num2++;
					Number s = new Number(0, 4+num2, i.getPx(),titleFormate3);
					sheet11.addCell(s);
					Label s1 = new Label(1, 4+num2, i.getJbdw(),titleFormate3);
					sheet11.addCell(s1);
					Number s2 = new Number(2, 4+num2, i.getAjsl(),titleFormate3);
					sheet11.addCell(s2);
					Number s3 = new Number(3, 4+num2, i.getKpsl(),titleFormate3);
					sheet11.addCell(s3);
					Number s4 = new Number(4, 4+num2, i.getKf_sum(),titleFormate3);
					sheet11.addCell(s4);
					Number s5 = new Number(5, 4+num2, i.getAvg(),titleFormate3);
					sheet11.addCell(s5);
				}
				WritableSheet sheet2 = book.createSheet("行政", 2);
				Label labelq = new Label(0, 0, excelname,titleFormate);
				Label label2q = new Label(0, 1, exceltime,titleFormate2);
				sheet2.mergeCells(0,0, 5, 0);//合并单元格

				sheet2.addCell(labelq);
				sheet2.setRowView(0, 700);//设置第一行的高度
				sheet2.setColumnView(1, 60);
				sheet2.mergeCells(0,1, 5, 1);

				sheet2.addCell(label2q);
				sheet2.mergeCells(0,2, 5, 2);//合并单元格
				Label z = new Label(0, 2, "各单位执法数据排行(行政)",titleFormate2);
				sheet2.addCell(z);
				Label z1 = new Label(0, 3, "排名",titleFormate2);
				sheet2.addCell(z1);
				Label z2 = new Label(1, 3, "经办单位",titleFormate2);
				sheet2.addCell(z2);
				Label z3 = new Label(2, 3, "案件数量",titleFormate2);
				sheet2.addCell(z3);
				Label z4 = new Label(3, 3, "考评数量",titleFormate2);
				sheet2.addCell(z4);
				Label z5 = new Label(4, 3, "总扣分",titleFormate2);
				sheet2.addCell(z5);
				Label z6 = new Label(5, 3, "平均得分",titleFormate2);
				sheet2.addCell(z6);
				Map<String, Object> map3 = new HashMap<String, Object>();
				if(kssj!="") {
					map3.put("kssj", kssj);
				}else {
					map3.put("kssj", null);
				}
				if(jssj!="") {
					map3.put("jssj", jssj);
				}else {
					map3.put("jssj", null);
				}
				if(mjbm!="") {
					map3.put("dw", mjbm);
				}else {
					map3.put("dw", null);
				}
				map3.put("pageStart", 0);
				map3.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				map3.put("casetype", "xz");
				map3.put("pageEnd", cs.getbjdwtjCount(map3).size());
				List<BJDWTJEntity> list3 = cs.getbjdwtj(map3);
				int px2 = 1;
				for(BJDWTJEntity i:list3) {
					i.setPx(px2);
					px2++;
					map3.put("jbdw", i.getJbdwid());
					i.setAjsl(cs.getbjdwajsl(map3).size());
					int s= cs.getbjdwajsl(map3).size();
					float avg =  ((100*s)+i.getKf_sum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num3 = -1;
				for(BJDWTJEntity i:list3) {
					num3++;
					Number s = new Number(0, 4+num3, i.getPx(),titleFormate3);
					sheet2.addCell(s);
					Label s1 = new Label(1, 4+num3, i.getJbdw(),titleFormate3);
					sheet2.addCell(s1);
					Number s2 = new Number(2, 4+num3, i.getAjsl(),titleFormate3);
					sheet2.addCell(s2);
					Number s3 = new Number(3, 4+num3, i.getKpsl(),titleFormate3);
					sheet2.addCell(s3);
					Number s4 = new Number(4, 4+num3, i.getKf_sum(),titleFormate3);
					sheet2.addCell(s4);
					Number s5 = new Number(5, 4+num3, i.getAvg(),titleFormate3);
					sheet2.addCell(s5);
				}
				WritableSheet sheet3 = book.createSheet("刑事", 3);
				Label labelw = new Label(0, 0, excelname,titleFormate);
				Label label2w = new Label(0, 1, exceltime,titleFormate2);
				sheet3.mergeCells(0,0, 5, 0);//合并单元格

				sheet3.addCell(labelw);
				sheet3.setRowView(0, 700);//设置第一行的高度
				sheet3.setColumnView(1, 60);
				sheet3.mergeCells(0,1, 5, 1);

				sheet3.addCell(label2w);
				sheet3.mergeCells(0,2, 5, 2);//合并单元格
				Label x = new Label(0, 2, "各单位执法数据排行(刑事)",titleFormate2);
				sheet3.addCell(x);
				Label x1 = new Label(0, 3, "排名",titleFormate2);
				sheet3.addCell(x1);
				Label x2 = new Label(1, 3, "经办单位",titleFormate2);
				sheet3.addCell(x2);
				Label x3 = new Label(2, 3, "案件数量",titleFormate2);
				sheet3.addCell(x3);
				Label x4 = new Label(3, 3, "考评数量",titleFormate2);
				sheet3.addCell(x4);
				Label x5 = new Label(4, 3, "总扣分",titleFormate2);
				sheet3.addCell(x5);
				Label x6 = new Label(5, 3, "平均得分",titleFormate2);
				sheet3.addCell(x6);
				map3.put("casetype", "xs");
				map3.put("pageEnd", cs.getbjdwtjCount(map3).size());


				List<BJDWTJEntity> list4 = cs.getbjdwtj(map3);
				int px3 = 1;
				for(BJDWTJEntity i:list4) {
					i.setPx(px3);
					px3++;
					map3.put("jbdw", i.getJbdwid());
					i.setAjsl(cs.getbjdwajsl(map3).size());
					int s= cs.getbjdwajsl(map3).size();
					float avg =  ((100*s)+i.getKf_sum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num4 = -1;
				for(BJDWTJEntity i:list4) {
					num4++;
					Number s = new Number(0, 4+num4, i.getPx(),titleFormate3);
					sheet3.addCell(s);
					Label s1 = new Label(1, 4+num4, i.getJbdw(),titleFormate3);
					sheet3.addCell(s1);
					Number s2 = new Number(2, 4+num4, i.getAjsl(),titleFormate3);
					sheet3.addCell(s2);
					Number s3 = new Number(3,4+num4, i.getKpsl(),titleFormate3);
					sheet3.addCell(s3);
					Number s4 = new Number(4, 4+num4, i.getKf_sum(),titleFormate3);
					sheet3.addCell(s4);
					Number s5 = new Number(5, 4+num4, i.getAvg(),titleFormate3);
					sheet3.addCell(s5);
				}



				WritableSheet sheet6 = book.createSheet("执法监督", 6);
				Label labelwe = new Label(0, 0, excelname,titleFormate);
				Label label2wee = new Label(0, 1, exceltime,titleFormate2);
				sheet6.mergeCells(0,0, 5, 0);//合并单元格

				sheet6.addCell(labelwe);
				sheet6.setRowView(0, 700);//设置第一行的高度
				sheet6.setColumnView(1, 60);
				sheet6.mergeCells(0,1, 5, 1);

				sheet6.addCell(label2wee);
				sheet6.mergeCells(0,2, 5, 2);//合并单元格
				Label xs = new Label(0, 2, "各单位执法数据排行(执法监督)",titleFormate2);
				sheet6.addCell(xs);
				Label xs1 = new Label(0, 3, "排名",titleFormate2);
				sheet6.addCell(xs1);
				Label xs2 = new Label(1, 3, "经办单位",titleFormate2);
				sheet6.addCell(xs2);
				Label xs3 = new Label(2, 3, "执法监督数量",titleFormate2);
				sheet6.addCell(xs3);
				Label xs4 = new Label(3, 3, "考评数量",titleFormate2);
				sheet6.addCell(xs4);
				Label xs5 = new Label(4, 3, "总扣分",titleFormate2);
				sheet6.addCell(xs5);
				Label xs6 = new Label(5, 3, "平均得分",titleFormate2);
				sheet6.addCell(xs6);
				map3.put("casetype", "zfjd");
				map3.put("pageEnd", cs.getbjdwtjCount(map3).size());


				List<BJDWTJEntity> list66 = cs.getbjdwtj(map3);
				int px66 = 1;
				for(BJDWTJEntity i:list66) {
					i.setPx(px66);
					px66++;
					map3.put("jbdw", i.getJbdwid());
					i.setAjsl(cs.getbjdwajsl(map3).size());
					int s= cs.getbjdwajsl(map3).size();
					float avg =  ((100*s)+i.getKf_sum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num66 = -1;
				for(BJDWTJEntity i:list66) {
					num66++;
					Number s = new Number(0, 4+num66, i.getPx(),titleFormate3);
					sheet6.addCell(s);
					Label s1 = new Label(1, 4+num66, i.getJbdw(),titleFormate3);
					sheet6.addCell(s1);
					Number s2 = new Number(2, 4+num66, i.getAjsl(),titleFormate3);
					sheet6.addCell(s2);
					Number s3 = new Number(3,4+num66, i.getKpsl(),titleFormate3);
					sheet6.addCell(s3);
					Number s4 = new Number(4, 4+num66, i.getKf_sum(),titleFormate3);
					sheet6.addCell(s4);
					Number s5 = new Number(5, 4+num66, i.getAvg(),titleFormate3);
					sheet6.addCell(s5);
				}


				WritableSheet sheet4 = book.createSheet("行政+刑事", 4);
				Label labele = new Label(0, 0, excelname,titleFormate);
				Label label2e = new Label(0, 1, exceltime,titleFormate2);
				sheet4.mergeCells(0,0, 5, 0);//合并单元格

				sheet4.addCell(labele);
				sheet4.setRowView(0, 700);//设置第一行的高度
				sheet4.setColumnView(1, 60);
				sheet4.mergeCells(0,1, 5, 1);

				sheet4.addCell(label2e);
				sheet4.mergeCells(0,2, 5, 2);//合并单元格
				Label xx = new Label(0, 2, "各单位执法数据排行(行政+刑事)",titleFormate2);
				sheet4.addCell(xx);
				Label xx1 = new Label(0, 3, "排名",titleFormate2);
				sheet4.addCell(xx1);
				Label xx2 = new Label(1, 3, "经办单位",titleFormate2);
				sheet4.addCell(xx2);
				Label xx3 = new Label(2, 3, "案件数量",titleFormate2);
				sheet4.addCell(xx3);
				Label xx4 = new Label(3, 3, "考评数量",titleFormate2);
				sheet4.addCell(xx4);
				Label xx5 = new Label(4, 3, "总扣分",titleFormate2);
				sheet4.addCell(xx5);
				Label xx6 = new Label(5, 3, "平均得分",titleFormate2);
				sheet4.addCell(xx6);
				map3.put("casetype", "xzxs");
				map3.put("pageEnd", cs.getbjdwtjCount(map3).size());


				List<BJDWTJEntity> list5 = cs.getbjdwtj(map3);
				int px4 = 1;
				for(BJDWTJEntity i:list5) {
					i.setPx(px4);
					px4++;
					map3.put("jbdw", i.getJbdwid());
					i.setAjsl(cs.getbjdwajsl(map3).size());
					int s= cs.getbjdwajsl(map3).size();
					float avg =  ((100*s)+i.getKf_sum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num5 = -1;
				for(BJDWTJEntity i:list5) {
					num5++;
					Number s = new Number(0, 4+num5, i.getPx(),titleFormate3);
					sheet4.addCell(s);
					Label s1 = new Label(1, 4+num5, i.getJbdw(),titleFormate3);
					sheet4.addCell(s1);
					Number s2 = new Number(2, 4+num5, i.getAjsl(),titleFormate3);
					sheet4.addCell(s2);
					Number s3 = new Number(3,4+num5, i.getKpsl(),titleFormate3);
					sheet4.addCell(s3);
					Number s4 = new Number(4, 4+num5, i.getKf_sum(),titleFormate3);
					sheet4.addCell(s4);
					Number s5 = new Number(5, 4+num5, i.getAvg(),titleFormate3);
					sheet4.addCell(s5);
				}


				WritableSheet sheet5 = book.createSheet("三合一", 5);
				Label labelr = new Label(0, 0, excelname,titleFormate);
				Label label2r = new Label(0, 1, exceltime,titleFormate2);
				sheet5.mergeCells(0,0, 5, 0);//合并单元格

				sheet5.addCell(labelr);
				sheet5.setRowView(0, 700);//设置第一行的高度
				sheet5.setColumnView(1, 60);
				sheet5.mergeCells(0,1, 5, 1);

				sheet5.addCell(label2r);
				sheet5.mergeCells(0,2, 5, 2);//合并单元格
				Label xxx = new Label(0, 2, "各单位执法数据排行(三合一)",titleFormate2);
				sheet5.addCell(xxx);
				Label xxx1 = new Label(0, 3, "排名",titleFormate2);
				sheet5.addCell(xxx1);
				Label xxx2 = new Label(1, 3, "经办单位",titleFormate2);
				sheet5.addCell(xxx2);
				Label xxx3 = new Label(2, 3, "案件数量",titleFormate2);
				sheet5.addCell(xxx3);
				Label xxx4 = new Label(3, 3, "考评数量",titleFormate2);
				sheet5.addCell(xxx4);
				Label xxx5 = new Label(4, 3, "总扣分",titleFormate2);
				sheet5.addCell(xxx5);
				Label xxx6 = new Label(5, 3, "平均得分",titleFormate2);
				sheet5.addCell(xxx6);

				map3.put("casetype", "all");
				map3.put("pageEnd", cs.getbjdwtjCount(map3).size());


				List<BJDWTJEntity> list6 = cs.getbjdwtj(map3);
				int px5 = 1;
				for(BJDWTJEntity i:list6) {
					i.setPx(px5);
					px5++;
					map3.put("jbdw", i.getJbdwid());
					i.setAjsl(cs.getbjdwajsl(map3).size());
					int s= cs.getbjdwajsl(map3).size();
					float avg =  ((100*s)+i.getKf_sum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num6 = -1;
				for(BJDWTJEntity i:list6) {
					num6++;
					Number s = new Number(0, 4+num6, i.getPx(),titleFormate3);
					sheet5.addCell(s);
					Label s1 = new Label(1, 4+num6, i.getJbdw(),titleFormate3);
					sheet5.addCell(s1);
					Number s2 = new Number(2, 4+num6, i.getAjsl(),titleFormate3);
					sheet5.addCell(s2);
					Number s3 = new Number(3,4+num6, i.getKpsl(),titleFormate3);
					sheet5.addCell(s3);
					Number s4 = new Number(4, 4+num6, i.getKf_sum(),titleFormate3);
					sheet5.addCell(s4);
					Number s5 = new Number(5, 4+num6, i.getAvg(),titleFormate3);
					sheet5.addCell(s5);
				}

				book.write();
				book.close();

			}else if(type.equals("zhibiao")){

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

				WritableSheet sheet1 = book.createSheet("警情", 0);

				sheet1.mergeCells(0,0, 6, 0);//合并单元格
				Label label = new Label(0, 0, excelname,titleFormate);
				sheet1.addCell(label);
				sheet1.setRowView(0, 700);//设置第一行的高度
				sheet1.setColumnView(1, 60);
				sheet1.setColumnView(6, 60);

				sheet1.mergeCells(0,1, 6, 1);
				Label label2 = new Label(0, 1, exceltime,titleFormate2);
				sheet1.addCell(label2);

				sheet1.mergeCells(0,2, 6, 2);//合并单元格
				Label a = new Label(0, 2, "指标扣分排行(警情)",titleFormate2);
				sheet1.addCell(a);
				Label a1 = new Label(0, 3, "排名",titleFormate2);
				sheet1.addCell(a1);
				Label a3 = new Label(1, 3, "指标标准",titleFormate2);
				sheet1.addCell(a3);
				Label a4 = new Label(2, 3, "涉及警情数",titleFormate2);
				sheet1.addCell(a4);
				Label a5 = new Label(3, 3, "出现次数",titleFormate2);
				sheet1.addCell(a5);
				Label a61 = new Label(4, 3, "扣分总值",titleFormate2);
				sheet1.addCell(a61);
				Label a6 = new Label(5, 3, "扣分占比",titleFormate2);
				sheet1.addCell(a6);
				Label a7 = new Label(6, 3, "扣分最多单位",titleFormate2);
				sheet1.addCell(a7);

				Map<String, Object> map = new HashMap<String, Object>();
				if(kssj!="") {
					map.put("kssj", kssj);
				}else {
					map.put("kssj", null);
				}
				if(jssj!="") {
					map.put("jssj", jssj);
				}else {
					map.put("jssj", null);
				}
				map.put("pageStart", 0);
				map.put("casetype", "jq");
				SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
				map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list = cs.getzhibiao(map);
				int px = 1;
				for(ZhibiaoEntity i:list) {
					i.setPx(px);
					px++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getConcatCountOrgName());
				}
				int num1=-1;
				for(ZhibiaoEntity i:list) {
					num1++;
					Number b1 = new Number(0, 4+num1, i.getPx(),titleFormate3);
					sheet1.addCell(b1);
					Label b3 = new Label(1, 4+num1, i.getZbbz(),titleFormate3);
					sheet1.addCell(b3);
					Number b4 = new Number(2, 4+num1, i.getAjsl(),titleFormate3);
					sheet1.addCell(b4);
					Number b5 = new Number(3, 4+num1, i.getCxcs(),titleFormate3);
					sheet1.addCell(b5);
					Number b51 = new Number(4, 4+num1, i.getKfzz(),titleFormate3);
					sheet1.addCell(b51);
					Label b6 = new Label(5, 4+num1, i.getKfzb(),titleFormate3);
					sheet1.addCell(b6);
					Label b7 = new Label(6, 4+num1, i.getKfzddw(),titleFormate3);
					sheet1.addCell(b7);
				}

				WritableSheet sheet2 = book.createSheet("行政", 1);

				sheet2.mergeCells(0,0, 6, 0);//合并单元格
				Label labely = new Label(0, 0, excelname,titleFormate);
				sheet2.addCell(labely);
				sheet2.setRowView(0, 700);//设置第一行的高度
				sheet2.setColumnView(1, 60);
				sheet2.setColumnView(6, 60);

				sheet2.mergeCells(0,1, 6, 1);
				Label label2y = new Label(0, 1, exceltime,titleFormate2);
				sheet2.addCell(label2y);

				sheet2.mergeCells(0,2, 6, 2);//合并单元格
				Label z = new Label(0, 2, "指标扣分排行(行政)",titleFormate2);
				sheet2.addCell(z);
				Label x = new Label(0, 3, "排名",titleFormate2);
				sheet2.addCell(x);
				Label v = new Label(1, 3, "指标标准",titleFormate2);
				sheet2.addCell(v);
				Label b = new Label(2, 3, "涉及案件数",titleFormate2);
				sheet2.addCell(b);
				Label n = new Label(3, 3, "出现次数",titleFormate2);
				sheet2.addCell(n);
				Label n1 = new Label(4, 3, "扣分总值",titleFormate2);
				sheet2.addCell(n1);
				Label m = new Label(5, 3, "扣分占比",titleFormate2);
				sheet2.addCell(m);
				Label k = new Label(6, 3, "扣分最多单位",titleFormate2);
				sheet2.addCell(k);

				map.put("casetype", "xz");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list2 = cs.getzhibiao(map);
				int px2 = 1;
				for(ZhibiaoEntity i:list2) {
					i.setPx(px2);
					px2++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getConcatCountOrgName());
				}
				int num2=-1;
				for(ZhibiaoEntity i:list2) {
					num2++;
					Number b1 = new Number(0, 4+num2, i.getPx(),titleFormate3);
					sheet2.addCell(b1);
					Label b3 = new Label(1, 4+num2, i.getZbbz(),titleFormate3);
					sheet2.addCell(b3);
					Number b4 = new Number(2, 4+num2, i.getAjsl(),titleFormate3);
					sheet2.addCell(b4);
					Number b5 = new Number(3, 4+num2, i.getCxcs(),titleFormate3);
					sheet2.addCell(b5);
					Number b51 = new Number(4, 4+num2, i.getKfzz(),titleFormate3);
					sheet2.addCell(b51);
					Label b6 = new Label(5, 4+num2, i.getKfzb(),titleFormate3);
					sheet2.addCell(b6);
					Label b7 = new Label(6, 4+num2, i.getKfzddw(),titleFormate3);
					sheet2.addCell(b7);
				}

				WritableSheet sheet3 = book.createSheet("刑事", 2);

				sheet3.mergeCells(0,0, 6, 0);//合并单元格
				Label labelu = new Label(0, 0, excelname,titleFormate);
				sheet3.addCell(labelu);
				sheet3.setRowView(0, 700);//设置第一行的高度
				sheet3.setColumnView(1, 60);
				sheet3.setColumnView(6, 60);

				sheet3.mergeCells(0,1, 6, 1);
				Label label2u = new Label(0, 1, exceltime,titleFormate2);
				sheet3.addCell(label2u);

				sheet3.mergeCells(0,2, 6, 2);//合并单元格
				Label z1 = new Label(0, 2, "指标扣分排行(刑事)",titleFormate2);
				sheet3.addCell(z1);
				Label x1 = new Label(0, 3, "排名",titleFormate2);
				sheet3.addCell(x1);

				Label v1 = new Label(1, 3, "指标标准",titleFormate2);
				sheet3.addCell(v1);
				Label b12 = new Label(2, 3, "涉及案件数",titleFormate2);
				sheet3.addCell(b12);
				Label n11 = new Label(3, 3, "出现次数",titleFormate2);
				sheet3.addCell(n11);
				Label n111 = new Label(4, 3, "扣分总值",titleFormate2);
				sheet3.addCell(n111);
				Label m1 = new Label(5, 3, "扣分占比",titleFormate2);
				sheet3.addCell(m1);
				Label k1 = new Label(6, 3, "扣分最多单位",titleFormate2);
				sheet3.addCell(k1);

				map.put("casetype", "xs");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list3 = cs.getzhibiao(map);
				int px3 = 1;
				for(ZhibiaoEntity i:list3) {
					i.setPx(px3);
					px3++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getConcatCountOrgName());
				}
				int num3=-1;
				for(ZhibiaoEntity i:list3) {
					num3++;
					Number b1 = new Number(0, 4+num3, i.getPx(),titleFormate3);
					sheet3.addCell(b1);
					Label b3 = new Label(1, 4+num3, i.getZbbz(),titleFormate3);
					sheet3.addCell(b3);
					Number b4 = new Number(2, 4+num3, i.getAjsl(),titleFormate3);
					sheet3.addCell(b4);
					Number b5 = new Number(3, 4+num3, i.getCxcs(),titleFormate3);
					sheet3.addCell(b5);
					Number b51 = new Number(4, 4+num3, i.getKfzz(),titleFormate3);
					sheet3.addCell(b51);
					Label b6 = new Label(5, 4+num3, i.getKfzb(),titleFormate3);
					sheet3.addCell(b6);
					Label b7 = new Label(6, 4+num3, i.getKfzddw(),titleFormate3);
					sheet3.addCell(b7);
				}




				WritableSheet sheet66 = book.createSheet("执法监督", 5);

				sheet66.mergeCells(0,0, 6, 0);//合并单元格
				Label labelu66 = new Label(0, 0, excelname,titleFormate);
				sheet66.addCell(labelu66);
				sheet66.setRowView(0, 700);//设置第一行的高度
				sheet66.setColumnView(1, 60);
				sheet66.setColumnView(6, 60);

				sheet66.mergeCells(0,1, 6, 1);
				Label label2u66 = new Label(0, 1, exceltime,titleFormate2);
				sheet66.addCell(label2u66);

				sheet66.mergeCells(0,2, 6, 2);//合并单元格
				Label z166 = new Label(0, 2, "指标扣分排行(刑事)",titleFormate2);
				sheet66.addCell(z166);
				Label x166 = new Label(0, 3, "排名",titleFormate2);
				sheet66.addCell(x166);
				Label v166 = new Label(1, 3, "指标标准",titleFormate2);
				sheet66.addCell(v166);
				Label b1266 = new Label(2, 3, "涉及执法监督数",titleFormate2);
				sheet66.addCell(b1266);
				Label n1166 = new Label(3, 3, "出现次数",titleFormate2);
				sheet66.addCell(n1166);
				Label n11166 = new Label(4, 3, "扣分总值",titleFormate2);
				sheet66.addCell(n11166);
				Label m166 = new Label(5, 3, "扣分占比",titleFormate2);
				sheet66.addCell(m166);
				Label k166 = new Label(6, 3, "扣分最多单位",titleFormate2);
				sheet66.addCell(k166);

				map.put("casetype", "zfjd");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list366 = cs.getzhibiao(map);
				int px366 = 1;
				for(ZhibiaoEntity i:list366) {
					i.setPx(px366);
					px366++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getConcatCountOrgName());
				}
				int num366=-1;
				for(ZhibiaoEntity i:list366) {
					num366++;
					Number b1 = new Number(0, 4+num366, i.getPx(),titleFormate3);
					sheet66.addCell(b1);
					Label b3 = new Label(1, 4+num366, i.getZbbz(),titleFormate3);
					sheet66.addCell(b3);
					Number b4 = new Number(2, 4+num366, i.getAjsl(),titleFormate3);
					sheet66.addCell(b4);
					Number b5 = new Number(3, 4+num366, i.getCxcs(),titleFormate3);
					sheet66.addCell(b5);
					Number b51 = new Number(4, 4+num366, i.getKfzz(),titleFormate3);
					sheet66.addCell(b51);
					Label b6 = new Label(5, 4+num366, i.getKfzb(),titleFormate3);
					sheet66.addCell(b6);
					Label b7 = new Label(6, 4+num366, i.getKfzddw(),titleFormate3);
					sheet66.addCell(b7);
				}



				WritableSheet sheet4 = book.createSheet("行政+刑事", 3);

				sheet4.mergeCells(0,0, 6, 0);//合并单元格
				Label labeli = new Label(0, 0, excelname,titleFormate);
				sheet4.addCell(labeli);
				sheet4.setRowView(0, 700);//设置第一行的高度
				sheet4.setColumnView(1, 60);
				sheet4.setColumnView(6, 60);

				sheet4.mergeCells(0,1, 6, 1);
				Label label2i = new Label(0, 1, exceltime,titleFormate2);
				sheet4.addCell(label2i);

				sheet4.mergeCells(0,2, 6, 2);//合并单元格
				Label z1q = new Label(0, 2, "指标扣分排行(行政+刑事)",titleFormate2);
				sheet4.addCell(z1q);
				Label x1q = new Label(0, 3, "排名",titleFormate2);
				sheet4.addCell(x1q);

				Label v1q = new Label(1, 3, "指标标准",titleFormate2);
				sheet4.addCell(v1q);
				Label b12q = new Label(2, 3, "涉及案件数",titleFormate2);
				sheet4.addCell(b12q);
				Label n11q = new Label(3, 3, "出现次数",titleFormate2);
				sheet4.addCell(n11q);
				Label n111q = new Label(4, 3, "扣分总值",titleFormate2);
				sheet4.addCell(n111q);
				Label m1q = new Label(5, 3, "扣分占比",titleFormate2);
				sheet4.addCell(m1q);
				Label k1q = new Label(6, 3, "扣分最多单位",titleFormate2);
				sheet4.addCell(k1q);

				map.put("casetype", "xzxs");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list4 = cs.getzhibiao(map);
				int px4 = 1;
				for(ZhibiaoEntity i:list4) {
					i.setPx(px4);
					px4++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getConcatCountOrgName());
				}
				int num4=-1;
				for(ZhibiaoEntity i:list4) {
					num4++;
					Number b1 = new Number(0, 4+num4, i.getPx(),titleFormate3);
					sheet4.addCell(b1);
					Label b3 = new Label(1, 4+num4, i.getZbbz(),titleFormate3);
					sheet4.addCell(b3);
					Number b4 = new Number(2, 4+num4, i.getAjsl(),titleFormate3);
					sheet4.addCell(b4);
					Number b5 = new Number(3, 4+num4, i.getCxcs(),titleFormate3);
					sheet4.addCell(b5);
					Number b51 = new Number(4, 4+num4, i.getKfzz(),titleFormate3);
					sheet4.addCell(b51);
					Label b6 = new Label(5, 4+num4, i.getKfzb(),titleFormate3);
					sheet4.addCell(b6);
					Label b7 = new Label(6, 4+num4, i.getKfzddw(),titleFormate3);
					sheet4.addCell(b7);
				}

				WritableSheet sheet5 = book.createSheet("三合一", 4);

				sheet5.mergeCells(0,0, 6, 0);//合并单元格
				Label labeliq = new Label(0, 0, excelname,titleFormate);
				sheet5.addCell(labeliq);
				sheet5.setRowView(0, 700);//设置第一行的高度
				sheet5.setColumnView(1, 60);
				sheet5.setColumnView(6, 60);

				sheet5.mergeCells(0,1, 6, 1);
				Label label2iq = new Label(0, 1, exceltime,titleFormate2);
				sheet5.addCell(label2iq);

				sheet5.mergeCells(0,2, 6, 2);//合并单元格
				Label z1qw = new Label(0, 2, "指标扣分排行(三合一)",titleFormate2);
				sheet5.addCell(z1qw);
				Label x1qw = new Label(0, 3, "排名",titleFormate2);
				sheet5.addCell(x1qw);

				Label v1qw = new Label(1, 3, "指标标准",titleFormate2);
				sheet5.addCell(v1qw);
				Label b12qw = new Label(2, 3, "涉及案件数",titleFormate2);
				sheet5.addCell(b12qw);
				Label n11qw = new Label(3, 3, "出现次数",titleFormate2);
				sheet5.addCell(n11qw);
				Label n111qw = new Label(4, 3, "扣分总值",titleFormate2);
				sheet5.addCell(n111qw);
				Label m1qw = new Label(5, 3, "扣分占比",titleFormate2);
				sheet5.addCell(m1qw);
				Label k1qw = new Label(6, 3, "扣分最多单位",titleFormate2);
				sheet5.addCell(k1qw);

				map.put("casetype", "all");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list4w = cs.getzhibiao(map);
				int px4w = 1;
				for(ZhibiaoEntity i:list4w) {
					i.setPx(px4w);
					px4w++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getConcatCountOrgName());
				}
				int num4w=-1;
				for(ZhibiaoEntity i:list4w) {
					num4w++;
					Number b1 = new Number(0, 4+num4w, i.getPx(),titleFormate3);
					sheet5.addCell(b1);
					Label b3 = new Label(1, 4+num4w, i.getZbbz(),titleFormate3);
					sheet5.addCell(b3);
					Number b4 = new Number(2, 4+num4w, i.getAjsl(),titleFormate3);
					sheet5.addCell(b4);
					Number b5 = new Number(3, 4+num4w, i.getCxcs(),titleFormate3);
					sheet5.addCell(b5);
					Number b51 = new Number(4, 4+num4w, i.getKfzz(),titleFormate3);
					sheet5.addCell(b51);
					Label b6 = new Label(5, 4+num4w, i.getKfzb(),titleFormate3);
					sheet5.addCell(b6);
					Label b7 = new Label(6, 4+num4w, i.getKfzddw(),titleFormate3);
					sheet5.addCell(b7);
				}

				book.write();
				book.close();

			}else if(type.equals("fazhiyuan")) {

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



				Map<String, Object> map = new HashMap<String, Object>();
				if(kssj!="") {
					map.put("kssj", kssj);
				}else {
					map.put("kssj", null);
				}
				if(jssj!="") {
					map.put("jssj", jssj);
				}else {
					map.put("jssj", null);
				}
				map.put("pageStart", 0);
				SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
				map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				WritableSheet sheet1 = book.createSheet("警情", 0);

				sheet1.mergeCells(0,0, 6, 0);//合并单元格
				Label label = new Label(0, 0, excelname,titleFormate);
				sheet1.addCell(label);
				sheet1.setRowView(0, 700);//设置第一行的高度

				sheet1.mergeCells(0,1, 6, 1);
				Label label2 = new Label(0, 1, exceltime,titleFormate2);
				sheet1.addCell(label2);

				sheet1.mergeCells(0,2, 6, 2);//合并单元格
				Label a1 = new Label(0,2,"法制员工作量统计(警情)",titleFormate2);
				sheet1.addCell(a1);
				Label a2 = new Label(0,3,"排名",titleFormate2);
				sheet1.addCell(a2);
				Label a3 = new Label(1,3,"法制员警号",titleFormate2);
				sheet1.addCell(a3);
				Label a4 = new Label(2,3,"法制员姓名",titleFormate2);
				sheet1.addCell(a4);
				Label a5 = new Label(3,3,"警情数量",titleFormate2);
				sheet1.addCell(a5);
				Label a6 = new Label(4,3,"考评数量",titleFormate2);
				sheet1.addCell(a6);
				Label a7 = new Label(5,3,"扣分总值",titleFormate2);
				sheet1.addCell(a7);
				Label a71 = new Label(6,3,"个案平均分",titleFormate2);
				sheet1.addCell(a71);

				map.put("casetype", "jq");
				map.put("pageEnd", cs.getfazhiyuanCount(map).size());
				List<Mainpolice> list = cs.getfazhiyuan(map);
				int px = 1;
				for(Mainpolice i:list) {
					i.setPx(px);
					px++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getfzyajsl(map).size());
					int s= cs.getfzyajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num1 = -1;
				for(Mainpolice i:list) {
					num1++;
					Number z = new Number(0, 4+num1, i.getPx(),titleFormate3);
					sheet1.addCell(z);
					Label z1 = new Label(1, 4+num1, i.getLogin_name(),titleFormate3);
					sheet1.addCell(z1);
					Label z2 = new Label(2, 4+num1, i.getMjxm(),titleFormate3);
					sheet1.addCell(z2);
					Number z3 = new Number(3, 4+num1, i.getAjsl(),titleFormate3);
					sheet1.addCell(z3);
					Number z4 = new Number(4, 4+num1, i.getKpsl(),titleFormate3);
					sheet1.addCell(z4);
					Number z5 = new Number(5, 4+num1, i.getKfsum(),titleFormate3);
					sheet1.addCell(z5);
					Number z51 = new Number(6, 4+num1, i.getAvg(),titleFormate3);
					sheet1.addCell(z51);
				}

				WritableSheet sheet2 = book.createSheet("行政", 1);

				sheet2.mergeCells(0,0, 6, 0);//合并单元格
				Label labela = new Label(0, 0, excelname,titleFormate);
				sheet2.addCell(labela);
				sheet2.setRowView(0, 700);//设置第一行的高度

				sheet2.mergeCells(0,1, 6, 1);
				Label label2a = new Label(0, 1, exceltime,titleFormate2);
				sheet2.addCell(label2a);

				sheet2.mergeCells(0,2, 6, 2);//合并单元格
				Label b1 = new Label(0,2,"法制员工作量统计(行政)",titleFormate2);
				sheet2.addCell(b1);
				Label b2 = new Label(0,3,"排名",titleFormate2);
				sheet2.addCell(b2);
				Label b3 = new Label(1,3,"法制员警号",titleFormate2);
				sheet2.addCell(b3);
				Label b4 = new Label(2,3,"法制员姓名",titleFormate2);
				sheet2.addCell(b4);
				Label b5 = new Label(3,3,"案件数量",titleFormate2);
				sheet2.addCell(b5);
				Label b6 = new Label(4,3,"考评数量",titleFormate2);
				sheet2.addCell(b6);
				Label b7 = new Label(5,3,"扣分总值",titleFormate2);
				sheet2.addCell(b7);
				Label b71 = new Label(6,3,"个案平均分",titleFormate2);
				sheet2.addCell(b71);


				map.put("casetype", "xz");
				map.put("pageEnd", cs.getfazhiyuanCount(map).size());
				List<Mainpolice> list2 = cs.getfazhiyuan(map);
				int px2 = 1;
				for(Mainpolice i:list2) {
					i.setPx(px2);
					px2++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getfzyajsl(map).size());
					int s= cs.getfzyajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num2 = -1;
				for(Mainpolice i:list2) {
					num2++;
					Number z = new Number(0, 4+num2, i.getPx(),titleFormate3);
					sheet2.addCell(z);
					Label z1 = new Label(1, 4+num2, i.getLogin_name(),titleFormate3);
					sheet2.addCell(z1);
					Label z2 = new Label(2, 4+num2, i.getMjxm(),titleFormate3);
					sheet2.addCell(z2);
					Number z3 = new Number(3, 4+num2, i.getAjsl(),titleFormate3);
					sheet2.addCell(z3);
					Number z4 = new Number(4, 4+num2, i.getKpsl(),titleFormate3);
					sheet2.addCell(z4);
					Number z5 = new Number(5, 4+num2, i.getKfsum(),titleFormate3);
					sheet2.addCell(z5);
					Number z51 = new Number(6, 4+num2, i.getAvg(),titleFormate3);
					sheet2.addCell(z51);
				}

				WritableSheet sheet3 = book.createSheet("刑事", 2);

				sheet3.mergeCells(0,0, 6, 0);//合并单元格
				Label labelaa = new Label(0, 0, excelname,titleFormate);
				sheet3.addCell(labelaa);
				sheet3.setRowView(0, 700);//设置第一行的高度

				sheet3.mergeCells(0,1, 6, 1);
				Label label2aa = new Label(0, 1, exceltime,titleFormate2);
				sheet3.addCell(label2aa);

				sheet3.mergeCells(0,2, 6, 2);//合并单元格
				Label b11 = new Label(0,2,"法制员工作量统计(刑事)",titleFormate2);
				sheet3.addCell(b11);
				Label b21 = new Label(0,3,"排名",titleFormate2);
				sheet3.addCell(b21);
				Label b31 = new Label(1,3,"法制员警号",titleFormate2);
				sheet3.addCell(b31);
				Label b41 = new Label(2,3,"法制员姓名",titleFormate2);
				sheet3.addCell(b41);
				Label b51 = new Label(3,3,"案件数量",titleFormate2);
				sheet3.addCell(b51);
				Label b61 = new Label(4,3,"考评数量",titleFormate2);
				sheet3.addCell(b61);
				Label b711 = new Label(5,3,"扣分总值",titleFormate2);
				sheet3.addCell(b711);
				Label b7111 = new Label(6,3,"个案平均分",titleFormate2);
				sheet3.addCell(b7111);


				map.put("casetype", "xs");
				map.put("pageEnd", cs.getfazhiyuanCount(map).size());
				List<Mainpolice> list3 = cs.getfazhiyuan(map);
				int px3 = 1;
				for(Mainpolice i:list3) {
					i.setPx(px3);
					px3++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getfzyajsl(map).size());
					int s= cs.getfzyajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num3 = -1;
				for(Mainpolice i:list3) {
					num3++;
					Number z = new Number(0, 4+num3, i.getPx(),titleFormate3);
					sheet3.addCell(z);
					Label z1 = new Label(1, 4+num3, i.getLogin_name(),titleFormate3);
					sheet3.addCell(z1);
					Label z2 = new Label(2, 4+num3, i.getMjxm(),titleFormate3);
					sheet3.addCell(z2);
					Number z3 = new Number(3, 4+num3, i.getAjsl(),titleFormate3);
					sheet3.addCell(z3);
					Number z4 = new Number(4, 4+num3, i.getKpsl(),titleFormate3);
					sheet3.addCell(z4);
					Number z5 = new Number(5, 4+num3, i.getKfsum(),titleFormate3);
					sheet3.addCell(z5);
					Number z51 = new Number(6, 4+num3, i.getAvg(),titleFormate3);
					sheet3.addCell(z51);
				}


				WritableSheet sheet366 = book.createSheet("执法监督", 5);

				sheet366.mergeCells(0,0, 6, 0);//合并单元格
				Label labelaa66 = new Label(0, 0, excelname,titleFormate);
				sheet366.addCell(labelaa66);
				sheet366.setRowView(0, 700);//设置第一行的高度

				sheet366.mergeCells(0,1, 6, 1);
				Label label2aa66 = new Label(0, 1, exceltime,titleFormate2);
				sheet366.addCell(label2aa66);

				sheet366.mergeCells(0,2, 6, 2);//合并单元格
				Label b1166 = new Label(0,2,"法制员工作量统计(执法监督)",titleFormate2);
				sheet366.addCell(b1166);
				Label b2166 = new Label(0,3,"排名",titleFormate2);
				sheet366.addCell(b2166);
				Label b3166 = new Label(1,3,"法制员警号",titleFormate2);
				sheet366.addCell(b3166);
				Label b4166 = new Label(2,3,"法制员姓名",titleFormate2);
				sheet366.addCell(b4166);
				Label b5166 = new Label(3,3,"执法监督数量",titleFormate2);
				sheet366.addCell(b5166);
				Label b6166 = new Label(4,3,"考评数量",titleFormate2);
				sheet366.addCell(b6166);
				Label b71166 = new Label(5,3,"扣分总值",titleFormate2);
				sheet366.addCell(b71166);
				Label b711166 = new Label(6,3,"个案平均分",titleFormate2);
				sheet366.addCell(b711166);


				map.put("casetype", "zfjd");
				map.put("pageEnd", cs.getfazhiyuanCount(map).size());
				List<Mainpolice> list366 = cs.getfazhiyuan(map);
				int px366 = 1;
				for(Mainpolice i:list366) {
					i.setPx(px366);
					px366++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getfzyajsl(map).size());
					int s= cs.getfzyajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num366 = -1;
				for(Mainpolice i:list366) {
					num366++;
					Number z = new Number(0, 4+num366, i.getPx(),titleFormate3);
					sheet366.addCell(z);
					Label z1 = new Label(1, 4+num366, i.getLogin_name(),titleFormate3);
					sheet366.addCell(z1);
					Label z2 = new Label(2, 4+num366, i.getMjxm(),titleFormate3);
					sheet366.addCell(z2);
					Number z3 = new Number(3, 4+num366, i.getAjsl(),titleFormate3);
					sheet366.addCell(z3);
					Number z4 = new Number(4, 4+num366, i.getKpsl(),titleFormate3);
					sheet366.addCell(z4);
					Number z5 = new Number(5, 4+num366, i.getKfsum(),titleFormate3);
					sheet366.addCell(z5);
					Number z51 = new Number(6, 4+num366, i.getAvg(),titleFormate3);
					sheet366.addCell(z51);
				}

				WritableSheet sheet4 = book.createSheet("行政+刑事", 3);

				sheet4.mergeCells(0,0, 6, 0);//合并单元格
				Label labelaaa = new Label(0, 0, excelname,titleFormate);
				sheet4.addCell(labelaaa);
				sheet4.setRowView(0, 700);//设置第一行的高度

				sheet4.mergeCells(0,1, 6, 1);
				Label label2aaa = new Label(0, 1, exceltime,titleFormate2);
				sheet4.addCell(label2aaa);

				sheet4.mergeCells(0,2, 6, 2);//合并单元格
				Label b11a = new Label(0,2,"法制员工作量统计(行政+刑事)",titleFormate2);
				sheet4.addCell(b11a);
				Label b21a = new Label(0,3,"排名",titleFormate2);
				sheet4.addCell(b21a);
				Label b31a = new Label(1,3,"法制员警号",titleFormate2);
				sheet4.addCell(b31a);
				Label b41a = new Label(2,3,"法制员姓名",titleFormate2);
				sheet4.addCell(b41a);
				Label b51a = new Label(3,3,"案件数量",titleFormate2);
				sheet4.addCell(b51a);
				Label b61a = new Label(4,3,"考评数量",titleFormate2);
				sheet4.addCell(b61a);
				Label b711a = new Label(5,3,"扣分总值",titleFormate2);
				sheet4.addCell(b711a);
				Label b7111a = new Label(6,3,"个案平均分",titleFormate2);
				sheet4.addCell(b7111a);


				map.put("casetype", "xzxs");
				map.put("pageEnd", cs.getfazhiyuanCount(map).size());
				List<Mainpolice> list4 = cs.getfazhiyuan(map);
				int px4 = 1;
				for(Mainpolice i:list4) {
					i.setPx(px4);
					px4++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getfzyajsl(map).size());
					int s= cs.getfzyajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num4 = -1;
				for(Mainpolice i:list4) {
					num4++;
					Number z = new Number(0, 4+num4, i.getPx(),titleFormate3);
					sheet4.addCell(z);
					Label z1 = new Label(1, 4+num4, i.getLogin_name(),titleFormate3);
					sheet4.addCell(z1);
					Label z2 = new Label(2, 4+num4, i.getMjxm(),titleFormate3);
					sheet4.addCell(z2);
					Number z3 = new Number(3, 4+num4, i.getAjsl(),titleFormate3);
					sheet4.addCell(z3);
					Number z4 = new Number(4, 4+num4, i.getKpsl(),titleFormate3);
					sheet4.addCell(z4);
					Number z5 = new Number(5, 4+num4, i.getKfsum(),titleFormate3);
					sheet4.addCell(z5);
					Number z51 = new Number(6, 4+num4, i.getAvg(),titleFormate3);
					sheet4.addCell(z51);
				}

				WritableSheet sheet5 = book.createSheet("三合一", 4);

				sheet5.mergeCells(0,0, 6, 0);//合并单元格
				Label labelaaaa = new Label(0, 0, excelname,titleFormate);
				sheet5.addCell(labelaaaa);
				sheet5.setRowView(0, 700);//设置第一行的高度

				sheet5.mergeCells(0,1, 6, 1);
				Label label2aaaa = new Label(0, 1, exceltime,titleFormate2);
				sheet5.addCell(label2aaaa);

				sheet5.mergeCells(0,2, 6, 2);//合并单元格
				Label b11aa = new Label(0,2,"法制员工作量统计(三合一)",titleFormate2);
				sheet5.addCell(b11aa);
				Label b21aa = new Label(0,3,"排名",titleFormate2);
				sheet5.addCell(b21aa);
				Label b31aa = new Label(1,3,"法制员警号",titleFormate2);
				sheet5.addCell(b31aa);
				Label b41aa = new Label(2,3,"法制员姓名",titleFormate2);
				sheet5.addCell(b41aa);
				Label b51aa = new Label(3,3,"案件数量",titleFormate2);
				sheet5.addCell(b51aa);
				Label b61aa = new Label(4,3,"考评数量",titleFormate2);
				sheet5.addCell(b61aa);
				Label b711aa = new Label(5,3,"扣分总值",titleFormate2);
				sheet5.addCell(b711aa);
				Label b7111aa = new Label(6,3,"个案平均分",titleFormate2);
				sheet5.addCell(b7111aa);


				map.put("casetype", "all");
				map.put("pageEnd", cs.getfazhiyuanCount(map).size());
				List<Mainpolice> list5 = cs.getfazhiyuan(map);
				int px5 = 1;
				for(Mainpolice i:list5) {
					i.setPx(px5);
					px5++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getfzyajsl(map).size());
					int s= cs.getfzyajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num5 = -1;
				for(Mainpolice i:list5) {
					num5++;
					Number z = new Number(0, 4+num5, i.getPx(),titleFormate3);
					sheet5.addCell(z);
					Label z1 = new Label(1, 4+num5, i.getLogin_name(),titleFormate3);
					sheet5.addCell(z1);
					Label z2 = new Label(2, 4+num5, i.getMjxm(),titleFormate3);
					sheet5.addCell(z2);
					Number z3 = new Number(3, 4+num5, i.getAjsl(),titleFormate3);
					sheet5.addCell(z3);
					Number z4 = new Number(4, 4+num5, i.getKpsl(),titleFormate3);
					sheet5.addCell(z4);
					Number z5 = new Number(5, 4+num5, i.getKfsum(),titleFormate3);
					sheet5.addCell(z5);
					Number z51 = new Number(6, 4+num5, i.getAvg(),titleFormate3);
					sheet5.addCell(z51);
				}

				book.write();
				book.close();

			}else if(type.equals("leadership")) {
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



				Map<String, Object> map = new HashMap<String, Object>();
				if(mjbm!="") {
					map.put("mjbm", mjbm);
				}else {
					map.put("mjbm", null);
				}
				if(kssj!="") {

					map.put("kssj", kssj);
				}else {
					map.put("kssj", null);
				}
				if(jssj!="") {
					map.put("jssj", jssj);
				}else {
					map.put("jssj", null);
				}
				map.put("pageStart", 0);

				WritableSheet sheet1 = book.createSheet("警情", 0);

				sheet1.mergeCells(0,0, 7, 0);//合并单元格
				Label label = new Label(0, 0, excelname,titleFormate);
				sheet1.addCell(label);
				sheet1.setRowView(0, 700);//设置第一行的高度
				sheet1.setColumnView(3, 60);

				sheet1.mergeCells(0,1, 7, 1);
				Label label2 = new Label(0, 1, exceltime,titleFormate2);
				sheet1.addCell(label2);

				sheet1.mergeCells(0,2, 7, 2);//合并单元格
				Label a1 = new Label(0,2,"审核领导排行(警情)",titleFormate2);
				sheet1.addCell(a1);
				Label a2 = new Label(0,3,"排名",titleFormate2);
				sheet1.addCell(a2);
				Label a3 = new Label(1,3,"领导警号",titleFormate2);
				sheet1.addCell(a3);
				Label a4 = new Label(2,3,"领导姓名",titleFormate2);
				sheet1.addCell(a4);
				Label a8 = new Label(3, 3,"领导单位",titleFormate2);
				sheet1.addCell(a8);
				Label a5 = new Label(4,3,"警情数量",titleFormate2);
				sheet1.addCell(a5);
				Label a6 = new Label(5,3,"考评数量",titleFormate2);
				sheet1.addCell(a6);
				Label a7 = new Label(6,3,"扣分总值",titleFormate2);
				sheet1.addCell(a7);
				Label a71 = new Label(7,3,"个案平均分",titleFormate2);
				sheet1.addCell(a71);
				SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
				map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				map.put("casetype", "jq");
				map.put("pageEnd", cs.getleadershipCount(map).size());

				List<Mainpolice> ce =  cs.getleadership(map);
				int px = 1;
				for(Mainpolice i:ce) {
					i.setPx(px);
					px++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getleadershipajsl(map).size());
					int s= cs.getleadershipajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num1 = -1;
				for(Mainpolice i:ce) {
					num1++;
					Number q = new Number(0,4+num1,i.getPx(),titleFormate3);
					sheet1.addCell(q);
					Label q1 = new Label(1,4+num1,i.getLogin_name(),titleFormate3);
					sheet1.addCell(q1);
					Label q2 = new Label(2,4+num1,i.getMjxm(),titleFormate3);
					sheet1.addCell(q2);
					Label q3 = new Label(3, 4+num1,i.getMjdw(),titleFormate3);
					sheet1.addCell(q3);
					Number q4 = new Number(4,4+num1,i.getAjsl(),titleFormate3);
					sheet1.addCell(q4);
					Number q5 = new Number(5,4+num1,i.getKpsl(),titleFormate3);
					sheet1.addCell(q5);
					Number q6 = new Number(6,4+num1,i.getKfsum(),titleFormate3);
					sheet1.addCell(q6);
					Number q61 = new Number(7,4+num1,i.getAvg(),titleFormate3);
					sheet1.addCell(q61);
				}

				WritableSheet sheet2 = book.createSheet("行政", 1);

				sheet2.mergeCells(0,0, 7, 0);//合并单元格
				Label labelq = new Label(0, 0, excelname,titleFormate);
				sheet2.addCell(labelq);
				sheet2.setRowView(0, 700);//设置第一行的高度
				sheet2.setColumnView(3, 60);

				sheet2.mergeCells(0,1, 7, 1);
				Label label2q = new Label(0, 1, exceltime,titleFormate2);
				sheet2.addCell(label2q);

				sheet2.mergeCells(0,2, 7, 2);//合并单元格
				Label b1 = new Label(0,2,"审核领导排行(行政)",titleFormate2);
				sheet2.addCell(b1);
				Label b2 = new Label(0,3,"排名",titleFormate2);
				sheet2.addCell(b2);
				Label b3 = new Label(1,3,"领导警号",titleFormate2);
				sheet2.addCell(b3);
				Label b4 = new Label(2,3,"领导姓名",titleFormate2);
				sheet2.addCell(b4);
				Label b8 = new Label(3, 3,"领导单位",titleFormate2);
				sheet2.addCell(b8);
				Label b5 = new Label(4,3,"案件数量",titleFormate2);
				sheet2.addCell(b5);
				Label b6 = new Label(5,3,"考评数量",titleFormate2);
				sheet2.addCell(b6);
				Label b7 = new Label(6,3,"扣分总值",titleFormate2);
				sheet2.addCell(b7);
				Label b72 = new Label(7,3,"个案平均分",titleFormate2);
				sheet2.addCell(b72);

				map.put("casetype", "xz");
				map.put("pageEnd", cs.getleadershipCount(map).size());
				List<Mainpolice> ce2 =  cs.getleadership(map);
				int px2 = 1;
				for(Mainpolice i:ce2) {
					i.setPx(px2);
					px2++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getleadershipajsl(map).size());
					int s= cs.getleadershipajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num2 = -1;
				for(Mainpolice i:ce2) {
					num2++;
					Number q = new Number(0,4+num2,i.getPx(),titleFormate3);
					sheet2.addCell(q);
					Label q1 = new Label(1,4+num2,i.getLogin_name(),titleFormate3);
					sheet2.addCell(q1);
					Label q2 = new Label(2,4+num2,i.getMjxm(),titleFormate3);
					sheet2.addCell(q2);
					Label q3 = new Label(3,4+num2,i.getMjdw(),titleFormate3);
					sheet2.addCell(q3);
					Number q4 = new Number(4,4+num2,i.getAjsl(),titleFormate3);
					sheet2.addCell(q4);
					Number q5 = new Number(5,4+num2,i.getKpsl(),titleFormate3);
					sheet2.addCell(q5);
					Number q6 = new Number(6,4+num2,i.getKfsum(),titleFormate3);
					sheet2.addCell(q6);
					Number q61 = new Number(7,4+num2,i.getAvg(),titleFormate3);
					sheet2.addCell(q61);
				}


				WritableSheet sheet3 = book.createSheet("刑事", 2);

				sheet3.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqw = new Label(0, 0, excelname,titleFormate);
				sheet3.addCell(labelqw);
				sheet3.setRowView(0, 700);//设置第一行的高度
				sheet3.setColumnView(3, 60);

				sheet3.mergeCells(0,1, 7, 1);
				Label label2qw = new Label(0, 1, exceltime,titleFormate2);
				sheet3.addCell(label2qw);

				sheet3.mergeCells(0,2, 7, 2);//合并单元格
				Label b11 = new Label(0,2,"审核领导排行(刑事)",titleFormate2);
				sheet3.addCell(b11);
				Label b21 = new Label(0,3,"排名",titleFormate2);
				sheet3.addCell(b21);
				Label b31 = new Label(1,3,"领导警号",titleFormate2);
				sheet3.addCell(b31);
				Label b41 = new Label(2,3,"领导姓名",titleFormate2);
				sheet3.addCell(b41);
				Label b81 = new Label(3,3,"领导单位",titleFormate2);
				sheet3.addCell(b81);
				Label b51 = new Label(4,3,"案件数量",titleFormate2);
				sheet3.addCell(b51);
				Label b61 = new Label(5,3,"考评数量",titleFormate2);
				sheet3.addCell(b61);
				Label b71 = new Label(6,3,"扣分总值",titleFormate2);
				sheet3.addCell(b71);
				Label b712 = new Label(7,3,"个案平均分",titleFormate2);
				sheet3.addCell(b712);

				map.put("casetype", "xs");
				map.put("pageEnd", cs.getleadershipCount(map).size());
				List<Mainpolice> ce3 =  cs.getleadership(map);
				int px3 = 1;
				for(Mainpolice i:ce3) {
					i.setPx(px3);
					px3++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getleadershipajsl(map).size());
					int s= cs.getleadershipajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num3 = -1;
				for(Mainpolice i:ce3) {
					num3++;
					Number q = new Number(0,4+num3,i.getPx(),titleFormate3);
					sheet3.addCell(q);
					Label q1 = new Label(1,4+num3,i.getLogin_name(),titleFormate3);
					sheet3.addCell(q1);
					Label q2 = new Label(2,4+num3,i.getMjxm(),titleFormate3);
					sheet3.addCell(q2);
					Label q3 = new Label(3,4+num3,i.getMjdw(),titleFormate3);
					sheet3.addCell(q3);
					Number q4 = new Number(4,4+num3,i.getAjsl(),titleFormate3);
					sheet3.addCell(q4);
					Number q5 = new Number(5,4+num3,i.getKpsl(),titleFormate3);
					sheet3.addCell(q5);
					Number q6 = new Number(6,4+num3,i.getKfsum(),titleFormate3);
					sheet3.addCell(q6);
					Number q61 = new Number(7,4+num3,i.getAvg(),titleFormate3);
					sheet3.addCell(q61);
				}

				WritableSheet sheet366 = book.createSheet("执法监督", 5);

				sheet366.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqw66 = new Label(0, 0, excelname,titleFormate);
				sheet366.addCell(labelqw66);
				sheet366.setRowView(0, 700);//设置第一行的高度
				sheet366.setColumnView(3, 60);

				sheet366.mergeCells(0,1, 7, 1);
				Label label2qw66 = new Label(0, 1, exceltime,titleFormate2);
				sheet366.addCell(label2qw66);

				sheet366.mergeCells(0,2, 7, 2);//合并单元格
				Label b1166 = new Label(0,2,"审核领导排行(刑事)",titleFormate2);
				sheet366.addCell(b1166);
				Label b2166 = new Label(0,3,"排名",titleFormate2);
				sheet366.addCell(b2166);
				Label b3166 = new Label(1,3,"领导警号",titleFormate2);
				sheet366.addCell(b3166);
				Label b4166 = new Label(2,3,"领导姓名",titleFormate2);
				sheet366.addCell(b4166);
				Label b8166 = new Label(3,3,"领导单位",titleFormate2);
				sheet366.addCell(b8166);
				Label b5166 = new Label(4,3,"执法监督数量",titleFormate2);
				sheet366.addCell(b5166);
				Label b6166 = new Label(5,3,"考评数量",titleFormate2);
				sheet366.addCell(b6166);
				Label b7166 = new Label(6,3,"扣分总值",titleFormate2);
				sheet366.addCell(b7166);
				Label b71266 = new Label(7,3,"个案平均分",titleFormate2);
				sheet366.addCell(b71266);

				map.put("casetype", "zfjd");
				map.put("pageEnd", cs.getleadershipCount(map).size());
				List<Mainpolice> ce366=  cs.getleadership(map);
				int px366 = 1;
				for(Mainpolice i:ce366) {
					i.setPx(px366);
					px366++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getleadershipajsl(map).size());
					int s= cs.getleadershipajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num366 = -1;
				for(Mainpolice i:ce366) {
					num366++;
					Number q = new Number(0,4+num366,i.getPx(),titleFormate3);
					sheet366.addCell(q);
					Label q1 = new Label(1,4+num366,i.getLogin_name(),titleFormate3);
					sheet366.addCell(q1);
					Label q2 = new Label(2,4+num366,i.getMjxm(),titleFormate3);
					sheet366.addCell(q2);
					Label q3 = new Label(3,4+num366,i.getMjdw(),titleFormate3);
					sheet366.addCell(q3);
					Number q4 = new Number(4,4+num366,i.getAjsl(),titleFormate3);
					sheet366.addCell(q4);
					Number q5 = new Number(5,4+num366,i.getKpsl(),titleFormate3);
					sheet366.addCell(q5);
					Number q6 = new Number(6,4+num366,i.getKfsum(),titleFormate3);
					sheet366.addCell(q6);
					Number q61 = new Number(7,4+num366,i.getAvg(),titleFormate3);
					sheet366.addCell(q61);
				}


				WritableSheet sheet4 = book.createSheet("行政+刑事", 3);

				sheet4.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqwe = new Label(0, 0, excelname,titleFormate);
				sheet4.addCell(labelqwe);
				sheet4.setRowView(0, 700);//设置第一行的高度
				sheet4.setColumnView(3, 60);

				sheet4.mergeCells(0,1, 7, 1);
				Label label2qwe = new Label(0, 1, exceltime,titleFormate2);
				sheet4.addCell(label2qwe);

				sheet4.mergeCells(0,2, 7, 2);//合并单元格
				Label b11q = new Label(0,2,"审核领导排行(行政+刑事)",titleFormate2);
				sheet4.addCell(b11q);
				Label b21q = new Label(0,3,"排名",titleFormate2);
				sheet4.addCell(b21q);
				Label b31q = new Label(1,3,"领导警号",titleFormate2);
				sheet4.addCell(b31q);
				Label b41q = new Label(2,3,"领导姓名",titleFormate2);
				sheet4.addCell(b41q);
				Label b81q = new Label(3,3,"领导单位",titleFormate2);
				sheet4.addCell(b81q);
				Label b51q = new Label(4,3,"案件数量",titleFormate2);
				sheet4.addCell(b51q);
				Label b61q = new Label(5,3,"考评数量",titleFormate2);
				sheet4.addCell(b61q);
				Label b71q = new Label(6,3,"扣分总值",titleFormate2);
				sheet4.addCell(b71q);
				Label b712q = new Label(7,3,"个案平均分",titleFormate2);
				sheet4.addCell(b712q);

				map.put("casetype", "xzxs");
				map.put("pageEnd", cs.getleadershipCount(map).size());
				List<Mainpolice> ce4 =  cs.getleadership(map);
				int px4 = 1;
				for(Mainpolice i:ce4) {
					i.setPx(px4);
					px4++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getleadershipajsl(map).size());
					int s= cs.getleadershipajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num4 = -1;
				for(Mainpolice i:ce4) {
					num4++;
					Number q = new Number(0,4+num4,i.getPx(),titleFormate3);
					sheet4.addCell(q);
					Label q1 = new Label(1,4+num4,i.getLogin_name(),titleFormate3);
					sheet4.addCell(q1);
					Label q2 = new Label(2,4+num4,i.getMjxm(),titleFormate3);
					sheet4.addCell(q2);
					Label q3 = new Label(3,4+num4,i.getMjdw(),titleFormate3);
					sheet4.addCell(q3);
					Number q4 = new Number(4,4+num4,i.getAjsl(),titleFormate3);
					sheet4.addCell(q4);
					Number q5 = new Number(5,4+num4,i.getKpsl(),titleFormate3);
					sheet4.addCell(q5);
					Number q6 = new Number(6,4+num4,i.getKfsum(),titleFormate3);
					sheet4.addCell(q6);
					Number q61 = new Number(7,4+num4,i.getAvg(),titleFormate3);
					sheet4.addCell(q61);
				}


				WritableSheet sheet5 = book.createSheet("三合一", 4);

				sheet5.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqwer = new Label(0, 0, excelname,titleFormate);
				sheet5.addCell(labelqwer);
				sheet5.setRowView(0, 700);//设置第一行的高度
				sheet5.setColumnView(3, 60);

				sheet5.mergeCells(0,1, 7, 1);
				Label label2qwer = new Label(0, 1, exceltime,titleFormate2);
				sheet5.addCell(label2qwer);

				sheet5.mergeCells(0,2, 7, 2);//合并单元格
				Label b11qw = new Label(0,2,"审核领导排行(三合一)",titleFormate2);
				sheet5.addCell(b11qw);
				Label b21qw = new Label(0,3,"排名",titleFormate2);
				sheet5.addCell(b21qw);
				Label b31qw = new Label(1,3,"领导警号",titleFormate2);
				sheet5.addCell(b31qw);
				Label b41qw = new Label(2,3,"领导姓名",titleFormate2);
				sheet5.addCell(b41qw);
				Label b81qw = new Label(3,3,"领导单位",titleFormate2);
				sheet5.addCell(b81qw);
				Label b51qw = new Label(4,3,"案件数量",titleFormate2);
				sheet5.addCell(b51qw);
				Label b61qw = new Label(5,3,"考评数量",titleFormate2);
				sheet5.addCell(b61qw);
				Label b71qw = new Label(6,3,"扣分总值",titleFormate2);
				sheet5.addCell(b71qw);
				Label b712qw = new Label(7,3,"个案平均分",titleFormate2);
				sheet5.addCell(b712qw);

				map.put("casetype", "all");
				map.put("pageEnd", cs.getleadershipCount(map).size());
				List<Mainpolice> ce5 =  cs.getleadership(map);
				int px5 = 1;
				for(Mainpolice i:ce5) {
					i.setPx(px5);
					px5++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getleadershipajsl(map).size());
					int s= cs.getleadershipajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num5 = -1;
				for(Mainpolice i:ce5) {
					num5++;
					Number q = new Number(0,4+num5,i.getPx(),titleFormate3);
					sheet5.addCell(q);
					Label q1 = new Label(1,4+num5,i.getLogin_name(),titleFormate3);
					sheet5.addCell(q1);
					Label q2 = new Label(2,4+num5,i.getMjxm(),titleFormate3);
					sheet5.addCell(q2);
					Label q3 = new Label(3,4+num5,i.getMjdw(),titleFormate3);
					sheet5.addCell(q3);
					Number q4 = new Number(4,4+num5,i.getAjsl(),titleFormate3);
					sheet5.addCell(q4);
					Number q5 = new Number(5,4+num5,i.getKpsl(),titleFormate3);
					sheet5.addCell(q5);
					Number q6 = new Number(6,4+num5,i.getKfsum(),titleFormate3);
					sheet5.addCell(q6);
					Number q61 = new Number(7,4+num5,i.getAvg(),titleFormate3);
					sheet5.addCell(q61);
				}

				book.write();
				book.close();

			}else if(type.equals("dutypolice")) {
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

				Map<String, Object> map = new HashMap<String, Object>();
				if(mjbm!="") {
					map.put("mjbm", mjbm);
				}else {
					map.put("mjbm", null);
				}
				if(kssj!="") {

					map.put("kssj", kssj);
				}else {
					map.put("kssj", null);
				}
				if(jssj!="") {
					map.put("jssj", jssj);
				}else {
					map.put("jssj", null);
				}
				if(mjid!="" && !mjid.equals("0")) {
					map.put("mjid", mjid);
				}else {
					map.put("mjid", null);
				}

				map.put("pageStart", 0);

				WritableSheet sheet1 = book.createSheet("警情", 0);

				sheet1.mergeCells(0,0, 7, 0);//合并单元格
				Label label = new Label(0, 0, excelname,titleFormate);
				sheet1.addCell(label);
				sheet1.setRowView(0, 700);//设置第一行的高度
				sheet1.setColumnView(3, 60);

				sheet1.mergeCells(0,1, 7, 1);
				Label label2 = new Label(0, 1, exceltime,titleFormate2);
				sheet1.addCell(label2);

				sheet1.mergeCells(0,2, 7, 2);//合并单元格
				Label a1 = new Label(0,2,"责任民警统计排行(警情)",titleFormate2);
				sheet1.addCell(a1);
				Label a2 = new Label(0,3,"排名",titleFormate2);
				sheet1.addCell(a2);
				Label a3 = new Label(1,3,"民警警号",titleFormate2);
				sheet1.addCell(a3);
				Label a4 = new Label(2,3,"民警姓名",titleFormate2);
				sheet1.addCell(a4);
				Label a5 = new Label(3,3,"民警单位",titleFormate2);
				sheet1.addCell(a5);
				Label a6 = new Label(4,3,"警情数量",titleFormate2);
				sheet1.addCell(a6);
				Label a7 = new Label(5,3,"考评数量",titleFormate2);
				sheet1.addCell(a7);
				Label a8 = new Label(6,3,"扣分总值",titleFormate2);
				sheet1.addCell(a8);
				Label a81 = new Label(7,3,"个案平均分",titleFormate2);
				sheet1.addCell(a81);
				SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
				map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				map.put("casetype", "jq");
				map.put("pageEnd", cs.getdutypoliceCount(map));

				List<Mainpolice> ce =  cs.getdutypolice(map);
				int px = 1;
				for(Mainpolice i:ce) {
					i.setPx(px);
					px++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getzerenajsl(map).size());
					i.setKpsl(cs.getzerenkpsl(map).size());

					int s= cs.getzerenajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num1 = -1;
				for(Mainpolice i:ce) {
					num1++;
					Number x = new Number(0,4+num1,i.getPx(),titleFormate3);
					sheet1.addCell(x);
					Label x1 = new Label(1,4+num1,i.getLogin_name(),titleFormate3);
					sheet1.addCell(x1);
					Label x2 = new Label(2,4+num1,i.getMjxm(),titleFormate3);
					sheet1.addCell(x2);
					Label x3 = new Label(3,4+num1,i.getMjdw(),titleFormate3);
					sheet1.addCell(x3);
					Number x4 = new Number(4,4+num1,i.getAjsl(),titleFormate3);
					sheet1.addCell(x4);
					Number x5 = new Number(5,4+num1,i.getKpsl(),titleFormate3);
					sheet1.addCell(x5);
					Number x6 = new Number(6,4+num1,i.getKfsum(),titleFormate3);
					sheet1.addCell(x6);
					Number x61 = new Number(7,4+num1,i.getAvg(),titleFormate3);
					sheet1.addCell(x61);
				}

				WritableSheet sheet2 = book.createSheet("行政", 1);

				sheet2.mergeCells(0,0, 7, 0);//合并单元格
				Label labelq = new Label(0, 0, excelname,titleFormate);
				sheet2.addCell(labelq);
				sheet2.setRowView(0, 700);//设置第一行的高度
				sheet2.setColumnView(3, 60);

				sheet2.mergeCells(0,1, 7, 1);
				Label label2q = new Label(0, 1, exceltime,titleFormate2);
				sheet2.addCell(label2q);

				sheet2.mergeCells(0,2, 7, 2);//合并单元格
				Label b1 = new Label(0,2,"责任民警统计排行(行政)",titleFormate2);
				sheet2.addCell(b1);
				Label b2 = new Label(0,3,"排名",titleFormate2);
				sheet2.addCell(b2);
				Label b3 = new Label(1,3,"民警警号",titleFormate2);
				sheet2.addCell(b3);
				Label b4 = new Label(2,3,"民警姓名",titleFormate2);
				sheet2.addCell(b4);
				Label b5 = new Label(3,3,"民警单位",titleFormate2);
				sheet2.addCell(b5);
				Label b6 = new Label(4,3,"案件数量",titleFormate2);
				sheet2.addCell(b6);
				Label b7 = new Label(5,3,"考评数量",titleFormate2);
				sheet2.addCell(b7);
				Label b8 = new Label(6,3,"扣分总值",titleFormate2);
				sheet2.addCell(b8);
				Label b81 = new Label(7,3,"个案平均分",titleFormate2);
				sheet2.addCell(b81);

				map.put("casetype", "xz");
				map.put("pageEnd", cs.getdutypoliceCount(map));

				List<Mainpolice> ce2 =  cs.getdutypolice(map);
				int px2 = 1;
				for(Mainpolice i:ce2) {
					i.setPx(px2);
					px2++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getzerenajsl(map).size());
					i.setKpsl(cs.getzerenkpsl(map).size());

					int s= cs.getzerenajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num2 = -1;
				for(Mainpolice i:ce2) {
					num2++;
					Number x = new Number(0,4+num2,i.getPx(),titleFormate3);
					sheet2.addCell(x);
					Label x1 = new Label(1,4+num2,i.getLogin_name(),titleFormate3);
					sheet2.addCell(x1);
					Label x2 = new Label(2,4+num2,i.getMjxm(),titleFormate3);
					sheet2.addCell(x2);
					Label x3 = new Label(3,4+num2,i.getMjdw(),titleFormate3);
					sheet2.addCell(x3);
					Number x4 = new Number(4,4+num2,i.getAjsl(),titleFormate3);
					sheet2.addCell(x4);
					Number x5 = new Number(5,4+num2,i.getKpsl(),titleFormate3);
					sheet2.addCell(x5);
					Number x6 = new Number(6,4+num2,i.getKfsum(),titleFormate3);
					sheet2.addCell(x6);
					Number x61 = new Number(7,4+num2,i.getAvg(),titleFormate3);
					sheet2.addCell(x61);
				}

				WritableSheet sheet3 = book.createSheet("刑事", 2);

				sheet3.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqw = new Label(0, 0, excelname,titleFormate);
				sheet3.addCell(labelqw);
				sheet3.setRowView(0, 700);//设置第一行的高度
				sheet3.setColumnView(3, 60);

				sheet3.mergeCells(0,1, 7, 1);
				Label label2qw = new Label(0, 1, exceltime,titleFormate2);
				sheet3.addCell(label2qw);

				sheet3.mergeCells(0,2, 7, 2);//合并单元格
				Label b11 = new Label(0,2,"责任民警统计排行(刑事)",titleFormate2);
				sheet3.addCell(b11);
				Label b21 = new Label(0,3,"排名",titleFormate2);
				sheet3.addCell(b21);
				Label b31 = new Label(1,3,"民警警号",titleFormate2);
				sheet3.addCell(b31);
				Label b41 = new Label(2,3,"民警姓名",titleFormate2);
				sheet3.addCell(b41);
				Label b51 = new Label(3,3,"民警单位",titleFormate2);
				sheet3.addCell(b51);
				Label b61 = new Label(4,3,"案件数量",titleFormate2);
				sheet3.addCell(b61);
				Label b71 = new Label(5,3,"考评数量",titleFormate2);
				sheet3.addCell(b71);
				Label b812 = new Label(6,3,"扣分总值",titleFormate2);
				sheet3.addCell(b812);
				Label b813 = new Label(7,3,"个案平均分",titleFormate2);
				sheet3.addCell(b813);

				map.put("casetype", "xs");
				map.put("pageEnd", cs.getdutypoliceCount(map));

				List<Mainpolice> ce3 =  cs.getdutypolice(map);
				int px3 = 1;
				for(Mainpolice i:ce3) {
					i.setPx(px3);
					px3++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getzerenajsl(map).size());
					i.setKpsl(cs.getzerenkpsl(map).size());

					int s= cs.getzerenajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num3 = -1;
				for(Mainpolice i:ce3) {
					num3++;
					Number x = new Number(0,4+num3,i.getPx(),titleFormate3);
					sheet3.addCell(x);
					Label x1 = new Label(1,4+num3,i.getLogin_name(),titleFormate3);
					sheet3.addCell(x1);
					Label x2 = new Label(2,4+num3,i.getMjxm(),titleFormate3);
					sheet3.addCell(x2);
					Label x3 = new Label(3,4+num3,i.getMjdw(),titleFormate3);
					sheet3.addCell(x3);
					Number x4 = new Number(4,4+num3,i.getAjsl(),titleFormate3);
					sheet3.addCell(x4);
					Number x5 = new Number(5,4+num3,i.getKpsl(),titleFormate3);
					sheet3.addCell(x5);
					Number x6 = new Number(6,4+num3,i.getKfsum(),titleFormate3);
					sheet3.addCell(x6);
					Number x61 = new Number(7,4+num3,i.getAvg(),titleFormate3);
					sheet3.addCell(x61);
				}

				WritableSheet sheet366 = book.createSheet("执法监督", 5);

				sheet366.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqw66 = new Label(0, 0, excelname,titleFormate);
				sheet366.addCell(labelqw66);
				sheet366.setRowView(0, 700);//设置第一行的高度
				sheet366.setColumnView(3, 60);

				sheet366.mergeCells(0,1, 7, 1);
				Label label2qw66 = new Label(0, 1, exceltime,titleFormate2);
				sheet366.addCell(label2qw66);

				sheet366.mergeCells(0,2, 7, 2);//合并单元格
				Label b1166 = new Label(0,2,"责任民警统计排行(刑事)",titleFormate2);
				sheet366.addCell(b1166);
				Label b2166 = new Label(0,3,"排名",titleFormate2);
				sheet366.addCell(b2166);
				Label b3166 = new Label(1,3,"民警警号",titleFormate2);
				sheet366.addCell(b3166);
				Label b4166 = new Label(2,3,"民警姓名",titleFormate2);
				sheet366.addCell(b4166);
				Label b5166 = new Label(3,3,"民警单位",titleFormate2);
				sheet366.addCell(b5166);
				Label b6166 = new Label(4,3,"案件数量",titleFormate2);
				sheet366.addCell(b6166);
				Label b7166 = new Label(5,3,"考评数量",titleFormate2);
				sheet366.addCell(b7166);
				Label b81266 = new Label(6,3,"扣分总值",titleFormate2);
				sheet366.addCell(b81266);
				Label b81366 = new Label(7,3,"个案平均分",titleFormate2);
				sheet366.addCell(b81366);

				map.put("casetype", "zfjd");
				map.put("pageEnd", cs.getdutypoliceCount(map));

				List<Mainpolice> ce366 =  cs.getdutypolice(map);
				int px366 = 1;
				for(Mainpolice i:ce366) {
					i.setPx(px366);
					px366++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getzerenajsl(map).size());
					i.setKpsl(cs.getzerenkpsl(map).size());

					int s= cs.getzerenajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num366 = -1;
				for(Mainpolice i:ce366) {
					num366++;
					Number x = new Number(0,4+num366,i.getPx(),titleFormate3);
					sheet366.addCell(x);
					Label x1 = new Label(1,4+num366,i.getLogin_name(),titleFormate3);
					sheet366.addCell(x1);
					Label x2 = new Label(2,4+num366,i.getMjxm(),titleFormate3);
					sheet366.addCell(x2);
					Label x3 = new Label(3,4+num366,i.getMjdw(),titleFormate3);
					sheet366.addCell(x3);
					Number x4 = new Number(4,4+num366,i.getAjsl(),titleFormate3);
					sheet366.addCell(x4);
					Number x5 = new Number(5,4+num366,i.getKpsl(),titleFormate3);
					sheet366.addCell(x5);
					Number x6 = new Number(6,4+num366,i.getKfsum(),titleFormate3);
					sheet366.addCell(x6);
					Number x61 = new Number(7,4+num366,i.getAvg(),titleFormate3);
					sheet366.addCell(x61);
				}




				WritableSheet sheet4 = book.createSheet("行政+刑事", 3);

				sheet4.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqwe = new Label(0, 0, excelname,titleFormate);
				sheet4.addCell(labelqwe);
				sheet4.setRowView(0, 700);//设置第一行的高度
				sheet4.setColumnView(3, 60);

				sheet4.mergeCells(0,1, 7, 1);
				Label label2qwe = new Label(0, 1, exceltime,titleFormate2);
				sheet4.addCell(label2qwe);

				sheet4.mergeCells(0,2, 7, 2);//合并单元格
				Label b11q = new Label(0,2,"责任民警统计排行(行政+刑事)",titleFormate2);
				sheet4.addCell(b11q);
				Label b21q = new Label(0,3,"排名",titleFormate2);
				sheet4.addCell(b21q);
				Label b31q = new Label(1,3,"民警警号",titleFormate2);
				sheet4.addCell(b31q);
				Label b41q = new Label(2,3,"民警姓名",titleFormate2);
				sheet4.addCell(b41q);
				Label b51q = new Label(3,3,"民警单位",titleFormate2);
				sheet4.addCell(b51q);
				Label b61q = new Label(4,3,"案件数量",titleFormate2);
				sheet4.addCell(b61q);
				Label b71q = new Label(5,3,"考评数量",titleFormate2);
				sheet4.addCell(b71q);
				Label b812q = new Label(6,3,"扣分总值",titleFormate2);
				sheet4.addCell(b812q);
				Label b813q = new Label(7,3,"个案平均分",titleFormate2);
				sheet4.addCell(b813q);

				map.put("casetype", "xzxs");
				map.put("pageEnd", cs.getdutypoliceCount(map));

				List<Mainpolice> ce4 =  cs.getdutypolice(map);
				int px4 = 1;
				for(Mainpolice i:ce4) {
					i.setPx(px4);
					px4++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getzerenajsl(map).size());
					i.setKpsl(cs.getzerenkpsl(map).size());

					int s= cs.getzerenajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num4 = -1;
				for(Mainpolice i:ce4) {
					num4++;
					Number x = new Number(0,4+num4,i.getPx(),titleFormate3);
					sheet4.addCell(x);
					Label x1 = new Label(1,4+num4,i.getLogin_name(),titleFormate3);
					sheet4.addCell(x1);
					Label x2 = new Label(2,4+num4,i.getMjxm(),titleFormate3);
					sheet4.addCell(x2);
					Label x3 = new Label(3,4+num4,i.getMjdw(),titleFormate3);
					sheet4.addCell(x3);
					Number x4 = new Number(4,4+num4,i.getAjsl(),titleFormate3);
					sheet4.addCell(x4);
					Number x5 = new Number(5,4+num4,i.getKpsl(),titleFormate3);
					sheet4.addCell(x5);
					Number x6 = new Number(6,4+num4,i.getKfsum(),titleFormate3);
					sheet4.addCell(x6);
					Number x61 = new Number(7,4+num4,i.getAvg(),titleFormate3);
					sheet4.addCell(x61);
				}

				WritableSheet sheet5 = book.createSheet("三合一", 4);

				sheet5.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqwer = new Label(0, 0, excelname,titleFormate);
				sheet5.addCell(labelqwer);
				sheet5.setRowView(0, 700);//设置第一行的高度
				sheet5.setColumnView(3, 60);

				sheet5.mergeCells(0,1, 7, 1);
				Label label2qwer = new Label(0, 1, exceltime,titleFormate2);
				sheet5.addCell(label2qwer);

				sheet5.mergeCells(0,2, 7, 2);//合并单元格
				Label b11qw = new Label(0,2,"责任民警统计排行(三合一)",titleFormate2);
				sheet5.addCell(b11qw);
				Label b21qw = new Label(0,3,"排名",titleFormate2);
				sheet5.addCell(b21qw);
				Label b31qw = new Label(1,3,"民警警号",titleFormate2);
				sheet5.addCell(b31qw);
				Label b41qw = new Label(2,3,"民警姓名",titleFormate2);
				sheet5.addCell(b41qw);
				Label b51qw = new Label(3,3,"民警单位",titleFormate2);
				sheet5.addCell(b51qw);
				Label b61qw = new Label(4,3,"案件数量",titleFormate2);
				sheet5.addCell(b61qw);
				Label b71qw = new Label(5,3,"考评数量",titleFormate2);
				sheet5.addCell(b71qw);
				Label b812qw = new Label(6,3,"扣分总值",titleFormate2);
				sheet5.addCell(b812qw);
				Label b813qw = new Label(7,3,"个案平均分",titleFormate2);
				sheet5.addCell(b813qw);

				map.put("casetype", "all");
				map.put("pageEnd", cs.getdutypoliceCount(map));

				List<Mainpolice> ce5 =  cs.getdutypolice(map);
				int px5 = 1;
				for(Mainpolice i:ce5) {
					i.setPx(px5);
					px5++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getzerenajsl(map).size());
					i.setKpsl(cs.getzerenkpsl(map).size());

					int s= cs.getzerenajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num5 = -1;
				for(Mainpolice i:ce5) {
					num5++;
					Number x = new Number(0,4+num5,i.getPx(),titleFormate3);
					sheet5.addCell(x);
					Label x1 = new Label(1,4+num5,i.getLogin_name(),titleFormate3);
					sheet5.addCell(x1);
					Label x2 = new Label(2,4+num5,i.getMjxm(),titleFormate3);
					sheet5.addCell(x2);
					Label x3 = new Label(3,4+num5,i.getMjdw(),titleFormate3);
					sheet5.addCell(x3);
					Number x4 = new Number(4,4+num5,i.getAjsl(),titleFormate3);
					sheet5.addCell(x4);
					Number x5 = new Number(5,4+num5,i.getKpsl(),titleFormate3);
					sheet5.addCell(x5);
					Number x6 = new Number(6,4+num5,i.getKfsum(),titleFormate3);
					sheet5.addCell(x6);
					Number x61 = new Number(7,4+num5,i.getAvg(),titleFormate3);
					sheet5.addCell(x61);
				}

				book.write();
				book.close();


			}else if(type.equals("maininvestigator")) {
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




				Map<String, Object> map = new HashMap<String, Object>();
				if(mjbm!="") {
					map.put("mjbm", mjbm);
				}else {
					map.put("mjbm", null);
				}
				if(kssj!="") {

					map.put("kssj", kssj);
				}else {
					map.put("kssj", null);
				}
				if(jssj!="") {
					map.put("jssj", jssj);
				}else {
					map.put("jssj", null);
				}
				map.put("pageStart", 0);
				map.put("casetype", "jq");
				SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
				map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				map.put("pageEnd", cs.getEvaluationListCount(map).size());

				WritableSheet sheet1 = book.createSheet("警情", 0);

				sheet1.mergeCells(0,0, 7, 0);//合并单元格
				Label label = new Label(0, 0, excelname,titleFormate);
				sheet1.addCell(label);
				sheet1.setRowView(0, 700);//设置第一行的高度
				sheet1.setColumnView(3, 60);

				sheet1.mergeCells(0,1, 7, 1);
				Label label2 = new Label(0, 1, exceltime,titleFormate2);
				sheet1.addCell(label2);

				sheet1.mergeCells(0,2, 7, 2);//合并单元格
				Label a1 = new Label(0,2,"主办侦查员排行(警情)",titleFormate2);
				sheet1.addCell(a1);
				Label a2 = new Label(0,3,"排名",titleFormate2);
				sheet1.addCell(a2);
				Label a3 = new Label(1,3,"民警警号",titleFormate2);
				sheet1.addCell(a3);
				Label a4 = new Label(2,3,"民警姓名",titleFormate2);
				sheet1.addCell(a4);
				Label a5 = new Label(3,3,"民警单位",titleFormate2);
				sheet1.addCell(a5);
				Label a6 = new Label(4,3,"警情数量",titleFormate2);
				sheet1.addCell(a6);
				Label a7 = new Label(5,3,"考评数量",titleFormate2);
				sheet1.addCell(a7);
				Label a8 = new Label(6,3,"总扣分",titleFormate2);
				sheet1.addCell(a8);
				Label a81 = new Label(7,3,"个案平均分",titleFormate2);
				sheet1.addCell(a81);

				List<Mainpolice> ce =  cs.getEvaluationList(map);
				int px = 1;
				for(Mainpolice i:ce) {
					i.setPx(px);
					px++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getajsl(map).size());
					int s= cs.getajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num1 = -1;
				for(Mainpolice i:ce) {
					num1++;
					Number s2 = new Number(0,4+num1,i.getPx(),titleFormate3);
					sheet1.addCell(s2);
					Label s3 = new Label(1,4+num1,i.getLogin_name(),titleFormate3);
					sheet1.addCell(s3);
					Label s4 = new Label(2,4+num1,i.getMjxm(),titleFormate3);
					sheet1.addCell(s4);
					Label s5 = new Label(3,4+num1,i.getMjdw(),titleFormate3);
					sheet1.addCell(s5);
					Number s6 = new Number(4,4+num1,i.getAjsl(),titleFormate3);
					sheet1.addCell(s6);
					Number s7 = new Number(5,4+num1,i.getKpsl(),titleFormate3);
					sheet1.addCell(s7);
					Number s8 = new Number(6,4+num1,i.getKfsum(),titleFormate3);
					sheet1.addCell(s8);
					Number s81 = new Number(7,4+num1,i.getAvg(),titleFormate3);
					sheet1.addCell(s81);
				}

				map.put("casetype", "xz");
				map.put("pageEnd", cs.getEvaluationListCount(map).size());
				WritableSheet sheet2 = book.createSheet("行政", 1);

				sheet2.mergeCells(0,0, 7, 0);//合并单元格
				Label labelq = new Label(0, 0, excelname,titleFormate);
				sheet2.addCell(labelq);
				sheet2.setRowView(0, 700);//设置第一行的高度
				sheet2.setColumnView(3, 60);

				sheet2.mergeCells(0,1, 7, 1);
				Label label2q = new Label(0, 1, exceltime,titleFormate2);
				sheet2.addCell(label2q);

				sheet2.mergeCells(0,2, 7, 2);//合并单元格
				Label b1 = new Label(0,2,"主办侦查员排行(行政)",titleFormate2);
				sheet2.addCell(b1);
				Label b2 = new Label(0,3,"排名",titleFormate2);
				sheet2.addCell(b2);
				Label b3 = new Label(1,3,"民警警号",titleFormate2);
				sheet2.addCell(b3);
				Label b4 = new Label(2,3,"民警姓名",titleFormate2);
				sheet2.addCell(b4);
				Label b5 = new Label(3,3,"民警单位",titleFormate2);
				sheet2.addCell(b5);
				Label b6 = new Label(4,3,"案件数量",titleFormate2);
				sheet2.addCell(b6);
				Label b7 = new Label(5,3,"考评数量",titleFormate2);
				sheet2.addCell(b7);
				Label b8 = new Label(6,3,"总扣分",titleFormate2);
				sheet2.addCell(b8);
				Label b81 = new Label(7,3,"个案平均分",titleFormate2);
				sheet2.addCell(b81);

				List<Mainpolice> ce2 =  cs.getEvaluationList(map);
				int px2 = 1;
				for(Mainpolice i:ce2) {
					i.setPx(px2);
					px2++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getajsl(map).size());
					int s= cs.getajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num2 = -1;
				for(Mainpolice i:ce2) {
					num2++;
					Number s2 = new Number(0,4+num2,i.getPx(),titleFormate3);
					sheet2.addCell(s2);
					Label s3 = new Label(1,4+num2,i.getLogin_name(),titleFormate3);
					sheet2.addCell(s3);
					Label s4 = new Label(2,4+num2,i.getMjxm(),titleFormate3);
					sheet2.addCell(s4);
					Label s5 = new Label(3,4+num2,i.getMjdw(),titleFormate3);
					sheet2.addCell(s5);
					Number s6 = new Number(4,4+num2,i.getAjsl(),titleFormate3);
					sheet2.addCell(s6);
					Number s7 = new Number(5,4+num2,i.getKpsl(),titleFormate3);
					sheet2.addCell(s7);
					Number s8 = new Number(6,4+num2,i.getKfsum(),titleFormate3);
					sheet2.addCell(s8);
					Number s81 = new Number(7,4+num2,i.getAvg(),titleFormate3);
					sheet2.addCell(s81);
				}

				map.put("casetype", "xs");
				map.put("pageEnd", cs.getEvaluationListCount(map).size());
				WritableSheet sheet3 = book.createSheet("刑事", 2);

				sheet3.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqw = new Label(0, 0, excelname,titleFormate);
				sheet3.addCell(labelqw);
				sheet3.setRowView(0, 700);//设置第一行的高度
				sheet3.setColumnView(3, 60);

				sheet3.mergeCells(0,1, 7, 1);
				Label label2qw = new Label(0, 1, exceltime,titleFormate2);
				sheet3.addCell(label2qw);

				sheet3.mergeCells(0,2, 7, 2);//合并单元格
				Label b11 = new Label(0,2,"主办侦查员排行(刑事)",titleFormate2);
				sheet3.addCell(b11);
				Label b21 = new Label(0,3,"排名",titleFormate2);
				sheet3.addCell(b21);
				Label b31 = new Label(1,3,"民警警号",titleFormate2);
				sheet3.addCell(b31);
				Label b41 = new Label(2,3,"民警姓名",titleFormate2);
				sheet3.addCell(b41);
				Label b51 = new Label(3,3,"民警单位",titleFormate2);
				sheet3.addCell(b51);
				Label b61 = new Label(4,3,"案件数量",titleFormate2);
				sheet3.addCell(b61);
				Label b71 = new Label(5,3,"考评数量",titleFormate2);
				sheet3.addCell(b71);
				Label b812 = new Label(6,3,"总扣分",titleFormate2);
				sheet3.addCell(b812);
				Label b813 = new Label(7,3,"个案平均分",titleFormate2);
				sheet3.addCell(b813);

				List<Mainpolice> ce3 =  cs.getEvaluationList(map);
				int px3 = 1;
				for(Mainpolice i:ce3) {
					i.setPx(px3);
					px3++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getajsl(map).size());
					int s= cs.getajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num3 = -1;
				for(Mainpolice i:ce3) {
					num3++;
					Number s2 = new Number(0,4+num3,i.getPx(),titleFormate3);
					sheet3.addCell(s2);
					Label s3 = new Label(1,4+num3,i.getLogin_name(),titleFormate3);
					sheet3.addCell(s3);
					Label s4 = new Label(2,4+num3,i.getMjxm(),titleFormate3);
					sheet3.addCell(s4);
					Label s5 = new Label(3,4+num3,i.getMjdw(),titleFormate3);
					sheet3.addCell(s5);
					Number s6 = new Number(4,4+num3,i.getAjsl(),titleFormate3);
					sheet3.addCell(s6);
					Number s7 = new Number(5,4+num3,i.getKpsl(),titleFormate3);
					sheet3.addCell(s7);
					Number s8 = new Number(6,4+num3,i.getKfsum(),titleFormate3);
					sheet3.addCell(s8);
					Number s82 = new Number(7,4+num3,i.getAvg(),titleFormate3);
					sheet3.addCell(s82);
				}

				map.put("casetype", "zfjd");
				map.put("pageEnd", cs.getEvaluationListCount(map).size());
				WritableSheet sheet366 = book.createSheet("执法监督", 5);

				sheet366.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqw66 = new Label(0, 0, excelname,titleFormate);
				sheet366.addCell(labelqw66);
				sheet366.setRowView(0, 700);//设置第一行的高度
				sheet366.setColumnView(3, 60);

				sheet366.mergeCells(0,1, 7, 1);
				Label label2qw66 = new Label(0, 1, exceltime,titleFormate2);
				sheet366.addCell(label2qw66);

				sheet366.mergeCells(0,2, 7, 2);//合并单元格
				Label b1166 = new Label(0,2,"主办侦查员排行(刑事)",titleFormate2);
				sheet366.addCell(b1166);
				Label b2166 = new Label(0,3,"排名",titleFormate2);
				sheet366.addCell(b2166);
				Label b3166 = new Label(1,3,"民警警号",titleFormate2);
				sheet366.addCell(b3166);
				Label b4166 = new Label(2,3,"民警姓名",titleFormate2);
				sheet366.addCell(b4166);
				Label b5166 = new Label(3,3,"民警单位",titleFormate2);
				sheet366.addCell(b5166);
				Label b6166 = new Label(4,3,"执法监督数量",titleFormate2);
				sheet366.addCell(b6166);
				Label b7166 = new Label(5,3,"考评数量",titleFormate2);
				sheet366.addCell(b7166);
				Label b81266 = new Label(6,3,"总扣分",titleFormate2);
				sheet366.addCell(b81266);
				Label b81366 = new Label(7,3,"个案平均分",titleFormate2);
				sheet366.addCell(b81366);

				List<Mainpolice> ce366 =  cs.getEvaluationList(map);
				int px366 = 1;
				for(Mainpolice i:ce366) {
					i.setPx(px366);
					px366++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getajsl(map).size());
					int s= cs.getajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num366 = -1;
				for(Mainpolice i:ce366) {
					num366++;
					Number s2 = new Number(0,4+num366,i.getPx(),titleFormate3);
					sheet366.addCell(s2);
					Label s3 = new Label(1,4+num366,i.getLogin_name(),titleFormate3);
					sheet366.addCell(s3);
					Label s4 = new Label(2,4+num366,i.getMjxm(),titleFormate3);
					sheet366.addCell(s4);
					Label s5 = new Label(3,4+num366,i.getMjdw(),titleFormate3);
					sheet366.addCell(s5);
					Number s6 = new Number(4,4+num366,i.getAjsl(),titleFormate3);
					sheet366.addCell(s6);
					Number s7 = new Number(5,4+num366,i.getKpsl(),titleFormate3);
					sheet366.addCell(s7);
					Number s8 = new Number(6,4+num366,i.getKfsum(),titleFormate3);
					sheet366.addCell(s8);
					Number s82 = new Number(7,4+num366,i.getAvg(),titleFormate3);
					sheet366.addCell(s82);
				}


				map.put("casetype", "xzxs");
				map.put("pageEnd", cs.getEvaluationListCount(map).size());
				WritableSheet sheet4 = book.createSheet("行政+刑事", 3);

				sheet4.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqwe = new Label(0, 0, excelname,titleFormate);
				sheet4.addCell(labelqwe);
				sheet4.setRowView(0, 700);//设置第一行的高度
				sheet4.setColumnView(3, 60);

				sheet4.mergeCells(0,1, 7, 1);
				Label label2qwe = new Label(0, 1, exceltime,titleFormate2);
				sheet4.addCell(label2qwe);

				sheet4.mergeCells(0,2, 7, 2);//合并单元格
				Label b11q = new Label(0,2,"主办侦查员排行(行政+刑事)",titleFormate2);
				sheet4.addCell(b11q);
				Label b21q = new Label(0,3,"排名",titleFormate2);
				sheet4.addCell(b21q);
				Label b31q = new Label(1,3,"民警警号",titleFormate2);
				sheet4.addCell(b31q);
				Label b41q = new Label(2,3,"民警姓名",titleFormate2);
				sheet4.addCell(b41q);
				Label b51q = new Label(3,3,"民警单位",titleFormate2);
				sheet4.addCell(b51q);
				Label b61q = new Label(4,3,"案件数量",titleFormate2);
				sheet4.addCell(b61q);
				Label b71q = new Label(5,3,"考评数量",titleFormate2);
				sheet4.addCell(b71q);
				Label b812q = new Label(6,3,"总扣分",titleFormate2);
				sheet4.addCell(b812q);
				Label b813q = new Label(7,3,"个案平均分",titleFormate2);
				sheet4.addCell(b813q);

				List<Mainpolice> ce4 =  cs.getEvaluationList(map);
				int px4 = 1;
				for(Mainpolice i:ce4) {
					i.setPx(px4);
					px4++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getajsl(map).size());
					int s= cs.getajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num4 = -1;
				for(Mainpolice i:ce4) {
					num4++;
					Number s2 = new Number(0,4+num4,i.getPx(),titleFormate3);
					sheet4.addCell(s2);
					Label s3 = new Label(1,4+num4,i.getLogin_name(),titleFormate3);
					sheet4.addCell(s3);
					Label s4 = new Label(2,4+num4,i.getMjxm(),titleFormate3);
					sheet4.addCell(s4);
					Label s5 = new Label(3,4+num4,i.getMjdw(),titleFormate3);
					sheet4.addCell(s5);
					Number s6 = new Number(4,4+num4,i.getAjsl(),titleFormate3);
					sheet4.addCell(s6);
					Number s7 = new Number(5,4+num4,i.getKpsl(),titleFormate3);
					sheet4.addCell(s7);
					Number s8 = new Number(6,4+num4,i.getKfsum(),titleFormate3);
					sheet4.addCell(s8);
					Number s82 = new Number(7,4+num4,i.getAvg(),titleFormate3);
					sheet4.addCell(s82);
				}

				map.put("casetype", "all");
				map.put("pageEnd", cs.getEvaluationListCount(map).size());
				WritableSheet sheet5 = book.createSheet("三合一", 4);

				sheet5.mergeCells(0,0, 7, 0);//合并单元格
				Label labelqwer = new Label(0, 0, excelname,titleFormate);
				sheet5.addCell(labelqwer);
				sheet5.setRowView(0, 700);//设置第一行的高度
				sheet5.setColumnView(3, 60);

				sheet5.mergeCells(0,1, 7, 1);
				Label label2qwer = new Label(0, 1, exceltime,titleFormate2);
				sheet5.addCell(label2qwer);

				sheet5.mergeCells(0,2, 7, 2);//合并单元格
				Label b11qw = new Label(0,2,"主办侦查员排行(三合一)",titleFormate2);
				sheet5.addCell(b11qw);
				Label b21qw = new Label(0,3,"排名",titleFormate2);
				sheet5.addCell(b21qw);
				Label b31qw = new Label(1,3,"民警警号",titleFormate2);
				sheet5.addCell(b31qw);
				Label b41qw = new Label(2,3,"民警姓名",titleFormate2);
				sheet5.addCell(b41qw);
				Label b51qw = new Label(3,3,"民警单位",titleFormate2);
				sheet5.addCell(b51qw);
				Label b61qw = new Label(4,3,"案件数量",titleFormate2);
				sheet5.addCell(b61qw);
				Label b71qw = new Label(5,3,"考评数量",titleFormate2);
				sheet5.addCell(b71qw);
				Label b812qw = new Label(6,3,"总扣分",titleFormate2);
				sheet5.addCell(b812qw);
				Label b813qw = new Label(7,3,"个案平均分",titleFormate2);
				sheet5.addCell(b813qw);

				List<Mainpolice> ce5 =  cs.getEvaluationList(map);
				int px5 = 1;
				for(Mainpolice i:ce5) {
					i.setPx(px5);
					px5++;
					map.put("mjid", i.getMjid());
					i.setAjsl(cs.getajsl(map).size());
					int s= cs.getajsl(map).size();
					float avg =  ((100*s)+i.getKfsum())/s;
			/*float avg = i.getAvg();*/
					BigDecimal b = new BigDecimal(avg);
					double newavg = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					i.setAvg(Float.parseFloat(newavg+""));
				}
				int num5 = -1;
				for(Mainpolice i:ce5) {
					num5++;
					Number s2 = new Number(0,4+num5,i.getPx(),titleFormate3);
					sheet5.addCell(s2);
					Label s3 = new Label(1,4+num5,i.getLogin_name(),titleFormate3);
					sheet5.addCell(s3);
					Label s4 = new Label(2,4+num5,i.getMjxm(),titleFormate3);
					sheet5.addCell(s4);
					Label s5 = new Label(3,4+num5,i.getMjdw(),titleFormate3);
					sheet5.addCell(s5);
					Number s6 = new Number(4,4+num5,i.getAjsl(),titleFormate3);
					sheet5.addCell(s6);
					Number s7 = new Number(5,4+num5,i.getKpsl(),titleFormate3);
					sheet5.addCell(s7);
					Number s8 = new Number(6,4+num5,i.getKfsum(),titleFormate3);
					sheet5.addCell(s8);
					Number s82 = new Number(7,4+num5,i.getAvg(),titleFormate3);
					sheet5.addCell(s82);
				}
				book.write();
				book.close();


			}else if(type.equals("coinvestigator")) {
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


				Map<String, Object> map = new HashMap<String, Object>();
				if(mjbm!="") {
					map.put("mjbm", mjbm);
				}else {
					map.put("mjbm", null);
				}
				if(kssj!="") {
					map.put("kssj", kssj);
				}else {
					map.put("kssj", null);
				}
				if(jssj!="") {
					map.put("jssj", jssj);
				}else {
					map.put("jssj", null);
				}

				map.put("pageStart", 0);
				map.put("casetype", "jq");
				SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
				map.put("dataAuth", " ( o.p_id like " + sessionInfo.getSuperAndSubOrgPid() + ")" );
				map.put("pageEnd", cs.getCopoliceListCount(map).size());
				WritableSheet sheet1 = book.createSheet("警情", 0);

				sheet1.mergeCells(0,0, 5, 0);//合并单元格
				Label label = new Label(0, 0, excelname,titleFormate);
				sheet1.addCell(label);
				sheet1.setRowView(0, 700);//设置第一行的高度
				sheet1.setColumnView(3, 60);

				sheet1.mergeCells(0,1, 5, 1);
				Label label2 = new Label(0, 1, exceltime,titleFormate2);
				sheet1.addCell(label2);

				sheet1.mergeCells(0,2, 5, 2);//合并单元格
				Label a1 = new Label(0,2,"协办侦查员排行(警情)",titleFormate2);
				sheet1.addCell(a1);
				Label a2 = new Label(0,3,"排名",titleFormate2);
				sheet1.addCell(a2);
				Label a3 = new Label(1,3,"民警警号",titleFormate2);
				sheet1.addCell(a3);
				Label a4 = new Label(2,3,"民警姓名",titleFormate2);
				sheet1.addCell(a4);
				Label a5 = new Label(3,3,"民警单位",titleFormate2);
				sheet1.addCell(a5);
				Label a6 = new Label(4,3,"警情数量",titleFormate2);
				sheet1.addCell(a6);
				Label a7 = new Label(5,3,"协办数量",titleFormate2);
				sheet1.addCell(a7);
				List<CopoliceEntity> ce =  cs.getCopoliceList(map);
				int px = 1;
				for(CopoliceEntity i:ce) {
					i.setPx(px);
					px++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getxbajsl(map).size());

				}
				int num1=-1;
				for(CopoliceEntity i:ce) {
					num1++;
					Number z1 = new Number(0,4+num1,i.getPx(),titleFormate3);
					sheet1.addCell(z1);
					Label z2 = new Label(1,4+num1,i.getLogin_name(),titleFormate3);
					sheet1.addCell(z2);
					Label z3 = new Label(2,4+num1,i.getMjxm(),titleFormate3);
					sheet1.addCell(z3);
					Label z4 = new Label(3,4+num1,i.getMjdw(),titleFormate3);
					sheet1.addCell(z4);
					Number z5 = new Number(4,4+num1,i.getAjsl(),titleFormate3);
					sheet1.addCell(z5);
					Number z6 = new Number(5,4+num1,i.getXbsl(),titleFormate3);
					sheet1.addCell(z6);
				}

				map.put("casetype", "xz");
				map.put("pageEnd", cs.getCopoliceListCount(map).size());
				WritableSheet sheet2 = book.createSheet("行政", 1);

				sheet2.mergeCells(0,0, 5, 0);//合并单元格
				Label labelq = new Label(0, 0, excelname,titleFormate);
				sheet2.addCell(labelq);
				sheet2.setRowView(0, 700);//设置第一行的高度
				sheet2.setColumnView(3, 60);

				sheet2.mergeCells(0,1, 5, 1);
				Label label2q = new Label(0, 1, exceltime,titleFormate2);
				sheet2.addCell(label2q);

				sheet2.mergeCells(0,2, 5, 2);//合并单元格
				Label b1 = new Label(0,2,"协办侦查员排行(行政)",titleFormate2);
				sheet2.addCell(b1);
				Label b2 = new Label(0,3,"排名",titleFormate2);
				sheet2.addCell(b2);
				Label b3 = new Label(1,3,"民警警号",titleFormate2);
				sheet2.addCell(b3);
				Label b4 = new Label(2,3,"民警姓名",titleFormate2);
				sheet2.addCell(b4);
				Label b5 = new Label(3,3,"民警单位",titleFormate2);
				sheet2.addCell(b5);
				Label b6 = new Label(4,3,"案件数量",titleFormate2);
				sheet2.addCell(b6);
				Label b7 = new Label(5,3,"协办数量",titleFormate2);
				sheet2.addCell(b7);
				List<CopoliceEntity> ce2 =  cs.getCopoliceList(map);
				int px2 = 1;
				for(CopoliceEntity i:ce2) {
					i.setPx(px2);
					px2++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getxbajsl(map).size());

				}
				int num2=-1;
				for(CopoliceEntity i:ce2) {
					num2++;
					Number z1 = new Number(0,4+num2,i.getPx(),titleFormate3);
					sheet2.addCell(z1);
					Label z2 = new Label(1,4+num2,i.getLogin_name(),titleFormate3);
					sheet2.addCell(z2);
					Label z3 = new Label(2,4+num2,i.getMjxm(),titleFormate3);
					sheet2.addCell(z3);
					Label z4 = new Label(3,4+num2,i.getMjdw(),titleFormate3);
					sheet2.addCell(z4);
					Number z5 = new Number(4,4+num2,i.getAjsl(),titleFormate3);
					sheet2.addCell(z5);
					Number z6 = new Number(5,4+num2,i.getXbsl(),titleFormate3);
					sheet2.addCell(z6);
				}

				map.put("casetype", "xs");
				map.put("pageEnd", cs.getCopoliceListCount(map).size());
				WritableSheet sheet3 = book.createSheet("刑事", 2);

				sheet3.mergeCells(0,0, 5, 0);//合并单元格
				Label labelqw = new Label(0, 0, excelname,titleFormate);
				sheet3.addCell(labelqw);
				sheet3.setRowView(0, 700);//设置第一行的高度
				sheet3.setColumnView(3, 60);

				sheet3.mergeCells(0,1, 5, 1);
				Label label2qw = new Label(0, 1, exceltime,titleFormate2);
				sheet3.addCell(label2qw);

				sheet3.mergeCells(0,2, 5, 2);//合并单元格
				Label b11 = new Label(0,2,"协办侦查员排行(刑事)",titleFormate2);
				sheet3.addCell(b11);
				Label b21 = new Label(0,3,"排名",titleFormate2);
				sheet3.addCell(b21);
				Label b31 = new Label(1,3,"民警警号",titleFormate2);
				sheet3.addCell(b31);
				Label b41 = new Label(2,3,"民警姓名",titleFormate2);
				sheet3.addCell(b41);
				Label b51 = new Label(3,3,"民警单位",titleFormate2);
				sheet3.addCell(b51);
				Label b61 = new Label(4,3,"案件数量",titleFormate2);
				sheet3.addCell(b61);
				Label b71 = new Label(5,3,"协办数量",titleFormate2);
				sheet3.addCell(b71);
				List<CopoliceEntity> ce3 =  cs.getCopoliceList(map);
				int px3 = 1;
				for(CopoliceEntity i:ce3) {
					i.setPx(px3);
					px3++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getxbajsl(map).size());

				}
				int num3=-1;
				for(CopoliceEntity i:ce3) {
					num3++;
					Number z1 = new Number(0,4+num3,i.getPx(),titleFormate3);
					sheet3.addCell(z1);
					Label z2 = new Label(1,4+num3,i.getLogin_name(),titleFormate3);
					sheet3.addCell(z2);
					Label z3 = new Label(2,4+num3,i.getMjxm(),titleFormate3);
					sheet3.addCell(z3);
					Label z4 = new Label(3,4+num3,i.getMjdw(),titleFormate3);
					sheet3.addCell(z4);
					Number z5 = new Number(4,4+num3,i.getAjsl(),titleFormate3);
					sheet3.addCell(z5);
					Number z6 = new Number(5,4+num3,i.getXbsl(),titleFormate3);
					sheet3.addCell(z6);
				}

				map.put("casetype", "zfjd");
				map.put("pageEnd", cs.getCopoliceListCount(map).size());
				WritableSheet sheet366 = book.createSheet("执法监督", 5);

				sheet366.mergeCells(0,0, 5, 0);//合并单元格
				Label labelqw66 = new Label(0, 0, excelname,titleFormate);
				sheet366.addCell(labelqw66);
				sheet366.setRowView(0, 700);//设置第一行的高度
				sheet366.setColumnView(3, 60);

				sheet366.mergeCells(0,1, 5, 1);
				Label label2qw66 = new Label(0, 1, exceltime,titleFormate2);
				sheet366.addCell(label2qw66);

				sheet366.mergeCells(0,2, 5, 2);//合并单元格
				Label b1166 = new Label(0,2,"协办侦查员排行(刑事)",titleFormate2);
				sheet366.addCell(b1166);
				Label b2166 = new Label(0,3,"排名",titleFormate2);
				sheet366.addCell(b2166);
				Label b3166 = new Label(1,3,"民警警号",titleFormate2);
				sheet366.addCell(b3166);
				Label b4166 = new Label(2,3,"民警姓名",titleFormate2);
				sheet366.addCell(b4166);
				Label b5166 = new Label(3,3,"民警单位",titleFormate2);
				sheet366.addCell(b5166);
				Label b6166 = new Label(4,3,"执法监督数量",titleFormate2);
				sheet366.addCell(b6166);
				Label b7166 = new Label(5,3,"协办数量",titleFormate2);
				sheet366.addCell(b7166);
				List<CopoliceEntity> ce366 =  cs.getCopoliceList(map);
				int px366 = 1;
				for(CopoliceEntity i:ce366) {
					i.setPx(px366);
					px366++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getxbajsl(map).size());

				}
				int num366=-1;
				for(CopoliceEntity i:ce366) {
					num366++;
					Number z1 = new Number(0,4+num366,i.getPx(),titleFormate3);
					sheet366.addCell(z1);
					Label z2 = new Label(1,4+num366,i.getLogin_name(),titleFormate3);
					sheet366.addCell(z2);
					Label z3 = new Label(2,4+num366,i.getMjxm(),titleFormate3);
					sheet366.addCell(z3);
					Label z4 = new Label(3,4+num366,i.getMjdw(),titleFormate3);
					sheet366.addCell(z4);
					Number z5 = new Number(4,4+num366,i.getAjsl(),titleFormate3);
					sheet366.addCell(z5);
					Number z6 = new Number(5,4+num366,i.getXbsl(),titleFormate3);
					sheet366.addCell(z6);
				}

				map.put("casetype", "xzxs");
				map.put("pageEnd", cs.getCopoliceListCount(map).size());
				WritableSheet sheet4 = book.createSheet("行政+刑事", 3);

				sheet4.mergeCells(0,0, 5, 0);//合并单元格
				Label labelqwe = new Label(0, 0, excelname,titleFormate);
				sheet4.addCell(labelqwe);
				sheet4.setRowView(0, 700);//设置第一行的高度
				sheet4.setColumnView(3, 60);

				sheet4.mergeCells(0,1, 5, 1);
				Label label2qwe = new Label(0, 1, exceltime,titleFormate2);
				sheet4.addCell(label2qwe);

				sheet4.mergeCells(0,2, 5, 2);//合并单元格
				Label b11q = new Label(0,2,"协办侦查员排行(行政+刑事)",titleFormate2);
				sheet4.addCell(b11q);
				Label b21q = new Label(0,3,"排名",titleFormate2);
				sheet4.addCell(b21q);
				Label b31q = new Label(1,3,"民警警号",titleFormate2);
				sheet4.addCell(b31q);
				Label b41q = new Label(2,3,"民警姓名",titleFormate2);
				sheet4.addCell(b41q);
				Label b51q = new Label(3,3,"民警单位",titleFormate2);
				sheet4.addCell(b51q);
				Label b61q = new Label(4,3,"案件数量",titleFormate2);
				sheet4.addCell(b61q);
				Label b71q = new Label(5,3,"协办数量",titleFormate2);
				sheet4.addCell(b71q);
				List<CopoliceEntity> ce4 =  cs.getCopoliceList(map);
				int px4 = 1;
				for(CopoliceEntity i:ce4) {
					i.setPx(px4);
					px4++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getxbajsl(map).size());

				}
				int num4=-1;
				for(CopoliceEntity i:ce4) {
					num4++;
					Number z1 = new Number(0,4+num4,i.getPx(),titleFormate3);
					sheet4.addCell(z1);
					Label z2 = new Label(1,4+num4,i.getLogin_name(),titleFormate3);
					sheet4.addCell(z2);
					Label z3 = new Label(2,4+num4,i.getMjxm(),titleFormate3);
					sheet4.addCell(z3);
					Label z4 = new Label(3,4+num4,i.getMjdw(),titleFormate3);
					sheet4.addCell(z4);
					Number z5 = new Number(4,4+num4,i.getAjsl(),titleFormate3);
					sheet4.addCell(z5);
					Number z6 = new Number(5,4+num4,i.getXbsl(),titleFormate3);
					sheet4.addCell(z6);
				}
				map.put("casetype", "all");
				map.put("pageEnd", cs.getCopoliceListCount(map).size());
				WritableSheet sheet5 = book.createSheet("三合一", 4);

				sheet5.mergeCells(0,0, 5, 0);//合并单元格
				Label labelqwer = new Label(0, 0, excelname,titleFormate);
				sheet5.addCell(labelqwer);
				sheet5.setRowView(0, 700);//设置第一行的高度
				sheet5.setColumnView(3, 60);

				sheet5.mergeCells(0,1, 5, 1);
				Label label2qwer = new Label(0, 1, exceltime,titleFormate2);
				sheet5.addCell(label2qwer);

				sheet5.mergeCells(0,2, 5, 2);//合并单元格
				Label b11qw = new Label(0,2,"协办侦查员排行(三合一)",titleFormate2);
				sheet5.addCell(b11qw);
				Label b21qw = new Label(0,3,"排名",titleFormate2);
				sheet5.addCell(b21qw);
				Label b31qw = new Label(1,3,"民警警号",titleFormate2);
				sheet5.addCell(b31qw);
				Label b41qw = new Label(2,3,"民警姓名",titleFormate2);
				sheet5.addCell(b41qw);
				Label b51qw = new Label(3,3,"民警单位",titleFormate2);
				sheet5.addCell(b51qw);
				Label b61qw = new Label(4,3,"案件数量",titleFormate2);
				sheet5.addCell(b61qw);
				Label b71qw = new Label(5,3,"协办数量",titleFormate2);
				sheet5.addCell(b71qw);
				List<CopoliceEntity> ce5 =  cs.getCopoliceList(map);
				int px5 = 1;
				for(CopoliceEntity i:ce5) {
					i.setPx(px5);
					px4++;
					map.put("mjid", i.getMjid());

					i.setAjsl(cs.getxbajsl(map).size());

				}
				int num5=-1;
				for(CopoliceEntity i:ce5) {
					num5++;
					Number z1 = new Number(0,4+num5,i.getPx(),titleFormate3);
					sheet5.addCell(z1);
					Label z2 = new Label(1,4+num5,i.getLogin_name(),titleFormate3);
					sheet5.addCell(z2);
					Label z3 = new Label(2,4+num5,i.getMjxm(),titleFormate3);
					sheet5.addCell(z3);
					Label z4 = new Label(3,4+num5,i.getMjdw(),titleFormate3);
					sheet5.addCell(z4);
					Number z5 = new Number(4,4+num5,i.getAjsl(),titleFormate3);
					sheet5.addCell(z5);
					Number z6 = new Number(5,4+num5,i.getXbsl(),titleFormate3);
					sheet5.addCell(z6);
				}

				book.write();
				book.close();

			}else if(type.equals("dwzhibiao")) {

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

				WritableSheet sheet1 = book.createSheet("警情", 0);

				sheet1.mergeCells(0,0, 5, 0);//合并单元格
				Label label = new Label(0, 0, excelname,titleFormate);
				sheet1.addCell(label);
				sheet1.setRowView(0, 700);//设置第一行的高度
				sheet1.setColumnView(1, 60);

				sheet1.mergeCells(0,1, 5, 1);
				Label label2 = new Label(0, 1, exceltime,titleFormate2);
				sheet1.addCell(label2);

				sheet1.mergeCells(0,2, 5, 2);//合并单元格
				Label a = new Label(0, 2, "指标扣分排行(警情)",titleFormate2);
				sheet1.addCell(a);
				Label a1 = new Label(0, 3, "排名",titleFormate2);
				sheet1.addCell(a1);
				Label a3 = new Label(1, 3, "指标标准",titleFormate2);
				sheet1.addCell(a3);
				Label a4 = new Label(2, 3, "涉及警情数",titleFormate2);
				sheet1.addCell(a4);
				Label a5 = new Label(3, 3, "出现次数",titleFormate2);
				sheet1.addCell(a5);
				Label a61 = new Label(4, 3, "扣分总值",titleFormate2);
				sheet1.addCell(a61);
				Label a6 = new Label(5, 3, "扣分占比",titleFormate2);
				sheet1.addCell(a6);


				Map<String, Object> map = new HashMap<String, Object>();
				map.put("bmid", bmid);
				if(kssj!="") {
					map.put("kssj", kssj);
				}else {
					map.put("kssj", null);
				}
				if(jssj!="") {
					map.put("jssj", jssj);
				}else {
					map.put("jssj", null);
				}
				map.put("pageStart", 0);
				map.put("casetype", "jq");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());

				List<ZhibiaoEntity> list = cs.getzhibiao(map);
				int px = 1;
				for(ZhibiaoEntity i:list) {
					/*i.setKfzb(String.format("%.2f", ((double)i.getKfzz()/cs.getzhibiao_zkf(map)*100))+"%");*/
					i.setPx(px);
					px++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getOrgName());
				}
				int num1=-1;
				for(ZhibiaoEntity i:list) {
					num1++;
					Number b1 = new Number(0, 4+num1, i.getPx(),titleFormate3);
					sheet1.addCell(b1);
					Label b3 = new Label(1, 4+num1, i.getZbbz(),titleFormate3);
					sheet1.addCell(b3);
					Number b4 = new Number(2, 4+num1, i.getAjsl(),titleFormate3);
					sheet1.addCell(b4);
					Number b5 = new Number(3, 4+num1, i.getCxcs(),titleFormate3);
					sheet1.addCell(b5);
					Number b51 = new Number(4, 4+num1, i.getKfzz(),titleFormate3);
					sheet1.addCell(b51);
					Label b6 = new Label(5, 4+num1, i.getKfzb(),titleFormate3);
					sheet1.addCell(b6);
				}

				WritableSheet sheet2 = book.createSheet("行政", 1);

				sheet2.mergeCells(0,0, 5, 0);//合并单元格
				Label labely = new Label(0, 0, excelname,titleFormate);
				sheet2.addCell(labely);
				sheet2.setRowView(0, 700);//设置第一行的高度
				sheet2.setColumnView(1, 60);

				sheet2.mergeCells(0,1, 5, 1);
				Label label2y = new Label(0, 1, exceltime,titleFormate2);
				sheet2.addCell(label2y);

				sheet2.mergeCells(0,2, 5, 2);//合并单元格
				Label z = new Label(0, 2, "指标扣分排行(行政)",titleFormate2);
				sheet2.addCell(z);
				Label x = new Label(0, 3, "排名",titleFormate2);
				sheet2.addCell(x);
				Label v = new Label(1, 3, "指标标准",titleFormate2);
				sheet2.addCell(v);
				Label b = new Label(2, 3, "涉及案件数",titleFormate2);
				sheet2.addCell(b);
				Label n = new Label(3, 3, "出现次数",titleFormate2);
				sheet2.addCell(n);
				Label n1 = new Label(4, 3, "扣分总值",titleFormate2);
				sheet2.addCell(n1);
				Label m = new Label(5, 3, "扣分占比",titleFormate2);
				sheet2.addCell(m);

				map.put("casetype", "xz");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list2 = cs.getzhibiao(map);
				int px2 = 1;
				for(ZhibiaoEntity i:list2) {
//					i.setKfzb(String.format("%.2f", ((double)i.getKfzz()/cs.getzhibiao_zkf(map)*100))+"%");
					i.setPx(px2);
					px2++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getOrgName());
				}
				int num2=-1;
				for(ZhibiaoEntity i:list2) {
					num2++;
					Number b1 = new Number(0, 4+num2, i.getPx(),titleFormate3);
					sheet2.addCell(b1);
					Label b3 = new Label(1, 4+num2, i.getZbbz(),titleFormate3);
					sheet2.addCell(b3);
					Number b4 = new Number(2, 4+num2, i.getAjsl(),titleFormate3);
					sheet2.addCell(b4);
					Number b5 = new Number(3, 4+num2, i.getCxcs(),titleFormate3);
					sheet2.addCell(b5);
					Number b51 = new Number(4, 4+num2, i.getKfzz(),titleFormate3);
					sheet2.addCell(b51);
					Label b6 = new Label(5, 4+num2, i.getKfzb(),titleFormate3);
					sheet2.addCell(b6);
				}

				WritableSheet sheet3 = book.createSheet("刑事", 2);

				sheet3.mergeCells(0,0, 5, 0);//合并单元格
				Label labelu = new Label(0, 0, excelname,titleFormate);
				sheet3.addCell(labelu);
				sheet3.setRowView(0, 700);//设置第一行的高度
				sheet3.setColumnView(1, 60);

				sheet3.mergeCells(0,1, 5, 1);
				Label label2u = new Label(0, 1, exceltime,titleFormate2);
				sheet3.addCell(label2u);

				sheet3.mergeCells(0,2, 5, 2);//合并单元格
				Label z1 = new Label(0, 2, "指标扣分排行(刑事)",titleFormate2);
				sheet3.addCell(z1);
				Label x1 = new Label(0, 3, "排名",titleFormate2);
				sheet3.addCell(x1);

				Label v1 = new Label(1, 3, "指标标准",titleFormate2);
				sheet3.addCell(v1);
				Label b12 = new Label(2, 3, "涉及案件数",titleFormate2);
				sheet3.addCell(b12);
				Label n11 = new Label(3, 3, "出现次数",titleFormate2);
				sheet3.addCell(n11);
				Label n111 = new Label(4, 3, "扣分总值",titleFormate2);
				sheet3.addCell(n111);
				Label m1 = new Label(5, 3, "扣分占比",titleFormate2);
				sheet3.addCell(m1);

				map.put("casetype", "xs");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list3 = cs.getzhibiao(map);
				int px3 = 1;
				for(ZhibiaoEntity i:list3) {
					/*i.setKfzb(String.format("%.2f", ((double)i.getKfzz()/cs.getzhibiao_zkf(map)*100))+"%");*/
					i.setPx(px3);
					px3++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getOrgName());
				}
				int num3=-1;
				for(ZhibiaoEntity i:list3) {
					num3++;
					Number b1 = new Number(0, 4+num3, i.getPx(),titleFormate3);
					sheet3.addCell(b1);
					Label b3 = new Label(1, 4+num3, i.getZbbz(),titleFormate3);
					sheet3.addCell(b3);
					Number b4 = new Number(2, 4+num3, i.getAjsl(),titleFormate3);
					sheet3.addCell(b4);
					Number b5 = new Number(3, 4+num3, i.getCxcs(),titleFormate3);
					sheet3.addCell(b5);
					Number b51 = new Number(4, 4+num3, i.getKfzz(),titleFormate3);
					sheet3.addCell(b51);
					Label b6 = new Label(5, 4+num3, i.getKfzb(),titleFormate3);
					sheet3.addCell(b6);
				}



				WritableSheet sheet366 = book.createSheet("执法监督", 5);

				sheet366.mergeCells(0,0, 5, 0);//合并单元格
				Label labelu66 = new Label(0, 0, excelname,titleFormate);
				sheet366.addCell(labelu66);
				sheet366.setRowView(0, 700);//设置第一行的高度
				sheet366.setColumnView(1, 60);

				sheet366.mergeCells(0,1, 5, 1);
				Label label2u66 = new Label(0, 1, exceltime,titleFormate2);
				sheet366.addCell(label2u66);

				sheet366.mergeCells(0,2, 5, 2);//合并单元格
				Label z166 = new Label(0, 2, "指标扣分排行(刑事)",titleFormate2);
				sheet366.addCell(z166);
				Label x166 = new Label(0, 3, "排名",titleFormate2);
				sheet366.addCell(x166);

				Label v166 = new Label(1, 3, "指标标准",titleFormate2);
				sheet366.addCell(v166);
				Label b1266 = new Label(2, 3, "涉及执法监督数",titleFormate2);
				sheet366.addCell(b1266);
				Label n1166 = new Label(3, 3, "出现次数",titleFormate2);
				sheet366.addCell(n1166);
				Label n11166 = new Label(4, 3, "扣分总值",titleFormate2);
				sheet366.addCell(n11166);
				Label m166 = new Label(5, 3, "扣分占比",titleFormate2);
				sheet366.addCell(m166);

				map.put("casetype", "zfjd");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list366 = cs.getzhibiao(map);
				int px366 = 1;
				for(ZhibiaoEntity i:list366) {
					/*i.setKfzb(String.format("%.2f", ((double)i.getKfzz()/cs.getzhibiao_zkf(map)*100))+"%");*/
					i.setPx(px366);
					px366++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getOrgName());
				}
				int num366=-1;
				for(ZhibiaoEntity i:list3) {
					num3++;
					Number b1 = new Number(0, 4+num366, i.getPx(),titleFormate3);
					sheet366.addCell(b1);
					Label b3 = new Label(1, 4+num366, i.getZbbz(),titleFormate3);
					sheet366.addCell(b3);
					Number b4 = new Number(2, 4+num366, i.getAjsl(),titleFormate3);
					sheet366.addCell(b4);
					Number b5 = new Number(3, 4+num366, i.getCxcs(),titleFormate3);
					sheet366.addCell(b5);
					Number b51 = new Number(4, 4+num366, i.getKfzz(),titleFormate3);
					sheet366.addCell(b51);
					Label b6 = new Label(5, 4+num366, i.getKfzb(),titleFormate3);
					sheet366.addCell(b6);
				}


				WritableSheet sheet4 = book.createSheet("行政+刑事", 3);

				sheet4.mergeCells(0,0, 5, 0);//合并单元格
				Label labeli = new Label(0, 0, excelname,titleFormate);
				sheet4.addCell(labeli);
				sheet4.setRowView(0, 700);//设置第一行的高度
				sheet4.setColumnView(1, 60);

				sheet4.mergeCells(0,1, 5, 1);
				Label label2i = new Label(0, 1, exceltime,titleFormate2);
				sheet4.addCell(label2i);

				sheet4.mergeCells(0,2, 5, 2);//合并单元格
				Label z1q = new Label(0, 2, "指标扣分排行(行政+刑事)",titleFormate2);
				sheet4.addCell(z1q);
				Label x1q = new Label(0, 3, "排名",titleFormate2);
				sheet4.addCell(x1q);

				Label v1q = new Label(1, 3, "指标标准",titleFormate2);
				sheet4.addCell(v1q);
				Label b12q = new Label(2, 3, "涉及案件数",titleFormate2);
				sheet4.addCell(b12q);
				Label n11q = new Label(3, 3, "出现次数",titleFormate2);
				sheet4.addCell(n11q);
				Label n111q = new Label(4, 3, "扣分总值",titleFormate2);
				sheet4.addCell(n111q);
				Label m1q = new Label(5, 3, "扣分占比",titleFormate2);
				sheet4.addCell(m1q);

				map.put("casetype", "xzxs");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list4 = cs.getzhibiao(map);
				int px4 = 1;
				for(ZhibiaoEntity i:list4) {
//					i.setKfzb(String.format("%.2f", ((double)i.getKfzz()/cs.getzhibiao_zkf(map)*100))+"%");
					i.setPx(px4);
					px4++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getOrgName());
				}
				int num4=-1;
				for(ZhibiaoEntity i:list4) {
					num4++;
					Number b1 = new Number(0, 4+num4, i.getPx(),titleFormate3);
					sheet4.addCell(b1);
					Label b3 = new Label(1, 4+num4, i.getZbbz(),titleFormate3);
					sheet4.addCell(b3);
					Number b4 = new Number(2, 4+num4, i.getAjsl(),titleFormate3);
					sheet4.addCell(b4);
					Number b5 = new Number(3, 4+num4, i.getCxcs(),titleFormate3);
					sheet4.addCell(b5);
					Number b51 = new Number(4, 4+num4, i.getKfzz(),titleFormate3);
					sheet4.addCell(b51);
					Label b6 = new Label(5, 4+num4, i.getKfzb(),titleFormate3);
					sheet4.addCell(b6);
				}

				WritableSheet sheet5 = book.createSheet("三合一", 4);

				sheet5.mergeCells(0,0, 5, 0);//合并单元格
				Label labeliq = new Label(0, 0, excelname,titleFormate);
				sheet5.addCell(labeliq);
				sheet5.setRowView(0, 700);//设置第一行的高度
				sheet5.setColumnView(1, 60);

				sheet5.mergeCells(0,1, 5, 1);
				Label label2iq = new Label(0, 1, exceltime,titleFormate2);
				sheet5.addCell(label2iq);

				sheet5.mergeCells(0,2, 5, 2);//合并单元格
				Label z1qw = new Label(0, 2, "指标扣分排行(三合一)",titleFormate2);
				sheet5.addCell(z1qw);
				Label x1qw = new Label(0, 3, "排名",titleFormate2);
				sheet5.addCell(x1qw);

				Label v1qw = new Label(1, 3, "指标标准",titleFormate2);
				sheet5.addCell(v1qw);
				Label b12qw = new Label(2, 3, "涉及案件数",titleFormate2);
				sheet5.addCell(b12qw);
				Label n11qw = new Label(3, 3, "出现次数",titleFormate2);
				sheet5.addCell(n11qw);
				Label n111qw = new Label(4, 3, "扣分总值",titleFormate2);
				sheet5.addCell(n111qw);
				Label m1qw = new Label(5, 3, "扣分占比",titleFormate2);
				sheet5.addCell(m1qw);

				map.put("casetype", "all");
				map.put("pageEnd", cs.getzhibiaoCount(map).size());
				List<ZhibiaoEntity> list4w = cs.getzhibiao(map);
				int px4w = 1;
				for(ZhibiaoEntity i:list4w) {
					/*i.setKfzb(String.format("%.2f", ((double)i.getKfzz()/cs.getzhibiao_zkf(map)*100))+"%");*/
					i.setPx(px4w);
					px4w++;
					map.put("id", i.getId());
					i.setAjsl(cs.getzhibiao_ajsl(map).size());
					i.setKfzddw(cs.getzhibiao_kfzd(map).getOrgName());
				}
				int num4w=-1;
				for(ZhibiaoEntity i:list4w) {
					num4w++;
					Number b1 = new Number(0, 4+num4w, i.getPx(),titleFormate3);
					sheet5.addCell(b1);
					Label b3 = new Label(1, 4+num4w, i.getZbbz(),titleFormate3);
					sheet5.addCell(b3);
					Number b4 = new Number(2, 4+num4w, i.getAjsl(),titleFormate3);
					sheet5.addCell(b4);
					Number b5 = new Number(3, 4+num4w, i.getCxcs(),titleFormate3);
					sheet5.addCell(b5);
					Number b51 = new Number(4, 4+num4w, i.getKfzz(),titleFormate3);
					sheet5.addCell(b51);
					Label b6 = new Label(5, 4+num4w, i.getKfzb(),titleFormate3);
					sheet5.addCell(b6);
				}

				book.write();
				book.close();


			}
		} catch (Exception e) {
//				// TODO: handle exception
			e.getMessage();
			e.printStackTrace();
			return "0";
		}
		if(fb) {
			session.setAttribute("filepath", creaturl+".xls");
			session.setAttribute("filename", excelname);
			return "1";
		}else {
			return "0";
		}
	}

	@RequestMapping("/downLoadExcel")
	public void downLoadExcel( HttpServletResponse res,HttpSession session){
		String path = (String)session.getAttribute("filepath");
		String filename = (String)session.getAttribute("filename");
		File file = new File(path);
		try {
			res.setContentType("application/vnd.ms-excel;charset=utf-8");
			res.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(filename+".xls", "UTF-8"));
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
	}

	@RequestMapping(value = "/queryquota")
	@ResponseBody
	public Map<String,Object> queryquota(String scoring_stand,Integer pageSize, Integer pageNumber) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("st", scoring_stand);
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		List<QuotaEntity_tqy> list = cs.queryquota(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.maxpage(map));
		result.put("rows", list);
		return result;
	}


	@RequestMapping(value = "/getmaxid")
	@ResponseBody
	public int getmaxid() {
		return cs.getmaxid();
	}


	@RequestMapping(value = "/addquota")
	@ResponseBody
	public String addquota(int id,String item,String scoring_stand,int pointsvalue,int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("item", item);
		map.put("ss", scoring_stand);
		map.put("pv", pointsvalue);
		map.put("type", type);
		try {
			cs.addquota(map);
		} catch (Exception e) {
			// TODO: handle exception
			return "0";
		}
		return "1";
	}
	@RequestMapping(value = "/updatequota")
	@ResponseBody
	public String updatequota(int id,String item,String scoring_stand,int pointsvalue,int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("item", item);
		map.put("ss", scoring_stand);
		map.put("pv", pointsvalue);
		map.put("type", type);
		try {
			cs.updatequota(map);
		} catch (Exception e) {
			// TODO: handle exception
			return "0";
		}
		return "1";
	}
	@RequestMapping(value = "/delequota")
	@ResponseBody
	public String delequota(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		try {
			cs.delequota(map);
		} catch (Exception e) {
			// TODO: handle exception
			return "0";
		}
		return "1";
	}
	@RequestMapping(value = "/update_getquota")
	@ResponseBody
	public QuotaEntity_tqy update_getquota(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return cs.update_getquota(map);
	}



	@RequestMapping(value = "/selectCaseNo")
	@ResponseBody
	public EvaluationEntity_tqy selectCaseNo(HttpServletResponse response,String caseNo,String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseNo", caseNo);
		map.put("caseId", id);
		EvaluationEntity_tqy e = cs.selectCaseNo(map);
		map.put("id", e.getId());
		e.setZb(cs.getzhubanbyid(map));
		e.setLd(cs.getlingdaobyid(map));
		e.setXb(cs.getxiebanbyid(map));
		return e;
	}


	@RequestMapping(value = "/setsession")
	@ResponseBody
	public void setsession(HttpSession session,String name,String val) {
		session.setAttribute(name, val);
	}

	@RequestMapping(value = "/getsession")
	@ResponseBody
	public Map getsession(HttpSession session,String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name, (String)session.getAttribute(name));
		return map;
	}

	@RequestMapping(value = "/addevaluationCaseNo")
	@ResponseBody
	public Map<String,Object> addevaluationCaseNo(HttpServletResponse response,String caseno,String casename, Integer pageSize, Integer pageNumber){
		Integer pageStart = (pageNumber-1)*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("caseno", caseno);
		map.put("casename", casename);
		List<EvaluationEntity_tqy> list = cs.addevaluationCaseNo(map);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.count_addevaluationCaseNo(result));

		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/selectAllItem")
	@ResponseBody
	public List<ItemEntity> selectAllItem(){
		return cs.selectAllItem();
	}

	@RequestMapping(value = "/selectorganization")
	@ResponseBody
	public Map<String,Object> selectorganization(Integer pageSize, Integer pageNumber,String organization){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer pageStart = (pageNumber-1)*pageSize;
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageSize);
		map.put("organization", organization);
		List<OrgIdNameEntity> list = cs.selectorganization(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", cs.selectorganization_count(map));
		result.put("rows", list);
		return result;
	}

	@RequestMapping(value = "/clearsession")
	@ResponseBody
	public void clearsession(HttpSession session,HttpServletRequest request){
		session.removeAttribute(ControllerTool.getUserID(request).toString());
		session.removeAttribute("dwid");
	}
}

