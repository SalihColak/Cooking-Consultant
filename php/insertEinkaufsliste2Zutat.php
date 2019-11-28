<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$einkid = $_GET['einkid'];
$zutid = $_GET['zutid'];

//Example: http://localhost/insertEinkaufsliste2Zutat.php?einkid=1&zutid=2
$sql = "INSERT INTO einkaufsliste2zutat (einkid, zutid) VALUES ($einkid, $zutid)";
 
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