<?php
// --------------------------------------------------------------------
// CheckIfFriends.php:  Page for Checking if a user is friends with 
//	another user.
//
// Created: 5/10/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $myUsername   = mysqli_real_escape_string($connection, $_POST['myUsername']);
    $thisUsername = mysqli_real_escape_string($connection, $_POST['thisUsername']);
    
    $sql    = 'SELECT * FROM Follows WHERE Follower="' . $myUsername . '" AND Followee = "' . $thisUsername . '";';
    $result = SQLQuery($sql);
    if ($result->num_rows > 0) {
        print('true'); // means the users are friends
    } else {
        print('false'); // usersa are not friends
    }
}

?>
