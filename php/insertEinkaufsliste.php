<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$zustand = $_GET['zustand'];
$userid = $_GET['userid'];
$rezid = $_GET['rezid'];
$menge = $_GET['menge'];

//Example: http://localhost/insertEinkaufsliste.php?zustand=zustand&userid=1&rezid=2
$sql = "INSERT INTO einkaufsliste (zustand, userid, rezid, menge) VALUES ('$zustand', $userid, $rezid, $menge)";
 
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