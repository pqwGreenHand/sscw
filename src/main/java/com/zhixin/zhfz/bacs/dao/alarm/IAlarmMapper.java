package com.zhixin.zhfz.bacs.dao.alarm;

import com.zhixin.zhfz.bacs.entity.AlarmEntity;
import com.zhixin.zhfz.bacs.entity.AlarmTypeCoutEntity;
import com.zhixin.zhfz.bacs.entity.ArchivesTreeEntity;
import com.zhixin.zhfz.bacs.entity.BurnLogEntity;

import java.util.List;
import java.util.Map;

public interface IAlarmMapper {

    public List<AlarmEntity> list(Map<String, Object> map);

    public int count(Map<String, Object> map);

    public  List<AlarmTypeCoutEntity> listAlarmCountByType(Map<String, Object> map);

}
