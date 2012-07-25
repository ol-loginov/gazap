/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     25.07.2012 11:46:37                          */
/*==============================================================*/


drop table if exists Avatar;

drop table if exists Contribution;

drop table if exists ContributionTile;

drop table if exists Geometry;

drop table if exists GeometryGeoid;

drop table if exists GeometryPlain;

drop table if exists GeometryPlainTile;

drop table if exists Map;

drop table if exists MapApprover;

drop table if exists UserAcl;

drop table if exists UserMapRole;

drop table if exists UserProfile;

drop table if exists UserSocialLink;

drop table if exists UserSummary;

drop table if exists UserWorldRole;

drop table if exists World;

/*==============================================================*/
/* Table: Avatar                                                */
/*==============================================================*/
create table Avatar
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   updatedAt            datetime not null,
   deletedAt            datetime,
   world                int,
   owner                int,
   primary key (id)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: Contribution                                          */
/*==============================================================*/
create table Contribution
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   class                varchar(16) not null,
   version              int not null default 0,
   map                  int not null,
   author               int not null,
   decision             varchar(16) not null,
   approveLevel         int not null,
   primary key (id)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: ContributionTile                                      */
/*==============================================================*/
create table ContributionTile
(
   id                   int not null,
   x                    int not null,
   y                    int not null,
   size                 int not null,
   scale                int not null,
   action               varchar(16) not null,
   file                 varchar(64),
   primary key (id)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: Geometry                                              */
/*==============================================================*/
create table Geometry
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   updatedAt            datetime not null,
   deletedAt            datetime,
   class                varchar(16) not null,
   version              int not null default 0,
   primary key (id)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: GeometryGeoid                                         */
/*==============================================================*/
create table GeometryGeoid
(
   id                   int not null,
   radiusZ              float not null,
   radiusX              float not null,
   radiusY              float not null,
   primary key (id)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: GeometryPlain                                         */
/*==============================================================*/
create table GeometryPlain
(
   id                   int not null,
   west                 int not null,
   north                int not null,
   east                 int not null comment '	',
   south                int not null,
   tileSize             int not null,
   scaleMin             int not null,
   scaleMax             int not null,
   primary key (id)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: GeometryPlainTile                                     */
/*==============================================================*/
create table GeometryPlainTile
(
   id                   int not null,
   geometry             int not null,
   x                    int not null,
   y                    int not null,
   size                 int not null,
   scale                int not null,
   file                 varchar(64) not null,
   primary key (id)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: Map                                                   */
/*==============================================================*/
create table Map
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   updatedAt            datetime not null,
   deletedAt            datetime,
   title                varchar(64) not null,
   alias                varchar(64),
   geometry             int,
   approveLimit         int not null,
   primary key (id),
   key AK_UniqueAlias (alias)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: MapApprover                                           */
/*==============================================================*/
create table MapApprover
(
   map                  int not null,
   user                 int not null,
   level                int not null,
   primary key (map, user, level)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: UserAcl                                               */
/*==============================================================*/
create table UserAcl
(
   userProfile          int not null,
   aclRole              varchar(16) not null
)
charset = utf8
engine = MYISAM;

insert into UserAcl(userProfile,aclRole)values(1,'GOD_MODE');

/*==============================================================*/
/* Table: UserMapRole                                           */
/*==============================================================*/
create table UserMapRole
(
   user                 int not null,
   map                  int not null,
   userMapRole          varchar(16) not null,
   primary key (user, map, userMapRole)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: UserProfile                                           */
/*==============================================================*/
create table UserProfile
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   updatedAt            datetime not null,
   deletedAt            datetime,
   systemAccount        bit(1) not null default 0,
   email                varchar(128) not null,
   password             varchar(64) not null,
   passwordSalt         varchar(32) not null,
   alias                varchar(32),
   displayName          varchar(64) not null,
   primary key (id),
   unique key AK_UniqueEmail (email),
   unique key AK_UniqueAlias (alias)
)
charset = utf8
engine = MYISAM;

insert into UserProfile(createdAt,updatedAt,email,password,passwordSalt,displayName,systemAccount) values(now(),now(),'first','5db93645c96d6cec82ca1bdd0ad96aa7','','The Very First User',1);
insert into UserProfile(createdAt,updatedAt,email,password,passwordSalt,displayName,systemAccount) values(now(),now(),'oregu@yandex.ru','7AY4QBuAdh1AyPM+QYH+WO82bB/b9UKnmhkS+dPcQGA=','d81714926b1e3a2f7d86feb33796c46','oregu',0);

/*==============================================================*/
/* Table: UserSocialLink                                        */
/*==============================================================*/
create table UserSocialLink
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   updatedAt            datetime not null,
   provider             varchar(32),
   providerUser         varchar(50),
   user                 int not null,
   userUrl              varchar(512),
   userEmail            varchar(128),
   accessToken          varchar(512),
   accessSecret         varchar(512),
   primary key (id)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: UserSummary                                           */
/*==============================================================*/
create table UserSummary
(
   user                 int not null,
   worldsCreated        int not null default 0,
   playersCreated       int not null default 0,
   mapsCreated          int not null default 0,
   primary key (user)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: UserWorldRole                                         */
/*==============================================================*/
create table UserWorldRole
(
   user                 int not null,
   world                int not null,
   userWorldRole        varchar(16) not null,
   primary key (user, world, userWorldRole)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: World                                                 */
/*==============================================================*/
create table World
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   updatedAt            datetime not null,
   deletedAt            datetime,
   title                varchar(64) not null,
   alias                varchar(64),
   primary key (id),
   unique key AK_UniqueAlias (alias),
   unique key AK_UniqueTitle (title)
)
charset = utf8
engine = MYISAM;

alter table Avatar add constraint FK_Avatar_owner foreign key (owner)
      references UserProfile (id) on delete restrict on update restrict;

alter table Avatar add constraint FK_Avatar_world foreign key (world)
      references World (id) on delete restrict on update restrict;

alter table Contribution add constraint FK_Contribution_map foreign key (map)
      references Map (id) on delete restrict on update restrict;

alter table ContributionTile add constraint FK_ContributionTile_id foreign key (id)
      references Contribution (id) on delete restrict on update restrict;

alter table GeometryGeoid add constraint FK_GeometryGeoid_id foreign key (id)
      references Geometry (id) on delete restrict on update restrict;

alter table GeometryPlain add constraint FK_GeometryPlain_id foreign key (id)
      references Geometry (id) on delete restrict on update restrict;

alter table GeometryPlainTile add constraint FK_GeometryPlainTile_geometry foreign key (geometry)
      references GeometryPlain (id) on delete restrict on update restrict;

alter table Map add constraint FK_Map_geometry foreign key (id)
      references Geometry (id) on delete restrict on update restrict;

alter table MapApprover add constraint FK_MapApprover_map foreign key (map)
      references Map (id) on delete restrict on update restrict;

alter table MapApprover add constraint FK_MapApprover_user foreign key (user)
      references UserProfile (id) on delete restrict on update restrict;

alter table UserAcl add constraint FK_UserAcl_userProfile foreign key (userProfile)
      references UserProfile (id) on delete restrict on update restrict;

alter table UserMapRole add constraint FK_UserMapRole_map foreign key (map)
      references Map (id) on delete restrict on update restrict;

alter table UserMapRole add constraint FK_UserMapRole_user foreign key (user)
      references UserProfile (id) on delete restrict on update restrict;

alter table UserSocialLink add constraint FK_UserSocialLink_user foreign key (user)
      references UserProfile (id) on delete restrict on update restrict;

alter table UserSummary add constraint FK_UserSummary_user foreign key (user)
      references UserProfile (id) on delete restrict on update restrict;

alter table UserWorldRole add constraint FK_UserWorldRole_user foreign key (user)
      references UserProfile (id) on delete restrict on update restrict;

alter table UserWorldRole add constraint FK_UserWorldRole_world foreign key (world)
      references World (id) on delete restrict on update restrict;
