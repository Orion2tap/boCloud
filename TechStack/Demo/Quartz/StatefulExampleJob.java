import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.StatefulJob;

@PersistJobDataAfterExecution
public class StatefulExampleJob implements StatefulJob {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Job逻辑
        System.out.println("执行StatefulJob，保持状态信息");
    }
}
