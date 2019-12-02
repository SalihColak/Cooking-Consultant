<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$userid = $_GET['userid'];

/**	Example: http://localhost/deleteBenutzer.php?userid=1
*	Löscht alle Verbindungen des Benutzers zu Rezepten
*	Löscht alle Einkaufslisten des Benutzers
*	Löscht den Benutzer
*/
$sql = "DELETE FROM benutzer2rezept WHERE userid = $userid";
if (!($result = mysqli_query($con, $sql)))
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM einkaufsliste WHERE userid = $userid";
if (!($result = mysqli_query($con, $sql)))
{
	echo "<br>" . "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM benutzer WHERE userid = $userid";

if ($result = mysqli_query($con, $sql))
{
	echo "Erfolgreich geloescht!";
}else 
{
	echo "<br>" . "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}
 
 
// Close connections
mysqli_close($con);
?>