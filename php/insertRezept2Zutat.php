<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$rezid = $_GET['rezid'];
$zutid = $_GET['zutid'];

//Example: http://localhost/insertRezept2Zutat.php?rezid=2&zutid=2
$sql = "INSERT INTO rezept2zutat (rezid, zutid) VALUES ($rezid, $zutid)";
 
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