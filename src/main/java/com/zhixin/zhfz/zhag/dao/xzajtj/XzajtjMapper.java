package com.zhixin.zhfz.zhag.dao.xzajtj;

import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;

import java.util.List;
import java.util.Map;

public interface XzajtjMapper {

    //案件来源
    List<CriminalAndAdministrationCaseEntity> quyXZAJSourceByDay(Map<String, Object> map);

    //案件类型
    List<CriminalAndAdministrationCaseEntity> quyXZAJTypeByDay(Map<String, Object> map);

    //案件状态
    List<CriminalAndAdministrationCaseEntity> quyXZAJStateByDay(Map<String, Object> map);

    //案由
    List<CriminalAndAdministrationCaseEntity> quyXZAJCauseByDay(Map<String, Object> map);

    //刑事案件数量
    List<CriminalAndAdministrationCaseEntity> quyXZAJAllNumberByDay(Map<String, Object> map);

    //告警类型
    List<GjxxEntity> quyXZGJTypeByDay(Map<String, Object> map);

    /**
     * 查询多少天内受理立案折线图
     *
     * @param map
     * @return
     */
    public List<CriminalAndAdministrationCaseEntity> selectSLLAByDay(Map<String, Object> map);

    /**
     * 查询多少天内刑事告警折线图
     *
     * @param map
     * @return
     */
    public List<GjxxEntity> selectXSGJByDay(Map<String, Object> map);
}
