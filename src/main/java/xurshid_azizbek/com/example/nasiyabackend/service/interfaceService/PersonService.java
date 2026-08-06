package xurshid_azizbek.com.example.nasiyabackend.service.interfaceService;

import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonDueDateRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;

public interface PersonService {
    ApiResponse createPerson(User user, Integer mahallaId, PersonRequest personRequest);
    ApiResponse getAllPerson(User user, Integer mahallaId);
    ApiResponse getPersonById(User user, Integer personId);
    ApiResponse updatePerson(User user, Integer personId, PersonRequest personRequest);
    ApiResponse deletePerson(User user, Integer personId);
    ApiResponse updateDueDate(Integer personId, PersonDueDateRequest request, User currentUser);
}
