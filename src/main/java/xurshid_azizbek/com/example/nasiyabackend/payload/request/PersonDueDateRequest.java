package xurshid_azizbek.com.example.nasiyabackend.payload.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonDueDateRequest(
        @NotNull(message = "Muddatni kiriting")
        LocalDate dueDate
) {
}
