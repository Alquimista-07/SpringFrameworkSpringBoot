USE db_springboot;

CREATE TABLE `db_springboot`.`users`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(60) NOT NULL,
    `enabled` TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY(`id`),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC)
);

CREATE TABLE `db_springboot`.`authorities`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `authority` VARCHAR(45) NOT NULL,
    PRIMARY KEY(`id`),
    UNIQUE INDEX `user_id_authority_unique` (`user_id` ASC, `authority` ASC),
    CONSTRAINT `fk_authorities_users`
     FOREIGN KEY (`user_id`)
     REFERENCES `db_springboot`.`users` (`id`)
     ON DELETE CASCADE
     ON UPDATE CASCADE
);

INSERT INTO users (username, password, enabled) VALUES('andres', '$2a$10$X3CJF5JMtM.iQNTuRdBYz.NSW2MkL5M8c4KsBEtr8dR/VwhL/OG6y', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$CmmQ9ZFvJ0pWz5OfVvxmFu85v4y.MNdcWtGO82lwNqKdCgLg4HBh6', 1);

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_ADMIN');