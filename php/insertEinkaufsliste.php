<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$zustand = $_POST['zustand'];
$userid = $_POST['userid'];
$rezid = $_POST['rezid'];

$sql = "Insert into einkaufsliste (zustand, userid, rezid) values ('$zustand', '$userid', $'rezid')";
 
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