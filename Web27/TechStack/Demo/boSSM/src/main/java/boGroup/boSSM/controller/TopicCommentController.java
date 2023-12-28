package boGroup.boSSM.controller;

import boGroup.boSSM.model.TopicCommentModel;
import boGroup.boSSM.service.TopicCommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TopicCommentController {

    private TopicCommentService service;
    private HttpServletRequest request;

    // 构造函数
    public TopicCommentController(TopicCommentService service, HttpServletRequest request) {
        // 依赖注入
        this.service = service;
        this.request = request;
    }

    // 添加评论
    @PostMapping("/topicComment/add/{topicId}")
    public ModelAndView add(String content, @PathVariable String topicId) {
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        service.add(content, topicId , userID);

        return new ModelAndView("redirect:/topic/detail/" + topicId);
    }

    // 评论的编辑页面
    @GetMapping("/topicComment/edit")
    public ModelAndView editView(Integer id) {
        TopicCommentModel comment = service.findById(id);

        ModelAndView m = new ModelAndView("topic/comment/edit");
        m.addObject("comment", comment);

        return m;
    }

    // 更新评论
    @PostMapping("/topicComment/update")
    public ModelAndView updateMapper(Integer id, String content) {
        service.update(id, content);
        Integer topicId = service.findById(id).getTopicId();
        return new ModelAndView("redirect:/topic/detail/" + topicId);
    }

    // 删除评论
    @GetMapping("/topicComment/delete")
    public ModelAndView deleteMapper(Integer id) {
        // 注意顺序
        // 先通过评论 id 获得 topicId, 再删除评论
        Integer topicId = service.findById(id).getTopicId();
        service.delete(id);
        return new ModelAndView("redirect:/topic/detail/" + topicId);
    }
}
