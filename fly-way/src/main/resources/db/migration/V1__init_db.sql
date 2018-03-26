drop table if exists resource;

drop table if exists server;

/*==============================================================*/
/* Table: resource                                              */
/*==============================================================*/
create table resource
(
   id                   bigint(11) not null auto_increment,
   name                 varchar(100) not null,
   description          varchar(250) not null,
   url                  varchar(250) not null,
   type                 varchar(20) not null comment '资源请求方式:GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE;',
   server_id            bigint(11) not null,
   create_time          timestamp not null,
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
   name                 varchar(50) not null,
   description          varchar(250) not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   available            tinyint(1) default 1,
   primary key (id)
);

alter table server comment '所有微服务均需在此表注册信息';
