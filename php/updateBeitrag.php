<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$beitid = $_GET['beitid'];
$titel = $_GET['titel'];
$kategorie = $_GET['kategorie'];
$inhalt = $_GET['inhalt'];

$sql = "UPDATE beitrag SET titel = '$titel', kategorie = '$kategorie', inhalt = '$inhalt' where beitid = $beitid";

if ($result = mysqli_query($con, $sql))
{
	echo "Erfolgreiches Update";
}else
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}
 
// Close connections
mysqli_close($con);
?>