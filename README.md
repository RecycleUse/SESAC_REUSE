# SESAC_REUSE

# REUSE 프로젝트 소개

여러분들께선 물건을 버릴 때, 분리 수거를 어떻게 해야하는지 어려웠던 적이 있으신가요?
종이, 플라스틱 등 한 가지로만 이루어진 제품보단 다양한 소재가 섞인 제품이 훨씬 많다보니 어떻게 버려야하는지 헷갈릴 수 있습니다. 이로 인해 분리 수거가 제대로 이루어지지 않아 이로 환경 오염이 가증될 수 있습니다. 그래서 저희는 올바른 재활용 정보를 제공하는 서비스를 만들었습니다.
저희는 환경부가 제공한 가이드라인을 기반으로 데이터 베이스를 구축했습니다.


# 주요 기능

## 회원가입 및 로그인
![회원가입 및 로그인](https://github.com/devyejin/sesac_reuse_prj/assets/109127968/88e07f56-7cbd-487c-bc0f-296792fe78a8)

스프링 시큐리티를 통한 회원가입 및 로그인을 구현하였습니다. id를 email주소를 사용하는 만큼 유효한 email인지 확인을 위해 email을 통한 인증번호 확인을 하는 로직을 추가하였습니다.

## 비밀번호 재발급 기능
![비밀번호찾기기능](https://github.com/devyejin/sesac_reuse_prj/assets/109127968/6f7cdd95-c2d5-4bce-a0d5-184c1ee5cdf6)

비밀번호 분실시, 회원가입한 이메일을 통해 재발급 가능하도록 구현하였습니다.

## 비인증회원의 경우 게시물 읽기만 가능, 작성은 로그인 해야 가능

![비로그인-게시판-게시글작성-로그인창](https://github.com/devyejin/sesac_reuse_prj/assets/109127968/8762b001-aa1b-484e-8b94-92e932f45799)

비인증 회원의 경우, 게시글 읽기만 가능하고 작성을 원하는 경우 로그인 후 작성 가능합니다.
또한, 관리자의 경우에만 공지사항 작성이 가능합니다.


## 회원탈퇴시 세션종료 및 기존 작성글 삭제
![회원탈퇴,세션종료,기존글삭제](https://github.com/devyejin/sesac_reuse_prj/assets/109127968/96ae83d7-be72-4816-8ee0-78ad6661fd0d)

회원탈퇴시 기존에 작성한 게시글들은 회원 개인정보 보호차 삭제 처리하였습니다.

## 검색 기능

통합 게시판의 경우, 제목, 내용, 작성자 등 유형을 선택해 검색이 가능합니다.

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
