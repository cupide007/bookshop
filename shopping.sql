/*
 Navicat Premium Dump SQL

 Source Server         : wsl
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : 127.0.0.1:3307
 Source Schema         : shopping

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 06/01/2026 14:12:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for addresses
-- ----------------------------
DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses`  (
  `address_id` int NOT NULL AUTO_INCREMENT COMMENT '地址唯一标识',
  `user_id` int NOT NULL COMMENT '用户ID',
  `recipient_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收件人姓名',
  `recipient_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收件人电话',
  `address_detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `is_default` tinyint(1) NULL DEFAULT 0 COMMENT '是否为默认地址',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否为已删除的记录',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`address_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `addresses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of addresses
-- ----------------------------
INSERT INTO `addresses` VALUES (1, 1, '张三', '13800000000', '北京市海淀区XX路XX号', 0, 0, '2025-02-26 15:04:57', '2026-01-05 13:52:11');
INSERT INTO `addresses` VALUES (2, 2, '李四', '13800000001', '上海市浦东新区XX路XX号', 1, 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57');
INSERT INTO `addresses` VALUES (3, 1, '李华', '13800138000', '测试大学宿舍楼101', 1, 0, '2026-01-05 13:52:11', '2026-01-05 13:52:11');

-- ----------------------------
-- Table structure for cart_items
-- ----------------------------
DROP TABLE IF EXISTS `cart_items`;
CREATE TABLE `cart_items`  (
  `cart_id` int NOT NULL AUTO_INCREMENT COMMENT '购物车项唯一标识',
  `user_id` int NOT NULL COMMENT '用户ID',
  `product_id` int NOT NULL COMMENT '商品ID',
  `quantity` int NOT NULL COMMENT '商品数量',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否为已删除的记录',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`cart_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart_items
-- ----------------------------
INSERT INTO `cart_items` VALUES (1, 1, 1, 2, 1, '2025-02-26 15:04:57', '2026-01-05 14:00:17');
INSERT INTO `cart_items` VALUES (2, 1, 2, 4, 1, '2025-02-26 15:04:57', '2026-01-05 15:42:15');
INSERT INTO `cart_items` VALUES (3, 2, 3, 1, 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57');
INSERT INTO `cart_items` VALUES (4, 1, 1, 1, 1, '2026-01-05 14:15:19', '2026-01-05 14:16:27');
INSERT INTO `cart_items` VALUES (5, 1, 1, 2, 1, '2026-01-05 15:54:52', '2026-01-05 15:55:24');
INSERT INTO `cart_items` VALUES (6, 1, 2, 5, 1, '2026-01-05 15:54:57', '2026-01-05 15:56:07');
INSERT INTO `cart_items` VALUES (7, 1, 1, 1, 1, '2026-01-06 01:44:46', '2026-01-06 01:48:46');
INSERT INTO `cart_items` VALUES (8, 1, 2, 1, 1, '2026-01-06 01:58:11', '2026-01-06 02:55:01');
INSERT INTO `cart_items` VALUES (9, 1, 3, 1, 1, '2026-01-06 01:58:17', '2026-01-06 02:01:37');
INSERT INTO `cart_items` VALUES (10, 1, 1, 2, 1, '2026-01-06 03:34:44', '2026-01-06 03:57:52');
INSERT INTO `cart_items` VALUES (11, 1, 2, 1, 1, '2026-01-06 03:34:52', '2026-01-06 03:56:40');
INSERT INTO `cart_items` VALUES (12, 1, 4, 1, 1, '2026-01-06 03:48:40', '2026-01-06 04:28:15');
INSERT INTO `cart_items` VALUES (13, 1, 2, 2, 1, '2026-01-06 04:21:06', '2026-01-06 05:43:29');
INSERT INTO `cart_items` VALUES (14, 1, 3, 1, 0, '2026-01-06 05:47:10', '2026-01-06 05:47:10');
INSERT INTO `cart_items` VALUES (15, 1, 4, 1, 0, '2026-01-06 05:47:11', '2026-01-06 05:47:11');
INSERT INTO `cart_items` VALUES (16, 1, 8, 1, 0, '2026-01-06 05:47:14', '2026-01-06 05:47:14');

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类唯一标识',
  `parent_id` int NULL DEFAULT NULL COMMENT '父分类ID（用于多级分类）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否为已删除的记录',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, NULL, '电子产品', 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57');
INSERT INTO `categories` VALUES (2, NULL, '服装', 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57');
INSERT INTO `categories` VALUES (3, NULL, '家居用品', 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57');
INSERT INTO `categories` VALUES (4, NULL, '食品', 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57');

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `favorite_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_id` int NOT NULL,
  `is_delete` tinyint(1) NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`favorite_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorites
-- ----------------------------
INSERT INTO `favorites` VALUES (1, 1, 2, 1, '2026-01-06 11:33:19');
INSERT INTO `favorites` VALUES (2, 1, 4, 0, '2026-01-06 11:38:36');
INSERT INTO `favorites` VALUES (3, 1, 1, 0, '2026-01-06 11:50:36');

-- ----------------------------
-- Table structure for order_items
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items`  (
  `item_id` int NOT NULL AUTO_INCREMENT COMMENT '订单明细唯一标识',
  `order_id` int NOT NULL COMMENT '订单ID',
  `product_id` int NOT NULL COMMENT '商品ID',
  `quantity` int NOT NULL COMMENT '商品数量',
  `price` decimal(10, 2) NOT NULL COMMENT '商品单价',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否为已删除的记录',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `product_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE,
  INDEX `order_id`(`order_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_items
-- ----------------------------
INSERT INTO `order_items` VALUES (1, 1, 1, 1, 6999.00, 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57', NULL, NULL);
INSERT INTO `order_items` VALUES (2, 1, 2, 2, 99.99, 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57', NULL, NULL);
INSERT INTO `order_items` VALUES (3, 2, 3, 1, 2999.00, 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57', NULL, NULL);
INSERT INTO `order_items` VALUES (4, 3, 1, 1, 6999.00, 0, '2026-01-05 14:00:17', '2026-01-05 14:00:17', NULL, NULL);
INSERT INTO `order_items` VALUES (5, 4, 1, 1, 6999.00, 0, '2026-01-05 14:16:27', '2026-01-05 14:16:27', NULL, NULL);
INSERT INTO `order_items` VALUES (6, 5, 2, 4, 99.99, 0, '2026-01-05 15:42:15', '2026-01-05 15:42:15', NULL, NULL);
INSERT INTO `order_items` VALUES (7, 6, 2, 5, 99.99, 0, '2026-01-05 15:56:07', '2026-01-05 15:56:07', NULL, NULL);
INSERT INTO `order_items` VALUES (8, 7, 1, 1, 6999.00, 0, '2026-01-06 01:48:46', '2026-01-06 01:48:46', 'iPhone 14', '1.jpg');
INSERT INTO `order_items` VALUES (9, 8, 1, 2, 6999.00, 0, '2026-01-06 01:51:02', '2026-01-06 01:51:02', 'iPhone 14', '1.jpg');
INSERT INTO `order_items` VALUES (10, 8, 2, 1, 99.99, 0, '2026-01-06 01:51:02', '2026-01-06 01:51:02', '男士T恤', '2.webp');
INSERT INTO `order_items` VALUES (11, 9, 3, 1, 2999.00, 0, '2026-01-06 02:01:37', '2026-01-06 02:01:37', '沙发', '3.webp');
INSERT INTO `order_items` VALUES (12, 10, 2, 1, 99.99, 0, '2026-01-06 02:55:01', '2026-01-06 02:55:01', '男士T恤', '2.webp');
INSERT INTO `order_items` VALUES (13, 11, 2, 1, 99.99, 0, '2026-01-06 03:56:40', '2026-01-06 03:56:40', '男士T恤', '2.webp');
INSERT INTO `order_items` VALUES (14, 12, 1, 2, 6999.00, 0, '2026-01-06 03:57:52', '2026-01-06 03:57:52', 'iPhone 14', '1.jpg');
INSERT INTO `order_items` VALUES (15, 13, 4, 1, 10.00, 0, '2026-01-06 04:28:15', '2026-01-06 04:28:15', '薯片', '4.webp');
INSERT INTO `order_items` VALUES (16, 14, 2, 2, 99.99, 0, '2026-01-06 05:43:29', '2026-01-06 05:43:29', '男士T恤', '2.webp');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int NOT NULL AUTO_INCREMENT COMMENT '订单唯一标识',
  `user_id` int NOT NULL COMMENT '用户ID',
  `order_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '订单总金额',
  `status` enum('pending','paid','shipped','completed','cancelled') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending' COMMENT '订单状态',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否为已删除的记录',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货详细地址',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 1, '202502260001', 7098.98, 'paid', 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57', NULL, NULL, NULL);
INSERT INTO `orders` VALUES (2, 2, '202502260002', 2999.00, 'shipped', 0, '2025-02-26 15:04:57', '2025-02-26 15:04:57', NULL, NULL, NULL);
INSERT INTO `orders` VALUES (3, 1, '42d6ca2f-1f64-484e-94db-bca8b45a18bd', 6999.00, 'paid', 0, '2026-01-05 14:00:17', '2026-01-05 14:12:17', NULL, NULL, NULL);
INSERT INTO `orders` VALUES (4, 1, '90e78ec9-a7e0-43c7-b460-c1ec8844a882', 6999.00, 'pending', 0, '2026-01-05 14:16:27', '2026-01-05 14:16:27', NULL, NULL, NULL);
INSERT INTO `orders` VALUES (5, 1, 'a8dffaab-f0b5-4e46-97ae-3d1e7c7a114e', 399.96, 'paid', 0, '2026-01-05 15:42:15', '2026-01-05 15:50:05', NULL, NULL, NULL);
INSERT INTO `orders` VALUES (6, 1, 'b620fc44-4633-464d-abdf-85372b6a3b24', 499.95, 'paid', 0, '2026-01-05 15:56:07', '2026-01-05 15:56:10', NULL, NULL, NULL);
INSERT INTO `orders` VALUES (7, 1, '202601060001', 6999.00, 'paid', 0, '2026-01-06 01:48:47', '2026-01-06 01:49:02', '张三', '13800000000', '北京市海淀区XX路XX号');
INSERT INTO `orders` VALUES (8, 1, '202601060002', 14097.99, 'paid', 0, '2026-01-06 01:51:03', '2026-01-06 01:51:51', '张三', '13800000000', '北京市海淀区XX路XX号');
INSERT INTO `orders` VALUES (9, 1, '202601060003', 2999.00, 'paid', 0, '2026-01-06 10:01:38', '2026-01-06 02:01:42', '张三', '13800000000', '北京市海淀区XX路XX号');
INSERT INTO `orders` VALUES (10, 1, '202601060004', 99.99, 'paid', 0, '2026-01-06 10:55:01', '2026-01-06 02:55:03', '张三', '13800000000', '北京市海淀区XX路XX号');
INSERT INTO `orders` VALUES (11, 1, '202601060005', 99.99, 'paid', 0, '2026-01-06 11:56:41', '2026-01-06 03:56:43', '张三', '13800000000', '北京市海淀区XX路XX号');
INSERT INTO `orders` VALUES (12, 1, '202601060006', 13998.00, 'paid', 0, '2026-01-06 11:57:53', '2026-01-06 03:57:55', '张三', '13800000000', '北京市海淀区XX路XX号');
INSERT INTO `orders` VALUES (13, 1, '202601060007', 10.00, 'paid', 0, '2026-01-06 12:28:15', '2026-01-06 04:28:19', '张三', '13800000000', '北京市海淀区XX路XX号');
INSERT INTO `orders` VALUES (14, 1, '202601060008', 199.98, 'paid', 0, '2026-01-06 13:43:29', '2026-01-06 05:43:30', '张三', '13800000000', '北京市海淀区XX路XX号');

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `product_id` int NOT NULL AUTO_INCREMENT COMMENT '商品唯一标识',
  `category_id` int NOT NULL COMMENT '商品分类ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品描述',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `stock` int NOT NULL COMMENT '商品库存',
  `image_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品主图URL',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否为已删除的记录',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上架时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, 1, 'iPhone 14', '1_1.avif;1_2.avif;1_3.avif', 6999.00, 92, '1.jpg', 0, '2025-02-26 15:04:57', '2026-01-05 14:16:27');
INSERT INTO `products` VALUES (2, 2, '男士T恤', '2_1.avif;2_2.avif;2_3.avif;2_4.avif', 99.99, 98, '2.webp', 0, '2025-02-26 15:04:57', '2026-01-06 01:46:41');
INSERT INTO `products` VALUES (3, 3, '沙发', '3_1.avif;3_2.avif;3_3.avif;3_4.avif', 2999.00, 50, '3.webp', 0, '2025-02-26 15:04:57', '2025-12-22 10:53:42');
INSERT INTO `products` VALUES (4, 4, '薯片', '美味薯片，多种口味', 10.00, 499, '4.webp', 0, '2025-02-26 15:04:57', '2025-04-11 15:23:14');
INSERT INTO `products` VALUES (5, 1, 'Apple iPhone 14', 'The latest iPhone with advanced camera and performance.', 799.99, 100, '5.webp', 0, '2025-03-25 16:06:49', '2025-04-11 15:23:17');
INSERT INTO `products` VALUES (6, 2, 'Samsung Galaxy S23', 'Flagship smartphone with high-resolution display and powerful processor.', 999.99, 80, '6.webp', 0, '2025-03-25 16:06:49', '2025-04-11 15:23:22');
INSERT INTO `products` VALUES (7, 3, 'Sony WH-1000XM5 Headphones', 'Noise-canceling headphones with superior sound quality.', 349.99, 120, '7.png', 0, '2025-03-25 16:06:49', '2025-04-11 15:23:27');
INSERT INTO `products` VALUES (8, 1, 'Google Pixel 7', 'Compact smartphone with excellent camera and clean software experience.', 599.99, 90, '8.webp', 0, '2025-03-25 16:06:49', '2025-04-11 15:23:30');
INSERT INTO `products` VALUES (9, 2, 'Dell XPS 13 Laptop', 'Ultrabook with high-performance processor and lightweight design.', 1299.99, 60, '9.webp', 0, '2025-03-25 16:06:49', '2025-04-11 15:23:33');
INSERT INTO `products` VALUES (10, 3, 'Logitech MX Master 3 Mouse', 'Ergonomic wireless mouse with advanced features.', 69.99, 150, '10.webp', 0, '2025-03-25 16:06:49', '2025-04-11 15:23:40');
INSERT INTO `products` VALUES (11, 1, 'OnePlus 11', 'Fast and sleek smartphone with a great camera and long battery life.', 899.99, 70, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:52');
INSERT INTO `products` VALUES (12, 2, 'Bose QuietComfort 45 Headphones', 'Premium noise-canceling headphones with comfort and clarity.', 299.99, 110, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:53');
INSERT INTO `products` VALUES (13, 3, 'MacBook Air M2', 'Lightweight and powerful laptop with Apple Silicon.', 1499.99, 50, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:53');
INSERT INTO `products` VALUES (14, 1, 'Razer BlackWidow V3 Keyboard', 'Mechanical keyboard with RGB lighting and tactile switches.', 129.99, 130, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:54');
INSERT INTO `products` VALUES (15, 2, 'HP Spectre x360', 'Convertible laptop with a sleek design and powerful performance.', 1199.99, 45, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:54');
INSERT INTO `products` VALUES (16, 3, 'JBL Flip 5 Speaker', 'Portable Bluetooth speaker with rich sound quality.', 99.99, 200, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:54');
INSERT INTO `products` VALUES (17, 1, 'Xiaomi Mi 12', 'High-performance smartphone with a great camera and fast charging.', 749.99, 100, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:55');
INSERT INTO `products` VALUES (18, 2, 'Lenovo ThinkPad X1 Carbon', 'Business laptop with durability and performance.', 1399.99, 55, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:55');
INSERT INTO `products` VALUES (19, 3, 'Anker PowerCore 10000', 'Portable charger with high capacity and fast charging.', 29.99, 300, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:57');
INSERT INTO `products` VALUES (20, 1, 'Nokia 3310', 'Classic phone with long battery life and durability.', 49.99, 200, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:57');
INSERT INTO `products` VALUES (21, 2, 'ASUS ROG Zephyrus G15', 'Gaming laptop with powerful graphics and performance.', 1599.99, 30, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:57');
INSERT INTO `products` VALUES (22, 3, 'Philips Hue Bulb', 'Smart LED bulb with customizable colors and brightness.', 59.99, 180, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:24:59');
INSERT INTO `products` VALUES (23, 1, 'Motorola Moto G71', 'Budget-friendly smartphone with a good camera and performance.', 299.99, 120, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:25:00');
INSERT INTO `products` VALUES (24, 2, 'Microsoft Surface Pro 8', '2-in-1 device with a detachable keyboard and powerful performance.', 1099.99, 40, '1.jpg', 0, '2025-03-25 16:06:49', '2025-04-11 15:25:01');

-- ----------------------------
-- Table structure for reviews
-- ----------------------------
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews`  (
  `review_id` int NOT NULL AUTO_INCREMENT COMMENT '评价唯一标识',
  `user_id` int NOT NULL COMMENT '用户ID',
  `product_id` int NOT NULL COMMENT '商品ID',
  `rating` int NOT NULL COMMENT '评分（1-5）',
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评价内容',
  `image_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价图片URL',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否为已删除的记录',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  PRIMARY KEY (`review_id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品评价表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reviews
-- ----------------------------
INSERT INTO `reviews` VALUES (1, 1, 1, 5, '非常满意，性能很棒！', 'https://example.com/review1.jpg', 0, '2025-02-26 15:04:57');
INSERT INTO `reviews` VALUES (2, 2, 3, 4, '沙发很舒服，性价比高！', 'https://example.com/review2.jpg', 0, '2025-02-26 15:04:57');
INSERT INTO `reviews` VALUES (4, 1, 2, 1, '质量太差了！！！', NULL, 0, '2026-01-06 11:56:07');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密后的密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `role` enum('user','vip','admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'user' COMMENT '用户角色',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否为已删除的记录',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像图片名称',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'user1', '$2a$10$zc8VC5pf1afLS3PZgE5OPO0I3pUtbr57cIxs1RRduzBd4NCt1KLMG', 'user1@example.com', '13800000000', 'user', 0, '2025-02-26 15:04:57', '2025-12-22 11:42:23', NULL);
INSERT INTO `users` VALUES (2, 'user2', '$2a$10$zc8VC5pf1afLS3PZgE5OPO0I3pUtbr57cIxs1RRduzBd4NCt1KLMG', 'user2@example.com', '13800000001', 'vip', 0, '2025-02-26 15:04:57', '2025-12-22 11:42:25', NULL);
INSERT INTO `users` VALUES (3, 'admin', '$2a$10$zc8VC5pf1afLS3PZgE5OPO0I3pUtbr57cIxs1RRduzBd4NCt1KLMG', 'admin@example.com', '13800000002', 'admin', 0, '2025-02-26 15:04:57', '2025-12-22 11:42:28', NULL);
INSERT INTO `users` VALUES (4, 'newuser', '$2a$10$2scSM6H23/oDnToIoqi3bO1jaKmocmMhYDuNSshGuU7DUMyqGhYt2', 'new@test.com', '13900000000', 'user', 0, '2026-01-05 14:14:46', '2026-01-05 14:14:46', NULL);

SET FOREIGN_KEY_CHECKS = 1;
