<?php

include '../inc/dbconfig.php';
include '../inc/member.php';

$mem = new Member($db);

$id = (isset($_POST['id'      ]) && $_POST['id'   ] != '' ) ? $_POST['id'   ] : '';
$email = (isset($_POST['email']) && $_POST['email'] != '' ) ? $_POST['email'] : '';

if($_POST['mode'] == 'id_chk') {

  if($id == '') {
    die(json_encode(['result' => 'empty_id']));
  }

  if($mem->id_exists($id)) {
    die(json_encode(['result' => 'fail']));
  } else {
    die(json_encode(['result' => 'success']));
  }
} else if($_POST['mode'] == 'email_chk') {

  if($email == '') {
    die(json_encode(['result' => 'empty_email']));
  }

  if($mem->email_exists($email)) {
    die(json_encode(['result' => 'fail']));
  } else {
    die(json_encode(['result' => 'success']));
  }
}

$password = $_POST['password'];
$zipcode = $_POST['zipcode'];
$addr1 = $_POST['addr1'];
$addr2 = $_POST['addr2'];
// $photo = $_POST['photo'];
$tfile = './upload/profile_image.png';
$create_at = date("Y-m-d H:i:s");
// $ip = $_POST['ip'];

try {
  $sql = "INSERT INTO memsite.member(id, email, password, zipcode, addr1, addr2, photo, create_at) 
  VALUES('{$id}', '{$email}', '{$password}', '{$zipcode}', '{$addr1}', '{$addr2}', '{$tfile}', '{$create_at}')"; // '{$ip}'
  
  move_uploaded_file($_FILES['photo']['tmp_name'], $tfile);

  $db->exec($sql);
  echo "회원가입이 완료되었습니다.";

} catch(PDOException $e) {
  echo $e->getMessage();
}