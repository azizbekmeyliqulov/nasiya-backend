package xurshid_azizbek.com.example.nasiyabackend.mapper;

import org.springframework.stereotype.Component;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.PersonResponse;

@Component
public class PersonMapper {

    public Person requestToPerson(Mahalla mahalla, Integer number, PersonRequest request) {
        return Person.builder()
                .mahalla(mahalla)
                .number(number)
                .firstName(request.firstName().trim())
                .lastName(request.lastName() == null ? "" : request.lastName().trim())
                .nickname(request.nickname() == null ? "" : request.nickname().trim())
                .phone(request.phone() == null ? "" : request.phone().trim())
                .build();
    }

    public PersonResponse personToResponse(Person person, Long balance) {
        return new PersonResponse(
                person.getId(),
                person.getMahalla().getId(),
                person.getNumber(),
                person.getFirstName(),
                person.getLastName(),
                person.getNickname(),
                person.getPhone(),
                person.getDueDate(),
                balance,
                person.getCreatedAt()
        );
    }
    public void updateEntity(Person person, PersonRequest personRequest) {
        person.setFirstName(personRequest.firstName().trim());
        person.setLastName(personRequest.lastName() == null ? "" : personRequest.lastName().trim());
        person.setNickname(personRequest.nickname()== null ? "" : personRequest.nickname().trim());
        person.setPhone(personRequest.phone()== null ? "" : personRequest.phone().trim());

    }
}
