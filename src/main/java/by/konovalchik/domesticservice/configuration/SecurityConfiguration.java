package by.konovalchik.domesticservice.configuration;

import by.konovalchik.domesticservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;




@Profile("Production")
@EnableWebSecurity()
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                   .antMatchers( "/", "/guest/registration").permitAll()
                   .antMatchers("/executor/**").hasAnyRole("EXECUTOR")
                   .antMatchers("/user/**").hasAnyRole("USER")
                   .anyRequest().authenticated()
                   .and()
                .formLogin()
                   .loginPage("/guest/authorization").permitAll()
                   .defaultSuccessUrl("/",true)
//                  .failureUrl("/login.html?error=true")
                   .failureHandler(authenticationFailureHandler())
                   .and()
                .logout()
                   .permitAll();
//                 http.csrf().disable();
//        http.headers().frameOptions().disable();
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/scripts/**")
                .antMatchers("/styles/**")
                .antMatchers("/images/**")
                .antMatchers("/fonts/**");
    }



    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }


}
