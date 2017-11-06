CREATE TABLE `invoice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ivoice_vendor_code` INT NULL,
  `status` VARCHAR(45) NULL,
  `invoice_type` VARCHAR(45) NULL,
  `invoice_date` TIMESTAMP(3) NOT NULL,
  PRIMARY KEY (`id`))