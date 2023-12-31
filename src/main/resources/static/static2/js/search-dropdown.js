document.addEventListener("DOMContentLoaded", function () {
    const searchButton = document.getElementById("searchButton");
    const itemNameInput = document.getElementById("keyword");
    const searchResults = document.getElementById("searchResults");
    itemNameInput.addEventListener("input", function () {
        const itemName = itemNameInput.value;
        if (itemName.length >= 1) {  // 검색어 한글자부터 드롭다운 시작
            fetch(`/search?name=${itemName}`)
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


                            const itemImage = document.createElement("img");

                            // 아이템의 ID를 가져옴 (이 부분은 원하는 Item의 ID를 가져오는 방식에 따라 변경해야 함)
                            const itemId = item.id; // 예: 서버에서 동적으로 가져옴

                            // 이미지 정보를 비동기적으로 가져오는 함수
                            async function fetchItemImage() {
                                try {
                                    const response = await fetch(`/api/getItemImagePath?itemId=${itemId}`);
                                    if (response.ok) {
                                        const imagePath = await response.text(); // imagePath로 수정
                                        if (imagePath) {
                                            itemImage.onload = function () {
                                                // 이미지 로드 완료 후 이미지 박스를 표시
                                                imageBox.style.display = "block";
                                            };
                                            itemImage.src = `/static/${imagePath}`; // imagePath로 수정
                                            itemImage.alt = item.name;
                                            imageBox.appendChild(itemImage);
                                        } else {
                                            // 이미지 정보가 없을 경우 이미지 박스를 숨김 처리 (이미지 엑박 방지)
                                            imageBox.style.display = "none";
                                        }
                                    } else {
                                        console.error("Error fetching image path:", response.status);
                                    }
                                } catch (error) {
                                    console.error("Error fetching image path:", error);
                                }
                            }

                            // 이미지 정보 가져오기
                            fetchItemImage();

                            // // 이미지의 상대 경로 설정
                            // fetch(`/api/getItemImageName?itemId=${itemId}`)
                            //     .then(response => response.text())
                            //     .then(imageName => {
                            //         if (imageName) {
                            //             itemImage.onload = function() {
                            //                 // 이미지 로드 완료 후 이미지 박스를 표시
                            //                 imageBox.style.display = "block";
                            //             };
                            //             itemImage.src = `/static/static2/images/item_images/${imageName}`;
                            //             itemImage.alt = item.name;
                            //             imageBox.appendChild(itemImage);
                            //         } else {
                            //             // 이미지 정보가 없을 경우 이미지 박스를 숨김 처리 (이미지 엑박 방지)
                            //             imageBox.style.display = "none";
                            //         }
                            //     })
                            //     .catch(error => {
                            //         console.error("Error fetching image name:", error);
                            //     });

                            // 아이템 이름 설정
                            const itemNameElement = document.createElement("div");
                            itemNameElement.classList.add("search-dropdown-name");  // 스타일 클래스
                            itemNameElement.innerText = item.name;

                            // 카테고리 이름 설정
                            const categoryNameElement = document.createElement("div");
                            categoryNameElement.classList.add("search-dropdown-category");  // 스타일 클래스
                            categoryNameElement.innerText = `${item.category.name}`;

                            // 이미지 박스, 아이템 이름, 카테고리 이름 추가
                            dropdownItem.appendChild(imageBox);
                            dropdownItem.appendChild(itemNameElement);
                            dropdownItem.appendChild(categoryNameElement);
                            dropdownItem.addEventListener("click", function () {
                                window.location.href = `/item-detail?id=${item.id}`;
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
        fetch(`/search?name=${searchTerm}`)
            .then(response => response.json())
            .then(data => {
                if (data.length === 0) {
                    window.location.href = "/search-fail"; // 검색 결과가 없을 때
                } else if (data.length === 1) {
                    window.location.href = `/item-detail?id=${data[0].id}`; // 검색 결과가 하나일 때
                } else {
                    if (itemNameInput.value.trim() !== "") {
                        window.location.href = `/search-success?name=${searchTerm}`; // 검색 결과가 여러 개일 때
                    }
                }
            })
            .catch(error => {
                console.error("Error fetching search results:", error);
            });
    }
});