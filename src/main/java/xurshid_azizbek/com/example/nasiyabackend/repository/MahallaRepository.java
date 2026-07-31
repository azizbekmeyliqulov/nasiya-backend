package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.Mahalla;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.repository.projection.MahallaWithCountProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface MahallaRepository extends JpaRepository<Mahalla, Integer> {

    boolean existsByUserAndNameIgnoreCase(User user, String name);
    List<Mahalla> findAllByUser(User user);
    Optional<Mahalla> findByIdAndUser(Integer id, User user);  // egalik tekshiruvi uchun
    @Query(value = """
    SELECT m.id AS id, m.name AS name, m.created_at AS createdAt, COUNT(p.id) AS peopleCount
    FROM mahalla m
    LEFT JOIN person p ON p.mahalla_id = m.id AND p.is_deleted = false
    WHERE m.user_id = :userId AND m.is_deleted = false
    GROUP BY m.id, m.name, m.created_at
    ORDER BY m.created_at DESC
    """, nativeQuery = true)
    List<MahallaWithCountProjection> findAllByUserIdWithCount(@Param("userId") Integer userId);

    @Query(value = """
    SELECT m.id AS id, m.name AS name, m.created_at AS createdAt, COUNT(p.id) AS peopleCount
    FROM mahalla m
    LEFT JOIN person p ON p.mahalla_id = m.id AND p.is_deleted = false
    WHERE m.user_id = :userId AND m.id = :mahallaId AND m.is_deleted = false
    GROUP BY m.id, m.name, m.created_at
    """, nativeQuery = true)
    Optional<MahallaWithCountProjection> findByIdAndUserIdWithCount(
            @Param("mahallaId") Integer mahallaId, @Param("userId") Integer userId);
    Optional<Mahalla> findByIdAndUserIdAndIsDeletedFalse(Integer id, Integer userId);
}
