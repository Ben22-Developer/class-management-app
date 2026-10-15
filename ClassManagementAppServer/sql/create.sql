-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema classManagementAppServer
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema classManagementAppServer
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `classManagementAppServer` DEFAULT CHARACTER SET utf8 ;
USE `classManagementAppServer` ;

-- -----------------------------------------------------
-- Table `classManagementAppServer`.`api`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `password_UNIQUE` (`password` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classManagementAppServer`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classManagementAppServer`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`user_role` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  INDEX `fk_user_role_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_user_role_role1_idx` (`role_id` ASC) VISIBLE,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_role_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `classManagementAppServer`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `classManagementAppServer`.`role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classManagementAppServer`.`token_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`token_type` (
  `id` INT NOT NULL,
  `category` ENUM('FREE', 'PAID') NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `category_UNIQUE` (`category` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classManagementAppServer`.`paid_token_cash_amount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`paid_token_cash_amount` (
  `token_type_id` INT NOT NULL,
  `cash_amount` DOUBLE NOT NULL,
  PRIMARY KEY (`token_type_id`),
  CONSTRAINT `fk_table1_token_type1`
    FOREIGN KEY (`token_type_id`)
    REFERENCES `classManagementAppServer`.`token_type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classManagementAppServer`.`user_token_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`user_token_type` (
  `user_id` INT NOT NULL,
  `token_type_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_token_token1_idx` (`token_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_token_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `classManagementAppServer`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_token_token1`
    FOREIGN KEY (`token_type_id`)
    REFERENCES `classManagementAppServer`.`token_type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classManagementAppServer`.`user_token_amount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`user_token_amount` (
  `user_id` INT NOT NULL,
  `token_amount` INT NOT NULL,
  `date` DATETIME NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_token_amount_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_token_amount_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `classManagementAppServer`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classManagementAppServer`.`api_key_Confirm`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`api_key_Confirm` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `token_category` ENUM('FREE', 'PAID') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `password_UNIQUE` (`password` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classManagementAppServer`.`paid_api_key_confirm`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`paid_api_key_confirm` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `hasPaid` TINYINT(0) NOT NULL,
  `paid_amount` DOUBLE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `password_UNIQUE` (`password` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classManagementAppServer`.`paid_token_validitiy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`paid_token_validitiy` (
  `subscription_time` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_paid_token_validitiy_user_token_type1`
    FOREIGN KEY (`user_id`)
    REFERENCES `classManagementAppServer`.`user_token_type` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `classManagementAppServer`;

DELIMITER $$
USE `classManagementAppServer`$$
CREATE DEFINER = CURRENT_USER TRIGGER `classManagementAppServer`.`api_key_Confirm` AFTER INSERT ON `api_key_Confirm` FOR EACH ROW
BEGIN
	IF NEW.token_category = 'PAID' THEN
		INSERT INTO `classManagementAppServer`.`paid_api_key_confirm`(username, password, hasPaid) VALUES 
        (NEW.username, NEW.password, false);
    END IF;
END$$


DELIMITER ;



-- -----------------------------------------------------
-- Table `classManagementAppServer`.`user_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`user_schedule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `schedule_uuid` VARCHAR(100) NOT NULL UNIQUE,
  `user_id` INT NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `description` MEDIUMTEXT NOT NULL,
  `date` DATETIME NOT NULL,
  INDEX `fk_user_schedule_user1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_schedule_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `classManagementAppServer`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classManagementAppServer`.`archive_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`archive_schedule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `schedule_uuid` VARCHAR(100) NOT NULL UNIQUE,
  `title` VARCHAR(200) NOT NULL,
  `date` DATETIME NOT NULL,
  `description` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id`, `title`, `date`, `user_id`),
  CONSTRAINT `fk_archive_schedule_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `classManagementAppServer`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

delete from archive_schedule where id != -1;

DELIMITER $$
USE `classManagementAppServer`$$
CREATE DEFINER = CURRENT_USER TRIGGER `classManagementAppServer`.`user_schedule` BEFORE DELETE ON `user_schedule` FOR EACH ROW
BEGIN
	INSERT INTO archive_schedule (user_id, schedule_uuid, title, date, description) VALUES (OLD.user_id, old.schedule_uuid, OLD.title, OLD.date, OLD.description);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- Table `classManagementAppServer`.`password_reset_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`password_reset_code` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL UNIQUE,
  `code` VARCHAR(100) NOT NULL,
  `DateTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `classManagementAppServer`.`chat_rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classManagementAppServer`.`chat_rating` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_message` TEXT NOT NULL,
  `bot_message` TEXT NOT NULL,
  `rate` DOUBLE NOT NULL,
  `dateTime` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `classmanagementappserver`.`TeacherMessage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `classmanagementappserver`.`user_feedback` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `feedback` TEXT NOT NULL,
  `timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
  )
ENGINE = InnoDB;

drop table `classmanagementappserver`.`user_feedback`;

INSERT INTO role VALUES(1,'ROLE_USER');
INSERT INTO role VALUES(2,'ROLE_ADMIN');
INSERT INTO token_type VALUES(1,'FREE',0);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


ALTER TABLE user AUTO_INCREMENT = 0;
ALTER TABLE role AUTO_INCREMENT = 0;
ALTER TABLE user_feedback AUTO_INCREMENT = 0;
ALTER TABLE user_role AUTO_INCREMENT = 0;
ALTER TABLE user_schedule AUTO_INCREMENT = 0;
ALTER TABLE user_token_amount AUTO_INCREMENT = 0;
ALTER TABLE user_token_type AUTO_INCREMENT = 0;
ALTER TABLE token_type AUTO_INCREMENT = 0;
ALTER TABLE spring_ai_chat_memory AUTO_INCREMENT = 0;
ALTER TABLE password_reset_code AUTO_INCREMENT = 0;
ALTER TABLE paid_token_validitiy AUTO_INCREMENT = 0;
ALTER TABLE paid_token_cash_amount AUTO_INCREMENT = 0;
ALTER TABLE paid_api_key_confirm AUTO_INCREMENT = 0;
ALTER TABLE chat_rating AUTO_INCREMENT = 0;
ALTER TABLE archive_schedule AUTO_INCREMENT = 0;
ALTER TABLE api_key_confirm AUTO_INCREMENT = 0;