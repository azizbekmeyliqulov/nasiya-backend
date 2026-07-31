package xurshid_azizbek.com.example.nasiyabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.MahallaRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.security.CurrentUser;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.MahallaService;

@RestController
@RequestMapping("/mahalla")
@RequiredArgsConstructor
public class MahallaController {

    private final MahallaService mahallaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Mahalla yaratish uchun api")
    public ResponseEntity<ApiResponse> createMahalla(@CurrentUser User user,
                                                     @RequestBody @Valid MahallaRequest mahallaRequest) {
        ApiResponse response = mahallaService.createMahalla(user, mahallaRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Barcha mahallalar ro'yxatini olish uchun api")
    public ResponseEntity<ApiResponse> getAllMahalla(@CurrentUser User user) {
        ApiResponse response = mahallaService.getAllMahalla(user.getId());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{mahallaId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Bitta mahallani id bo'yicha olish uchun api")
    public ResponseEntity<ApiResponse> getMahallaById(@CurrentUser User user,
                                                      @PathVariable Integer mahallaId) {
        ApiResponse response = mahallaService.getMahallaById(user.getId(), mahallaId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{mahallaId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Mahallani yangilash uchun api")
    public ResponseEntity<ApiResponse> updateMahalla(@CurrentUser User user,
                                                     @PathVariable Integer mahallaId,
                                                     @RequestBody @Valid MahallaRequest mahallaRequest) {
        ApiResponse response = mahallaService.updateMahalla(user.getId(), mahallaId, mahallaRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{mahallaId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Mahallani o'chirish uchun api")
    public ResponseEntity<ApiResponse> deleteMahalla(@CurrentUser User user,
                                                     @PathVariable Integer mahallaId) {
        ApiResponse response = mahallaService.deleteMahalla(user.getId(), mahallaId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
