<?php
require('connection.php');

$identity = $_POST['identity'];

$idar = str_split($identity,2);
$position=$idar[0];
$department = $idar[3];
$origin = 'p';
    
if($idar[2]=="SS"){
    $origin = 's';
    }
if($idar[2]=="5A"||$idar[2]=="5a"){
    $posint = intval($position);
    $position = $posint-1;
       
}
$query = "SELECT * from `board_items` WHERE origin LIKE '%$origin%' && department LIKE '%$department%' && position LIKE '%$position%'";
//echo $query;


$raw = mysqli_query($db,$query);
//echo mysqli_num_rows($raw);
echo json_encode(mysqli_fetch_all($raw));
