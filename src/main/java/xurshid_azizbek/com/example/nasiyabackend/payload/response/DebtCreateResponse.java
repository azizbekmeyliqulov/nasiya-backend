package xurshid_azizbek.com.example.nasiyabackend.payload.response;

import java.time.LocalDate;

public record DebtCreateResponse (
        DebtResponse debt,
        Long balance,
        LocalDate dueDate
){
}
