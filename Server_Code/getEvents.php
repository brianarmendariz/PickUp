
<?php
// --------------------------------------------------------------------
// getEvents.php:  Page for getting all events in database.
//
// Created: 2/18/2016 SS
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";


$sql = 'SELECT * FROM PickupEvents;';
$result = SQLQuery($sql);
$resultData = "";
	if ($result->num_rows > 0) {
		// output data of each row
		while($row = $result->fetch_assoc()) {

	$resultData .= '#,EventID::' . $row["EventID"];
	$resultData .= ',Author::' . $row["Author"];
	$resultData .= ',EventName::' . $row["EventName"];
	$resultData .= ',Sport::' . $row["Sport"];
	$resultData .= ',Location::' . $row["Location"];
	$resultData .= ',Latitude::' . $row["Latitude"];
	$resultData .= ',Longitude::' . $row["Longitude"];
	$resultData .= ',DateTimeCreated::' . $row["DateTimeCreated"];
	$resultData .= ',EventDateTime::' . $row["EventDateTime"];
	$resultData .= ',AgeMax::' . $row["AgeMax"];
	$resultData .= ',AgeMin::' . $row["AgeMin"];
	$resultData .= ',MinUserRating::' . $row["MinUserRating"];
	$resultData .= ',PlayerNumber::' . $row["PlayerNumber"];	$resultData .= 'PlayerNumber:' . $row["PlayerNumber"];
	$resultData .= ',IsPrivate::' . $row["IsPrivate"];

	
		}
	}
print ($resultData);


?>