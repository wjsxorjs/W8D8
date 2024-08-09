package com.sist.jwt_mem.domain.member.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.jwt_mem.domain.member.entity.Member;
import com.sist.jwt_mem.domain.member.repository.MemberRepository;
import com.sist.jwt_mem.global.jwt.JwtProvider;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;

    //jwtprovider도 필요
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //로그인을 위한 인증
    public Member memInfo(String mid, String mpw) {
        Member member = null;
        try {
            member = memberRepository.findByMid(mid).orElseThrow(() -> new RuntimeException("존재 X"));
            
            // member가 null이 아니면 해당 id로 회원정보가 있다는 뜻이며
            // member안에 암호화된 비밀번호가 존재한다.
            // 즉, 인자로 받은 평문의 비밀번호가 member가 가지고 있는 암호화된
            // 비밀번호가 맞는 것인지 판단해야한다.
            if(member!=null){
                if(passwordEncoder.matches(mpw,member.getMPw())){
                    // jwt토큰 생성
                    Map<String, Object> m_map = new HashMap<>();
                    m_map.put("midx", member.getMIdx());
                    m_map.put("mid", member.getMId());
                    m_map.put("mpw", member.getMPw());
                    m_map.put("mname", member.getMName());

                    String accessToken = jwtProvider.genToken(m_map, 60*60*1);
                }
            }

            
            
        } catch (Exception e) {}
        return member;
    }


    // 회원정보 저장
    public Member regMember(Member mvo){
        return memberRepository.save(mvo);
    }


}