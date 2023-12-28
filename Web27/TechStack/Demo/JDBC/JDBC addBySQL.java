public TodoModel addBySQL(String content) {
    TodoModel m = new TodoModel();
    m.setContent(content);

    // 获取数据源
    MysqlDataSource ds = Utility.getDataSource();
    // 准备 SQL 语句
    String sqlInsert = "INSERT INTO Todo (content) VALUES (?)";

    try {
        // 建立数据库连接 (先执行 schema.sql 的建库建表语句)
        Connection connection = ds.getConnection();
        // 预编译 将 SQL 语句和数据库主键 id 传递给 statement 实例
        PreparedStatement statement = connection.prepareStatement(
                sqlInsert, Statement.RETURN_GENERATED_KEYS
        );
        // 下标从 1 开始, 第 1 个 ? 匹配参数 content
        statement.setString(1, content);
        statement.executeUpdate();
        // 调用statement实例的getGeneratedKeys方法获取所有id
        ResultSet rs = statement.getGeneratedKeys();
        /*
            白:
            rs 拿回的数据是根据你的 sql 来的。
            这里我们可以判断自己写的 sql 只会拿回一条数据，所以直接读取第一行就行了。
            如果不止拿回一条数据，就只能遍历
         */
        // 游标定位到第一行 (默认位置处于第一行之前)
        rs.first();
        Integer id = rs.getInt("GENERATED_KEY");
        m.setId(id);

        // 关闭所有连接 后开先关
        rs.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }

    return m;

}
