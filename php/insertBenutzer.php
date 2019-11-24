<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$titel = $_POST['titel'];
$name = $_POST['name'];
$vorname = $_POST['vorname'];
$geschlecht = $_POST['geschlecht'];
$geburtsdatum = $_POST['geburtsdatum'];
$email = $_POST['email'];
$passwort = $_POST['passwort'];
$admin = $_POST['admin'];

// Select all of our stocks from table 'stock_tracker'
$sql = "Insert into benutzer (titel, name, vorname, geschlecht, geburtsdatum, email, passwort, admin) values ('$titel', $'name', $'vorname', $'geschlecht', $'geburtsdatum', $'email', $'passwort', $'admin'";
 
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