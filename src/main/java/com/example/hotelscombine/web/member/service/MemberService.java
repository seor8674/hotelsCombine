package com.example.hotelscombine.web.member.service;

import com.example.hotelscombine.web.member.domain.MemberRepository;
import com.example.hotelscombine.web.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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
