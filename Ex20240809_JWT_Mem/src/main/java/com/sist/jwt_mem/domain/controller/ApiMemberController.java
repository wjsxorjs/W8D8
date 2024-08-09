package com.sist.jwt_mem.domain.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.jwt_mem.domain.member.entity.Member;
import com.sist.jwt_mem.domain.member.service.MemberService;
import com.sist.jwt_mem.global.jwt.JwtProvider;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/member")
public class ApiMemberController {
    
    @Autowired
    private MemberService m_service;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(Member mvo) {
        Map<String, Object> m_map = new HashMap<>();
        
        // 파라미터로 전달된 member의 mId값을 가지고 검색

        if(mvo.getMId() != null){
            
            Member m = m_service.memInfo(mvo.getMId(), mvo.getMPw());

            
        }
        return m_map;
    }

    @PostMapping("/reg")
    @ResponseBody
    public Map<String, Object> reg(Member mvo) {
        Map<String, Object> m_map = new HashMap<>();
        

        if(mvo.getMId() != null){
            String pw_enc = passwordEncoder.encode(mvo.getMPw());
            mvo.setMPw(pw_enc);
            
            Member result = m_service.regMember(mvo);

            m_map.put("result", result);
        }
        return m_map;
    }
    


}
