package boGroup.boSSM;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        Utility.log("tomcat 使用的生产级 servlet 入口");
        return application.sources(SsmApplication.class);
    }
}
