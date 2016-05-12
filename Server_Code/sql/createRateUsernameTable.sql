Use Pickup;
drop table rateUsername;

create Table RateUsername(
   RatingID int AUTO_INCREMENT PRIMARY KEY,	/*Unique ID for rating table */ 
   RaterUsername varchar(40),				/*Email of the Rater */ 
   RatedUsername varchar(40), 				/*Email of the Rated user */ 
   Vote int									/*IIf Rater has up or down voted the rated */ 
);
