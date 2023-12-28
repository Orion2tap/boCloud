package boGroup.boSSM.mapper;

import boGroup.boSSM.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

// Spring-Boot 在 controller 进行依赖注入
@Repository
// MyBatis-Spring-Boot 将该接口与对应 xml 文件建立映射, 并注入到 session
@Mapper
public interface UserMapper {
    // 增
    void insert(UserModel m);

    // 删
    void delete(int id);

    // 改
    void update(UserModel m);

    // 查
    UserModel selectOneById(int id);

    UserModel selectOneByUsername(String username);

    ArrayList<UserModel> selectAll();
}
