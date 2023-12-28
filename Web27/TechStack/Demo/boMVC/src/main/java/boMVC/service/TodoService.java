package boMVC.service;

import boMVC.Utility;
import boMVC.models.ModelFactory;
import boMVC.models.Todo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// impot ../Utils

public class TodoService {
    public static Todo add(String content) {
        Todo m = new Todo();
        m.content = content;
        m.completed = false;
        m.createdTime = Utility.formattedTime(System.currentTimeMillis() / 1000L);
        m.updatedTime = Utility.formattedTime(System.currentTimeMillis() / 1000L);

        ArrayList<Todo> all = load();
        if (all.size() == 0) {
            m.id = 1;
        } else {
            m.id = all.get(all.size() - 1).id + 1;
        }

        all.add(m);
        save(all);

        return m;
    }

    public static void save(ArrayList<Todo> list) {
        String className = Todo.class.getSimpleName();
        ModelFactory.save(className, list, (model) -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.content);
            lines.add(model.completed.toString());
            lines.add(model.createdTime);
            lines.add(model.updatedTime);
            return lines;
        });
    }

    public static ArrayList<Todo> load() {
        String className = Todo.class.getSimpleName();
        ArrayList<Todo> all = ModelFactory.load(className, 5, (modelData) -> {

            Todo m = new Todo();
            m.id = Integer.valueOf(modelData.get(0));
            m.content = modelData.get(1);
            m.completed = Boolean.valueOf(modelData.get(2));
            m.createdTime = modelData.get(3);
            m.updatedTime = modelData.get(4);

            return m;
        });

        return all;
    }

    public static void delete(Integer id) {
        ArrayList<Todo> ms = load();
        for (Todo m : ms) {
            if (m.id.equals(id)) {
                ms.remove(m);
                break;
            }
        }

        save(ms);
    }

    public static Todo findById(Integer id) {
        ArrayList<Todo> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }
    
    public static void update(Integer id, String content) {
        ArrayList<Todo> ms = load();

        for (Todo e : ms) {
            if (e.id.equals(id)) {
                e.content = content;
                e.updatedTime = Utility.formattedTime(System.currentTimeMillis() / 1000L);
            }
        }
        
        save(ms);
    }

    public static void complete(Integer id) {
        ArrayList<Todo> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                e.completed = true;
            }
        }

        save(ms);
    }

    // 返回所有todo的HTML
    public static String todoListHtml(){
        ArrayList<Todo> all = load();
        StringBuilder allHtml = new StringBuilder();

        for (Todo m:all) {
            String s = String.format(
                    " <h3>\n" +
                            "   %s:  %s " +
                            "<a href=\"/todo/delete?id=%s\">删除</a>\n" +
                            "<a href=\"/todo/edit?id=%s\">编辑</a>\n" +
                            "<a href=\"/todo/complete?id=%s\">完成</a>" +
                            " </h3>" +
                            "<h4>创建时间: %s</h4>" +
                            "<h4>修改时间: %s</h4>",
                    m.id,
                    m.content,
                    m.id,
                    m.id,
                    m.id,
                    m.createdTime,
                    m.updatedTime
            );

            // 如果 completed 属性为 true, 替换 h3 标签
            if (m.completed) {
                s = s.replace("<h3>", "<h3 style='text-decoration:line-through'>");
            }
            allHtml.append(s);
        }

        return allHtml.toString();
    }

}
