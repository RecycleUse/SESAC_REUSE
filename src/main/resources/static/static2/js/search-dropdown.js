document.addEventListener("DOMContentLoaded", function () {
    const searchButton = document.getElementById("searchButton");
    const itemNameInput = document.getElementById("keyword");
    const searchResults = document.getElementById("searchResults");

    itemNameInput.addEventListener("input", function () {
        const itemName = itemNameInput.value;
        if (itemName.length >= 1) {  // 검색어 한글자부터 드롭다운 시작
            fetch(`/search?itemName=${itemName}`)
                .then(response => response.json())
                .then(data => {
                    if (data.length === 0) {
                        searchResults.innerHTML = ""; // 드롭다운 비우기
                    } else {
                        searchResults.innerHTML = "";
                        const dropdown = document.createElement("div");
                        dropdown.classList.add("search-dropdown", "show");  // 스타일 클래스
                        data.forEach(item => {
                            const dropdownItem = document.createElement("button");
                            dropdownItem.classList.add("search-dropdown-item");
                            dropdownItem.type = "button";

                            // 이미지 박스 생성 및 스타일 적용
                            const imageBox = document.createElement("div");
                            imageBox.classList.add("search-dropdown-image");  // 스타일 클래스
                            const image = document.createElement("img");

                            // 이미지의 상대 경로 설정
                            if (item.item_id) {
                                image.src = `/static/static2/images/item_images/${item.item_id}.jpg`;
                                image.alt = item.itemName;  // 이미지 대체 텍스트
                                imageBox.appendChild(image);
                            } else {
                                // 이미지 정보가 없을 경우 이미지 박스를 숨김 처리 (이미지 엑박 방지)
                                imageBox.style.display = "none";
                            }

                            // 아이템 이름 설정
                            const itemNameElement = document.createElement("div");
                            itemNameElement.classList.add("search-dropdown-name");  // 스타일 클래스
                            itemNameElement.innerText = item.itemName;

                            // 카테고리 이름 설정
                            const categoryNameElement = document.createElement("div");
                            categoryNameElement.classList.add("search-dropdown-category");  // 스타일 클래스
                            categoryNameElement.innerText = `${item.category.name}`;

                            // 이미지 박스, 아이템 이름, 카테고리 이름 추가
                            dropdownItem.appendChild(imageBox);
                            dropdownItem.appendChild(itemNameElement);
                            dropdownItem.appendChild(categoryNameElement);

                            dropdownItem.addEventListener("click", function () {
                                window.location.href = `/item-detail?item_id=${item.item_id}`;
                            });
                            dropdown.appendChild(dropdownItem);
                        });
                        // 드롭다운을 추가한 후에 스타일 클래스를 적용하고 화면에 표시
                        searchResults.appendChild(dropdown);
                    }
                })
                .catch(error => {
                    console.error("Error fetching search results:", error);
                });
        } else {
            searchResults.innerHTML = ""; // 검색어가 짧을 때 드롭다운 비우기
        }
    });

    searchButton.addEventListener("click", function (event) {
        event.preventDefault();
        performSearch();
    });

    function performSearch() {
        const searchTerm = itemNameInput.value;

        fetch(`/search?itemName=${searchTerm}`)
            .then(response => response.json())
            .then(data => {
                if (data.length === 0) {
                    window.location.href = "/search-fail"; // 검색 결과가 없을 때
                } else if (data.length === 1) {
                    window.location.href = `/item-detail?item_id=${data[0].item_id}`; // 검색 결과가 하나일 때
                } else {
                    if (itemNameInput.value.trim() !== "") {
                        window.location.href = `/search-success?itemName=${searchTerm}`; // 검색 결과가 여러 개일 때
                    }
                }
            })
            .catch(error => {
                console.error("Error fetching search results:", error);
            });
    }
});