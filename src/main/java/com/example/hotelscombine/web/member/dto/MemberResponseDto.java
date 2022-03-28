package com.example.hotelscombine.web.member.dto;

import com.example.hotelscombine.web.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {

    @NotNull
    @Size(min = 3,max = 50)
    private String email;

    @NotNull
    @Size(min = 3,max = 50)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3,max = 100)
    private String password;

    public static MemberResponseDto toResponseDto(Member member){
        return MemberResponseDto.builder()
                .name(member.getName())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();
    }

}
