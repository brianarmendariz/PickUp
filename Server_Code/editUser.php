
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
	$Username = $_POST['Username'];
        $FirstName = $_POST['FirstName'];
	$LastName = $_POST['LastName'];
	$Birthday = date('Y-m-d');
	$Gender = $_POST['Gender'];
	$UserRating = $_POST['UserRating'];
	$PicturePath = $_POST['PicturePath'];


	$sql = 'SELECT * FROM Users WHERE Username = "' . $Username . '";';
	$result = SQLQuery($sql);
	if ($result->num_rows > 0) {
		$sql = 'UPDATE Users SET Username="' . $Username . '", FirstName="' . $FirstName . '", LastName="' . $LastName . '", Birthday="' . $Birthday .
		'", Gender="' .  $Gender . '", UserRating="' . $UserRating . '", PicturePath="' . $PicturePath . '" 
	    WHERE Username = "' . $Username . '";';
	    
		if(SQLQuery($sql) == TRUE){
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
