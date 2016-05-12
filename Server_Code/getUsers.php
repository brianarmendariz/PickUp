
<?php
// --------------------------------------------------------------------
// getUsers.php:  Page for getting all Users in database.
//
// Created: 5/8/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";


$sql        = 'SELECT Username FROM Users;';
$result     = SQLQuery($sql);
$resultData = "";
if ($result->num_rows > 0) {
    // output data of each row
    while ($row = $result->fetch_assoc()) {
        $resultData .= '#' . $row["Username"];
    }
}
print($resultData);


?>
