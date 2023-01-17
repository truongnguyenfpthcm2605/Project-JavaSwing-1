﻿USE FASTFOOD

go
drop proc if exists viewConfirm
go
create proc viewConfirm
as
begin
	select 
		u.USERS users,
		users.IMAGES avt,
		users.NAMES names,
		u.NOTE note,
		users.ROLES roles
	from USERSTORE u inner join users on u.USERS = users.USERS
end


go
drop proc if exists confirm
go
create proc confirm @user varchar(20), @yesNo int
as
begin
	delete from USERSTORE where USERS = @user
	if @yesNo = 1
		begin
			update users
			set ROLES = 'store' where USERS = @user
			insert into STORE (NAMES,IDUSER) select USERS,USERS from users where USERS=@user
		end
end


go
drop proc if exists insert_Bill
go
create proc insert_Bill @user varchar(20), @IDPRODUCT INT, @QUANTITY INT, @PRICE MONEY
AS
BEGIN
	DECLARE @SL INT = (SELECT QUANTITY FROM PRODUCT where IDPRODUCT = @IDPRODUCT)
	SET XACT_ABORT ON
	BEGIN TRAN
	BEGIN TRY
		UPDATE PRODUCT SET QUANTITY = QUANTITY - @QUANTITY WHERE IDPRODUCT = @IDPRODUCT
		insert into bill (USERS,IDPRODUCT,QUANTITY,PRICE) values (@user,@IDPRODUCT,@QUANTITY,@PRICE)
		SELECT 1 DungSai,@SL SL
	--LỆNH 2
	--..
	COMMIT
	END TRY
	BEGIN CATCH
		SELECT 0 DungSai,@SL SL
		ROLLBACK
	END CATCH
END


go
drop proc if exists bill_store
go
create proc bill_store @userStore varchar(20), @monthYear varchar(20)
as
begin
	select b.IDBILL,b.USERS,b.IDPRODUCT,b.DATEPURCHASE,b.QUANTITY,b.PRICE,p.IMAGES,p.NAMES
	from bill b inner join product p on b.idproduct = p.idproduct 
			inner join store s on s.idstore = p.idstore
	WHERE DATEPURCHASE LIKE '%'+@monthYear+'%' and s.IDUSER = @userStore
	ORDER BY DATEPURCHASE DESC
end


drop proc if exists bill_year_month
go
create proc bill_year_month @userStore varchar(20)
as
begin
	select distinct (CAST(YEAR(b.DATEPURCHASE) as varchar) + '-'
				 +IIF(
					LEN(CAST(MONTH(b.DATEPURCHASE) as varchar)) = 1,
						'0'+CAST(MONTH(b.DATEPURCHASE) as varchar),
						CAST(MONTH(b.DATEPURCHASE) as varchar)
					)
				) AS THANG
	from BILL b inner join PRODUCT p on b.IDPRODUCT = p.IDPRODUCT
			inner join STORE s on s.IDSTORE = p.IDSTORE
	where s.IDUSER = @userStore
	ORDER BY THANG DESC
end



drop proc if exists product_Name
go
create proc product_Name @names nvarchar(100), @user varchar(20)
as
begin
	SELECT p.* ,DBO.luotLike(P.IDPRODUCT),
	iif(DBO.userLike(@user,p.IDPRODUCT) is null ,0, DBO.userLike(@user,p.IDPRODUCT)) likes
	FROM PRODUCT P 
	where EXISTSS = 1 and NAMES like  '%'+@names+'%'
end
go

go
drop proc if exists product_TypeProduct
go
create proc product_TypeProduct @typeProduct int, @user varchar(20)
as
begin
	SELECT p.* ,DBO.luotLike(IDPRODUCT),
	iif(DBO.userLike(@user,p.IDPRODUCT) is null ,0, DBO.userLike(@user,p.IDPRODUCT)) likes
	FROM PRODUCT P
	where EXISTSS = 1 and P.TYPEPRODUCT = @typeProduct
end
go


