package boGroup.boSSM.service;

import boGroup.boSSM.mapper.TodoMapper;
import boGroup.boSSM.model.TodoModel;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class TodoService {
    TodoMapper mapper;

    public TodoService(TodoMapper mapper) {
        // 依赖注入: Spring 自动 new 一个 TodoMapper 接口的实例 mapper 传入 TodoService 的构造函数
        this.mapper = mapper;
    }

    public TodoModel add(String content) {
        TodoModel m = new TodoModel();
        m.setContent(content);
//        Utility.log("m before insert id %s", m.getId());    // id = null
        mapper.insert(m);
//        Utility.log("m after insert id %s", m.getId());     // id = 数据库取回的id
        return m;
    }

    public void update(Integer id, String content) {
        TodoModel m = new TodoModel();
        m.setId(id);
        m.setContent(content);
        mapper.update(m);
    }

    public void deleteById(Integer id) {
        mapper.delete(id);
    }

    public  TodoModel findById(Integer id) {
        return mapper.select(id);
    }

    public  ArrayList<TodoModel> all() {
        return mapper.selectAll();
    }
}
