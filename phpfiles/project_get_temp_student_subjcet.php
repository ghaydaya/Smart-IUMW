<?php
 
 $con=mysqli_connect("localhost","root","","testdb");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
   
   $response=array();
   $result = mysqli_query($con,"SELECT * FROM temp_student_subject");
        if (mysqli_num_rows($result) > 0) {
 
			$response["subject"]=array();
            while($row = mysqli_fetch_array($result)){
 
				$product = array();
				$product["sub_code"] = $row["sub_code_t"];
				$product["sub_name"] = $row["ss_code_t"];
				$product["student_id"]= $row["student_id_t"];
				array_push($response["subject"],$product);
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