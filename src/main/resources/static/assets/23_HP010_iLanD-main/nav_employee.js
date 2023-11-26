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