const deleteBtn = document.getElementById('deleteBtn');


deleteBtn.addEventListener("click", e => {
    e.preventDefault();

    if (confirm("정말 삭제하시겠습니까?")) {
        const form = document.getElementById('deleteForm');

        // boardId 값을 URL 쿼리 파라미터로 추가
        form.action = `/board/delete?boardId=${boardId}`;

        form.submit();
    }
});