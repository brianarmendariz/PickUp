Use Pickup;
drop table PickupEvents;


create Table PickupEvents(
   EventID int AUTO_INCREMENT PRIMARY KEY,
   AuthorName varchar(40),
   Email varchar(40), 
   EventName varchar(40),                
   Sport varchar(40),
   Location varchar(80),
   Latitude double(20,16),
   Longitude double(20,16),
   DateTimeCreated datetime,
   EventDateTime datetime,
   AgeMax varchar(3),
   AgeMin varchar(3),
   MinUserRating varchar(3),
   PlayerNumber varchar(3),
   IsPrivate boolean,
   Gender varchar(10)
   
);
