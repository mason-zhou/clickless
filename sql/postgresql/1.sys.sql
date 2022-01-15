-- ----------------------------
-- 1、部门表
-- ----------------------------
DROP TABLE IF EXISTS sys_dept;

CREATE TABLE sys_dept
(
    dept_id     BIGSERIAL   NOT NULL,
    parent_id   BIGINT      NULL DEFAULT 0,
    ancestors   VARCHAR(50) NULL,
    dept_name   VARCHAR(30) NULL DEFAULT '',
    order_num   BIGINT      NULL DEFAULT 0,
    leader      VARCHAR(20) NULL DEFAULT '',
    phone       VARCHAR(20) NULL DEFAULT '',
    email       VARCHAR(20) NULL DEFAULT '',
    status      CHAR(1)     NULL DEFAULT '0',
    del_flag    CHAR(1)     NULL DEFAULT '0',
    create_by   VARCHAR(64) NULL DEFAULT '',
    create_time timestamptz NULL DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64) NULL DEFAULT '',
    update_time timestamptz NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_sys_dept PRIMARY KEY (dept_id)
);
ALTER SEQUENCE IF EXISTS sys_dept_dept_id_seq RESTART WITH 200 CACHE 20;

COMMENT
    ON TABLE sys_dept IS '部门表';
COMMENT
    ON COLUMN sys_dept.dept_id IS '部门id';
COMMENT
    ON COLUMN sys_dept.parent_id IS '父部门id';
COMMENT
    ON COLUMN sys_dept.ancestors IS '祖级列表';
COMMENT
    ON COLUMN sys_dept.dept_name IS '部门名称';
COMMENT
    ON COLUMN sys_dept.order_num IS '显示顺序';
COMMENT
    ON COLUMN sys_dept.leader IS '负责人';
COMMENT
    ON COLUMN sys_dept.phone IS '联系电话';
COMMENT
    ON COLUMN sys_dept.email IS '邮箱';
COMMENT
    ON COLUMN sys_dept.status IS '部门状态（0正常 1停用）';
COMMENT
    ON COLUMN sys_dept.del_flag IS '删除标志（0代表存在 2代表删除）';
COMMENT
    ON COLUMN sys_dept.create_by IS '创建者';
COMMENT
    ON COLUMN sys_dept.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_dept.update_by IS '更新者';
COMMENT
    ON COLUMN sys_dept.update_time IS '更新时间';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
