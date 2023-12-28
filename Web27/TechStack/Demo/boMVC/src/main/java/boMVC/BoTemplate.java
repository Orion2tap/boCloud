package boMVC;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;

public class BoTemplate {
    static Configuration config;

    static {
        // static 里面的东西只会被初始化一次
        config = new Configuration(
                Configuration.VERSION_2_3_30);
        String resource = String.format("%s.class", Utility.class.getSimpleName());
        Utility.log("[BoTemplate - resource]:%s", resource);
        Utility.log("[BoTemplate - resource path]:%s", Utility.class.getResource(""));
        var res = Utility.class.getResource(resource);
        // 从 jar 包读取
        if (res != null && res.toString().startsWith("jar:")) {
            config.setClassForTemplateLoading(Utility.class, "/templates");
        // 常规读取
        } else {
            try {
                File f = new File("src/main/resources/templates");
                config.setDirectoryForTemplateLoading(f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        config.setDefaultEncoding("utf-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(true);
        config.setWrapUncheckedExceptions(true);
    }

    public static String render(Object data, String templateFileName) {
        Template template;
        // 获取模板
        try {
            template = config.getTemplate(templateFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(result);
        try {
            template.process(data, writer);
        } catch (TemplateException | IOException e) {
            String messsage = String.format("模板 process 失败 <%s> error<%s>", data, e);
            throw new RuntimeException(messsage, e);
        }
        return result.toString();
    }
}
