package xurshid_azizbek.com.example.nasiyabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonDueDateRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.security.CurrentUser;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.PersonService;

@RestController
@RequestMapping("/mahalla/{mahallaId}/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Person yaratish uchun api")
    public ResponseEntity<ApiResponse> createPerson(@CurrentUser User user,
                                                    @PathVariable Integer mahallaId,
                                                    @RequestBody @Valid PersonRequest personRequest) {
        ApiResponse response = personService.createPerson(user, mahallaId, personRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Bitta mahalladagi barcha personlar ro'yxatini olish uchun api")
    public ResponseEntity<ApiResponse> getAllPerson(@CurrentUser User user,
                                                    @PathVariable Integer mahallaId) {
        ApiResponse response = personService.getAllPerson(user, mahallaId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{personId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Bitta personni id bo'yicha olish uchun api")
    public ResponseEntity<ApiResponse> getPersonById(@CurrentUser User user,
                                                     @PathVariable Integer mahallaId,
                                                     @PathVariable Integer personId) {
        ApiResponse response = personService.getPersonById(user, personId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{personId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Personni yangilash uchun api")
    public ResponseEntity<ApiResponse> updatePerson(@CurrentUser User user,
                                                    @PathVariable Integer mahallaId,
                                                    @PathVariable Integer personId,
                                                    @RequestBody @Valid PersonRequest personRequest) {
        ApiResponse response = personService.updatePerson(user, personId, personRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{personId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Personni o'chirish uchun api")
    public ResponseEntity<ApiResponse> deletePerson(@CurrentUser User user,
                                                    @PathVariable Integer mahallaId,
                                                    @PathVariable Integer personId) {
        ApiResponse response = personService.deletePerson(user, personId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PatchMapping("/{personId}/due-date")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Personning qarz muddatini qo'lda o'zgartirish uchun api")
    public ResponseEntity<ApiResponse> updateDueDate(@CurrentUser User user,
                                                     @PathVariable Integer mahallaId,
                                                     @PathVariable Integer personId,
                                                     @RequestBody @Valid PersonDueDateRequest request) {
        ApiResponse response = personService.updateDueDate(personId, request, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/mahalla/{mahallaId}/person/search")
    public ResponseEntity<ApiResponse> searchInMahalla(
            @PathVariable Integer mahallaId,
            @RequestParam String keyword,
            @CurrentUser User user) {
        ApiResponse response = personService.searchInMahalla(mahallaId, keyword, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


}