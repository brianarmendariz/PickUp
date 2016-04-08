
<?php
// --------------------------------------------------------------------
// getEvent.php:  Page for getting a single Event from database.
//
// Created: 2/23/2016 SS
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";



if( $_SERVER["REQUEST_METHOD"] == "POST") 
{
    $Username = $_POST["Username"];
	$Password = $_POST["Password"];
	$NewPassword = $_POST["NewPassword"];


	$sql = 'SELECT * FROM Users WHERE Username="' . $Username . '" AND Password = "' . $Password .'";';
	$result = SQLQuery($sql);
	
	
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		
		$sqlUpdate = 'UPDATE Users SET Password = "' . $NewPassword . '" WHERE Username = "' . $Username . '";';
		$result = SQLQuery($sqlUpdate);
		print ($result);
	}
	else{
	print ("false");
}

}


?>
