package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MahallaRepository extends JpaRepository<Mahalla, Integer> {

    boolean existsByUserAndNameIgnoreCase(User user, String name);
    List<Mahalla> findAllByUser(User user);
    Optional<Mahalla> findByIdAndUser(Integer id, User user);  // egalik tekshiruvi uchun
}
