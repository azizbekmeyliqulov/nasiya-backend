package xurshid_azizbek.com.example.nasiyabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PaymentRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.security.CurrentUser;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.PaymentService;

@RestController
@RequestMapping("/mahalla/{mahallaId}/person/{personId}/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Person uchun to'lov qabul qilish uchun api")
    public ResponseEntity<ApiResponse> create(@CurrentUser User user,
                                              @PathVariable Integer mahallaId,
                                              @PathVariable Integer personId,
                                              @RequestBody @Valid PaymentRequest paymentRequest) {
        ApiResponse response = paymentService.create(mahallaId, personId, paymentRequest, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Personning to'lovlar tarixini olish uchun api")
    public ResponseEntity<ApiResponse> getAllByPerson(@CurrentUser User user,
                                                      @PathVariable Integer mahallaId,
                                                      @PathVariable Integer personId) {
        ApiResponse response = paymentService.getAllByPerson(mahallaId, personId, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}