<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$einkid = $_GET['einkid'];

/** Example: http://localhost/getZutatByEinkaufslisteID.php?einkid=1
*	Returned alle Zutaten, die in einer Einkaufsliste gespeichert sind
*/
$sql = "SELECT * FROM zutat WHERE zutid IN (SELECT zutid FROM einkaufsliste2zutat WHERE einkid = $einkid)";
 
// Confirm there are results
if ($result = mysqli_query($con, $sql))
{
	// We have results, create an array to hold the results
	// and an array to hold the data
	$resultArray = array();
	$tempArray = array();
 
	// Loop through each result
	while($row = $result->fetch_object())
	{
		// Add each result into the results array
		$tempArray = $row;
	    array_push($resultArray, $tempArray);
	}
 
	// Encode the array to JSON and output the results
	echo json_encode($resultArray);
}else
{
	echo "Fehler beim Ausfuehren von $sql." . "<br>" . mysqli_error($con);
}
 
// Close connections
mysqli_close($con);
?>