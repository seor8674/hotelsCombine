package com.example.hotelscombine.web.member.domain;

import com.example.hotelscombine.web.authority.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @Column(name="member_id")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String email;

    @JsonIgnore
    private String password;

    private String name;

    private boolean activated;


    @ManyToMany
    @JoinTable(name = "user_authority"
    ,joinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "member_id")}
    ,inverseJoinColumns = {@JoinColumn(name = "authority_name",referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @Builder
    public Member(String email, String password,String name) {
        this.email = email;
        this.password = password;
        this.name=name;
    }


}
