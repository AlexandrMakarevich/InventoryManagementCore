CREATE TABLE `inventory_status` (
  `product_id` INT NOT NULL,
  `last_check_date` TIMESTAMP(3) NOT NULL,
  `count_of_product` INT NULL,
  INDEX `index_inventory_status_1` (`product_id` ASC),
  CONSTRAINT `fk_inventory_status_1`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)