go
drop proc if exists userViewStore
go
create proc userViewStore @idStore int, @user varchar(20)
as
begin
	SELECT p.* ,DBO.luotLike(IDPRODUCT),
	iif(DBO.userLike(@user,p.IDPRODUCT) is null ,0, DBO.userLike(@user,p.IDPRODUCT)) likes
	FROM PRODUCT P
	where EXISTSS = 1 and P.IDSTORE = @idStore
end
go



go
drop proc if exists triAn
go
create proc triAn @user varchar(20)
as
begin
	select distinct u.EMAIL email
	from Bill b join product p on b.idproduct = p.idproduct
				join store s on s.idstore = p.idstore
				join users u on u.USERS = b.USERS
	where s.IDUSER = @user
end
go


go
drop proc if exists tk_Bill
go
create proc tk_Bill @user varchar(20), @monthYear varchar(30)
as
begin
	select b.IDPRODUCT,sum(b.QUANTITY) Sl,SUM(b.QUANTITY*b.PRICE) price,p.IMAGES,p.NAMES
	from bill b inner join product p on b.idproduct = p.idproduct 
			inner join store s on s.idstore = p.idstore
	WHERE DATEPURCHASE LIKE '%'+@monthYear+'%' and s.IDUSER = @user
	group by b.IDPRODUCT,p.IMAGES,p.NAMES,s.IDUSER
end

go
drop proc if exists proc_user_like
go
create proc proc_user_like @users varchar(20), @idproduct int, @likes bit
as
begin
	begin try
		INSERT INTO Tim (USERS,IDPRODUCT) values (@users,@idproduct)
	end try
	begin catch
		update tim
		set LIKES = @likes where USERS = @users and IDPRODUCT = @idproduct
	end catch

end

go
drop proc if exists isTacGia
go
create proc isTacGia @idproduct int, @idUser varchar(20)
as
begin
	if exists (select *  from PRODUCT p join STORE s on p.IDSTORE = s.IDSTORE 
				where IDPRODUCT = @idproduct and s.IDUSER = @idUser)
			select N'Tác giả'
	else
			select ''
end
go

go
drop proc if exists selectCommentOne
go
create proc selectCommentOne @user varchar(20), @idProduct int
as
begin
	select c.*,u.IMAGES,
		IIF(dbo.userLikeCommentOne(@user,c.IDComment) is null, 0, dbo.userLikeCommentOne(@user,c.IDComment)) my,
		dbo.quantityLikeCommentOne(c.IDComment) slLike,
		dbo.quantityReportCommentOne(c.IDComment) slComment
	from CommentOne c join users u on c.USERS = u.USERS
	where c.IDProduct = @idProduct
	order by c.viTri desc,c.IDComment desc
end
go




go
drop proc if exists proc_UserLikeCommentOne
go
create proc proc_UserLikeCommentOne @users varchar(20), @idcommentone int, @likes int
as
begin
	begin try
		INSERT INTO LikeCommentOne (IDComment,USERS,likes) values ( @idcommentone,@users,@likes)
	end try
	begin catch
		update LikeCommentOne
		set likes = @likes where USERS = @users and IDComment = @idcommentone
	end catch

end






go
drop proc if exists selectCommentTwo
go
create proc selectCommentTwo @user varchar(20), @idCommentOne int
as
begin
	select c.*,u.IMAGES,
		dbo.userLikeCommentTwo(@user,c.IDCommentTwo) my,
		dbo.quantityLikeCommentTwo(c.IDCommentTwo) slLike
	from CommentTwo c join users u on c.USERS = u.USERS
	where c.IDComment = @idCommentOne
	order by c.IDCommentTwo desc
end
go




go
drop proc if exists proc_UserLikeCommentTwo
go
create proc proc_UserLikeCommentTwo @users varchar(20), @idcommentTwo int, @likes int
as
begin
	begin try
		INSERT INTO LikeCommentTwo(IDCommentTwo,USERS,likes) values ( @idcommentTwo,@users,@likes)
	end try
	begin catch
		update LikeCommentTwo
		set likes = @likes where USERS = @users and IDCommentTwo = @idcommentTwo
	end catch

end

select * from LikeCommentTwo
