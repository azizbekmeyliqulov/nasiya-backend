package xurshid_azizbek.com.example.nasiyabackend.service.interfaceService;

import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.MahallaRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;

public interface MahallaService {

    ApiResponse createMahalla(User user, MahallaRequest mahallaRequest);
    ApiResponse getAllMahalla(Integer userId);
    ApiResponse getMahallaById(Integer userId, Integer mahallaId);
    ApiResponse updateMahalla(Integer userId, Integer mahallaId, MahallaRequest mahallaRequest);
    ApiResponse deleteMahalla(Integer userId, Integer mahallaId);
}
