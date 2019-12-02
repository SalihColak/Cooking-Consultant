<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$zutid = $_GET['zutid'];

/**	Example: http://localhost/deleteZutat.php?zutid=3
*  	Löscht alle Verbindungen der Zutat zu Rezepten
*	Löscht die Zutat
*/
$sql = "DELETE FROM rezept2zutat WHERE zutid = $zutid";
if (!($result = mysqli_query($con, $sql)))
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}

$sql = "DELETE FROM zutat WHERE zutid = $zutid";
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