<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$ben2einid = $_POST['ben2einid'];

$sql = "delete from benutzer2einkaufsliste where ben2einid = $'ben2einid'";
 
// Confirm there are results
if ($result = mysqli_query($con, $sql))
{
	echo Erfolgreich geloescht
}else
{
	echo Fehler beim Loeschen
}
 
// Close connections
mysqli_close($con);
?>