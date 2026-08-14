package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.PersonSearchResponse;

import java.time.LocalDate;
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
    @Query("""
    SELECT p FROM Person p
    WHERE p.mahalla = :mahalla  
      AND p.isDeleted = false
      AND lower(p.firstName) = lower(:firstName)
      AND lower(p.lastName) = lower(:lastName)
      AND lower(p.nickname) = lower(:nickname)
""")
    Optional<Person> findDuplicate(Mahalla mahalla, String firstName, String lastName, String nickname);
    long countByIsDeletedFalseAndCreatedBy(Integer createdBy);
    boolean existsByMahallaIdAndIsDeletedFalse(Integer mahallaId);

    long countByDueDateBeforeAndIsDeletedFalse(LocalDate date);
    @Query("""
    SELECT p FROM Person p
    WHERE p.mahalla = :mahalla
      AND p.isDeleted = false
      AND p.id <> :excludeId
      AND lower(p.firstName) = lower(:firstName)
      AND lower(p.lastName) = lower(:lastName)
      AND lower(p.nickname) = lower(:nickname)
""")
    Optional<Person> findDuplicateExcept(Mahalla mahalla, String firstName, String lastName, String nickname, Integer excludeId);
    @Query("SELECT new xurshid_azizbek.com.example.nasiyabackend.payload.response.PersonSearchResponse(" +
            "p.id, p.firstName, p.lastName, p.nickname, p.phone, p.number, p.mahalla.id, p.mahalla.name, " +
            "CASE WHEN p.dueDate < CURRENT_DATE AND EXISTS (" +
            "SELECT 1 FROM Debt d2 WHERE d2.person = p AND d2.settled = false AND d2.isDeleted = false) " +
            "THEN true ELSE false END) " +
            "FROM Person p WHERE p.mahalla.id = :mahallaId AND p.createdBy = :createdBy AND p.isDeleted = false AND (" +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.nickname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR CAST(p.number AS string) LIKE CONCAT('%', :keyword, '%'))")
    List<PersonSearchResponse> searchInMahalla(@Param("mahallaId") Integer mahallaId,
                                               @Param("keyword") String keyword,
                                               @Param("createdBy") Integer createdBy);
    @Query("SELECT new xurshid_azizbek.com.example.nasiyabackend.payload.response.PersonSearchResponse(" +
            "p.id, p.firstName, p.lastName, p.nickname, p.phone, p.number, p.mahalla.id, p.mahalla.name, " +
            "CASE WHEN p.dueDate < CURRENT_DATE AND EXISTS (" +
            "SELECT 1 FROM Debt d2 WHERE d2.person = p AND d2.settled = false AND d2.isDeleted = false) " +
            "THEN true ELSE false END) " +
            "FROM Person p WHERE p.createdBy = :createdBy AND p.isDeleted = false AND (" +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.nickname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR CAST(p.number AS string) LIKE CONCAT('%', :keyword, '%'))")
    List<PersonSearchResponse> searchAllMahallas(@Param("keyword") String keyword,
                                                 @Param("createdBy") Integer createdBy);
}
