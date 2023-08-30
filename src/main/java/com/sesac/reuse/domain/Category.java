package com.sesac.reuse.domain;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "name")
    private String categoryName;
}
