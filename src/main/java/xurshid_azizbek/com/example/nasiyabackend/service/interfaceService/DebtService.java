package xurshid_azizbek.com.example.nasiyabackend.service.interfaceService;

import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.DebtRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;

public interface DebtService {

    ApiResponse create(Integer mahallaId, Integer personId, DebtRequest request, User currentUser);

    ApiResponse getAllOpen(Integer mahallaId, Integer personId, User currentUser);

    ApiResponse getAllSettled(Integer mahallaId, Integer personId, User currentUser);

    ApiResponse getById(Integer mahallaId, Integer personId, Integer debtId, User currentUser);
}