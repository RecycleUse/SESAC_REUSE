$(document).ready(function() {
    $('#summernote').summernote({
        height: 300,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: ''	//placeholder 설정
    });
});

const formSelect = document.getElementById('type');
const summernote = document.getElementById('summernote');

const text = `재활용품 추가/수정 요청 시 아래의 양식을 작성해 주세요.

분류 :

재활용 가능 여부 :

이름 :

분리수거 방법 :

비슷한 품목 :`;

const lines = text.split('\n');  // 줄바꿈을 기준으로 텍스트를 분리

let HTMLString = '<div>';
for (let line of lines) {
    HTMLString += '<p>' + line + '</p>';  // 각 줄에 <p> 태그를 추가
}
HTMLString += '</div>';



formSelect.addEventListener('change', e => {

    e.stopPropagation();
    e.preventDefault();

    console.log("박스선택됨");
    if(formSelect.value === 'ADDITIONAL_REQUEST') {
        console.log("추가요청 선택됨");

        $('#summernote').summernote('pasteHTML',HTMLString);

    } else {
        console.log("그 외");
        $('#summernote').summernote('code', '');

    }
});