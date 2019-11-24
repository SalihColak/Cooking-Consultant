<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$userid = $_POST['userid'];
$einkid = $_POST['einkid'];

$sql = "Insert into benutzer2einkaufsliste (userid, einkid) values ('$userid', $'einkid')";
 
// Confirm there are results
if ($result = mysqli_query($con, $sql))
{
	echo Erfolgreich eingefuegt
}else
{
	echo Fehler beim Einfuegen
}
 
// Close connections
mysqli_close($con);
?>