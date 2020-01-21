<?php

require "vendor/autoload.php";
use Geodesy\Location\LatLong;
use Geodesy\Distance\VincentyFormula;

$db_name = 'goadb';
$db_user = 'root';
$db_pass = 'root';
$db_host = 'localhost';
$result = array();
$conn = mysqli_connect($db_host, $db_user, $db_pass,$db_name,"3308");
$inputJSON = file_get_contents("php://input");
$input = json_decode($inputJSON,TRUE);
$lat1=$input[0]['lat'];
$long1=$input[0]['lng'];
$lat2=0;
$long2=0;
$loc1 = new LatLong();
$loc1->setLatitude($lat1);
$loc1->setLongitude($long1);
if(!$conn)
{
	array_push($result, array('message'=>"Error while fetching data",'status'=>"1"));
}	
else
{
	$query = "SELECT * FROM latlong";  
	$qresult = mysqli_query($conn,$query);
	if($qresult)
	{
		if(mysqli_num_rows($qresult)<=0)
		{
			array_push($result, array('message'=>"No data"));
		}
		else
		{
			while($row=mysqli_fetch_array($qresult)){
				$lat2 = $row[1];
				$long2 = $row[2];
				$loc2 = new LatLong;
				$loc2->setLatitude($lat2);
				$loc2->setLongitude($long2);
				$distance = new VincentyFormula($loc1,$loc2);
				$d = $distance->getDistance();
				if($d<=5000)
				{
					array_push($result, array(
					'message'=>"Places fetched",
					'place'=>$row[0],
					'latitude'=>$row[1],
					'longitude'=>$row[2]));
				}				
			}
		}
	}
	echo json_encode($result);			
	mysqli_close($conn);
}	
?>
