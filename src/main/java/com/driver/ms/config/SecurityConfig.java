package com.driver.ms.config;


/*@Configuration
@EnableWebSecurity*/
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {


     /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }*/
}
