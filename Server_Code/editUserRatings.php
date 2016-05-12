
<?php
// --------------------------------------------------------------------
// editUserRatings.php:  Page for editing UserRatings
//
// Created: 2/15/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";




if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    
    $RaterUsername = $_POST['RaterUsername'];
    $RatedUsername = $_POST['RatedUsername'];
    $Vote          = $_POST['Vote'];
    
    
    $sql    = 'SELECT * FROM RateUsername WHERE RaterUsername = "' . $RaterUsername . '" AND RatedUsername= "' . $RatedUsername . '";';
    $result = SQLQuery($sql);
    if ($result->num_rows > 0) {
        $sql = 'UPDATE RateUsername SET RaterUsername="' . $RaterUsername . '", RatedUsername="' . $RatedUsername . '", Vote="' . $Vote . '"
	    WHERE RaterUsername = "' . $RaterUsername . '" AND RatedUsername= "' . $RatedUsername . '";';
        
        if (SQLQuery($sql) == TRUE) {
            print('true');
        } else {
            print('false2');
        }
        
    } else {
        print('false1');
    }
}

?>
