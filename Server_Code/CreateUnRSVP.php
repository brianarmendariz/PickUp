<?php
// --------------------------------------------------------------------
// createUnRSVP.php:  Page for inserting RSVP info into database.
//
// Created: 4/8/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";



if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $RSVPUser = $_POST['UnRSVPUser'];
    $EventID  = $_POST['EventID'];
    
    
    
    $sql = 'SELECT * FROM EventRSVPs WHERE RSVPUser="' . $RSVPUser . '" AND EventID = "' . $EventID . '";';
    
    $result = SQLQuery($sql);
    
    if ($result->num_rows > 0) {
        $sql    = 'DELETE FROM EventRSVPs WHERE RSVPUser="' . $RSVPUser . '" AND EventID = "' . $EventID . '";';
        $result = SQLQuery($sql);
        print('true');
    } else {
        print('false');
    }
    
    
    
}


?>