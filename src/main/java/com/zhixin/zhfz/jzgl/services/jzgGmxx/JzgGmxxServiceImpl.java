package com.zhixin.zhfz.jzgl.services.jzgGmxx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhixin.zhfz.jzgl.dao.jzgGmxx.IJzgGmxxMapper;
import com.zhixin.zhfz.jzgl.entity.JzgGmxxEntity;

 
@Service
public class JzgGmxxServiceImpl implements IJzgGmxxService {


	@Autowired
	private IJzgGmxxMapper jzgGmxxMapper = null;

	@Override
	public List<JzgGmxxEntity> getGmByUserId(Long id) {
		return jzgGmxxMapper.getGmByUserId(id);
	}

	@Override
	public List<List<Integer>> listJzgGmxx(String jzgId) {
		List<List<Integer>> result = new ArrayList<>();//存放以两组为副柜 三组为主柜模式的柜门id集合的集合
		List<Map<String, Object>> jzgGmxxs = jzgGmxxMapper.listJzgGmxx(jzgId);
		//获取中控屏幕位置(即列类型非5)，并按列组合柜门
		int zkpwwz = 2;//中控屏幕列位置
		Map<Integer, List<Integer>> gmMap = new HashMap<>();//key 位置  value id集合
		for (int i = 0; i < jzgGmxxs.size() ; i++) {
			int lwz = Integer.parseInt(jzgGmxxs.get(i).get("wz")+"");
			int id = Integer.parseInt(jzgGmxxs.get(i).get("id")+"");
			if(gmMap.containsKey(lwz)){
				gmMap.get(lwz).add(id);
			}else{
				List<Integer> tempList = new ArrayList<>();
				tempList.add(id);
				gmMap.put(lwz, tempList);
			}
			int lx = Integer.parseInt(jzgGmxxs.get(i).get("lx")+"");
			if(lx!=5){
				zkpwwz = lwz;
			}
		}
		//按照真实两列一组副柜,组合柜门
		List<Integer> gmids = new ArrayList<>();//存放一组柜子的id集合
		for (int i = 1; i <= gmMap.size() ; i++) {
			if(i!=(zkpwwz-1)){//组合副柜
				List<Integer> gmArrOne = gmMap.get(i);//副柜的第一列柜门
				List<Integer> gmArrTwo = gmMap.get(i+1);//副柜的第二列柜门
				for(int j=0; j<5; j++){//副柜为5门
					gmids.add(gmArrOne.get(j));
					gmids.add(gmArrTwo.get(j));
				}
				result.add(gmids);
				gmids = new ArrayList<>();
				i++;//两柜一组
			}else{//组合主柜
				gmids.addAll(gmMap.get(i));
				gmids.addAll(gmMap.get(i+1));
				gmids.addAll(gmMap.get(i+2));
				result.add(gmids);
				gmids = new ArrayList<>();
				i+=2;//三柜一组
			}
		}
		return result;
	}

	@Override
	public List<Map<String, String>> listJzgGmxxData(String jzgId) {
		return jzgGmxxMapper.listJzgGmxxData(jzgId);
	}

	@Override
	public List<JzgGmxxEntity> queryAllJzgGmxxAll(Map<String, Object> map) {
		return jzgGmxxMapper.queryAllJzgGmxx(map);
	}

	@Override
	public int countAllJzgGmxx(Map<String, Object> map) {
		return jzgGmxxMapper.countAllJzgGmxx(map);
	}
 

	@Override
	public void insertGmxxAll(JzgGmxxEntity entity) {
		jzgGmxxMapper.insertGmxx(entity);
		
	}

	@Override
	public void updateGmxx(JzgGmxxEntity entity) {
		jzgGmxxMapper.updateGmxx(entity);
	}

	
}
