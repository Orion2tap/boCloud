package boGroup.boSSM.controller;

import boGroup.boSSM.Utility;
import boGroup.boSSM.model.BoardModel;
import boGroup.boSSM.model.TopicModel;
import boGroup.boSSM.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class TopicController {

    private TopicService service;
    private HttpServletRequest request;

    // 构造函数
    public TopicController(TopicService topicService, HttpServletRequest request) {
        // 依赖注入: 自动 new 一个 topicService 传入
//        this.topicService = new topicService();
        this.service = topicService;
        this.request = request;
    }

    // topic 首页 (展示所有版块)
    @GetMapping("/topic")
    public ModelAndView indexView() {
        ArrayList<BoardModel> boards = service.allBoards();

        ModelAndView m = new ModelAndView("topic/index");
        m.addObject("boards", boards);

        return m;
    }

    // 指定版块的所有 topic
    @GetMapping("/topic/board/{id}")
    public ModelAndView boardView(@PathVariable Integer id) {
        ArrayList<TopicModel> topics = service.findTopicsByBoardId(id);
        BoardModel board = service.findBoardByBoardId(id);

        ModelAndView mv = new ModelAndView("topic/board");
        mv.addObject("board", board);
        mv.addObject("topics", topics);

        return mv;
    }

    // 每个 topic 的详细内容
    @GetMapping("/topic/detail/{id}")
    public ModelAndView detailView(@PathVariable Integer id) {
        TopicModel m = service.findByIdWithCommentsAndUser(id);

        ModelAndView mv = new ModelAndView("topic/detail");
        mv.addObject("topic", m);

        return mv;
    }

    // 添加 topic
    @PostMapping("/topic/add/{id}")
    public ModelAndView add(String title, String content, @PathVariable Integer id) {
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        service.add(title, content , userID, id);

        return new ModelAndView("redirect:/topic/board/" + id);
    }

    // topic 编辑页面
    @GetMapping("/topic/edit")
    public ModelAndView editView(int id) {
        TopicModel topic = service.findById(id);

        ModelAndView m = new ModelAndView("topic/edit");
        m.addObject("topic", topic);

        return m;
    }

    // topic 更新
    @PostMapping("/topic/update")
    public ModelAndView updateMapper(int id, String content) {
        Utility.log("[topic update content]: %s", content);
        service.update(id, content);

        return new ModelAndView("redirect:/topic/detail/" + id);

    }

    // topic 删除
    @GetMapping("/topic/delete")
    public ModelAndView deleteMapper(int id) {
        service.deleteById(id);
        return new ModelAndView("redirect:/topic");
    }
}
