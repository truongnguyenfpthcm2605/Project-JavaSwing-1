CREATE DATABASE FASTFOOD
go

USE FASTFOOD
GO
CREATE TABLE users
(
	USERS VARCHAR(20) PRIMARY KEY,
	PASS VARCHAR(20) NOT NULL,
	NAMES NVARCHAR(30),
	PHONE  CHAR(10),
	EMAIL VARCHAR(60) NOT NULL,
	ADDRESSS  NVARCHAR(100),
	IMAGES  VARCHAR(30),
	BRITH DATE,
	ROLES VARCHAR(20),
	GENDER BIT,
)
GO

CREATE TABLE USERSTORE(
	USERS VARCHAR(20) PRIMARY KEY REFERENCES users(USERS),
	NOTE NVARCHAR(50)
)
GO
CREATE TABLE STORE(
	IDSTORE INT IDENTITY(1,1) PRIMARY KEY,
	NAMES NVARCHAR(50),
	IDUSER VARCHAR(20) REFERENCES users(USERS)
)

GO
CREATE TABLE TYPEPRODUCT(
	TYPEPRODUCT INT IDENTITY(1,1) PRIMARY KEY,
	TYPENAMEPRODUCT NVARCHAR(50)
)

GO

CREATE TABLE PRODUCT(
	IDPRODUCT int identity(1,1) primary key,
	IDSTORE INT REFERENCES STORE(IDSTORE),
	TYPEPRODUCT INT REFERENCES TYPEPRODUCT(TYPEPRODUCT),
	NAMES NVARCHAR(50),
	PRICE MONEY,
	QUANTITY INT,
	IMAGES nvarchar(40),
	NOTE NVARCHAR(50),
	EXISTSS BIT DEFAULT 1
)

GO
CREATE TABLE CART(
	USERS VARCHAR(20) REFERENCES users(USERS),
	IDPRODUCT INT REFERENCES PRODUCT(IDPRODUCT),
	QUANTITY INT,
	PRIMARY KEY(USERS,IDPRODUCT)
)

GO
CREATE TABLE BILL(
	IDBILL INT IDENTITY(1,1) PRIMARY KEY,
	USERS VARCHAR(20) references users(USERS),
	IDPRODUCT INT references PRODUCT(IDPRODUCT),
	DATEPURCHASE DATE DEFAULT GETDATE(),
	QUANTITY INT,
	PRICE MONEY,

)


go
create table Tim(
	USERS VARCHAR(20) REFERENCES users(USERS),
	IDPRODUCT INT references PRODUCT(IDPRODUCT),
	LIKES BIT DEFAULT 1,
	PRIMARY KEY (USERS,IDPRODUCT)
)


go
create table CommentOne(
	IDComment int identity(1,1) primary key,
	USERS varchar(20) references USERS(USERS),
	IDProduct int references PRODUCT(IDPRODUCT),
	Content nvarchar(300),
	DateComment Date Default getDate(),
	viTri bit default 0,
	tacGia nvarchar(20)
)


go
create table LikeCommentOne(
	IDComment int references CommentOne(IDComment),
	USERS varchar(20) references USERS(USERS),
	likes int,
	primary key(IDComment,USERS)
)

go
create table CommentTwo(
	IDCommentTwo int identity(1,1) primary key,
	IDComment int references CommentOne(IDComment),
	USERS varchar(20) references USERS(USERS),
	Content varchar(300),
	DateComment Date Default getDate(),
	tacGia nvarchar(20)
)

go
create table LikeCommentTwo(
	IDCommentTwo int references CommentTwo(IDCommentTwo),
	USERS varchar(20) references USERS(USERS),
	likes int,
	primary key(IDCommentTwo,USERS)
)


go

drop table Moneys
create table Moneys
(
	USERS varchar(20) references USERS(USERS),
	moneys float default 0.0,
	primary key(USERS)
)
go
drop trigger if exists update_moneys
GO
create trigger update_moneys on users for insert 
as
begin
	insert into Moneys values((select users from inserted),0)
end
go
select * from userstore

drop trigger if exists update_Quantity_SP
GO
create trigger update_Quantity_SP on PRODUCT for UPDATE
AS
BEGIN
	IF EXISTS (SELECT * FROM inserted WHERE QUANTITY <= 0)
		BEGIN
			ROLLBACK TRANSACTION
		END
END
select * from Moneys








delete users 
update users set roles = 'store' where users= 'Nv3'
insert into users values('store','123',null,null,'truong@gmail.com',null,null,null,'store',null)
insert into STORE values (N'Hight land','store')
select moneys from Moneys where users = 'Nv1'
update Moneys set moneys = 103440 where users = 'Nv2'

insert into TYPEPRODUCT values(N'Cà Phê'),(N'Trà Sữa'),(N'Gà Gán'),(N'Bánh Mì Tươi')












