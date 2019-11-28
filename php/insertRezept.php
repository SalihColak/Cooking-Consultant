<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$name = $_GET['name'];
$schritte = $_GET['schritte'];
$menge = $_GET['menge'];
$kochzeit = $_GET['kochzeit'];
$art = $_GET['art'];
$anlass = $_GET['anlass'];
$praeferenz = $_GET['praeferenz'];
$bild = $_GET['bild'];
$beschreibung = $_GET['beschreibung'];

//Example: http://localhost/insertRezept.php?name=name&schritte=schritte&menge=menge&kochzeit=10&art=BRUNCH&anlass=ESSEN%20ZU%20ZWEIT&praeferenz=ASIATISCH&bild=bild&beschreibung=beschreibung
$sql = "INSERT INTO rezept (name, schritte, menge, kochzeit, art, anlass, praeferenz, bild, beschreibung) VALUES ('$name', '$schritte', '$menge', $kochzeit, '$art', '$anlass', '$praeferenz', '$bild', '$beschreibung')";
 
if ($result = mysqli_query($con, $sql))
{
	echo "Erfolgreich eingefuegt!" . "<br>" . "Die ID lautet: " . mysqli_insert_id($con);
}else
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}
 
// Close connections
mysqli_close($con);
?>