package com.sesac.reuse.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name="user")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String pw;
    private String nickname;

    @ElementCollection(fetch = FetchType.LAZY) //권한을 별도의 엔티티가 아닌 Member엔티티에서 관리하도록 하는 어노테이션, 간편용, 찐은 별도테이블 구성하는게 좋음
    private Set<MemberRole> roleSet = new HashSet<>();

    private boolean del;
    private SocialSignUpInfo social;

    public void encrptyPassword(String encrptedPw) {
        this.pw = encrptedPw;
    }

    public void addRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);
    }

    public void setSocial(SocialSignUpInfo social) {
        this.social = social;
    }
}
