// package com.cards.cards.services;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.cards.cards.models.AuthenticationResponse;
// import com.cards.cards.models.User;

// import lombok.AllArgsConstructor;
// import lombok.NoArgsConstructor;
// import lombok.RequiredArgsConstructor;

// @Service
// @AllArgsConstructor
// public class AuthenticationService {

//     private final PasswordEncoder passwordEncoder;

//     private final JwtService jwtService;

//     private final AuthenticationManager authenticationManager;

//     // public AuthenticationService(PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager){
//     //     this.passwordEncoder = passwordEncoder;
//     //     this.jwtService = jwtService;
//     //     this.authenticationManager = authenticationManager;
//     // }

//     public AuthenticationResponse register(User request) {

//         User user = new User();
//         user.setPassword(passwordEncoder.encode(request.getPassword()));
//         user.setRole(request.getRole());

//         String token = jwtService.generateToken(user);

//         return new AuthenticationResponse(token);
//     }

//     public AuthenticationResponse authenticate(User request) {
//         authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         request.getEmail(),
//                         request.getPassword()));

//         // TODO: find the user in the database
//         String token = jwtService.generateToken(request);
//         return new AuthenticationResponse(token);
//     }

// }
