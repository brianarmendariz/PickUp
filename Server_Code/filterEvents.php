
<?php
// --------------------------------------------------------------------
// filterEvents.php:  Page for filtering and returning a list of events
//
// Created: 4/9/2016 SS
// --------------------------------------------------------------------

require_once "config.php";
require_once "libs/databaselib.php";




if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    $EventName        = $_POST['EventName'];
    $Sport            = $_POST['Sport'];
    $DateCreatedStart = $_POST['DateCreatedStart'];
    $DateCreatedEnd   = $_POST['DateCreatedEnd'];
    
    
    $EventTimeStart = $_POST['EventTimeStart'];
    $EventTimeEnd   = $_POST['EventTimeEnd'];
    
    $EventDateStart = $_POST['EventDateStart'];
    $EventDateEnd   = $_POST['EventDateEnd'];
    
    $AgeMin = $_POST['AgeMin'];
    $AgeMax = $_POST['AgeMax'];
    
    $MinUserRating = $_POST['MinUserRating'];
    $Gender        = $_POST['Gender'];
    $onlyNotFull   = $_POST['OnlyNotFull'];
    $isPublic      = $_POST['IsPublic'];
    
    
    $multipleWheres = false;
    $sql            = 'SELECT * FROM PickupEvents';
    
    if (isset($_POST['Author']) && $_POST['Author'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $sql .= '(AuthorName LIKE "%' . $_POST['Author'] . '%" OR Email ="' . $_POST['Author'] . '")';
        $multipleWheres = true;
    }
    if (isset($_POST['AuthorEmail']) && $_POST['AuthorEmail'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $sql .= 'Email ="' . $_POST['AuthorEmail'] . '"';
        $multipleWheres = true;
    }
    
    if (isset($_POST['EventName']) && $_POST['EventName'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= ' EventName LIKE "%' . $_POST['EventName'] . '%"';
    }
    if (isset($_POST['Sport']) && $_POST['Sport'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= ' Sport = "' . $_POST['Sport'] . '"';
    }
    
    if (isset($_POST['DateCreatedStart']) && isset($_POST['DateCreatedEnd']) && $_POST['DateCreatedStart'] != "" && $_POST['DateCreatedEnd'] != "" && $_POST['DateCreatedStart'] <= $_POST['DateCreatedEnd']) {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= '(DateTimeCreated BETWEEN "' . $_POST['DateCreatedStart'] . ' 00:00:00' . '" AND "' . $_POST['DateCreatedEnd'] . ' 11:59:99")';
    }
    if (isset($_POST['EventTimeStart']) && isset($_POST['EventTimeEnd']) && $_POST['EventTimeStart'] != "" && $_POST['EventTimeEnd'] != "" && $_POST['EventTimeStart'] <= $_POST['EventTimeEnd']) {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= '(EventTime BETWEEN "' . $_POST['EventTimeStart'] . '" AND "' . $_POST['EventTimeEnd'] . '")';
    }
    if (isset($_POST['EventDateStart']) && isset($_POST['EventDateEnd']) && $_POST['EventDateStart'] != "" && $_POST['EventDateEnd'] != "" && $_POST['EventDateStart'] <= $_POST['EventDateEnd']) {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= '(EventDate BETWEEN "' . $_POST['EventDateStart'] . '" AND "' . $_POST['EventDateEnd'] . '")';
    }
    if (isset($_POST['AgeMin']) && $_POST['AgeMin'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= 'AgeMin >= ' . $_POST['AgeMin'];
    }
    if (isset($_POST['AgeMax']) && $_POST['AgeMax'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= 'AgeMax <= ' . $_POST['AgeMax'];
    }
    if (isset($_POST['MinUserRating']) && $_POST['MinUserRating'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= 'MinUserRating >= ' . $_POST['MinUserRating'];
    }
    
    if (isset($_POST['Gender']) && $_POST['Gender'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= "Gender = '" . $_POST['Gender'] . "'";
    }
    if (isset($_POST['Gender']) && $_POST['Gender'] != "") {
        $sql .= CheckMultipleWheres($multipleWheres);
        $multipleWheres = true;
        $sql .= "Gender = '" . $_POST['Gender'] . "'";
    }
    
    $sql .= ";";
    print($sql);
    $result = SQLQuery($sql);
    
    $resultData = "";
    if ($result != FALSE && $result->num_rows > 0) {
        // output data of each row
        while ($row = $result->fetch_assoc()) {
            
            $EventID    = $row["EventID"];
            $rsvpsql    = 'SELECT * FROM EventRSVPs WHERE EventID = "' . $EventID . '"';
            $RSVPResult = SQLQuery($rsvpsql);
            if ($RSVPResult->num_rows < $row["PlayerNumber"] || $_POST['OnlyNotFull'] != 'true') {
                
                $resultData .= '#,EventID::' . $row["EventID"];
                $resultData .= ',AuthorName::' . $row["AuthorName"];
                $resultData .= ',"Email::' . $row["Email"];
                $resultData .= ',EventName::' . $row["EventName"];
                $resultData .= ',Sport::' . $row["Sport"];
                $resultData .= ',Location::' . $row["Location"];
                $resultData .= ',Latitude::' . $row["Latitude"];
                $resultData .= ',Longitude::' . $row["Longitude"];
                $resultData .= ',DateTimeCreated::' . $row["DateTimeCreated"];
                $resultData .= ',EventDateTime::' . $row["EventDateTime"];
                $resultData .= ',AgeMax::' . $row["AgeMax"];
                $resultData .= ',AgeMin::' . $row["AgeMin"];
                $resultData .= ',MinUserRating::' . $row["MinUserRating"];
                $resultData .= ',PlayerNumber::' . $row["PlayerNumber"];
                $resultData .= ',IsPrivate::' . $row["IsPrivate"];
                $resultData .= ',Gender::' . $row["Gender"];
            }
            
        }
    }
    print($resultData);
    
}
// ------ --------------------------------------------------------------
// Checks the boolean value $multipleWheres to see if it is set to true.
//  If true (i.e. there has already been a Where clause), it appends an
//  AND instead of a WHERE. 
// --------------------------------------------------------------------
function CheckMultipleWheres($multipleWheres)
{
    $sql = "";
    if ($multipleWheres) {
        $sql .= " AND ";
    } else {
        $sql .= " WHERE ";
    }
    return $sql;
}
?>