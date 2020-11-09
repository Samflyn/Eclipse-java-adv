package com.example.demo.configurer;

import com.example.demo.service.EmpSecService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // for PreAuthorize
@RequiredArgsConstructor
public class SecurityConfigure extends WebSecurityConfigurerAdapter {
    /***
     * BasicAuthenticationFilter -> authentication from header
     * UserNamePasswordAuthenticationFilter -> from request post body
     * DefaultLoginPageGeneratingFilter -> form login page
     * DefaultLogoutPageGeneratingFilter
     * FilterSecurityInterceptor -> which handles authorization
     * Order matters for antMatchers
     * @param http
     * @throws Exception
     */

    private final EmpSecService empSecService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf by default is only for state changing calls
        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

//        auth
//                .inMemoryAuthentication()
//                .withUser("sam")
//                .password(passwordEncoder.encode("samflynn"))
//                .roles("USER", "ADMIN");

        auth
                .userDetailsService(empSecService).passwordEncoder(passwordEncoder);
    }
}
