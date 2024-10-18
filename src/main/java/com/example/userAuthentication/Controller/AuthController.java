//package com.example.userAuthentication.Controller;
//
//import com.example.userAuthentication.Model.User;
//import com.example.userAuthentication.Security.JwtUtil;
//import com.example.userAuthentication.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public String login(@RequestBody User loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        // Jika berhasil, buat dan kembalikan token JWT
//        String token = jwtUtil.generateToken(authentication.getName());
//        return token;
//    }
//
//    @PostMapping("/register")
//    public User register(@RequestBody User newUser) {
//        return userService.createUser(newUser);
//    }
//}