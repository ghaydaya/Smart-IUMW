<?php
   $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $response=array();
   $username = $_GET['student_id'];
   $password = $_GET['password'];

   //$username='ID';
   //$password='123';
   
   $result = mysqli_query($con,"SELECT * FROM student where student_id='$username' and password='$password'");
   if(!empty($result))
   {
		//$result = mysqli_fetch_array($result);
		//$product = array();
        //$product["student_name"] = $result["student_name_t"];
        //$product["passport"] = $result["passport_t"];
		//$product["student_id"] = $result["student_id_t"];
		//$product["home_no"] = $result["home_no_t"];
		//$product["mobile_no"] = $result["mobile_no_t"];
		//$product["semester_enroll"] = $result["semester_enroll_t"];
		//$product["intake"] = $result["intake_t"];
		//$product["password"] = $result["password_t"];

	    $response["success"] = 1;
            // user node
            //$response["products"] = array();
 
            //array_push($response["products"], $product);
 
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