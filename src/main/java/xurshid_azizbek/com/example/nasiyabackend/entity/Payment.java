package xurshid_azizbek.com.example.nasiyabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import xurshid_azizbek.com.example.nasiyabackend.entity.abs.AbsEntity;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "payment")
public class Payment extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(nullable = false)
    private Long amount;   // qancha to'ladi

    @Column(nullable = false)
    private Long before;   // to'lovdan oldingi jami qarz

    @Column(nullable = false)
    private Long after;    // to'lovdan keyin qolgan qarz
}