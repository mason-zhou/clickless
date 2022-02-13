-- ----------------------------
-- 工作流引擎 相关表
-- ----------------------------

-- ----------------------------
-- 1.流程业务状态实体表(通用)
-- ----------------------------
DROP TABLE IF EXISTS wkfl_business_status;
CREATE TABLE wkfl_business_status
(
    business_key        VARCHAR(60) NOT NULL
        CONSTRAINT pk_wkfl_business_status PRIMARY KEY,
    process_instance_id VARCHAR(60) DEFAULT NULL,
    status              int2        DEFAULT NULL,
    create_time         timestamptz DEFAULT CURRENT_TIMESTAMP,
    update_time         timestamptz DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE wkfl_business_status IS '流程业务状态实体表(通用)';
COMMENT ON COLUMN wkfl_business_status.business_key IS '业务ID';
COMMENT ON COLUMN wkfl_business_status.process_instance_id IS '流程实例ID';
COMMENT ON COLUMN wkfl_business_status.status IS '状态';
COMMENT ON COLUMN wkfl_business_status.create_time IS '创建时间';
COMMENT ON COLUMN wkfl_business_status.update_time IS '更新时间';

CREATE TRIGGER update_time_trigger
    BEFORE UPDATE
    ON wkfl_business_status
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();

-- ----------------------------
-- 2.流程定义配置表(通用)
-- ----------------------------
DROP TABLE IF EXISTS wkfl_process_config;
CREATE TABLE wkfl_process_config
(
    id             VARCHAR(60) NOT NULL
        CONSTRAINT pk_wkfl_process_config PRIMARY KEY,
    process_key    VARCHAR(60)  DEFAULT NULL,
    business_route VARCHAR(100) DEFAULT NULL,
    form_name      VARCHAR(100) DEFAULT NULL,
    create_time    timestamptz  DEFAULT CURRENT_TIMESTAMP,
    update_time    timestamptz  DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE wkfl_process_config IS '流程定义配置表(通用)';
COMMENT ON COLUMN wkfl_process_config.id IS '主键id';
COMMENT ON COLUMN wkfl_process_config.process_key IS '流程定义KEY';
COMMENT ON COLUMN wkfl_process_config.business_route IS '业务申请路由名';
COMMENT ON COLUMN wkfl_process_config.form_name IS '关联表单组件名';
COMMENT ON COLUMN wkfl_process_config.create_time IS '创建时间';
COMMENT ON COLUMN wkfl_process_config.update_time IS '更新时间';

CREATE TRIGGER update_time_trigger
    BEFORE UPDATE
    ON wkfl_process_config
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();

-- ----------------------------
-- 3.请假业务表
-- ----------------------------
DROP TABLE IF EXISTS wkfl_biz_leave;
CREATE TABLE wkfl_biz_leave
(
    id            VARCHAR(255)
        CONSTRAINT pk_wkfl_biz_leave PRIMARY KEY,
    username      VARCHAR(40)      DEFAULT NULL,
    duration      DOUBLE PRECISION DEFAULT NULL,
    principal     VARCHAR(50)      DEFAULT NULL,
    contact_phone VARCHAR(50)      DEFAULT NULL,
    leave_type    int2             DEFAULT NULL,
    title         VARCHAR(255)     DEFAULT NULL,
    leave_reason  VARCHAR(1000)    DEFAULT NULL,
    start_date    timestamptz      DEFAULT NULL,
    end_date      timestamptz      DEFAULT NULL,
    create_time   timestamptz      DEFAULT CURRENT_TIMESTAMP,
    update_time   timestamptz      DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE wkfl_biz_leave IS '请假业务表';
COMMENT ON COLUMN wkfl_biz_leave.id IS '主键ID';
COMMENT ON COLUMN wkfl_biz_leave.username IS '主键ID';
COMMENT ON COLUMN wkfl_biz_leave.duration IS '主键ID';
COMMENT ON COLUMN wkfl_biz_leave.principal IS '工作委托人';
COMMENT ON COLUMN wkfl_biz_leave.contact_phone IS '联系电话';
COMMENT ON COLUMN wkfl_biz_leave.leave_type IS '请假类型：1病假，2事假，3年假，4婚假，5产假，6丧假，7探亲，8调休，9其他';
COMMENT ON COLUMN wkfl_biz_leave.title IS '标题';
COMMENT ON COLUMN wkfl_biz_leave.leave_reason IS '请假原因';
COMMENT ON COLUMN wkfl_biz_leave.start_date IS '请假开始时间';
COMMENT ON COLUMN wkfl_biz_leave.end_date IS '请假结束时间';
COMMENT ON COLUMN wkfl_biz_leave.create_time IS '创建时间';
COMMENT ON COLUMN wkfl_biz_leave.update_time IS '更新时间';

CREATE TRIGGER update_time_trigger
    BEFORE UPDATE
    ON wkfl_biz_leave
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();

-- ----------------------------
-- 4.借款业务表
-- ----------------------------
DROP TABLE IF EXISTS wkfl_biz_loan;
CREATE TABLE wkfl_biz_loan
(
    id          VARCHAR(255)
        CONSTRAINT pk_wkfl_biz_loan PRIMARY KEY,
    user_id     VARCHAR(40)      DEFAULT '',
    nick_name   VARCHAR(40)      DEFAULT NULL,
    money       DOUBLE PRECISION DEFAULT NULL,
    purpose     VARCHAR(1000)    DEFAULT NULL,
    remark      VARCHAR(1000)    DEFAULT NULL,
    loan_date   timestamptz      DEFAULT NULL,
    create_time timestamptz      DEFAULT CURRENT_TIMESTAMP,
    update_time timestamptz      DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE wkfl_biz_loan IS '借款业务表';
COMMENT ON COLUMN wkfl_biz_loan.id IS '主键ID';
COMMENT ON COLUMN wkfl_biz_loan.user_id IS '申请人ID';
COMMENT ON COLUMN wkfl_biz_loan.nick_name IS '申请人昵称';
COMMENT ON COLUMN wkfl_biz_loan.money IS '借款金额';
COMMENT ON COLUMN wkfl_biz_loan.purpose IS '借款用途';
COMMENT ON COLUMN wkfl_biz_loan.remark IS '备注';
COMMENT ON COLUMN wkfl_biz_loan.loan_date IS '借款日期';
COMMENT ON COLUMN wkfl_biz_loan.create_time IS '创建时间';
COMMENT ON COLUMN wkfl_biz_loan.update_time IS '更新时间';

CREATE TRIGGER update_time_trigger
    BEFORE UPDATE
    ON wkfl_biz_loan
    FOR EACH ROW
EXECUTE PROCEDURE update_timestamp();