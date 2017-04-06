<?php
   $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $response=array();
   $username =$_GET['student_id'];
   $code =$_GET['name'];
   $result = mysqli_query($con,"SELECT * FROM temp_student_subject where student_id_t='$username' and sub_code_t='$code'");
   if(!empty($result))
   {
		$result = mysqli_fetch_array($result);
		$product = array();
        $product["ss_code"] = $result["ss_code_t"];
        $product["sub_code"] = $result["sub_code_t"];
		$product["student_id"] = $result["student_id_t"];
		

	    $response["success"] = 1;
            // user node
            $response["products"] = array();
 
            array_push($response["products"], $product);
 
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