
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

    console.log(response.data);

    return response.data;
}