<?php
// --------------------------------------------------------------------
// createUser.php:  Page for inserting User info into database.
//
// Created: 3/6/2016 BA
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";
require_once "libs/password.php";


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    
    $Username    = mysqli_real_escape_string($connection, $_POST['Username']);
    $Password    = mysqli_real_escape_string($connection, $_POST['Password']);
    $FirstName   = mysqli_real_escape_string($connection, $_POST['FirstName']);
    $LastName    = mysqli_real_escape_string($connection, $_POST['LastName']);
    $Birthday    = mysqli_real_escape_string($connection, $_POST['Birthday']);
    $Gender      = mysqli_real_escape_string($connection, $_POST['Gender']);
    $UserRating  = mysqli_real_escape_string($connection, $_POST['UserRating']);
    $PicturePath = mysqli_real_escape_string($connection, $_POST['PicturePath']);
    
    $passwordHash = password_hash($Password, PASSWORD_DEFAULT);
    
    
    $sql = 'SELECT * FROM Users WHERE Username="' . $Username . '";';
    
    $result = SQLQuery($sql);
    
    if ($result->num_rows > 0) {
        print('false');
    } else {
        
        $sql = 'INSERT INTO Users (Username, Password, FirstName, LastName, Birthday, Gender, UserRating, picturePath)
    			VALUES("' . $Username . '",
			       "' . $passwordHash . '", 
			       "' . $FirstName . '", 
			       "' . $LastName . '",
			       "' . $Birthday . '",
			       "' . $Gender . '",
			       "' . $UserRating . '",
			       "' . $PicturePath . '");';
        
        SQLQuery($sql);
    }
    
}


?>
