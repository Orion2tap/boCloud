package boGroup.boSSM.mapper;

import boGroup.boSSM.model.BoardModel;
import boGroup.boSSM.model.TopicCommentModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

// Spring-Boot 在 controller 进行依赖注入
@Repository
// MyBatis-Spring-Boot 将该接口与对应 xml 文件建立映射, 并注入到 session
@Mapper
public interface TopicCommentMapper {
    // 增
    void insert(TopicCommentModel m);

    // 删
    void delete(Integer id);

    // 改
    void update(TopicCommentModel m);

    // 查
    TopicCommentModel selectOne(Integer id);

    ArrayList<TopicCommentModel> selectAll();

}
