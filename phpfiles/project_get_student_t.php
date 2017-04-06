<?php
   $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $response=array();
   $username = $_GET['student_id'];
   //echo $username;
   //$password = $_GET['password'];
   //$name = $_GET['student_name'];
   $result = mysqli_query($con,"SELECT * FROM temp_student where student_id_t='$username'");
   if(!empty($result))
   {
		$result = mysqli_fetch_array($result);
		$product = array();
        $product["student_name"] = $result["student_name_t"];
        $product["passport"] = $result["passport_t"];
		$product["student_id"] = $result["student_id_t"];
		$product["home_no"] = $result["home_no_t"];
		$product["mobile_no"] = $result["mobile_no_t"];
		$product["semester_enroll"] = $result["semester_enroll_t"];
		$product["intake"] = $result["intake_t"];
		$product["password"] = $result["password_t"];

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