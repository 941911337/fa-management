# fa-management
# 资产管理系统
本项目可作为开发脚手架进行使用,去除掉部分业务代码即可

### 本项目使用

* springboot (MVC框架)

* mybatis-plus (持久层框架)

* mysql (数据库)

* redis (缓存)

* nacos (配置中心)

* bucket4j (令牌桶进行限流 也可以使用nginx进行限流)

* shiro +jwt (作为token生成 开发)

具体依赖搭建自行搭建 使用 仅提供源码
#版本说明
* 1.0.0 版本初始化构建 使用mybatis

* 1.1.0 版本使用mybatis-plus

* 1.2.0 版本将代码构建为三个模块 common (共用模块) business (业务模块) web(访问控制模块) 

* 1.3.0 版本将代码构建为四个模块 common (共用模块) business (业务模块) web(访问控制模块) log(日志模块) 建议日志独立数据库 防止占用fa数据库的io
数据放在本地队列中 100条进行批量入库 后续版本考虑加入mq  增加日志模块开关 进行解耦 日志模块改为非必须
