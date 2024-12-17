package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
// 로그인 시 사용할 UserSecurityService는 스프링 시큐리티가 제공하는 UserDetailsService 인터페이스를 구현해야 함.
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override // 사용자명(username)으로 스프링 시큐리티의 사용자(User) 객체를 조회하여 리턴하는 메서드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = userRepository.findByUsername(username); // 사용자명으로 SiteUser 객체를 조회

        if(_siteUser.isEmpty()){ // 사용자명에 해당하는 데이터가 없을 경우에는 UsernameNotFoundException을 발생
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(username)){ // 사용자명이 ‘admin’인 경우에는 ADMIN 권한(ROLE_ADMIN)을 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }else{ // 이외의 경우에는 USER 권한(ROLE_USER)을 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities); // 해당 객체는 스프링 시큐리티에서 사용
    }
}
