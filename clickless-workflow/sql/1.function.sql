--------------------
-- 自定义函数
-- 用途：update_time字段自动更新
--      data_version字段自动更新
--------------------

-- 行数据 [update_time] 自动生成函数
-- 说明：每次更新行数据时，自动更新update_time字段为当前最新时间
-- 使用方法：create trigger update_timestamp_trigger before update on <table_name> for each row execute procedure update_timestamp();
CREATE OR REPLACE FUNCTION update_timestamp() RETURNS TRIGGER AS
$$
BEGIN
    new.update_time = CURRENT_TIMESTAMP;
    RETURN new;
END
$$
    LANGUAGE plpgsql;

-- 行数据 [data_version] 自动生成函数
-- 说明：每次更新行数据时，数据版本(data_version)自动加1，用于乐观锁场景
-- 使用方法：create trigger data_version_triger before update on <table_name> for each row execute procedure data_version();
CREATE OR REPLACE FUNCTION data_version() RETURNS TRIGGER AS
$$
BEGIN
    new.data_version = new.data_version + 1;
    RETURN new;
END
$$
    LANGUAGE plpgsql;