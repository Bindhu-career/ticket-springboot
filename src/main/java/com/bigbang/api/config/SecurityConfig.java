package com.bigbang.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import com.bigbang.api.util.Constants;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	 private static final String[] AUTH_WHITELIST = {

	            // -- swagger ui
	            "/swagger-resources/**",
	            "/swagger-ui.html",
	            "/v2/api-docs",
	            "/webjars/**"
	    };
	 
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		JwtWebSecurityConfigurer.forRS256("bigbang-api", "https://ashwinachu.auth0.com/").configure(http)
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/v1/hello").permitAll()
				.antMatchers(HttpMethod.GET, "/v1/welcome").permitAll()
				.antMatchers(HttpMethod.POST, "/v1/user/register").permitAll()
				.antMatchers(HttpMethod.GET, "/v1/user/login").permitAll()
				.antMatchers(HttpMethod.GET, "/v1/user/verify/**").permitAll()
		      	.antMatchers(HttpMethod.PUT, "/v1/user/resend/**").permitAll()
		      	.antMatchers(HttpMethod.GET, "/v1/user/forget/**").permitAll()    
		      	.antMatchers(HttpMethod.GET, "/v1/user/savepass/**").permitAll()
		      	.antMatchers(HttpMethod.GET, "/v1/user/upload/**").permitAll()
		      	.antMatchers(HttpMethod.POST, "/v1/user/createchannel/**").permitAll()
				
		        .antMatchers(HttpMethod.GET, "/v1/user").authenticated()
				.antMatchers(HttpMethod.GET, "/v1/user/**").authenticated()
  			    .antMatchers(HttpMethod.PUT, "/v1/user").authenticated()
				.antMatchers(HttpMethod.DELETE, "/v1/user/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/v1/user/profile/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/v1/user/changepassword/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/v1/user/deleteuser/**").authenticated()
				.antMatchers(HttpMethod.GET, "/v1/user/slack/**").authenticated()
			

				.antMatchers(HttpMethod.GET, Constants.BILLING_API).authenticated()
				.antMatchers(HttpMethod.POST, Constants.BILLING_API).authenticated()
				.antMatchers(HttpMethod.PUT, Constants.BILLING_API).authenticated()
				.antMatchers(HttpMethod.DELETE, Constants.BILLING_API).authenticated()
				.antMatchers(HttpMethod.PUT, "/billinginfo/pay/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/billinginfo/card/**").authenticated()

				.antMatchers(HttpMethod.GET, Constants.MEETING_API).authenticated()
				.antMatchers(HttpMethod.POST, Constants.MEETING_API).authenticated()
				.antMatchers(HttpMethod.PUT, Constants.MEETING_API).authenticated()
				.antMatchers(HttpMethod.DELETE, Constants.MEETING_API).authenticated()
				
				.antMatchers(HttpMethod.GET, Constants.MESSAGING_API).authenticated()
				.antMatchers(HttpMethod.POST, Constants.MESSAGING_API).authenticated()
				.antMatchers(HttpMethod.PUT, Constants.MESSAGING_API).authenticated()
				.antMatchers(HttpMethod.DELETE, Constants.MESSAGING_API).authenticated()
				.antMatchers(HttpMethod.GET, "/messaging/user/**").authenticated()


				.antMatchers(HttpMethod.GET, Constants.MILESTONE_API).authenticated()
				.antMatchers(HttpMethod.POST, Constants.MILESTONE_API).authenticated()
				.antMatchers(HttpMethod.PUT, Constants.MILESTONE_API).authenticated()
				.antMatchers(HttpMethod.DELETE, Constants.MILESTONE_API).authenticated()

				.antMatchers(HttpMethod.GET, Constants.NOTIFICATION_API).authenticated()
				.antMatchers(HttpMethod.POST, Constants.NOTIFICATION_API).authenticated()
				.antMatchers(HttpMethod.PUT, Constants.NOTIFICATION_API).authenticated()
				.antMatchers(HttpMethod.DELETE, Constants.NOTIFICATION_API).authenticated()

				.antMatchers(HttpMethod.GET, Constants.PERFERENCE_API).authenticated()
				.antMatchers(HttpMethod.POST, Constants.PERFERENCE_API).authenticated()
				.antMatchers(HttpMethod.PUT, Constants.PERFERENCE_API).authenticated()
				.antMatchers(HttpMethod.DELETE, Constants.PERFERENCE_API).authenticated()

				.antMatchers(HttpMethod.GET, Constants.PROJECT_API).authenticated()
				.antMatchers(HttpMethod.PUT, Constants.PROJECT_API).authenticated()
				.antMatchers(HttpMethod.POST, Constants.PROJECT_API).authenticated()
				.antMatchers(HttpMethod.DELETE, Constants.PROJECT_API).authenticated()

				.antMatchers(HttpMethod.GET, Constants.TRANSACTION_API).authenticated()
				.antMatchers(HttpMethod.POST, Constants.TRANSACTION_API).authenticated()
				.antMatchers(HttpMethod.PUT, Constants.TRANSACTION_API).authenticated()
				.antMatchers(HttpMethod.DELETE, Constants.TRANSACTION_API).authenticated();

	}
	
}
