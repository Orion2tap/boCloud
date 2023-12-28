package boMVC.service;

import boMVC.Utility;
import boMVC.models.Message;
import boMVC.models.ModelFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageService {
    public static Message add(HashMap<String, String> form) {
        String author = form.get("author");
        String message = form.get("message");
        Message m = new Message();
        m.author = author;
        m.message = message;

        ArrayList<Message> all = load();
        all.add(m);
        save(all);

        return m;
    }

    public static void save(ArrayList<Message> list) {
        String className = Message.class.getSimpleName();

        // Java 会自动创建一个类, 该类根据lambda表达式的内容实现该接口
        // 然后 new 一个该类的实例, 传给 ModelFactory.save 方法

        // 简写一: 如果能通过上下文推导出参数类型 则可以不写明该类型
        //        (Message model) -> {...} 简写为 (model) -> {...}
        // 简写二: 如果能通过上下文推导出参数类型且只有1个参数 则可以不写括号
        //        (model) -> {...} 简写为 model -> {...}
        ModelFactory.save(className, list, (model) -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.author);
            lines.add(model.message);
            return lines;
        });
    }

    public static ArrayList<Message> load() {
        String className = Message.class.getSimpleName();
        ArrayList<Message> all = ModelFactory.load(className, 2, (modelData) -> {
            String author = modelData.get(0);
            String message = modelData.get(1);

            Message m = new Message();
            m.author = author;
            m.message = message;
            return m;
        });

        return all;
    }

    public static String messageListHtml() {
        /*
        bobo: I love binbin
        binbin: I love bobo

        对象--->字符串
        类似序列化  只不过String不是存储到文件 而是显示在HTML页面上
         */
        ArrayList<Message> all = load();
        StringBuilder result = new StringBuilder();

        for (Message m:all) {
            String s = String.format("%s: %s", m.author, m.message);
            result.append(s);
        }
        return result.toString();
    }

//    原版ORM
//    public static ArrayList<Message> load() {
//        // all 数组的每个元素都是一个Message实例
//        ArrayList<Message> all = new ArrayList<>();
//        String filename = "Message.txt";
//
//        // 获取所有数据 按行切片
//        String data = Utility.load(filename);
//        String[] lines = data.split("\n");
//
//        //                    字符串
//        // 反序列化: 有序字节流---------->对象
//        for (int i = 0; i < lines.length; i = i + 2) {
//            String author = lines[i];
//            String message = lines[i+1];
//
//            Message m = new Message();
//            m.author = author;
//            m.message = message;
//            all.add(m);
//        }
//        return all;
//    }
//
//    public static void save(ArrayList<Message> messages) {
//        StringBuilder all = new StringBuilder();
//        String filename = "Message.txt";
//
//        //              字符串
//        // 序列化: 对象---------->有序字节流
//        for (Message m: messages) {
////            StringBuilder s = new StringBuilder();
////            s.append(m.author);
////            s.append("\n");
////            s.append(m.message);
////            s.append("\n");
////
////            all.append(s);
//            all.append(m.author);
//            all.append("\n");
//            all.append(m.message);
//            all.append("\n");
//        }
//        Utility.save(filename, all.toString());
//    }
}
