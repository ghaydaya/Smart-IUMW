<?php
 
 $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
   
   $response=array();
   $result = mysqli_query($con,"SELECT * FROM semester");
        if (mysqli_num_rows($result) > 0) {
 
			$response["semester"]=array();
            while($row = mysqli_fetch_array($result)){
 
				$product = array();
				$product["sem_number"] = $row["sem_number"];
				$product["sem_session"] = $row["sem_session"];
				array_push($response["semester"],$product);
				//array_push($response["products"], $product);
			}
			
     
            
	    // success
            $response["success"] = 1;
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No semester found";
 
            // echo no users JSON
            echo json_encode($response);
        }
mysqli_close($con);
?>