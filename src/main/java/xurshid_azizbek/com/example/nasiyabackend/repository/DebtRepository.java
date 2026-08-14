package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.Debt;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.payload.dto.TopDebtorDto;
import xurshid_azizbek.com.example.nasiyabackend.repository.projection.PersonBalanceProjection;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt,Integer> {
    // 1. Jami faol (yopilmagan) qarzlar summasi
    @Query("SELECT COALESCE(SUM(d.amount), 0L) FROM Debt d WHERE d.settled = false AND d.isDeleted = false AND d.createdBy= :createdBy")
    Long calculateTotalActiveDebt(@Param("createdBy") Integer  createdBy);

    // 2. Jami yopilgan (to'langan) qarzlar summasi
    @Query("SELECT COALESCE(SUM(d.amount), 0L) FROM Debt d WHERE d.settled = true AND d.isDeleted = false AND d.createdBy= :createdBy")
    Long calculateTotalSettledDebt(@Param("createdBy") Integer  createdBy);

    // 3. Shu oyda qancha nasiya berildi? (createdAt bo'yicha)
    @Query("SELECT COALESCE(SUM(d.amount), 0L) FROM Debt d WHERE d.createdAt >= :startDate AND d.createdAt <= :endDate AND d.isDeleted = false AND d.createdBy = :createdBy")
    Long calculateTotalDebtByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("createdBy" )Integer createdBy);

    // Top qarzdorlarni ism, familiya va laqabi bilan tortib olish (N+1 siz)
    // Top qarzdorlarni ism, familiya va laqabi bilan tortib olish (N+1 siz)
    @Query("SELECT new xurshid_azizbek.com.example.nasiyabackend.payload.dto.TopDebtorDto(" +
            "p.mahalla.id, p.mahalla.name, p.id, p.firstName, p.lastName, p.nickname, SUM(d.amount)) " +
            "FROM Debt d JOIN d.person p " +
            "WHERE d.settled = false AND d.isDeleted = false AND d.createdBy = :createdBy " +
            "GROUP BY p.mahalla.id, p.mahalla.name, p.id, p.firstName, p.lastName, p.nickname " +
            "ORDER BY SUM(d.amount) DESC")
    List<TopDebtorDto> findTopDebtors(Pageable pageable, @Param("createdBy") Integer createdBy);

    // Muddati o'tib ketgan (dueDate < bugun) va haligacha qarzi bor mijozlarning jami qarzi
    @Query("SELECT COALESCE(SUM(d.amount), 0L) FROM Debt d JOIN d.person p " +
            "WHERE d.settled = false AND d.isDeleted = false AND p.dueDate < :today AND d.createdBy= :createdBy ")
    Long calculateTotalOverdueDebt(@Param("today") LocalDate today,@Param("createdBy") Integer createdBy);
    List<Debt> findAllByPersonAndSettledFalseOrderByCreatedAtAsc(Person person);
    List<Debt> findAllByPersonAndSettledTrueOrderByCreatedAtAsc(Person person);
    Optional<Debt> findByIdAndCreatedByAndIsDeletedFalse(Integer id, Integer createdBy);
    List<Debt> findAllByPersonIdAndSettledFalseAndIsDeletedFalse(Integer personId);
    List<Debt> findAllByPersonIdAndSettledTrueAndIsDeletedFalse(Integer personId);
    boolean existsByPersonIdAndSettledFalseAndIsDeletedFalse(Integer personId);
    boolean existsByPersonAndSettledFalse(Person person);
    @Query(value = "select coalesce(sum(amount),0) from debt where person_id = :personId and settled = false", nativeQuery = true)
    Long sumUnsettledByPersonId(@Param("personId") Integer personId);
    @Query(value = "SELECT person_id AS personId, COALESCE(SUM(amount), 0) AS balance " +
            "FROM debt WHERE person_id IN :personIds AND settled = false AND is_deleted = false " +
            "GROUP BY person_id", nativeQuery = true)
    List<PersonBalanceProjection> sumUnsettledByPersonIds(@Param("personIds") List<Integer> personIds);

}
