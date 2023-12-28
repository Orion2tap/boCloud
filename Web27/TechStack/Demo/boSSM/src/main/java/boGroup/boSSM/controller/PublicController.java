package boGroup.boSSM.controller;

import boGroup.boSSM.model.UserModel;
import boGroup.boSSM.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 路由类
@Controller
public class PublicController {
    private UserService userService;

    public PublicController (UserService userService) {
        this.userService = userService;
    }

    // 请求类型为 Get, 请求路径为 "/"
    @GetMapping("/")
    // 接收一个 HttpServletRequest (类似之前的 Request )对象, 返回一个 ModelAndView 对象
    public ModelAndView index(HttpServletRequest request) {
//        Integer userId = (Integer) request.getSession().getAttribute("user_id");
        UserModel u = userService.currentUser(request);
        // 载入模板 index.ftl, 省略后缀
        ModelAndView mv = new ModelAndView("index");
        // 替换页面标记
        mv.addObject("username", u.getUsername());
        return mv;

        /*
            Spring日志:
            Mapped to public org.springframework.web.servlet.ModelAndView                           匹配路由
                boSSM.controller.PublicController.index(javax.servlet.http.HttpServletRequest)
            Selected 'text/html' given [text/html 等属性]                                            文件类型
            View name 'index', model {username=guest}                                               视图名字和数据模型
            Rendering [index.ftl]                                                                   标记替换
            Completed 200 OK                                                                        返回状态码
         */
    }



}
