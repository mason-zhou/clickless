## Activiti 工作流模块

## 内置数据库表说明
```
通用数据表
act_ge_bytearray 存放资源文件(图片,xml)
act_ge_property 属性数据表(版本号)

历史数据表
act_hi_actinst 历史节点表
act_hi_attachment 历史附件表
act_hi_comment 历史意见表
act_hi_detail 历史详情表，提供历史变量查询
act_hi_identitylink 历史流程人员表，每个节点对应的处理人信息
act_hi_procinst 历史流程实例表
act_hi_taskinst 历史任务实例表
act_hi_varinst 历史变量表

流程定义数据表
act_re_deployment 部署信息表
act_re_model 流程设计模型基本信息表
act_re_procdef 流程定义数据表

流程运行时数据表
act_ru_deadletter_job 作业死亡信息表（作业超过指定次数，就会写到这张表）
act_ru_event_subscr 时间监听信息表
act_ru_execution 运行时流程执行实例表
act_ru_identitylink 运行时流程办理人员表
act_ru_integration 运行时积分表
act_ru_job 定时异步任务数据表
act_ru_suspended_job 运行时作业暂停表
act_ru_task 运行时任务节点表
act_ru_timer_job 运行时定时器作业表
act_ru_variable 运行时的流程变量数据表

其他
act_evt_log 通用事件日志记录表
act_procdef_info 流程定义动态变量信息表

```

## 常见错误解决
Activiti 7的M4以上版本，部署流程定义时，可能报错如下：

报错:`Unknown column 'VERSION_' in 'field list'`

解决：因为ACT_RE_DEPLOYMENT表缺少`VERSION_`和`PROJECT_RELEASE_VERSION_`字段，执行以下语句添加：
```sql
ALTER TABLE ACT_RE_DEPLOYMENT ADD COLUMN VERSION_ VARCHAR(255);

ALTER TABLE ACT_RE_DEPLOYMENT ADD COLUMN PROJECT_RELEASE_VERSION_ VARCHAR(255);
```
