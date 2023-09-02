package com.sesac.reuse.entity.member;

import com.sesac.reuse.base.BaseEntity;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.reply.Reply;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String pw;
    private String nickname;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY) //권한을 별도의 엔티티가 아닌 Member엔티티에서 관리하도록 하는 어노테이션, 간편용, 찐은 별도테이블 구성하는게 좋음
    private Set<MemberRole> roleSet = new HashSet<>();

    private boolean del; //탈퇴여부

    @Enumerated(EnumType.STRING)
    private SocialSignUpInfo social;

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL) // 연관관계주인X
    private List<Board> boards;

    @OneToMany(mappedBy="member", cascade =CascadeType.ALL)
    private List<Reply> replies;


    public void encrptyPassword(String encrptedPw) {
        this.pw = encrptedPw;
    }

    public void addRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);
    }

    public void setSocial(SocialSignUpInfo social) {
        this.social = social;
    }


    public void changeNickname(String nickname) {

        this.nickname = nickname;
    }

    public void changePw(String encoedPw) {
        this.pw = encoedPw;
    }
}
