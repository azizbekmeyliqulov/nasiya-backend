package xurshid_azizbek.com.example.nasiyabackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.DashboardStatResponse;
import xurshid_azizbek.com.example.nasiyabackend.repository.DebtRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.PersonRepository;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final DebtRepository debtRepository;
    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public ApiResponse getDashboardStatistics(User currentUser,int page,int size) {

        int safeSize = Math.min(size, 50);
        LocalDate today = LocalDate.now();
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = today.atTime(LocalTime.MAX);

        var topDebtors = debtRepository.findTopDebtors( PageRequest.of(page, safeSize), currentUser.getId());

        DashboardStatResponse statResponse = DashboardStatResponse.builder()
                .totalActiveDebt(debtRepository.calculateTotalActiveDebt(currentUser.getId()))
                .totalSettledDebt(debtRepository.calculateTotalSettledDebt(currentUser.getId()))
                .totalDebtsThisMonth(debtRepository.calculateTotalDebtByDateRange(startOfMonth, endOfMonth, currentUser.getId()))
                .totalOverdueDebt(debtRepository.calculateTotalOverdueDebt(today, currentUser.getId()))
                .totalActiveClients(personRepository.countByIsDeletedFalseAndCreatedBy(currentUser.getId()))
                .topDebtors(topDebtors)
                .build();
        return new ApiResponse("Umumiy statistika",true, HttpStatus.OK,statResponse);
    }
}