/*如果这个文件名字为data.sql，那么文件中的内容每次跑程序时都会执行，所以第一次执行完了之后记得改名字或者删除掉*/
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'wangyunfei',32,'hefei');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'aa',20,'shanghai');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'bb',18,'beijing');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'cc',23,'zhegnzhou');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'dd',22,'wuhan');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'ee',24,'nanjing');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'ff',26,'suzhou');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'gg',28,'xiamen');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'hh',28,'shanghai');
insert into person (id,name,age,address) VALUES (hibernate_sequence.nextval,'ii',20,'beijing');

insert into SYS_USER (id,username, password) values (1 ,'wyf','wyf');
insert into SYS_USER (id,username, password) values (2 ,'wisely','wyf');
insert into SYS_ROLE(id,name) values(1,'ROLE_ADMIN');
insert into SYS_ROLE(id,name) values(2,'ROLE_USER');
insert into SYS_USER_ROLES (SYS_USER_ID, ROLES_ID) values (1,1);
insert into SYS_USER_ROLES (SYS_USER_ID, ROLES_ID) values (2,2);