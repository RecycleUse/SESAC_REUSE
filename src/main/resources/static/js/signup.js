
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


