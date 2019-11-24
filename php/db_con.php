<?php

    function openCon()
    {
        $dbname = 'syp14';
        $dbhost = 'localhost';
        $dbuser = 'root';
        $dbpass = '';

        $conn = new mysqli($dbhost,$dbuser,$dbpass,$dbname) or die("Connect failed: %s\n". $conn -> error);

        return $conn;
    }

    function closeCon($conn)
    {
        $conn -> close();
    }
?>