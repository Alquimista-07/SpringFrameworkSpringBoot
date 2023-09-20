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