Use Pickup;
Drop Table EventRSVPs;

create Table EventRSVPs(
   RSVPID int AUTO_INCREMENT PRIMARY KEY,	/*Unique ID for the EventRSVP table */ 
   RSVPUser varchar(40), 					/*Username of user who RSVPd */ 
   EventID int								/*ID of Event that was RSVPd to*/ 
     
);
