package boMVC.service;

import boMVC.Utility;
import boMVC.models.Session;
import boMVC.models.ModelFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionService {
    public static Session add(Integer userId, String sessionId) {
        Session m = new Session();
        m.userId = userId;
        m.sessionId = sessionId;

        ArrayList<Session> all = load();
        all.add(m);
        Utility.log("[Session add][%s][%s]", m.userId, m.sessionId);
        save(all);

        return m;
    }

    public static void save(ArrayList<Session> list) {
        Utility.log("[Session save]");
        String className = Session.class.getSimpleName();
        // Java 会自动创建一个类, 该类根据lambda表达式的内容实现该接口
        // 然后 new 一个该类的实例, 传给 ModelFactory.save 方法

        // 简写一: 如果能通过上下文推导出参数类型 则可以不写明该类型
        //        (Message model) -> {...} 简写为 (model) -> {...}
        // 简写二: 如果能通过上下文推导出参数类型且只有1个参数 则可以不写括号
        //        (model) -> {...} 简写为 model -> {...}
        ModelFactory.save(className, list, (model) -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.userId.toString());
            lines.add(model.sessionId);
            return lines;
        });
    }

    public static ArrayList<Session> load() {
        Utility.log("[Session load]");
        String className = Session.class.getSimpleName();
        ArrayList<Session> all = ModelFactory.load(className, 2, (modelData) -> {
            Integer userId = Integer.valueOf(modelData.get(0));
            String sessionId = modelData.get(1);

            Session m = new Session();
            m.userId = userId;
            m.sessionId = sessionId;
            return m;
        });

        return all;
    }

    public static Session findBySessionId(String sessionId) {
        Utility.log("[查找Session根据sessionId]:%s", sessionId);

        ArrayList<Session> all = load();
        for (Session m : all) {
            if (m.sessionId.equals(sessionId)) {
                return m;
            }
        }

        return null;
    }
}
