package xurshid_azizbek.com.example.nasiyabackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.DashboardStatResponse;
import xurshid_azizbek.com.example.nasiyabackend.security.CurrentUser;
import xurshid_azizbek.com.example.nasiyabackend.service.StatisticsService;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final StatisticsService dashboardService;


    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse> getDashboardStats(
            @CurrentUser User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ApiResponse dashboardStatistics = dashboardService.getDashboardStatistics(user, page, size);

        return ResponseEntity.status(dashboardStatistics.getStatus()).body(dashboardStatistics);
    }
}