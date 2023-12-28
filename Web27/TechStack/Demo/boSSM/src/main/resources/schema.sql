DROP DATABASE IF EXISTS ssm;
CREATE DATABASE ssm CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ssm;

CREATE TABLE `ssm`.`user`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL DEFAULT 'guest',
    `password` VARCHAR(45) NOT NULL,
    `role` ENUM('normal', 'admin', 'guest') NOT NULL DEFAULT 'normal',
    `email` VARCHAR(45) NOT NULL DEFAULT 'xxx@qq.com',
    PRIMARY KEY (`id`)
);

CREATE TABLE todo
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `content`   VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE weibo
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `content`   VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `ssm`.`topic`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(10) NOT NULL,
    `content` VARCHAR(1000) NOT NULL,
    `userId` INT NOT NULL DEFAULT -1,
    `boardId` INT NOT NULL,
    `createdTime` VARCHAR(45) NOT NULL DEFAULT '000',
    `updatedTime` VARCHAR(45) NOT NULL DEFAULT '000',

PRIMARY KEY (`id`)
)
ENGINE = InnoDB;

CREATE TABLE `topiccomment`
(
    `id` int(11) NOT NULL,
    `content` varchar(45) DEFAULT NULL,
    `userId` int(11) NOT NULL,
    `topicId` int(11) NOT NULL,
    `createdTime` VARCHAR(45) NOT NULL DEFAULT '000',
    `updatedTime` VARCHAR(45) NOT NULL DEFAULT '000',

PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ssm`.`board` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);
