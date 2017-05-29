<?php

$title = $_POST['title'];
$description = $_POST['description'];
$content = $_POST['content'];
$college = $_POST['college'];  
$department = $_POST['department'];
$year = $_POST['year'];
$date = Date("Y-m-d");
$date = Date("Y-m-d");
require("cnxn.php");
echo mysqli_error($mysqli);
echo mysqli_error($mysqli);

$res = mysqli_query($mysqli,"INSERT INTO `noticeboard`(`title`,`description`, `content`, `college`, `department`, `year`, `date`) VALUES ('$title','$description','$content','$college','$department',$year,'$date')");

echo mysqli_error($mysqli);
$forid = mysqli_query($mysqli,"SELECT * FROM `noticeboard` WHERE `title` LIKE '$title' AND `date` LIKE '$date'");

$temp = mysqli_fetch_assoc($forid)['id'].".jpg";
$uploaded = $_FILES['image']['tmp_name'];
    if(@getimagesize($uploaded)){
        
          move_uploaded_file($uploaded,"/noticeboard/uploads/".$temp);
    }

header("location:http://jntuaceasa.com/?page_id=263")  ;
?>    