INSERT INTO sys_dept
VALUES (100, 0, '0', '总公司', 0, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (101, 100, '0,100', '深圳总公司', 1, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (102, 100, '0,100', '长沙分公司', 2, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (103, 101, '0,100,101', '研发部门', 1, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (104, 101, '0,100,101', '市场部门', 2, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (105, 101, '0,100,101', '测试部门', 3, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (106, 101, '0,100,101', '财务部门', 4, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (107, 101, '0,100,101', '运维部门', 5, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (108, 102, '0,100,102', '市场部门', 1, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);
INSERT INTO sys_dept
VALUES (109, 102, '0,100,102', '财务部门', 2, 'clickless', '15888888888', 'ry@qq.com', '0', '0', 'admin', NOW(), '', NULL);


-- ----------------------------
-- 2、用户信息表
-- ----------------------------
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
    user_id     BIGSERIAL    NOT NULL,
    dept_id     BIGINT       NULL DEFAULT NULL,
    user_name   VARCHAR(30)  NULL DEFAULT '',
    nick_name   VARCHAR(30)  NULL DEFAULT '',
    user_type   VARCHAR(2)   NULL DEFAULT '00',
    email       VARCHAR(100) NULL DEFAULT '',
    phonenumber VARCHAR(20)  NULL DEFAULT '',
    sex         CHAR(1)      NULL DEFAULT '0',
    avatar      VARCHAR(100) NULL,
    password    VARCHAR(100) NULL DEFAULT '',
    status      CHAR(1)      NULL DEFAULT '0',
    del_flag    CHAR(1)      NULL DEFAULT '0',
    login_ip    VARCHAR(50)  NULL DEFAULT '',
    login_date  timestamptz  NULL,
    create_by   VARCHAR(64)  NULL DEFAULT '',
    create_time timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  NULL DEFAULT '',
    update_time timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    remark      VARCHAR(500) NULL,
    CONSTRAINT pk_sys_user PRIMARY KEY (user_id)
);

ALTER SEQUENCE IF EXISTS sys_user_user_id_seq RESTART WITH 100 CACHE 20;

COMMENT
    ON TABLE sys_user IS '用户信息表';
COMMENT
    ON COLUMN sys_user.user_id IS '用户ID';
COMMENT
    ON COLUMN sys_user.dept_id IS '部门ID';
COMMENT
    ON COLUMN sys_user.user_name IS '用户账号';
COMMENT
    ON COLUMN sys_user.nick_name IS '用户昵称';
COMMENT
    ON COLUMN sys_user.user_type IS '用户类型（00系统用户）';
COMMENT
    ON COLUMN sys_user.email IS '用户邮箱';
COMMENT
    ON COLUMN sys_user.phonenumber IS '手机号码';
COMMENT
    ON COLUMN sys_user.sex IS '用户性别（0男 1女 2未知）';
COMMENT
    ON COLUMN sys_user.avatar IS '头像路径';
COMMENT
    ON COLUMN sys_user.password IS '密码';
COMMENT
    ON COLUMN sys_user.status IS '帐号状态（0正常 1停用）';
COMMENT
    ON COLUMN sys_user.del_flag IS '删除标志（0代表存在 2代表删除）';
COMMENT
    ON COLUMN sys_user.login_ip IS '最后登录IP';
COMMENT
    ON COLUMN sys_user.login_date IS '最后登录时间';
COMMENT
    ON COLUMN sys_user.create_by IS '创建者';
COMMENT
    ON COLUMN sys_user.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_user.update_by IS '更新者';
COMMENT
    ON COLUMN sys_user.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_user.remark IS '备注';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
INSERT INTO sys_user
VALUES (1, 103, 'admin', '张三', '00', 'user1@clickless.com', '15888888888', '1', '',
        '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', NOW(), 'admin', NOW(),
        '', NULL, '管理员');
INSERT INTO sys_user
VALUES (2, 105, 'clickless', '李四', '00', 'user2@clickless.com', '15666666666', '1', '',
        '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', NOW(), 'admin', NOW(),
        '', NULL, '测试员');

-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
DROP TABLE IF EXISTS sys_post;
CREATE TABLE sys_post
(
    post_id     BIGSERIAL    NOT NULL,
    post_code   VARCHAR(64)  NOT NULL,
    post_name   VARCHAR(100) NOT NULL,
    post_sort   BIGINT       NOT NULL,
    status      CHAR(1)      NOT NULL,
    create_by   VARCHAR(64)  NULL DEFAULT '',
    create_time timestamptz       DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  NULL DEFAULT '',
    update_time timestamptz       DEFAULT CURRENT_TIMESTAMP,
    remark      VARCHAR(500) NULL DEFAULT '',
    CONSTRAINT pk_sys_post PRIMARY KEY (post_id)
);
ALTER SEQUENCE IF EXISTS sys_post_post_id_seq RESTART WITH 10 CACHE 20;

COMMENT
    ON TABLE sys_post IS '岗位信息表';
COMMENT
    ON COLUMN sys_post.post_id IS '岗位ID';
COMMENT
    ON COLUMN sys_post.post_code IS '岗位编码';
COMMENT
    ON COLUMN sys_post.post_name IS '岗位名称';
COMMENT
    ON COLUMN sys_post.post_sort IS '显示顺序';
COMMENT
    ON COLUMN sys_post.status IS '状态（0正常 1停用）';
COMMENT
    ON COLUMN sys_post.create_by IS '创建者';
COMMENT
    ON COLUMN sys_post.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_post.update_by IS '更新者';
COMMENT
    ON COLUMN sys_post.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_post.remark IS '备注';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
INSERT INTO sys_post
VALUES (1, 'ceo', '董事长', 1, '0', 'admin', NOW(), '', NOW(), '');
INSERT INTO sys_post
VALUES (2, 'se', '项目经理', 2, '0', 'admin', NOW(), '', NOW(), '');
INSERT INTO sys_post
VALUES (3, 'hr', '人力资源', 3, '0', 'admin', NOW(), '', NOW(), '');
INSERT INTO sys_post
VALUES (4, 'user', '普通员工', 4, '0', 'admin', NOW(), '', NOW(), '');

-- ----------------------------
-- 4、角色信息表
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role
(
    role_id             BIGSERIAL    NOT NULL,
    role_name           VARCHAR(30)  NOT NULL,
    role_key            VARCHAR(100) NOT NULL,
    role_sort           BIGINT       NULL,
    data_scope          CHAR(1)      NULL,
    menu_check_strictly BOOLEAN           DEFAULT TRUE,
    dept_check_strictly BOOLEAN           DEFAULT TRUE,
    status              CHAR(1)      NULL DEFAULT '0',
    del_flag            CHAR(1)      NULL DEFAULT '0',
    create_by           VARCHAR(64)  NULL DEFAULT '',
    create_time         timestamptz       DEFAULT CURRENT_TIMESTAMP,
    update_by           VARCHAR(64)  NULL DEFAULT '',
    update_time         timestamptz       DEFAULT CURRENT_TIMESTAMP,
    remark              VARCHAR(500) NULL DEFAULT '',
    CONSTRAINT pk_sys_role PRIMARY KEY (role_id)
);
ALTER SEQUENCE IF EXISTS sys_role_role_id_seq RESTART WITH 100 CACHE 20;
COMMENT
    ON TABLE sys_role IS '角色信息表';
COMMENT
    ON COLUMN sys_role.role_id IS '角色ID';
COMMENT
    ON COLUMN sys_role.role_name IS '角色名称';
COMMENT
    ON COLUMN sys_role.role_key IS '角色权限字符串';
COMMENT
    ON COLUMN sys_role.role_sort IS '显示顺序';
COMMENT
    ON COLUMN sys_role.data_scope IS '1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5:仅本人数据权限';
COMMENT
    ON COLUMN sys_role.menu_check_strictly IS '菜单树选择项是否关联显示';
COMMENT
    ON COLUMN sys_role.dept_check_strictly IS '部门树选择项是否关联显示';
COMMENT
    ON COLUMN sys_role.status IS '角色状态（0正常 1停用）';
COMMENT
    ON COLUMN sys_role.del_flag IS '删除标志（0代表存在 2代表删除）';
COMMENT
    ON COLUMN sys_role.create_by IS '创建者';
COMMENT
    ON COLUMN sys_role.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_role.update_by IS '更新者';
COMMENT
    ON COLUMN sys_role.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_role.remark IS '备注';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------

INSERT INTO sys_role
VALUES ('1', '超级管理员', 'admin', 1, 1, TRUE, TRUE, '0', '0', 'admin', NOW(), '', NOW(), '超级管理员');
INSERT INTO sys_role
VALUES ('2', '普通角色', 'common', 2, 2, TRUE, TRUE, '0', '0', 'admin', NOW(), '', NOW(), '普通角色');

-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu
(
    menu_id     BIGSERIAL    NOT NULL,
    menu_name   VARCHAR(50)  NOT NULL,
    parent_id   BIGINT       NULL DEFAULT 0,
    order_num   BIGINT       NULL DEFAULT 0,
    path        VARCHAR(200) NULL DEFAULT '',
    component   VARCHAR(255) NULL DEFAULT NULL,
    query       VARCHAR(255) NULL DEFAULT NULL,
    is_frame    CHAR(1)      NULL DEFAULT '1',
    is_cache    CHAR(1)      NULL DEFAULT '0',
    menu_type   CHAR(1)      NULL DEFAULT '',
    visible     CHAR(1)      NULL DEFAULT '0',
    status      CHAR(1)      NULL DEFAULT '0',
    perms       VARCHAR(100) NULL DEFAULT '',
    icon        VARCHAR(100) NULL DEFAULT '#',
    create_by   VARCHAR(64)  NULL DEFAULT '',
    create_time timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  NULL DEFAULT '',
    update_time timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    remark      VARCHAR(500) NULL DEFAULT '',
    CONSTRAINT pk_sys_menu PRIMARY KEY (menu_id)
);
ALTER SEQUENCE IF EXISTS sys_menu_menu_id_seq RESTART WITH 2000 CACHE 20;

COMMENT
    ON TABLE sys_menu IS '菜单权限表';
COMMENT
    ON COLUMN sys_menu.menu_id IS '菜单ID';
COMMENT
    ON COLUMN sys_menu.menu_name IS '菜单名称';
COMMENT
    ON COLUMN sys_menu.parent_id IS '父菜单ID';
COMMENT
    ON COLUMN sys_menu.order_num IS '显示顺序';
COMMENT
    ON COLUMN sys_menu.path IS '路由地址';
COMMENT
    ON COLUMN sys_menu.component IS '组件路径';
COMMENT
    ON COLUMN sys_menu.query IS '路由参数';
COMMENT
    ON COLUMN sys_menu.is_frame IS '是否为外链（0是 1否）';
COMMENT
    ON COLUMN sys_menu.is_cache IS '是否缓存（0缓存 1不缓存）';
COMMENT
    ON COLUMN sys_menu.menu_type IS '菜单类型（M目录 C菜单 F按钮）';
COMMENT
    ON COLUMN sys_menu.visible IS '菜单状态（0显示 1隐藏）';
COMMENT
    ON COLUMN sys_menu.status IS '菜单状态（0正常 1停用）';
COMMENT
    ON COLUMN sys_menu.perms IS '权限标识';
COMMENT
    ON COLUMN sys_menu.icon IS '菜单图标';
COMMENT
    ON COLUMN sys_menu.create_by IS '创建者';
COMMENT
    ON COLUMN sys_menu.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_menu.update_by IS '更新者';
COMMENT
    ON COLUMN sys_menu.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_menu.remark IS '备注';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
INSERT INTO sys_menu
VALUES ('1', '系统管理', '0', '1', 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', NOW(), '', NULL,
        '系统管理目录');
INSERT INTO sys_menu
VALUES ('2', '系统监控', '0', '2', 'monitor', NULL, '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', NOW(), '', NULL,
        '系统监控目录');
INSERT INTO sys_menu
VALUES ('3', '系统工具', '0', '3', 'tool', NULL, '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', NOW(), '', NULL, '系统工具目录');
INSERT INTO sys_menu
VALUES ('4', '示例模块', '0', '4', 'sample', NULL, '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', NOW(), '', NULL, '示例模块目录');
INSERT INTO sys_menu
VALUES ('5', 'clickless官网', '0', '5', 'https://github.com/mason-zhou/clickless', NULL, '', 0, 0, 'M', '0', '0', '', 'guide', 'admin', NOW(), '',
        NULL, 'clickless官网地址');
-- 二级菜单
INSERT INTO sys_menu
VALUES ('100', '用户管理', '1', '1', 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user',
        'admin', NOW(), '', NULL, '用户管理菜单');
INSERT INTO sys_menu
VALUES ('101', '角色管理', '1', '2', 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples',
        'admin', NOW(), '', NULL, '角色管理菜单');
INSERT INTO sys_menu
VALUES ('102', '菜单管理', '1', '3', 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table',
        'admin', NOW(), '', NULL, '菜单管理菜单');
INSERT INTO sys_menu
VALUES ('103', '部门管理', '1', '4', 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree',
        'admin', NOW(), '', NULL, '部门管理菜单');
INSERT INTO sys_menu
VALUES ('104', '岗位管理', '1', '5', 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post',
        'admin', NOW(), '', NULL, '岗位管理菜单');
INSERT INTO sys_menu
VALUES ('105', '字典管理', '1', '6', 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict',
        'admin', NOW(), '', NULL, '字典管理菜单');
INSERT INTO sys_menu
VALUES ('106', '参数设置', '1', '7', 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit',
        'admin', NOW(), '', NULL, '参数设置菜单');
INSERT INTO sys_menu
VALUES ('107', '通知公告', '1', '8', 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list',
        'message', 'admin', NOW(), '', NULL, '通知公告菜单');
INSERT INTO sys_menu
VALUES ('108', '日志管理', '1', '9', 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', NOW(), '', NULL, '日志管理菜单');
INSERT INTO sys_menu
VALUES ('109', '在线用户', '2', '1', 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list',
        'online', 'admin', NOW(), '', NULL, '在线用户菜单');
INSERT INTO sys_menu
VALUES ('110', '定时任务', '2', '2', 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job',
        'admin', NOW(), '', NULL, '定时任务菜单');
INSERT INTO sys_menu
VALUES ('111', '数据监控', '2', '3', 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid',
        'admin', NOW(), '', NULL, '数据监控菜单');
INSERT INTO sys_menu
VALUES ('112', '服务监控', '2', '4', 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list',
        'server', 'admin', NOW(), '', NULL, '服务监控菜单');
INSERT INTO sys_menu
VALUES ('113', '缓存监控', '2', '5', 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis',
        'admin', NOW(), '', NULL, '缓存监控菜单');
INSERT INTO sys_menu
VALUES ('114', '表单构建', '3', '1', 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build',
        'admin', NOW(), '', NULL, '表单构建菜单');
INSERT INTO sys_menu
VALUES ('115', '代码生成', '3', '2', 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin',
        NOW(), '', NULL, '代码生成菜单');
INSERT INTO sys_menu
VALUES ('116', '系统接口', '3', '3', 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list',
        'swagger', 'admin', NOW(), '', NULL, '系统接口菜单');
-- 三级菜单
INSERT INTO sys_menu
VALUES ('500', '操作日志', '108', '1', 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',
        'form', 'admin', NOW(), '', NULL, '操作日志菜单');
INSERT INTO sys_menu
VALUES ('501', '登录日志', '108', '2', 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0',
        'monitor:logininfor:list', 'logininfor', 'admin', NOW(), '', NULL, '登录日志菜单');
-- 用户管理按钮
INSERT INTO sys_menu
VALUES ('1001', '用户查询', '100', '1', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1002', '用户新增', '100', '2', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1003', '用户修改', '100', '3', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1004', '用户删除', '100', '4', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1005', '用户导出', '100', '5', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1006', '用户导入', '100', '6', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1007', '重置密码', '100', '7', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', NOW(), '',
        NULL, '');
-- 角色管理按钮
INSERT INTO sys_menu
VALUES ('1008', '角色查询', '101', '1', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1009', '角色新增', '101', '2', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1010', '角色修改', '101', '3', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1011', '角色删除', '101', '4', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1012', '角色导出', '101', '5', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', NOW(), '',
        NULL, '');
-- 菜单管理按钮
INSERT INTO sys_menu
VALUES ('1013', '菜单查询', '102', '1', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1014', '菜单新增', '102', '2', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1015', '菜单修改', '102', '3', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1016', '菜单删除', '102', '4', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', NOW(), '',
        NULL, '');
-- 部门管理按钮
INSERT INTO sys_menu
VALUES ('1017', '部门查询', '103', '1', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1018', '部门新增', '103', '2', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1019', '部门修改', '103', '3', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1020', '部门删除', '103', '4', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', NOW(), '',
        NULL, '');
-- 岗位管理按钮
INSERT INTO sys_menu
VALUES ('1021', '岗位查询', '104', '1', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1022', '岗位新增', '104', '2', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1023', '岗位修改', '104', '3', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1024', '岗位删除', '104', '4', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1025', '岗位导出', '104', '5', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', NOW(), '',
        NULL, '');
-- 字典管理按钮
INSERT INTO sys_menu
VALUES ('1026', '字典查询', '105', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1027', '字典新增', '105', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1028', '字典修改', '105', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1029', '字典删除', '105', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1030', '字典导出', '105', '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', NOW(), '',
        NULL, '');
-- 参数设置按钮
INSERT INTO sys_menu
VALUES ('1031', '参数查询', '106', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1032', '参数新增', '106', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1033', '参数修改', '106', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1034', '参数删除', '106', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1035', '参数导出', '106', '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', NOW(), '',
        NULL, '');
-- 通知公告按钮
INSERT INTO sys_menu
VALUES ('1036', '公告查询', '107', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1037', '公告新增', '107', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1038', '公告修改', '107', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1039', '公告删除', '107', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', NOW(), '',
        NULL, '');
-- 操作日志按钮
INSERT INTO sys_menu
VALUES ('1040', '操作查询', '500', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1041', '操作删除', '500', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1042', '日志导出', '500', '4', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', NOW(), '',
        NULL, '');
-- 登录日志按钮
INSERT INTO sys_menu
VALUES ('1043', '登录查询', '501', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', NOW(),
        '', NULL, '');
INSERT INTO sys_menu
VALUES ('1044', '登录删除', '501', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', NOW(),
        '', NULL, '');
INSERT INTO sys_menu
VALUES ('1045', '日志导出', '501', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', NOW(),
        '', NULL, '');
-- 在线用户按钮
INSERT INTO sys_menu
VALUES ('1046', '在线查询', '109', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1047', '批量强退', '109', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', NOW(),
        '', NULL, '');
INSERT INTO sys_menu
VALUES ('1048', '单条强退', '109', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', NOW(),
        '', NULL, '');
-- 定时任务按钮
INSERT INTO sys_menu
VALUES ('1049', '任务查询', '110', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1050', '任务新增', '110', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1051', '任务修改', '110', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1052', '任务删除', '110', '4', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', NOW(), '',
        NULL, '');
INSERT INTO sys_menu
VALUES ('1053', '状态修改', '110', '5', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', NOW(),
        '', NULL, '');
INSERT INTO sys_menu
VALUES ('1054', '任务导出', '110', '7', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', NOW(), '',
        NULL, '');
-- 代码生成按钮
INSERT INTO sys_menu
VALUES ('1055', '生成查询', '115', '1', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1056', '生成修改', '115', '2', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1057', '生成删除', '115', '3', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1058', '导入代码', '115', '2', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1059', '预览代码', '115', '4', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_menu
VALUES ('1060', '生成代码', '115', '5', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', NOW(), '', NULL,
        '');

-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_sys_user_role PRIMARY KEY (user_id, role_id)
);

COMMENT
    ON TABLE sys_user_role IS '用户和角色关联表';
COMMENT
    ON COLUMN sys_user_role.user_id IS '用户ID';
COMMENT
    ON COLUMN sys_user_role.role_id IS '角色ID';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
INSERT INTO sys_user_role
VALUES ('1', '1');
INSERT INTO sys_user_role
VALUES ('2', '2');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu
(
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    CONSTRAINT pk_sys_role_menu PRIMARY KEY (role_id, menu_id)
);

COMMENT
    ON TABLE sys_role_menu IS '角色和菜单关联表';
COMMENT
    ON COLUMN sys_role_menu.role_id IS '角色ID';
COMMENT
    ON COLUMN sys_role_menu.menu_id IS '菜单ID';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
INSERT INTO sys_role_menu
VALUES ('2', '1');
INSERT INTO sys_role_menu
VALUES ('2', '2');
INSERT INTO sys_role_menu
VALUES ('2', '3');
INSERT INTO sys_role_menu
VALUES ('2', '4');
INSERT INTO sys_role_menu
VALUES ('2', '100');
INSERT INTO sys_role_menu
VALUES ('2', '101');
INSERT INTO sys_role_menu
VALUES ('2', '102');
INSERT INTO sys_role_menu
VALUES ('2', '103');
INSERT INTO sys_role_menu
VALUES ('2', '104');
INSERT INTO sys_role_menu
VALUES ('2', '105');
INSERT INTO sys_role_menu
VALUES ('2', '106');
INSERT INTO sys_role_menu
VALUES ('2', '107');
INSERT INTO sys_role_menu
VALUES ('2', '108');
INSERT INTO sys_role_menu
VALUES ('2', '109');
INSERT INTO sys_role_menu
VALUES ('2', '110');
INSERT INTO sys_role_menu
VALUES ('2', '111');
INSERT INTO sys_role_menu
VALUES ('2', '112');
INSERT INTO sys_role_menu
VALUES ('2', '113');
INSERT INTO sys_role_menu
VALUES ('2', '114');
INSERT INTO sys_role_menu
VALUES ('2', '115');
INSERT INTO sys_role_menu
VALUES ('2', '500');
INSERT INTO sys_role_menu
VALUES ('2', '501');
INSERT INTO sys_role_menu
VALUES ('2', '1000');
INSERT INTO sys_role_menu
VALUES ('2', '1001');
INSERT INTO sys_role_menu
VALUES ('2', '1002');
INSERT INTO sys_role_menu
VALUES ('2', '1003');
INSERT INTO sys_role_menu
VALUES ('2', '1004');
INSERT INTO sys_role_menu
VALUES ('2', '1005');
INSERT INTO sys_role_menu
VALUES ('2', '1006');
INSERT INTO sys_role_menu
VALUES ('2', '1007');
INSERT INTO sys_role_menu
VALUES ('2', '1008');
INSERT INTO sys_role_menu
VALUES ('2', '1009');
INSERT INTO sys_role_menu
VALUES ('2', '1010');
INSERT INTO sys_role_menu
VALUES ('2', '1011');
INSERT INTO sys_role_menu
VALUES ('2', '1012');
INSERT INTO sys_role_menu
VALUES ('2', '1013');
INSERT INTO sys_role_menu
VALUES ('2', '1014');
INSERT INTO sys_role_menu
VALUES ('2', '1015');
INSERT INTO sys_role_menu
VALUES ('2', '1016');
INSERT INTO sys_role_menu
VALUES ('2', '1017');
INSERT INTO sys_role_menu
VALUES ('2', '1018');
INSERT INTO sys_role_menu
VALUES ('2', '1019');
INSERT INTO sys_role_menu
VALUES ('2', '1020');
INSERT INTO sys_role_menu
VALUES ('2', '1021');
INSERT INTO sys_role_menu
VALUES ('2', '1022');
INSERT INTO sys_role_menu
VALUES ('2', '1023');
INSERT INTO sys_role_menu
VALUES ('2', '1024');
INSERT INTO sys_role_menu
VALUES ('2', '1025');
INSERT INTO sys_role_menu
VALUES ('2', '1026');
INSERT INTO sys_role_menu
VALUES ('2', '1027');
INSERT INTO sys_role_menu
VALUES ('2', '1028');
INSERT INTO sys_role_menu
VALUES ('2', '1029');
INSERT INTO sys_role_menu
VALUES ('2', '1030');
INSERT INTO sys_role_menu
VALUES ('2', '1031');
INSERT INTO sys_role_menu
VALUES ('2', '1032');
INSERT INTO sys_role_menu
VALUES ('2', '1033');
INSERT INTO sys_role_menu
VALUES ('2', '1034');
INSERT INTO sys_role_menu
VALUES ('2', '1035');
INSERT INTO sys_role_menu
VALUES ('2', '1036');
INSERT INTO sys_role_menu
VALUES ('2', '1037');
INSERT INTO sys_role_menu
VALUES ('2', '1038');
INSERT INTO sys_role_menu
VALUES ('2', '1039');
INSERT INTO sys_role_menu
VALUES ('2', '1040');
INSERT INTO sys_role_menu
VALUES ('2', '1041');
INSERT INTO sys_role_menu
VALUES ('2', '1042');
INSERT INTO sys_role_menu
VALUES ('2', '1043');
INSERT INTO sys_role_menu
VALUES ('2', '1044');
INSERT INTO sys_role_menu
VALUES ('2', '1045');
INSERT INTO sys_role_menu
VALUES ('2', '1046');
INSERT INTO sys_role_menu
VALUES ('2', '1047');
INSERT INTO sys_role_menu
VALUES ('2', '1048');
INSERT INTO sys_role_menu
VALUES ('2', '1049');
INSERT INTO sys_role_menu
VALUES ('2', '1050');
INSERT INTO sys_role_menu
VALUES ('2', '1051');
INSERT INTO sys_role_menu
VALUES ('2', '1052');
INSERT INTO sys_role_menu
VALUES ('2', '1053');
INSERT INTO sys_role_menu
VALUES ('2', '1054');
INSERT INTO sys_role_menu
VALUES ('2', '1055');
INSERT INTO sys_role_menu
VALUES ('2', '1056');
INSERT INTO sys_role_menu
VALUES ('2', '1057');
INSERT INTO sys_role_menu
VALUES ('2', '1058');
INSERT INTO sys_role_menu
VALUES ('2', '1059');
INSERT INTO sys_role_menu
VALUES ('2', '1060');

-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
DROP TABLE IF EXISTS sys_role_dept;

CREATE TABLE sys_role_dept
(
    role_id BIGINT NOT NULL,
    dept_id BIGINT NOT NULL,
    CONSTRAINT pk_sys_role_dept PRIMARY KEY (role_id, dept_id)
);

COMMENT
    ON TABLE sys_role_dept IS '角色和部门关联表';
COMMENT
    ON COLUMN sys_role_dept.role_id IS '用户ID';
COMMENT
    ON COLUMN sys_role_dept.dept_id IS '岗位ID';

-- ----------------------------
-- 角色和部门关联表  角色1-N部门
-- ----------------------------
INSERT INTO sys_role_dept
VALUES ('2', '100');
INSERT INTO sys_role_dept
VALUES ('2', '101');
INSERT INTO sys_role_dept
VALUES ('2', '105');

-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
DROP TABLE IF EXISTS sys_user_post;
CREATE TABLE sys_user_post
(
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    CONSTRAINT pk_sys_user_post PRIMARY KEY (user_id, post_id)
);

COMMENT
    ON TABLE sys_user_post IS '用户与岗位关联表';
COMMENT
    ON COLUMN sys_user_post.user_id IS '用户ID';
COMMENT
    ON COLUMN sys_user_post.post_id IS '岗位ID';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
INSERT INTO sys_user_post
VALUES ('1', '1');
INSERT INTO sys_user_post
VALUES ('2', '2');

-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
DROP TABLE IF EXISTS sys_oper_log;
CREATE TABLE sys_oper_log
(
    oper_id        BIGSERIAL     NOT NULL,
    title          VARCHAR(50)   NULL DEFAULT '',
    business_type  SMALLINT      NULL DEFAULT 0,
    method         VARCHAR(100)  NULL DEFAULT '',
    request_method VARCHAR(10)   NULL DEFAULT '',
    operator_type  SMALLINT      NULL DEFAULT 0,
    oper_name      VARCHAR(50)   NULL DEFAULT '',
    dept_name      VARCHAR(50)   NULL DEFAULT '',
    oper_url       VARCHAR(255)  NULL DEFAULT '',
    oper_ip        VARCHAR(30)   NULL DEFAULT '',
    oper_location  VARCHAR(255)  NULL DEFAULT '',
    oper_param     VARCHAR(2000) NULL DEFAULT '',
    json_result    VARCHAR(2000) NULL DEFAULT '',
    status         SMALLINT      NULL DEFAULT 0,
    error_msg      VARCHAR(2000) NULL DEFAULT '',
    oper_time      timestamptz   NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_sys_oper_log PRIMARY KEY (oper_id)
);
ALTER SEQUENCE IF EXISTS sys_oper_log_oper_id_seq RESTART WITH 100 CACHE 20;

COMMENT
    ON TABLE sys_oper_log IS '操作日志记录';
COMMENT
    ON COLUMN sys_oper_log.oper_id IS '日志主键';
COMMENT
    ON COLUMN sys_oper_log.title IS '模块标题';
COMMENT
    ON COLUMN sys_oper_log.business_type IS '业务类型（0其它 1新增 2修改 3删除）';
COMMENT
    ON COLUMN sys_oper_log.method IS '方法名称';
COMMENT
    ON COLUMN sys_oper_log.request_method IS '请求方式';
COMMENT
    ON COLUMN sys_oper_log.operator_type IS '操作类别（0其它 1后台用户 2手机端用户）';
COMMENT
    ON COLUMN sys_oper_log.oper_name IS '操作人员';
COMMENT
    ON COLUMN sys_oper_log.dept_name IS '部门名称';
COMMENT
    ON COLUMN sys_oper_log.oper_url IS '请求URL';
COMMENT
    ON COLUMN sys_oper_log.oper_ip IS '主机地址';
COMMENT
    ON COLUMN sys_oper_log.oper_location IS '操作地点';
COMMENT
    ON COLUMN sys_oper_log.oper_param IS '请求参数';
COMMENT
    ON COLUMN sys_oper_log.json_result IS '返回参数';
COMMENT
    ON COLUMN sys_oper_log.status IS '操作状态（0正常 1异常）';
COMMENT
    ON COLUMN sys_oper_log.error_msg IS '错误消息';
COMMENT
    ON COLUMN sys_oper_log.oper_time IS '操作时间';


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type
(
    dict_id     BIGSERIAL    NOT NULL,
    dict_name   VARCHAR(100) NULL DEFAULT '',
    dict_type   VARCHAR(100) NULL DEFAULT '' UNIQUE,
    status      CHAR(1)      NULL DEFAULT '0',
    create_by   VARCHAR(64)  NULL DEFAULT '',
    create_time timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  NULL DEFAULT '',
    update_time timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    remark      VARCHAR(500) NULL DEFAULT '',
    CONSTRAINT pk_sys_dict_type PRIMARY KEY (dict_id)
);

ALTER SEQUENCE IF EXISTS sys_dict_type_dict_id_seq RESTART WITH 100 CACHE 20;

COMMENT
    ON TABLE sys_dict_type IS '字典类型表';
COMMENT
    ON COLUMN sys_dict_type.dict_id IS '字典主键';
COMMENT
    ON COLUMN sys_dict_type.dict_name IS '字典名称';
COMMENT
    ON COLUMN sys_dict_type.dict_type IS '字典类型';
COMMENT
    ON COLUMN sys_dict_type.status IS '状态（0正常 1禁用）';
COMMENT
    ON COLUMN sys_dict_type.create_by IS '创建者';
COMMENT
    ON COLUMN sys_dict_type.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_dict_type.update_by IS '更新者';
COMMENT
    ON COLUMN sys_dict_type.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_dict_type.remark IS '备注';

-- ----------------------------
-- 初始化-字典类型表
-- ----------------------------
INSERT INTO sys_dict_type
VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', NOW(), '', NULL, '用户性别列表');
INSERT INTO sys_dict_type
VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', NOW(), '', NULL, '菜单状态列表');
INSERT INTO sys_dict_type
VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', NOW(), '', NULL, '系统开关列表');
INSERT INTO sys_dict_type
VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', NOW(), '', NULL, '任务状态列表');
INSERT INTO sys_dict_type
VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', NOW(), '', NULL, '任务分组列表');
INSERT INTO sys_dict_type
VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', NOW(), '', NULL, '系统是否列表');
INSERT INTO sys_dict_type
VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', NOW(), '', NULL, '通知类型列表');
INSERT INTO sys_dict_type
VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', NOW(), '', NULL, '通知状态列表');
INSERT INTO sys_dict_type
VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', NOW(), '', NULL, '操作类型列表');
INSERT INTO sys_dict_type
VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', NOW(), '', NULL, '登录状态列表');

-- ----------------------------
-- 12、字典数据表
-- ----------------------------
DROP TABLE IF EXISTS sys_dict_data;
CREATE TABLE sys_dict_data
(
    dict_code   BIGSERIAL    NOT NULL,
    dict_sort   BIGINT       NULL DEFAULT 0,
    dict_label  VARCHAR(100) NULL DEFAULT '',
    dict_value  VARCHAR(100) NULL DEFAULT '',
    dict_type   VARCHAR(100) NULL DEFAULT '',
    css_class   VARCHAR(100) NULL,
    list_class  VARCHAR(100) NULL,
    is_default  CHAR(1)      NULL DEFAULT 'N',
    status      CHAR(1)      NULL DEFAULT '0',
    create_by   VARCHAR(64)  NULL DEFAULT '',
    create_time timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  NULL DEFAULT '',
    update_time timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    remark      VARCHAR(500) NULL DEFAULT '',
    CONSTRAINT pk_sys_dict_data PRIMARY KEY (dict_code)
);
ALTER SEQUENCE IF EXISTS sys_dict_data_dict_code_seq RESTART WITH 100 CACHE 20;

COMMENT
    ON TABLE sys_dict_data IS '字典数据表';
COMMENT
    ON COLUMN sys_dict_data.dict_code IS '字典编码';
COMMENT
    ON COLUMN sys_dict_data.dict_sort IS '字典排序';
COMMENT
    ON COLUMN sys_dict_data.dict_label IS '字典标签';
COMMENT
    ON COLUMN sys_dict_data.dict_value IS '字典键值';
COMMENT
    ON COLUMN sys_dict_data.dict_type IS '字典类型';
COMMENT
    ON COLUMN sys_dict_data.css_class IS '样式属性（其他样式扩展）';
COMMENT
    ON COLUMN sys_dict_data.list_class IS '表格回显样式';
COMMENT
    ON COLUMN sys_dict_data.is_default IS '是否默认（Y是 N否）';
COMMENT
    ON COLUMN sys_dict_data.status IS '状态（0正常 1禁用）';
COMMENT
    ON COLUMN sys_dict_data.create_by IS '创建者';
COMMENT
    ON COLUMN sys_dict_data.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_dict_data.update_by IS '更新者';
COMMENT
    ON COLUMN sys_dict_data.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_dict_data.remark IS '备注';

-- ----------------------------
-- 初始化-字典数据表
-- ----------------------------
INSERT INTO sys_dict_data
VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', NOW(), '', NULL, '性别男');
INSERT INTO sys_dict_data
VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', NOW(), '', NULL, '性别女');
INSERT INTO sys_dict_data
VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', NOW(), '', NULL, '性别未知');
INSERT INTO sys_dict_data
VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '显示菜单');
INSERT INTO sys_dict_data
VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '隐藏菜单');
INSERT INTO sys_dict_data
VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '正常状态');
INSERT INTO sys_dict_data
VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '停用状态');
INSERT INTO sys_dict_data
VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '正常状态');
INSERT INTO sys_dict_data
VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '停用状态');
INSERT INTO sys_dict_data
VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', NOW(), '', NULL, '默认分组');
INSERT INTO sys_dict_data
VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', NOW(), '', NULL, '系统分组');
INSERT INTO sys_dict_data
VALUES (12, 1, '是', '1', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '系统默认是');
INSERT INTO sys_dict_data
VALUES (13, 2, '否', '0', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '系统默认否');
INSERT INTO sys_dict_data
VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', NOW(), '', NULL, '通知');
INSERT INTO sys_dict_data
VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', NOW(), '', NULL, '公告');
INSERT INTO sys_dict_data
VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', NOW(), '', NULL, '正常状态');
INSERT INTO sys_dict_data
VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '关闭状态');
INSERT INTO sys_dict_data
VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '新增操作');
INSERT INTO sys_dict_data
VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', NOW(), '', NULL, '修改操作');
INSERT INTO sys_dict_data
VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '删除操作');
INSERT INTO sys_dict_data
VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', NOW(), '', NULL, '授权操作');
INSERT INTO sys_dict_data
VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', NOW(), '', NULL, '导出操作');
INSERT INTO sys_dict_data
VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', NOW(), '', NULL, '导入操作');
INSERT INTO sys_dict_data
VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '强退操作');
INSERT INTO sys_dict_data
VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', NOW(), '', NULL, '生成操作');
INSERT INTO sys_dict_data
VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '清空操作');
INSERT INTO sys_dict_data
VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', NOW(), '', NULL, '正常状态');
INSERT INTO sys_dict_data
VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '停用状态');

