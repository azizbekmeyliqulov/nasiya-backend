package xurshid_azizbek.com.example.nasiyabackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.security.CurrentUser;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonSearchController {

    private final PersonService personService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchAllMahallas(
            @RequestParam String keyword,
            @CurrentUser User user) {
        ApiResponse response = personService.searchAllMahallas(keyword, user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}