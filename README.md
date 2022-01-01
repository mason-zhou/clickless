# clickless 

> Click Less, Do More !

#### 简介
* clickless是一个快速开发脚手架。
* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。
* 数据库使用PostgreSQL 14.0
* 持久层框架使用MyBatis Plus
* 多数据源管理使用dynamic-datasource-spring-boot-starter

#### 项目结构
```
# 后端
clickless-admin-server 后端主服务
clickless-common 公共依赖包
clickless-framework 项目框架层
clickless-generator 代码生成器
clickless-quartz 定时器
clickless-system 项目系统层
clickless-sample 示例代码

# 前端
clickless-ui 前端

# 其他
doc 项目文档
sql 数据库脚本
bin 服务器启动脚本
```

#### 项目开启

##### 1.安装数据库、中间件

| 名称       | 用途       | 版本  |
| ---------- | ---------- | ----- |
| PostgreSQL | 数据库     | 14.0  |
| Redis      | 缓存中间件 | 5.0.8 |

##### 2.初始化数据库
建立一个空白数据库，如:clickless 

依次运行以下脚本
```shell
sql/postgresql/1.sys.sql  # 系统核心表
sql/postgresql/2.quartz.sql # 定时任务表
sql/postgresql/3.sample-data.sql # 示例模块相关表(可选)
```

##### 3.修改配置
后端：application-dev.yml配置文件，检查数据源及Redis配置

前端：vue.config.js配置文件，检查后端接口访问路径

##### 4.启动项目
```shell
# 启动后端
进入后端工程目录 clickless-admin-server
运行 AdminServerApplication

# 启动前端
# 进入前端工程目录
cd clickless-ui

# 安装依赖(首次启动时运行)
npm install

# 可以通过如下方式解决 npm 下载速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 启动前端服务
npm run dev
```



