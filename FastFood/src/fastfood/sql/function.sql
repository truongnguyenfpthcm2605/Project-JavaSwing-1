USE FASTFOOD

go
drop function if exists luotLike
go
create function luotLike(@idproduct int) returns int
as
begin
	DECLARE @SL INT = (SELECT COUNT(*) 
						FROM TIM WHERE IDPRODUCT = @IDPRODUCT AND LIKES = 1
						GROUP BY IDPRODUCT)
	RETURN @SL
end
go




go
drop function if exists bill_SLBuy
go
create function bill_SLBuy (@IDPRODUCT int) returns int
as
begin
	DECLARE @SLMUA INT = (SELECT SUM(QUANTITY) FROM BILL WHERE IDPRODUCT = @IDPRODUCT GROUP BY IDPRODUCT)
	RETURN @SLMUA
end
go





go
drop function if exists userLike
go
create function userLike(@users varchar(20),@idproduct int) returns BIT
as
begin
	DECLARE @LIKES BIT = (select likes from tim where users = @users and idproduct = @idproduct)
	return @LIKES
end




go
drop function if exists userLikeCommentOne
go
create function userLikeCommentOne(@user varchar(20), @idCommentOne int) returns int
as
begin
	DECLARE @LIKES int = (select l.likes from LikeCommentOne l 
							where l.IDComment = @idCommentOne and l.USERS = @user)
	return @LIKES
end
go

	go
drop function if exists quantityLikeCommentOne
go
create function quantityLikeCommentOne(@idCommentOne int) returns int
as
begin
	DECLARE @SL INT = (SELECT COUNT(*) FROM LikeCommentOne L
						WHERE L.IDComment = @idCommentOne and L.likes = 1
						GROUP BY L.IDComment)
	RETURN @SL
end
go

go
drop function if exists quantityReportCommentOne
go
create function quantityReportCommentOne(@idCommentOne int) returns int
as
begin
	DECLARE @SL INT = (SELECT COUNT(*) FROM CommentTwo c
						where c.IDComment = @idCommentOne
						group by c.IDComment
						)
	RETURN @SL
end
go




go
drop function if exists userLikeCommentTwo
go
create function userLikeCommentTwo(@user varchar(20), @idCommentTwo int) returns int
as
begin
	DECLARE @LIKES int = (select l.likes from LikeCommentTwo l 
							where l.IDCommentTwo = @idCommentTwo and l.USERS = @user)
	return @LIKES
end
go

	go
drop function if exists quantityLikeCommentTwo
go
create function quantityLikeCommentTwo(@idCommentTwo int) returns int
as
begin
	DECLARE @SL INT = (SELECT COUNT(*) FROM LikeCommentTwo L
						WHERE L.IDCommentTwo = @idCommentTwo and L.likes = 1
						GROUP BY L.IDCommentTwo)
	RETURN @SL
end
go