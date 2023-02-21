package com.zhixin.zhfz.bacs.services.geo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhixin.zhfz.bacs.entity.GeoDataEntity;
import com.zhixin.zhfz.common.common.ConfigJsonUtil;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.services.organiztion.IOrganizationService;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GeoMapServiceImpl implements IGeoMapService {
	private static final Logger logger = LoggerFactory.getLogger(GeoMapServiceImpl.class);
	
	private static final String base_json="base.json";
	private static final String ext_ids="ext.ids";
	
	@Autowired
	private IGeoDataService geoDataService;
	
	@Autowired
	private IOrganizationService organizationService;
	

	@Override
	public JSONObject getRegionGeoJson() throws Exception {
		Object o=PropertyUtil.getContextProperty(base_json);
		//o="common/beijing-geo.txt";
		if(o!=null){
			String fileName= o.toString();
			return ConfigJsonUtil.readJsonObject(fileName);
		}
		else{
			return null;
		}
	}

	@Override
	public JSONObject getRegionGeoJsonReplaceChildren(List<Long> regionCods) throws Exception {
		JSONObject json= getRegionGeoJson();
		if(json!=null){
			
			JSONArray retFeatures=new JSONArray();
			JSONArray features=json.getJSONArray("features");
			if(features!=null && regionCods!=null){
				for(int i=0;i<features.size();i++   ){
					try{
						JSONObject f=features.getJSONObject(i);
						String id=f.getString("id");
						
						String name=f.getJSONObject("properties").getString("name");
						for(Long delId:regionCods)
						{
							if(id!=null && delId!=null ){
								if(!id.equals(delId.toString()))
								{
									retFeatures.add(f);
								}else{
									System.out.println("remove:"+f);
								}
							}
						}
						
					
					}catch(Exception e){
						logger.error(e.getMessage(),e);
					}
				}
				
				
				//TODO 加入处理过的子区
				List<GeoDataEntity> geoDataList=geoDataService.listAllGeoData();
				if(geoDataList!=null ){
					for(GeoDataEntity entity:geoDataList){
						if(entity!=null && entity.getIsActive()==1){
							JSONObject dataFeatures=new JSONObject();
							JSONObject properties=new JSONObject();
							JSONObject geometry=new JSONObject();
							dataFeatures.put("type", "Feature");
							dataFeatures.put("id", entity.getRegionCode().toString());
							
							properties.put("name", entity.getRegionName());
							properties.put("childNum", entity.getChildNum());
							properties.put("cp", JSON.parseArray(entity.getNameCp()));
							
							geometry.put("type", entity.getType());
							geometry.put("coordinates", JSON.parseArray(entity.getDatas()));
							
							dataFeatures.put("properties", properties);
							dataFeatures.put("geometry", geometry);
							
							retFeatures.add(dataFeatures);
						}
						
					}
				}
				
				
				json.remove("features");
				json.put("features", retFeatures);
			}
			
			
		}
		
		return json;
	}
	
	@Override
	public JSONObject getRegionGeoJsonByProp() throws Exception{
		List<Long> regionCods=new ArrayList<Long>();
		Object o= PropertyUtil.getContextProperty(ext_ids);
		//o="common/beijing-geo.txt";
		if(o!=null){
			String ids= o.toString();
			for(String extId:ids.split(",")){
				regionCods.add(Long.valueOf(extId));
			}
		}
		return getRegionGeoJsonReplaceChildren(regionCods);
		
	}
	
	@Override
	public JSONObject getOrgPointGeoJson() throws Exception{
		JSONObject json=new JSONObject();
		JSONArray features=new JSONArray();
		
		json.put("type", "FeatureCollection");
		
		
		
		List<OrganizationEntity> list=organizationService.listMapOrg();
		if(list!=null){
			for(OrganizationEntity org:list){
				
				JSONObject properties=new JSONObject();
				JSONObject geometry=new JSONObject();
				JSONObject feature=new JSONObject();
				properties.put("name", org.getName());
				geometry.put("type", "Point");
				geometry.put("coordinates", JSON.parseArray(org.getGis()));
				feature.put("id", "o_"+org.getId());
				feature.put("type", "Feature");
				feature.put("properties", properties);
				feature.put("geometry", geometry);
				features.add( feature);
			}
		}
		
		json.put("features", features);
		
		
		return json;
		
		
	}
	

	public static void main(String arg[]){
		GeoMapServiceImpl gs=new GeoMapServiceImpl();
		List<Long> list=new ArrayList<Long>();
		list.add(110105L);
		try {
			JSONObject json=gs.getRegionGeoJsonReplaceChildren(list);
			System.out.println(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
