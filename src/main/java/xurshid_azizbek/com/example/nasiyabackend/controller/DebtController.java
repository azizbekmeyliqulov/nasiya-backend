package xurshid_azizbek.com.example.nasiyabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.DebtRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.security.CurrentUser;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.DebtService;

@RestController
@RequestMapping("/mahalla/{mahallaId}/person/{personId}/debt")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Person uchun yangi qarz yaratish uchun api")
    public ResponseEntity<ApiResponse> create(@CurrentUser User user,
                                              @PathVariable Integer mahallaId,
                                              @PathVariable Integer personId,
                                              @RequestBody @Valid DebtRequest debtRequest) {
        ApiResponse response = debtService.create(mahallaId, personId, debtRequest, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{debtId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Bitta qarzni id bo'yicha olish uchun api")
    public ResponseEntity<ApiResponse> getById(@CurrentUser User user,
                                               @PathVariable Integer mahallaId,
                                               @PathVariable Integer personId,
                                               @PathVariable Integer debtId) {
        ApiResponse response = debtService.getById(mahallaId, personId, debtId, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/open")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Personning ochiq qarzlari ro'yxatini olish uchun api")
    public ResponseEntity<ApiResponse> getAllOpen(@CurrentUser User user,
                                                  @PathVariable Integer mahallaId,
                                                  @PathVariable Integer personId) {
        ApiResponse response = debtService.getAllOpen(mahallaId, personId, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/settled")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Personning yopilgan qarzlari ro'yxatini olish uchun api")
    public ResponseEntity<ApiResponse> getAllSettled(@CurrentUser User user,
                                                     @PathVariable Integer mahallaId,
                                                     @PathVariable Integer personId) {
        ApiResponse response = debtService.getAllSettled(mahallaId, personId, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}