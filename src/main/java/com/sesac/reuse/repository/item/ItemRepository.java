package com.sesac.reuse.repository.item;

import com.sesac.reuse.entity.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  //엔티티, PK로 지정한 컬럼의 데이터 타입
public interface ItemRepository extends JpaRepository<Item, String> {
    // item_name을 기준으로 해당 키워드가 포함된 것을 찾겠다.

//    Item findByitem_nameContaining(String item_name);

    List<Item> findByNameContaining(String name);


    // 페이징 처리
    Page<Item> findAll(Pageable pageable);

    // item_id 뒷 자리수 가져오기
    @Query(value = "SELECT SUBSTR(item_id, 2) FROM Item i WHERE i.category_id = :category_id ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    String findLastItemIdByCategoryId(@Param("category_id") String category_id);
}