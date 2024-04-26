package org.example.licenta.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.example.licenta.services.TokenService;
import org.example.licenta.utils.RSAKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final RSAKeyProperties keys;

    @Autowired
    private TokenService tokenService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService) throws Exception {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        return new ProviderManager(daoProvider);
    }

    //    TODO: GRIJA CU ENDPOINT URILE IN BACKEND + ROLURILE MORTII LOR (LOGAREA SE FACE DOAR CU TOKEN UL VIETII VEZI
//     FILMULET LA SFARSIT https://www.youtube.com/watch?v=TeBt0Ike_Tk)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
//                    toate permisiunile
                    authorize.requestMatchers(new AntPathRequestMatcher("/home")).permitAll();
                    authorize.requestMatchers(new AntPathRequestMatcher("/login")).permitAll();
                    authorize.requestMatchers(new AntPathRequestMatcher("/users/create")).permitAll();

//                    permisiuni admin
                    authorize.requestMatchers(new AntPathRequestMatcher("/users")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/teams")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/teams/{id}")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/teams/{id}/delete")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/teams/create")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/reservations")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/departments")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/departments/{id}")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/departments/{id}/delete")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/departments/create")).hasRole("ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/departments/{id}/update")).hasRole("ADMIN");

//                    permisiuni user si admin
                    authorize.requestMatchers(new AntPathRequestMatcher("/welcome")).hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/users/{id}")).hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/users/{id}/delete")).hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/users/{id}/update")).hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/reservations/{id}")).hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/reservations/{id}/delete")).hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/reservations/{id}/update")).hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers(new AntPathRequestMatcher("/reservations/create")).hasAnyRole("USER", "ADMIN");
                });


        http.oauth2ResourceServer(configurer -> configurer.jwt(
                jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
}