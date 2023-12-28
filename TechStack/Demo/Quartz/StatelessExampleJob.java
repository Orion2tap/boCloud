import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class StatelessExampleJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Job逻辑
        System.out.println("执行StatelessJob，每次都是新的实例");
    }
}

