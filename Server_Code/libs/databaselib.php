<?php
// --------------------------------------------------------------------
// databaselib.php:  library functions for database access
//
// Created: 2/15/2016
// --------------------------------------------------------------------

require_once "config.php";
$connection = GetSqlConnection();
// --------------------------------------------------------------------

function GetSqlConnection()
{
    global $config;
    static $conn = null;
    
    $conn = new mysqli(
       $config["db"]["host"], 
       $config["db"]["username"],
       $config["db"]["password"],
       $config["db"]["dbname"]);
   

   return $conn;
}

// --------------------------------------------------------------------

function SqlQuery($sql)
{
    $conn = GetSqlConnection();
    $result = $conn->query($sql);
    if($result == false) { return false; }
    return $result;
}

?>


