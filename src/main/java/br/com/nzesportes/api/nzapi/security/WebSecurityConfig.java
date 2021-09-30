package br.com.nzesportes.api.nzapi.security;

import br.com.nzesportes.api.nzapi.security.jwt.AuthEntryPointJwt;
import br.com.nzesportes.api.nzapi.security.jwt.AuthenticationFilter;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthenticationFilter authenticationJwtTokenFilter() {
        return new AuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // AUTHORIZATION
                .authorizeRequests().antMatchers("/api/auth/**").permitAll().and()
                .authorizeRequests().antMatchers("/first-access/**").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/contact").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/products/details/**").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/products/**").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/categories").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/brands").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/menu").permitAll().and()
                .authorizeRequests().antMatchers( "/webhook/**").permitAll().and()
                .authorizeRequests().antMatchers("/better-send/calculate").permitAll()

                // PRODUCTS
                .antMatchers(HttpMethod.GET, "/products/categories").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
