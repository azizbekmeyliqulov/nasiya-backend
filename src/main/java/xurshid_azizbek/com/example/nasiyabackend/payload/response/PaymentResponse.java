package xurshid_azizbek.com.example.nasiyabackend.payload.response;

public record PaymentResponse(
        Long outstanding,
        Long paid,
        Long remaining
) {
}
