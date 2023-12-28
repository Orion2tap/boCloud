package boGroup.boSSM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
@EnableAsync
public class SsmApplication {

    public static void main(String[] args) {
        Utility.log(System.getProperty("file.encoding"));
        Utility.log("本地 ide 或命令行入口，生产级 tomcat 不运行该 main 函数");
        SpringApplication.run(SsmApplication.class, args);
    }
}
