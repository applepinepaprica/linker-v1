DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
	`user_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`username` varchar(20) NOT NULL,
	`password` varchar(255) NOT NULL,
	UNIQUE(`username`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS role;
CREATE TABLE `role` (
	`role_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`name` varchar(50) NOT NULL,
	UNIQUE(`name`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS user_role;
CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB;

INSERT INTO role VALUES (1, 'USER');
INSERT INTO role VALUES (2, 'PRIVELEGED_USER');

DROP TABLE IF EXISTS note;
DROP TABLE IF EXISTS file;

CREATE TABLE file(
	`file_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`name` text(255) NOT NULL,
	`data` longblob NOT NULL,
	`numberOfViews` int NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE note(
	`note_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`url` varchar(36) NOT NULL,
	`name` text(100) NOT NULL,
	`text` text(5000) DEFAULT NULL,
	`numberOfViews` int NOT NULL DEFAULT 0,
	`maxNumberOfViews` smallint unsigned NOT NULL,
	`file_id` int DEFAULT NULL,
	`user_id` int DEFAULT NULL,
	UNIQUE KEY `url_UNIQUE` (`url`),
	CONSTRAINT `FK859n2jvi8ivhui0rl0etyekv` FOREIGN KEY (`file_id`) REFERENCES `file` (`file_id`),
	CONSTRAINT `FK859n2jvi8ivhui0rl0esqsfb` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;