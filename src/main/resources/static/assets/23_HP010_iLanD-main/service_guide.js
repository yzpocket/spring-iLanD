// 팝업 열기
function openPage() {
    const popup = document.querySelector('.popup');
    popup.style.display = 'block';
}

// 팝업 닫기
function closePage() {
    const popup = document.querySelector('.popup');
    popup.style.display = 'none';
}

// // 버튼 클릭 시 팝업 열기
// const mainserviceButton = document.getElementById('service_guide_button');
// mainserviceButton.addEventListener('click', openPopup);

// X 버튼 클릭 시 팝업 닫고 iLanD_main.html로 이동
const closeBtn = document.querySelector('.close-btn');
closeBtn.addEventListener('click', function () {
    closePage();
    window.location.href = 'iLanD_main.html';
});


// 이미지 슬라이드
var slideIndex = 0;
    showSlides();

    function showSlides() {
        var i;
        var slides = document.getElementsByClassName("ad-image");
       
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slideIndex++;
        if (slideIndex > slides.length) {
            slideIndex = 1
        }
        slides[slideIndex - 1].style.display = "block";
    
        setTimeout(showSlides, 1000); // 3초마다 이미지가 체인지됩니다
    }
