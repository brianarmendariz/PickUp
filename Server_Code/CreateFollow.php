<?php
// --------------------------------------------------------------------
// createFollow.php:  Page for inserting Follow info into database.
//
// Created: 5/8/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";



if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    
    $Follower = $_POST['Follower'];
    $Followee = $_POST['Followee'];   
    
    $sql = 'SELECT * FROM Follows WHERE Follower="' . $Follower . '" && Followee ="' . $Followee . '";';
    
    $result = SQLQuery($sql);
    
    if ($result->num_rows > 0) {
        print('false');
    } else {
        
        $sql = 'INSERT INTO Follows (Follower, Followee)
    			VALUES("' . $Follower . '",
			       "' . $Followee . '");';
        SQLQuery($sql);
        print('true');
    }
}


?>
