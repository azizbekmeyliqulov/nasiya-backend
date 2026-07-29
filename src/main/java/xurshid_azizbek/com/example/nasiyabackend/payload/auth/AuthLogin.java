package xurshid_azizbek.com.example.nasiyabackend.payload.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthLogin {

    private String email;

    private String password;
}
