
   <?php
$con=mysqli_connect("localhost","root","","testdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$username = $_GET['username'];
$password =$_GET['password'];
$role=$_GET['role'];


 
$result = mysqli_query($con,"INSERT INTO users (username, password, role) 
          VALUES ('$username', '$password', '$role')");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>