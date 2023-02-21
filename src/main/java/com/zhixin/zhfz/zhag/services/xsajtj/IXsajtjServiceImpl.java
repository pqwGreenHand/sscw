package com.zhixin.zhfz.zhag.services.xsajtj;

import com.zhixin.zhfz.jxkp.entity.RateSignEntity;
import com.zhixin.zhfz.zhag.dao.xsajtj.IXsajtjMapper;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IXsajtjServiceImpl implements IXsajtjService {

    @Autowired
    private IXsajtjMapper xsajtjMapper;

    /**
     * 查询多少天内受理立案折线图
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> selectSLLAByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.selectSLLAByDay(map);
    }

    /**
     * 查询多少天内刑事告警折线图
     * @param day
     * @return
     */
    @Override
    public List<GjxxEntity> selectXSGJByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.selectXSGJByDay(map);
    }
    /**
     * 案件来源
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyAJSourceByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.quyAJSourceByDay(map);
    }

    /**
     * 案件类型
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyAJTypeByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.quyAJTypeByDay(map);
    }

    /**
     * 案件状态
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyAJStateByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.quyAJStateByDay(map);
    }

    /**
     * 案由
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyAJCauseByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.quyAJCauseByDay(map);
    }

    /**
     * 刑事案件数量
     * @param day
     * @return
     */
    @Override
    public List<CriminalAndAdministrationCaseEntity> quyXSAJAllNumberByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.quyXSAJAllNumberByDay(map);
    }
    @Override
    public List<RateSignEntity> quyXSXZAJAllNumberByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.quyXSXZAJAllNumberByDay(map);
    }
    @Override
    public List<RateSignEntity> listGjly(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.listGjly(map);
    }

    /**
     * 告警类型
     * @param day
     * @return
     */
    @Override
    public List<GjxxEntity> quyGJTypeByDay(Integer day) {
        Map<String,Object> map = new HashMap<>();
        map.put("day",day);
        return xsajtjMapper.quyGJTypeByDay(map);
    }

}
