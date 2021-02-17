-- ================ Root 카테고리
insert into tbl_category(category_name, parent_id) values ('패션', 0);
insert into tbl_category(category_name, parent_id) values ('가전/디지털', 0);
insert into tbl_category(category_name, parent_id) values ('도서', 0);
insert into tbl_category(category_name, parent_id) values ('식품', 0);


-- =============== Level 2 카테고리
-- 패션
insert into tbl_category(category_name, parent_id) values ('여성', 1);
insert into tbl_category(category_name, parent_id) values ('남성', 1);
insert into tbl_category(category_name, parent_id) values ('아동', 1);
insert into tbl_category(category_name, parent_id) values ('스포츠', 1);
insert into tbl_category(category_name, parent_id) values ('잡화', 1);

-- 가전
insert into tbl_category(category_name, parent_id) values ('컴퓨터', 2);
insert into tbl_category(category_name, parent_id) values ('냉장고', 2);
insert into tbl_category(category_name, parent_id) values ('청소기', 2);
insert into tbl_category(category_name, parent_id) values ('세탁기/건조기', 2);

-- 도서
insert into tbl_category(category_name, parent_id) values ('여행', 3);
insert into tbl_category(category_name, parent_id) values ('역사', 3);
insert into tbl_category(category_name, parent_id) values ('예술', 3);
insert into tbl_category(category_name, parent_id) values ('공학/과학', 3);

-- 식품
insert into tbl_category(category_name, parent_id) values ('과일', 4);
insert into tbl_category(category_name, parent_id) values ('채소', 4);
insert into tbl_category(category_name, parent_id) values ('생수/음료', 4);
insert into tbl_category(category_name, parent_id) values ('수산물', 4);
insert into tbl_category(category_name, parent_id) values ('축산', 4);

-- =============== Level 3 카테고리행
-- === 패션
-- 여성
insert into tbl_category(category_name, parent_id) values ('티', 5);
insert into tbl_category(category_name, parent_id) values ('원피스', 5);
insert into tbl_category(category_name, parent_id) values ('블라우스', 5);
insert into tbl_category(category_name, parent_id) values ('바지/레깅스', 5);

-- 남성
insert into tbl_category(category_name, parent_id) values ('티', 6);
insert into tbl_category(category_name, parent_id) values ('맨투맨/후드', 6);
insert into tbl_category(category_name, parent_id) values ('셔츠', 6);
insert into tbl_category(category_name, parent_id) values ('바지/청바지', 6);

-- 아동
insert into tbl_category(category_name, parent_id) values ('여아', 7);
insert into tbl_category(category_name, parent_id) values ('남아', 7);
insert into tbl_category(category_name, parent_id) values ('공용', 7);

-- 스포츠
insert into tbl_category(category_name, parent_id) values ('여성', 8);
insert into tbl_category(category_name, parent_id) values ('남성', 8);
insert into tbl_category(category_name, parent_id) values ('유아', 8);

-- 신발/가방/잡화
insert into tbl_category(category_name, parent_id) values ('시계', 9);
insert into tbl_category(category_name, parent_id) values ('신발', 9);
insert into tbl_category(category_name, parent_id) values ('가방', 9);
insert into tbl_category(category_name, parent_id) values ('모자', 9);

-- === 가전
-- 컴퓨터
insert into tbl_category(category_name, parent_id) values ('노트북', 10);
insert into tbl_category(category_name, parent_id) values ('데스크탑', 10);
insert into tbl_category(category_name, parent_id) values ('모니터', 10);
insert into tbl_category(category_name, parent_id) values ('키보드/마우스', 10);

-- 냉장고
insert into tbl_category(category_name, parent_id) values ('양문형냉장고', 11);
insert into tbl_category(category_name, parent_id) values ('3/4도어냉장고', 11);
insert into tbl_category(category_name, parent_id) values ('미니냉장고', 11);
insert into tbl_category(category_name, parent_id) values ('김치냉장고', 11);

-- 청소기
insert into tbl_category(category_name, parent_id) values ('무선/스틱청소기', 12);
insert into tbl_category(category_name, parent_id) values ('진공청소기', 12);
insert into tbl_category(category_name, parent_id) values ('로봇청소기', 12);
insert into tbl_category(category_name, parent_id) values ('스팀청소기', 12);

-- 세탁기/건조기
insert into tbl_category(category_name, parent_id) values ('세탁기', 13);
insert into tbl_category(category_name, parent_id) values ('건조기', 13);
insert into tbl_category(category_name, parent_id) values ('탈수기', 13);


-- === 도서
-- 여행
insert into tbl_category(category_name, parent_id) values ('국내여행', 14);
insert into tbl_category(category_name, parent_id) values ('해외여행', 14);

-- 역사
insert into tbl_category(category_name, parent_id) values ('한국사', 15);
insert into tbl_category(category_name, parent_id) values ('중국사', 15);
insert into tbl_category(category_name, parent_id) values ('서양사', 15);

-- 예술
insert into tbl_category(category_name, parent_id) values ('건축', 16);
insert into tbl_category(category_name, parent_id) values ('미술', 16);
insert into tbl_category(category_name, parent_id) values ('음악', 16);
insert into tbl_category(category_name, parent_id) values ('무용', 16);

-- 공학/과학
insert into tbl_category(category_name, parent_id) values ('화학', 17);
insert into tbl_category(category_name, parent_id) values ('수학', 17);
insert into tbl_category(category_name, parent_id) values ('물리', 17);
insert into tbl_category(category_name, parent_id) values ('공학', 17);

-- === 식품
-- 과일
insert into tbl_category(category_name, parent_id) values ('사과/배', 18);
insert into tbl_category(category_name, parent_id) values ('귤/감', 18);
insert into tbl_category(category_name, parent_id) values ('수박', 18);
insert into tbl_category(category_name, parent_id) values ('딸기', 18);

-- 채소
insert into tbl_category(category_name, parent_id) values ('콩나물', 19);
insert into tbl_category(category_name, parent_id) values ('두부', 19);
insert into tbl_category(category_name, parent_id) values ('당근', 19);
insert into tbl_category(category_name, parent_id) values ('오이', 19);

-- 생수/음료
insert into tbl_category(category_name, parent_id) values ('생수/탄산수', 20);
insert into tbl_category(category_name, parent_id) values ('과일음료', 20);
insert into tbl_category(category_name, parent_id) values ('커피', 20);
insert into tbl_category(category_name, parent_id) values ('탄산/스포츠음료', 20);

-- 수산물
insert into tbl_category(category_name, parent_id) values ('생선', 21);
insert into tbl_category(category_name, parent_id) values ('오징어', 21);
insert into tbl_category(category_name, parent_id) values ('새우', 21);
insert into tbl_category(category_name, parent_id) values ('멸치', 21);

-- 축산
insert into tbl_category(category_name, parent_id) values ('소고기', 22);
insert into tbl_category(category_name, parent_id) values ('돼지고기', 22);
insert into tbl_category(category_name, parent_id) values ('닭/오리고기', 22);
insert into tbl_category(category_name, parent_id) values ('양고기', 22);
