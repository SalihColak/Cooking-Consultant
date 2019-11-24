<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$name = $_POST['name'];
$schritte = $_POST['schritte'];
$menge = $_POST['menge'];
$kochzeit = $_POST['kochzeit'];
$art = $_POST['art'];
$anlass = $_POST['anlass'];
$praeferenz = $_POST['praeferenz'];
$bild = $_POST['bild'];
$beschreibung = $_POST['beschreibung'];

$sql = "Insert into rezept (name, schritte, menge, kochzeit, art, anlass, praeferenz, bild, beschreibung) values ($'name', $'schritte', $'menge', $'kochzeit', $'art', $'anlass', $'praeferenz', $'bild', $'beschreibung')";
 
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