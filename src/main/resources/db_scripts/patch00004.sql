CREATE TABLE `invoice_item_map` (
  `invoice_id` INT NOT NULL,
  `invoice_item_id` INT NOT NULL,
  INDEX `index_invoice_item_map_1` (`invoice_item_id` ASC),
  INDEX `index_invoice_item_map_2` (`invoice_id` ASC),
  CONSTRAINT `fk_invoice_item_map_1`
    FOREIGN KEY (`invoice_item_id`)
    REFERENCES `invoice_item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_invoice_item_map_2`
    FOREIGN KEY (`invoice_id`)
    REFERENCES `invoice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)