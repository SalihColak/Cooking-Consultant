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

/**	http://localhost/getRezeptByArtAnlassPraeferenz.php?art=%27SNACK%27,%20%27BRUNCH%27&anlass=%27FEIER%27,%20%27GEBURTSTAG%27&praeferenz=%27DEUTSCH%27,%20%27ASIATISCH%27
*	Suche fuer die App: Nach Art, Anlass und Praeferenz
*	Einfache Anfuehrungszeichen muessen selbst angegeben werden!
*/
$sql = "SELECT * FROM rezept WHERE art IN ($art) AND anlass IN ($anlass) AND praeferenz IN ($praeferenz)";
 
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