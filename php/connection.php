<?php

error_reporting(~E_WARNING);
$db = mysqli_connect("jntu3574113699.db.3574113.hostedresource.com","jntu3574113699","lp}Sl415x","jntu3574113699");
function mysqli_fetch_all($result)
   { 
    $data = [];
while ($row = $result->fetch_assoc()) {
    $data[] = $row;
}
    return $data;
}

?>