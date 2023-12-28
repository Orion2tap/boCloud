package boGroup.boSSM.controller;

import boGroup.boSSM.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class HelloWorldController {

    @GetMapping("/demo")
    // 将请求中参数 name111 的值作为传入参数 name 的值, 获取失败则取默认值
    public ModelAndView demoRoute(@RequestParam(name="name111", required=false, defaultValue="默认") String name) {
        ModelAndView mv = new ModelAndView("helloworld");
        mv.addObject("name", name);
        return mv;
    }

    @GetMapping("/demo1")
    // 先从 HttpServletRequest 里面寻找名字为 gua 的参数再传入
    public ModelAndView demoRoute1(String gua) {
        ModelAndView mv = new ModelAndView("helloworld");
        mv.addObject("name", gua);
        return mv;
    }

    @GetMapping("/demo2")
    // 传入 HttpServletRequest
    public ModelAndView demoRoute2(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("helloworld");
        // 从 HttpServletRequest 里面取参数
        String name = request.getParameter("name");
        mv.addObject("name", name);
        return mv;
    }

    // 动态路由
    // 如果访问 /demo3/bobo, 那么 bobo 将作为参数name的值传给路由函数
    @GetMapping("/demo3/{name}")
    public ModelAndView demoRoute3(@PathVariable String name) {
        ModelAndView mv = new ModelAndView("helloworld");
        mv.addObject("name", name);
        return mv;
    }
}
