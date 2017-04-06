<?php
$con=mysqli_connect("localhost","root","","testdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$fullName = $_GET['fullname'];
$userName = $_GET['username'];
$passWord = $_GET['password'];
$phoneNumber = $_GET['phonenumber'];
$emailAddress = $_GET['emailaddress'];

// ads
//$fullName = 'a';
//$userName = 'b';
//$passWord = 'c';
//$phoneNumber = 'd';
//$emailAddress = 'e';

 
$result = mysqli_query($con,"INSERT INTO user (fullname, username, password, phone, email) 
          VALUES ('$fullName', '$userName', '$passWord', '$phoneNumber', '$emailAddress')");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>