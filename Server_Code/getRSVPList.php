<?php
// --------------------------------------------------------------------
// getRSVPList.php:  Page for getting a list of RSVPs for an event.
//
// Created: 4/5/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";



if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $EventID = $_POST['EventID'];
    
    
    
    $sql        = 'SELECT Users.Username, Users.FirstName, Users.LastName, EventRSVPs.EventID FROM EventRSVPs Right Join Users on EventRSVPs.RSVPUser=Users.Username WHERE EventID = "' . $EventID . '";';
    $result     = SQLQuery($sql);
    $resultData = "";
    if ($result === FALSE) {
        //echo 'false';
    } else if ($result->num_rows > 0) {
        // output data of each row
        while ($row = $result->fetch_assoc()) {
            
            $resultData .= '#,EventID::' . $row["EventID"];
            $resultData .= ',Username::' . $row["Username"];
            $resultData .= ',Name::' . $row["FirstName"] . " " . $row["LastName"];
            
            
        }
    }
    print($resultData);
}


?>