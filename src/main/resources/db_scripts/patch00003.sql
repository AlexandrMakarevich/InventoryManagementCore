CREATE TABLE `invoice_item_map` (
  `invoice_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  INDEX `index_invoice_item_map_1` (`product_id` ASC),
  INDEX `index_invoice_item_map_2` (`invoice_id` ASC),
  CONSTRAINT `fk_invoice_item_map_1`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_item_map_2`
    FOREIGN KEY (`invoice_id`)
    REFERENCES `invoice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)