<?php 


if(isset($_POST['uploaded_file']))
{
	$filename = $_POST['name'];
	$file_path = "uploads/$filename.jpg";
	$file = base64_decode($_POST['uploaded_file']);	
	$moved=file_put_contents($file_path, $file);
	if($moved)	
	{
				$db_name = 'goadb';
				$db_user = 'root';
				$db_pass = 'root';
				$db_host = 'localhost'; 
			
				$conn = mysqli_connect($db_host, $db_user, $db_pass,$db_name,"3308");
				if (!$conn ) 
				{
			    	die("Connection failed: " . $conn->connect_error);
				}
				else
				{
					$query = "insert into images(img_name,img_location) values('image1','$file_path')";								
					if($conn->query($query) === TRUE  )
					{						
						$result= "1";
					}
					
				}
		mysqli_close($conn);										
	}
	else{
		$result = "0";
	}
echo ($result);
}

?>