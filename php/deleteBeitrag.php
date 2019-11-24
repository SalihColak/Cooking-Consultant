<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$beitid = $_POST['beitid'];

$sql = "delete from beitrag where beitid = $'beitid'";
 
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