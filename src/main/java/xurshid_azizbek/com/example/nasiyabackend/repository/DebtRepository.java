package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.Debt;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt,Integer> {
    List<Debt> findAllByPersonAndSettledFalseOrderByCreatedAtAsc(Person person);
    List<Debt> findAllByPersonAndSettledTrueOrderByCreatedAtAsc(Person person);
    Optional<Debt> findByIdAndCreatedByAndIsDeletedFalse(Integer id, Integer createdBy);
    List<Debt> findAllByPersonIdAndSettledFalseAndIsDeletedFalse(Integer personId);
    List<Debt> findAllByPersonIdAndSettledTrueAndIsDeletedFalse(Integer personId);
    boolean existsByPersonIdAndSettledFalseAndIsDeletedFalse(Integer personId);
    boolean existsByPersonAndSettledFalse(Person person);
    @Query(value = "select coalesce(sum(amount),0) from debt where person_id=: personId and settled = false ",nativeQuery = true)
    Long sumUnsettledByPersonId(@Param("personId") Integer personId);
}
