package xurshid_azizbek.com.example.nasiyabackend.payload.response;

import java.time.LocalDateTime;

public record MahallaResponse(
        Integer id,
        String name,
        Long peopleCount,
        LocalDateTime createdAt
) {
}
