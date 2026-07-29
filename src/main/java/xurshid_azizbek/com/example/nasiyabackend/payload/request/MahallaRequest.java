package xurshid_azizbek.com.example.nasiyabackend.payload.request;

import jakarta.validation.constraints.NotBlank;

public record MahallaRequest(
        @NotBlank(message = "Mahalla nomini kiriting")
        String name
) {
}
