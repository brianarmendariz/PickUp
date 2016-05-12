Use Pickup;
Drop Table Follows;

create Table Follows(
	FollowID int AUTO_INCREMENT PRIMARY KEY,
   	Follower varchar(40), /*Email of follower */
   	Followee varchar(40) /*Email of followee */               
);
