package xurshid_azizbek.com.example.nasiyabackend.payload.response;

import java.time.LocalDateTime;

public record PaymentResponse(
        Integer id,
        Long before,
        Long amount,
        Long after,
        LocalDateTime createdAt
) {
}