package com.example.fam.security

import com.example.fam.service.impl.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
class SecurityConfig(val unauthorizedHandler: JwtAuthenticationEntryPoint,
                     val bCryptPasswordEncoder: BCryptPasswordEncoder,
                     val customUserDetailsService: CustomUserDetailsService,
                     val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebSecurityConfigurerAdapter() {


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder)
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    override fun configure(http: HttpSecurity?) {
        http!!.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/api/users/register").permitAll()
                .antMatchers("/api/users/login").permitAll()
                .antMatchers("/api/users/resetPassword").permitAll()
                .antMatchers("/api/users/forgotPassword").permitAll()
                .antMatchers("/api/contact").permitAll()
                .antMatchers("/h2/**").permitAll()
                .anyRequest().authenticated();
        http.headers().frameOptions().disable();


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}