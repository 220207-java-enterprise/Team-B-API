drop table if exists ers_reimbursements;
drop table if exists ers_users;
drop table if exists ers_user_roles;
drop table if exists ers_reimbursement_statuses;
drop table if exists ers_reimbursement_types;

create table ERS_User_Roles(
Role_Id varchar primary key,
role varchar unique
);

create table ERS_Users(
User_Id varchar primary key,
Username varchar unique not null,
Email varchar unique not null,
password varchar not null,
Given_Name varchar not null,
Surname varchar not null,
Is_Active boolean,
Role_Id varchar not null
);

alter table ers_users
add foreign key(role_Id)
references ERS_User_Roles (Role_Id);

create table ers_Reimbursement_Statuses(
Status_Id varchar primary key,
Status varchar unique
);
create table ers_Reimbursement_Types(
Type_Id varchar primary key,
Type varchar unique
);

create table ERS_Reimbursements(
Reimb_Id varchar primary key,
Amount numeric(6,2) not null,
Submitted timestamp not null,
Resolved timestamp,
Description varchar not null,
Receipt Bytea,
Payment_Id varchar,
Author_Id varchar not null,
Resolver_Id varchar,
Status_Id varchar not null,
Type_Id varchar not null
);

alter table ers_reimbursements
add foreign key(Status_Id)
references ers_Reimbursement_Statuses(Status_Id);
alter table ers_reimbursements
add foreign key(Type_Id)
references ers_Reimbursement_Types(Type_Id);
alter table ers_reimbursements
add foreign key(Author_Id)
references ers_users (User_Id);
alter table ers_reimbursements
add foreign key(Resolver_Id)
references ers_users (User_Id);

insert into ers_user_roles
values
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc96', 'ADMIN'),
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc97', 'FINANCE MANAGER'),
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc98', 'EMPLOYEE');

insert into ers_reimbursement_types
values
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc9a', 'LODGING'),
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc9b', 'TRAVEL'),
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc9c', 'FOOD'),
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc9d', 'OTHER');

insert into ers_reimbursement_statuses
values
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc9e', 'PENDING'),
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc9f', 'APPROVED'),
	('7c3521f5-ff75-4e8a-9913-01d15ee4dc9g', 'DENIED');