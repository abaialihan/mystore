package com.abai.mystore.security.jwt;

import com.abai.mystore.entity.Role;
import com.abai.mystore.security.jwt.Exeptions.JwtAuthenticationExeptions;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/** Класс который генерит нам token **/
@Component
public class JwtTokenProvider {

    //поле секрет. на основе этого поле происходит генерцаия и дишифрация токена
    @Value("${jwt.token.secret}")
    private String secret;

    //время за которое токен будет заекспайрен
    @Value("${jwt.token.expired}")
    private long validityMilliSec;

    @Autowired
    private UserDetailsService userDetailsService;

    // шифруем пароль пользователей
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @PostConstruct
    protected void init() {secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    // метод который создает, настраивает и возвращает токен
    public String createToken(String username, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRolesNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMilliSec);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    //возвращает аутентикаию
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,
                "",
                userDetails.getAuthorities());
    }

    // возвращает имя юзера по токену
    public String getUsername(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }


    // разрешить или нет
    public String resolveToken(HttpServletRequest reg){
        String bearerToken = reg.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer_")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    //проверяем токен на валидность
    public boolean isValidateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claimsJws.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationExeptions("JWT token is expired or invalid");
        }
    }

    //возвращает имена ролей
    private List<String> getRolesNames(List<Role> userRoles){
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> {
            result.add(role.getName());
        });

        return result;
    }
}
