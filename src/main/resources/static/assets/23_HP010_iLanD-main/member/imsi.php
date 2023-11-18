<?php

// DB 연결
include 'inc/dbconfig.php';
include 'inc/member.php';


// 아이디 중복 테스트

$email = 'q1103@email.com';

$mem = new Member($db);

if ( $mem->email_exists($email)) {
  echo "이메일이 중복됩니다.";
} else {
  echo "사용할 수 있는 이메일입니다.";
}

?>