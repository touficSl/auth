DROP SCHEMA IF EXISTS auth;
CREATE SCHEMA auth;
USE auth;

DROP TABLE IF EXISTS `menu_authorization`;
CREATE TABLE `menu_authorization` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `menu_auth_id` VARCHAR(255) NOT NULL,
  `api` varchar(700) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isget` BIT(1) NULL DEFAULT 0,
  `ispost` BIT(1) NULL DEFAULT 0,
  `isupdate` BIT(1) NULL DEFAULT 0,
  `isdelete` BIT(1) NULL DEFAULT 0,
  `isconfiguration` BIT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Menu authorization';


INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('global', '/auth/api/user/menu/list', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('global', '/auth/api/user/authorization', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('global', '/auth/api/user/details', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`, `isupdate`) VALUES ('profile', '/auth/api/user/update', b'1', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('profile', '/auth/api/user/verify/change/email', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('profile', '/auth/api/user/change/email', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('profile', '/auth/api/user/verify/change/mobile', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`, `isupdate`) VALUES ('profile', '/auth/api/user/change/mobile', b'1', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isconfiguration`) VALUES ('profile', '/auth/api/user/change/password', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('manageusers', '/auth/api/admin/user/list', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('manageusers', '/auth/api/admin/user/list/count', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`, `isupdate`) VALUES ('manageusers', '/auth/api/admin/user/update', b'1', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('manageusers', '/auth/api/admin/role/list', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('manageusers', '/auth/api/admin/user/details', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isdelete`) VALUES ('manageusers', '/auth/api/admin/user/able', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `ispost`) VALUES ('manageusers', '/auth/api/admin/user/create', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('manageroles', '/auth/api/admin/role/menu/list', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `ispost`, `isupdate`) VALUES ('manageroles', '/auth/api/admin/role/menu/save', b'1', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('manageroles', '/auth/api/admin/auth/type/list', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('manageroles', '/auth/api/admin/menu/list', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isdelete`) VALUES ('manageroles', '/auth/api/admin/role/menu/remove', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('manageroles', '/auth/api/admin/settings/details', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isupdate`) VALUES ('manageroles', '/auth/api/admin/settings/update', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('managetokens', '/auth/api/admin/token/list', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isdelete`) VALUES ('managetokens', '/auth/api/admin/token/force/revoke', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isget`) VALUES ('tokens', '/auth/api/user/token/list', b'1');
INSERT INTO `menu_authorization` (`menu_auth_id`, `api`, `isdelete`) VALUES ('tokens', '/auth/api/user/token/force/revoke', b'1');



DROP TABLE IF EXISTS `authorization`;
CREATE TABLE `authorization` (
  `user_role` varchar(20) NOT NULL,
  `api` varchar(700) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enable` BIT(1) NULL DEFAULT 1,
  PRIMARY KEY (`user_role`, `api`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='User authorization';


INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/menu/list');
INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/menu/list');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/authorization');
INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/authorization');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/details');
INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/details');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/update');
INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/update');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/verify/change/email');
INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/verify/change/email');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/change/email');
INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/change/email');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/verify/change/mobile');
INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/verify/change/mobile');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/change/mobile');
INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/change/mobile');

-- USER

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/token/list');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('User', '/auth/api/user/token/force/revoke');

-- ADMIN

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/user/change/password');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/user/list');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/user/list/count');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/user/update');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/role/list');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/user/details');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/role/menu/list');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/role/menu/save');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/auth/type/list');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/menu/list');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/role/menu/remove');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/user/able');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/user/create');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/token/list');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/token/force/revoke');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/settings/details');

INSERT INTO `authorization` (`user_role`, `api`) VALUES ('Admin', '/auth/api/admin/settings/update');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `username` VARCHAR(250) COLLATE utf8mb4_general_ci NOT NULL,
  `password` TEXT COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_role` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mobile_no` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `hire_date` date DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `first_name_ar` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `last_name_ar` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `otp` VARCHAR(10) COLLATE utf8mb4_general_ci,
  `changepasswordcode` VARCHAR(10) COLLATE utf8mb4_general_ci,
  `changeemailcode` VARCHAR(600) COLLATE utf8mb4_general_ci,
  `changemobilecode` VARCHAR(600) COLLATE utf8mb4_general_ci,
  `enable` BIT(1) NULL DEFAULT 1,
  `locked` BIT(1) NULL DEFAULT 0,
  `invalidattempts` INT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Registered Users with LDAP connection and GRC users';

ALTER TABLE `users` 
ADD COLUMN `bypass3rdpartyauth` BIT(1) NULL DEFAULT 0 AFTER `invalidattempts`;

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `user_role` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `auth_type` VARCHAR(20) DEFAULT NULL,
  `require_2fa` BIT(1) NULL DEFAULT 1,
  PRIMARY KEY (`user_role`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Registered Users with LDAP connection and GRC users';

INSERT INTO `roles` (`user_role`, `auth_type`, `require_2fa`) VALUES ('Admin', 'PASS', b'0');
INSERT INTO `roles` (`user_role`, `auth_type`, `require_2fa`) VALUES ('User', 'PASS', b'0');

-- _PASSWORDS_
-- $2a$10$8dwMI0sQnOnnro/xk8WrpOYfNGMBtOuv/1.ELoNZ38ypyhypQ4b1S = Abc123
-- $2a$10$mXW7KnbzdbNSH4fe/Ym0qOQWQACT/iaMfOjj0IfwkZ44BoncOJliS = e6pQ)aeQroCEs
INSERT INTO `users` (`first_name`, `username`, `user_role`, `email`, `mobile_no`, `password`) VALUES ('tsadmin', 'admin', 'Admin', 'touficsleiman.uae@gmail.com', '0566114217', '$2a$10$mXW7KnbzdbNSH4fe/Ym0qOQWQACT/iaMfOjj0IfwkZ44BoncOJliS');
INSERT INTO `users` (`first_name`, `username`, `user_role`, `email`, `mobile_no`, `password`) VALUES ('tsuser', 'user', 'User', 'touficsleyman@gmail.com', '0566114217', '$2a$10$mXW7KnbzdbNSH4fe/Ym0qOQWQACT/iaMfOjj0IfwkZ44BoncOJliS');

DROP TABLE IF EXISTS `tokens`;
CREATE TABLE `tokens` (
  `id` VARCHAR(500) NOT NULL,
  `username` VARCHAR(250) NULL,
  `accesstoken` LONGTEXT NULL,
  `refreshtoken` LONGTEXT NULL,
  `refreshkey` TEXT NULL,
  `device` TEXT NULL,
  `ip` VARCHAR(100) NULL,
  `access_expiry_date` TIMESTAMP NULL,
  `refresh_expiry_date` TIMESTAMP NULL,
  `revoked_date_time` TIMESTAMP NULL,
  `reason` VARCHAR(100) NULL,
  `date_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Registered Users Tokens';

  
  

-- EVENTS - SCHEDULERS
CREATE EVENT `REMOVE_REVOKED_TOKENS` ON SCHEDULE EVERY 12 HOUR STARTS CURRENT_TIMESTAMP ON COMPLETION NOT PRESERVE ENABLE DO 
DELETE FROM tokens 
WHERE CURDATE() > DATE_ADD(refresh_expiry_date, INTERVAL 5 DAY);

SHOW EVENTS FROM auth;

-- SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `lang` varchar(20) COLLATE utf8mb4_general_ci DEFAULT 'en',
  `name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `href` text COLLATE utf8mb4_general_ci,
  `onclick` text COLLATE utf8mb4_general_ci,
  `icon` text COLLATE utf8mb4_general_ci,
  `order` int DEFAULT '0',
  `isget` bit(1) DEFAULT b'0',
  `ispost` bit(1) DEFAULT b'0',
  `isupdate` bit(1) DEFAULT b'0',
  `isdelete` bit(1) DEFAULT b'0',
  `isconfiguration` bit(1) DEFAULT b'0',
  `parent_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `auth_id` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `show` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='I added get, post, update, delete, and configure in case a page has these actions (if true), admin can choose to allow users menu action for the selected menu';
  
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `order`, `auth_id`) VALUES ('indexen', 'en', 'Dashboard', 'index', 'icon-speedometer', 10, 'index');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `order`, `auth_id`) VALUES ('indexar', 'ar', 'الصفحة الرئيسية', 'index', 'icon-speedometer', 20, 'index');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `order`, `auth_id`) VALUES ('settingsen', 'en', 'Settings', NULL, 'icon-settings', 30, 'settings');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `order`, `auth_id`) VALUES ('settingsar', 'ar', 'إعدادات', NULL, 'icon-settings', 40, 'settings');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('translateen', 'en', 'Translate', NULL, 'fa fa-language', 'settingsen', 50, 'translate');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('translatear', 'ar', 'ترجم', NULL, 'fa fa-language', 'settingsar', 60, 'translate');
INSERT INTO `menu` (`id`, `lang`, `name`, `onclick`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('english', 'ar', 'إنجليزي', 'changeLanguageSwitch(\'en\')', 'fa fa-language', 'translatear', 70, 'enar');
INSERT INTO `menu` (`id`, `lang`, `name`, `onclick`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('arabic', 'en', 'Arabic', 'changeLanguageSwitch(\'ar\')', 'fa fa-language', 'translateen', 80, 'enar');

INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('styleen', 'en', 'Style', NULL, 'fa fa-eyedropper', 'settingsen', 90, 'style');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('stylear', 'ar', 'شكل', NULL, 'fa fa-eyedropper', 'settingsar', 100, 'style');
INSERT INTO `menu` (`id`, `lang`, `name`, `onclick`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('darken', 'en', 'Dark', 'changeStyleSwitch(\'dark\')', 'fa fa-square', 'styleen', 110, 'dark');
INSERT INTO `menu` (`id`, `lang`, `name`, `onclick`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('whiteen', 'en', 'White', 'changeStyleSwitch(\'white\')', 'fa fa-square-o', 'styleen', 120, 'white');
INSERT INTO `menu` (`id`, `lang`, `name`, `onclick`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('darkar', 'ar', 'عتمة', 'changeStyleSwitch(\'dark\')', 'fa fa-square', 'stylear', 130, 'dark');
INSERT INTO `menu` (`id`, `lang`, `name`, `onclick`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('whitear', 'ar', 'أبيض', 'changeStyleSwitch(\'white\')', 'fa fa-square-o', 'stylear', 140, 'white');

INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('profileen', 'en', 'Profile', 'profile', 'fa fa-user', 'settingsen', 150, 'profile');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('profilear', 'ar', 'الملف الشخصي', 'profile', 'fa fa-user', 'settingsar', 160, 'profile');

INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('logouten', 'en', 'Logout', 'logout', 'fa fa-sign-out', 'settingsen', 170, 'logout');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('logoutar', 'ar', 'تسجيل خروج', 'logout', 'fa fa-sign-out', 'settingsar', 180, 'logout');

INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `order`, `auth_id`) VALUES ('manageen', 'en', 'Manage', NULL, 'fa fa-briefcase', 190, 'manage');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `order`, `auth_id`) VALUES ('managear', 'ar', 'إدارة', NULL, 'fa fa-briefcase', 200, 'manage');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('manageusersen', 'en', 'Users', 'manageusers', 'fa fa-users', 'manageen', 210, 'manageusers');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('manageusersar', 'ar', 'المستخدمين', 'manageusers', 'fa fa-users', 'managear', 220, 'manageusers');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('managerolesen', 'en', 'Roles', 'manageroles', 'fa fa-cogs', 'manageen', 230, 'manageroles');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('managerolesar', 'ar', 'الأدوار', 'manageroles', 'fa fa-cogs', 'managear', 240, 'manageroles');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('managetokensen', 'en', 'User Sessions', 'managetokens', 'fa fa-wrench', 'manageen', 250, 'managetokens');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('managetokensar', 'ar', 'جلسات المستخدمين', 'managetokens', 'fa fa-wrench', 'managear', 260, 'managetokens');

UPDATE `menu` SET `isget` = b'1', `isconfiguration` = b'1' WHERE (`id` = 'indexar');
UPDATE `menu` SET `isget` = b'1', `isconfiguration` = b'1' WHERE (`id` = 'indexen');
UPDATE `menu` SET `isget` = b'1', `ispost` = b'1', `isupdate` = b'1', `isconfiguration` = b'1' WHERE (`id` = 'profilear');
UPDATE `menu` SET `isget` = b'1', `ispost` = b'1', `isupdate` = b'1', `isconfiguration` = b'1' WHERE (`id` = 'profileen');
UPDATE `menu` SET `isget` = b'1', `ispost` = b'1', `isupdate` = b'1', `isdelete` = b'1' WHERE (`id` = 'manageusersar');
UPDATE `menu` SET `isget` = b'1', `ispost` = b'1', `isupdate` = b'1', `isdelete` = b'1' WHERE (`id` = 'manageusersen');
UPDATE `menu` SET `isget` = b'1', `ispost` = b'1', `isupdate` = b'1', `isdelete` = b'1' WHERE (`id` = 'managerolesar');
UPDATE `menu` SET `isget` = b'1', `ispost` = b'1', `isupdate` = b'1', `isdelete` = b'1' WHERE (`id` = 'managerolesen');
UPDATE `menu` SET `isget` = b'1', `ispost` = b'1', `isupdate` = b'1', `isdelete` = b'1' WHERE (`id` = 'managetokensar');
UPDATE `menu` SET `isget` = b'1', `ispost` = b'1', `isupdate` = b'1', `isdelete` = b'1' WHERE (`id` = 'managetokensen');

INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('tokensen', 'en', 'Sessions', 'tokens', 'fa fa-wrench', 'settingsen', 250, 'tokens');
INSERT INTO `menu` (`id`, `lang`, `name`, `href`, `icon`, `parent_id`, `order`, `auth_id`) VALUES ('tokensar', 'ar', 'الجلسات', 'tokens', 'fa fa-wrench', 'settingsar', 260, 'tokens');


CREATE TABLE `menu_role` (
  `menu_id` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `user_role` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `isget` bit(1) DEFAULT b'0',
  `ispost` bit(1) DEFAULT b'0',
  `isupdate` bit(1) DEFAULT b'0',
  `isdelete` bit(1) DEFAULT b'0',
  `isconfiguration` bit(1) DEFAULT b'0',
  PRIMARY KEY (`menu_id`,`user_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='User Roles Menu, I added get, post, update delete configuration to allow specific role to specific action';
  
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('indexen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('indexen', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('indexar', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('indexar', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('settingsen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('settingsen', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('settingsar', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('settingsar', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('translateen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('translateen', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('translatear', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('translatear', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('english', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('english', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('arabic', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('arabic', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('styleen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('styleen', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('stylear', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('stylear', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('darken', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('darken', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('whiteen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('whiteen', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('darkar', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('darkar', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('whitear', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('whitear', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('profileen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('profileen', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('profilear', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('profilear', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('logouten', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('logouten', 'User');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('logoutar', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('logoutar', 'User');

-- Admin
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('manageen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('managear', 'Admin');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('manageusersen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('manageusersar', 'Admin');


INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('managerolesen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('managerolesar', 'Admin');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('managetokensen', 'Admin');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('managetokensar', 'Admin');

INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('tokensen', 'User');
INSERT INTO `menu_role` (`menu_id`, `user_role`) VALUES ('tokensar', 'User');


DROP TABLE IF EXISTS `system_logs`;
CREATE TABLE `system_logs` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `event` text COLLATE utf8mb4_general_ci NOT NULL,
  `status` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ref_link_id` text COLLATE utf8mb4_general_ci,
  `type` text COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='System logs without logins';


DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ldapregisterrole` bit(1) DEFAULT b'0',
  `ldapdefaultrole` text,
  `ldapdomain` text,
  `ldapurl` text,
  `ldapbasedn` text,
  `apikey` text,
  `apisecret` text,
  `adminkey` text,
  `jwtsecret` TEXT NULL,
  `jwtexpirationms` bigint DEFAULT '86400000',
  `jwtexpirationmsshort` bigint DEFAULT '7200000',
  `jwtexpirationmscode` bigint DEFAULT '900000',
  `uaepassregisterrole` bit(1) DEFAULT b'0',
  `uaepassdefaultrole` text,
  `uaepassusername` text,
  `uaepasspassword` text,
  `uaepassendpoint` text,
  `uaepasscallbackurl` text,
  `uaepassuserinfourl` text,
  `uaepassstate` text,
  `uaepassredirecturl` text,
  `uaepassauthurl` text,
  `uaepassuseeid` bit(1) DEFAULT b'0' COMMENT 'use eid as username\nelse use uuid as username',
  `passregisterrole` bit(1) DEFAULT b'0',
  `passdefaultrole` text,
  `maximuminvalidattempts` int DEFAULT '5',
  `recaptchavalidation` bit(1) DEFAULT b'0',
  `recaptchasitekey` text,
  `recaptchaapi` text,
  `smsauthapi` text,
  `smsauthusername` text,
  `smsauthpassword` text,
  `smsapi` text,
  `smsapplicationid` text,
  `smspassword` text,
  `mailhost` text,
  `mailport` int DEFAULT '25',
  `mailusername` text,
  `mailpassword` text,
  `mailstarttlsenable` bit(1) DEFAULT b'1',
  `mailfrom` text,
  `mailauth` bit(1) DEFAULT b'1',
  `mailsupport` text,
  `isdefault` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='This table contains all project settings';

INSERT INTO `settings` (`ldapregisterrole`, `ldapdefaultrole`, `ldapdomain`, `ldapurl`, `ldapbasedn`, `apikey`, `apisecret`, `adminkey`, `jwtsecret`, `jwtexpirationms`, `jwtexpirationmsshort`, `jwtexpirationmscode`, `uaepassregisterrole`, `uaepassdefaultrole`, `uaepassusername`, `uaepasspassword`, `uaepassendpoint`, `uaepasscallbackurl`, `uaepassuserinfourl`, `uaepassstate`, `uaepassredirecturl`, `uaepassauthurl`, `uaepassuseeid`, `passregisterrole`, `passdefaultrole`, `maximuminvalidattempts`, `recaptchavalidation`, `recaptchasitekey`, `recaptchaapi`, `smsauthapi`, `smsauthusername`, `smsauthpassword`, `smsapi`, `smsapplicationid`, `smspassword`, `mailhost`, `mailport`, `mailusername`, `mailpassword`, `mailstarttlsenable`, `mailfrom`, `mailauth`, `mailsupport`, `isdefault`) 
VALUES (b'0', 'User', '@.shj.ae', '', '', 'hde36b429-f747-44l7-a841-e6djf4fce2dh', 'UAyFQThz0RdPzHCpShthfVB3zcoskeeo920mEbrwieyRiO1jqPAOhO4oM', 'wsTk5JDXd74XLGUrJ1', '======================AUTH=Spring===========================', '86400000', '7200000', '900000', b'0', 'UAEInspector', 'sandbox_stage', 'sandbox_stage', 'https://stg-id.uaepass.ae/', 'idshub/token?grant_type=authorization_code&redirect_uri=#CALLBACKURL#&code=#CODE#', 'idshub/userinfo', 'testing', 'http://localhost/login#USERNAME#', 'idshub/authorize?response_type=code&client_id=#CLIENTID#&scope=urn:uae:digitalid:profile:general&state=#STATE#&redirect_uri=#REDIRECTURL#&acr_values=urn:safelayer:tws:policies:authentication:level:low', b'0', b'0', 'GRCInspector', '5', b'0', '6LeHQ00qAAAAAFcHhSxKSNd3RU3xDnYlPFygLsNw', 'https://recaptchaenterprise.googleapis.com/v1/projects/inspection-412005/assessments?key=AIzaSyD7Da8bHTmTFScXJDYdvxklV9aD_ySlqJ4', '', '', '', '', '', '', '', '25', '', 'P@ssw0rd', b'1', '', b'1', 'touficsleiman.uae@gmail.com', b'1');

-- UPDATE `settings` SET `ldapregisterrole` = b'1', `uaepassregisterrole` = b'1' WHERE (`id` = '1');


-- -- DEV without /auth -- 

-- use auth;
-- set sql_safe_updates = 0;
-- UPDATE authorization
-- SET api = REPLACE(api, '/auth/api/', '/api/');

-- UPDATE menu_authorization
-- SET api = REPLACE(api, '/auth/api/', '/api/');


ALTER TABLE `menu` 
CHANGE COLUMN `opendropdownlist` `opendropdownlist` BIT(1) NOT NULL DEFAULT 0 ,
CHANGE COLUMN `showdropdownlist` `showdropdownlist` BIT(1) NOT NULL DEFAULT 0 ;


