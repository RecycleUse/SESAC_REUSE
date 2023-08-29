package com.sesac.reuse.repository;

import com.sesac.reuse.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    public Optional<Member> findByEmail(String email);
}
