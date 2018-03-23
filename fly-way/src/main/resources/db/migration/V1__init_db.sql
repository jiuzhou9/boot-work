/*
 Navicat MySQL Data Transfer

 Source Server         : localmysql
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : 20180301

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 11/02/2017 11:25:43 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `app_role`
-- ----------------------------
DROP TABLE IF EXISTS `app_role`;
CREATE TABLE `app_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` int(11) DEFAULT NULL COMMENT 'appID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否有效：0无效1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='App角色中间表';

-- ----------------------------
--  Records of `app_role`
-- ----------------------------

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `desc` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否有效：0无效1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Records of `role`
-- ----------------------------

-- ----------------------------
--  Table structure for `role_sysresource`
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源ID',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否有效：0无效1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色资源表';

-- ----------------------------
--  Records of `role_sysresource`
-- ----------------------------

-- ----------------------------
--  Table structure for `sysresource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '资源名',
  `desc` varchar(250) DEFAULT NULL COMMENT '资源描述',
  `url` varchar(250) DEFAULT NULL COMMENT '资源url',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否有效：0无效1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
--  Records of `sysresource`
-- ----------------------------

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `password` varchar(100) DEFAULT NULL COMMENT '用户密码',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否删除 0是1否',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(32) NOT NULL COMMENT '用户邮箱',
  `address` varchar(100) NOT NULL COMMENT '联系地址',
  `mobile` varchar(15) NOT NULL COMMENT '用户手机',
  `reg_ip` bigint(20) DEFAULT NULL COMMENT '注册IP',
  `last_login_time` date DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` bigint(20) DEFAULT NULL COMMENT '最后登录IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `user`
-- ----------------------------

-- ----------------------------
--  Table structure for `user_app`
-- ----------------------------
DROP TABLE IF EXISTS `user_app`;
CREATE TABLE `user_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `secret` varchar(36) DEFAULT NULL COMMENT 'AppSecret',
  `name` varchar(20) DEFAULT NULL COMMENT 'AppName',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否有效：0无效1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户App表';

-- ----------------------------
--  Records of `user_app`
-- ----------------------------

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '修改时间',
  `available` tinyint(1) DEFAULT NULL COMMENT '是否有效：0无效1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 1;
