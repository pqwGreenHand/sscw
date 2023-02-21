package com.zhixin.zhfz.zhag.services.xzajtj;

import com.zhixin.zhfz.zhag.dao.xzajtj.XzajtjMapper;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XzajtjServiceImpl implements XzajtjService {

    @Autowired
    private XzajtjMapper xzajtjMapper;

    /**
     * 案件来源
     *
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyXZAJSourceByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzajtjMapper.quyXZAJSourceByDay(map);
    }

    /**
     * 案件类型
     *
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyXZAJTypeByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzajtjMapper.quyXZAJTypeByDay(map);
    }

    /**
     * 案件状态
     *
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyXZAJStateByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzajtjMapper.quyXZAJStateByDay(map);
    }

    /**
     * 案由
     *
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyXZAJCauseByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzajtjMapper.quyXZAJCauseByDay(map);
    }

    /**
     * 刑事案件数量
     *
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyXZAJAllNumberByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzajtjMapper.quyXZAJAllNumberByDay(map);
    }

    /**
     * 告警类型
     *
     * @param day
     * @return
     */
    @Override
    public List<GjxxEntity> quyXZGJTypeByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzajtjMapper.quyXZGJTypeByDay(map);
    }

    /**
     * 查询多少天内受理立案折线图
     *
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> selectSLLAByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzajtjMapper.selectSLLAByDay(map);
    }

    /**
     * 查询多少天内刑事告警折线图
     *
     * @param day
     * @return
     */
    @Override
    public List<GjxxEntity> selectXSGJByDay(Integer day) {
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        return xzajtjMapper.selectXSGJByDay(map);
    }
}
