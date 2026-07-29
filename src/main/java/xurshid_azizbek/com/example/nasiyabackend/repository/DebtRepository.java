package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.Debt;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt,Integer> {
    List<Debt> findAllByPersonAndSettledFalseOrderByCreatedAtAsc(Person person);
    List<Debt> findAllByPersonAndSettledTrueOrderByCreatedAtAsc(Person person);
    boolean existsByPersonAndSettledFalse(Person person);  // 3.4-band uchun — "had_active"
}
