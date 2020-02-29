<?php
require "connection.php";

$result = array();
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON,TRUE);    
$place= $input['placename'];  
    
    

$sql = "select * from image_tb where location_name='$place'";
	
	$res = mysqli_query($con,$sql);
	//$result['error'] = "no url";
    //$infomation ="";
	
	$url = "http://192.168.137.95/GoaApp/Images/";//path of the folder where images are stored

    //echo "Rows".mysqli_num_rows($res);
	while($row = mysqli_fetch_array($res)){
        
        $result['location_name'] = $row['location_name'];
	       $result['url'] = $url.$row['image_name'];
        $result['info'] = $row["info"]; 
        
       
    }
echo json_encode($result);
mysqli_close($con);	
?>