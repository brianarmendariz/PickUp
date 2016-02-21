 drop Database Pickup; 
Create Database Pickup;
Use Pickup;


create Table PickupEvents(
   EventID int AUTO_INCREMENT PRIMARY KEY,
   Author varchar(40), 
   EventName varchar(40),                
   Sport varchar(40),
   Location varchar(80),
   Latitude double(20,16),
   Longitude double(20,16),
   DateTimeCreated datetime,
   EventDateTime datetime,
   AgeMax int,
   AgeMin int,
   MinUserRating int,
   PlayerNumber int,
   IsPrivate boolean,
   Gender varchar(10)
   
);
