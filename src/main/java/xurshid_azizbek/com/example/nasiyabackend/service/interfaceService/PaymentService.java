package xurshid_azizbek.com.example.nasiyabackend.service.interfaceService;

import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PaymentRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;

public interface PaymentService {

    ApiResponse create(Integer mahallaId, Integer personId, PaymentRequest request, User currentUser);

    ApiResponse getAllByPerson(Integer mahallaId, Integer personId, User currentUser);
}