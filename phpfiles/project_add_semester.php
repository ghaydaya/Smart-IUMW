
   <?php
$con=mysqli_connect("localhost","root","","testdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$sem_num=$_GET['sem_num'];
$sem_session=$_GET['sem_session'];
$sem_code=$_GET['sem_code'];

 
$result = mysqli_query($con,"INSERT INTO semester (sem_number, sem_session, sem_code) 
          VALUES ('$sem_num', '$sem_session', '$sem_code')");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>