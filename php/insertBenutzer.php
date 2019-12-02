<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$titel = $_GET['titel'];
$name = $_GET['name'];
$vorname = $_GET['vorname'];
$geschlecht = $_GET['geschlecht'];
$geburtsdatum = $_GET['geburtsdatum'];
$email = $_GET['email'];
$passwort = $_GET['passwort'];
$admin = $_GET['admin'];

/** Example: http://localhost/insertBenutzer.php?titel=Herr&name=name&vorname=vorname&geschlecht=m&geburtsdatum=1900-01-01&email=email&passwort=pass&admin=0
*
*	Error bei duplicate Email:
*
*	Fehler beim Ausfuehren von INSERT INTO benutzer (titel, name, vorname, geschlecht, geburtsdatum, email, passwort, admin) VALUES ('Herr', 'name', 'vorname', 'm', '1900-01-01', 'email', 'pass', 0).
*	Duplicate entry 'email' for key 'email'
*/
$sql = "INSERT INTO benutzer (titel, name, vorname, geschlecht, geburtsdatum, email, passwort, admin) VALUES ('$titel', '$name', '$vorname', '$geschlecht', '$geburtsdatum', '$email', '$passwort', $admin)";
 
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