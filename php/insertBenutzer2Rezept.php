<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$userid = $_GET['userid'];
$rezid = $_GET['rezid'];

//Example: http://localhost/insertBenutzer2Rezept.php?userid=1&rezid=2
$sql = "INSERT INTO benutzer2rezept (userid, rezid) VALUES ($userid, $rezid)";
 
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