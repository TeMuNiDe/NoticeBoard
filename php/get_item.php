<?php
require('connection.php');
$id = $_POST['id'];
$items = mysqli_query($db,"SELECT * FROM board_items WHERE id LIKE '$id' ");
echo mysqli_error($db);
$items_arr = mysqli_fetch_all($items);
echo json_encode($items_arr);