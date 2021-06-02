/*
Navicat MySQL Data Transfer

Source Server         : mybatis
Source Server Version : 50726
Source Host           : 127.0.0.1:3306
Source Database       : train

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2021-06-03 07:48:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for all_template
-- ----------------------------
DROP TABLE IF EXISTS `all_template`;
CREATE TABLE `all_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255) DEFAULT NULL,
  `guan_sort` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for event_change
-- ----------------------------
DROP TABLE IF EXISTS `event_change`;
CREATE TABLE `event_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event` int(11) DEFAULT NULL,
  `date_time` int(11) DEFAULT NULL,
  `gong_li_biao` int(11) DEFAULT NULL,
  `other` int(11) DEFAULT NULL,
  `distance` int(11) DEFAULT NULL,
  `singal_machine` int(11) DEFAULT NULL,
  `xin_hao` int(11) DEFAULT NULL,
  `speed` int(11) DEFAULT NULL,
  `restrict_speed` int(11) DEFAULT NULL,
  `ling_wei` int(11) DEFAULT NULL,
  `qian_yin` int(11) DEFAULT NULL,
  `qian_hou` int(11) DEFAULT NULL,
  `guan_ya` int(11) DEFAULT NULL,
  `gang_ya` int(11) DEFAULT NULL,
  `zhuan_su_dian_liu` int(11) DEFAULT NULL,
  `jun_gang1` int(11) DEFAULT NULL,
  `jun_gang2` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL,
  `is_save` int(11) DEFAULT NULL,
  `zhuang_bei_dian` varchar(255) DEFAULT NULL,
  `file_start_time` datetime DEFAULT NULL,
  `che_ci` varchar(255) DEFAULT NULL,
  `ji_xing` varchar(255) DEFAULT NULL,
  `che_hao` varchar(255) DEFAULT NULL,
  `si_ji_name` varchar(255) DEFAULT NULL,
  `fu_si_ji_name` varchar(255) DEFAULT NULL,
  `file_state` int(11) DEFAULT NULL,
  `test_score` int(11) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `old_file_name` varchar(255) DEFAULT NULL,
  `ji_che_hao` varchar(255) DEFAULT NULL,
  `jiao_lu_hao` varchar(255) DEFAULT NULL,
  `ji_chang` int(11) DEFAULT NULL,
  `start_station` varchar(255) DEFAULT NULL,
  `ban_ben` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1254 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ji_che_info
-- ----------------------------
DROP TABLE IF EXISTS `ji_che_info`;
CREATE TABLE `ji_che_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ji_xing` varchar(255) DEFAULT NULL,
  `ji_xing_hao` int(11) DEFAULT NULL,
  `ji_che_hao` int(11) DEFAULT NULL,
  `dan_shuang_duan` int(11) DEFAULT NULL,
  `other_ji_che_hao` int(11) DEFAULT NULL,
  `zhi_dong_ji_name` varchar(255) DEFAULT NULL,
  `zhi_dong_ji_hao` int(11) DEFAULT NULL,
  `lie_zhi_ratio` double DEFAULT NULL,
  `step_shun_xu_id` int(11) DEFAULT NULL,
  `event_change_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for quan_cheng
-- ----------------------------
DROP TABLE IF EXISTS `quan_cheng`;
CREATE TABLE `quan_cheng` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_id` int(11) DEFAULT NULL,
  `xu_hao` int(11) DEFAULT NULL,
  `event` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `gong_li_biao` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `other` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `distance` int(11) DEFAULT NULL,
  `signal_machine` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `xin_hao` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `speed` int(11) DEFAULT NULL,
  `restrict_speed` int(11) DEFAULT NULL,
  `ling_wei` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `qian_yin` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `qian_hou` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `guan_ya` int(11) DEFAULT NULL,
  `gang_ya` int(11) DEFAULT NULL,
  `zhuan_su_dian_liu` int(11) DEFAULT NULL,
  `jun_gang1` int(11) DEFAULT NULL,
  `jun_gang2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `file_id` (`file_id`),
  CONSTRAINT `file_id` FOREIGN KEY (`file_id`) REFERENCES `file_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1513323 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for step_analysis
-- ----------------------------
DROP TABLE IF EXISTS `step_analysis`;
CREATE TABLE `step_analysis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_id` int(11) DEFAULT NULL,
  `ji_xing` varchar(255) DEFAULT NULL,
  `one_step` int(11) DEFAULT NULL,
  `two_step` int(11) DEFAULT NULL,
  `three_step` int(11) DEFAULT NULL,
  `four_step` int(11) DEFAULT NULL,
  `five_step` int(11) DEFAULT NULL,
  `shuang_one_step` int(255) DEFAULT NULL,
  `shuang_two_step` int(11) DEFAULT NULL,
  `shuang_three_step` int(11) DEFAULT NULL,
  `shuang_four_step` int(11) DEFAULT NULL,
  `shuang_five_step` int(11) DEFAULT NULL,
  `shuang_six_step` int(11) DEFAULT NULL,
  `shuang_seven_step` int(11) DEFAULT NULL,
  `shuang_eight_step` int(11) DEFAULT NULL,
  `shuang_nine_step` int(11) DEFAULT NULL,
  `six_step` int(11) DEFAULT NULL,
  `seven_step` int(11) DEFAULT NULL,
  `eight_step` int(11) DEFAULT NULL,
  `nine_step` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1246 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for step_info
-- ----------------------------
DROP TABLE IF EXISTS `step_info`;
CREATE TABLE `step_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_xiang_dian` int(11) DEFAULT NULL,
  `end_xiang_dian` int(11) DEFAULT NULL,
  `xiang_dian_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for step_select
-- ----------------------------
DROP TABLE IF EXISTS `step_select`;
CREATE TABLE `step_select` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_id` int(11) DEFAULT NULL,
  `max_stress` int(11) DEFAULT NULL,
  `min_stress` int(11) DEFAULT NULL,
  `prior_number` int(11) DEFAULT NULL,
  `stress_name` varchar(255) DEFAULT NULL,
  `max_time` int(11) DEFAULT NULL,
  `min_time` int(11) DEFAULT NULL,
  `guan_sort` varchar(255) DEFAULT NULL,
  `is_end` int(11) DEFAULT NULL,
  `state_enter` int(11) DEFAULT NULL,
  `state_out` int(11) DEFAULT NULL,
  `is_stable` int(11) DEFAULT NULL,
  `is_depend` int(11) DEFAULT NULL,
  `start_id` int(11) DEFAULT NULL,
  `end_id` int(11) DEFAULT NULL,
  `sort_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for step_shun_xu
-- ----------------------------
DROP TABLE IF EXISTS `step_shun_xu`;
CREATE TABLE `step_shun_xu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `one_step` int(11) DEFAULT NULL,
  `two_step` int(11) DEFAULT NULL,
  `three_step` int(11) DEFAULT NULL,
  `four_step` int(11) DEFAULT NULL,
  `five_step` int(11) DEFAULT NULL,
  `six_step` int(11) DEFAULT NULL,
  `seven_step` int(11) DEFAULT NULL,
  `eight_step` int(11) DEFAULT NULL,
  `nine_step` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for username
-- ----------------------------
DROP TABLE IF EXISTS `username`;
CREATE TABLE `username` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xiang_dian
-- ----------------------------
DROP TABLE IF EXISTS `xiang_dian`;
CREATE TABLE `xiang_dian` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ji_xing` varchar(255) DEFAULT NULL,
  `step` int(11) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zhuan_dian
-- ----------------------------
DROP TABLE IF EXISTS `zhuan_dian`;
CREATE TABLE `zhuan_dian` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `max_time` datetime DEFAULT NULL,
  `max_stress` int(11) DEFAULT NULL,
  `min_stress` int(11) DEFAULT NULL,
  `min_time` datetime DEFAULT NULL,
  `left_stress` int(11) DEFAULT NULL,
  `right_stress` int(11) DEFAULT NULL,
  `step_select_id` int(11) DEFAULT NULL,
  `stress_name` varchar(255) DEFAULT NULL,
  `start_pos` int(11) DEFAULT NULL,
  `end_pos` int(11) DEFAULT NULL,
  `file_id` int(11) DEFAULT NULL,
  `prior_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
