<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$userid = $_GET['userid'];
$einkid = $_GET['einkid'];

//Example: http://localhost/insertBenutzer2Einkaufsliste.php?userid=1&einkid=1
$sql = "INSERT INTO benutzer2einkaufsliste (userid, einkid) VALUES ($userid, $einkid)";
 
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