
<?php
// --------------------------------------------------------------------
// getUser.php:  Page for getting a single Event from database.
//
// Created: 3/6/2016 BRAIN
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";




if( $_SERVER["REQUEST_METHOD"] == "POST") 
{

    $Username = $_POST["Username"];


	$sql = 'SELECT * FROM Users WHERE Username = "' . $Username . '";';
	
	$result = SQLQuery($sql);
	$resultData = "";
	if ($result->num_rows > 0) {
		$row = $result->fetch_assoc();
		$resultData .= 'Username::' . $row["Username"];
		$resultData .= ',Password::' . $row["Password"];
		$resultData .= ',FirstName::' . $row["FirstName"];
		$resultData .= ',LastName::' . $row["LastName"];
		$resultData .= ',Birthday::' . $row["Birthday"];
		$resultData .= ',Gender::' . $row["Gender"];
		$resultData .= ',UserRating::' . $row["UserRating"];
		$resultData .= ',picturePath::' . $row["picturePath"];
		print ($resultData);
	}
	else{
	print ("false");
}

}


?>