<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$name = $_GET['name'];
$einheit = $_GET['einheit'];
$bild = $_GET['bild'];

//Example: http://localhost/insertZutat.php?name=name&einheit=einheit&bild=bild
$sql = "INSERT INTO zutat (name, einheit, bild) VALUES ('$name', '$einheit', '$bild')";
 
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