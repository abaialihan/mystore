package com.abai.mystore.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/** для фильтра токена
 * этот класс анализирует запрос, смотрит на токен. валидный или нет**/
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //получаем токен из запроса
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        //проверяем токен
        if(token != null && jwtTokenProvider.isValidateToken(token)){
            //если все хорошо, то передаем аутентикаию
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
             if(authentication != null){
                 SecurityContextHolder.getContext().setAuthentication(authentication);
             }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
