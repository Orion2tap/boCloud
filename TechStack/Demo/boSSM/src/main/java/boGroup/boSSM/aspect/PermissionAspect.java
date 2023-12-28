package boGroup.boSSM.aspect;


import boGroup.boSSM.Utility;
import boGroup.boSSM.model.TopicModel;
import boGroup.boSSM.model.UserModel;
import boGroup.boSSM.model.UserRole;
import boGroup.boSSM.service.TopicService;
import boGroup.boSSM.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 注册切面
@Aspect
// 注册 spring bean
@Component
public class PermissionAspect {
    private HttpServletRequest request;
    private UserService userService;
    private TopicService topicService;

    public PermissionAspect(HttpServletRequest request, UserService userService, TopicService topicService) {
        this.request = request;
        this.userService = userService;
        this.topicService = topicService;
    }

//    @Before("execution(* boGroup.boSSM.controller.TodoController.*(..))")
//    public void matchSingle() {
//        Utility.log("单方法匹配 %s", request.getRequestURI());
//    }

//    @Around("execution(* boGroup.boSSM.controller.TodoController.index(..))")
//    public ModelAndView matchSingle(ProceedingJoinPoint joint) throws Throwable {
//        Utility.log("路由函数之前执行 %s", request.getRequestURI());
//
//        // 执行路由函数
//        ModelAndView result = (ModelAndView) joint.proceed();
//
//        Utility.log("路由函数之后执行 %s", request.getRequestURI());
//        return result;
//    }

    @Around("execution(* boGroup.boSSM.controller.TopicController.*(..)) || execution(* boGroup.boSSM.controller.WeiboController.*(..))")
    public ModelAndView loginRequired(ProceedingJoinPoint joint) throws Throwable {
        // TopicController 所有方法被执行时调用
        // @Around 能在执行方法之前和之后处理, 由 loginRequired 决定什么时候调用 controller 的方法
        // execution 匹配方法执行
        //
        // * boGroup.boSSM.controller.TodoController.*(..)
        // 第一个 *，匹配任意方法返回值
        // 第二个 *，匹配 TodoController 下的任意方法, 可以把 * 换成具体方法名
        // (..) 匹配任意参数
        // 简写 @Around("within(boGroup.boSSM.controller.TodoController)")
        // ProceedingJoinPoint 正在被调用的方法
        // 返回值类型要和被处理的控制器方法类型一样, 所以 TodoController 的所有方法返回值都是 ModelAndView

//        getRequestURL() 返回全路径, getRequestURI() 返回除去host（域名或者ip）部分的路径
        Utility.log("[loginRequired 正在访问的 url]: %s", request.getRequestURI());
        Utility.log("[loginRequired 正在执行的方法]: %s %s", joint.getSignature(), joint.getArgs());

        // setAttribute()和getAttribute()，可以给当前HttpServletRequest对象附加多个Key-Value，相当于把HttpServletRequest当作一个Map<String, Object>使用
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        if (userID == null) {
            // 跳转回登陆页面
            Utility.log("loginRequired 没有 session");
            return new ModelAndView("redirect:/login");
        } else {
            UserModel u = userService.findById(userID);
            if (u == null || u.getRole().equals(UserRole.guest)) {
                // 跳转回登陆页面
                Utility.log("loginRequired 用户不存在 %s", userID);
                return new ModelAndView("redirect:/login");
            } else {
                // 执行被插入的方法
                return (ModelAndView) joint.proceed();
            }
        }
    }

//    @Around("execution(* boGroup.boSSM.controller.TopicController.deleteMapper(..)) || execution(* boGroup.boSSM.controller.TopicController.index(..))")
//    @Around("execution(* boGroup.boSSM.controller.TopicController.deleteMapper(..))")
    @Around("execution(* boGroup.boSSM.controller.TopicController.deleteMapper(..)) || execution(* boGroup.boSSM.controller.TopicController.updateMapper(..))")
    public ModelAndView ownerRequired(ProceedingJoinPoint joint) throws Throwable {
        Utility.log("[ownerRequired 正在访问的 url]: %s", request.getRequestURI());
        Utility.log("[ownerRequired 正在执行的方法]: %s %s", joint.getSignature(), joint.getArgs());
        Integer userID = (Integer) request.getSession().getAttribute("user_id");
        // getParameter(name)：返回请求参数，GET请求从URL读取参数，POST请求从Body中读取参数；

        Integer topicId = Integer.valueOf(request.getParameter("id"));

        if (userID == null) {
            // 跳转回登陆页面
            Utility.log("[ownerRequired 没有 session]");
            return new ModelAndView("redirect:/login");
        } else {
            UserModel u = userService.findById(userID);
            if (u == null || u.getRole().equals(UserRole.guest)) {
                // 跳转回登陆页面
                Utility.log("[ownerRequired 不存在 userID 为 %s 的用户]", userID);
                return new ModelAndView("redirect:/login");
            } else {
                TopicModel topic = topicService.findById(topicId);
                if (topic == null) {
                    return new ModelAndView("redirect:/topic");
                } else {
                    // 如果登录用户的id等于发帖用户的id
                    if (userID.equals(topic.getUserId())) {
                        // 执行被插入的方法
                        return (ModelAndView) joint.proceed();
                    } else {
                        // 重定向到首页
                        return new ModelAndView("redirect:/topic");
                    }
                }
            }
        }
    }

}
