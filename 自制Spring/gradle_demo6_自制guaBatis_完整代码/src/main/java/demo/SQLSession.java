package demo;

import demo.Common.Utility;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class SQLSession {
    public static String databaseName = "java";
    private Connection connection;

    public SQLSession() {
        // 取一个数据库连接
        try {
            this.connection = getDataSourceWithDB().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("12345");
        dataSource.setServerName("127.0.0.1");
        return dataSource;
    }

    public static MysqlDataSource getDataSourceWithDB() {
        MysqlDataSource ds = getDataSource();
        ds.setDatabaseName(databaseName);
        return ds;
    }

    public <T> T getMapper(Class<T> clazz) {
        String fileName = String.format("%s.xml", clazz.getSimpleName());
        String path = Utility.resourcePath(fileName);
        Map<Method, String> SQLMap = XMLParser.getSQLMap(path);
        MapperInvocationHandler handler = new MapperInvocationHandler(connection, SQLMap);

        // 这个强制类型转换没法解决, 库里面就是 unchecked
        @SuppressWarnings("unchecked")
        T mapper = (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[] {clazz},
                handler
        );
        return mapper;
    }

}
