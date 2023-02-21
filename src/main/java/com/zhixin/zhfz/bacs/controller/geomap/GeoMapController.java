package com.zhixin.zhfz.bacs.controller.geomap;

import com.alibaba.fastjson.JSONObject;
import com.zhixin.zhfz.bacs.services.geo.IGeoMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/bacs/map")
public class GeoMapController {

	private static final Logger logger = LoggerFactory.getLogger(GeoMapController.class);

	@Autowired
	private IGeoMapService geoMapService;
	

	@RequestMapping(value = "/getRegionGeoMapData")
	@ResponseBody
	public JSONObject getRegionGeoMapData(@RequestParam Map<String, Object> params,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("++++++++getGeoMapData+++++++++++");
//		return this.geoMapService.getGeoJsonByProp();
		return this.geoMapService.getRegionGeoJsonByProp();
	}
	
	@RequestMapping(value = "/getOrgGeoMapData")
	@ResponseBody
	public JSONObject getOrgGeoMapData(@RequestParam Map<String, Object> params,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("++++++++getOrgGeoMapData+++++++++++");
		return this.geoMapService.getOrgPointGeoJson();
	}

}
