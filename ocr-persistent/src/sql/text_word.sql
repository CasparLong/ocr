/*
Navicat MySQL Data Transfer

Source Server         : 阿里云
Source Server Version : 50639
Source Host           : 47.106.84.60:3306
Source Database       : ocr

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-04-17 17:09:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `text_word`
-- ----------------------------
DROP TABLE IF EXISTS `text_word`;
CREATE TABLE `text_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(255) NOT NULL,
  `word_type` tinyint(4) NOT NULL COMMENT '类型,0-商城,1-金额相关',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of text_word
-- ----------------------------
INSERT INTO `text_word` VALUES ('1', '广州麦当劳信德商务大厦分店', '0', '2018-04-14 19:37:50', '2018-04-14 19:37:50');
INSERT INTO `text_word` VALUES ('2', '广州麦当劳信德商务大厦', '0', '2018-04-14 19:38:11', '2018-04-14 19:38:11');
INSERT INTO `text_word` VALUES ('3', '小计', '1', '2018-04-14 19:38:27', '2018-04-14 19:38:27');
INSERT INTO `text_word` VALUES ('4', '合计', '1', '2018-04-17 16:57:25', '2018-04-17 16:57:25');
INSERT INTO `text_word` VALUES ('5', '实收', '1', '2018-04-17 16:58:59', '2018-04-17 16:58:59');
