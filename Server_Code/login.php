<?php
// --------------------------------------------------------------------
// login.php:  Page for inserting User info into database.
//
// Created: 3/6/2016 BA
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";
require_once "libs/password.php";


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $Username = mysqli_real_escape_string($connection, $_POST['Username']);
    $Password = mysqli_real_escape_string($connection, $_POST['Password']);
    $sql      = 'SELECT Password from Users where Username ="' . $Username . '"';
    $result   = SQLQuery($sql);
    
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        if (password_verify($Password, $row["Password"])) {
            print('login successful'); // means the user and password are correct
        } else {
            
            $sql = 'SELECT * FROM Users WHERE Username="' . $Username . '" AND Password = "' . $Password . '";';
            
            $result = SQLQuery($sql);
            
            if ($result->num_rows > 0) {
                print('login successful'); // means the user and password are correct
            } else {
                print('login failed'); // user and password incorrect
            }
        }
    } else {
        print('login failed'); // user and password incorrect
    }
    
    
}

?>
