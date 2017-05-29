<?php
require('connection.php');

$id = $_POST['msg_id'];

$recipient_stream = $_POST['recipient_stream'];


    $url = 'https://fcm.googleapis.com/fcm/send';

$rg_array  = explode(",",$recipient_stream);
    $fields = array (
            'registration_ids' =>$rg_array,
            'data' => array (
                'operation' => 'insert',
                    'id' => $id
            )
    );

 $fields = json_encode ( $fields );
//echo $fields;
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

$enc = mysqli_real_escape_string($db,$result);


mysqli_query($db,"INSERT INTO `message_map`(`cast_id`,`msg_id`) VALUES('$enc','$id')");
echo mysqli_error($db);
    echo $result;
    curl_close ( $ch );