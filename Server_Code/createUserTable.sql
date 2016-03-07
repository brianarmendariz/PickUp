Use Pickup;


create Table Users(
   UserID int AUTO_INCREMENT PRIMARY KEY,
   Username varchar(40), 
   Password varchar(40),                
   FirstName varchar(40),
   LastName varchar(40),
   Birthday date,
   Gender varchar(10),
   UserRating int,
   picturePath varchar(100),
   
);