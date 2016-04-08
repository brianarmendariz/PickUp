<?php
// --------------------------------------------------------------------
// login.php:  Page for inserting User info into database.
//
// Created: 3/6/2016 BA
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";



if( $_SERVER["REQUEST_METHOD"] == "POST") 
{


	/*set default values for these fields incase the user switches to advanced form */
	$Username = $_POST['Username'];
	$Password = $_POST['Password'];

	$sql = 'SELECT * FROM Users WHERE Username="' . $Username . '" AND Password = "' . $Password .'";';
	
	$result = SQLQuery($sql);
	
	
	if ($result->num_rows > 0) {
		print('login successful');  // means the user and password are correct
	}
	else{
		print('login failed'); // user and password incorrect
	}

}


?>