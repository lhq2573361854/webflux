MYSQL 高级(300w数据性能)
====
install
----
配置文件
====
    my.cnf 位于 /etc/my.cnf

数据文件
----
    1. frm 结构
    2. myd 数据
    3. myi 索引

日志
----
    log-bin 主从复制

log-error 记录错误信息
----
    索引优化

索引优化
---
    1. 执行、等待时间长和性能下降sql慢
        查询写的烂
        索引失效
            单值索引 create index index_table_filed on table(filed)
            复合索引create index index_table_filed on table(filed,filed)
        关联查询join太多了
        复习调优以及各个参数设置
    2. join 
        请见mysql_1.jpg
        join操作请见菜鸟教程
    3. 索引Index(影响到查找where 排序 group by)
        优点： 索引是数据结构 排好序的快速查找的数据结构 
               降低IO成本、降低CPU的消耗
        缺点： 索引是一张表保存了主键和索引字段只想索引表的记录，所以也是要占空间的
               insert update 会重建索引表
        结构： BTree
              原理： 二分多路查找 类似于快排
              Hash
              full-text
              R-Tree
        那些适合创建索引：主键自动
                        频繁使用
                        查询关联
                        频繁更新不要创建 
                        where用到的
                        单/组合 who？ 高并发趋向于组合
                        查询排序中字段
                        统计分组
        那些不适合创建索引：  表记录太少
                            经常修改的
                            高度重复的
        
        性能分析： 
            mysql Query Optomizer
            常见瓶颈：IO
                     cpu
                     硬件
            Explain
                explain + SQl
                字段
                    id：表读取顺序
                        1. 相同从上到下执行
                        2. 不同越大越先加载 
                        3. id相同不同，同时存在 derived+id 衍生表
                    select_type: 
                        1 simple
                        2 primary
                        3 subquery
                        4 derived
                        5 union
                        6 union result
                    type:
                        system 表只有一行记录（等于系统表），这是const类型的特列，平时不会出现，这个也可以忽略不计
                        const 表示通过索引一次就找到了，const用于比较primary key 或者unique索引。因为只匹配一行数据，所以很快。如将主键置于where列表中，MySQL就能将该查询转换为一个常量。 
                        eq_ref 唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键或唯一索引扫描 （一对一）
                        ref 非唯一性索引扫描，返回匹配某个单独值的所有行，本质上也是一种索引访问，它返回所有匹配某个单独值的行，然而，它可能会找到多个符合条件的行，所以他应该属于查找和扫描的混合体。 
                        range 只检索给定范围的行，使用一个索引来选择行，key列显示使用了哪个索引，一般就是在你的where语句中出现between、< 、>、in等的查询，这种范围扫描索引比全表扫描要好，因为它只需要开始于索引的某一点，而结束于另一点，不用扫描全部索引。 
                        index   Full Index Scan，Index与All区别为index类型只遍历索引树。这通常比ALL快，因为索引文件通常比数据文件小。（也就是说虽然all和Index都是读全表，但index是从索引中读取的，而all是从硬盘读取的） 
                        all   Full Table Scan 将遍历全表以找到匹配的行 
                    possible_key 可能用到的index
                    key 实际用到index
                    key_len 使用的字节数的大小和精度相反
                    ref 索引使用的字段
                    rows 优化器查询的次数
                    Extra
                        using filesort： 
                        using tempoary:
                        using index;
                    case： 优化案例 1 针对范围的不能使用index 
                                   2 针对join的问题在相反方向建立索引 left/right join 连接的表 可以变换位置 
                                   3 多表中 使用经常的建立索引 小表驱动大表
                IN适合于外表大而内表小的情况；EXISTS适合于外表小而内表大的情况。https://www.cnblogs.com/clarke157/p/7912871.html
                https://www.cnblogs.com/lyjun/p/11371851.html#%E6%A6%82%E8%BF%B0
                翻译过来就是
                提取表是用在 from 语句中的。             
                子查询是用在 where语句中的，但是也可以用在从一张表中查询，然后插入到另一张表，就像上面展示的那个例子一样
               
                我们先讨论IN和EXISTS。
                in 在里面需要遍历 而存在不需要遍历只需要查找
                select * from t1 where exists ( select null from t2 where y = x )
                可以理解为：
                for x in ( select * from t1 )
                loop
                  if ( exists ( select null from t2 where y = x.x )
                  then
                     OUTPUT THE RECORD!
                  end if
                end loop 
                
                
                select * from t1 where x in ( select y from t2 )
                事实上可以理解为：
                select *
                    from t1, ( select distinct y from t2 ) t2
                where t1.x = t2.y;
                
                
                
                
                
                
                
                
                
                
                
                
                
                