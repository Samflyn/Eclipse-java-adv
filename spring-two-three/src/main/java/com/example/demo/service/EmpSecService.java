package com.example.demo.service;

import com.example.demo.repository.EmpSecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmpSecService implements UserDetailsService {
    private final EmpSecRepository empSecRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(empSecRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
