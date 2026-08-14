package xurshid_azizbek.com.example.nasiyabackend.payload.response;

public record PersonSearchResponse(
        Integer id,
        String firstName,
        String lastName,
        String nickname,
        String phone,
        Integer number,
        Integer mahallaId,
        String mahallaName,
        Boolean overdue
) {
}
