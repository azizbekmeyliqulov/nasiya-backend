package xurshid_azizbek.com.example.nasiyabackend.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRegister {
    private String fullName;
    private String phoneNumber;
    private String email;
    private int age;
    private String password;
}
