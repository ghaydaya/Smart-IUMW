<?php
$con=mysqli_connect("localhost","root","","testdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 

//$student_id = $_GET['student_id'];
$student_id = 'super_temp';
//$emailAddress = $_GET['emailaddress'];

// ads
//$fullName = 'a';
//$userName = 'b';
//$passWord = 'c';
//$phoneNumber = 'd';
//$emailAddress = 'e';

 
$result = mysqli_query($con,"DELETE FROM student WHERE student_id = '$student_id' ");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>