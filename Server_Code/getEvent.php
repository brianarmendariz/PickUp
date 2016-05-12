
<?php
// --------------------------------------------------------------------
// getEvent.php:  Page for getting a single Event from database.
//
// Created: 2/23/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";




if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $EventID = $_POST["EventID"];
    
    
    $sql        = 'SELECT * FROM PickupEvents WHERE EventID = "' . $EventID . '";';
    $result     = SQLQuery($sql);
    $resultData = "";
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
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
        $resultData .= ',PlayerNumber::' . $row["PlayerNumber"];
        $resultData .= ',PlayerNumber:' . $row["PlayerNumber"];
        $resultData .= ',IsPrivate::' . $row["IsPrivate"];
        $resultData .= ',Gender::' . $row["Gender"];
        print($resultData);
    } else {
        print("false");
    }
    
}


?>
