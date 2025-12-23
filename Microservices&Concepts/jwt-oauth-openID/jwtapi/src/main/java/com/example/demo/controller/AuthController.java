package com.example.demo.controller;

import com.example.demo.auth.JwtUtil;
import com.example.demo.model.User;
import com.example.demo.model.request.LoginReq;
import com.example.demo.model.response.ErrorRes;
import com.example.demo.model.response.LoginRes;
import com.example.demo.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            User user = userRepository.findByEmail(loginReq.getEmail());
            if (user == null) {
                throw new BadCredentialsException("User not found");
            }
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(loginReq.getEmail(), token);
            return ResponseEntity.ok(loginRes);
        } catch (BadCredentialsException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginReq loginReq) {
        try {
            User user = new User();
            user.setEmail(loginReq.getEmail());
            user.setPassword(loginReq.getPassword());
            userRepository.save(user);
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(loginReq.getEmail(), token);
            return ResponseEntity.ok(loginRes);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}