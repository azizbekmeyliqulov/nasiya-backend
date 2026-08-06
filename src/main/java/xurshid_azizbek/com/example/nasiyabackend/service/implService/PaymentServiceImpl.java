package xurshid_azizbek.com.example.nasiyabackend.service.implService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xurshid_azizbek.com.example.nasiyabackend.entity.*;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.DebtKind;
import xurshid_azizbek.com.example.nasiyabackend.exception.BadRequestException;
import xurshid_azizbek.com.example.nasiyabackend.exception.NotFoundException;
import xurshid_azizbek.com.example.nasiyabackend.mapper.PaymentMapper;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PaymentRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.ApiResponse;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.PaymentResponse;
import xurshid_azizbek.com.example.nasiyabackend.repository.DebtRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.MahallaRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.PaymentRepository;
import xurshid_azizbek.com.example.nasiyabackend.repository.PersonRepository;
import xurshid_azizbek.com.example.nasiyabackend.service.interfaceService.PaymentService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MahallaRepository mahallaRepository;
    private final PersonRepository personRepository;
    private final DebtRepository debtRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public ApiResponse create(Integer mahallaId, Integer personId, PaymentRequest request, User currentUser) {
        log.info("To'lov qilinmoqda personId: {} userId: {}", personId, currentUser.getId());

        mahallaFound(mahallaId, currentUser.getId());
        Person person = personFound(personId, currentUser.getId());

        List<Debt> unsettledDebts = debtRepository.findAllByPersonIdAndSettledFalseAndIsDeletedFalse(personId);
        Long outstanding = debtRepository.sumUnsettledByPersonId(personId);

        if (request.amount() > outstanding) {
            throw new BadRequestException("To'lov summasi qarzdan katta bo'lishi mumkin emas");
        }

        Long paid = request.amount();
        Long remaining = outstanding - paid;

        for (Debt debt : unsettledDebts) {
            debt.setSettled(true);
            debt.setSettledAt(LocalDateTime.now());
        }
        debtRepository.saveAll(unsettledDebts);

        if (remaining > 0) {
            Debt carryDebt = Debt.builder()
                    .person(person)
                    .kind(DebtKind.CARRY)
                    .amount(remaining)
                    .products("[\"Qoldiq qarz\"]")
                    .takenBy(null)
                    .takenByName("")
                    .note("")
                    .settled(false)
                    .build();
            debtRepository.save(carryDebt);
        }

        Payment payment = Payment.builder()
                .person(person)
                .amount(paid)
                .before(outstanding)
                .after(remaining)
                .build();
        paymentRepository.save(payment);

        log.info("To'lov yakunlandi personId: {} paid: {} remaining: {}", personId, paid, remaining);
        return new ApiResponse("To'lov qabul qilindi", true, HttpStatus.OK, paymentMapper.toResponse(payment));
    }
    @Override
    public ApiResponse getAllByPerson(Integer mahallaId, Integer personId, User currentUser) {
        log.info("To'lovlar tarixi qidirilmoqda personId: {} userId: {}", personId, currentUser.getId());
        List<Payment> payments = paymentRepository.findAllByPersonIdAndIsDeletedFalse(personId);
        List<PaymentResponse> response = payments.stream().map(paymentMapper::toResponse).toList();
        return new ApiResponse("To'lovlar tarixi", true, HttpStatus.OK, response);
    }

    private Mahalla mahallaFound(Integer mahallaId, Integer userId){
        return mahallaRepository.findByIdAndUserIdAndIsDeletedFalse(mahallaId,userId).orElseThrow(()->{
            log.info("mahalla topilmadi mahalla id: {}",mahallaId);
            return new NotFoundException("mahalla topilmadi");
        });
    }
    private Person personFound(Integer personId, Integer userId){
        return personRepository.findByIdAndCreatedByAndIsDeletedFalse(personId,userId).orElseThrow(()->{
            log.info("person topilmadi  person id: {}",personId);
            return new NotFoundException("person topilmadi");
        });
    }
}
