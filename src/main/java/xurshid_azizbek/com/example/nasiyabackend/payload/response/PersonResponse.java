package xurshid_azizbek.com.example.nasiyabackend.payload.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonResponse(
        Integer id,
        Integer mahallaId,
        Integer number,
        String firstName,
        String lastName,
        String nickname,
        String phone,
        LocalDate dueDate,
        Long balance,
        LocalDateTime createdAt

) {
}
