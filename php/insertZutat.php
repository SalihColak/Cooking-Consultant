<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$name = $_POST['name'];
$einheit = $_POST['einheit'];
$bild = $_POST['bild'];

$sql = "Insert into zutat (name, einheit, bild) values ('$name', $'einheit', $'bild')";
 
// Confirm there are results
if ($result = mysqli_query($con, $sql))
{
	echo Erfolgreich eingefuegt
}else
{
	echo Fehler beim Einfuegen
}
 
// Close connections
mysqli_close($con);
?>