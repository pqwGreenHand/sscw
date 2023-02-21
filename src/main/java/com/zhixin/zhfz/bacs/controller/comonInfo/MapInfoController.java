package com.zhixin.zhfz.bacs.controller.comonInfo;


import com.zhixin.zhfz.bacs.entity.GeoPersonInfoCountEntity;
import com.zhixin.zhfz.bacs.entity.JingwuInfoEntity;
import com.zhixin.zhfz.bacs.entity.JwTodayPrisonerEntity;
import com.zhixin.zhfz.bacs.entity.JwWeekCaseEntity;
import com.zhixin.zhfz.bacs.services.jingwu.IGeoPersonInfoCountService;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/commonInfo/")
public class MapInfoController {

	private static final Logger logger = LoggerFactory.getLogger(MapInfoController.class);

	/*@Autowired
	private IRegionService regionService;*/

	@Autowired
	private IOrganizationService organizationService;

	@Autowired
	private IGeoPersonInfoCountService geoPersonInfoCountService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private long dayLong=24 * 3600 * 1000L;

	/**
	 * jingwuInfo.jsp 获取选择的区域(包含全部派出所)或派出所内信息
	 * 
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listJingwuInfo")
	@ResponseBody
	public List<JingwuInfoEntity> listJingwuInfo(@RequestBody Map<String, Object> map, HttpServletRequest request,
												 HttpServletResponse response) throws Exception {
		List<JingwuInfoEntity> retList = new ArrayList<JingwuInfoEntity>();
		List<OrganizationEntity> orgList = new ArrayList<OrganizationEntity>();
		// 获取选择的派出所
		Object oid = map.get("oid");
		if (oid != null) {
			if (oid.toString().startsWith("o_")) {
				String id = oid.toString().replace("o_", "");
				OrganizationEntity org = organizationService.getOrganizationById(Integer.valueOf(id));
				orgList.add(org);
			} else {
				List<OrganizationEntity> rList = organizationService.listOrgByRegionCode(Long.valueOf(oid.toString()));
				if (rList != null) {
					orgList.addAll(rList);
				}

			}
		}
		for (OrganizationEntity org : orgList) {
			JingwuInfoEntity info = new JingwuInfoEntity(org);
			// TODO 临时生成的测试数据
			Random r = new Random();
			

			String[] ss = { "未成年人", "老年人", "特殊疾病", "孕妇", "残疾人", "成年人" };
			Map<String, Integer> sexMap = geoPersonInfoCountToMap(
					geoPersonInfoCountService.listPersonInfoCountSexByOrg(org.getId().longValue()));
			Map<String, Integer> typeMap = geoPersonInfoCountToMap(
					geoPersonInfoCountService.listPersonInfoCountTypeByOrg(org.getId().longValue()));
			Map<String, Integer> sevenDayMap = geoPersonInfoCountToMap(
					geoPersonInfoCountService.listPersonInfoCount7DayByOrg(org.getId().longValue()));
			if (sexMap.containsKey("1")) {
				JwTodayPrisonerEntity entity = new JwTodayPrisonerEntity("男", sexMap.get("1"));
				info.addTodayPrisoner(entity);
			} else {
				JwTodayPrisonerEntity entity = new JwTodayPrisonerEntity("男", 0);
				info.addTodayPrisoner(entity);
			}
			if (sexMap.containsKey("2")) {
				JwTodayPrisonerEntity entity = new JwTodayPrisonerEntity("女", sexMap.get("2"));
				info.addTodayPrisoner(entity);
			} else {
				JwTodayPrisonerEntity entity = new JwTodayPrisonerEntity("女", 0);
				info.addTodayPrisoner(entity);
			}

			for (String s : ss) {
				if (typeMap.containsKey(s)) {
					JwTodayPrisonerEntity entity = new JwTodayPrisonerEntity(s, typeMap.get(s));
					info.addTodayPrisoner(entity);
				} else {
					JwTodayPrisonerEntity entity = new JwTodayPrisonerEntity(s, 0);
					info.addTodayPrisoner(entity);
				}
			}

			// 7day
			// int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

			// info.addWeekCase(entity);
			for (int i = 7; i > 0; i--) {
				String d=getWeeDate(i);
				JwWeekCaseEntity weekCase=null;
				if(sevenDayMap.containsKey(d)){
					weekCase= new JwWeekCaseEntity("",sevenDayMap.get(d), d);
				}else{
					weekCase= new JwWeekCaseEntity("",0, d);
				}
				
				info.addWeekCase(weekCase);
			}
			retList.add(info);
		}

		return retList;
	}

	private Map<String, Integer> geoPersonInfoCountToMap(List<GeoPersonInfoCountEntity> list) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (list != null) {
			for (GeoPersonInfoCountEntity entity : list) {
				map.put(entity.getInfo(), entity.getNum());

			}
		}
		return map;
	}

	private int getWeeDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis() + 2 * dayLong));
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(w==0){
			w=7;
		}
		return w;
	}
	
	private String getWeeDate(int passDay) {
		
		return sdf.format(new Date(System.currentTimeMillis()-passDay*dayLong));
	}

	public static void main(String arg[]) {
//		MapInfoController m=new MapInfoController();
//		System.out.println(m.getWeeDate(1));
	}

}
