
<?php
// --------------------------------------------------------------------
// changePassword.php:  Page for Changing a user's password.
//
// Created: 2/23/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";
require_once "libs/password.php";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $Username        = mysql_real_escape_string($_POST["Username"]);
    $Password        = mysql_real_escape_string($_POST["Password"]);
    $NewPassword     = mysql_real_escape_string($_POST["NewPassword"]);
    $passwordHash    = password_hash($Password, PASSWORD_DEFAULT);
    $newPasswordHash = password_hash($NewPassword, PASSWORD_DEFAULT);
    $sql             = 'SELECT * from Users where Username ="' . $Username . '"';
    $result          = SQLQuery($sql);
    if ($result->num_rows > 0) {
        $row         = $result->fetch_assoc();
        $curPassword = $row['Password'];
        if (password_verify($Password, $curPassword)) {
            $sqlUpdate = 'UPDATE Users SET Password = "' . $newPasswordHash . '" WHERE Username = "' . $Username . '";';
            $result    = SQLQuery($sqlUpdate);
            print("First REsult" . $result);
        } else {
            
            $sql = 'SELECT * FROM Users WHERE Username="' . $Username . '" AND Password = "' . $Password . '";';
            
            $result = SQLQuery($sql);
            
            if ($result->num_rows > 0) {
                $row       = $result->fetch_assoc();
                $sqlUpdate = 'UPDATE Users SET Password = "' . $newPasswordHash . '" WHERE Username = "' . $Username . '";';
                $result    = SQLQuery($sqlUpdate);
                print("second Result" . $result);
            } else {
                print("last false");
            }
        }
        
    }
}
?>
