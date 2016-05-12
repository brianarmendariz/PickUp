<?php
// --------------------------------------------------------------------
// getUserRatingsList.php:  Page for getting a list of RSVPs for an event.
//
// Created: 4/5/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";



if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $RaterUsername = $_POST['RaterUsername'];
    
    
    
    $sql        = 'SELECT * FROM RateUsername WHERE RaterUsername ="' . $RaterUsername . '";';
    $result     = SQLQuery($sql);
    $resultData = "";
    if ($result === FALSE) {
        //print ("false");
    } else if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $resultData .= '#,RatingID::' . $row["RatingID"];
            $resultData .= ',RaterUsername::' . $row["RaterUsername"];
            $resultData .= ',RatedUsername::' . $row["RatedUsername"];
            $resultData .= ',Vote::' . $row["Vote"];
            
        }
        
    }
    print($resultData);
}
?>