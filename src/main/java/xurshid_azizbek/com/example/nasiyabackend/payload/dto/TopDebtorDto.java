package xurshid_azizbek.com.example.nasiyabackend.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopDebtorDto {
    private Integer mahallaId;
    private String mahallaName;
    private Integer personId;
    private String firstName;
    private String lastName;
    private String nickname;
    private Long totalUnsettledAmount;
}