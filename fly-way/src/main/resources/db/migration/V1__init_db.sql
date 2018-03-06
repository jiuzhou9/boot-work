# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 39.106.47.236 (MySQL 5.6.29-mycat-1.6-RELEASE-20161028204710)
# Database: edb_a82492
# Generation Time: 2017-12-17 13:20:16 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


# Dump of table adjust_coefficient
# ------------------------------------------------------------

DROP TABLE IF EXISTS `adjust_coefficient`;

CREATE TABLE `adjust_coefficient` (
    `weekofyear` INT(2)         NOT NULL
    COMMENT '第几周',
    `adj_coef`   DECIMAL(16, 6) NOT NULL
    COMMENT '调整系数',
    `category1`  VARCHAR(36)    NOT NULL
    COMMENT '一级品类',
    `category2`  VARCHAR(36)    NOT NULL
    COMMENT '二级品类',
    `category3`  VARCHAR(36)    NOT NULL
    COMMENT '三级品类'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

# Dump of table daily_simulation_state
# ------------------------------------------------------------

DROP TABLE IF EXISTS `daily_simulation_state`;

CREATE TABLE `daily_simulation_state` (
    `begin_date`       DATE                DEFAULT NULL,
    `end_date`         DATE                DEFAULT NULL,
    `data_ready`       TINYINT(1) NOT NULL DEFAULT '0',
    `fixup_data`       TINYINT(1) NOT NULL DEFAULT '0',
    `create_info`      TINYINT(1) NOT NULL DEFAULT '0',
    `sale_range`       TINYINT(1) NOT NULL DEFAULT '0',
    `init_simulation`  TINYINT(1) NOT NULL DEFAULT '0',
    `lop`              TINYINT(1) NOT NULL DEFAULT '0',
    `simulation`       TINYINT(1) NOT NULL DEFAULT '0',
    `create_report`    TINYINT(1) NOT NULL DEFAULT '0',
    `lop_advise`       TINYINT(1) NOT NULL DEFAULT '0',
    `last_update_time` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY `uindex` (`begin_date`, `end_date`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table healthy_check_info_temp
# ------------------------------------------------------------

DROP TABLE IF EXISTS `healthy_check_info_temp`;

CREATE TABLE `healthy_check_info_temp` (
    `merchant_id`     VARCHAR(30)    NOT NULL
    COMMENT '商户id',
    `merged_stock_no` VARCHAR(10)    NOT NULL
    COMMENT '合并库存',
    `healthy_degree`  DECIMAL(16, 3) NOT NULL
    COMMENT '健康程度',
    `date_type`       INT(11) DEFAULT NULL
    COMMENT '数据类型 0表示半年，1表示一年',
    `summary_date`    DATE           NOT NULL
    COMMENT '仿真后计算健康程度的时间',
    UNIQUE KEY `unique_index` (`merchant_id`, `merged_stock_no`, `summary_date`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='存储健康检测各个仓库健康状况的临时表';



# Dump of table inventory_advise
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_advise`;

CREATE TABLE `inventory_advise` (
    `id`                        VARCHAR(36)      NOT NULL,
    `merchant_id`               VARCHAR(36)      NOT NULL,
    `sku_id`                    VARCHAR(36)               DEFAULT NULL,
    `merged_stock_no`           VARCHAR(10)               DEFAULT NULL,
    `stock_count`               INT(11)          NOT NULL DEFAULT '0'
    COMMENT '当前库存',
    `total_sale_count`          INT(11)          NOT NULL DEFAULT '0'
    COMMENT '总销量',
    `total_sale_7`              DECIMAL(16, 6)   NOT NULL DEFAULT '0.000000'
    COMMENT '平均销量',
    `total_sale_30`             DECIMAL(16, 6)   NOT NULL DEFAULT '0.000000'
    COMMENT '30天平均销量',
    `onroad_count`              INT(11)          NOT NULL DEFAULT '0'
    COMMENT '在途数量',
    `sum_mean`                  DECIMAL(16, 6)   NOT NULL DEFAULT '0.000000'
    COMMENT '预测均值',
    `sum_sigma`                 DECIMAL(16, 6)   NOT NULL DEFAULT '0.000000'
    COMMENT '标准差',
    `lop`                       INT(11)          NOT NULL DEFAULT '0'
    COMMENT '补货量',
    `rate`                      DECIMAL(16, 6)   NOT NULL DEFAULT '0.000000'
    COMMENT '现货率',
    `turn_around_day`           DECIMAL(16, 6)   NOT NULL DEFAULT '0.000000'
    COMMENT '周转天数',
    `create_time`               TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '记录创建日期',
    `date`                      DATE             NOT NULL
    COMMENT '当前日期',
    `advise_stockin_date`       DATE                      DEFAULT NULL
    COMMENT '最佳补货日期',
    `lop_info_json`             TEXT             NOT NULL,
    `lop_lowbound`              INT(11)          NOT NULL DEFAULT '0',
    `lop_upbound`               INT(11)          NOT NULL DEFAULT '0',
    `emergency_index`           DOUBLE                    DEFAULT NULL
    COMMENT '紧急程度，越大越紧急',
    `next_lop`                  INT(11)                   DEFAULT NULL
    COMMENT '下一个补货量',
    `next_order_count`          INT(11)                   DEFAULT '0',
    `next_next_order_count`     INT(11)                   DEFAULT '0',
    `next_next_lop`             INT(11)                   DEFAULT NULL
    COMMENT '下下个采购周期的采购补货点',
    `total_sale_3`              DECIMAL(16, 6)   NOT NULL DEFAULT '0.000000'
    COMMENT '平均销量',
    `coverday`                  INT(10) UNSIGNED NOT NULL DEFAULT '12'
    COMMENT '现货率',
    `vlt`                       INT(10) UNSIGNED NOT NULL DEFAULT '12'
    COMMENT '在途时长',
    `next_predict_sumsale`      INT(11)          NOT NULL DEFAULT '0'
    COMMENT '下一周期预测销量',
    `next_next_predict_sumsale` INT(11)          NOT NULL DEFAULT '0'
    COMMENT '下下周期预测销量',
    `selling_days`              DECIMAL(16, 2)            DEFAULT '0.00'
    COMMENT '可销库存覆盖天数',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;



# Dump of table inventory_change_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_change_history`;

CREATE TABLE `inventory_change_history` (
    `id`                    INT(11)          NOT NULL,
    `order_id`              VARCHAR(100)              DEFAULT NULL,
    `merchant_id`           VARCHAR(36)      NOT NULL,
    `sku_id`                VARCHAR(36)      NOT NULL,
    `create_time`           DATETIME(3)      NOT NULL,
    `stock_count_snapshoot` INT(11)          NOT NULL DEFAULT '0',
    `change_count`          INT(11)          NOT NULL DEFAULT '0',
    `fixup`                 INT(11)                   DEFAULT '0',
    `deta_id`               INT(11)          NOT NULL DEFAULT '0',
    `deta_time`             DATETIME                  DEFAULT NULL,
    `cal_stock_count`       INT(11)          NOT NULL DEFAULT '0',
    `change_type`           VARCHAR(50)               DEFAULT NULL,
    `available`             TINYINT(1)                DEFAULT '0',
    `stock_no`              VARCHAR(5)                DEFAULT NULL,
    `auto_id`               INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`auto_id`),
    KEY `create_time_index` (`create_time`),
    KEY `inventory_change_history_merchant_id_sku_id_stock_no_index` (`merchant_id`, `sku_id`, `stock_no`),
    KEY `inventory_change_history_id_merchant_id_pk` (`id`, `merchant_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;



# Dump of table inventory_check_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_check_history`;

CREATE TABLE `inventory_check_history` (
    `merchant_id`        VARCHAR(36) NOT NULL,
    `sku_id`             VARCHAR(36) NOT NULL,
    `order_id`           VARCHAR(36) NOT NULL,
    `first_check_count`  INT(11)     NOT NULL DEFAULT '0',
    `second_check_count` INT(11)     NOT NULL DEFAULT '0',
    `stock_no`           VARCHAR(100)         DEFAULT NULL,
    `check_time`         DATETIME(3) NOT NULL,
    KEY `stock_check_history_merchant_id_index` (`merchant_id`),
    KEY `stock_check_history_merchant_id_sku_id_index` (`merchant_id`, `sku_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table inventory_history_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_history_info`;

CREATE TABLE `inventory_history_info` (
    `merchant_id`      VARCHAR(36) NOT NULL,
    `sku_id`           VARCHAR(36) NOT NULL,
    `date`             DATE        NOT NULL,
    `count`            INT(11)              DEFAULT '0',
    `sale_count`       INT(11)     NOT NULL DEFAULT '0',
    `stockout_count`   INT(11)     NOT NULL DEFAULT '0',
    `purchasein_count` INT(11)     NOT NULL DEFAULT '0',
    `stockin_count`    INT(11)     NOT NULL DEFAULT '0',
    `stock_no`         VARCHAR(10) NOT NULL DEFAULT '1',
    UNIQUE KEY `uindex` (`merchant_id`, `sku_id`, `date`, `stock_no`),
    KEY `merchant_id_index` (`merchant_id`),
    KEY `inventory_history_info_merchant_id_sku_id_stock_no_index` (`merchant_id`, `sku_id`, `stock_no`),
    KEY `date_idx` (`date`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;



# Dump of table inventory_onroad
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_onroad`;

CREATE TABLE `inventory_onroad` (
    `id`                    BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT '在途记录唯一标识，只要在一个商户里唯一就行',
    `merchant_id`           VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '商户id',
    `sku_id`                VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT 'sku id',
    `onroad_count`          INT(11)             NOT NULL DEFAULT '0'
    COMMENT '在途库存',
    `stock_count`           INT(11)             NOT NULL DEFAULT '0'
    COMMENT '当前库存',
    `merged_stock_no`       VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '合并仓库编号',
    `update_time`           TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '记录更新时间',
    `sellable_stock_clount` INT(11)             NOT NULL DEFAULT '0'
    COMMENT '可销库存',
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_key` (`merchant_id`, `sku_id`, `merged_stock_no`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table inventory_onroad_base
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_onroad_base`;

CREATE TABLE `inventory_onroad_base` (
    `id`                    BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT '在途记录唯一标识，只要在一个商户里唯一就行',
    `merchant_id`           VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '商户ID',
    `sku_id`                VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT 'SKU ID',
    `onroad_count`          INT(11)             NOT NULL DEFAULT '0'
    COMMENT '在途库存',
    `stock_count`           INT(11)             NOT NULL DEFAULT '0'
    COMMENT '当前库存',
    `stock_no`              VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '仓库编号',
    `update_time`           TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '记录更新时间',
    `sellable_stock_clount` INT(11)             NOT NULL DEFAULT '0'
    COMMENT '可销库存',
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_key` (`merchant_id`, `sku_id`, `stock_no`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table inventory_onroad_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_onroad_detail`;

CREATE TABLE `inventory_onroad_detail` (
    `id`              BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT '主键id',
    `merchant_id`     VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '商户id',
    `date`            DATE                         DEFAULT NULL
    COMMENT '当前日期',
    `sku_id`          VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT 'sku id',
    `onroad_count`    INT(11)             NOT NULL DEFAULT '0'
    COMMENT '在途库存',
    `stock_count`     INT(11)             NOT NULL DEFAULT '0'
    COMMENT '当前库存',
    `purchase_count`  INT(11)                      DEFAULT NULL
    COMMENT '今日采购量',
    `stockin_count`   INT(11)             NOT NULL DEFAULT '0',
    `merged_stock_no` VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '合并仓库编号',
    `update_time`     TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '记录更新时间',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='记录在途明细';



# Dump of table inventory_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_product`;

CREATE TABLE `inventory_product` (
    `product_id`        BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT '产品序列号',
    `merchant_id`       VARCHAR(36)                  DEFAULT NULL
    COMMENT '商户id',
    `product_name`      VARCHAR(150)                 DEFAULT NULL
    COMMENT '产品名称',
    `product_spec`      VARCHAR(255)                 DEFAULT NULL
    COMMENT '产品规格',
    `brand_code`        VARCHAR(50)                  DEFAULT NULL
    COMMENT '产品品牌编码',
    `brand_name`        VARCHAR(100)                 DEFAULT NULL
    COMMENT '品牌名称',
    `spu_id`            VARCHAR(36)                  DEFAULT NULL
    COMMENT 'spuid',
    `sku_id`            VARCHAR(36)                  DEFAULT NULL
    COMMENT 'skuid',
    `bar_code`          VARCHAR(100)                 DEFAULT NULL
    COMMENT '条形码',
    `pic_url`           VARCHAR(255)                 DEFAULT NULL
    COMMENT '图片路径',
    `reference_price`   FLOAT                        DEFAULT '0'
    COMMENT '参考价格',
    `product_cost`      DECIMAL(12, 2)               DEFAULT '0.00'
    COMMENT '成本',
    `supplier_code`     VARCHAR(50)                  DEFAULT NULL
    COMMENT '供应商编号',
    `supplier_name`     VARCHAR(100)                 DEFAULT NULL
    COMMENT '供应商名称',
    `status`            INT(11)             NOT NULL DEFAULT '1001'
    COMMENT '产品状态，1001：正常，2001：停用，2002：停产，2003：缺货，2004：过期，2005：停售',
    `stock_no`          VARCHAR(36)                  DEFAULT NULL
    COMMENT '实体仓编号',
    `user_price`        FLOAT                        DEFAULT NULL
    COMMENT '用户填写的价格',
    `min_purchase_unit` INT(3)                       DEFAULT NULL
    COMMENT '最小采购单位',
    `update_time`       TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '更新时间',
    `category1`         VARCHAR(255)                 DEFAULT NULL,
    `category2`         VARCHAR(255)                 DEFAULT NULL,
    `category3`         VARCHAR(255)                 DEFAULT NULL,
    PRIMARY KEY (`product_id`),
    UNIQUE KEY `pro_unique_index` (`merchant_id`, `sku_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='商户产品表';



# Dump of table inventory_purchase_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_purchase_history`;

CREATE TABLE `inventory_purchase_history` (
    `order_id`             VARCHAR(100) NOT NULL,
    `merchant_id`          VARCHAR(36)  NOT NULL,
    `sku_id`               VARCHAR(36)           DEFAULT NULL,
    `deta_day`             INT(11)      NOT NULL DEFAULT '0',
    `in_check_date`        DATETIME              DEFAULT NULL,
    `in_count`             INT(11)      NOT NULL DEFAULT '0',
    `create_time`          DATETIME     NOT NULL,
    `purchase_date`        DATETIME              DEFAULT NULL,
    `purchase_verify_date` DATETIME              DEFAULT NULL,
    `stock_no`             VARCHAR(5)   NOT NULL,
    KEY `index` (`merchant_id`, `sku_id`, `stock_no`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table inventory_sale_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_sale_history`;

CREATE TABLE `inventory_sale_history` (
    `merchant_id`       VARCHAR(36)    NOT NULL DEFAULT ''
    COMMENT '商户id',
    `order_id`          VARCHAR(50)    NOT NULL DEFAULT ''
    COMMENT '订单id',
    `order_time`        DATETIME       NOT NULL
    COMMENT '订货日期',
    `send_order_time`   DATETIME                DEFAULT NULL
    COMMENT '发货日期',
    `order_amount`      DECIMAL(16, 2) NOT NULL DEFAULT '0.00'
    COMMENT '订单总金额',
    `pay_status`        VARCHAR(10)             DEFAULT NULL
    COMMENT '支付状态',
    `send_status`       VARCHAR(10)             DEFAULT NULL
    COMMENT '发货状态',
    `order_count`       INT(11)                 DEFAULT NULL
    COMMENT '购买数量',
    `order_cost`        DECIMAL(16, 2)          DEFAULT NULL
    COMMENT '成本价格',
    `address`           VARCHAR(500)   NOT NULL DEFAULT ''
    COMMENT '详细地址',
    `shop_code`         VARCHAR(36)    NOT NULL
    COMMENT '店铺编号',
    `pro_total_cost`    DECIMAL(16, 2) NOT NULL
    COMMENT '产品总金额',
    `actual_total_in`   DECIMAL(16, 2) NOT NULL
    COMMENT '实收金额',
    `stock_no`          VARCHAR(10)    NOT NULL DEFAULT ''
    COMMENT '仓库编号',
    `sales_total`       DECIMAL(16, 2) NOT NULL
    COMMENT '打折金额',
    `province`          VARCHAR(150)   NOT NULL DEFAULT ''
    COMMENT '省',
    `city`              VARCHAR(200)            DEFAULT ''
    COMMENT '市',
    `county`            VARCHAR(300)   NOT NULL DEFAULT ''
    COMMENT '县/区',
    `sku_id`            VARCHAR(36)    NOT NULL DEFAULT '',
    `default_stock_no`  VARCHAR(10)    NOT NULL DEFAULT ''
    COMMENT '默认发货仓库编号',
    `shop_product_name` VARCHAR(300)            DEFAULT NULL
    COMMENT '网店品名',
    KEY `merchant_id_index` (`merchant_id`),
    KEY `order_id_index` (`order_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COMMENT ='历史销售记录';



# Dump of table inventory_suit_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_suit_detail`;

CREATE TABLE `inventory_suit_detail` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT 'id',
    `sku_id`      VARCHAR(36)                  DEFAULT NULL
    COMMENT 'skuid',
    `merchant_id` VARCHAR(36)                  DEFAULT NULL,
    `sku_count`   INT(11)                      DEFAULT '0'
    COMMENT '套装单品数量',
    `suit_id`     VARCHAR(50)         NOT NULL DEFAULT ''
    COMMENT '套装ID',
    `create_time` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `id` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='套装明细信息';



# Dump of table inventory_vlt
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_vlt`;

CREATE TABLE `inventory_vlt` (
    `id`              BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT '主键',
    `merchant_id`     VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '商户id',
    `spu_id`          VARCHAR(36)                  DEFAULT NULL
    COMMENT 'spuid',
    `sku_id`          VARCHAR(36)                  DEFAULT NULL
    COMMENT 'skuid',
    `supplier_code`   VARCHAR(50)                  DEFAULT NULL
    COMMENT '供应商编号',
    `vlt`             INT(10) UNSIGNED    NOT NULL DEFAULT '12'
    COMMENT '在途时长',
    `stock_no`        VARCHAR(10)                  DEFAULT NULL,
    `create_time`     TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `merged_stock_no` VARCHAR(36)                  DEFAULT NULL,
    KEY `id` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table inventory_warehouse
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_warehouse`;

CREATE TABLE `inventory_warehouse` (
    `id`               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT '编号',
    `merchant_id`      VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '商户id',
    `stock_no`         VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '仓库编号',
    `stock_name`       VARCHAR(255)                 DEFAULT NULL
    COMMENT '仓库名称',
    `stock_type`       VARCHAR(50)                  DEFAULT NULL
    COMMENT '仓库类型',
    `last_update_time` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `merchant_id_stock_no` (`merchant_id`, `stock_no`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='商户仓库表';



# Dump of table merchant_shop
# ------------------------------------------------------------

DROP TABLE IF EXISTS `merchant_shop`;

CREATE TABLE `merchant_shop` (
    `id`            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `merchant_id`   VARCHAR(36)         NOT NULL
    COMMENT '商户编号',
    `shop_code`     VARCHAR(30)         NOT NULL
    COMMENT '店铺编号',
    `shop_name`     VARCHAR(36)         NOT NULL
    COMMENT '店铺名称',
    `is_available`  INT(1)              NOT NULL
    COMMENT '是否可用',
    `platform_type` VARCHAR(36)         NOT NULL
    COMMENT '平台类型',
    `create_time`   TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`   TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '更新时间',
    UNIQUE KEY `id` (`id`),
    UNIQUE KEY `merchant_id` (`merchant_id`, `shop_code`, `shop_name`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='供应商店铺';



# Dump of table merge_stock_no
# ------------------------------------------------------------

DROP TABLE IF EXISTS `merge_stock_no`;

CREATE TABLE `merge_stock_no` (
    `merchant_id`       VARCHAR(36)  NOT NULL,
    `merged_stock_no`   VARCHAR(36)  NOT NULL,
    `stock_no`          VARCHAR(36)  NOT NULL,
    `merged_stock_name` VARCHAR(255) NOT NULL DEFAULT '',
    `stock_type`        VARCHAR(50)           DEFAULT NULL,
    `stock_name`        VARCHAR(255)          DEFAULT NULL,
    `status`            INT(10) UNSIGNED      DEFAULT '0',
    `last_update_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    COMMENT '最后更新时间',
    KEY `merchant_id_index` (`merchant_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table order_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item` (
    `merchant_id`          VARCHAR(36)    NOT NULL DEFAULT ''
    COMMENT '商户ID',
    `order_id`             VARCHAR(50)    NOT NULL DEFAULT ''
    COMMENT '订单ID',
    `sku_id`               VARCHAR(36)             DEFAULT NULL
    COMMENT 'skuid',
    `count`                INT(11)                 DEFAULT NULL
    COMMENT '购买数量',
    `bar_code`             VARCHAR(100)            DEFAULT NULL
    COMMENT '条形码',
    `stock_no`             VARCHAR(10)    NOT NULL DEFAULT ''
    COMMENT '仓库编号',
    `default_stock_no`     VARCHAR(10)    NOT NULL DEFAULT ''
    COMMENT '默认发货仓库编号',
    `original_price`       DECIMAL(16, 2)          DEFAULT NULL
    COMMENT '原价',
    `actual_sale_price`    DECIMAL(16, 2)          DEFAULT NULL
    COMMENT '成交价',
    `product_price`        DECIMAL(16, 2)          DEFAULT NULL
    COMMENT 'sku成本',
    `sales_avg`            DECIMAL(16, 2) NOT NULL
    COMMENT '平均打折金额',
    `platform_sales_avg`   DECIMAL(16, 2) NOT NULL
    COMMENT '平台承担优惠平均金额',
    `merchant_giving_avg`  DECIMAL(16, 2) NOT NULL
    COMMENT '商家承担优惠平均金额',
    `shop_product_name`    VARCHAR(300)            DEFAULT NULL
    COMMENT '网店品名',
    `order_promotion_info` VARCHAR(500)            DEFAULT NULL
    COMMENT '订单促销信息',
    `amount`               DECIMAL(16, 2)          DEFAULT '0.00'
    COMMENT 'sku成交金额',
    `software_name`        VARCHAR(150)            DEFAULT NULL
    COMMENT '软件品名',
    `software_spec`        VARCHAR(100)            DEFAULT NULL
    COMMENT '软件规格',
    KEY `order_id` (`order_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='历史订单行';



# Dump of table promotion_attached_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `promotion_attached_product`;

CREATE TABLE `promotion_attached_product` (
    `promotion_code` VARCHAR(36) NOT NULL
    COMMENT '促销编号',
    `sku_id`         VARCHAR(36) NOT NULL,
    `bar_code`       VARCHAR(100) DEFAULT NULL
    COMMENT '条形码',
    `product_name`   VARCHAR(150) DEFAULT NULL
    COMMENT '产品名称'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='附赠产品';



# Dump of table promotion_coefficient
# ------------------------------------------------------------

DROP TABLE IF EXISTS `promotion_coefficient`;

CREATE TABLE `promotion_coefficient` (
    `merchant_id`     VARCHAR(255)   DEFAULT NULL,
    `category1`       VARCHAR(255)   DEFAULT NULL,
    `category2`       VARCHAR(255)   DEFAULT NULL,
    `category3`       VARCHAR(255)   DEFAULT NULL,
    `sales_type`      VARCHAR(255)   DEFAULT NULL,
    `coef_intercept`  DECIMAL(16, 6) DEFAULT '0.000000',
    `coef_rule_value` DECIMAL(16, 6) DEFAULT '0.000000',
    `coef_price`      DECIMAL(16, 6) DEFAULT '0.000000',
    `coef_istaobao`   DECIMAL(16, 6) DEFAULT '0.000000',
    `coef_ishitao`    DECIMAL(16, 6) DEFAULT '0.000000',
    `coef_isjhs`      DECIMAL(16, 6) DEFAULT '0.000000',
    `coef_istop`      DECIMAL(16, 6) DEFAULT '0.000000',
    `coef_iswap`      DECIMAL(16, 6) DEFAULT '0.000000'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table promotion_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `promotion_product`;

CREATE TABLE `promotion_product` (
    `promotion_code`        VARCHAR(36) NOT NULL
    COMMENT '促销编号',
    `sku_id`                VARCHAR(36) NOT NULL,
    `bar_code`              VARCHAR(100)   DEFAULT NULL
    COMMENT '条形码',
    `product_name`          VARCHAR(150)   DEFAULT NULL
    COMMENT '产品名称',
    `reference_price`       DECIMAL(16, 2) DEFAULT '0.00'
    COMMENT '售价',
    `promotion_price`       DECIMAL(16, 2) DEFAULT '0.00'
    COMMENT '促销价',
    `promotion_stock_count` INT(11)        DEFAULT NULL
    COMMENT '备货量',
    `status`                VARCHAR(10)    DEFAULT NULL
    COMMENT '0代表未选择，1表示选择',
    UNIQUE KEY `promotion_code` (`promotion_code`, `sku_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='促销产品';



# Dump of table purchase_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `purchase_order`;

CREATE TABLE `purchase_order` (
    `order_id`        BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT '主键',
    `merchant_id`     VARCHAR(30)         NOT NULL
    COMMENT '商户id',
    `purchase_num`    INT(11)             NOT NULL
    COMMENT '购买数量',
    `comments`        VARCHAR(300)                 DEFAULT NULL
    COMMENT '备注',
    `po_id`           VARCHAR(50)         NOT NULL
    COMMENT '采购单id',
    `po_confirm_time` DATE                         DEFAULT NULL
    COMMENT '确认时间',
    `purchase_type`   VARCHAR(20)         NOT NULL DEFAULT ''
    COMMENT '采购类型1补货采购 2 备货采购 3 新品采购 4 其它采购',
    `operation_time`  DATETIME                     DEFAULT NULL
    COMMENT '操作时间',
    `po_inspector`    VARCHAR(20)         NOT NULL
    COMMENT '检查采购订单人',
    `purchase_status` INT(11)                      DEFAULT NULL
    COMMENT '采购单状态：1代表已上传，0代表未上传',
    `product_id`      INT(11)             NOT NULL
    COMMENT '产品id',
    `checked_flag`    INT(11)                      DEFAULT NULL
    COMMENT '0代表未选中，1代表已选中',
    `advise_id`       VARCHAR(36)         NOT NULL
    COMMENT '库存建议id',
    `supplier_code`   VARCHAR(100)        NOT NULL
    COMMENT '供应商code',
    `supplier_name`   VARCHAR(150)        NOT NULL
    COMMENT '供应商名称',
    `stock_no`        VARCHAR(100)        NOT NULL
    COMMENT '仓库',
    `bar_code`        VARCHAR(100)                 DEFAULT NULL
    COMMENT '二维码',
    `purchase_price`  DECIMAL(10, 2)               DEFAULT NULL
    COMMENT '单价',
    `if_adopted`      INT(11)             NOT NULL
    COMMENT '是否采纳建议',
    PRIMARY KEY (`order_id`),
    KEY `product_id` (`product_id`),
    KEY `idx_adviseid` (`advise_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='采购单';

# Dump of table replishment_cycle
# ------------------------------------------------------------

DROP TABLE IF EXISTS `replishment_cycle`;

CREATE TABLE `replishment_cycle` (
    `id`                    BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `cycle_days`            INT(11)             NOT NULL
    COMMENT '采购周期',
    `next_cycle_start_date` TIMESTAMP           NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
    COMMENT '下一个采购周期起始日期',
    `if_holiday_putoff`     INT(2)              NOT NULL DEFAULT '0'
    COMMENT '节假日是否顺延',
    `create_time`           TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '创建时间',
    `update_time`           TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '更新时间',
    `merchant_id`           VARCHAR(36)         NOT NULL DEFAULT ''
    COMMENT '商户ID',
    `type`                  INT(11)                      DEFAULT NULL
    COMMENT '1代表概念周期，2代表具体的周转天数',
    `cycle_flag`            VARCHAR(15)                  DEFAULT NULL
    COMMENT '周转类型标志，day表示一天，week表示一周，session表示季度，half-year表示半年',
    UNIQUE KEY `id` (`id`),
    UNIQUE KEY `merchant_id` (`merchant_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;



# Dump of table sale_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sale_order`;

CREATE TABLE `sale_order` (
    `merchant_id`              VARCHAR(36)    NOT NULL DEFAULT ''
    COMMENT '商户ID',
    `order_id`                 VARCHAR(50)    NOT NULL DEFAULT ''
    COMMENT '订单ID',
    `order_date`               DATETIME       NOT NULL
    COMMENT '订货日期',
    `delivery_date`            DATE                    DEFAULT NULL
    COMMENT '发货日期',
    `order_total_cost`         DECIMAL(16, 2)          DEFAULT NULL
    COMMENT '成本价格',
    `pay_status`               VARCHAR(10)             DEFAULT NULL
    COMMENT '支付状态',
    `delivery_status`          VARCHAR(10)             DEFAULT NULL
    COMMENT '发货状态',
    `shop_code`                VARCHAR(36)    NOT NULL
    COMMENT '店铺编号',
    `pro_total_cost`           DECIMAL(16, 2) NOT NULL
    COMMENT '产品总金额',
    `actual_total_in`          DECIMAL(16, 2) NOT NULL
    COMMENT '实收金额',
    `order_total_in`           DECIMAL(16, 2) NOT NULL
    COMMENT '订单总金额',
    `sales_total`              DECIMAL(16, 2) NOT NULL
    COMMENT '打折金额',
    `platform_sales_total`     DECIMAL(16, 2) NOT NULL
    COMMENT '平台承担优惠金额',
    `merchant_giving_total`    DECIMAL(16, 2) NOT NULL
    COMMENT '商家承担优惠金额',
    `province`                 VARCHAR(150)   NOT NULL DEFAULT ''
    COMMENT '省',
    `city`                     VARCHAR(200)   NOT NULL DEFAULT ''
    COMMENT '市',
    `county`                   VARCHAR(500)   NOT NULL DEFAULT ''
    COMMENT '县/区',
    `address`                  VARCHAR(200)   NOT NULL
    COMMENT '详细地址',
    `deal_status`              VARCHAR(10)             DEFAULT NULL
    COMMENT '处理状态',
    `platform_delivery_status` VARCHAR(30)             DEFAULT NULL
    COMMENT '平台发货状态',
    `check_pro_status`         VARCHAR(10)             DEFAULT NULL
    COMMENT '验货状态',
    `order_type`               VARCHAR(10)             DEFAULT NULL
    COMMENT '订单类型',
    `actual_deliver_cost`      DECIMAL(16, 2)          DEFAULT '0.00'
    COMMENT '实收快递费',
    `actual_deliver_pay`       DECIMAL(16, 2)          DEFAULT '0.00'
    COMMENT '实付快递费',
    `origin_order_sales`       DECIMAL(16, 2)          DEFAULT '0.00'
    COMMENT '整单优惠',
    `order_promotion_info`     VARCHAR(500)            DEFAULT NULL
    COMMENT '订单促销信息',
    UNIQUE KEY `order_id` (`order_id`),
    KEY `merchant_id_index` (`merchant_id`),
    KEY `order_id_index` (`order_id`),
    KEY `order_date_idx` (`order_date`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='历史订单头';



# Dump of table sale_order_price
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sale_order_price`;

CREATE TABLE `sale_order_price` (
    `merchant_id`       VARCHAR(36) NOT NULL
    COMMENT '商铺id',
    `sku_id`            VARCHAR(36) NOT NULL DEFAULT '',
    `origin_sale_price` DECIMAL(16, 2)       DEFAULT NULL
    COMMENT '原始售价',
    `actual_sale_price` DECIMAL(16, 2)       DEFAULT NULL
    COMMENT '实际售价'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;



# Dump of table sales_client_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sales_client_type`;

CREATE TABLE `sales_client_type` (
    `promotion_code` VARCHAR(36) NOT NULL
    COMMENT '促销编号',
    `client_type`    VARCHAR(50) NOT NULL
    COMMENT '终端'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='促销渠道';



# Dump of table sales_promotion
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sales_promotion`;

CREATE TABLE `sales_promotion` (
    `id`                      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `merchant_id`             VARCHAR(36)         NOT NULL
    COMMENT '商户编号',
    `promotion_code`          VARCHAR(36) NOT NULL
    COMMENT '促销编号',
    `promotion_name`          VARCHAR(50) NOT NULL
    COMMENT '促销名称',
    `date_type`               VARCHAR(20)          DEFAULT NULL
    COMMENT '日期类型',
    `isbuyedall`              INT(1)               DEFAULT NULL
    COMMENT '是否购买全部',
    `attached_type`           VARCHAR(20)          DEFAULT NULL
    COMMENT '赠送类型',
    `start_date`              TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    COMMENT '开始日期',
    `end_date`                TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    COMMENT '结束日期',
    `client_type`             VARCHAR(20)          DEFAULT NULL
    COMMENT '终端类型',
    `rule_value`              VARCHAR(30)          DEFAULT NULL
    COMMENT '规则值',
    `promotion_action_source` VARCHAR(30)          DEFAULT NULL
    COMMENT '促销信息来源',
    `sales_type`              VARCHAR(10)          DEFAULT NULL
    COMMENT '促销类型',
    `sales_scope`             VARCHAR(36)          DEFAULT NULL
    COMMENT '促销范围',
    `sales_status`            VARCHAR(10)          DEFAULT NULL
    COMMENT '促销状态',
    `create_time`             TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '创建日期',
    UNIQUE KEY `id` (`id`),
    UNIQUE KEY `promotion_code` (`promotion_code`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='促销信息';



# Dump of table sales_promotion_channel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sales_promotion_channel`;

CREATE TABLE `sales_promotion_channel` (
    `promotion_code`    VARCHAR(36) NOT NULL
    COMMENT '促销编号',
    `promotion_channel` VARCHAR(50) NOT NULL
    COMMENT '促销渠道'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;



# Dump of table sales_promotion_shop
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sales_promotion_shop`;

CREATE TABLE `sales_promotion_shop` (
    `promotion_code` VARCHAR(36) NOT NULL
    COMMENT '促销编号',
    `shop_code`      VARCHAR(30) NOT NULL
    COMMENT '店铺编号',
    `shop_name`      VARCHAR(36) DEFAULT NULL
    COMMENT '店铺名称'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='促销店铺';



# Dump of table sales_quantity_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sales_quantity_detail`;

CREATE TABLE `sales_quantity_detail` (
    `id`                   BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `merchant_id`          VARCHAR(36)         NOT NULL
    COMMENT '商户编号',
    `sku_id`               VARCHAR(36)         NOT NULL,
    `total_sale_7`         INT(10)             NOT NULL DEFAULT '0'
    COMMENT '7日总销量',
    `total_sale_30`        INT(11)             NOT NULL DEFAULT '0'
    COMMENT '30日总销量',
    `total_sale_yesterday` INT(10)             NOT NULL DEFAULT '0'
    COMMENT '昨日销量',
    `create_time`          TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`          TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '更新时间',
    `stock_no`             VARCHAR(10)         NOT NULL DEFAULT '',
    `single_sale_30`       INT(11)             NOT NULL DEFAULT '0'
    COMMENT '单品30日总销量',
    `single_sale_7`        INT(10)             NOT NULL DEFAULT '0'
    COMMENT '单品7日总销量',
    UNIQUE KEY `id` (`id`),
    UNIQUE KEY `uk_sku_stock` (`sku_id`, `stock_no`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='销量数据';



# Dump of table simulation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `simulation`;

CREATE TABLE `simulation` (
    `id`                 INT(11)        NOT NULL AUTO_INCREMENT,
    `merchant_id`        VARCHAR(36)    NOT NULL,
    `sku_id`             VARCHAR(100)   NOT NULL,
    `date`               DATE                    DEFAULT NULL,
    `sale_count`         INT(11)        NOT NULL DEFAULT '0',
    `sale_count_sim`     INT(11)        NOT NULL DEFAULT '0'
    COMMENT '用来做仿真的销量',
    `stockin_count`      INT(11)        NOT NULL DEFAULT '0',
    `stock_count`        INT(11)        NOT NULL DEFAULT '0',
    `lop`                DECIMAL(16, 0)          DEFAULT '0',
    `onroad_count`       INT(11)        NOT NULL DEFAULT '0',
    `simulation_stock`   INT(11)        NOT NULL DEFAULT '0',
    `simulation_stockin` INT(11)        NOT NULL DEFAULT '0',
    `deta_count`         INT(11)        NOT NULL DEFAULT '0',
    `unsatisfied_count`  INT(11)        NOT NULL DEFAULT '0',
    `fixup_onroad_count` INT(11)        NOT NULL DEFAULT '0',
    `merged_stock_no`    VARCHAR(10)    NOT NULL,
    `next_order_date`    DATE                    DEFAULT NULL,
    `sales_cash`         DECIMAL(16, 2) NOT NULL
    COMMENT '打折金额',
    `original_price`     DECIMAL(16, 2) NOT NULL
    COMMENT '原价',
    `trans_price`        DECIMAL(16, 2) NOT NULL
    COMMENT '成交价',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uindex` (`merchant_id`, `sku_id`, `date`, `merged_stock_no`),
    KEY `merchant_id` (`merchant_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table simulation_promotion
# ------------------------------------------------------------

DROP TABLE IF EXISTS `simulation_promotion`;

CREATE TABLE `simulation_promotion` (
    `merchant_id`    VARCHAR(36) NOT NULL
    COMMENT '商铺id',
    `promotion_code` VARCHAR(36) NOT NULL
    COMMENT '促销编号',
    `promotion_name` VARCHAR(50) NOT NULL
    COMMENT '促销名称',
    `start_date`     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '开始日期',
    `end_date`       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '结束日期',
    `sales_type`     VARCHAR(10)          DEFAULT ''
    COMMENT '促销类型',
    `rule_value`     VARCHAR(30)          DEFAULT NULL
    COMMENT '规则值',
    `istaobao`       TINYINT(4)  NOT NULL DEFAULT '0'
    COMMENT '是否在taobao',
    `ishitao`        TINYINT(4)  NOT NULL DEFAULT '0'
    COMMENT '是否在hitao',
    `isjhs`          TINYINT(4)  NOT NULL DEFAULT '0'
    COMMENT '是否在jhs',
    `istop`          TINYINT(4)  NOT NULL DEFAULT '0'
    COMMENT '是否在top',
    `iswap`          TINYINT(4)  NOT NULL DEFAULT '0'
    COMMENT '是否在wap',
    `sku_id`         VARCHAR(36) NOT NULL DEFAULT ''
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;



# Dump of table simulation_report
# ------------------------------------------------------------

DROP TABLE IF EXISTS `simulation_report`;

CREATE TABLE `simulation_report` (
    `merchant_id`                VARCHAR(36)    NOT NULL,
    `sku_id`                     VARCHAR(36)    NOT NULL,
    `simulation_begin_day`       DATE           NOT NULL,
    `simulation_end_day`         DATE           NOT NULL,
    `sale_count`                 INT(11)        NOT NULL DEFAULT '0',
    `avg_org_stock_count`        DECIMAL(16, 4) NOT NULL DEFAULT '0.0000',
    `avg_simulation_stock_count` DECIMAL(16, 4) NOT NULL DEFAULT '0.0000',
    `merged_stock_no`            VARCHAR(10)    NOT NULL,
    `create_time`                TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`                TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY `index` (`merchant_id`, `sku_id`, `merged_stock_no`),
    KEY `beginday_index` (`simulation_begin_day`),
    KEY `end_day_index` (`simulation_end_day`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table sku_sale_info_range
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sku_sale_info_range`;

CREATE TABLE `sku_sale_info_range` (
    `merchant_id`          VARCHAR(36)    NOT NULL,
    `sku_id`               VARCHAR(36)    NOT NULL,
    `min_time`             DATETIME       NOT NULL,
    `max_time`             DATETIME       NOT NULL,
    `merged_stock_no`      VARCHAR(10)    NOT NULL,
    `avg_sale_count`       DECIMAL(16, 6) NOT NULL DEFAULT '0.000000',
    `total_avg_sale_count` DECIMAL(16, 6) NOT NULL DEFAULT '0.000000',
    UNIQUE KEY `uindex` (`merchant_id`, `sku_id`, `merged_stock_no`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;



# Dump of table sku_sales
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sku_sales`;

CREATE TABLE `sku_sales` (
    `id`            INT(11) UNSIGNED NOT NULL AUTO_INCREMENT
    COMMENT '自增主键',
    `merchant_id`   VARCHAR(36)      NOT NULL DEFAULT ''
    COMMENT '商户的ID',
    `sku_id`        VARCHAR(36)      NOT NULL DEFAULT ''
    COMMENT 'SKU_ID',
    `total_sale_1`  INT(11)          NOT NULL DEFAULT '0'
    COMMENT '昨日销量',
    `total_sale_3`  INT(11)          NOT NULL DEFAULT '0'
    COMMENT '3日销量',
    `total_sale_7`  INT(11)          NOT NULL DEFAULT '0'
    COMMENT '7日销量',
    `total_sale_30` INT(11)          NOT NULL DEFAULT '0'
    COMMENT '30日销量',
    `create_time`   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    COMMENT '更新时间',
    `stock_no`      VARCHAR(5)                DEFAULT ''
    COMMENT '仓库编号',
    PRIMARY KEY (`id`),
    KEY `sku_id_idx` (`sku_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='商户SKU的销量信息表';



# Dump of table special_config_shop
# ------------------------------------------------------------

DROP TABLE IF EXISTS `special_config_shop`;

CREATE TABLE `special_config_shop` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `merchant_id` VARCHAR(36)         NOT NULL
    COMMENT '商户id',
    `plan_code`   VARCHAR(50)         NOT NULL
    COMMENT '方案编号',
    `plan_name`   VARCHAR(150)        NOT NULL
    COMMENT '方案名称',
    `shop_code`   VARCHAR(30)         NOT NULL
    COMMENT '店铺编号',
    UNIQUE KEY `id` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='特定方案店铺设置';



# Dump of table special_config_sku
# ------------------------------------------------------------

DROP TABLE IF EXISTS `special_config_sku`;

CREATE TABLE `special_config_sku` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `merchant_id` VARCHAR(36)         NOT NULL
    COMMENT '商户id',
    `plan_code`   VARCHAR(50)         NOT NULL
    COMMENT '方案编号',
    `plan_name`   VARCHAR(150)        NOT NULL
    COMMENT '方案名称',
    `sku_id`      VARCHAR(36)         NOT NULL,
    UNIQUE KEY `id` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='特定方案产品设置';



# Dump of table special_config_stock
# ------------------------------------------------------------

DROP TABLE IF EXISTS `special_config_stock`;

CREATE TABLE `special_config_stock` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `merchant_id` VARCHAR(36)         NOT NULL
    COMMENT '商户id',
    `plan_code`   VARCHAR(50)         NOT NULL
    COMMENT '方案编号',
    `plan_name`   VARCHAR(150)        NOT NULL
    COMMENT '方案名称',
    `stock_no`    VARCHAR(36)         NOT NULL,
    UNIQUE KEY `id` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='特定方案仓库设置';



# Dump of table vlt_change_history
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vlt_change_history`;

CREATE TABLE `vlt_change_history` (
    `id`              BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `merchant_id`     VARCHAR(36)         NOT NULL
    COMMENT '商户编号',
    `sku_id`          VARCHAR(36)         NOT NULL,
    `arrival_date`    DATE                         DEFAULT NULL
    COMMENT '到货日期',
    `count`           INT(11)             NOT NULL DEFAULT '0'
    COMMENT '到货数量',
    `vlt`             INT(3)              NOT NULL DEFAULT '0'
    COMMENT '到货时长',
    `supplier_code`   VARCHAR(36)                  DEFAULT ''
    COMMENT '供应商编号',
    `supplier_name`   VARCHAR(36)                  DEFAULT ''
    COMMENT '供应商名称',
    `create_time`     TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`     TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT '更新时间',
    `stock_no`        VARCHAR(36)                  DEFAULT NULL,
    `merged_stock_no` VARCHAR(36)                  DEFAULT NULL,
    UNIQUE KEY `id` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;



# Dump of table warehouse_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `warehouse_config`;

CREATE TABLE `warehouse_config` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `merchant_id` VARCHAR(36)         NOT NULL
    COMMENT '商户id',
    `stock_no`    VARCHAR(36)         NOT NULL,
    `shop_code`   VARCHAR(30)         NOT NULL
    COMMENT '店铺编号',
    `area_name`   VARCHAR(150)        NOT NULL
    COMMENT '省市',
    UNIQUE KEY `id` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COMMENT ='智能库存配置';


/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
