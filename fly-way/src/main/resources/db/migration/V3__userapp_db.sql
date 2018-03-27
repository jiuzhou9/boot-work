drop table if exists app;

/*==============================================================*/
/* Table: app                                                   */
/*==============================================================*/
create table app
(
   id                   bigint(11) not null auto_increment,
   name                 varchar(20) not null,
   code                 varchar(32) not null,
   user_id              bigint(11) not null,
   available            tinyint(1) default 1,
   secret               varchar(36) not null,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
);
drop table if exists app_role;

/*==============================================================*/
/* Table: app_role                                              */
/*==============================================================*/
create table app_role
(
   id                   bigint(11) not null auto_increment,
   app_id               bigint(11) not null,
   role_id              bigint(11) not null,
   available            tinyint(1) default 1,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
);
