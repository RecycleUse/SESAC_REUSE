package com.sesac.reuse.repository;

import com.sesac.reuse.model.entity.Image;
import com.sesac.reuse.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  //엔티티, PK로 지정한 컬럼의 데이터 타입
public interface ItemRepository extends JpaRepository<Item, String> {
    // item_name을 기준으로 해당 키워드가 포함된 것을 찾겠다.

//    Item findByitem_nameContaining(String item_name);

    List<Item> findByItemNameContaining(String itemName);
}