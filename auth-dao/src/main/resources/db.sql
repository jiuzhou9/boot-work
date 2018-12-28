/*
 Navicat Premium Data Transfer

 Source Server         : 网关Dev(new)
 File Encoding         : utf-8

 Date: 10/19/2018 15:01:21 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `accesskey`
-- ----------------------------
DROP TABLE IF EXISTS `accesskey`;
CREATE TABLE `accesskey` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `key` varchar(40) NOT NULL COMMENT '用户创建的key',
    `name` varchar(120) DEFAULT NULL COMMENT 'key名称，界面显示用',
    `company_code` varchar(32) NOT NULL COMMENT '公司编码',
    `user_code` varchar(32) NOT NULL COMMENT '用户编码，即操作人',
    `secret` varchar(40) NOT NULL COMMENT '用于加密url参数，防止被篡改，用户可见',
    `private_secret` varchar(40) NOT NULL COMMENT '用于生成app令牌使用，用户不可见',
    `constraint` varchar(10240) DEFAULT NULL COMMENT '访问限制，允许访问的ip，支持模糊匹配，如10.1.2.*，多个ip之间使用逗号分隔',
    `expire_time` timestamp NOT NULL DEFAULT '2038-01-01 00:00:00' COMMENT '令牌过期时间，不包含该时间点',
    `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效，1为生效，0为失效',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key` (`key`),
    UNIQUE KEY `uk_key_company_code` (`key`,`company_code`),
    UNIQUE KEY `uk_name_company_code` (`name`,`company_code`),
    KEY `idx_company_code` (`company_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='访问信息，一个公司可以有多个accesskey，一个accesskey绑定多个服务';

-- ----------------------------
--  Records of `accesskey`
-- ----------------------------
BEGIN;
INSERT INTO `accesskey` VALUES ('1', '690da61e018a42e6a3e3b55bed84d4c8', '测试key', '4b1eaf04328d4fd8be11faba6a863369', '6c39a47f98364416b8e2e2306a8ea368', '3176fde1c63c4a38b85cb4a9da439a8f', 'd1ffc9f42e834b7c90ea6e5145ff91a4yJQ', null, '2038-01-01 00:00:00', '1', '2018-05-24 17:23:45', '2018-08-10 12:50:57'), ('2', 'a89f7c6114964168ad807b38d9a3269e', 'TAIPING', '98ad2fdf6afa4d4cbd3e9feb303157a1', 'f6a2da4753d447a08af42c0d53fe2d1c', 'c233e2aef0854dd493c4e044a9da4095', '1f435d27dc174161bb7b4b26af8088848Xs', null, '2038-01-01 00:00:00', '1', '2018-10-16 18:21:09', '2018-10-16 18:21:09');
COMMIT;

-- ----------------------------
--  Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `company_code` varchar(32) NOT NULL COMMENT '公司编码，32位UUID',
    `business_code` varchar(20) DEFAULT NULL COMMENT '公司的英文名称，例如顺丰，那么此处的值就是：sf',
    `name` varchar(120) NOT NULL COMMENT '名称，界面显示用',
    `business_license` varchar(30) NOT NULL COMMENT '营业执照编号',
    `fax` varchar(20) DEFAULT NULL COMMENT '传真',
    `address` varchar(120) DEFAULT NULL COMMENT '地址',
    `zipcode` varchar(10) DEFAULT NULL COMMENT '邮编',
    `website` varchar(120) DEFAULT NULL COMMENT '官网',
    `business_license_path` varchar(10240) DEFAULT NULL COMMENT '营业执照照片存储地址，JSON格式',
    `parent_company_code` varchar(32) DEFAULT NULL COMMENT '所属母公司id，不填表示该账号即为母公司',
    `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效，1为生效，0为失效',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_company_code` (`company_code`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='公司注册信息';

-- ----------------------------
--  Records of `company`
-- ----------------------------
BEGIN;
INSERT INTO `company` VALUES ('1', '4b1eaf04328d4fd8be11faba6a863369', 'sinoikea', '顺丰速运有限公司', 'string', 'string', '北京', 'string', 'string', 'string', 'string', '1', '2018-05-24 17:23:18', '2018-10-13 20:27:29'), ('2', '98ad2fdf6afa4d4cbd3e9feb303157a1', 'TAIPING', '中国工商银行', 'asdfghjk', '12345678', '北京', 'string', 'string', 'sdfghj', null, '1', '2018-10-16 18:02:37', '2018-10-17 11:36:16');
COMMIT;

-- ----------------------------
--  Table structure for `config`
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `key` varchar(60) NOT NULL COMMENT '配置的键名',
    `value` varchar(60) NOT NULL COMMENT '配置的键值',
    `company_code` varchar(32) NOT NULL COMMENT '公司编码',
    `user_code` varchar(32) NOT NULL COMMENT '用户编码，即操作人',
    `comments` varchar(1024) DEFAULT NULL COMMENT '备注',
    `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效，1为生效，0为失效',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_company_code` (`company_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司配置信息';

-- ----------------------------
--  Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `BLOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `CALENDAR_NAME` varchar(200) NOT NULL,
    `CALENDAR` blob NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `CRON_EXPRESSION` varchar(200) NOT NULL,
    `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `ENTRY_ID` varchar(95) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `INSTANCE_NAME` varchar(200) NOT NULL,
    `FIRED_TIME` bigint(13) NOT NULL,
    `SCHED_TIME` bigint(13) NOT NULL,
    `PRIORITY` int(11) NOT NULL,
    `STATE` varchar(16) NOT NULL,
    `JOB_NAME` varchar(200) DEFAULT NULL,
    `JOB_GROUP` varchar(200) DEFAULT NULL,
    `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
    `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `JOB_NAME` varchar(200) NOT NULL,
    `JOB_GROUP` varchar(200) NOT NULL,
    `DESCRIPTION` varchar(250) DEFAULT NULL,
    `JOB_CLASS_NAME` varchar(250) NOT NULL,
    `IS_DURABLE` varchar(1) NOT NULL,
    `IS_NONCONCURRENT` varchar(1) NOT NULL,
    `IS_UPDATE_DATA` varchar(1) NOT NULL,
    `REQUESTS_RECOVERY` varchar(1) NOT NULL,
    `JOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `LOCK_NAME` varchar(40) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `QRTZ_LOCKS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('SchedulerFactory', 'STATE_ACCESS'), ('SchedulerFactory', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `INSTANCE_NAME` varchar(200) NOT NULL,
    `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
    `CHECKIN_INTERVAL` bigint(13) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `QRTZ_SCHEDULER_STATE`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('SchedulerFactory', 'NON_CLUSTERED', '1539932489774', '7500');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `REPEAT_COUNT` bigint(7) NOT NULL,
    `REPEAT_INTERVAL` bigint(12) NOT NULL,
    `TIMES_TRIGGERED` bigint(10) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `STR_PROP_1` varchar(512) DEFAULT NULL,
    `STR_PROP_2` varchar(512) DEFAULT NULL,
    `STR_PROP_3` varchar(512) DEFAULT NULL,
    `INT_PROP_1` int(11) DEFAULT NULL,
    `INT_PROP_2` int(11) DEFAULT NULL,
    `LONG_PROP_1` bigint(20) DEFAULT NULL,
    `LONG_PROP_2` bigint(20) DEFAULT NULL,
    `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
    `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
    `BOOL_PROP_1` varchar(1) DEFAULT NULL,
    `BOOL_PROP_2` varchar(1) DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
    `SCHED_NAME` varchar(120) NOT NULL,
    `TRIGGER_NAME` varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `JOB_NAME` varchar(200) NOT NULL,
    `JOB_GROUP` varchar(200) NOT NULL,
    `DESCRIPTION` varchar(250) DEFAULT NULL,
    `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
    `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
    `PRIORITY` int(11) DEFAULT NULL,
    `TRIGGER_STATE` varchar(16) NOT NULL,
    `TRIGGER_TYPE` varchar(8) NOT NULL,
    `START_TIME` bigint(13) NOT NULL,
    `END_TIME` bigint(13) DEFAULT NULL,
    `CALENDAR_NAME` varchar(200) DEFAULT NULL,
    `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
    `JOB_DATA` blob,
    PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
    CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `quota`
-- ----------------------------
DROP TABLE IF EXISTS `quota`;
CREATE TABLE `quota` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `key` varchar(40) NOT NULL COMMENT '用户创建的key',
    `resource_code` varchar(32) NOT NULL COMMENT '服务编码，32位UUID',
    `quota` bigint(20) NOT NULL DEFAULT '0' COMMENT '最大使用次数',
    `dailyquota` bigint(20) NOT NULL DEFAULT '0' COMMENT '每日最大使用次数',
    `concurrency_limit` bigint(20) NOT NULL DEFAULT '0' COMMENT '每秒并发次数限制，主要通过分析服务器端日志统计实现',
    `begin_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务开始生效时间',
    `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务最后有效时间，包括当天时间',
    `type` int(11) NOT NULL DEFAULT '-1' COMMENT '服务计费的类型，-1：未设置，0：按起始截止时间，1：按调用次数，2：按车次',
    `company_code` varchar(32) NOT NULL COMMENT '公司编码',
    `user_code` varchar(32) NOT NULL COMMENT '用户编码，即操作人',
    `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效，1为生效，0为失效',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_service_code` (`resource_code`),
    KEY `idx_company_code` (`company_code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='服务配额信息';

-- ----------------------------
--  Records of `quota`
-- ----------------------------
BEGIN;
INSERT INTO `quota` VALUES ('1', '690da61e018a42e6a3e3b55bed84d4c8', '1175c4afd1404994aa2edf0f77996971', '9568', '0', '0', '2018-05-24 17:29:20', '2018-05-24 17:29:20', '1', '06bd6a0badf94c5c958449ad5e1c093a', '6c39a47f98364416b8e2e2306a8ea368', '1', '2018-05-24 17:29:20', '2018-05-29 18:42:04'), ('2', '690da61e018a42e6a3e3b55bed84d4c8', '484370b6b3084e83bebe0e3049decac8', '10000', '0', '0', '2018-09-10 14:09:53', '2018-09-10 14:09:53', '1', '06bd6a0badf94c5c958449ad5e1c093a', '6c39a47f98364416b8e2e2306a8ea368', '1', '2018-09-10 14:09:53', '2018-09-10 15:20:35'), ('3', '690da61e018a42e6a3e3b55bed84d4c8', 'e34dabce141745429961039d67dc8b87', '10000', '0', '0', '2018-09-10 15:25:01', '2018-09-10 15:25:01', '1', '06bd6a0badf94c5c958449ad5e1c093a', '6c39a47f98364416b8e2e2306a8ea368', '1', '2018-09-10 15:25:01', '2018-09-10 15:44:42'), ('4', '690da61e018a42e6a3e3b55bed84d4c8', '7c7978446ee8436aaf9c8a2a41be8464', '10000', '0', '0', '2018-09-10 15:44:34', '2018-09-10 15:44:34', '1', '06bd6a0badf94c5c958449ad5e1c093a', '6c39a47f98364416b8e2e2306a8ea368', '1', '2018-09-10 15:44:34', '2018-09-10 15:44:55'), ('5', 'a89f7c6114964168ad807b38d9a3269e', '1175c4afd1404994aa2edf0f77996971', '9568', '0', '0', '2018-05-24 17:29:20', '2018-05-24 17:29:20', '1', '98ad2fdf6afa4d4cbd3e9feb303157a1', 'f6a2da4753d447a08af42c0d53fe2d1c', '1', '2018-05-24 17:29:20', '2018-05-29 18:42:04'), ('6', 'a89f7c6114964168ad807b38d9a3269e', '484370b6b3084e83bebe0e3049decac8', '10000', '0', '0', '2018-09-10 14:09:53', '2018-09-10 14:09:53', '1', '98ad2fdf6afa4d4cbd3e9feb303157a1', 'f6a2da4753d447a08af42c0d53fe2d1c', '1', '2018-09-10 14:09:53', '2018-09-10 15:20:35'), ('7', 'a89f7c6114964168ad807b38d9a3269e', 'e34dabce141745429961039d67dc8b87', '10000', '0', '0', '2018-09-10 15:25:01', '2018-09-10 15:25:01', '1', '98ad2fdf6afa4d4cbd3e9feb303157a1', 'f6a2da4753d447a08af42c0d53fe2d1c', '1', '2018-09-10 15:25:01', '2018-09-10 15:44:42'), ('8', 'a89f7c6114964168ad807b38d9a3269e', '7c7978446ee8436aaf9c8a2a41be8464', '10000', '0', '0', '2018-09-10 15:44:34', '2018-09-10 15:44:34', '1', '98ad2fdf6afa4d4cbd3e9feb303157a1', 'f6a2da4753d447a08af42c0d53fe2d1c', '1', '2018-09-10 15:44:34', '2018-09-10 15:44:55');
COMMIT;

-- ----------------------------
--  Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `resource_code` varchar(32) NOT NULL COMMENT '资源编码，32位UUID',
    `name` varchar(1024) NOT NULL COMMENT '资源名称，用于界面显示',
    `uri` varchar(1024) NOT NULL COMMENT '资源位置',
    `type` varchar(256) NOT NULL COMMENT '资源类型，最多支持5段，每段之间使用:分隔，第1段必定为srn，空的段冒号也不允许省略，如srn:stockgo:::',
    `action` varchar(20) NOT NULL COMMENT '操作类型：GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE',
    `comments` varchar(512) DEFAULT NULL COMMENT '角色描述',
    `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效，1为生效，0为失效',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_resource_code` (`resource_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='资源信息';

-- ----------------------------
--  Records of `resource`
-- ----------------------------
BEGIN;
INSERT INTO `resource` VALUES ('1', '1175c4afd1404994aa2edf0f77996971', '测试api get示例', '/api/v1/test/testInput', 'srn:unicorn-api:::', 'POST', '测试', '1', '2018-05-24 13:48:52', '2018-09-10 14:56:12'), ('2', '484370b6b3084e83bebe0e3049decac8', '获取优化结果', '/api/v1/optimization/result', 'srn:unicorn-api:::result', 'POST', '获取优化结果', '1', '2018-09-10 14:07:41', '2018-09-25 14:41:13'), ('3', 'e34dabce141745429961039d67dc8b87', '优化请求接口', '/api/v1/optimization/request', 'srn:unicorn-api:::request', 'POST', '优化请求接口', '1', '2018-09-10 15:24:29', '2018-09-25 14:41:16'), ('4', '7c7978446ee8436aaf9c8a2a41be8464', '订单地址经纬度解析接口', '/api/v1/geocode/geo', 'srn:unicorn-api:::geo', 'POST', '根据客户端提交的明文地址信息解析出经纬度返回给客户端', '1', '2018-09-10 15:43:28', '2018-10-17 10:57:02');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `role_code` varchar(32) NOT NULL COMMENT '角色编码，32位UUID',
    `name` varchar(256) NOT NULL COMMENT '角色名称，用于界面显示',
    `company_code` varchar(32) NOT NULL COMMENT '公司编码',
    `comments` varchar(512) NOT NULL COMMENT '角色描述',
    `resourceacl` text COMMENT '权限访问列表，JSON格式',
    `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效，1为生效，0为失效',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
--  Table structure for `schema_version`
-- ----------------------------
DROP TABLE IF EXISTS `schema_version`;
CREATE TABLE `schema_version` (
    `version_rank` int(11) NOT NULL,
    `installed_rank` int(11) NOT NULL,
    `version` varchar(50) NOT NULL,
    `description` varchar(200) NOT NULL,
    `type` varchar(20) NOT NULL,
    `script` varchar(1000) NOT NULL,
    `checksum` int(11) DEFAULT NULL,
    `installed_by` varchar(100) NOT NULL,
    `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `execution_time` int(11) NOT NULL,
    `success` tinyint(1) NOT NULL,
    PRIMARY KEY (`version`),
    KEY `schema_version_vr_idx` (`version_rank`),
    KEY `schema_version_ir_idx` (`installed_rank`),
    KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `schema_version`
-- ----------------------------
BEGIN;
INSERT INTO `schema_version` VALUES ('1', '1', '1', 'init', 'SQL', 'V1__init.sql', '-1426889810', 'rhino', '2018-05-24 11:51:59', '245', '1'), ('2', '2', '2', 'alert resource table', 'SQL', 'V2__alert_resource_table.sql', '1025137427', 'rhino', '2018-09-14 10:51:54', '126', '1'), ('3', '3', '3', 'delete resource pre', 'SQL', 'V3__delete_resource_pre.sql', '-878286866', 'rhino', '2018-09-25 11:27:26', '97', '1'), ('4', '4', '4', 'add company businessCode', 'SQL', 'V4__add_company_businessCode.sql', '-1808934947', 'rhino', '2018-09-25 12:19:50', '95', '1'), ('5', '5', '5', 'alter company businessCode comments', 'SQL', 'V5__alter_company_businessCode_comments.sql', '-1788052861', 'rhino', '2018-10-10 18:27:49', '110', '1'), ('6', '6', '6', 'add quartz', 'SQL', 'V6__add_quartz.sql', '1623150834', 'rhino', '2018-10-16 23:44:31', '2832', '1');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一id，自增',
    `user_code` varchar(32) NOT NULL COMMENT '用户编码，32位UUID',
    `username` varchar(60) NOT NULL COMMENT '用户名',
    `password` varchar(255) NOT NULL COMMENT '密码',
    `surname` varchar(60) DEFAULT NULL COMMENT '姓氏',
    `givenname` varchar(60) DEFAULT NULL COMMENT '名字',
    `nickname` varchar(120) DEFAULT NULL COMMENT '昵称',
    `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
    `phone` varchar(20) DEFAULT NULL COMMENT '固定电话',
    `email` varchar(90) DEFAULT NULL COMMENT '邮箱',
    `company_code` varchar(32) DEFAULT NULL COMMENT '所属公司编码',
    `type` varchar(36) DEFAULT NULL COMMENT '账号来源，三方账号使用授权的Key标识',
    `parent_code` varchar(32) NOT NULL DEFAULT '' COMMENT '账号的父账号编码，不填表示该账号即为父账号，子账号可承担部分父账号的管理权限',
    `comments` varchar(512) DEFAULT NULL COMMENT '描述',
    `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效，1为生效，0为失效',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_code` (`user_code`),
    UNIQUE KEY `uk_mobile` (`mobile`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='公司用户注册信息';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', '6c39a47f98364416b8e2e2306a8ea368', 'test', '$2a$10$LpkFPxkniP2MaZ3NGVLQue0g8WzJf/8bbcvt0uT82BMa90THvJAuO', 'test', 'test', 'test', '18100000000', '', '123@qq.com', '8bd626e67c5642e69cee7a56d327b6e0', null, '', '测试用户', '1', '2018-05-24 17:08:05', '2018-08-23 14:39:08'), ('2', 'f6a2da4753d447a08af42c0d53fe2d1c', '中国工商银行', '$2a$10$YOE1ylAGXu6gTLkQb13V8.jM21nLPlLvISynqOb/p/Z3G9N31AkdC', '工商', '工商', '工商', 'string', '12345678910', '12346@qq.com', '98ad2fdf6afa4d4cbd3e9feb303157a1', null, '', '工商Dev用户', '1', '2018-10-16 18:00:21', '2018-10-16 18:02:37');
COMMIT;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_code` varchar(32) NOT NULL COMMENT '用户编码，32位UUID',
    `role_code` varchar(32) NOT NULL COMMENT '角色编码，32位UUID',
    `company_code` varchar(32) NOT NULL COMMENT '公司编码，32位UUID',
    `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效，1为生效，0为失效',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色对应表';

SET FOREIGN_KEY_CHECKS = 1;
