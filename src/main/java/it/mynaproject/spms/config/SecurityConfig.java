package it.mynaproject.spms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.csrf.CsrfFilter;

import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;

import it.mynaproject.spms.filter.CorsFilter;
import it.mynaproject.spms.login.LoginFailureHandler;
import it.mynaproject.spms.login.LoginSuccessHandler;
import it.mynaproject.spms.login.LogoutSuccessHandler;
import it.mynaproject.spms.login.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private UserDetailsService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.addFilterAfter(new CorsFilter(), SecurityContextHolderFilter.class);

		CsrfTokenResponseHeaderBindingFilter csrfTokenFilter = new CsrfTokenResponseHeaderBindingFilter();
		http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);

		http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint()).and()

				.authorizeRequests()

				.antMatchers(HttpMethod.GET, "/token").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/swagger/**").permitAll()

				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/resp/**").hasRole("RESP")
				.antMatchers("/**").hasRole("USER")

				.anyRequest().authenticated()
				.and().formLogin().loginProcessingUrl("/authenticate")
				.successHandler(new LoginSuccessHandler())
				.failureHandler(new LoginFailureHandler())

				.and().httpBasic()

				.and().logout().logoutSuccessHandler(new LogoutSuccessHandler());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Pbkdf2CustomEncoder();
	}
}
