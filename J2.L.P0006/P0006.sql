create database ProductManagement

goto

create table TblUsers 
(
	userID varchar(10) primary key not null,
	fullName nvarchar(50) not null,
	password varchar(50) not null,
	status bit not null
);

goto

create table TblCategories
(
	categoryID varchar(10) primary key not null,
	categoryName nvarchar(50) not null,
	description nvarchar(200) not null
);

goto

create table TblProducts
(
	productID varchar(10) primary key not null,
	productName nvarchar(50) not null,
	unit varchar(50) not null,
	price float not null,
	quantity int not null,
	categoryID varchar(10) not null,

	constraint fk_pro_ca
	foreign key (categoryID)
	references TblCategories(categoryID)
);