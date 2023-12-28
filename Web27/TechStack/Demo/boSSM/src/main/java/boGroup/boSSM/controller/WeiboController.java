package boGroup.boSSM.controller;

import boGroup.boSSM.Utility;
import boGroup.boSSM.model.WeiboModel;
import boGroup.boSSM.service.WeiboService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class WeiboController {

    private WeiboService weiboService;

    public WeiboController(WeiboService weiboService) {
        this.weiboService = weiboService;
    }

    @GetMapping("/weibo")
    public ModelAndView index() {
        Utility.log("[weibo index]");
        ArrayList<WeiboModel> weibos = weiboService.all();
        ModelAndView m = new ModelAndView("weibo/index");
        m.addObject("weibos", weibos);
        return m;
    }

    @PostMapping("/weibo/add")
    public ModelAndView add(String content) {
        WeiboModel weibo = weiboService.add(content);
        Utility.log("[weibo add]: %s", weibo);
        ModelAndView mv = new ModelAndView("redirect:/weibo");
        return mv;
    }

    @GetMapping("/weibo/delete")
    public String deleteMapper(int id) {
        Utility.log("[weibo delete] [id]: %s", id);
        weiboService.deleteById(id);
        return "redirect:/weibo";
    }

    @GetMapping("/weibo/edit")
    public ModelAndView edit(int id) {
        WeiboModel weibo = weiboService.findById(id);
        Utility.log("[weibo edit]: %s", weibo);
        ModelAndView m = new ModelAndView("weibo/edit");
        m.addObject("weibo", weibo);
        return m;
    }

    @PostMapping("/weibo/update")
    public String updateMapper(int id, String content) {
        Utility.log("[weibo update] [id]: %s [content]: %s", id, content);
        weiboService.update(id, content);
        return "redirect:/weibo";
    }
}
