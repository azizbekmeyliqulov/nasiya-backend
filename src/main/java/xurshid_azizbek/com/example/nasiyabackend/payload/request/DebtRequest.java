package xurshid_azizbek.com.example.nasiyabackend.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.TakenBy;

import java.time.LocalDate;
import java.util.List;

public record DebtRequest(
        @NotNull(message = "Eng kami 1 000 so'm")
        @Min(value = 1000, message = "Eng kami 1 000 so'm")
        Long amount,

        @NotEmpty(message = "Kamida 1 ta mahsulot kiriting")
        List<String> products,

        TakenBy takenBy,
        String takenByName,
        String note,
        LocalDate dueDate
) {
}
