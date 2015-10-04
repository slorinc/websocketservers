package com.comeon.servera.configuration;

import com.comeon.servera.beans.RefreshValuesBean;
import com.comeon.servera.services.WsSockJsClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by s_lor_000 on 10/2/2015.
 */
@Configuration
@EnableScheduling
public class CustomSchedulingConfigurer implements SchedulingConfigurer {

    @Autowired
    private RefreshValuesBean refreshValuesBean;

    @Autowired
    private WsSockJsClientService wsSockJsClientService;

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                wsSockJsClientService::sendData,
                triggerContext -> {
                    Calendar nextExecutionTime = new GregorianCalendar();
                    Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                    nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                    nextExecutionTime.add(Calendar.MILLISECOND, refreshValuesBean.getRefreshData()*1000);
                    return nextExecutionTime.getTime();
                }
        );
    }
}
