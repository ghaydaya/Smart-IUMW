<?php
   $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }

   $response=array();
   $username = $_GET['username'];
   $password = $_GET['password'];
   $role=$_GET['role'];

   //$username='ID';
   //$password='123';
   
   $result = mysqli_query($con,"SELECT * FROM users where username='$username' and password='$password' and role='$role'");
   if(!empty($result))
   {
	    $response["success"] = 1;
        echo json_encode($response);
   }
   else{
      //echo $data;
	  $response["success"]=0;
	echo json_encode($response);
   }
   
   mysqli_close($con);
?>