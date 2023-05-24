package com.openclassrooms.shopmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
				.authorizeRequests()
//                .antMatchers("/css/**", "/products").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")

                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        ;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
//        authBuilder
//                .inMemoryAuthentication()
//                .withUser(User.withDefaultPasswordEncoder().username("admin").password("password").roles("ADMIN"));
//    }

}