package com.zhixin.zhfz.common.services.schedule;

import com.zhixin.zhfz.common.entity.ScheduleEntity;
import com.zhixin.zhfz.common.form.ScheduleForm;

import java.util.List;
import java.util.Map;

/**
 * @prgram: zhfz
 * @Description: 待办服务接口
 * @Auther: xcf
 * @Date: 2019-04-16 20:46
 */
public interface IScheduleSevice {

    int count(Map<String, Object> map);

    List<ScheduleEntity> listSchedule(Map<String, Object> map);

    int updateScheduleById(ScheduleForm scheduleForm);
}
