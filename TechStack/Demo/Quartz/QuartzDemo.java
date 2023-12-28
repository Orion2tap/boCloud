import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzDemo {
    public static void main(String[] args) throws SchedulerException {
        // 创建Scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 定义JobDetail
        JobDetail statefulJob = JobBuilder.newJob(StatefulExampleJob.class)
                .withIdentity("statefulJob", "group1").build();

        JobDetail statelessJob = JobBuilder.newJob(StatelessExampleJob.class)
                .withIdentity("statelessJob", "group1").build();

        // 定义Cron Trigger（包括misfire策略）
        Trigger statefulJobTrigger = TriggerBuilder.newTrigger()
                .withIdentity("statefulJobTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?")
                    .withMisfireHandlingInstructionDoNothing())
                .build();

        Trigger statelessJobTrigger = TriggerBuilder.newTrigger()
                .withIdentity("statelessJobTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?")
                    .withMisfireHandlingInstructionFireAndProceed())
                .build();

        // 调度作业
        scheduler.scheduleJob(statefulJob, statefulJobTrigger);
        scheduler.scheduleJob(statelessJob, statelessJobTrigger);

        // 启动调度器
        scheduler.start();
    }
}
