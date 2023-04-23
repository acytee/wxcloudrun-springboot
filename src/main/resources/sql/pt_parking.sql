CREATE TABLE `pt_parking` (
  `parkingId` varchar(32) NOT NULL COMMENT '车位ID',
  `type` int NOT NULL COMMENT '车位类型（1可租，2想租）',
  `status` int NOT NULL COMMENT '状态：1上架，0下架',
  `color` int NOT NULL COMMENT '颜色：1绿色可租，2黄色有人租，3蓝色想租，4红色异常',
  `browsers` int NOT NULL COMMENT '浏览人数',
  `rent` int NOT NULL COMMENT '租金（元）',
  `address` varchar(100) NOT NULL COMMENT '地址',
  `longitude` float NOT NULL COMMENT '经度',
  `latitude` float NOT NULL COMMENT '纬度',
  `week` varchar(20) COMMENT '星期（[1,2,3,4,5,6,7]）',
  `st` varchar(5) COMMENT '开始时间（08:00）',
  `et` varchar(5) COMMENT '结束时间（20:00）',
  `createUserId` varchar(32) NOT NULL COMMENT '创建人',
  `createTime` datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`parkingId`),
  CONSTRAINT `pt_parking_fk_1` FOREIGN KEY (`createUserId`) REFERENCES `pt_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '车位信息';