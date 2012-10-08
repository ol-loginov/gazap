insert into Profile(createdAt,updatedAt,email,password,passwordSalt,displayName,systemAccount) values(now(),now(),'first','5db93645c96d6cec82ca1bdd0ad96aa7','','The Very First User',1);
insert into Profile(createdAt,updatedAt,email,password,passwordSalt,displayName,systemAccount) values(now(),now(),'oregu@yandex.ru','7AY4QBuAdh1AyPM+QYH+WO82bB/b9UKnmhkS+dPcQGA=','d81714926b1e3a2f7d86feb33796c46','oregu',0);

insert into ProfileAcl(profile,aclRole) select id, 'GOD_MODE' from Profile;
insert into ProfileSummary(profile) select id from Profile;


