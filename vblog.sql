create database vblog;
use vblog;
CREATE TABLE `m_user` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `username` varchar(64) DEFAULT NULL,
                          `avatar` varchar(255) DEFAULT NULL,
                          `email` varchar(64) DEFAULT NULL,
                          `password` varchar(64) DEFAULT NULL,
                          `status` int(5) NOT NULL,
                          `created` datetime DEFAULT NULL,
                          `last_login` datetime DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `UK_USERNAME` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `m_blog` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `user_id` bigint(20) NOT NULL,
                          `title` varchar(255) NOT NULL,
                          `description` varchar(255) NOT NULL,
                          `content` longtext,
                          `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                          `status` tinyint(4) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
INSERT INTO `vblog`.`m_user` (`id`, `username`, `avatar`, `email`, `password`, `status`, `created`, `last_login`) VALUES ('1', 'lam', 'https://i.loli.net/2020/10/05/N3bF7ir5fdzwjgW.jpg', '309280341@qq.com', 'hurrican3', '0', '2020-10-05 10:21:01', NULL);
