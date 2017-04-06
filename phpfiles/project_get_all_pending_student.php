<?php
 
 $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
   
   $response=array();
    $result = mysqli_query($con,"SELECT * FROM temp_student");
        if (mysqli_num_rows($result) > 0) {
 
			$response["products"]=array();
            while($row = mysqli_fetch_array($result)){
 
				$product = array();
				$product["student_name"] = $row["student_name_t"];
				$product["student_id"] = $row["student_id_t"];
				array_push($response["products"],$product);
				//array_push($response["products"], $product);
			}
			
     
            
	    // success
            $response["success"] = 1;
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";
 
            // echo no users JSON
            echo json_encode($response);
        }
mysqli_close($con);
?>