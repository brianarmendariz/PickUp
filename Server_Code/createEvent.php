
<?php
// --------------------------------------------------------------------
// createEvent.php:  Page for inserting Event data into database.
//
// Created: 2/15/2016 SS
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";




if( $_SERVER["REQUEST_METHOD"] == "POST") 
{

    /*set default values for these fields incase the user switches to advanced form */
    $Author = $_POST["Author"];
    $EventName = $_POST['EventName'];
    $Sport = $_POST['Sport'];
    $Location = $_POST['Location'];
	$Latitude = $_POST['Latitude'];
	$Longitude = $_POST['Longitude'];
    $DateTimeCreated = date('Y-m-d H:i:s');
    $EventDateTime = $_POST['EventDateTime'];
    $AgeMax = $_POST['AgeMax'];
    $AgeMin = $_POST['AgeMin'];
    $MinUserRating = $_POST["MinUserRating"];
    $PlayerNumber = $_POST['PlayerAmount'];
    $IsPrivate = $_POST['IsPrivate'];
	$Gender = $_POST['Gender'];

	$sql = 'INSERT INTO PickupEvents (Author, EventName, Sport, Location, DateTimeCreated, EventDateTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, IsPrivate, Gender)
    VALUES("'.$Author.'",
	"'.$EventName.'", 
	"'.$Sport.'", 
	"'.$Location.'",
	"'.$DateTimeCreated.'",
	"'.$EventDateTime.'",
	"'.$Latitude.'",
	"'.$Longitude.'",
	"'.$AgeMax.'",
	"'.$AgeMin.'",
	"'.$MinUserRating.'",
	"'.$PlayerNumber.'",
	"'.$IsPrivate.'",
	"'.$Gender.'");';
    
	SQLQuery($sql);
	
	$sql = 'SELECT EventID FROM PickupEvents WHERE EventName="' . $EventName . '" AND Author = "' . $Author .
		'" AND DateTimeCreated="' . $DateTimeCreated . '";';
	$result = SQLQuery($sql);
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$eventID = $row["EventID"];
		print($eventID);
	}
	else{
		print('false');
	}
//);
}


?>