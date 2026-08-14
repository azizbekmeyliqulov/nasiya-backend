package xurshid_azizbek.com.example.nasiyabackend.service.implService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.exception.*;
import xurshid_azizbek.com.example.nasiyabackend.mapper.PersonMapper;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonExtendDueDateRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.PersonResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.PersonSearchResponse;
import xurshid_azizbek.com.example.nasiyabackend.repository.DebtRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.MahallaRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.PersonRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.projection.PersonBalanceProjection;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.PersonService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MahallaRepository mahallaRepository;
    private final PersonMapper personMapper;
    private final DebtRepository debtRepository;

    @Override
    public ApiResponse createPerson(User user, Integer mahallaId, PersonRequest personRequest) {
        log.info("Creating Person mahallaId={}, userId={}", mahallaId, user.getId());
        Mahalla mahalla = mahallaFound(mahallaId, user.getId());

        String firstName = norm(personRequest.firstName());
        String lastName = norm(personRequest.lastName());
        String nickname = norm(personRequest.nickname());
        String phone = norm(personRequest.phone());

        if (firstName.isEmpty()) {
            throw new BadRequestException("Ismni kiriting");
        }

        checkDuplicatePerson(mahalla, firstName, lastName, nickname, null);

        int nextNumber = personRepository.findMaxNumberByMahalla(mahalla) + 1;
        Person person = personMapper.requestToPerson(mahalla, nextNumber, firstName, lastName, nickname, phone);

        Person save;
        try {
            save = personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            // ikkita so'rov bir vaqtda kelgan — DB unique index ushlab qoldi
            log.info("Race condition: duplicate person on save, mahallaId={}", mahallaId);
            throw new AlreadyNameException("Bu odam allaqachon qo'shilgan", null, null);
        }

        log.info("Person created id:{}, number:{}, mahalla: {}", save.getId(), save.getNumber(), mahalla.getId());
        return new ApiResponse("Person yaratildi", true, HttpStatus.CREATED, personMapper.personToResponse(save, 0L));
    }

    @Override
    public ApiResponse getAllPerson(User user, Integer mahallaId) {
        log.info("Get all persons mahallaId={},userId={} ", mahallaId, user.getId());
        Mahalla mahalla = mahallaFound(mahallaId, user.getId());
        List<Person> persons = personRepository.findAllByMahallaIdAndIsDeletedFalse(mahallaId);
        List<Integer> personIds = persons.stream().map(Person::getId).toList();
        List<PersonBalanceProjection> balances = debtRepository.sumUnsettledByPersonIds(personIds);
        Map<Integer, Long> balanceMap = balances.stream()
                .collect(Collectors.toMap(PersonBalanceProjection::getPersonId, PersonBalanceProjection::getBalance));

        List<PersonResponse> list = personRepository.findAllByMahallaAndCreatedByAndIsDeletedFalseOrderByNumberAsc(mahalla, user.getId()).stream()
                .map(person -> personMapper.personToResponse(person, balanceMap.getOrDefault(person.getId(), 0L)))
                .toList();

        if (list.isEmpty()) {
            log.info("person not found,mahallaId={}", mahallaId);
            return new ApiResponse("Odamlar hozircha mavjud emas", true, HttpStatus.OK, list);
        }

        return new ApiResponse("Odamlar topildi", true, HttpStatus.OK, list);
    }

    @Override
    public ApiResponse getPersonById(User user, Integer personId) {
        Person person = personFound(personId, user.getId());
        Long balance = debtRepository.sumUnsettledByPersonId(personId);
        PersonResponse personResponse = personMapper.personToResponse(person, balance);
        return new ApiResponse("Person topildi", true, HttpStatus.OK, personResponse);
    }

    @Override
    public ApiResponse updatePerson(User user, Integer personId, PersonRequest personRequest) {
        log.info("Personni yangilash personId={}, userId={}", personId, user.getId());
        Person person = personFound(personId, user.getId());

        String firstName = norm(personRequest.firstName());
        String lastName = norm(personRequest.lastName());
        String nickname = norm(personRequest.nickname());
        String phone = norm(personRequest.phone());

        if (firstName.isEmpty()) {
            throw new BadRequestException("Ismni kiriting");
        }

        checkDuplicatePerson(person.getMahalla(), firstName, lastName, nickname, person.getId());

        personMapper.updateEntity(person, firstName, lastName, nickname, phone);

        try {
            personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            log.info("Race condition: duplicate person on update, personId={}", personId);
            throw new AlreadyNameException("Bu odam allaqachon qo'shilgan", null, null);
        }

        Long balance = debtRepository.sumUnsettledByPersonId(personId);
        log.info("Person yangilandi personId={}, userId:{}", personId, user.getId());
        return new ApiResponse("Person yangilandi", true, HttpStatus.OK, personMapper.personToResponse(person, balance));
    }

    @Override
    public ApiResponse deletePerson(User user, Integer personId) {
        log.info("Personni o'chirish so'rovi:  personId={},userId={} ", personId, user.getId());
        Person person = personFound(personId, user.getId());
        Long l = debtRepository.sumUnsettledByPersonId(personId);
        if (l == 0) {
            person.setIsDeleted(true);
            personRepository.save(person);
            log.info("Person o'chirildi  personId={},userId:{} ", personId, user.getId());
        }else {
            throw new PersonHasDebtException("Qarzi bor odamni o'chirib bulmaydi!");
        }
        return new ApiResponse("Person o'chirildi ", true, HttpStatus.OK, null);


    }

    @Override
    public ApiResponse extendDueDate(Integer personId, PersonExtendDueDateRequest request, User currentUser) {
        log.info("Due date uzaytirilmoqda personId: {} userId: {}", personId, currentUser.getId());
        Person person = personFound(personId, currentUser.getId());

        if (!person.getDueDate().isBefore(LocalDate.now())) {
            throw new DueDateNotExpiredException("Muddati hali o'tmagan, uzaytirib bo'lmaydi");
        }

        LocalDate newDueDate = LocalDate.now().plusDays(request.days());
        person.setDueDate(newDueDate);
        personRepository.save(person);

        log.info("Due date uzaytirildi personId: {} newDueDate: {}", personId, newDueDate);
        return new ApiResponse("Muddat uzaytirildi", true, HttpStatus.OK, personMapper.personToResponse(person, 0L));
    }

    @Override
    public ApiResponse searchInMahalla(Integer mahallaId, String keyword, User currentUser) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new EmptyKeywordException("Qidiruv so'zi bo'sh bo'lishi mumkin emas");
        }
        List<PersonSearchResponse> result = personRepository.searchInMahalla(mahallaId, keyword, currentUser.getId());
        return new ApiResponse("Qidiruv natijasi", true, HttpStatus.OK, result);
    }

    @Override
    public ApiResponse searchAllMahallas(String keyword, User currentUser) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new EmptyKeywordException("Qidiruv so'zi bo'sh bo'lishi mumkin emas");
        }
        List<PersonSearchResponse> personSearchResponses = personRepository.searchAllMahallas(keyword, currentUser.getId());
        return new ApiResponse("Qidiruv natijasi", true, HttpStatus.OK, personSearchResponses);
    }

    private Mahalla mahallaFound(Integer mahallaId, Integer userId) {
        return mahallaRepository.findByIdAndUserIdAndIsDeletedFalse(mahallaId, userId).orElseThrow(() -> {
                    log.info("Mahalla topilmadi id={},userId={} ", mahallaId, userId);
                    return new NotFoundException("Mahalla topilmadi: " + mahallaId);
                }
        );
    }

    private Person personFound(Integer personId, Integer userId) {
        return personRepository.findByIdAndCreatedByAndIsDeletedFalse(personId, userId).orElseThrow(() -> {
            log.info("Person topilmadi personId: {} userId: {} ", personId, userId);
            return new NotFoundException("Person topilmadi, id: " + personId);
        });
    }

    // faqat firstName + lastName + nickname uchligi solishtiriladi — telefon kalitga kirmaydi
    private void checkDuplicatePerson(Mahalla mahalla, String firstName, String lastName, String nickname, Integer excludePersonId) {
        Optional<Person> existing = (excludePersonId == null)
                ? personRepository.findDuplicate(mahalla, firstName, lastName, nickname)
                : personRepository.findDuplicateExcept(mahalla, firstName, lastName, nickname, excludePersonId);

        existing.ifPresent(p -> {
            log.info("Person duplicate found, mahallaId={}, firstName={}, lastName={}, nickname={}",
                    mahalla.getId(), firstName, lastName, nickname);

            String fullName = lastName.isEmpty() ? firstName : firstName + " " + lastName;
            String message = nickname.isEmpty()
                    ? "Bu mahallada «" + fullName + "» allaqachon bor. Farqlash uchun laqab yozing (masalan: kursdosh)"
                    : "Bu mahallada «" + fullName + " (" + nickname + ")» allaqachon bor";

            throw new AlreadyNameException(message, p.getId(), p.getNumber());
        });
    }

    // apostrof turlari + ortiqcha bo'shliqni normallashtirish (registr o'zgarmaydi — ekranda ko'rinishi buzilmasin)
    private static String norm(String s) {
        if (s == null) return "";
        return s.trim()
                .replaceAll("[ʻʼ‘’`´]", "'")
                .replaceAll("\\s+", " ");
    }
}