package xurshid_azizbek.com.example.nasiyabackend.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xurshid_azizbek.com.example.nasiyabackend.entity.Debt;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.DebtKind;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.DebtRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.DebtResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DebtMapper {

    private final ObjectMapper objectMapper;

    public Debt requestToDebt(DebtRequest request, Person person) {
        return Debt.builder()
                .person(person)
                .kind(DebtKind.DEBT)
                .amount(request.amount())
                .takenBy(request.takenBy())
                .takenByName(request.takenByName() == null ? "" : request.takenByName())
                .products(toJson(request.products()))
                .note(request.note() == null ? "" : request.note())
                .settled(false)
                .build();
    }

    public DebtResponse toResponse(Debt debt) {
        return new DebtResponse(
                debt.getId(),
                debt.getKind().name(),
                debt.getAmount(),
                debt.getTakenBy() != null ? debt.getTakenBy().getLabel() : null,
                debt.getTakenByName(),
                fromJson(debt.getProducts()),
                debt.getNote(),
                debt.isSettled(),
                debt.getSettledAt(),
                debt.getCreatedAt()
        );
    }

    private String toJson(List<String> products) {
        try {
            return objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Mahsulotlarni saqlashda xatolik");
        }
    }

    private List<String> fromJson(String products) {
        try {
            return objectMapper.readValue(products, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Mahsulotlarni o'qishda xatolik");
        }
    }
}