-- ----------------------------
-- 13、参数配置表
-- ----------------------------
DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config
(
    config_id    BIGSERIAL    NOT NULL,
    config_name  VARCHAR(100) NULL DEFAULT '0',
    config_key   VARCHAR(100) NULL DEFAULT '',
    config_value VARCHAR(500) NULL DEFAULT '',
    config_type  CHAR(1)      NULL DEFAULT '0',
    create_by    VARCHAR(64)  NULL DEFAULT '',
    create_time  timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    update_by    VARCHAR(64)  NULL DEFAULT '',
    update_time  timestamptz  NULL DEFAULT CURRENT_TIMESTAMP,
    remark       VARCHAR(500) NULL DEFAULT '',
    CONSTRAINT pk_sys_config PRIMARY KEY (config_id)
);
ALTER SEQUENCE IF EXISTS sys_config_config_id_seq RESTART WITH 100 CACHE 20;
COMMENT
    ON TABLE sys_config IS '参数配置表';
COMMENT
    ON COLUMN sys_config.config_id IS '参数主键';
COMMENT
    ON COLUMN sys_config.config_name IS '参数名称';
COMMENT
    ON COLUMN sys_config.config_key IS '参数键名';
COMMENT
    ON COLUMN sys_config.config_value IS '参数键值';
