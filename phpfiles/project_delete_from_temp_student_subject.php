<?php
$con=mysqli_connect("localhost","root","","testdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 

$student_id = $_GET['student_id'];
$code=$_GET['code'];
//$student_id = 'z';
//$emailAddress = $_GET['emailaddress'];

// ads
//$fullName = 'a';
//$userName = 'b';
//$passWord = 'c';
//$phoneNumber = 'd';
//$emailAddress = 'e';

 
$result = mysqli_query($con,"DELETE FROM temp_student_subject WHERE student_id_t = '$student_id' and sub_code_t='$code' ");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>