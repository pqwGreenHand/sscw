package com.zhixin.zhfz.zhag.services.xzcftj;

import com.zhixin.zhfz.zhag.dao.xzcftj.XzcftjMapper;
import com.zhixin.zhfz.zhag.entity.CscfEntity;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XzcftjServiceImpl implements XzcftjService {
    @Autowired
    private XzcftjMapper xzcftjMapper;

    /**
     * 行政处罚名称
     *
     * @param day
     * @return
     */
    @Override
    public List<CscfEntity> quyXZCfmcByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzcftjMapper.quyXZCfmcByDay(map);
    }

    /**
     * 行政处罚数量
     *
     * @param day
     * @return
     */
    @Override
    public List<CscfEntity> quyXZCfzsByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzcftjMapper.quyXZCfzsByDay(map);
    }

    /**
     * 警情类别
     *
     * @param day
     * @return
     */
    @Override
    public List<JqxxEntity> quyjqlbByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzcftjMapper.quyjqlbByDay(map);
    }

    /**
     * 创建日期
     *
     * @param day
     * @return
     * @throws Exception
     */
    @Override
    public List<CscfEntity> quyXZcfNumberByDay(Integer day) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xzcftjMapper.quyXZcfNumberByDay(map);
    }




    /**
     * 行政处罚执行时间
     *
     * @param day
     * @return
     */
    @Override
    public List<CscfEntity> selectZXSJByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzcftjMapper.selectZXSJByDay(map);
    }

    /**
     * 行政处罚案别
     *
     * @param day
     * @return
     */
    @Override
    public List<CscfEntity> selectCFABByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzcftjMapper.selectCFABByDay(map);
    }

    /**
     * 通过日期查询行政处罚告警总数
     *
     * @param useDate
     * @return
     */
    @Override
    public int quyXZcfGJByDate(String useDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("useDate", "'"+useDate+"'");
        return xzcftjMapper.quyXZcfGJByDate(map);
    }

    /**
     * 通过日期查询行政处罚预警总数
     *
     * @param useDate
     * @return
     */
    @Override
    public int quyXZcfYJByDate(String useDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("useDate", "'"+useDate+"'");
        return xzcftjMapper.quyXZcfYJByDate(map);
    }
}
