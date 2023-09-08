// JavaScript를 사용하여 {name} 값을 동적으로 설정
const form = document.querySelector(".custom-form");
const input = document.getElementById("keyword");
const searchButton = document.getElementById("searchButton");

form.addEventListener("submit", function (event) {
    event.preventDefault(); // 폼 제출 기본 동작 방지

    const nameValue = input.value; // 사용자 입력을 가져옴
    const action = `/openai/chat/${nameValue}`;
    form.setAttribute("action", action);

    // Ajax 요청 보내기
    fetch(action, {
        method: "GET"
    })
        .then(response => response.text())
        .then(result => {
            // 결과를 결과 컨테이너에 동적으로 업데이트
            document.getElementById("resultContainer").innerHTML = result;
        });
});