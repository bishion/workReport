CREATE TABLE `report` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `work_type` varchar(255) DEFAULT NULL,
  `developer` varchar(255) DEFAULT NULL,
  `work_content` varchar(4000) DEFAULT NULL,
  `process` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `week_no` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `remark` varchar(4000) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1042 DEFAULT CHARSET=utf8 COMMENT='工时统计';

CREATE TABLE `project` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

CREATE TABLE `bug_report` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL DEFAULT '' COMMENT '问题类型',
  `menu` varchar(100) NOT NULL DEFAULT '' COMMENT '所属菜单',
  `description` varchar(500) NOT NULL DEFAULT '' COMMENT '问题描述',
  `status` varchar(20) NOT NULL DEFAULT '' COMMENT '解决状态',
  `reporter` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_date` date DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(255) NOT NULL DEFAULT '' COMMENT '修改人',
  `modify_date` date DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='bug反馈表';

CREATE TABLE `developer` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `developer` varchar(255) NOT NULL DEFAULT '' COMMENT '开发人员',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='组员信息';