COMMENT
    ON COLUMN sys_config.config_type IS '系统内置（1是 0否）';
COMMENT
    ON COLUMN sys_config.create_by IS '创建者';
COMMENT
    ON COLUMN sys_config.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_config.update_by IS '更新者';
COMMENT
    ON COLUMN sys_config.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_config.remark IS '备注';

-- ----------------------------
-- 初始化-参数配置表
-- ----------------------------
INSERT INTO sys_config
VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', '1', 'admin', NOW(), '', NULL,
        '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO sys_config
VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', '1', 'admin', NOW(), '', NULL, '初始化密码 123456');
INSERT INTO sys_config
VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', '1', 'admin', NOW(), '', NULL,
        '深色主题theme-dark，浅色主题theme-light');
INSERT INTO sys_config
VALUES (4, '账号自助-验证码开关', 'sys.account.captchaOnOff', 'true', '1', 'admin', NOW(), '', NULL,
        '是否开启验证码功能（true开启，false关闭）');
INSERT INTO sys_config
VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', '1', 'admin', NOW(), '', NULL,
        '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
DROP TABLE IF EXISTS sys_logininfor;
CREATE TABLE sys_logininfor
(
    info_id        BIGSERIAL    NOT NULL,
    user_name      VARCHAR(50)  NULL DEFAULT '',
    ipaddr         VARCHAR(50)  NULL DEFAULT '',
    login_location VARCHAR(255) NULL DEFAULT '',
    browser        VARCHAR(50)  NULL DEFAULT '',
    os             VARCHAR(50)  NULL DEFAULT '',
    status         CHAR(1)      NULL DEFAULT '0',
    msg            VARCHAR(255) NULL DEFAULT '',
    login_time     timestamptz  NULL,
    CONSTRAINT pk_sys_logininfor PRIMARY KEY (info_id)
);

