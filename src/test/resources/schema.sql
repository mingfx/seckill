--数据库初始化

--创建数据库
CREATE DATABASE seckill;
--使用数据库
use seckill;
--创建秒杀库存表，mysql只有innodb支持事务，所以表的引擎设为InnoDB
CREATE TABLE seckill(
'seckill_id' bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
'name' varchar (120) NOT NULL ,
'number' int NOT NULL ,
'start_time' timestamp NOT NULL ,
'end_time' timestamp NOT NULL ,
'create_time' timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'

--初始化数据
insert into
  seckill(name ,number ,start_time ,end_time )
values
('1000元秒杀iphone x',100,'2019-03-07 00:00:00','2019-03-09 00:00:00'),
('2000元秒杀iphone XR',200,'2019-03-07 00:00:00','2019-03-09 00:00:00'),
('3000元秒杀iphone Xs',300,'2019-03-07 00:00:00','2019-03-09 00:00:00'),
('4000元秒杀iphone Xs MAX',400,'2019-03-07 00:00:00','2019-03-09 00:00:00');
--秒杀成功明细表
--用户登录认证相关的信息，秒杀里一定要有用户身份
create table success_killed(
seckill_id bigint NOT NULL COMMENT '秒杀商品id',
user_phone bigint NOT NULL COMMENT '用户手机号',
state tinyint NOT NULL DEFAULT -1 COMMENT '状态标示：-1：无效 0：成功 1：已付款 2：已发货',
create_time timestamp NOT NULL COMMENT '创建时间',
PRIMARY KEY(seckill_id,user_phone),/*联合主键：同一个用户只可能对同一个秒杀库存的产品进行操作*/
key idx_create_time(create_time)

)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

--连接数据库控制台
mysql -uroot -p

--为什么手写DDL
--记录每次上线的DDL修改
ALTER TABLE seckill
DROP INDEX idx_create_time,
ADD index idx_c_s(start_time,create_time);