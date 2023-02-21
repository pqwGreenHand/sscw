package com.zhixin.zhfz.bacs.services.crimedefine;

import com.zhixin.zhfz.bacs.dao.crimedefine.ICrimeDefineMapper;
import com.zhixin.zhfz.bacs.entity.CrimeDefineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Component()
public class CrimeDefineServiceImpl implements ICrimeDefineService {
	
	@Autowired
	private ICrimeDefineMapper mapper;

	/**
	 * 查询案件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CrimeDefineEntity> list(Map<String, Object> map) throws Exception {
		return mapper.list(map);
	}

	/**
	 * 案件类型计数分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public int count(Map<String, Object> map) throws Exception {
		return mapper.count(map);
	}

	/**
	 * 根据id查询案件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getCrimeTypeId(Map<String, Object> map) throws Exception{		
		return mapper.getCrimeTypeId(map);
	}

	/**
	 * 删除案件
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void delete(int id) throws Exception {
		mapper.delete(id);
	}

	/**
	 * 修改案件
	 * @param entity
	 */
	@Override
	public void update(CrimeDefineEntity entity) {
        mapper.update(entity);		
	}

	/**
	 * 插入案件
	 * @param entity
	 * @throws Exception
	 */
	@Override
	public void insert(CrimeDefineEntity entity) throws Exception {
		mapper.insert(entity);
	}

	/**
	 * 更新热度
	 * @param entity
	 */
	@Override
	public void updateSortNo(CrimeDefineEntity entity) {
		mapper.updateSortNo(entity);		
	}

	
}
