*边看视频边敲的，仅供学习回顾*

[视频请戳](https://www.bilibili.com/video/BV1oW411u75R)

### NoSQL的数据模型：聚合模型（kv模型、Bson、列族、图形）

### NoSQL的数据库的四大分类

KV键值、文档型数据库（mongoDB：基于分布式文件存储的数据库）、列存储数据库（HBase）、图关系数据库

<img src="C:\Users\Sammi\Desktop\NoSQL四类对比图.jpg" alt="四者对比" style="zoom:50%;" />

### 分布式数据库原理

传统关系型数据库的ACID：原子性、一致性、隔离性、持久性

#### CAP：三进二

C:consistency 强一致性

A:availability 可用性

**P:partition tolerance 分区容错性**

<img src="C:\Users\Sammi\Desktop\CAP.png" alt="CAP" style="zoom:50%;" />

#### BASE

基本可用(basically Available) 、软状态(Soft state) 、最终一致性(Eventually consistent）

#### 分布式

不同的多台服务器上面部署不同的服务模块（工程），他们之间通过Rpc/Rmi之间通信和调用，对外提供服务和组内协作

#### 集群

不同的多台服务器上面部署相同的服务模块，通过分布式调度软件进行统一的调度，对外提供服务和访问

### redis（远程字典服务器：REmote DIctionary Server）

KV、 CACHE、persistence　[中文官网](http://www.redis.cn/)  [官网](https://redis.io/)

开源免费，C语言编写，高性能的K-V分布式内存数据库，基于内存运行并支持持久化的NoSQL数据库

*三个特点*

1. 支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用
2. 不仅支持key－value类型的数据，同时还提供list、set、zset、Hash等数据结构的存储
3. 支持数据备份，即master－slave模式的数据备份

*能做什么*

内存存储和持久化：redis支持异步将内存中的数据写到硬盘中，同时不影响继续服务

取最新N个数据的操作，如：可以将最新的10条评论的ID放在Redis的List集合中

模拟类似于httpSession这种需要设定过期时间的功能

发布、订阅消息系统

定时器、计数器



### 安装redis

1.下载redis6.0.6，上传虚机并解压。

```shell
[root@localhost opt]# tar -zxvf redis-6.0.6.tar.gz 
```

2.进入redis-6.0.6目录，执行make命令(需要先安装gcc，gcc是C语言编译工具，**yum install gcc**)

```shell
[root@localhost opt]# cd redis-6.0.6
```

如果先运行make发现没有安装gcc再去安装gcc的话，二次运行make会报这个错

<img src="C:\Users\Sammi\Desktop\make报错.png" alt="make报错" style="zoom:38%;" />

需要先把第一次运行make安装的东西先删掉，然后再make

```shell
[root@localhost redis-6.0.6]# make distclean
```

```she
[root@localhost redis-6.0.6]# make
```

make执行时报错，如图

<img src="C:\Users\Sammi\Desktop\make报错2.png" alt="make报错2" style="zoom:30%;" />

[解决方法](https://blog.csdn.net/realize_dream/article/details/106483499?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-5) (主要是升级gcc这几步)

```shell
yum -y install centos-release-scl
yum -y install devtoolset-9-gcc devtoolset-9-gcc-c++ devtoolset-9-binutils
scl enable devtoolset-9 bash
```

3.make成功后，make install

```shell
[root@localhost redis-6.0.6]# make install
```

**/usr：用户很多应用程序和文件都放在这个目录，类似于windows的program files 目录**

安装的redis就在目录/usr/local/bin下

4.启动redis服务器，指定redis配置文件（redis.conf：修改daemonize yes #后台启动，默认是no）

```shell
[root@localhost bin]# redis-server /myredis/redis.conf
```

5.客户端连接

```shell
[root@localhost bin]# redis-cli  -p 6379
127.0.0.1:6379> ping
PONG
```

```
docker 版
##### 1. docker pull redis:6.0.6
##### 2. 本机下载redis6.0.6并解压
##### 3.配置redis.conf配置文件，并复制到虚机
bind 127.0.0.1 	#注释掉这部分，使redis可以外部访问
daemonize no	#用守护线程的方式启动，默认no
requirepass 你的密码	#给redis设置密码
appendonly yes	#redis持久化，默认是no
tcp-keepalive 300 	#防止出现远程主机强迫关闭了一个现有的连接的错误 默认是300
##### 4. 启动docker的redis
1.直接启动
docker run -p 6379:6379 --name redis -d redis 
2.指定配置文件启动
##### 5.连接redis
docker exec -it 容器ID  /bin/bash ：进入到容器
redis-cli ：启动redis客户端
```



#### redis-benchmark 自带压力测试工具

[菜鸟教程](https://www.runoob.com/redis/redis-benchmarks.html)



#### redis基础知识

redis默认有16个数据库，默认使用的是第0个，可以使用select进行切换数据库，DBSIZE查看数据库大小

```shell
127.0.0.1:6379> select 3
OK
127.0.0.1:6379[3]> DBSIZE
(integer) 0
127.0.0.1:6379[3]> 
```

查看数据库所有的Key

```shell
127.0.0.1:6379> keys *
1) "counter:{tag}:__rand_int__"
2) "key:{tag}:__rand_int__"
3) "mylist:{tag}"
4) "myhash:{tag}:__rand_int__"
```

清除当前库 ==flushDB==

```shell
127.0.0.1:6379> flushDB
OK
127.0.0.1:6379> keys *
(empty array)
```

清除全部数据库的内容==FLUSHALL==



#### 五大数据类型 

[命令参考大全](https://redis.io/commands)

###### Key(键)

```bash
127.0.0.1:6379> keys *   #查看所有的key
(empty array)
127.0.0.1:6379> set name 123  #设置key-value，存在key则覆盖value
OK
127.0.0.1:6379> keys *  
1) "name"
127.0.0.1:6379> get name   #获取key=name的value值
"123"
127.0.0.1:6379> EXISTS name   #判断是否存在key=name，1表示有，0表示没有
(integer) 1
127.0.0.1:6379> EXPIRE name 10   #根据key设置过期时间
(integer) 1
127.0.0.1:6379> ttl name
(integer) 2
127.0.0.1:6379> ttl name   #-2则已过期（移除内存系统），-1表示永不过期
(integer) -2
127.0.0.1:6379> get name
(nil)
127.0.0.1:6379> set name 234
OK
127.0.0.1:6379> type name   #查看key=name的value的类型
string
127.0.0.1:6379> move name 1  #将key=name移到数据库1中
(integer) 1
127.0.0.1:6379> get name
(nil)
127.0.0.1:6379> select 1
OK
127.0.0.1:6379[1]> get name
"234"
```

###### String（单值单value）

一个redis中字符串value最多可以是512M

```bash
127.0.0.1:6379> set key1 value1
OK
127.0.0.1:6379> append key1 ,ceshi   #追加字符串，如果当前key1不存在，就相当于set key1
(integer) 12
127.0.0.1:6379> get key1
"value1,ceshi"
127.0.0.1:6379> strlen key1    #获取字符串的长度
(integer) 12
```

```bash
##自增自减，一定要是数字才能进行加减
127.0.0.1:6379> set num 0
OK
127.0.0.1:6379> get num 
"0"
127.0.0.1:6379> incr num  #自增1
(integer) 1
127.0.0.1:6379> get num
"1"
127.0.0.1:6379> decr num  #自减1
(integer) 0
127.0.0.1:6379> incrby num 12  #设置步长，指定增量
(integer) 12
127.0.0.1:6379> get num
"12"
127.0.0.1:6379> decrby num 6  #设置步长，指定减量
(integer) 6
127.0.0.1:6379> get num
"6"
```

```bash
##getrange 获取指定范围的字符串 全部0到-1
##setrange replace
127.0.0.1:6379> getrange key1 0 -1   # 截取全部字符串，和get key一样
"value1,ceshi"
127.0.0.1:6379> getrange key1 0 3   # 截取字符串[0,3]，不改变原有值
"valu"
127.0.0.1:6379> set key2 zxcvbn
OK
127.0.0.1:6379> get key2
"zxcvbn" 
127.0.0.1:6379> setrange key2 3 **  #替换指定位置开始的字符串
(integer) 6
127.0.0.1:6379> get key2
"zxc**n"
```

```bash
##setex 键秒值
##setnx 
127.0.0.1:6379> setex key3 10 qwe  #设置key3的值，10秒后过期
OK
127.0.0.1:6379> ttl key3
(integer) -2
127.0.0.1:6379> get key3
(nil)
127.0.0.1:6379> setnx key4 asd  #如果key4不存在，则创建key4
(integer) 1
127.0.0.1:6379> setnx key4 fgh #如果key4存在，创建失败
(integer) 0
127.0.0.1:6379> get key4
"asd"
```

```bash
##mset 批量设置，不带原子性
##mget 批量获取
#msetnx 批量设置，带原子性，即全部key不存在才设置
127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3  #设置多个值
OK
127.0.0.1:6379> keys *
1) "k3"
2) "num"
3) "k1"
4) "k2"
5) "key1"
6) "key4" 
127.0.0.1:6379> mget k1 k2 k3  #获取多个值
1) "v1"
2) "v2"
3) "v3"
127.0.0.1:6379> msetnx k1 v1 k4 v4  #msetnx 是一个原子性的操作，要不一起成功，要不一起失败
(integer) 0
127.0.0.1:6379> get k4
(nil)
```

```bash
set user:{id}:{field} value
```

```bash
#getset 先get后set
127.0.0.1:6379> getset name zhangsan  
(nil)
127.0.0.1:6379> get name
"zhangsan"
127.0.0.1:6379> getset name lisi
"zhangsan"
127.0.0.1:6379> get name
"lisi"
```

###### List（单值多value）

实际上是一个链表，两端可以插入数据。头尾插入效率高

```bash
##Lpush
##Rpush
##Lrange
127.0.0.1:6379> lpush list one #将值插入到列表头部
(integer) 1
127.0.0.1:6379> lpush list two
(integer) 2
127.0.0.1:6379> lpush list three
(integer) 3
127.0.0.1:6379> lrange list 0 -1  #获取list中的字符串
1) "three"
2) "two"
3) "one"
127.0.0.1:6379> lrange list 0 1 #获取list中的[0,1]的值
1) "three"
2) "two"
127.0.0.1:6379> rpush list r-one #将值插入到列表尾部
(integer) 4
127.0.0.1:6379> rpush list r-two
(integer) 5
127.0.0.1:6379> rpush list r-three
(integer) 6
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "two"
3) "one"
4) "r-one"
5) "r-two"
6) "r-three"
```

```bash
##Lppo
##Rpop
127.0.0.1:6379> Lpop list   #移除list的第一个元素
"three"
127.0.0.1:6379> Rpop list   #移除list的最后一个元素
"r-three"
127.0.0.1:6379> lrange list 0 -1
1) "two"
2) "one"
3) "r-one"
4) "r-two"
```

```bash
##Lindex 根据所用小标获得元素（从上到下）
##Llen
127.0.0.1:6379> Lindex list 1 #通过下标获取list的某个值
"one"
127.0.0.1:6379> lrange list 0 -1
1) "two"
2) "one"
3) "r-one"
4) "r-two"
127.0.0.1:6379> Llen list 
(integer) 4
```

```bash
##lrem key 删除N个 值
count > 0: 从头往尾移除值为 value 的元素。
count < 0: 从尾往头移除值为 value 的元素。
count = 0: 移除所有值为 value 的元素
127.0.0.1:6379> lrange list 0 -1
1) "two"
2) "r-one"
3) "r-two"
127.0.0.1:6379> lrem list 0 two #移除list中所有值=two的元素
(integer) 1
127.0.0.1:6379> lrange list 0 -1
1) "r-one"
2) "r-two"
127.0.0.1:6379> lrem list 1 r-two #从头到尾移除第一个值=r-two的元素 
(integer) 1
127.0.0.1:6379> lrange list 0 -1
1) "r-one"
127.0.0.1:6379> lpush list n-two n-three
(integer) 3
127.0.0.1:6379> lrange list 0 -1
1) "n-three"
2) "n-two"
3) "r-one"
127.0.0.1:6379> lrem list -2 n-two #从尾到头移除最后两个值=n-two的元素
(integer) 1
127.0.0.1:6379> lrange list 0 -1
1) "n-three"
2) "r-one"
```

```shell
##Ltrim key 开始index 结束index ，截取指定范围的值后再赋值给key
127.0.0.1:6379> lpush list 1 2 3 4 5 6 7 8
(integer) 8
127.0.0.1:6379> lrange list 0 -1
1) "8"
2) "7"
3) "6"
4) "5"
5) "4"
6) "3"
7) "2"
8) "1"
127.0.0.1:6379> ltrim list 0 4
ok
127.0.0.1:6379> lrange list 0 -1
1) "8"
2) "7"
3) "6"
4) "5"
5) "4"
```

```she
##rpoplpush 源列表 目的列表  ；从源列表中移除最后一个元素插到目的列表的第一个元素的位置
##lset key index value ：把第index个元素的值变成value
##linsert key before/after 值1 值2 ；在key列表中的值1前面/后面插入值2
```

###### Set（单值多value）

无序集合，通过hashTable实现，不允许重复的成员

```shell
##sadd/smembers/sismember
127.0.0.1:6379> sadd set01 1 1 2 2 3 
(integer) 3
127.0.0.1:6379> smembers set01 
1) "1"
2) "2"
3) "3"
127.0.0.1:6379> sismember x 
(integer) 0
127.0.0.1:6379> sismember 1 
(integer) 1
```

```shell
##scard key 获取集合里面的元素个数
##srem key value 删除集合中元素
##srandmember key 某个整数(随机几个数)
##spop key 随机出栈
##smove key1 key2 在key1的某个值移到key2
```

```sh
##数学集合类
##sdiff差集，在第一个set里而不在后面任一个set里面的项
##sinter交集，在set1 同时也在set2 的项
##sunion并集，set1 set2 所有项，去重
```

###### hash

KV模式不变，但V是一个键值对

```shell
##重点
##hset/hget/hmset/hmget/hgetall/hdel
127.0.0.1:6379> hset user id 1
(integer) 1
127.0.0.1:6379> hget user id
"11"
127.0.0.1:6379> hmset customer id 11 name zhangshan age 22
OK
127.0.0.1:6379> hmget customer id name age
1) "11"
2) "zhangshan"
3) "22"
127.0.0.1:6379> hgetall customer
1) "id"
2) "11"
3) "name"
4) "zhangshan"
5) "age"
6) "22"
127.0.0.1:6379> hdel user id
(integer) 1
```

```shell
##hexists key field-key  在key里面的某个值的key
##hlen
127.0.0.1:6379> hexists customer id
(integer) 1
127.0.0.1:6379> hlen customer
(integer) 3
```

```shell
##hkeys/hvals
127.0.0.1:6379> hkeys customer
1) "id"
2) "name"
3) "age"
127.0.0.1:6379> hvals customer
1) "11"
2) "zhangshan"
3) "22"
```

```shell
##hincrby/hincrbyfloat
127.0.0.1:6379> hset customer score 91.5
(integer) 1
127.0.0.1:6379> hincrbyfloat customer score 0.5
"92"
127.0.0.1:6379> hincrby customer score 2
(integer) 94
```

```shell
##hsetnx
127.0.0.1:6379> hsetnx customer email 123@qq.com
(integer) 1
```



###### zset

与set一样是string类型元素的集合，且不允许重复的成员。

==不同的是每个元素都会关联一个double类的分数==

redis根据分数来为集合中的成员进行从小到大的排序。

==zset的成员是唯一的，但分数（score）可以重复==

```shell
##zadd/zrange …… [withscores]
127.0.0.1:6379> zadd zset01 60 v1 70 v2 80 v3 90 v4 100 v5
(integer) 5
127.0.0.1:6379> zrange zset01 0 -1
1) "v1"
2) "v2"
3) "v3"
4) "v4"
5) "v5"
127.0.0.1:6379> zrange zset01 0 -1 withscores
 1) "v1"
 2) "60"
 3) "v2"
 4) "70"
 5) "v3"
 6) "80"
 7) "v4"
 8) "90"
 9) "v5"
10) "100"
```

```shell
##zrangebyscore key 开始score 结束score 
  # withscore
  # (  不包含
  # Limit 作用返回限制 从索引位置x开始，y个 ？
127.0.0.1:6379> zrangebyscore zset01 60 90
1) "v1"
2) "v2"
3) "v3"
4) "v4"
127.0.0.1:6379> zrangebyscore zset01 (60 (90
1) "v2"
2) "v3"
127.0.0.1:6379> zrangebyscore zset01 60 90 limit 2 2
1) "v3"
2) "v4"
127.0.0.1:6379> zrangebyscore zset01 60 90 withscores
1) "v1"
2) "60"
3) "v2"
4) "70"
5) "v3"
6) "80"
7) "v4"
8) "90"
```

```shell
##zrem key 某个score下对应的value值，作用是删除元素
127.0.0.1:6379> zrem zset01 v5
(integer) 1
```

```shell
##zcard/zcount key score区间/zrank key values值 ,作用是获取下标值/zscore key 对应值，获得分数
127.0.0.1:6379> zcard zset01
(integer) 4
127.0.0.1:6379> zcount zset01 60 80
(integer) 3
127.0.0.1:6379> zrank zset01 v4
(integer) 3
127.0.0.1:6379> zscore zset01 v4
"90"
```

```shell
##zrevrank key value 作用是逆序获得下标值
127.0.0.1:6379> zrevrank zset01 v4
(integer) 0
```

```shell
##zrevrange key 开始 结束   逆序输出开始到结束的field-key
127.0.0.1:6379> zrevrange zset01 0 -1
1) "v4"
2) "v3"
3) "v2"
4) "v1"
```

```shell
##在zrevrangebyscore key 结束 开始  逆序输出开始到结束的field-key
127.0.0.1:6379> zrevrangebyscore zset01 90 60
1) "v4"
2) "v3"
3) "v2"
4) "v1"
```

#### redis配置文件

[参数的详细解释](https://blog.csdn.net/sj349781478/article/details/109287749)

1. 它在哪：/opt/redis6.0.6/         #单独拷贝出来，避免修改错误无法恢复到源文件
2. Units 单位

- 配置大小单位，开头定义了一些基本的度量单位，只支持bytes，不支持bit
- 对大小写不敏感

3. INCLUDES

   与struts2配置文件类似，可以通过includes包含，redis.conf可以作为总闸，包含其他

4. **General**

   **daemonize yes #是否以后台进程运行，默认为no **
   pidfile /var/run/redis.pid #如以后台进程运行，则需指定一个pid，默认为/var/run/redis.pid 

   port 6379 #监听端口，默认为6379 

   loglevel notice #日志记slave-serve-stale-data yes：在master服务器挂掉或者同步失败时，从服务器是否继续提供服务。录等级，有4个可选值，debug，verbose（默认值），notice，warning 

   logfile /var/log/redis.log #日志记录方式，默认值为stdout 

   syslog-enabled no  #系统日志是否输出到syslog中

   syslog-ident redis  #指定syslog里的日志标志，默认redis

   syslog-facility local0  #输入syslog日志的设备local0-local7，默认是local0

   databases 16 #可用数据库数，默认值为16，默认数据库为0 ，切换库用seletc

   dir ./ #指定本地数据库存放目录

   

   /**NetWork**/

   bind 127.0.0.1 #绑定主机IP，默认值为127.0.0.1

   tcp-backlog  511 #设置tcp的backlog，backlog其实是一个连接队列，backlog队列总和=未完成三次握手队列 + 已经完成三次握手队列

   在高并发环境下需要一个高backlog值来避免慢客户端连接问题，注意linux内核会将这个值减小到/pro/sys/net/core/somaxconn的值，所以需要确认增大somaxconn和tcp_max_syn_backlog两个值来达到想要的效果

   timeout 0    #当客户端闲置多长时间后关闭连接，如果=0，表示关闭该功能

   tcp-keepalive 300  #单位为秒，如果设置为0，则不会进行keepalive检测，建议设置成300

5. SECURITY

   requirepass foobared  # 设置redis连接密码，如果设置了，连接redis时需要通过auth <password> 提供密码，默认关闭

   ```shell
   [root@localhost bin]# redis-cli
   127.0.0.1:6379> config get requirepass  #查看配置文件的requirepass配置，默认是空
   1) "requirepass"
   2) ""
   127.0.0.1:6379> config set requirepass 123456  #设置密码
   OK
   ##PS: @Sammi 使用 redis6.0.6 需要退出客户端exit重新登录，才能触发密码认证
   127.0.0.1:6379> config get requirepass
   (error) NOAUTH Authentication required.  
   127.0.0.1:6379> auth 123456
   OK 
   ```

6. LIMITS限制

   MaxClients 10000  #默认同一时间最大客户端连接数，如果设置为0，则不作限制。

   Maxmemory  #设置最大内存，达到最大内存设置后，Redis会先尝试清除已到期或即将到期的Key，当此方法处理后，任到达最大内存设置，将无法再进行写入操作。 

   Maxmemory-policy

   <img src="C:\Users\Sammi\Desktop\redis过期策略.png" alt="redis过期策略" style="zoom:38%;" />

   LRU：最近最少使用

   LFU :  最不经常使用

   - volatile-lru ：使用LRU算法移除key，只对设置过期时间的键
   - allkeys-lru : 使用LRU算法移除key
   - volatile-lfu ：使用LFU算法移除key，只对设置过期时间的键
   - allkeys-lfu  ：使用LFU算法移除key
   -  volatile-random ：在过期集合中移除随机的key，只对设置了过期时间的键
   - allkeys-random ： 移除随机的key
   - volatile-ttl ：移除那些TTL值最小的key，即那些最近要过期的key
   - noeviction ：不进行移除。针对写操作，只是返回错误信息（默认）

   Maxmemory-samples #设置样本数量，LRU算法和最小TTL算法都并非是精确的算法，而是估算值，所以你可以设置样本的大小，redis默认会检查这么多个key并选择其中LRU的那个，默认是5

   

#### redis持久化

[官网-持久化介绍](https://redis.io/topics/persistence)

##### rdb ：Redis Database

###### 是什么

在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是Snapshot快照，它恢复时是将快照文件直接读到内存里。

Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。

整个过程中，主进程是不进行任何IO操作的，这就确保了极高的性能

如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加的高效。RDB的缺点是最后一次持久化后的数据可能丢失。

###### Fork

作用是复制一个与当前进程一样的进程。新进程的所有数据（变量、环境变量、程序计数器等），数值都和原进程一致，但是是一个全新的进程，并作为原进程的子进程

###### RDB 保存的是dump.rdb文件

######  配置位置

查看配置文件SNAPSHOTTING快照模块

save 秒钟 写操作次数

快照触发条件：(默认三种，满足其一即可)

<img src="C:\Users\Sammi\Desktop\RDB.png" alt="保存快照默认配置" style="zoom:38%;" />

1分钟内改了1万次，

或5分钟内改了10次，

或15分钟内改了1次

```shell
127.0.0.1:6379> save   #不想等配置文件中触发快照的条件满足，可以直接save迅速备份，只管保存，其他不管，全部阻塞！
OK
```

stop-writes-on-bgsave-error yes  #出错了就停止写入。如果设置为no，表示你不在乎数据不一致或者其他的手段发现和控制

rdbcompression yes   #对于存储在磁盘中的快照，可以设置是否进行压缩存储，如果是的话，LZF算法进行压缩。如果你不想消耗CPU来进行压缩的话，可以设置为关闭此功能。

rdbchecksum yes  #在存储快照后，还可以让redis使用CRC64算法来进行数据校验，但是这样会增加大约10%的性能消耗，如果希望获得最大的性能提升，可以关闭此功能

dbfilename dump.rdb  #默认rdb快照文件保存为 dump.rdb

dir ./     #启动server目录，dump.rdb 保存在这个目录下

###### 如何触发RDB快照

1. 配置文件中默认的快照配置，冷拷贝后重新使用（最好两台，并把dump.rdb文件拷贝到备机，避免主机宕机导致数据无法恢复）

2. 命令save 或者 bgsave

   save：save时只管保存，其他不管，全部阻塞

   bgsave：redis会在后台异步进行快照操作，快照同时还可以响应客户端请求。可以通过lastsave命令获得最后一次成功执行快照的时间

3. 执行flushall命令，也会产生dump.db文件，但里面是空的，没有意义

###### 如何恢复

将备份文件（dump.rdb）移到redis的安装目录并启动服务即可

config get dir 获得目录

###### 优势

适合大规模的数据恢复

对数据完整性和一致性要求不高

###### 劣势

在一定间隔时间做一次备份，所以如果redis意外down掉的话，就会丢失最后一次快照后的所有修改

fork的时候，内存中的数据被克隆了一份，大致2倍的膨胀性需要考虑

###### 如何停止（不建议）

动态所有停止DBRDB保存规则的方法：

```shell
127.0.0.1:6379> config set save ""
```

即如果想禁用RDB持久化的策略，只要不设置任何save指令，或者给save传入一个空字符串参数也可以

###### 小总结

<img src="C:\Users\Sammi\Desktop\rdb总结.png" alt="总结" style="zoom:38%;" />





##### aof：Append Only File

###### 是什么

**以日志的形式来记录每个写操作**，将Redis执行过的所有写指令记录下来（读操作不记录），只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作

###### Aof保存的是appendonly.aof文件

###### 配置位置

查看配置文件APPEND ONLY MODE模块

appendonly yes #默认是no，yes就是打开aof持久化

appendfilename "appendonly.aof"   #默认保存在appendonly.aof文件里面

appendfsync everysec  

#always : 同步持久化，每次发生数据变更会被立即记录到磁盘，性能较差但数据完整性比较好

#everysec : **出厂默认推荐**，异步操作，每秒记录，如果一秒内宕机，有数据丢失

#no: 写后不会有fsync调用，由操作系统自动调度刷磁盘，性能是最好的

###### AOF启动/修复/恢复

**正常恢复**

启动：设置yes  ：修改appendonly no，改为yes

将有数据的aof文件复制一份保存到对应目录，config get dir

恢复：重启redis然后重新加载

**异常恢复**

启动：设置yes 

备份被写坏的aof文件

修复：redis-check-aof --fix appendonly.aof    redis-check-aof  dump.rdb

恢复：重启redis然后重新加载

###### Rewrite

```shell
df -h  #查看磁盘空间
free  #看内存
```

是什么：AOF采用文件追加方式，文件会越来越大为避免出现此情况，新增了重写机制，当AOF文件的大小超过所设定的阈值，Redis就会启动AOF文件的内容压缩，只保留可以恢复数据的最小指令集，可以使用命令bgrewriteaof

重写原理：AOF文件持续增长而过大时，会fork出一条新进程来将文件重写（也是先写临时文件最后再rename），遍历新进程的内存中数据，每条记录有一条的set语句。重写aof文件的操作，并没有读取旧的aof文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照有点类似

触发机制：redis会记录上次重写时aof的大小，默认配置是当aof文件大小是上次rewrite后大小的一倍且文件大于64M时触发

```shell
no-appendfsync-on-rewrite no #重写时是否可以运用appendfsync，用默认no即可，保证数据安全行
auto-aof-rewrite-percentage 100 #设置重写的基准值
auto-aof-rewrite-min-size 64mb #设置重写的基准值
```

###### 优势

每秒同步：appendfsync always 同步持久化 每次发生数据变更会被立即记录到磁盘 性能较差但数据完整性较好

每修改同步：appendfsync everysec  异步操作，每秒记录，如果一秒内宕机，有数据丢失

不同步：appendfsync no 从不同步

###### 劣势

相同数据集的数据而言aof文件要远大于rdb文件，恢复速度慢于rdb

aof运行效率慢于rdb，每秒同步策略效率较好，不同步效率和rdb相同

###### 小总结

<img src="C:\Users\Sammi\Desktop\aof总结.png" alt="aof总结" style="zoom:38%;" />



##### which one

**官网建议：**

RDB持久化方式能够在指定的时间间隔能对你的数据进行快照存储

AOF持久化方式记录每次对服务器写的操作，当服务器重启的时候会重新执行这些命令来恢复原始的数据，aof命令以redis协议追加保存每次写的操作到文件末尾，Redis还能对aof文件进行后台重写，使得aof文件的体积不至于过大

只做缓存：如果你只希望你的数据在服务器运行的时候存在，你也可以不使用任何持久化

同时开启两种持久化方式：

1.在这种情况下，当redis重启的时候会优先载入aof文件来恢复原始的数据，因为在通常情况下aof文件保存的数据集要比rdb文件保存的数据集要完整

2.rdb的数据不实时，同时使用两者服务器重启也只会找aof文件。那要不要只使用aof呢？**作者建议不要**，因为rdb更适合用于备份数据库（aof在不断变化不好备份），快速重启，而且不会有aof可能潜在的bug，留着作为一个万一的手段。



**性能建议：**

<img src="C:\Users\Sammi\Desktop\性能建议.png" alt="性能建议" style="zoom:38%;" />



#### 事务

##### 是什么

可以一次执行多个命令，本质是一组命令的集合，一个事务中的所有命令都会序列化，**按顺序地串行化执行而不会被其他命令插入，不许加塞**

[官网](https://redis.io/topics/transactions)

##### 能干什么

一个队列中，一次性、顺序性、排他性的执行一系列命令

##### 怎么玩

使用[MULTI](https://redis.io/commands/multi)命令输入Redis事务。该命令始终以答复`OK`。此时，用户可以发出多个命令。Redis不会执行这些命令，而是将它们排队。一旦调用[EXEC，](https://redis.io/commands/exec)将执行所有命令。

而是调用[DISCARD](https://redis.io/commands/discard)将刷新事务队列并退出事务。

###### 常用命令：

DISCARD　取消事务，放弃执行事务块内的所有命令

EXEC 　执行所有事务块中的命令

MULTI 　标记一个事务块的开始

UNWATCH 　取消watch命令对**所有key**的监视

WATCH  监控**一个（或多个）key**，如果在事务执行之前这个（或这些）key被其他命令所改动，那么事务将被打断

###### case1:正常执行

```shell
127.0.0.1:6379> multi
OK
127.0.0.1:6379> set k1 v1
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> get k2
QUEUED
127.0.0.1:6379> set k3 v3
QUEUED
127.0.0.1:6379> exec
1) OK
2) OK
3) "v2"
4) OK
```

###### case2:放弃事务

```shell
127.0.0.1:6379> multi
OK
127.0.0.1:6379> set k1 v1
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> set k3 v33
QUEUED
127.0.0.1:6379> discard
OK
127.0.0.1:6379> get k3
"v3"
```

###### case3:全体连坐

```shell
127.0.0.1:6379> multi
OK
127.0.0.1:6379> set k1 v1
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> set k3 v3
QUEUED
127.0.0.1:6379> getset k3
(error) ERR wrong number of arguments for 'getset' command
127.0.0.1:6379> set k4 v4
QUEUED
127.0.0.1:6379> set k5 v5
QUEUED
127.0.0.1:6379> exec
(error) EXECABORT Transaction discarded because of previous errors.
127.0.0.1:6379> get k5
(nil)
```

###### case4：冤头债主

```shell
127.0.0.1:6379> multi
OK
127.0.0.1:6379> incr k1
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> set k2 22
QUEUED
127.0.0.1:6379> set k3 33
QUEUED
127.0.0.1:6379> set k4 44
QUEUED
127.0.0.1:6379> get k4
QUEUED
127.0.0.1:6379> exec
1) (error) ERR value is not an integer or out of range
2) OK
3) OK
4) OK
5) OK
6) "44"
```

###### case 5 : watch监控

**悲观锁/乐观锁/CAS

悲观锁：顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁。传统的关系型数据库里面就用到了很多这种锁机制，比如行锁、表锁等，读锁、写锁等，都是在做操作之前先上锁。

乐观锁：顾名思义，就是很乐观，每次取拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断下在此期间别人有没有取更新这个数据，可以使用版本号等机制。乐观锁适用于多读的应用类型，这样可以提高吞吐量。

*乐观锁策略：提交版本必须大于记录当前版本才能执行更新*

CAS：check and set

初始化信用卡可用余额和欠额

```shell
127.0.0.1:6379> set balance 100
OK
127.0.0.1:6379> set debt 0
OK
```

无加塞篡改，先监控再开启multi，保证两笔金额变动在同一个事务内

```shell
127.0.0.1:6379> watch balance
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379> decrby balance 20
QUEUED
127.0.0.1:6379> incrby debt 20
QUEUED
127.0.0.1:6379> exec
1) (integer) 80
2) (integer) 20
```

有加塞篡改，监听了key，如果key被修改了，后面一个事务的执行失效

```shell
127.0.0.1:6379> watch balance
OK
#####别人操作
127.0.0.1:6379> set balance 500
OK
#####
127.0.0.1:6379> multi
OK
127.0.0.1:6379> decrby balance 20
QUEUED
127.0.0.1:6379> incrby debt 20
QUEUED
127.0.0.1:6379> exec
(nil)
```

unwatch

```shell
127.0.0.1:6379> watch balance
OK
127.0.0.1:6379> set balance 500
OK
127.0.0.1:6379> unwatch
OK
127.0.0.1:6379> watch balance
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379> set balance 20
QUEUED
127.0.0.1:6379> set debt 20
QUEUED
127.0.0.1:6379> exec
1) OK
2) OK
```

一旦执行了exec之前加的监控锁都会被取消掉了

**小结**

watch指令，类似乐观锁，事务提交时，如果key的值已被别的客户端改变，比如某个list已被别的客户端push/pop过了，整个事务队列都不会被执行

通过watch命令在事务执行之前监控了多个keys，倘若在watch之后有任何key的值发生了变化，exec命令执行的事务都将被放弃，同时返回Nullmulti-bulk应答以通知调用者事务执行失败



##### 三阶段

开启：以MULTI开始一个事务

入队：将多个命令入队到事务中，接到这些命令并不会立即执行，而是放到等待执行的事务队列里面

执行：由EXEC命令触发事务

##### 三特点

单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行地过程中，不会被其他客户端发送来的命令请求所打断

没有隔离级别地概念，队列中的命令没有提交之前都不会实际的被执行，因为事务提交前任何指令都不会被实际执行，也就不存在“事务内的查询要看到事务里的更新，在事务外查询不能看到”这个问题

不保证原子性：redis同一个事务中如果有一条命令执行失败，其后的命令仍然会被执行，没有回滚





#### 发布订阅

##### 是什么

进程间的一种消息通信模式：发送者（pub）发送消息，订阅者（sub）接收消息。

订阅/发布消息图

<img src="C:\Users\Sammi\Desktop\订阅关系.png" alt="订阅" style="zoom:38%;" />

<img src="C:\Users\Sammi\Desktop\发送消息.png" alt="发布消息" style="zoom:38%;" />

##### 命令

psubscribe pattern [pattern ……]：订阅一个或多个符合给定模式的频道

pubsub subcommand [argument [argument……]]：查看订阅与发布系统状态

publish channel message：将信息发送到指定的频道

punsubscribe [pattern [pattern ……]]：退订所有给定模式的频道

subscribe channel [channel ……]：订阅给定的一个或多个频道的信息

unsubscribe [channel [channel……]]：指退订给定的频道

##### 案例

先订阅后发布后才能收到消息，

1.一次性订阅多个，subscribe c1 c2 c3

2.消息发布，publish c2 hello-c1

====

3.订阅多个，通配符* ，psubscribe new *

4.收取消息，publish new1 hello-new1



#### 复制（master/slave）

##### 是什么

[replication官网介绍](https://redis.io/topics/replication)

主从复制，主机数据更新后根据配置和策略，自动同步到备机的master/slave机制，master以写为主，slave以读为主

##### 能干什么

###### 读写分离

###### 容灾恢复

##### 怎么玩

1. 配从（库）不配主（库）

2. 从库配置：slaveof 主库IP 主库端

   每次与master断开之后，都需要重新连接，除非你配置进redis.conf 文件

   Info replication

3. 修改配置文件细节操作

   拷贝多个redis.conf 文件

   开启damonize yes

   Pid 文件名字

   指定端口

   Log 文件名字

   Dump.rdb 名字、AppendOnly.aof名字

4. 常用三招

   *一主二仆*

   ​	Init 

   ​	一个Master 两个Slave

   ```shell
   ##不小心把master 变成了 自己的slave
   127.0.0.1:6379> slaveof 127.0.0.1 6379
   OK
   127.0.0.1:6379> set k4 v4
   (error) READONLY You can't write against a read only replica.
   ##解决1
   127.0.0.1:6379> slaveof no one
   OK
   ##解决2
   将redis.conf配置文件里面的slave-read-only的值修改为no
   ```

   ​	日志查看

   ​	主从问题演示

   *薪火相传*

   上一个Slave可以是下一个slave的Master，Slave同样可以接收其他slaves的连接和同步请求，那么该slave作为了链条中下一个的master，可以有效减轻master的写压力

   中途变更转向：会清除之前的数据，重新建立拷贝最新的

   slaveof新主库IP 新主库端口

   

   *反客为主*

   slaveof no one 使当前数据库停止与其他数据库的同步，转成主数据库

   

##### 复制原理

slave启动成功连接到master会发送一个sync命令

master接到命令启动后台的存盘进程，同时收集所有接收到的用于修改数据集命令，在后台进程执行完毕之后，master将传送整个数据文件到slave，以完成一次完全同步

全量复制：而slave服务在接收到数据库文件数据后，将其存盘并加载到内存中

增量复制：master继续将新的所有收集到的修改命令依次传给slave，完成同步

但是只要是重新连接master，一次完全同步（全量复制）将被自动执行

##### 哨兵模式（sentinel）

是什么：反客为主的自动版，能够后台监控主机是否故障，如果故障了，根据投票数自动将从库转换为主库

怎么玩（步骤）：

1. 调整结构，6379带着80、81

2. 自定义的/myredis目录下新建sentinel.conf文件，名字绝不能错

3. 设置哨兵，填写内容

   sentinel monitor 被监控数据库名字（自己起名字）127.0.0.1 6379 1

   上面最后一个数字1，表示主机挂掉后slave投票看让谁接替成为主机，得票数多少后成为主机

4. 启动哨兵 

   Redis-sentinel /myredis/sentinel.conf  (请根据实际目录写)

   原有的master挂了，投票新选，一人一票可能存在打平的情况，一直投票直到选出新的master。旧的master重启回来，会以slave的方式重新加入集群中。

一组sentinel能同时监控多个master

##### 复制的缺点

复制的延时：由于所有的写操作都是现在master上操作，然后同步更新到slave上，所以从master同步到slave机器有一定的延迟，当系统非常繁忙时，延迟问题会更加严重，slave机器数量的增加也会使这个问题更加严重。



#### Jedis

 <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.7.1</version><!--版本号可根据实际情况填写-->
         </dependency>
         <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.3.2</version><!--版本号可根据实际情况填写-->
         </dependency>

##### 单例模式

###### 懒汉式

public class Singleton {
		 private static Singleton instance=null;
 		private Singleton(){}
		 static Singleton getInstance() {
		 if(instance==null) {
		 instance=new Singleton();
		 }
		 return instance;
		}
		}

###### 饿汉式

public class Singleton {
		 private static Singleton instance=new Singleton();
 		private Singleton(){}
		 static Singleton getInstance() {
		 return instance;
		}
		}





















