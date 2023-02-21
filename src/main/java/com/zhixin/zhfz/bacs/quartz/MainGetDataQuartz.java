package com.zhixin.zhfz.bacs.quartz;

import com.zhixin.zhfz.bacs.quartz.getDateJob.IGetDataJob;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.LinkedList;

public class MainGetDataQuartz implements ApplicationContextAware {

    private volatile ApplicationContext applicationContext;

    public void execute(){
        System.out.println("----------进去抽取警综数据程序---------");
        Collection<IGetDataJob> jobList=new LinkedList<>(this.applicationContext.getBeansOfType(IGetDataJob.class).values());
        for (IGetDataJob job : jobList){
            job.get();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
