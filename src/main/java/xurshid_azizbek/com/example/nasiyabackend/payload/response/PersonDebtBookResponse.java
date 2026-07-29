package xurshid_azizbek.com.example.nasiyabackend.payload.response;

import java.util.List;

public record PersonDebtBookResponse(
        PersonResponse person,
        MahallaShortResponse mahalla,
        List<DebtResponse> active,
        List<Object> history
) {
}
