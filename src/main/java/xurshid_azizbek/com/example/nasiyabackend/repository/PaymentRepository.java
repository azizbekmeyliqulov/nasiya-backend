package xurshid_azizbek.com.example.nasiyabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xurshid_azizbek.com.example.nasiyabackend.entity.Payment;
import xurshid_azizbek.com.example.nasiyabackend.entity.Person;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByPersonOrderByCreatedAtAsc(Person person);
    List<Payment> findAllByPersonIdAndIsDeletedFalse(Integer personId);
}
