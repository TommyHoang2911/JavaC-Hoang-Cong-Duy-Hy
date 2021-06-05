package com.example.finalproject.security.config;

import com.example.finalproject.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        daoAuthenticationProvider.setUserDetailsService(appUserService);
        return daoAuthenticationProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Request setting to ignore security settings
        // Access to static resources (images, css, javascript) ignore security settings
        web.ignoring().antMatchers("/ images/**",
                "/ css/**",
                "/ js/**",
                "/ scss/**",
                "/ vendor/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                // Set access permissions
                // No restrictions on '/ css /', 'fonts', '/ js /' in static directory
                .antMatchers("/ css/**", "/ js/**", "/ scss/**", "/ vendor/**", "/resources/**").permitAll()
                .antMatchers("/**").permitAll().anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/notes", true).failureForwardUrl("/error")
                .and().logout().permitAll().logoutUrl("/doLogout");
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
