<?php

    function openCon()
    {
        $dbname = 'syp14';
        $dbhost = 'localhost';
        $dbuser = 'root';
        $dbpass = '';

        $conn = new mysqli($dbhost,$dbuser,$dbpass,$dbname) or die("Connect failed: %s\n". $conn -> error);
		$conn->set_charset("utf8mb4");
		
        return $conn;
    }

    function closeCon($conn)
    {
        $conn -> close();
    }
?>