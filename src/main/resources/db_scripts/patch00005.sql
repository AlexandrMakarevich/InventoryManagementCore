CREATE TABLE `inventory_state` (
  `id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `product_name` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  `last_up_date` TIMESTAMP(3) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `index_inventory_state_1` (`product_id` ASC),
  CONSTRAINT `fk_inventory_state_1`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)