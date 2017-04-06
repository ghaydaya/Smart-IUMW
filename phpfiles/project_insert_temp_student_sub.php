
   <?php
$con=mysqli_connect("localhost","root","","testdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$ss_code=$_GET['sscode'];
$sub_code=$_GET['sname'];
$student_id=$_GET['student_id'];


 
$result = mysqli_query($con,"INSERT INTO temp_student_subject (ss_code_t, sub_code_t, student_id_t) 
          VALUES ('$ss_code', '$sub_code', '$student_id')");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>