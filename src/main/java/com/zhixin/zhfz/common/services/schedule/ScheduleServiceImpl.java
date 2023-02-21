package com.zhixin.zhfz.common.services.schedule;

import com.zhixin.zhfz.common.dao.schedule.IScheduleMapper;
import com.zhixin.zhfz.common.entity.ScheduleEntity;
import com.zhixin.zhfz.common.form.ScheduleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @prgram: zhfz
 * @Description: 待办实现类
 * @Auther: xcf
 * @Date: 2019-04-16 20:47
 */
@Service
public class ScheduleServiceImpl implements IScheduleSevice{


    @Autowired
    private IScheduleMapper scheduleMapper;

    @Override
    public int count(Map<String, Object> map) {

        return this.scheduleMapper.count(map);

    }

    @Override
    public List<ScheduleEntity> listSchedule(Map<String, Object> map) {

        return this.scheduleMapper.list(map);
    }

    @Override
    public int updateScheduleById(ScheduleForm scheduleForm) {
        return this.scheduleMapper.updateScheduleById(scheduleForm);
    }
}
