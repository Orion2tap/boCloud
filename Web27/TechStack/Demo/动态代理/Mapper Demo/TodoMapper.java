// TodoMapper 接口
public interface TodoMapper {
//    TodoModel selectTodo(int id);
    // 为便于理解 这里直接返回一个 String
    // 原本应该执行 SQL 语句之后从数据库返回 TodoModel
    String selectTodo(int id);
}
