<?php 
$db_name = 'goadb';
$db_user = 'root';
$db_pass = 'root';
$db_host = 'localhost'; 

$response = array();
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

$conn = mysqli_connect($db_host, $db_user, $db_pass,$db_name,"3308");

$review = $input['review'];
$source = $input['source'];
$user_Name = $input['name'];


if (!$conn ) 
{
    $response["status"] = 3;
	$response["message"] = "Some Error";	
}
else
{
		$query = "INSERT INTO review(name,reviews,source) VALUES ('$user_Name','$review','$source')";		
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