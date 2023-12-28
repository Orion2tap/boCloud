DROP DATABASE IF EXISTS `java`;
CREATE DATABASE `java` CHARACTER SET utf8mb4;
USE `java`;

CREATE TABLE `Session`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `sessionID` VARCHAR(255) NOT NULL,
    `userID`    INT          NOT NULL,
    PRIMARY KEY (`id`)
);


INSERT INTO `java`.`Session` (`sessionID`, `userID`) VALUES ('testSession', '1');
