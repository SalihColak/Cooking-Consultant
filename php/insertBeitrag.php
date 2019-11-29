<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$titel = $_GET['titel'];
$kategorie = $_GET['kategorie'];
$inhalt = $_GET['inhalt'];

//Example: http://localhost/insertBeitrag.php?titel=titel&kategorie=kategorie&inhalt=inhalt
$sql = "INSERT INTO beitrag (titel, kategorie, inhalt) VALUES ('$titel', '$kategorie', '$inhalt')";
 
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