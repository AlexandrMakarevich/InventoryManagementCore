CREATE TABLE `product_invoice_map` (
  `product_id` INT NOT NULL,
  `invoice_id` INT NOT NULL,
  `count_product` INT NULL,
  `invoice_type` VARCHAR(45) NULL,
  INDEX `index_product_invoice_map_1` (`product_id` ASC),
  INDEX `index_product_invoice_map_2` (`invoice_id` ASC),
  CONSTRAINT `fk_product_invoice_map_1`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_invoice_map_2`
    FOREIGN KEY (`invoice_id`)
    REFERENCES `invoice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)