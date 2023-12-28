package boGroup.boSSM.service;

import boGroup.boSSM.Utility;
import boGroup.boSSM.mapper.TopicMapper;
import boGroup.boSSM.model.BoardModel;
import boGroup.boSSM.model.TopicModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TopicService {
    TopicMapper mapper;

    public TopicService(TopicMapper mapper) {
        // 依赖注入: Spring 自动 new 一个 TopicMapper 接口的实例 mapper 传入 TopicService 的构造函数
        this.mapper = mapper;
    }

    public TopicModel add(String title, String content, Integer userId, Integer boardId) {
        TopicModel m = new TopicModel();
        m.setContent(content);
        m.setUserId(userId);
        m.setTitle(title);
        m.setBoardId(boardId);
        m.setCreatedTime(Utility.formattedTime(System.currentTimeMillis() / 1000L));
        m.setUpdatedTime(Utility.formattedTime(System.currentTimeMillis() / 1000L));

        mapper.insert(m);
        return m;
    }

    public void deleteById(Integer id) {
        mapper.delete(id);
    }

    public void update(Integer id, String content) {
        TopicModel m = new TopicModel();
        m.setId(id);
        m.setContent(content);
        m.setUpdatedTime(Utility.formattedTime(System.currentTimeMillis() / 1000L));

        mapper.update(m);
    }

    public  TopicModel findById(Integer id) {
        return mapper.selectOne(id);
    }

    public  TopicModel findByIdWithComments(Integer id) {
        return mapper.selectOneWithComments(id);
    }

    public  TopicModel findByIdWithCommentsAndUser(Integer id) {
        return mapper.selectOneWithCommentsAndUser(id);
    }

    public ArrayList<BoardModel> allBoards() {
        return mapper.selectAllBoards();
    }

    public ArrayList<TopicModel> findTopicsByBoardId (Integer id) {
        return mapper.findTopicsByBoardId(id);
    }

    public BoardModel findBoardByBoardId (Integer id) {
        return mapper.findBoardByBoardId(id);
    }
}
