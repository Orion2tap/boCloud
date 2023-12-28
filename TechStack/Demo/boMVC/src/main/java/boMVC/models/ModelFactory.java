package boMVC.models;

import boMVC.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelFactory {
    public static <T> ArrayList<T> load(String className, int fieldCount, Deserializer<T> deserializer) {
        // 根据类名加载数据库
        String dir = "db";
        String fileName = String.format("%s/%s.txt", dir, className);
        String data = Utility.load(fileName);
        // 数据库为空
        if (data.strip().length() == 0) {
            return new ArrayList<>();
        // 数据库不为空
        } else {
            /*
                                          data
                             "bobo\nhello\nbinbin\nhaha\n"
                                            ↓
                                      modelsDataRaw
                           ["bobo", "hello", "binbin", "haha"]
                                            ↓
                                        modelsData
                           {"bobo", "hello", "binbin", "haha"}
                                            ↓
                  i         modelData                   model                                  models
                  0     {"bobo", "hello"}   (author: bobo, message: hello)      {(author: bobo, message: hello)}
                  2     {"binbin", "haha"}  (author: binbin, message: haha)     {(author: bobo, message: hello), (author: binbin, message: haha)}
             */
            Utility.log("[反序列化准备]:\n%s", data);
            String[] modelsDataRaw = data.split("\n");
            // 转成List为了使用subList函数
            List<String> modelsData = Arrays.asList(modelsDataRaw);

            ArrayList<T> models = new ArrayList<>();
            for (int i = 0; i < modelsData.size(); i = i + fieldCount) {
                List<String> modelData = modelsData.subList(i, i + fieldCount);
                T model = deserializer.deserialize(modelData);
                models.add(model);
            }
            Utility.log("[反序列化完成]:\n%s", models);
            return models;
        }
    }

    // 由于 Java 的面向对象特性 为了使用非静态的 serialize 函数
    // 需要先 new 一个实现了 Serializer 接口的实例 serializer
    // 然后将实例作为参数传进 save 函数
    public static <T> void save(String className, ArrayList<T> list, Serializer<T> serializer) {
        /*
        参数含义:
              className:    "Message"
              list:         [(author: bobo, message: hello), (author: binbin, message: haha)]
              serializer:   传入了整个实例 但目的仅仅是调用它的实例方法serialize

        序列化过程:
                         m                          lines              line             content
          (author: bobo, message: hello)      ["bobo", "hello"]       "bobo"      "bobo\n"
                                                                      "hello      "bobo\nhello\n"
          (author: binbin, message: haha)     ["binbin", "haha"]      "binbin"    "bobo\nhello\nbinbin\n"
                                                                      "haha       "bobo\nhello\nbinbin\nhaha\n"
        */
        Utility.log("[序列化准备]:\n%s", list);
        StringBuilder content = new StringBuilder();
        for(T m : list) {
            //                 serialize(m)                         拼接
            // 序列化: 对象m -----------------> 字符串数组lines -----------------> 字符串content
            ArrayList<String> lines = serializer.serialize(m);
            for (String line: lines) {
                content.append(line);
                content.append("\n");
            }
        }
        Utility.log("[序列化完成]:\n%s", content);
        // 根据类名更新数据库
        String dir = "db";
        String fileName = String.format("%s/%s.txt", dir, className);
        Utility.save(fileName, content.toString());
    }

    public static <T> T findBy(ArrayList<T> all, Equality<T> equality) {
        for (T m : all) {
            // 返回符合相等条件的对象
            if (equality.equal(m)) {
                return m;
            }
        }

        return null;
    }
}
