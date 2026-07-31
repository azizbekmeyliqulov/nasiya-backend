package xurshid_azizbek.com.example.nasiyabackend.payload.response;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record MahallaResponse(
        Integer id,
        String name,
        Long peopleCount,
        LocalDateTime createdAt
) {
}