ALTER SEQUENCE IF EXISTS sys_logininfor_info_id_seq RESTART WITH 100 CACHE 20;

COMMENT
    ON TABLE sys_logininfor IS '系统访问记录';
COMMENT
    ON COLUMN sys_logininfor.info_id IS '访问ID';
COMMENT
    ON COLUMN sys_logininfor.user_name IS '用户账号';
COMMENT
    ON COLUMN sys_logininfor.ipaddr IS '登录IP地址';
COMMENT
    ON COLUMN sys_logininfor.login_location IS '登录地点';
COMMENT
    ON COLUMN sys_logininfor.browser IS '浏览器类型';
COMMENT
    ON COLUMN sys_logininfor.os IS '操作系统';
COMMENT
    ON COLUMN sys_logininfor.status IS '登录状态（0成功 1失败）';
COMMENT
    ON COLUMN sys_logininfor.msg IS '提示消息';
COMMENT
    ON COLUMN sys_logininfor.login_time IS '访问时间';


-- ----------------------------
-- 15、定时任务调度表
-- ----------------------------
DROP TABLE IF EXISTS sys_job;

CREATE TABLE sys_job
(
    job_id          BIGSERIAL    NOT NULL,
    job_name        VARCHAR(64)  NOT NULL DEFAULT '',
    job_group       VARCHAR(64)  NOT NULL DEFAULT 'DEFAULT',
    invoke_target   VARCHAR(500) NOT NULL,
    cron_expression VARCHAR(255) NULL     DEFAULT '',
    misfire_policy  VARCHAR(20)  NULL     DEFAULT '3',
    concurrent      CHAR(1)      NULL     DEFAULT '1',
    status          CHAR(1)      NULL     DEFAULT '0',
    create_by       VARCHAR(64)  NULL     DEFAULT '',
    create_time     timestamptz  NULL     DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)  NULL     DEFAULT '',
    update_time     timestamptz  NULL     DEFAULT CURRENT_TIMESTAMP,
    remark          VARCHAR(500) NULL     DEFAULT '',
    CONSTRAINT pk_sys_job PRIMARY KEY (job_id, job_name, job_group)
);

