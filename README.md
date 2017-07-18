# 一个简单的小组周报填写工具
## 技术点
1. 前端BUI,后端SpringBoot，mybatis
2. JSON:fastjson; mail:javamail; excel:poi; task:Scheduled;
## 功能
### 单点登录
需要自己接入自己公司的单点登录。在Application中配置FilterRegistrationBean
### 周报填写
1. index.html是默认首页，填写自己本周的工作量
2. 每人只能填写和修改自己的工作量，但是可以查看别人的记录
### 短信提醒
TaskService中有定时任务配置，包括短信提醒，报表发送等功能
### 报表管理及发送
1. admin.html是报表管理页面，任何人可以查看和导出报表
2. 定时器也会定时将填写报表导出并发送给管理员。收件人配置在TaskService中

## 使用配置
1. 数据库名:work_report，如果要自定义，在Application中修改DataSource的URL即可
2. 数据库表初始化脚本在：.\workReport\src\test\script\init.sql
3. 需要手动修改的地方：数据库配置，短信发送配置，邮件发送配置，单点接入配置
4. 权限只限制“填写和修改只能是登录者本人”，其他权限没有做，需要的可以接入本公司自己的权限系统，也可以自己扩充