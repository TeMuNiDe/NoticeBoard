<?php
require('connection.php');



$id = $_POST['id'];
 
mysqli_query($db,"delete from `board_items` WHERE `id` LIKE '$id'");
  $url = 'https://fcm.googleapis.com/fcm/send';
  $fields = array (
            'to' =>"/topics/all",
            'data' => array (
                'operation' => 'delete',
                    'id' => $id
            )
    );

 $fields = json_encode ( $fields );

    $headers = array (
            'Authorization: key=' . "AAAA1AWbMLU:APA91bHS_-ghNjwLs2gftqZntObe-M0Hj9f2VHQr9KLFSh_eT88KN_8vAiLSwJzf_ePy_rnu-3ZOKEVttvUEjKNXqQZgDLkx6uUmoiPJ29WI9D7ia_XCE1BAy44INT3c4-YHM_u6BWy6HL00VoAQGfR_sXnzjOA1Vg",
            'Content-Type: application/json'
    );

    $ch = curl_init ();
    curl_setopt ( $ch, CURLOPT_URL, $url );
    curl_setopt ( $ch, CURLOPT_POST, true );
    curl_setopt ( $ch, CURLOPT_HTTPHEADER, $headers );
    curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, true );
    curl_setopt ( $ch, CURLOPT_POSTFIELDS, $fields );

    $result = curl_exec ( $ch );
    echo $result;
echo "Deletion Success";
    curl_close ( $ch );
mysqli_close($db);