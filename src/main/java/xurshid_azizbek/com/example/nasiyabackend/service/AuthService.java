package xurshid_azizbek.com.example.nasiyabackend.service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.Role;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.auth.AuthLogin;
import xurshid_azizbek.com.example.nasiyabackend.payload.auth.AuthRegister;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.AuthResponse;
import xurshid_azizbek.com.example.nasiyabackend.repository.UserRepository;
import xurshid_azizbek.com.example.nasiyabackend.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    public ApiResponse registerUser(AuthRegister authRegister, Role role) {
        boolean b = userRepository.existsByEmail(authRegister.getEmail());
        if (b) {
            return new ApiResponse("User already exists", false, HttpStatus.ALREADY_REPORTED, null);
        }

        User user = User.builder()
                .email(authRegister.getEmail())
                .fullName(authRegister.getFullName())
                .age(authRegister.getAge())
                .phoneNumber(authRegister.getPhoneNumber())
                .password(passwordEncoder.encode(authRegister.getPassword()))
                .role(role)
                .enabled(true)
                .build();
        userRepository.save(user);
        return new ApiResponse("User registered", true, HttpStatus.OK, null);
    }


    public ApiResponse login(AuthLogin authLogin){
        User user = userRepository.findByEmail(authLogin.getEmail()).orElse(null);
        if(user == null){
            return new ApiResponse("User not found", false, HttpStatus.NOT_FOUND, null);
        }

        if(passwordEncoder.matches(authLogin.getPassword(), user.getPassword())){
            String token = jwtProvider.generateToken(authLogin.getEmail());
            AuthResponse authResponse=new AuthResponse(user.getId(), token,user.getRole());
            return new ApiResponse("Success", true, HttpStatus.OK, authResponse );
        }

        return new ApiResponse("Wrong password", false, HttpStatus.BAD_REQUEST, null);
    }




}
