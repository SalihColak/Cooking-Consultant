<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$zutid = $_GET['zutid'];
$name = $_GET['name'];
$einheit = $_GET['einheit'];
$bild = $_GET['bild'];

//Example: http://localhost/updateZutat.php?zutid=5&name=upName&einheit=upEinheit&bild=upBild
$sql = "UPDATE zutat SET name = '$name', einheit = '$einheit', bild = '$bild' WHERE zutid = $zutid";

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