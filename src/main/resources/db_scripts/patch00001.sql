CREATE TABLE `product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(100) NULL,
  creation_date TIMESTAMP not null,
  PRIMARY KEY (`id`))