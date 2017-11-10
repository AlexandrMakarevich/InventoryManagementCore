CREATE TABLE `inventory_state` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `state_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `index_inventory_state_1` (`product_id` ASC),
  CONSTRAINT `fk_inventory_state_1`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)