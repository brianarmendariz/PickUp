
<?php
// --------------------------------------------------------------------
// deleteEvent.php:  Page for deleting Event from database.
//
// Created: 3/11/2016 BA
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";




if( $_SERVER["REQUEST_METHOD"] == "POST") 
{

    $Username = $_POST["Username"];


	$sql = 'SELECT * FROM Users WHERE Username = "' . $Username . '";';
	$result = SQLQuery($sql);
	if ($result->num_rows > 0) {
		$sql = 'DELETE FROM Users WHERE Username ="' . $Username . '";';
		if(SQLQuery($sql) == TRUE){
			print ("delete user : true");
		}
		else{
			print("delete user : false");
		}
	}
}


?>