## Disable foreign key checks
set foreign_key_checks = 0;

## Delete all data from all tables
truncate table address;
truncate table booking;
truncate table bookingType;
truncate table bulkbooking;
truncate table configattributes;
truncate table media;
truncate table notification;
truncate table parkingoperatorsettings;
truncate table parkingsite;
truncate table parkingsitearticle;
truncate table parkingsitevalidationprovider;
truncate table report;
truncate table report_parameters;
truncate table roles;
truncate table systemnotification;
truncate table tenant;
truncate table tenantparkingsitearticlelink;
truncate table tenantparkingsitevalidationproviderlink;
truncate table tenantsettings;
truncate table userpasswordreset;
truncate table users;
truncate table userrolelink;
truncate table usertenantlink;

# select concat('truncate table', table_name, ';')
# from information_schema.tables
# where table_schema = 'skidata_parking';

# SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') 
# FROM INFORMATION_SCHEMA.TABLES where  table_schema in ('skidata_parking');

#mysql -Nse 'show tables' DATABASE_NAME | while read table; do mysql -e "truncate table $table" DATABASE_NAME; done

## Enable foreign key checks
set foreign_key_checks = 1;

## Load initial user-roles
insert roles (id, insertDate, status, uniqueId, updateDate, version, roleType)
values (1, current_timestamp(), 'ACTIVE', '11', current_timestamp(), 0, 'SYSTEM_ADMINISTRATOR');

insert roles (id, insertDate, status, uniqueId, updateDate, version, roleType)
values (2, current_timestamp(), 'ACTIVE', 'a6476f53-4c0c-43b1-84f9-3300e5a4b7dc', current_timestamp(), 0, 'SKIDATA_ADMINISTRATOR');

insert roles (id, insertDate, status, uniqueId, updateDate, version, roleType)
values (3, current_timestamp(), 'ACTIVE', '6b48b49f-8c15-4a04-a9d4-e51ea8a8f733', current_timestamp(), 0, 'PARKING_OPERATOR');

insert roles (id, insertDate, status, uniqueId, updateDate, version, roleType)
values (4, current_timestamp(), 'ACTIVE', '09466c39-00c5-43f6-9177-8bc8f62f909f', current_timestamp(), 0, 'TENANT_ADMINISTRATOR');

insert roles (id, insertDate, status, uniqueId, updateDate, version, roleType)
values (5, current_timestamp(), 'ACTIVE', 'f132b061-fefe-4198-bda4-85d779a7cabb', current_timestamp(), 0, 'TENANT_USER');

## Load initial usernames/passwords
insert users (id, insertDate, status, uniqueId, updateDate, version, contactNumber, email, firstname, lastname, passwd, username, ParkingSite_id, tenant_id)
values (1, current_timestamp(), 'ACTIVE', '1', current_timestamp(), 0, '0000000000', 'sysadmin@domain.com', 'System', 'Administrator', 'P@ssw0rd', 'admin', null, null);

insert users (id, insertDate, status, uniqueId, updateDate, version, contactNumber, email, firstname, lastname, passwd, username, ParkingSite_id, tenant_id)
values (2, current_timestamp(), 'ACTIVE', '745063d4-126d-4573-b8b6-f3b98526bd51', current_timestamp(), 0, '0000000000', 'skidataadmin@domain.com', 'SkiData', 'Administrator', 'P@ssw0rd', 'skidata', null, null);

insert users (id, insertDate, status, uniqueId, updateDate, version, contactNumber, email, firstname, lastname, passwd, username, ParkingSite_id, tenant_id)
values (3, current_timestamp(), 'ACTIVE', 'e2805190-492e-4687-8d61-b89e62ecfc7b', current_timestamp(), 0, '0000000000', 'parkingoperator@domain.com', 'Parking', 'parking', 'P@ssw0rd', 'parkingoperator', null, null);

## Pair users with roles
insert userrolelink (id, insertDate, status, uniqueId, updateDate, version, Roles_id, User_id)
values (1, current_timestamp(), 
	'ACTIVE', 
    '8349f2b3-6ee8-41b7-a1f3-519ea7a99c81', 
    current_timestamp(), 
    0, 
    (select id from roles where roleType = 'SYSTEM_ADMINISTRATOR'), 
    (select id from users where username = 'admin'));
    
insert userrolelink (id, insertDate, status, uniqueId, updateDate, version, Roles_id, User_id)
values (2, current_timestamp(), 
	'ACTIVE', 
    '4bc679a4-d466-420c-b77b-74807d43b3a5', 
    current_timestamp(), 
    0, 
    (select id from roles where roleType = 'SKIDATA_ADMINISTRATOR'), 
    (select id from users where username = 'skidata'));
    
insert userrolelink (id, insertDate, status, uniqueId, updateDate, version, Roles_id, User_id)
values (3, current_timestamp(), 
	'ACTIVE', 
    '8994f485-62c1-490c-bf7a-1fd4654e27a2', 
    current_timestamp(), 
    0, 
    (select id from roles where roleType = 'PARKING_OPERATOR'), 
    (select id from users where username = 'parkingoperator'));

## Query all users and their assigned roles
select u.username, u.passwd, r.roleType from users u 
	join userrolelink url on u.id=url.user_id 
    join roles r on url.roles_id=r.id;