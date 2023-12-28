package boGroup.boSSM.mapper;

import boGroup.boSSM.model.BoardModel;
import boGroup.boSSM.model.TopicModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

// Spring-Boot 在 controller 进行依赖注入
@Repository
// MyBatis-Spring-Boot 将该接口与对应 xml 文件建立映射, 并注入到 session
@Mapper
public interface TopicMapper {
    // 增
    void insert(TopicModel m);

    // 删
    void delete(int id);

    // 改
    void update(TopicModel m);

    // 查
    TopicModel selectOne(int id);

    ArrayList<TopicModel> selectAll();

    TopicModel selectOneWithComments(int id);

    TopicModel selectOneWithCommentsAndUser(int id);

    BoardModel findBoardByBoardId (int id);

    ArrayList<TopicModel> findTopicsByBoardId(int id);

    ArrayList<BoardModel> selectAllBoards();
}
