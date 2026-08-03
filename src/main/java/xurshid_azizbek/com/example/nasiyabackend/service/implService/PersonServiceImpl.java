package xurshid_azizbek.com.example.nasiyabackend.service.implService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.exception.NotFoundException;
import xurshid_azizbek.com.example.nasiyabackend.mapper.PersonMapper;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.PersonResponse;
import xurshid_azizbek.com.example.nasiyabackend.repository.MahallaRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.PersonRepository;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.PersonService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MahallaRepository mahallaRepository;
    private final PersonMapper personMapper;

    @Override
    public ApiResponse createPerson(User user, Integer mahallaId, PersonRequest personRequest) {
        log.info("Creating Person mahallaId={},userId={} ", mahallaId, user.getId());
        Mahalla mahalla = mahallaFound(mahallaId, user.getId());
        int nextNumber = personRepository.findMaxNumberByMahalla(mahalla) + 1;
        Person person = personMapper.requestToPerson(mahalla, nextNumber, personRequest);
        Person save = personRepository.save(person);
        log.info("Person created id:{},number:{},mahalla: {}",save.getId(),save.getNumber(),mahalla.getId());
        return new ApiResponse("Person Created",true, HttpStatus.CREATED,personMapper.personToResponse(save,0L));
    }

    @Override
    public ApiResponse getAllPerson(User user, Integer mahallaId) {
        log.info("Get all persons mahallaId={},userId={} ", mahallaId, user.getId());
        Mahalla mahalla = mahallaFound(mahallaId, user.getId());
        List<PersonResponse> list = personRepository.findAllByMahallaAndCreatedByAndIsDeletedFalseOrderByNumberAsc(mahalla, user.getId()).stream().map(
                person -> personMapper.personToResponse(person, 0L)
        ).toList();
        if (list.isEmpty()) {
            log.info("person not found,mahallaId={}",mahallaId);
            return new ApiResponse("Odamlar hozircha mavjud emas",true,HttpStatus.OK,list);
        }

        return new ApiResponse("Odamlar topildi",true,HttpStatus.OK,list);
    }

    @Override
    public ApiResponse getPersonById(User user, Integer personId) {
        Person person = personFound(personId, user.getId());
        PersonResponse personResponse = personMapper.personToResponse(person, 0L);
        return new ApiResponse("Person topildi",true,HttpStatus.OK,personResponse);
    }

    @Override
    public ApiResponse updatePerson(User user, Integer personId, PersonRequest personRequest) {
        log.info("Personni yangilash personId={},userId={} ", personId, user.getId());
        Person person = personFound(personId, user.getId());
        personMapper.updateEntity(person, personRequest);
        personRepository.save(person);
        log.info("Person yangilandi personId={},userId:{} ", personId, user.getId());
        return new ApiResponse("Person yangilandi",true,HttpStatus.OK,personMapper.personToResponse(person,0L));
    }

    @Override
    public ApiResponse deletePerson(User user, Integer personId) {
        log.info("Person o'chirildi personId={},userId={} ", personId, user.getId());
        Person person = personFound(personId, user.getId());
        person.setIsDeleted(true);
        personRepository.save(person);
        log.info("Person o'chirildi  personId={},userId:{} ", personId, user.getId());
        return new ApiResponse("Person o'chirildi ",true,HttpStatus.OK,null);
    }





    private Mahalla mahallaFound(Integer mahallaId,Integer userId) {
       return mahallaRepository.findByIdAndUserIdAndIsDeletedFalse(mahallaId, userId).orElseThrow(()-> {
            log.info("Mahalla topilmadi id={},userId={} ", mahallaId, userId);
           return new NotFoundException("Mahalla topilmadi: " + mahallaId);
       }
       );
    }
    private Person personFound(Integer personId,Integer userId) {
        return personRepository.findByIdAndCreatedByAndIsDeletedFalse(personId,userId).orElseThrow(()->{
            log.info("Person topilmadi personId: {} userId: {} ", personId, userId);
            return new NotFoundException("Person topilmadi, id: " + personId);
        });
    }
}
