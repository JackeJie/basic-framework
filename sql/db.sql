create database demo;
use demo;


CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nickname` varchar(30) DEFAULT NULL COMMENT '用户名',
  `username` varchar(20) DEFAULT NULL COMMENT ' 姓名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐值',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `head` varchar(200) DEFAULT NULL COMMENT '头像',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `deleted` int(11) NOT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `department_id` int(11) DEFAULT NULL COMMENT '部门id',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别，0：女，1：男，默认1',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统用户';

insert into `sys_user` (`id`, `nickname`, `username`, `password`, `salt`, `phone`, `head`, `remark`, `state`, `role_id`, `deleted`, `version`, `create_by`, `create_time`, `email`, `update_by`, `update_time`, `department_id`, `gender`) values('1','admin','admin','11a254dab80d52bc4a347e030e54d861a9d2cdb2af2185a9ca4a7318e830d04d','666','15888889900','http://localhost:8888/api/resource/201908201013068.png','Administrator Account','0','1','0','1',NULL,'2020-10-10 12:14:52',NULL,NULL,'2020-07-24 14:21:16',NULL,NULL);


CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `permission_id` (`permission_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=542 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色权限关系';

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `code` varchar(100) DEFAULT NULL COMMENT '角色唯一编码',
  `type` int(11) DEFAULT NULL COMMENT '角色类型',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '角色状态，0：启用，1： 禁用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sys_role_name_uindex` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统角色';

INSERT  INTO `sys_role`(`id`,`name`,`code`,`type`,`state`,`remark`,`version`,`create_time`,`update_time`,`deleted`)
VALUES (1,'管理员','admin',NULL,0,'管理员拥有所有权限',0,'2020-10-10 11:17:23',NULL,0);


CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '权限名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `url` varchar(200) DEFAULT NULL COMMENT '路径',
  `code` varchar(100) NOT NULL COMMENT '唯一编码',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `type` int(11) NOT NULL COMMENT '类型，1：菜单，2：按钮',
  `level` int(11) NOT NULL COMMENT '层级，1：第一级，2：第二级，N：第N级',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_permission_code_uindex` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=90205 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sys_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `level` int(11) DEFAULT NULL COMMENT '部门层级',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sys_department_name_uindex` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='部门';


CREATE TABLE `tb_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oper_modul` varchar(64) DEFAULT NULL COMMENT '功能模块',
  `oper_type` varchar(64) DEFAULT NULL COMMENT '操作类型',
  `oper_desc` varchar(500) DEFAULT NULL COMMENT '操作描述',
  `oper_requ_param` text COMMENT '请求参数',
  `oper_resp_param` text COMMENT '返回参数',
  `oper_user_id` varchar(64) DEFAULT NULL COMMENT '操作员id',
  `oper_user_name` varchar(64) DEFAULT NULL COMMENT '操作员名称',
  `oper_method` varchar(255) DEFAULT NULL COMMENT '操作方法',
  `oper_uri` varchar(255) DEFAULT NULL COMMENT '请求uri',
  `oper_ip` varchar(64) DEFAULT NULL COMMENT '请求id',
  `oper_create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `type` int(1) DEFAULT NULL COMMENT '类型 1 成功  2 错误',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_error_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exc_requ_param` text COMMENT '请求参数',
  `exc_name` varchar(255) DEFAULT NULL COMMENT '异常名称',
  `exc_message` text COMMENT '异常信息',
  `oper_user_id` varchar(64) DEFAULT NULL COMMENT '操作人id',
  `oper_user_name` varchar(64) DEFAULT NULL COMMENT '操作员名称',
  `oper_method` varchar(255) DEFAULT NULL COMMENT '操作方法',
  `oper_uri` varchar(255) DEFAULT NULL COMMENT '请求uri',
  `oper_ip` varchar(64) DEFAULT NULL COMMENT '请求ip',
  `oper_ver` varchar(64) DEFAULT NULL COMMENT '操作版本号',
  `oper_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='错误日志信息';