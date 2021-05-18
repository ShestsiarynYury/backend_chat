package app.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import app.filter.JwtAuthenticationFilter;
import app.security.JwtAuthenticationEntryPoint;
import app.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
	prePostEnabled = true,
	securedEnabled = true
)
public class MySequrityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired private UserService userService;
    @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use DelegatingPasswordEncoder
		auth.userDetailsService(this.userService).passwordEncoder(this.passwordEncoder());
    }
    
    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
    }
    
    @Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity
			.cors()
			.and()
			.csrf()
			.disable()
			.headers()
			.frameOptions()
			.deny()
			.and()
			.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			// dont authenticate this particular request
			.authorizeRequests()
			.antMatchers("/signin", "/register", "/h2-console/**", "/users/**", "/chat/**").permitAll()
			.anyRequest().authenticated();
			// make sure we use stateless session; session won't be used to
			// store user's state.
			// Add a filter to validate the tokens with every request
			//.and()
		httpSecurity.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/", "/index.html", "/*.js", "/*.css", "/static/**", "/img/**", "/*.ico", "/h2-console/**", "/data/**", "/chat/**");
	}
}
