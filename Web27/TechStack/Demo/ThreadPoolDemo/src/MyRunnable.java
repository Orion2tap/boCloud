import java.util.Date;

/**
 * 每个 MyRunnable 对象都是一个由线程执行的任务
 * 重写了 run 和 toString
 * @author bobo
 */

public class MyRunnable implements Runnable {

    private final String command;

    public MyRunnable(String s) {
        this.command = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " StartTime = " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " EndTime = " + new Date());
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.command;
    }
}