package boGroup.boSSM.controller;

import boGroup.boSSM.Utility;
import boGroup.boSSM.model.TopicModel;
import boGroup.boSSM.model.UserModel;
import boGroup.boSSM.service.TopicService;
import boGroup.boSSM.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class UserController {

    private UserService service;

    // 构造函数
    public UserController(UserService service) {
        // 依赖注入: 自动 new 一个 topicService 传入
//        this.topicService = new topicService();
        this.service = service;
    }

    // 注册页面
    @GetMapping("/register")
    public ModelAndView registerView () {
        Utility.log("[registerView] 加载注册页面");
        return new ModelAndView("/user/register");
    }

    // 用户注册
    @PostMapping("/user/add")
    public ModelAndView add (String username, String password) {
        service.add(username, password);
        Utility.log("[register] 用户注册: 用户名[%s] 密码[%s]", username, password);

        ModelAndView mv = new ModelAndView ("/user/login");
        mv.addObject("greeting", "注册成功");
        return mv;
    }

    // 登录页面
    @GetMapping("/login")
    public ModelAndView loginView () {
        Utility.log("[loginView] 加载登录页面");

        ModelAndView mv = new ModelAndView ("/user/login");
        mv.addObject("greeting", "");
        return mv;
    }

    // 用户登录
    @PostMapping("/user/login")
    public ModelAndView login (String username, String password, HttpServletRequest request) {
        Utility.log("[login] 用户登录: 用户名[%s] 密码[%s]", username, password);
        // 用户验证✔ 添加session 重定向到首页
        if (service.loginAuthentication(username, password)) {
//            // session 还不存在 user_id, 所以这种方式会报错
//            UserModel currentUser = service.currentUser(request);
            // 根据用户名获取用户
            UserModel u = service.findByUsername(username);

            // session 增加 user_id 字段
            request.getSession().setAttribute("user_id", u.getId());

            return new ModelAndView("redirect:/");
        // 用户验证✖ 重定向到登录
        } else {
            ModelAndView mv = new ModelAndView ("/user/login");
            mv.addObject("greeting", "登录失败");
            return mv;
        }
    }


}
