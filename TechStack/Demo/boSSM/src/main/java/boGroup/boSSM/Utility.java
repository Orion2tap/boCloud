package boGroup.boSSM;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    private static Logger logger = LoggerFactory.getLogger(Utility.class);
    static public void log(String format, Object...args) {
        logger.info(String.format(format, args));
    }

    public static String load(String filename) {
        String content = "";
        try (FileInputStream is = new FileInputStream(filename)) {
            content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void save(String filename, String data) {
        try (FileOutputStream os = new FileOutputStream(filename)) {
            os.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("12345");
        dataSource.setServerName("127.0.0.1");
        dataSource.setDatabaseName("ssm");

        // 用来设置时区和数据库连接的编码
        try {
            dataSource.setCharacterEncoding("UTF-8");
            dataSource.setServerTimezone("Asia/Shanghai");
            Utility.log("[url]: %s", dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }
    // 时间格式转换
    public static String formattedTime(Long unixTime) {
        Date date = new Date(unixTime * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }
    // 从 jar 包读文件
    public static InputStream fileStream(String path) throws FileNotFoundException {
        String resource = String.format("%s.class", Utility.class.getSimpleName());
        Utility.log("[Utility - resource]:%s", resource);
        Utility.log("[Utility - resource path]:%s", Utility.class.getResource(""));
        URL res = Utility.class.getResource(resource);
        // 从 jar 包读取
        if (res != null && res.toString().startsWith("jar:")) {
            /*
            jar包内的目录结构:
                 * guaMVC-1.0.jar
                     * boMVC
                     * freemarker
                     * META-INF
                         * LICENSE
                         * MANIFEST.MF (其中Main-Class为boMVC.Server)
                     * static
                     * templates
                     * application.properties
                     * schema.sql
             */
            // 打包后, templates 放在 jar 包的根目录下, 要加 / 才能取到
            // 不加 / 就是从 类的当前包目录下取
            path = String.format("/%s", path);
            InputStream is = Utility.class.getResourceAsStream(path);
            if (is == null) {
                throw new FileNotFoundException(String.format("在 jar 里面找不到 %s", path));
            } else {
                return is;
            }
        // 常规读取
        } else {
            path = String.format("src/main/resources/%s", path);
            return new FileInputStream(path);
        }
    }


}
