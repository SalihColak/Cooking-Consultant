<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$rezid = $_GET['rezid'];
$name = $_GET['name'];
$schritte = $_GET['schritte'];
$menge = $_GET['menge'];
$kochzeit = $_GET['kochzeit'];
$art = $_GET['art'];
$anlass = $_GET['anlass'];
$praeferenz = $_GET['praeferenz'];
$bild = $_GET['bild'];
$beschreibung = $_GET['beschreibung'];

//Example: http://localhost/updateRezept.php?rezid=2&name=upName&schritte=upSchritte&menge=upMenge&kochzeit=100&art=BRUNCH&anlass=FEIER&praeferenz=DEUTSCH&bild=upBild&beschreibung=upBeschreibung
$sql = "UPDATE rezept SET name = '$name', schritte = '$schritte', menge = '$menge', kochzeit = $kochzeit, art = '$art', anlass = '$anlass', praeferenz = '$praeferenz', bild = '$bild', beschreibung = '$beschreibung' WHERE rezid = $rezid";

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