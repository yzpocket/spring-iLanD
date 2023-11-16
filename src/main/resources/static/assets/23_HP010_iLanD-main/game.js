// 150초 타이머
let seconds = 100;
const timerElement = document.getElementById('timer');
function updateTimer() {
  if (seconds > 0) {
    seconds--;
    timerElement.textContent = seconds;
  } else {
    clearInterval(interval);
    timerElement.textContent = 'Time';
  }
}
const interval = setInterval(updateTimer, 1000); // 1초마다 updateTimer 함수 실행