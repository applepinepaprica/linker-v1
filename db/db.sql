DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
	`user_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`username` varchar(255) NOT NULL,
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