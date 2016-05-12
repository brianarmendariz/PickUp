
<?php
// --------------------------------------------------------------------
// deleteRSVP.php:  Page for deleting RSVP from database.
//
// Created: 4/5/2016 BA
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";




if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $RSVPUser = $_POST["RSVPUser"];
    $EventID  = $_POST["EventID"];
    
    
    $sql    = 'SELECT * FROM EventRSVPs WHERE RSVPUser = "' . $RSVPUser . '" AND EventID = "' . $EventID . '";';
    $result = SQLQuery($sql);
    if ($result->num_rows > 0) {
        $sql = 'DELETE FROM EventRSVPs WHERE RSVPUser = "' . $RSVPUser . '" AND EventID = "' . $EventID . '";';
        if (SQLQuery($sql) == TRUE) {
            print("delete user : true");
        } else {
            print("delete user : false");
        }
    }
}


?>
