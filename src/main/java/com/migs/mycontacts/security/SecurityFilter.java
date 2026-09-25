package com.migs.mycontacts.security;

import com.migs.mycontacts.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @NonNull
    private TokenService tokenService;

    @NonNull
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String tokenJwt = this.getToken(request);

        if (tokenJwt != null) {
            String subject = tokenService.getSubject(tokenJwt);

            if (subject != null) {
                var usuario = usuarioRepository.findByUsername(subject);

                if (usuario.isPresent()) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                        usuario.get(), null, usuario.get().getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            return null;
        }

        return request.getHeader("Authorization").replace("Bearer ", "").trim();
    }
}
