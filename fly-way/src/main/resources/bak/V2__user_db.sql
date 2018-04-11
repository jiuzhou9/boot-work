drop table if exists user;

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint(11) not null auto_increment,
   username             varchar(20) not null,
   nick_name            varchar(20) not null comment '默认与username相同',
   password             varchar(60) not null,
   email                varchar(32),
   address              varchar(50),
   mobile               varchar(11) not null,
   available            tinyint(1) default 1,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
);
drop table if exists user_role;

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role
(
   id                   bigint(11) not null auto_increment,
   user_id              bigint(11) not null,
   role_id              bigint(11) not null,
   available            tinyint(1) default 1,
   create_time          timestamp not null default CURRENT_TIMESTAMP,
   update_time          timestamp not null default CURRENT_TIMESTAMP,
   primary key (id)
);
