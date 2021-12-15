package tim13.webshop.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import tim13.webshop.shop.security.TokenUtils;
import tim13.webshop.shop.security.auth.RestAuthenticationEntryPoint;
import tim13.webshop.shop.security.auth.TokenAuthenticationFilter;
import tim13.webshop.shop.services.UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserService jwtUserDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	private TokenUtils tokenUtils;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint).and().authorizeRequests()

				.antMatchers(HttpMethod.GET, "/api/conferences").hasAuthority("READ_CONFERENCES")
				.antMatchers(HttpMethod.GET, "/api/courses").hasAuthority("READ_COURSES")
				.antMatchers(HttpMethod.GET, "/api/equipments").hasAuthority("READ_EQUIPMENT")
				.antMatchers(HttpMethod.GET, "/api/equipment-shopping-carts").hasAuthority("READ_ESC")
				.antMatchers(HttpMethod.POST, "/api/equipment-shopping-carts/add-item").hasAuthority("ADD_ITEM_TO_ESC")
				.antMatchers(HttpMethod.DELETE, "/api/equipment-shopping-carts/remove-item/{id}")
				.hasAuthority("REMOVE_ITEM_FROM_ESC").antMatchers(HttpMethod.GET, "/api/service-shopping-carts")
				.hasAuthority("READ_SSC").antMatchers(HttpMethod.POST, "/api/service-shopping-carts/add-item")
				.hasAuthority("ADD_ITEM_TO_SSC")
				.antMatchers(HttpMethod.DELETE, "/api/service-shopping-carts/remove-item/{id}")
				.hasAuthority("REMOVE_ITEM_FROM_SSC").antMatchers(HttpMethod.POST, "/api/orders")
				.hasAuthority("ADD_ORDER")

				.anyRequest().permitAll().and().cors().and()
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class);

		http.csrf().disable();

		http.headers().xssProtection().and().contentSecurityPolicy("script-src 'self'");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/sign-up");
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg");
	}

}
