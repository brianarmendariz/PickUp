<?php
// --------------------------------------------------------------------
// getEventsForUser.php:  Page for getting all events in database.
//
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";

$Username = $_POST["Username"];

$sql = 'SELECT * ' . 'FROM PickupEvents ' . 'LEFT JOIN EventRSVPs ' . 'ON PickupEvents.EventID=EventRSVPs.EventID ' . 'WHERE EventRSVPs.RSVPUser="' . $Username . '";';

$result     = SQLQuery($sql);
$resultData = "";
if ($result->num_rows > 0) {
    // output data of each row
    while ($row = $result->fetch_assoc()) {
        
        $resultData .= '#,EventID::' . $row["EventID"];
        $resultData .= ',AuthorName::' . $row["AuthorName"];
        $resultData .= ',Email::' . $row["Email"];
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
        $resultData .= ',PlayerNumber:' . $row["PlayerNumber"];
        $resultData .= ',IsPrivate::' . $row["IsPrivate"];
        $resultData .= ',Gender::' . $row["Gender"];
        //use the results for the next query
        
    }
}
print($resultData);


?>
