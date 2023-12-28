# 目录
1. 基础
    - 特点
    - 结构
    - 重要字段
2. 场景
    - 构造函数
    - 初始化
    - 扩容
3. 关键方法分析
    - tableSizeFor(int cap)
    - hash(Object key)
    - indexFor(int hash, int length)
    - resize()
    - put(K key, V value)
    - get(Object key)
4. 参考链接

## 1. 基础
![structure](https://github.com/liubobo1996/boboWeb/raw/master/MyPic/Collection/HashMap/structure.jpg)

#### 特点
1. key-value 结构, 数据类型不限制
2. 根据 key 计算 hash 值进而计算索引, 根据索引存储 Node<K,V>
3. 计算结果的无序性导致了元素存储的无序性
4. 最多一个 key 为 null (不约束 value)
5. 查询效率高
6. 线程不安全
    - 因为 put(), resize() 等方法允许多线程操作同一个数组
    - 可能造成数据异常甚至死循环
    - 线程安全的 Map 可以选择 ConcurrentHashMap
7. 查找/插入元素的时间复杂度
    - 最好情况 不存在哈希碰撞, O(1)
    - 中间情况 链表转化为红黑树, O(logn)
    - 最坏情况 所有元素都映射到同一个桶, 等价于一个链表, O(n)
8. 减少 hash 碰撞的办法
    - 改善 hash 算法
    - 扩大 table 容量

#### 结构
1. 底层是一个由桶组成的数组, 名为 table, 默认初始容量 16
2. 根据 key 计算 hash 值进而计算索引
3. 数组内每个桶的 key 存放索引, value 存放一个 Node<k1, v1>, 它的 next 指向下一个 Node<k2, v2>
```java
static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;
        ...
}   
```
4. hash 碰撞 (计算的 hash 值相同), 通过 equals 判断是否存在相同的 key
    - 存在则覆盖该 key 的 value
    - 不存在则 Node<k1, v1> 指向 Node<k2, v2>, 链表长度变为 2
5. 链表长度大于阈值 (默认为 8)
    - 数据量 < 64, 扩大 table 容量 (MIN_TREEIFY_CAPACITY 默认值为 64)
    - 数据量 ≥ 64, 链表转化为红黑树, 链表长度 < 6 红黑树退化为链表

#### 重要字段
1. size
    - 已存储的数据量, 即 Node 的总数
2. loadFactor
    - 负载因子, 默认 0.75
3. threshold
    - table 的最大容量, 2^n, 最大为 2^30
    - threshold = 数组长度 length * 负载因子 loadFactor

---
## 2. 场景
#### 构造函数
```java
// 一共 4 个构造函数, 以第 1 个构造函数为例
HashMap(int, float)
HashMap(int)
HashMap()
HashMap(Map<? extends K, ? extends V>)

// 1. 构造一个空的 HashMap
// 指定初始容量 7 和负载因子 0.75
HashMap(int initialCapacity, float loadFactor)

// 2. 参数判断
    // 初始容量不为负数
    // 初始容量大于最大值则取最大值
    // 负载因子不能小于 0 并且必须是数字

// 3. 传入负载因子
// 由 tableSizeFor 函数计算最大容量为 8
this.loadFactor = loadFactor;
this.threshold = tableSizeFor(initialCapacity);
```

#### 初始化
```bash
# 第一次 put 元素时初始化
# 计算实际容量, threshold 被重新赋值
# 实际容量 6 = 最大容量 8 * 负载因子 0.75
threshold = threshold * loadFactor
...put...
```

#### 扩容
```bash
# 扩容的三种场景
1. table 为空或其长度为 0
2. 链表长度 > 8 且数据量 < 64
3. 数据量 > 实际容量
# 扩容结果
table length 变为最大容量(threshold 的旧值)的 2 倍
```

---
## 3. 关键方法分析
#### tableSizeFor(int cap)
1. 暴力方法实现的 tableSizeFor
```
涉及对数、除、取整，强制类型转换、指数五种高级运算, 必然需要大量底层操作, 严重降低性能
cap 即输入的初始容量 initialCapacity
```
```
public static int fun(int cap) {
        // 取对数
        double m = Math.log(cap) / Math.log(2);
        // 向上取整
        int m2 = (int) Math.ceil(m);
        // 最大容量 threshold = 2 的 m 次幂
        return (int) Math.pow(2, m2);
}
```
2. HashMap 作者通过位运算实现的 tableSizeFor
```
static final int tableSizeFor(int cap) {
        // 如果不 减 1, 输入 32 会返回 64, 而 32 本身就是 2 的幂次方, 符合要求
        // 我们需要输入 32, 返回 32, 因此将输入减 1
        int n = cap - 1;
        // 所有有效位都变为 1
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        // 如果 n < 0, 返回 1, 否则进入下一个判断
        // 如果 n >= MAXIMUM_CAPACITY, 返回 MAXIMUM_CAPACITY, 否则返回 n + 1 (0111 变 1000)
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```
![tableSizeFor](https://github.com/liubobo1996/boboWeb/raw/master/MyPic/Collection/HashMap/tableSizeFor.png)

3. 移位思想
```
求大于 5 且最接近 5 的 2 的幂次方 (8)
二进制下体现为最高位的前一位变 1, 后面位全变 0
```
```
十进制 5       0000 0101
                  ↓
全变为 1       0000 0111
                  ↓
加 1 得到      0000 1000    十进制 8
```
4. 作者思路
```
先移位，再或运算
右移 1 位，再或运算，就有 2 位变为 1 (最高位和最高位后面的一位)
右移 2 位，再或运算，就有 4 位变为 1
...
最后右移 16 位再或运算，保证 32 位的 int 类型整数最高有效位之后的位都能变为 1
```
```
原始值   00001xxx xxxxxxxx xxxxxxxx xxxxxxxx [共32位]
右移1位  000001xx xxxxxxxx xxxxxxxx xxxxxxxx
或运算   000011xx xxxxxxxx xxxxxxxx xxxxxxxx
右移2位  00000011 xxxxxxxx xxxxxxxx xxxxxxxx
或运算   00001111 xxxxxxxx xxxxxxxx xxxxxxxx
右移4位  00000000 1111xxxx xxxxxxxx xxxxxxxx
或运算   00001111 1111xxxx xxxxxxxx xxxxxxxx
右移8位  00000000 00001111 1111xxxx xxxxxxxx
或运算   00001111 11111111 1111xxxx xxxxxxxx
右移16位 00000000 00000000 00001111 11111111
或运算   00001111 11111111 11111111 11111111
结果加1  00010000 00000000 00000000 00000000
```
```
不管该 32 位原始值多大，都能将其转换，只是值较小时，可能多做几次无意义操作
这个方法之所以高效，是因为移位运算和或运算都属于比较底层的操作
```

#### hash(Object key)
1. hash()
```
static final int hash(Object key) {
    int h;    
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```
2. hashCode()
```
s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
s[i] 是字符串的第 i 个字符的 ASCII 码，n 是字符串的长度，^ 表示求幂。空字符串的哈希值为 0。
```
3. 将 hashCode 右移 16位后与原值进行异或运算
```
这样做是从速度、质量等多方面综合考虑的，而且将高位和低位进行混合运算，这样是可以有效降低冲突概率的。
另外，高位是可以保证不变的，变的是低位，并且低位中掺杂了高位的信息，最后生成的 hash 值的随机性会增大。
```
```
467,926,597                         // hashCode
00011011 11100011 11111110 01000101 // hashCode (bin)
00000000 00000000 00011011 11100011 // 右移 16 位
00011011 11100011 11111111 11100111 // 异或 (无进位加法)  
```

#### indexFor(int hash, int length)
1. 暴力方法计算索引 i
```
// 相对于位运算更消耗性能
hash % length
```
2. HashMap 作者通过位运算计算索引 i
```
// jdk1.7 的源码，jdk1.8 没有这个方法，但是原理一样
static int indexFor(int hash, int length) {  
    return hash & (length-1);
}   
```
![indexFor](https://github.com/liubobo1996/boboWeb/raw/master/MyPic/Collection/HashMap/indexFor.png)

#### resize()
```
扩容后新索引的计算
    oldCap = 16 扩容到 newCap = 32
    hash & oldCap == 0 扩容后 索引不变
    hash & oldCap != 0 扩容后 新索引 = 旧索引 + oldCap (16)
图解见底部链接
```

#### put(K key, V value)
```
此处介绍的是 putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict), 因为 put() 其实就是直接调用的 putVal()
```
![put](https://github.com/liubobo1996/boboWeb/raw/master/MyPic/Collection/HashMap/put.png)

#### get(Object key)
```
此处介绍的是 getNode(int hash, Object key), 因为 get() 其实就是直接调用的 getNode()

前置判断
从索引处的第一个 Node 开始遍历, 判断其 key 值和参数的 key 是否相同
    1. 相同, 返回该 Node
    2. 不相同, 判断结构 (红黑树/链表), 按类型返回查询结果
```

---
## 4. 参考链接
1. [详解 HashMap 数据结构](https://juejin.cn/post/6844904111817637901)
2. [tableSizeFor 方法图解](https://segmentfault.com/a/1190000039392972)
3. [HashMap 常用方法测试](https://javaguide.cn/java/collection/hashmap-source-code.html#hashmap-%E5%B8%B8%E7%94%A8%E6%96%B9%E6%B3%95%E6%B5%8B%E8%AF%95)
