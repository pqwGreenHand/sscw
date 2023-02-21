package com.zhixin.zhfz.zhag.services.qzcstj;

import com.zhixin.zhfz.zhag.dao.qzcstj.QzcstjMapper;
import com.zhixin.zhfz.zhag.entity.CscfEntity;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QzcstjServiceImpl implements QzcstjService {

    @Autowired
    private QzcstjMapper qzcstjMapper;

    /**
     * 强制措施名称
     *
     * @param day
     * @return
     */
    @Override
    public List<CscfEntity> quyQzcsByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return qzcstjMapper.quyQzcsByDay(map);
    }

    /**
     * 强制措施总数量
     *
     * @param day
     * @return
     */
    @Override
    public List<CscfEntity> quyQzcszsByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return qzcstjMapper.quyQzcszsByDay(map);
    }

    /**
     * 警情类别
     *
     * @param day
     * @return
     */
    @Override
    public List<JqxxEntity> quyQzcsjqlbByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return qzcstjMapper.quyQzcsjqlbByDay(map);
    }

    /**
     * 创建日期
     *
     * @param day
     * @return
     * @throws Exception
     */
    @Override
    public List<CscfEntity> quyQzcsNumberByDay(Integer day) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return qzcstjMapper.quyQzcsNumberByDay(map);
    }




    /**
     * 强制措施执行时间
     *
     * @param day
     * @return
     */
    @Override
    public List<CscfEntity> selectQzcsZXSJByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return qzcstjMapper.selectQzcsZXSJByDay(map);
    }

    /**
     * 强制措施案别
     *
     * @param day
     * @return
     */
    @Override
    public List<CscfEntity> selectQzcsABByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return qzcstjMapper.selectQzcsABByDay(map);
    }

    /**
     * 通过日期查询强制措施告警总数
     *
     * @param useDate
     * @return
     */
    @Override
    public int quyQzcsGJByDate(String useDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("useDate", "'"+useDate+"'");
        return qzcstjMapper.quyQzcsGJByDate(map);
    }

    /**
     * 通过日期查询强制措施预警总数
     *
     * @param useDate
     * @return
     */
    @Override
    public int quyQzcsYJByDate(String useDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("useDate", "'"+useDate+"'");
        return qzcstjMapper.quyQzcsYJByDate(map);
    }

}
