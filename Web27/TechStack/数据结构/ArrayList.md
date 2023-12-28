#### ArrayList
```bash
# new
ArrayList<String> a = new ArrayList<>();
# 增
a.add("hello");
# 删 (按下标 / 按元素)
a.remove(0);
a.remove(s2);
# 改
a.set(0, "hello2");
# 查 (取)
String s1 = a.get(0);
# 长度
int length = a.size();
# 打印
log("打印数组 %s \n", a);
# for each
for (String e:a) {
    log("foreach e %s", e);
}
```
