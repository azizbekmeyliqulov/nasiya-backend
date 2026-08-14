package xurshid_azizbek.com.example.nasiyabackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xurshid_azizbek.com.example.nasiyabackend.payload.dto.TopDebtorDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatResponse {
    private Long totalActiveDebt;
    private Long totalSettledDebt;
    private Long totalDebtsThisMonth;
     private Long totalOverdueDebt;
     private Long totalActiveClients;
    private List<TopDebtorDto> topDebtors;
}