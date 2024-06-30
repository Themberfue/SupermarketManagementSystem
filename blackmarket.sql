/*
 Navicat Premium Dump SQL

 Source Server         : Themberfue
 Source Server Type    : MySQL
 Source Server Version : 80012 (8.0.12)
 Source Host           : localhost:3306
 Source Schema         : blackmarket

 Target Server Type    : MySQL
 Target Server Version : 80012 (8.0.12)
 File Encoding         : 65001

 Date: 30/06/2024 13:06:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aorder
-- ----------------------------
DROP TABLE IF EXISTS `aorder`;
CREATE TABLE `aorder`  (
  `order_id` int(11) NOT NULL COMMENT '订单ID（主键）',
  `customer_id` int(11) NULL DEFAULT NULL COMMENT '客户ID（外键）',
  `customer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户姓名',
  `order_date` date NULL DEFAULT NULL COMMENT '订单日期',
  `money` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '金额',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of aorder
-- ----------------------------
INSERT INTO `aorder` VALUES (3, 1, '1', '2024-05-28', '11.0');
INSERT INTO `aorder` VALUES (2, 1, '1', '2024-05-28', '1.0');
INSERT INTO `aorder` VALUES (4, 2, '2', '2024-06-23', '199.0');
INSERT INTO `aorder` VALUES (5, 3, '元坤', '2024-05-29', '11.0');

-- ----------------------------
-- Table structure for cashier
-- ----------------------------
DROP TABLE IF EXISTS `cashier`;
CREATE TABLE `cashier`  (
  `cashier_id` int(11) NOT NULL COMMENT '收银员ID (主键)',
  `cashier_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收银员姓名',
  `shift` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班次（早班、晚班）',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `hire_date` date NULL DEFAULT NULL COMMENT '入职日期',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`cashier_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收银员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cashier
-- ----------------------------
INSERT INTO `cashier` VALUES (1, 'word', 'mor', '195', '@gmail', '2024-06-19', '1');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` int(11) NOT NULL COMMENT '分类ID（主键）',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `caregory_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '分类描述',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '服装', '西部时尚秀，穿对才能活。经典牛仔帽，挡风又遮阳；闪亮红西装，酒吧里最亮的星；猎人服装，低调中隐藏实力。想做风骚牛仔还是酷炫侠盗？西部世界里，只有不够骚的搭配，没有穿错的衣服！');
INSERT INTO `category` VALUES (2, '药品及药剂', '药品与药剂，西部生存必备。受伤了？来瓶健康药剂，瞬间满血复活。疲劳了？强效药水让你精神焕发。中毒了？解毒药剂帮你解除危机。总之，这些药品就是你的西部生存必备良药，关键时刻救你一命！');
INSERT INTO `category` VALUES (3, '杂货及干货', '西部杂货店，牛仔的百宝箱。口粮不足？干肉干果来顶饱。子弹告急？弹药杂货随时补给。钓鱼？各种饵料应有尽有。猎捕？陷阱、诱饵样样齐全。总之，杂货与干货就是你在荒野中的全能助手，确保你在西部的每一天都能过得风生水起！');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `customer_id` int(11) NOT NULL COMMENT '客户ID (主键)',
  `customer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户姓名',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮件\n',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `is_vip` tinyint(1) NULL DEFAULT 0 COMMENT '是否是VIP客户',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (123, '坤中', '1292134543', 'kunzhong.@example.com', '江西省', 1, '123');
INSERT INTO `customer` VALUES (102, '陈清轩', '1232132213', 'ccxc@example.com', '洛杉矶', 1, '102');
INSERT INTO `customer` VALUES (103, '州一名', '1212321312', 'zym@example.com', '王伦的', 1, '103');
INSERT INTO `customer` VALUES (1, NULL, NULL, NULL, NULL, 0, '1');
INSERT INTO `customer` VALUES (2, NULL, NULL, NULL, NULL, 0, '1');
INSERT INTO `customer` VALUES (3, NULL, NULL, NULL, NULL, 1, '1');

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`  (
  `inventory_id` int(11) NOT NULL COMMENT '库存ID（主键）',
  `product_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品ID（外键）',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`inventory_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inventory
-- ----------------------------

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `order_detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单明细ID（主键）',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '订单ID（外键）',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '商品ID（外键）',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `discount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '折扣',
  PRIMARY KEY (`order_detail_id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品ID（主键）',
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `product_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品描述',
  `product_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品位置',
  `category_id` int(11) NULL DEFAULT NULL COMMENT '分类ID（外键）',
  `supplier_id` int(11) NULL DEFAULT NULL COMMENT '供应商ID',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `stocks` int(11) NULL DEFAULT NULL COMMENT '库存数量',
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  INDEX `supplier_id`(`supplier_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('A01', '赌徒装束', '带图案的霰弹枪外套、定制时髦裤、银色饰扣盖尔登豪华马刺、金属尖头豪华无情靴和丝质奢华马甲，会让您成为桨轮游舫旅途中令人愉快的风景。', 'A_一_01', 1, 1, 123.25, 99);
INSERT INTO `product` VALUES ('A02', '露莓溪装束', '我们呈献给您我们提供的最佳装束之一。这款装束包含采用精致黄铜纽扣的精美剪裁霰弹枪外套、日常衬衫外套、双色霰弹枪皮套裤和精致的龙卷风靴，外加镶有皮革带的侦探帽，这些衣物相互配合相得益彰。', 'A_一_02', 1, 2, 115.50, 99);
INSERT INTO `product` VALUES ('A03', '瓦伦丁装束', '所有服装均保证跟所示内容分毫不差。我们为您诚心推介引领当今潮流的工作和休闲款色。包含长款防尘风衣、精纺马甲、棉质斜纹衬衫、加厚马裤和方头快枪手靴以及赌徒帽，搭配一双擦得油亮的骑兵手套。', 'A_一_03', 1, 1, 113.25, 99);
INSERT INTO `product` VALUES ('A04', '牛仔装束', '以下是我们的改良牛仔装束系列介绍。黑色棉质衬衫外套，搭配时髦条纹马甲、牛仔裤和黑色靴子。装饰着少许流苏的皮套裤，镶有皮革带的帽子，很适合在拉紧工作手套，卷起袖子照料动物时，遮住正午的阳光。', 'A_一_04', 1, 1, 107.75, 99);
INSERT INTO `product` VALUES ('A05', '科森装束', '无论从哪方面来看，这都是一款明智的套装，从防尘风衣、鸭舌帽、时尚的纽扣时髦裤到传教士靴。它赞美了一个人选择拒绝堕落生活的品质和美德，要是听到有人在背地里声张他的丑闻，他就会上前去给他们一拳。', 'A_一_05', 1, 2, 105.75, 99);
INSERT INTO `product` VALUES ('A06', '圣丹尼斯装束', '希望您能关注我们的装束系列，内含时髦的精美条纹日常衬衫外套，搭配有时髦图案的奢华马甲。与工作裤和传教士靴相得益彰，每一处细节和做工都能完美呈现您的身形和帅气，适合穿去参加花园宴会或是地下拳击。', 'A_一_06', 1, 3, 98.75, 99);
INSERT INTO `product` VALUES ('A07', '风滚草镇装束', '在赶牲畜、烙印、去角、追野牛和阉割动物时需要穿上特定的装束。我们的牛仔服款式销量比世上任何一家公司都多，配有防尘风衣、霰弹枪套裤、盖尔登马刺和实用的侦察兵帽。', 'A_一_07', 1, 4, 98.25, 99);
INSERT INTO `product` VALUES ('A08', '罗斯科装束', '男人的礼仪就是他的财富。虽然一个身无分文的乞丐也可能非常善良而且很有礼貌，但他仍然太过贫穷而无法享受更好的东西。', 'A_一_08', 1, 2, 96.25, 99);
INSERT INTO `product` VALUES ('A09', '厄尔装束', '我们制作过许多款式的衣服，但很少有像这一套这样精干、贴身、干练和高贵的。灵感源于罗兹的商人和那里悠久的家庭传统，这款长外套上装饰了闪耀的银纽扣。', 'A_一_09', 1, 4, 103.50, 99);
INSERT INTO `product` VALUES ('A10', '骑士装束', '组合了礼服大衣、长筒传道士靴、斯滕格豪华马刺、细条纹工作裤和时髦的浅色图案奢华马甲，这套装束在衣品出众的人群中倍受喜爱。', 'A_一_10', 1, 3, 159.75, 99);
INSERT INTO `product` VALUES ('A11', '经典礼服大衣', '经典双排扣礼服大衣。这款尾部开叉的全襟大衣采用大开口翻领设计，两侧带有口袋。内里含有里衬，采用了双排扣，一定会让您万分满意。', 'A_二_01', 1, 3, 18.50, 99);
INSERT INTO `product` VALUES ('A12', '侦察兵夹克', '双排扣羊皮侦查兵夹克。立领设计，全羊毛内衬。物超所值史无前例。就算穷得叮当响也要举债来买。', 'A_二_02', 1, 4, 19.75, 99);
INSERT INTO `product` VALUES ('A13', '漫步者夹克', '单排扣漫步者夹克。采用优质羊毛，特有丝光棉布开口翻领和内衬。是绅士们前往城镇解决正事的首选。', 'A_二_03', 1, 2, 16.50, 99);
INSERT INTO `product` VALUES ('A14', '精纺外套', '袖口翻边的单排扣毛绒外套。采用羊毛和棉材质，全里衬，开口翻领。特有法式袖口，正面有翻盖口袋，设计精妙。', 'A_二_04', 1, 1, 14.75, 99);
INSERT INTO `product` VALUES ('A15', '防尘风衣', '防尘风衣。一款高档长款风衣，采用小立领设计，正面有带纽扣的大口袋，裁剪干净利落。订购时请说明尺码。柔软精致。', 'A_二_05', 1, 2, 20.75, 99);
INSERT INTO `product` VALUES ('A16', '经典燕尾服', '经典双排扣燕尾服。高腰设计，衣领为大开口的翻领，适合在去歌剧院和决斗时穿。', 'A_二_06', 1, 4, 10.50, 99);
INSERT INTO `product` VALUES ('A17', '时髦长裤', '丝光棉布时髦裤。精心裁剪，带裤中线。出于众多原因，许多顾客对这款裤子非常感兴趣。这些原因过于冗长，无法进行进一步解释。', 'A_三_01', 1, 3, 10.00, 99);
INSERT INTO `product` VALUES ('A18', '城镇裤', '拉绒棉斜纹城镇裤。由于最近曝出的有关美国最大供应商的有趣新闻，我们以荒谬的价格从这家经营商处购买了大量的此类裤子。', 'A_三_02', 1, 4, 9.50, 99);
INSERT INTO `product` VALUES ('A19', '骑行工作裤', '帆布骑行工作裤。我们收购了印象中规模最大的裤子生产线。防刮擦设计，不会在工作过程中带来疼痛感。', 'A_三_03', 1, 2, 7.75, 99);
INSERT INTO `product` VALUES ('A20', '骑行牛仔裤', '粗布衬垫牛仔裤，时尚耐穿。还需要进一步说明我们以最低价格出售优质的新产品这一事实吗？', 'A_三_04', 1, 1, 7.50, 99);
INSERT INTO `product` VALUES ('A21', '粗布牛仔裤', '粗布牛仔裤。排扣式靴型牛仔裤。铆钉加固。良心出品，结实耐用。', 'A_三_05', 1, 2, 7.00, 99);
INSERT INTO `product` VALUES ('A22', '日常裤', '日常穿高腰帆布裤。可调节的背部系带。正面有两个口袋，可以装您的烟草和银元，让您不经意地拥抱好运。', 'A_三_06', 1, 1, 6.75, 99);
INSERT INTO `product` VALUES ('A23', '工作裤', '帆布工作裤，排扣式靴型下装。卖出的每一条都能为我们起到宣传作用，因此请以得体的方式穿上它们。', 'A_三_07', 1, 3, 6.25, 99);
INSERT INTO `product` VALUES ('A24', '牧场裤', '牛仔布牧场裤。排扣式设计。结实耐穿。那些讨价还价的朋友，请自便吧，别再有这种不可思议的幼稚行为了。', 'A_三_08', 1, 2, 5.25, 99);
INSERT INTO `product` VALUES ('A25', '翻边城镇裤', '拉绒棉斜纹翻边城镇裤。在镇上可不要穿错裤子，穿错裤子比不穿裤子、上学迟到和不学习还要糟糕。', 'A_三_09', 1, 1, 4.75, 99);
INSERT INTO `product` VALUES ('A26', '豪华无情靴', '带时髦钢制鞋头的豪华无情靴。自始至终用心打造。众多顾客唯一指定的时髦款式。', 'A_四_01', 1, 3, 29.00, 99);
INSERT INTO `product` VALUES ('A27', '磨床靴', '真皮铆钉磨床靴。高级尖头设计，靴筒采用定制缝线和铆钉装饰。外形轻巧，能彰显您的地位。', 'A_四_02', 1, 2, 28.25, 99);
INSERT INTO `product` VALUES ('A28', '传教士靴', '男士棉质牛皮传教士靴。由顶级牛皮制成，双排缝线工艺，装饰有拉绒棉。直筒设计，结实耐穿，质量有口皆碑。', 'A_四_03', 1, 2, 27.75, 99);
INSERT INTO `product` VALUES ('A29', '龙卷风靴', '男士帆布真皮龙卷风靴。方形鞋头，帆布镶边靴筒，采用机器缝制，两截双层鞋底，低后跟。', 'A_四_04', 1, 3, 24.50, 99);
INSERT INTO `product` VALUES ('A30', '豪华快枪手靴', '纯手工制作的豪华快枪手靴。精挑细选，由制靴大师纯手工打造而成。牛仔靴型鞋头。这双靴子采用优质材料制成，极为耐穿。', 'A_四_05', 1, 3, 25.50, 99);
INSERT INTO `product` VALUES ('A31', '运动员靴', '男士系带运动靴。加固喙形鞋头，系带骑行靴。鞋底重量适中，鞋底采用了缝边，鞋面带有系带。做工精致，上脚舒适，将是您的挚爱之选。', 'A_四_06', 1, 4, 21.00, 99);
INSERT INTO `product` VALUES ('A32', '快枪手靴', '纯手工定制快枪手靴。这双靴子绝对能满足您对好靴子的要求，对此我们完全可以保证。', 'A_四_07', 1, 1, 22.50, 99);
INSERT INTO `product` VALUES ('A33', '旧西部靴', '缝线鞋头荷叶饰边的旧西部靴。纯正小牛皮制成。尖头设计，上拉的靴袢可用于进行明喻、暗喻和新旧时代的对比。', 'A_四_08', 1, 2, 20.50, 99);
INSERT INTO `product` VALUES ('A34', '翼纹短靴', '男士翼纹短靴。双排缝线工艺，采用翼纹设计，刷油粒面皮，由机器缝制而成。皮革结实耐磨，给您舒适穿戴体验。', 'A_四_09', 1, 1, 19.00, 99);
INSERT INTO `product` VALUES ('A35', '猎鸟靴', '男士猎鸟靴。采用精选优质小牛皮制成，喙形鞋头，靴筒宽大。品质保证。', 'A_四_10', 1, 2, 17.50, 99);
INSERT INTO `product` VALUES ('A36', '光滑骑行靴', '男士骑行靴。用光滑小牛皮制成。喙形鞋头，修身靴筒。结实的鞋帮和鞋底。是一款价廉物美的牛皮靴。', 'A_四_11', 1, 3, 19.00, 99);
INSERT INTO `product` VALUES ('A37', '纽扣低帮鞋', '男士小牛皮纽扣低帮鞋，机器缝制。皮革经上油处理，由橡胶粘合。质优坚固耐用。', 'A_四_12', 1, 4, 15.75, 99);
INSERT INTO `product` VALUES ('A38', '骑兵靴', '优雅的骑兵靴。牛仔靴型鞋头，由深色全粒面皮革制成。若想要一双精美、耐穿，能带您重回骑兵时代的靴子的话，此时不买更待何时。', 'A_四_13', 1, 2, 17.00, 99);
INSERT INTO `product` VALUES ('A39', '经典牛仔靴', '经典牛仔靴，采用牛仔靴型鞋头，荷叶饰边靴筒。原装正版，舒适贴合，外形轻巧。花小钱即可拥有的耐用靴子。', 'A_四_14', 1, 1, 16.00, 99);
INSERT INTO `product` VALUES ('A40', '工人靴', '工人靴。帆布靴筒，耐磨的喙形鞋头。采用牛皮和棉布制成。结实厚重。质量有保证。', 'A_四_15', 1, 1, 14.00, 99);
INSERT INTO `product` VALUES ('A41', '法式礼服衬衫', '法式礼服衬衫。棉制面料，采用亚麻翼尖领和法式袖口设计。这款衬衫以帅气和极佳的穿着舒适感而闻名。', 'A_五_01', 1, 3, 10.75, 99);
INSERT INTO `product` VALUES ('A42', '日常衬衫外套', '镶边立领日常衬衫外套。前部为半排纽扣设计的棉制衬衫，带胸袋，采用镶边立领设计。这是最能体现人类纺织工艺最高水平的衬衫。', 'A_五_02', 1, 2, 8.75, 99);
INSERT INTO `product` VALUES ('A43', '立领衬衫外套', '立领衬衫外套。前部为半排纽扣设计的棉制衬衫，嵌入式前襟，采用立领设计。这款衬衫因为其让人打心底感到满足的新鲜感而广受好评。', 'A_五_03', 1, 4, 8.50, 99);
INSERT INTO `product` VALUES ('A44', '日常衬衫', '日常衬衫。棉质。配搭前胸纽扣和尖领。这款衬衫广受好评，因为人们对这种既讲究又舒适的东西渴望已久。', 'A_五_04', 1, 1, 6.75, 99);
INSERT INTO `product` VALUES ('A45', '带领衬衫外套', '镶边立领衬衫外套。棉质。前部为半排纽扣设计的礼服衬衫，嵌入式前襟，采用镶边立领设计。这是美国绝无仅有的精品服装之一。', 'A_五_05', 1, 2, 8.00, 99);
INSERT INTO `product` VALUES ('A46', '高筒礼帽', '带有缎带的高筒礼帽。这个风格最近正在各大城市流行。', 'A_六_01', 1, 3, 29.00, 99);
INSERT INTO `product` VALUES ('A47', '德比帽', '经典的德比帽。城里的绅士们都以戴上这顶帽子为荣。我们从未让客户失望过，所有差评都是假的。', 'A_六_02', 1, 3, 21.50, 99);
INSERT INTO `product` VALUES ('A48', '折褶帽', '男士羊毛折褶帽。我们很高兴能为您提供这顶帽子，并恳请您给好友推荐我们的目录。', 'A_六_03', 1, 2, 19.00, 99);
INSERT INTO `product` VALUES ('A49', '大山谷帽', '镶有扣带的宽边大山谷帽。这是牛仔最喜欢的一种宽边帽。', 'A_六_04', 1, 1, 16.00, 99);
INSERT INTO `product` VALUES ('A50', '大城市帽', '窄沿大城市帽。这款帽子说明了为什么有的人能用更少的钱买到比农民更好的帽子。', 'A_六_05', 1, 2, 15.25, 99);
INSERT INTO `product` VALUES ('A51', '亚瑟帽', '范德林德帮二把手亚瑟-摩根的同款帽子，你怎能不买？', 'A_六_06', 1, 2, 30.75, 99);
INSERT INTO `product` VALUES ('A52', '旧骑兵帽', '帽顶两侧内陷，镶有皮革带的斗牛士帽。适合在追牛扳倒和其他牛仔竞技活动上戴。', 'A_六_07', 1, 3, 9.50, 99);
INSERT INTO `product` VALUES ('B01', '强效神奇补剂', '克洛科特医生的多用途神奇补剂具有独特的治疗和恢复效果。没有别的产品能同时给您的生命、体力和专注力带来增益。', 'B_一_01', 2, 2, 7.00, 99);
INSERT INTO `product` VALUES ('B02', '强效蛇油', 'V·劳伦·克拉克蛇油药剂用纯响尾蛇油炼制。让您拥有冷酷的死神之眼和蛇一样致命的速度。', 'B_一_02', 2, 1, 4.50, 99);
INSERT INTO `product` VALUES ('B03', '强效苦药酒', '我们最畅销的恢复药来自克洛桑医生，它能立刻治愈疲劳，激发耐力。保证有效，人人必备！', 'B_一_03', 2, 4, 3.75, 99);
INSERT INTO `product` VALUES ('B04', '强效疗伤药', '真正的医学奇迹，治愈无法抑制的疼痛。厄尔·博丁的强效疗伤药能恢复 100% 的生命值，无效退款！', 'B_一_04', 2, 1, 4.75, 99);
INSERT INTO `product` VALUES ('B05', '神奇补剂', '认准克洛科特医生出品，您才能恢复生命值、体力值和注意力。最新科技，值得信赖，精心研制，药到病除，让您的脸蛋笑开花。效果无与伦比，无处可比。', 'B_一_05', 2, 2, 2.50, 99);
INSERT INTO `product` VALUES ('B06', '毛发生长剂', 'J·J·麦克卢尔的神奇补剂让您避免秃头的耻辱。它保证能刺激毛发的生长，并且让您的头发和胡子看起来健康又有光泽。', 'B_一_06', 2, 3, 9.75, 99);
INSERT INTO `product` VALUES ('B07', '蛇油', 'V·劳伦克·克拉克蛇油搽剂用纯响尾蛇油制作。保证货真价实。不要使用以蜥蜴或更小的爬行或两栖动物制作的廉价仿冒品。', 'B_一_07', 2, 1, 4.25, 99);
INSERT INTO `product` VALUES ('B08', '疗伤药', '厄尔·博丁大叔牌采用的神奇配方可以恢复健康和增进食欲。请勿使用仿冒品或家庭秘方。婴幼儿和体弱多病者皆宜。', 'B_一_08', 2, 1, 1.75, 99);
INSERT INTO `product` VALUES ('B09', '发油', '发油和润发胶。塑造完美发型，让您自信出席商务午宴、在她的窗外吟唱示爱，或者参加宗教仪式。', 'B_一_09', 2, 4, 3.25, 99);
INSERT INTO `product` VALUES ('B10', '马用复活剂', '贝蒂·苏和波比马用复苏剂对于严重丧失行动能力或受伤的马匹具有非凡的疗效。保证能让您百分百满意。', 'B_二_01', 2, 2, 10.50, 99);
INSERT INTO `product` VALUES ('B11', '强效马匹兴奋剂', ' 吉恩·A·弗罗斯特牌拥有各种新研发的强效马匹兴奋剂。这款强效兴奋剂保证让您的马匹拥有前所未有的活力，让您的骑行重新擦出火花。', 'B_二_02', 2, 3, 9.75, 99);
INSERT INTO `product` VALUES ('B12', '强效马匹药品', '贝蒂·苏和波比牌强效马用药品是效力最强且最有效的药品，能治疗所有马匹的问题和疾病，保证让您的马匹恢复活力。', 'B_二_03', 2, 2, 8.50, 99);
INSERT INTO `product` VALUES ('B13', '马匹兴奋剂', '吉恩·A·弗罗斯特牌马用兴奋剂，兽医办公室和家庭马厩必备。它恢复耐力的效果绝非家庭秘方可比。', 'B_二_04', 2, 3, 4.25, 99);
INSERT INTO `product` VALUES ('B14', '马用药品', '贝蒂·苏和波比的马用药品专用于缓解马匹的不适和疾病。保证能让您百分百满意。', 'B_二_05', 2, 2, 3.75, 99);
INSERT INTO `product` VALUES ('B15', '嚼烟', '快乐杰克牌嚼烟，切头型。品尝！咀嚼！吐出！给你全天的绅士级享受。一盒里有四份嚼烟。', 'B_三_01', 2, 1, 4.75, 99);
INSERT INTO `product` VALUES ('B16', '叶子口香糖', '马里亚纳·马德拉尼牌叶子口香糖刺激提神。治愈消化不良、冷漠和无聊。美味可口。孩子们爱它！每盒里有四块。', 'B_三_02', 2, 1, 5.00, 99);
INSERT INTO `product` VALUES ('C01', '鲑鱼罐头', '施密茨鲑鱼罐头。质量上乘。请务必时刻记得避免阳光暴晒。', 'C_一_01', 3, 3, 1.25, 99);
INSERT INTO `product` VALUES ('C02', '牛肉罐头', '埃德娜·麦克斯威尼品牌腌牛肉。没有其他肉能刚从罐头里拿出来就如此美味。随时食用且可无限期保存。非常适合补给品不足或瘟疫和饥荒的时候。', 'C_一_02', 3, 1, 1.75, 99);
INSERT INTO `product` VALUES ('C03', '芸豆罐头', '麻雀群牌营养四季豆。全部都经过精心挑选并在完美的卫生条件下烹饪。欧洲进口的名牌好豆。', 'C_一_03', 3, 1, 1.00, 99);
INSERT INTO `product` VALUES ('C04', '菠萝罐头', '大山谷菠萝。每一口都是阳光的味道。手工采摘并快速罐装。瓜玛出品。', 'C_一_04', 3, 2, 1.00, 99);
INSERT INTO `product` VALUES ('C05', '盐腌杂锦内脏', '烂醉莫莉牌盐腌杂锦内脏。精挑细选的器官肉和内脏腌制而成。非常适合搭配酱、甜面包、羊杂碎或猪肠食用。', 'C_一_05', 3, 4, 2.25, 99);
INSERT INTO `product` VALUES ('C06', '草莓罐头', '大山谷草莓。美味多汁，制作糖果和派的首选，或在公园里喂给一位小情人。', 'C_一_06', 3, 2, 1.00, 99);
INSERT INTO `product` VALUES ('C07', '烤豆子罐头', '施密茨牌番茄特调烤豆子。分量实在，富含营养，质量上乘。采用在世界展览会上得奖的豆子。', 'C_一_07', 3, 3, 1.75, 99);
INSERT INTO `product` VALUES ('C08', '桃子罐头', '大山谷桃。我们的桃园自 1832 年就开始出产顶级质量的多汁桃子，即便经历了多年的瘟疫和干旱。', 'C_一_08', 3, 1, 1.00, 99);
INSERT INTO `product` VALUES ('C09', '甜玉米罐头', '黑翼食品出品的甜玉米。罐装锁住天然的甜美滋味。试试看就知道了！', 'C_一_09', 3, 2, 1.00, 99);
INSERT INTO `product` VALUES ('C10', '豌豆罐头', '露莓溪牌豌豆。新鲜采摘包装。老少咸宜。今天就订购一箱吧。', 'C_一_10', 3, 2, 1.00, 99);
INSERT INTO `product` VALUES ('C11', '杏子罐头', '大山谷杏子。优选并采摘熟透了的优质水果并罐装在淡糖浆中，十分美味。', 'C_一_11', 3, 4, 1.00, 99);
INSERT INTO `product` VALUES ('C12', '嫩猪里脊', '当地的嫩猪肉，来自镇上最好的肉铺。', 'C_二_01', 3, 1, 3.75, 99);
INSERT INTO `product` VALUES ('C13', '成年鹿肉', '当地的成年鹿肉，来自镇上最好的肉铺。', 'C_二_02', 3, 3, 3.75, 99);
INSERT INTO `product` VALUES ('C14', '上等牛肉', '当地的上等牛肉，来自镇上最好的肉铺。', 'C_二_03', 3, 2, 3.75, 99);
INSERT INTO `product` VALUES ('C15', '切达干酪', '用最好的凝乳制作的本地奶酪。任何霉点都在售卖前及时切除。', 'C_二_04', 3, 1, 2.50, 99);
INSERT INTO `product` VALUES ('C16', '咸牛肉', '牛肉干，以传世古法制作。随时食用或存储以备应对粮食缺乏的情况。', 'C_二_05', 3, 4, 3.50, 99);
INSERT INTO `product` VALUES ('C17', '巴特利特梨', '巴特利特梨。可用于制作罐装水果或引诱马匹。', 'C_二_06', 3, 2, 1.75, 99);
INSERT INTO `product` VALUES ('C18', '新鲜桃子', '当地果园产的桃子，保证新鲜并不过软。制作水果派的首选。', 'C_二_07', 3, 3, 0.75, 99);
INSERT INTO `product` VALUES ('C19', '本地萝卜', '胡萝卜是炖菜、砂锅、蛋糕的热门配料，也是马的最爱。', 'C_二_08', 3, 1, 0.75, 99);
INSERT INTO `product` VALUES ('C20', '手工采摘的玉米', '新鲜采摘的玉米，没有溃烂或枯萎。', 'C_二_09', 3, 2, 0.75, 99);
INSERT INTO `product` VALUES ('C21', '果园出产的苹果', '来自当地果园。新鲜美味而且大多没有虫子。人畜皆宜的完美健康食品。', 'C_二_10', 3, 1, 0.75, 99);
INSERT INTO `product` VALUES ('C22', '传统燕麦饼', '埃丝特婶婶的传统燕麦饼用全麦燕麦制成，这些燕麦对您和马来说都是天然的美味，如果您愿意与它分享的话。一盒里有四块燕麦饼。', 'C_三_01', 3, 4, 1.50, 99);
INSERT INTO `product` VALUES ('C23', '斯诺伯格牌糖果', '斯诺博格传统糖果。老少皆宜，不论何时都能给大家带来欢乐。适用于作为勤劳的奖励。一包里有四根糖。', 'C_三_02', 3, 2, 1.00, 99);
INSERT INTO `product` VALUES ('C24', '什锦饼干', '赫德利烘焙公司什锦饼干是非常好的饼干。清脆、清淡、细腻的美味令人心情愉快。非常适合酒吧、花园宴会或民兵聚会。一盒里有四块饼干。', 'C_三_03', 3, 3, 1.25, 99);
INSERT INTO `product` VALUES ('C25', '巧克力棒', '斯诺博格精选牛奶巧克力。来自最好的可可种植园。令人心满意足的午餐后美食。', 'C_三_04', 3, 1, 0.75, 99);
INSERT INTO `product` VALUES ('C26', '黄油饼干', '勒布朗牌黄油饼干。采用优质原料新鲜烘焙。包装于坚固的铁罐中，铁罐还可用于存放您年轻时收藏的小玩意。一罐里有四块饼干。', 'C_三_05', 3, 2, 1.00, 99);
INSERT INTO `product` VALUES ('C27', '咖啡粉', '贵格与菲茨浓郁咖啡粉。非常适合那些经常感叹打仗时咖啡最好喝的水手或战士。', 'C_三_06', 3, 1, 2.25, 99);
INSERT INTO `product` VALUES ('C28', '圆面包', '烤面包。用代代相传的配方每日新鲜制作。', 'C_三_07', 3, 4, 0.75, 99);
INSERT INTO `product` VALUES ('D01', '12', '1', '1', 1, 1, 1.00, 1);
INSERT INTO `product` VALUES ('2', '1', '1', '1', 1, 1, 1.00, 1);
INSERT INTO `product` VALUES ('C31', '瓜玛朗姆酒', '辛科托雷斯牌瓜玛朗姆酒是纯甘蔗蒸馏的进口产品。没有什么比它更能带来快乐，一扫忧郁。', 'C_四_03', 3, 1, 9.75, 99);
INSERT INTO `product` VALUES ('C32', '草原月牌金酒', '拳拳草原月牌金酒。用杜松子和其他高级药用植物制成的高品质金酒。长夜漫漫，拳拳相伴。', 'C_四_04', 3, 3, 14.50, 99);

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `supplier_id` int(11) NOT NULL COMMENT '供应商ID（主键）',
  `supplier_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '供应商名称',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  PRIMARY KEY (`supplier_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '供应商表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES (1, '瓦伦丁', 'US', 'SanFrancisco');
INSERT INTO `supplier` VALUES (2, '草莓镇', 'China', 'DingNan');
INSERT INTO `supplier` VALUES (3, '圣丹尼斯', 'US', 'NewOrleans');
INSERT INTO `supplier` VALUES (4, '罗兹镇', 'UK', 'Edinburgh ');

-- ----------------------------
-- Table structure for warehouse_staff
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_staff`;
CREATE TABLE `warehouse_staff`  (
  `staff_id` int(11) NOT NULL COMMENT '员工ID（主键）',
  `staff_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '员工姓名',
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `hire_date` date NULL DEFAULT NULL COMMENT '入职日期',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '仓库管理人员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of warehouse_staff
-- ----------------------------
INSERT INTO `warehouse_staff` VALUES (1, 'ming', 'main', '183', '@outlook.com', '2024-06-19', '1');

SET FOREIGN_KEY_CHECKS = 1;
