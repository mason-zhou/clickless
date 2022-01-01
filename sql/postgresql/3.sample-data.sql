-- ----------------------------
-- 1、商品数据 示例
-- ----------------------------
DROP TABLE IF EXISTS sample_product;

CREATE TABLE sample_product
(
    id           BIGSERIAL   NOT NULL,
    product_no   VARCHAR(30) NULL     DEFAULT NULL,
    product_name VARCHAR(30) NULL     DEFAULT NULL,
    weight       int8        NULL     DEFAULT NULL,
    launch_date  DATE        NULL     DEFAULT NULL,
    sex          int2        NULL     DEFAULT NULL,
    create_time  timestamptz NULL     DEFAULT NULL,
    is_disabled  int2        NOT NULL DEFAULT 0,
    is_deleted   int2        NOT NULL DEFAULT 0,
    CONSTRAINT pk_sample_product PRIMARY KEY (id)
);

ALTER SEQUENCE IF EXISTS sample_product_id_seq RESTART WITH 100 CACHE 20;

COMMENT
    ON TABLE sample_product IS '示例表-商品数据';
COMMENT
    ON COLUMN sample_product.id IS '代理主键';
COMMENT
    ON COLUMN sample_product.product_no IS '商品编号';
COMMENT
    ON COLUMN sample_product.product_name IS '商品名称';
COMMENT
    ON COLUMN sample_product.weight IS '商品重量';
COMMENT
    ON COLUMN sample_product.launch_date IS '上市日期';
COMMENT
    ON COLUMN sample_product.sex IS '适用性别（1男 0女）';
COMMENT
    ON COLUMN sample_product.create_time IS '创建时间';
COMMENT
    ON COLUMN sample_product.is_disabled IS '是否禁用（0正常 1禁用）';
COMMENT
    ON COLUMN sample_product.is_deleted IS '逻辑删除（0正常 1删除）';



-- ----------------------------
-- 初始化-商品数据 示例数据
-- ----------------------------
INSERT INTO sample_product(product_no, product_name, weight, launch_date, sex, create_time, is_disabled, is_deleted)
VALUES ('PD001', '测试商品1', 10, '2021-3-1', 1, NOW(), 0, 0);
INSERT INTO sample_product(product_no, product_name, weight, launch_date, sex, create_time, is_disabled, is_deleted)
VALUES ('PD002', '测试商品2', 20, '2021-4-2', 0, NOW(), 0, 0);
INSERT INTO sample_product(product_no, product_name, weight, launch_date, sex, create_time, is_disabled, is_deleted)
VALUES ('PD002', '测试商品3', 35, '2021-5-3', 1, NOW(), 0, 0);


