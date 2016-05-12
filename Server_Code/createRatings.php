
<?php
// --------------------------------------------------------------------
// createRatings.php:  Page for inserting Event data into database.
//
// Created: 2/15/2016 SS
// Edited: 4/10/2016 SS - added EventDate and EventTime. Eventually
//						should take out EventDateTime.
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $RaterUsername = $_POST['RaterUsername'];
    $RatedUsername = $_POST['RatedUsername'];
    $Vote          = $_POST['Vote'];
    
    
    $sql = 'INSERT INTO RateUsername(RaterUsername,RatedUsername, Vote)
    VALUES("' . $RaterUsername . '",
	"' . $RatedUsername . '",
	"' . $Vote . '");';
    
    SQLQuery($sql);
    
    $sql    = 'SELECT RatingID FROM RateUsername WHERE RaterUsername="' . $RaterUsername . '" AND RatedUsername = "' . $RatedUsername . '" AND Vote="' . $Vote . '";';
    $result = SQLQuery($sql);
    if ($result->num_rows > 0) {
        $row      = $result->fetch_assoc();
        $RatingID = $row["RatingID"];
        
        print($RatingID);
    } else {
        print('false');
    }
}


?>
