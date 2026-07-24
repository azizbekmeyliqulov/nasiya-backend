package xurshid_azizbek.com.example.nasiyabackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);
    List<User> findAllByRole(Role role);


    boolean existsByPhoneNumber(String phoneNumber);
}
