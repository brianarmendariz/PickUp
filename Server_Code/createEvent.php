
<?php
// --------------------------------------------------------------------
// createEvent.php:  Page for inserting Event data into database.
//
// Created: 2/15/2016 SS
// Edited: 4/10/2016 SS - added EventDate and EventTime. Eventually
//						should take out EventDateTime.
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $AuthorName      = mysqli_real_escape_string($connection, $_POST["AuthorName"]);
    $Email           = mysqli_real_escape_string($connection, $_POST["Email"]);
    $EventName       = mysqli_real_escape_string($connection, $_POST['EventName']);
    $Sport           = mysqli_real_escape_string($connection, $_POST['Sport']);
    $Location        = mysqli_real_escape_string($connection, $_POST['Location']);
    $Latitude        = mysqli_real_escape_string($connection, $_POST['Latitude']);
    $Longitude       = mysqli_real_escape_string($connection, $_POST['Longitude']);
    $DateTimeCreated = mysqli_real_escape_string($connection, date('Y-m-d H:i:s'));
    $EventDateTime   = new DateTime($_POST['EventDateTime']);
    $AgeMax          = mysqli_real_escape_string($connection, $_POST['AgeMax']);
    $AgeMin          = mysqli_real_escape_string($connection, $_POST['AgeMin']);
    $MinUserRating   = mysqli_real_escape_string($connection, $_POST["MinUserRating"]);
    $PlayerNumber    = mysqli_real_escape_string($connection, $_POST['PlayerAmount']);
    $IsPrivate       = mysqli_real_escape_string($connection, $_POST['IsPrivate']);
    $Gender          = mysqli_real_escape_string($connection, $_POST['Gender']);
    
    $EventDate = $EventDateTime->format('Y/m/d');
    $EventTime = $EventDateTime->format('H:i:s');
    
    
    
    $sql = 'INSERT INTO PickupEvents (AuthorName,Email, EventName, Sport, Location, DateTimeCreated, EventDateTime, EventDate, EventTime, Latitude, Longitude, AgeMax, AgeMin, MinUserRating, PlayerNumber, IsPrivate, Gender)
    VALUES("' . $AuthorName . '",
	"' . $Email . '",
	"' . $EventName . '", 
	"' . $Sport . '", 
	"' . $Location . '",
	"' . $DateTimeCreated . '",
	"' . $EventDateTime->format('Y-m-d H:i:s') . '",
	"' . $EventDate . '",
	"' . $EventTime . '",	
	"' . $Latitude . '",
	"' . $Longitude . '",
	"' . $AgeMax . '",
	"' . $AgeMin . '",
	"' . $MinUserRating . '",
	"' . $PlayerNumber . '",
	"' . $IsPrivate . '",
	"' . $Gender . '");';
    
    SQLQuery($sql);
    
    $sql    = 'SELECT EventID FROM PickupEvents WHERE EventName="' . $EventName . '" AND AuthorName = "' . $AuthorName . '" AND DateTimeCreated="' . $DateTimeCreated . '";';
    $result = SQLQuery($sql);
    if ($result->num_rows > 0) {
        $row     = $result->fetch_assoc();
        $eventID = $row["EventID"];
        
        $sql2    = 'INSERT INTO EventRSVPs (RSVPUser, EventID)VALUES("' . $Email . '","' . $eventID . '");';
        $result2 = SQLQuery($sql2);
        
        print($result2);
    } else {
        print('false');
    }
    //);
}


?>
