
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



