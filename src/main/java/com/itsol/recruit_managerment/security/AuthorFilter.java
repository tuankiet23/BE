package com.itsol.recruit_managerment.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsol.recruit_managerment.repositories.IUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yaml.snakeyaml.events.Event;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;


public class AuthorFilter extends OncePerRequestFilter {
    @Autowired
    IUserRespository iUserRespository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorHeader =request.getHeader("Authorization");
        if(authorHeader != null && authorHeader.startsWith("Bearer ")){
            try {
                String token =authorHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());// truongbb - secret key không nên fix cứng như này, nên mã hóa 1 lớp và để ở config
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT =verifier.verify(token);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//                String userID = decodedJWT.getId();
//                iUserRespository.getUserById( );
//                new ObjectMapper().writeValue(response.getOutputStream(), userID);
                List<GrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            }catch (Exception e){
                response.addHeader("error", e.getMessage());
                response.sendError(FORBIDDEN.value());
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }
}
