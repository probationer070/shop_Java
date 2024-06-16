	package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;


// 현재는 
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userService;
	
	@Bean	// 특정 Http 요청에 대한 웹 기반 보안 구성. 인증/인가 및 로그인 및 로그아웃 설정
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		requestCache.setMatchingRequestParameterName(null);
		
		http.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			.requestCache(request -> request.requestCache(requestCache))
			.httpBasic(httpBasic -> httpBasic.disable())
			.sessionManagement((session) -> session
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
			.formLogin(formlogin -> 
				formlogin.loginPage("/signin")
						 .loginProcessingUrl("/signin")
						 .usernameParameter("username")
						 .passwordParameter("password")
						 .defaultSuccessUrl("/main", true).permitAll())
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/signin")
					.invalidateHttpSession(true))
			.authorizeHttpRequests(authReq ->
					authReq.requestMatchers("/", "/itemView/**", "/signin", "/signup", "/uploads/**").permitAll()
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					.anyRequest().authenticated());
		return http.build();
	}
	
	@Bean	// 비밀번호 암호화를 위한 빈 등록
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Bean	// 인증관리자 설정
	public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(userService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		
		return daoAuthenticationProvider;
		
	}
}
