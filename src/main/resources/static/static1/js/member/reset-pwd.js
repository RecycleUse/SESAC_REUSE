
document.getElementById("resetPwBtn").addEventListener("click",e => {
    const email = document.getElementById("email").value;
    console.log("click",email);

    e.preventDefault()
    e.stopPropagation();

    axios.post("/auth2/reset-pwd", {
        email : email
    })
        .then(response => {
            console.log(response);

            if(200 <= response.status && response.status < 300) {
                alert("입력하신 이메일로 임시 비밀번호가 발송되었습니다. 확인 후 로그인해주세요.")
                console.log("data:", response.data); //찍어보고
                //로그인으로 가는 링크 모달창에 넣어주고싶은데 ㅠ
            } else {
                throw new Error(`HTTP error! status : ${response.status}` );
            }
        })
        .catch(error => {
            console.error(`HTTP error! status : ${error.response.status}`);
            if(error.response.status === 400) {
                alert(error.response.data);
            }
        })
})
