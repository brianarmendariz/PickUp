<?php
// --------------------------------------------------------------------
// index.php -- Default entry page for Pickup!
//
// Created: 2/15/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
session_start();
//log_page();

/*if(IsLoggedIn()) { JumpToPage("pages/welcome.php");  }
else             { JumpToPage("pages/login.php");    }

?>