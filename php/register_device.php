<?php
require('connection.php');
$fcm_token=$_POST['fcm_token'];
$identity = $_POST['identity'];

$idar = str_split($identity,2);
$position=$idar[0];
$department = $idar[3];
$origin = 'p';
    
if($idar[2]=="SS"){
    $origin = 's';
    $identity = $identity.time();
    }
if($idar[2]=="5A"||$idar[2]=="5a"){
    $posint = intval($position);
    $position = $posint-1;
       //echo $position;
}

$sql = "INSERT INTO `recipient_list`(`fcm_token`,`identity`,`origin`,`department`,`position`) VALUES('$fcm_token','$identity','$origin','$department','$position') ON DUPLICATE KEY UPDATE fcm_token = VALUES(fcm_token) ";
$insertion = mysqli_query($db,$sql);

echo $insertion;
echo mysqli_error($db);
