// 버튼 눌렀을 때 현재창에서 html 열기
document.getElementById('employeeButton').addEventListener('click', function() {
    openPage('employee_main.html');
  });
  
  function openPage(url) {
    window.location.href = url;
  }/* 이거 왜 안되냐 */