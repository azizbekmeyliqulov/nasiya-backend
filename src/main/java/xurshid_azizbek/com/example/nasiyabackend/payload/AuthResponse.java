package xurshid_azizbek.com.example.nasiyabackend.payload;


import xurshid_azizbek.com.example.nasiyabackend.entity.enums.Role;

public record AuthResponse(
        Integer user_id,
       String token,
        Role role

) {
}
