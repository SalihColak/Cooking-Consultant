<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$titel = $_POST['titel'];
$kategorie = $_POST['kategorie'];
$inhalt = $_POST['inhalt'];

$sql = "Insert into beitrag (titel, kategorie, inhalt) values ('$titel', $'kategorie', $'inhalt')";
 
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