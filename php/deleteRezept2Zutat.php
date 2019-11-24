<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$rez2zutid = $_POST['rez2zutid'];

$sql = "delete from rezept2zutat where rez2zutid = $'rez2zutid'";
 
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