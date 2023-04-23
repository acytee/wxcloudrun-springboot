CREATE TABLE `pt_user` (
  `userId` varchar(32) NOT NULL COMMENT '用户ID',
  `unionId` varchar(32) NOT NULL COMMENT '微信unionId',
  `openId` varchar(32) NOT NULL COMMENT '微信openId',
  `nickName` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatarUrl` varchar(100) NOT NULL COMMENT '头像',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `createTime` datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
  PRIMARY KEY (`userId`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '用户信息';