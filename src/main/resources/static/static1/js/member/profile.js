
const formObj = document.getElementById("formAccountSettings");

document.getElementById("saveBtn").addEventListener("click", async e => {
    console.log("click");
    e.preventDefault();
    e.stopPropagation();

    const formData = new FormData(formObj);

    const modifiedResult = await modifyProfile(formData);

},false);

//회원정보 수정
async function modifyProfile(formObj) {

    console.log("modify Profile to Server");
    console.log(formObj);

    const response = await axios({
        method:'post',
        url:'/auth2/modify-profile',
        data:formObj instanceof FormData ? formObj : JSON.stringify(formObj),
        headers: {
            'Content-Type': formObj instanceof FormData ? undefined : 'application/json'
        },
    });

    // console.log(response.data);

    return response.data;
}

document.addEventListener("DOMContentLoaded", function() {
    // const resultMessage = "${resultMessage}";

    console.log(resultMessage);

    if (resultMessage && resultMessage.length > 0) {
        // resultMessage가 존재하면 알림창을 띄우기
        alert(resultMessage);
    }
});





if(withdrawResult) alert(withdrawResult);

const deactiveCheckBox = document.getElementById('accountActivation');
deactiveCheckBox.addEventListener('change', e => {
    const detectiveBtn = document.querySelector('.deactivate-account');

    if(deactiveCheckBox.checked) {
        detectiveBtn.removeAttribute('disabled');
    } else {
        detectiveBtn.setAttribute('disabled','disabled');
    }
});

// console.log(resultMessage);
// if (resultMessage) {
//     alert(resultMessage);
// }

document.addEventListener("DOMContentLoaded", function() {
    console.log(resultMessage);
    if (resultMessage) {
        alert(resultMessage);
    }
});