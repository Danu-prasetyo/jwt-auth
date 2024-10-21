package com.example.userAuthentication.Controller;


import com.example.userAuthentication.Model.AuthRequest;
import com.example.userAuthentication.Model.AuthResponse;
import com.example.userAuthentication.Model.User;
import com.example.userAuthentication.Security.CustomUserDetails;
import com.example.userAuthentication.Security.JWTUtil;
import com.example.userAuthentication.Service.UserService;
import com.example.userAuthentication.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    // Endpoint untuk mendapatkan semua pengguna
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint untuk mendapatkan pengguna berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Endpoint untuk mendaftar pengguna baru
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Endpoint untuk memperbarui pengguna
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint untuk menghapus pengguna
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "409");
            response.put("message", "User already exist");
            return ResponseEntity.status(404).body(response);
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER"); // Set default role to "USER"
        }

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            // Log error untuk membantu debugging
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        final String jwtToken = jwtUtil.generateToken(authRequest.getUsername());
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        // Mapping ke DTO
        UserResponse userResponse = new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(userResponse);
    }

}
