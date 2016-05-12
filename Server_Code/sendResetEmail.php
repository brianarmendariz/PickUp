
<?php
// --------------------------------------------------------------------
// sendResetEmail.php:  Page for resetting user's password to a 
// generated random string and sending the user an email with the new
// temporary password.
//
// Created: 2/15/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";
require_once "libs/password.php";


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $Username     = $_POST['Username'];
    $Password     = generateRandomString();
    $PasswordHash = password_hash($Password, PASSWORD_DEFAULT);
    $sql          = 'SELECT * FROM Users Where Username = "' . $Username . '";';
    $result       = SQLQuery($sql);
    if ($result->num_rows > 0) {
        
        $row     = $result->fetch_assoc();
        $to      = $Username;
        $subject = "Pickup Password Reset";
        
        $message = "
		<html>
		<head>
		<title>Pickup Password Reset</title>
		</head>
		<body>
		<h1>Pickup Password Reset </h1>
		<p>You requested a password reset for the Pickup App. Please use the generated password below to login and reset your password on the Change Settings page.</p>
		<table>
		<tr>
		<th>User</th>
		<th>New Password</th>
		</tr>
		<tr>
		<td>" . $Username . "   </td>
		<td>" . $Password . "</td>
		</tr>
		</table>
		</body>
		</html>
		";
        
        // Always set content-type when sending HTML email
        $headers = "MIME-Version: 1.0" . "\r\n";
        $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";
        
        // More headers
        $headers .= 'From: <webmaster@csulbpickup.com>' . "\r\n";
        
        mail($to, $subject, $message, $headers);
        print('true');
        $sql    = 'UPDATE Users SET Password="' . $PasswordHash . '" WHERE Username = "' . $Username . '";';
        $result = SQLQuery($sql);
        
    } else {
        print('false');
    }
}

function generateRandomString($length = 10)
{
    $characters       = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString     = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}



?>
