/*
-- Query: SELECT * FROM natwende.users where id = 1
LIMIT 0, 1000

-- Date: 2018-02-27 22:55
*/
INSERT INTO `Users` (`id`,`insertDate`,`status`,`uniqueId`,`updateDate`,`version`,`email`,`firstname`,`lastname`,`passwd`,`username`,`contactNumber`,`operator_id`) VALUES (1,current_timestamp(),'ACTIVE','1',current_timestamp(),0,'bkatapa@gmail.com','Admin','admin','123','admin','+27731972464',NULL);


/*
-- Query: SELECT * FROM natwende.userrolelink where user_id = 1
LIMIT 0, 1000

-- Date: 2018-02-27 22:55
*/
INSERT INTO `UserRoleLink` (`id`,`insertDate`,`status`,`uniqueId`,`updateDate`,`version`,`Roles_id`,`User_id`) VALUES (1,current_timestamp(),'ACTIVE','8349f2b3-6ee8-41b7-a1f3-519ea7a99c81',current_timestamp(),0,1,1);
