<?php 
$db_name = 'goadb';
$db_user = 'root';
$db_pass = 'root';
$db_host = 'localhost'; 

$result = array();

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE); //convert JSON into array

$location=$input[0]['location'];

$conn = mysqli_connect($db_host, $db_user, $db_pass,$db_name,"3308");
//array_push($result, array('message'=>"Connection error"));	
if (!$conn ) 
{
    //$result["status"] = 3;
	//array_push($result, array('message'=>"Connection error"));	
}
else
{
		$query = "SELECT avg(Q1),avg(Q2),avg(Q3),avg(Q4),avg(Q5),avg(Q6),avg(Q7) FROM  rating_z WHERE location = 'Church'";		
		$retval = mysqli_query($conn,$query);
		//echo "esrdfg:".$retval;
		//$rows = mysqli_num_rows($retval);
		if(mysqli_num_rows($retval)<=0)
		{
			//$result["status"] = 0;
			//$result["message"] = "No ";
			//array_push($result, array('message'=>"No such user"));
		}
		else
		{
		    while($row=mysqli_fetch_array($retval)) 
			{ 
				
				array_push($result, array(
					'Q1'=>$row[0],
					'Q2'=>$row[1],
					'Q3'=>$row[2],
					'Q4'=>$row[3],
					'Q5'=>$row[4],
					'Q6'=>$row[5],
					'Q7'=>$row[6]));
				

			}
			//$result['status']=1;
			//$result['message']="Info fetched";
			//echo json_encode($result);
		}
		
			echo json_encode($result);			
				mysqli_close($conn);
}

?>

