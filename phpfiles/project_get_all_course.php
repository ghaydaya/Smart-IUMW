<?php
   $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $response=array();
   $username =$_GET['student_id'];
   //echo $username;
   //$password = $_GET['password'];
   //$name = $_GET['student_name'];
   $result = mysqli_query($con,"SELECT * FROM student_subject where student_id='$username'");
   if (mysqli_num_rows($result) > 0) {
 
			$response["course"]=array();
            while($row = mysqli_fetch_array($result)){
 
				$product = array();
				$product["ss_code"] = $row["ss_code"];
				$product["sub_code"] = $row["sub_code"];
				$product["student_id"] = $row["student_id"];
				array_push($response["course"],$product);
				//array_push($response["products"], $product);
			}
			
     
            
	    // success
            $response["success"] = 1;
            // echoing JSON response
            echo json_encode($response);
        }
   else{
      //echo $data;
	  $response["success"]=0;
	echo json_encode($response);
   }
   
   mysqli_close($con);
?>