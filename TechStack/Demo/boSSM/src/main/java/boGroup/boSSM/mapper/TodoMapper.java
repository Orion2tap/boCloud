package boGroup.boSSM.mapper;

import boGroup.boSSM.model.TodoModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

// Spring-Boot 在 controller 进行依赖注入
@Repository
// MyBatis-Spring-Boot 将该接口与对应 xml 文件建立映射, 并注入到 session
@Mapper
public interface TodoMapper {
    ArrayList<TodoModel> selectAll();

    TodoModel select(int id);

    void insert(TodoModel todo);

    void update(TodoModel todo);

    void delete(int id);


}
