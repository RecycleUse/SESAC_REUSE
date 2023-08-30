package com.sesac.reuse.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity  // JPA를 사용하여 테이블과 매핑할 클래스임을 나타냅니다.
@Getter  // Lombok을 사용하여 getter 메소드를 자동으로 생성합니다.
@Setter  // Lombok을 사용하여 setter 메소드를 자동으로 생성합니다.
@RequiredArgsConstructor  // Lombok을 사용하여 final이나 @NonNull 필드만 매개변수로 갖는 생성자를 생성합니다.
public class User {

    @Id  // 해당 프로퍼티가 테이블의 기본 키 역할을 한다는 것을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본 키 생성 전략을 지정합니다. IDENTITY는 데이터베이스에 위임하는 방식입니다.
    private Long user_id;

    private String email;  // 사용자의 이메일 주소를 저장하는 프로퍼티

    private String nickname;  // 사용자의 별명을 저장하는 프로퍼티

//    private String pw;

    // email과 nickname을 매개변수로 받는 생성자입니다.
    // Lombok의 @RequiredArgsConstructor 어노테이션으로 인해 필요없게 될 수도 있습니다.
    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
//        this.pw = pw;
    }
}
