// nav_employee.js

// 로그인 함수
function login() {
    const email = document.querySelector('input[type="email"]').value;
    const password = document.querySelector('input[type="password"]').value;

    const loginData = {
        email: email,
        password: password
    };

    // 서버에 로그인 요청 보내기
    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('로그인에 실패했습니다.');
                alert('로그인에 실패했습니다.')
            }
            return response.json();
        })
        .then(data => {
            // 로그인이 성공한 경우
            console.log(data);
            alert('로그인에 성공했습니다.')
            // 여기에서 필요한 리다이렉션 또는 다른 작업을 수행할 수 있습니다.
            openPage('/admin'); // 로그인 성공 후 이동할 페이지
        })
        .catch(error => {
            // 로그인이 실패한 경우
            console.error(error.message);
            alert('로그인에 실패했습니다.')
        });
}
function agreement() {
    // SignUp 버튼을 눌렀을 때 실행되는 코드
    window.location.href = "/agreement";
}
function signupView() {
    // 체크 여부 확인
    const isAgreementChecked1 = $('#chk_member1').prop('checked');
    const isAgreementChecked2 = $('#chk_member2').prop('checked');

    // 두 개의 약관에 모두 동의한 경우에만 회원가입 페이지로 이동
    if (isAgreementChecked1 && isAgreementChecked2) {
        // SignUp 버튼을 눌렀을 때 실행되는 코드
        window.location.href = "/signup";
    } else {
        // 동의하지 않은 약관이 있을 경우 처리 (예: 경고 메시지 등)
        alert('약관에 모두 동의해야 합니다.');
        // 또는 다른 처리를 원하는 대로 추가
    }
}
// 가입 취소 버튼 클릭 시 뒤로 가기
function cancelSignup() {
    history.back();
}