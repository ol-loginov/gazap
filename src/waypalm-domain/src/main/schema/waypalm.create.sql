/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     14.03.2013 19:10:22                          */
/*==============================================================*/


drop table if exists Profile;

drop table if exists ProfileAcl;

drop table if exists ProfileSummary;

drop table if exists SocialLink;

/*==============================================================*/
/* Table: Profile                                               */
/*==============================================================*/
create table Profile
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   updatedAt            datetime not null,
   deletedAt            datetime,
   systemAccount        bit(1) not null default 0,
   email                varchar(128) not null,
   emailConfirmDate     datetime,
   emailConfirmToken    varchar(128),
   password             varchar(64) not null,
   passwordSalt         varchar(32) not null,
   displayName          varchar(64) not null,
   primary key (id),
   unique key AK_UniqueEmail (email)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: ProfileAcl                                            */
/*==============================================================*/
create table ProfileAcl
(
   profile              int not null,
   aclRole              varchar(16) not null
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: ProfileSummary                                        */
/*==============================================================*/
create table ProfileSummary
(
   profile              int not null,
   surfaceOwned         int not null default 0,
   surfaceOwnedLimit    int not null default 1,
   primary key (profile)
)
charset = utf8
engine = MYISAM;

/*==============================================================*/
/* Table: SocialLink                                            */
/*==============================================================*/
create table SocialLink
(
   id                   int not null auto_increment,
   createdAt            datetime not null,
   updatedAt            datetime not null,
   provider             varchar(32),
   providerUser         varchar(50),
   profile              int not null,
   userUrl              varchar(512),
   userEmail            varchar(128),
   accessToken          varchar(512),
   accessSecret         varchar(512),
   primary key (id)
)
charset = utf8
engine = MYISAM;

alter table ProfileAcl add constraint FK_ProfileAcl_profile foreign key (profile)
      references Profile (id) on delete restrict on update restrict;

alter table ProfileSummary add constraint FK_ProfileSummary_profile foreign key (profile)
      references Profile (id) on delete restrict on update restrict;

alter table SocialLink add constraint FK_SocialLink_profile foreign key (profile)
      references Profile (id) on delete restrict on update restrict;

