package com.sesac.reuse.entity.itemidgenerator;


import com.sesac.reuse.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


// Custom ID Generation Strategy : 사용자 지정 Primary key 생성전략
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemCustomKeyEntity {

    @Id
    @GeneratedValue(generator = "custom-key-generator")
    @GenericGenerator(name = "custom-key-generator", strategy = "com.sesac.reuse.entity.itemidgenerator.ItemCustomIdGenerator")
    private String id;

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;
}
