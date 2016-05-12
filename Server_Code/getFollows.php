
<?php
// --------------------------------------------------------------------
// getFollows.php:  Page for getting all Follows for a User.
//
// Created: 5/8/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";

if ($_SERVER["REQUEST_METHOD"] == "GET") {
    
    $Follower = $_GET["Follower"];
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $Follower = $_POST["Follower"];
}



$sql        = 'SELECT Users.Username, Users.FirstName, Users.LastName FROM Follows
		 Right Join Users on Follows.Followee=Users.Username
		 WHERE Follower = "' . $Follower . '";';
$result     = SQLQuery($sql);
$resultData = "";
if ($result === FALSE) {
    //echo 'false';
} else if ($result->num_rows > 0) {
    // output data of each row
    while ($row = $result->fetch_assoc()) {
        
        $resultData .= '#,Username::' . $row["Username"];
        $resultData .= ',Name::' . $row["FirstName"] . " " . $row["LastName"];
        
        
    }
}
print($resultData);




?>
