<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

//Example: http://localhost/deleteBeitrag.php?beitid=2
$beitid = $_GET['beitid'];

$sql = "DELETE FROM beitrag WHERE beitid = $beitid";
 
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