Use Pickup;
Drop Table Users;

create Table Users(
   Username varchar(40) PRIMARY KEY, 	/*Unique Email address of User */ 
   Password varchar(255),            	/*User's password - stored as hash */     
   FirstName varchar(40),				/*User's first name */ 
   LastName varchar(40),				/*User's last name */ 
   Birthday date,						/*User's birthday */ 
   Gender varchar(10),					/*User's Gender */ 
   UserRating int,						/*User's Rating - initialized to 0 */ 
   picturePath varchar(100)				/*Path to user's profile picture */ 
   
);
