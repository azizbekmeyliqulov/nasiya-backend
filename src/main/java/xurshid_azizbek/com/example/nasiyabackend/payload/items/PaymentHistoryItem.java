package xurshid_azizbek.com.example.nasiyabackend.payload.items;

import java.time.LocalDateTime;

public record PaymentHistoryItem (
        String row,   // "payment"
        Integer id,
        Long amount,
        Long before,
        Long after,
        LocalDateTime createdAt
 )  implements HistoryItem {
}
