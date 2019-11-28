<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$rezid = $_GET['rezid'];

/** Example: http://localhost/getZutatByRezeptID.php?rezid=2
*	Returned alle Zutaten, die in einem Rezept enthalten sind
*/
$sql = "SELECT * FROM zutat WHERE zutid IN (SELECT zutid FROM rezept2zutat WHERE rezid = $rezid)";
 
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