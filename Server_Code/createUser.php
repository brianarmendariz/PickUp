<?php
// --------------------------------------------------------------------
// createUser.php:  Page for inserting User info into database.
//
// Created: 3/6/2016 BA
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";



if( $_SERVER["REQUEST_METHOD"] == "POST") 
{

    /*set default values for these fields incase the user switches to advanced form */
    $Username = $_POST["Username"];
    $FirstName = $_POST['FirstName'];
	$LastName = $_POST['LastName'];
	$Birthday = $_POST['Birthday'];
	$Gender = $_POST['Gender'];
	$UserRating = $_POST['UserRating'];
	$picturePath = $_POST['picturePath'];


	$sql = 'INSERT INTO Users (Username, Password, FirstName, LastName, Birthday, Gender, UserRating, picturePath)
    VALUES("'.$Username.'",
	"'.$Password.'", 
	"'.$FirstName.'", 
	"'.$LastName.'",
	"'.$Birthday.'",
	"'.$Gender.'",
	"'.$UserRating.'",
	"'.$picturePath.'");';
    
	SQLQuery($sql);
	
	$sql = 'SELECT UserID FROM Users WHERE Username="' . $Username . '" AND Password = "' . $Password .'";';
	
	$result = SQLQuery($sql);
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$UserID = $row["UserID"];
		print($eventID);
	}
	else{
		print('false');
	}
//);
}


?>