package com.zhixin.zhfz.bacs.services.camera;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.bacs.dao.camera.ICameraMapper;
import com.zhixin.zhfz.bacs.entity.CameraEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CameraServiceImpl implements ICameraService {
	
	@Autowired
	ICameraMapper mapper;

	@Override
	public List<CameraEntity> list(Map<String, Object> map) throws Exception {
		return mapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) throws Exception {
		return mapper.count(map);
	}

	@Override
	public void delete(int id) throws Exception {
		mapper.delete(id);
	}

	@Override
	public void update(CameraEntity entity) throws Exception {
		mapper.update(entity);
	}

	@Override
	public void insert(CameraEntity entity) throws Exception {
		mapper.insert(entity);
	}
	
	@Override
	public List<CameraEntity> getCameraByRoomID(Map<String, Object> map) throws Exception {
		return mapper.getCameraByRoomID(map);
	}

	@Override
	public void insertList(List<CameraEntity> cameraList) throws Exception {
		mapper.insertList(cameraList);
	}
}
