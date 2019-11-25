/*
 Navicat Premium Data Transfer

 Source Server         : MySQL5.7
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : preliminary_underwriting

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 24/11/2019 13:18:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_agent
-- ----------------------------
DROP TABLE IF EXISTS `tb_agent`;
CREATE TABLE `tb_agent`  (
  `agent_id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `openid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `employee_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `company` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `firm` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`agent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_agent
-- ----------------------------
INSERT INTO `tb_agent` VALUES (83, '长城 喻梅  18618104918', 'https://wx.qlogo.cn/mmopen/vi_32/niaWaBeN2vBzXSMBCg0NEib3HRwDztiaEIZTq7AT7S0j0Q5LwQaKjpWF5895t1wN8MD7nJQicyF1n8NKWsTeDtQV1A/132', 'RYby+rZUsa42+kkep344X+goS9d/kvwoRCOci6F6ls4=', '0022990035', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (84, '丽', 'https://wx.qlogo.cn/mmopen/vi_32/ickRAJe2gicXyoKMtwL9A176DGDGCwCkVuQjh3hkGUOiaticMIIquLyvZJue0G5NiaHKiaGfQqdO1Zd0oBj8m7xJXOMQ/132', 'gHCkrZJekbaQjM2jCLFwixAelUFl86WsUbvJl0R4bZQ=', '0022011358', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (87, '樊颖', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIVedOUm8wj7oq6cRYCNuRziayn2W4y1CDXB1utjPT2WKRBrFp9zh2ZCky575wIrPkxOSn8YAo4QXQ/132', 'Mw78bsdLPWilHVv7fNqgIOmkZNNLlGQ7nymi6YevtEQ=', '0022990000', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (88, 'E兔', 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqVpSTpNcLMkXibajmxmNyCwaTlcAfEicj2Z6WcKYo6XgEMLqy3trgDonQicic6kmehHwyRPltRNvXKbQ/132', 'PA8FSS/FbQvRJfSV9bRENpH5tINuVEssJRe1OZEJr7U=', '0005001655', '盛唐融信保险代理（北京）有限公司', '盛唐融信');
INSERT INTO `tb_agent` VALUES (89, 'lightea', 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erymJoro2qibqBtQ55n6ibITxWic49dujia3RV1Ciagz13EPN40TBKkDrE8G2yIKWT99tnR4Biaf0VWzNgA/132', '8dlxaHBwW4HsnSpMp6QCi06fViribI/RRAwvWLmVpPs=', '0022999999', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (90, '辉@', 'https://wx.qlogo.cn/mmopen/vi_32/txxQxJE9LI8cCwMPoOZc8asgMxIqAHWia4Mk4X9kFvnmiahzmAQZUkQYxD6iaaOgAMxX39747oZznmOpgv5bwuP2g/132', '7AOc35x/u+oneWqo7TEifc2ymufS1JCsoqXOYyQo5Ik=', '0022999999', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (91, '我是叶子', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJuZmYkOSJzSuibh3t3hx0axNNh1rmWPKH07RjBd4LEppNokjkQlUX3h3SiaIXYfeTScY2vWXnYibbGw/132', 'Owc/SZrTDb3uLaRARwhu/LIr+Jf0ml9vz414dZPfYDQ=', '0022990057', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (94, '王春艳', 'https://wx.qlogo.cn/mmopen/vi_32/HLDjKFe3k6FPWgxmw8JwSia3Yk3lLI0v50C4eibo0RdstPEzJR6VwDNxkmA7mHFqLh4g68zss4hia0qNCCZJNOqDQ/132', 'VAElo/CJDg23lGMWyWggfvcluCOrH+Al2IbaaHrP/a8=', '0024000277', '长城人寿北京分公司经代部', '长城经代');
INSERT INTO `tb_agent` VALUES (95, '吻过地平线', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL9NonveERGicRkpd4JpjT4QHMYWA7Q0BYkcx5Ixk2ud7FRxs0OOXnq7NS0npNhdoKeCc4iaxwcHI8A/132', '737/C38/KCMnQNzZp1yvUi68nr2tqW8n6nutPJF12go=', '0005003085', '盛唐融信保险代理（北京）有限公司', '盛唐融信');
INSERT INTO `tb_agent` VALUES (96, 'cain', 'https://wx.qlogo.cn/mmopen/vi_32/IEEnWB1xYlaqjzMebgwwPX5xiaaq0VAyTsBibY6GkiaHW6R9ywoq9PQ2X1C8vvmpN6rcYYxxJibnqBaKic0xPdUnxNg/132', '8cAsNhxBq7T5h7rmSJPLSCkT5bePDTUCOtyuOPKQYaI=', '0024200290', '长城人寿北京分公司经代部', '长城经代');
INSERT INTO `tb_agent` VALUES (97, 'Dr.Gao', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJhKbN4I2mv6yibP2LLiaqVVWTtaAHkA5bzpvwibFOoVeOuVR4NliakJlBWco5H1rfn5P0mbCTf57tMqA/132', 'JtUgzjYaXko2PDMHcLGNnZvSpSoph97Nmdx34PCXCcY=', '0024990056', '长城人寿北京分公司经代部', '长城经代');
INSERT INTO `tb_agent` VALUES (98, 'без т.нар.', 'https://wx.qlogo.cn/mmopen/vi_32/257kicUhyI7FRoibJTmu5iaibtRN9f4SrRYxlsoCVt4FoVbQmlTic6hLQ1DWSeTkDI0F54xDJC8lYghRTW7piamdSdZw/132', 'pIAEbjpq5SYEqZoA5ki6oGMroQtskakctKu7GJFJ2bc=', '0022000000', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (99, '张焱森', 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqLkpP7NYKOWuo5locbphWibqRIgicVQKibt5ZuRXt0V62dia10h7etxFUibbcFKN2PJ0s87kTC38E6Bsg/132', 'eQSUfvNAxu47LjF+J2v2gbgQ2hkrWQW+PcyjhmNx/8c=', '0022100271', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (100, 'Dream💫', 'https://wx.qlogo.cn/mmopen/vi_32/dBIWQshSAMqluzmWYFu9lGg0MjLAtoypM9L3I8Uj5K7RGCZQjrdbKT5IxRr4hp8XBFTjYtu7w1wjiaAdlMzye5g/132', 'kH2mflchQrf2xqOoPVE8zWqZeV6LBC/qJ8FjjELgNL8=', '0024990065', '长城人寿北京分公司经代部', '长城经代');
INSERT INTO `tb_agent` VALUES (101, '绍卿', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTInQXtupVJGRjJzMJjOibdFb0SktuARib4xHSb1Hou58qiaRM8OdP3gDWeHqVobq61xlF4JrABoYE3uA/132', 'Kz5BuVePk3lerw6gyMy4SBtyz3FbcKnhpcpM1zIblhI=', '0024990063', '长城人寿北京分公司经代部', '长城经代');
INSERT INTO `tb_agent` VALUES (102, '小芳', 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqejs6Mzty7Cx9Dz61PzzV1P1QPEWEEBvQFodu1hTpAyjDqZNQpSRgnpb0iavLQy04yvyV9NW4pTTg/132', 'FllNkbs8R22BJ1yT+psdeRksHT8Iaf0wM/vK8qpJJ14=', '0024990068', '长城人寿北京分公司经代部', '长城经代');
INSERT INTO `tb_agent` VALUES (103, '莲', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIs5Ln4825jzRviakLiaKIiass4yTfnnxFBsHVs57SQiaYMU1ppngeZTFvVBvGBBa8eXibvMibJxmLTNXiaQ/132', 'ujVDkeEIZyTn4+Wr7fDNc1x/Z3WYPczanyY/5RpMlVg=', '0022990064', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (104, '雪成', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKnmKIhvg8UmQiaELdSXL3icKJ3tHGJudZW6JeicYeLbVn8BTstbbk43YtLibCgjO3iakUgsicrZhAJVDMw/132', 'P/bYGwCpN1Xj1G3/fP3IuoEN4i82Zetktw0FmNGDRKk=', '0022990043', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (105, '曙光女神之宽恕', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLVViawqRqFBnibT2ibQLV7s31hAribvHuOyHUQugz72FLnkh4ywW9HSVJegZMiaTkBicM2Aej6FXIBcGqQ/132', 'OXlPYl6RBI8TFEy9LEU2eEgI6HuHu+K8yBRU2r8Kulo=', '0022990053', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (106, 'LHT', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJLpxkMl7PX6xfiaUggQRMrXhYkIFRjgrJe7dB14QseAtlgPGkmGqhoyG0h2Ur5vsgpw5GGLSvHm1g/132', 'Zs7HLKxEV9NH30YgI2D12gRZCyixnh/RIQSNknju+sY=', '0022990046', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (107, 'A徐咏強', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKnuYbm9xricGuUIfibuK6icrTICTuibWeqcZPHr8IEGLGWyZwNGkO7WDrKM3ibgksbQ1guAgC3YRu9rJw/132', 'EYvdEwLk7DDqPjQgeYFNmhx465IL2yZs7L+sXJsuA7E=', '0022990042', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (108, '贾高荔', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIfanFzmAMw8KibEyAOdib0jLibloG3cTaibUGBtxo00OQibKmESqRym0CBzVK3v9cO6IdpG73y4ibictJzw/132', 'FlNip6dcjRBN26POzPyDYAUCk5gk2brJOKpfON71sjI=', '0022990044', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (109, '、、、劉', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJghSQXrkiaQjZR56sdUkXThLmk6tQkd0x4tXOKxCLcYDKYYs0bfOOGdnuYgooyLnXKagbbWWrlZ8A/132', 'HygRQOlCAlBe9DKNrgSki45APhSulhXG0tYmwkgkV8g=', '0022990049', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (110, '陈海雁', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIY60ETaXWlAeeMZo9Qsj4icdnkVcxViacchze6Tj9bd2EOK3RhwnhCWsFalN1wRGvEicLDUIUxhe0Dw/132', 'cHDpPuPMD9oFALOToodOQVp1CHNOfQxbmpyRTNjloak=', '0022990047', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (111, '长城丁国斌13811583889', 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqsv5AVB7D2C611t4vUjYicVU1GS64jXFJ9KjgQgE3c2sHCvpLDlNTcLKTo0P8hs7g6E2RQicM1RCgw/132', 'VhJ2Phv95FMB82AVj+qiZRh9KTOITUlx7SlyY0BUt6s=', '0022990014', '长城人寿北京分公司续期收展部', '长城收展');
INSERT INTO `tb_agent` VALUES (112, 'AA人心换人心', 'https://wx.qlogo.cn/mmopen/vi_32/XZuqcSKjTykpTJBc8ZlrWU4y0AVX4TazAqR7cN2SCXQNxMZGeLicXeLO2Vx9BuroBqnicaQxvibNKY5zX2Rp60saw/132', 'LQbCvkIQFu7sB1sL3DG5Wp9U6n7Z5nvlmkdgSvwEpDU=', '0022990051', '长城人寿北京分公司续期收展部', '长城收展');

-- ----------------------------
-- Table structure for tb_announcement
-- ----------------------------
DROP TABLE IF EXISTS `tb_announcement`;
CREATE TABLE `tb_announcement`  (
  `announcement_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`announcement_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_announcement
-- ----------------------------
INSERT INTO `tb_announcement` VALUES (17, '欢迎使用预核保小程序', '预核保服务是长城北分在在“科技助力营销”上迈出的小小一步。请您按要求填写客户资料并提供相关材料，我们会在两个工作日内做出回复。 我们希望将来能够有更多的服务种类在线上实现，减少各种中间环节，使得营销业务伙伴将更多的精力投注于为客户提供服务。', '2019-07-16 14:57:50');

-- ----------------------------
-- Table structure for tb_company
-- ----------------------------
DROP TABLE IF EXISTS `tb_company`;
CREATE TABLE `tb_company`  (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `firm` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`company_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_company
-- ----------------------------
INSERT INTO `tb_company` VALUES (6, '明亚保险经纪股份有限公司北京分公司', '明亚', '0001');
INSERT INTO `tb_company` VALUES (7, '中植保险经纪有限公司', '中植', '0002');
INSERT INTO `tb_company` VALUES (8, '华康保险代理有限公司北京分公司', '华康', '0003');
INSERT INTO `tb_company` VALUES (9, '众信易诚保险代理股份有限公司北京分公司', '众信易诚', '0004');
INSERT INTO `tb_company` VALUES (10, '盛唐融信保险代理（北京）有限公司', '盛唐融信', '0005');
INSERT INTO `tb_company` VALUES (11, '大童保险销售服务有限公司北京燕京分公司', '大童燕分', '0006');
INSERT INTO `tb_company` VALUES (12, '大童保险销售服务有限公司北京分公司', '大童北分', '0007');
INSERT INTO `tb_company` VALUES (13, '泛华联兴保险销售股份公司北京分公司', '泛华联兴', '0008');
INSERT INTO `tb_company` VALUES (14, '北京市金诚华夏保险代理有限公司', '金诚华夏', '0009');
INSERT INTO `tb_company` VALUES (15, '康宏碧升保险代理有限公司', '康宏碧升', '0010');
INSERT INTO `tb_company` VALUES (16, '北京宏利保险代理有限公司', '宏利', '0011');
INSERT INTO `tb_company` VALUES (17, '北京国恒保险代理有限公司', '国恒', '0012');
INSERT INTO `tb_company` VALUES (18, '北京诚信保险代理有限公司', '诚信', '0013');
INSERT INTO `tb_company` VALUES (19, '五星在线保险销售有限公司', '五星在线', '0014');
INSERT INTO `tb_company` VALUES (20, '上海明大保险经纪有限公司北京分公司', '上海明大', '0015');
INSERT INTO `tb_company` VALUES (21, '黎明保险经纪有限公司北京分公司', '黎明北分', '0016');
INSERT INTO `tb_company` VALUES (22, '北京银河时空保险经纪有限责任公司', '银河时空', '0017');
INSERT INTO `tb_company` VALUES (23, '隽天保险经纪（上海）有限责任公司北京分公司', '隽天北分', '0018');
INSERT INTO `tb_company` VALUES (24, '华夏信达保险经纪（北京）有限公司北京分公司', '华夏信达', '0019');
INSERT INTO `tb_company` VALUES (25, '方胜磐石保险经纪有限公司', '方盛磐石', '0020');
INSERT INTO `tb_company` VALUES (26, '北京高晟财富保险代理股份有限公司', '高晟财富', '0021');
INSERT INTO `tb_company` VALUES (27, '长城人寿北京分公司续期收展部', '长城收展', '0022');
INSERT INTO `tb_company` VALUES (29, '长城人寿北京分公司个险部', '长城个险', '0023');
INSERT INTO `tb_company` VALUES (30, '长城人寿北京分公司经代部', '长城经代', '0024');

-- ----------------------------
-- Table structure for tb_underwriting
-- ----------------------------
DROP TABLE IF EXISTS `tb_underwriting`;
CREATE TABLE `tb_underwriting`  (
  `underwriting_id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_id` int(11) NULL DEFAULT NULL,
  `form_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `conclusion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `submit_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`underwriting_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 471 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_underwriting
-- ----------------------------
INSERT INTO `tb_underwriting` VALUES (264, 83, 'a832df59abba41c985a58c5749efc95c', '李女士', 'female', '1997-07-11', '15934586248', '健康', NULL, NULL, '2019-07-11 10:13:14', '2019-07-11 15:34:45');
INSERT INTO `tb_underwriting` VALUES (265, 83, '0fd657b6d3374a5da0bfb0450fa3197d', '李女士', 'female', '2004-03-11', '15934586248', '健康', '/uploadData/2019/07/11/李女士/3732477e-36b1-412a-86b7-c065df5676ff.jpg', '审核结果：审核通过，备注：通过。', '2019-07-11 10:14:41', '2019-07-15 11:40:59');
INSERT INTO `tb_underwriting` VALUES (266, 83, '429cbb372e5a482ca2420b57ebde826b', '李女士', 'female', '1999-07-11', '15934586248', '健康', '/uploadData/2019/07/11/李女士/4183b4f1-b5ee-4bda-a067-a60c084ea659.jpg', '审核结果：审核失败，备注：延期。', '2019-07-11 10:21:21', '2019-07-11 15:35:17');
INSERT INTO `tb_underwriting` VALUES (267, 84, 'f38828b518984ae6a71e0e4101c4a038', '陈女士', 'female', '1997-07-11', '15934586248', '高血糖', NULL, NULL, '2019-07-11 16:23:59', '2019-07-15 11:40:11');
INSERT INTO `tb_underwriting` VALUES (268, 84, '8b03a6e3fb6e4266bd5623617a8c2e02', '陈伟霆', 'female', '2019-07-11', '15934586248', '高血糖', NULL, '审核结果：审核通过，备注：标体通过。', '2019-07-11 16:27:26', '2019-07-15 11:40:37');
INSERT INTO `tb_underwriting` VALUES (275, 87, 'c03fa95b2a2e492a92154af19ae55a16', '王女士', 'female', '1992-07-15', '15934586248', '高血压', NULL, '审核结果：审核通过，备注：通过。', '2019-07-15 09:10:14', '2019-07-15 11:38:22');
INSERT INTO `tb_underwriting` VALUES (277, 91, '1453da71d8424329b5696516a831080c', '汪萌', 'female', '1978-11-05', '15934586248', '健康', NULL, '审核结果：审核失败，备注：附件未上传，请重新提交。', '2019-07-16 15:51:33', '2019-07-16 16:48:07');
INSERT INTO `tb_underwriting` VALUES (278, 91, '0f5062cb2f7f488eb56d5139107bebf1', '汪萌', 'female', '1978-11-05', '15934586248', '健康', NULL, '审核结果：审核通过，备注：通过。', '2019-07-16 15:53:26', '2019-07-16 16:48:18');
INSERT INTO `tb_underwriting` VALUES (279, 91, '2615af9674364817b633db890b5df2fd', '尹根根', 'male', '1980-03-28', '15934586248', '高血糖', NULL, '审核结果：审核失败，备注：附件未上传，请重新提交。', '2019-07-16 16:03:08', '2019-07-16 16:48:46');
INSERT INTO `tb_underwriting` VALUES (280, 94, 'aa7f40c2048c418e9eeaaa0ce25cce3a', '王骞阅', 'female', '1986-07-17', '', '高血糖', '/uploadData/2019/07/17/王骞阅/fb796e4b-5ad5-42ed-ba80-2e37f5a28ce8.jpg,/uploadData/2019/07/17/王骞阅/736e85e2-9516-43ef-806e-48adca84961e.jpg,/uploadData/2019/07/17/王骞阅/58e659a2-a0f1-4747-9168-8782ad33ae2e.jpg,/uploadData/2019/07/17/王骞阅/bdfc122d-c868-496d-bcac-8350f936ce1c.jpg', '审核结果：审核通过，备注：失败，条件不符合要求。', '2019-07-17 16:17:07', '2019-07-17 16:24:01');
INSERT INTO `tb_underwriting` VALUES (281, 94, 'cff7366c2ad8449e907bb27483ee70c5', '薛冰', 'male', '1976-07-17', '', '见附件', '/uploadData/2019/07/17/薛冰/a1a54ae7-8ee5-4247-8908-c4d1c46b60f2.jpg,/uploadData/2019/07/17/薛冰/2caf898d-1c63-4d98-a54e-afa5431df1ae.jpg', '审核结果：审核通过，备注：通过。', '2019-07-17 16:49:41', '2019-07-17 16:58:11');
INSERT INTO `tb_underwriting` VALUES (282, 94, 'f1385c354fef4619a6fb5ccbc188ad73', '李冬福', 'male', '2019-07-17', '15934586248', '健康', '/uploadData/2019/07/17/李冬福/a78fe8a5-1086-44d3-b541-6ed0a20a5ae0.jpg,/uploadData/2019/07/17/李冬福/b1666ae0-55f4-4e49-9693-c7aabbe73a59.jpg,/uploadData/2019/07/17/李冬福/c137c54b-016f-40ce-962d-901027f238e2.jpg,/uploadData/2019/07/17/李冬福/fdf732fc-1101-4ccc-ab3f-20dff053f9ad.jpg,/uploadData/2019/07/17/李冬福/6cc38c5a-96ae-485c-bd17-f1b5037e0c75.jpg,/uploadData/2019/07/17/李冬福/43799cbc-7f65-4df4-8c28-914db0eb9d67.jpg', '审核结果：审核通过，备注：失败，条件不符合要求。', '2019-07-17 21:00:14', '2019-07-18 10:17:46');
INSERT INTO `tb_underwriting` VALUES (283, 94, '73ee8390a9ac4f2ca4d796b66c0ded19', '王春艳', 'male', '2019-07-17', '15934586248', '健康', NULL, '审核结果：审核通过，备注：通过。', '2019-07-17 21:16:33', '2019-07-18 10:19:48');
INSERT INTO `tb_underwriting` VALUES (284, 94, 'bb409be20844436ea44ba9735a8158a1', '张琳客户', 'male', '2019-07-17', '15934586248', '健康', '/uploadData/2019/07/17/张琳客户/dd6d3a7e-3d60-434b-ae19-ffa714e3c5fe.jpg,/uploadData/2019/07/17/张琳客户/e20950d0-4866-4126-8a06-84e26599cff5.jpg,/uploadData/2019/07/17/张琳客户/c6460524-0cb0-40ad-959c-d4d18c4e607a.jpg,/uploadData/2019/07/17/张琳客户/a75ce585-d7bf-4878-a1a0-5d2d00d9a821.jpg', '审核结果：审核通过，备注：通过。', '2019-07-17 21:20:37', '2019-07-18 10:23:33');
INSERT INTO `tb_underwriting` VALUES (285, 94, '5b9d2b2d596a4b8a870800311b8005af', '张琳', 'male', '2019-07-17', '15934586248', '见附件', '/uploadData/2019/07/17/张琳/176d0965-26ed-46cc-842e-d139df77bc57.jpg,/uploadData/2019/07/17/张琳/d65de815-2915-4725-95b9-4406a9fdb6f4.jpg', '审核结果：审核通过，备注：失败，条件不符合要求。', '2019-07-17 21:21:55', '2019-07-18 10:25:05');
INSERT INTO `tb_underwriting` VALUES (286, 94, 'f0d95af86f8946ad80c5c6c66c99cfae', '赵聪', 'male', '1985-05-17', '15934586248', '见附件', NULL, '审核结果：审核失败，备注：附件未上传，请重新提交。', '2019-07-17 21:23:43', '2019-07-18 10:25:33');
INSERT INTO `tb_underwriting` VALUES (288, 94, '82b0485814624b26a245f2362a5b8350', '刘欣', 'female', '2019-07-18', '15934586248', '健康', NULL, '审核结果：审核失败，备注：附件未上传，请重新提交。', '2019-07-18 22:37:45', '2019-07-19 11:43:54');
INSERT INTO `tb_underwriting` VALUES (289, 94, '89f16c2e75bd4846905eb41d066e650b', '嘉彦', 'male', '2019-07-18', '15934586248', '见附件', '/uploadData/2019/07/18/嘉彦/1d2c4341-6904-4629-bca3-8d36f10afe2b.jpg,/uploadData/2019/07/18/嘉彦/32ca69f2-bd29-4411-802a-13b8836df9cb.jpg', '审核结果：审核通过，备注：失败，条件不符合要求。', '2019-07-18 22:42:53', '2019-07-19 11:48:14');
INSERT INTO `tb_underwriting` VALUES (290, 94, '448e3aa6b3ff4e7bbca7ccc41f210861', '胡兆燕', 'male', '2019-07-18', '15934586248', '附件', NULL, '审核结果：审核失败，备注：附件未上传，请重新提交。', '2019-07-18 22:44:02', '2019-07-19 11:48:34');
INSERT INTO `tb_underwriting` VALUES (291, 94, '0548c27650c44c10a03a178efb6b2937', '胡丽萍', 'male', '2019-07-18', '15934586248', '附件', '/uploadData/2019/07/18/胡丽萍/74c802f7-2104-49d1-b1db-e87921e473af.jpg,/uploadData/2019/07/18/胡丽萍/c7ca166e-560e-48c5-a5cc-b819823f62d7.jpg,/uploadData/2019/07/18/胡丽萍/7bf51882-62c2-4d73-a677-e352872901d9.jpg', '审核结果：审核通过，备注：失败，条件不符合要求。', '2019-07-18 22:45:57', '2019-07-19 11:50:44');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (8, '预核保', 'LY3Vfpb6oQhma3RaZpfqyw==', '预核保超级管理员', '17630329898', 200);
INSERT INTO `tb_user` VALUES (11, '测试账号', 'asYfuYwa9kUK7LnI9cas4A==', '', '17630329576', 300);

-- ----------------------------
-- Table structure for tb_zip
-- ----------------------------
DROP TABLE IF EXISTS `tb_zip`;
CREATE TABLE `tb_zip`  (
  `zip_id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `download` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `compress` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`zip_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_zip
-- ----------------------------
INSERT INTO `tb_zip` VALUES (1, '201907', NULL, '0');
INSERT INTO `tb_zip` VALUES (2, '201908', NULL, '0');
INSERT INTO `tb_zip` VALUES (5, '201911', NULL, '0');

SET FOREIGN_KEY_CHECKS = 1;
