package boGroup.boSSM;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

public class JDBCDemo {
    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static MysqlDataSource getDataSource() {
        // 连接数据库之前的配置
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("12345");
        dataSource.setServerName("127.0.0.1");
        dataSource.setDatabaseName("ssm");

        // 用来设置时区和数据库连接的编码
        try {
            dataSource.setCharacterEncoding("UTF-8");
            dataSource.setServerTimezone("Asia/Shanghai");
            Utility.log("[JDBCDemo url]: %s", dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }

    public static void addBySQL(String content) {
        // 获取数据源
        MysqlDataSource ds = getDataSource();
        // 准备 SQL 语句
        String sqlInsert = String.format("INSERT INTO `Todo` (content) VALUES ('%s')", content);

        try {
            // 建立数据库连接
            Connection connection = ds.getConnection();
            // 创建执行 SQL 语句需要的 Statement 对象
            Statement statement = connection.createStatement();
            // 执行 SQL 语句
            statement.executeUpdate(sqlInsert);

            // 关闭所有连接
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void selectBySQL() {
        MysqlDataSource ds = getDataSource();
        String sql = String.format("select * from `Todo`");

        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            // 调用 executeQuery 方法, 获得数据库查询结果 rs
            ResultSet rs = statement.executeQuery(sql);

            // 初始状态                 rs 指向null
            // 第一次循环 rs.next()      rs 指向第一条数据
            // ...                     ...
            while (rs.next()) {
                log("id: %s", rs.getInt("id"));
                log("content: %s", rs.getString("content"));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void selectOneBySQL() {
        MysqlDataSource ds = getDataSource();
        String sql = String.format("select * from `Todo` where id = %s", 2);

        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // 取查询结果的第一行数据
            rs.first();
            log("id: %s", rs.getInt("id"));
            log("content: %s", rs.getString("content"));

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void selectByIdSQLInjection(String content) {
        MysqlDataSource ds = getDataSource();
        String sql = String.format("select * from `ssm`.`Todo` where id = %s", content);

        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                log("result: %s", rs.getInt("id"));
                log("result: %s", rs.getString("content"));

            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void selectBySQLSafe(String content) {
        MysqlDataSource ds = getDataSource();
        String sql = "select * from `ssm`.`Todo` where id = ?";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            // 下标从 1 开始, 第 1 个 ? 匹配参数 content
            statement.setString(1, content);


            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                log("result: %s", rs.getInt("id"));
                log("result: %s", rs.getString("content"));

            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        addBySQL("vvvv123");
//        selectBySQL();
//        selectOneBySQL();
//        selectByIdSQLInjection("1 or true");
//        selectBySQLSafe("qqqq or true");
//        selectBySQLSafe("1 or true");

    }
}
