//package org.example.licenta.configuration;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.example.licenta.db.entities.RoleEntity;
//import org.example.licenta.db.entities.UserEntity;
//import org.example.licenta.db.repositories.UserRepository;
//import org.example.licenta.dto.UserFullDto;
//import org.example.licenta.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Component
//public class UserAuthenticationProvider {
//
//    @Value("${security.jwt.token.secret-key:secret-key}")
//    private String secretKey;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostConstruct
//    protected void init() {
//        // this is to avoid having the raw secret key available in the JVM
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }
//
//    public String createToken(UserFullDto user) {
//        Date now = new Date();
//
//        String scope = user.getUserRole().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//
//        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//        return JWT.create()
//                .withSubject(user.getUserId())
//                .withIssuedAt(now)
//                .withClaim("firstName", user.getUserFirstName())
//                .withClaim("lastName", user.getUserName())
//                .withClaim("roles", user.getUserRole())
//                .sign(algorithm);
//    }
//
//    public Authentication validateToken(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//
//        JWTVerifier verifier = JWT.require(algorithm)
//                .build();
//
//        DecodedJWT decoded = verifier.verify(token);
//
//        UserFullDto user = UserFullDto.builder()
//                .userId(decoded.getSubject())
//                .userFirstName(decoded.getClaim("firstName").asString())
//                .userName(decoded.getClaim("lastName").asString())
//                .userRole(decoded.getClaim("roles").asString())
//                .build();
//
//        Set<RoleEntity> authorities = new HashSet<>();
//        authorities.add(new RoleEntity(user.getUserRole()));
//
//        return new UsernamePasswordAuthenticationToken(user, null, authorities);
//    }
//
//    public Authentication validateTokenStrongly(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secretKey);
//
//        JWTVerifier verifier = JWT.require(algorithm)
//                .build();
//
//        DecodedJWT decoded = verifier.verify(token);
//
//        UserEntity user = userRepository.findById(decoded.getSubject()).get();
//
//        Set<RoleEntity> authorities = new HashSet<>();
//        authorities.add(new RoleEntity(user.getUserRole()));
//
//        return new UsernamePasswordAuthenticationToken(user, null, authorities);
//    }
//}
