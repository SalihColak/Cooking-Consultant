<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$einkid = $_GET['einkid'];

/**	Example: http://localhost/deleteEinkaufsliste.php?einkid=4
*	Löscht alle Verbindungen der Einkaufsliste zu Benutzern
*	Löscht alle Verbindungen der Einkaufsliste zu Zutaten
*	Löscht die Einkaufsliste
*/
$sql = "DELETE FROM benutzer2einkaufsliste WHERE einkid = $einkid";
if (!($result = mysqli_query($con, $sql)))
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM einkaufsliste2zutat WHERE einkid = $einkid";
if (!($result = mysqli_query($con, $sql)))
{
	echo "<br>" . "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM einkaufsliste WHERE einkid = $einkid";
 
if ($result = mysqli_query($con, $sql))
{
	echo "Erfolgreich geloescht!";
}else
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}
 
// Close connections
mysqli_close($con);
?>