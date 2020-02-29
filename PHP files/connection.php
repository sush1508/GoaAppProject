<?php
$db_host = 'localhost';
    $db_user = 'root';
    $db_pass = 'root';
    $db_name= 'goadb';

    $con = mysqli_connect($db_host, $db_user, $db_pass, $db_name,"3308");
    if(!$con)
{
    echo "Connection error".mysqli_connect_error();
}
else
{
    //echo "Connection established";
}

?>