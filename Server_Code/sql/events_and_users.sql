use Pickup;

/*Insert dummy data for testing into database */

INSERT INTO Users (Username,Password,FirstName,LastName,Birthday,Gender,UserRating,PicturePath)    
VALUES ("userA@user.com","a","a","a","14-14-2016","M",0,"");

INSERT INTO Users (Username,Password,FirstName,LastName,Birthday,Gender,UserRating,PicturePath)  
VALUES ("userB@user.com","b","b","b","14-14-2016","F",0,"");

INSERT INTO Users (Username,Password,FirstName,LastName,Birthday,Gender,UserRating,PicturePath)    
VALUES ("userC@user.com","c","c","c","14-14-2016","M",0,"");

INSERT INTO Users (Username,Password,FirstName,LastName,Birthday,Gender,UserRating,PicturePath)    
VALUES ("userD@user.com","d","d","d","14-14-2016","M",0,"");

INSERT INTO Users (Username,Password,FirstName,LastName,Birthday,Gender,UserRating,PicturePath)   
VALUES ("userE@user.com","e","e","e","14-14-2016","F",0,"");

INSERT INTO Users (Username,Password,FirstName,LastName,Birthday,Gender,UserRating,PicturePath)    
VALUES ("userF@user.com","f","f","f","14-14-2016","F",0,"");

INSERT INTO Users (Username,Password,FirstName,LastName,Birthday,Gender,UserRating,PicturePath)    
VALUES ("userG@user.com","g","g","g","14-14-2016","F",0,"");

INSERT INTO Users (Username,Password,FirstName,LastName,Birthday,Gender,UserRating,PicturePath)    
VALUES ("userH@user.com","h","h","h","14-14-2016","M",0,"");





INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, 
	EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, 
	IsPrivate, Gender)
    VALUES("a a", "userA@user.com", "EventA", "Basketball", "CSUN", "2016-04-14 01:00:00", "2016-04-14 17:30:00", 
	"2016-04-14", "17:30:00", "34.2416", "118.5287", "50", "5", "-8", "10", 
	"P/NP", "Any");
	
INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, 
	EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, 
	IsPrivate, Gender)
    VALUES("a a", "userA@user.com", "EventA", "Basketball", "Phoenix", "2016-04-14 01:00:00", "2016-04-01 12:30:00", 
	"2016-04-01", "12:30:00", "33.4484", "112.0740", "50", "5", "-8", "10", 
	"P/NP", "Any");
	
INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, 
	EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, 
	IsPrivate, Gender)
    VALUES("a a", "userA@user.com", "EventA", "Volleyball", "Boulder", "2016-04-14 01:00:00", "2016-04-01 12:30:00", 
	"2016-04-01", "12:30:00", "40.0150", "105.2705", "50", "5", "-8", "10", 
	"P/NP", "Any");
	
INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, 
	EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, 
	IsPrivate, Gender)
    VALUES("a a", "userA@user.com", "EventA", "Volleyball", "San Mateo", "2016-04-14 01:00:00", "2016-04-01 12:30:00", 
	"2016-04-01", "12:30:00", "37.5630", "122.3255", "50", "5", "-8", "10", 
	"P/NP", "Any");	
	
INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, 
	EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, 
	IsPrivate, Gender)
    VALUES("b b", "userB@user.com", "EventB", "Tennis", "MGM GRAND", "2016-04-14 01:00:00", "2016-04-14 11:30:00", 
	"2016-04-14", "11:30:00", "36.1026", "115.1703", "30", "20", "-2", "8", 
	"P/NP", "Any");	

INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, 
	EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, 
	IsPrivate, Gender)
    VALUES("b b", "userB@user.com", "EventB", "Roller Hockey", "HBP", "2016-04-14 01:00:00", "2016-04-21 14:15:00", 
	"2016-04-21", "14:15:00", "33.6551", "118.0042", "20", "8", "-8", "2", 
	"P/NP", "Any");	
	
INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, 
	EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, 
	IsPrivate, Gender)
    VALUES("b b", "userB@user.com", "EventB", "Ice Hockey", "Staples Center", "2016-04-14 01:00:00", "2016-04-21 14:45:00", 
	"2016-04-21", "14:45:00", "34.0430", "118.2673", "66", "28", "-8", "16", 
	"P/NP", "Any");	

INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, 
	EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, 
	IsPrivate, Gender)
    VALUES("b b", "userB@user.com", "EventB", "Badminton", "Chicano Park", "2016-04-14 01:00:00", "2016-04-20 14:00:00", 
	"2016-04-20", "14:00:00", "32.7003", "117.1430", "66", "28", "-8", "16", 
	"P/NP", "Any");	


