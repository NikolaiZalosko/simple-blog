package com.nickz.blog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nickz.blog.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	// Setting Service to find User in the database.
	// And Setting PassswordEncoder
	auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
	httpSecurity.csrf().disable();
	httpSecurity.authorizeRequests().antMatchers("/register").not().fullyAuthenticated()
		.antMatchers("/register**", "/js/**", "/css/**", "/images/**", "/webjars/**", "/vendor/**").permitAll()
		.anyRequest().authenticated().and().formLogin().loginProcessingUrl("/loginProcess")
		.defaultSuccessUrl("/").failureUrl("/login?error").usernameParameter("username")
		.passwordParameter("password").loginPage("/login").permitAll().and().logout()
		.invalidateHttpSession(true).clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
		.permitAll();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
	DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
	auth.setUserDetailsService(userService);
	auth.setPasswordEncoder(passwordEncoder());
	return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(authenticationProvider());
    }

}
