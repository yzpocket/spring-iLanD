<?php

include 'inc_header.php'; 

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="management_contents.css">
    <title>management contents</title>
</head>
<body>
  <div class="top-button">
    <select name="combobox" id="combobox">
      <option value="1">Movies</option>
      <option value="2">TV</option>
    </select>
    <form action="검색결과페이지.html" method="GET"> <!--html 링크 추후 수정-->
        <!-- 입력 창과 아이콘을 감싸는 div -->
        <div class="search-container">
            <!-- 입력 창 생성 -->
            <input type="text" name="검색어" placeholder="검색어를 입력하세요" class="search-input">
            <!-- 검색 버튼 (이미지 아이콘) -->
            <button type="submit" class="search-button">
                <img width="20" height="20" src="https://img.icons8.com/sf-regular/48/search.png" alt="search"/>
            </button>
            
            <!-- <nav class="navbar navbar-light d-flex justify-content-between w-50">
              <form class="form-inline">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
              </form>
            </nav>    -->
        </div>
    </form>
  </div>

  <h3>Movies List</h3>
  <div class="m-list-box">
    <table>
      <form method="GET"> <!-- 여기 수정-->
      <tr id="head-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h4>제목</h4></td>
        <td><h4>url</h4></td>
        <td><h4>만료</h4></td>
        <td><h4>업로드</h4></td>
        <td><a href="management_contents_1.html"></a><input type="image" onclick="alert('업로드가 완료되었습니다.')" width="18" height="18" src="https://img.icons8.com/fluency-systems-regular/96/upload--v1.png" alt="submit"></a>
            <input type="image" width="18" height="18" src="https://img.icons8.com/fluency-systems-regular/144/trash--v1.png" alt="trash">
        </td>
      </tr>
      <tr>
        <td></td>
        <td><input type="text" name="" id="title-box"></td>
        <td><input type="url" name="" id="title-box"></td>
        <td><input type="date" name="" id="date-box"></td>
        <td><input type="date" name="" id="date-box"></td>
        <td><input class="" type="submit" value="추가" id="add"></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5></h5></td>
        <td><h5></h5></td>
        <td><h5></h5></td>
        <td><h5></h5></td>
        <td><h5 id="point-blue"></h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>스즈메의 문단속</h5></td>
        <td><h5>https://iland.s..</h5></td>
        <td><h5>24.09.20</h5></td>
        <td><h5>23.09.20</h5></td>
        <td><h5 id="point-blue"></h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>극한직업</h5></td>
        <td><h5>https://iland.g..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>범죄도시3</h5></td>
        <td><h5>https://iland.b..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>7번방의 선물</h5></td>
        <td><h5>https://iland.7..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>타이타닉</h5></td>
        <td><h5>https://iland.t..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>메이즈러너 데스..</h5></td>
        <td><h5>https://iland.m..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      </form>
    </table>
  </div>
  <br>
  <h3>TV List</h3>
  <div class="m-list-box mb-5">
    <table>
      <form method="GET" action="employee_main.html" id="management-contents-form">
      <tr id="head-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h4>제목</h4></td>
        <td><h4>url</h4></td>
        <td><h4>만료</h4></td>
        <td><h4>업로드</h4></td>
        <td><input type="image" width="18" height="18" src="https://img.icons8.com/fluency-systems-regular/96/upload--v1.png" alt="submit">
            <input type="image" width="18" height="18" src="https://img.icons8.com/fluency-systems-regular/144/trash--v1.png" alt="trash">
        </td>
      </tr>
      <tr>
        <td></td>
        <td><input type="text" name="" id="title-box"></td>
        <td><input type="url" name="" id="title-box"></td>
        <td><input type="date" name="" id="date-box"></td>
        <td><input type="date" name="" id="date-box"></td>
        <td><input type="submit" value="추가" id="add"></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5></h5></td>
        <td><h5></h5></td>
        <td><h5></h5></td>
        <td><h5></h5></td>
        <td><h5 id="point-blue"></h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>슬기로운 의사생활</h5></td>
        <td><h5>https://iland.n..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>멜로가 체질</h5></td>
        <td><h5>https://iland.i..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>스물다섯 스물하나</h5></td>
        <td><h5>https://iland.d..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>모범 택시</h5></td>
        <td><h5>https://iland.s..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>패밀리가 떴다</h5></td>
        <td><h5>https://iland.s..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      <tr id="content-tr">
        <td><input type="checkbox" name="" id=""></td>
        <td><h5>하트시그널 4</h5></td>
        <td><h5>https://iland.g..</h5></td>
        <td><h5>24.01.01</h5></td>
        <td><h5>23.01.01</h5></td>
        <td><h5 id="point-blue">완료</h5></td>
      </tr>
      </form>
    </table>
  </div>
</body>