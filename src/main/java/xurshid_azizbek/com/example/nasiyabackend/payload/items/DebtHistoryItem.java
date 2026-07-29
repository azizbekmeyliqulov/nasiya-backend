package xurshid_azizbek.com.example.nasiyabackend.payload.items;

import java.time.LocalDateTime;
import java.util.List;

public record DebtHistoryItem(
        String row,   // "debt"
        Integer id, String kind, Long amount,
        String takenBy, String takenByName,
        List<String> products,
        LocalDateTime createdAt
) implements HistoryItem {}
