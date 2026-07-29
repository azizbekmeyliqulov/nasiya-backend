package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.entity.UserSettings;

import java.util.Optional;

@Repository
public interface UserSettingsRepository  extends JpaRepository<UserSettings, Integer> {

    Optional<UserSettings> findByUser(User user);
}
