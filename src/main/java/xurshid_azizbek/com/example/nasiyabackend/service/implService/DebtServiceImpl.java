package xurshid_azizbek.com.example.nasiyabackend.service.implService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xurshid_azizbek.com.example.nasiyabackend.entity.Debt;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.exception.NotFoundException;
import xurshid_azizbek.com.example.nasiyabackend.mapper.DebtMapper;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.DebtRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.DebtResponse;
import xurshid_azizbek.com.example.nasiyabackend.repository.DebtRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.MahallaRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.PersonRepository;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.DebtService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final PersonRepository personRepository;
    private final MahallaRepository mahallaRepository;
    private final DebtMapper mapper;

    @Override
    public ApiResponse create(Integer mahallaId, Integer personId, DebtRequest request, User currentUser) {
        log.info("Qarz yaratilmoqda  mahallaId: {} personId: {} userId: {}", mahallaId, personId, currentUser.getId());
        mahallaFound(mahallaId, currentUser.getId());
        Person person = personFound(personId, currentUser.getId());
        boolean hasOpenDebt = debtRepository.existsByPersonIdAndSettledFalseAndIsDeletedFalse(personId);
        if (request.dueDate() != null && !hasOpenDebt) {
            person.setDueDate(request.dueDate());
            personRepository.save(person);
        }
        Debt debt = mapper.requestToDebt(request, person);
        debtRepository.save(debt);
        log.info("Qarz yaratildi debtId: {} mahallaId: {} personId: {} userId: {}",debt.getId(), mahallaId, personId, currentUser.getId());
        return new ApiResponse("Qarz yaratildi",true, HttpStatus.CREATED,mapper.toResponse(debt));
    }

    @Override
    public ApiResponse getAllOpen(Integer mahallaId, Integer personId, User currentUser) {
        log.info("Ochiq qarzlar qidirilmoqda personId: {} userId: {}", personId, currentUser.getId());
        List<Debt> debts = debtRepository.findAllByPersonIdAndSettledFalseAndIsDeletedFalse(personId);
        List<DebtResponse> response = debts.stream().map(mapper::toResponse).toList();
        return new ApiResponse("Ochiq qarzlar",true,HttpStatus.OK,response);
    }

    @Override
    public ApiResponse getAllSettled(Integer mahallaId, Integer personId, User currentUser) {
        log.info("Yopilgan qarzlar qidirilmoqda personId: {} userId: {}", personId, currentUser.getId());
        List<Debt> debts = debtRepository.findAllByPersonIdAndSettledTrueAndIsDeletedFalse(personId);
        List<DebtResponse> response = debts.stream().map(mapper::toResponse).toList();
        return new ApiResponse("Yopilgan qarzlar",true,HttpStatus.OK,response);
    }

    @Override
    public ApiResponse getById(Integer mahallaId, Integer personId, Integer debtId, User currentUser) {
        log.info("Qarz qidirilmoqda debtId: {} mahallaId: {} personId: {} userId: {}",debtId, mahallaId, personId, currentUser.getId());
        Debt debt = debtFound(debtId, currentUser.getId());
        return new ApiResponse("Qarz topildi",true,HttpStatus.OK,mapper.toResponse(debt));

    }


    private Mahalla mahallaFound(Integer mahallaId,Integer userId){
       return mahallaRepository.findByIdAndUserIdAndIsDeletedFalse(mahallaId,userId).orElseThrow(()->{
            log.info("mahalla topilmadi mahalla id: {}",mahallaId);
            return new NotFoundException("mahalla topilmadi");
        });
    }
    private Person personFound(Integer personId,Integer userId){
        return personRepository.findByIdAndCreatedByAndIsDeletedFalse(personId,userId).orElseThrow(()->{
            log.info("person topilmadi  person id: {}",personId);
            return new NotFoundException("person topilmadi");
        });
    }
    private Debt debtFound(Integer debtId,Integer userId){
        return debtRepository.findByIdAndCreatedByAndIsDeletedFalse(debtId, userId).orElseThrow(() -> {
            log.info("Qarz topilmadi debtId: {}  userId: {}", debtId,  userId);
            return new NotFoundException("Qarz topilmadi");
        });
    }
}
