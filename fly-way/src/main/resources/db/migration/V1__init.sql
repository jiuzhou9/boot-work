drop table if exists app;

drop table if exists app_role;

drop table if exists resource;

drop table if exists role;

drop table if exists role_resource;

drop table if exists server;

drop table if exists user;

drop table if exists user_role;

/*==============================================================*/
/* Table: app                                                   */
/*==============================================================*/
create table app
(
   id                   bigint(11) not null auto_increment,
   name                 varchar(20) not null comment 'APP名字',
   code                 varchar(18) not null comment 'APPcode',
   user_id              bigint(11) not null comment '用户id',
   available            tinyint(1) default 1 comment '0否1是',
   secret               varchar(36) not null comment 'APP 秘钥',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: app_role                                              */
/*==============================================================*/
create table app_role
(
   id                   bigint(11) not null auto_increment,
   app_id               bigint(11) not null,
   role_id              bigint(11) not null comment '角色id',
   available            tinyint(1) default 1 comment '0否1是',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: resource                                              */
/*==============================================================*/
create table resource
(
   id                   bigint(11) not null auto_increment,
   name                 varchar(100) not null comment '资源名',
   description          varchar(250) not null comment '资源描述',
   url                  varchar(250) not null comment '资源URL',
   type                 varchar(20) not null comment '资源请求方式:GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE;',
   server_id            bigint(11) not null comment '资源所在server的serverid',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   available            tinyint(1) default 1 comment '0否1是',
   primary key (id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   bigint(11) not null auto_increment,
   name                 varchar(20) not null comment '角色名',
   times                bigint(10) comment '计费方式总次数',
   time_slot            int(2) comment '时间段类型:1:24h;2:7 * 24h;3:30 * 24h;4:365 * 24h',
   type                 int(1) not null comment '计费类型:1:次数计费;2:时间计费',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   available            tinyint(1) default 1 comment '0否1是',
   primary key (id)
);

/*==============================================================*/
/* Table: role_resource                                         */
/*==============================================================*/
create table role_resource
(
   id                   bigint(11) not null auto_increment,
   role_id              bigint(11) not null,
   resource_id          bigint(11) not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   available            tinyint(1) default 1 comment '0否1是',
   primary key (id)
);

/*==============================================================*/
/* Table: server                                                */
/*==============================================================*/
create table server
(
   id                   bigint(11) not null auto_increment,
   name                 varchar(50) not null comment '服务名例如：/eureka-api',
   description          varchar(250) not null comment '服务描述',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   available            tinyint(1) default 1 comment '0否1是',
   primary key (id)
);

alter table server comment '所有微服务均需在此表注册信息';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint(11) not null auto_increment,
   username             varchar(20) not null comment '用户名',
   nick_name            varchar(20) not null comment '用户昵称，默认与username相同',
   password             varchar(60) not null,
   email                varchar(32),
   address              varchar(50),
   mobile               varchar(11) not null,
   available            tinyint(1) default 1 comment '0否1是',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
);

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role
(
   id                   bigint(11) not null auto_increment,
   user_id              bigint(11) not null,
   role_id              bigint(11) not null,
   remainder            bigint(10) comment '剩余次数',
   start_time           timestamp default CURRENT_TIMESTAMP comment '起始时间',
   end_time             timestamp default CURRENT_TIMESTAMP comment '结束时间',
   available            tinyint(1) default 1 comment '0否1是',
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
);
