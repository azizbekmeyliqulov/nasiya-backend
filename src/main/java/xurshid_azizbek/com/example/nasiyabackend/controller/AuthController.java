package xurshid_azizbek.com.example.nasiyabackend.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.Role;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.auth.AuthLogin;
import xurshid_azizbek.com.example.nasiyabackend.payload.auth.AuthRegister;
import xurshid_azizbek.com.example.nasiyabackend.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody AuthRegister authRegister,
                                                @RequestParam Role role){
        ApiResponse apiResponse = authService.registerUser(authRegister,role);
        return ResponseEntity.ok(apiResponse);
    }



    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthLogin authLogin){
        ApiResponse apiResponse = authService.login(authLogin);
        return ResponseEntity.ok(apiResponse);
    }
}