ALTER SEQUENCE IF EXISTS sys_job_job_id_seq RESTART WITH 100 CACHE 20;

COMMENT
    ON TABLE sys_job IS '定时任务调度表';
COMMENT
    ON COLUMN sys_job.job_id IS '任务主键';
COMMENT
    ON COLUMN sys_job.job_name IS '任务名称';
COMMENT
    ON COLUMN sys_job.job_group IS '任务组名';
COMMENT
    ON COLUMN sys_job.invoke_target IS '调用目标字符串';
COMMENT
    ON COLUMN sys_job.cron_expression IS 'cron执行表达式';
COMMENT
    ON COLUMN sys_job.misfire_policy IS '计划执行错误策略（1立即执行 2执行一次 3放弃执行）';
COMMENT
    ON COLUMN sys_job.concurrent IS '是否并发执行（0允许 1禁止）';
COMMENT
    ON COLUMN sys_job.status IS '状态（0正常 1暂停）';
COMMENT
    ON COLUMN sys_job.create_by IS '创建者';
COMMENT
    ON COLUMN sys_job.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_job.update_by IS '更新者';
COMMENT
    ON COLUMN sys_job.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_job.remark IS '备注信息';

-- ----------------------------
-- 初始化-定时任务调度表
-- ----------------------------
INSERT INTO sys_job
VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_job
VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams("ry")', '0/15 * * * * ?', '3', '1', '1', 'admin', NOW(), '', NULL,
        '');
