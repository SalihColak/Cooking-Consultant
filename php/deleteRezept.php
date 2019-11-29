<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$rezid = $_GET['rezid'];

/**	Example: http://localhost/deleteRezept.php?rezid=1
*	Löscht alle Verbindungen des Rezepts zu Benutzern
*	Löscht alle Verbindungen von Einkaufslisten für dieses Rezept zu Benutzern
*	Löscht alle Verbindungen von Einkaufslisten für dieses Rezept zu Zutaten
*	Löscht alle Einkaufslisten für dieses Rezept
*	Löscht alle Verbindungen des Rezepts zu Zutaten
*	Löscht das Rezept
*/
$sql = "DELETE FROM benutzer2rezept WHERE rezid = $rezid";
if (!($result = mysqli_query($con, $sql)))
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM benutzer2einkaufsliste WHERE einkid IN (SELECT einkid FROM einkaufsliste WHERE rezid = $rezid)";
if (!($result = mysqli_query($con, $sql)))
{
	echo "<br>" . "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM einkaufsliste2zutat WHERE einkid IN (SELECT einkid FROM einkaufsliste WHERE rezid = $rezid)";
if (!($result = mysqli_query($con, $sql)))
{
	echo "<br>" . "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM einkaufsliste WHERE rezid = $rezid";
if (!($result = mysqli_query($con, $sql)))
{
	echo "<br>" . "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM rezept2zutat WHERE rezid = $rezid";
if (!($result = mysqli_query($con, $sql)))
{
	echo "<br>" . "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM rezept WHERE rezid = $rezid";
 
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