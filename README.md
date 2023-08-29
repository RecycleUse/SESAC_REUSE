# SESAC_REUSE


# DB 설계

# 아이템 DB

| 테이블명 | 컬럼명 | 설명 | 비고 |
|:--------:|:------:|:----:|:----:|
| category(대분류) | category_id | 카테고리 PK | A: 종이, B: 종이팩, ... |
|  | name | 카테고리 이름 | 분리배출 가이드라인 pdf p.27 참조 |
| item(소분류) | item_id | 아이템 PK | A숫자: 종이류, B숫자: 종이팩, ... |
|  | item_name | 아이템 이름 |  |
|  | recycle_info | 버리는 방법 |  |
|  | recyclable | 재활용 가능여부 | boolean 타입으로 Y/N |
|  | category_id | 카테고리 FK |  |
| item_similar | Item_similar_pk | 아이템 유사 검색어 PK |  |
|  | similar_search_terms | 유사검색어 | nullable |
|  | item_id | 아이템FK |  |
| item_image | image_id | 이미지 PK | auto increment 사용하여 1씩 증가 |
|  | image_path | 이미지 경로 |  |
|  | image_name | 이미지명 | item 테이블 item_id 컬럼 + UUID |
|  | item_id | 아이템 FK | Item join을 위한 컬럼 |

# 사용자 관련 DB

| 테이블명 | 컬럼명 | 설명 | 비고 |
|:--------:|:------:|:----:|:----:|
| user | id | 회원 PK |  |
|  | email | email |  |
|  | pw | 비밀번호 |  |
|  | name | 닉네임 |  |
| bookmark | bookmark_id | 북마크 PK | auto increment 사용하여 1씩 증가 |
|  | item_id | 아이템 FK |  |
|  | user_id | 회원 FK |  |

# 게시판 DB

| 테이블명 | 컬럼명 | 설명 | 비고 |
|:--------:|:------:|:----:|:----:|
| board | board_id | PK | auto_increment |
|  | type | 분류 | enum으로 관리 (공지사항, 추가요청, 기타) |
|  | title | 글 제목 |  |
|  | content | 글 내용 |  |
|  | writer | 작성자 |  |
|  | create_date | 작성 날짜 |  |
|  | status | 진행 상태 | nullable (분류가 추가요청일 때만 출력) |
| board_file | file_id | PK |  |
|  | file_path | 파일경로 |  |
|  | file_name | 파일명 |  |
|  | ori_name | 원본파일명 |  |
|  | board_id | FK |  |
| reply | reply_id | PK | auto_increment |
|  | writer | 작성자 |  |
|  | content | 답변 내용 |  |
|  | create_date | 작성 날짜 |  |
|  | board_id | Board FK |  |
