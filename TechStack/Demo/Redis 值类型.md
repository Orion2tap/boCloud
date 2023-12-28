# Redis 值类型 (键类型总为 String)

1. **字符串（String）**：最基本的类型，可以包含任何数据，如文本、数字或二进制数据。
   - 常用命令：`SET`, `GET`, `INCR`, `DECR`, `APPEND`。

2. **列表（List）**：字符串列表，按插入顺序排序。
   - 常用命令：`LPUSH`, `RPUSH`, `LPOP`, `RPOP`, `LRANGE`。

3. **集合（Set）**：不重复的字符串集合。
   - 常用命令：`SADD`, `SMEMBERS`, `SREM`, `SINTER`, `SUNION`。

4. **有序集合（Sorted Set）**：不重复的元素集合，每个元素关联一个权重。
   - 常用命令：`ZADD`, `ZRANGE`, `ZREM`, `ZSCORE`, `ZRANK`。

5. **散列（Hash）**：键值对的集合，适合存储对象。
   - 常用命令：`HSET`, `HGET`, `HGETALL`, `HDEL`, `HMSET`。

6. **位图（Bitmap）**：通过位操作处理的字符串，用于高效地处理复杂的位运算。
   - 常用命令：`SETBIT`, `GETBIT`, `BITCOUNT`, `BITOP`。

7. **超级日志（HyperLogLog）**：用于基数估计的概率数据结构。
   - 常用命令：`PFADD`, `PFCOUNT`, `PFMERGE`。

8. **地理空间（Geo）**：用于存储地理位置信息，并执行相关查询。
   - 常用命令：`GEOADD`, `GEODIST`, `GEORADIUS`, `GEOHASH`。

9. **流（Streams）**：用于构建消息队列或者实现流处理。
   - 常用命令：`XADD`, `XREAD`, `XGROUP`, `XACK`。
