package xurshid_azizbek.com.example.nasiyabackend.payload.response;

public record UserSettingsResponse(
        String groupLabel,
        Long goodLimit,
        Long badLimit
) {
}
