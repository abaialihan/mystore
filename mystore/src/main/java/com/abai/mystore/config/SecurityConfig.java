package com.abai.mystore.config;

import com.abai.mystore.security.jwt.JwtConfigurer;
import com.abai.mystore.security.jwt.JwtTokenProvider;
import com.abai.mystore.service.UserService;
import com.abai.mystore.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //метод конфигурации
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable() //отключаем то что не нужно
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //сессию для каждого Юзера не создаем
                .and()
                .authorizeRequests() //авторизация запроса
                .antMatchers(LOGIN_ENDPOINT).permitAll() //запрос логин доступен всем
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN") // а админский логин только у кого роль админ
                .anyRequest().authenticated() //все остальные запросы должны быть аутентицированы
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider)); //передаем конфигурацию, где мы проверяем каждый запрос
    }
}
