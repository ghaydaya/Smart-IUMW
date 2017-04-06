
   <?php
$con=mysqli_connect("localhost","root","","testdb");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
 $pid=$_GET['id'];
$name=$_GET['sub_name'];
//$code='323';//$_GET['sub_code'];
$credit=$_GET['sub_credit'];

 
$result = mysqli_query($con,"UPDATE subject SET sub_name = '$name', sub_credit = '$credit' WHERE sub_code= $pid");
 
if($result == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>