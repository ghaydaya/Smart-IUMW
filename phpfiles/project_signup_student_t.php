<?php
$con=mysqli_connect("localhost","root","","testdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$student_name = $_GET['student_name'];
$passport = $_GET['passport'];
$student_id = $_GET['student_id'];
$home_no = $_GET['home_no'];
$mobile_no = $_GET['mobile_no'];
$semester_enroll = $_GET['semester_enroll'];
$intake = $_GET['intake'];
$password = $_GET['password'];

//$emailAddress = $_GET['emailaddress'];

// ads
//$fullName = 'a';
//$userName = 'b';
//$passWord = 'c';
//$phoneNumber = 'd';
//$emailAddress = 'e';

 
$result = mysqli_query($con,"INSERT INTO temp_student (student_name_t,passport_t,student_id_t,home_no_t,mobile_no_t,semester_enroll_t,intake_t,password_t) 
          VALUES ('$student_name','$passport','$student_id','$home_no','$mobile_no','$semester_enroll','$intake','$password')");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>