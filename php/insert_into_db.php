<?php
require('connection.php');

$title = mysqli_real_escape_string($db,$_POST['title']);
$description = mysqli_real_escape_string($db,$_POST['description']);
$image_link = mysqli_real_escape_string($db,$_POST['image_link']);
$content = mysqli_real_escape_string($db,$_POST['content']);
$origin = mysqli_real_escape_string($db,$_POST['origin']);
$department = mysqli_real_escape_string($db,$_POST['department']);
$position = mysqli_real_escape_string($db,$_POST['position']);

date_default_timezone_set("Asia/Kolkata");
$id = time();
$date = date('Y-m-d');


$res = mysqli_query($db,"INSERT INTO board_items(`id`, `title`, `description`, `image_link`, `content`, `date`, `origin`, `department`, `position`) VALUES ('$id','$title','$description','$image_link','$content','$date','$origin','$department','$position')");
echo mysqli_error($db);
if($res){
echo $id;
}
else{
    
 
    echo "failure";
    
}




