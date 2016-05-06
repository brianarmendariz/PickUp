<?php
// --------------------------------------------------------------------
// createRSVP.php:  Page for inserting RSVP info into database.
//
// Created: 4/5/2016 SS
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";



if( $_SERVER["REQUEST_METHOD"] == "POST") 
{

	$RSVPUser = $_POST['RSVPUser'];
	$EventID = $_POST['EventID'];



	$sql = 'SELECT * FROM EventRSVPs WHERE RSVPUser="' . $RSVPUser . '" AND EventID = "' . $EventID . '";';
	
	$result = SQLQuery($sql);

	if($result === FALSE){
		$sql = 'INSERT INTO EventRSVPs (RSVPUser, EventID)VALUES("'.$RSVPUser.'","'.$EventID.'");';
    
		$result2 = SQLQuery($sql);
		if($result2 === FALSE){
			print('false');
		}
		else{
			print('true');
		}

	} 

	else if ($result->num_rows > 0) {
		print('false');
	}
else{
$sql = 'INSERT INTO EventRSVPs (RSVPUser, EventID)VALUES("'.$RSVPUser.'","'.$EventID.'");';
    
		$result2 = SQLQuery($sql);
		if($result2 === FALSE){
			print('false');
		}
		else{
			print('true');
		}
}

}


?>