Use Pickup;
/*Drop Table Users;
*/
create Table Users(
   Username varchar(40) PRIMARY KEY, 
   Password varchar(40),                
   FirstName varchar(40),
   LastName varchar(40),
   Birthday date,
   Gender varchar(10),
   UserRating int,
   picturePath varchar(100)
   
);