INSERT INTO sys_job
VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams("ry", true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3',
        '1', '1', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 16、定时任务调度日志表
-- ----------------------------
DROP TABLE IF EXISTS sys_job_log;
CREATE TABLE sys_job_log
(
    job_log_id     BIGSERIAL     NOT NULL,
    job_name       VARCHAR(64)   NOT NULL,
    job_group      VARCHAR(64)   NOT NULL,
    invoke_target  VARCHAR(500)  NOT NULL,
    job_message    VARCHAR(500)  NULL,
    status         CHAR(1)            DEFAULT '0',
    exception_info VARCHAR(2000) NULL,
    create_time    timestamptz   NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_sys_job_log PRIMARY KEY (job_log_id)
);
ALTER SEQUENCE IF EXISTS sys_job_log_job_log_id_seq CACHE 20;

COMMENT
    ON TABLE sys_job_log IS '定时任务调度日志表';
COMMENT
    ON COLUMN sys_job_log.job_log_id IS '任务日志ID';
COMMENT
    ON COLUMN sys_job_log.job_name IS '任务名称';
COMMENT
    ON COLUMN sys_job_log.job_group IS '任务组名';
COMMENT
    ON COLUMN sys_job_log.invoke_target IS '调用目标字符串';
COMMENT
    ON COLUMN sys_job_log.job_message IS '日志信息';
COMMENT
    ON COLUMN sys_job_log.status IS '执行状态（0正常 1失败）';
COMMENT
    ON COLUMN sys_job_log.exception_info IS '异常信息';
COMMENT
    ON COLUMN sys_job_log.create_time IS '创建时间';

-- ----------------------------
-- 17、通知公告表
-- ----------------------------
DROP TABLE IF EXISTS sys_notice;

CREATE TABLE sys_notice
(
    notice_id      BIGSERIAL     NOT NULL,
    notice_title   VARCHAR(50)   NOT NULL,
    notice_type    CHAR(1)       NOT NULL,
    notice_content VARCHAR(2000) NULL,
    status         CHAR(1)       NULL DEFAULT '0',
    create_by      VARCHAR(64)   NULL DEFAULT '',
    create_time    timestamptz   NULL DEFAULT CURRENT_TIMESTAMP,
    update_by      VARCHAR(64)   NULL DEFAULT '',
    update_time    timestamptz   NULL DEFAULT CURRENT_TIMESTAMP,
    remark         VARCHAR(255)  NULL,
    CONSTRAINT pk_sys_notice PRIMARY KEY (notice_id)
);
ALTER SEQUENCE IF EXISTS sys_notice_notice_id_seq RESTART WITH 10 CACHE 20;
COMMENT
    ON TABLE sys_notice IS '通知公告表';
COMMENT
    ON COLUMN sys_notice.notice_id IS '公告主键';
COMMENT
    ON COLUMN sys_notice.notice_title IS '公告标题';
COMMENT
    ON COLUMN sys_notice.notice_type IS '公告类型（1通知 2公告）';
COMMENT
    ON COLUMN sys_notice.notice_content IS '公告内容';
COMMENT
    ON COLUMN sys_notice.status IS '公告状态（0正常 1关闭）';
COMMENT
    ON COLUMN sys_notice.create_by IS '创建者';
COMMENT
    ON COLUMN sys_notice.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_notice.update_by IS '更新者';
COMMENT
    ON COLUMN sys_notice.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_notice.remark IS '备注';

-- ----------------------------
-- 17、初始化-公告信息表数据
-- ----------------------------
INSERT INTO sys_notice
VALUES ('1', '温馨提醒：2021-10-01 clickless新版本发布啦', '2', '新版本内容', '0', 'admin', NOW(), '', NULL, '管理员');
INSERT INTO sys_notice
VALUES ('2', '维护通知：2021-10-01 clickless系统凌晨维护', '1', '维护内容', '0', 'admin', NOW(), '', NULL, '管理员');

-- ----------------------------
-- 18、代码生成业务表
-- ----------------------------
DROP TABLE IF EXISTS gen_table;
CREATE TABLE gen_table
(
    table_id          BIGSERIAL NOT NULL,
    table_name        VARCHAR(200) DEFAULT '',
    table_comment     VARCHAR(500) DEFAULT '',
    sub_table_name    VARCHAR(64)  DEFAULT '',
    sub_table_fk_name VARCHAR(64)  DEFAULT '',
    class_name        VARCHAR(100) DEFAULT '',
    tpl_category      VARCHAR(200) DEFAULT 'crud',
    package_name      VARCHAR(100),
    module_name       VARCHAR(30),
    business_name     VARCHAR(30),
    function_name     VARCHAR(50),
    function_author   VARCHAR(50),
    gen_type          CHAR(1)      DEFAULT '0',
    gen_path          VARCHAR(200) DEFAULT '/',
    options           VARCHAR(1000),
    create_by         VARCHAR(64)  DEFAULT '',
    create_time       timestamptz  DEFAULT CURRENT_TIMESTAMP,
    update_by         VARCHAR(64)  DEFAULT '',
    update_time       timestamptz  DEFAULT CURRENT_TIMESTAMP,
    remark            VARCHAR(500) DEFAULT NULL
);

ALTER SEQUENCE IF EXISTS gen_table_table_id_seq CACHE 20;

COMMENT
    ON TABLE gen_table IS '代码生成业务表';
COMMENT
    ON COLUMN gen_table.table_id IS '编号';
COMMENT
    ON COLUMN gen_table.table_name IS '表名称';
COMMENT
    ON COLUMN gen_table.table_comment IS '表描述';
COMMENT
    ON COLUMN gen_table.sub_table_name IS '关联子表的表名';
COMMENT
    ON COLUMN gen_table.sub_table_fk_name IS '子表关联的外键名';
COMMENT
    ON COLUMN gen_table.class_name IS '实体类名称';
COMMENT
    ON COLUMN gen_table.tpl_category IS '使用的模板（crud单表操作 tree树表操作）';
COMMENT
    ON COLUMN gen_table.package_name IS '生成包路径';
COMMENT
    ON COLUMN gen_table.module_name IS '生成模块名';
COMMENT
    ON COLUMN gen_table.business_name IS '生成业务名';
COMMENT
    ON COLUMN gen_table.function_name IS '生成功能名';
COMMENT
    ON COLUMN gen_table.function_author IS '生成功能作者';
COMMENT
    ON COLUMN gen_table.gen_type IS '生成代码方式（0zip压缩包 1自定义路径）';
COMMENT
    ON COLUMN gen_table.gen_path IS '生成路径（不填默认项目路径）';
COMMENT
    ON COLUMN gen_table.options IS '其它生成选项';
COMMENT
    ON COLUMN gen_table.create_by IS '创建者';
COMMENT
    ON COLUMN gen_table.create_time IS '创建时间';
COMMENT
    ON COLUMN gen_table.update_by IS '更新者';
COMMENT
    ON COLUMN gen_table.update_time IS '更新时间';
COMMENT
    ON COLUMN gen_table.remark IS '备注';


-- ----------------------------
-- 19、代码生成业务表字段
-- ----------------------------
DROP TABLE IF EXISTS gen_table_column;
CREATE TABLE gen_table_column
(
    column_id      BIGSERIAL NOT NULL,
    table_id       BIGINT,
    column_name    VARCHAR(200),
    column_comment VARCHAR(500),
    column_type    VARCHAR(100),
    java_type      VARCHAR(500),
    java_field     VARCHAR(200),
    is_pk          CHAR(1),
    is_increment   CHAR(1),
    is_required    CHAR(1),
    is_insert      CHAR(1),
    is_edit        CHAR(1),
    is_list        CHAR(1),
    is_query       CHAR(1),
    query_type     VARCHAR(200) DEFAULT 'EQ',
    html_type      VARCHAR(200),
    dict_type      VARCHAR(200) DEFAULT '',
    sort           BIGINT,
    create_by      VARCHAR(64)  DEFAULT '',
    create_time    timestamptz  DEFAULT CURRENT_TIMESTAMP,
    update_by      VARCHAR(64)  DEFAULT '',
    update_time    timestamptz  DEFAULT CURRENT_TIMESTAMP
);

ALTER SEQUENCE IF EXISTS gen_table_column_column_id_seq CACHE 20;

COMMENT
    ON TABLE gen_table_column IS '代码生成业务表字段';
COMMENT
    ON COLUMN gen_table_column.column_id IS '编号';
COMMENT
    ON COLUMN gen_table_column.table_id IS '归属表编号';
COMMENT
    ON COLUMN gen_table_column.column_name IS '列名称';
COMMENT
    ON COLUMN gen_table_column.column_comment IS '列描述';
COMMENT
    ON COLUMN gen_table_column.column_type IS '列类型';
COMMENT
    ON COLUMN gen_table_column.java_type IS 'JAVA类型';
COMMENT
    ON COLUMN gen_table_column.java_field IS 'JAVA字段名';
COMMENT
    ON COLUMN gen_table_column.is_pk IS '是否主键（1是）';
COMMENT
    ON COLUMN gen_table_column.is_increment IS '是否自增（1是）';
COMMENT
    ON COLUMN gen_table_column.is_required IS '是否必填（1是）';
COMMENT
    ON COLUMN gen_table_column.is_insert IS '是否为插入字段（1是）';
COMMENT
    ON COLUMN gen_table_column.is_edit IS '是否编辑字段（1是）';
COMMENT
    ON COLUMN gen_table_column.is_list IS '是否列表字段（1是）';
COMMENT
    ON COLUMN gen_table_column.is_query IS '是否查询字段（1是）';
COMMENT
    ON COLUMN gen_table_column.query_type IS '查询方式（等于、不等于、大于、小于、范围）';
COMMENT
    ON COLUMN gen_table_column.html_type IS '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）';
COMMENT
    ON COLUMN gen_table_column.dict_type IS '字典类型';
COMMENT
    ON COLUMN gen_table_column.sort IS '排序';
COMMENT
    ON COLUMN gen_table_column.create_by IS '创建者';
COMMENT
    ON COLUMN gen_table_column.create_time IS '创建时间';
COMMENT
    ON COLUMN gen_table_column.update_by IS '更新者';
COMMENT
    ON COLUMN gen_table_column.update_time IS '更新时间';



-- ----------------------------
-- 20、页面模型
-- ----------------------------
DROP TABLE IF EXISTS sys_page_model;

CREATE TABLE sys_page_model
(
    id            BIGSERIAL   NOT NULL,
    model_key     VARCHAR(64) NOT NULL,
    model_name    VARCHAR(64) NULL,
    db_table_name VARCHAR(64) NULL,
    create_by     VARCHAR(32) NULL,
    create_time   timestamptz NULL     DEFAULT CURRENT_TIMESTAMP,
    update_by     VARCHAR(32) NULL,
    update_time   timestamptz NULL     DEFAULT CURRENT_TIMESTAMP,
    is_deleted    int2        NOT NULL DEFAULT 0,
    CONSTRAINT pk_sys_page_model PRIMARY KEY (id)
);
ALTER SEQUENCE IF EXISTS sys_page_model_id_seq RESTART WITH 200 CACHE 20;

COMMENT
    ON TABLE sys_page_model IS '页面模型';
COMMENT
    ON COLUMN sys_page_model.id IS '代理主键';
COMMENT
    ON COLUMN sys_page_model.model_key IS '模型编码';
COMMENT
    ON COLUMN sys_page_model.model_name IS '模型名称';
COMMENT
    ON COLUMN sys_page_model.db_table_name IS '数据库表名';
COMMENT
    ON COLUMN sys_page_model.create_by IS '创建者';
COMMENT
    ON COLUMN sys_page_model.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_page_model.update_by IS '更新者';
COMMENT
    ON COLUMN sys_page_model.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_page_model.is_deleted IS '删除标记（0正常 1删除）';


-- ----------------------------
-- 21、页面模型字段
-- ----------------------------
DROP TABLE IF EXISTS sys_page_model_field;

CREATE TABLE sys_page_model_field
(
    id             BIGSERIAL   NOT NULL,
    model_id       BIGINT      NOT NULL,
    field_key      VARCHAR(64) NOT NULL,
    field_name     VARCHAR(64) NULL,
    db_column_name VARCHAR(64) NULL,
    dict_type      VARCHAR(64) NULL,
    field_sort     INTEGER     NOT NULL DEFAULT 0,
    create_by      VARCHAR(32) NULL,
    create_time    timestamptz NULL     DEFAULT CURRENT_TIMESTAMP,
    update_by      VARCHAR(32) NULL,
    update_time    timestamptz NULL     DEFAULT CURRENT_TIMESTAMP,
    is_deleted     int2        NOT NULL DEFAULT 0,
    CONSTRAINT pk_sys_page_model_field PRIMARY KEY (id)
);
ALTER SEQUENCE IF EXISTS sys_page_model_field_id_seq RESTART WITH 200 CACHE 20;

COMMENT
    ON TABLE sys_page_model_field IS '页面模型字段';
COMMENT
    ON COLUMN sys_page_model_field.id IS '代理主键';
COMMENT
    ON COLUMN sys_page_model_field.model_id IS '模型ID';
COMMENT
    ON COLUMN sys_page_model_field.field_key IS '字段编码';
COMMENT
    ON COLUMN sys_page_model_field.field_name IS '字段名称';
COMMENT
    ON COLUMN sys_page_model_field.db_column_name IS '数据库列名';
COMMENT
    ON COLUMN sys_page_model_field.dict_type IS '数据库列名';
COMMENT
    ON COLUMN sys_page_model_field.field_sort IS '字段排序';
COMMENT
    ON COLUMN sys_page_model_field.create_by IS '创建者';
COMMENT
    ON COLUMN sys_page_model_field.create_time IS '创建时间';
COMMENT
    ON COLUMN sys_page_model_field.update_by IS '更新者';
COMMENT
    ON COLUMN sys_page_model_field.update_time IS '更新时间';
COMMENT
    ON COLUMN sys_page_model_field.is_deleted IS '删除标记（0正常 1删除）';

-- ----------------------------
-- 22、用户选择字段
-- ----------------------------
DROP TABLE IF EXISTS sys_user_field;

CREATE TABLE sys_user_field
(
    id          BIGSERIAL   NOT NULL,
    user_id     BIGINT      NOT NULL,
    model_key   VARCHAR(64) NOT NULL,
    field_key   VARCHAR(64) NOT NULL,
    field_sort  INTEGER     NOT NULL DEFAULT 0,
    create_by   VARCHAR(64) NULL,
    create_time timestamptz NULL     DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_sys_user_field PRIMARY KEY (id)
);
ALTER SEQUENCE IF EXISTS sys_user_field_id_seq RESTART WITH 200 CACHE 20;

COMMENT
    ON TABLE sys_user_field IS '用户选择字段';
COMMENT
    ON COLUMN sys_user_field.id IS '代理主键';
COMMENT
    ON COLUMN sys_user_field.user_id IS '用户ID';
COMMENT
    ON COLUMN sys_user_field.model_key IS '模型编码';
COMMENT
    ON COLUMN sys_user_field.field_key IS '字段编码';
COMMENT
    ON COLUMN sys_user_field.field_sort IS '字段排序';
COMMENT
    ON COLUMN sys_user_field.create_by IS '创建者';
COMMENT
    ON COLUMN sys_user_field.create_time IS '创建时间';




-- ----------------------------
-- 函数 ，代替mysql的find_in_set
-- 例如： select * from sys_dept where FIND_IN_SET (101,ancestors) <> 0
-- mysql可接受0或其它number做为where 条件，postgresql只接受表达式做为where 条件 需要明确定义 表达式true  false
-- ----------------------------
CREATE OR REPLACE FUNCTION find_in_set(BIGINT, VARCHAR)
    RETURNS BOOLEAN
AS
$BODY$
DECLARE
    str ALIAS FOR $1;
    strs ALIAS FOR $2;
    pos    INTEGER;
    status BOOLEAN;
BEGIN
    SELECT POSITION(',' || str || ',' IN ',' || strs || ',') INTO pos;
    IF pos > 0 THEN
        status = TRUE;
    ELSE
        status = FALSE;
    END IF;
    RETURN status;
END;
$BODY$ LANGUAGE plpgsql;

-- ----------------------------
-- 视图：用于代码生成模块，读取数据库元数据 表
-- ----------------------------
CREATE OR REPLACE VIEW view_self_table
AS
SELECT datname                                                   AS table_catalog,
       PG_GET_USERBYID(relowner)                                 AS tableowner,
       nspname                                                   AS table_schema,
       relname                                                   AS table_name,
       CAST(OBJ_DESCRIPTION(relfilenode, 'pg_class') AS varchar) AS table_comment,
       NOW()                                                        create_time,
       NOW()                                                        update_time
FROM pg_class c
         LEFT JOIN pg_namespace pg_ns ON
    pg_ns."oid" = c.relnamespace
         LEFT JOIN pg_database ON relowner = datdba
WHERE relname IN (SELECT tablename FROM pg_tables);

-- --------------------------------------
-- 视图：用于代码生成模块，读取数据库元数据 字段
-- --------------------------------------
CREATE OR REPLACE VIEW view_self_table_columns
AS
SELECT table_catalog,
       table_schema,
       table_name,
       ordinal_position                                          AS sort,
       column_name,
       data_type                                                 AS typename,
       (CASE
            WHEN (is_nullable = 'no' AND contype != 'p') THEN '1'
            ELSE NULL
           END)                                                  AS is_required,
       (CASE
            WHEN contype = 'p' THEN '1'
            ELSE '0'
           END)                                                  AS is_pk,
       COALESCE(character_maximum_length, numeric_precision, -1) AS length,
       numeric_scale                                             AS scale,
       CASE
           is_nullable
           WHEN 'NO' THEN 0
           ELSE 1
           END                                                   AS cannull,
       column_default                                            AS defaultval,
       CASE
           WHEN POSITION('nextval' IN column_default) > 0 THEN 1
           ELSE 0
           END                                                   AS isidentity,
       (CASE
            WHEN POSITION('nextval' IN column_default) > 0 THEN 1
            ELSE 0
           END)                                                  AS is_increment,
       c.detext                                                  AS column_comment,
       c.typname                                                 AS column_type,
       c.contype,
       ordinal_position
FROM information_schema.columns
         LEFT JOIN (SELECT datname,
                           PG_GET_USERBYID(relowner) AS tableowner,
                           nspname,
                           relname,
                           attname,
                           description               AS detext,
                           typname,
                           pg_cons.contype
                    FROM pg_class
                             LEFT JOIN pg_attribute pg_attr ON
                        pg_attr.attrelid = pg_class.oid
                             LEFT JOIN pg_description pg_desc ON
                                pg_desc.objoid = pg_attr.attrelid
                            AND pg_desc.objsubid = pg_attr.attnum
                             LEFT JOIN pg_namespace pg_ns ON
                        pg_ns."oid" = pg_class.relnamespace
                             LEFT JOIN pg_database ON relowner = datdba
                             LEFT JOIN pg_type ON pg_attr.atttypid = pg_type."oid"
                             LEFT JOIN (SELECT pg_con.*, UNNEST(conkey) conkey_new FROM pg_constraint pg_con) pg_cons ON
                                pg_attr.attrelid = pg_class.oid
                            AND pg_attr.attnum = pg_cons.conkey_new AND pg_cons.conrelid = pg_class.oid
                    WHERE pg_attr.attnum > 0
                      AND pg_attr.attrelid = pg_class.oid
) c
                   ON table_catalog = datname AND table_schema = nspname AND table_name = relname AND
                      column_name = attname
--where
--	table_schema = 'public'
--	and table_name = 'sys_user_role'
--order by ordinal_position asc