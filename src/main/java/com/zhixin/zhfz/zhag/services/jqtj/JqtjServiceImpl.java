package com.zhixin.zhfz.zhag.services.jqtj;

import com.zhixin.zhfz.zhag.dao.jqtj.IJqtjMapper;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JqtjServiceImpl implements IJqtjService {

    @Autowired
    private IJqtjMapper jqtjMapper;

    /**
     * 查询多少天内接警处警折线图
     *
     * @param day
     * @return
     */
    @Override
    public List<JqxxEntity> selectJCJByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return jqtjMapper.selectJCJByDay(map);
    }

    /**
     * 查询多少天内处理未处理折线图
     *
     * @param day
     * @return
     */
    @Override
    public List<JqxxEntity> selectCLByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return jqtjMapper.selectCLByDay(map);
    }

	/**
	 * 处理结果
	 * @param day
	 * @return
	 */
	@Override
	public List<JqxxEntity> quyHandleResultByDay(Integer day) {
		Map<String,Object> map = new HashMap<>();
		map.put("day",day);
		return jqtjMapper.selectHandleResultByDay(map);
	}

	/**
	 * 警情类别
	 * @param day
	 * @return
	 */
	@Override
	public List<JqxxEntity> quyJQTypeByDay(Integer day) {
		Map<String,Object> map = new HashMap<>();
		map.put("day",day);
		return jqtjMapper.quyJQTypeByDay(map);
	}

	/**
	 * 接警方式
	 * @param day
	 * @return
	 */
	@Override
	public List<JqxxEntity> quyJJMethedByDay(Integer day) {
		Map<String,Object> map = new HashMap<>();
		map.put("day",day);
		return jqtjMapper.quyJJMethedByDay(map);
	}

	/**
	 * 受理单位
	 * @param day
	 * @return
	 */
	@Override
	public List<JqxxEntity> quySLCompanyByDay(Integer day) {
		Map<String,Object> map = new HashMap<>();
		map.put("day",day);
		return jqtjMapper.quySLCompanyByDay(map);
	}

	/**
	 * 总警情数
	 * @param day
	 * @return
	 */
	@Override
	public List<JqxxEntity> quyAllJQNumberByDay(Integer day) {
		Map<String,Object> map = new HashMap<>();
		map.put("day",day);
		return jqtjMapper.quyAllJQNumberByDay(map);
	}

	/**
	 * 处警单位
	 * @param day
	 * @return
	 */
	@Override
	public List<JqxxEntity> quyCJCompanyByDay(Integer day) {
		Map<String,Object> map = new HashMap<>();
		map.put("day",day);
		return jqtjMapper.quyCJCompanyByDay(map);
	}
}
