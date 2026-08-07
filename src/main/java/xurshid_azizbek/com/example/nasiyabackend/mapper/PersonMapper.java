package xurshid_azizbek.com.example.nasiyabackend.mapper;

import org.springframework.stereotype.Component;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.PersonRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.PersonResponse;

@Component
public class PersonMapper {

    public Person requestToPerson(Mahalla mahalla, Integer number, String firstName, String lastName, String nickname, String phone) {
        return Person.builder()
                .mahalla(mahalla)
                .number(number)
                .firstName(firstName)
                .lastName(lastName)
                .nickname(nickname)
                .phone(phone)
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

    public void updateEntity(Person person, String firstName, String lastName, String nickname, String phone) {
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setNickname(nickname);
        person.setPhone(phone);
    }
}
