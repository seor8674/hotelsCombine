package com.example.hotelscombine.web.member.service;

import com.example.hotelscombine.util.SecurityUtil;
import com.example.hotelscombine.web.authority.Authority;
import com.example.hotelscombine.web.member.domain.Member;
import com.example.hotelscombine.web.member.domain.MemberRepository;
import com.example.hotelscombine.web.member.dto.MemberResponseDto;
import com.example.hotelscombine.web.member.dto.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signup(MemberSignUpDto memberSignUpDto){
        if(memberRepository.findOneWithAuthoritiesByemail(memberSignUpDto.getEmail()).orElse(null)!=null){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder()
                .email(memberSignUpDto.getEmail())
                .password(memberSignUpDto.getPassword())
                .name(memberSignUpDto.getName())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMemberWithAuthorities(String email){
        return memberRepository.findOneWithAuthoritiesByemail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMyMemberWithAuthorities(){
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByemail);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMemeberInfo(String email){
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::toResponseDto)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }


    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo(){
        return null;
    }

}
