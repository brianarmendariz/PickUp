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

//	$url = 'http://www.csulbpickup.com/createUser.php';
//	$json = json_decode(file_get_contents($url), true);

//	echo file_get_contents('php://input');

	/*set default values for these fields incase the user switches to advanced form */
	$Username = $_POST['Username'];
	$Password = $_POST['Password'];
    	$FirstName = $_POST['FirstName'];
	$LastName = $_POST['LastName'];
	$Birthday = $_POST['Birthday'];
	$Gender = $_POST['Gender'];
	$UserRating = $_POST['UserRating'];
	$PicturePath = $_POST['PicturePath'];

//	print("\n" + $Username);


	$sql = 'SELECT * FROM Users WHERE Username="' . $Username . '";';
	
	$result = SQLQuery($sql);
	
	if ($result->num_rows > 0) {
		print('false');
	}
	else{
		//print("\n" + $Username);
	    


		$sql = 'INSERT INTO Users (Username, Password, FirstName, LastName, Birthday, Gender, UserRating, picturePath)
    			VALUES("'.$Username.'",
			       "'.$Password.'", 
			       "'.$FirstName.'", 
			       "'.$LastName.'",
			       "'.$Birthday.'",
			       "'.$Gender.'",
			       "'.$UserRating.'",
			       "'.$PicturePath.'");';
    
			      SQLQuery($sql);
	}

}


?>