<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$userid = $_GET['userid'];
$titel = $_GET['titel'];
$name = $_GET['name'];
$vorname = $_GET['vorname'];
$geschlecht = $_GET['geschlecht'];
$geburtsdatum = $_GET['geburtsdatum'];
$email = $_GET['email'];
$passwort = $_GET['passwort'];
$admin = $_GET['admin'];

//Example: http://localhost/updateBenutzer.php?userid=3&titel=Frau&name=upName&vorname=upVorname&geschlecht=w&geburtsdatum=1800-01-01&email=upEmail&passwort=upPasswort&admin=1
$sql = "UPDATE benutzer SET titel = '$titel', name = '$name', vorname = '$vorname', geschlecht = '$geschlecht', geburtsdatum = '$geburtsdatum', email = '$email', passwort = '$passwort', admin = $admin WHERE userid = $userid";

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