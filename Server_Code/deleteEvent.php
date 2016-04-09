
<?php
// --------------------------------------------------------------------
// deleteEvent.php:  Page for deleting Event from database.
//
// Created: 2/22/2016 SS
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";




if( $_SERVER["REQUEST_METHOD"] == "POST") 
{

    $EventID = $_POST["EventID"];


	$sql = 'SELECT * FROM PickupEvents WHERE EventID = "' . $EventID . '";';
	$result = SQLQuery($sql);
	if ($result->num_rows > 0) {
		$sql = 'DELETE FROM PickupEvents WHERE EventID = ' . $EventID . ';';
		if(SQLQuery($sql)=== TRUE){
			print ("true");

		$sql = 'DELETE FROM EventRSVPs WHERE EventID = ' . $EventID . ';';
		SQLQuery($sql);

		}
		else{
			print("false");
		}
	}
	print ("false");

}


?>