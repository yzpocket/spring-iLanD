// JavaScript 코드
const images = ["game_rock.png", "game_paper.png", "game_scissors.png"];
let currentIndex = 0;
let userScore = 0;
let computerScore = 0;
let drawScore = 0;
const winningScore = 5; // 이길 점수
const drawLimit = 5; // 무승부 제한

function getRandomChoice() {
    return Math.floor(Math.random() * 3) + 1; // 1, 2, 3 중 하나를 무작위로 선택
}

function changeImage() {
    const computerChoice = getRandomChoice();

    // 이미지 변경만으로 미리 처리
    document.getElementById("img").src = `assets/23_HP010_iLanD-main/img/${images[computerChoice - 1]}`;
    document.getElementById("computerChoice").value = computerChoice;

    currentIndex = (currentIndex + 1) % images.length; // 다음 이미지로 변경
}

function userChoiceClick(choice) {
    const userChoice = parseInt(choice, 10);
    const computerChoice = parseInt(document.getElementById("computerChoice").value, 10);

    // 여기에서 승패를 판단하고 점수를 업데이트하는 로직 추가
    compareChoices(userChoice, computerChoice);

    // 이미지 변경 함수 호출 (이미지는 미리 설정되어 있음)
    showResultImage(computerChoice);

    // 승리 여부 확인
    if (userScore === winningScore || computerScore === winningScore || drawScore === drawLimit) {
        if (userScore >= winningScore) {
            announceWinner("사용자");
        } else if (computerScore >= winningScore) {
            announceLoser("컴퓨터");
        } else {
            announceDrawLimit();
        }
    }
}

// 승패를 판단하고 점수를 업데이트하는 로직을 추가하는 함수
function compareChoices(userChoice, computerChoice) {
    if (userChoice === computerChoice) {
        alert("무승부!");
        drawScore++;
        document.getElementById("draw_score").textContent = drawScore;

        if (drawScore === drawLimit) {
            announceDrawLimit();
        }
    } else if (
        (userChoice === 1 && computerChoice === 3) ||
        (userChoice === 2 && computerChoice === 1) ||
        (userChoice === 3 && computerChoice === 2)
    ) {
        alert("사용자가 이겼습니다!");
        userScore++;
    } else {
        alert("컴퓨터가 이겼습니다!");
        computerScore++;
    }

    document.getElementById("user_score").textContent = userScore;
    document.getElementById("computer_score").textContent = computerScore;
}

// 승리 또는 패배 알림 및 점수 초기화
function announceWinner(winner) {
    alert(`축하합니다! ${winner}가 5승으로 이겼습니다!`);
    resetScores();
}

// 패배 알림 및 초기화
function announceLoser(loser) {
    alert(`이런... ${loser}가 5승으로 이겼네요. 못하겠다고요!`);
    resetScores();
}

// 무승부 제한 알림 및 초기화
function announceDrawLimit() {
    alert("게임을 이해하고 계신거죠..? 무승부가 5회 달성되었습니다!");
    resetScores();
}

// 이미지를 변경하여 결과를 보여주는 함수
function showResultImage(computerChoice) {
    // 이미지 변경만으로 미리 처리
    document.getElementById("img").src = `assets/23_HP010_iLanD-main/img/${images[computerChoice - 1]}`;
}

// 점수 초기화 함수
function resetScores() {
    userScore = 0;
    computerScore = 0;
    drawScore = 0;

    document.getElementById("user_score").textContent = userScore;
    document.getElementById("computer_score").textContent = computerScore;
    document.getElementById("draw_score").textContent = drawScore;
}

// 0.2초마다 이미지 변경
setInterval(changeImage, 300);
