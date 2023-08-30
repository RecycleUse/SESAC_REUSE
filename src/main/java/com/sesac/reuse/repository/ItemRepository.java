package com.sesac.reuse.repository;

import com.sesac.reuse.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  //엔티티, PK로 지정한 컬럼의 데이터 타입
public interface ItemRepository extends JpaRepository<Item, String> {
    // ItemName을 기준으로 해당 키워드가 포함된 것을 찾겠다.

    Item findByItemNameContaining(String itemName);
}