DROP DATABASE IF EXISTS `esun_order_system`;
CREATE DATABASE `esun_order_system` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `esun_order_system`;

DROP TABLE IF EXISTS `Order_Detail`;
DROP TABLE IF EXISTS `Orders`;
DROP TABLE IF EXISTS `Product`;

CREATE TABLE `Product` (
    `product_id`   VARCHAR(20) NOT NULL,
    `product_name` VARCHAR(100) NOT NULL,
    `price`        DECIMAL(10, 2) NOT NULL,
    `quantity`     INT NOT NULL,
    `created_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Orders` (
    `order_id`    VARCHAR(30) NOT NULL,
    `member_id`   VARCHAR(20) NOT NULL,
    `total_price` DECIMAL(10, 2) NOT NULL,
    `pay_status`  TINYINT NOT NULL COMMENT '0: 未付款, 1: 已付款',
    `order_date`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Order_Detail` (
    `order_item_sn` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id`      VARCHAR(30) NOT NULL,
    `product_id`    VARCHAR(20) NOT NULL,
    `quantity`      INT NOT NULL,
    `stand_price`   DECIMAL(10, 2) NOT NULL COMMENT 'The unit price at the time of purchase',
    `item_price`    DECIMAL(10, 2) NOT NULL COMMENT 'Total price for this line item (stand_price * quantity)',
    PRIMARY KEY (`order_item_sn`),
    FOREIGN KEY (`order_id`) REFERENCES `Orders`(`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES `Product`(`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DELIMITER //
DROP PROCEDURE IF EXISTS get_order_details;

DELIMITER //
CREATE PROCEDURE get_order_details(IN p_order_id VARCHAR(30))
BEGIN
    SELECT * FROM `Order_Detail` WHERE order_id = p_order_id;
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE get_available_products()
BEGIN
    SELECT * FROM Product WHERE quantity > 0;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS add_new_product;

CREATE PROCEDURE add_new_product (
    IN p_product_name VARCHAR(100), 
    IN p_price DECIMAL(10, 2),
    IN p_quantity INT
)
BEGIN
    DECLARE next_id_num INT;
    DECLARE new_product_id VARCHAR(20);

    SELECT 
        IFNULL(MAX(CAST(SUBSTRING(product_id, 2) AS UNSIGNED)), 0) INTO next_id_num
    FROM 
        Product;

    SET next_id_num = next_id_num + 1;

    SET new_product_id = CONCAT('P', LPAD(next_id_num, 3, '0'));

    INSERT INTO Product (product_id, product_name, price, quantity)
    VALUES (new_product_id, p_product_name, p_price, p_quantity);

END

DROP PROCEDURE IF EXISTS create_order;

CREATE PROCEDURE create_order(
    IN p_member_id VARCHAR(30),
    IN p_total_price DECIMAL(10,2),
    IN p_pay_status INT,
    OUT p_order_id VARCHAR(30)
)
BEGIN
    SET p_order_id = CONCAT('Ms', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), FLOOR(RAND() * 1000));

    INSERT INTO Orders (order_id, member_id, total_price, pay_status)
    VALUES (p_order_id, p_member_id, p_total_price, p_pay_status);
END

CREATE PROCEDURE create_order_detail(
    IN p_order_id VARCHAR(30),
    IN p_product_id VARCHAR(20),
    IN p_quantity INT,
    IN p_stand_price DECIMAL(10,2),
    IN p_item_price DECIMAL(10,2)
)
BEGIN
    INSERT INTO Order_Detail (order_id, product_id, quantity, stand_price, item_price)
    VALUES (p_order_id, p_product_id, p_quantity, p_stand_price, p_item_price);
END

CREATE PROCEDURE update_product_stock(
    IN p_product_id VARCHAR(20),
    IN p_quantity INT
)
BEGIN
    UPDATE Product
    SET quantity = quantity - p_quantity
    WHERE product_id = p_product_id AND quantity >= p_quantity;
END




