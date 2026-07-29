package xurshid_azizbek.com.example.nasiyabackend.payload.response;

import java.time.LocalDateTime;
import java.util.List;

public record DebtResponse(
        Integer id,
        String kind,
        Long amount,
        String takenBy,
        String takenByName,
        List<String> products,
        String note,
        LocalDateTime createdAt

) {
}
