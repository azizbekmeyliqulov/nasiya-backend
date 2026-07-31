package xurshid_azizbek.com.example.nasiyabackend.repository.projection;

import java.time.LocalDateTime;

public interface MahallaWithCountProjection {

    Integer getId();
    String getName();
    LocalDateTime getCreatedAt();
    Long getPeopleCount();
}
