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
   $result = mysqli_query($con,"SELECT * FROM student where student_id='$username'");
   if(!empty($result))
   {
		$result = mysqli_fetch_array($result);
		$product = array();
        $product["student_name"] = $result["student_name"];
        $product["passport"] = $result["passport"];
		$product["student_id"] = $result["student_id"];
		$product["home_no"] = $result["home_no"];
		$product["mobile_no"] = $result["mobile_no"];
		$product["semester_enroll"] = $result["semester_enroll"];
		$product["intake"] = $result["intake"];
		$product["password"] = $result["password"];

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