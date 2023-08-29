package com.sesac.reuse.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="app_user")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String pw;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private boolean del;
}
