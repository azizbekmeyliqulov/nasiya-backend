package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    boolean existsByMahallaAndFirstNameIgnoreCaseAndLastNameIgnoreCaseAndIsDeletedFalse(
            Mahalla mahalla, String firstName, String lastName);
    List<Person> findAllByMahallaAndFirstNameIgnoreCaseAndLastNameIgnoreCaseAndIsDeletedFalse(
            Mahalla mahalla, String firstName, String lastName);

    List<Person> findAllByMahallaAndCreatedByAndIsDeletedFalseOrderByNumberAsc(Mahalla mahalla, Integer createdBy);

    Optional<Person> findByIdAndCreatedByAndIsDeletedFalse(Integer id, Integer createdBy);
    List<Person>findAllByMahallaIdAndIsDeletedFalse(Integer mahallaId);
    // isDeleted filtrsiz — raqam hech qachon qayta ishlatilmasligi kerak
    @Query("SELECT COALESCE(MAX(p.number), 0) FROM Person p WHERE p.mahalla = :mahalla")
    Integer findMaxNumberByMahalla(Mahalla mahalla);
}
