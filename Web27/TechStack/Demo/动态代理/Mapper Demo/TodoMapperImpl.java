// TodoMapper 接口的一个实现类 TodoMapperImpl
public class TodoMapperImpl implements TodoMapper{
    @Override
    public String selectTodo (int id) {
        String result = "[TodoMapperImpl: selectTodo by id " + id + "]";
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        TodoMapper todoMapper = new TodoMapperImpl();
        todoMapper.selectTodo(777);
    }
}

