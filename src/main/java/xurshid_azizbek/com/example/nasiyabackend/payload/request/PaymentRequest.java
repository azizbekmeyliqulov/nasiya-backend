package xurshid_azizbek.com.example.nasiyabackend.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull(message = "Summani to'g'ri kiriting")
        @Min(value = 1, message = "Summani to'g'ri kiriting")
        Long amount
) {
}
