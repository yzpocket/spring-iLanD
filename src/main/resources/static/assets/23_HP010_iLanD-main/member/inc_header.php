<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>약관</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
  <?php  
  if(isset($js_array)) {
    foreach($js_array AS $var) {
      echo '<script src="'.$var.'?v='. date("YmdHis").'"></script>' .PHP_EOL;
    }
  }  
  ?>  

</head>
<body>
  <div class="container d-flex flex-column justify-content-center align-items-center text-center gap-3">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
      <a href="login.php" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none">
        <img src="images/iLanD_logo.png" alt="" style="width: 2rem" class="me-2">
        <span class="fs-4"><h5 class="fs-4-h5 mt-2">직원모드</h5></span>
      </a>

      <ul class="nav nav-pills justify-content-center">
        <li class="nav-item"><a href="#" class="nav-link">채팅</a></li>
        <li class="nav-item"><a href="#" class="nav-link">공지</a></li>
        <li class="nav-item"><a href="management_contents_1.php" class="nav-link">콘텐츠</a></li>
        <li class="d-flex align-items-center">&#124;</li>
        <li class="nav-item"><a href="stipulation.php" class="nav-link">Sign up</a></li> <!-- class="nav-link active" aria-current="page"-->
        <li class="nav-item"><a href="#" class="nav-link">Exit</a></li>
      </ul>
    </header>
  </div>