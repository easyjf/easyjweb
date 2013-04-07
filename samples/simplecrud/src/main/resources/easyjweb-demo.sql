# SQL Manager 2005 for MySQL 3.7.5.1
# ---------------------------------------
# Host     : localhost
# Port     : 3306
# Database : easyjweb-demo


SET FOREIGN_KEY_CHECKS=0;

USE `easyjweb-demo`;

#
# Structure for the `user` table : 
#

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `password` varchar(30) default NULL,
  `sex` varchar(20) default NULL,
  `email` varchar(100) default NULL,
  `bornDate` datetime default NULL,
  `loginTimes` int(11) default NULL,
  `lastLoginTime` datetime default NULL,
  `intro` varchar(1000) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for the `user` table  (LIMIT 0,500)
#

INSERT INTO `user` (`id`, `name`, `password`, `sex`, `email`, `bornDate`, `loginTimes`, `lastLoginTime`, `intro`) VALUES 
  (1,'admin','admin',NULL,'admin@aaa.com',NULL,NULL,NULL,'fff');

COMMIT;

