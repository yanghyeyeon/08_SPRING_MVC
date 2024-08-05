package com.ohgiraffers.example.auth.principal;

import com.ohgiraffers.example.member.model.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class AuthPrincipal implements UserDetails, Serializable {

    private final Member member;

    // 권한 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        ArrayList<GrantedAuthority> auth = new ArrayList<>();

        auth.add(new SimpleGrantedAuthority("ROLE_" + member.getRole()));

        return List.of();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }
}
