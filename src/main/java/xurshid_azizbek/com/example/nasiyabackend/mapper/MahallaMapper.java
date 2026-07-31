package xurshid_azizbek.com.example.nasiyabackend.mapper;

import org.springframework.stereotype.Component;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.request.MahallaRequest;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.MahallaResponse;
import xurshid_azizbek.com.example.nasiyabackend.repository.projection.MahallaWithCountProjection;

@Component
public class MahallaMapper {

    public Mahalla requestToMahalla(User user, String name) {
        return Mahalla.builder().
                user(user)
                .name(name)                                .
                build();
    }
    public MahallaResponse mahallaToResponse(Mahalla mahalla,Long peopleCount) {
        return MahallaResponse.builder()
                .id(mahalla.getId())
                .name(mahalla.getName())
                .peopleCount(peopleCount)
                .createdAt(mahalla.getCreatedAt())
                .build();

    }
        public MahallaResponse projectionToResponse(MahallaWithCountProjection projection){
            return MahallaResponse.builder()
                    .id(projection.getId())
                    .name(projection.getName())
                    .peopleCount(projection.getPeopleCount())
                    .createdAt(projection.getCreatedAt())
                    .build();
        }
}
