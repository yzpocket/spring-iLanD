<?php

$servername = 'localhost'; // 웹서버와 DB서버가 한 컴퓨터에 있음을 의미
$dbuser = 'root';
$dbpassword = '';
$dbname = 'memsite';


try {
$db = new PDO("mysql:host={$servername};dbname={$dbname}", $dbuser, $dbpassword);

// Prepared Statement 를 지원하지 않는 경우 데이터베이스의 기능을 사용하도록 해줌
$db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
$db->setAttribute(PDO::MYSQL_ATTR_USE_BUFFERED_QUERY, true); // 쿼리 버퍼링을 활성화
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); // PDO 객체가 에러를 처리하는 방식 정함

// echo "DB 연결 성공";
} catch (PDOException $e) {
  echo $e->getMessage(); //에러가 발생했는지 아닌지 구분하려면 이 코드가 꼭 필요함 // SQLSTATE[HY000] [1049] Unknown database 'memsite1'
}