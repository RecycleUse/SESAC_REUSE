// const errorMessage = "사용자 입력 정보가 틀렸습니다.";
//
// if(error) {
//     alert(errorMessage);
// }

let currentURL = window.location.href;
function getParameterByName(name, url) {
    if (!url) {
        url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    let regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

let errorValue = getParameterByName("error", currentURL);
let errorMessageValue = getParameterByName("errorMessage", currentURL);

if (errorValue) {
    alert(decodeURIComponent(errorMessageValue));
}