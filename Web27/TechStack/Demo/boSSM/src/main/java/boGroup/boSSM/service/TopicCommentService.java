package boGroup.boSSM.service;

import boGroup.boSSM.Utility;
import boGroup.boSSM.mapper.TopicCommentMapper;
import boGroup.boSSM.model.BoardModel;
import boGroup.boSSM.model.TopicCommentModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TopicCommentService {
    TopicCommentMapper mapper;

    public TopicCommentService(TopicCommentMapper mapper) {
        // 依赖注入
        this.mapper = mapper;
    }

    public TopicCommentModel add(String content, String topicId, Integer userId) {
        TopicCommentModel m = new TopicCommentModel();
        m.setContent(content);
        m.setTopicId(Integer.valueOf(topicId));
        m.setUserId(userId);
        m.setCreatedTime(Utility.formattedTime(System.currentTimeMillis() / 1000L));
        m.setUpdatedTime(Utility.formattedTime(System.currentTimeMillis() / 1000L));

        mapper.insert(m);
        return m;
    }

    public void delete(Integer id) {
        mapper.delete(id);
    }

    public void update(Integer id, String content) {
        TopicCommentModel m = new TopicCommentModel();
        m.setId(id);
        m.setContent(content);
        m.setUpdatedTime(Utility.formattedTime(System.currentTimeMillis() / 1000L));

        mapper.update(m);
    }

    public TopicCommentModel findById(Integer id) {
        return mapper.selectOne(id);
    }

}
