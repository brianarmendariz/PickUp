
<?php
// --------------------------------------------------------------------
// editEvent.php:  Page for editing Event data in database.
//
// Created: 2/15/2016 SS
// --------------------------------------------------------------------

require_once "config.php"; 
require_once "libs/databaselib.php";




if( $_SERVER["REQUEST_METHOD"] == "POST") 
{

    /*set default values for these fields incase the user switches to advanced form */
   	$EventID = $_POST["EventID"];
    $EventName = $_POST['EventName'];
    $Sport = $_POST['Sport'];
    $Location = $_POST['Location'];
	$Latitude = $_POST['Latitude'];
	$Longitude = $_POST['Longitude'];
    $DateTimeCreated = date('Y-m-d H:i:s');
    $EventDateTime = $_POST['EventDateTime'];
    $AgeMax = $_POST['AgeMax'];
    $AgeMin = $_POST['AgeMin'];
    $MinUserRating = $_POST["MinUserRating"];
    $PlayerNumber = $_POST['PlayerAmount'];
    $IsPrivate = $_POST['IsPrivate'];
	$Gender = $_POST['Gender'];


	$sql = 'SELECT * FROM PickupEvents WHERE EventID = "' . $EventID . '";';
	$result = SQLQuery($sql);
	if ($result->num_rows > 0) {
		$sql = 'UPDATE PickupEvents SET  EventName="' . $EventName . 
		'", Sport="' . $Sport . '", Location="' . $Location . '", EventDateTime="' . $EventDateTime .
		 '", Latitude="' .  $Latitude . '", Longitude="' . $Longitude . '", AgeMax="' . $AgeMax . 
		 '", AgeMin="' .  $AgeMin . '", MinUserRating="' . $MinUserRating . '", PlayerNumber="' . $PlayerNumber .
		    '", IsPrivate="' . $IsPrivate . '", Gender="' . $Gender . '" 
	    WHERE EventID = ' . $EventID . ';';
	    
		if(SQLQuery($sql)=== TRUE){
			print('true');
		}
		else{
			print('false2');
		}

	}
	else{
		print('false1');
	}
}

?>
