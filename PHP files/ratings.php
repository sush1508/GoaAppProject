<?php 
$db_name = 'goadb';
$db_user = 'root';
$db_pass = 'root';
$db_host = 'localhost'; 

$response = array();
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

$conn = mysqli_connect($db_host, $db_user, $db_pass,$db_name,"3308");

$user_Name = $input['name'];
$location = $input['location'];
$date = $input['date'];
$time = $input['time'];
$rate1 = $input['rate1'];
$rate2 = $input['rate2'];
$rate3 = $input['rate3'];
$rate4 = $input['rate4'];
$rate5 = $input['rate5'];
$rate6 = $input['rate6'];
$rate7 = $input['rate7'];
$rate8 = $input['rate8'];
$comment = $input['comment'];



if (!$conn ) 
{
    $response["status"] = 3;
	$response["message"] = "Error";	
}
else
{
		$query = "INSERT INTO rating_z VALUES ('$user_Name','$location','$date','$time','$rate1','$rate2','$rate3','$rate4','$rate5','$rate6','$rate7')";		
		$result = mysqli_query($conn,$query);
		
	
		if(!$result)
		{
			$response["status"] = 0;
			$response["message"] = "Error submitting";
		}
		else
		{
			$response["status"] = 1;
			$response["message"] = "Submitted";
			
		}				
				mysqli_close($conn);
}
echo json_encode($response);
?>