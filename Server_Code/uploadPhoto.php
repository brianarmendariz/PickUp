<?php
  $filename="abc.xyz";
  $fileData=file_get_contents('php://input');
  $fhandle=fopen($filename, 'wb');
  fwrite($fhandle, $fileData);
  fclose($fhandle);
  echo("Done uploading");
?>
