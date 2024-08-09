package com.sist.jwt_mem.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.jwt_mem.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    @Query("SELECT m FROM Member m WHERE m.mId = :mId")
    Optional<Member> findByMid(@Param("mId") String mId); //List의 데이터: 0~多개 | Optional의 데이터: 0~1개
}