CREATE TABLE `invoice_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `product_quantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `index_invoice_item` (`product_id` ASC),
  CONSTRAINT `fk_invoice_item`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)