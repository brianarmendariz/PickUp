
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

	$sql = 'INSERT INTO PickupEvents (Author, EventName, Sport, Location, DateTimeCreated, EventDateTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, IsPrivate)
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
	"'.$IsPrivate.'")';
    SQLQuery($sql);
	
	return true;
}
print ("Its Working");


?>