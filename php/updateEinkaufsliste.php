<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$einkid = $_GET['einkid'];
$zustand = $_GET['zustand'];
$userid = $_GET['userid'];
$rezid = $_GET['rezid'];
$menge = $_GET['menge'];

//Example: http://localhost/updateEinkaufsliste.php?einkid=6&zustand=upZustand&userid=3&rezid=2
$sql = "UPDATE einkaufsliste SET zustand = '$zustand', userid = $userid, rezid = $rezid, menge = $menge WHERE einkid = $einkid";

if ($result = mysqli_query($con, $sql))
{
	echo "Erfolgreiches Update";
}else
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}
 
// Close connections
mysqli_close($con);
?>