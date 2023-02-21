package com.zhixin.zhfz.bacsapp.services.track;


import com.zhixin.zhfz.bacsapp.dao.track.ITrackMapper;
import com.zhixin.zhfz.bacsapp.entity.TrackInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class TrackServiceImpl implements ITrackService {

	@Autowired
	private ITrackMapper mapper;



	@Override
	public void deleteTrack(Integer id) {
		mapper.deleteTrack(id);
	}

	@Override
	public void insertTrack(TrackInfoEntity entity) throws Exception {
		mapper.insertTrack(entity);
	}



	@Override
	public List<TrackInfoEntity> listTrack(Map<String, Object> map) {
		return mapper.listTrack(map);
	}
	



	}
