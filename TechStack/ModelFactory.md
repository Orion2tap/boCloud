### [ModelFactory]
#### 1. Deserializer 接口
```
public interface Deserializer <T> {
    T deserialize(List<String> modelData);
}
```
#### 2. 在 MessageService.load() 中使用lambda表达式
```
// Java 会自动创建一个类, 该类根据lambda表达式的内容实现该接口
// 然后 new 一个该类的实例, 传给 ModelFactory.load 方法
public static ArrayList<Message> load() {
    ...
    // 写法一: 如果能通过上下文推导出参数类型 则可以不写明该类型
    //        (Message modelData) -> {...} 简写为 (modelData) -> {...}
    // 写法二: 在写法一成立的情况下 如果只有1个参数 则可以不写括号
    //        (modelData) -> {...} 简写为 modelData -> {...}
    ArrayList<Message> all = ModelFactory.load(className, 2, (modelData) -> {
        String author = modelData.get(0);
        String message = modelData.get(1);

        Message m = new Message();
        m.author = author;
        m.message = message;
        return m;
    });
    ...
}
```
#### 3. ModelFactory.save(...)
  1. 由于 Java 的面向对象特性 为了使用非静态的 serialize 函数, 需要先 new 一个实现了 Serializer 接口的实例 serializer, 然后将实例作为参数传进 ModelFactory.save 函数
  2. **三个参数**
  ```
  className:    "Message"
  list:         [(author: bobo, message: hello), (author: binbin, message: haha)]
  serializer:   传入了整个实例 但目的仅仅是调用它的实例方法serialize
  ```
  3. **序列化过程**
  ```
  /*
                    serialize(m)                         拼接
     序列化: 对象m -----------------> 字符串数组lines -----------------> 字符串content
   */

  /*
                     m                          lines              line             content
      (author: bobo, message: hello)      ["bobo", "hello"]       "bobo"      "bobo\n"
                                                                  "hello      "bobo\nhello\n"
      (author: binbin, message: haha)     ["binbin", "haha"]      "binbin"    "bobo\nhello\nbinbin\n"
                                                                  "haha       "bobo\nhello\nbinbin\nhaha\n"
   */
  ```
  4. **伪代码**
  ```
  public static <T> void save(String className, ArrayList<T> list, Serializer<T> serializer) {
      ...
      for (T m: list) {
          ArrayList<String> lines = serializer.serialize(m);
          ...
      }
      ...
  }
  ```
