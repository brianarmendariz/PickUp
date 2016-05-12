Use Pickup;
drop table PickupEvents;

create Table PickupEvents(
   EventID int AUTO_INCREMENT PRIMARY KEY,	/*Unique event ID */ 
   AuthorName varchar(40),					/*First and Last name of event author */ 
   Email varchar(40), 						/*Email address of event Author */ 
   EventName varchar(40),  					/*Name of event */               
   Sport varchar(40),						/*Sport to be played at event */ 
   Location varchar(80),					/*Address of event */ 
   Latitude double(20,16),					/*Latitude of event location */ 
   Longitude double(20,16),					/*Longitude of event location */ 
   DateTimeCreated datetime,				/*Date/time the event was created */ 
   EventDateTime datetime,					/*Dat and Time of event */ 
   EventDate date,							/*Date of the event */ 
   EventTime time,							/*Time of the event */ 
   AgeMax varchar(3),						/*Maximum age invited */ 
   AgeMin varchar(3),						/*Minimum age invited */ 	
   MinUserRating varchar(3),				/*Mininum user rating invited to Event */ 
   PlayerNumber varchar(3),					/*Number of players invited */ 
   IsPrivate boolean,						/*If event is public or private */ 
   Gender varchar(10)						/*Gender specified for Event */ 
   
);
