package xurshid_azizbek.com.example.nasiyabackend.payload.request;

import jakarta.validation.constraints.NotBlank;

public record PersonRequest(
        @NotBlank(message = "Ismni kiriting")
        String firstName,
        String lastName,
        String nickname,
        String phone
) {
}
