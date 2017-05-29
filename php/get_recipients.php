<?php
require('connection.php');
$origin_stream = $_POST['origin'];
$department_stream = $_POST['department'];
$position_stream = $_POST['position'];

$query_origin = splithend($origin_stream,1,"origin");
$query_department = splithend($department_stream,2,"department");
$query_position = splithend($position_stream,2,"position");


$query_string =  "($query_origin) AND ($query_department) AND ($query_position)";
$complete_query = "SELECT fcm_token FROM `recipient_list` WHERE $query_string";


//echo $complete_query;
$recipients_raw = mysqli_query($db,$complete_query);
echo mysqli_error($db);

$recipients_array = mysqli_fetch_all($recipients_raw);
$token_stream = "";
foreach($recipients_array as $fcm_token){
    
    foreach($fcm_token as $token)
    $token_stream = $token_stream."\n".$token;
}
echo $token_stream;




function splithend($stream,$block_length,$column){
    $query_bit = "0";

    $split_stream = str_split($stream,$block_length);
    
    foreach($split_stream as $bit){
        
        $query_bit = $query_bit." || "." `$column` LIKE "."'$bit'";
        
    }
    return $query_bit;
}