package com.sesac.reuse.entity.itemidgenerator;

import com.sesac.reuse.component.BeanUtil;
import com.sesac.reuse.entity.item.Item;
import com.sesac.reuse.service.itemAdmin.ItemCustomIdSevice;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class ItemCustomIdGenerator implements IdentifierGenerator {

    private ItemCustomIdSevice itemCustomIdSevice;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        if (itemCustomIdSevice == null) {
            // ItemRepository의 @Query문은 static이 불가능하여 service까지 구현하여 public으로 변경
            // BeanUtil 유틸리티 클래스 : 빈 프로그래밍 방식으로 주입
            itemCustomIdSevice = BeanUtil.getBean(ItemCustomIdSevice.class);
        }

        ItemCustomKeyEntity entity = (ItemCustomKeyEntity) object; // 현재 저장되려는 엔터티 인스턴스
        System.out.println(entity);

        Item item = entity.getItem(); // 연관된 아이템 엔터티 가져오기

        if (item == null || item.getId() == null) {
            throw new HibernateException("Missing category id");  // 카테고리 정보가 없으면 예외 발생
        }

        String categoryId = String.valueOf(item.getCategory().getCategoryId()); // 카테고리 ID 가져오기

        System.out.println("entity:" + entity.getItem().getId());
        System.out.println("category_id:" + categoryId);

        // 현재 알파벳 접두어에 따른 ID 카운트를 가져오는 로직 : 데이터베이스 조회 등을 통해 가져올 수 있음
        int count = 0;
        System.out.println(itemCustomIdSevice.generateId(categoryId));
        return categoryId + String.format("%03d", count + 1); // 새 ID 생성해서 반환
    }
    
}
