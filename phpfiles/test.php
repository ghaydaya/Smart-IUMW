<?php
   $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $username = $_GET['student_id'];
   $password = $_GET['password'];
   //$name = $_GET['student_name'];
   $result = mysqli_query($con,"SELECT * FROM student where student_id='$username' 
      and password='$password'");
   $result = mysqli_fetch_array($result);
	$product = array();
        $product["student_name"] = $result["student_name"];
        $product["password"] = $result["password"];
   

	    $response["success"] = 1;
            // user node
            $response["product"] = array();
 
            array_push($response["product"], $product);
 
            // echoing JSON response
            echo json_encode($response);

   if($data){
      //echo $data;
	echo json_encode($data);
   }
   mysqli_close($con);
?>