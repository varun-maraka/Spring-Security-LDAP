package io.javabrains.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.ldapAuthentication()
			.userDnPatterns("uid={0},ou=people")
			.groupSearchBase("ou=groups")
			.contextSource()
			.url("ldap://localhost:8389/dc=springframework,dc=org")
			.and()
			.passwordCompare()
			.passwordEncoder(new LdapShaPasswordEncoder())
			.passwordAttribute("userPassword");
	}
	@Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
	
	@Override
	protected void configure(HttpSecurity httpSec) throws Exception{
		httpSec.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/auth").authenticated()
				.antMatchers("/user").hasAnyRole("USER","ADMIN")
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/h2-console/**").permitAll().anyRequest().authenticated() 
				.and().formLogin();
		
		httpSec.headers().frameOptions().sameOrigin();
		httpSec.csrf().disable(); 
		// This code make h2 database console available to access
		//httpSec.headers().frameOptions().disable();
	}
	
}
