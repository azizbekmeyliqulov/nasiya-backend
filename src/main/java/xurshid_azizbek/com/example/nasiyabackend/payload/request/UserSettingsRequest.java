package xurshid_azizbek.com.example.nasiyabackend.payload.request;

import jakarta.validation.constraints.NotBlank;

public record UserSettingsRequest(
        @NotBlank
        String groupLabel,
        Long goodLimit,
        Long badLimit
) {
}
