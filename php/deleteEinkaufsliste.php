<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$einkid = $_POST['einkid'];

$sql = "delete from einkaufsliste where einkid = $'einkid'";
 
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