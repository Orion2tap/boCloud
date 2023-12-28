package boGroup.boSSM.controller;

import boGroup.boSSM.Utility;
import boGroup.boSSM.model.TodoModel;
import boGroup.boSSM.service.TodoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.jshell.execution.Util;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class TodoController {

    private TodoService todoService;

    // 构造函数
    public TodoController(TodoService todoService) {
        // 依赖注入: 自动 new 一个 todoService 传入
//        this.todoService = new TodoService();
        this.todoService = todoService;
    }

    @GetMapping("/todo")
    public ModelAndView index() {
        Utility.log("[todo index]");
        ArrayList<TodoModel> todos = todoService.all();
        ModelAndView m = new ModelAndView("todo/index");
        m.addObject("todos", todos);
        return m;
    }

    @PostMapping("/todo/add")
    public ModelAndView add(String content) {
        Utility.log("[todo add] [content]: %s", content);
        todoService.add(content);
        ModelAndView mv = new ModelAndView("redirect:/todo");
        return mv;
    }

    @GetMapping("/todo/delete")
    public String deleteMapper(int id) {                    // 自动类型转换成 int
        Utility.log("[todo delete] [id]: %s", id);
        todoService.deleteById(id);
        return "redirect:/todo";
    }

    @GetMapping("/todo/edit")
    public ModelAndView edit(int id) {
        Utility.log("[todo edit] [id]: %s", id);
        TodoModel todo = todoService.findById(id);
        ModelAndView m = new ModelAndView("todo/edit");
        m.addObject("todo", todo);
        return m;
    }

    @PostMapping("/todo/update")
    public String updateMapper(int id, String content) {
        Utility.log("[todo update] [id&content]: %s %s", id, content);
        todoService.update(id, content);
        return "redirect:/todo";
    }

    @GetMapping("/ajax/todo")
    public ModelAndView ajaxTodoView() {
        Utility.log("[ajax todo]");
        ModelAndView m = new ModelAndView("todo/ajax");
        return m;
    }

    @PostMapping("/ajax/todo/all")
    @ResponseBody
    public String ajaxTodoAll(@RequestBody String jsonString) {
        Utility.log("[json]:%s", jsonString);
        JSONObject object = JSON.parseObject(jsonString);
        Utility.log("[object]:%s", object);

        ArrayList<TodoModel> todos = todoService.all();
        return JSON.toJSONString(todos);
    }

    @GetMapping("/trans")
    @Transactional
    public ModelAndView trans() {
        // 函数内所有数据库操作视作一个事务
        TodoModel t = todoService.add("1111");
        TodoModel t2 = todoService.add("1111");
        if (!t.getId().equals(1)) {
            // 抛出异常则回滚事务
            throw new RuntimeException("eee");
        }
        return new ModelAndView("redirect:/todo");
    }
}
