import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        /*
            初始化线程池:
                corePoolSize 核心线程数
                maximumPoolSize 最大线程数
                keepAliveTime 存活时间 (完成任务后)
                unit 存活时间的单位
                workQueue 任务队列的类型及容量
                handler 饱和策略
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                5,
                10,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 创建 MyRunnable 对象, 放入线程池执行
        for (int i = 0; i < 10; i++) {
            Runnable worker = new MyRunnable("" + i);
            pool.execute(worker);
        }

        // 终止线程池
        pool.shutdown();
        while (!pool.isTerminated()) {};
        System.out.println("pool closed");
    }

}
