<?php
 include 'db_con.php';
 $con = openCon();
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$art = $_GET['art'];
$anlass = $_GET['anlass'];
$praeferenz = $_GET['praeferenz'];

/**	Example http://localhost/getRezeptByArtAnlassPraeferenz.php?art=SNACK&anlass=FEIER&praeferenz=INDISCH
*	Suche fuer die App: Nach Art, Anlass und Praeferenz
*/
$sql = "SELECT * FROM rezept WHERE art = '$art' AND anlass = '$anlass' AND praeferenz = '$praeferenz'";
 
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