
const signUpForm = document.getElementById("formAuthentication");

signUpForm.addEventListener("submit", e => {
    e.preventDefault();
    e.stopPropagation();

    const pw = document.getElementById("pw").value;
    const confirmPw = document.getElementById("confirmPw").value;

    if(pw !== confirmPw) {
        document.getElementById("message").innerHTML = "비밀번호가 일치하지 않습니다.";
        return false;
    } else {
        console.log("비밀번호 일치");
        signUpForm.submit();
    }
});


//가입과정에서 존재하는 회원이라 리다이렉트되는 경우

console.log(error);

if(error && error === 'email') {
    alert("이미 가입된 회원입니다. 이메일을 확인해 주세요.");
}


//인증번호 로직
let checkEmail = document.getElementById('checkEmail');
let email = document.getElementById('email');
let emailConfirm = document.getElementById('emailConfirm');
let emailConfirmTxt = document.getElementById('emailConfirmTxt');

checkEmail.addEventListener('click', e => {
    axios.post("/signup/mailConfirm", {
        email: email.value
    })
        .then(response => {
            alert("입력하신 이메일로 인증번호가 발송되었습니다. 확인 후 입력해주세요.")
            console.log("data:", response.data);
            checkEmailConfirm(response.data, emailConfirm, emailConfirmTxt);
        })
        .catch(error => {
            console.error(error);
        });
})

//data : 발송된 인증번호
function checkEmailConfirm(data,emailConfirm,emailConfirmTxt) {
    emailConfirm.addEventListener("keyup", e => {
        let span;
        if (data !== emailConfirm.value) {
            span = document.createElement("span");
            span.id = 'emailConfirmChk';
            span.textContent = "인증번호를 잘못 입력하셨습니다.";
            span.style.color = "#FA3E3E";
            span.style.fontWeight ="bold";
            span.style.fontSize ="10px";

            if(emailConfirmTxt.children.length > 0) {
                emailConfirmTxt.removeChild(emailConfirmTxt.firstChild);
            }

            emailConfirmTxt.appendChild(span);

        } else {
            let chkSpan;
            chkSpan=document.createElement("span");
            chkSpan.id='emailconfirmchk';
            chkSpan.textContent="인증번호 확인 완료";

            chkSpan.style.color="#0D6EFD";
            chkSpan.style.fontWeight="bold";
            chkSpan.style.fontSize="10px";

            if(emailConfirmTxt.children.length > 0) {
                emailConfirmTxt.removeChild(emailConfirmTxt.firstChild);
            }

            emailConfirmTxt.appendChild(chkSpan);

        }
    })
}



