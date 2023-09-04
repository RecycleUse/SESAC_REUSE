package com.sesac.reuse.repository.member;

import com.sesac.reuse.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmail(String email);  // email을 이용해서 회원 정보 찾음
}
