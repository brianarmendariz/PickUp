<?php
require "libs/password.php";
echo "Test for functionality of compat library: " . (PasswordCompat\binary\check() ? "Pass" : "Fail");
echo "\n";
